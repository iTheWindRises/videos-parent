package com.zwj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FetchVideoCover {

    private String ffmpeg;


    public static void main(String[] args) {
        //./ffmpeg -ss 00:00:01 -y -i wxwxwx.mp4 -vframes 1 new.jpg
        FetchVideoCover ffmpeg = new FetchVideoCover("/Users/thewindrises/utils/tools/ffmpeg-2018/bin/./ffmpeg");
        try {
            ffmpeg.convertor("/Users/thewindrises/Downloads/1/www.0du520.com_02.mp4",
                    "/Users/thewindrises/Downloads/这是通过java生成的视频封面.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convertor(String videoInputPath,String coverOutputPath) throws IOException {
        List<String> command = new ArrayList<>();

        command.add(ffmpeg);
        command.add("-ss");
        command.add("00:00:01");

        command.add("-y");
        command.add("-i");
        command.add(videoInputPath);

        command.add("-vframes");
        command.add("1");

        command.add(coverOutputPath);


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
    public FetchVideoCover(String ffmpeg) {
        this.ffmpeg = ffmpeg;
    }
}
