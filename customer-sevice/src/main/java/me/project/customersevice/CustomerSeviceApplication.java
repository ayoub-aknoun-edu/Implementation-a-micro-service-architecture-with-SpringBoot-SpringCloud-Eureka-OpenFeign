package me.project.customersevice;

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

@SpringBootApplication
public class CustomerSeviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CustomerSeviceApplication.class, args);
	}
	@Bean
	CommandLineRunner run(CustomerRepository customerRepository, RepositoryRestConfiguration restConfiguration){
		restConfiguration.exposeIdsFor(Customer.class);
		return args -> {
			customerRepository.save(new Customer(null,"Hassan","hassan@gmail.com"));
			customerRepository.save(new Customer(null,"Ayoub","ayoub49@gmail.com"));
			customerRepository.save(new Customer(null,"yassine","yasinnexx@gmail.com"));
			customerRepository.save(new Customer(null,"Zineb","zineb.an@gmail.com"));
			customerRepository.findAll().forEach(System.out::println);
		};
	}
}
@Entity @Data @AllArgsConstructor @NoArgsConstructor @ToString
class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
}

@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer,Long> {
}
