package me.project.inventoryservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Random;

@SpringBootApplication
public class InventoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration){
		restConfiguration.exposeIdsFor(Product.class);
		return args -> {
			productRepository.save(new Product(null,"Computer",1+new Random().nextDouble()*10000,12));
			productRepository.save(new Product(null,"Printer",1+new Random().nextDouble()*10000,10));
			productRepository.save(new Product(null,"Smartphone",1+new Random().nextDouble()*10000,5));
			productRepository.save(new Product(null,"HeadPhones",1+new Random().nextDouble()*1000,12));
			productRepository.save(new Product(null,"Bike",1+new Random().nextDouble()*10000,10));
			productRepository.save(new Product(null,"mac",1+new Random().nextDouble()*100000,5));
			productRepository.save(new Product(null,"32gb ddr5",1+new Random().nextDouble()*10000,12));
			productRepository.save(new Product(null,"USB",1+new Random().nextDouble()*1000,10));
			productRepository.save(new Product(null,"SSD 1to",1+new Random().nextDouble()*10000,5));
			productRepository.findAll().forEach(System.out::println);
		};

	}
}

@Entity @Data @AllArgsConstructor @NoArgsConstructor @ToString
class Product{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;
	private double quantity;
}
@RepositoryRestResource
interface ProductRepository extends JpaRepository<Product,Long>{}


