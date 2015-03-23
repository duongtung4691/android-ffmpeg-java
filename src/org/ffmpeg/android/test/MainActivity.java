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
				arrayList.add("/storage/emulated/0/Android/data"
						+ CmdParameters.ROOT_FILE + "videoOutput" + 0 + ".mp4");
				arrayList.add("/storage/emulated/0/Android/data"
						+ CmdParameters.ROOT_FILE + "videoOutput" + 1 + ".mp4");
				arrayList.add("/storage/emulated/0/Android/data"
						+ CmdParameters.ROOT_FILE + "videoOutput" + 2 + ".mp4");
				arrayList.add("/storage/emulated/0/Android/data"
						+ CmdParameters.ROOT_FILE + "videoOutput" + 3 + ".mp4");
				arrayList.add("/storage/emulated/0/Android/data"
						+ CmdParameters.ROOT_FILE + "videoOutput" + 4 + ".mp4");
				arrayList.add("/storage/emulated/0/Android/data"
						+ CmdParameters.ROOT_FILE + "videoOutput" + 5 + ".mp4");
				ProcessCreateVideo process = new ProcessCreateVideo(
						MainActivity.this, arrayList, "videoOutput", 2);
				process.execute();
			}
		});
	}

	public class ProcessCreateVideo extends
			AsyncTask<List<String>, Void, String> {
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
			for (int i = 0; i < 6; i++) {
				// shell.cmdRun(CmdUtils.imagetoMp4(MainActivity.this,
				// "/storage/emulated/0/Android/data/com.asus.gallery/image00"+i+".jpg",
				// videoOutput+i));
				shell.cmdRun(CmdUtils.addJoin(MainActivity.this,
						imagePath, videoOutput));

			}
			File file = new File(MainActivity.this.getExternalCacheDir()
					+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
			if (file.isFile())

				return MainActivity.this.getExternalCacheDir()
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
