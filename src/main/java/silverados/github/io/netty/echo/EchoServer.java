package silverados.github.io.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.ssl.SslContext;
import silverados.github.io.netty.NettyEventLoopFactory;
import silverados.github.io.netty.NettySslUtil;

public class EchoServer {
    public static final int PORT = 8888;

    public static void main(String[] args) {
        EventLoopGroup bossGroup = NettyEventLoopFactory.newEventLoopGroup(1);
        EventLoopGroup workerGroup = NettyEventLoopFactory.newEventLoopGroup();
        try {
            final SslContext sslContext = NettySslUtil.buildSslServerContext();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NettyEventLoopFactory.serverSocketChannelClass())
                           .option(ChannelOption.SO_BACKLOG, 1024)
                           .childHandler(new EchoServerInitializer(sslContext));
            serverBootstrap.bind(PORT).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}