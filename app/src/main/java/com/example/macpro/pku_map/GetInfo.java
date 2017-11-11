package com.example.macpro.pku_map;


import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

public class GetInfo{

    //post请求
    protected void loginByAsyncHttpClientPost(String userName, String userPass) {
        //创建异步请求对象
        AsyncHttpClient client = new AsyncHttpClient();
        //输入要请求的url
        String url = "http://120.25.232.47:8001/login/";
        //String url = "http://www.baidu.com";
        //请求的参数对象
        RequestParams params = new RequestParams();
        //将参数加入到参数对象中
        params.put("username",userName);
        params.put("userpass",userPass);
        //进行post请求
        client.post(url, params, new AsyncHttpResponseHandler() {
            //如果成功
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                //i代表状态码
                if (i == 200){
                    tv_result.setText(new String(bytes));
                }
            }
            //如果失败
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                //打印异常信息
                throwable.printStackTrace();
            }
        });

    }
    //get请求
    protected void loginByAsyncHttpClientGet(String userName, String userPass) {

        AsyncHttpClient client = new AsyncHttpClient();
        //String url = "http://www.baidu.com";
        String url = "http://120.25.232.47:8001/login/";

        RequestParams params =  new RequestParams();
        params.put("username",userName);
        params.put("userpass",userPass);

        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Log.d("请求响应码",i+"");
                for (int ii = 0; ii < headers.length;ii++){
                    Header header = headers[ii];
                    Log.d("values","header name:"+header.getName()+" value:"+header.getValue());
                }
                tv_result.setText(new String(bytes));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

}