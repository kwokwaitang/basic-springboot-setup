package com.kwt.basicspringbootsetup.controller;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;
import com.kwt.basicspringbootsetup.service.McuMovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class McuController {

    private final McuMovieService mcuMovieService;

    public McuController(McuMovieService mcuMovieService) {
        this.mcuMovieService = Objects.requireNonNull(mcuMovieService, () -> "MCU movie service is unavailable");
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("mcuMovies", Collections.emptyList());
        model.addAttribute("mcuMoviesByReleaseYear", Collections.emptyList());
        model.addAttribute("mcuMoviesByChronologicalOrder", Collections.emptyList());

        Optional<List<MarvelStudioFilmDto>> mcuMovies = mcuMovieService.getMcuMovies();
        mcuMovies.ifPresent(marvelStudioFilms -> model.addAttribute("mcuMovies", marvelStudioFilms));

        Optional<List<MarvelStudioFilmDto>> mcuMoviesByReleaseYearOrder = mcuMovieService.getMcuMoviesByReleaseYearOrder();
        mcuMoviesByReleaseYearOrder.ifPresent(marvelStudioFilms -> model.addAttribute("mcuMoviesByReleaseYearOrder", marvelStudioFilms));

        // Non-functional (or traditional) style...
        Optional<List<MarvelStudioFilmDto>> mcuMoviesByChronologicalOrder = mcuMovieService.getMcuMoviesByChronologicalOrder();

        //noinspection OptionalIsPresent
        if (mcuMoviesByChronologicalOrder.isPresent()) {
            model.addAttribute("mcuMoviesByChronologicalOrder", mcuMoviesByChronologicalOrder.get());
        }

        return "index";
    }
}
