package com.example.conexionbt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonEnableBluetooth = findViewById(R.id.button_enable_bluetooth);
        // Comprobar si Bluetooth está habilitado
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT)== PackageManager.PERMISSION_DENIED){
            if(Build.VERSION.SDK_INT>=31){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 100);
                return;
            }
        }
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);

        if(Build.VERSION.SDK_INT>=31){
            bluetoothAdapter = bluetoothManager.getAdapter();
        }else{
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(getApplicationContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_SHORT).show();
        }
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            buttonEnableBluetooth.setVisibility(View.GONE);
        }

        buttonEnableBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Si Bluetooth está deshabilitado, habilitarlo
                if (!bluetoothAdapter.isEnabled()) {
                    try {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, 1);
                    } catch (SecurityException e) {
                        // Mostrar un mensaje de error
                        Toast.makeText(getApplicationContext(), "No tienes permiso para habilitar Bluetooth", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}