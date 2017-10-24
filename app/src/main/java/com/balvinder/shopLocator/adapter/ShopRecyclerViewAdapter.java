package com.balvinder.shopLocator.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balvinder.shopLocator.R;
import com.balvinder.shopLocator.activity.ShopDetailActivity;
import com.balvinder.shopLocator.model.Shop;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShopRecyclerViewAdapter extends RecyclerView.Adapter<ShopRecyclerViewAdapter.ShopViewholder> {

    List<Shop> shops;
    Context context;
    LatLng currentLatLng;
    public ShopRecyclerViewAdapter(Context context,List<Shop> shops,LatLng currentLatLng) {
        this.shops = shops;
        this.context = context;
        this.currentLatLng = currentLatLng;
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    @Override
    public ShopViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_card_layout,parent,false);
        ShopViewholder shopViewholder = new ShopViewholder(view);
        return shopViewholder;

    }

    @Override
    public void onBindViewHolder(ShopViewholder holder, int position) {
        holder.shopName.setText(shops.get(position).getName());
        holder.shopAddress.setText(shops.get(position).getVicinity());

        Picasso.with(context).load(shops.get(position).getIcon()).into(holder.shopImage);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ShopViewholder extends RecyclerView.ViewHolder{
        @BindView(R.id.cv)
        CardView cardView;

        @BindView(R.id.shop_name)
        TextView shopName;

        @BindView(R.id.shop_address)
        TextView shopAddress;

        @BindView(R.id.imageView)
        ImageView shopImage;
        Context context;

        public ShopViewholder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.cv)
        public void startShopDetailActivity(CardView cv){
            Intent intent = new Intent(context,ShopDetailActivity.class);
            String placeId = shops.get(getAdapterPosition()).getPlace_id();
            intent.putExtra("placeIdKey",placeId);
            intent.putExtra("currentLat",currentLatLng.latitude);
            intent.putExtra("currentLong",currentLatLng.longitude);
            context.startActivity(intent);
        }
    }
}
