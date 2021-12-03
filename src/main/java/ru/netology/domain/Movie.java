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