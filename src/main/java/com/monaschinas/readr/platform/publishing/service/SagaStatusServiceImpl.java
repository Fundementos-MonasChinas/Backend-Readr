package com.monaschinas.readr.platform.publishing.service;

import com.monaschinas.readr.platform.publishing.domain.model.SagaStatus;
import com.monaschinas.readr.platform.publishing.domain.persistence.SagaStatusRepository;
import com.monaschinas.readr.platform.publishing.domain.service.SagaStatusService;
import com.monaschinas.readr.platform.shared.exception.ResourceNotFoundException;
import com.monaschinas.readr.platform.shared.exception.ResourceValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SagaStatusServiceImpl implements SagaStatusService {
    private static final String ENTITY = "SagaStatus";

    private final SagaStatusRepository sagaStatusRepository;

    private final Validator validator;

    public SagaStatusServiceImpl(SagaStatusRepository sagaStatusRepository, Validator validator) {
        this.sagaStatusRepository = sagaStatusRepository;
        this.validator = validator;
    }

    @Override
    public List<SagaStatus> getAll() { return sagaStatusRepository.findAll(); }

    @Override
    public Page<SagaStatus> getAll(Pageable pageable) { return sagaStatusRepository.findAll(pageable); }

    @Override
    public SagaStatus getById(Long sagaStatusId) {
        return sagaStatusRepository.findById(sagaStatusId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY,sagaStatusId));
    }

    @Override
    public SagaStatus create(SagaStatus sagaStatus) {
        Set<ConstraintViolation<SagaStatus>> violations = validator.validate(sagaStatus);

        if(!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }

        return sagaStatusRepository.save(sagaStatus);
    }

    @Override
    public SagaStatus update(Long sagaStatusId, SagaStatus sagaStatus) {

        Set<ConstraintViolation<SagaStatus>> violations = validator.validate(sagaStatus);

        if(!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }
        Optional<SagaStatus> sagaStatusWithName = sagaStatusRepository.findByName(sagaStatus.getName());
        if(sagaStatusWithName.isPresent() && !sagaStatusWithName.get().getId().equals(sagaStatus.getId())) {
            throw new ResourceValidationException(ENTITY, "A language with the same name already exists");
        }
        return sagaStatusRepository.findById(sagaStatusId)
                .map(genreToUpdate -> sagaStatusRepository.save(genreToUpdate
                        .withName(sagaStatus.getName())))
                .orElseThrow(()->new ResourceNotFoundException(ENTITY,sagaStatusId));
    }

    @Override
    public ResponseEntity<?> delete(Long sagaStatusId) {
        return sagaStatusRepository.findById(sagaStatusId)
                .map(sagaStatus -> {
                    sagaStatusRepository.delete(sagaStatus);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY,sagaStatusId));
    }
}
