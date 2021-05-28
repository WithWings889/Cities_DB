package com.example.lab_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class ContactsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button findBtn;
    ArrayList<ContactModel> arrayList = new ArrayList<ContactModel>();
    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setTitle("Contacts");

        recyclerView = findViewById(R.id.recycler_view);
        findBtn = findViewById(R.id.button12);
        checkPermission();

        findBtn.setOnClickListener(v -> getContacts(1));

        findViewById(R.id.button11).setOnClickListener( v -> getContacts(0));
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(ContactsActivity.this,
               Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(ContactsActivity.this,
                   new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
           getContacts(0);
        }
    }

    private void getContacts(int isFilter){
        arrayList.clear();
        arrayList = getNameEmailDetails(isFilter);
//        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
//        Cursor phone_cursor = getContentResolver().query(
//                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                null,
//                null,
//                null, sort
//        );
//
//        if(phone_cursor.getCount() > 0) {
//            while (phone_cursor.moveToNext()) {
//                try {
//                    int id = Integer.parseInt(phone_cursor.getString(
//                            phone_cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
//                    ));
//
//                    /*Cursor name_cursor = getContentResolver().query(
//                            ContactsContract.Data.CONTENT_URI, null,
//                            ContactsContract.Data.CONTACT_ID + "  = " + id,
//                            null, null
//                    );*/
//
//                    Cursor name_cursor = getContentResolver().query(
//                            ContactsContract.Data.CONTENT_URI, null,
//                            ContactsContract.Data.CONTACT_ID + " = " + id,
//                            null, null
//                    );
//
//                    String name = phone_cursor.getString(phone_cursor.getColumnIndex(
//                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
//                    ));
//
//                    String first_name = "";
//                    String last_name = "";
//
//                    while (name_cursor.moveToNext()) {
//                        if (name_cursor.getString(name_cursor.getColumnIndex(
//                                ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)) != null) {
//                            first_name = name_cursor.getString(name_cursor.getColumnIndex(
//                                    ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME
//                            ));
//                            last_name = name_cursor.getString(name_cursor.getColumnIndex(
//                                    ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME
//                            ));
//                        }
//                    }
//                    name_cursor.close();
//
//                    String phoneNumber = phone_cursor.getString(phone_cursor.getColumnIndex(
//                            ContactsContract.CommonDataKinds.Phone.NUMBER
//                    ));
//
//                    ContactModel model = new ContactModel();
//                    model.setName(name);
//                    model.setPhone(phoneNumber);
//
//                    if (isFilter == 1 && !phoneNumber.endsWith("7"))
//                        continue;
//
//                    arrayList.add(model);
//                } catch (Exception e) {
//                    Toast.makeText(ContactsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//            phone_cursor.close();
//        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<ContactModel> getNameEmailDetails(int isFilter) {
        String filter;
        ArrayList<ContactModel> emlRecs = new ArrayList<ContactModel>();
        HashSet<String> emlRecsHS = new HashSet<String>();
        Context context = this;
        ContentResolver cr = context.getContentResolver();
        String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        if(isFilter == 1) {
            filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE '%@ukr.net%'";
        }
        else filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur.moveToFirst()) {
            do {
                // names comes in hand sometimes
                String name = cur.getString(1);
                String phone = cur.getString(2);
                String emlAddr = cur.getString(3);


                ContactModel model = new ContactModel();
                model.setName(name);
                model.setPhone(phone);
                model.setEmailAddress(emlAddr);
                emlRecs.add(model);

            } while (cur.moveToNext());
        }

        cur.close();

        return emlRecs;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getContacts(0);
        } else {
            Toast.makeText(ContactsActivity.this, "Permissions Denied", Toast.LENGTH_SHORT).show();
            checkPermission();
        }
    }
}
