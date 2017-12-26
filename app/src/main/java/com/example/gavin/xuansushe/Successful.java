package com.example.gavin.xuansushe;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by GAVIN on 2017/12/26.
 */

public class Successful extends AppCompatActivity {//已分配宿舍信息
    TextView no,name,sex,buildingno,bedno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("分配宿舍信息");
        no= (TextView) findViewById(R.id.succhooseno);
        name= (TextView) findViewById(R.id.succhoosename);
        sex= (TextView) findViewById(R.id.succhoosesex);
        buildingno= (TextView) findViewById(R.id.sucdorno);
        bedno= (TextView) findViewById(R.id.sucbedno);
        User student= (User) getIntent().getSerializableExtra("student");
        student.getStudentid();
        no.setText(student.getStudentid());
        name.setText(student.getName());
        sex.setText(student.getGender());
        buildingno.setText(student.getRoom());
        bedno.setText(student.getRoom());
    }
}
