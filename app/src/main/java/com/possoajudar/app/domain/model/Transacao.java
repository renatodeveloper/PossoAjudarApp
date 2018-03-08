package com.possoajudar.app.domain.model;

/**
 * Created by renato on 23/02/2018.
 */

public class Transacao {
    public static final int ERRO = -1000;
    public static final int OK = 1000;
    public int cod = 1000;
    public String erro;
    public String data;

    public Transacao() {
    }
}
