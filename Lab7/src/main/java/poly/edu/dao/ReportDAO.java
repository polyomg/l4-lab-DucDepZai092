package poly.edu.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.edu.entity.Product;
import poly.edu.model.Report;

public interface ReportDAO extends JpaRepository<Product, Integer> {
    @Query("SELECT o.category.name AS group, SUM(o.price) AS sum, COUNT(o) AS count "
         + "FROM Product o "
         + "GROUP BY o.category.name "
         + "ORDER BY SUM(o.price) DESC")
    List<Report> getInventoryByCategory();
}
