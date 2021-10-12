package com.example.ezcommerce.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ezcommerce.Adapter.CategoryAdapter;
import com.example.ezcommerce.Adapter.ListAdapter;
import com.example.ezcommerce.Adapter.ListAdapter;
import com.example.ezcommerce.Interface.Api;
import com.example.ezcommerce.MainActivity;
import com.example.ezcommerce.Model.Data;
import com.example.ezcommerce.Model.Product;
import com.example.ezcommerce.ProductDetailActivity;
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
 */
public class ProductListFragment extends Fragment implements ListAdapter.OnProductListener {
    RecyclerView recyclerViewList;
    List<Product> products;
    ListAdapter listAdapter;
    RecyclerView recyclerViewCategory;
    List<String> categories = new ArrayList<>();
    CategoryAdapter categoryAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false
        );

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.HORIZONTAL, false
        );

        recyclerViewList= view.findViewById(R.id.rv_menu);
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setLayoutManager(layoutManager);

        recyclerViewCategory= view.findViewById(R.id.rv_category);
        recyclerViewCategory.setHasFixedSize(true);
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategory.setLayoutManager(layoutManager2);

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

                for (Product p : products) {
                    p.setImg(p.getImg());
                    if(!categories.contains(p.getCategory())) {
                        categories.add(p.getCategory());
                    }
                }
                listAdapter = new ListAdapter(products, getActivity(), ProductListFragment.this);
                recyclerViewList.setAdapter(listAdapter);
                categoryAdapter = new CategoryAdapter(categories, getActivity());
                recyclerViewCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickProduct(int id) {
        Log.d("click", "clicked "+ id);

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
