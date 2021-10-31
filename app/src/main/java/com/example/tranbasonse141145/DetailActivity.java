package com.example.tranbasonse141145;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import com.example.tranbasonse141145.daos.ArmorDAO;
import com.example.tranbasonse141145.dtos.ArmorDTO;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private EditText edtID, edtClassification,edtDescription,edtTimeOfCreate,editDefense;
    private  String action;
    private
    SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Spinner spinnerStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.spinnerStatus = (Spinner) findViewById(R.id.spinner_status);
        String[] listStatus = {"In progress", "Finished"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                listStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerStatus.setAdapter(adapter);
        this.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerStatus.setSelection(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        editDefense = findViewById(R.id.edtDefense);
        edtID = findViewById(R.id.edtID);
        edtClassification = findViewById(R.id.edtClassification);
        edtDescription = findViewById(R.id.edtDescription);
        edtTimeOfCreate = findViewById(R.id.edtTimeOfCreate);
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        if (action.equals("update")) {
            spinnerStatus.setEnabled(true);
            ArmorDTO dto = (ArmorDTO) intent.getSerializableExtra("dto");
            edtID.setText(dto.getArmorId() + "");
            edtClassification.setText(dto.getClassification());
            edtDescription.setText(dto.getDescription());
            edtTimeOfCreate.setText(dto.getTimeOfCreate());
            editDefense.setText(dto.getDefense() + "");
            String status = dto.isStatus() ? "In progress" :"Finished";

            for (int position = 0; position < spinnerStatus.getCount(); position++) {
                if (spinnerStatus.getItemAtPosition(position).equals(status)) {
                    spinnerStatus.setSelection(position);
                    break;
                }
            }

        } else if (action.equals("create")) {
            spinnerStatus.setEnabled(false);
            edtID.setText(intent.getStringExtra("id"));
            Date myDate = new Date();
            String timeCreate = timeStampFormat.format(myDate);
            edtTimeOfCreate.setText(timeCreate);
        }
    }

    public void clickToDelete(View view) {
        Intent intent = getIntent();
        ArmorDTO dto = (ArmorDTO) intent.getSerializableExtra("dto");
        String idDelete = dto.getArmorId();

        try {
            ArmorDAO dao= new ArmorDAO();
            FileInputStream fis = openFileInput("Armor.txt");
            List<ArmorDTO> list = dao.loadFromInternal(fis);
            for (int i = 0; i< list.size(); i++){
                if( list.get(i).getArmorId().equals(idDelete) ){
                    list.remove(i);
                    FileOutputStream fos= openFileOutput("Armor.txt", MODE_PRIVATE);
                    dao.saveToInternal(fos,list);
                    Toast.makeText(this, "Save Internal Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        } catch (Exception  throwables) {
            throwables.printStackTrace();
        }

    }
}