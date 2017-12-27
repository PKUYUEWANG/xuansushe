package com.example.gavin.xuansushe;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAVIN on 2017/12/25.
 */

public class SelectRoom extends AppCompatActivity {
    ListView myinfolistview;
    Button btn_deal;
    User student=new User();
    Button btn_boos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseroom);//选中布局
        ActionBar actionBar=getSupportActionBar();//标头
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("个人信息");
        student= (User) getIntent().getSerializableExtra("student");
        myinfolistview= (ListView) findViewById(R.id.mylist);
        btn_deal= (Button) findViewById(R.id.deal_with_mybus);
        btn_boos=(Button) findViewById(R.id.deal_with_mybOOs);
//        btn_deal.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction()==MotionEvent.ACTION_DOWN){
//                    v.setBackgroundColor(Color.rgb(211,121,121));
//                }else if(event.getAction()==MotionEvent.ACTION_UP){
//                    v.setBackgroundColor(Color.rgb(141,238,238));
//                }
//                return false;
//            }
//        });
        btn_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectRoom.this,SelectPerson.class);//选取几人办理界面
                Bundle bundle=new Bundle();
                bundle.putSerializable("student",student);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_boos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.ss.pku.edu.cn/index.php/overview/map"));
                startActivity(intent);
            }
        });

        initlistview();//初始化Myinfo
    }

    private void initlistview() {//显示用户信息
        List<String> myinfo=new ArrayList<>();
        myinfo.add("姓名:"+"                                                        "+ student.getName());
        myinfo.add("学号:"+"                                                        "+ student.getStudentid());
        myinfo.add("性别:"+"                                                        "+ student.getGender() );
        myinfo.add("校验码:"+"                                                   "+ student.getVcode() );
        ArrayAdapter myadapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,myinfo);
        myinfolistview.setAdapter(myadapter);
    }
}
