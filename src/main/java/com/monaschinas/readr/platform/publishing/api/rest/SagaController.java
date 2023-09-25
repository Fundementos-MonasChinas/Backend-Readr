package com.monaschinas.readr.platform.publishing.api.rest;

import com.monaschinas.readr.platform.publishing.domain.service.SagaService;
import com.monaschinas.readr.platform.publishing.mapping.SagaMapper;
import com.monaschinas.readr.platform.publishing.resource.CreateSagaResource;
import com.monaschinas.readr.platform.publishing.resource.SagaResource;
import com.monaschinas.readr.platform.publishing.resource.UpdateSagaResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/publishing/sagas", produces = "application/json")
public class SagaController {
    private final SagaService sagaService;
    private final SagaMapper mapper;

    public SagaController(SagaService sagaService, SagaMapper mapper) {
        this.sagaService = sagaService;
        this.mapper = mapper;
    }
    @GetMapping
    public Page<SagaResource> getAllSagas(Pageable pageable){
        return mapper.modelListPage(sagaService.getAll(),pageable);
    }

    @GetMapping({"sagaId"})
    public SagaResource getSagaById(@PathVariable Long sagaId){
        return mapper.toResource(sagaService.getById(sagaId));
    }

    @PostMapping
    public ResponseEntity<SagaResource> createSaga(@RequestBody CreateSagaResource resource){
        return new ResponseEntity<>(mapper.toResource(sagaService.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }

    @PutMapping({"sagaId"})
    public SagaResource updateSaga(@PathVariable Long sagaId, @RequestBody UpdateSagaResource resource){
        return mapper.toResource(sagaService.update(sagaId, mapper.toModel(resource)));
    }

    @DeleteMapping({"sagaId"})
    public ResponseEntity<?> deleteSaga(@PathVariable Long sagaId){
        return sagaService.delete(sagaId);
    }
}
