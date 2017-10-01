package com.develogical;

import com.oocode.weather.DayForecaster;
import com.oocode.weather.Forecaster;
import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class WeatherTests {

    @Test
    public void checkCFAdapter(){
        Forecasters f = mock(Forecasters.class);
        when(f.forecastFor(Region.LONDON, Day.MONDAY)).thenReturn(new Forecast("Sunny'ish", 500));
        CachedForecast cf = new CachedForecast(f);

        Forecast londonForecast = cf.getFromForecaster(f, Region.LONDON, Day.MONDAY);

        assertEquals(londonForecast.summary(), "Sunny'ish");
        assertEquals(londonForecast.temperature(), 500);
    }

    @Test
    public void checkCFAPI(){

        CachedForecast cf = new CachedForecast(mock(Forecasters.class))
        {
            protected Forecast getFromForecaster(Forecasters forecaster, Region city, Day day)
            {
                return new Forecast("Rainny", 501);
            }
        };

        Forecast londonForecast = cf.forecastFor(Region.LONDON, Day.MONDAY);

        assertEquals(londonForecast.summary(), "Rainny");
        assertEquals(londonForecast.temperature(), 501);
    }


    @Test
    public void checkAPEAPI(){
        APEForecaster apeForecaster = new APEForecaster();


    }

    /*
    @Test
    public void checkAPETemperatures(){
        APEForecaster apeForecaster = new APEForecaster()
        {
            private DayForecaster getForecaster()
            {
                DayForecaster df = mock(DayForecaster.class);
                when(df.forecast(com.oocode.weather.Forecaster.Regions.SE_ENGLAND, 0)).thenReturn(20.0);
                return df;
            }
        };

        assertEquals(apeForecaster.getTemperature(com.oocode.weather.Forecaster.Regions.SE_ENGLAND, 0), 20.0, 0.1);
    }*/


    @Test
    public void checkAveragingAPI()
    {
        AvgForecaster avgF = new AvgForecaster(new ArrayList<Forecasters>());
    }


    @Test
    public void assertAverages()
    {
        ArrayList<Forecasters> forecasters = new ArrayList<>();

        APEForecaster apeForecaster = mock(APEForecaster.class);
        when(apeForecaster.forecastFor(Region.SOUTH_EAST_ENGLAND, Day.SATURDAY)).thenReturn(new Forecast("Sunny", 32));

        CachedForecast cachedForecast = mock(CachedForecast.class);
        when(cachedForecast.forecastFor(Region.SOUTH_EAST_ENGLAND, Day.SATURDAY)).thenReturn(new Forecast("Rain", 20));

        ForecasterAdapter simpleForecast = mock(ForecasterAdapter.class);
        when(simpleForecast.forecastFor(Region.SOUTH_EAST_ENGLAND, Day.SATURDAY)).thenReturn(new Forecast("Indecisive", 34));

        forecasters.add(apeForecaster);
        forecasters.add(cachedForecast);
        forecasters.add(simpleForecast);

        AvgForecaster avgF = new AvgForecaster(forecasters);

        Forecast result = avgF.forecastFor(Region.SOUTH_EAST_ENGLAND, Day.SATURDAY);

        assertEquals(result.temperature(), 29);
        assertEquals(result.summary(), "Sunny");


    }

}
