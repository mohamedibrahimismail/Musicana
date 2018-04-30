package com.example.miestro.musicana;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MIESTRO on 15/08/2017.
 */

public class Send_Data_Login extends StringRequest {

    private static final String url="http://169.254.239.242:800/videos_store/login.php";
    private Map<String,String> Mapdata;

    public Send_Data_Login(String name, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener,null);
        Mapdata = new HashMap<>();
        Mapdata.put("name",name);
        Mapdata.put("password",password);
    }

    @Override
    public Map<String,String> getParams(){return Mapdata;}

}
