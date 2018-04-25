package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.possoajudar.app.R;
import com.possoajudar.app.application.module.app.GoogleAnalyticsApplication;
import com.possoajudar.app.application.service.ICadUserView;
import com.possoajudar.app.application.service.cadastro.CadUserPresenter;
import com.possoajudar.app.application.service.cadastro.CadUserService;
import com.possoajudar.app.application.service.dao.DaoModelPresenter;
import com.possoajudar.app.application.service.gps.GpsService;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.OnClick;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by Renato on 26/09/2017.
 */

public class CadUsuario extends RoboActivity implements ICadUserView {

    @InjectView(R.id.lyCadUserEditTextNome)
    EditText userNome;
    @InjectView(R.id.lyCadUserEditTextEmail)
    EditText userEmail;
    @InjectView(R.id.lyCadUserEditTextSenha)
    EditText userSenha;
    @InjectView(R.id.lyCadUserImageViewCadastrar)
    ImageView addUser;
    @InjectView(R.id.lyCadUserImageViewLimpar)
    ImageView cleanUser;
    @InjectView(R.id.imageViewPhoto)
    ImageView takeFoto;
    int REQUEST_IMAGE_CAPTURE = 2;


    ProgressDialog progressDialog;
    public ActivityUtil activityUtil;
    private CadUserPresenter cadUserPresenter;

    private DaoModelPresenter daoModelPresenterTESTE;
    GpsService gps;

    private static final String TAG = CadUsuario.class.getSimpleName();
    private Tracker mTracker;
    private String name = "CadUsuario";

    byte[] byteArrayPhoto;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName(name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_cad_usuario);
        try {
            activityUtil = new ActivityUtil();
            JSONObject object = activityUtil.recuperaPrefFormLogin(getApplicationContext());
            if (object != null) {
                userEmail.setText(object.getString(getApplicationContext().getString(R.string.dsLoginTblUser)));
                userSenha.setText(object.getString(getApplicationContext().getString(R.string.dsSenhaTblUser)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cadUserPresenter = new CadUserPresenter(this, this);

        progressDialog = new ProgressDialog(this);

        try {
            activityUtil = new ActivityUtil();
        } catch (Exception e) {
            e.getMessage().toString();
        }

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Atenção");
                //progressDialog.show();
                //Toast.makeText(getApplicationContext(), "Olá..", Toast.LENGTH_LONG).show();
                cadUserPresenter.registerNewUser();
                activityUtil.limpaPrefFormLogin(getApplicationContext());
            }
        });

        cleanUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNome.setText("");
                userEmail.setText("");
                userSenha.setText("");
                activityUtil.limpaPrefFormLogin(getApplicationContext());
            }
        });

        takeFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    byteArrayPhoto = null;
                    Intent cameraIntent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                } catch (Exception e) {
                    e.getMessage().toString();
                }
            }
        });



        //Analytics Integration
        // Obtain the shared Tracker instance.
        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }


    @Override
    public String getCadNome() {
        return userNome.getText().toString();
    }


    @Override
    public String getCadUserEmail() {
        return userEmail.getText().toString();
    }

    @Override
    public String getCadUserSenha() {
        return userSenha.getText().toString();
    }

    @Override
    public byte[] getByteArrayPhoto() {
        return byteArrayPhoto;
    }

    @Override
    public void setByteArrayPhoto(byte[] byteArray) {

    }

    @Override
    public void nonePhoto() {

    }

    @Override
    public void showCadUserNomeError(int resId) {

        userNome.setError(getString(resId));
    }

    @Override
    public void showCadUserEmailError(int resId) {

        userEmail.setError(getString(resId));
    }

    @Override
    public void showCadUserPasswordError(int resId) {
        userSenha.setError(getString(resId));
    }

    @Override
    public void showCadUserError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void startMainActivity() {
        gps = new GpsService(getApplicationContext());
        if (gps.canGetLocation()) {
            try{
                JSONObject userJson = new JSONObject();
                userJson.put(getString(R.string.dsNomeTblUser), userNome.getText().toString());
                userJson.put(getString(R.string.dsLoginTblUser), userEmail.getText().toString());
                userJson.put(getString(R.string.dsSenhaTblUser), userSenha.getText().toString());

                activityUtil.definePrefUserLogado(getApplicationContext(), gps, userJson);
                startActivity(new Intent(this, MainActivity.class));
                //new ActivityUtil(this).startMainActivity();
            }catch (Exception e){
                e.getMessage().toString();
            }
        } else {
            gps.showSettingsAlert(this);
        }
    }


    /*
    Método responsável por tratar a foto, novo acesso
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ImageView imageViewDone = (ImageView) findViewById(R.id.imageViewPhotoDone);
            imageViewDone.setImageBitmap(imageBitmap);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            //Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            //Toast.makeText(CadUsuario.this,"Here "+ getRealPathFromURI(tempUri), Toast.LENGTH_LONG).show();

            try {
                //SavefileTest(imageBitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArrayPhoto = stream.toByteArray();
                this.setByteArrayPhoto(byteArrayPhoto);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    //****************************************************** onKeyDown
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





    /*
    Teste Save foto novo usuário

    public void SavefileTest(Bitmap photo) throws Exception {
                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    SQLiteDatabase databaseTESTE   =  daoModelPresenterTESTE.getExternalDB();
                    String sql = "INSERT INTO USUARIO (idUsuario, dsLogin, dsSenha, namePhoto, bytePhoto, idRedeSocial, idServico) VALUES(?,?,?,?,?,?,?)";
                    SQLiteStatement insertStmt      =   databaseTESTE.compileStatement(sql);
                    insertStmt.clearBindings();
                    insertStmt.bindLong(1, 10);
                    insertStmt.bindString(2, "meunome");
                    insertStmt.bindString(3, "minhasenha");
                    insertStmt.bindString(4, "nomefoto");
                    insertStmt.bindBlob(5, byteArray);
                    insertStmt.bindLong(6, 1);
                    insertStmt.bindLong(7, 1);
                    insertStmt.executeInsert();
                    databaseTESTE.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

        */
}