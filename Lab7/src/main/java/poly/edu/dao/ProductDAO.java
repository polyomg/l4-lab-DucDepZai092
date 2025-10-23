package poly.edu.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import poly.edu.entity.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
//    @Query("FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
//    List<Product> findByPrice(double minPrice, double maxPrice);
    // thay doan code tren thanh doan nay (Bai4):
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

//    @Query("FROM Product o WHERE o.name LIKE ?1")
//    Page<Product> findByKeywords(String keywords, Pageable pageable);
    // thay doan code tren thanh doan code nay (Bai5);
    Page<Product> findAllByNameLike(String keywords, Pageable pageable);

}
