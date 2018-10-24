package com.gamecodeschool.weatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamecodeschool.models.Forecast;

import java.util.List;

public class WeatherForcastsAdapter extends BaseAdapter {
    Context mContext;
    List<Forecast> forcasts;

    public WeatherForcastsAdapter(Context mContext, List<Forecast> forcasts) {
        this.mContext = mContext;
        this.forcasts = forcasts;
    }

    @Override
    public int getCount() {
        return forcasts.size();
    }

    @Override
    public Object getItem(int position) {
        return forcasts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.weather_forcast_item, parent, false);

        TextView txtDate = v.findViewById(R.id.txtDate);
        TextView txtText = v.findViewById(R.id.txtText);
        TextView txtHigh = v.findViewById(R.id.txtHigh);
        TextView txtLow = v.findViewById(R.id.txtLow);
        ImageView imageForcast = v.findViewById(R.id.imageForcast);

        txtDate.setText(forcasts.get(position).getDate());
        txtText.setText(forcasts.get(position).getText());
        txtHigh.setText(forcasts.get(position).getHigh() + " °F ");
        txtLow.setText(forcasts.get(position).getLow() + " °F ");

        String code = forcasts.get(position).getCode();

        Object x=PublicMethods.getWeatherContion(code);
        Log.d("drawa", "getView: " + x);

        imageForcast.setImageResource(PublicMethods.getWeatherContion(code));

        return v;
    }
}
