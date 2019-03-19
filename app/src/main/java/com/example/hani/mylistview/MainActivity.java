package com.example.hani.mylistview;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private FilmsListAdapter adapter;
    private List<Films> mFilmsListe;
    private int getSelectedIndex;
    String url = "https://lorempixel.com/200/200/";
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        //Start(Create a list + list View)
        mListView = (ListView) findViewById(R.id.sampleListView);
        mFilmsListe = new ArrayList<>();

        //Init Adapter
        adapter = new FilmsListAdapter(getApplicationContext(), mFilmsListe);
        mListView.setAdapter(adapter);
        Bitmap icon = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.defaulticon);
        final SerialBitmap serialIcon = new SerialBitmap(icon);
        final List<Films> films = new ArrayList<>();
        final int[] id = new int[0];

        mFilmsListe = genererMovies();

        adapter = new FilmsListAdapter(MainActivity.this, mFilmsListe);
        mListView.setAdapter(adapter);


        //Handel OnItemClick
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Product ID " + view.getTag(), Toast.LENGTH_SHORT).show();

                //MainActivity.this.getSelectedIndex = position;
                CheckBox cb = (CheckBox) view.findViewById(R.id.chk);
                cb.setChecked(!cb.isChecked());
                if(cb.isChecked()){
                    mFilmsListe.get(position).setSelected(true);
                    mListView.getChildAt(position).setBackgroundColor(Color.GRAY);
                }
                else
                {
                    mFilmsListe.get(position).setSelected(false);
                    mListView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });
        //Button Add element to the listView
        final Button b = (Button) findViewById(R.id.b1);
        b.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // ASK permission of GPS
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        2);

                // Dialog
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                final View mview=getLayoutInflater().inflate(R.layout.dialog,null);
                builder.setTitle("Add items to your shopping list");
                final int[] i = {0};
                final EditText et1 = (EditText) mview.findViewById(R.id.editText1);
                final EditText et2 = (EditText) mview.findViewById(R.id.editText2);
                final Spinner sp = (Spinner) mview.findViewById(R.id.spinner);
                Bitmap icon = BitmapFactory.decodeResource(getBaseContext().getResources(),
                        R.drawable.defaulticon);
                final SerialBitmap serialIcon = new SerialBitmap(icon);
                ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(MainActivity.this, R.array.category, android.R.layout.simple_spinner_item);
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(adp);
                //button Ok
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     if(!et1.getText().toString().isEmpty() && !et2.getText().toString().isEmpty()
                            && !sp.getSelectedItem().toString().equalsIgnoreCase("choose a category"))
                     {
                        final String name = et1.getText().toString();
                        final int year = Integer.parseInt(et2.getText().toString());
                        String gender = sp.getSelectedItem().toString();

                        mFilmsListe.add(new Films(name, year, gender, null, i[0],false));
                        adapter.notifyDataSetChanged();
                        i[0]++;
                        Toast.makeText(MainActivity.this, "Add element", Toast.LENGTH_LONG).show();
                     }
                     else
                     {
                        Toast.makeText(MainActivity.this, "fill empty fields", Toast.LENGTH_LONG).show();
                     }
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(mview);
                AlertDialog dlog=builder.create();
                dlog.show();
            }
        });
        //Clear Button
        final Button cb = (Button) findViewById(R.id.b2);
        cb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mFilmsListe.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Clear the list", Toast.LENGTH_LONG).show();
            }
        });
        //Delete Button
        final Button db = (Button) findViewById(R.id.b3);
        db.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(mFilmsListe.isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "No items to delete.",
                            Toast.LENGTH_LONG).show();
                }
                int itemCount = mListView.getCount();
                for(int i=itemCount - 1 ; i>=0; i--){
                    Films aux = (Films) adapter.getItem(i);
                    if(aux.isSelected()){
                        mFilmsListe.remove(aux);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        // asyncs Button
        final Button asyncs = (Button) findViewById(R.id.b_asyncs);
        asyncs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for(Films movie : mFilmsListe){
                    ChangeCoverAsyncTaskS c = new ChangeCoverAsyncTaskS(adapter,movie);
                    c.execute(url);
                }
            }
        });
        // save Button
        final Button save = (Button) findViewById(R.id.b_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // save Load
        final Button load = (Button) findViewById(R.id.b_load);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    private List<Films> genererMovies(){
        mFilmsListe = new ArrayList<Films>();
        for (int i=1; i<10; i++){
            mFilmsListe.add(new Films("M2", 2019, "MIAGE", null , i,false));
        }
        return mFilmsListe;
    }
}