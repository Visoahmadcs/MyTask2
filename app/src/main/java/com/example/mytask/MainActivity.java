package com.example.mytask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<ContactModel> contactModelArrayList;

    // test
    Button btn_save;
    Button view_data;


    String Cname = "";
    String Cnumber = "";

    ContactDb db;
    private ContactModel contactModel;
    int PERMISSION_ALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // for getting permissions at run time

        String[] PERMISSIONS = {
                android.Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


        db = new ContactDb(this);

//        contactModel = new ContactModel(Cname,Cnumber);
        btn_save = findViewById(R.id.save);
        view_data = findViewById(R.id.view);

        btn_save.setOnClickListener(this);
        view_data.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listView);
        contactModelArrayList = new ArrayList<>();

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            int image = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));


            contactModel = new ContactModel(name, phoneNumber);
            contactModel.setName(name);
            contactModel.setNumber(phoneNumber);
            contactModel.setImge(image);
            
            contactModelArrayList.add(contactModel);


            Log.d("name>>", name + "  " + phoneNumber);
        }
        phones.close();
        customAdapter = new CustomAdapter(this, contactModelArrayList);
        listView.setAdapter(customAdapter);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.save:
                for (int i = 0; i < contactModelArrayList.size(); i++) {
                    Cname = contactModelArrayList.get(i).getName();
                    Cnumber = contactModelArrayList.get(i).getNumber();

//                  Cname=contactModel.getName();
//                  Cnumber= contactModel.getNumber();
                    db.insertData(Cname, Cnumber);
                }

                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
                break;
            case R.id.view:
                Intent intent = new Intent(getApplicationContext(), ViewContent.class);
                startActivity(intent);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
