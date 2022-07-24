package com.kwt.basicspringbootsetup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;
import com.kwt.basicspringbootsetup.service.McuMovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class McuRestApiControllerTest {

    private MockMvc mvc;

    @Mock
    McuMovieService mcuMovieService;

    private JacksonTester<List<MarvelStudioFilmDto>> json;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        MockitoAnnotations.openMocks(this);
        McuRestApiController controllerUnderTest = new McuRestApiController(mcuMovieService);
        mvc = standaloneSetup(controllerUnderTest).build();
    }

    @Test
    @DisplayName("When there are MCU movies available")
    void getListOfMcuMovies_withContent() throws Exception {
        // Given...
        MarvelStudioFilmDto ironMan = new MarvelStudioFilmDto(3L, "Iron Man", 2008, 3);
        MarvelStudioFilmDto ironMan2 = new MarvelStudioFilmDto(4L, "Iron Man 2", 2010, 4);
        MarvelStudioFilmDto ironMan3 = new MarvelStudioFilmDto(11L, "Iron Man 3", 2013, 11);
        List<MarvelStudioFilmDto> mcuMovies = Arrays.asList(ironMan, ironMan2, ironMan3);

        Optional<List<MarvelStudioFilmDto>> optionalListOfMcuMovies =  Optional.of(mcuMovies);

        given(mcuMovieService.getMcuMovies()).willReturn(optionalListOfMcuMovies);

        // When...
        MockHttpServletResponse response = mvc.perform(get("/mcu-movies").contentType(APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        // Then (assert)...
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(json.write(optionalListOfMcuMovies.get()).getJson());
    }

    @Test
    @DisplayName("When there are no MCU movies available")
    void getListOfMcuMovies_withNoContent() throws Exception {
        when(mcuMovieService.getMcuMovies()).thenReturn(Optional.empty());

        MockHttpServletResponse response = mvc.perform(get("/mcu-movies").contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn().getResponse();

        // Making sure the "when" has been called once only
        verify(mcuMovieService, times(1)).getMcuMovies();
    }

    @Test
    void getMcuMoviesByReleaseYear() throws Exception {
        MarvelStudioFilmDto ironMan = new MarvelStudioFilmDto(3L, "Iron Man", 2008, 3);
        MarvelStudioFilmDto incredibleHulk = new MarvelStudioFilmDto(5L, "The Incredible Hulk", 2008, 5);
        MarvelStudioFilmDto ironMan2 = new MarvelStudioFilmDto(4L, "Iron Man 2", 2010, 4);
        List<MarvelStudioFilmDto> mcuMovies = Arrays.asList(ironMan, incredibleHulk, ironMan2);

        Optional<List<MarvelStudioFilmDto>> optionalListOfMcuMovies =  Optional.of(mcuMovies);

        when(mcuMovieService.getMcuMoviesByReleaseYear()).thenReturn(optionalListOfMcuMovies);

        mvc.perform(get("/mcu-movies-by-release-year").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(ironMan.getName())))
                .andExpect(jsonPath("$[0].releaseYear", is(ironMan.getReleaseYear())))
                .andExpect(jsonPath("$[1].name", is(incredibleHulk.getName())))
                .andExpect(jsonPath("$[1].chronologicalOrder", is(incredibleHulk.getChronologicalOrder())))
                .andExpect(jsonPath("$[2].name", is(ironMan2.getName())))
                .andExpect(jsonPath("$[2].releaseYear", is(ironMan2.getReleaseYear())))
                .andExpect(jsonPath("$[2].chronologicalOrder", is(ironMan2.getChronologicalOrder())));

        // Making sure the "when" has been called once only
        verify(mcuMovieService, times(1)).getMcuMoviesByReleaseYear();
    }

    @Test
    @DisplayName("When there are no MCU movies by release year available")
    void getMcuMoviesByReleaseYear_withNoContent() throws Exception {
        when(mcuMovieService.getMcuMoviesByReleaseYear()).thenReturn(Optional.empty());

        mvc.perform(get("/mcu-movies-by-release-year").contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", hasSize(0)));

        // Making sure the "when" has been called once only
        verify(mcuMovieService, times(1)).getMcuMoviesByReleaseYear();
    }

    @Test
    void getMcuMoviesByChronologicalOrder() throws Exception {
        MarvelStudioFilmDto capAmericaFirstAvenger = new MarvelStudioFilmDto(1L, "Captain America : The First Avenger", 2011, 1);
        MarvelStudioFilmDto capMarvel = new MarvelStudioFilmDto(2L, "Captain Marvel", 2019, 2);
        MarvelStudioFilmDto ironMan = new MarvelStudioFilmDto(3L, "Iron Man", 2008, 3);
        List<MarvelStudioFilmDto> mcuMovies = Arrays.asList(capAmericaFirstAvenger, capMarvel, ironMan);

        Optional<List<MarvelStudioFilmDto>> optionalListOfMcuMovies =  Optional.of(mcuMovies);

        when(mcuMovieService.getMcuMoviesByChronologicalOrder()).thenReturn(optionalListOfMcuMovies);

        mvc.perform(get("/mcu-movies-by-chronological-order").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(capAmericaFirstAvenger.getName())))
                .andExpect(jsonPath("$[0].releaseYear", is(capAmericaFirstAvenger.getReleaseYear())))
                .andExpect(jsonPath("$[1].name", is(capMarvel.getName())))
                .andExpect(jsonPath("$[1].chronologicalOrder", is(capMarvel.getChronologicalOrder())))
                .andExpect(jsonPath("$[2].name", is(ironMan.getName())))
                .andExpect(jsonPath("$[2].releaseYear", is(ironMan.getReleaseYear())))
                .andExpect(jsonPath("$[2].chronologicalOrder", is(ironMan.getChronologicalOrder())));

        // Making sure the "when" has been called once only
        verify(mcuMovieService, times(1)).getMcuMoviesByChronologicalOrder();
    }

    @Test
    @DisplayName("When there are no MCU movies by chronological order available")
    void getMcuMoviesByChronologicalOrder_withNoContent() throws Exception {
        when(mcuMovieService.getMcuMoviesByChronologicalOrder()).thenReturn(Optional.empty());

        mvc.perform(get("/mcu-movies-by-chronological-order").contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", hasSize(0)));

        // Making sure the "when" has been called once only
        verify(mcuMovieService, times(1)).getMcuMoviesByChronologicalOrder();
    }
}