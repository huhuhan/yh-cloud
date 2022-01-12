## 使用说明

- maven执行`install`命令

- 引入本模块依赖包

- 配置如下

    ```yaml
    yh:
      uploader:
        # 默认方式：ordinary。可选ordinary、minio、aliyun
        default-type: ordinary
        # 本地存储，默认开启必须配置属性
        ordinary:
          path: /user/yh_upload
          domain: http://127.0.0.1:9031
        # MinIO存储，选择开启，并配置以下相关属性
        minio:
          enabled: false
          endpoint: http://xxx
          access-key:
          secret-key:
          bucket-name: testdemo
        # 阿里云OSS存储，选择开启，并配置以下相关属性
        aliyun:
          enabled: false
          endpoint: http://xxx
          access-key:
          secret-key:
          bucket-name: testdemo
    ```  
- 代码使用：通过`com.yh.common.file.uploader.UploaderFactory`静态方法获取`IUploader`操作文件
