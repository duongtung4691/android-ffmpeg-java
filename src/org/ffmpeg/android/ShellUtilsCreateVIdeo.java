package org.ffmpeg.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import org.ffmpeg.android.filters.ProcessMaker;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class ShellUtilsCreateVIdeo {
	private static ShellUtilsCreateVIdeo _instance;

	public static ShellUtilsCreateVIdeo getInstance(Context context) {
		if (_instance == null) {
			_instance = new ShellUtilsCreateVIdeo(context);
		}
		return _instance;
	}

	public final String[] libraryAssets = { "ffmpeg", "libavcodec.so",
			"libavcodec.so.56", "libavcodec.so.56.1.100", "libpostproc.so.53",
			"libpostproc.so.53.0.100", "libavdevice.so", "libswresample.so.1",
			"libswresample.so.1.1.100", "libavdevice.so.56",
			"libavdevice.so.56.0.100", "libavfilter.so", "libavfilter.so.5",
			"libavfilter.so.5.1.100", "libavformat.so", "libavformat.so.56",
			"libavformat.so.56.4.101", "libavutil.so", "libavutil.so.54",
			"libavutil.so.54.7.100", "libswscale.so", "libswscale.so.3",
			"libswscale.so.3.0.100" };

	public ShellUtilsCreateVIdeo(Context context) {
		for (int i = 0; i < libraryAssets.length; i++) {
			try {
				InputStream ffmpegInputStream = context.getAssets().open(
						libraryAssets[i]);
				FileMover fm = new FileMover(ffmpegInputStream,
						"/data/data/org.ffmpeg.android/" + libraryAssets[i]);
				fm.moveIt();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ProcessMaker processMaker = new ProcessMaker();
		processMaker.processFFmpeg();
		File savePath = new File(Environment.getExternalStorageDirectory()
				.getPath() + CmdParameters.ROOT_FILE);
		savePath.mkdirs();
	}

	public void cmdRun(List<String> cmd) {
		Process ffmpegProcess = null;
		try {
			ffmpegProcess = new ProcessBuilder(cmd).redirectErrorStream(true)
					.start();
			OutputStream ffmpegOutStream = ffmpegProcess.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ffmpegProcess.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				Log.v("LOG", "***" + line + "***");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (ffmpegProcess != null) {
			ffmpegProcess.destroy();
		}
	}

}
