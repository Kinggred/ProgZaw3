package org.example.http;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class HTTPServer {
    HTTPHandler httpHandler = new HTTPHandler();

    public void start() throws IOException, InvocationTargetException, IllegalAccessException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(8080));
        serverChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        while (true) {
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    accept(selector, serverChannel);
                } else if (key.isReadable()) {
                    read(key, buffer);
                }
            }
        }
    }

    private void accept(Selector selector, ServerSocketChannel serverChannel) throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Accepted new connection");
    }

    private void read(SelectionKey key, ByteBuffer buffer) throws IOException, InvocationTargetException, IllegalAccessException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            clientChannel.close();
            System.out.println("Client disconnected");
            return;
        }

        buffer.flip();
        String request = new String(buffer.array(), 0, buffer.limit());
        String response = httpHandler.dispatch(request);
        clientChannel.write(ByteBuffer.wrap(response.getBytes()));
        clientChannel.close();
        buffer.clear();
    }
}
