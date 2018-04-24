package com.sourcey.materiallogindemo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jocey on 4/22/18.
 */

public class MovieList {
    public static MovieList get() {

        return new MovieList();
    }

    private MovieList() {
    }

    public List<Image> getData() {
        return Arrays.asList(
                new Image(R.drawable.movie1),
                new Image(R.drawable.movie2),
                new Image(R.drawable.movie3),
                new Image(R.drawable.movie4)
                );


    }
}
