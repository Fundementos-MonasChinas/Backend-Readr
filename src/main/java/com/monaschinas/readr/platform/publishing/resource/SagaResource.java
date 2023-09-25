package com.monaschinas.readr.platform.publishing.resource;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class SagaResource {
    private Long id;
    private String title;
    private String synopsis;
    private SagaStatusResource sagaStatus;
}
