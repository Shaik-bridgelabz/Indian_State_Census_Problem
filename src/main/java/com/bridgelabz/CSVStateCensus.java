package com.bridgelabz;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus {
    @CsvBindByName(column = "State",required = true)
    private String state;

    @CsvBindByName(column = "Population",required = true)
    private String population;

    @CsvBindByName(column = "AreaInSqKm",required = true)
    private String AreaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm",required = true)
    private String DensityPerSqKm;

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getPopulation()
    {
        return population;
    }

    public void setPopulation(String population)
    {
        this.population = population;
    }

    public String getAreaInSqKm()
    {
        return AreaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm)
    {
        AreaInSqKm = areaInSqKm;
    }

    public String getDensityPerSqKm()
    {
        return DensityPerSqKm;
    }

    public void setDensityPerSqKm(String densityPerSqKm)
    {
        DensityPerSqKm = densityPerSqKm;
    }
}
