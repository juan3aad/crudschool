package com.formacionbdi.microservicios.app.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.formacionbdi.microservicios")
@EntityScan({"com.formacionbdi.microservicios.app.cursos.models.entity",
	"com.formacionbdi.microservicios.commons.examenes.models.entity"
	})
	
@EnableFeignClients
//(basePackages = "com.formacionbdi.microservicios.app.cursos.clients")
//@ComponentScan(basePackages = "com.formacionbdi.microservicios.app.cursos.services")



public class MicroserviciosCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosCursosApplication.class, args);
	}

}
