package com.yh.cloud.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.cloud.file.mapper.SysFileMapper;
import com.yh.cloud.file.model.entity.SysFile;
import com.yh.cloud.file.model.vo.SysFileQuery;
import com.yh.cloud.file.service.ISysFileService;
import com.yh.common.db.service.impl.SuperServiceImpl;
import com.yh.common.file.model.ObjectInfoPo;
import com.yh.common.file.uploader.IUploader;
import com.yh.common.file.uploader.UploaderFactory;
import com.yh.common.web.exception.BusinessException;
import com.yh.common.web.wrapper.ReturnCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 系统附件表
 *
 * @author yanghan
 * @date 2021-10-20 11:15:16
 */
@Service
public class SysFileServiceImpl extends SuperServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    @Override
    public IPage<SysFile> findList(SysFileQuery<SysFile> sysFileQuery) {
        IPage<SysFile> pageQuery = sysFileQuery.initPageQuery();
        QueryWrapper<SysFile> queryWrapper = Wrappers.query();
        sysFileQuery.initFilter(queryWrapper);
        return this.page(pageQuery, queryWrapper);
    }

    @Override
    public SysFile uploadFile(MultipartFile file, String uploaderType) throws IOException {
        byte[] data = IoUtil.readBytes(file.getInputStream());
        String fileName = file.getOriginalFilename();

        SysFile sysFile = new SysFile();
        sysFile.setName(fileName);
        sysFile.setExt(FileUtil.extName(fileName));
        // 选择上传器存储至文件系统
        IUploader iUploader = UploaderFactory.getUploader(uploaderType);
        sysFile.setUploader(iUploader.type());
        ObjectInfoPo objectInfoPo = iUploader.upload(data, fileName);
        BeanUtil.copyProperties(objectInfoPo, sysFile);
        // 保存记录
        this.save(sysFile);
        return this.getById(sysFile.getId());
    }

    @Override
    public byte[] download(String fileId, String uploaderType) {
        SysFile sysFile = this.getById(fileId);
        IUploader uploader = UploaderFactory.getUploader(uploaderType);
        return uploader.take(sysFile.getPath());
    }

    @Override
    public boolean removeFile(Long fileId, String uploaderType) {
        SysFile sysFile = this.getById(fileId);
        if(null == sysFile){
            throw new BusinessException(ReturnCode.NULL_ERROR);
        }
        IUploader uploader = UploaderFactory.getUploader(uploaderType);
        boolean flag = uploader.remove(sysFile.getPath(), sysFile.getHash());
        if (!flag) {
            throw new BusinessException("删除失败，请检查文件系统", ReturnCode.ERROR.getCode());
        }
        return this.removeById(sysFile.getId());
    }
}
