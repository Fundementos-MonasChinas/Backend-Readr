package com.monaschinas.readr.platform.publishing.mapping;

import com.monaschinas.readr.platform.publishing.domain.model.Saga;
import com.monaschinas.readr.platform.publishing.resource.*;
import com.monaschinas.readr.platform.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class SagaMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;
    public SagaResource toResource(Saga model) { return mapper.map(model, SagaResource.class); }
    public Page<SagaResource> modelListPage(List<Saga> modelList, Pageable pageable){
        return new PageImpl<>(mapper.mapList(modelList, SagaResource.class), pageable, modelList.size());
    }
    public Saga toModel(CreateSagaResource resource) { return mapper.map(resource, Saga.class); }
    public Saga toModel(UpdateSagaResource resource) { return mapper.map(resource, Saga.class); }
}
