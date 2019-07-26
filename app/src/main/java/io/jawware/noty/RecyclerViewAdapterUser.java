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

public class RecyclerViewAdapterUser extends RecyclerView.Adapter<RecyclerViewAdapterUser.MyHolder>{

    List<Listdata> listdata;
    Context mContext;
    String catogType;

    public RecyclerViewAdapterUser(List<Listdata> listdata, Context context) {
        this.listdata = listdata;
        this.mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productsitem,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, final int position) {
        Listdata data = listdata.get(position);


        holder.vname.setText(data.getName());
        holder.vprice.setText(data.getPrice());
        holder.vdiscount.setText(data.getDiscount());

        Glide.with(mContext).load(listdata.get(position).getImageURL()).into(holder.imageView);

        holder.backgroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(v.getContext(),ProductBrief.class);
                go.putExtra("stuff",listdata.get(position));
                v.getContext().startActivity(go);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView vname , vprice,vdiscount;
        ImageView imageView;
        CardView backgroundView;

        public MyHolder(View itemView) {
            super(itemView);
            vname = (TextView) itemView.findViewById(R.id.prdttit);
            vprice = (TextView) itemView.findViewById(R.id.prdtprice);
            vdiscount = (TextView) itemView.findViewById(R.id.prdtdis);
            imageView=  (ImageView) itemView.findViewById(R.id.prdtimg);
            backgroundView = (CardView) itemView.findViewById(R.id.bg_card);

        }
    }


}