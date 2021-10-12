package com.example.ezcommerce.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezcommerce.Model.Cart;
import com.example.ezcommerce.Model.Product;
import com.example.ezcommerce.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>  {
    List<Product> products;
    List<Cart> carts;
    Context context;
    private final ClickListener listener;

    public CartAdapter(List<Product> products, List<Cart> carts, Context context, ClickListener listener) {
        this.products = products;
        this.carts = carts;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);

        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.imageView.setImageBitmap(product.getImage());
        holder.tv_name.setText(product.getName());
        int qty = 0;
        for (Cart cart: carts) {
            if(cart.getProductId() == product.getId()) {
                qty = cart.getQty();
                break;
            }
        }
        String price = "$" + product.getPrice() + "";
        holder.tv_price.setText(price);
        holder.tv_qty.setText(String.valueOf(qty+"pcs"));
        holder.tv_desc.setText(product.getDescription());
        Log.d("bind name", product.getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CartAdapter.ClickListener  {
        ImageView imageView;
        TextView tv_name, tv_price, tv_qty, tv_desc;
        ImageButton btnUp, btnDown;
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            imageView = itemView.findViewById(R.id.iv_cart_image);
            tv_name = itemView.findViewById(R.id.tv_cart_item_name);
            tv_price = itemView.findViewById(R.id.tv_cart_item_price);
            tv_qty = itemView.findViewById(R.id.tv_cart_item_qty);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            btnUp = itemView.findViewById(R.id.btn_increment);
            btnDown = itemView.findViewById(R.id.btn_decrement);

            btnUp.setOnClickListener(this);
            btnDown.setOnClickListener(this);
        }

        @Override
        public void onClickPlus(int position) {
            listenerRef.get().onClickPlus(getAdapterPosition());
        }

        @Override
        public void onClickMinus(int position) {
            listenerRef.get().onClickMinus(getAdapterPosition());
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == btnUp.getId()) {
                listenerRef.get().onClickPlus(getAdapterPosition());
            } else if (v.getId() == btnDown.getId()) {
                listenerRef.get().onClickMinus(getAdapterPosition());
            }
        }
    }

    public interface ClickListener {

        void onClickPlus(int position);

        void onClickMinus(int position);
    }
}
