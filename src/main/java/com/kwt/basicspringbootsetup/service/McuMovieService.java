package com.kwt.basicspringbootsetup.service;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;

import java.util.List;
import java.util.Optional;

public interface McuMovieService {
    Optional<List<MarvelStudioFilmDto>> getMcuMovies();

    Optional<List<MarvelStudioFilmDto>> getMcuMoviesByReleaseYear();

    Optional<List<MarvelStudioFilmDto>> getMcuMoviesByChronologicalOrder();

    Boolean createMcuMovie(MarvelStudioFilmDto marvelStudioFilmDto) throws Exception;
}
