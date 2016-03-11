package week5.hk5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import week5.hk5.R;

public class ImageFragment extends Fragment {
	String dogName;

	static ImageFragment newInstance(String dogName) {
		ImageFragment imageFragment = new ImageFragment();

		// Supply dogName input as an argument.
		Bundle args = new Bundle();
		args.putString("dog_name", dogName);
		imageFragment.setArguments(args);

		return imageFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dogName = getArguments() != null ? getArguments().getString("dog_name") : "";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_image, container, false);

		((ImageView)v.findViewById(R.id.imageView)).setImageResource(DogInfo.getResourceId(dogName));
		return v;
	}

}
