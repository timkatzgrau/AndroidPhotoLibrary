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
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
    String tagStr;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton btnSelectImage;
    ImageView imgView;
    public static final String EXTRA_TITLE = "com.example.katzgrau.photosandroid57.TITLE";
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
                case R.id.navigation_addTag:
                    // custom dialog
                    final Dialog dialog = new Dialog(ctx);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.radiobutton);
                    List<String> stringList=new ArrayList<>();
                    RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.lactate_radiodisplay);
                    for(int i = 0; i < album.getPhotos().get(pos).getTags().size(); i++){
                        stringList.add(album.getPhotos().get(pos).getTags().get(i).toString());
                        RadioButton rb = new RadioButton(ctx); // dynamically creating RadioButton and adding to RadioGroup.
                        rb.setText(album.getPhotos().get(pos).getTags().get(i).toString());
                        rg.addView(rb);
                    }



                    Button btn = (Button) (Button)dialog.findViewById(R.id.button);

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.lactate_radio);
                            int radioButtonID = radioGroup.getCheckedRadioButtonId();
                            RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
                            String key = (String) radioButton.getText();

                            EditText edit = (EditText)dialog.findViewById(R.id.newTagText);
                            String value = edit.getText().toString();

                            if(!value.equals("")) {
                                Tag tag = new Tag(key,value);
                                album.getPhotos().get(pos).addTag(tag);
                                stringList.add(tag.toString());


                                RadioButton rb = new RadioButton(ctx); // dynamically creating RadioButton and adding to RadioGroup.
                                rb.setText(tag.toString());
                                rg.addView(rb);
                                View view = dialog.getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                                tagStr = "" ;
                                for(int i = 0; i < album.getPhotos().get(pos).getTags().size(); i++){
                                    tagStr = tagStr + " , " + album.getPhotos().get(pos).getTags().get(i);
                                }
                                t.setText(tagStr);
                            }
                        }
                    });

                    Button cancelbtn = (Button) (Button)dialog.findViewById(R.id.backbutton);

                    cancelbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    Button deletebutton = (Button) (Button)dialog.findViewById(R.id.deletebutton);

                    deletebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.lactate_radiodisplay);
                            if(radioGroup != null) {
                                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                                RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);

                                if(radioButton != null) {
                                    String str = (String) radioButton.getText();

                                    album.getPhotos().get(pos).getTags().remove(album.getPhotos().get(pos).getTagByName(str));
                                    stringList.clear();
                                    radioGroup.removeView(radioButton);
                                    tagStr = "" ;
                                    for(int i = 0; i < album.getPhotos().get(pos).getTags().size(); i++){
                                        tagStr = tagStr + " , " + album.getPhotos().get(pos).getTags().get(i);
                                    }
                                    t.setText(tagStr);
                                }
                            }
                        }
                    });





                    dialog.show();
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

        t = (TextView) findViewById(R.id.textViewOpenPhoto);
         tagStr = "" ;
        for(int i = 0; i < album.getPhotos().get(pos).getTags().size(); i++){
            tagStr = tagStr + " , " + album.getPhotos().get(pos).getTags().get(i);
        }
        t.setText(tagStr);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationopenphoto);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


}
