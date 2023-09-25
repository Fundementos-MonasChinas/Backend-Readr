package com.monaschinas.readr.platform.profiling.mapping;

import com.monaschinas.readr.platform.profiling.domain.model.Profile;
import com.monaschinas.readr.platform.profiling.resource.CreateProfileResource;
import com.monaschinas.readr.platform.profiling.resource.ProfileResource;
import com.monaschinas.readr.platform.profiling.resource.UpdateProfileResource;
import com.monaschinas.readr.platform.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProfileMapper {
  @Autowired
  private EnhancedModelMapper mapper;

  public ProfileResource toResource(Profile model) {
    return mapper.map(model, ProfileResource.class);
  }

  public Profile toModel(CreateProfileResource resource) {
    return mapper.map(resource, Profile.class);
  }

  public Profile toModel(UpdateProfileResource resource) {
    return mapper.map(resource, Profile.class);
  }

  public Page<ProfileResource> modelListPage(List<Profile> modelList, Pageable pageable) {
    return new PageImpl<>(mapper.mapList(modelList, ProfileResource.class), pageable, modelList.size());
  }
}
