package com.example.nikhils1.pharmapp;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by nikhils1 on 7/21/17.
 */

public class myadaptor extends ArrayAdapter<myobject> {
  public  Context context;
    public  myobject object[];
    public myadaptor(Context context, int resource,myobject[] object)
    {
        super(context,resource,object);
        this.context=context;
        this.object=object;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_element,parent,false);
        }
        TextView company = (TextView)convertView.findViewById(R.id.company);
        TextView product = (TextView)convertView.findViewById(R.id.product);
        TextView price =(TextView) convertView.findViewById(R.id.price);
        TextView quantity =(TextView) convertView.findViewById(R.id.quantity);
        TextView unit = (TextView)convertView.findViewById(R.id.unit);
        TextView chemical =(TextView) convertView.findViewById(R.id.chemical);
        company.setText("COMPANY :"+object[position].company_name);
        product.setText("PRODUCT :"+object[position].product_name);
        price.setText("PRICE :"+Integer.toString(object[position].price_per_unit));
        quantity.setText("QUANTITY :"+Integer.toString(object[position].quantity));
        unit.setText("UNIT :"+object[position].unit);
        chemical.setText("CHEMICAL CONTENTS :"+object[position].chemical_contents);
        return convertView;
    }
}
