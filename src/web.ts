import { WebPlugin } from '@capacitor/core';

import type { VoiceRecorderPlugin, PermissionResult, RecordingResult, RecordingStatus } from './definitions';

export class VoiceRecorderWeb extends WebPlugin implements VoiceRecorderPlugin {
  async checkPermissions(): Promise<PermissionResult> {
    throw this.unimplemented('Not implemented on web. Use native platform.');
  }

  async requestPermissions(): Promise<PermissionResult> {
    throw this.unimplemented('Not implemented on web. Use native platform.');
  }

  async startRecording(): Promise<void> {
    throw this.unimplemented('Not implemented on web. Use native platform.');
  }

  async stopRecording(): Promise<RecordingResult> {
    throw this.unimplemented('Not implemented on web. Use native platform.');
  }

  async isRecording(): Promise<RecordingStatus> {
    throw this.unimplemented('Not implemented on web. Use native platform.');
  }
}
