package sample.service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by as on 12.12.2017.
 */
public class MyJSON {

    // CLIENT PRODUCE
    public static String clientProduceJSON(int max, String user){

        System.out.println("CLIENT_PRODUCE_JSON-------------------");

        Random random = new Random();
        List objList = new ArrayList();
        for (int i = 0; i < max ; i++) {
            objList.add(random.nextInt(10));
        }

        HashMap objMap = new HashMap();
        objMap.put("user", user);
        objMap.put("table", objList);

        String jobject = new Gson().toJson(objMap);

        // PRETTY JSON
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jobject).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);
        System.out.println("\n"+prettyJson);

        return jobject;
    }

    // SERVER CONSUME
    public static ArrayList<String> serverConsumeJSON(String json){

        System.out.println("SERVER_CONSUME_JSON-------------------");
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        // get OBJECT table
        JsonArray jsonArray = jsonObject.getAsJsonArray("table");
        // SET TYPE
        Type listType = new TypeToken<List<String>>() {}.getType();
        ArrayList<String> yourList = new Gson().fromJson(jsonArray, listType);

        System.out.println(yourList.toString());

        return yourList;

    }

    // SERVER PRODUCE
    public static String serverProduceJSON(ArrayList<String> list){

        System.out.println("SERVER_PRODUCE_JSON-------------------");

        OptionalDouble average = list
                .stream()
                .map(s -> Double.parseDouble(s))
                .mapToDouble(a -> a)
                .average();

//        System.out.println("avg: "+average.getAsDouble());
        HashMap objMap = new HashMap();
        objMap.put("user", "server");
        objMap.put("table_avg", average.getAsDouble());
        String jobject = new Gson().toJson(objMap);

        // PRETTY JSON
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jobject).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);
        System.out.println(prettyJson);

        return jobject;

    }

    // CLIENT CONSUME
    public static String clientConsumeJSON(String json){

        System.out.println("CLIENT_CONSUME_JSON-------------------");

        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        // get OBJECT table
        JsonPrimitive jsonPrimitive = jsonObject.getAsJsonPrimitive("table_avg");
        // SET TYPE
        //Type listType = new TypeToken<List<String>>() {}.getType();
        //ArrayList<String> yourList = new Gson().serverConsumeJSON(jsonPrimitive, listType);
        String avg = jsonPrimitive.getAsString();
        System.out.println("client avg: "+avg);
        return avg;
    }

}
