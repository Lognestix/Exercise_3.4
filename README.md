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
```
```Java
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
```