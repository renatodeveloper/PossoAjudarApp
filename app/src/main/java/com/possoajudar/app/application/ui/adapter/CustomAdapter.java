package com.possoajudar.app.application.ui.adapter;

import android.content.Context;
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
        TextView txtApontamento;
        TextView txtDataApontamento;
        TextView txtStatus;
        ImageView propaganda;
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
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Apontamento dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_apontamento, parent, false);//row_item
            viewHolder.txtApontamento = (TextView) convertView.findViewById(R.id.textViewApontamento);
            viewHolder.txtDataApontamento = (TextView) convertView.findViewById(R.id.textViewDataApontamento);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.textViewStatus);
            viewHolder.propaganda = (ImageView) convertView.findViewById(R.id.img_propaganda);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtApontamento.setText(dataModel.getName());
        viewHolder.txtDataApontamento.setText(dataModel.getType());
        viewHolder.txtStatus.setText(dataModel.getVersion_number());
        //viewHolder.propaganda.setImageIcon(?);
        viewHolder.propaganda.setOnClickListener(this);
        viewHolder.propaganda.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}