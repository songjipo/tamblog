package com.tjck.tamblog.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

public class HttpUtil {
    
    @SuppressWarnings("deprecation")
	public static String postByHttps(String url, String body,String sign, String contentType) throws UnsupportedEncodingException {
        String result = "";
        //Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
        Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", https);
        PostMethod post = new PostMethod(url);
       
        HttpClient client = new HttpClient();
        try {
            System.out.println("url : " + url);
            System.out.println("sign : " + sign);
            System.out.println("body : " + body);
//            post.setRequestEntity(new StringRequestEntity(body,"text/xml","UTF-8"));
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
//            post.setRequestHeader("Content-Type", contentType);
            post.setRequestHeader("TP-SIGN", sign);
            post.setRequestBody(body);
            client.executeMethod(post);
            result = post.getResponseBodyAsString();
            Protocol.unregisterProtocol("https");
            return result;
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
     
        return "error";
    }

}
