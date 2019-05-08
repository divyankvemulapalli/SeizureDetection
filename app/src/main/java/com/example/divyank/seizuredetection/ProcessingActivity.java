package com.example.divyank.seizuredetection;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcessingActivity extends AppCompatActivity {

    public AnimatedCircleLoadingView animatedCircleLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);
        animatedCircleLoadingView = (AnimatedCircleLoadingView) findViewById(R.id.circle_loading_view);
        startLoading();
        startPercentMockThread();





    }

    private void responseEntropy()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.d("IN res", "rsuccess");
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");


                    if (success) {



                        String phone = jsonResponse.getString("ph");
                        String fphone= jsonResponse.getString("fph");
                        String shannon= jsonResponse.getString("shannon");
                        String log= jsonResponse.getString("log");
                        String norm= jsonResponse.getString("norm");
                        String state= jsonResponse.getString("state");
                        String pname= jsonResponse.getString("name");
                        String fname = jsonResponse.getString("fname");

                        Intent intent = new Intent(ProcessingActivity.this, DisplayActivity.class);
                        intent.putExtra("shannon", shannon);
                        intent.putExtra("log", log);
                        intent.putExtra("norm", norm);
                        intent.putExtra("state", state);
                        intent.putExtra("pname",pname);
                        intent.putExtra("fphone",fphone);
                        intent.putExtra("fname",fname);




                        ProcessingActivity.this.startActivity(intent);

                    }
                    else
                        animatedCircleLoadingView.stopFailure();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        EntropyRequest entropyRequest = new EntropyRequest("1", "2", responseListener);
        RequestQueue queue = Volley.newRequestQueue(ProcessingActivity.this);
        queue.add(entropyRequest);
    }

    private void startLoading() {
        animatedCircleLoadingView.startDeterminate();
    }

    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(500);
                        changePercent(i);
                    }

                    responseEntropy();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();

    }

    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.setPercent(percent);
            }
        });


    }

    public void resetLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.resetLoading();
            }
        });
    }
}