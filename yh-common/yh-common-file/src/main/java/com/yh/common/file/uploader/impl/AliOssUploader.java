package com.yh.common.file.uploader.impl;

import cn.hutool.core.io.IoUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.yh.common.file.config.UploaderProperties;
import com.yh.common.file.model.ObjectInfoPo;
import com.yh.common.file.model.OssPo;
import com.yh.common.file.uploader.AbstractUploader;
import com.yh.common.file.uploader.UploaderFactory;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;

/**
 * 阿里云OSS对象存储
 *
 * @author yanghan
 * @date 2021/8/9
 */
public class AliOssUploader extends AbstractUploader {

    private OSS oss;

    @Override
    public String type() {
        return UploaderProperties.PREFIX_ALIYUN;
    }


    @Override
    @SneakyThrows
    public ObjectInfoPo upload(byte[] file, String fileName) {
        OSS ossClient = this.getClient();
        String path = super.getPath("", fileName);
        ByteArrayInputStream is = new ByteArrayInputStream(file);
        ossClient.putObject(this.getBucket(), path, is);
        IoUtil.close(is);
        this.shutdown();

        ObjectInfoPo objectInfoPo = new ObjectInfoPo();
        // 将bucket作为前缀加到路径中
        objectInfoPo.setPath(this.getBucket() + path);
        objectInfoPo.setHash(super.getHash(file));
        return objectInfoPo;
    }

    @Override
    public byte[] take(String path) {
        OSS ossClient = this.getClient();
        int firstIndex = path.indexOf("/");
        String bucket = path.substring(0, firstIndex);
        String fileName = path.substring(firstIndex + 1);
        OSSObject object = ossClient.getObject(bucket, fileName);
        this.shutdown();
        return IoUtil.readBytes(object.getObjectContent(), true);
    }

    @Override
    public boolean remove(String path, String hash) {
        // 删除文件或目录。如果要删除目录，目录必须为空。
        OSS ossClient = this.getClient();
        int firstIndex = path.indexOf("/");
        String bucket = path.substring(0, firstIndex);
        String fileName = path.substring(firstIndex + 1);
        ossClient.deleteObject(bucket, fileName);
        this.shutdown();
        return true;
    }


    private String getBucket() {
        OssPo ossPo = UploaderFactory.uploaderProperties.getAliyun();
        return ossPo.getBucketName();
    }

    @SneakyThrows
    private OSS getClient() {
        if (null == oss) {
            try {
                OssPo ossPo = UploaderFactory.uploaderProperties.getAliyun();
                oss = new OSSClientBuilder().build(ossPo.getEndpoint(), ossPo.getAccessKey(), ossPo.getSecretKey());
            } catch (Exception e) {
                throw new Exception("[" + UploaderProperties.PREFIX_ALIYUN + "]上传器配置错误，请校验！");
            }
        }
        return oss;
    }

    /**
     * 阿里云客户端每次使用需要关闭
     */
    private void shutdown() {
        oss.shutdown();
        oss = null;
    }
}
