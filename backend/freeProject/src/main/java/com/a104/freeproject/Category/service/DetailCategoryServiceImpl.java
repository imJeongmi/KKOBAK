package com.a104.freeproject.Category.service;

import com.a104.freeproject.Category.entity.Category;
import com.a104.freeproject.Category.entity.DetailCategory;
import com.a104.freeproject.Category.repository.CategoryRepository;
import com.a104.freeproject.Category.repository.DetailCategoryRepository;
import com.a104.freeproject.Category.response.DetailCategoryResponse;
import com.a104.freeproject.Category.response.DetailResponse;
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
public class DetailCategoryServiceImpl implements DetailCategoryService{

    private final DetailCategoryRepository detailRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public boolean regDetail(Long category, String name) throws NotFoundException {

        if(!categoryRepository.existsById(category)) throw new NotFoundException("해당 이름의 카테고리가 없습니다.");
        Category c = categoryRepository.findById(category).get();

        if(detailRepository.existsByCategoryAndName(c,name)) throw new NotFoundException("해당 세부 카테고리가 존재합니다.");

        try{
            DetailCategory detail = DetailCategory.builder().category(c).name(name).build();
            detailRepository.save(detail);
            return true;
        }catch (Exception e){
            throw e;
        }

    }

    @Override
    public List<DetailResponse> getCategoryList(Long category) throws NotFoundException {

        if(!categoryRepository.existsById(category)) throw new NotFoundException("해당 이름의 카테고리가 없습니다.");
        Category c = categoryRepository.findById(category).get();

        try{
            List<DetailCategory> list = detailRepository.findAllByCategory(c);
            List<DetailResponse> output = new LinkedList<>();

            for(DetailCategory d : list){
                output.add(DetailResponse.builder()
                        .detailId(d.getId())
                        .detailName(d.getName())
                        .build());
            }

            return output;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<DetailCategoryResponse> getAllCategoryList() throws NotFoundException {

        try{
            List<DetailCategory> list = detailRepository.findAll();
            List<DetailCategoryResponse> output = new LinkedList<>();

            for(DetailCategory d : list){
                output.add(DetailCategoryResponse.builder()
                        .detailId(d.getId())
                        .detailName(d.getName())
                        .categoryId(d.getCategory().getId())
                        .build());
            }

            return output;
        }catch (Exception e){
            throw e;
        }
    }
}
