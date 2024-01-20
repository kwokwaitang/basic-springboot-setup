package com.kwt;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;
import com.kwt.basicspringbootsetup.service.McuMovieService;
import io.cucumber.java8.En;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MarvelStudioFilmsSteps extends CucumberTestContextConfiguration implements En {

    private McuMovieService mcuMovieService;

    private List<MarvelStudioFilmDto> mcuMovies;

    public MarvelStudioFilmsSteps() {
        Given("The number of MCU movies that have debuted", () -> {
            return;
        });

        When("listing movies released in the year {int}", (Integer year) -> {
            mcuMovieService.getMcuMoviesByReleaseYear(year);
        });

        Then("there are a number of films {string} shown", (String films) -> {
            assertThat(mcuMovies.size(), is(films.split(",").length));
        });
    }
}
