package com.charlyCorporation.carrito_compras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.charlyCorporation.carrito_compras.client")
public class CarritoComprasApplication {

	public static void main(String[] args) {

		SpringApplication.run(CarritoComprasApplication.class, args);
	}

}
