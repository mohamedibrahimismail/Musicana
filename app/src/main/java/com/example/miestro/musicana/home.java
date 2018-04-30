package com.example.miestro.musicana;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class home extends Activity {

    RequestQueue requestQueue;
    String url ="http://169.254.239.242:800/videos_store/show_all_movies.php";
    ArrayList<listitem> listMovies = new ArrayList<listitem>();
    ListView listView;
    TextView text_username;

    Globalv globalv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        listView = (ListView)findViewById(R.id.listview);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      //  text_total=(TextView)findViewById(R.id.totalmusic);
        text_username=(TextView)findViewById(R.id.username);

        globalv =(Globalv)getApplicationContext();

        text_username.setText(""+globalv.getUser_name());
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("allvideos");
                          //  text_total.setText(" عدد الاغاني : "+jsonArray.length());

                            for(int i =0; i<jsonArray.length();i++){
                                JSONObject respons = jsonArray.getJSONObject(i);
                                String id = respons.getString("id");
                                String title = respons.getString("title");
                                String info = respons.getString("info");
                                String link = respons.getString("link");
                                String photolink = respons.getString("photo_link");
                                listMovies.add(new listitem(id,photolink,title,info,link));


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listData();
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

        ListAdapter ad=new ListAdapter(listMovies);
        listView.setAdapter(ad);


    }

    class ListAdapter extends BaseAdapter{

        ArrayList<listitem> listitem=new ArrayList<listitem>();

        ListAdapter(ArrayList<listitem> listitem){

            this.listitem = listitem ;
        }

        @Override
        public int getCount() {
            return listitem.size();
        }

        @Override
        public Object getItem(int position) {
            return listitem.get(position).title;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int  i, View convertView, ViewGroup parent) {


            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.row_item, null);

            TextView Title = (TextView) view.findViewById(R.id.text_title);
            TextView info = (TextView) view.findViewById(R.id.text_info);
            ImageView img = (ImageView) view.findViewById(R.id.image_movie);

            Title.setText(listitem.get(i).title);
            info.setText(listitem.get(i).info);

            Picasso.with(getApplicationContext()).load(listitem.get(i).img).into(img);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent OpenMovie = new Intent(home.this,movie.class);
                    OpenMovie.putExtra("text_title",listitem.get(i).title);
                    OpenMovie.putExtra("movie_id",listitem.get(i).id);
                    OpenMovie.putExtra("link",listitem.get(i).link);

                    startActivity(OpenMovie);



                }
            });

            return view;

        }
    }

}
