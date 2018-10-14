package com.zwj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.zwj.mapper"})
@ComponentScan(basePackages = {"com.zwj","org.n3r.idworker"})
public class VideosApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideosApiApplication.class, args);
    }
}
