package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.util.ArrayList;

public class AvgForecaster implements Forecasters{
    private ArrayList<Forecasters> forecastersArray;

    public AvgForecaster(ArrayList<Forecasters> forecastersArray) {
        this.forecastersArray = forecastersArray;


    }

    @Override
    public Forecast forecastFor(Region r, Day d) {
        ArrayList<Forecast> forecasts = new ArrayList<>();

        double forecastAvg = 0;

        for(Forecasters f : this.forecastersArray)
        {
            Forecast instanceForecast = f.forecastFor(r, d);

            forecastAvg += instanceForecast.temperature();

            forecasts.add(instanceForecast);
        }

        forecastAvg = forecastAvg / forecasts.size();

        double bestForecastDelta = 0;
        String bestSummary = null;

        for(Forecast f1 : forecasts){
            double delta = Math.abs((f1.temperature() - forecastAvg));
            if(bestSummary == null || delta < bestForecastDelta)
            {
                bestSummary = f1.summary();
                bestForecastDelta = delta;
            }
        }

        return new Forecast(bestSummary, (int)Math.round(forecastAvg));
    }
}
