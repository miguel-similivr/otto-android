package com.github.pires.obd.reader.io;

import android.util.Log;

import com.google.gson.Gson;
import com.udinic.accounts_authenticator_example.authentication.ParseComServerAuthenticate;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpfte_000 on 2018-03-03.
 */

public class HttpObd {
    public String sendCode(String enginecode, Double data, int car_id, String token) throws Exception {

        Log.d("Otto", "userSignIn");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url ="http://ottorepairs.com/api/datalog";

        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.addHeader("Authorization", "Bearer " + token);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("enginecode", enginecode));
        params.add(new BasicNameValuePair("data", data.toString()));
        params.add(new BasicNameValuePair("car_id", String.valueOf(car_id)));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        String authtoken = null;
        try {
            HttpResponse response = httpClient.execute(httpPost);

            String responseString = EntityUtils.toString(response.getEntity());
            Log.d("Response:", responseString);
            if (response.getStatusLine().getStatusCode() != 200) {
                ParseComServerAuthenticate.ParseComError error = new Gson().fromJson(responseString, ParseComServerAuthenticate.ParseComError.class);
                throw new Exception("Error signing-in ["+error+"]");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return authtoken;
    }
}
