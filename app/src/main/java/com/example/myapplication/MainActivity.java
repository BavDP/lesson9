package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.helpers.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.categories);
        showProductCategories();
    }
    /// отображение списка категорий товаров из json-файла category.json
    private void showProductCategories() {
        InputStream productCategory = getResources().openRawResource(R.raw.category);
        try {
            JSONArray categories = new JsonReader(productCategory).getAsArray();
            for (int i = 0; i < categories.length(); i++) {
                JSONObject category = (JSONObject) categories.get(i);
                linearLayout.addView(createProductListElement(category));
            }
        } catch (JSONException e) {
            e.getStackTrace();
        }
    }

    private View createProductListElement(JSONObject category) throws JSONException {
        LayoutInflater inflater = getLayoutInflater();
        View categoryView = inflater.inflate(R.layout.product_category, null);
        categoryView.setOnClickListener((View v) -> {
            // при клике на пункт каталога переходим на его товары
            gotoProductsActivity(category);
        });
        TextView name = categoryView.findViewById(R.id.categoryName);
        name.setText(category.getString("name"));
        ImageView imageView = categoryView.findViewById(R.id.categoryIcon);
        String iconName = category.getString("icon");
        int iconId = getResources().getIdentifier(iconName, "drawable", getPackageName());
        imageView.setImageResource(iconId);
        return categoryView;
    }

    /// выполняем переход на новое активити с помощбю передачи данных
    /// через Intent
    private void gotoProductsActivity(JSONObject viewModel) {
        Intent intent = new Intent(this, ProductsOfCategoryActivity.class);
        // TODO: передача данных через Intent
        try {
            intent.putExtra("categoryName", viewModel.getString("icon"));
            intent.putExtra("categoryTitle", viewModel.getString("name"));
            this.startActivity(intent);
        } catch (JSONException e) {
            e.getStackTrace();
        }
    }
}