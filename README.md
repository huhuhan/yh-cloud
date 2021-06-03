## 项目简介

### 技术框架

- Spring框架版本：Spring Cloud Hoxton.SR5 | Spring Boot 2.2.10.RELEASE
- 注册中心：Eureka
- 服务间通信：OpenFeign
- 服务熔断：Hystrix
- 统一网关：Spring Cloud Gateway
- 认证鉴权：Spring Cloud Oauth2 + JWT
- 服务监控：Spring Boot Admin



- 工作流引擎：Activiti



### 运行前提

配置Hosts
```python
127.0.0.1 yh-eureka
127.0.0.1 yh-gateway
```

### 服务模块

```text
├─yh-cloud------------------------------父项目，公共依赖
│  │
│  ├─yh-activiti------------------------工作流服务
│  │
│  ├─yh-actuator------------------------健康监控服务
│  │
│  ├─yh-auth----------------------------认证鉴权服务
│  │
│  ├─yh-common--------------------------公共依赖
│  │
│  ├─yh-gateway-------------------------网关服务
│  │
│  ├─yh-eureka--------------------------微服务注册中心
│  │
│  ├─yh-gateway-------------------------网关服务
│  │
│  ├─yh-generator-----------------------代码生成器
│  │
```


### 其他信息

- [更新日志](https://github.com/huhuhan/yh-cloud/blob/master/UPDATE_LOG.md)
- [前端项目](https://github.com/huhuhan/yh-cloud-ui)（适配部分项目例子）

- 项目例子：
  - Spring Cloud Oauth2 Demo：见Tag版本[oauth2](https://github.com/huhuhan/yh-cloud/releases/tag/oauth2)
  - Activiti 工作流 Demo：见Tag版本[activiti5](https://github.com/huhuhan/yh-cloud/releases/tag/activiti5)
- Demo演示：参考[demo-all](https://github.com/huhuhan/demo-all)项目

