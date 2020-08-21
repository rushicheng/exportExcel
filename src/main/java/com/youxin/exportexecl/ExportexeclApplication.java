package com.youxin.exportexecl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@Slf4j
@SpringBootApplication
public class ExportexeclApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ExportexeclApplication.class, args);

        log.info("------------------------SERVER START--------------------------");
        log.info("------------------------服务已启动--------------------------");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        log.info("------------------------SERVER START！！！--------------------------");
        log.info("------------------------服务已启动！！！--------------------------");
        return application.sources(ExportexeclApplication.class);
    }


}
