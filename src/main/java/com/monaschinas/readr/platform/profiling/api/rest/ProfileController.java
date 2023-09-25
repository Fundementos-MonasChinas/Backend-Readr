package com.monaschinas.readr.platform.profiling.api.rest;

import com.monaschinas.readr.platform.profiling.domain.service.ProfileService;
import com.monaschinas.readr.platform.profiling.mapping.ProfileMapper;
import com.monaschinas.readr.platform.profiling.resource.CreateProfileResource;
import com.monaschinas.readr.platform.profiling.resource.ProfileResource;
import com.monaschinas.readr.platform.profiling.resource.UpdateProfileResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/profiling/profiles", produces = "application/json")
@Tag(name = "Profiles", description = "Create, read, update and delete profiles")
public class ProfileController {
  private final ProfileService profileService;
  private final ProfileMapper mapper;

  public ProfileController(ProfileService profileService, ProfileMapper mapper) {
    this.profileService = profileService;
    this.mapper = mapper;
  }

  @Operation(summary = "Get all profiles")
  @GetMapping
  public Page<ProfileResource> getAllProfiles(Pageable pageable) {
    return mapper.modelListPage(profileService.getAll(), pageable);
  }

  @Operation(summary = "Get profile by id")
  @GetMapping("{profileId}")
  public ProfileResource getProfileById(@PathVariable Long profileId) {
    return mapper.toResource(profileService.getById(profileId));
  }

  @Operation(summary = "Create profile", responses = {
    @ApiResponse(
      description = "Profile successfully created",
      responseCode = "201",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ProfileResource.class)
      )
    )
  })
  @PostMapping
  public ResponseEntity<ProfileResource> createProfile(@RequestBody CreateProfileResource resource) {
    return new ResponseEntity<>(mapper.toResource(profileService.create(mapper.toModel(resource))), HttpStatus.CREATED);
  }

  @Operation(summary = "Update profile", responses = {
    @ApiResponse(
      responseCode = "200",
      description = "Profile successfully updated",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ProfileResource.class)
      )
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Profile not found"
    )
  })
  @PutMapping("{profileId}")
  public ProfileResource updateProfile(@PathVariable Long profileId, @RequestBody UpdateProfileResource resource) {
    return mapper.toResource(profileService.update(profileId, mapper.toModel(resource)));
  }

  @Operation(summary = "Delete profile", responses = {
    @ApiResponse(
      responseCode = "204",
      description = "Profile successfully deleted"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Profile not found"
    )
  })
  @DeleteMapping("{profileId}")
  public ResponseEntity<?> deleteStudent(@PathVariable Long profileId) {
    return profileService.delete(profileId);
  }
}
