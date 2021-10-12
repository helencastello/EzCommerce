package com.example.ezcommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.ezcommerce.Fragment.ProductDetailFragment;
import com.example.ezcommerce.Fragment.ProductListFragment;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null) {
                int id = bundle.getInt("id");

                ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                productDetailFragment.setArguments(bundle);
                productDetailFragment.setProductPosition(id);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, productDetailFragment);
                ft.commit();
            }
        }
    }
}
