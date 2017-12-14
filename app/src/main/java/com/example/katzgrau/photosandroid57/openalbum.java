package com.example.katzgrau.photosandroid57;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
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
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class openalbum extends AppCompatActivity {

    private TextView mTextMessage;
    private Context ctx = this;
    private String m_Text = "";
    private ListView listView;
    public static final int RESULT_LOAD_IMG = 1;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "openalbum";
    private Album album;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton btnSelectImage;
    ImageView imgView;
    public static final String EXTRA_TITLE = "com.example.katzgrau.photosandroid57.TITLE";
    public static final String EXTRA_POSITION = "com.example.katzgrau.photosandroid57.POSITION";;


    ListView list;
    ArrayList<String> web = new ArrayList<String>();
    ArrayList<Uri> uris = new ArrayList<Uri>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_rename:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle("Rename");

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
                                AlertDialog alertDialog = new AlertDialog.Builder(openalbum.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("album name already exists");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            } else {
                                album.setName(m_Text);
                                Intent intent = new Intent(openalbum.this, openalbum.class);
                                intent.putExtra(EXTRA_TITLE, m_Text);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
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
                case R.id.navigation_backfromopen:
                    Intent intenty = new Intent(openalbum.this, MainActivity.class);
                    startActivity(intenty);
                    return true;
                case R.id.navigation_delete:
                    Instagram.getApp().deleteAlbum(album);
                    Intent intentydelete = new Intent(openalbum.this, MainActivity.class);
                    startActivity(intentydelete);
                    return true;
                case R.id.navigation_addImage:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);


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

        for(int i = 0; i < album.getPhotos().size(); i++) {

            String tagString = "";

            for (int j = 0; j < album.getPhotos().get(i).getTags().size(); j++) {
                tagString += album.getPhotos().get(i).getTags().get(j).toString() + " ";
            }

            if(album.getPhotos().size() > 0) {
                web.add(tagString);
                uris.add(album.getPhotos().get(i).getPhotoFileURI());
            }
        }
        learn2crack.customlistview.CustomList adapter = new
                learn2crack.customlistview.CustomList(openalbum.this, web, uris);
        list=(ListView)findViewById(R.id.listopenalbum);
        // Assign adapter to ListView
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        String title = intent.getStringExtra(MainActivity.EXTRA_TITLE);
        setTitle(title);
        setContentView(R.layout.activity_openalbum);
        album = Instagram.getApp().getAlbumByTitle(title);
        listView = (ListView) findViewById(R.id.listopenalbum);
        learn2crack.customlistview.CustomList adapter = new
                learn2crack.customlistview.CustomList(openalbum.this, web, uris);
        list=(ListView)findViewById(R.id.listopenalbum);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = album.name;
                System.out.println("HIIIIIIIII" + position);
                int pos = position;
                Intent intent = new Intent(openalbum.this, openphoto.class);
                intent.putExtra(EXTRA_TITLE, itemValue );
                intent.putExtra(EXTRA_POSITION, position );
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
        reload();




        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationopenalbum);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


}
