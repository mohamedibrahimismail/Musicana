package com.example.miestro.musicana;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class movie extends YouTubeBaseActivity  {

    VideoView videoView;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    ArrayList<list_item_comments> listComments = new ArrayList<list_item_comments>();

    LinearLayout linearLayout;
    ListView listView;
    ImageButton btn_add_like;
    TextView text_title,text_total_comments,text_total_likes;

    String movie_id;

    Globalv globalv;
    String user_name;

    EditText Etxt_comment;

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_movie);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //videoView = (VideoView)findViewById(R.id.videoView);
        text_title = (TextView)findViewById(R.id.text_title);
        listView = (ListView)findViewById(R.id.commentlistview);

        text_total_likes = (TextView)findViewById(R.id.text_total_likes);
        text_total_comments = (TextView)findViewById(R.id.text_total_comments);

        linearLayout = (LinearLayout)findViewById(R.id.linearlayoutcomment);
        btn_add_like = (ImageButton)findViewById(R.id.imageButtonLike);

        Etxt_comment = (EditText)findViewById(R.id.Etxt_comment);

        globalv = (Globalv) getApplicationContext();

        user_name = globalv.getUser_name();


        Intent data = getIntent();

     final  String link = data.getExtras().getString("link");
        //  Uri uri = Uri.parse(link);
        movie_id = data.getExtras().getString("movie_id");
        text_title.setText(data.getExtras().getString("text_title").trim());


        youTubePlayerView =(YouTubePlayerView)findViewById(R.id.youtube_playerview);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.setShowFullscreenButton(false);
                youTubePlayer.loadVideo(link);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youTubePlayerView.initialize(playerconfig.API_KEY,onInitializedListener);

       // progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        //assert progressBar != null;
      //  progressBar.bringToFront();

/*
        Intent data = getIntent();

     //   String link = data.getExtras().getString("link");
      //  Uri uri = Uri.parse(link);
        movie_id = data.getExtras().getString("movie_id");
        text_title.setText(data.getExtras().getString("text_title").trim());
/*
        MediaController mediaController = new MediaController(this);
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(this));
        mediaController.setAnchorView(videoView);
        videoView.requestFocus();
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                progressBar.setVisibility(View.INVISIBLE);
            }
        });

*/
        get_comments();
       get_mlike();


    }

    public void get_comments(){

        String url = "http://169.254.239.242:800/videos_store/show_comments.php?movie_id="+movie_id;
        requestQueue = Volley.newRequestQueue(this);
        listComments.clear();



        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("comments");
                            text_total_comments.setText(String.valueOf(jsonArray.length()));

                            for(int i =0; i<jsonArray.length();i++){
                                JSONObject respons = jsonArray.getJSONObject(i);
                                String id = respons.getString("id");
                                String name = respons.getString("name");
                                String text = respons.getString("text");
                                String date_time = respons.getString("date_time");
                                listComments.add(new list_item_comments(id,name,text,date_time));


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listData();
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", "VOLLY");
            }
        }

        );

        requestQueue.add(jsonObjectRequest);






    }



    public void get_mlike() {




        String url = "http://169.254.239.242:800/videos_store/show_mlikes.php?movie_id="+movie_id;
        requestQueue = Volley.newRequestQueue(this);

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {

                        StringBuilder text = new StringBuilder();

                        try {
                            JSONArray jsonArray = response.getJSONArray("mlikes");
                            text_total_likes.setText(jsonArray.length()+"");


                            for(int i =0; i<jsonArray.length();i++){
                                JSONObject respons = jsonArray.getJSONObject(i);

                                if(respons.getString("name").equals(user_name)){
                                    btn_add_like.setEnabled(false);
                                    btn_add_like.setImageResource(R.drawable.like_disabled);
                                }
                                text.append(respons.getString("name"));


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                  /*      if (text.toString().contains(user_name)) {



                        }*/


                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY","VOLLY");
            }
        }

        );

        requestQueue.add(jsonObjectRequest);




    }


    public void listData(){

        ListAdapter adapter = new ListAdapter(listComments);
        listView.setAdapter(adapter);


    }


    public void Like(View view){


        String url = "http://169.254.239.242:800/videos_store/add_mlikes.php?movie_id="+movie_id+"&ename="+user_name;
       RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){

                                Toast.makeText(movie.this,"تم اضافة اعجاب",Toast.LENGTH_LONG).show();
                                btn_add_like.setEnabled(false);
                                btn_add_like.setImageResource(R.drawable.like_disabled);
                                get_mlike();

                            }else {

                                Toast.makeText(movie.this,"عفوا لم يتم اضافة اعجاب",Toast.LENGTH_LONG).show();

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }




                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                }
        });
// Add the request to the RequestQueue.

        requestQueue.add(stringRequest);
    }

    public void  comment(View view){

        listView.setVisibility(View.INVISIBLE);
         linearLayout.setVisibility(View.VISIBLE);

    }

    public void  post(View view){


        String comment_text = Etxt_comment.getText().toString().trim();

        if(comment_text.equals("")){

            Toast.makeText(movie.this,"يجب كتابة تعليق",Toast.LENGTH_LONG).show();

        }else{

            Response.Listener<String> responselisener = new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){

                            Toast.makeText(movie.this,"تم ارسال التعليق",Toast.LENGTH_LONG).show();
                            linearLayout.setVisibility(View.INVISIBLE);
                            listView.setVisibility(View.VISIBLE);
                            get_comments();

                        }else{

                            Toast.makeText(movie.this,"حدث خطأ",Toast.LENGTH_LONG).show();
                            get_comments();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            };

        Send_Data_Comment send_data_comment = new Send_Data_Comment(movie_id,user_name,comment_text,responselisener);
        RequestQueue queue = Volley.newRequestQueue(movie.this);
         queue.add(send_data_comment);
                }







    }

    class ListAdapter extends BaseAdapter{


        ArrayList<list_item_comments> list_c = new ArrayList<list_item_comments>();
        ListAdapter(ArrayList<list_item_comments> listitem){
            this.list_c = listitem;
        }


        @Override
        public int getCount() {
            return list_c.size();
        }

        @Override
        public Object getItem(int position) {
            return list_c.get(position).name;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.row_item_comments,null);

            TextView date_time = (TextView) view.findViewById(R.id.date_timecomment);
            TextView name = (TextView) view.findViewById(R.id.usernamecomment);
            TextView text = (TextView) view.findViewById(R.id.textcomment);

            date_time.setText(list_c.get(position).date_time+"");
            name.setText(list_c.get(position).name+"");
            text.setText(list_c.get(position).text+"");

            return view;
        }
    }
}
