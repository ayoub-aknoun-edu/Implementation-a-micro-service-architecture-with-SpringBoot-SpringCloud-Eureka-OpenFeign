package me.project.billingservice.wab;

import me.project.billingservice.entities.Bill;
import me.project.billingservice.feign.CustomerRestClient;
import me.project.billingservice.feign.ProductItemRestClient;
import me.project.billingservice.model.Customer;
import me.project.billingservice.repositories.BillRepository;
import me.project.billingservice.repositories.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class BillingRestController {

    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi->{
            pi.setProductName(productItemRestClient.getProductById(pi.getProductID()).getName());
        });
        return bill;
}

    @GetMapping(path = "/bills/{id}")
    public List<Bill> getBillByCustomer(@PathVariable(name = "id") Long id){
        List<Bill> bills= billRepository.findBillsByCustomerID(id);
        bills.forEach(bill -> {
            Customer customer = customerRestClient.getCustomerById(bill.getCustomerID());
            bill.setCustomer(customer);
            bill.getProductItems().forEach(pi->{
                pi.setProductName(productItemRestClient.getProductById(pi.getProductID()).getName());
            });
        });
        return bills;
    }

    @GetMapping(path = "/bills")
    public List<Bill> getALlBills(){
        List<Bill> bills= billRepository.findAll();
        bills.forEach(bill -> {
            Customer customer = customerRestClient.getCustomerById(bill.getCustomerID());
            bill.setCustomer(customer);
            bill.getProductItems().forEach(pi->{
                pi.setProductName(productItemRestClient.getProductById(pi.getProductID()).getName());
            });
        });
        return bills;
    }
}
