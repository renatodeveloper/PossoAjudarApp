package com.possoajudar.app.domain.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Blob;
import java.util.List;

/**
 * Created by Renato on 27/09/2017.
 */

public class Usuario {
    public int idUsuario;
    public String dsNome;
    public String dsLogin;
    public String dsSenha;
    public int idRedeSocial;
    public int idServico;
    public String namePhoto;
    public Blob   bytePhoto;
}
