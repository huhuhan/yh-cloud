package com.yh.common.file.model;

import lombok.Data;

/**
 * OSS存储对象服务的配置信息
 *
 * @author yanghan
 * @date 2021/10/20
 */
@Data
public class OssPo {
    /** 域名 */
    private String endpoint;
    /** 访问KEY */
    private String accesskey;
    /** 访问密钥 */
    private String secretKey;
    /** 桶 */
    private String bucketName;
}
