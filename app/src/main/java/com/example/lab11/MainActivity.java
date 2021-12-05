package com.example.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText e1 = (EditText) findViewById(R.id.ip1);
        EditText e2 = (EditText) findViewById(R.id.ip2);
        EditText e3 = (EditText) findViewById(R.id.ip3);
        EditText e4 = (EditText) findViewById(R.id.ip4);

        e1.setFilters(new InputFilter[]{new InputFilterMinMax(0,255)});
        e2.setFilters(new InputFilter[]{new InputFilterMinMax(0,255)});
        e3.setFilters(new InputFilter[]{new InputFilterMinMax(0,255)});
        e4.setFilters(new InputFilter[]{new InputFilterMinMax(0,255)});


    }

    public void buttonPress(View view){  getIPInfo(); }


    private String getIP() {

        EditText e1 = (EditText) findViewById(R.id.ip1);
        EditText e2 = (EditText) findViewById(R.id.ip2);
        EditText e3 = (EditText) findViewById(R.id.ip3);
        EditText e4 = (EditText) findViewById(R.id.ip4);

        String ip = e1.getText().toString() + "." + e2.getText().toString() + "." +
                e3.getText().toString() + "." + e4.getText().toString();

        Log.i("-->",ip);

        return ip;
    }

    private void printInfo(IPInfo info) {
        TextView textView = (TextView) findViewById(R.id.textView);

        String s;

        if(info == null) s = "Faild";
        else {
            s = "ip: " + info.getIp() + "\n" +
                    "hostname: " + info.getHostname() + "\n" +
                    "city: " + info.getCity() + "\n" +
                    "region: " + info.getRegion() + "\n" +
                    "country: " + info.getCountry() + "\n" +
                    "loc: " + info.getLoc() + "\n" +
                    "org: " + info.getOrg() + "\n" +
                    "postal: " + info.getPostal() + "\n" +
                    "timezone: " + info.getTimezone() + "\n" +
                    "readme: " + info.getReadme() + "\n";


        }
        textView.setText(s);
    }


    private void getIPInfo(){

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class, getIP());

        Call<IPInfo> call = apiInterface.getIPInfo();

        call.enqueue(new Callback<IPInfo>() {
            @Override
            public void onResponse(Call<IPInfo> call, Response<IPInfo> response) {
                printInfo(response.body());
            }

            @Override
            public void onFailure(Call<IPInfo> call, Throwable t) {
                printInfo(null);
            }
        });
    }

}