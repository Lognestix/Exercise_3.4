package ru.netology.manager;

import lombok.NoArgsConstructor;
import ru.netology.domain.Movie;
import ru.netology.repository.AfishaRepository;

@NoArgsConstructor
public class AfishaManager {
    private int amountFilms = 10;
    private AfishaRepository repository;

    public AfishaManager(AfishaRepository repository) { this.repository = repository; }

    //Указание репозитория и количества выводимых фильмов
    public AfishaManager(AfishaRepository repository, int amountFilms) {
        this.repository = repository;
        if (amountFilms <= 0) {
            amountFilms = this.amountFilms;
        }
        this.amountFilms = amountFilms;
    }

    //Добавление фильма в репозиторий
    public void addMovie(Movie movie) { repository.saveMovie(movie); }

    //Поиск фильма в репозитории по идентификатору фильма
    public Movie[] findByIdMovie(int idMovie) {
        Movie[] result = repository.findMovieById(idMovie);
        return result;
    }

    //Вывод всех добавленых фильмов в обратном порядке
    public Movie[] getAll() {
        Movie[] movies = repository.findAll();
        int resultLength;
        if (amountFilms <= movies.length) {
            resultLength = amountFilms;
        } else {
            resultLength = movies.length;
        }
        Movie[] result = new Movie[resultLength];
        for (int cycle = 0; cycle < result.length; cycle++) {
            int index = movies.length - cycle - 1;
            result[cycle] = movies[index];
        }
        return result;
    }

    //Удаление фильма из репозитория по идентификатору фильма
    public void removeByIdMovie(int idMovie) { repository.removeMovieById(idMovie); }

    //Удаление всех добавленных фильмов из репозитория
    public void removeAllMovies() { repository.removeAllMovies(); }
}
