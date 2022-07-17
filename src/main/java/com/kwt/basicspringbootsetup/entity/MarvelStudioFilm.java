package com.kwt.basicspringbootsetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MCU_MOVIE")
public class MarvelStudioFilm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "RELEASE_YEAR")
    private Integer releaseYear;

    @Column(name = "CHRONOLOGICAL_ORDER")
    private Integer chronologicalOrder;

    public MarvelStudioFilm() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
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
        return "MarvelStudioFilms{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", chronologicalOrder=" + chronologicalOrder +
                '}';
    }
}
