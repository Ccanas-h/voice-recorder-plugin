package cl.tobal.voicerecorder;

import android.Manifest;
import com.getcapacitor.JSObject;
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
            implementation.startRecording();
            call.resolve();
        } catch (Exception e) {
            call.reject("Failed to start recording: " + e.getMessage(), e);
        }
    }

    @PluginMethod
    public void stopRecording(PluginCall call) {
        try {
            VoiceRecorder.RecordingResult result = implementation.stopRecording();
            
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
