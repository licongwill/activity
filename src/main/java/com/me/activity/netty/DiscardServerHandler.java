package com.me.activity.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @program: activity
 * @description: 接受到的任何信息不进行处理
 * @author: lic
 * @create: 2020-01-13 18:48
 **/
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // Discard the received data silently.
        //((ByteBuf) msg).release(); // (3)
        ByteBuf in = (ByteBuf)msg;
        try{
            while (in.isReadable()){
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        }finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
