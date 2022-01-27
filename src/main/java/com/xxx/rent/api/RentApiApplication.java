package com.xxx.rent.api;

import com.xxx.rent.api.model.Customer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yuyao wang
 */

// global configs
@MapperScan({"com.xxx.rent.api.mapper"})
@EnableTransactionManagement
@SpringBootApplication

public class RentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentApiApplication.class, args);
    }

//    @Bean
//    @Scope("prototype")
//    public Customer customer(){
//        return new Customer();
//    }
}
