
## 服务运行

### 配置Hosts

```python
127.0.0.1 yh-eureka
127.0.0.1 yh-gateway
```


### 服务启动

依次启动

1. yh-eureka
2. yh-gateway
3. yh-actuator (可选）
4. yh-auth-server (可选，默认采用demo-user-api）
5. yh-user-server (可选，替代demo-user-api)


## 业务服务

> 参考例子：yh-user


### 新建服务

#### 创建子模块

1. 新建Maven子模块（New Module）

2. 补充以下依赖

   ```xml
   <project>
       <dependencies>       
           <dependency>
               <groupId>com.yh.cloud</groupId>
               <artifactId>yh-common-base</artifactId>
           </dependency>
           <dependency>
               <groupId>com.yh.cloud</groupId>
               <artifactId>yh-common-web</artifactId>
           </dependency>
           <dependency>
               <groupId>com.yh.cloud</groupId>
               <artifactId>yh-common-db</artifactId>
           </dependency>  
           <!--可选-->
           <dependency>
               <groupId>com.yh.cloud</groupId>
               <artifactId>yh-common-ribbon</artifactId>
           </dependency>
           <!--可选-->
           <dependency>
               <groupId>com.yh.cloud</groupId>
               <artifactId>yh-common-auth</artifactId>
           </dependency>
       </dependencies>
   
       <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
                   <configuration>
                       <!--可执行jar，要求有输入函数main-->
                       <executable>true</executable>
                   </configuration>
                   <executions>
                       <execution>
                           <goals>
                               <!--将其他依赖包也打包进去-->
                               <goal>repackage</goal>
                           </goals>
                       </execution>
                   </executions>
               </plugin>
           </plugins>
           <!--打包包名-->
           <finalName>${project.artifactId}</finalName>
       </build>
   </project>

   ```


#### 创建单独项目

1. 创建Maven项目（New Project），选择Maven

2. yh-cloud项目（主要是yh-common），Maven需要先执行`Install`命令

3. 引入Spring Boot 、Cloud版本，版本保持一致

   ```xml
   <!--依赖声明管理-->
   <dependencyManagement>
       <dependencies>
           <!--************************Spring******************-->
           <!--spring cloud 组件-->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-dependencies</artifactId>
               <version>${spring.cloud.version}</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
           <!--spring boot 组件-->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-dependencies</artifactId>
               <version>${spring-boot.version}</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
           <!--************************本项目******************-->
       </dependencies>
   </dependencyManagement>
   ```
```
   
基本依赖版本号
   
   ```xml
   <!--版本号-->
   <properties>
       <!--基本构建编译信息-->
       <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
       <maven.compiler.source>1.8</maven.compiler.source>
       <maven.compiler.target>1.8</maven.compiler.target>
       <java.version>1.8</java.version>
       <!--自定义模块版本-->
       <yh-cloud.version>1.0-SNAPSHOT</yh-cloud.version>
       <yh-common.version>1.0-SNAPSHOT</yh-common.version>
       <!--第三方依赖版本-->
       <spring-boot.version>2.2.10.RELEASE</spring-boot.version>
       <spring.cloud.version>Hoxton.SR5</spring.cloud.version>
   </properties>
```

   


### 服务注册

引入依赖包
```xml
        <!--微服务 客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```
添加配置，建议命名为`bootstrap.yml`
```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://yh:yh@sc-eureka:8761/eureka/
  instance:
    instance-id: ${spring.application.name}:${spring.cloud.client.ip-address}:${server.port}
```


### 认证鉴权



#### 请求参数


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


### 网络隔离模式

- 默认模式，【yh-gateway】已集成认证鉴权

- 所有请求必须通过【yh-gateway】转发，服务部署时仅开放【yh-gateway】端口。

- 获取授权，请求经【yh-gateway】转发至【yh--auth-server】
- 请求，【yh-gateway】认证鉴权，再转发


### 无网络隔离模式

- 忽略yh-gateway服务，直接请求业务服务

- 若需要统一网关，则新建服务或移除auth依赖包注释相关代码

- 业务服务需要认证鉴权的话，集成步骤如下：

  1. 引入依赖

     ```xml
             <dependency>
                 <groupId>com.yh.cloud</groupId>
                 <artifactId>yh-common-auth</artifactId>
             </dependency>
     ```

  2. 添加配置

     ```yaml
     #作为需要oauth授权认证的资源客户端
     security:
       oauth2:
         client:
           client-id: client_demo
           client-secret: secret_client_demo
         resource:
         # yh-auth服务地址
           token-info-uri: http://localhost:9001/oauth/check_token
     ```

  3. 注入配置类

     ```java
     @Configuration
     @EnableResourceServer
     public class MyResourceServerConfig extends DefaultResourceServerConfig {
     }
     ```

     


### 代码生成器

- 启动【yh-generator】服务，访问Swagger地址

- 请求接口`/generator/mysql/`

- 默认配置文件参考`generator.properties`，接口请求参数支持覆盖

- 生成的模板代码，依赖于

  ```xml
  <dependencies>
      <dependency>
          <groupId>com.yh.cloud</groupId>
          <artifactId>yh-common-web</artifactId>
      </dependency>
      <dependency>
          <groupId>com.yh.cloud</groupId>
          <artifactId>yh-common-db</artifactId>
      </dependency>
      <dependency>
          <groupId>com.yh.cloud</groupId>
          <artifactId>yh-common-base</artifactId>
      </dependency>
      <!-- 数据库驱动依赖自行选择 -->
      <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
      </dependency>
  </dependencies>
  ```

- 应用，mybatis-plus的配置

    ```java
    @Configuration
    @MapperScan(value = {"com.yh.cloud.xx.mapper"})
    public class MyBatisPlusConfig {
    }
    ```


