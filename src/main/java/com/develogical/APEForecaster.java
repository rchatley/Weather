package com.develogical;

import com.oocode.weather.DayForecaster;
import com.oocode.weather.Forecaster;
import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

public class APEForecaster implements Forecasters {

    protected double getTemperature(Forecaster.Regions baseRegion, int day) {
        return this.getForecaster().forecast(baseRegion, day);
    }

    private DayForecaster getForecaster() {
        return new DayForecaster();
    }


    private Forecaster.Regions mapAPERegions(Region baseRegion) {
        /*
        NE_SCOTLAND,
        NW_SCOTLAND,
        SE_SCOTLAND,
        SW_SCOTLAND,
        MID_SCOTLAND,
        NE_ENGLAND,
        NW_ENGLAND,
        MID_ENGLAND,
        S_WALES;


        BIRMINGHAM,
            EDINBURGH,
            GLASGOW,
            LONDON,
            MANCHESTER,
            NORTH_ENGLAND,
            SOUTH_EAST_ENGLAND,
            WALES;
         */

        switch (baseRegion.toString()) {
            case "SOUTH_WEST_ENGLAND":
                return Forecaster.Regions.SW_ENGLAND;
            case "SOUTH_EAST_ENGLAND":
                return Forecaster.Regions.SE_ENGLAND;
            default:
                return Forecaster.Regions.N_WALES;
        }
    }

    private int mapAPEDays(Day day) {
        switch (day.toString()) {
            case "MONDAY":
                return 0;
            case "TUESDAY":
                return 1;
            default:
                return 6;
        }
    }

    @Override
    public Forecast forecastFor(Region r, Day d) {
        double temp =  this.getForecaster().forecast(mapAPERegions(r), mapAPEDays(d));
        String summary =  this.getForecaster().forecastSummary(mapAPERegions(r), mapAPEDays(d));
        return new Forecast(summary, (int)temp);
    }
}
