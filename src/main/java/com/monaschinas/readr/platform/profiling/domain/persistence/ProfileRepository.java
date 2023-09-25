package com.monaschinas.readr.platform.profiling.domain.persistence;

import com.monaschinas.readr.platform.profiling.domain.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
  List<Profile> findByRoleId(Long roleId);
  Page<Profile> findByRoleId(Long roleId, Pageable pageable);
}
