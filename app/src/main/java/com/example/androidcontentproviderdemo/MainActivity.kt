package com.example.androidcontentproviderdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btn_get_contacts)

        button.setOnClickListener {
            getPhoneContacts()
        }

    }

    @SuppressLint("Range")
    private fun getPhoneContacts() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                0
            )
        }

        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        val cursor = contentResolver.query(
            uri, null, null, null, null)

        if (cursor == null) {
            Log.i("CONTACT_PROVIDER_DEMO", "Cursor is null")
            return
        }

        Log.i("CONTACT_PROVIDER_DEMO", "TOTAL NUMBERS OF CONTACTS: ${cursor.count}")

        if (cursor.count != 0) {
            while (cursor.moveToNext()) {

                val contactName = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                )

                val contactNumber = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                )

                Log.i("CONTACT_PROVIDER_DEMO", "Contact found: $contactName - $contactNumber")

            }
        }

    }

}