package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Category;
import uz.pdp.codingbat.entity.Language;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.payload.CategoryDto;
import uz.pdp.codingbat.repository.CategoryRepository;
import uz.pdp.codingbat.repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResult add(CategoryDto categoryDto) {
        Integer languageId = categoryDto.getLanguageId();
        Optional<Language> optionalLanguage = languageRepository.findById(languageId);
        if (!optionalLanguage.isPresent()) {
            return new ApiResult("Language not found", false);
        }

        Category category = new Category();
        category.setDescription(categoryDto.getDescription());
        category.setLanguage(optionalLanguage.get());
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return new ApiResult("Successfully added", true);
    }

    public List<Category> getAll() {
        List<Category> all = categoryRepository.findAll();
        return all;
    }

    public Category getOneById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) return null;
        return optionalCategory.get();
    }

    public ApiResult delete(Integer id) {
        try {
            categoryRepository.deleteById(id);
            return new ApiResult("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResult("Error in deleting", false);
        }
    }

    public ApiResult edit(Integer id, CategoryDto categoryDto) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) return new ApiResult("Category not found", false);
        Optional<Language> optionalLanguage = languageRepository.findById(categoryDto.getLanguageId());
        if (!optionalLanguage.isPresent()) return new ApiResult("Language not found", false);


        Category category = optionalCategory.get();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setLanguage(optionalLanguage.get());
        categoryRepository.save(category);
        return new ApiResult("Successfully edited", true);


    }
}
