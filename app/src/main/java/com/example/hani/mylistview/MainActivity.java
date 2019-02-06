package com.example.hani.mylistview;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
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
public class MainActivity extends AppCompatActivity {
    private ListView IvProduct;
    private producteListeAdapter adapter;
    private List<Produits> mProduitsListe;
    private int[] icons = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4
            , R.drawable.icon5};
    private int getSelectedIndex;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start(Create a list + list View)
        IvProduct = (ListView) findViewById(R.id.sampleListView);
        mProduitsListe = new ArrayList<>();

        //Init Adapter
        adapter = new producteListeAdapter(getApplicationContext(), mProduitsListe);
        IvProduct.setAdapter(adapter);
        mProduitsListe.add(new Produits("Drink",0,icons[0],0,false));
        mProduitsListe.add(new Produits("Fish",0,icons[1],1,false));
        mProduitsListe.add(new Produits("Salad",0,icons[2],2,false));
        mProduitsListe.add(new Produits("Bread",0,icons[3],3,false));
        mProduitsListe.add(new Produits("Meet",0,icons[4],4,false));

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
                    mProduitsListe.get(position).setSelected(true);
                    IvProduct.getChildAt(position).setBackgroundColor(Color.GRAY);
                }
                else
                {
                    mProduitsListe.get(position).setSelected(false);
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
                        final String a = et1.getText().toString();
                        final int b = Integer.parseInt(et2.getText().toString());
                        String text = sp.getSelectedItem().toString();
                        int m = 0;
                        switch (text) {
                            case "Drink":
                                m = icons[0];
                                break;
                            case "Fish":
                                m = icons[1];
                                break;
                            case "Vegetables":
                                m = icons[2];
                                break;
                            case "Bread":
                                m = icons[3];
                                break;
                            case "Meat":
                                m = icons[4];
                                break;
                        }
                        mProduitsListe.add(new Produits(a, b, m, i[0],false));
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
                mProduitsListe.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Clear the list", Toast.LENGTH_LONG).show();
            }
        });
        //Delete Button
        final Button db = (Button) findViewById(R.id.b3);
        db.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(mProduitsListe.isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "No items to delete.",
                            Toast.LENGTH_LONG).show();
                }
                int itemCount = IvProduct.getCount();
                for(int i=itemCount - 1 ; i>=0; i--){
                    Produits aux = (Produits) adapter.getItem(i);
                    if(aux.isSelected()){
                        mProduitsListe.remove(aux);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}