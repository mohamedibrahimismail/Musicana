package com.example.miestro.musicana;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MIESTRO on 27/08/2017.
 */

public class Send_Data_Comment extends StringRequest {

    private static final String url = "http://169.254.239.242:800/videos_store/add_comment.php";
    private Map<String,String> MapData;

    public Send_Data_Comment(String movie_id,String name,String comment_text, Response.Listener<String> listener) {
        super(Method.POST,url, listener, null);
        MapData = new HashMap<>();
        MapData.put("movie_id",movie_id);
        MapData.put("ename",name);
        MapData.put("comment_text",comment_text);


    }
    @Override
    public Map<String,String> getParams(){return MapData;}
}
