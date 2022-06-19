package com.sattazalyk.car_gps2;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.sattazalyk.car_gps2.serverData.Account;
import com.sattazalyk.car_gps2.serverData.DBHandler;

public class LoginActivity extends AppCompatActivity {
    Button btn_enter, btn_registration;
    TextView edt_login, edt_pass;

    DBHandler dbHandler = new DBHandler(this);

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
            Account account = new Account();

            String iid = edt_login.getText().toString().trim();
            String pass = edt_pass.getText().toString().trim();

            if(!iid.equals("") && !pass.equals("")){
                Boolean checkAccount = dbHandler.checkAccountPass(iid, pass);
                if(checkAccount == true){
                    Toast.makeText(this,"Вы вошли в приложение",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);


                    startActivity(intent);
                } else Toast.makeText(this, "Неверные данные!",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Заполните формы!", Toast.LENGTH_SHORT).show();
            }

        });

        btn_registration.setOnClickListener(view -> {
           Intent intent = new Intent(this, RegistrationActivity.class);
           startActivity(intent);
        });
    }
}