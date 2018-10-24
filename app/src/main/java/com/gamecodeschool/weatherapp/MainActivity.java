package com.gamecodeschool.weatherapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gamecodeschool.models.Forecast;
import com.gamecodeschool.models.WeatherModel;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    TextView txtCity, txtDate, txtDegree;
    ImageView imageMain;
    String mCityName, ipUrl = "https://ipapi.co/json";
    ListView listViewForcast;
    ArcProgress arcProgress;
    ProgressDialog dialog;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidgets();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait");
        dialog.setMessage("Loading data from server");

        batteryLevel();
        getIPData();
    }

    void bindWidgets() {
        txtCity = findViewById(R.id.txtCity);
        txtDate = findViewById(R.id.txtDate);
        txtDegree = findViewById(R.id.txtDegree);
        listViewForcast = findViewById(R.id.listViewForcast);
        imageMain = findViewById(R.id.imageMain);
        arcProgress = findViewById(R.id.arc_progress);
    }

    public void getIPData() {
        final AsyncHttpClient client = new AsyncHttpClient();
        dialog.show();
        client.get(ipUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Get IP Detail Connection failed.", Toast.LENGTH_LONG).show();
                flag = false;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                parseResponseOfIPData(responseString);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (!flag)
                    dialog.dismiss();
            }
        });
    }

    public void parseResponseOfIPData(String response) {
        try {
            JSONObject js = new JSONObject(response);
            mCityName = js.getString("city");
            getWeatherData();
        } catch (Exception e) {
            Toast.makeText(this, "Failed parsing response.", Toast.LENGTH_LONG).show();
        }
    }

    void getWeatherData() {
        String weatherUrl = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + mCityName + "%2C%20ir%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        AsyncHttpClient client = new AsyncHttpClient();

        if (!dialog.isShowing())
            dialog.show();

        client.get(weatherUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Get Weather Detail Connection failed.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                parseResponseOfWeatherData(responseString);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });
    }

    private void parseResponseOfWeatherData(String response) {
        String tempValue, textValue, dateValue, code;

        List<Forecast> forcastResults;
        Gson gson = new Gson();
        WeatherModel weatherModel = gson.fromJson(response, WeatherModel.class);

        tempValue = weatherModel.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
        textValue = weatherModel.getQuery().getResults().getChannel().getItem().getCondition().getText();
        dateValue = weatherModel.getQuery().getResults().getChannel().getItem().getCondition().getDate();
        code = weatherModel.getQuery().getResults().getChannel().getItem().getCondition().getCode();
        forcastResults = weatherModel.getQuery().getResults().getChannel().getItem().getForecast();

        WeatherForcastsAdapter adapter = new WeatherForcastsAdapter(MainActivity.this, forcastResults);

        listViewForcast.setAdapter(adapter);

        txtCity.setText(mCityName);
        txtDegree.setText(tempValue + "°F ");
        imageMain.setImageResource(PublicMethods.getWeatherContion(code));
        try {
            String dateParts[] = dateValue.split(" ");
            txtDate.setText(dateParts[0] + " " + dateParts[1] + " " + dateParts[2] + " " + dateParts[3]);

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Failed in dateParsing", Toast.LENGTH_LONG).show();
        }
    }

    private void batteryLevel() {
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int rawlevel = intent.getIntExtra("level", -1);
                int scale = intent.getIntExtra("scale", -1);
                int level = -1;
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                arcProgress.setProgress(level);
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);
    }
}
