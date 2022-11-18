package com.szh.peer.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * about http request for peers each other.
 */
@Slf4j
@Service
public class InteractRestTools {

    //@Qualifier(value = "interactRestTemplate")
    @Autowired
    private RestTemplate interactRestTemplate;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public static final String INTERACT_PREFIX_URI = "";
    // peer内部之间接收对方的请求
    public static final String INTERACT_REPLY_HELLO_URI = "interact/reply/hello";

    public <T> T getForEntity(String serviceUrl, String uri, Class<T> clazz) {
        T response = restTemplateExchange(serviceUrl, uri, HttpMethod.GET, null, clazz);
        if (response == null) {
            log.error("getForEntity response is null!");
            throw new RuntimeException("getForEntity response is null");
        }
        return response;
    }

    public <T> T postForEntity(String serviceUrl, String uri, Object params, Class<T> clazz) {
        T response = restTemplateExchange(serviceUrl, uri, HttpMethod.POST, params, clazz);
        if (response == null) {
            log.error("postForEntity response is null!");
            throw new RuntimeException("postForEntity response is null");
        }
        return response;
    }

    public <T> T deleteForEntity(String serviceUrl, String uri, Object params, Class<T> clazz) {
        T response = restTemplateExchange(serviceUrl, uri, HttpMethod.DELETE, params, clazz);
        if (response == null) {
            log.error("deleteForEntity response is null!");
            throw new RuntimeException("deleteForEntity response is null");
        }
        return response;
    }

    public static HttpEntity buildHttpEntity(Object param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String paramStr = null;
        if (Objects.nonNull(param)) {
            paramStr = JSON.toJSONString(param);
        }
        HttpEntity requestEntity = new HttpEntity(paramStr, headers);
        return requestEntity;
    }

    /**
     * restTemplate exchange.
     */
    private <T> T restTemplateExchange(String serviceUrl, String uri, HttpMethod method, Object param, Class<T> clazz) {
        HttpEntity entity = buildHttpEntity(param);// build entity
        // 处理掉serviceUrl最后的结尾/
        while (serviceUrl.endsWith("/")) {
            serviceUrl = serviceUrl.substring(0, serviceUrl.lastIndexOf("/"));
        }
        String url = String.format("%s%s%s%s", serviceUrl, contextPath, INTERACT_PREFIX_URI, uri);
        log.info("== Interact request: {}, {}, {}", method, url, entity);
        ResponseEntity<T> response = interactRestTemplate.exchange(url, method, entity, clazz);
        log.info("== Interact response: {}", response);
        return response.getBody();
    }
}