package mate.academy.service;

import mate.academy.model.Movie;

public interface MovieService {
    Movie add(Movie movie);

    Movie get(Long id);

    Movie update(Movie movie);

    boolean delete(Long id);
}
