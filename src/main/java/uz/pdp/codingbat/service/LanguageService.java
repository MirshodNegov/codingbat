package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Language;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.payload.LanguageDto;
import uz.pdp.codingbat.repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;


    public ApiResult add(LanguageDto languageDto) {
        boolean existsByName = languageRepository.existsByName(languageDto.getName());
        if (existsByName)
            return new ApiResult("Language already exists", false);
        Language language = new Language();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return new ApiResult("Successfully added", true);
    }

    public List<Language> getAll() {
        List<Language> all = languageRepository.findAll();
        return all;
    }

    public Language getOneById(Integer id) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (!optionalLanguage.isPresent()) return null;
        return optionalLanguage.get();
    }

    public ApiResult delete(Integer id) {
        try {
            languageRepository.deleteById(id);
            return new ApiResult("Successfully deleted", true);
        } catch (Exception r) {
            return new ApiResult("Error in deleting", false);
        }
    }

    public ApiResult edit(Integer id, LanguageDto languageDto) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (!optionalLanguage.isPresent()) return new ApiResult("language not found", false);
        boolean existsByNameAndIdNot = languageRepository.existsByNameAndIdNot(languageDto.getName(), id);
        if (existsByNameAndIdNot) return new ApiResult("Language already exists", false);

        Language language = optionalLanguage.get();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return new ApiResult("Successfully edited", true);

    }
}
