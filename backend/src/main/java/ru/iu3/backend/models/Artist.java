package ru.iu3.backend.models;

import javax.persistence.*;

@Entity
@Table(name = "artists")
@Access(AccessType.FIELD)

/**
 * Класс-модель, описывающая артистов
 */
public class Artist {
    // Поле ID (not null, auto increment)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    // Поле с именем
    @Column(name = "name", nullable = false)
    public String name;

    // Поле-внешний ключ countryID
    @Column(name = "countryid")
    public Long countryid;

    // Поле "Возраст"
    @Column(name = "age")
    public String age;

    // Конструктор без параметров
    public Artist() {}

    /**
     * Конструктор с параметром id
     * @param id
     */
    public Artist(Long id) {
        this.id = id;
    }
}