package com.example.a10_sqlite_01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lv_Travel;
    ArrayList<Travel> travels;
    TravelAdapter adapter;
    Button btnSave, btnCancel;
    EditText edt_Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_Travel = findViewById(R.id.lv_Travel);
        travels = new ArrayList<>();

        adapter = new TravelAdapter(this, R.layout.mot_dong_visit, travels);
        lv_Travel.setAdapter(adapter);


        // db
        database = new Database(this, "Note.Sqlite", null, 1);
        // table
        database.queryData("CREATE TABLE IF NOT EXISTS Travel(id INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR(50))");
        // insert
//        database.queryData("INSERT INTO Note VALUES(null,'Lập trình di động')");
//        database.queryData("INSERT INTO Note VALUES(null,'Lập trình React Native')");

//        database.queryData("INSERT INTO Travel VALUES(null,'Da Lat')");
//        database.queryData("INSERT INTO Travel VALUES(null,'Buon Me Thuoc')");
//        database.queryData("INSERT INTO Travel VALUES(null,'Can Tho')");

        getAllData();

        // thim button
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        edt_Name = findViewById(R.id.edt_Name);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = edt_Name.getText().toString();
                if (string.equals(""))
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                else {
                    database.queryData("INSERT INTO Travel VALUES(null,'" + string + "')");
                    Toast.makeText(MainActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                    getAllData();
                }

            }
        });
    }

    private void getAllData() {
        // Đọc dữ liệu
        Cursor cursor = database.queryDataReturn("SELECT * FROM Travel");
        travels.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            travels.add(new Travel(id, name));
        }
        adapter.notifyDataSetChanged();
    }

    public void dialogUpdateTravel(String name_travel, int id_travel){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update);
        EditText edtUpdate_Dialog = dialog.findViewById(R.id.edtUpdate_Name);
        Button btnConfirm_Dialog = dialog.findViewById(R.id.btnConfirm_UpdateDialog);
        Button btnHuy_Dialog = dialog.findViewById(R.id.btnHuy_DialogUpdate);

        edtUpdate_Dialog.setText(name_travel);

        btnHuy_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = edtUpdate_Dialog.getText().toString();
                database.queryData("UPDATE Travel SET name = '"+newName+"' where id = '"+id_travel+"' ");
                Toast.makeText(MainActivity.this, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getAllData();
            }
        });

        dialog.show();
    }
    public void deleteTravel(String name, int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có mún xóa '"+name+"' ko ???");
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.queryData("DELETE FROM Travel where id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Đã xóa " + name, Toast.LENGTH_SHORT).show();
                getAllData();
            }
        });

        dialogXoa.show();
    }

}