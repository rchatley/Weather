package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ape09 on 12/07/2017.
 */
public class CachedForecast implements Forecasters
{
        private Forecasters forecaster;
        private LinkedHashMap cache;
        private final int MAX_CAPACITY = 10;

        public CachedForecast(Forecasters f)
        {
            this.forecaster = f;
            this.cache = new LinkedHashMap(MAX_CAPACITY + 1)
            {
                @Override
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return size() > MAX_CAPACITY;
                }
            };
        }

        public Forecast forecastFor(Region city, Day day)
        {
            //If k is not in cache, then fetch and store
            String k = city.toString() + day.toString();
            if(!this.cache.containsKey(k))
            {
                System.out.println("Key->'" + k + "', is not in cache, retrieving!");
                //Value is not in cache, fetch and store
                this.cache.put(k, getFromForecaster(this.forecaster, city, day));
            }
            else
            {
                System.out.println("Key->'" + k + "', is in cache!");
            }
            return (Forecast) this.cache.get(k);
        }

        protected Forecast getFromForecaster(Forecasters forecaster, Region city, Day day)
        {
            return forecaster.forecastFor(city,day);
        }

    public static void main(String[] args) {
            CachedForecast cf = new CachedForecast(new ForecasterAdapter());

            cf.forecastFor(Region.LONDON, Day.MONDAY);
            cf.forecastFor(Region.LONDON, Day.MONDAY);
            cf.forecastFor(Region.LONDON, Day.MONDAY);
            cf.forecastFor(Region.LONDON, Day.MONDAY);
    }
}

