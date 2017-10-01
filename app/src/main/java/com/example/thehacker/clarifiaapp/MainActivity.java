package com.example.thehacker.clarifiaapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.workflow.WorkflowPredictResult;
import clarifai2.exception.ClarifaiException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.R.id.input;
import static android.R.id.message;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isNetworkOk()){
            final ClarifaiClient client = new ClarifaiBuilder("c81464bcf9a44c0bbf9d51b72e08659e")
                    .client(new OkHttpClient())
                    .buildSync();
            client.workflowPredict("MyWorkflow")
                    .withInputs(ClarifaiInput.forImage("http://www.pachd.com/free-images/food-images/babanas-01.jpg")).executeAsync(new ClarifaiRequest.Callback<WorkflowPredictResult>() {
                @Override
                public void onClarifaiResponseSuccess(WorkflowPredictResult workflowPredictResult) {
                    System.out.println("Request succesful");
                    Log.v(TAG, "Here are the results: " + workflowPredictResult.toString());
                }

                @Override
                public void onClarifaiResponseUnsuccessful(int errorCode) {

                }

                @Override
                public void onClarifaiResponseNetworkError(IOException e) {

                }
            });







    }
}

    private boolean isNetworkOk() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isGood = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isGood = true;
        }
        return isGood;
    }
}
