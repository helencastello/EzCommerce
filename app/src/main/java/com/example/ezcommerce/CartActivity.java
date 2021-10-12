package com.example.ezcommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.ezcommerce.Fragment.CartFragment;
import com.example.ezcommerce.Fragment.ProductDetailFragment;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null) {
                int id = bundle.getInt("id");

                CartFragment cartFragment = new CartFragment();
                cartFragment.setArguments(bundle);
                cartFragment.addToCart(id);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, cartFragment);
                ft.commit();
            }
        }
    }
}
