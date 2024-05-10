package com.travel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel
 * @CreateTime: 2021-05-17 14:46
 * @Description: 启动类
 */
@SpringBootApplication
@MapperScan("com.travel.mapper")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
