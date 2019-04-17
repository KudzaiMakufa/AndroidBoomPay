package com.boomp.pay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);




        Button btnrecharge = (Button)findViewById(R.id.btnrecharge);
        final EditText phoneno = (EditText)findViewById(R.id.editText) ;
        final EditText points = (EditText)findViewById(R.id.editText2) ;

        btnrecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(phoneno.getText().toString().equals("") && points.getText().toString().equals("")){

                    Toast.makeText(HomeScreen.this, "Fill in all the required fields", Toast.LENGTH_SHORT).show();

                }
                else if(points.getText().toString().equals("0")){
                    Toast.makeText(HomeScreen.this, "Invalid Points selected", Toast.LENGTH_SHORT).show();
                }
                else if(phoneno.getText().toString().length() < 9){
                    Toast.makeText(HomeScreen.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }

                else{


                AlertDialog.Builder alertWrong = new AlertDialog.Builder(HomeScreen.this);


                alertWrong.setMessage("Recharging account for '" + phoneno.getText().toString() + "'  \n" + "Points " + points.getText().toString() + "\nAmount per point " + 5 + "\nTotal Cost "
                        + "$"+Integer.parseInt(points.getText().toString()) * 5).setCancelable(false)
                        .setPositiveButton("recharge", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = "http://172.28.0.172/autoboom/public/park/recharge";
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("phone", phoneno.getText().toString());
                                params.put("points", points.getText().toString());

                                CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {


                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {


                                            if (response.getString("login").equals("success")) {


                                                Toast.makeText(HomeScreen.this, "saved ", Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (Exception e) {
                                            Toast.makeText(HomeScreen.this, "failed", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError response) {
                                        Toast.makeText(HomeScreen.this, "Connection to server failed  ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                AppController.getInstance().addToRequestQueue(jsObjRequest);
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = alertWrong.create();
                alert.setTitle("Account Recharge");
                alert.show();


            }
        }

        });
    }
}
