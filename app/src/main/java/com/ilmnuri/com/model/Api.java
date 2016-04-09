package com.ilmnuri.com.model;

import android.os.Environment;


public class Api {
    public static String BaseUrl = "http://ilmnuri.net/";

    public static String all_category = "http://api.ilmnuri.net/api/v1.0/albums/";
    public static String catetory1 = "http://api.ilmnuri.net/api/v1.0/albums/Abdulloh/";
    public static String catetory2 = "http://api.ilmnuri.net/api/v1.0/albums/AbuNur/";
    public static String catetory3 = "http://api.ilmnuri.net/api/v1.0/albums/Ayyubxon/";

    public static String catetory1_image = "http://images.ilmnuri.com.s3-website-us-west-2.amazonaws.com/ilm.jpg";
    public static String catetory2_image = "http://images.ilmnuri.com.s3-website-us-west-2.amazonaws.com/abunur.jpg";
    public static String catetory3_image = "http://images.ilmnuri.com.s3-website-us-west-2.amazonaws.com/ayyubxon.jpg";
    public static String localPath = Environment.getExternalStorageDirectory().toString() + "/ilmnuri";
}
