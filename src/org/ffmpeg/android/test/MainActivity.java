package org.ffmpeg.android.test;

import java.util.ArrayList;
import java.util.List;

import org.ffmpeg.android.R;
import org.ffmpeg.android.test.MainActivity.PhotoItem;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		LoaderCallbacks<List<PhotoItem>> {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		item = new ArrayList<PhotoItem>();
		createData();
		initView();
	}
	Button select;
	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private GridView gridView;
	private ImageAdapter adapter;
	private ArrayList<PhotoItem> item;

	void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		adapter = new ImageAdapter(MainActivity.this, item);
		gridView.setAdapter(adapter);
		select = (Button)findViewById(R.id.btn_select_image);
		select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(item.isEmpty()&&item.size()<5){
					Toast.makeText(MainActivity.this, "please choose 5 image", Toast.LENGTH_SHORT).show();
				}else {
					
				}
			}
		});
	}

	void createData() {
		 getLoaderManager().initLoader(0, null, this);
		// LoadImagesFromSDCard loadImage = new LoadImagesFromSDCard(
		// MainActivity.this);
		// loadImage.deliverResult(item);
	}
	public void finishAct(){
		finish();
	}
	static int nSize;

	public static class LoadImagesFromSDCard extends AsyncTaskLoader<List<PhotoItem>> {
		public static List<PhotoItem> list;
		public Context mContext;

		public LoadImagesFromSDCard(Context context) {
			super(context);
			this.mContext = context;
		}

		@Override
		public List<PhotoItem> loadInBackground() {
			list = new ArrayList<MainActivity.PhotoItem>();
			final Context context = getContext();
			if (!isCancelled()) {
				Bitmap bitmap = null;

				String sBitmapName;

				Cursor cursor = context
						.getApplicationContext()
						.getContentResolver()
						.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								sColumnsArray, null, null, null);

				int columnIndex = cursor
						.getColumnIndex(MediaStore.Images.Media._ID);

				nSize = cursor.getCount();
				((MainActivity)this.mContext).finish();
				int imageID = 0;
				for (int i = 0; i < nSize; i++) {
					cursor.moveToPosition(i);

					imageID = cursor.getInt(columnIndex);

					bitmap = MediaStore.Images.Thumbnails.getThumbnail(context
							.getApplicationContext().getContentResolver(),
							imageID, MediaStore.Images.Thumbnails.MICRO_KIND,
							null);
					sBitmapName = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
					PhotoItem item = new PhotoItem(bitmap, sBitmapName);
					list.add(item);

				}
				cursor.close();
			}
			return list;
		}

		@Override
		public void deliverResult(List<PhotoItem> newPhotoListItems) {
			if (isReset()) {
				if (newPhotoListItems != null) {
					onReleaseResources(newPhotoListItems);
				}
			}
			List<PhotoItem> oldPhotos = list;
			list = newPhotoListItems;

			if (isStarted()) {
				super.deliverResult(newPhotoListItems);
			}

			if (oldPhotos != null) {
				onReleaseResources(oldPhotos);
			}
		}

		@Override
		protected void onStartLoading() {

			if (list != null) {
				deliverResult(list);
			} else {
				forceLoad();
			}
		}

		@Override
		protected void onStopLoading() {
			// Attempt to cancel the current load task if possible.
			cancelLoad();
		}

		@Override
		public void onCanceled(List<PhotoItem> photoListItems) {
			super.onCanceled(photoListItems);

			// At this point we can release the resources associated with 'apps'
			// if needed.
			onReleaseResources(photoListItems);
		}

		@Override
		protected void onReset() {
			super.onReset();
			onStopLoading();
			if (list != null) {
				onReleaseResources(list);
				list = null;
			}
		}

		protected void onReleaseResources(List<PhotoItem> photoListItems) {
		}

		private boolean isCancelled() {
			return false;
		}

	}

	public static class PhotoItem {
		private Bitmap _bitmap;
		private String _sName;

		PhotoItem(Bitmap bitmap, String sName) {
			_bitmap = bitmap;
			_sName = sName;
		}

		public Bitmap getBitmap() {
			return _bitmap;
		}

		public String getName() {
			return _sName;
		}
	}

	class ImageAdapter extends BaseAdapter {

		private ArrayList<PhotoItem> _photos;

		private LayoutInflater _inflater;

		public ImageAdapter(Context context, ArrayList<PhotoItem> arrayList) {
			_photos = arrayList;
			_inflater = (LayoutInflater) context.getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return _photos.size();
		}

		public Object getItem(int position) {
			return _photos.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			final ViewHolder holder;

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = _inflater.inflate(R.layout.gallery_image, null);
				holder.imageview = (ImageView) convertView
						.findViewById(R.id.thumbImage);
				holder.checkbox = (CheckBox) convertView
						.findViewById(R.id.itemCheckBox);
				holder.textView = (TextView) convertView
						.findViewById(R.id.text);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.checkbox.setId(position);
			holder.imageview.setId(position);
			holder.textView.setId(position);

			if (_bClear) {
				thumbnailsselection = new boolean[nSize];
				holder.checkbox.setVisibility(View.INVISIBLE);
			}

			holder.checkbox.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					int id = cb.getId();
					if (_bSelectionChecked)
						if (thumbnailsselection[id]) {
							cb.setChecked(false);
							cb.setVisibility(View.INVISIBLE);
							thumbnailsselection[id] = false;
						} else {
							cb.setChecked(true);
							cb.setVisibility(View.VISIBLE);
							thumbnailsselection[id] = true;
						}
				}
			});

			holder.imageview.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (_bSelectionChecked) {
						holder.checkbox.setVisibility(View.VISIBLE);
						holder.checkbox.setChecked(true);
						thumbnailsselection[v.getId()] = true;
					} else {
						
					}
				}
			});

			holder.imageview.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					_bSelectionChecked = true;
					holder.checkbox.setVisibility(View.VISIBLE);
					holder.checkbox.setChecked(true);
					return false;
				}
			});

			holder.imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
			holder.imageview.setImageBitmap(_photos.get(position).getBitmap());
			holder.textView.setText(_photos
					.get(position)
					.getName()
					.substring(0,
							_photos.get(position).getName().lastIndexOf(".")));
