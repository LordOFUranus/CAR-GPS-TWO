package com.sattazalyk.car_gps2;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.sattazalyk.car_gps2.serverData.DBHandler;

public class RegistrationActivity extends AppCompatActivity {

    EditText edt_fn, edt_ln, edt_phone, edt_iid, edt_pass;
    Button btn_end_reg;

    private void initializationUI() {
        edt_fn = findViewById(R.id.edt_fn);
        edt_ln = findViewById(R.id.edt_ln);
        edt_phone = findViewById(R.id.edt_phone);
        edt_iid = findViewById(R.id.edt_iid);
        edt_pass = findViewById(R.id.edt_reg_pass);
        btn_end_reg = findViewById(R.id.btn_end_reg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initializationUI();
        DBHandler dbHandler = new DBHandler();

        btn_end_reg.setOnClickListener(view -> {
            if (!edt_fn.getText().toString().trim().equals("")
                    && !edt_ln.getText().toString().trim().equals("")
                    && !edt_phone.getText().toString().trim().equals("")
                    && !edt_iid.getText().toString().trim().equals("")
                    && !edt_pass.getText().toString().trim().equals("")) {
                if (edt_iid.getText().length() == 12) {
                    if (edt_phone.getText().length() == 11)  {
                        dbHandler.registration(edt_fn.getText().toString().trim(),
                                edt_ln.getText().toString().trim(),
                                edt_phone.getText().toString().trim(),
                                edt_iid.getText().toString().trim(),
                                edt_pass.getText().toString().trim());
                    } else Toast.makeText(this, "Казахстанский номер состоит из 11 цифр", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(this, "ИИН состоит из 12 цифр", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
        });
    }
}