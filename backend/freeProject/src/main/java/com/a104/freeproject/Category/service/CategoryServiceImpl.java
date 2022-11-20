package com.a104.freeproject.Category.service;

import com.a104.freeproject.Category.entity.Category;
import com.a104.freeproject.Category.repository.CategoryRepository;
import com.a104.freeproject.Category.request.CategoryRequest;
import com.a104.freeproject.Category.request.CategoryUpdateRequest;
import com.a104.freeproject.Category.response.CategoryResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    public boolean regCategory(CategoryRequest input) throws NotFoundException {

        if(categoryRepository.existsByName(input.getName())) throw new NotFoundException("이미 존재하는 카테고리명입니다.");

        try{
            Category c = Category.builder().
                    name(input.getName()).
                    imgurl(input.getImgurl()).
                    build();
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
            output.add(CategoryResponse.builder().
                    id(c.getId()).
                    name(c.getName()).
                    imgurl(c.getImgurl()).
                    build());
        }
        return output;
    }

    @Override
    public boolean updateCategory(CategoryUpdateRequest input) throws NotFoundException {
        Optional<Category> result = categoryRepository.findById(input.getId());
        if (!result.isPresent()) throw new NotFoundException("업데이트할 카테고리가 없습니다.");
        Category category = result.get();
        category.setName(input.getName());
        category.setImgurl(input.getImgurl());
        return true;
    }

    @Override
    public boolean deleteCategory(Long id) throws NotFoundException{
        if(!categoryRepository.existsById(id)) throw new NotFoundException("삭제할 카테고리가 없습니다.");
        categoryRepository.deleteById(id);
        return true;
    }

}
