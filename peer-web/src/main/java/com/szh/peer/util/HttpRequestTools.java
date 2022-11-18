package com.szh.peer.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * request param/body formater
 */
public class HttpRequestTools {

    /**
     * get uri.
     */
    public static String getUri(HttpServletRequest httpRequest) {
        String uri = httpRequest.getRequestURI().replace("//", "/");
        String contextPath = httpRequest.getContextPath();
        return StringUtils.removeStart(uri, contextPath);
    }


    /**
     * convert map to query params
     * ex: uri:permission,
     *     params: (groupId, 1) (address, 0x01)
     *
     * result: permission?groupId=1&address=0x01
     */
    public static String getQueryUri(String uriHead, Map<String, String> map) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.add(entry.getKey(), entry.getValue());
        }

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParams(params).build();

        return uriHead + uriComponents.toString();
    }

    public static HttpHeaders headers(String fileName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename*=UTF-8''" + encode(fileName));
        return httpHeaders;
    }

    public static String encode(String name) {
        try {
            return URLEncoder.encode(name, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
