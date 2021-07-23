package com.example.nikhils1.pharmapp;

/**
 * Created by nikhils1 on 7/21/17.
 */

public class myobject {
    public String company_name;
    public String product_name;
    public String chemical_contents;
    public String unit;
    public int price_per_unit;
    public int quantity;
    public String alternate;
    myobject()
    {}
    myobject(String company_name,String product_name,String chemical_contents,String unit , int price_per_unit,int quantity,String alternate)
    {
        this.company_name=company_name;
        this.product_name=product_name;
        this.chemical_contents=chemical_contents;
        this.unit=unit;
        this.price_per_unit=price_per_unit;
        this.quantity=quantity;
        this.alternate=alternate;
    }
}
