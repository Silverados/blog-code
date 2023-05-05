package silverados.github.io.netty.echo;


import io.netty.channel.*;

@ChannelHandler.Sharable
public class EchoServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String response;
        boolean close = false;
        if ("bye".equalsIgnoreCase(msg)) {
            response = "Bye!!!";
            close = true;
        } else {
            response = msg;
        }

        ChannelFuture future = ctx.write(response + "\r\n");
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}