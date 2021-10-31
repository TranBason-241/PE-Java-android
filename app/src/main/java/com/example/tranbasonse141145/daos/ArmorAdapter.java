package com.example.tranbasonse141145.daos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tranbasonse141145.R;
import com.example.tranbasonse141145.dtos.ArmorDTO;

import java.util.List;

public class ArmorAdapter extends BaseAdapter {
    private List<ArmorDTO> listWeapon;
    public ArmorAdapter() {
    }

    public List<ArmorDTO> getListWeapon() {
        return listWeapon;
    }

    public void setListWeapon(List<ArmorDTO> listWeapon) {
        this.listWeapon = listWeapon;
    }

    @Override
    public int getCount() {
        return listWeapon.size();
    }

    @Override
    public Object getItem(int i) {
        return listWeapon.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView== null){
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            convertView =  inflater.inflate(R.layout.item, parent, false);
        }

        TextView txtID = convertView.findViewById(R.id.txtID);
        TextView txtName = convertView.findViewById(R.id.txtClassification);
        TextView txtAttack =  convertView.findViewById(R.id.txtDefense);
        ArmorDTO dto= listWeapon.get(position);
        txtID.setText("ID:  "  + dto.getArmorId()+"");
        txtName.setText("Class:  "  +dto.getClassification() + "");
        txtAttack.setText("Denfense:  "  +dto.getDefense() + "");
        return convertView;
    }
}
