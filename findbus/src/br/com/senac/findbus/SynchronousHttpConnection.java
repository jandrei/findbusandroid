package br.com.senac.findbus;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SynchronousHttpConnection {
	 
    public static final int DID_START = 0;
    public static final int DID_ERROR = 1;
    public static final int DID_SUCCEED = 2;
 
    private static final int GET = 0;
    private static final int POST = 1;
    private static final int PUT = 2;
    private static final int DELETE = 3;
 
    public SynchronousHttpConnection() {                
    }
 
    public String get(String url) throws IllegalStateException, IOException {
        return executeHTTPConnection(GET, url, null);
    }
 
    public String post(String url, String data) throws IllegalStateException, IOException {
        return executeHTTPConnection(POST, url, data);
    }
 
    public String put(String url, String data) throws IllegalStateException, IOException {
        return executeHTTPConnection(PUT, url, data);
    }
 
    public String delete(String url) throws IllegalStateException, IOException {
        return executeHTTPConnection(DELETE, url, null);
    }
 
    public Bitmap bitmap(String url) throws IllegalStateException, IOException {
        return executeHTTPConnectionBitmap(url);
    }
 
 
    private String executeHTTPConnection(int method, String url, String data) throws IllegalStateException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 25000);
        HttpResponse response = null;
        switch (method) {
        case GET:
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            break;
        case POST:
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(data));
            response = httpClient.execute(httpPost);
            break;
        case PUT:
            HttpPut httpPut = new HttpPut(url);
            httpPut.setEntity(new StringEntity(data));
            response = httpClient.execute(httpPut);
            break;
        case DELETE:
            response = httpClient.execute(new HttpDelete(url));
            break;
        default:
            throw new IllegalArgumentException("Unknown Request.");
        }  
 
        return processResponse(response.getEntity());
 
    }
 
    private Bitmap executeHTTPConnectionBitmap(String url) throws IllegalStateException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 25000);
        HttpResponse response = httpClient.execute(new HttpGet(url));       
        return processBitmapEntity(response.getEntity());
    }
 
    private String processResponse(HttpEntity entity) throws IllegalStateException,IOException {
                String jsonText = EntityUtils.toString(entity, HTTP.UTF_8);
        return jsonText;
    }
 
    private Bitmap processBitmapEntity(HttpEntity entity) throws IOException {
        BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
        Bitmap bm = BitmapFactory.decodeStream(bufHttpEntity.getContent());
        return bm;
    }
 
}