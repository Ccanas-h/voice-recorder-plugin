package cl.tobal.voicerecorder;

import com.getcapacitor.Logger;

public class VoiceRecorder {

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }
}
