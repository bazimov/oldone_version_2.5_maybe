package com.ilmnuri.com;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.ApplicationController;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ilmnuri.com.Utility.ExceptionHandler;
import com.ilmnuri.com.Utility.Utils;
import com.ilmnuri.com.model.AlbumModel;
import com.ilmnuri.com.model.Api;
import com.ilmnuri.com.model.Global;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private final String TAG_REQUEST = "MY_TAG";
    private RequestQueue mVolleyQueue;
    private ArrayList<AlbumModel> albumModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ///set exception handler
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        getList();


    }
    private void getList() {


        // Initialise Volley Request Queue.
        mVolleyQueue = Volley.newRequestQueue(this);
        albumModels = new ArrayList<>();

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, Api.all_category, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray arrAlbum = response.getJSONArray("albums");
                    int length = arrAlbum.length();
                    ArrayList<AlbumModel> arrAlbums = new ArrayList<>();
                    for (int i = 0; i < length; i ++) {

                        JSONObject albumObject = arrAlbum.getJSONObject(i);

                        AlbumModel  albumModel = new AlbumModel();

                        albumModel.setCategory(albumObject.getString("category"));
                        albumModel.setAlbum(albumObject.getString("album"));
//                        albumModel.setId(Integer.parseInt(albumObject.getString("id")));
                        ArrayList<String> arrayList = new ArrayList<>();
                        JSONArray jsonArray = albumObject.getJSONArray("items");
                        for(int k = 0; k < jsonArray.length(); k ++ ) {
                            arrayList.add(jsonArray.getString(k));
                        }
                        albumModel.setArrTrack(arrayList);

                        arrAlbums.add(albumModel);
                    }
//                    QuickSort quickSort = new QuickSort(arrAlbums);
//                    quickSort.sort();
                    Global.getInstance().setArrayList(arrAlbums);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Do something after 2s = 2000ms

                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                            ApplicationController.getInstance().getRequestQueue().getCache().invalidate(Api.all_category, true);
                            mVolleyQueue.getCache().invalidate(Api.all_category, true);
                            finish();
                        }
                    }, 2000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                // For AuthFailure, you can re login with user credentials.
                // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                // In this case you can check how client is forming the api and debug accordingly.
                // For ServerError 5xx, you can do retry or handle accordingly.
                Utils.showToast(SplashActivity.this, error.toString());
                if( error instanceof NetworkError) {
                } else if( error instanceof ServerError) {
                } else if( error instanceof AuthFailureError) {
                } else if( error instanceof ParseError) {
                } else if( error instanceof NoConnectionError) {
                } else if( error instanceof TimeoutError) {
                }

            }
        });

        //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjRequest.setTag(TAG_REQUEST);
        mVolleyQueue.add(jsonObjRequest);
    }

}
