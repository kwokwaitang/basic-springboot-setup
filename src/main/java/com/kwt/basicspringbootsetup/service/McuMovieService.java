package com.kwt.basicspringbootsetup.service;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;

import java.util.List;
import java.util.Optional;

public interface McuMovieService {
    Optional<List<MarvelStudioFilmDto>> getMcuMovies();

    Optional<List<MarvelStudioFilmDto>> getMcuMoviesByReleaseYearOrder();

    Optional<List<MarvelStudioFilmDto>> getMcuMoviesByReleaseYear(Integer year);

    Optional<List<MarvelStudioFilmDto>> getMcuMoviesByChronologicalOrder();

    @SuppressWarnings("java:S112")
    Boolean createMcuMovie(MarvelStudioFilmDto marvelStudioFilmDto) throws Exception;
}
