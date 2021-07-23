package com.example.nikhils1.pharmapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView lv = (ListView)findViewById(R.id.mainlistview);
        final database db = new database(this);
        final myobject[] objs = db.get_database();
        final myadaptor adaptor = new myadaptor(MainActivity.this,0,objs);
        lv.setAdapter(adaptor);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int item=i;
                AlertDialog.Builder del = new AlertDialog.Builder(MainActivity.this);
                del.setMessage("Do you really want to delete this product ?");
                del.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    Cursor locator = db.getalldata();
                        locator.moveToPosition(item);
                        int curr_id=locator.getInt(0);
                        db.delete(curr_id);
                        myobject[] objs12 = db.get_database();
                        myadaptor adaptor21 = new myadaptor(MainActivity.this,0,objs12);
                        lv.setAdapter(adaptor21);
                    }
                });
                del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                del.create().show();
                return true;
            }
        });
        Button add_button = (Button)findViewById(R.id.add);
        Button search_button = (Button)findViewById(R.id.search);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogue = new AlertDialog.Builder(MainActivity.this);
                dialogue.setTitle("Enter Product to Search");
                final EditText product = new EditText(MainActivity.this);
                dialogue.setView(product);
                dialogue.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    if(db.is_present(product.getText().toString()))
                    {
                        AlertDialog.Builder newdialogue = new AlertDialog.Builder(MainActivity.this);
                        newdialogue.setTitle("Product Found");
                        ListView lv = new ListView(MainActivity.this);
                        myobject obj[]=db.get_that_content(product.getText().toString());
                        myadaptor adaptor = new myadaptor(MainActivity.this,0,obj);
                        newdialogue.setView(lv);
                        newdialogue.setView(lv);
                        lv.setAdapter(adaptor);
                        newdialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        newdialogue.create().show();
                    }
                    else
                    {
                        AlertDialog.Builder newdialogue = new AlertDialog.Builder(MainActivity.this);
                        newdialogue.setTitle("Product Not Found");
                        newdialogue.setMessage("Do you want to search for alternate products with same contents ?");
                        newdialogue.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog.Builder newestdialog = new AlertDialog.Builder(MainActivity.this);
                                final EditText chemical = new EditText(MainActivity.this);
                                chemical.setHint("Chemical Contents");
                                newestdialog.setView(chemical);
                                newestdialog.setPositiveButton("Search Alt", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(db.is_related(chemical.getText().toString()))
                                        {
                                            AlertDialog.Builder newone = new AlertDialog.Builder(MainActivity.this);
                                            newone.setTitle("Alternate Products");
                                            ListView lv = new ListView(MainActivity.this);
                                            myobject objk[]=db.get_related_products(chemical.getText().toString());
                                            myadaptor ap = new myadaptor(MainActivity.this,0,objk);
                                            lv.setAdapter(ap);
                                            newone.setView(lv);
                                            newone.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            });
                                            newone.create().show();
                                        }
                                        else
                                            Toast.makeText(MainActivity.this,"NOT ALTERNATIVE FOUND",1).show();
                                    }
                                });
                                newestdialog.show();
                            }
                        });
                        newdialogue.show();
                    }
                    }
                });
                dialogue.create().show();
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialogue = new AlertDialog.Builder(MainActivity.this);
                dialogue.setTitle("Enter the details");
                LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final EditText company = new EditText(MainActivity.this);
                company.setHint("COMPANY/BRAND NAME");
                final EditText product = new EditText(MainActivity.this);
                product.setHint("PRODUCT NAME");
                final EditText Chemical = new EditText(MainActivity.this);
                Chemical.setHint("CHEMICAL CONTENTS");
                final EditText unit = new EditText(MainActivity.this);
                unit.setHint("UNIT");
                final EditText price = new EditText(MainActivity.this);
                price.setHint("PRICE");
                final EditText quantity = new EditText(MainActivity.this);
                quantity.setHint("QUANTITY");
                linearLayout.addView(company,layout);
                linearLayout.addView(product,layout);
                linearLayout.addView(Chemical,layout);
                linearLayout.addView(unit,layout);
                price.setInputType(InputType.TYPE_CLASS_NUMBER);
                linearLayout.addView(price,layout);
                quantity.setInputType(InputType.TYPE_CLASS_NUMBER);
                linearLayout.addView(quantity,layout);
                dialogue.setView(linearLayout);
                dialogue.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                      String  rids=db.get_related_ids(Chemical.getText().toString());
                       if( db.insert(company.getText().toString(),product.getText().toString(),Chemical.getText().toString(),unit.getText().toString(),Integer.parseInt(price.getText().toString()),Integer.parseInt(quantity.getText().toString()),rids))
                       {
                           Toast.makeText(MainActivity.this , "Entry Made Successfully",1).show();
                           myobject[] objs1 = db.get_database();
                           myadaptor adaptor1 = new myadaptor(MainActivity.this,0,objs1);
                           lv.setAdapter(adaptor1);
                       }
                        else
                           Toast.makeText(MainActivity.this , "There was some error",1).show();
                  }
                });
                dialogue.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialogue.create().show();
            }
        });
    }
}
