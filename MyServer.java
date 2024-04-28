package test;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer
{
    private int port;
    private ClientHandler clientHandler;
    public static boolean stop;


    public MyServer(int port, ClientHandler clientHandler)
    {
        this.port = port;
        this.clientHandler = clientHandler;
        stop = false;
    }

    public void start()
    {
        new Thread(() -> // running in background.
        {
            try
            {
                runServer();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public void runServer() throws IOException
    {
        ServerSocket server = new ServerSocket(port);
        server.setSoTimeout(1000);// every second you can connect
        while (!stop)
        {
            try
            {
                Socket client = server.accept();
                try
                {
                    clientHandler.handleClient(client.getInputStream(),client.getOutputStream());
                    client.getInputStream().close();
                    //client.getOutputStream().close();
                    client.close();
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        server.close();
    }


    public void close()
    {
        stop = true;
    }
}