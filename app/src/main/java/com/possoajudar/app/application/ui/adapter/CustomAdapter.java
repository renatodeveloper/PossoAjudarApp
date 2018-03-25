package com.possoajudar.app.application.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.possoajudar.app.R;
import com.possoajudar.app.domain.model.Apontamento;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by renato on 31/07/2017.
 */

public class CustomAdapter  extends ArrayAdapter<Apontamento> implements View.OnClickListener{

    private ArrayList<Apontamento> dataSet;
    ArrayList<String> dataUrlPropaganda;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView dsAltura;
        TextView dsPeso;
        TextView dsHora;
        TextView dsStatus;
        ImageView imgStatus;
        ImageView imgPropaganda;
    }

    public CustomAdapter(ArrayList<Apontamento> data, Context context) {
        super(context, R.layout.list_row_apontamento, data);//row_item
        this.dataSet = data;
        this.mContext=context;
    }

    public CustomAdapter(ArrayList<Apontamento> data, ArrayList<String> dataUrl, Context context) {
        super(context, R.layout.list_row_apontamento, data);//row_item
        this.dataSet = data;
        this.mContext=context;
        this.dataUrlPropaganda = dataUrl;
    }


    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Apontamento dataModel=(Apontamento)object;
        switch (v.getId()) {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getVlPeso(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Apontamento dataModel = getItem(position);
        String urlMaster = "";
        if(dataUrlPropaganda != null && dataUrlPropaganda.size()>0){
            urlMaster = dataUrlPropaganda.get(position);
        }

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_apontamento, parent, false);//row_item
            viewHolder.dsAltura = (TextView) convertView.findViewById(R.id.textViewAltura);
            viewHolder.dsPeso = (TextView) convertView.findViewById(R.id.textViewPeso);
            viewHolder.dsHora = (TextView) convertView.findViewById(R.id.textViewHora);
            //viewHolder.dsStatus = (TextView) convertView.findViewById(R.id.textViewStatus);
            viewHolder.imgStatus = (ImageView) convertView.findViewById(R.id.imageViewStatus);
            viewHolder.imgPropaganda = (ImageView) convertView.findViewById(R.id.imageViewPropaganda);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.dsAltura.setText("Altura: " + dataModel.getVlAltura());
        viewHolder.dsPeso.setText("Peso: " + dataModel.getVlPeso());
        viewHolder.dsHora.setText(dataModel.getDsDataHora());

        //viewHolder.dsStatus.setText(dataModel.getDsStatus());

        if(dataModel.getImc() >= 40){
                viewHolder.imgStatus.setImageResource(R.mipmap.flag_red);
                viewHolder.dsPeso.setTextColor(R.color.color_red);
        }else
        if(dataModel.getImc() >= 35){
            viewHolder.imgStatus.setImageResource(R.mipmap.flag_red);
            viewHolder.dsPeso.setTextColor(R.color.color_red);
        }
        else
        if(dataModel.getImc() >= 30){
            viewHolder.imgStatus.setImageResource(R.mipmap.flag_red);
            viewHolder.dsPeso.setTextColor(R.color.color_red);
        }
        else
        if(dataModel.getImc() >= 25){
            viewHolder.imgStatus.setImageResource(R.mipmap.flag_orange);
        }
        else
        if(dataModel.getImc() >= 18.5){
            viewHolder.imgStatus.setImageResource(R.mipmap.flag_green);
        }
        else
        if(dataModel.getImc() >= 17){
            viewHolder.imgStatus.setImageResource(R.mipmap.flag_red);
            viewHolder.dsPeso.setTextColor(R.color.color_red);
        }
        else
        if(dataModel.getImc() < 17 ){
            viewHolder.imgStatus.setImageResource(R.mipmap.flag_red);
            viewHolder.dsPeso.setTextColor(R.color.color_red);
        }


        //for(int p=0; p< dataUrlPropaganda.size(); p++){
        if((!urlMaster.equals("")) && (urlMaster.length()>0)){
            Picasso.with(getContext())
                    .load(urlMaster)
                    .resize(25,25).into(viewHolder.imgPropaganda);
        }
        //}

        //viewHolder.imgPropaganda.setImageResource(R.mipmap.shopping);
        //viewHolder.imgPropaganda.setOnClickListener(this);
        //viewHolder.imgPropaganda.setTag(position);

        viewHolder.imgStatus.setOnClickListener(this);
        viewHolder.imgStatus.setTag(position);

        // Return the completed view to render on screen
        return convertView;
    }
}