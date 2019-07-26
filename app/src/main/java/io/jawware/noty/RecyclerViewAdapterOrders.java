package io.jawware.noty;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapterOrders extends RecyclerView.Adapter<RecyclerViewAdapterOrders.MyHolder>{

    List<OrderModal> orderdata;
    Context mContext;

    public RecyclerViewAdapterOrders(List<OrderModal> listdata, Context context) {
        this.orderdata = listdata;
        this.mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, final int position) {
        OrderModal data = orderdata.get(position);

        holder.vname.setText(data.getProductname());
        holder.vprice.setText(data.getProductprize());
        holder.vquan.setText(data.getProductquan());
        holder.vdiscount.setText(data.getProductdiscount());



    }

    @Override
    public int getItemCount() {
        return orderdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView vname , vprice,vdiscount,vquan;

        public MyHolder(View itemView) {
            super(itemView);
            vname = (TextView) itemView.findViewById(R.id.producttitle_i);
            vprice = (TextView) itemView.findViewById(R.id.productprize_i);
            vdiscount = (TextView) itemView.findViewById(R.id.productdiscount_i);
            vquan = (TextView) itemView.findViewById(R.id.productqauantity_i);

        }
    }


}