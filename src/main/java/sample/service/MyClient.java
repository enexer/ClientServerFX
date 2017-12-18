package sample.service;

import java.io.*;
import java.net.Socket;

public class MyClient implements Runnable {

    public static CallBackInterface callback;

    private String host;
    private int port;
    private String dataType;

    public MyClient(CallBackInterface callback, String host, int port, String dataType) {
        this.callback = callback;
        this.host = host;
        this.port = port;
        this.dataType = dataType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void starttClient() throws IOException {

        new Thread() {
            @Override
            public void run() {
//                //Socket s = new Socket("localhost", 3333);
//                Socket s = null;
//                try {
//                    s = new Socket(host, port);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                DataInputStream din = null;
//                try {
//                    din = new DataInputStream(s.getInputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                DataOutputStream dout = null;
//                try {
//                    dout = new DataOutputStream(s.getOutputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//                String str = dataType, str2 = "ok";
//                while (!str.equals("0")) {
//
//                    //System.out.println("TYP: ? 1-json,2-xml,3-default");
//                    // dane wejsciowe
//                    //str = "1";//br.readLine();
//
//                    String data_send = null;
//
//                    if (str.equals("1")) {
//                        data_send = MyJSON.clientProduceJSON(5, "as");
//                    } else if (str.equals("2")) {
//                        data_send = MyXML.clientProduceXML(5);
//                    } else if (str.equals("3")) {
//                        data_send = MyDefault.produce();
//                    } else {
//                        System.out.println("zly numer");
//                    }
//
//                    // wyslanie danych do serwera
////            String data_send = MyXML.clientProduceXML(5);
////            String data_send = MyDefault.produce();
////            String data_send = MyJSON.clientProduceJSON(5,"as");
//
//                    try {
//                        MyClient.callback.updateViewProduce(data_send);
//                        dout.writeUTF(data_send);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        dout.flush();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    // pobranie danych
//                    try {
//                        str2 = din.readUTF();
//                        MyClient.callback.updateViewConsume(str2);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    // sprawdzenie jakiego typu otrzymano dane
//                    char ch = str2.charAt(0);
//                    String result = null;
//
//                    if (ch == '<') { // XML
//                        result = MyXML.clientConsumeXML(str2);
//                    } else if (ch == '{') { // JSON
//                        result = MyJSON.clientConsumeJSON(str2);
//                    } else { // DEFAULT
//                        result = str2;
//                    }
//
//                    //            String result = MyXML.clientConsumeXML(str2);
//                    //            String result = MyJSON.clientConsumeJSON(str2);
//                    System.out.println("DATA FROM SERVER: " + result + "\n" + str2);
//                    MyClient.callback.updateView(result);
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                try {
//                    dout.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    s.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }.start();
    }


    @Override
    public void run() {
        Socket s = null;
        try {
            s = new Socket(host, port);
            DataInputStream din = null;
            din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = null;
            dout = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String str2 = "ok";

            while (!dataType.equals("0")) {

                //System.out.println("TYP: ? 1-json,2-xml,3-default");
                // dane wejsciowe
                //str = "1";//br.readLine();

                String data_send = null;

                if (dataType.equals("1")) {
                    data_send = MyJSON.clientProduceJSON(5, "as");
                } else if (dataType.equals("2")) {
                    data_send = MyXML.clientProduceXML(5);
                } else if (dataType.equals("3")) {
                    data_send = MyDefault.produce();
                } else if (dataType.equals("0")) {
                    data_send = dataType;
                } else {
                    System.out.println("datatype: " + dataType);
                }

                MyClient.callback.updateViewProduce(data_send);
                dout.writeUTF(data_send);
                dout.flush();


                // pobranie danych
                str2 = din.readUTF();
                MyClient.callback.updateViewConsume(str2);

                // sprawdzenie jakiego typu otrzymano dane
                char ch = str2.charAt(0);
                String result = null;

                if (ch == '<') { // XML
                    result = MyXML.clientConsumeXML(str2);
                } else if (ch == '{') { // JSON
                    result = MyJSON.clientConsumeJSON(str2);
                } else { // DEFAULT
                    result = str2;
                }

                System.out.println("DATA FROM SERVER: " + result + "\n" + str2);
                MyClient.callback.updateView(result);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            dout.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
