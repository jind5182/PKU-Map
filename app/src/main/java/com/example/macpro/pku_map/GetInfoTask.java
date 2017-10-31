package com.example.macpro.pku_map;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class GetInfoTask extends AsyncTask<String,Void,String> {


    TextView displayTxt = null;
    public GetInfoTask(TextView dt) {
        displayTxt = dt;
    }

    @Override
    protected String doInBackground(String...params) {
        //new Thread() {
        //  @Override
        // public void run() {
        //Button login_btn = (Button)findViewById(R.id.login_button);
        // EditText usr_name = (EditText)findViewById(R.id.user_editText);

        // String ur_name = usr_name.getText().toString();

        // EditText Pwd = (EditText)findViewById(R.id.pwd_editText);
        // String pwd = Pwd.getText().toString();




        String path = "http://120.25.232.47:8001/login/";
        JSONObject js = new JSONObject();
        try {
            js.put("username", params[0]);
            js.put("pwd", params[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return JsonPost(path, js);



        //  }


        //}.start();
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        displayTxt.setText(o);
    }

    public static String JsonPost(final String path, final JSONObject json) {
        BufferedReader in = null;
        String result = "";
        OutputStream os = null;

        try {
            URL url = new URL(path);
            String content = String.valueOf(json);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
//            conn.setRequestMethod("GET");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("user-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");


//            conn.connect();


//            if (conn.getResponseCode() == 200) {
//                return "200";
//            } else {
//                return "error";
//            }
            os = conn.getOutputStream();
            os.write(content.getBytes());
            os.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            String line;
            if (conn.getResponseCode() == 200) {
                while ((line = in.readLine()) != null){
                    result += line;
                }
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "error";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "error";
        } catch (java.net.ProtocolException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        } finally {
            try {
                if (os != null) {
                    os.close();
                }

                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        return result;
    }
}