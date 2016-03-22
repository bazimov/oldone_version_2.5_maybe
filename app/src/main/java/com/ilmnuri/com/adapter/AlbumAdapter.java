package com.ilmnuri.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ilmnuri.com.PlayActivity;
import com.ilmnuri.com.R;
import com.ilmnuri.com.Utility.Utils;
import com.ilmnuri.com.model.AlbumModel;
import com.ilmnuri.com.model.Api;

/**
 * Created by Administrator on 3/5/2016.
 */
public class AlbumAdapter extends BaseAdapter {
    private AlbumModel albumModel;
    private Context context;

    public AlbumAdapter (Context context, AlbumModel albumModel)  {
        this.context = context;
        this.albumModel = albumModel;
    }

    @Override
    public int getCount() {
        return albumModel.getArrTrack().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (convertView == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, null);
        }
        RelativeLayout relativeLayout = (RelativeLayout)v.findViewById(R.id.rl_item_album);
        TextView textView = (TextView)v.findViewById(R.id.tv_item_album);
        ImageView imageView = (ImageView)v.findViewById(R.id.iv_item_album);
        ImageButton btnDelete = (ImageButton)v.findViewById(R.id.btn_delete);

        textView.setText(albumModel.getArrTrack().get(position).replace(".mp3", "").replace("_", " "));
        if (Utils.checkFileExist(Api.localPath + "/" + albumModel.getArrTrack().get(position))) {
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                intent.putExtra("category", albumModel.getCategory());
                intent.putExtra("url",albumModel.getCategory() + "/" + albumModel.getAlbum() + "/" + albumModel.getArrTrack().get(position));
                context.startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.deleteFile(Api.localPath + "/" + albumModel.getArrTrack().get(position));
                notifyDataSetChanged();
            }
        });
        return v;
    }


}
