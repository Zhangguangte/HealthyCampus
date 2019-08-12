package com.example.health;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText Name,Pwd;
    private Button Sign;
    private TextView Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Name = findViewById(R.id.Name);
        Pwd = findViewById(R.id.Pwd);
        Sign = findViewById(R.id.Sign);
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Name.getText().toString().trim().equals("123456")&&Pwd.getText().toString().trim().equals("123456"))
                {
                    Intent intent=new Intent(LoginActivity.this, IndexActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(LoginActivity.this,Name.getText().toString()+"|"+Pwd.getText().toString().trim()+"|",Toast.LENGTH_LONG).show();
                }
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}
