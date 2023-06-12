package com.zzt.zt_websocket.netty;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.zzt.zt_websocket.javaws.JavaWsActivity;
import com.zzt.zt_websocket.R;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NettyActivity extends AppCompatActivity {
    private static final String TAG = "test-socket";

    NettyWebSocketClient myClient;

    public static void start(Context context) {
        Intent starter = new Intent(context, NettyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("netty");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, 100);
        }


        Button viewById = findViewById(R.id.button_netty);
        viewById.setText("跳到 java-websocket");
        findViewById(R.id.button_netty).setOnClickListener(v -> {
            JavaWsActivity.start(this);
        });
        findViewById(R.id.button).setOnClickListener(v -> {
            wsConnect();
//            wsConnect2();
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            if (myClient != null) {
                try {
                    myClient.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.button3).setOnClickListener(v -> {
            if (myClient != null) {
                String sendValidate = "{\"type\":\"connect\",\"p\":{\"t\":1681716116110,\"auth\":\"64800f9f714c5918510dae85694ef4b2\",\"uuid\":\"881174193DA10094\"}}";
                try {
                    myClient.eval(sendValidate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.button4).setOnClickListener(v -> {
            if (myClient != null) {
                String sendValidate = "{\"type\":\"rtc\",\"p\":{\"codes\":\"XTREND|EURUSD,XTREND|ADAUSD,XTREND|AAPL,XTREND|LTCUSD,XTREND|EURUSD,XTREND|XAUUSD,XTREND|GBPUSD\"}}";
                try {
                    myClient.eval(sendValidate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void wsConnect() {
        try {
            String wsUrl = "ws://test-quo-push-ws.yinyu.tech/ws";
            URI uri = new URI(wsUrl);
            myClient = new NettyWebSocketClient(uri);
            myClient.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void wsConnect2() {
        EventLoopGroup group = new NioEventLoopGroup();
        final ClientHandler handler = new ClientHandler();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加一个http的编解码器
                            pipeline.addLast(new HttpClientCodec());
                            // 添加一个用于支持大数据流的支持
                            pipeline.addLast(new ChunkedWriteHandler());
                            // 添加一个聚合器，这个聚合器主要是将HttpMessage聚合成FullHttpRequest/Response
                            pipeline.addLast(new HttpObjectAggregator(1024 * 64));

                            pipeline.addLast(handler);

                        }
                    });

            URI websocketURI = new URI("ws://test-quo-push-ws.yinyu.tech:10002/ws");
            HttpHeaders httpHeaders = new DefaultHttpHeaders();
            //进行握手
            WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI, WebSocketVersion.V13, (String) null, true, httpHeaders);
            final Channel channel = bootstrap.connect(websocketURI.getHost(), websocketURI.getPort()).sync().channel();
            handler.setHandshaker(handshaker);
            handshaker.handshake(channel);
            //阻塞等待是否握手成功
            handler.handshakeFuture().sync();
            System.out.println("握手成功");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            group.shutdownGracefully();
        }

    }


}