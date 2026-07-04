package com.shipracoding.introduction;

import com.shipracoding.introduction.entities.UserEntity;
import com.shipracoding.introduction.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class IntroductionApplicationTests {

    @Autowired
    private JwtService jwtService;

	@Test
	void contextLoads() {
        UserEntity user = new UserEntity(4L,"shiprajha471@gmail.com","1234","ravi");

        String token = jwtService.generateToken(user);

        System.out.println(token);

        Long id = jwtService.getUserIdFromToken(token);

        System.out.println(id);
	}
}
