package com.ilmnuri.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ilmnuri.com.adapter.AlbumAdapter;
import com.ilmnuri.com.model.AlbumModel;

import java.util.ArrayList;


public class AlbumActivity extends AppCompatActivity {

    private AlbumAdapter albumAdapter;

    private ListView listView;
    private AlbumModel albumModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        albumModel = (AlbumModel)getIntent().getSerializableExtra("album");
       initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tvTitle = (TextView)toolbar.findViewById(R.id.tv_album_title);
        tvTitle.setText(albumModel.getCategory() + "/" + albumModel.getAlbum());
//        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("Album Name");

        listView = (ListView)findViewById(R.id.lv_album);
        albumAdapter = new AlbumAdapter(AlbumActivity.this, albumModel);
        listView.setAdapter(albumAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    ///for test
    private ArrayList<String> generateTestData() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 30; i ++ ) {


            arrayList.add("Track " + String.valueOf(i));
        }
        return arrayList;
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_second, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
