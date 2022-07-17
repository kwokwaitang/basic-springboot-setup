package com.kwt.basicspringbootsetup.repository;

import com.kwt.basicspringbootsetup.entity.MarvelStudioFilm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface McuMovieRepository extends CrudRepository<MarvelStudioFilm, Long> {
    // Any unique/custom methods to access the data

    @Query(nativeQuery = true, value = "SELECT * FROM MCU_MOVIE ORDER BY RELEASE_YEAR, CHRONOLOGICAL_ORDER")
    Optional<List<MarvelStudioFilm>> byReleaseYear();

    @Query(nativeQuery = true, value = "SELECT * FROM MCU_MOVIE ORDER BY CHRONOLOGICAL_ORDER")
    Optional<List<MarvelStudioFilm>> byChronologicalOrder();
}
