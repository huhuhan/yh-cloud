package com.yh.common.file.uploader.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.yh.common.file.config.UploaderProperties;
import com.yh.common.file.model.ObjectInfoPo;
import com.yh.common.file.model.OssPo;
import com.yh.common.file.uploader.AbstractUploader;
import com.yh.common.file.uploader.UploaderFactory;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.Struct;

/**
 * OSS对象存储，MinIO服务
 *
 * @author yanghan
 * @date 2021/8/9
 */
@Service
public class MinIoOssUploader extends AbstractUploader {

    private MinioClient minioClient;

    @Override
    public String type() {
        return UploaderProperties.PREFIX_MINIO;
    }

    @Override
    @SneakyThrows
    public ObjectInfoPo upload(MultipartFile file) {
        MinioClient client = this.getClient();
        String path = super.getPath("", file.getOriginalFilename());
        InputStream is = file.getInputStream();
        client.putObject(this.getBucket(), path, is, new PutObjectOptions(is.available(), -1));
        IoUtil.close(is);
        // 将bucket作为前缀加到路径中
        path = this.getBucket() + "/" + path;
        // 构建对象
        ObjectInfoPo objectInfoPo = new ObjectInfoPo();
        objectInfoPo.setPath(path);
        objectInfoPo.setHash(super.getHash(file));
        return objectInfoPo;
    }

    @Override
    @SneakyThrows
    public byte[] take(String path) {
        MinioClient client = this.getClient();
        int firstIndex = path.indexOf("/");
        String bucket = path.substring(0, firstIndex);
        String fileName = path.substring(firstIndex + 1);
        InputStream in = client.getObject(bucket, fileName);
        return IoUtil.readBytes(in, true);
    }

    @Override
    @SneakyThrows
    public void remove(String path) {
        MinioClient client = this.getClient();
        int firstIndex = path.indexOf("/");
        String bucket = path.substring(0, firstIndex);
        String fileName = path.substring(firstIndex + 1);
        client.removeObject(bucket, fileName);
    }

    @SneakyThrows
    private MinioClient getClient() {
        if (null == minioClient) {
            try {
                OssPo ossPo = UploaderFactory.uploaderProperties.getMinio();
                if (StrUtil.isBlank(ossPo.getAccessKey()) && StrUtil.isBlank(ossPo.getSecretKey())) {
                    // 开放读写的bucket可不用配置密钥
                    minioClient = new MinioClient(ossPo.getEndpoint());
                } else {
                    minioClient = new MinioClient(ossPo.getEndpoint(), ossPo.getAccessKey(), ossPo.getSecretKey());
                }
            } catch (Exception e) {
                throw new Exception("[" + UploaderProperties.PREFIX_MINIO + "]上传器配置错误，请校验！");
            }
        }
        return minioClient;
    }

    private String getBucket() {
        OssPo ossPo = UploaderFactory.uploaderProperties.getMinio();
        return ossPo.getBucketName();
    }
}
