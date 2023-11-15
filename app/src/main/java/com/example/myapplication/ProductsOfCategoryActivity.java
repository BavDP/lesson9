package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myapplication.helpers.JsonReader;
import com.example.myapplication.models.ProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class ProductsOfCategoryActivity extends AppCompatActivity {
    private String categoryName;
    private LinearLayout list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_of_category);
        init();
        showProducts();
    }

    private void init() {
        TextView titleView = findViewById(R.id.title);
        list = findViewById(R.id.productsList);
        // TODO: получение данных из Intent
        Intent intent = getIntent();
        titleView.setText(intent.getExtras().getString("categoryTitle"));
        categoryName = intent.getExtras().getString("categoryName");
    }

    private void showProducts() {
        // по названию категории загружаем json-файл из каталога raw
        InputStream products = getResources().openRawResource(getResources().getIdentifier(categoryName, "raw", getPackageName()));
        JSONArray productsJson = new JsonReader(products).getAsArray();
        for (int i = 0; i < productsJson.length(); i++) {
            try {
                JSONObject product = (JSONObject) productsJson.get(i);
                list.addView(createProductListElement(product));
            } catch (JSONException e) {
                e.getStackTrace();
            }
        }
    }

    private View createProductListElement(JSONObject product) throws JSONException{
        LayoutInflater inflater = getLayoutInflater();
        View productView = inflater.inflate(R.layout.product, null);
        TextView productTitle = productView.findViewById(R.id.productTitle);
        TextView productPrice = productView.findViewById(R.id.productPrice);
        TextView productCode = productView.findViewById(R.id.productCode);
        RatingBar productRating = productView.findViewById(R.id.productRating);
        Button orderButton = productView.findViewById(R.id.orderButton);

        String pTitle = product.getString("name");
        String pPrice = product.getString("price");
        String pCode = product.getString("id");

        productTitle.setText(pTitle);
        productPrice.setText(getResources().getString(R.string.price, pPrice));
        productCode.setText(getResources().getString(R.string.productCode, pCode));

        double rating = product.getDouble("rating");
        productRating.setRating((float)rating);
        ProductModel productModel = new ProductModel(pCode, pTitle, Double.parseDouble(pPrice));
        orderButton.setOnClickListener((View v) -> gotoOrderLayout(productModel));

        return productView;
    }

    // TODO: передача данных новой активности с использованием Serializable
    private void gotoOrderLayout(ProductModel productModel) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("product", productModel);
        startActivity(intent);
    }
}