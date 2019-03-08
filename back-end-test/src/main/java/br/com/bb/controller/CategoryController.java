package br.com.bb.controller;


import br.com.bb.entity.Category;
import br.com.bb.entity.Product;
import br.com.bb.exception.CategoryNotFoundException;
import br.com.bb.exception.ProductNotFoundException;
import br.com.bb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(method=RequestMethod.POST, value="/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category persistedCategory = categoryService.createCategory(category);

        if(null != persistedCategory.getCategoryId()){
            return new ResponseEntity<> (persistedCategory,HttpStatus.OK);
        }else{
            return new ResponseEntity<> (persistedCategory,HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method=RequestMethod.POST, value="/category/{categoryId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> addProductsToCategory(@PathVariable(name="categoryId") Long categoryId, @RequestBody Product product) {
        try {
            Category persistedCategory = categoryService.addProductToCategory(categoryId, product);
            return new ResponseEntity<> (persistedCategory,HttpStatus.OK);
        } catch (ProductNotFoundException | CategoryNotFoundException ex) {
            return new ResponseEntity<>(getDummyCategory(categoryId), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/category/{categoryId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> removeProductsFromCategory(@PathVariable(name="categoryId") Long categoryId, @RequestBody Product product) {

        try {
            Category persistedCategory = categoryService.removeProductFromCategory(categoryId, product);
            return new ResponseEntity<> (persistedCategory,HttpStatus.OK);
        } catch (ProductNotFoundException | CategoryNotFoundException exception) {
            return new ResponseEntity<>(getDummyCategory(categoryId), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/category/listAll")
    public List<Category> findAll() throws CategoryNotFoundException, ProductNotFoundException {
        return categoryService.findAll();
    }

    @RequestMapping(value ="/category/{letter}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> findCategoryByLetterOccurrence(@PathVariable(name="letter") char letter) throws CategoryNotFoundException, ProductNotFoundException {
        return categoryService.findCategoryByLetterOccurrence(letter);
    }


    @RequestMapping(method=RequestMethod.GET,value="/category/categoryId}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long categoryId){

        try {
            Category category = categoryService.findCategoryById(categoryId);
            return new ResponseEntity<>(category, HttpStatus.OK);

        }catch(CategoryNotFoundException catEx){
            return new ResponseEntity<> (getDummyCategory(categoryId),HttpStatus.BAD_REQUEST);
        }


    }


    private Category getDummyCategory(Long categoryId){
        Category category = new Category();
        category.setId(categoryId);
        category.setProducts(new ArrayList<>());
        return category;
    }


}
