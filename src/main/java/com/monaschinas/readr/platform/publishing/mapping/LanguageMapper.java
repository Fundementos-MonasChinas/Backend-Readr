package com.monaschinas.readr.platform.publishing.mapping;

import com.monaschinas.readr.platform.publishing.domain.model.Language;
import com.monaschinas.readr.platform.publishing.resource.CreateLanguageResource;
import com.monaschinas.readr.platform.publishing.resource.LanguageResource;
import com.monaschinas.readr.platform.publishing.resource.UpdateLanguageResource;
import com.monaschinas.readr.platform.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class LanguageMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public LanguageResource toResource(Language model) { return mapper.map(model, LanguageResource.class); }

    public Page<LanguageResource> modelListPage(List<Language> modelList, Pageable pageable){
        return new PageImpl<>(mapper.mapList(modelList, LanguageResource.class), pageable, modelList.size());
    }

    public Language toModel(CreateLanguageResource resource) { return mapper.map(resource, Language.class); }
    public Language toModel(UpdateLanguageResource resource) { return mapper.map(resource, Language.class); }
}
