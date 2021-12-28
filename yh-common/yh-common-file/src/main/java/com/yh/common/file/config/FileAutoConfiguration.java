package com.yh.common.file.config;

import com.yh.common.file.uploader.UploaderFactory;
import com.yh.common.file.uploader.impl.AliOssUploader;
import com.yh.common.file.uploader.impl.MinIoOssUploader;
import com.yh.common.file.uploader.impl.OrdinaryUploader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties({UploaderProperties.class})
public class FileAutoConfiguration {

    @Bean
    public UploaderFactory uploaderFactory() {
        return new UploaderFactory();
    }

    @Bean
    public OrdinaryUploader ordinaryUploader() {
        return new OrdinaryUploader();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = UploaderProperties.PREFIX + "." + UploaderProperties.PREFIX_ALIYUN,
            name = {"enabled"},
            havingValue = "true"
    )
    public AliOssUploader aliOssUploader() {
        return new AliOssUploader();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = UploaderProperties.PREFIX + "." + UploaderProperties.PREFIX_MINIO,
            name = {"enabled"},
            havingValue = "true"
    )
    public MinIoOssUploader minIoOssUploader() {
        return new MinIoOssUploader();
    }

}
