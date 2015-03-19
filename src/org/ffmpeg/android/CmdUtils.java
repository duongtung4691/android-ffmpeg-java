package org.ffmpeg.android;

import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;

public class CmdUtils {
	private final static String FRAME_RATE_VAL = "10";

	/**
	 * 
	 * @param imageURI
	 *            the path of image input
	 * @param videoOutput
	 *            path and file name video output
	 * @return
	 */
	public static ArrayList<String> imagetoMp4(String imageURI,
			String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-loop");
		cmd.add("1");
		cmd.add("-i");
		cmd.add(imageURI);
		Log.d("LOG", imageURI);
		cmd.add("-vcodec");
		cmd.add("libx264");
		cmd.add("-s");
		cmd.add("1280x720");
		cmd.add("-strict");
		cmd.add("experimental");
		cmd.add("-r");
		cmd.add(FRAME_RATE_VAL);
		cmd.add("-t");
		cmd.add("7");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	/**
	 * ffmpeg -i input.mp4 -i input.mp3 -c copy -map 0:0 -map 1:0 output.mp4
	 * 
	 * @param audioPath
	 *            path audio file
	 * @param videoPath
	 *            path video file
	 * @param videoOutput
	 *            name video output
	 * @return
	 */
	public static ArrayList<String> addAudio(String audioPath,
			String videoPath, String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
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
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	/**
	 * 
	 * ffmpeg \ -loop 1 -i input0.png \ -loop 1 -i input1.png \ -loop 1 -i
	 * input2.png \ -loop 1 -i input3.png \ -loop 1 -i input4.png \
	 * -filter_complex \ "[0:v]trim=duration=15,fade=t=out:st=14.5:d=0.5[v0]; \
	 * [1:v]trim=duration=15,fade=t=in:st=0:d=0.5,fade=t=out:st=14.5:d=0.5[v1];
	 * \ [2:v]trim=duration=15,fade=t=in:st=0:d=0.5,fade=t=out:st=14.5:d=0.5[v2]
	 * ; \
	 * [3:v]trim=duration=15,fade=t=in:st=0:d=0.5,fade=t=out:st=14.5:d=0.5[v3];
	 * \ [4:v]trim=duration=15,fade=t=in:st=0:d=0.5,fade=t=out:st=14.5:d=0.5[v4]
	 * ; \ [v0][v1][v2][v3][v4]concat=n=5:v=1:a=0,format=yuv420p[v]" -map "[v]"
	 * out.mp4 ffmpeg -i input.mp4 -i input.mp3 -c copy -map 0:0 -map 1:0
	 * output.mp4
	 * 
	 * @param imagePath
	 *            path image
	 * @param videoOutput
	 *            name video output
	 * @return
	 */
	public static ArrayList<String> addVideos(ArrayList<String> imagePath,
			String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		//
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-loop");
		cmd.add("1");
		for (int i = 0; i < imagePath.size(); i++) {
			cmd.add("-i");
			cmd.add(imagePath.get(i));
		}
//		cmd.add("-filter:v");
//		for (int i = 0; i < imagePath.size(); i++) {
//			if (i == 0)
//				cmd.add("\"[" + i
//						+ ":v]trim=duration=7,fade=t=out:st=6.5:d=0.5[v" + i
//						+ "];");
//			else
//				cmd.add("["
//						+ i
//						+ ":v]trim=duration=7,fade=t=in:st=0.5:d=0.5,fade=t=out:st=6.5:d=0.5[v"
//						+ i + "];");
//		}
//		cmd.add("[v0][v1][v2][v3][v4]concat=n=5:v=1:a=0,format=yuv420p[v]\"");
		cmd.add("-vcodec");
		cmd.add("libx264");
		cmd.add("-s");
		cmd.add("1280x720");
		cmd.add("-strict");
		cmd.add("experimental");
		cmd.add("-r");
		cmd.add(FRAME_RATE_VAL);
		cmd.add("-t");
		cmd.add("7");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	/**
	 * 
	 * ffmpeg -f concat -i inputs.txt -c copy output.mp4　 ## or something
	 * like: ffmpeg -f concat -i inputs.txt -c:v libx264 -preset veryfast -crf
	 * 22 -c:a copy output.mp4
	 * 
	 * @param txtPath
	 *            txt file path of videos
	 * @param videoOutput
	 *            file output name
	 * @return
	 */

	public static ArrayList<String> addJoin(String txtPath, String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-f");
		cmd.add("concat");
		cmd.add("-i");
		cmd.add(txtPath);
		cmd.add("-c");
		cmd.add("libx264");
		cmd.add("-crf");
		cmd.add("22");
		cmd.add("-acodec");
		cmd.add("copy");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	/**
	 * // ffmpeg -loop 1 -i image_1.jpg -vf
	 * "zoompan=z='if(lte(zoom,1.0),1.5,max(1.001,zoom-0.0015))':d=125" -c:v
	 * libx264 -t 5 -s "800x450" zoomout.mp4
	 * 
	 * @param imagePath
	 * @param videoOutput
	 * @return
	 */

	public static ArrayList<String> addEffectsZoomIn(String imagePath,
			String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		// ffmpeg -i input.mp4 -i input.mp3 -c copy -map 0:0 -map 1:0 output.mp4
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-i");
		cmd.add(imagePath);
		cmd.add("-vf");
		cmd.add("\"");
		cmd.add("zoompan=z='if(lte(zoom,1.0),1.5,max(1.001,zoom-0.0015))':d=125");
		cmd.add("\"");
		cmd.add("-vcodec");
		cmd.add("libx264");
		cmd.add("-s");
		cmd.add("1280*720");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	// -filter_complex
	// "overlay='if(gte(t,1), -w+(t-1)*200, NAN)':(main_h-overlay_h)/2"
	/**
	 * 
	 * @param imagePath
	 * @param videoPath
	 * @param videoOutput
	 * @return
	 */
	public static ArrayList<String> addEffectsLeftRight(String imagePath,
			String videoPath, String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-i");
		cmd.add(videoPath);
		cmd.add("-i");
		cmd.add(imagePath);
		cmd.add("-filter_complex");
		cmd.add("\"");
		cmd.add("overlay='if(gte(t,1), -w+(t-1)*200, NAN)':(main_h-overlay_h)/2");
		cmd.add("\"");
		cmd.add("-c");
		cmd.add("copy");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	/**
	 * 
	 * @param imagePath
	 * @param videoPath
	 * @param videoOutput
	 * @return
	 */
	public static ArrayList<String> addEffectsRightLeft(String imagePath,
			String videoPath, String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-i");
		cmd.add(videoPath);
		cmd.add("-i");
		cmd.add(imagePath);
		cmd.add("-filter_complex");
		cmd.add("\"");
		cmd.add("overlay='if(gte(overlay_w,main_w), main_w-(t-1)*200, NAN)':(main_h-overlay_h)/2");
		cmd.add("\"");
		cmd.add("-c");
		cmd.add("copy");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	/**
	 * ffmpeg -i C:\Users\duong\Desktop\output.mp4 -i C:\Users\duong\Des
	 * ktop\images\Untitled-1_04.png -filter_complex
	 * "overlay=x=(main_w-overlay_w)/2:'i
	 * f(gte(t,2),if(lte(overlay_h,main_h),main_h-(t-1)*100,NAN))'" -vcodec
	 * libx264 -t 10 C:\Users\duong\Desktop\zoomout3.mp4
	 * 
	 * @param imagePath
	 * @param videoPath
	 * @param videoOutput
	 * @return
	 * 
	 * 
	 */
	public static ArrayList<String> addEffectsBotTop(String imagePath,
			String videoPath, String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-i");
		cmd.add(videoPath);
		cmd.add("-i");
		cmd.add(imagePath);
		cmd.add("-filter_complex");
		cmd.add("\"");
		cmd.add("overlay=x=(main_w-overlay_w)/2:'if(gte(t,2),if(lte(overlay_h,main_h),main_h-(t-1)*100,NAN))'");
		cmd.add("\"");
		cmd.add("-c");
		cmd.add("copy");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	/**
	 * ffmpeg -i C:\Users\duong\Desktop\output.mp4 -i C:\Users\duong\Des
	 * ktop\images\Untitled-1_04.png -filter_complex
	 * "overlay=x=(main_w-overlay_w)/2:'i f(gte(t,1),-h+(t-1)*100,NAN)'" -vcodec
	 * libx264 -t 10 C:\Users\duong\Desktop\zoom out2.mp4
	 * 
	 * @param imagePath
	 * @param videoPath
	 * @param videoOutput
	 * @return
	 */
	public static ArrayList<String> addEffectsTopBot(String imagePath,
			String videoPath, String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-i");
		cmd.add(videoPath);
		cmd.add("-i");
		cmd.add(imagePath);
		cmd.add("-filter_complex");
		cmd.add("\"");
		cmd.add("overlay=x=(main_w-overlay_w)/2:'if(gte(t,1),-h+(t-1)*100,NAN)'");
		cmd.add("\"");
		cmd.add("-c");
		cmd.add("copy");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	// min(zoom+0.0015,1.5)':d=700:x='if(gte(zoom,1.5),x,x+1/a)':y='if(gte(zoom,1.5),y,y+1)
	// zoom in
	/**
	 * 
	 * @param imagePath
	 * @param videoOutput
	 * @return
	 */
	public static ArrayList<String> addEffectsZoomOut(String imagePath,
			String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-i");
		cmd.add(imagePath);
		cmd.add("-vf");
		cmd.add("\"");
		cmd.add("zoompan=z=min(zoom+0.0015,1.5)':d=700:x='if(gte(zoom,1.5),x,x+1/a)':y='if(gte(zoom,1.5),y,y+1)'");
		cmd.add("\"");
		cmd.add("-c");
		cmd.add("copy");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	// chua xong
	// ffmpeg -i input.mp4 -vf
	// "drawtext="fontfile=/usr/share/fonts/truetype/freefont/FreeSans.ttf:
	// text='YOUR TEXT HERE':fontcolor=red@1.0:fontsize=70:x=00: y=40"" -y
	// output.mp4
	/**
	 * 
	 * @param videoPath
	 * @param text
	 * @param videoOutput
	 * @return
	 */
	public static ArrayList<String> addTitleVideo(String videoPath,
			String text, String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-i");
		cmd.add(videoPath);
		cmd.add("-vf");
		cmd.add("\"");
		cmd.add("drawtext=fontfile=file:///android_asset/font/DroidSans.tff : text='"
				+ text
				+ "':fontcolor=white@1.0"
				+ ":fontsize=70 x=20 y=(main_h-overlay_h)/2:box=1:boxcolor=black@.2");
		cmd.add("\"");
		cmd.add("-c");
		cmd.add("copy");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

	// dang code
	public static ArrayList<String> addFilterBackground(String videoPath,
			String text, String videoOutput) {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(CmdParameters.ROOT_PATH_FFMPEG);
		cmd.add("-i");
		cmd.add(videoPath);
		cmd.add("-vf");
		cmd.add("\"");
		cmd.add("drawtext=fontfile=file:///android_asset/font/DroidSans.tff : text='"
				+ text
				+ "':fontcolor=white@1.0"
				+ ":fontsize=70 x=20 y=(main_h-overlay_h)/2:box=1:boxcolor=black@.2");
		cmd.add("\"");
		cmd.add("-c");
		cmd.add("copy");
		cmd.add(Environment.getExternalStorageDirectory().getPath()
				+ CmdParameters.ROOT_FILE + videoOutput + ".mp4");
		return cmd;
	}

}
