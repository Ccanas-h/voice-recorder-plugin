# @tobal/voice-recorder

custom plugin capacitor for recording audio

## Install

```bash
npm install @tobal/voice-recorder
npx cap sync
```

## API

<docgen-index>

* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`startRecording()`](#startrecording)
* [`stopRecording()`](#stoprecording)
* [`isRecording()`](#isrecording)
* [`addListener('recordingStateChanged', ...)`](#addlistenerrecordingstatechanged-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionResult>
```

Check if microphone permission has been granted

**Returns:** <code>Promise&lt;<a href="#permissionresult">PermissionResult</a>&gt;</code>

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionResult>
```

Request microphone permission from the user

**Returns:** <code>Promise&lt;<a href="#permissionresult">PermissionResult</a>&gt;</code>

--------------------


### startRecording()

```typescript
startRecording() => Promise<void>
```

Start recording audio

--------------------


### stopRecording()

```typescript
stopRecording() => Promise<RecordingResult>
```

Stop recording and return the audio file information

**Returns:** <code>Promise&lt;<a href="#recordingresult">RecordingResult</a>&gt;</code>

--------------------


### isRecording()

```typescript
isRecording() => Promise<RecordingStatus>
```

Check if currently recording

**Returns:** <code>Promise&lt;<a href="#recordingstatus">RecordingStatus</a>&gt;</code>

--------------------


### addListener('recordingStateChanged', ...)

```typescript
addListener(eventName: 'recordingStateChanged', listenerFunc: (event: RecordingStateChangedEvent) => void) => Promise<any>
```

Listen to recording state changes

| Param              | Type                                                                                                  | Description                                           |
| ------------------ | ----------------------------------------------------------------------------------------------------- | ----------------------------------------------------- |
| **`eventName`**    | <code>'recordingStateChanged'</code>                                                                  | - Must be 'recordingStateChanged'                     |
| **`listenerFunc`** | <code>(event: <a href="#recordingstatechangedevent">RecordingStateChangedEvent</a>) =&gt; void</code> | - Callback function that receives the recording state |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin

--------------------


### Interfaces


#### PermissionResult

| Prop          | Type                 |
| ------------- | -------------------- |
| **`granted`** | <code>boolean</code> |


#### RecordingResult

| Prop           | Type                |
| -------------- | ------------------- |
| **`filePath`** | <code>string</code> |
| **`duration`** | <code>number</code> |
| **`mimeType`** | <code>string</code> |


#### RecordingStatus

| Prop            | Type                 |
| --------------- | -------------------- |
| **`recording`** | <code>boolean</code> |


#### RecordingStateChangedEvent

| Prop            | Type                 |
| --------------- | -------------------- |
| **`recording`** | <code>boolean</code> |

</docgen-api>


