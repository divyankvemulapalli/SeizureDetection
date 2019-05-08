package com.example.divyank.seizuredetection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import static com.example.divyank.seizuredetection.MainActivity.isNetworkStatusAvialable;


public class RegisterActivity extends AppCompatActivity {


    EditText name;
    EditText pass;
    EditText age;
    EditText sex;
    EditText num;
    EditText add;
    EditText fname;
    EditText fnum;

    Boolean sent = false;

    String pname;
    String ppass;
    String page;
    String psex;
    String padd ;
    String pnum;
    String pfname;
    String pfnum;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        name = (EditText)findViewById(R.id.editText3);
        pass = (EditText)findViewById(R.id.editText4);
        age = (EditText)findViewById(R.id.editText5);
         sex = (EditText)findViewById(R.id.editText6);
        add = (EditText)findViewById(R.id.editText7);
        num = (EditText)findViewById(R.id.editText8);
        fname = (EditText)findViewById(R.id.editText9);
        fnum = (EditText)findViewById(R.id.editText10);

        Button regButton = (Button) findViewById(R.id.regbutton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptRegister();

            }
        });



    }


    private void attemptRegister() {

        // Reset errors.
        name.setError(null);
        pass.setError(null);
        age.setError(null);
        sex.setError(null);
        num.setError(null);
        add.setError(null);
        fname.setError(null);
        fnum.setError(null);

        // Store values at the time of the login attempt.

        pname = name.getText().toString();
         ppass = pass.getText().toString();
         page = age.getText().toString();
         psex = sex.getText().toString();
         padd = add.getText().toString();
         pnum = num.getText().toString();
         pfname = fname.getText().toString();
         pfnum = fnum.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.


        if (TextUtils.isEmpty(pname)) {
            name.setError(getString(R.string.error_invalid_name));
            focusView = name;
            cancel = true;
        }

        if (TextUtils.isEmpty(ppass)) {
            pass.setError(getString(R.string.error_invalid_password));
            focusView = pass;
            cancel = true;
        }else if (!(ppass.length()>3))
        {
            pass.setError(getString(R.string.error_invalid_password_length));
            focusView = pass;
            cancel = true;
        }



        if (TextUtils.isEmpty(psex)) {
            sex.setError(getString(R.string.error_invalid_sex));
            focusView = sex;
            cancel = true;
        }/*else if (!(psex.matches("[a-z][A-Z]]")))
        {
            sex.setError(getString(R.string.error_invalid_sex_length));
            focusView = sex;
            cancel = true;
        }*/

        if (TextUtils.isEmpty(page)) {
            age.setError(getString(R.string.error_invalid_age));
            focusView = age;
            cancel = true;
        }else if (!(page.matches("[0-9]{1,2}")))
        {
            age.setError(getString(R.string.error_invalid_age_length));
            focusView = age;
            cancel = true;
        }

        if (TextUtils.isEmpty(padd)) {
            add.setError(getString(R.string.error_invalid_add));
            focusView = add;
            cancel = true;
        }

        if (TextUtils.isEmpty(pfname)) {
            fname.setError(getString(R.string.error_invalid_fname));
            focusView = fname;
            cancel = true;
        }

        if (TextUtils.isEmpty(pnum)) {
            num.setError(getString(R.string.error_invalid_moblie));
            focusView = num;
            cancel = true;
        }else if (!(pnum.matches("[0-9]{10}")))
        {
            num.setError(getString(R.string.error_invalid_moblie_length));
            focusView = num;
            cancel = true;
        }

        if (TextUtils.isEmpty(pfnum)) {
            fnum.setError(getString(R.string.error_invalid_moblie_length));
            focusView = fnum;
            cancel = true;
        }else if (!(pfnum.matches("[0-9]{10}")))
        {
            fnum.setError(getString(R.string.error_invalid_mobile_number_length));
            focusView = fnum;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if(isNetworkStatusAvialable (getApplicationContext())) {

            } else {
                Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                finish();

            }

            new Send().execute();
            Toast.makeText(getApplicationContext(),"Uploaded Successfully",Toast.LENGTH_LONG).show();


        }
    }


    class Send extends AsyncTask<String, Void,Long > {



        protected Long doInBackground(String... urls) {



            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://cherrythecoder.000webhostapp.com/phpcode.php");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("Name", pname));
                nameValuePairs.add(new BasicNameValuePair("Password", ppass));
                nameValuePairs.add(new BasicNameValuePair("Age", page));
                nameValuePairs.add(new BasicNameValuePair("Sex", psex));
                nameValuePairs.add(new BasicNameValuePair("Address", padd));
                nameValuePairs.add(new BasicNameValuePair("Mobileno", pnum));
                nameValuePairs.add(new BasicNameValuePair("Familyname", pfname));
                nameValuePairs.add(new BasicNameValuePair("FamilyMobileno", pfnum));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                //sent=true;

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

                sent=true;



            } catch (Exception e) {
                // TODO Auto-generated catch block

                Toast.makeText(getApplicationContext(),"Uploaded Unsuccessfully",Toast.LENGTH_LONG).show();

            }


            finally {
                if (sent)
                {
                    finish();
                }
            }
            return null;

        }
        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }
    }


}



