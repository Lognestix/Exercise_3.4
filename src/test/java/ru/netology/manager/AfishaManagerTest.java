package ru.netology.manager;

import org.junit.jupiter.api.Test;
import ru.netology.domain.Movie;

import static org.junit.jupiter.api.Assertions.*;

class AfishaManagerTest {

    //Общие данные:
    private final AfishaManager afishaManagerDefault = new AfishaManager();
    private final AfishaManager afishaManagerCustom = new AfishaManager(5);
    private final AfishaManager afishaManagerCustomNegative = new AfishaManager(-5);

    private final Movie zero = new Movie(0, null, false, "Бладшот.", "Боевик");
    private final Movie first = new Movie(1, null, false, "Вперед.", "Мультфильм");
    private final Movie second = new Movie(2, null, false, "Отель Белград.", "Комедия");
    private final Movie third = new Movie(3, null, false, "Джентельмены.", "Боевик");
    private final Movie fourth = new Movie(4, null, false, "Человек-неведимка.", "Ужасы");
    private final Movie fifth = new Movie(5, null, true, "Тролли. Мировой тур.", "Мультфильм");
    private final Movie sixth = new Movie(6, null, true, "Номер один.", "Комедия");
    private final Movie seventh = new Movie(7, null, false, "Энканто.", "Мультфильм");
    private final Movie eighth = new Movie(8, null, true, "Нормальный только я.", "Комедия");
    private final Movie ninth = new Movie(9, null, true, "Кощей. Начало.", "Мультфильм");
    private final Movie tenth = new Movie(10, null, false, "Месть земли.", "Боевик");
    private final Movie eleventh = new Movie(11, null, false, "Венецианский купец.", "Драма");

    @Test   //Тест на сохранение фильма в массиве данных
    void shouldSaveMovie() {
        afishaManagerDefault.saveMovie(zero);

        Movie[] expected = { zero };
        Movie[] actual = afishaManagerDefault.findAll();

        assertArrayEquals(actual, expected);
    }

    @Test   //Тест на вывод последних 10 добавленных фильмов, при том что их меньше
    void shouldFindAllBelowNominalAfishaManagerDefault() {
        afishaManagerDefault.saveMovie(zero);
        afishaManagerDefault.saveMovie(first);
        afishaManagerDefault.saveMovie(second);
        afishaManagerDefault.saveMovie(third);
        afishaManagerDefault.saveMovie(fourth);

        Movie[] expected = { fourth, third, second, first, zero };
        Movie[] actual = afishaManagerDefault.findAll();

        assertArrayEquals(actual, expected);
    }

    @Test   //Тест на вывод последних 10 добавленных фильмов
    void shouldFindAllNominalAfishaManagerDefault() {
        afishaManagerDefault.saveMovie(zero);
        afishaManagerDefault.saveMovie(first);
        afishaManagerDefault.saveMovie(second);
        afishaManagerDefault.saveMovie(third);
        afishaManagerDefault.saveMovie(fourth);
        afishaManagerDefault.saveMovie(fifth);
        afishaManagerDefault.saveMovie(sixth);
        afishaManagerDefault.saveMovie(seventh);
        afishaManagerDefault.saveMovie(eighth);
        afishaManagerDefault.saveMovie(ninth);
        afishaManagerDefault.saveMovie(tenth);
        afishaManagerDefault.saveMovie(eleventh);

        Movie[] expected = { eleventh, tenth, ninth, eighth, seventh, sixth, fifth, fourth, third, second };
        Movie[] actual = afishaManagerDefault.findAll();

        assertArrayEquals(actual, expected);
    }

    @Test   //Тест на удаление фильма по идентификатору фильма
    void shouldRemoveMovieById() {
        afishaManagerDefault.saveMovie(zero);
        afishaManagerDefault.saveMovie(first);
        afishaManagerDefault.removeMovieById(0);

        Movie[] expected = { first };
        Movie[] actual = afishaManagerDefault.findAll();

        assertArrayEquals(actual, expected);
    }

    @Test   //Тест на вывод заданного количества добавленных фильмов, при том что их меньше
    void shouldFindAllBelowNominalAfishaManagerCustom() {
        afishaManagerCustom.saveMovie(zero);
        afishaManagerCustom.saveMovie(first);
        afishaManagerCustom.saveMovie(second);
        afishaManagerCustom.saveMovie(third);

        Movie[] expected = { third, second, first, zero };
        Movie[] actual = afishaManagerCustom.findAll();

        assertArrayEquals(actual, expected);
    }

    @Test   //Тест на вывод заданного количества добавленных фильмов
    void shouldFindAllNominalAfishaManagerCustom() {
        afishaManagerCustom.saveMovie(zero);
        afishaManagerCustom.saveMovie(first);
        afishaManagerCustom.saveMovie(second);
        afishaManagerCustom.saveMovie(third);
        afishaManagerCustom.saveMovie(fourth);
        afishaManagerCustom.saveMovie(fifth);
        afishaManagerCustom.saveMovie(sixth);
        afishaManagerCustom.saveMovie(seventh);
        afishaManagerCustom.saveMovie(eighth);
        afishaManagerCustom.saveMovie(ninth);
        afishaManagerCustom.saveMovie(tenth);
        afishaManagerCustom.saveMovie(eleventh);

        Movie[] expected = { eleventh, tenth, ninth, eighth, seventh };
        Movie[] actual = afishaManagerCustom.findAll();

        assertArrayEquals(actual, expected);
    }

    @Test   //Тест на вывод заданного количества добавленных фильмов, при негативном заданном значении
    void shouldFindAllAfishaManagerCustomNegative() {
        afishaManagerCustomNegative.saveMovie(zero);
        afishaManagerCustomNegative.saveMovie(first);
        afishaManagerCustomNegative.saveMovie(second);
        afishaManagerCustomNegative.saveMovie(third);

        Movie[] expected = { third, second, first, zero };
        Movie[] actual = afishaManagerCustomNegative.findAll();

        assertArrayEquals(actual, expected);
    }
}