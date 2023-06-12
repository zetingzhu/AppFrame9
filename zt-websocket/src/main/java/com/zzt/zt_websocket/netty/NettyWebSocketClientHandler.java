package com.zzt.zt_websocket.netty;

import android.util.Log;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: zeting
 * @date: 2023/4/17
 */

public class NettyWebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    private static final String TAG = "test-socket -  netty";

    private final WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;

    public NettyWebSocketClientHandler(final WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        handshaker.handshake(ctx.channel());
        Log.d(TAG, ">>> channelActive 连接成功 ");
    }


    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        //System.out.println("WebSocket Client disconnected!");
        Log.d(TAG, ">>> channelInactive 连接失败 ");

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        final Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            // web socket client connected
            handshaker.finishHandshake(ch, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
            return;
        }

        if (msg instanceof FullHttpResponse) {
            final FullHttpResponse response = (FullHttpResponse) msg;
            throw new Exception("Unexpected FullHttpResponse (getStatus=" + response.getStatus() + ", content="
                    + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        final WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            final TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            Log.d(TAG, ">>> channelRead0 TextWebSocketFrame :" + textFrame.text());
        } else if (frame instanceof PongWebSocketFrame) {
            Log.d(TAG, ">>> channelRead0 PongWebSocketFrame :" + frame);
        } else if (frame instanceof CloseWebSocketFrame) {
            Log.d(TAG, ">>> channelRead0 CloseWebSocketFrame :" + frame);
            ch.close();
        } else if (frame instanceof BinaryWebSocketFrame) {
            Log.d(TAG, ">>> channelRead0 BinaryWebSocketFrame :" + frame.toString());
        } else {
            Log.d(TAG, ">>> channelRead0   :" + frame.getClass().getSimpleName());
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        Log.e(TAG, ">>> exceptionCaught 断开");
        cause.printStackTrace();

        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }

        ctx.close();
    }
}