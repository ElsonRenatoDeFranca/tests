package br.com.bb.repository;

import br.com.bb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by e068635 on 2/21/2019.
 */
public interface ProductRepository  extends JpaRepository<Product, Long> {
}
