package com.timekeeper.common.data.mybatis.filter;

import com.alibaba.druid.DbType;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

/**
 * 打印可执行的 sql 日志
 */
@Slf4j
public class DruidSqlLogFilter extends FilterEventAdapter {

    private final boolean showSql;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final SQLUtils.FormatOption FORMAT_OPTION = new SQLUtils.FormatOption(false, false);

    public DruidSqlLogFilter(boolean showSql) {
        this.showSql = showSql;
    }

    @Override
    protected void statementExecuteBefore(StatementProxy statement, String sql) {
        statement.setLastExecuteStartNano();
    }

    @Override
    protected void statementExecuteBatchBefore(StatementProxy statement) {
        statement.setLastExecuteStartNano();
    }

    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        statement.setLastExecuteStartNano();
    }

    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        statement.setLastExecuteStartNano();
    }

    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
        statement.setLastExecuteTimeNano();
    }

    @Override
    protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
        statement.setLastExecuteTimeNano();
    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        statement.setLastExecuteTimeNano();
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        statement.setLastExecuteTimeNano();
    }

    @Override
    public void statement_close(FilterChain chain, StatementProxy statement) throws SQLException {
        // 先调用父类关闭 statement
        super.statement_close(chain, statement);

        // 支持动态开启
        if (!showSql) {
            return;
        }

        // 是否开启调试
        if (!log.isInfoEnabled()) {
            return;
        }

        // 打印可执行的 sql
        String sql = statement.getBatchSql();
        if (StringUtils.isEmpty(sql)) {
            return;
        }
        int parametersSize = statement.getParametersSize();
        List<Object> parameters = new ArrayList<>(parametersSize);
        for (int i = 0; i < parametersSize; ++i) {
            parameters.add(getJdbcParameter(statement.getParameter(i)));
        }

        DbType dbType = DbType.of(statement.getConnectionProxy().getDirectDataSource().getDbType());
        String formattedSql = SQLUtils.format(sql, dbType, parameters, FORMAT_OPTION);
        printSql(formattedSql, statement);
    }

    /**
     * 获取 jdbc 参数
     *
     * @param jdbcParam jdbc 参数
     * @return 参数值
     */
    private static Object getJdbcParameter(JdbcParameter jdbcParam) {
        if (jdbcParam == null) {
            return null;
        }
        Object value = jdbcParam.getValue();
        // 处理 java8 时间
        if (value instanceof TemporalAccessor) {
            return value.toString();
        }
        return value;
    }

    /**
     * 打印可执行的 sql
     *
     * @param sql 执行的 sql
     * @param statement statement
     */
    private static void printSql(String sql, StatementProxy statement) {
        String sqlLogger = "\n--------------------------------[ {} Sql Log: {} ]---------------------------------\n"
                + "{}"
                + "\n--------------------------------[ Sql Execution Time: {} ]---------------------------------\n";
        log.info(sqlLogger,
                getDbName(statement.getConnectionProxy().getDirectDataSource().getName()),
                formatter.format(LocalDateTime.now()),
                sql.trim(),
                format(statement.getLastExecuteTimeNano()));
    }

    /**
     * 格式化执行时间，单位为 ms 和 s，保留三位小数
     *
     * @param nanos 纳秒
     * @return 格式化后的时间
     */
    private static String format(long nanos) {
        if (nanos < 1) {
            return "0ms";
        }
        double millis = (double) nanos / (1000 * 1000);
        // 不够 1 ms，最小单位为 ms
        if (millis > 1000) {
            return String.format("%.3fs", millis / 1000);
        } else {
            return String.format("%.3fms", millis);
        }
    }

    /**
     * 获取数据库名称
     *
     * @param dbName 数据库名称
     * @return 数据库名称
     */
    private static String getDbName(String dbName) {
        switch (dbName) {
            case "mysql":
                return "MySQL";
            case "oushu":
                return "OuShu";
            default:
                return "Unknown";
        }
    }
}