//			holder.checkbox.setChecked(thumbnailsselection[position]);
			holder.id = position;
			return convertView;
		}
	}

	static class ViewHolder {
		ImageView imageview;
		CheckBox checkbox;
		TextView textView;
		int id;
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		int columnIndex = 0;
		Cursor cursor = MainActivity.this
				.getApplicationContext()
				.getContentResolver()
				.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						sColumnsArray, null, null, null);

		if (cursor != null) {
			columnIndex = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToPosition(position);
			String imagePath = cursor.getString(columnIndex);
			Toast.makeText(MainActivity.this, "image " + imagePath,
					Toast.LENGTH_SHORT).show();
		}
	}

	private boolean _bClear = false;
	private boolean _bSelectionChecked = false;
	private boolean[] thumbnailsselection;
	Vibrator vibrator;
	final static String[] sColumnsArray = { MediaStore.Images.Media.DATA,
			MediaStore.Images.Media._ID, MediaStore.Images.Thumbnails._ID,
			MediaStore.Images.Media.DISPLAY_NAME };

	@Override
	public Loader<List<PhotoItem>> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return new LoadImagesFromSDCard(MainActivity.this);
	}

	@Override
	public void onLoadFinished(Loader<List<PhotoItem>> loader,
			List<PhotoItem> data) {
		for (int i = 0; i < data.size(); i++) {
			PhotoItem item1 = data.get(i);
			item.add(item1);
		}

		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<List<PhotoItem>> loader) {
		item.clear();
		adapter.notifyDataSetChanged();
	}
	
	public void doCreateVideo(){
//		new Thread(){
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				super.run();
//				ShellUtilsCreateVIdeo.getInstance(MainActivity.this).imageToMp4({"",""});
//			}
//		}.start();
	}
}
