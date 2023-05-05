package silverados.github.io.netty.echo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class EchoClientInitializer extends ChannelInitializer<SocketChannel> {

    private static final StringDecoder STRING_DECODER = new StringDecoder();
    private static final StringEncoder STRING_ENCODER = new StringEncoder();
    private static final EchoClientHandler CLIENT_HANDLER = new EchoClientHandler();
    private final SslContext sslContext;

    public EchoClientInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        if (sslContext != null) {
            p.addLast(sslContext.newHandler(ch.alloc(), EchoClient.HOST, EchoClient.PORT));
        }

        p.addLast(new LineBasedFrameDecoder(1024));
        p.addLast(STRING_DECODER);
        p.addLast(STRING_ENCODER);

        p.addLast(CLIENT_HANDLER);
    }
}