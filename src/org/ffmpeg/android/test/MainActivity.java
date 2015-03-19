package org.ffmpeg.android.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ffmpeg.android.CmdParameters;
import org.ffmpeg.android.CmdUtils;
import org.ffmpeg.android.R;
import org.ffmpeg.android.ShellUtilsCreateVIdeo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button = (Button) findViewById(R.id.btn_select_image);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> arrayList = new ArrayList<String>();
				arrayList.add("/storage/emulated/0/Android/data/com.asus.gallery/FB_IMG_1425298915789.jpg");
				arrayList.add("/storage/emulated/0/Android/data/com.asus.gallery/FB_IMG_1426223967234.jpg");
				arrayList.add("/storage/emulated/0/Android/data/com.asus.gallery/FB_IMG_1425298915789.jpg");
				arrayList.add("/storage/emulated/0/Android/data/com.asus.gallery/FB_IMG_1426223967234.jpg");
				arrayList.add("/storage/emulated/0/Android/data/com.asus.gallery/FB_IMG_1425298915789.jpg");
				ProcessCreateVideo process = new ProcessCreateVideo(
						MainActivity.this,
						arrayList,
						"videoOutput", 2);
				process.execute();
			}
		});
	}

	public class ProcessCreateVideo extends AsyncTask<List<String>, Void, String> {
		private ArrayList<String> imagePath;
		private String videoOutput;
		private ShellUtilsCreateVIdeo shell;
		private int effect;

		public ProcessCreateVideo(Context context, ArrayList<String> pathImage,
				String videoOutput, int effect) {
			this.imagePath = pathImage;
			this.videoOutput = videoOutput;
			this.shell = new ShellUtilsCreateVIdeo(context);
			this.effect = effect;
		}

		@Override
		protected String doInBackground(List<String>... params) {
			for(int i=0;i<imagePath.size();i++){
				shell.cmdRun(CmdUtils.imagetoMp4(imagePath.get(i), videoOutput+i));
			}
			
			File file = new File(Environment.getExternalStorageDirectory()
					.getPath() + CmdParameters.ROOT_FILE + videoOutput + ".mp4");
			if (file.isFile())

				return Environment.getExternalStorageDirectory().getPath()
						+ CmdParameters.ROOT_FILE + videoOutput + ".mp4";
			else
				return null;
		}

		@Override
		protected void onPostExecute(String s) {
			if (s == null) {
				Log.d("dt", "failed");
			} else {
				Log.d("dt", "complete");
			}
			super.onPostExecute(s);
		}
	}

}
