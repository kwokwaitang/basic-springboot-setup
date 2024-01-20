package com.kwt.basicspringbootsetup.controller;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;
import com.kwt.basicspringbootsetup.service.McuMovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.iterableWithSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class McuControllerTest {

    @Mock
    McuMovieService mcuMovieService;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        final McuController controllerUnderTest = new McuController(mcuMovieService);
        mvc = standaloneSetup(controllerUnderTest).build();
    }

    @Test
    @DisplayName("When...")
    void shouldIndex() throws Exception {
        List<MarvelStudioFilmDto> mcuMovies = getMcuMovies();
        List<MarvelStudioFilmDto> mcuMoviesByReleaseYearOrder = getMcuMoviesByReleaseYear();
        List<MarvelStudioFilmDto> mcuMoviesByChronologicalOrder = getMcuMoviesByChronologicalOrder();

        when(mcuMovieService.getMcuMovies()).thenReturn(Optional.of(mcuMovies));
        when(mcuMovieService.getMcuMoviesByReleaseYearOrder()).thenReturn(Optional.of(mcuMoviesByReleaseYearOrder));
        when(mcuMovieService.getMcuMoviesByChronologicalOrder()).thenReturn(Optional.of(mcuMoviesByChronologicalOrder));

        mvc.perform(get("/"))
                .andExpect(model().attribute("mcuMovies", iterableWithSize(3)))
                .andExpect(model().attribute("mcuMoviesByReleaseYearOrder", iterableWithSize(3)))
                .andExpect(model().attribute("mcuMoviesByChronologicalOrder", iterableWithSize(3)))

                // Validate the response code and view
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(print());

        verify(mcuMovieService, times(1)).getMcuMovies();
        verify(mcuMovieService, times(1)).getMcuMoviesByReleaseYearOrder();
        verify(mcuMovieService, times(1)).getMcuMoviesByChronologicalOrder();
    }

    private List<MarvelStudioFilmDto> getMcuMovies() {
        List<MarvelStudioFilmDto> mcuMovies = new ArrayList<>();
        mcuMovies.add(new MarvelStudioFilmDto(1L, "Captain America : The First Avenger", 2011, 1));
        mcuMovies.add(new MarvelStudioFilmDto(2L, "Captain Marvel", 2019, 2));
        mcuMovies.add(new MarvelStudioFilmDto(3L, "Iron Man", 2008, 3));

        return mcuMovies;
    }

    private List<MarvelStudioFilmDto> getMcuMoviesByReleaseYear() {
        List<MarvelStudioFilmDto> mcuMovies = new ArrayList<>();
        mcuMovies.add(new MarvelStudioFilmDto(3L, "Iron Man", 2008, 3));
        mcuMovies.add(new MarvelStudioFilmDto(5L, "The Incredible Hulk", 2008, 5));
        mcuMovies.add(new MarvelStudioFilmDto(4L, "Iron Man 2", 2010, 4));

        return mcuMovies;
    }

    private List<MarvelStudioFilmDto> getMcuMoviesByChronologicalOrder() {
        return getMcuMovies();
    }
}