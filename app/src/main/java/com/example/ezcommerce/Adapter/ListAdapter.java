package com.example.ezcommerce.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezcommerce.Model.Product;
import com.example.ezcommerce.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    List<Product> products;
    Context context;
    private OnProductListener onProductListener;

    public ListAdapter(List<Product> products, Context context, OnProductListener onProductListener) {
        this.products = products;
        this.context = context;
        this.onProductListener = onProductListener;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new ListAdapter.ViewHolder(view, onProductListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.imageView.setImageBitmap(product.getImage());
        holder.tv_name.setText(product.getName());
        String price = "$" + product.getPrice() + "";
        holder.tv_price.setText(price);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView tv_name, tv_price;
        OnProductListener onProductListener;

        public ViewHolder(@NonNull View itemView, OnProductListener onProductListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            tv_name = itemView.findViewById(R.id.tv_item_name);
            tv_price = itemView.findViewById(R.id.tv_item_price);
            this.onProductListener = onProductListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProductListener.onClickProduct(products.get(getAdapterPosition()).getId());
        }
    }

    public interface OnProductListener {
        void onClickProduct(int id);
    }
}
