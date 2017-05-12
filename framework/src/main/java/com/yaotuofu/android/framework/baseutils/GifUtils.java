package com.yaotuofu.android.framework.baseutils;

import android.util.Log;

/**
 * @author AirZcm on 20161124.
 */
public class GifUtils {

    public static String getVideo2gifCommand(String sourcePath, String outPath, int width, int height) {

        StringBuilder command = new StringBuilder("-ss ");
        command.append(0.85);
        command.append(" -t ");
        command.append(1);
        command.append(" -i ");
        command.append(sourcePath);
        command.append(" -s ");
        command.append(width + "x" + height);
        command.append(" -f ");
        command.append("gif");
        command.append(" -r ");
        command.append("8 ");
        command.append(outPath);
        L.i("broncho", "command = " + command.toString());
        return command.toString();
    }

    public static String formatFastMotion(String sourcePath, String outPath) {
        StringBuilder command = new StringBuilder("-i ");
        command.append(sourcePath);
        command.append(" -vf setpts=0.5*PTS -af atempo=2.0 "); //两倍速 gif ok
        command.append(outPath);
        Log.i("broncho", "command = " + command.toString());
        return command.toString();
    }

    public static String formatMute(String sourcePath, String outPath) {
        StringBuilder command = new StringBuilder("-i ");
        command.append(sourcePath);
        command.append(" -vcodec copy -an "); //消声 video ok
        command.append(outPath);
        L.i("broncho", "command = " + command.toString());
        return command.toString();
    }

}
