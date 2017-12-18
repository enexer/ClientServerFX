package sample.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by as on 12.12.2017.
 */
public class MyDefault {

    // CLIENT PRODUCE
    public static String produce(){

        System.out.println("CLIENT_PRODUCE_DEFAULT-------------------");

        ArrayList<Integer> lista = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            lista.add(new Random().nextInt(100));
        }
        String list_out = lista.toString();

        System.out.println(list_out);

        return list_out;
    }

    // SERVER CONSUME
    public static String consume(String list_out) {

        System.out.println("SERVER_CONSUME_DEFAULT-------------------");

        String list_out2 = list_out
                .substring(1, list_out.length() - 1)
                .replaceAll("\\s+", "");

//        System.out.println("" + list_out2);

        ArrayList<String> lista2 = new ArrayList<String>(Arrays.asList(list_out2.split(",")));

//        System.out.println("" + lista2.toString());

        ArrayList<Integer> myList = new ArrayList<>(lista2.size());

        for (int i = 0; i < lista2.size(); i++) {
            myList.add(Integer.parseInt(lista2.get(i)));
        }

        Double average = myList.stream()
                .mapToInt(val -> val)
                .average()
                .getAsDouble();


        System.out.println("avg: "+"["+average+"]");

        return average.toString();
    }

}
