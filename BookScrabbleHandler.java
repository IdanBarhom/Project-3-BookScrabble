package test;


import java.io.*;
import java.util.Arrays;


public class BookScrabbleHandler implements ClientHandler {


    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        try {
        BufferedReader readFromUser =new BufferedReader(new InputStreamReader(inFromclient));
        PrintWriter writeToUser =new PrintWriter(outToClient);
        String line;

            while ((line = readFromUser.readLine()) != null) {
                String[] allParts=line.split(",");
                String[] args=new String[allParts.length - 1];
                System.arraycopy(allParts, 1,args, 0, args.length);

                if (allParts[0].equals("Q")) {
                    writeToUser.println((DictionaryManager.get().query(args)));
                    writeToUser.flush();
                } else {
                    writeToUser.println((DictionaryManager.get().challenge(args)));
                    writeToUser.flush();

                }
            }

            //readFromUser.close();
            //writeToUser.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void close()  {
        MyServer.stop = true;
    }
}
