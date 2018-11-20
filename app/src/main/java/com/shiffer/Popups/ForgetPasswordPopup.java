package com.shiffer.Popups;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.shiffer.R;

public class ForgetPasswordPopup extends Activity {
    Context ActivityContext;

    Activity CurrentActivtiy;

    EditText FogetPasswordEditText;
    FirebaseAuth FireBaseAuth;
    Button RestPasswrodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityContext = this;

        CurrentActivtiy = this;

        FireBaseAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.forgetpasswordlayout);

        DisplayMetrics DisplayScreen = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(DisplayScreen);

        getWindow().setLayout(
                (int) (DisplayScreen.widthPixels * 0.35)
                , (int) (DisplayScreen.heightPixels * 0.25));

        RestPasswrodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (com.shiffer.HelperMethods.GetInstance(ActivityContext).CheckConnection(CurrentActivtiy)) {

                    SendEmailPasswordLink();
                }
            }
        });

    }


    void SendEmailPasswordLink() {

        try {


            FireBaseAuth.sendPasswordResetEmail(FogetPasswordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {


                        Snackbar.make(findViewById(android.R.id.content), R.string.ResetPasswordEmailSentSnackBar, Snackbar.LENGTH_SHORT).show();


                    } else {

                        Toast EmailSentToast = Toast.makeText(ActivityContext, task.getException().toString(), Toast.LENGTH_LONG);
                        EmailSentToast.setGravity(Gravity.CENTER, 0, 0);
                        EmailSentToast.show();


                    }


                }
            });


        } catch (Exception Ex) {

            System.out.println(Ex.getMessage());

        }

    }


}
