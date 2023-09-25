package com.monaschinas.readr.platform.profiling.domain.service;

import com.monaschinas.readr.platform.profiling.domain.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProfileService {
  List<Profile> getAll();
  Page<Profile> getAll(Pageable pageable);
  Profile getById(Long profileId);
  Profile create(Profile profile);
  Profile update(Long profileId, Profile profile);
  ResponseEntity<?> delete(Long profileId);
}
