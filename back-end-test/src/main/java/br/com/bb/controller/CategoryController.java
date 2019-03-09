package br.com.bb.controller;


import br.com.bb.entity.Category;
import br.com.bb.exception.CategoryNotFoundException;
import br.com.bb.exception.ProductNotFoundException;
import br.com.bb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;


    @RequestMapping(value = "/category/listAll")
    public List<Category> findAll() throws CategoryNotFoundException, ProductNotFoundException {
        return categoryService.findAll();
    }

    @RequestMapping(value ="/category/{letter}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> findCategoryByLetterOccurrence(@PathVariable(name="letter") char letter) throws CategoryNotFoundException, ProductNotFoundException {
        return categoryService.findCategoryByLetterOccurrence(Character.toLowerCase(letter));
    }


    @RequestMapping(method=RequestMethod.GET,value="/category/{categoryId}")
    public ResponseEntity<Category> findCategoryByCategoryId(@PathVariable Long categoryId){

        try {
            Category category = categoryService.findCategoryById(categoryId);
            return new ResponseEntity<>(category, HttpStatus.OK);

        }catch(CategoryNotFoundException catEx){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }

    }


}
