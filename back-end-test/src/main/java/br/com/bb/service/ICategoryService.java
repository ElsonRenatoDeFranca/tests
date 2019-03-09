package br.com.bb.service;

import br.com.bb.entity.Category;
import br.com.bb.exception.CategoryNotFoundException;

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
     * @param categoryId
     * @return
     * @throws CategoryNotFoundException
     */
    Category findCategoryById(Long categoryId)  throws CategoryNotFoundException;

    /**
     *
     * @param name
     * @return
     * @throws CategoryNotFoundException
     */
    Category findCategoryByName(String name)  throws CategoryNotFoundException;


    /**
     *
     * @param letter
     * @return
     * @throws CategoryNotFoundException
     */
    List<Category> findCategoryByLetterOccurrence(char letter) throws CategoryNotFoundException;

}
