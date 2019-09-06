package com.example.txinventoryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import tk.mybatis.spring.annotation.MapperScan;


@MapperScan("com.example.txinventoryserver.mapper")
@ImportResource({"classpath:applicationContext.xml"})
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TxInventoryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxInventoryServerApplication.class, args);
    }

}
