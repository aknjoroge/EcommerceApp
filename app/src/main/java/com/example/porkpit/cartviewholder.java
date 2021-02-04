package com.example.porkpit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartviewholder extends  RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtpname,txtpprice,txtpdescription,txtpamount,txttotalprice;
    itemclicklistener listener;

    public cartviewholder(@NonNull View itemView) {
        super(itemView);
        txttotalprice=itemView.findViewById(R.id.cartproducttoatalprice);
        txtpname = itemView.findViewById(R.id.cartproductname);
        txtpprice = itemView.findViewById(R.id.cartproductprice);
        txtpdescription=itemView.findViewById(R.id.cartproductdescription);
        txtpamount=itemView.findViewById(R.id.cartproductquantity);


    }
    public void setItemClickListener(itemclicklistener listener){
        this.listener =listener;
    }

    @Override
    public void onClick(View v) {
        listener.onclick(v,getAdapterPosition(),false);

    }
}
