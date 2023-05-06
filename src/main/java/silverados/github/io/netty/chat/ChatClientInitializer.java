package silverados.github.io.netty.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {
    private static final StringEncoder ENCODER = new StringEncoder();
    private static final StringDecoder DECODER = new StringDecoder();
    private static final ChatClientHandler CLIENT_HANDLER = new ChatClientHandler();

    final SslContext sslContext;

    public ChatClientInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        if (sslContext != null) {
            p.addLast(sslContext.newHandler(ch.alloc()));
        }

        p.addLast(new LineBasedFrameDecoder(4096));
        p.addLast(ENCODER);
        p.addLast(DECODER);
        p.addLast(CLIENT_HANDLER);
    }
}
