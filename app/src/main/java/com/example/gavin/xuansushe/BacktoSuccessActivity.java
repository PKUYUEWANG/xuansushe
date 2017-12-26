package com.example.gavin.xuansushe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by GAVIN on 2017/12/26.
 */

public class BacktoSuccessActivity extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backto_success);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("办理住宿成功");
        btn= (Button) findViewById(R.id.button_aback);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BacktoSuccessActivity.this,Successful.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("student",getIntent().getSerializableExtra("student"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
