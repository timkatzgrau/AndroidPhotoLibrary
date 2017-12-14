package com.example.katzgrau.photosandroid57;

import android.app.AlertDialog.Builder;
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
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class searchresults extends AppCompatActivity {

    private TextView mTextMessage;
    private Context ctx = this;
    private String m_Text = "";
    private ListView listView;
    public static final int RESULT_LOAD_IMG = 1;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "openalbum";
    private Album album;
    ArrayList<Photo> photoResults;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton btnSelectImage;
    ImageView imgView;
    public static final String EXTRA_TAGS = "com.example.katzgrau.photosandroid57.EXTRA_TAGS";
    public static final String EXTRA_POSITION = "com.example.katzgrau.photosandroid57.POSITION";;


    ListView list;
    ArrayList<String> web = new ArrayList<String>();
    ArrayList<Uri> uris = new ArrayList<Uri>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_backfromsearch:
                    Intent intenty = new Intent(searchresults.this, MainActivity.class);
                    startActivity(intenty);
                    return true;
            }
            return false;
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    Instagram.getApp().addPhoto(selectedImageUri,album);
                    reload();
                }
            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    //Test

    public void reload() {
        list.setAdapter(null);
        web.clear();
        uris.clear();
        ArrayList<String> values = new ArrayList<String>();

        for(int i = 0; i < photoResults.size(); i++) {

            if(photoResults.size() > 0) {
                web.add(" ");
                uris.add(photoResults.get(i).getPhotoFileURI());
            }
        }
        learn2crack.customlistview.CustomList adapter = new
                learn2crack.customlistview.CustomList(searchresults.this, web, uris);
        list = (ListView)findViewById(R.id.listsearch);
        // Assign adapter to ListView
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        ArrayAdapter mAdapter;
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ArrayList<Tag> tags = (ArrayList<Tag>)intent.getSerializableExtra(search.EXTRA_TAGS);

        Tag[] searchTags = new Tag[tags.size()];
        for (int j = 0; j < tags.size(); j++) {
            searchTags[j] = tags.get(j);
        }
        photoResults = Instagram.getApp().searchByTags(searchTags);


        String title = intent.getStringExtra(MainActivity.EXTRA_TITLE);
        setTitle(title);
        setContentView(R.layout.searchresultsview);
        learn2crack.customlistview.CustomList adapter = new
                learn2crack.customlistview.CustomList(searchresults.this, web, uris);
        list=(ListView)findViewById(R.id.listsearch);
        list.setAdapter(adapter);

        reload();




        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationsearchresults);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


}