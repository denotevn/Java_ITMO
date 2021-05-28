
package utility;

import server.AppServer;
import server.Server;
import interaction.Request;
import interaction.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.*;

public class ConnectionHandler extends RecursiveAction {
    private Server server;
    private  int port;
    private DatagramChannel channel;
    private CommandManager commandManager;
    private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();


    public ConnectionHandler(Server server, int port, CommandManager commandManager) {
        this.server = server;
        this.port = port;
        this.commandManager = commandManager;
    }

    @Override
    protected void compute() {
        boolean stop = false;
        Con con = new Con();
        /**
         * configureBlocking(boolean block) throws IOException
         * block - true neu kenh nay se duoc dat o che do chan
         * block - false neu kenh duoc dat o che do khong chan
         * */
        try{
            /**can viet logger o doan nay*/
            channel = DatagramChannel.open();
            channel.socket().bind(new InetSocketAddress(port));
            channel.configureBlocking(false);

            Selector selector = Selector.open();
            SelectionKey clikey = channel.register(selector,SelectionKey.OP_READ);
            /** attach(Object ob) - Dinh kem doi tuong da cho vao khoa nay*/
            clikey.attach(con);
            while(channel.isOpen()){
                try{
                    if (selector. selectNow() != 0){
                        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                        while (iter.hasNext()){
                            SelectionKey key = iter.next();
                            iter.remove();

                            if (!key.isValid()) {
                                continue;
                            }

                            if (key.isReadable()) {
                                forkJoinPool.submit(()->{
                                    try {
                                        read(key);
                                    } catch (IOException exception) {
                                        exception.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    key.interestOps(SelectionKey.OP_WRITE);
                                });
                            }
                            if (key.isWritable()) {
                                write(key);
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.err.println("glitch, continuing... " + (e.getMessage() != null ? e.getMessage() : ""));
                }
                selector.close();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (con.request == null) {
                Outputer.printerror("Unexpected disconnection from the client!");
                AppServer.LOGGER.severe("Непредвиденный разрыв соединения с клиентом!");
            } else {
                Outputer.println("Клиент успешно отключен от сервера!");
               AppServer.LOGGER.info("Клиент успешно отключен от сервера!");
            }
        }finally {
            try {
                forkJoinPool.shutdown();
                channel.close();
                Outputer.println("Клиент отключен от сервера.");
                AppServer.LOGGER.info("Клиент отключен от сервера.");
            } catch (IOException exception) {
                Outputer.printerror("Произошла ошибка при попытке завершить соединение с клиентом!");
                AppServer.LOGGER.severe("Произошла ошибка при попытке завершить соединение с клиентом!");
            }
            if (stop) server.stop();
            server.releaseConnection();
        }

    }

    private void read(SelectionKey key) throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        Con con = (Con)key.attachment();

        ByteBuffer buf = ByteBuffer.allocate(2048);
        con.sa = channel.receive(buf);
        // viet nhat ky ow day

        byte[] arr = buf.array();
        ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        ObjectInputStream ois = new ObjectInputStream(bais);

        con.request = (Request) ois.readObject();
        Future<Response> responseFuture = forkJoinPool.submit(new HandlerRequestTask(con.request, commandManager));
        con.response = responseFuture.get();

        //viet nhat ky vao day
        ois.close();
    }

    private void write(SelectionKey key) {
        forkJoinPool.submit(()->{
            try{
                DatagramChannel channel = (DatagramChannel) key.channel();
                Con con = (Con) key.attachment();

                ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));
                oos.writeObject(con.response);
                oos.flush();

                byte[] arr = baos.toByteArray();
                ByteBuffer buf = ByteBuffer.wrap(arr);
                channel.send(buf, con.sa);

                oos.close();
            }catch (IOException e) {
                Outputer.printerror ("An error occurred while sending data to the client!");
                AppServer.LOGGER.severe("An error occurred while sending data to the client!");
            }
        });
    }

    static class Con {
        Request request;
        Response response;
        SocketAddress sa;
    }


}
