package com.yh.cloud.file.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.yh.cloud.file.model.entity.SysFile;
import com.yh.cloud.file.model.vo.SysFileQuery;
import com.yh.cloud.file.service.ISysFileService;
import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 系统附件表
 *
 * @author yanghan
 * @date 2021-10-20 11:15:16
 */
@RestController
@RequestMapping("/sysfile")
@Api(tags = "系统附件表")
public class SysFileController {
    @Autowired
    private ISysFileService sysFileService;

    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public ReturnWrapper<IPage<SysFile>> findList(SysFileQuery<SysFile> sysFileQuery) {
        return ReturnWrapMapper.ok(sysFileService.findList(sysFileQuery));
    }

    @ApiOperation(value = "查询对象")
    @GetMapping("/{id}")
    public ReturnWrapper<SysFile> findById(@PathVariable Long id) {
        return ReturnWrapMapper.ok(sysFileService.getById(id));
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public ReturnWrapper delete(@PathVariable Long id,
                                @RequestParam(required = false) String uploaderType) {
        return ReturnWrapMapper.ok(sysFileService.removeFile(id, uploaderType));
    }

    @ApiOperation(value = "上传文件")
    @PostMapping("/upload")
    public ReturnWrapper upload(@RequestParam("file") MultipartFile file,
                                @RequestParam(required = false) String uploaderType) {
        return ReturnWrapMapper.ok(sysFileService.uploadFile(file, uploaderType));
    }

    @ApiOperation(value = "下载文件")
    @GetMapping("/download/{fileId}")
    public ResponseEntity uploadFile(@PathVariable String fileId,
                                     @RequestParam(required = false) String uploaderType) throws Exception {
        SysFile sysFile = sysFileService.getById(fileId);

        HttpHeaders headers = new HttpHeaders();
        //浏览器下载文件名乱码处理
        String downloadFileName = URLEncoder.encode(sysFile.getName(), StandardCharsets.UTF_8.toString());
        headers.setContentDispositionFormData("attachment", downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(sysFileService.download(fileId, uploaderType), headers, HttpStatus.OK);
    }
}
