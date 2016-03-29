package com.ilmnuri.com;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

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
                            mVolleyQueue.getCache().invalidate(Api.all_category, true);
                            finish();
                        }
                    }, 2000);

                } catch (Exception e) {
                    Utils.showToast(SplashActivity.this, "Ana bo'lmasam, bunaqasini kutmagandik. " +
                            "Qandaydir hato bo'ldi, iltimos appni butunlay yopib qayta oching!");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.showToast(SplashActivity.this, "Internet yo'qqa o'hshaydi! Hozircha internetsiz app ishlamaydi, " +
                        "uzur. Agar internet bor bo'lsa appni yopib qayta ochib ko'ring.");
                if( error instanceof NetworkError) {
                    Utils.showToast(SplashActivity.this, "Ana bo'lmasam, bunaqasini kutmagandik. " +
                            "Yoki internet yo'q, yoki hotiraga ruhsat berilmagan. Appni " +
                            "o'chirib qayta yo'qib ko'ring.");
                } else if( error instanceof ServerError) {
                    Utils.showToast(SplashActivity.this, "Ana bo'lmasam, bunaqasini kutmagandik. " +
                            "o'chirib qayta yo'qib ko'ring. Yoki bizga habar qiling ilmnuri@ilmnuri.com");
                } else if( error instanceof AuthFailureError) {
                    Utils.showToast(SplashActivity.this, "Ana bo'lmasam, bunaqasini kutmagandik. " +
                            "Appni o'chirib qayta yo'qib ko'ring.");
                } else if( error instanceof ParseError) {
                    Utils.showToast(SplashActivity.this, "Ana bo'lmasam, bunaqasini kutmagandik. " +
                            "Appni o'chirib qayta yo'qib ko'ring.");
                } else if( error instanceof TimeoutError) {
                    Utils.showToast(SplashActivity.this, "Ana bo'lmasam, bunaqasini kutmagandik. " +
                            "Yoki internet yo'q, yoki hotiraga ruhsat berilmagan. Appni " +
                            "o'chirib qayta yo'qib ko'ring.");
                }

            }
        });

        //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjRequest.setTag(TAG_REQUEST);
        mVolleyQueue.add(jsonObjRequest);
    }

}
