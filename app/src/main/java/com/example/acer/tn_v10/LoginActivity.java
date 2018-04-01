package com.example.acer.tn_v10;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.acer.tn_v10.R.id.info;

@SuppressWarnings("deprecation")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText et_Secret;
    public EditText et_appId;
    public Editable psw;
    public TextView tv_login;
    public Context mcontext;

    private String baseURL = " http://http://192.168.179.2:8080/NA-4.0/servlet/WelcomeUserServlet";
    public String id;
    public String pw;
    public static final int USER_LOGIN = 1;
    private static Handler handler = new Handler();
    // 创建等待框
    private ProgressDialog dialog;
    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mcontext = this;
        //get the contents
        Button btn_login = (Button) findViewById(R.id.btn_login);
        et_appId = (EditText) findViewById(R.id.et_appId);
        et_Secret = (EditText) findViewById(R.id.et_Secret);

        //get the content from the EditText
//        id = et_appId.getText();
//        psw = et_Secret.getText();

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
// 提示框
                dialog = new ProgressDialog(this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                new Thread(new MyThread()).start();

        }
    }
    public class MyThread implements Runnable {
        @Override
        public void run() {
            id = et_appId.getText().toString();
            pw = et_Secret.getText().toString();
            info = WebService.executeHttpPost(id, pw);
            // info = WebServicePost.executeHttpPost(username.getText().toString(), password.getText().toString());
            handler.post(new Runnable() {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                public void run() {
                    dialog.dismiss();

                  if(id.equals("admin")&& pw.equals("admin")){
                        startActivity(intent);
                    }else if(info.equals("truesb")){
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"AppId或者Secrent输入错误...",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}