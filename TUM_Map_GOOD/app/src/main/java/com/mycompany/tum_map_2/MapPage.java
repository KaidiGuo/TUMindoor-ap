package com.mycompany.tum_map_2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

public class MapPage extends Activity {
    private ScaleImage mScaleImage;
    private ScaleImage mScaleImage2;

    private Bitmap bitmap=null;
    private Bitmap bitmap2=null;

    private Canvas canvas;
    private Paint paint;
    Button button1 =null;
    Button button2 =null;

    public int Flag=0;

    public String StartRoomNumber="07";
    public String EndRoomNumber="07";
    public String FloorNumber="0";
    public String CoorFile=null;
    public String PointsFile=null;
    //public Array PointsLineSplit;
    public String PointsLine=null;
    public String PointsLineSplit[]=null;
    //public String SubPointsLineSplit[]=null;
    public String CoorLine=null;

    public int StartX=0;
    public int StartY=0;
    public int EndX=0;
    public int EndY=0;
    public String CoorLineSplit[]=null;

    public String EndLineSplit[];
    public String StartLineSplit[];
    TextView Line1 =null;
    TextView Line2 =null;
    TextView Line3=null;
    TextView t3=null;

    public double Scale=0;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page);



        button2=(Button)findViewById(R.id.backhome);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bitmap != null){
                    bitmap.recycle(); //此句造成的以上异常
                    bitmap = null;
                }
                Intent intent2 = new Intent();
                intent2.setClass(MapPage.this, FrontScreen.class);
                startActivity(intent2);
                finish();//destroy this Activity

            }
        });


        TextView t1=(TextView)findViewById(R.id.room_num_start);
        t1.setText(FrontScreen.MyLocation);//get the input where am I number

        TextView t2=(TextView)findViewById(R.id.room_num_end);
        t2.setText(FrontScreen.Endpoint);//get the input End room number

        // under here is the module of draw one floor ----------------------------------------------------------------

        mScaleImage = (ScaleImage)findViewById(R.id.scale_image);
        mScaleImage.setVisibility(View.VISIBLE);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if(bitmap != null){
            bitmap.recycle(); //此句造成的以上异常
            bitmap = null;
        }
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tumfloor1).copy(Bitmap.Config.ARGB_8888, true);
        //change the image here

        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        mScaleImage.setImageBitmap(bitmap);
        float height=bitmap.getHeight();
        float scaleF=height/655;
        Scale= Math.round(scaleF*10)/10.0;

        //---------------------------------------------------------------------------------------------------------



        StartRoomNumber=FrontScreen.MyLocation;//The start roomnumber
        EndRoomNumber=FrontScreen.Endpoint;//the end roomnumber
        Line1=(TextView)findViewById(R.id.line1);
        //Line2=(TextView)findViewById(R.id.line2);
        //Line3=(TextView)findViewById(R.id.line3);
       // t3=(TextView)findViewById(R.id.floors);

        if (EndRoomNumber!=null){
            FloorNumber=String.valueOf(EndRoomNumber.charAt(0));//get char, is the floor number

        }



        //here is the situation with two floors--------------------------------------------------------------------
        if (FloorNumber.equals("1")){

//        if (FrontScreen.FloorFlag==0) {
//            canvas.drawLine((int) (Scale * 187), (int) (Scale * 167), (int) (Scale * 275), (int) (Scale * 167), paint);
//            canvas.drawLine((int) (Scale * 275), (int) (Scale * 167), (int) (Scale * 275), (int) (Scale * 96), paint);
//            canvas.drawLine((int) (Scale * 275), (int) (Scale * 96), (int) (Scale * 242), (int) (Scale * 96), paint);
//            mScaleImage.setImageBitmap(bitmap);
//        }
//
//            button1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(bitmap != null){
//
//
//                        bitmap.recycle();
//                    }
//
//
//
//                    Intent intent2 = new Intent();
//                    intent2.setClass(MapPage.this, FrontScreen.class);
//                    startActivity(intent2);
//                    finish();//destroy this Activity
//                    FrontScreen.FloorFlag=1;
//
//
//
//                }
//            });
//         if (FrontScreen.FloorFlag==1){
//             if(bitmap != null){
//
//
//                 bitmap.recycle();
//             }
//
//
//             t3.setText(FloorNumber);//get the input where am I number
//
//             Line2.setVisibility(View.VISIBLE);
//             Line3.setVisibility(View.VISIBLE);
//
//
//             mScaleImage2 = (ScaleImage)findViewById(R.id.scale_image);
//             mScaleImage2.setVisibility(View.VISIBLE);
//             //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//
//             bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.tumfloor2).copy(Bitmap.Config.ARGB_8888, true);
//             //change the image here
//
//             canvas = new Canvas(bitmap2);
//             paint = new Paint();
//             paint.setColor(Color.RED);
//             paint.setStrokeWidth(5);
//             canvas.drawBitmap(bitmap2, new Matrix(), paint);
//             mScaleImage.setImageBitmap(bitmap2);
//             float height=bitmap2.getHeight();
//             float scaleF=height/655;
//             double Scale= Math.round(scaleF*10)/10.0;
//         }




        }




        //here is NAVIGATION WITH TWO POINTS-----------------------------------------------------------------------
        if (FrontScreen.MyLocation!="0"&&FrontScreen.Endpoint!="0"){
            try {
                //use inputstreamreader to open the file and use bufferreader to read line by line
                InputStreamReader inputReaderPoints = new InputStreamReader(getResources().openRawResource(R.raw.points0));
                BufferedReader bfPoints = new BufferedReader(inputReaderPoints);
                int counter=0;
                int Change=0;

                while ((PointsLine = bfPoints.readLine()) != null && counter !=2) {
                    PointsLineSplit = PointsLine.split(" ");
                    //if flag changed, means we find the end roomnumber in txt file, we don´t need do the circle again
                    if (EndRoomNumber != null && EndRoomNumber.equals(PointsLineSplit[0])) {
                        EndLineSplit = PointsLineSplit;
                        counter++;
                    }
                    else if (StartRoomNumber!=null&&StartRoomNumber.equals(PointsLineSplit[0])){
                        StartLineSplit = PointsLineSplit;
                        counter++;
                    }

                }
                //find from which point they become different
                if (EndLineSplit!=null&&StartLineSplit!=null){
                for (int i=1;i<Math.min(EndLineSplit.length, StartLineSplit.length);i++){
                    if (Integer.parseInt(EndLineSplit[i])!=Integer.parseInt(StartLineSplit[i])){
                        Change=i;
                        break;
                    }

                }
                }

                //and draw lines, separately
                //here is part 111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                for (int X = Change-1; X < EndLineSplit.length - 1; X++) {
                    int StartPoint = Integer.parseInt(EndLineSplit[X]);
                    int EndPoint = Integer.parseInt(EndLineSplit[X + 1]);
                    // and acoording to the roomnumber we find, go through all the path points in coor.txt
                    InputStreamReader inputReaderCoor = new InputStreamReader(getResources().openRawResource(R.raw.coor0));
                    BufferedReader bfCoor = new BufferedReader(inputReaderCoor);


                    int counter2 = 0;

                    while ((CoorLine = bfCoor.readLine()) != null && (StartX == 0 || EndX == 0)) {
                        counter2++;
                        if (counter2 == StartPoint) {
                            CoorLineSplit = CoorLine.split(" ");
                            StartX = (int) (Scale * Integer.parseInt(CoorLineSplit[0]));
                            StartY = (int) (Scale * Integer.parseInt(CoorLineSplit[1]));
                        }
                        if (counter2 == EndPoint) {
                            CoorLineSplit = CoorLine.split(" ");
                            EndX = (int) (Scale * Integer.parseInt(CoorLineSplit[0]));
                            EndY = (int) (Scale * Integer.parseInt(CoorLineSplit[1]));
                        }

                    }
                    canvas.drawLine(StartX, StartY, EndX, EndY, paint);
                    counter2 = 0;
                    bfCoor.close();
                    StartX = 0;
                    StartY = 0;
                    EndX = 0;
                    EndY = 0;

                }
                //here is part 22222222222222222222222222222222222222222222222222222222222222222222222222222222222222
                for (int X = Change-1; X < StartLineSplit.length - 1; X++) {
                    int StartPoint = Integer.parseInt(StartLineSplit[X]);
                    int EndPoint = Integer.parseInt(StartLineSplit[X + 1]);
                    // and acoording to the roomnumber we find, go through all the path points in coor.txt
                    InputStreamReader inputReaderCoor = new InputStreamReader(getResources().openRawResource(R.raw.coor0));
                    BufferedReader bfCoor = new BufferedReader(inputReaderCoor);


                    int counter2 = 0;

                    while ((CoorLine = bfCoor.readLine()) != null && (StartX == 0 || EndX == 0)) {
                        counter2++;
                        if (counter2 == StartPoint) {
                            CoorLineSplit = CoorLine.split(" ");
                            StartX = (int) (Scale * Integer.parseInt(CoorLineSplit[0]));
                            StartY = (int) (Scale * Integer.parseInt(CoorLineSplit[1]));
                        }
                        if (counter2 == EndPoint) {
                            CoorLineSplit = CoorLine.split(" ");
                            EndX = (int) (Scale * Integer.parseInt(CoorLineSplit[0]));
                            EndY = (int) (Scale * Integer.parseInt(CoorLineSplit[1]));
                        }

                    }
                    canvas.drawLine(StartX, StartY, EndX, EndY, paint);
                    counter2 = 0;
                    bfCoor.close();
                    StartX = 0;
                    StartY = 0;
                    EndX = 0;
                    EndY = 0;

                }

                bfPoints.close();


            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            mScaleImage.setImageBitmap(bitmap);


        }


        //here is NAVIGATION WITH one POINTS-----------------------------------------------------------------------


        //if user only input the end room, call founctions below

        if(FrontScreen.MyLocation.equals("0")) {
            try {
                //use inputstreamreader to open the file and use bufferreader to read line by line
                InputStreamReader inputReaderPoints = new InputStreamReader(getResources().openRawResource(R.raw.points0));
                BufferedReader bfPints = new BufferedReader(inputReaderPoints);


                while ((PointsLine = bfPints.readLine()) != null && Flag == 0) {
                    PointsLineSplit = PointsLine.split(" ");
                    //if flag changed, means we find the end roomnumber in txt file, we don´t need do the circle again
                    if (EndRoomNumber != null && EndRoomNumber.equals(PointsLineSplit[0])) {
                        Flag = 1;


                        for (int X = 1; X < PointsLineSplit.length - 1; X++) {
                            int StartPoint = Integer.parseInt(PointsLineSplit[X]);
                            int EndPoint = Integer.parseInt(PointsLineSplit[X + 1]);
                            // and acoording to the roomnumber we find, go through all the path points in coor.txt
                            InputStreamReader inputReaderCoor = new InputStreamReader(getResources().openRawResource(R.raw.coor0));
                            BufferedReader bfCoor = new BufferedReader(inputReaderCoor);


                            int counter = 0;

                            while ((CoorLine = bfCoor.readLine()) != null && (StartX == 0 || EndX == 0)) {
                                counter++;
                                if (counter == StartPoint) {
                                    CoorLineSplit = CoorLine.split(" ");
                                    StartX = (int) (Scale * Integer.parseInt(CoorLineSplit[0]));
                                    StartY = (int) (Scale * Integer.parseInt(CoorLineSplit[1]));
                                }
                                if (counter == EndPoint) {
                                    CoorLineSplit = CoorLine.split(" ");
                                    EndX = (int) (Scale * Integer.parseInt(CoorLineSplit[0]));
                                    EndY = (int) (Scale * Integer.parseInt(CoorLineSplit[1]));
                                }

                            }
                            canvas.drawLine(StartX, StartY, EndX, EndY, paint);
                            counter = 0;
                            bfCoor.close();
                            StartX = 0;
                            StartY = 0;
                            EndX = 0;
                            EndY = 0;


                        }


                    }


                }

                bfPints.close();


            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            mScaleImage.setImageBitmap(bitmap);

        }






      //  int a =0;
      //  canvas.drawLine(a, 0, 100, 100, paint);//start coordinate and end coordinate
//        canvas.drawLine(460, 440, 690, 440, paint);
//        canvas.drawLine(690, 440, 690, 300, paint);
        //mScaleImage.setImageBitmap(bitmap);

        // Over here is a module of draw one floor ----------------------------------------------------------------



    }




    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}



