package com.shiffer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShifferSplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            setTheme(R.style.ShifferSplashScreenStyle);


            super.onCreate(savedInstanceState);


            FirebaseAuth FireBaseAuth = FirebaseAuth.getInstance();

            FirebaseUser FireBaseUser = FireBaseAuth.getCurrentUser();

            // start inside the app
            if (FireBaseUser != null && FireBaseUser.isEmailVerified()) {
                //todo
// move to inside the app
//startActivity(new Intent(this,LogedInActivty.class));

            } else {


                startActivity(new Intent(this, StartUpActivity.class));
            }

            finish();

        } catch (Exception Ex) {


            Toast EmailSentToast = Toast.makeText(this, getString(R.string.ExpiredTokenToast), Toast.LENGTH_LONG);

            EmailSentToast.setGravity(Gravity.CENTER, 0, 0);

            startActivity(new Intent(this, StartUpActivity.class));

            EmailSentToast.show();

            System.console().writer().println(Ex);

        }


    }

}
