package com.example.katzgrau.photosandroid57;

import android.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class openphoto extends AppCompatActivity {

    private TextView mTextMessage;
    private Context ctx = this;
    private String m_Text = "";
    private ListView listView;
    public static final int RESULT_LOAD_IMG = 1;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "openphoto";
    private Album album;
    private int pos;
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
                case R.id.navigation_slideshow:
                    String itemValue = album.name;
                    Intent intent = new Intent(openphoto.this, slideshow.class);
                    intent.putExtra(EXTRA_TITLE, itemValue );
                    intent.putExtra(EXTRA_POSITION, pos );
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    return true;
                case R.id.navigation_backfromphoto:
                    Intent intentyy = new Intent(openphoto.this, openalbum.class);
                    intentyy.putExtra(EXTRA_TITLE, album.name );
                    intentyy.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intentyy);
                    return true;
                case R.id.navigation_movePhoto:
                    Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle("Single Choice");
                    ArrayList<String> albumNames = new ArrayList<String>();
                    for(int i = 0; i < Instagram.getApp().getAllAlbums().size(); i++){
                        albumNames.add(Instagram.getApp().getAllAlbums().get(i).name);
                    }
                    String[] names = new String[albumNames.size()];
                    albumNames.toArray(names);
                    builder.setItems(names, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Album selectedAlbum = Instagram.getApp().getAlbumByTitle(names[which]);
                            selectedAlbum.addPhoto(album.getPhotos().get(pos));
                            album.getPhotos().remove(pos);
                            Toast.makeText(ctx,"Photo has been moved to" + names[which],Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Intent intenty = new Intent(openphoto.this, openalbum.class);
                            intenty.putExtra(EXTRA_TITLE, album.name );
                            intenty.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intenty);
                        }
                    });
                    builder.setNegativeButton("cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                case R.id.navigation_deletePhoto:
                    album.getPhotos().remove(pos);
                    Intent intenty = new Intent(openphoto.this, openalbum.class);
                    intenty.putExtra(EXTRA_TITLE, album.name );
                    intenty.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int position = intent.getIntExtra(openalbum.EXTRA_POSITION, 0);
        pos = position;
        String title = intent.getStringExtra(openalbum.EXTRA_TITLE);

        setTitle(title);
        setContentView(R.layout.photoview);
        ImageView imgView = findViewById(R.id.imageViewOpenPhoto);
        album = Instagram.getApp().getAlbumByTitle(title);
        imgView.setImageURI(album.getPhotos().get(position).getPhotoFileURI());



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationopenphoto);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


}
