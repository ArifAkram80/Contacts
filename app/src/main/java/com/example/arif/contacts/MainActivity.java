package com.example.arif.contacts;


import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{


    UserDBHelper mDatabaseHelper;
    public ListView listView;
    ArrayList< myDataRetriver >  listData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        mDatabaseHelper = new UserDBHelper(this);
        populateView();
        Custom_List_Adapter adapter = new Custom_List_Adapter(this, R.layout.custom_list_view, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(), "You've Clicked on "+i,Toast.LENGTH_SHORT);
                Intent I = new Intent(MainActivity.this, DetailsOfContacts.class);
                I.putExtra("Position", i);
                startActivity(I);

            }
        });
    }

    private void populateView()
    {
        Cursor data = mDatabaseHelper.getInformation();
        while(data.moveToNext())
        {
            String name = data.getString(1);
            byte[] image = data.getBlob(4);
            listData.add(new  myDataRetriver (name,image));

        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.CreateButt)
        {

            Toast.makeText(getApplicationContext(), "Create New Contact",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), AddContacts.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
