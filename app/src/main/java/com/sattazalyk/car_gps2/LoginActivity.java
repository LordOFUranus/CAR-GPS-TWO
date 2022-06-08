package com.sattazalyk.car_gps2;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    Button btn_enter, btn_registration;
    TextView edt_login, edt_pass;

    private void initializationUI() {
        btn_enter = findViewById(R.id.btn_enter);
        btn_registration = findViewById(R.id.btn_registration);
        edt_login = findViewById(R.id.edt_login);
        edt_pass = findViewById(R.id.edt_pass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializationUI();

        btn_enter.setOnClickListener(view -> {
            if (!edt_login.getText().toString().trim().equals("") && !edt_pass.getText().toString().trim().equals("")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else Toast.makeText(this,"Введите данные",Toast.LENGTH_SHORT).show();
        });

        btn_registration.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}