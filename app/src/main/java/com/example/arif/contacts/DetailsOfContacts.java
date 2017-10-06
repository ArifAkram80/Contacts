package com.example.arif.contacts;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsOfContacts extends AppCompatActivity {

    int Pos =0;
    TextView Name_View, Mob_View, Email_View;
    ImageView Image_View;
    Button DeleteButt;
    UserDBHelper userDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_contacts);

        //---- ---- Intend Bundle ---- ----
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Pos = 0;
        if(bundle != null)
        {
            Pos = bundle.getInt("Position");
        }



        // ---- ---- Find View ---- ----
        Name_View = (TextView) findViewById(R.id.DetName);
        Mob_View = (TextView) findViewById(R.id.DetMob);
        Email_View = (TextView) findViewById(R.id.DetEmail);
        Image_View = (ImageView) findViewById(R.id.DetImg);
        DeleteButt = (Button) findViewById(R.id.DeleteButt);

       /// --- --- DataBase -----
        final UserDBHelper userDBHelper = new UserDBHelper(getApplicationContext());

        Name_View.setText(userDBHelper.fetch_Name(Pos+1));
        Toast.makeText(getApplicationContext(), "Position "+ Pos +" Name "+ userDBHelper.fetch_Name(Pos+1),Toast.LENGTH_SHORT).show();
        Mob_View.setText(userDBHelper.fetch_MOB(Pos+1));
        Email_View.setText(userDBHelper.fetch_Email(Pos+1));
        Image_View.setImageBitmap(userDBHelper.fetch_Img(Pos+1));


        DeleteButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              boolean Del = userDBHelper.Delete(userDBHelper.fetch_Name(Pos+1));
              if(Del == true)
              {
                  Toast.makeText(getApplicationContext(), "Contact Deleted Successfully", Toast.LENGTH_SHORT).show();
                  Intent I = new Intent(DetailsOfContacts.this, MainActivity.class);
                  startActivity(I);
              }
              else {
                  Toast.makeText(getApplicationContext(),"Data Not Deleted", Toast.LENGTH_SHORT);

              }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id ;
        id = item.getItemId();
        if(id == R.id.EditButt)
        {
            Toast.makeText(getApplicationContext(),"Edit Button Clicked", Toast.LENGTH_SHORT).show();
            Intent I = new Intent(DetailsOfContacts.this, EditActivity.class);
            I.putExtra("ContactPos", Pos);
            startActivity(I);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
