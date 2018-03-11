package com.possoajudar.app.application.ui.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.possoajudar.app.R;
import com.possoajudar.app.application.ui.activities.MainActivity;
import com.possoajudar.app.domain.model.Apontamento;

import java.util.ArrayList;

/**
 * Created by renato on 28/07/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Apontamento> dataGrupoSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDs;
        TextView textViewSubDs;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewDs = (TextView) itemView.findViewById(R.id.textViewDs);
            this.textViewSubDs = (TextView) itemView.findViewById(R.id.textViewSubDs);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public MyRecyclerViewAdapter(ArrayList<Apontamento> dataGrupo) {
        this.dataGrupoSet = dataGrupo;
    }

    @Override
    public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListener);

        MyRecyclerViewAdapter.MyViewHolder myViewHolder = new MyRecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerViewAdapter.MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewDs;
        TextView textViewVersion = holder.textViewSubDs;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataGrupoSet.get(listPosition).getVlPeso());
        textViewVersion.setText(dataGrupoSet.get(listPosition).getVlAltura());
        imageView.setImageResource(dataGrupoSet.get(listPosition).getDataHora());
    }

    @Override
    public int getItemCount() {
        return dataGrupoSet.size();
    }
}
