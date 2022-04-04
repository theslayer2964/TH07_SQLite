package com.example.a10_sqlite_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TravelAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Travel> travelList;

    public TravelAdapter(MainActivity context, int layout, List<Travel> travelList) {
        this.context = context;
        this.layout = layout;
        this.travelList = travelList;
    }

    @Override
    public int getCount() {
        return travelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtName, txtId;
        ImageView imgDelete, imgUpdate;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder.txtName = view.findViewById(R.id.txtName_1dong);
            viewHolder.txtId = view.findViewById(R.id.txtId_1dong);
            viewHolder.imgDelete = view.findViewById(R.id.imgDelet_1dong);
            viewHolder.imgUpdate = view.findViewById(R.id.imgUpdate_1dong);

            view.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) view.getTag();

        Travel travel = travelList.get(i);
        viewHolder.txtName.setText(travel.getName());
        viewHolder.txtId.setText(String.valueOf(travel.getId()));
        viewHolder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialogUpdateTravel(travel.getName(),travel.getId());
            }
        });
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.deleteTravel(travel.getName(), travel.getId());
            }
        });

        return view;
    }
}
