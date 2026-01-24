package cl.tobal.voicerecorder;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;
import com.getcapacitor.Logger;
import java.io.File;
import java.io.IOException;

public class VoiceRecorder {

    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    private String currentFilePath;
    private long recordingStartTime;
    private Context context;

    public VoiceRecorder(Context context) {
        this.context = context;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void startRecording() throws IOException, IllegalStateException {
        if (isRecording) {
            throw new IllegalStateException("Recording is already in progress");
        }

        // Generate unique filename with timestamp
        String fileName = "recording_" + System.currentTimeMillis() + ".m4a";
        File outputDir = context.getExternalCacheDir();
        if (outputDir == null) {
            throw new IOException("Cannot access external cache directory");
        }
        File outputFile = new File(outputDir, fileName);
        currentFilePath = outputFile.getAbsolutePath();

        // Initialize MediaRecorder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mediaRecorder = new MediaRecorder(context);
        } else {
            mediaRecorder = new MediaRecorder();
        }

        // Configure MediaRecorder
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioEncodingBitRate(128000);
        mediaRecorder.setAudioSamplingRate(44100);
        mediaRecorder.setOutputFile(currentFilePath);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            recordingStartTime = System.currentTimeMillis();
            isRecording = true;
            Logger.info("VoiceRecorder", "Recording started: " + currentFilePath);
        } catch (IOException | IllegalStateException e) {
            cleanup();
            throw e;
        }
    }

    public RecordingResult stopRecording() throws IllegalStateException {
        if (!isRecording) {
            throw new IllegalStateException("No recording in progress");
        }

        try {
            mediaRecorder.stop();
            long duration = System.currentTimeMillis() - recordingStartTime;
            
            RecordingResult result = new RecordingResult();
            result.filePath = currentFilePath;
            result.duration = duration;
            result.mimeType = "audio/mp4";

            Logger.info("VoiceRecorder", "Recording stopped: " + currentFilePath + 
                       " (duration: " + duration + "ms)");
            
            return result;
        } finally {
            cleanup();
        }
    }

    private void cleanup() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.release();
            } catch (Exception e) {
                Logger.error("VoiceRecorder", "Error releasing MediaRecorder", e);
            }
            mediaRecorder = null;
        }
        isRecording = false;
        currentFilePath = null;
    }

    // Result class to hold recording information
    public static class RecordingResult {
        public String filePath;
        public long duration;
        public String mimeType;
    }
}
