package com.possoajudar.app.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cep {

    @SerializedName("bairro")
    @Expose
    private String bairro;
    @SerializedName("cidade")
    @Expose
    private String cidade;
    @SerializedName("logradouro")
    @Expose
    private String logradouro;
    @SerializedName("estado_info")
    @Expose
    private EstadoInfo estadoInfo;
    @SerializedName("cep")
    @Expose
    private String cep;
    @SerializedName("cidade_info")
    @Expose
    private CidadeInfo cidadeInfo;
    @SerializedName("estado")
    @Expose
    private String estado;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public EstadoInfo getEstadoInfo() {
        return estadoInfo;
    }

    public void setEstadoInfo(EstadoInfo estadoInfo) {
        this.estadoInfo = estadoInfo;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public CidadeInfo getCidadeInfo() {
        return cidadeInfo;
    }

    public void setCidadeInfo(CidadeInfo cidadeInfo) {
        this.cidadeInfo = cidadeInfo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}