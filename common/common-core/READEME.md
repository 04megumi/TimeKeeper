# 🧩 common-core 模块

`common-core` 是 TimeKeeper 项目的核心工具模块，主要提供以下通用功能支持：

- 🌐 全局常量定义
- ⚠️ 通用异常体系（基础异常、业务异常）
- ✅ 统一返回结果封装类

---

## 📁 目录结构说明

```
common-core
├── pom.xml                   # Maven 配置
├── READEME.md                # 模块说明文档
└── src
    └── main
        ├── java
        │   └── com.timekeeper.common.core
        │       ├── constant
        │       │   └── PaginationConstants.java       # 分页参数相关常量
        │       ├── exception
        │       │   ├── BaseException.java             # 自定义异常基类
        │       │   └── BusinessException.java         # 业务异常（支持错误码与信息）
        │       └── util
        │           └── R.java                         # 通用返回结果封装工具类
        └── resources
            └── application.yml                        # 默认配置文件（可选）
```

---

## 📌 说明

该模块一般被其他服务模块依赖引入，用于统一业务响应结构、异常模型和基础配置常量，避免重复定义。

建议仅包含无业务依赖的“纯工具类/通用模型”。