package com.shiffer;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.shiffer.Popups.ForgetPasswordPopup;

public class StartUpActivity extends AppCompatActivity {

    boolean IsLoginSegment = true;


    Context ActivityContext;

    Button ForgetPasswordButton;
    Button GetAuthButton;
    Button FaceBookLoginButton;


    EditText EmailEditText;
    EditText PasswordEditText;

    RadioGroup StartUpActivtiSegments;
    RadioButton SignInSegment;
    RadioButton NewUserSegment;

    FirebaseAuth FirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        ActivityContext = this;


        final Drawable ButtonBorder = getResources().getDrawable(R.drawable.buttonborder);


        GetAuthButton = findViewById(R.id.GetAuthButton);
        ForgetPasswordButton = findViewById(R.id.ForgetPasswordButton);
        FaceBookLoginButton = findViewById(R.id.FaceBookLoginButton);


        EmailEditText = findViewById(R.id.EmailEditText);
        PasswordEditText = findViewById(R.id.PasswordEditText);


        StartUpActivtiSegments = findViewById(R.id.StartUpActivtiSegments);
        SignInSegment = findViewById(R.id.SignInSegment);
        NewUserSegment = findViewById(R.id.NewUserSegment);

        //set the firebase Auth object
        FirebaseAuth = FirebaseAuth.getInstance();


        ForgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StartUpActivity.this, ForgetPasswordPopup.class));

            }
        });

        StartUpActivtiSegments.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                try {


                    switch (checkedId) {
                        case R.id.SignInSegment: // SignInSegment
                        {
                            GetAuthButton.setText(getText(R.string.Signin));

                            ButtonBorder.setColorFilter(new
                                    PorterDuffColorFilter((ContextCompat.getColor(ActivityContext, R.color.GreenColorShiffer)), PorterDuff.Mode.MULTIPLY));

                            GetAuthButton.setBackground(ButtonBorder);

                            IsLoginSegment = true;

                        }
                        break;
                        case R.id.NewUserSegment: // NewUserSegment
                        {
                            GetAuthButton.setText(getText(R.string.CreateAccount));

                            ButtonBorder.setColorFilter(new
                                    PorterDuffColorFilter((ContextCompat.getColor(ActivityContext, R.color.RedColorShiffer)), PorterDuff.Mode.MULTIPLY));

                            GetAuthButton.setBackground(ButtonBorder);

                            IsLoginSegment = false;
                        }
                        break;
                    }


                } catch (Exception Ex) {
                    System.out.println(Ex.getMessage());


                }

            }
        });


        GetAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    if (HelperMethods.GetInstance(ActivityContext).CheckConnection()) {

                        if (IsLoginSegment) {
                            FirebaseAuth.signInWithEmailAndPassword(EmailEditText.getText().toString(), PasswordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (FirebaseAuth.getCurrentUser().isEmailVerified()) {

                                            //todo
// move to inside the app
//startActivity(new Intent(this,LogedInActivty.class));


                                        }else {
                                            Toast EmailSentToast = Toast.makeText(ActivityContext, getString(R.string.RequestVerifyingEmailToast), Toast.LENGTH_LONG);
                                            EmailSentToast.setGravity(Gravity.CENTER, 0, 0);
                                            EmailSentToast.show();

                                        }

                                    } else {

                                        Toast EmailSentToast = Toast.makeText(ActivityContext, task.getException().toString(), Toast.LENGTH_LONG);
                                        EmailSentToast.setGravity(Gravity.CENTER, 0, 0);
                                        EmailSentToast.show();

                                    }


                                }
                            });

                        } else {

                            FirebaseAuth.createUserWithEmailAndPassword(EmailEditText.getText().toString(), PasswordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Toast EmailSentToast = Toast.makeText(ActivityContext, getString(R.string.VerifyingEmailToast), Toast.LENGTH_LONG);
                                        EmailSentToast.setGravity(Gravity.CENTER, 0, 0);
                                        EmailSentToast.show();

                                        EmailEditText.setText("");
                                        PasswordEditText.setText("");


                                    } else {
                                        try {
                                            throw task.getException();

                                        }
                                        catch (FirebaseAuthUserCollisionException ExistEmail)
                                        {
                                            //does email exist Case
                                            Snackbar.make(findViewById(android.R.id.content),R.string.ResetPasswordEmailSentSnackBar,Snackbar.LENGTH_SHORT)
                                                    .setAction(R.string.LetsDoIt, new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            //todo fill edittextwith signup detail and change the laye to sign details.



                                                        }
                                                    });


                                        } catch (Exception Ex) {
                                            Toast EmailSentToast = Toast.makeText(ActivityContext, Ex.toString(), Toast.LENGTH_LONG);
                                            EmailSentToast.setGravity(Gravity.CENTER, 0, 0);
                                            EmailSentToast.show();                                        }


                                    }

                                }
                            });

                        }


                    } else {


                    }


                } catch (Exception Ex) {

                    System.out.println(Ex.getMessage());

                }
            }

        });

    }
}
