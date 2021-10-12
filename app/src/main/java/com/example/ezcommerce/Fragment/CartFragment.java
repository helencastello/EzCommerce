package com.example.ezcommerce.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezcommerce.Adapter.CartAdapter;
import com.example.ezcommerce.Adapter.ListAdapter;
import com.example.ezcommerce.Interface.Api;
import com.example.ezcommerce.Model.Cart;
import com.example.ezcommerce.Model.Data;
import com.example.ezcommerce.Model.Product;
import com.example.ezcommerce.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements CartAdapter.ClickListener{

    List<Product> products = new ArrayList<>();
    RecyclerView recyclerViewCart;
    CartAdapter cartAdapter;
    TextView tv_subtotal, tv_taxes, tv_shipping, tv_total;
    float subtotal, taxes, shipping, total;
    Button btnNext, btnCancel;

    private static List<Cart> carts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_cart, container, false);

        tv_subtotal = view.findViewById(R.id.tv_subtotal);
        tv_taxes = view.findViewById(R.id.tv_taxes);
        tv_shipping = view.findViewById(R.id.tv_shipping);
        tv_total = view.findViewById(R.id.tv_total);
        btnNext = view.findViewById(R.id.btn_next);
        btnCancel = view.findViewById(R.id.btn_cancel);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Transaction has done\n Cart will be emptied", Toast.LENGTH_LONG).show();
                emptyCart();
                Log.d("next cuy", "next");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new ProductListFragment());
                ft.commit();
                Log.d("cancel cuy", "cancel");
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false
        );

        recyclerViewCart= view.findViewById(R.id.rv_cart);
        recyclerViewCart.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<Data> call = api.getData();

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                products = response.body().getProducts();

                setView();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addToCart(int id) {
        Boolean exist = false;
        for (Cart cart : carts) {
            if (cart.getProductId() == id) {
                exist = true;
                break;
            }
        }

        if(exist) {
            for (Cart cart : carts) {
                if (cart.getProductId() == id) {
                    cart.setQty(cart.getQty()+1);
                }
            }
        } else {
            Cart cart = new Cart(id, 1);
            carts.add(cart);
        }
    }

    @Override
    public void onClickPlus(int position) {
        Log.d("klik qty", String.valueOf(position + " up"));
        carts.get(position).setQty(carts.get(position).getQty() + 1);
        setView();
    }

    @Override
    public void onClickMinus(int position) {
        Log.d("klik qty", String.valueOf(position + " down"));
        if(carts.get(position).getQty() > 0) {
            carts.get(position).setQty(carts.get(position).getQty() - 1);
        }
        if(carts.get(position).getQty() == 0) carts.remove(position);
        setView();
    }

    public void setView(){
        List<Product> productsInCart = new ArrayList<>();

        subtotal = 0;

        for (Product p : products) {
            Log.d("product nih", p.getName());
            p.setImg(p.getImg());
            for (Cart cart : carts) {
                if(cart.getProductId() == p.getId()) {
                    productsInCart.add(p);
                    subtotal += p.getPrice() * cart.getQty();
                }
            }
        }

        taxes = subtotal == 0 ? 0 : 3;
        shipping = subtotal == 0 ? 0 : 5;
        total = subtotal + shipping + taxes;

        tv_shipping.setText(String.valueOf("$" + shipping));
        tv_taxes.setText(String.valueOf("$" + taxes));
        tv_subtotal.setText(String.valueOf("$" + subtotal));
        tv_total.setText(String.valueOf("$" + total));

        cartAdapter = new CartAdapter(productsInCart, carts, getActivity(), CartFragment.this);
        recyclerViewCart.setAdapter(cartAdapter);
    }

    public void emptyCart() {
        carts.clear();
        setView();
    }
}
