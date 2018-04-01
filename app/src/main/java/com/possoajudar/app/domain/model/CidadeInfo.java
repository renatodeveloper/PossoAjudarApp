package com.possoajudar.app.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CidadeInfo {

    @SerializedName("area_km2")
    @Expose
    private String areaKm2;
    @SerializedName("codigo_ibge")
    @Expose
    private String codigoIbge;

    public String getAreaKm2() {
        return areaKm2;
    }

    public void setAreaKm2(String areaKm2) {
        this.areaKm2 = areaKm2;
    }

    public String getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

}