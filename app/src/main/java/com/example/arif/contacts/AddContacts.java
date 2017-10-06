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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;



public class AddContacts extends AppCompatActivity {

    final int REQUEST_CODE_GALLARY = 999;

    UserDBHelper MyDBHelper;
    Button Add_Con_Butt, Add_Img_Butt;
    EditText Edit_Name, Edit_Mob, Edit_Email;
    ImageView Img_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        MyDBHelper = new UserDBHelper(this);

        //---- ---- Button ---- ----
        Add_Con_Butt = (Button) findViewById(R.id.AddButton);
        Add_Img_Butt = (Button) findViewById(R.id.AddImgBut);

        // ---- ----- Edit Text ---- ----

        Edit_Name = (EditText) findViewById(R.id.EditName);
        Edit_Email = (EditText) findViewById(R.id.EditEmail);
        Edit_Mob = (EditText) findViewById(R.id.EditMob);
        Img_View = (ImageView) findViewById(R.id.imageView);

        Add_Img_Butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(AddContacts.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLARY);
            }
        });

        Add_Con_Butt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e("DataBase", "Click Before Fun");
                byte[] New_img = ImageViewToByte(Img_View);
                String New_name = Edit_Name.getText().toString();
                String New_mob = Edit_Mob.getText().toString();
                String New_email = Edit_Email.getText().toString();

                if(New_name.length() != 0 || New_mob.length() != 0)
                {
                    AddToTable(New_name,New_mob,New_email,New_img);

                    Intent I = new Intent(AddContacts.this, MainActivity.class);
                    startActivity(I);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Field Mandatory", Toast.LENGTH_SHORT).show();
                }



                Toast.makeText(getApplicationContext(), "DATA Inserted ", Toast.LENGTH_SHORT).show();
            }

            private byte[] ImageViewToByte(ImageView image) {
                Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray = stream.toByteArray();
                return byteArray;

            }
        });


    }


    @Override
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
            Img_View.setImageBitmap(bitmap);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AddToTable(String name, String mob, String email, byte[] img_of_con)
    {
        Log.e("DataBase", "Add To Table");
        boolean insertData = MyDBHelper.AddInfo(name,mob,email,img_of_con);

        if(insertData == true)
        {
            Toast.makeText(getApplicationContext(), "DataInserted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Data NOT Inserted", Toast.LENGTH_SHORT).show();
        }
    }


}
