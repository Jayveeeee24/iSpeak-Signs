package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class RecognizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize);
        Toast.makeText(this, "Starting Camera, Please Wait...", Toast.LENGTH_SHORT).show();
    }

}