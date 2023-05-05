package silverados.github.io.netty.echo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class EchoServerInitializer extends ChannelInitializer<SocketChannel> {
    private static final StringDecoder STRING_DECODER = new StringDecoder();
    private static final StringEncoder STRING_ENCODER = new StringEncoder();
    private static final EchoServerHandler SERVER_HANDLER = new EchoServerHandler();
    private SslContext sslContext;

    public EchoServerInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        if (sslContext != null) {
            p.addLast(sslContext.newHandler(ch.alloc()));
        }

        p.addLast(new LineBasedFrameDecoder(1024));
        p.addLast(STRING_ENCODER);
        p.addLast(STRING_DECODER);
        p.addLast(SERVER_HANDLER);
    }
}