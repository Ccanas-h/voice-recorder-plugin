package cl.tobal.voicerecorder;

import android.Manifest;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.PermissionState;

@CapacitorPlugin(
    name = "VoiceRecorder",
    permissions = {
        @Permission(
            strings = { Manifest.permission.RECORD_AUDIO },
            alias = "microphone"
        )
    }
)
public class VoiceRecorderPlugin extends Plugin {

    private VoiceRecorder implementation;

    @Override
    public void load() {
        implementation = new VoiceRecorder(getContext());
    }

    @PluginMethod
    public void checkPermissions(PluginCall call) {
        JSObject result = new JSObject();
        
        if (getPermissionState("microphone") == PermissionState.GRANTED) {
            result.put("granted", true);
        } else {
            result.put("granted", false);
        }
        
        call.resolve(result);
    }

    @PluginMethod
    public void requestPermissions(PluginCall call) {
        if (getPermissionState("microphone") == PermissionState.GRANTED) {
            JSObject result = new JSObject();
            result.put("granted", true);
            call.resolve(result);
        } else {
            requestPermissionForAlias("microphone", call, "permissionCallback");
        }
    }

    @PluginMethod
    public void startRecording(PluginCall call) {
        if (getPermissionState("microphone") != PermissionState.GRANTED) {
            call.reject("Microphone permission not granted");
            return;
        }

        try {
            Logger.info("VoiceRecorderPlugin", "🎤 Java: startRecording() called");
            implementation.startRecording(); // BLOCKING - returns when recording started
            Logger.info("VoiceRecorderPlugin", "✅ Java: MediaRecorder started");
            
            // Emit event IMMEDIATELY - recording has already started
            JSObject eventData = new JSObject();
            eventData.put("recording", true);
            Logger.info("VoiceRecorderPlugin", "📡 Java: Emitting recordingStateChanged event with recording=true");
            notifyListeners("recordingStateChanged", eventData);
            Logger.info("VoiceRecorderPlugin", "📡 Java: Event emitted");
            
            call.resolve();
            Logger.info("VoiceRecorderPlugin", "✅ Java: call.resolve() completed");
        } catch (Exception e) {
            Logger.info("VoiceRecorderPlugin", "❌ Java: Error - " + e.getMessage());
            call.reject("Failed to start recording: " + e.getMessage(), e);
        }
    }

    @PluginMethod
    public void stopRecording(PluginCall call) {
        try {
            VoiceRecorder.RecordingResult result = implementation.stopRecording(); // BLOCKING
            
            // Emit event IMMEDIATELY - recording has already stopped
            JSObject eventData = new JSObject();
            eventData.put("recording", false);
            notifyListeners("recordingStateChanged", eventData);
            
            JSObject ret = new JSObject();
            ret.put("filePath", result.filePath);
            ret.put("duration", result.duration);
            ret.put("mimeType", result.mimeType);
            
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Failed to stop recording: " + e.getMessage(), e);
        }
    }

    @PluginMethod
    public void isRecording(PluginCall call) {
        JSObject result = new JSObject();
        result.put("recording", implementation.isRecording());
        call.resolve(result);
    }

    @PluginMethod
    public void permissionCallback(PluginCall call) {
        JSObject result = new JSObject();
        
        if (getPermissionState("microphone") == PermissionState.GRANTED) {
            result.put("granted", true);
        } else {
            result.put("granted", false);
        }
        
        call.resolve(result);
    }
}
