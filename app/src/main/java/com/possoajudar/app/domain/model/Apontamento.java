package com.possoajudar.app.domain.model;

/**
 * Created by renato on 25/07/2017.
 */

public class Apontamento {
    String dataHora;
    String dsApontamento;

    public Apontamento(String dataHora, String dsApontamento){
        this.dataHora = dataHora;
        this.dsApontamento = dsApontamento;
    }

    @Override
    public String toString() {
        return  dataHora + " - " + dsApontamento;
    }
}
