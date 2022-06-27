package org.oub.billingservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.oub.billingservice.entities.Bill;
import org.oub.billingservice.entities.ProductItem;
import org.oub.billingservice.feign.CustomerRestClient;
import org.oub.billingservice.feign.ProductItemRestClient;
import org.oub.billingservice.model.Customer;
import org.oub.billingservice.model.Product;
import org.oub.billingservice.repository.BillRepository;
import org.oub.billingservice.repository.ProductItemRepository;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository productItemRepository,
							CustomerRestClient customerRestClient,
							ProductItemRestClient productItemRestClient){
		return args -> {
			Customer customer = customerRestClient.getCustomerById(1L);
			Customer customer2 = customerRestClient.getCustomerById(2L);
			Bill bill1 = billRepository.save(new Bill(null, new Date(), null, customer.getId(), null));
			Bill bill2 = billRepository.save(new Bill(null, new Date(), null, customer2.getId(), null));


			Product product1 = productItemRestClient.getProductById(1L);
			Product product2 = productItemRestClient.getProductById(2L);
			Product product3 = productItemRestClient.getProductById(3L);

			ProductItem productItem = new ProductItem();
			productItem.setPrice(product1.getPrice());
			productItem.setQuantity(1+new Random().nextInt(100));
			productItem.setBill(bill1);
			productItem.setProductID(product1.getId());
			productItemRepository.save(productItem);

			ProductItem productItem2 = new ProductItem();
			productItem2.setPrice(product2.getPrice());
			productItem2.setQuantity(1+new Random().nextInt(100));
			productItem2.setBill(bill1);
			productItem2.setProductID(product2.getId());
			productItemRepository.save(productItem2);

			ProductItem productItem3 = new ProductItem();
			productItem3.setPrice(product3.getPrice());
			productItem3.setQuantity(1+new Random().nextInt(100));
			productItem3.setBill(bill2);
			productItem3.setProductID(product3.getId());
			productItemRepository.save(productItem3);
		};
	}

}
