package com.shipracoding.introduction;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;

@SpringBootApplication
public class IntroductionApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(IntroductionApplication.class, args);

	}

    @Override
    public void run(String... args) throws Exception {

    }

}

