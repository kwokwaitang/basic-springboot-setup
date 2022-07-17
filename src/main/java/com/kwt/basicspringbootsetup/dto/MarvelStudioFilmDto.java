package com.kwt.basicspringbootsetup.dto;

/**
 * Data Transfer Object - used to convey the extract data to the front-end
 */
public class MarvelStudioFilmDto {

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
}
