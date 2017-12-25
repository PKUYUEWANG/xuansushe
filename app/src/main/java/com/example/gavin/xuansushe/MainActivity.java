package com.example.gavin.xuansushe;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by GAVIN on 2017/11/20.
 */

public class MainActivity extends Activity implements View.OnClickListener{



        private EditText mAccount, mPassword;
        private Button mLogin;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.xuansushe);
            initView();
        }

        void initView() {
            mAccount = (EditText) findViewById(R.id.editText3);//拿到布局的控件，并强制类型转换
            mPassword = (EditText) findViewById(R.id.login_edit_pwd);
            mLogin = (Button) findViewById(R.id.button);
            mLogin.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            final String studentid = mAccount.getText().toString().trim();
            final String password = mPassword.getText().toString().trim();
            loginByGet(mAccount.getText().toString(), mPassword.getText().toString());
        }

        public void loginByGet(String username, String password) {
            String data = "username=" + username + "&password=" + password;
            final String address = "https://api.mysspku.com/index.php/V1/MobileCourse/Login?" + data;
            Log.d("Login", address);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection con = null;
                    try {
                        URL url = new URL(address);
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setConnectTimeout(8000);
                        con.setReadTimeout(8000);
                        InputStream is = con.getInputStream();//字节流转换成字符串
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        StringBuilder response = new StringBuilder();
                        String str;
                        while ((str = reader.readLine()) != null) {
                            response.append(str + "\n");
                            Log.d("login_activity", str);
                        }
                        String responseStr = response.toString();
                        Log.d("login_activity", responseStr);
                        getJSON(responseStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            con.disconnect();
                        }

                    }
                }
            }).start();

        }
        public class User extends Application {
            private String studentid;
            private String name;
            private String gender;
            private String vcode;
            private String room;
            private String building;
            private String location;
            private String grade;
            private String num;
            private String errcode;

            public String getStudentid() {
                return studentid;
            }

            public void setStudentid(String studentid) {
                this.studentid = studentid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getVcode() {
                return vcode;
            }

            public void setVcode(String vcode) {
                this.vcode = vcode;
            }

            public String getRoom() {
                return room;
            }

            public void setRoom(String room) {
                this.room = room;
            }

            public String getBuilding() {
                return building;
            }

            public void setBuilding(String building) {
                this.building = building;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getErrcode() {
                return errcode;
            }

            public void setErrcode(String errcode) {
                this.errcode = errcode;
            }

            @Override
            public String toString() {
                return "SelectionResult{" +
                        "errcode='" + errcode + '\'' +
                        '}';
            }
        }
        private void getJSON(String Jsondata) {
            try {
                JSONObject a = new JSONObject(Jsondata);
                System.out.println(a.get("errcode"));
                String code = a.getString("errcode");
                if (code.equals("0")) {
                    System.out.println("OK");
                    Intent intent = new Intent(this, login.class);
                    startActivity(intent);
                    this.finish();
                    String studentid = mAccount.getText().toString();
                    ((User) getApplication()).setStudentid(studentid);
                } else if (code.equals("40001")) {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "学号不存在", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else if (code.equals("40002")) {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else if (code.equals("40009")) {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "参数错误", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "请求失败！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

