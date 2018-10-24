package com.gamecodeschool.weatherapp;

import android.content.Context;

import java.util.HashMap;

public class PublicMethods {

    private static HashMap<String, Integer> weatherConditionMap = new HashMap<>();

    private static void createHashMap() {
        weatherConditionMap.put("0", R.drawable.thunderstorm);
        weatherConditionMap.put("1", R.drawable.tstormrain);
        weatherConditionMap.put("2", R.drawable.tstormrain);
        weatherConditionMap.put("3", R.drawable.thunderstorm);
        weatherConditionMap.put("4", R.drawable.thunderstorm);
        weatherConditionMap.put("5", R.drawable.rainysnow);
        weatherConditionMap.put("6", R.drawable.sleet);
        weatherConditionMap.put("7", R.drawable.sleet);
        weatherConditionMap.put("8", R.drawable.fairdrizzle);
        weatherConditionMap.put("9", R.drawable.drizzle);
        weatherConditionMap.put("10", R.drawable.freezingrain);
        weatherConditionMap.put("11", R.drawable.showers);
        weatherConditionMap.put("12", R.drawable.showers);
        weatherConditionMap.put("13", R.drawable.flurries);
        weatherConditionMap.put("14", R.drawable.snowshower);
        weatherConditionMap.put("15", R.drawable.blowingsnow);
        weatherConditionMap.put("16", R.drawable.snow);
        weatherConditionMap.put("17", R.drawable.freezingrain);
        weatherConditionMap.put("18", R.drawable.sleet);
        weatherConditionMap.put("19", R.drawable.na);
        weatherConditionMap.put("20", R.drawable.fog);
        weatherConditionMap.put("21", R.drawable.hazy);
        weatherConditionMap.put("22", R.drawable.smoke);
        weatherConditionMap.put("23", R.drawable.wind);
        weatherConditionMap.put("24", R.drawable.wind);
        weatherConditionMap.put("25", R.drawable.snow);
        weatherConditionMap.put("26", R.drawable.cloudy);
        weatherConditionMap.put("27", R.drawable.mcloudynight);
        weatherConditionMap.put("28", R.drawable.mcloudy);
        weatherConditionMap.put("29", R.drawable.partlycloudy);
        weatherConditionMap.put("30", R.drawable.partlycloudy);
        weatherConditionMap.put("31", R.drawable.sunny);
        weatherConditionMap.put("32", R.drawable.sunny);
        weatherConditionMap.put("33", R.drawable.fair);
        weatherConditionMap.put("34", R.drawable.sunny);
        weatherConditionMap.put("35", R.drawable.na);
        weatherConditionMap.put("36", R.drawable.na);
        weatherConditionMap.put("37", R.drawable.thunderstorm);
        weatherConditionMap.put("38", R.drawable.thunderstorm);
        weatherConditionMap.put("39", R.drawable.thunderstorm);
        weatherConditionMap.put("40", R.drawable.na);
        weatherConditionMap.put("41", R.drawable.snow);
        weatherConditionMap.put("42", R.drawable.snowshower);
        weatherConditionMap.put("43", R.drawable.snow);
        weatherConditionMap.put("44", R.drawable.partlycloudy);
        weatherConditionMap.put("45", R.drawable.snowshower);
        weatherConditionMap.put("46", R.drawable.snowshower);
        weatherConditionMap.put("47", R.drawable.snowshower);
        weatherConditionMap.put("3200", R.drawable.na);
    }

    static int getWeatherContion(String code) {
        if(weatherConditionMap.size() == 0){
            createHashMap();
        }
        return weatherConditionMap.get(code);
    }
}
