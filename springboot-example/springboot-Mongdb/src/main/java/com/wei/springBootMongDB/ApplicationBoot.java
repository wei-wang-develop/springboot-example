package com.wei.springBootMongDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用启动类
 * @author Wei WANG
 *
 */
@SpringBootApplication
public class ApplicationBoot {
	public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(ApplicationBoot.class,args);
    }
}
