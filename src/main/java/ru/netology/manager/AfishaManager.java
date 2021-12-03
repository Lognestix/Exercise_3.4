package ru.netology.manager;

import lombok.NoArgsConstructor;
import ru.netology.domain.Movie;

@NoArgsConstructor
public class AfishaManager {
    private Movie[] movies = new Movie[0];
    private int amountFilms = 10;

    public AfishaManager(int amountFilms) {        //Указание количества выводимых фильмов
        if (amountFilms <= 0) {
            amountFilms = this.amountFilms;
        }
        this.amountFilms = amountFilms;
    }

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

    public Movie[] findAll() {      //Вывести сохраненные результаты в обратном порядке
        //Вывод последних 10 добавленных фильмов, если фильмов меньше 10, то выдаёт столько, сколько есть
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
}
