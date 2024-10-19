//-- Active: 1729365158064@@127.0.0.1@3306@spring
package com.example2.demo; //현재 폴더 위치

import org.springframework.boot.SpringApplication; //스프링 핵심 클래스 
import org.springframework.boot.autoconfigure.SpringBootApplication; //자동설정기능 활성화

@SpringBootApplication //애노태이션 (스프링부트 APP 명시 , 하위 다양한 설정을 자동 등록)
public class DemoApplication { //클래스 이름

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
