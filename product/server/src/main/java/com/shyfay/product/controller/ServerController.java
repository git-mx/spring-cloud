package com.shyfay.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mx
 * @since 2019/3/23
 */
@RestController
@RequestMapping("/server")
public class ServerController {

    /**
     *  在application.yml文件里不指定启动端口，复制一个副本来运行，通过envionment里面的VM OPtions来指定运行端口
     *  application1和application2，先将下面的from后面改成8081,启动application1，启动好之后把8081改成8082
     *  再启动application2。然后再去client端调用服务，就会发现输出的内容是 8081和8082交替进行的，这就表示了
     *  负载均衡器Ribbon的默认负载均衡策略是轮询方式。
     *
     */
    @GetMapping("/msg")
    public String msg(){
        return "this is message from 9001";
    }
}
