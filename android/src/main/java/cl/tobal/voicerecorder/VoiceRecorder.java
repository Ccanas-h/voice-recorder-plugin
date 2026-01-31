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
        
        // ⚡ OPTIMIZADO PARA WHISPER
        // Whisper procesa internamente a 16kHz mono, así que grabamos directamente en ese formato
        // Beneficios: archivos 4x más pequeños, sin conversión en backend, mejor precisión
        mediaRecorder.setAudioEncodingBitRate(64000);  // 64 kbps (óptimo para voz, antes: 128k)
        mediaRecorder.setAudioSamplingRate(16000);     // 16 kHz (formato nativo de Whisper, antes: 44.1k)
        mediaRecorder.setAudioChannels(1);              // Mono (Whisper convierte a mono de todas formas)
        
        mediaRecorder.setOutputFile(currentFilePath);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start(); // BLOCKING - when this returns, recording HAS started
            
            // Update state IMMEDIATELY after start()
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
            mediaRecorder.stop(); // BLOCKING - when this returns, recording HAS stopped
            
            // Update state IMMEDIATELY after stop()
            isRecording = false;
            
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
                Logger.info("VoiceRecorder", "Error releasing MediaRecorder: " + e.getMessage());
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
