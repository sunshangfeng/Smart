package com.ssf.smart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.io.IOException;

/**
 * Created by Android on 2015/11/15.
 */
public class WebMusicInterface {

    private Context mContext;

    private MediaPlayer mediaPlayer;

    private WebView mWeb;

    public WebMusicInterface(Context context, WebView webView) {
        this.mContext = context;
        this.mWeb = webView;
    }

    private int i;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @JavascriptInterface//加入这个注解
    public void PLAY(final String str) {

        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            AssetFileDescriptor fileDescriptor = mContext.getAssets().openFd("music/" + str + ".mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    i++;
                    if (i != 2) {
                        PLAY(str);
                    } else {
                        i = 0;
                        mWeb.post(new Runnable() {
                            @Override
                            public void run() {
                                mWeb.loadUrl("javascript:playEnd('" + str + "')");
                                Log.e(WebMusicInterface.class.getSimpleName(),"播放完成回调");
                            }
                        });
                    }
                }
            });
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
