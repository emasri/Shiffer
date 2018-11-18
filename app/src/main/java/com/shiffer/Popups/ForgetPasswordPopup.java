package com.shiffer.Popups;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.shiffer.R;

public class ForgetPasswordPopup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.forgetpasswordlayout);

        DisplayMetrics DisplayScreen = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(DisplayScreen);

        getWindow().setLayout((int)(DisplayScreen.widthPixels*0.35),(int)(DisplayScreen.heightPixels*0.25));

    }
}
