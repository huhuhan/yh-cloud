package com.yh.cloud.auth.util;

import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 本项目临时需要的公共方法工具类
 * 后续接入项目，替换后项目的统一工具类方法
 *
 * @author yanghan
 * @date 2020/6/2
 */
public class TempUtils {


    public static Map<String, Object> tempResult(int httpStatus, String message, String error, Object data) {
        Map<String, Object> result = new HashMap<>(6);
        result.put("info", "error by TempUtils");
        result.put("status", httpStatus);
        result.put("message", message);
        result.put("ok", null == error);
        result.put("timestamp", LocalDateTime.now());
        if (null != data) {
            result.put("data", data);
        }
        if (null != error) {
            result.put("error", error);
        }
        return result;
    }

    public static void logDebug(Logger log, String msg) {
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
    }

    /**
     * servlet
     *
     * @param response
     * @param result
     * @throws IOException
     */
    public static void responseWriter(ServletResponse response, Map<String, Object> result) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        PrintWriter out = response.getWriter();
        out.write(JSONUtil.toJsonStr(result));
        out.flush();
        out.close();
    }

    /**
     * webflux
     *
     * @param response
     * @param result
     * @return
     */
    public static Mono<Void> responseWriter(ServerHttpResponse response, Map<String, Object> result) {
        response.getHeaders().setAccessControlAllowCredentials(true);
        response.getHeaders().setAccessControlAllowOrigin("*");
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(JSONUtil.toJsonStr(result).getBytes(Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
            DataBufferUtils.release(buffer);
        });
    }
}
