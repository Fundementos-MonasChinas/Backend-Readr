package com.monaschinas.readr.platform.publishing.mapping;

import com.monaschinas.readr.platform.publishing.domain.model.Chapter;
import com.monaschinas.readr.platform.publishing.resource.ChapterResource;
import com.monaschinas.readr.platform.publishing.resource.CreateChapterResource;
import com.monaschinas.readr.platform.publishing.resource.UpdateChapterResource;
import com.monaschinas.readr.platform.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class ChapterMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public ChapterResource toResource(Chapter model) { return mapper.map(model, ChapterResource.class); }

    public Page<ChapterResource> modelListPage(List<Chapter> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, ChapterResource.class), pageable, modelList.size());
    }

    public Chapter toModel(CreateChapterResource resource) { return mapper.map(resource, Chapter.class); }
    public Chapter toModel(UpdateChapterResource resource) { return mapper.map(resource, Chapter.class); }
}
