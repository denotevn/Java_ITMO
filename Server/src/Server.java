import commands.Commands;
import commands.ExitCommand;
import commands.SaveCommand;
import interaction.Request;
import interaction.Response;
import utility.CollectionManager;
import utility.Outputer;
import utility.RequestHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class Server {
    private final int port;
    private final int soTimeOut;
    private final RequestHandler requestHandler;
    private DatagramChannel channel;
    private final CollectionManager collectionManager;
    private final String fileName;


    public Server(int port, int soTimeOut, RequestHandler requestHandler,
                  CollectionManager collectionManager, String fileName) {
        this.port = port;
        this.soTimeOut = soTimeOut;
        this.requestHandler = requestHandler;
        this.collectionManager = collectionManager;
        this.fileName = fileName;
    }

    public void run(){
        try{
            boolean processingStatus = true;
            while (processingStatus){
                processingStatus = processing();
            }
            if (channel != null) channel.close();
            Outputer.println("Server shutdown.");
        } catch (IOException e) {
            Outputer.printerror("An error occurred while trying to end the connection to the client!");
            ServerApplication.LOGGER.warning("An error occurred while trying to end the connection to the client!");
        }
    }
    /** InetSocketAddress(port)
     * // tao dia chi o cam trong do dia cho IP la dia cho ki tu dai dien va so cong la mot gia tri duoc chi
     * InetSocketAddress(InetAddress addr, int port)
     * tao dia chi o cam tu dia chi IP va so cong
     * InetSocketAddress(String hostname, int port)
     * tao dia chi o cam tu len may chu va so cong
     * */
    public boolean processing(){
        Con con = new Con();
        try{
            ServerApplication.LOGGER.info("Server starting on port " + port);

            channel = DatagramChannel.open(); // Mo kenh datagram
            channel.socket().bind(new InetSocketAddress(port)); // lien ket kenh da mo voi dia chi tao tu o cam da cho
            /**
             * configureBlocking(boolean block) throws IOException
             * block - true neu kenh nay se duoc dat o che do chan
             * block - false neu kenh duoc dat o che do khong chan
             * */
            channel.configureBlocking(false);
/**dang ky kenh co the lua chon*/
            Selector selector = Selector.open();
            SelectionKey cliKey = channel.register(selector, SelectionKey.OP_READ);
            /** attach(Object ob) - Dinh kem doi tuong da cho vao khoa nay*/
            cliKey.attach(con);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(channel.isOpen()){
                try{
                    if (reader.ready()){// da san sang doc hay chua
                        String line = reader.readLine();
                        if ("exit".equalsIgnoreCase(line)){

                            Commands exit = new ExitCommand();
                            exit.executed("",null);
                            ServerApplication.LOGGER.info("Saved collection.");
                            ServerApplication.LOGGER.info("Server shutdown");
                            break;
                        }

                        if ("save".equalsIgnoreCase(line)){
                            Commands save = new SaveCommand(collectionManager);
                            save.executed("",null);
                            ServerApplication.LOGGER.info("Saved collection!");
                        }
                    }

                    if (selector.selectNow() != 0){ /**neu ít nhat 1 kenh san sang cho 1 hoat đong*/
                    /**
                     * truy xuat cac tap hop ca khoa da chon de xu li
                     * iter la ca doi tuong SelectionKey moi khoa kenh da dang ky san sang cho mot hoat dong
                     * */
                        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

                        while (iter.hasNext()){
                            try{
                                SelectionKey key = iter.next(); //Returns the channel for which this key was created
                                iter.remove();
                                if (!key.isValid()) continue;
                                /**
                                 * 	isReadable()
                                 * 	Kiem tra xem kenh cua khoa nay da san sang doc hay chua
                                 * */
                                if (key.isReadable()){
                                    read(key);
                                    key.interestOps(SelectionKey.OP_WRITE);
                                }
                                /**
                                 * 	isWritable()
                                 * 	kiem tra xem key cua khoa nay da san sang de ghi hay chua
                                 * */
                                if (key.isWritable()) {
                                    write(key);
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            selector.close();
            return false;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (con.request == null) {
                Outputer.printerror("Unexpected disconnection from the client!");
                ServerApplication.LOGGER.severe("Непредвиденный разрыв соединения с клиентом!");
            } else {
                Outputer.println("Клиент успешно отключен от сервера!");
                ServerApplication.LOGGER.info("Клиент успешно отключен от сервера!");
            }
        }
        return true;
    }

    public void read(SelectionKey key) throws IOException, ClassNotFoundException {
        /**
         * key.channel()
         * Tra ve kenh ma khoa nay duoc tao
         * */
        DatagramChannel channel = (DatagramChannel)key.channel();
        // truy xuat tep dinh kem hien tai
        Con con = (Con)key.attachment();



        ByteBuffer buf = ByteBuffer.allocate(2048);
        con.sa = channel.receive(buf);

        byte[] arr = buf.array();
        ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        ObjectInputStream  ois = new ObjectInputStream(bais);

        con.request = (Request) ois.readObject(); // doc mot doi tuong tu inputstream
        con.response = requestHandler.handle(con.request);

        System.out.println(con.request);

        ServerApplication.LOGGER.info("Processing request " + con.request.getCommandName() + "...");
        ois.close();
    }
    private void write(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        Con con = (Con) key.attachment();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));

        oos.writeObject(con.response);
        oos.flush();

        byte[] arr = baos.toByteArray();
        ByteBuffer buf = ByteBuffer.wrap(arr);
        channel.send(buf, con.sa);
        ServerApplication.LOGGER.info("Sending a response...");

        System.out.println(con.response);

        oos.close();
    }
    static class Con {
        Request request;
        Response response;
        SocketAddress sa;
    }
}
