package com.possoajudar.app.domain.model;

import com.possoajudar.app.R;

/**
 * Created by renato on 25/07/2017.
 */

public class Apontamento {
    public String ds;
    public String subDs;
    public int id;
    public int image;


    public Apontamento(String ds, String subDs, int id, int image) {
        this.ds = ds;
        this.subDs = subDs;
        this.id = id;
        this.image=image;
    }


    public String getDs() {
        return ds;
    }


    public String getSubDs() {
        return subDs;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id;
    }


    public Apontamento(String ds, String subDs){
        this.ds = ds;
        this.subDs = subDs;
    }

    @Override
    public String toString() {
        return  ds + " - " + subDs;
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
            arrayImgGrupoMock = new Integer[]{R.drawable.clean16x16, R.drawable.clean16x16, R.drawable.clean16x16, R.drawable.clean16x16, R.drawable.clean16x16, R.drawable.clean16x16, R.drawable.clean16x16, R.drawable.clean16x16, R.drawable.clean16x16, R.drawable.clean16x16, R.drawable.clean16x16, R.drawable.clean16x16};
            return  arrayImgGrupoMock;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  arrayImgGrupoMock;
    }

}
