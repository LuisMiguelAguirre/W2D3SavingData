package com.example.admin.w2d3savingdata;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;

    }

    public void createDataInfo(View view) {

        EditText nameId = (EditText) findViewById(R.id.name_id);
        EditText phoneId = (EditText) findViewById(R.id.phone_id);

        DAO dao = new DAO(this);
        long result = dao.Create(nameId.getText().toString(), phoneId.getText().toString());

        Toast.makeText(this, "Inserted in row: " + result, Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void readDataInfo(View view) {
        DAO dao = new DAO(this);
        List<String> persons;
        persons = dao.Read("");

        for (String item : persons) {
            System.out.println(item);
        }

    }

    public void updateDataInfo(View view) {
        DAO dao = new DAO(this);
        int result = dao.Update("7", "Luis", "00529933939703");
        Toast.makeText(this, "Updated record : " + result, Toast.LENGTH_SHORT).show();
    }

    public void dropDataInfo(View view) {
        DAO dao = new DAO(this);
        int result = dao.Drop("4");
        Toast.makeText(this, "Deleted record : " + result, Toast.LENGTH_SHORT).show();
    }
}
