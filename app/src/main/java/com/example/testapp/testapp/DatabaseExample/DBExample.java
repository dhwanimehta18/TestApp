package com.example.testapp.testapp.DatabaseExample;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testapp.testapp.R;

import java.util.ArrayList;

public class DBExample extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    Button btnadd;
    Button btnshow;
    Button btnaddrecords;
    EditText etId;
    EditText etName;
    ListView lstEmployeelist;
    private Dialog d;
    private ArrayList<Employee> alist;
    private ArrayAdapter<Employee> adapter;
    private Employee selectedItem;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.cmi_of_db, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cmi_update:
                updateDialogue(selectedItem);
                break;
            case R.id.cmi_delete:
                removeRecord(selectedItem);

                NotificationManager notification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder mbuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(DBExample.this).setSmallIcon(R.mipmap.ic_launcher).setContentText(
                        "Hello").setContentTitle("notify").setAutoCancel(true);

                int id = 1;
                Intent i = new Intent(DBExample.this,DBExample.class);
                PendingIntent pi = PendingIntent.getActivity(DBExample.this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
                mbuilder.setContentIntent(pi);
                notification.notify(id,mbuilder.build());
                btnshow.performClick();
                //setContentView(R.layout.notification_demo);
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void removeRecord(Employee selectedItem) {
        Datahelper helper = new Datahelper(DBExample.this);
        if (helper.removeRecord(selectedItem)) {
            Toast.makeText(getApplicationContext(), "Record Deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDialogue(Employee selectedItem) {
        d = new Dialog(DBExample.this);
        d.setContentView(R.layout.add_records_dialogue);
        etId = (EditText) d.findViewById(R.id.etId);
        etId.setEnabled(false);
        etName = (EditText) d.findViewById(R.id.etName);
        btnaddrecords = (Button) d.findViewById(R.id.btnaddrecords);
        btnaddrecords.setText("UPDATE");
        etId.setText(selectedItem.getId());
        etName.setText(selectedItem.getName());
        btnaddrecords.setOnClickListener(DBExample.this);
        d.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbexample);
        btnadd = (Button) findViewById(R.id.btnadd);
        btnshow = (Button) findViewById(R.id.btnshow);
        lstEmployeelist = (ListView) findViewById(R.id.lstEmployeelist);

        btnadd.setOnClickListener(DBExample.this);
        btnshow.setOnClickListener(DBExample.this);
        btnshow.performClick();

        registerForContextMenu(lstEmployeelist);

        lstEmployeelist.setOnItemLongClickListener(DBExample.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnadd:
                addEmployeeRecords();
                break;
            case R.id.btnshow:
                showEmployeeRecords();
                break;
            case R.id.btnaddrecords:
                if (btnaddrecords.getText().equals("UPDATE")) {
                    d.dismiss();
                    updateRecords(selectedItem);
                    btnshow.performClick();
                } else {
                    d.dismiss();
                    addRecordsDB();
                }
                break;
        }
    }

    private void updateRecords(Employee selectedItem) {
        String etname = etName.getText().toString();
        Datahelper helper = new Datahelper(DBExample.this);

        if (helper.updateSelectedEmployee(selectedItem, etname)) {
            Toast.makeText(getApplicationContext(), "Record Updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    private void addRecordsDB() {
        Employee emp = new Employee();
        emp.setId(etId.getText().toString());
        emp.setName(etName.getText().toString());
        Datahelper helper = new Datahelper(DBExample.this);
        if (helper.addEmployeeRecords(emp)) {
            Toast.makeText(getApplicationContext(), "Record Inserted successfully", Toast.LENGTH_SHORT).show();
            btnshow.performClick();
        } else {
            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEmployeeRecords() {
        Datahelper helper = new Datahelper(DBExample.this);

        alist = helper.showRecordsEmployee();
        adapter = new ArrayAdapter<Employee>(DBExample.this, android.R.layout.simple_list_item_1, alist);
        lstEmployeelist.setAdapter(adapter);
    }

    private void addEmployeeRecords() {
        d = new Dialog(DBExample.this);
        d.setContentView(R.layout.add_records_dialogue);
        etId = (EditText) d.findViewById(R.id.etId);
        etName = (EditText) d.findViewById(R.id.etName);
        btnaddrecords = (Button) d.findViewById(R.id.btnaddrecords);
        btnaddrecords.setOnClickListener(DBExample.this);
        d.show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        selectedItem = (Employee) parent.getItemAtPosition(position);
        return false;
    }
}