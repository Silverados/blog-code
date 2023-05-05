package silverados.github.io.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.ssl.SslContext;
import silverados.github.io.netty.NettyEventLoopFactory;
import silverados.github.io.netty.NettySslUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EchoClient {
    public static String HOST = "localhost";
    public static int PORT = EchoServer.PORT;
    public static void main(String[] args) {
        EventLoopGroup group = NettyEventLoopFactory.newEventLoopGroup();
        try {
            final SslContext sslContext = NettySslUtil.buildSslClientContext();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NettyEventLoopFactory.socketChannelClass())
                     .option(ChannelOption.TCP_NODELAY, true)
                     .handler(new EchoClientInitializer(sslContext));

            ChannelFuture lastFuture = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Channel channel = bootstrap.connect(HOST, PORT).sync().channel();
            for(;;) {
                String line = reader.readLine();
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
        } finally {
            group.shutdownGracefully();
        }
    }
}