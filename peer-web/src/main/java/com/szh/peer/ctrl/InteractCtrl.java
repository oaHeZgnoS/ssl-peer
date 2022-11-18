package com.szh.peer.ctrl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 专门用来peer内部之间交互的接口
 */
@Slf4j
@RequestMapping("interact")
@RestController
public class InteractCtrl {

    @RequestMapping("/reply/hello")
    public String replyHello(@RequestParam(required = false) String name) {
        log.info("== replyHello: received from {}", name);
        return "hello, " + name;
    }

}
