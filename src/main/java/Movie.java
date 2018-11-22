import javax.json.*;
import java.io.*;
import java.util.List;
import java.util.Set;

public class Movie implements Jsonable {


    int         id = -1;    final String ID         = "ID";
    String      title;      final String TITLE      = "Title";
    int         year;       final String YEAR       = "Year";
    String      released;   final String RELEASED   = "Released";
    int         runtime;    final String RUNTIME    = "Runtime";
    String[]    genres;     final String GENRES     = "Genres";
    Director    director;   final String DIRECTOR   = "Director";
    Writer[]    writers;    final String WRITERS    = "Writers";
    Actor[]     actors;     final String ACTORS     = "Actors";
    String      plot;       final String PLOT       = "Plot";
    String[]    languages;  final String LANGUAGES  = "Languages";
    String[]    countries;  final String COUNTRIES  = "Countries";
    String      awards;     final String AWARDS     = "Awards";
    String      poster;     final String POSTER     = "Poster";
    Rating[]    ratings;    final String RATINGS    = "Ratings";

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getReleased() {
        return released;
    }

    public int getRuntime() {
        return runtime;
    }

    public String[] getGenres() {
        return genres;
    }

    public Director getDirector() {
        return director;
    }

    public Writer[] getWriters() {
        return writers;
    }

    public Actor[] getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String[] getLanguages() {
        return languages;
    }

    public String[] getCountries() {
        return countries;
    }

    public String getAwards() {
        return awards;
    }

    public Rating[] getRatings() {
        return ratings;
    }

    public String getPoster() {
        return poster;
    }

    public void setId(int id) {
        this.id = id;
    }

    static public JsonArray makeJsonArrayFrom(String[] arr) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (String str: arr)
            arrayBuilder.add(str);
        return arrayBuilder.build();
    }

    static public<T extends Jsonable> JsonArray makeJsonArrayFrom(T[] arr) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (T str: arr)
            arrayBuilder.add(str.toJsonObject());
        return arrayBuilder.build();
    }

    static public<T extends Jsonable> JsonArray makeJsonArrayFromList(List<T> l) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (T str: l)
            arrayBuilder.add(str.toJsonObject());
        return arrayBuilder.build();
    }

    static public JsonArray makeJsonArrayFromStringArray(String[] arr) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (String str: arr)
            arrayBuilder.add(str);
        return arrayBuilder.build();
    }

    public String[] toStringArr(JsonArray json) {
        String[] arr = new String[json.size()];
        for (int i = 0; i < json.size(); ++i)
            arr[i] = json.getString(i);
        return arr;
    }

    public static Movie fromFile(String path) throws Exception {
        String json = MoviesHelper.readJsonFromFile(path);
        Movie movie = new Movie();
        movie.fromJson(json);
        return movie;
    }

    @Override
    public JsonObject toJsonObject() {
        return Json.createObjectBuilder()
                .add(ID, id)
                .add(TITLE, title)
                .add(YEAR, year)
                .add(RELEASED, released)
                .add(RUNTIME, runtime)
                .add(GENRES, makeJsonArrayFromStringArray(genres))
                .add(DIRECTOR, director.toJsonObject())
                .add(WRITERS, makeJsonArrayFrom(writers))
                .add(ACTORS, makeJsonArrayFrom(actors))
                .add(PLOT, plot)
                .add(LANGUAGES, makeJsonArrayFrom(languages))
                .add(COUNTRIES, makeJsonArrayFrom(countries))
                .add(AWARDS, awards)
                .add(POSTER, poster)
                .add(RATINGS, makeJsonArrayFrom(ratings))
                .build();
    }

    @Override
    public String toJsonString() {
        return toJsonObject().toString();
    }

    @Override
    public void fromJson(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jObject = reader.readObject();
        Set<String> keySet = jObject.keySet();

        if (keySet.contains(TITLE))
            this.title = jObject.getString(TITLE);

        if (keySet.contains(YEAR))
            this.year = jObject.getInt(YEAR);

        if (keySet.contains(RELEASED))
            this.released = jObject.getString(RELEASED);

        if (keySet.contains(RUNTIME))
            this.runtime = jObject.getInt(RUNTIME);

        if (keySet.contains(GENRES))
            this.genres = toStringArr(jObject.getJsonArray(GENRES));

        if (keySet.contains(DIRECTOR)) {
            this.director = new Director();
            this.director.fromJson(jObject.getJsonObject(DIRECTOR).toString());
        }

        if (keySet.contains(WRITERS)) {
            JsonArray writersArr = jObject.getJsonArray(WRITERS);
            this.writers = new Writer[writersArr.size()];
            for (int i = 0; i < writersArr.size(); ++i) {
                this.writers[i] = new Writer();
                this.writers[i].fromJson(writersArr.getJsonObject(i).toString());
            }
        }

        if (keySet.contains(ACTORS)) {
            JsonArray actorsArr = jObject.getJsonArray(ACTORS);
            this.actors = new Actor[actorsArr.size()];
            for (int i = 0; i < actorsArr.size(); ++i) {
                this.actors[i] = new Actor();
                this.actors[i].fromJson(actorsArr.getJsonObject(i).toString());
            }
        }

        if (keySet.contains(PLOT))
            this.plot = jObject.getString(PLOT);

        if (keySet.contains(LANGUAGES))
            this.languages = toStringArr(jObject.getJsonArray(LANGUAGES));

        if (keySet.contains(COUNTRIES))
            this.countries = toStringArr(jObject.getJsonArray(COUNTRIES));

        if (keySet.contains(AWARDS))
            this.awards = jObject.getString(AWARDS);

        if (keySet.contains(POSTER))
            this.poster = jObject.getString(POSTER);

        if (keySet.contains(RATINGS)) {
            JsonArray ratingsArr = jObject.getJsonArray(RATINGS);
            this.ratings = new Rating[ratingsArr.size()];
            for (int i = 0; i < ratingsArr.size(); ++i) {
                this.ratings[i] = new Rating();
                this.ratings[i].fromJson(ratingsArr.getJsonObject(i).toString());
            }
        }
    }

}
