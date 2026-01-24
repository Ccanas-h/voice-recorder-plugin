export interface PermissionResult {
  granted: boolean;
}

export interface RecordingResult {
  filePath: string;
  duration: number;
  mimeType: string;
}

export interface RecordingStatus {
  recording: boolean;
}

export interface VoiceRecorderPlugin {
  /**
   * Check if microphone permission has been granted
   */
  checkPermissions(): Promise<PermissionResult>;

  /**
   * Request microphone permission from the user
   */
  requestPermissions(): Promise<PermissionResult>;

  /**
   * Start recording audio
   * @throws Error if permission is not granted or recording is already in progress
   */
  startRecording(): Promise<void>;

  /**
   * Stop recording and return the audio file information
   * @returns RecordingResult with file path, duration, and MIME type
   * @throws Error if no recording is in progress
   */
  stopRecording(): Promise<RecordingResult>;

  /**
   * Check if currently recording
   */
  isRecording(): Promise<RecordingStatus>;
}
