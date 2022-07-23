package com.kwt.basicspringbootsetup.controller;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;
import com.kwt.basicspringbootsetup.service.McuMovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class McuRestApiControllerTest {

    private MockMvc mvc;

    @Mock
    McuMovieService mcuMovieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        McuRestApiController controllerUnderTest = new McuRestApiController(mcuMovieService);
        mvc = standaloneSetup(controllerUnderTest).build();
    }

    @Test
    @DisplayName("When there are MCU movies available")
    void getListOfMcuMovies_withContent() throws Exception {
        MarvelStudioFilmDto ironMan = new MarvelStudioFilmDto(3L, "Iron Man", 2008, 3);
        MarvelStudioFilmDto ironMan2 = new MarvelStudioFilmDto(4L, "Iron Man 2", 2010, 4);
        MarvelStudioFilmDto ironMan3 = new MarvelStudioFilmDto(11L, "Iron Man 3", 2013, 11);
        List<MarvelStudioFilmDto> mcuMovies = Arrays.asList(ironMan, ironMan2, ironMan3);

        Optional<List<MarvelStudioFilmDto>> optionalListOfMcuMovies =  Optional.of(mcuMovies);

        when(mcuMovieService.getMcuMovies()).thenReturn(optionalListOfMcuMovies);

        mvc.perform(get("/mcu-movies").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(ironMan.getName())))
                .andExpect(jsonPath("$[0].releaseYear", is(ironMan.getReleaseYear())))
                .andExpect(jsonPath("$[1].name", is(ironMan2.getName())))
                .andExpect(jsonPath("$[1].chronologicalOrder", is(ironMan2.getChronologicalOrder())))
                .andExpect(jsonPath("$[2].name", is(ironMan3.getName())))
                .andExpect(jsonPath("$[2].releaseYear", is(ironMan3.getReleaseYear())))
                .andExpect(jsonPath("$[2].chronologicalOrder", is(ironMan3.getChronologicalOrder())));

        // Making sure the "when" has been called once only
        verify(mcuMovieService, times(1)).getMcuMovies();
    }

    @Test
    @DisplayName("When there are no MCU movies available")
    void getListOfMcuMovies_withNoContent() throws Exception {
        when(mcuMovieService.getMcuMovies()).thenReturn(Optional.empty());

        mvc.perform(get("/mcu-movies").contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", hasSize(0)));

        // Making sure the "when" has been called once only
        verify(mcuMovieService, times(1)).getMcuMovies();
    }

    @Test
    void getMcuMoviesByReleaseYear() throws Exception {
        throw new RuntimeException("To be implemented");
    }

    @Test
    void getMcuMoviesByChronologicalOrder() throws Exception {
        throw new RuntimeException("To be implemented");
    }
}