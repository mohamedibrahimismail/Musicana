package com.example.miestro.musicana;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActivityChooserView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import static android.R.attr.visible;

public class MainActivity extends Activity {

    TextView textView;
    LinearLayout linearLayout;
    EditText login_name,login_password;
    Globalv globalv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        linearLayout = (LinearLayout)findViewById(R.id.linear);
        login_name = (EditText)findViewById(R.id.login_name);
        login_password = (EditText)findViewById(R.id.login_password);
        textView=(TextView)findViewById(R.id.t);
        globalv=(Globalv)getApplicationContext();
    }

    public void loginform(View v){

        linearLayout.setVisibility(View.VISIBLE);

    }

    public void register(View v){

        Intent intent = new Intent(this,register.class);
        startActivity(intent);

    }

    public void login(View v){

        final String name=login_name.getText().toString().trim();
        String password=login_password.getText().toString().trim();


        Response.Listener<String> responselisener=new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

              //  textView.setText(""+response);
                 try {

                    JSONObject jsonResponse =new JSONObject(response);


                    boolean success = jsonResponse.getBoolean("success");

                    if(success){

                        Toast.makeText(MainActivity.this,"تم تسجيل الدخول",Toast.LENGTH_SHORT).show();
                        linearLayout.setVisibility(View.INVISIBLE);
                        globalv.setUser_name(name);
                        startActivity(new Intent(MainActivity.this,home.class));


                    }else{
                        Toast.makeText(MainActivity.this,"البيانات غير صحيحة",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

        Send_Data_Login send_data = new Send_Data_Login(name,password,responselisener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(send_data);
    }
}
