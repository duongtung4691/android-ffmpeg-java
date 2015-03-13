package org.ffmpeg.android;

import java.util.ArrayList;

import android.os.Environment;

public class CmdUtils {
	private final static String FRAME_RATE_VAL = "30";

	public static ArrayList<String> imagetoMp4(String imageURI,
			String videoOutput) {
		ArrayList<String> cmd = new ArrayList<>();
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-y");
		cmd.add("-loop");
		cmd.add("-i");
		cmd.add(imageURI);
		cmd.add("-acodec");
		cmd.add("aac");
		cmd.add("-vcodec");
		cmd.add("mpeg4");
		cmd.add("-s");
		cmd.add("1280x720");
		cmd.add("-strict");
		cmd.add("experimental");
		cmd.add("-b:a");
		cmd.add("32k");
		cmd.add("-shortest");
		cmd.add("-f");
		cmd.add("mp4");
		cmd.add("-r");
		cmd.add(FRAME_RATE_VAL);
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ "/com.mobvcasting.mjpegffmpeg/" + videoOutput + ".mp4");
		return cmd;
	}

	public static ArrayList<String> addAudio(String audioPath,
			String videoPath, String videoOutput) {
		ArrayList<String> cmd = new ArrayList<>();
		// ffmpeg -i input.mp4 -i input.mp3 -c copy -map 0:0 -map 1:0 output.mp4
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-y");
		cmd.add("-loop");
		cmd.add("-i");
		cmd.add(videoPath);
		cmd.add("-i");
		cmd.add(audioPath);
		cmd.add("-c");
		cmd.add("copy");
		cmd.add("-map");
		cmd.add("0:0");
		cmd.add("-map");
		cmd.add("1:0");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ "/com.mobvcasting.mjpegffmpeg/" + videoOutput + ".mp4");
		return cmd;
	}

	public static ArrayList<String> addFilter(String audioPath,
			String videoPath, String videoOutput) {
		ArrayList<String> cmd = new ArrayList<>();
		// ffmpeg -i input.mp4 -i input.mp3 -c copy -map 0:0 -map 1:0 output.mp4
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-y");
		cmd.add("-loop");
		cmd.add("-i");
		cmd.add(videoPath);
		cmd.add("-i");
		cmd.add(audioPath);
		cmd.add("-c");
		cmd.add("copy");
		cmd.add("-map");
		cmd.add("0:0");
		cmd.add("-map");
		cmd.add("1:0");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ "/com.mobvcasting.mjpegffmpeg/" + videoOutput + ".mp4");
		return cmd;
	}
}
