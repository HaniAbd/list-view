package com.example.hani.mylistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Hani on 05-Feb-19.
 */

public class FilmsListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Films> mFilmsListe; //une cellule

//constracter

    public FilmsListAdapter(Context mContext, List<Films> mFilmsListe) {
        this.mContext = mContext;
        this.mFilmsListe = mFilmsListe;
    }

    @Override
    public int getCount() {
        return mFilmsListe.size();
    }

    @Override
    public Object getItem(int position) {
        return mFilmsListe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v= View.inflate(mContext,R.layout.films,null);

        TextView tvName=(TextView)v.findViewById(R.id.txt_name);
        TextView tvYear=(TextView)v.findViewById(R.id.txt_year);
        TextView tvGender=(TextView)v.findViewById(R.id.txt_gender);
        ImageView image=(ImageView)v.findViewById(R.id.imageView);

        CheckBox checkBox=(CheckBox)v.findViewById(R.id.chk);
        tvName.setText(mFilmsListe.get(position).getName());
        tvYear.setText(String.valueOf(mFilmsListe.get(position).getYear()));
        tvGender.setText(mFilmsListe.get(position).getGender());
        // image.setImageResource(mFilmsListe.get(position).getImage());
        v.setTag(mFilmsListe.get(position).getId());

        if(!(mFilmsListe.get(position).getImage()==null)) {
            image.setImageBitmap(mFilmsListe.get(position).getImage());
        }
        else{
            image.setImageResource(R.drawable.defaulticon);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // TODO Auto-generated method stub


                if (((CheckBox) v).isChecked()) {
                    mFilmsListe.get(position).setSelected(true);
                }
                else {
                    mFilmsListe.get(position).setSelected(false);
                }
            }
        });

        return v;
    }
    private class MovieViewHolder{
        public TextView name;
        public TextView year;
        public TextView gender;
        public ImageView image;
    }
}
