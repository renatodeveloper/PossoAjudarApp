package com.possoajudar.app.domain.model;

import com.possoajudar.app.R;

/**
 * Created by renato on 25/07/2017.
 */

public class Apontamento {
    int idApontamento;
    int dataHora;
    String dsDataHora;
    String vlPeso;
    String vlAltura;
    float imc;
    String dsStatus;
    int idUsuario;

    public Apontamento(int idApontamento, int dataHora, String dsDataHora, String vlPeso, String vlAltura, float imc, String dsStatus, int idUsuario){
        this.idApontamento = idApontamento;
        this.dataHora = dataHora;
        this.dsDataHora = dsDataHora;
        this.vlPeso = vlPeso;
        this.vlAltura = vlAltura;
        this.imc = imc;
        this.dsStatus = dsStatus;
        this.idUsuario = idUsuario;
    }
    public Apontamento(){

    }

    public int getDataHora() {
        return dataHora;
    }

    public void setDataHora(int dataHora) {
        this.dataHora = dataHora;
    }

    public String getDsDataHora() {
        return dsDataHora;
    }

    public void setDsDataHora(String dsDataHora) {
        this.dsDataHora = dsDataHora;
    }

    public String getVlPeso() {
        return vlPeso;
    }

    public void setVlPeso(String vlPeso) {
        this.vlPeso = vlPeso;
    }

    public String getVlAltura() {
        return vlAltura;
    }

    public void setVlAltura(String vlAltura) {
        this.vlAltura = vlAltura;
    }
    public float getImc() {
        return imc;
    }
    public void setImc(float imc) {
        this.imc = imc;
    }

    public String getDsStatus() {
        return dsStatus;
    }

    public void setDsStatus(String dsStatus) {
        this.dsStatus = dsStatus;
    }

    /*
    Metodos mock para Implementação RecyclerView
     */

    public static String[] getArrayDs(){
        String[]arrayDsGrupoMock = null;
        try{
            arrayDsGrupoMock = new String[]{"69.30", "69.70", "71.10", "69.40", "71.30", "72.00", "72.10", "71.80", "73.00", "71.90", "71.00" };
            return  arrayDsGrupoMock;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  arrayDsGrupoMock;
    }

    public static String[] getArraySubDs(){
        String[]arraySubDsGrupoMock = null;
        try{
            arraySubDsGrupoMock = new String[]{"11.07.2017", "12.07.2017", "14.07.2017", "13.07.2017", "15.07.2017", "17.07.2017", "19.07.2017", "22.07.2017", "23.07.2017", "25.07.2017","28.07.2017"};
            return  arraySubDsGrupoMock;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  arraySubDsGrupoMock;
    }

    public static Integer[]  getArrayId(){
        Integer[]arrayIdGrupoMock = null;
        try{
            arrayIdGrupoMock = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            return  arrayIdGrupoMock;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  arrayIdGrupoMock;
    }


    public static Integer[]  getArrayImgStatus() {
        Integer[] arrayImgGrupoMock = null;
        try{
            arrayImgGrupoMock = new Integer[]{R.drawable.clean, R.drawable.clean, R.drawable.clean, R.drawable.clean, R.drawable.clean, R.drawable.clean, R.drawable.clean, R.drawable.clean, R.drawable.clean, R.drawable.clean, R.drawable.clean, R.drawable.clean};
            return  arrayImgGrupoMock;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  arrayImgGrupoMock;
    }

}
