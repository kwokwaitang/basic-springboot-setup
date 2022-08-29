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

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

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

    @Override
    public Boolean createMcuMovie(MarvelStudioFilmDto marvelStudioFilmDto) throws Exception {
        if (Objects.isNull(marvelStudioFilmDto)) {
            throw new Exception("Problem with the received parameters");
        }
        if (Objects.isNull(marvelStudioFilmDto.getName()) || marvelStudioFilmDto.getName().isEmpty()) {
            throw new Exception("Missing a name for the new MCU movie");
        }
        if (Objects.isNull(marvelStudioFilmDto.getChronologicalOrder()) || marvelStudioFilmDto.getChronologicalOrder() < 0) {
            throw new Exception("The chronological order cannot be less than 0");
        }
        if (Objects.isNull(marvelStudioFilmDto.getReleaseYear()) || marvelStudioFilmDto.getReleaseYear() < 2008) {
            throw new Exception("The release year cannot be before 2008");
        }

        MarvelStudioFilm marvelStudioFilm = new MarvelStudioFilm();
        copyProperties(marvelStudioFilm, marvelStudioFilmDto);

        mcuMovieRepository.save(marvelStudioFilm);

        return true;
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
