package br.com.bb.service.impl;

import br.com.bb.constants.DemoAppConstants;
import br.com.bb.entity.Category;
import br.com.bb.entity.Product;
import br.com.bb.exception.CategoryNotFoundException;
import br.com.bb.exception.ProductNotFoundException;
import br.com.bb.repository.CategoryRepository;
import br.com.bb.repository.ProductRepository;
import br.com.bb.service.ICategoryService;
import br.com.bb.service.IProductService;
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

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private IProductService productService;



    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public Category findCategoryById(Long id) throws CategoryNotFoundException {
        return Optional.of(categoryRepository.findOne(id)).orElseThrow(() -> new CategoryNotFoundException(DemoAppConstants.CATEGORY_NOT_FOUND_ERROR_MESSAGE));
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

        categoryRepository.findAll().forEach(categories::add);
        TreeMap<String, Long> map = new TreeMap<>();
        categories.forEach(category -> map.put(category.getName(), category.getName().chars().filter(character -> character == letter).count()));
        List<String> maxOccurrencesOfLetter = getListOfMaxOccurrencesOfOneLetter(map);

        return maxOccurrencesOfLetter.stream().
                map((element -> categoryRepository.findByname(element))).
                findAny().orElse(new ArrayList<>());
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category addProductToCategory(Long categoryId, Product product) throws ProductNotFoundException, CategoryNotFoundException {
        Product searchedProduct = productService.findProductById(product.getId());

        Category category = findCategoryById(categoryId);

        if (searchedProduct != null) {
            category.getProducts().add(searchedProduct);
            categoryRepository.save(category);
        } else {
            throw new ProductNotFoundException(DemoAppConstants.PRODUCT_NOT_FOUND_ERROR_MESSAGE);
        }

        return category;

    }

    @Override
    public Category removeProductFromCategory(Long categoryId, Product product) throws ProductNotFoundException, CategoryNotFoundException {

        Category category = findCategoryById(categoryId);
        productRepository.delete(product);
        category.getProducts().removeIf(product1 -> product.equals(product));

        return category;
    }

}
