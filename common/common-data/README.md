# common-data 模块

该模块封装了 Spring Boot 项目中常用的数据访问基础配置，整合了：

- ✅ MyBatis-Plus 插件体系（分页、乐观锁）
- ✅ Druid 数据源管理和 SQL 日志格式化
- ✅ 元数据自动填充审计字段
- ✅ 当前用户信息注入服务（`CurrentUserService`）

---

## 🔧 配置说明

### 1️⃣ `MybatisPlusConfiguration`

注册 **Mybatis-Plus 插件拦截器** 和 **元数据自动填充器**。

```yaml
# application.yml 示例
mybatis-plus:
    interceptor:
        optimistic-lock: true   # 是否启用乐观锁插件（默认 false）
        show-sql: true          # 是否打印 SQL（默认 true）
```

#### ✅ 分页插件

分页插件始终启用，无需额外配置：

- 插件类：`PaginationInnerInterceptor`
- 默认最大分页记录数：`1000`

该插件会自动拦截分页查询语句，适用于所有 `Page<T>` 方式的分页调用。

#### ✅ 乐观锁插件

是否启用由配置项控制：

```yaml
mybatis-plus:
    interceptor:
      optimistic-lock: true
```

- 插件类：`OptimisticLockerInnerInterceptor`
- 要求实体类中定义 `@Version` 字段：

```java
@TableName("user")
public class User {

    @Version
    private Integer version;

    // 其他字段...
}
```

该插件用于多线程或并发场景下的数据安全更新，防止“最后写入覆盖”问题。

#### ✅ 元数据自动填充器

自动填充字段配置示例：

```java
/**
 * 创建时间
 */
@TableField(value = "create_time", fill = FieldFill.INSERT)
protected LocalDateTime createTime;

/**
 * 修改时间
 */
@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
protected LocalDateTime updateTime;

/**
 * 创建人
 */
@TableField(value = "create_by", fill = FieldFill.INSERT)
protected Integer createBy;

/**
 * 修改人
 */
@TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
protected Integer updateBy;
```

实际填充值来源于 `CurrentUserService`，你可以自定义这个接口以提供当前用户标识信息。

---

### 2️⃣ `DruidDataSourceConfig`

该类负责初始化 Druid 数据源并配置 SQL 日志格式化功能。

#### ✅ 配置方式：

```yaml
spring:
  datasource:
    username: ${MYSQL_USER:root}
    password: ${MYSQL_PWD:Wzy20041224!}
    url: jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DB:summer2025_dev}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true
```

#### ✅ SQL 日志打印（可选）

通过如下配置开启 SQL 日志输出：

```yaml
mybatis-plus:
    show-sql: true
```

这会启用自定义的 `DruidSqlLogFilter`，对控制台 SQL 输出进行格式化、美化，便于开发调试。

## 📝 SQL 日志输出示例

当 `mybatis-plus.show-sql=true` 时，控制台将打印格式化后的 SQL 语句，包含执行时间和数据库类型标识：

```
--------------------------------[ MySQL Sql Log: 2025-08-07 12:00:00 ]---------------------------------
SELECT id, username, email, create_time, update_time
FROM user
WHERE status = 1
ORDER BY create_time DESC
LIMIT 10
--------------------------------[ Sql Execution Time: 12.387ms ]---------------------------------
```

---

## 📦 模块结构简述

```text
common-data
└── src
└── main
├── java
│   └── com.timekeeper.common.data.mybatis
│       ├── config           # MyBatisPlus / Druid 配置
│       ├── filter           # Druid SQL 日志过滤器
│       ├── handler          # 审计字段填充器
│       ├── model            # 数据模型基类
│       ├── service          # 当前用户服务接口与实现
│       └── util             # 通用分页查询工具
└── resources
└── application.properties
```

---

## 💡 自定义扩展建议

- 若需自定义当前用户信息来源，请实现并注册自定义 `CurrentUserService`。
- 可根据业务场景扩展分页拦截器、审计字段策略等。

---

> 作者：魏子越  
> 更新时间：2025-08