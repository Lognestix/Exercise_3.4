package ru.netology.repository;

import org.junit.jupiter.api.Test;
import ru.netology.domain.Movie;

import static org.junit.jupiter.api.Assertions.*;

class AfishaRepositoryTest {

    //Общие данные:
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

    AfishaRepository repo = new AfishaRepository();

    @Test   //Тест на сохранение и поиск фильма в репозитории
    void shouldSaveMovieAndFindByIdMovie() {
        repo.saveMovie(zero);
        repo.saveMovie(first);

        Movie[] expectedExistingIdMovie = { zero };
        Movie[] actualExistingIdMovie = repo.findMovieById(0);      //Существующий идентификатор фильма в репозитории
        assertArrayEquals(expectedExistingIdMovie, actualExistingIdMovie, "Существующий idMovie");

        Movie[] expectedNotExistingIdMovie = { null };
        Movie[] actualNotExistingIdMovie = repo.findMovieById(2);   //Не существующий идентификатор фильма в репозитории
        assertArrayEquals(expectedNotExistingIdMovie, actualNotExistingIdMovie, "Не существующий idMovie");
    }

    @Test   //Тест на вывод всех добавленых фильмов и удаление всех добавленых фильмов
    void shouldFindAllAndRemoveAllMovies() {
        repo.saveMovie(zero);
        repo.saveMovie(first);
        repo.saveMovie(second);
        repo.saveMovie(third);
        repo.saveMovie(fourth);
        repo.saveMovie(fifth);
        repo.saveMovie(sixth);
        repo.saveMovie(seventh);
        repo.saveMovie(eighth);
        repo.saveMovie(ninth);
        repo.saveMovie(tenth);
        repo.saveMovie(eleventh);

        Movie[] expectedFindAll = { zero, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh };
        Movie[] actualFindAll = repo.findAll();        //Вывод всех добавленых фильмов из репозитория
        assertArrayEquals(expectedFindAll, actualFindAll, "Вывод всех добавленых фильмов");

        Movie[] expectedRemoveAllMovies = new Movie[0];
        repo.removeAllMovies();                 //Удаление всех добавленых фильмов из репозитория
        Movie[] actualRemoveAllMovies = repo.findAll();
        assertArrayEquals(expectedRemoveAllMovies, actualRemoveAllMovies, "Не существующий idMovie");
    }

    @Test   //Тест на удаление фильма по идентификатору фильма
    void shouldRemoveMovieById() {
        repo.saveMovie(zero);
        repo.saveMovie(first);
        repo.saveMovie(second);

        Movie[] expected = { zero, second };
        repo.removeMovieById(1);
        Movie[] actual = repo.findAll();
        assertArrayEquals(actual, expected);
    }
}