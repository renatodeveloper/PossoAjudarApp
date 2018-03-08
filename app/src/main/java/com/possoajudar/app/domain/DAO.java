package com.possoajudar.app.domain;

import android.content.ContentValues;

import com.possoajudar.app.domain.model.Transacao;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by renato on 23/02/2018.
 */

public interface DAO<E> {
    boolean save() throws Exception;

    boolean save(ContentValues values) throws Exception;

    boolean remove() throws Exception;

    boolean check(JSONObject object) throws Exception;

    E retrieve(long var1) throws Exception;

    ArrayList<E> retrieveAll() throws Exception;

    ArrayList<E> retrieveSome(String var1, String var2) throws Exception;

    Transacao transmitir() throws Exception;
}
