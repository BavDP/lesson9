package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.models.ProductModel;
import com.example.myapplication.models.UserModel;

public class OrderConfirmationActivity extends AppCompatActivity {
    private TextView productName;
    private TextView productPrice;
    private TextView userName;
    private TextView userLastName;
    private TextView userPhone;
    private TextView userEmail;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        init();
        showData();
    }

    private void init() {
        productName = findViewById(R.id.confirmProductName);
        productPrice = findViewById(R.id.confirmProductPrice);
        userName = findViewById(R.id.confirmUserName);
        userLastName = findViewById(R.id.confirmUserLastName);
        userPhone = findViewById(R.id.confirmUserPhone);
        userEmail = findViewById(R.id.confirmUserEmail);
        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(view -> {
            //TODO: закрываем приложение, т.к. эта активность первая в стеке активностей
            finish();
        });
    }

    private void showData() {
        // TODO: получаем данные из Bundle
        Bundle bundle = getIntent().getBundleExtra("bundle");
        UserModel userModel = (UserModel)bundle.getParcelable("user");
        ProductModel productModel = (ProductModel)bundle.getSerializable("order");
        productName.setText(productModel.getProductName());
        productPrice.setText(getResources().getString(R.string.price, productModel.getProductPrice() + ""));
        userName.setText(userModel.getFirstName());
        userLastName.setText(userModel.getLastName());
        userPhone.setText(userModel.getPhone());
        userEmail.setText(userModel.getEmail());
    }
}