package com.shiffer;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class HelperMethods {


    private static HelperMethods instance = new HelperMethods();


    static Context Context;
    ConnectivityManager ConnectivityManager;
    NetworkInfo WifiInfo, MobileInfo;
    boolean Connected = false;


    public static HelperMethods GetInstance(Context Context) {
        HelperMethods.Context = Context.getApplicationContext();
        return instance;
    }

    public boolean CheckConnection(Activity CurrentActivity) {
        try {
            ConnectivityManager = (ConnectivityManager) Context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo NetworkInfo = ConnectivityManager.getActiveNetworkInfo();
            Connected = NetworkInfo != null && NetworkInfo.isAvailable() &&
                    NetworkInfo.isConnected();
            if (Connected) {

                System.out.println("Connected");

                return Connected;

            } else {

                Snackbar.make(CurrentActivity.findViewById(android.R.id.content), R.string.NoInternetSnackBars, Snackbar.LENGTH_SHORT);

                return Connected;

            }


        } catch (Exception Ex) {

            System.out.println(Ex.getMessage());
            Log.v("connectivity", Ex.toString());

            return false;
        }
    }

}



