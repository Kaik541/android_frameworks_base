
package com.android.internal.util.weather;

import android.content.Context;
import android.util.Log;
import com.android.internal.R;

public class WeatherInfo {

    private static final String NODATA = "-";

    public String city, forecast_date, condition, condition_code, temp, temp_unit,
        humidity, wind, wind_dir, speed_unit, low, high;

    public WeatherInfo() {
        this.city = NODATA;
        this.forecast_date = NODATA;
        this.condition = NODATA;
        this.condition_code = NODATA;
        this.temp = NODATA;
        this.temp_unit = NODATA;
        this.humidity = NODATA;
        this.wind = NODATA;
        this.wind_dir = NODATA;
        this.speed_unit = NODATA;
        this.low = NODATA;
        this.high = NODATA;
    }

    public WeatherInfo(Context context, String city, String fdate, String condition, String condition_code,
            String temp, String temp_unit, String humidity,
            String wind, String wind_dir, String speed_unit,
            String low, String high) {
        this.city = city;
        this.forecast_date = fdate;
        this.condition = condition;
        this.condition_code = condition_code;
        this.temp_unit = temp_unit;
        this.humidity = humidity + "%";
        this.wind = calcDirection(context, wind_dir) + " " + trimSpeed(wind) + speed_unit;
        this.speed_unit = speed_unit;
        this.temp = temp + "°" + temp_unit.toLowerCase();
        this.low = low + "°"; // + temp_unit.toLowerCase();
        this.high = high + "°"; // + temp_unit.toLowerCase();
    }

    /**
     * find the optimal weather string (helper function for translation)
     * 
     * @param conditionCode condition code from Yahoo (this is the main
     *            identifier which will be used to find a matching translation
     *            in the project's resources
     * @param providedString
     * @return either the defaultString (which should be Yahoo's weather
     *         condition text), or the translated version from resources
     */
    public static String getTranslatedConditionString(Context context, int conditionCode,
            String providedString) {
        int resID = context.getResources().getIdentifier("weather_" + conditionCode, "string",
                context.getPackageName());
        return (resID != 0) ? context.getResources().getString(resID) : providedString;
    }

    private String calcDirection(Context context, String degrees) {
        int deg = Integer.parseInt(degrees);
        if (deg >= 338 || deg <= 22)
            return context.getResources().getString(R.string.weather_N);
        else if (deg < 68)
            return context.getResources().getString(R.string.weather_NE);
        else if (deg < 113)
            return context.getResources().getString(R.string.weather_E);
        else if (deg < 158)
            return context.getResources().getString(R.string.weather_SE);
        else if (deg < 203)
            return context.getResources().getString(R.string.weather_S);
        else if (deg < 248)
            return context.getResources().getString(R.string.weather_SW);
        else if (deg < 293)
            return context.getResources().getString(R.string.weather_W);
        else if (deg < 338)
            return context.getResources().getString(R.string.weather_NW);
        else
            return "";
    }

    private String trimSpeed(String speed) {
        return String.valueOf(Math.round(Float.parseFloat(speed)));
    }
}
