package com.ksintership.kozhushanmariia.repository;

import android.content.Context;
import android.util.Log;

import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.rest.RestApiService;
import com.ksintership.kozhushanmariia.utils.ThreadUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Response;


public class TrackPreviewRepositoryImpl implements TrackPreviewRepository {
    private static final String TAG = TrackPreviewRepositoryImpl.class.getSimpleName();

    private static final String TRACK_PREVIEW_CACHE_DIR = "tracks_cache";
    private static final String FILE_SUFFIX = ".mp3";

    Context context;
    RestApiService restService;

    ExecutorService executor;

    public TrackPreviewRepositoryImpl(Context context, RestApiService restService) {
        this.context = context;
        this.restService = restService;
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public int getCacheSizeInMb() {
        long startTime = System.currentTimeMillis();

        File[] listFiles = getDirectory().listFiles();
        int directorySize = 0;
        if (listFiles == null) return 0;
        for (File file : listFiles) {
            directorySize += file.length();
        }

        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        Log.i(TAG, "getCacheSizeInMb execution time = " + delta);

        return Math.round(directorySize / (1024 * 1024));
    }

    @Override
    public void clearCache() {
        long startTime = System.currentTimeMillis();

        File[] listFiles = getDirectory().listFiles();
        if (listFiles == null) return;
        for (File file : listFiles) {
            file.delete();
        }

        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        Log.i(TAG, "clearCache execution time = " + delta);
    }

    @Override
    public void loadTrackPreview(final TrackModel trackModel, Callback callback) {
        executor.execute(() -> {
            if (checkIfExist(String.valueOf(trackModel.getId()))) {
                trackModel.setLocalPathPreview(getTrackFile(String.valueOf(trackModel.getId())).getAbsolutePath());
                callback.onLoaded(trackModel);
                return;
            }

            try {
                Response<ResponseBody> response = restService.loadTrackPreview(trackModel.getTrackPreviewUrl()).execute();
                if (response.isSuccessful() && response.body() != null && response.body().contentLength() > 0) {

                    String path = writeToStorage(response.body(), String.valueOf(trackModel.getId()));
                    if (path == null) {
                        ThreadUtil.runOnMain(callback::onFailed);
                        return;
                    }
                    trackModel.setLocalPathPreview(path);
                    ThreadUtil.runOnMain(() -> callback.onLoaded(trackModel));
                }
            } catch (IOException e) {
                Log.e(TAG, "Rest request failed: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private String writeToStorage(ResponseBody body, String filename) {
        File track = getTrackFile(filename);
        createFileIfNotExists(track);
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            try {
                byte[] buffer = new byte[4096];

                outputStream = new FileOutputStream(track);
                inputStream = body.byteStream();

                int readed;
                while ((readed = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, readed);
                }
                outputStream.flush();

                return track.getAbsolutePath();
            } catch (IOException e) {
                Log.e(TAG, "File writing exception: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Finally close exception: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkIfExist(String filename) {
        return getTrackFile(filename).exists();
    }

    private void createFileIfNotExists(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Creating file exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private File getTrackFile(String filename) {
        return new File(getDirectory().getAbsolutePath() + File.separator + filename + FILE_SUFFIX);
    }

    @NotNull
    private File getDirectory() {
        File directory = new File(context.getFilesDir()
                + File.separator
                + TRACK_PREVIEW_CACHE_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

}
