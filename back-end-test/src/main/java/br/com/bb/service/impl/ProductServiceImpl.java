package br.com.bb.service.impl;

import br.com.bb.constants.DemoAppConstants;
import br.com.bb.entity.Product;
import br.com.bb.exception.ProductAlreadyExistsException;
import br.com.bb.exception.ProductNotFoundException;
import br.com.bb.repository.ProductRepository;
import br.com.bb.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    public RestTemplate restTemplate;

    @Value("${product.url}")
    private String remoteProductUrl;

    @Override
    @Cacheable("products")
    public Product getProductById(Long productId) {
        ResponseEntity<Product> responseEntity = this.restTemplate.exchange(remoteProductUrl + productId, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Product.class);
        Product productResult = responseEntity.getBody();
        return productResult;
    }

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product findProductById(Long id) throws ProductNotFoundException {
        return productRepository.findByid(id);
    }

    @Override
    public List<Product> findAll() throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public Product createProduct(Product product) throws ProductAlreadyExistsException, ProductNotFoundException {
        Product searchedProduct = this.findProductById(product.getId());

        if(searchedProduct != null){
            throw new ProductNotFoundException(DemoAppConstants.PRODUCT_ALREADY_EXISTS_ERROR_MESSAGE);
        }
        return productRepository.save(product);
    }

    @Override
    public void removeProduct(Long id) throws ProductNotFoundException {
        Product searchedProduct = this.findProductById(id);

        if(searchedProduct == null){
            throw new ProductNotFoundException(DemoAppConstants.PRODUCT_NOT_FOUND_ERROR_MESSAGE);
        }
        productRepository.delete(searchedProduct);
    }

}
