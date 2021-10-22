package com.yh.common.file.uploader;

import cn.hutool.core.util.StrUtil;
import com.yh.common.file.config.UploaderProperties;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.Map.Entry;


/**
 * 上传器工厂工具类
 *
 * @author yanghan
 * @date 2021/10/21
 */
public class UploaderFactory implements ApplicationContextAware {

    public static UploaderProperties uploaderProperties;

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        uploaderProperties = context.getBean(UploaderProperties.class);
    }

    @SneakyThrows
    public static IUploader getUploader(String type) {
        type = StrUtil.isBlank(type) ? uploaderProperties.getDefaultType() : type;
        Map<String, IUploader> map = context.getBeansOfType(IUploader.class);
        for (Entry<String, IUploader> entry : map.entrySet()) {
            if (entry.getValue().type().equals(type)) {
                return entry.getValue();
            }
        }
        throw new Exception(String.format("找不到类型为[%s]的上传器实现类", type));
    }

    public static IUploader getDefault() {
        return getUploader(uploaderProperties.getDefaultType());
    }

}
