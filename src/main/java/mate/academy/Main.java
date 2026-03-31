package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.Movie;
import mate.academy.service.MovieService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String MOVIE_TITLE_EXAMPLE = "A Knight of the Seven Kingdoms";
    private static final String UPDATE_MOVIE_TITLE_EXAMPLE = "A Knight of the Eight Kingdoms";
    private static final String MOVIE_DESCRIPTION_EXAMPLE = """
            A century before GOT, Ser Duncan the Tall, and his squire, Egg,
            wandered through Westeros while the Targaryen dynasty ruled the Iron Throne,
            and dragons were still remembered.
            Great destinies and enemies await the incomparable friends.""";

    public static void main(String[] args) {
        MovieService service = (MovieService) injector.getInstance(MovieService.class);

        Movie movie = new Movie();
        movie.setTitle(MOVIE_TITLE_EXAMPLE);
        movie.setDescription(MOVIE_DESCRIPTION_EXAMPLE);

        Movie movieAdd = service.add(movie);
        System.out.println(movieAdd + System.lineSeparator());

        Movie movieGet = service.get(movie.getId());
        System.out.println(movieGet + System.lineSeparator());

        movie.setTitle(UPDATE_MOVIE_TITLE_EXAMPLE);
        Movie movieUpdate = service.update(movie);
        System.out.println(movieUpdate + System.lineSeparator());

        boolean movieDelete = service.delete(movie.getId());
        System.out.println(movieDelete);
    }
}
