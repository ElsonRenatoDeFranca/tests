package br.com.bb.controller;

import br.com.bb.entity.Category;
import br.com.bb.entity.Product;
import br.com.bb.exception.CategoryNotFoundException;
import br.com.bb.repository.CategoryRepository;
import br.com.bb.service.ICategoryService;
import br.com.bb.service.IProductService;
import br.com.bb.service.impl.CategoryServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by e068635 on 2/27/2019.
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {


    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private IProductService productService;

    @InjectMocks
    private ICategoryService categoryService = new CategoryServiceImpl();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private  List<Category> categories;


    @Before
    public void setup(){
        categories = new ArrayList<>();
        Category mockCategory1 = createCategory(1L, "Alimentos", false);
        Category mockCategory2 = createCategory(2L, "Eletrodomésticos", false);
        Category mockCategory3 = createCategory(3L, "Móveis", false);

        categories.add(mockCategory1);
        categories.add(mockCategory2);
        categories.add(mockCategory3);
    }


    @Test
    public void createCategory_shouldReturnEmptyCategory_whenCreateCategoryServiceIsInvoked() {

        //When
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        Category category = new Category();

        Category newCategory = categoryService.createCategory(category);

        //Then
        assertThat(newCategory, is(notNullValue()));
    }

    @Test
    public void createCategory_shouldReturnCategory_whenCreateCategoryServiceIsInvoked() throws CategoryNotFoundException {


        Category mockCategory = createCategory(1000L, "categoryName02", false);
        Long expectedId = 1000L;

        //When
        when(categoryRepository.findByid(eq(expectedId))).thenReturn(Optional.of(mockCategory).get());
        Category newCategory = categoryService.findCategoryById(expectedId);

        verify(categoryRepository, times(1)).findByid(eq(expectedId));

        //Then
        assertThat(newCategory, is(notNullValue()));
        assertThat(newCategory, hasProperty("products", is(notNullValue())));

    }

    @Test
    public void createCategory_shouldReturnCategory_whenCategoryNameHasAnyOccurrenceOfLetter() throws CategoryNotFoundException {

        //Given
        char letter = 'e';
        Category alimentos = createCategory(1L, "Alimentos", false);

        //When
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRepository.findByname(anyString())).thenReturn(alimentos);


        List<Category> categoryList = categoryService.findCategoryByLetterOccurrence(letter);

        //Then
        assertThat(categoryList, is(notNullValue()));
        assertThat(categoryList, hasSize(greaterThan(0)));
    }

    private Category createCategory(Long categoryId, String categoryName, boolean emptyCategory){

        Category newCategory = new Category();

        if(!emptyCategory){
            Product arroz = createProduct(1L,"Arroz", "Arroz Parboilizado", 3.50);
            Product feijao = createProduct(2L,"Feijão", "Feijao Sitio Cercado", 4.10);

            newCategory.setCategoryId(categoryId);
            newCategory.setName(categoryName);
            newCategory.setProducts( Arrays.asList(arroz, feijao));
        }
        return newCategory;
    }

    private Product createProduct (Long id, String name, String description, Double cost){
        Product product = new Product();
        product.setCost(cost);
        product.setDescription(description);
        product.setId(id);
        product.setProductId(id);
        product.setName(name);

        return product;

    }



}
