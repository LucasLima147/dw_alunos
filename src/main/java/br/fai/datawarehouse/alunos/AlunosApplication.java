package br.fai.datawarehouse.alunos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AlunosApplication implements WebMvcConfigurer {

	public void addViewController(ViewControllerRegistry registration){
		registration.addViewController("/index").setViewName("index");
	}

	public static void main(String[] args) {
		SpringApplication.run(AlunosApplication.class, args);
	}

}