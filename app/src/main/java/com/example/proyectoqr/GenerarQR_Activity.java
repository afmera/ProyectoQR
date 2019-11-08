package com.example.proyectoqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Environment;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import net.glxn.qrgen.android.QRCode;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;

public class GenerarQR_Activity extends AppCompatActivity {

    private boolean tienePermisoParaEscribir = false;
    private static final int ALTURA_CODIGO = 500, ANCHURA_CODIGO = 500;

    TextView etTextoParaCodigo, etTextoparagenerar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_qr_);

        etTextoParaCodigo = findViewById(R.id.edtidentificacion);
        etTextoparagenerar = findViewById(R.id.edtcontraseña);
        final ImageView imagenCodigo = findViewById(R.id.ivCodigoGenerado);
        Button btnenviar = findViewById(R.id.btnenviar);
        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = obtenerTextoParaCodigo();
                if (texto.isEmpty()) return;
                String posibleTexto = Textogenerado();
                if (texto.isEmpty()) return;
                if (!tienePermisoParaEscribir) {
                    Intent i = new Intent(GenerarQR_Activity.this, MainActivity.class);
                    startActivity(i);
                }
                ByteArrayOutputStream byteArrayOutputStream = QRCode.from(texto).withSize(
                        ANCHURA_CODIGO, ALTURA_CODIGO).stream();
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(
                            Environment.getExternalStorageDirectory() + "/foto" + ".png");
                    byteArrayOutputStream.writeTo(fos);
                    Toast.makeText(GenerarQR_Activity.this, "Reserva realizada con exito", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String obtenerTextoParaCodigo() {
        etTextoParaCodigo.setError(null);
        String posibleTexto = etTextoParaCodigo.getText().toString();
        if (posibleTexto.isEmpty()) {
            etTextoParaCodigo.setError("Debe ingresar su numero de identificación");
            etTextoParaCodigo.requestFocus();
        }
        return posibleTexto;
    }

    private String Textogenerado() {
        etTextoParaCodigo.setError(null);
        String Texto = etTextoparagenerar.getText().toString();
        if (Texto.isEmpty()) {
            etTextoparagenerar.setError("Debe ingresar contraseña");
            etTextoparagenerar.requestFocus();
        }
        return Texto;
    }
}
