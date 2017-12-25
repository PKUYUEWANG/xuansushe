package com.example.gavin.xuansushe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.pku.gavin.util.NetUtil;

/**
 * Created by GAVIN on 2017/12/25.
 */

public class login extends AppCompatActivity implements View.OnClickListener{

    private TextView mName, mStuNum, mSex, mCheck;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sduinformation);
        initView();
    }

    void initView() {
        mName = (TextView) findViewById(R.id.name);
        mStuNum = (TextView) findViewById(R.id.stuNum);
        mSex = (TextView) findViewById(R.id.sex);
        mCheck = (TextView) findViewById(R.id.check_code);
        mBtn=(Button)findViewById(R.id.begin_btn);
        mBtn.setOnClickListener(this);


       User stuid=(User)getApplication();
        String studentid = stuid.getStudentid();

        loginByGet(studentid);


    }

    public void loginByGet(String student) {

        String data = "stuid=" + student;
        final String adrdess = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?" + data;
        Log.d("select_begin", adrdess);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(adrdess);

                    if ("https".equalsIgnoreCase(url.getProtocol())) {
                        NetUtil.ignoreSsl();
                    }
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(8000);
                    conn.setConnectTimeout(8000);

                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        response.append(str + "\n");
                        Log.d("activity_login", str);
                    }
                    String responseStr = response.toString();
                    Log.d("activity_login", responseStr);
                    getJSON(responseStr);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }

                }
            }
        }).start();

    }


    private void getJSON(String stuid) {
        try {
            JSONObject a = new JSONObject(stuid);
            String code = a.getString("errcode");
            if (code == "0") {
                JSONObject data = a.getJSONObject("data");
                if (data != null) {
                    String stu = data.getString("studentid");
                    String name = data.getString("name");
                    String gender = data.getString("gender");
                    String vcode = data.getString("vcode");
                    String grade = data.getString("grade");
                    mStuNum.setText(stu);
                    mName.setText(name);
                    mSex.setText(gender);
                    mCheck.setText(vcode);
                    ((User)getApplication()).setName(name);
                    ((User)getApplication()).setGender(gender);
                    ((User)getApplication()).setVcode(vcode);
                    ((User)getApplication()).setVcode(grade);

                } else {
                    Looper.prepare();
                    Toast.makeText(this, "请求失败！", Toast.LENGTH_SHORT).show();
                    Looper.loop();


                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, select.class);
        startActivity(intent);
        finish();
    }
}