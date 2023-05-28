package me.project.billingservice.repositories;

import me.project.billingservice.entities.Bill;
import me.project.billingservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem, Long>{
    public Collection<ProductItem> findByBillId(Long id);
}
