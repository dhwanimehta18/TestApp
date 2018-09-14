package com.example.testapp.testapp;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class PhoneBook extends AppCompatActivity implements View.OnClickListener {
    Button btnShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);
        btnShow = (Button) findViewById(R.id.btnShow);
        btnShow.setOnClickListener(PhoneBook.this);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        Uri uri = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI;
        String[] columns = {ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Contactables.DATA1};
        Cursor res = getContentResolver().query(uri, columns, null, null, null);
        if (res != null) {
            while (res.moveToNext()) {
                String name = res.getString(res.getColumnIndex(ContactsContract.CommonDataKinds.
                        Contactables.DISPLAY_NAME));
                String number = res.getString(res.getColumnIndex(ContactsContract.CommonDataKinds.
                        Contactables.DATA1));
                Log.i("Name", name);
                Log.i("Number", number);
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(PhoneBook.this, "Please check permission", Toast.LENGTH_SHORT).show();
        }
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] callLogColumns = {CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
        Cursor resCallLog = getContentResolver().query(callLogUri, callLogColumns, null, null, null);
        if(resCallLog != null){
            while (resCallLog.moveToNext()){
                String name = resCallLog.getString(resCallLog.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String number = resCallLog.getString(resCallLog.getColumnIndex(CallLog.Calls.NUMBER));
                String duration = resCallLog.getString(resCallLog.getColumnIndex(CallLog.Calls.DURATION));
                Log.i("Name",name);
                Log.i("Number",number);
                Log.i("Duration",duration);
            }
        }
    }
}
