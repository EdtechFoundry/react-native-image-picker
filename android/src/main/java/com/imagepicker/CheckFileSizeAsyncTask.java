package com.imagepicker;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;

public class CheckFileSizeAsyncTask extends AsyncTask<Void, Void, Boolean> {

    public interface FileSizeAsyncResponse {
        void processFinish(Boolean result);
    }

    private String filePath;
    private int numberOfSecondsToSleep;
    private FileSizeAsyncResponse delegate;

    public CheckFileSizeAsyncTask(String filePath, int numberOfSecondsToSleep, FileSizeAsyncResponse delegate) {
        this.filePath = filePath;
        this.numberOfSecondsToSleep = numberOfSecondsToSleep;
        this.delegate = delegate;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        File file = new File(this.filePath);
        for (int i = 0; i < this.numberOfSecondsToSleep; i++) {
            if (file.length() > 0) {
                return true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(ImagePickerModule.TAG, "Failed to sleep thread while checking filesize");
                return false;
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        delegate.processFinish(result);
    }
}