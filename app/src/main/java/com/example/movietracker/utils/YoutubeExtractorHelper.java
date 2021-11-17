package com.example.movietracker.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import io.reactivex.Single;

import static com.example.movietracker.utils.Constants.YOUTUBE_VIDEO_URL;

public class YoutubeExtractorHelper {
    private Context context;

    @Inject
    public YoutubeExtractorHelper(@NonNull Context con) {
        this.context = con;
    }

    @SuppressLint("StaticFieldLeak")
    public Single<String> extract(String youtubeId){
        return Single.create(emitter -> {
           new YouTubeExtractor(context) {
                @Override
                public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                    if (ytFiles != null) {
                        int tag = 22;
                        YtFile ytFile = ytFiles.get(tag);
                        if (ytFile!=null) {
                            String downloadUrl = ytFile.getUrl();
                            emitter.onSuccess(downloadUrl);
                        } else {
                            emitter.onError(new Exception("Video not available"));
                        }
                    } else {
                        emitter.onError(new Exception("Video not available"));
                    }
                }
            }.extract(YOUTUBE_VIDEO_URL+youtubeId, true, true);
        });

    }
}
