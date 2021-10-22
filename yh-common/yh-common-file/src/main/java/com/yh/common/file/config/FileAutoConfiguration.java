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
@ConditionalOnProperty(
        prefix = UploaderProperties.PREFIX,
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = false
)
public class FileAutoConfiguration {

    @Bean
    public UploaderFactory uploaderFactory() {
        return new UploaderFactory();
    }

    @Bean
    public AliOssUploader aliOssUploader() {
        return new AliOssUploader();
    }

    @Bean
    public OrdinaryUploader ordinaryUploader() {
        return new OrdinaryUploader();
    }

    @Bean
    public MinIoOssUploader minIoOssUploader() {
        return new MinIoOssUploader();
    }

}
