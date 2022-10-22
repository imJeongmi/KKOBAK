package com.a104.freeproject.Category.service;

import com.a104.freeproject.Category.entity.Category;
import com.a104.freeproject.Category.repository.CategoryRepository;
import com.a104.freeproject.Category.response.CategoryResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-secret.properties")
})
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public boolean regCategory(String name) throws NotFoundException {

        if(categoryRepository.existsByName(name)) throw new NotFoundException("이미 존재하는 카테고리명입니다.");

        try{
            Category c = Category.builder().name(name).build();
            categoryRepository.save(c);
            return true;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<CategoryResponse> getList() {
        List<Category> list = categoryRepository.findAll();
        List<CategoryResponse> output = new LinkedList<>();

        for(Category c : list){
            output.add(CategoryResponse.builder().id(c.getId()).name(c.getName()).build());
        }
        return output;
    }


}
