package com.kwt.basicspringbootsetup.service;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;
import com.kwt.basicspringbootsetup.entity.MarvelStudioFilm;
import com.kwt.basicspringbootsetup.repository.McuMovieRepository;
import ma.glasnost.orika.MapperFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class McuMovieServiceImpl implements McuMovieService {

    private final MapperFactory mapperFactory;

    private final McuMovieRepository mcuMovieRepository;

    public McuMovieServiceImpl(MapperFactory mapperFactory, McuMovieRepository mcuMovieRepository) {
        this.mapperFactory = Objects.requireNonNull(mapperFactory, () -> "Orika mapper factory is unavailable");
        this.mcuMovieRepository = Objects.requireNonNull(mcuMovieRepository, () -> "No access to MCU Movie database");
    }

    @Override
    public Optional<List<MarvelStudioFilmDto>> getMcuMovies() {
        List<MarvelStudioFilm> mcuMovies = (List<MarvelStudioFilm>) mcuMovieRepository.findAll();
        if (!mcuMovies.isEmpty()) {
            return Optional.of(mapperFactory.getMapperFacade().mapAsList(mcuMovies, MarvelStudioFilmDto.class));
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<MarvelStudioFilmDto>> getMcuMoviesByReleaseYear() {
        return getMcuMoviesBy(mcuMovieRepository.byReleaseYear());
    }

    @Override
    public Optional<List<MarvelStudioFilmDto>> getMcuMoviesByChronologicalOrder() {
        return getMcuMoviesBy(mcuMovieRepository.byChronologicalOrder());
    }

    private Optional<List<MarvelStudioFilmDto>> getMcuMoviesBy(Optional<List<MarvelStudioFilm>> mcuMovies) {
        if (mcuMovies.isPresent()) {
            return Optional.of(mapperFactory.getMapperFacade().mapAsList(mcuMovies.get(), MarvelStudioFilmDto.class));
        }

        return Optional.empty();
    }
}
