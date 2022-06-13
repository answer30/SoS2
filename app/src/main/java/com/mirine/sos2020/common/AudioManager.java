package com.mirine.sos2020.common;


import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class AudioManager {

    private MediaRecorder mRecorder;
    private Context context;
    private File forderPath;
    private String fileName;
    private int count;

    public AudioManager(Context context, File forderPath, String fileName) {
        this.context = context;
        this.forderPath = forderPath;
        this.fileName = fileName;
        Log.e("audioR", "init");
    }

    public void recordeAudio(final long second, final RecordeSuccessListener recordeSuccessListener) {
        Log.e("audioR", "rec");
        final String path = recordeAudio();

        new Thread() {
            @Override
            public void run() {
                try {
                    count = 0;
                    while(count < second) {
                        Thread.sleep(1000);
                        count+=1;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                stopRecording();
                Log.e("audioR", "stop");
                recordeSuccessListener.success(path);

            }
        }.start();
    }

    public String recordeAudio() {
        File file = new File(forderPath, fileName+".mp4");

        mRecorder = new MediaRecorder();
        final MediaRecorder recorder = mRecorder;

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(file.getAbsolutePath());

        Log.d("audioR", "Path : "+file.getAbsolutePath());



        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    // 녹음 중지
    public void stopRecording() {
        if (mRecorder != null) {
            count = 0;
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public interface RecordeSuccessListener {
        void success(String audioPath);
    }
}

