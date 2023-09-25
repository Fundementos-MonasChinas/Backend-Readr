package com.monaschinas.readr.platform.publishing.resource;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResource {
    private Long id;
    private Long profileId;
    private BookResource book;
}
