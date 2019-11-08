package com.example.proyectoqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;

public class LeerQR_Activity extends AppCompatActivity {

    private static final int CODIGO_PERMISO_ESCRIBIR_ALMACENAMIENTO = 1;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private boolean tienePermisoParaEscribir = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_qr_);
        Button btn_checkin=(Button)findViewById(R.id.btnenviar);

        btn_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeerQR_Activity.this, QrCodeActivity.class);
                Toast.makeText(LeerQR_Activity.this, "Va iniciar el Lector QR", Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
            }
        });
    }

    /*public void onClick(View v) {
        Intent intent = new Intent(LeerQR_Activity.this, QrCodeActivity.class);
        Toast.makeText(this, "Va iniciar el Lector QR", Toast.LENGTH_SHORT).show();
        startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
    }*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getApplicationContext(), "No se pudo obtener una respuesta.", Toast.LENGTH_SHORT).show();
            String resultado = data.getStringExtra(".error_decodificacion_en_la_imagen");
            if (resultado != null) {
                Toast.makeText(getApplicationContext(), "No se puede scannear el codigo fuente.", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data != null) {
                String lectura = data.getStringExtra("Codigo leido Correctamente.");
                Toast.makeText(getApplicationContext(), "Leido :" + lectura, Toast.LENGTH_SHORT).show();
            }
        }
        verificaryPerdirPermiso();
    }

    private void verificaryPerdirPermiso() {
        if (ContextCompat.checkSelfPermission(LeerQR_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED)) {
            //En caso de que haya dado permiso ponemos la bandera de true.
            tienePermisoParaEscribir = true;
        } else {
            ActivityCompat.requestPermissions(LeerQR_Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODIGO_PERMISO_ESCRIBIR_ALMACENAMIENTO);
        }
    }

    private void noTienPermisos() {
        Toast.makeText(LeerQR_Activity.this, "No haz dado permisos para escribir.", Toast.LENGTH_SHORT).show();
    }
    //@Override
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISO_ESCRIBIR_ALMACENAMIENTO:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //Si dieron permisos.
                    tienePermisoParaEscribir = true;
                } else {
                    //No dieron permisos
                    noTienPermisos();
                }
        }
    }
}
