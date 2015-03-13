package org.ffmpeg.android.filters;

import java.io.IOException;

import org.ffmpeg.android.CmdParameters;

public class ProcessMaker {
	public void processFFmpeg() {
		Process process = null;

		try {
			String[] args = { CmdParameters.SYSTEM_MOD, CmdParameters.CHMOD,
					CmdParameters.ROOT_PATH_FFMPEG };
			process = new ProcessBuilder(args).start();
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			process.destroy();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processSox() {
		Process process = null;

		try {
			String[] args = { CmdParameters.SYSTEM_MOD, CmdParameters.CHMOD,
					CmdParameters.ROOT_PATH_SOX };
			process = new ProcessBuilder(args).start();
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			process.destroy();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
