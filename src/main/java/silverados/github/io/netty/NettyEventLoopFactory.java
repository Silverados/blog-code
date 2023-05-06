package silverados.github.io.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.*;
import io.netty.channel.kqueue.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.SystemPropertyUtil;

import java.util.concurrent.ThreadFactory;

public class NettyEventLoopFactory {
    public static final boolean epollEnabled = Epoll.isAvailable();
    public static final boolean kQueueEnabled = KQueue.isAvailable();
    private static final int DEFAULT_EVENT_LOOP_THREADS;

    static {
        DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
    }

    public static EventLoopGroup newEventLoopGroup() {
        return newEventLoopGroup(DEFAULT_EVENT_LOOP_THREADS);
    }

    public static EventLoopGroup newEventLoopGroup(int threads) {
        return newEventLoopGroup(threads, (ThreadFactory) null);
    }

    public static EventLoopGroup newEventLoopGroup(int threads, String threadFactoryName) {
        return newEventLoopGroup(threads, new DefaultThreadFactory(threadFactoryName));
    }

    public static EventLoopGroup newEventLoopGroup(int threads, ThreadFactory threadFactory) {
        if (epollEnabled) {
            return new EpollEventLoopGroup(threads, threadFactory);
        }
        if (kQueueEnabled) {
            return new KQueueEventLoopGroup(threads, threadFactory);
        }
        return new NioEventLoopGroup(threads, threadFactory);
    }

    public static Class<? extends SocketChannel> socketChannelClass() {
        if (epollEnabled) {
            return EpollSocketChannel.class;
        }
        if (kQueueEnabled) {
            return KQueueSocketChannel.class;
        }
        return NioSocketChannel.class;
    }

    public static Class<? extends ServerSocketChannel> serverSocketChannelClass() {
        if (epollEnabled) {
            return EpollServerSocketChannel.class;
        }
        if (kQueueEnabled) {
            return KQueueServerSocketChannel.class;
        }
        return NioServerSocketChannel.class;
    }

    public static Class<? extends DatagramChannel> datagramChannelClass() {
        if (epollEnabled) {
            return EpollDatagramChannel.class;
        }
        if (kQueueEnabled) {
            return KQueueDatagramChannel.class;
        }
        return NioDatagramChannel.class;
    }
}
