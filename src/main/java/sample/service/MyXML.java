package sample.service;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

/**
 * Created by as on 12.12.2017.
 */
public class MyXML {
    // CLIENT PRODUCE
    public static String clientProduceXML(int max){

        System.out.println("CLIENT_PRODUCE_XML-------------------");

        Random random = new Random();
        List objList = new ArrayList();
        for (int i = 0; i < max ; i++) {
            objList.add(random.nextInt(10));
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(baos);
        // zamiania listy na xml
        encoder.writeObject(objList);
        encoder.close();

//        System.out.println("1-------------\n"+baos.toString());
        // xml jako string
        String o1= baos.toString();

        System.out.println(o1);

        return o1;
    }

    // SERVER CONSUME
    public static ArrayList<Integer> serverConsumeXML(String xml){

        System.out.println("SERVER_CONSUME_XML-------------------");

        // odczyt z stringa XML listy
        final XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xml.getBytes()));
        ArrayList<Integer> listFromFile = (ArrayList<Integer>) decoder.readObject();
        decoder.close();

        System.out.println(listFromFile);

        return  listFromFile;
    }

    // SERVER PRODUCE
    public static String serverProduceXML(ArrayList<Integer> list){

        System.out.println("SERVER_PRODUCE_XML-------------------");
        // obliczanie sredniej
        OptionalDouble average = list
                .stream()
                .mapToDouble(a -> a)
                .average();

        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        XMLEncoder encoder2 = new XMLEncoder(baos2);
        // zamiania listy na xml
        encoder2.writeObject(average.getAsDouble());
        encoder2.close();

        String o2= baos2.toString();

        System.out.println(o2);

        return o2;

    }

    // CLIENT CONSUME
    public static String clientConsumeXML(String json){

        System.out.println("CLIENT_CONSUME_XML-------------------");

        // odczyt z stringa XML listy
        final XMLDecoder decoder2 = new XMLDecoder(new ByteArrayInputStream(json.getBytes()));
        // can be null?
        Double avg = (Double) decoder2.readObject();
        decoder2.close();

        System.out.println("client avg: "+avg);
        return avg.toString();
    }
}
