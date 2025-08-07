# 🛡️ Timekeeper Common Security 模块使用说明

该模块基于 Spring Security + 自定义 `JwtDecoder` 实现了自定义 JWT 校验逻辑，支持用户身份验证、签名校验与用户权限注入。

---

## 📦 依赖引入

在你的业务模块的 `pom.xml` 中引入依赖（确保该模块已发布到本地或私服）：

```xml
 <dependency>
     <groupId>com.timekeeper</groupId>
     <artifactId>timekeeper-common-security</artifactId>
     <version>1.0.0</version>
 </dependency>
 ```

 ---

 ## ⚙️ 配置项

 在你的业务服务的 `application.yml` 或 `application.properties` 中添加 JWT 密钥配置：

 ```properties
 jwt.secret-key=your-secret-key
 ```

 ---

 ## ✅ 模块功能说明

 该模块包含以下核心功能：

 - ✅ 使用 `Hutool JWT` 实现轻量级的 Token 解码与验证。
 - ✅ 自定义 `JwtDecoder`（`BaseJwtDecoder`）实现：
   - Token 签名校验
   - Token 过期时间校验
   - 用户信息提取与验证（基于 `BaseUserDetailsService` 接口）
   - 自动注入用户权限与上下文身份

 ---

 ## 🔧 关键类说明

 ### BaseJwtDecoder

 位于 `com.timekeeper.common.security.configure` 包下，实现了 Spring Security 的 `JwtDecoder` 接口。

 核心方法 `decode(String token)` 流程：

 1. 解析传入的 JWT 字符串。
 2. 验证签名是否正确（使用 `jwt.secret-key`）。
 3. 校验 Token 是否过期。
 4. 根据 Token 中的用户名调用 `BaseUserDetailsService` 加载用户信息。
 5. 返回 Spring Security 兼容的 `Jwt` 对象，携带权限与用户详情。

 ---
 ## 🚀 复用及扩展

 - 你可以在不同微服务中复写 `BaseUserDetailsService` 接口，加载对应微服务的用户数据。
 - 该模块提供默认的用户服务实现作为兜底，避免注入失败。
 - 如需覆盖默认 `BaseJwtDecoder` 行为，可通过 Spring 的 Bean 优先级或配置排除进行定制。

 ---

 ## 📂 目录结构示例

 ```
 src
 └── main
     ├── java
     │   └── com
     │       └── timekeeper
     │           └── common
     │               └── security
     │                   ├── configure
     │                   │   └── BaseJwtDecoder.java
     │                   ├── service
     │                   │   ├── BaseUserDetails.java
     │                   │   └── BaseUserDetailsService.java
     │                   └── service.impl
     │                       └── DefaultUserDetailsService.java
     └── resources
         └── application.properties
 ```