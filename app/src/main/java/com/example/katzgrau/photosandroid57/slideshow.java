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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class slideshow extends AppCompatActivity {

    private TextView mTextMessage;
    private Context ctx = this;
    private String m_Text = "";
    private ListView listView;
    public static final int RESULT_LOAD_IMG = 1;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "slideshow";
    private Album album;
    private int pos;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton btnSelectImage;
    ImageView imgView;

    public static final String EXTRA_TITLE = "com.example.katzgrau.photosandroid57.TITLE";

    public static int EXTRA_POSITION;

    ListView list;
    ArrayList<String> web = new ArrayList<String>();
    ArrayList<Uri> uris = new ArrayList<Uri>();
    int currentIndex = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_previous:
                    if(currentIndex == 0){
                        currentIndex = album.getPhotos().size()-1;
                        imgView.setImageURI(album.getPhotos().get(currentIndex).getPhotoFileURI());
                    }else {
                        currentIndex--;
                        imgView.setImageURI(album.getPhotos().get(currentIndex).getPhotoFileURI());
                    }
                    return true;
                case R.id.navigation_next:
                    if(currentIndex == album.getPhotos().size()-1){
                        currentIndex = 0;
                        imgView.setImageURI(album.getPhotos().get(currentIndex).getPhotoFileURI());
                    }else {
                        currentIndex++;
                        imgView.setImageURI(album.getPhotos().get(currentIndex).getPhotoFileURI());
                    }
                    return true;
                case R.id.navigation_backslideshow:
                    Intent intenty = new Intent(slideshow.this, openalbum.class);
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
        currentIndex = pos;
        String title = intent.getStringExtra(openalbum.EXTRA_TITLE);

        setTitle(title);
        setContentView(R.layout.slideshow);
        imgView = findViewById(R.id.imageViewSlideshow);
        album = Instagram.getApp().getAlbumByTitle(title);
        imgView.setImageURI(album.getPhotos().get(position).getPhotoFileURI());



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationslideshow);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


}
