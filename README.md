# Настройки добавленные в pom.xml для данного проекта
```xml
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.2</version>
                <configuration>
                    <failIfNoTests>true</failIfNoTests>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.5</version>
                <executions>
                    <execution>
                        <id>agent-Smith</id>
                        <phase>initialize</phase>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>report-agent-Smith</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.8.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-junit-jupiter</artifactId>
            <version>3.6.28</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
# Код Java находящийся в этом репозитории
```Java
package ru.netology.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {
    private int idMovie;
    private String imageUrl;
    private boolean premiereTomorrow;
    private String nameMovie;
    private String genre;
}
```
```Java
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
```
```Java
package ru.netology.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.domain.Movie;
import ru.netology.repository.AfishaRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AfishaManagerTest {

    @Mock
    private AfishaRepository repository;    //Либо, если без аннотации private AfishaRepository repository = Mockito.mock(AfishaRepository.class);

    @InjectMocks
    //private AfishaManager afishaManagerDefault;     //Укороченная запись, если входной параметр только репозиторий
    private AfishaManager afishaManagerDefault = new AfishaManager(repository);
    @InjectMocks
    private AfishaManager afishaManagerCustom = new AfishaManager(repository, 5);
    @InjectMocks
    private AfishaManager afishaManagerCustomNegative = new AfishaManager(repository,-5);

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

    @Test   //Тест стандартного менеджера на вывод последних 10 фильмов
    void shouldAfishaManagerDefaultGetAll() {
        //Настройка заглушки
        Movie[] result = {zero, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh};
        doReturn(result).when(repository).findAll();

        Movie[] actual = afishaManagerDefault.getAll();
        Movie[] expected = {eleventh, tenth, ninth, eighth, seventh, sixth, fifth, fourth, third, second};
        assertArrayEquals(expected, actual, "Вывод 10 последних добавленных фильмов");

        //Проверка вызова заглушки
        verify(repository).findAll();
    }

    @Test   //Тест стандартного менеджера на вывод последних добавленых фильмов, если их меньше 10
    void shouldAfishaManagerDefaultGetAllLess10() {
        //Настройка заглушки
        Movie[] result = {zero, first, second, third, fifth, seventh, ninth, eleventh};
        doReturn(result).when(repository).findAll();

        Movie[] actual = afishaManagerDefault.getAll();
        Movie[] expected = { eleventh, ninth, seventh, fifth, third, second, first, zero };
        assertArrayEquals(expected, actual, "Вывод последних добавленных фильмов, если их меньше 10");

        //Проверка вызова заглушки
        verify(repository).findAll();
    }

    @Test   //Тест менеджера с указанным количеством на вывод последних фильмов
    void shouldAfishaManagerCustomGetAll() {
        //Настройка заглушки
        Movie[] result = { zero, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh };
        doReturn(result).when(repository).findAll();

        Movie[] actualManagerCustom = afishaManagerCustom.getAll();
        Movie[] expectedManagerCustom = { eleventh, tenth, ninth, eighth, seventh };
        assertArrayEquals(expectedManagerCustom, actualManagerCustom, "Вывод указанного числа последних добавленных фильмов");

        //Проверка вызова заглушки
        verify(repository).findAll();
    }

    @Test   //Тест менеджера с указанным количеством на вывод последних добавленых фильмов, если их меньше указанного числа
    void shouldAfishaManagerCustomGetAllLessNumber() {
        //Настройка заглушки
        Movie[] result = { zero, second, third, eleventh };
        doReturn(result).when(repository).findAll();

        Movie[] actualManagerCustomLessCustom = afishaManagerCustom.getAll();
        Movie[] expectedManagerCustomLessCustom = { eleventh, third, second, zero };
        assertArrayEquals(expectedManagerCustomLessCustom, actualManagerCustomLessCustom, "Вывод последних добавленных фильмов, если их меньше указанного числа");

        //Проверка вызова заглушки
        verify(repository).findAll();
    }
    @Test   //Тест менеджера с указанным количеством на вывод последних фильмов - негативный
    void shouldAfishaManagerCustomNegativeGetAll() {
        //Настройка заглушки
        Movie[] result = { zero, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh };
        doReturn(result).when(repository).findAll();

        Movie[] actualManagerCustomNegative = afishaManagerCustomNegative.getAll();
        Movie[] expectedManagerCustomNegative = { eleventh, tenth, ninth, eighth, seventh, sixth, fifth, fourth, third, second };
        assertArrayEquals(expectedManagerCustomNegative, actualManagerCustomNegative, "Вывод стандартного количества последних добавленных фильмов");

        //Проверка вызова заглушки
        verify(repository).findAll();
    }
}
```
```Java
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
```
```Java
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
```