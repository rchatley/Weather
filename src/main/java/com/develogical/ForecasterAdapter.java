package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;
import com.weather.Forecaster;

/**
 * Created by ape09 on 12/07/2017.
 */
public class ForecasterAdapter implements Forecasters{

    @Override
    public Forecast forecastFor(Region r, Day d) {
        return new Forecaster().forecastFor(r, d);
    }
}
