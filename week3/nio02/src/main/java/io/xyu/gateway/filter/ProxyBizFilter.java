 package io.xyu.gateway.filter;

 import io.netty.buffer.Unpooled;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.handler.codec.http.DefaultHttpHeaders;
 import io.netty.handler.codec.http.FullHttpRequest;
 import io.netty.handler.codec.http.HttpHeaders;
 import io.netty.handler.codec.http.FullHttpResponse;
 import io.netty.handler.codec.http.DefaultFullHttpResponse;
 import io.netty.channel.ChannelFutureListener;

 import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
 import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
 import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
 import static io.netty.handler.codec.http.HttpResponseStatus.OK;
 import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import io.netty.handler.codec.http.HttpUtil;


 public class ProxyBizFilter implements HttpRequestFilter {


     public static ProxyBizFilter newInstance() {
         return new ProxyBizFilter();
     }

     @Override
     public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx)  {
         String uri = fullRequest.uri();
         System.out.println(" filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx)接收到的请求,url: " + uri);
         FullHttpResponse response = null;

         String value = "init"; // "hello,kimmking"; // 对接上次作业的httpclient或者okhttp请求另一个url的响应数据
         
         if (uri.startsWith("/hello")) {
        	 CloseableHttpClient helloHttpClient = HttpClients.createDefault();
             try {
                 HttpGet request = new HttpGet("http://localhost:8088");
                 CloseableHttpResponse helloResponse = helloHttpClient.execute(request);

                 try {
                     // Get HttpResponse Status
                     HttpEntity entity = helloResponse.getEntity();
                     if (entity != null) {
                         // return it as a String
                    	 value = EntityUtils.toString(entity);
                     }

                 } finally {
                	 helloResponse.close();
                 }
            	 helloHttpClient.close();
             } catch(Exception e){
            	 System.out.println("exception!" + e.getMessage());
             }
             value = "filter-> hello";
         }   else if (uri.startsWith("/api-test")) {
             value = "filter-> api-test";
         }else {
             throw new RuntimeException("不支持的uri:" + uri);
         }
         HttpHeaders headers = fullRequest.headers();
         if (headers == null) {
             headers = new DefaultHttpHeaders();
         }
         headers.add("proxy-tag", this.getClass().getSimpleName());
         
         try {
             response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
             response.headers().set("Content-Type", "application/json");
             response.headers().setInt("Content-Length", response.content().readableBytes());
         } catch (Exception e) {
             System.out.println("处理出错:"+e.getMessage());
             response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
         } finally {
             if (fullRequest != null) {
                 if (!HttpUtil.isKeepAlive(fullRequest)) {
                     ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                 } else {
                     response.headers().set(CONNECTION, KEEP_ALIVE);
                     ctx.write(response);
                 }
             }
         }
     }
 }