package com.szh.peer.ctrl;

import com.alibaba.fastjson.JSON;
import com.szh.peer.util.HttpRequestTools;
import com.szh.peer.util.InteractRestTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("api")
@RestController
public class SystemCtrl {

    @Autowired
    private InteractRestTools interactRestTools;
    @Value("${spring.application.name}")
    public String peerName;

    @RequestMapping("/doSomething")
    public String doSomething() {
        Map<String, String> map = new HashMap<>();
        map.put("name", peerName);
        String uri = HttpRequestTools.getQueryUri(InteractRestTools.INTERACT_REPLY_HELLO_URI, map);
        // sslContext配置忽略了主机名称校验，这里也可以127.0.0.1
        String serviceUrl = "https://localhost:28688";
        if ("peer2".equals(peerName)) {
            serviceUrl = "https://127.0.0.1:28288";
        }
        String response = interactRestTools.getForEntity(serviceUrl, uri, String.class);
        log.info("== doSomething: interact result: {}", JSON.toJSONString(response));
        return response;
    }

    @PostMapping("/echo")
    public ResponseEntity<String> echo(@RequestBody(required = false) String reqInfo) {
        return ResponseEntity.ok(reqInfo);
    }

}
