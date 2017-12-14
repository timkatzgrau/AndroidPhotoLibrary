package com.example.katzgrau.photosandroid57;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Context ctx = this;
    private String m_Text = "";
    private ListView listView;
    public static final String EXTRA_TITLE = "com.example.katzgrau.photosandroid57.TITLE";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_search:
                    Intent intent = new Intent(MainActivity.this, search.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    return true;
                case R.id.navigation_dashboard:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle("Title");

                    // Set up the input
                    final EditText input = new EditText(ctx);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();

                            Album found = null;
                            String mTextLower = input.getText().toString().toLowerCase();

                            for(int i = 0; i < Instagram.getApp().getAllAlbums().size(); i++){
                                if(Instagram.getApp().getAllAlbums().get(i).name.toLowerCase().equals(mTextLower)){
                                    found = Instagram.getApp().getAllAlbums().get(i);
                                }
                            }

                            if (found != null) {
                                reload();
                            } else {
                                Instagram.getApp().createAlbum(m_Text);
                                reload();
                            }
                        }

                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                    return true;
            }
            return false;
        }
    };
    //Test

    public void reload() {
        listView.setAdapter(null);
        ArrayList<String> values = new ArrayList<String>();
        for(int i = 0; i < Instagram.getApp().getAllAlbums().size(); i++) {
            values.add(Instagram.getApp().getAllAlbums().get(i).name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        reload();

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, openalbum.class);
                intent.putExtra(EXTRA_TITLE, itemValue );
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);


            }
        });



        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


}
