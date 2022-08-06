package com.kwt.basicspringbootsetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarvelStudioFilmDto {

    private Long id;

    private String name;

    private Integer releaseYear;

    private Integer chronologicalOrder;
}
