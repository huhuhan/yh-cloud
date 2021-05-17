package com.yh.cloud.base.util;

import cn.hutool.core.net.NetUtil;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author yanghan
 * @date 2021/3/18
 */
public class WebUtil {

    /**
     * 初始化服务端口，在启动main方法中调用
     * <br>优先级高于application的配置
     *
     * @param args
     * @param minPort
     * @param maxPort
     * @return
     */
    public static void initServerPort(String[] args, int minPort, int maxPort) {
        boolean isServerPort = false;
        String serverPort = "";
        if (args != null) {
            for (String arg : args) {
                // 取服务启动外置参数，如java -jar xx.jar --server.port=8080
                if (StringUtils.hasText(arg) && arg.startsWith("--server.port")) {
                    isServerPort = true;
                    serverPort = arg.split("=")[1];
                    break;
                }
            }
        }
        // 没有指定端口, 则随机生成一个可用的端口
        if (!isServerPort) {
            int port = NetUtil.getUsableLocalPort(minPort, maxPort);
            serverPort = String.valueOf(port);
        }
        System.setProperty("server.port", serverPort);
        System.out.println(String.format("%s [%s] WebUtil.initServerPort - current server.port=%s", new Date(), Thread.currentThread().getName(), serverPort));
    }
}