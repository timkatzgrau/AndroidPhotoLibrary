package com.example.katzgrau.photosandroid57;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {

    private TextView mTextMessage;
    private Context ctx = this;
    private String m_Text = "";
    public static final int RESULT_LOAD_IMG = 1;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "openphoto";
    private Album album;
    private int pos;
    ListView listView;
    ArrayList<String> values;
    ArrayList<Tag> tags;
    String tagStr;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton btnSelectImage;
    ImageView imgView;
    public static final String EXTRA_TAGS = "com.example.katzgrau.photosandroid57.TAGS";
    public static final String EXTRA_POSITION = "com.example.katzgrau.photosandroid57.POSITION";;
    TextView t;
    ListView list;
    ArrayList<String> web = new ArrayList<String>();
    ArrayList<Uri> uris = new ArrayList<Uri>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_backfromsearch:
                    Intent intentyy = new Intent(search.this, MainActivity.class);
                    intentyy.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intentyy);
                    return true;
            }
            return false;
        }
    };
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
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);

        listView = (ListView) findViewById(R.id.list);



        values = new ArrayList<String>();
        tags = new ArrayList<Tag>();
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.value);
                String value = edit.getText().toString();

                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.lactate_radio);
                if(radioGroup != null) {
                    int radioButtonID = radioGroup.getCheckedRadioButtonId();
                    if (radioButtonID >= 0) {
                        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
                        String key = (String) radioButton.getText();

                        Tag tag = new Tag(key, value);

                        tags.add(tag);

                        values.add(tag.toString());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,
                                android.R.layout.simple_list_item_1, android.R.id.text1, values);
                        // Assign adapter to ListView
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }

            }
        });
        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(search.this, searchresults.class);
                intent.putExtra(EXTRA_TAGS, tags);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);


            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationsearch);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


}