package sample.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class MyServer {

    public static void main(String args[]) throws Exception {

        ServerSocket ss = new ServerSocket(3333);

        while (true) {
            Socket s = ss.accept();
            new Thread() {
                @Override
                public void run() {

                    DataInputStream din = null;
                    try {
                        din = new DataInputStream(s.getInputStream());
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

                        String clientInput="";
                        //while ((clientInput = din.readUTF()) != null) {
                        while (!clientInput.equals("0")) {

                            clientInput = din.readUTF();

                            System.out.println("DATA FROM CLIENT:\n" + clientInput);

                            char ch = clientInput.charAt(0);
                            String data_send = null;

                            if (ch == '<') { // XML
                                data_send = MyXML.serverProduceXML(MyXML.serverConsumeXML(clientInput));
                            } else if (ch == '{') { // JSON
                                data_send = MyJSON.serverProduceJSON(MyJSON.serverConsumeJSON(clientInput));
                            } else if (ch == '[') { // DEFAULT
                                data_send = MyDefault.consume(clientInput);
                            } else if (clientInput.equals("0")){
                                data_send = clientInput;
                            }

                            dout.writeUTF(data_send);
                            dout.flush();
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            din.close();
                            s.close();
                            ss.close();
                        } catch (IOException ex) {
                            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

            }.start();
        }
    }

}
