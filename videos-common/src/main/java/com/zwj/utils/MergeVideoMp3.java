package com.zwj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MergeVideoMp3 {

    private String ffmpeg;


    public static void main(String[] args) {
        MergeVideoMp3 ffmpeg = new MergeVideoMp3("/Users/thewindrises/utils/tools/ffmpeg-2018/bin/./ffmpeg");
        try {
            ffmpeg.convertor("/Users/thewindrises/Downloads/1/www.0du520.com_02.mp4",
                    "/Users/thewindrises/Downloads/2018抖音最新热门英文精选合集/CHINA-2Sand.mp3",
                    7.1,
                    "/Users/thewindrises/Downloads/这是通过java生成的视频.avi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convertor(String videoInputPath,String mp3InputPath,Double seconds,
                          String videoOutputPath) throws IOException {
//./ffmpeg -i wxwxwx.mp4 -i Serebro.mp3 -t 12  -y new.mp4
        List<String> command = new ArrayList<>();

        command.add(ffmpeg);
        command.add("-i");
        command.add(videoInputPath);

        command.add("-i");
        command.add(mp3InputPath);

        command.add("-t");
        command.add(String.valueOf(seconds));

        command.add("-y");
        command.add(videoOutputPath);

        for (String s : command) {
            System.out.print(s);
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line= "";
        while ((line = br.readLine())!=null) {
        }

        if (br !=null)
            br.close();
        if (inputStreamReader != null)
            inputStreamReader.close();
        if (errorStream != null)
            errorStream.close();
    }
    public MergeVideoMp3(String ffmpeg) {
        this.ffmpeg = ffmpeg;
    }
}
