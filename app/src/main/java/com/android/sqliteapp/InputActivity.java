package com.android.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    EditText editNama;
    EditText editAlamat;
    Button buttonSubmit;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input2);

        editNama = findViewById(R.id.edit_nama);
        editAlamat = findViewById(R.id.edit_alamat);
        buttonSubmit = findViewById(R.id.button_submit);
        databaseHelper = new DatabaseHelper(this);

        buttonSubmit.setOnClickListener(v -> submitData());

    }

    private void submitData(){
        if(editNama.getText().toString().length() > 0
                && editAlamat.getText().toString().length() > 0){
            long add = databaseHelper.addStudent(editNama.getText().toString(),
                    editAlamat.getText().toString());
            if (add>0){
                Toast.makeText(this, "Succes tambah data", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(this, "Gagal tambah data", Toast.LENGTH_SHORT).show();
            }

        }
    }
}