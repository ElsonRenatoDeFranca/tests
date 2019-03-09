package br.com.bb.repository;

import br.com.bb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByname(String name);

    Category findByid(Long id);
}
