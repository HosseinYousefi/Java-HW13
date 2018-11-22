import com.mongodb.BasicDBObject;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.json.Json;
import javax.json.JsonReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import static com.mongodb.client.model.Filters.*;


public class MovieManager {

    MongoClient mongoClient;
    MongoDatabase movieDatabase;

    MongoCollection collection;
    final String DATABASE_NAME = "MoviesDatabase";
    final String COLLECTION_NAME = "movies";

    public MovieManager(){
        mongoClient = new MongoClient();
        movieDatabase = mongoClient.getDatabase(DATABASE_NAME);
        collection = movieDatabase.getCollection(COLLECTION_NAME);
//        collection.drop();
    }

    public <T> T execFindQuery(Bson query, ResultHandler<T> handler) {
        FindIterable<Document> result;
        if (query != null)
            result = collection.find(query);
        else
            result = collection.find();
        T res = handler.handle(result);
        return res;
    }

    public Movie findMovieByID(int id) {
        Bson query = eq("ID", id);
        Document doc = execFindQuery(query, result -> result.first());
        System.out.println(doc);
        Movie movie = new Movie();
        try {
            movie.fromJson(doc.toJson());
            movie.setId(doc.getInteger("ID"));
        } catch (Exception e) {
            return null;
        }
        return movie;
    }

    public void removeMovieByID(int id) {
        Bson query = eq("ID", id);
        collection.deleteOne(query);
    }

    public void replaceMovieByID(int id, Movie movie) {
        Bson query = eq("ID", id);
        Document doc = Document.parse(movie.toJsonString());
        collection.replaceOne(query, doc);
    }

    public void execAddQuery(Movie movie) {
        Document doc = Document.parse(movie.toJsonString());
        collection.insertOne(doc);
    }

    public MongoIterable<Document> createDB(String[] files) throws Exception {
//        collection.drop(); //TODO

        for (String file: files) {
            String json = MoviesHelper.readJsonFromFile(file);
            Document doc = Document.parse(json);
            collection.insertOne(doc);
        }

        MongoIterable<Document> moviesCollection = collection.find();

        return moviesCollection;
    }

}