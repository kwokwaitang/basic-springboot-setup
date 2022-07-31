package com.kwt.basicspringbootsetup.controller;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;
import com.kwt.basicspringbootsetup.service.McuMovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * For the Open API / Swagger page...
 * http://localhost:8080/swagger-ui/index.html#/mcu-rest-api-controller
 *
 * Also useful...
 * https://springdoc.org/#migrating-from-springfox
 */
@Tag(name = "mcu-movies", description = "The MCU movies API")
@RestController
@RequestMapping("/mcu-movies")
public class McuRestApiController {

    private final McuMovieService mcuMovieService;

    @Autowired // <-- Included the annotation but its npt actually needed here...
    public McuRestApiController(McuMovieService mcuMovieService) {
        this.mcuMovieService = Objects.requireNonNull(mcuMovieService, () -> "Missing an MCU movie service");
    }

    @Operation(summary = "Get all MCU movies", tags = { "mcu-movies" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MarvelStudioFilmDto.class)) }),
            @ApiResponse(responseCode = "204", description = "No MCU movies available", content = @Content)
    })
    @GetMapping("/all")
    public ResponseEntity<List<MarvelStudioFilmDto>> getListOfMcuMovies() {
        Optional<List<MarvelStudioFilmDto>> mcuMovies = mcuMovieService.getMcuMovies();

        // noinspection OptionalIsPresent
        if (mcuMovies.isPresent()) {
            return ResponseEntity.ok(mcuMovies.get());
        }

        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all MCU movies by release year", tags = { "mcu-movies" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MarvelStudioFilmDto.class)) }),
            @ApiResponse(responseCode = "204", description = "No MCU movies available", content = @Content)
    })
    @GetMapping("/by-release-year")
    public ResponseEntity<List<MarvelStudioFilmDto>> getMcuMoviesByReleaseYear() {
        Optional<List<MarvelStudioFilmDto>> mcuMoviesByReleaseYear = mcuMovieService.getMcuMoviesByReleaseYear();

        // noinspection OptionalIsPresent
        if (mcuMoviesByReleaseYear.isPresent()) {
            return ResponseEntity.ok(mcuMoviesByReleaseYear.get());
        }

        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all MCU movies by chronological order", tags = { "mcu-movies" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MarvelStudioFilmDto.class)) }),
            @ApiResponse(responseCode = "204", description = "No MCU movies available", content = @Content)
    })
    @GetMapping("/by-chronological-order")
    public ResponseEntity<List<MarvelStudioFilmDto>> getMcuMoviesByChronologicalOrder() {
        Optional<List<MarvelStudioFilmDto>> mcuMoviesByChronologicalOrder = mcuMovieService.getMcuMoviesByChronologicalOrder();

        // Using a more functional-style programming...
        return mcuMoviesByChronologicalOrder.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT));
    }
}
