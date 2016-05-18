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


public class AlbumActivity extends AppCompatActivity {

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
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        assert toolbar != null;
        TextView tvTitle = (TextView)toolbar.findViewById(R.id.tv_album_title);

        tvTitle.setText(albumModel.getCategory() + "/" + albumModel.getAlbum());

        ListView listView = (ListView) findViewById(R.id.lv_album);
        AlbumAdapter albumAdapter = new AlbumAdapter(AlbumActivity.this, albumModel);
        assert listView != null;
        listView.setAdapter(albumAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

}
