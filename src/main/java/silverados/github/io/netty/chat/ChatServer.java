package silverados.github.io.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.ssl.SslContext;
import silverados.github.io.netty.NettyEventLoopFactory;
import silverados.github.io.netty.NettySslUtil;

public class ChatServer {
    public static final int PORT = 8888;

    public static void main(String[] args) {
        EventLoopGroup bossGroup = NettyEventLoopFactory.newEventLoopGroup(1);
        EventLoopGroup workerGroup = NettyEventLoopFactory.newEventLoopGroup();
        try {
            final SslContext sslContext = NettySslUtil.buildSslServerContext();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                           .channel(NettyEventLoopFactory.serverSocketChannelClass())
                           .option(ChannelOption.SO_BACKLOG, 1024)
                           .childHandler(new ChatServerInitializer(sslContext));

            ChannelFuture future = serverBootstrap.bind(PORT).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
