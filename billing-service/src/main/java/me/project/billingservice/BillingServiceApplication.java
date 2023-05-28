package me.project.billingservice;

import me.project.billingservice.entities.Bill;
import me.project.billingservice.entities.ProductItem;
import me.project.billingservice.feign.CustomerRestClient;
import me.project.billingservice.feign.ProductItemRestClient;
import me.project.billingservice.model.Customer;
import me.project.billingservice.model.Product;
import me.project.billingservice.repositories.BillRepository;
import me.project.billingservice.repositories.ProductItemRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            BillRepository billRepository,
            ProductItemRepository productItemRepository,
            CustomerRestClient customerRestClient,
            ProductItemRestClient productItemRestClient){

        return args -> {
            Customer customer1 = customerRestClient.getCustomerById(1L);
            Customer customer2 = customerRestClient.getCustomerById(2L);
            Customer customer3 = customerRestClient.getCustomerById(3L);
            List<Customer> customers = List.of(customer1, customer2, customer3);

            customers.forEach(customer -> {
                Bill bill = billRepository.save(new Bill(null, new Date(), null, customer.getId(), null));

                PagedModel<Product> productPagedModel = productItemRestClient.pageProducts(0,new Random().nextInt(10));
                productPagedModel.forEach(p->{
                    System.out.println(p.toString());
                    ProductItem productItem = new ProductItem();
                    productItem.setProductID(p.getId());
                    productItem.setPrice(p.getPrice());
                    productItem.setQuantity(1+ (int)(Math.random()*100));
                    productItem.setBill(bill);

                    productItemRepository.save(productItem);
                });
            });








        };
    }

}
