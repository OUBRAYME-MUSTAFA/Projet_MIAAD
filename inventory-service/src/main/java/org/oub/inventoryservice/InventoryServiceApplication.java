package org.oub.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.oub.inventoryservice.entities.Product;
import org.oub.inventoryservice.repository.ProductRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration){
		restConfiguration.exposeIdsFor(Product.class);
		return args -> {
			productRepository.save(new Product(null, "product-1", 4000, 400));
			productRepository.save(new Product(null, "product-2", 5555, 100));
			productRepository.save(new Product(null, "product-3", 6666, 200));
			productRepository.findAll().forEach(p->{
				System.out.println(p.getName());
			});
		};
	}

}
