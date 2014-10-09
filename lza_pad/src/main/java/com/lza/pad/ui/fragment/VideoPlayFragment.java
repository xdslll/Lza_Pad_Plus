package com.lza.pad.ui.fragment;

import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.lza.pad.R;
import com.lza.pad.lib.support.file.FileTools;
import com.lza.pad.ui.widget.TitleBar;

import java.io.File;

/**
 * 视频播放界面
 *
 * @author Sam
 * @Date 14-9-11
 */
public class VideoPlayFragment extends AbstractFragment {

    VideoView mVideoView;
    MediaController mMediaController;
    TextView mTxtInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_play, container, false);
        mTitleBar = (TitleBar) view.findViewById(R.id.video_play_title_bar);
        mTxtInfo = (TextView) view.findViewById(R.id.video_play_info);
        mVideoView = (VideoView) view.findViewById(R.id.video_play_view);
        mMediaController = new MediaController(getActivity());
        File sdCardFile = FileTools.getSDCardDir();
        String filePath = getActivity().getString(R.string.test_video_path);
        File videoFile = new File(sdCardFile, filePath);
        if (videoFile.exists()) {
            mVideoView.setVideoPath(videoFile.getAbsolutePath());
            mVideoView.setMediaController(mMediaController);
            mMediaController.setMediaPlayer(mVideoView);
            mVideoView.requestFocus();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //设置静音
        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }
}
