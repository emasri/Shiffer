package com.shiffer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.shiffer.Popups.ForgetPasswordPopup;

public class StartUpActivity extends AppCompatActivity {


    Context ActivityContext;


    Activity CurrentActivity;

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

        CurrentActivity = this;


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


                            GetAuthButton.setBackground(getResources().getDrawable(R.drawable.signinbuttonborder));


                        }
                        break;
                        case R.id.NewUserSegment: // NewUserSegment
                        {
                            GetAuthButton.setText(getText(R.string.CreateAccount));

                            GetAuthButton.setBackground(getResources().getDrawable(R.drawable.createaccountbuttonborder));


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


                    if (CheckEditText() && HelperMethods.GetInstance(ActivityContext).CheckConnection(CurrentActivity)) {

                        if (SignInSegment.isChecked()) {

                            SignIn();

                        } else {

                            CreateNewAccount();
                        }
                    }


                } catch (Exception Ex) {

                    System.out.println(Ex.getMessage());

                }


            }

        });

    }

    void SignIn() {
        try {
            //todo progressbar show

            FirebaseAuth.signInWithEmailAndPassword(EmailEditText.getText().toString(), PasswordEditText.getText().toString()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //todo progressbar hide

                            if (task.isSuccessful()) {
                                if (FirebaseAuth.getCurrentUser().isEmailVerified()) {

                                    //todo
// move to inside the app
//startActivity(new Intent(this,LogedInActivty.class));


                                } else {
                                    Toast EmailSentToast = Toast.makeText(ActivityContext, getString(R.string.RequestVerifyingEmailToast), Toast.LENGTH_LONG);
                                    EmailSentToast.setGravity(Gravity.CENTER, 0, 0);
                                    EmailSentToast.show();

                                }

                            } else {


                                try {

                                    throw task.getException();

                                } catch (FirebaseAuthInvalidCredentialsException Ex) {

                                    switch (Ex.getErrorCode()) {

                                        case "ERROR_WRONG_PASSWORD":
                                            Snackbar.make(findViewById(android.R.id.content), R.string.WrongPasswordSnackBar, Snackbar.LENGTH_SHORT).show();

                                            break;

                                    }

                                } catch (FirebaseAuthInvalidUserException Ex) {


                                    switch (Ex.getErrorCode()) {


                                        case "ERROR_USER_NOT_FOUND":
                                            Snackbar.make(findViewById(android.R.id.content), R.string.NotFoundUserSnackBar, Snackbar.LENGTH_LONG)
                                                    .setAction(R.string.NotFoundUserSnackBarButton, new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            SignInSegment.setChecked(false);
                                                            NewUserSegment.setChecked(true);


                                                            //initiate the button
                                                            GetAuthButton.performClick();
                                                            GetAuthButton.setPressed(true);
                                                            GetAuthButton.invalidate();
                                                            // delay completion till animation completes
                                                            GetAuthButton.postDelayed(new Runnable() {  //delay button
                                                                public void run() {
                                                                    GetAuthButton.setPressed(false);
                                                                    GetAuthButton.invalidate();
                                                                    //any other associated action
                                                                }
                                                            }, 800);  // .8secs delay time

                                                        }
                                                    }).setActionTextColor(ContextCompat.getColor(CurrentActivity, R.color.GrayColorShiffer)).show();

                                            break;




                                    }


                                } catch (Exception Ex) {


                                    Toast EmailSentToast = Toast.makeText(ActivityContext, Ex.toString(), Toast.LENGTH_LONG);
                                    EmailSentToast.setGravity(Gravity.CENTER, 0, 0);
                                    EmailSentToast.show();
                                }


                            }


                        }
                    });


        } catch (Exception Ex) {

            System.out.println(Ex.getMessage());

        }


    }


    void CreateNewAccount() {


        try {
            //todo progressbar show

            FirebaseAuth.createUserWithEmailAndPassword(EmailEditText.getText().toString(), PasswordEditText.getText().toString()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //todo progressbar hide

                                FirebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast EmailSentToast = Toast.makeText(ActivityContext, getString(R.string.VerifyingEmailToast), Toast.LENGTH_LONG);
                                            EmailSentToast.setGravity(Gravity.CENTER, 0, 0);
                                            EmailSentToast.show();


                                            EmailEditText.setText("");
                                            PasswordEditText.setText("");


                                        } else {
                                            try {
                                                throw task.getException();

                                            } catch (FirebaseAuthUserCollisionException ExistEmail) {

                                                //does email exist Case
                                                Snackbar.make(findViewById(android.R.id.content), R.string.AlreadyExistEmailSnackBar, Snackbar.LENGTH_LONG)
                                                        .setAction(R.string.LetsDoIt, new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {


                                                                SignInSegment.setChecked(true);
                                                                NewUserSegment.setChecked(false);


                                                                //initiate the button
                                                                GetAuthButton.performClick();
                                                                GetAuthButton.setPressed(true);
                                                                GetAuthButton.invalidate();
                                                                // delay completion till animation completes
                                                                GetAuthButton.postDelayed(new Runnable() {  //delay button
                                                                    public void run() {
                                                                        GetAuthButton.setPressed(false);
                                                                        GetAuthButton.invalidate();
                                                                        //any other associated action
                                                                    }
                                                                }, 800);  // .8secs delay time


                                                            }
                                                        }).setActionTextColor(ContextCompat.getColor(CurrentActivity, R.color.GrayColorShiffer)).show();
                                            } catch (FirebaseAuthWeakPasswordException Ex) {


                                                Snackbar.make(findViewById(android.R.id.content), R.string.InvalidPasswordSnackBar, Snackbar.LENGTH_SHORT).show();


                                            } catch (FirebaseAuthInvalidCredentialsException Ex) {


                                                Snackbar.make(findViewById(android.R.id.content), R.string.InvalidEmailSnackBar, Snackbar.LENGTH_SHORT).show();


                                            } catch (Exception Ex) {
                                                Toast EmailSentToast = Toast.makeText(ActivityContext, Ex.toString(), Toast.LENGTH_LONG);
                                                EmailSentToast.setGravity(Gravity.CENTER, 0, 0);
                                                EmailSentToast.show();
                                            }


                                        }


                                    }
                                });


                            }else {

                                System.out.println(task.getException().getMessage());


                            }
                        }


                    });


        } catch (Exception Ex) {

            System.out.println(Ex.getMessage());

        }

    }


    Boolean CheckEditText() {
        try {
            if (EmailEditText.getText().toString().isEmpty() && PasswordEditText.getText().toString().isEmpty()) {

                EmailEditText.setBackground(getResources().getDrawable(R.drawable.edittextborderwarningcase));
                PasswordEditText.setBackground(getResources().getDrawable(R.drawable.edittextborderwarningcase));

                return false;


            } else if (EmailEditText.getText().toString().isEmpty()) {

                EmailEditText.setBackground(getResources().getDrawable(R.drawable.edittextborderwarningcase));

                PasswordEditText.setBackground(getResources().getDrawable(R.drawable.edittextbordernormalcase));

                return false;


            } else if (PasswordEditText.getText().toString().isEmpty()) {

                PasswordEditText.setBackground(getResources().getDrawable(R.drawable.edittextborderwarningcase));
                EmailEditText.setBackground(getResources().getDrawable(R.drawable.edittextbordernormalcase));

                return false;

            } else {


                EmailEditText.setBackground(getResources().getDrawable(R.drawable.edittextbordernormalcase));
                PasswordEditText.setBackground(getResources().getDrawable(R.drawable.edittextbordernormalcase));

                return true;
            }
        } catch (Exception Ex) {

            System.out.println(Ex.getMessage());
            return false;


        }
    }
}






