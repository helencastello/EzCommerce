package com.example.ezcommerce.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezcommerce.CartActivity;
import com.example.ezcommerce.Interface.Api;
import com.example.ezcommerce.Model.Data;
import com.example.ezcommerce.Model.Product;
import com.example.ezcommerce.ProductDetailActivity;
import com.example.ezcommerce.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    TextView tvName, tvPrice, tvDesc;
    ImageView ivImg;
    Button buyBtn;
    int productId;
    Product product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        return view;
    }

    public void setProductPosition(int productPosition) {
        this.productId = productPosition;

        Bundle bundle = getArguments();
        if(bundle != null) {
            productId = bundle.getInt("id");
            String stringId =  productId+ "";
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Data> call = api.getDetailById(productId);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data data = response.body();
                List<Product> products = data.getProducts();
                product = products.get(0);
                product.setImg(product.getImg());
                Log.d("product name get", product.getName());

                tvName.setText(product.getName());
                String price = "$" + product.getPrice() + "";
                tvPrice.setText(price);
                tvDesc.setText(product.getDescription());
                ivImg.setImageBitmap(product.getImage());

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = (TextView) view.findViewById(R.id.tv_detail_name);
        tvPrice = (TextView) view.findViewById(R.id.tv_detail_price);
        tvDesc = (TextView) view.findViewById(R.id.tv_detail_desc);
        ivImg = (ImageView) view.findViewById(R.id.iv_detail_image);
        buyBtn = (Button) view.findViewById(R.id.btn_buy);
        buyBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("clickkkk", "adaa");

        Bundle bundle = new Bundle();
        bundle.putInt("id", productId);

        Intent intent = new Intent(getActivity(), CartActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
