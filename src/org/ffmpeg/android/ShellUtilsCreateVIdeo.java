package org.ffmpeg.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.ffmpeg.android.filters.ProcessMaker;

import android.content.Context;

public class ShellUtilsCreateVIdeo {
	private static ShellUtilsCreateVIdeo _instance;

	public static ShellUtilsCreateVIdeo getInstance(Context context) {
		if (_instance == null) {
			_instance = new ShellUtilsCreateVIdeo(context);
		}
		return _instance;
	}

	public final static String SHELL_CMD_CHMOD = "chmod";
	public final static String SHELL_CMD_KILL = "kill -9";
	public final static String SHELL_CMD_RM = "rm";
	public final static String SHELL_CMD_PS = "ps";
	public final static String SHELL_CMD_PIDOF = "pidof";

	public final static String CHMOD_EXE_VALUE = "755";

	public final String[] libraryAssets = { "ffmpeg", "libavcodec.so",
			"libavcodec.so.52", "libavcodec.so.52.99.1", "libavcore.so",
			"libavcore.so.0", "libavcore.so.0.16.0", "libavdevice.so",
			"libavdevice.so.52", "libavdevice.so.52.2.2", "libavfilter.so",
			"libavfilter.so.1", "libavfilter.so.1.69.0", "libavformat.so",
			"libavformat.so.52", "libavformat.so.52.88.0", "libavutil.so",
			"libavutil.so.50", "libavutil.so.50.34.0", "libswscale.so",
			"libswscale.so.0", "libswscale.so.0.12.0", "libsox.so", "sox" };

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
		processMaker.processSox();
	}

	public void cmdRun(String[] cmd) {
		Process ffmpegProcess = null;
		try {
			ffmpegProcess = new ProcessBuilder(cmd).redirectErrorStream(true)
					.start();
			OutputStream ffmpegOutStream = ffmpegProcess.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ffmpegProcess.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {

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
