package pnt.com.sddemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnt on 9/20/15.
 */
public class NetWork {
    String mail;
    String pass;

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    protected boolean checkInternetConnect(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }
    protected HttpResponse makeRquest(String url)
            throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(new BasicNameValuePair("user", mail));
        nameValuePairList.add(new BasicNameValuePair("pass", pass));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
        httpPost.setEntity(entity);
        return httpClient.execute(httpPost);
    }

    static InputStream is = null;

    protected String processHTTPResponce(HttpResponse response)
            throws ParseException, IOException{
        String content = "";
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if(statusCode<400){
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                try{
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    content = reader.readLine();
                    is.close();
                } catch (Exception e){
                    //
                }
            }
        }
        Log.d("content", content);
        return content;
    }

    protected int checkAccount(String result){
        int success = 0;
        try{
            JSONObject json = new JSONObject(result);
            if(json.has("success")){
                success = json.getInt("success");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success;
    }


}
