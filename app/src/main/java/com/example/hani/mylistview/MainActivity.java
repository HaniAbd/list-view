package com.example.hani.mylistview;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
    private ListView IvProduct;
    private FilmsListAdapter adapter;
    private List<Films> mFilmsListe;
    private int getSelectedIndex;
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start(Create a list + list View)
        IvProduct = (ListView) findViewById(R.id.sampleListView);
        mFilmsListe = new ArrayList<>();

        //Init Adapter
        adapter = new FilmsListAdapter(getApplicationContext(), mFilmsListe);
        IvProduct.setAdapter(adapter);
        Bitmap icon = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.defaulticon);
        final SerialBitmap serialIcon = new SerialBitmap(icon);
        final List<Films> films = new ArrayList<>();
        final int[] id = new int[0];

        for (int i = 0; i<20; i++) {
            Films f = new Films("M2", 2019, "MIAGE", serialIcon , i,false);
            films.add(f);
            adapter.notifyDataSetChanged();
        }

        //Handel OnItemClick
        IvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Product ID " + view.getTag(), Toast.LENGTH_SHORT).show();

                //MainActivity.this.getSelectedIndex = position;
                CheckBox cb = (CheckBox) view.findViewById(R.id.chk);
                cb.setChecked(!cb.isChecked());
                if(cb.isChecked()){
                    mFilmsListe.get(position).setSelected(true);
                    IvProduct.getChildAt(position).setBackgroundColor(Color.GRAY);
                }
                else
                {
                    mFilmsListe.get(position).setSelected(false);
                    IvProduct.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });
        //Button Add element to the listView
        final Button b = (Button) findViewById(R.id.b1);
        b.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
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

                        mFilmsListe.add(new Films(name, year, gender, serialIcon, i[0],false));
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
                int itemCount = IvProduct.getCount();
                for(int i=itemCount - 1 ; i>=0; i--){
                    Films aux = (Films) adapter.getItem(i);
                    if(aux.isSelected()){
                        mFilmsListe.remove(aux);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}