package br.com.bb.service;

import br.com.bb.entity.Product;
import br.com.bb.exception.ProductAlreadyExistsException;
import br.com.bb.exception.ProductNotFoundException;

import java.util.List;

public interface IProductService {

    Product getProductById(Long productId);

    /**
     *
     * @param id
     * @return
     * @throws ProductNotFoundException
     */
    Product findProductById(Long id) throws ProductNotFoundException;

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
