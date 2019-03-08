package br.com.bb.service;

import br.com.bb.entity.Product;
import br.com.bb.exception.CategoryNotFoundException;
import br.com.bb.exception.ProductAlreadyExistsException;
import br.com.bb.exception.ProductNotFoundException;

import java.util.List;

public interface IProductService {

    /**
     *
     * @param id
     * @return
     * @throws ProductNotFoundException
     */
    Product findProductById(Long id) throws ProductNotFoundException;


    /**
     *
     * @param id
     * @return
     * @throws ProductNotFoundException
     */
    List<Product> findProductByCategoryId(Long id) throws ProductNotFoundException, CategoryNotFoundException;

    /**
     *
     * @return
     * @throws ProductNotFoundException
     */
    List<Product> findAll() throws ProductNotFoundException;


    /**
     *
     * @param product
     * @return
     */
    Product createProduct(Product product)  throws ProductAlreadyExistsException, ProductNotFoundException;

    /**
     *
     * @param id
     * @throws ProductNotFoundException
     */
    void removeProduct(Long id) throws ProductNotFoundException;

}
