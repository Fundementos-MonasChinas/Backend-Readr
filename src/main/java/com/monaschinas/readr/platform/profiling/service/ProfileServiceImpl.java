package com.monaschinas.readr.platform.profiling.service;

import com.monaschinas.readr.platform.profiling.domain.model.Profile;
import com.monaschinas.readr.platform.profiling.domain.persistence.ProfileRepository;
import com.monaschinas.readr.platform.profiling.domain.service.ProfileService;
import com.monaschinas.readr.platform.shared.exception.ResourceNotFoundException;
import com.monaschinas.readr.platform.shared.exception.ResourceValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService {
  private static final String ENTITY = "Profile";

  private final ProfileRepository profileRepository;
  private final Validator validator;

  public ProfileServiceImpl(ProfileRepository profileRepository, Validator validator) {
    this.profileRepository = profileRepository;
    this.validator = validator;
  }

  @Override
  public List<Profile> getAll() {
    return profileRepository.findAll();
  }

  @Override
  public Page<Profile> getAll(Pageable pageable) {
    return profileRepository.findAll(pageable);
  }

  @Override
  public Profile getById(Long profileId) {
    return profileRepository.findById(profileId)
      .orElseThrow(() -> new ResourceNotFoundException(ENTITY, profileId));
  }

  @Override
  public Profile create(Profile profile) {
    Set<ConstraintViolation<Profile>> violations = validator.validate(profile);

    if (!violations.isEmpty())
      throw new ResourceValidationException(ENTITY, violations);

    return profileRepository.save(profile);
  }

  @Override
  public Profile update(Long profileId, Profile profile) {
    Set<ConstraintViolation<Profile>> violations = validator.validate(profile);

    if (!violations.isEmpty())
      throw new ResourceValidationException(ENTITY, violations);

    return profileRepository.findById(profileId)
      .map(profileToUpdate -> profileRepository.save(
        profileToUpdate.withFirstName(profile.getFirstName())
          .withLastName(profile.getLastName())
        )
      )
      .orElseThrow(() -> new ResourceNotFoundException(ENTITY, profileId));
  }

  @Override
  public ResponseEntity<?> delete(Long profileId) {
    return profileRepository.findById(profileId)
      .map(profile -> {
        profileRepository.delete(profile);
        return ResponseEntity.ok().build();
      })
      .orElseThrow(() -> new ResourceNotFoundException(ENTITY, profileId));
  }
}
