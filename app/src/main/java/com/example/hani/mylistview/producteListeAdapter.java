package com.example.hani.mylistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Hani on 13-Oct-17.
 */

public class producteListeAdapter extends BaseAdapter {
    private Context mContext;
    private List<Produits> mProduitsListe; //une cellule

//constracter

    public producteListeAdapter(Context mContext, List<Produits> mProduitsListe) {
        this.mContext = mContext;
        this.mProduitsListe = mProduitsListe;
    }

    @Override
    public int getCount() {
        return mProduitsListe.size();
    }

    @Override
    public Object getItem(int position) {
        return mProduitsListe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v= View.inflate(mContext,R.layout.produits,null);

        TextView tvName=(TextView)v.findViewById(R.id.txt_name);
        TextView tvAmounte=(TextView)v.findViewById(R.id.txt_name2);
        ImageView image=(ImageView)v.findViewById(R.id.imageView);
        CheckBox checkBox=(CheckBox)v.findViewById(R.id.chk);
        tvName.setText(mProduitsListe.get(position).getName());
        tvAmounte.setText(String.valueOf(mProduitsListe.get(position).getAmount()));
        image.setImageResource(mProduitsListe.get(position).getImg());
        v.setTag(mProduitsListe.get(position).getId());

        checkBox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // TODO Auto-generated method stub


                if (((CheckBox) v).isChecked()) {
                    mProduitsListe.get(position).setSelected(true);
                }
                else {
                    mProduitsListe.get(position).setSelected(false);
                }
            }
        });

        return v;
    }
}
