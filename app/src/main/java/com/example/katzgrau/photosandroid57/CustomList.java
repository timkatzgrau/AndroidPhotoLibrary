package learn2crack.customlistview;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katzgrau.photosandroid57.R;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{

	private final Activity context;
	private final ArrayList<String> web;
	private final ArrayList<Uri> imageId;
	public CustomList(Activity context,
					  ArrayList<String> web, ArrayList<Uri> imageId) {
		super(context, R.layout.list_single, web);
		this.context = context;
		this.web = web;
		this.imageId = imageId;

	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_single, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

		if(imageId.size() > 0) {
			imageView.setImageURI(imageId.get(position));
			txtTitle.setText(web.get(position));
		}
		return rowView;
	}
}