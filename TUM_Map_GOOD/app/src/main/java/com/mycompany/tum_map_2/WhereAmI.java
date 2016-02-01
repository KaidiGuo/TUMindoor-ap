package com.mycompany.tum_map_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WhereAmI extends Activity {
    Button button1 =null;
    Button button2 =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_am_i);

//        EditText t1=(EditText)findViewById(R.id.room_enter_where);
        button1=(Button)findViewById(R.id.search_where);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText t1=(EditText)findViewById(R.id.room_enter_where);
                FrontScreen.Endpoint= t1.getText().toString();//get the room number  input by user

                Intent intent1 = new Intent();
                intent1.setClass(WhereAmI.this, MapPage.class);//click and change to next layout
                startActivity(intent1);
            }
        });
        button2=(Button)findViewById(R.id.back_where);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setClass(WhereAmI.this,FrontScreen.class);
                startActivity(intent2);
            }
        });
    }
}
