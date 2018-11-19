package com.shiffer;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AppCycle extends Application {
    @Override
    public void onCreate() {

        try {

            super.onCreate();

            FirebaseAuth FireBaseAuth = FirebaseAuth.getInstance();

            FirebaseUser FireBaseUser = FireBaseAuth.getCurrentUser();

            // start inside the app
            if (FireBaseUser != null && FireBaseUser.isEmailVerified()) {
                //todo
// move to inside the app
//startActivity(new Intent(this,LogedInActivty.class));

            } else {


                startActivity(new Intent(AppCycle.this, StartUpActivity.class));
            }


        } catch (Exception Ex) {

            System.console().writer().println(Ex);

        }

    }
}
