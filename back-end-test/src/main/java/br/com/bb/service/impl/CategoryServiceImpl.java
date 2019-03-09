package br.com.bb.service.impl;

import br.com.bb.constants.DemoAppConstants;
import br.com.bb.entity.Category;
import br.com.bb.exception.CategoryNotFoundException;
import br.com.bb.repository.CategoryRepository;
import br.com.bb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public Category findCategoryById(Long categoryId) throws CategoryNotFoundException {
        return Optional.of(categoryRepository.findBycategoryId(categoryId)).orElseThrow(() -> new CategoryNotFoundException(DemoAppConstants.CATEGORY_NOT_FOUND_ERROR_MESSAGE));
    }

    @Override
    public Category findCategoryByName(String name) throws CategoryNotFoundException {
        return Optional.of(categoryRepository.findByname(name)).orElseThrow(() -> new CategoryNotFoundException(DemoAppConstants.CATEGORY_NOT_FOUND_ERROR_MESSAGE));
    }

    private Long getMaxNumberOfLetters(TreeMap<String, Long> map){
        return map.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getValue();
    }

    private List<String> getListOfMaxOccurrencesOfOneLetter(TreeMap<String, Long> map){

        Long maxNumberOfOccurrence = getMaxNumberOfLetters(map);

       return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maxNumberOfOccurrence && maxNumberOfOccurrence > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


    }

    @Override
    public List<Category> findCategoryByLetterOccurrence(char letter) throws CategoryNotFoundException {

        List<Category> categories = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();

        categoryRepository.findAll().forEach(categories::add);
        TreeMap<String, Long> map = new TreeMap<>();

        categories.forEach(category ->
                map.put(category.getName(), category.getName().chars().
                        filter(ch -> ch == letter).
                        count()));

        List<String> maxOccurrencesOfLetter = getListOfMaxOccurrencesOfOneLetter(map);

        if(maxOccurrencesOfLetter != null){
            for(String categoryName : maxOccurrencesOfLetter){
                Category cat = this.findCategoryByName(categoryName);
                categoryList.add(cat);
            }

        }

        return categoryList;
    }

}
