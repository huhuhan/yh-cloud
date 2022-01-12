## 使用说明


### 认证鉴权

  1. 业务服务，引入本模块依赖包
  
  2. 添加配置

     ```yaml
     #作为需要oauth授权认证的资源客户端
     security:
       oauth2:
         client:
           client-id: client_demo
           client-secret: secret_client_demo
         resource:
         # yh-auth-server服务地址
           token-info-uri: http://localhost:9001/oauth/check_token
     ```

  3. 注入配置类

     ```java
     @Configuration
     @EnableResourceServer
     public class MyResourceServerConfig extends DefaultResourceServerConfig {
     }
     ```

  4. 白名单配置
  
        ```yaml
        yh:
          # 白名单路径
          security:
            authIgnore:
              - "/**/oauth/**"
              - "/**/logout"
              - "/*/v2/api-docs"
              - "/fallback"
              - "/actuator/**"
            xssIgnore:
              - "127.0.0.1"
            csrfIgnore:
              - "127.0.0.1"
        ```
        





### 接口说明

基于Spring Cloud Oauth2提供的接口认证，详细参考[Spring Cloud OAuth2 Demo | 寒冷如铁](https://huhuhan.github.io/blog/views/spring/oauth2.html)



### 请求参数


由安全认证保护的服务接口，必须带token参数

- 方式一，请求头带`Authorization`参数

  ```go
    headers: { 
      'Authorization': 'Bearer 1b03594f-c51d-4a7a-9274-d018d27a7ff8'
    }
  ```

- 方式二，请求中参数`access_token`

  ```go
  {
   'access_token': '1b03594f-c51d-4a7a-9274-d018d27a7ff8' 
  }
  ```



### 优化扩展

详细参考[Spring Security Oauth2 总结 | 寒冷如铁 ](https://huhuhan.github.io/blog/views/spring/spring-security-oauth2.html)

