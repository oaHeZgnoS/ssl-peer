package com.szh.peer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
@Slf4j
public class RestTemplateConfig {

    @Value("${server.ssl.pure-key-store}")
    public String keyStore;
    @Value("${server.ssl.key-store-password}")
    public String keyStorePassword;
    @Value("${server.ssl.pure-trust-store}")
    public String trustStore;
    @Value("${server.ssl.trust-store-password}")
    public String trustStorePassword;

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory httpComponentsClientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        log.info("loading restTemplate");
        return restTemplate;
    }

    @Bean("httpComponentsClientHttpRequestFactory")
    public ClientHttpRequestFactory httpComponentsClientHttpRequestFactory() throws IOException, UnrecoverableKeyException,
            CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        SSLContext sslContext = SSLContextBuilder
                .create()
                // 作为client，load自己密钥库；理论上当server的server.ssl.client-auth=need时，通过它可以获取client的公钥证书传递给server来验证
                .loadKeyMaterial(new ClassPathResource(keyStore).getURL(),
                        keyStorePassword.toCharArray(), keyStorePassword.toCharArray())
                // 作为client，load自己的信任库；在请求server之前，client先通过它判断server是否受信
                .loadTrustMaterial(new ClassPathResource(trustStore).getURL(), trustStorePassword.toCharArray())
                .build();
        HttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                // 不需要主机验证
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(client);
        return requestFactory;
    }
}
