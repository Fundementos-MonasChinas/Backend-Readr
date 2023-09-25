package com.monaschinas.readr.platform.profiling.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileResource {
  @NotNull
  @NotBlank
  @Size(max = 32)
  private String firstName;

  @NotNull
  @NotBlank
  @Size(max = 32)
  private String lastName;

  @NotNull
  @NotBlank
  private Long roleId;
}
