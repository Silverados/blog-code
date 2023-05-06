package silverados.github.io.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.ssl.SslContext;
import silverados.github.io.netty.NettyEventLoopFactory;
import silverados.github.io.netty.NettySslUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {
    public static final String IP = "localhost";
    public static final int PORT = ChatServer.PORT;
    public static void main(String[] args) {
        EventLoopGroup group = NettyEventLoopFactory.newEventLoopGroup();
        try {
            final SslContext sslContext = NettySslUtil.buildSslClientContext();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NettyEventLoopFactory.socketChannelClass()).option(ChannelOption.TCP_NODELAY, true);
            bootstrap.handler(new ChatClientInitializer(sslContext));
            Channel channel = bootstrap.connect(IP, PORT).sync().channel();

            ChannelFuture lastFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for(;;) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }

                lastFuture = channel.writeAndFlush(line + "\r\n");

                if ("bye".equalsIgnoreCase(line)) {
                    channel.closeFuture().sync();
                    break;
                }
            }

            if (lastFuture != null) {
                lastFuture.sync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
