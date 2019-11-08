package com.example.proyectoqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_generarQR = (Button) findViewById(R.id.GenerarQR_Button);
        Button btn_leerQR = (Button) findViewById(R.id.LeerQR_Button);

        btn_generarQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GenerarQR_Activity.class);
                startActivity(intent);
            }
        });

        btn_leerQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeerQR_Activity.class);
                startActivity(intent);
            }
        });
    }
}
