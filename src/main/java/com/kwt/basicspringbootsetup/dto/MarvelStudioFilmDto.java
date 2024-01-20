package com.kwt.basicspringbootsetup.dto;

import java.util.Objects;

/**
 * Data Transfer Object - used to convey the extract data to the front-end
 */
public class MarvelStudioFilmDto implements Comparable<MarvelStudioFilmDto> {

    private Long id;

    private String name;

    private Integer releaseYear;

    private Integer chronologicalOrder;

    public MarvelStudioFilmDto() {
    }

    public MarvelStudioFilmDto(Long id, String name, Integer releaseYear, Integer chronologicalOrder) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.chronologicalOrder = chronologicalOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getChronologicalOrder() {
        return chronologicalOrder;
    }

    public void setChronologicalOrder(Integer chronologicalOrder) {
        this.chronologicalOrder = chronologicalOrder;
    }

    @Override
    public String toString() {
        return "MarvelStudioFilmDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", chronologicalOrder=" + chronologicalOrder +
                '}';
    }

    @Override
    public int compareTo(MarvelStudioFilmDto other) {
        return compare(this.releaseYear, other.releaseYear);
    }

    public static int compare (int x, int y) {
        return Integer.compare(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarvelStudioFilmDto that = (MarvelStudioFilmDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(releaseYear, that.releaseYear) && Objects.equals(chronologicalOrder, that.chronologicalOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releaseYear, chronologicalOrder);
    }
}
