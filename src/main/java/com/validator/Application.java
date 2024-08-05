package com.validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
// 엔티티 객체가 생성되거나 변경 되었 때 자동으로 값을 등록해줌
@SpringBootApplication
// 스프링 부트 애플리케이션을 실행하는데 사용
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
