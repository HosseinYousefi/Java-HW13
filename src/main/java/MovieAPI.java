import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;

import static spark.Spark.*;

import java.util.*;

import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

import static com.mongodb.client.model.Filters.*;

public class MovieAPI {

    public static void main(String[] args) {
        final Gson gson = new Gson();
        final Random random = new Random();
        MovieManager movieManager = new MovieManager();

        staticFileLocation("public");

        post("/movie/add", (request, response) -> {
            String json = request.body();
            Movie movie = new Movie();
            movie.fromJson(json);
            int id = random.nextInt(Integer.MAX_VALUE);
            movie.setId(id);
            movieManager.execAddQuery(movie);
            response.status(201); // 201 Created
            return id;
        });

        get("/movies", (request, response) -> {
            List<Movie> movieList = new ArrayList<>();
            movieManager.execFindQuery(null, result -> {
                for (Document doc: result) {
                    Movie movie = new Movie();
                    movie.fromJson(doc.toJson());
                    movie.setId(doc.getInteger("ID"));
                    movieList.add(movie);
                }
                return null;
            });
            if (clientAcceptsHtml(request)) {
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("movies", movieList);
                return render(movieMap, "movies.ftl");
            } else if (clientAcceptsJson(request)) {
                return Movie.makeJsonArrayFromList(movieList).toString();
            }
            return "You can either accept JSON or HTML";
        });


        get("/movie/:id", (request, response) -> {
            int id = Integer.valueOf(request.params(":id"));
            Movie movie = movieManager.findMovieByID(id);

            if (movie == null) {
                response.status(404); // 404 Not found
                return "Movie not found";
            }

            if (clientAcceptsHtml(request)) {
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("movie", movie);
                return render(movieMap, "movie.ftl");
            } else if (clientAcceptsJson(request))
                return movie.toJsonString();

            return "You can either accept JSON or HTML";
        });

        delete("/movie/delete/:id", (request, response) -> {
            int id = Integer.valueOf(request.params(":id"));
            Movie movie = movieManager.findMovieByID(id);
            if (movie == null) {
                response.status(404); // 404 Not found
                return "Movie not found";
            }
            movieManager.removeMovieByID(id);
            return "Movie with id '" + id + "' deleted";
        });

        put("/movie/update/:id", (request, response) -> {
            int id = Integer.valueOf(request.params(":id"));
            Movie movie = movieManager.findMovieByID(id);
            if (movie == null) {
                response.status(404); // 404 Not found
                return "Movie not found";
            }
            String json = request.body();
            movie.fromJson(json);
            movieManager.replaceMovieByID(id, movie);
            return "Movie with id '" + id + "' updated";
        });
    }

    public static String render(Map values, String template) {
        return new FreeMarkerEngine().render(new ModelAndView(values, template));
    }

    public static boolean clientAcceptsHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static boolean clientAcceptsJson(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("application/json");
    }

    public static class Book {

        public String author, title;
        public Integer pages;

        public Book(String author, String title, Integer pages) {
            this.author = author;
            this.title = title;
            this.pages = pages;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getPages() {
            return pages;
        }

        public void setPages(Integer pages) {
            this.pages = pages;
        }
    }
}