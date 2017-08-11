package com.app.task_2_android_app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class To_click extends AppCompatActivity {

    private Camera mCamera;
    private Camera_preview mPreview;
    public static final int MEDIA_TYPE_IMAGE = 1;
    EditText name,age,address,gender;
    Dialog dialog;
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_click);

        mCamera = getCameraInstance();
        mPreview = new Camera_preview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);


        final Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);
                        captureButton.setVisibility(View.INVISIBLE);
                    }
                }
        );

    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
        }
        return c;
    }
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){

                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(" ", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(" ", "Error accessing file: " + e.getMessage());
            }
            Toast.makeText(To_click.this, "Image stored in InternalStorage/Pictures/CameraApp/", Toast.LENGTH_SHORT).show();
            set_view();
        }

    };




    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Camera_App");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }
    void set_view()
    {
        dialog = new Dialog(To_click.this,R.style.ThemeDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        dialog.setTitle("Detail");
        dialog.show();


    }

    public void set_data(View view)
    {

        //String fname = Environment.getExternalStorageDirectory().toString()+"/Pictures/Camera_App";
        name=(EditText) dialog.findViewById(R.id.username);
        age=(EditText) dialog.findViewById(R.id.userage);
        address=(EditText) dialog.findViewById(R.id.useraddress);
        gender=(EditText) dialog.findViewById(R.id.usergender);



        String A=name.getText().toString();
        String B=age.getText().toString();
        String C=address.getText().toString();
        String D=gender.getText().toString();
        if(A.equals("")||B.equals("")||C.equals("")||D.equals(""))
        {
            Toast.makeText(To_click.this, "some fields are empty", Toast.LENGTH_SHORT).show();
            return ;
        }
        Log.e("Values", A);
            A = "Name :" + A +'\n'+"Age :" + B +'\n'+"Address :" + C +'\n'+"Gender :" + D +'\n';
            Log.e("Values", A);
            File mediaStorageDir = new File("/sdcard/Task");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("MyCameraApp", "failed to create directory");
                }
            }
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fname = "task_" + timeStamp;
                String fpath = "/sdcard/Task/" + fname + ".txt";

                File file = new File(fpath);

                // If file does not exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(A);
                bw.close();
                Toast.makeText(To_click.this, "Data stored at InternalStorage/Task/" + fname + ".txt", Toast.LENGTH_SHORT).show();
                Log.d("Suceess", "Sucess");
                dialog.dismiss();
                dialog = new Dialog(To_click.this, R.style.ThemeDialogCustom);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.graph);
                dialog.getWindow().setFormat(PixelFormat.TRANSLUCENT);
                dialog.show();
                //setGraph();

            } catch (IOException e) {
                e.printStackTrace();
                //return false;
            }

    }
    public void main_activity(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }




}
