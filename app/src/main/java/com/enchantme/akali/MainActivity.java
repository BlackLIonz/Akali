package com.enchantme.akali;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.Manifest.*;

public class MainActivity extends AppCompatActivity {

    TextView version;
    TextView IMEI;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        version = findViewById(R.id.version_textview);
        IMEI = findViewById(R.id.imei_textview);
        btn = findViewById(R.id.get_permission_button);

        View.OnClickListener getPermission = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        };
        btn.setOnClickListener(getPermission);

        getVersionInfo();
        getIMEI();
    }

    private void getVersionInfo() {
        String versionName ="";

        versionName = BuildConfig.VERSION_NAME;

        version.setText(versionName);
    }

    private void getIMEI() {
        if (ActivityCompat.checkSelfPermission(this, permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permission.READ_PHONE_STATE)){

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle(R.string.imei_dialog_title)
                        .setMessage(R.string.imei_dialog_message)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                requestPermission();
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .create()
                        .show();
            }
            else{
                requestPermission();
            }

        } else {
            String IMEINumber;
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            IMEINumber = telephonyManager.getDeviceId();
            IMEI.setText(IMEINumber);
        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{permission.READ_PHONE_STATE},
                Constants.REQUEST_FOR_IMEI);
    }
}
