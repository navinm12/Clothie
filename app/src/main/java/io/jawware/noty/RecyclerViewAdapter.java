package io.jawware.noty;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder>{

    List<Listdata> listdata;
    Context mContext;
    String catogType;

    public RecyclerViewAdapter(List<Listdata> listdata, Context context) {
        this.listdata = listdata;
        this.mContext = context;
}

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, int position) {
        Listdata data = listdata.get(position);


            holder.vname.setText(data.getName());
            holder.vprice.setText(data.getPrice());
            holder.vdiscount.setText(data.getDiscount());
            Glide.with(mContext).load(listdata.get(position).getImageURL()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView vname , vprice,vdiscount;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            vname = (TextView) itemView.findViewById(R.id.producttitle);
            vprice = (TextView) itemView.findViewById(R.id.productprize);
            vdiscount = (TextView) itemView.findViewById(R.id.prductdis);
            imageView=  (ImageView) itemView.findViewById(R.id.productpic);

        }
    }


}