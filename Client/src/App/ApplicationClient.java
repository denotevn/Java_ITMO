package App;

import exception.NotInDeclaredLimitsException;
import exception.WrongFormatCommandException;
import utility.Outputer;
import utility.UserHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ApplicationClient {
    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";

    private static final int RECONNECTION_TIMEOUT = 5 * 1000;
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;

    private static String host = "127.0.0.1";
    //    = "127.0.0.1";
    private static int port = 6789;
    //    = ;
    private static boolean initializeConnectionAddress(String []hostAndPortArgs){
        try{
            if (hostAndPortArgs.length !=2) throw new WrongFormatCommandException();
            host = hostAndPortArgs[0];
            port = Integer.parseInt(hostAndPortArgs[1]);
            if (port < 0) throw new NotInDeclaredLimitsException();
            return true;
        } catch (WrongFormatCommandException e) {
            String jarName = new java.io.File(ApplicationClient.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            Outputer.println("Using: 'java -jar " + jarName + " <host> <port>'");
        } catch (NotInDeclaredLimitsException e) {
            Outputer.printerror("Port must be a number!");
        }catch (NumberFormatException e)
        {
            Outputer.printerror("Port can't be negative");
        }
        return false;
    }
    public static void main(String[] args) throws IOException {
        Outputer.println("Chao mung da den voi app cua ta !");
//Run in IDE
//        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Enter the host and port: ");
//        String host1 = buf.readLine();
//        String[] hostAndPort = (host1.trim() + " ").split(" ", 2);
//        hostAndPort[0] = hostAndPort[0].trim();
//        hostAndPort[1] = hostAndPort[1].trim();
//
//        if (!initializeConnectionAddress(hostAndPort)) return;

//        if (!initializeConnectionAddress(args)) return;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        UserHandler userHandler = new UserHandler(reader);
//        Client client = new Client(hostAndPort[0], port, RECONNECTION_TIMEOUT,MAX_RECONNECTION_ATTEMPTS, userHandler);
        App.Client client = new App.Client(host,port,RECONNECTION_TIMEOUT,MAX_RECONNECTION_ATTEMPTS,userHandler);
        client.run();
        reader.close();
    }
}