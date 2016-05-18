package com.ilmnuri.com;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ///set exception handler
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        getList();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getList() {

        // Initialise Volley Request Queue.
        RequestQueue mVolleyQueue = Volley.newRequestQueue(this);

        if (isNetworkAvailable()) {
            mVolleyQueue.getCache().clear();
        }

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
                        ArrayList<String> arrayList = new ArrayList<>();
                        JSONArray jsonArray = albumObject.getJSONArray("items");
                        for(int k = 0; k < jsonArray.length(); k ++ ) {
                            arrayList.add(jsonArray.getString(k));
                        }
                        albumModel.setArrTrack(arrayList);

                        arrAlbums.add(albumModel);
                    }

                    Global.getInstance().setArrayList(arrAlbums);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                        }
                    }, 2000);

                } catch (Exception e) {
                    Utils.showToast(SplashActivity.this, "Qandaydir hato bo'ldi, Bizningcha, internet yo'q va keshda ham darsliklar topilmadi.");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.showToast(SplashActivity.this, "Xato! INTERNET yo'q va keshda ham darslik topilmadi!");
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                if( error instanceof NetworkError) {
                    Utils.showToast(SplashActivity.this, "Tarmoqda biron bir xatolik bo'ldi! Kesh quruq ekan, internet kerak.");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else if( error instanceof ServerError) {
                    Utils.showToast(SplashActivity.this, "Ilmnuri serverlarida xato bo'ldi. Yoki bizga habar qiling ilmnuri@ilmnuri.com");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else if( error instanceof AuthFailureError) {
                    Utils.showToast(SplashActivity.this, "Ana bo'lmasam, bunaqasini kutmagandik. " +
                            "Appni o'chirib qayta yo'qib ko'ring.");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else if( error instanceof ParseError) {
                    Utils.showToast(SplashActivity.this, "ilmnuri API larida xatolik bor.");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else if( error instanceof TimeoutError) {
                    Utils.showToast(SplashActivity.this, "Tarmoq uzoq kutishlik natijasida uzulib qoldi.");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }

            }
        });

        //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String TAG_REQUEST = "MY_TAG";
        jsonObjRequest.setTag(TAG_REQUEST);
        mVolleyQueue.add(jsonObjRequest);
    }

}
