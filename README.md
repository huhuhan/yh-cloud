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


### 服务模块

```text
├─yh-cloud------------------------------父项目，公共依赖
│  │
│  ├─yh-common--------------------------公共依赖
│  │
│  ├─yh-activiti---------------------9205---工作流服务（非微服务，与其他服务无关）
│  │
│  ├─yh-actuator---------------------8220---健康监控服务
│  │
│  ├─yh-auth-------------------------9001---认证鉴权服务（授权中心）
│  │
│  ├─yh-gateway----------------------9000---网关服务（统一网关）
│  │
│  ├─yh-eureka-----------------------8761---注册中心
│  │
│  ├─yh-generator--------------------8200---代码生成器
│  │
│  ├─yh-user-------------------------9011---用户组织服务
│  │
│  ├─yh-sys--------------------------9021---系统通用服务
│  │
│  ├─yh-file-------------------------9031---文件系统服务
│  │
```


### 其他信息

- [开发说明](https://github.com/huhuhan/yh-cloud/blob/master/DEVELOPMENT_DOC.md)
- [更新日志](https://github.com/huhuhan/yh-cloud/blob/master/UPDATE_LOG.md)
- [前端项目](https://github.com/huhuhan/yh-cloud-ui)（适配部分项目例子）


- 项目例子：
  - Spring Cloud Oauth2 Demo：见Tag版本[oauth2](https://github.com/huhuhan/yh-cloud/releases/tag/oauth2)
  - Activiti 工作流 Demo：见Tag版本[activiti5](https://github.com/huhuhan/yh-cloud/releases/tag/activiti5)
- Demo演示：参考[demo-all](https://github.com/huhuhan/demo-all)项目


