package br.com.bb.service;

import br.com.bb.entity.Category;
import br.com.bb.entity.Product;
import br.com.bb.exception.CategoryNotFoundException;
import br.com.bb.exception.ProductNotFoundException;

import java.util.List;

public interface ICategoryService {


    /**
     *
     * @return
     * @throws CategoryNotFoundException
     */
    List<Category> findAll()  throws CategoryNotFoundException;

    /**
     *
     * @param id
     * @return
     * @throws CategoryNotFoundException
     */
    Category findCategoryById(Long id)  throws CategoryNotFoundException;


    /**
     *
     * @param letter
     * @return
     * @throws CategoryNotFoundException
     */
    List<Category> findCategoryByLetterOccurrence(char letter) throws CategoryNotFoundException;


    /**
     *
     *
     */
    Category createCategory(Category category);


    /**
     *
     * @param categoryId
     * @param product
     * @return
     * @throws ProductNotFoundException
     * @throws CategoryNotFoundException
     */
    Category addProductToCategory(Long categoryId, Product product) throws ProductNotFoundException, CategoryNotFoundException;

    /**
     *
     * @param categoryId
     * @param product
     * @return
     * @throws ProductNotFoundException
     * @throws CategoryNotFoundException
     */
    Category removeProductFromCategory(Long categoryId, Product product) throws ProductNotFoundException, CategoryNotFoundException;

}
