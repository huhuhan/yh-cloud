package com.yh.common.file.uploader.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.yh.common.file.config.UploaderProperties;
import com.yh.common.file.model.ObjectInfoPo;
import com.yh.common.file.uploader.AbstractUploader;
import com.yh.common.file.uploader.UploaderFactory;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 本地目录存储
 *
 * @author yanghan
 * @date 2021/8/9
 */
public class OrdinaryUploader extends AbstractUploader {

    @Override
    public String type() {
        return UploaderProperties.PREFIX_ORDINARY;
    }

    @Override
    @SneakyThrows
    public ObjectInfoPo upload(MultipartFile file) {
        InputStream is = file.getInputStream();
        String path = super.getPath(this.getOrdinaryPath(), file.getOriginalFilename());
        FileUtil.writeFromStream(is, path);
        IoUtil.close(is);
        // 构建对象
        ObjectInfoPo objectInfoPo = new ObjectInfoPo();
        objectInfoPo.setPath(path);
        objectInfoPo.setHash(super.getHash(file));
        return objectInfoPo;
    }

    @Override
    @SneakyThrows
    public byte[] take(String path) {
        return IoUtil.readBytes(FileUtil.getInputStream(path));
    }

    @Override
    public boolean remove(String path, String hash) {
        return FileUtil.del(path);
    }

    @SneakyThrows
    private String getOrdinaryPath() {
        try {
            return UploaderFactory.uploaderProperties.getOrdinary().getPath();
        } catch (Exception e) {
            throw new Exception("[" + UploaderProperties.PREFIX_ORDINARY + "]上传器配置错误，请校验！");
        }
    }

}
