package App;

import exception.ConnectionErrorException;
import exception.NotInDeclaredLimitsException;
import interaction.Request;
import interaction.Response;
import utility.Outputer;
import utility.UserHandler;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {
    private String host;
    private int port;
    private int reconnectionTimeOut;
    private int maxReconnectionAttempts;
    private int reconnectionAttempts;
    private DatagramChannel clientChannel;
    private UserHandler userHandler;
    private SocketAddress addr;

    public Client(String host, int port, int reconnectionTimeout,
                  int maxReconnectionAttempts,
                  UserHandler userHandler) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeOut = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
    }
    public void run() throws IOException {
        boolean processingStatus = true;
        while (processingStatus){
            try{
//                checkAddress();
                connectToServer();
                processingStatus = processing(clientChannel,addr);
            }
//            catch (NotInDeclaredLimitsException e) {
//                e.printStackTrace();
//            }
            catch (ConnectionErrorException e) {
                if (reconnectionAttempts >= maxReconnectionAttempts) {
                    Outputer.printerror("Connection attempts exceeded!");
                    break;
                }
                try {
                    Thread.sleep(reconnectionTimeOut);
                } catch (IllegalArgumentException timeoutException) {
                    Outputer.printerror("Connection timeout '" + reconnectionTimeOut +
                            "' is out of range!");
                    Outputer.println("Reconnection will be done immediately.");
                } catch (Exception timeoutException) {
                    Outputer.printerror("An error occurred while trying to wait for a connection!");
                    Outputer.println("Reconnection will be done immediately.");
                }
            }
            reconnectionAttempts++;
        }
        if (clientChannel !=null) clientChannel.close();
        Outputer.println("Client shutdown");
    }
    private void connectToServer() throws ConnectionErrorException {
        try {
            if (reconnectionAttempts >= 1) Outputer.println("Reconnecting to the server...");
            /**Creates a socket address from an IP address and a port number.*/
            addr = new InetSocketAddress(host, port);
            /**Opens a datagram channel.*/
            clientChannel = DatagramChannel.open();
            /**Connects this channel's socket.*/
            clientChannel.connect(addr);
        } catch (IOException exception) {
            Outputer.printerror("An error occurred while connecting to the server!");
            throw new ConnectionErrorException();
        }
    }

    public void checkAddress() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try{
            if (port != 6789) throw new ConnectionErrorException();
            byte[] ipAddr = new byte[]{127,0,0,1};
            //Xac dinh dia chi IP cua may chu luu tru, voi ten cua may chu
            InetAddress IPAddress = InetAddress.getByName(host);
            //getByAddress(byte[] addr) - tra ve 1 doi tuong InetAddress cung cap boi dia chi addr
            if (!IPAddress.equals(InetAddress.getByAddress(ipAddr))) throw new UnknownHostException();
        } catch (ConnectionErrorException e) {
            Outputer.printerror("PORT_NUMBER's incorrect!");
            throw new ConnectionErrorException();
        } catch (UnknownHostException e) {
            Outputer.printerror("HOST_ADDRESS's incorrect!");
            throw new NotInDeclaredLimitsException();
        }
    }

    private boolean processing(DatagramChannel clientChannel,SocketAddress addr){
        Con con =  new Con();
        while(true){
            try{
                con.request = con.response != null ? userHandler.handler(con.response.getResponseCode()):
                        userHandler.handler(null);

                if(con.request.getCommandName().equals("exit")) break;
                if (con.request.isEmpty()) continue;

                send(clientChannel,con,addr);
                receive(clientChannel,con);

                Outputer.println(con.response.getResponseBody());
            } catch (ClassNotFoundException e) {
                Outputer.println("An error occurred while reading the received data!");
            }
        }
        return false;
    }

    private void send(DatagramChannel clientChanel,Con con,SocketAddress addr){
        try{
            // thuc hien 1 luon dau ra trong do du lieu duoc ghi vao 1 mang byte

            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            ObjectOutputStream oss = new ObjectOutputStream(new BufferedOutputStream(baos));

            oss.writeObject(con.request);
            oss.flush();


            //Tao mot mang moi duoc cap phat
            byte[] arr = baos.toByteArray();
            // goi mot mang byte vao bo dem de gui, luu mang byte vao  arr[]
            ByteBuffer buf = ByteBuffer.wrap(arr);
            //Sends a datagram via this channel.
            clientChanel.send(buf,addr);
            oss.close();
        } catch (IOException e) {
            e.printStackTrace();

            Outputer.println("Exception in method send !");
        }

    }

    private void receive(DatagramChannel clientChanel, Con con) throws ClassNotFoundException {
        try{
            /**phan bo bo dem file moi*/
            ByteBuffer buf = ByteBuffer.allocate(32768);
            /*** goi phuong thuc nay truoc khi su dung mot chuoi cac thao tac doc hoac lap day vung dem nay*/
            buf.clear();
            /**Nhan mot datagram thong qua kenh nay*/
            clientChanel.receive(buf);

            byte[] array= buf.array();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array);
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(byteArrayInputStream));
            /**readObject - doc 1 doi tuong tu ObjectInputStream*/
            con.response = (Response) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Con {
        Request request;
        Response response;
    }

}
