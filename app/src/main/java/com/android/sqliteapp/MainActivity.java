package com.android.sqliteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabAdd ;
    ListView listView;
    DatabaseHelper databaseHelper;
    ArrayList<Map<String,String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = findViewById(R.id.fab_add);
        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        fabAdd.setOnClickListener(v-> startActivity(new Intent(this, InputActivity.class)));

        listView.setOnItemLongClickListener((parent, view, position, i) -> {
            final int id = Integer.parseInt(Objects.requireNonNull(arrayList.get(position).get("id")));
            showConfirm(id);
            return true;
        });
    }

    private void showConfirm(final int id){
        new AlertDialog.Builder(this)
                .setTitle("Hapus Data")
                .setMessage("Apakah anda yakin menghapus data ini ? ")
                .setPositiveButton("Ya", (dialog, which) -> deleteData(id))
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void deleteData(int id){
        databaseHelper.delete(id);
        arrayList.clear();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        arrayList = databaseHelper.getAllStudents();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList,
                android.R.layout.simple_list_item_2, new String[]{"nama","alamat"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }
}