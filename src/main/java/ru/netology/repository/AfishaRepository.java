package ru.netology.repository;

import ru.netology.domain.Movie;

public class AfishaRepository {
    private Movie[] movies = new Movie[0];

    public void saveMovie(Movie movie) {        //Сохранение фильма
        //Создание нового массива размером +1
        int length = movies.length + 1;
        Movie[] tmp = new Movie[length];
        //Копирование элементов
        System.arraycopy(movies, 0, tmp, 0, movies.length);
        //Добавление элемента
        int lastIndex = tmp.length - 1;
        tmp[lastIndex] = movie;
        //Итоговый результат
        movies = tmp;
    }

    public Movie[] findMovieById(int idMovie) {      //Поиск фильма по идентификатору
        //Создание нового массива
        Movie[] tmp = new Movie[1];
        //Копирование найденного фильма
        for (Movie movie : movies) {
            if (movie.getIdMovie() == idMovie) {
                tmp[0] = movie;
            }
        }
        //Итоговый результат
        return tmp;
    }

    public Movie[] findAll() {      //Вывести сохраненные результаты
        //Вывод всех добавленных фильмов
        return movies;
    }

    public void removeMovieById(int idMovie) {      //Удаление фильма по идентификатору
        //Создание нового массива размером -1
        int length = movies.length - 1;
        Movie[] tmp = new Movie[length];
        //Копирование элементов, кроме удаляемого
        int index = 0;
        for (Movie movie : movies) {
            if (movie.getIdMovie() != idMovie) {
                tmp[index] = movie;
                index++;
            }
        }
        //Итоговый результат
        movies = tmp;
    }

    public void removeAllMovies() {       //Удаление всех добавленых фильмов
        //Создание пустого массива
        Movie[] tmp = new Movie[0];
        //Итоговый результат
        movies = tmp;
    }
}
