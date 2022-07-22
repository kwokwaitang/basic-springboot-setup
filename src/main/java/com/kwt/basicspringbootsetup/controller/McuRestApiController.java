package com.kwt.basicspringbootsetup.controller;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;
import com.kwt.basicspringbootsetup.service.McuMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class McuRestApiController {

    private final McuMovieService mcuMovieService;

    @Autowired // <-- Don't actually need here...
    public McuRestApiController(McuMovieService mcuMovieService) {
        this.mcuMovieService = Objects.requireNonNull(mcuMovieService, () -> "Missing an MCU movie service");
    }

    @GetMapping("/mcu-movies")
    public ResponseEntity<List<MarvelStudioFilmDto>> getListOfMcuMovies() {
        if (mcuMovieService.getMcuMovies().isPresent()) {
            return ResponseEntity.ok(mcuMovieService.getMcuMovies().get());
        }

        return ResponseEntity.ok().body(Collections.emptyList());
    }

    @GetMapping("/mcu-movies-by-release-year")
    public ResponseEntity<List<MarvelStudioFilmDto>> getMcuMoviesByReleaseYear() {
        Optional<List<MarvelStudioFilmDto>> mcuMoviesByReleaseYear = mcuMovieService.getMcuMoviesByReleaseYear();
        if (mcuMoviesByReleaseYear.isPresent()) {
            return ResponseEntity.ok(mcuMoviesByReleaseYear.get());
        }

        return ResponseEntity.ok().body(Collections.emptyList());
    }

    @GetMapping("/mcu-movies-by-chronological-order")
    public ResponseEntity<List<MarvelStudioFilmDto>> getMcuMoviesByChronologicalOrder() {
        Optional<List<MarvelStudioFilmDto>> mcuMoviesByChronologicalOrder = mcuMovieService.getMcuMoviesByChronologicalOrder();
        return mcuMoviesByChronologicalOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().body(Collections.emptyList()));
    }
}
