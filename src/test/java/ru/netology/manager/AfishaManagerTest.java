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