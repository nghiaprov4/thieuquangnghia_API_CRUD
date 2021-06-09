package com.example.mockingapi_thinking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnGet,btnDelete,btnGetJson,btnPut,btnPost;

    RecyclerView reView;
    RecyclerAdapter adapter;
    EditText txtID,txtName,txtCountry;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> list = new ArrayList<>();

    String url = "https://60b649fb17d1dc0017b8779e.mockapi.io/MyDuyen/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reView=findViewById(R.id.reView);

        btnGet = (Button) findViewById(R.id.btnGet);
        btnDelete=findViewById(R.id.btnDelete);
        btnGetJson=findViewById(R.id.btnGetJSon);
        btnPost=findViewById(R.id.btnPost);
        btnPut=findViewById(R.id.btnPut);

        txtID=findViewById(R.id.txtId);
        txtName=findViewById(R.id.txtName);
        txtCountry=findViewById(R.id.txtCountry);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetArrayJson(url);

            }
        });
        btnGetJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetJson(url+txtID.getText().toString().trim());
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteApi(url);
                txtID.setText("");
                txtName.setText("");
                txtCountry.setText("");
                recreate();
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               PostApi(url);
               txtID.setText("");
               txtName.setText("");
               txtCountry.setText("");
               recreate();
            }
        });
        btnPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutApi(url);
                txtID.setText("");
                txtName.setText("");
                txtCountry.setText("");
                recreate();
            }
        });
    }
    private void GetData(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,
                        "Error make by API server!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void GetJson(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            list.clear();
                            User user = new User();
                            user.setId(response.getString("id"));
                            user.setName(response.getString("name"));
                            user.setCountry(response.getString("country"));
                            list.add(user);
                            Toast.makeText(MainActivity.this,
                                    list+"", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        layoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                        adapter = new RecyclerAdapter(list);
                        reView.setAdapter(adapter);
                        reView.setLayoutManager(layoutManager);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by get JsonObject...", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    private void GetArrayJson(String url){
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                list.clear();
                                for(int i=0; i<response.length(); i++){
                                    try {
                                        JSONObject object = (JSONObject) response.get(i);
                                        User user = new User();
                                        user.setId(object.getString("id"));
                                        user.setName(object.getString("name"));
                                        user.setCountry(object.getString("country"));
                                        list.add(user);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                layoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                                adapter = new RecyclerAdapter(list);
                                reView.setAdapter(adapter);
                                reView.setLayoutManager(layoutManager);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void PostApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String>
                        params = new HashMap<>();
                params.put("name", txtName.getText().toString().trim());
                params.put("country", txtCountry.getText().toString().trim());
//                txtPost.setText(params.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void PutApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT, url + '/' + txtID.getText().toString().trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", txtName.getText().toString().trim());
                params.put("country", txtCountry.getText().toString().trim());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void DeleteApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url + '/' + txtID.getText().toString().trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}