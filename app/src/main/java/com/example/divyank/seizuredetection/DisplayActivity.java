package com.example.divyank.seizuredetection;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class DisplayActivity extends AppCompatActivity {

    private TrackGPS gps;
    double longitude;
    double latitude;
    public String address="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        TextView shannon = (TextView) findViewById(R.id.textView3);
        TextView log = (TextView) findViewById(R.id.textView5);
        TextView norm = (TextView) findViewById(R.id.textView7);

        Intent intent = getIntent();
        String shannon1 = intent.getStringExtra("shannon");
        String log1 = intent.getStringExtra("log");
        String norm1 = intent.getStringExtra("norm");
        String state = intent.getStringExtra("state");
        String pname = intent.getStringExtra("pname");
        String fphone = intent.getStringExtra("fphone");
        String fname = intent.getStringExtra("fname");
        RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);

        if(state.equals("normal"))
        {
            radioButton1.toggle();
            radioGroup.getChildAt(2).setEnabled(false);
            radioGroup.getChildAt(1).setEnabled(false);
        }

        if(state.equals("ictal") || state.equals("preictal"))
        {
            if(state.equals("ictal"))
            {
                radioButton3.toggle();
                radioButton1.toggle();
                radioGroup.getChildAt(0).setEnabled(false);
                radioGroup.getChildAt(1).setEnabled(false);
            }
            else {
                radioGroup.getChildAt(0).setEnabled(false);
                radioGroup.getChildAt(3).setEnabled(false);
                radioButton2.toggle();
                radioButton1.toggle();
            }
            LocationAddress locationAddress = new LocationAddress();
            radioButton3.toggle();
            gps = new TrackGPS(this);



            if(gps.canGetLocation()){


                longitude = gps.getlongitude();
                latitude = gps .getlatitude();

                Toast.makeText(this,Double.toString(latitude)+","+Double.toString(longitude),Toast.LENGTH_SHORT).show();

                address = locationAddress.getAddressFromLocation(latitude, longitude,getApplicationContext());

                Toast.makeText(this,address,Toast.LENGTH_SHORT).show();

                String location ="http://www.google.com/maps/place/"+Double.toString(latitude)+","+Double.toString(longitude);

                String message = "ALERT!"+"\n"+pname+" needs your help "+"\n"+"Address :\n"+address;

                String message1="Google maps link"+"\n"+location;


                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(fphone, null, message, null, null);
                smsManager.sendTextMessage(fphone, null, message1, null, null);
                Toast.makeText(this, "SMS sent to "+fname, Toast.LENGTH_LONG).show();

            }
            else
            {

                gps.showSettingsAlert();
            }
        }else
            if(state.equals("preictal"))
            {
                radioButton2.toggle();
            }
            else
            {
                radioButton1.toggle();
            }


        shannon.setText(shannon1);
        log.setText(log1);
        norm.setText(norm1);


    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button120:
                final AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);
                builder.setMessage("You want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                new Send().execute();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
        }
    }


    class Send extends AsyncTask<String, Void,Long > {



        protected Long doInBackground(String... urls) {



            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://cherrythecoder.000webhostapp.com/logout.php");

            try {


                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (Exception e) {
                // TODO Auto-generated catch block

                Toast.makeText(getApplicationContext(),"Logout Unsuccessful",Toast.LENGTH_LONG).show();

            }


            return null;

        }
        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }
    }


    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);
        builder.setMessage("You want to logout?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        new Send().execute();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();

        // your code.
    }
}
