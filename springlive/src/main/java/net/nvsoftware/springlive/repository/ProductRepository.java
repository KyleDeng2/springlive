package net.nvsoftware.springlive.repository;


import net.nvsoftware.springlive.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,String> {

}
