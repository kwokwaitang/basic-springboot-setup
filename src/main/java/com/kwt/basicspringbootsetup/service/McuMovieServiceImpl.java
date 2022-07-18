package com.kwt.basicspringbootsetup.service;

import com.kwt.basicspringbootsetup.dto.MarvelStudioFilmDto;
import com.kwt.basicspringbootsetup.entity.MarvelStudioFilm;
import com.kwt.basicspringbootsetup.repository.McuMovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class McuMovieServiceImpl implements McuMovieService {

    private final ModelMapper modelMapper;

    private final McuMovieRepository mcuMovieRepository;

    public McuMovieServiceImpl(ModelMapper modelMapper, McuMovieRepository mcuMovieRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper, () -> "Model mapper is unavailable");
        this.mcuMovieRepository = Objects.requireNonNull(mcuMovieRepository, () -> "No access to MCU Movie database");
    }

    @Override
    public Optional<List<MarvelStudioFilmDto>> getMcuMovies() {
        List<MarvelStudioFilm> mcuMovies = (List<MarvelStudioFilm>) mcuMovieRepository.findAll();
        if (!mcuMovies.isEmpty()) {
            return Optional.of(mapList(mcuMovies, MarvelStudioFilmDto.class));
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
            return Optional.of(mapList(mcuMovies.get(), MarvelStudioFilmDto.class));
        }

        return Optional.empty();
    }

    private <S, T> List<T> mapList(List<S> listOfEntities, Class<T> targetDtoClass) {
        return listOfEntities
                .stream()
                .map(entity -> modelMapper.map(entity, targetDtoClass))
                .collect(Collectors.toList());
    }
}
