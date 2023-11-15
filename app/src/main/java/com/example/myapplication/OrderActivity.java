package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.models.ProductModel;
import com.example.myapplication.models.UserModel;

public class OrderActivity extends AppCompatActivity {

    private final int WAIT_INTERVAL = 120000;
    private TextView productNameView;
    private TextView productPriceView;
    private TextView userNameView;
    private TextView userLastNameView;
    private TextView userPhoneView;
    private TextView userEmailView;
    private TextView timerView;
    private Button orderButton;
    private TextView TimerView;
    private ProductModel productModel;
    private long remainTime = WAIT_INTERVAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        // TODO: восстанавливаем интервал таймера, который был ДО переворота экрана
        if (savedInstanceState != null) {
            remainTime = savedInstanceState.getLong("REMAIN_TIME");
        }
        init();
        createTimer();
        showProductData();
        orderButtonSetup();
    }

    // TODO: запоминаем текущее оставшееся время до завершения заказа
    // чтобы восстановить таймер после поворота экрана
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("REMAIN_TIME", remainTime);
    }

    // TODO: передача данных о пользователе и покупаемом товаре на страницу
    // подтверждения заказа
    // Данные о пользователе передаются, реализуя интерфейс Parcelable
    // Данные о заказе передаются через Serializable
    // Оба объекта передаются через Bundle
    // Также тут новая активность делается первой в стеке активностей
    private void gotoConfirmLayout(UserModel userModel, ProductModel model) {
        if (userModel.isValid()) {
            Intent intent = new Intent(this, OrderConfirmationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", userModel);
            bundle.putSerializable("order", model);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }

    private void init() {
        timerView = ((TextView)findViewById(R.id.timer));
        userNameView = ((TextView)findViewById(R.id.userName));
        userLastNameView = ((TextView)findViewById(R.id.userLastName));
        userPhoneView = ((TextView)findViewById(R.id.userPhone));
        userEmailView = ((TextView)findViewById(R.id.userEmail));
        productNameView = findViewById(R.id.orderProductName);
        productPriceView = findViewById(R.id.orderProductPrice);
        orderButton = findViewById(R.id.doOrderButton);
        timerView = findViewById(R.id.timer);
        productModel = (ProductModel) getIntent().getExtras().getSerializable("product");
    }

    private void showProductData() {
        // TODO: получение данных от запускающего layout через Serializable
        productNameView.setText(productModel.getProductName());
        productPriceView.setText(getResources().getString(R.string.price, productModel.getProductPrice() + ""));
    }

    private void orderButtonSetup() {
        orderButton.setTag(R.id.orderButtonOrder, productModel);
        orderButton.setOnClickListener((View v) -> {
            UserModel userData = new UserModel(
                    userNameView.getText().toString(),
                    userLastNameView.getText().toString(),
                    userPhoneView.getText().toString(),
                    userEmailView.getText().toString()
            );
            gotoConfirmLayout(userData, (ProductModel)v.getTag(R.id.orderButtonOrder));
        });
    }

    // таймер отсчитывает 2 минуты, по истечению которых кнопка заказа делается неактивной
    private void createTimer() {
        new CountDownTimer(Math.min(WAIT_INTERVAL, remainTime), 1000) {
            @Override
            public void onTick(long l) {
                remainTime = l;
                timerView.setText(getResources().getString(R.string.remainTime, Math.round(l / 1000.0)));
            }

            @Override
            public void onFinish() {
                // время вышло и запрещаем заказывать
                orderButton.setEnabled(false);
                timerView.setVisibility(View.INVISIBLE);
            }
        }.start();
    }
}