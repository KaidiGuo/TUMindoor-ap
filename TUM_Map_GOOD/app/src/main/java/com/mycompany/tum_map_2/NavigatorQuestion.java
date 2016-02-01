package com.mycompany.tum_map_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NavigatorQuestion extends Activity {
    Button button1 =null;
    Button button2 =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator_question);

        button1=(Button)findViewById(R.id.search_nav);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get two contents of edittexts module
                EditText t1=(EditText)findViewById(R.id.room_enter_nav);
                EditText t2=(EditText)findViewById(R.id.end_enter_nav);
                //get the content in Edittext1 and give it to public variable Mylocation
                //to use a public static variable you need "java Name+variable Name"
                FrontScreen.MyLocation=t1.getText().toString();
                FrontScreen.Endpoint=t2.getText().toString();

                Intent intent1 = new Intent();
                intent1.setClass(NavigatorQuestion.this, MapPage.class);
                startActivity(intent1);
            }
        });

        button2=(Button)findViewById(R.id.back_nav);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setClass(NavigatorQuestion.this, FrontScreen.class);
                startActivity(intent2);
            }
        });



    }
}
