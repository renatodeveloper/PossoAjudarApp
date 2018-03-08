package com.possoajudar.app.application.ui.activities;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadConfServView;
import com.possoajudar.app.application.service.ServicoApontamento;
import com.possoajudar.app.application.service.cadastro.CadConfServPresenter;
import com.possoajudar.app.application.service.cadastro.CadConfServService;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import butterknife.BindView;
import butterknife.OnClick;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by renato on 07/02/2018.
 */

public class CadConfServ extends RoboActivity implements ICadConfServView{

    @InjectView(R.id.lyCadConfServImageViewCadastrar)
    ImageView cadConfServ;
    @InjectView(R.id.lyCadConfServImageViewLimpar)
    ImageView cleanCondServ;

    @InjectView(R.id.radio_alldays)
    RadioButton radioAlldays;
    @InjectView(R.id.radio_allweek)
    RadioButton radioAllweek;
    @InjectView(R.id.radio_allweekend)
    RadioButton radioAllweekend;
    @InjectView(R.id.radio_15days)
    RadioButton radioAll15days;
    @InjectView(R.id.radio_allmonth)
    RadioButton radioAllmonth;

    public ActivityUtil activityUtil;
    private CadConfServPresenter cadConfServPresenter;

    int optionConfServ = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_cad_setting_service);

        cadConfServPresenter = new CadConfServPresenter(this, this);

        cadConfServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "CLICK BY roboguice.inject.InjectView - CAD", Toast.LENGTH_LONG).show();
                cadConfServPresenter.registerConfServ();


                /*
                 Intent intent = new Intent();
                intent.putExtra("id", "Some Value Here to voltar");
                setResult(Activity.RESULT_OK, intent);
                finish();
                 */


            }
        });
        cleanCondServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "CLICK BY roboguice.inject.InjectView - CLEAN", Toast.LENGTH_LONG).show();
            }
        });


        radioAlldays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionConfServ = getApplicationContext().getResources().getInteger(R.integer.idCadastroDeConfServOptionAllDays);
            }
        });
        radioAllweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionConfServ = getApplicationContext().getResources().getInteger(R.integer.idCadastroDeConfServOptionAllWeek);
            }
        });
        radioAllweekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionConfServ =  getApplicationContext().getResources().getInteger(R.integer.idCadastroDeConfServOptionAllWeekend);
            }
        });
        radioAll15days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionConfServ =  getApplicationContext().getResources().getInteger(R.integer.idCadastroDeConfServOption15Days);
            }
        });
        radioAllmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionConfServ =  getApplicationContext().getResources().getInteger(R.integer.idCadastroDeConfServOptionAllMonth);
            }
        });
    }

    @Override
    public int getCadConfServRadioOption() {
        return optionConfServ;
    }


    @Override
    public void showCadConfServRadioOptionError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCadConfServError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void startMainActivity() {

        Intent intent = new Intent();
        intent.putExtra("id", "Some Value Here to voltar");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
