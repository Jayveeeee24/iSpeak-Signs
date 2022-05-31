package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;

public class RecognizeActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "RecognizeActivity";
    private Mat mRgba;
    private Mat mGray;
    private CameraBridgeViewBase mOpenCvCameraView;
    private CardView recognizeInfoCard;

    private objectDetectorClass objectDetectorClass;

    private CardView recognizeRemove;
    private CardView recognizeAdd;
    private TextView recognizeText;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface
                    .SUCCESS) {
                Log.i(TAG, "OpenCv is Loaded");
                mOpenCvCameraView.enableView();
                mOpenCvCameraView.enableFpsMeter();
            }
            super.onManagerConnected(status);
        }
    };

    public RecognizeActivity(){
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int MY_PERMISSIONS_REQUEST = 0;
        if (ContextCompat.checkSelfPermission(RecognizeActivity.this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(RecognizeActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST);
        }
        setContentView(R.layout.activity_recognize);

        recognizeAdd = findViewById(R.id.recognize_add);
        recognizeRemove = findViewById(R.id.recognize_remove);
        recognizeText = findViewById(R.id.recognized_text);
        recognizeInfoCard = findViewById(R.id.recognize_info_card);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.recognize_camera);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        try{
            objectDetectorClass=new objectDetectorClass(recognizeAdd, recognizeRemove, recognizeText, getAssets(),"hand_model.tflite",300, "Sign_language_model.tflite", 96);
            Log.d("MainActivity","Model is successfully loaded");
        }
        catch (IOException e){
            Log.d("MainActivity","Getting some error");
            e.printStackTrace();
        }

        recognizeInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecognizeActivity.this, RecognizeHowTo.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            Log.i(TAG, "OpenCV Initialization is done");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else{
            Log.i(TAG, "Open CV initialization failed, Please try again.");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mLoaderCallback);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mOpenCvCameraView != null){
            mOpenCvCameraView.disableView();
            mRgba.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mOpenCvCameraView != null){
            mRgba.release();
            mOpenCvCameraView.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mGray = new Mat(height, width, CvType.CV_8UC1);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        if (mRgba != null){
            mRgba.release();
        }

        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        return objectDetectorClass.recognizeImage(mRgba);
    }



}