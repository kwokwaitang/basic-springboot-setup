package com.kwt.basicspringbootsetup.controller;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;
import com.kwt.basicspringbootsetup.service.McuMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * For the Open API / Swagger page...
 * http://localhost:8080/swagger-ui/index.html#/mcu-rest-api-controller
 */
@RestController
public class McuRestApiController {

    private static final Logger LOGGER = Logger.getGlobal();

    private final McuMovieService mcuMovieService;

    @Autowired // <-- Included the annotation but its npt actually needed here...
    public McuRestApiController(McuMovieService mcuMovieService) {
        this.mcuMovieService = Objects.requireNonNull(mcuMovieService, () -> "Missing an MCU movie service");
    }

    @GetMapping("/mcu-movies")
    public ResponseEntity<List<MarvelStudioFilmDto>> getListOfMcuMovies() {
        Optional<List<MarvelStudioFilmDto>> mcuMovies = mcuMovieService.getMcuMovies();

        // noinspection OptionalIsPresent
        if (mcuMovies.isPresent()) {
            return ResponseEntity.ok(mcuMovies.get());
        }

        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/mcu-movies-by-release-year")
    public ResponseEntity<List<MarvelStudioFilmDto>> getMcuMoviesByReleaseYear() {
        Optional<List<MarvelStudioFilmDto>> mcuMoviesByReleaseYear = mcuMovieService.getMcuMoviesByReleaseYear();

        // noinspection OptionalIsPresent
        if (mcuMoviesByReleaseYear.isPresent()) {
            return ResponseEntity.ok(mcuMoviesByReleaseYear.get());
        }

        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/mcu-movies-by-chronological-order")
    public ResponseEntity<List<MarvelStudioFilmDto>> getMcuMoviesByChronologicalOrder() {
        Optional<List<MarvelStudioFilmDto>> mcuMoviesByChronologicalOrder = mcuMovieService.getMcuMoviesByChronologicalOrder();

        // Using a more functional-style programming...
        return mcuMoviesByChronologicalOrder.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT));
    }

    @PostMapping("/new-mcu-movie")
    public ResponseEntity<Boolean> createMcuMovie(@RequestBody MarvelStudioFilmDto marvelStudioFilmDto) {
        try {
            return ResponseEntity.ok().body(mcuMovieService.createMcuMovie(marvelStudioFilmDto));
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }

        return ResponseEntity.badRequest().body(false);
    }
}
