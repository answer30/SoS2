package com.mirine.sos2020.common;

import android.content.Context;

import com.mirine.sos2020.lib.Transaction;
import com.klinker.android.send_message.Message;
import com.klinker.android.send_message.Settings;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SendMessage {

    // MMS 메세지 보내기 (미디어)
    public void sendMMSWithAudio(Context context, String phone, String audioPath) {
        String subject = "";
        String text = "";

        Settings settings = new Settings();
        settings.setUseSystemSending(true);

        // TODO : 이 Transaction 클래스를 위에 링크에서 다운받아서 써야함
        Transaction transaction = new Transaction(context, settings);

        // 제목이 있을경우
        Message message = new Message(text, phone, subject);

        File file = new File(audioPath);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        message.addAudio(bytes);
        message.addMedia(bytes, "video/mp4");


        long id = android.os.Process.getThreadPriority(android.os.Process.myTid());

        transaction.sendNewMessage(message, id);
    }

    // MMS 메세지 보내기 (Only Text)
    public void sendMMStext(Context context, String phone, String text) {
        String subject = "SOS";
        String body = text;

        Settings settings = new Settings();
        settings.setUseSystemSending(true);

        // TODO : 이 Transaction 클래스를 위에 링크에서 다운받아서 써야함
        Transaction transaction = new Transaction(context, settings);

        // 제목이 있을경우
        Message message = new Message(body, phone, subject);
        long id = android.os.Process.getThreadPriority(android.os.Process.myTid());


        transaction.sendNewMessage(message, id);



    }

}
