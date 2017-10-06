package com.example.arif.contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditActivity extends AppCompatActivity {

    final int REQUEST_CODE_GALLARY = 999;

    int POS = 0;
    EditText UpName, UpMob, UpEmail;
    ImageView UpImg;
    Button UpButt, UpImgButt;

    UserDBHelper MyDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        MyDB = new UserDBHelper(getApplicationContext());

        /// ----  Retrieve The position of contact From Prev. Activity  ----- ////
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
         POS = -1;
        if(bundle != null)
        {
            POS = bundle.getInt("ContactPos");
        }



        //// ---- ---- Find View By Id ---- -----
        UpName = (EditText) findViewById(R.id.UPName);
        UpMob = (EditText) findViewById(R.id.UPMob);
        UpEmail = (EditText) findViewById(R.id.UPEmail);
        UpImg = (ImageView) findViewById(R.id.UPimg);
        UpImgButt = (Button) findViewById(R.id.UPImgBut);
        UpButt = (Button) findViewById(R.id.UpButt);

        /// ----- Initializing Value To The view -----

        UpName.setText(MyDB.fetch_Name(POS+1));
        UpEmail.setText(MyDB.fetch_Email(POS+1));
        UpMob.setText(MyDB.fetch_MOB(POS+1));
        UpImg.setImageBitmap(MyDB.fetch_Img(POS+1));

        //// ---- For the Edit Cursor be in the last ----////
        UpName.setSelection(UpName.length());
        UpEmail.setSelection(UpEmail.length());
        UpMob.setSelection(UpMob.length());


        /// IMG BUTTON AND UPDATE BUTTON NEEDED TO BE DONE

        UpImgButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLARY);
            }
        });

        UpButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name, Mob, Email;
                byte[] Img;
                Name =  UpName.getText().toString();
                Mob =  UpMob.getText().toString();
                Email =  UpEmail.getText().toString();
                Img =  ImageViewToByte(UpImg);


                if(Name.length() != 0 ) {
                    UpData(Name, Mob, Email, Img);
                    Intent I = new Intent(EditActivity.this, DetailsOfContacts.class);
                    I.putExtra("Position", POS);
                    startActivity(I);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Name Field Mandatory", Toast.LENGTH_SHORT).show();
                }

            }

            private byte[] ImageViewToByte(ImageView upImg) {
                Bitmap bitmap = ((BitmapDrawable)upImg.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray = stream.toByteArray();
                return byteArray;

            }
        });



    }

    private void UpData( String name, String mob, String email, byte[] img) {
        boolean UpdatedData = MyDB.UpDateContact((POS+1),name,mob,email,img);

        if(UpdatedData == true)
        {
            Toast.makeText(getApplicationContext(),"Data Updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Data Not Updated", Toast.LENGTH_SHORT).show();
        }


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if( requestCode == REQUEST_CODE_GALLARY)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent I = new Intent(Intent.ACTION_PICK);
                I.setType("image/*");
                startActivityForResult(I,REQUEST_CODE_GALLARY);
            }
            else{
                Toast.makeText(getApplicationContext(),"You Don't Have permission!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Uri uri = data.getData();
        try{
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            UpImg.setImageBitmap(bitmap);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
