package com.yh.cloud.gateway.handler;

import com.yh.common.auth.util.TempUtils;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 统一异常处理，优先级低于 {@link ResponseStatusExceptionHandler} 执行
 * @author yanghan
 * @date 2021/3/24
 */
public class JsonErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {


    public JsonErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                        ResourceProperties resourceProperties,
                                        ApplicationContext applicationContext,
                                        ServerCodecConfigurer serverCodecConfigurer) {
        // 抽象父类要求的必填参数，其他地方已经注入IOC
        super(errorAttributes, resourceProperties, applicationContext);
        // 必须设置消息读写对象
        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    /**
     * 重写抽象方方法
     *
     * @param errorAttributes
     * @return
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        // 参数一：要求所有请求拦截
        // 参数二：HandlerFunction函数接口
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Throwable error = super.getError(request);
        if (error instanceof ResponseStatusException) {
            status = ((ResponseStatusException) error).getStatus();
        }
        Map<String, Object> map = TempUtils.tempResult(status.value(), error.getMessage(), error.getMessage(), null);
        return ServerResponse.status(status.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(map));
    }
}
