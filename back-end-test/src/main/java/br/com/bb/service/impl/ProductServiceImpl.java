package br.com.bb.service.impl;

import br.com.bb.entity.Product;
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
}
