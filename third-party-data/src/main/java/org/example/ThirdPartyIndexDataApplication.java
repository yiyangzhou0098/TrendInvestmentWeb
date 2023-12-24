package org.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import sun.nio.ch.Net;

@SpringBootApplication
@EnableEurekaClient
public class ThirdPartyIndexDataApplication{
    public static void main(String[] args) {
        int port = 8090;
        int eurekaServerPort = 8761;

        if (NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("Detecting server port %d not started, you need to enable server to access this service", eurekaServerPort);
            System.exit(1);
        }

        if (null!=args && 0!=args.length) {
            for (String arg : args) {
                if (arg.startsWith("port=")) {
                    String strPort = StrUtil.subAfter(arg, "port=", true);
                    if (NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("Port %d is occupied %n", port);
            System.exit(1);
        }

        new SpringApplicationBuilder(ThirdPartyIndexDataApplication.class).properties("server.port=" + port).run(args);
    }
}

