package br.com.bb.service;

import br.com.bb.entity.Product;

public interface IProductService {

    Product getProductById(Long productId);
}
