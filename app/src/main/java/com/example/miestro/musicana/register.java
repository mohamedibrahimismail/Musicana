package com.example.miestro.musicana;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class register extends Activity {

    EditText n,e,p1,p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        n = (EditText)findViewById(R.id.name);
        e = (EditText)findViewById(R.id.email);
        p1= (EditText)findViewById(R.id.pass1);
        p2 = (EditText)findViewById(R.id.pass2);

    }

    public void registe(View v){

        String name = n.getText().toString().trim();
        String email = e.getText().toString().trim();
        String pass1 = p1.getText().toString().trim();
        String pass2 = p2.getText().toString().trim();

        if(!pass1.equals(pass2)){

            Toast.makeText(register.this,"يجب كتابة الرقم السري بشكل صحيح" ,Toast.LENGTH_LONG).show();
        }else{

            Response.Listener<String> responselisener=new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {

                    //  textView.setText(""+response);
                    try {

                        JSONObject jsonResponse =new JSONObject(response);


                        String result = jsonResponse.getString("register");



                            Toast.makeText(register.this,result,Toast.LENGTH_LONG).show();
                        if(result.equals("successful registered")) {
                            finish();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            };

            Send_Data_Register send_data = new Send_Data_Register(name,email,pass1,responselisener);
            RequestQueue queue = Volley.newRequestQueue(register.this);
            queue.add(send_data);
        }

        }

    }

