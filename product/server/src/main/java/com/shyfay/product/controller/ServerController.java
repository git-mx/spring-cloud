package com.shyfay.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor;

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
    @RequestMapping(value = "/msg", method = RequestMethod.GET)
    public String msg() throws InterruptedException {
        //设置超时
        Thread.sleep(2000);
        return "this is message from 9001";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() throws InterruptedException {
        return "this is message from 9001";
    }

}
