package com.mycompany.tum_map_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FrontScreen extends AppCompatActivity {
    Button button1 =null;
    Button button2=null;
    //because we need to use this variable in two page, so declare it at the first page
    public static String MyLocation ="0";// Only static variable can be called by "class name+ variable name"
    public static String Endpoint = "0";// Only static variable can be called by "class name+ variable name"
    public static int FloorFlag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_screen);
        MyLocation ="0";// Only static variable can be called by "class name+ variable name"
        Endpoint = "0";// Only static variable can be called by "class name+ variable name"
        if (FloorFlag==1){
            Intent intent3 = new Intent();
            intent3.setClass(FrontScreen.this, MapPage.class);
            startActivity(intent3);
        }

        //Here is how to realize the click and switch function
        button1=(Button)findViewById(R.id.show_loc_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(FrontScreen.this, WhereAmI.class);
                startActivity(intent1);
            }
        });

        button2=(Button)findViewById(R.id.nav_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent();
                intent2.setClass(FrontScreen.this,NavigatorQuestion.class);
                startActivity(intent2);
            }
        });
    }
}
