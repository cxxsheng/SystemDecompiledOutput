package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
final class HotwordAudioStreamCopier {

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_COPY_BUFFER_LENGTH_BYTES = 32768;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_COPY_BUFFER_LENGTH_BYTES = 65536;
    private static final java.lang.String OP_MESSAGE = "Streaming hotword audio to VoiceInteractionService";
    private static final java.lang.String TAG = "HotwordAudioStreamCopier";
    private static final java.lang.String TASK_ID_PREFIX = "HotwordDetectedResult@";
    private static final java.lang.String THREAD_NAME_PREFIX = "Copy-";
    private final android.app.AppOpsManager mAppOpsManager;
    private final int mDetectorType;
    private final java.util.concurrent.ExecutorService mExecutorService = java.util.concurrent.Executors.newCachedThreadPool();
    private final java.lang.String mVoiceInteractorAttributionTag;
    private final java.lang.String mVoiceInteractorPackageName;
    private final int mVoiceInteractorUid;

    HotwordAudioStreamCopier(@android.annotation.NonNull android.app.AppOpsManager appOpsManager, int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        this.mAppOpsManager = appOpsManager;
        this.mDetectorType = i;
        this.mVoiceInteractorUid = i2;
        this.mVoiceInteractorPackageName = str;
        this.mVoiceInteractorAttributionTag = str2;
    }

    @android.annotation.NonNull
    public android.service.voice.HotwordDetectedResult startCopyingAudioStreams(@android.annotation.NonNull android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws java.io.IOException {
        return startCopyingAudioStreams(hotwordDetectedResult, true);
    }

    @android.annotation.NonNull
    public android.service.voice.HotwordDetectedResult startCopyingAudioStreams(@android.annotation.NonNull android.service.voice.HotwordDetectedResult hotwordDetectedResult, boolean z) throws java.io.IOException {
        int i;
        java.util.List<android.service.voice.HotwordAudioStream> audioStreams = hotwordDetectedResult.getAudioStreams();
        if (audioStreams.isEmpty()) {
            com.android.server.voiceinteraction.HotwordMetricsLogger.writeAudioEgressEvent(this.mDetectorType, 7, this.mVoiceInteractorUid, 0, 0, 0);
            return hotwordDetectedResult;
        }
        int size = audioStreams.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(audioStreams.size());
        java.util.ArrayList arrayList2 = new java.util.ArrayList(audioStreams.size());
        char c = 0;
        int i2 = 0;
        int i3 = 0;
        for (android.service.voice.HotwordAudioStream hotwordAudioStream : audioStreams) {
            android.os.ParcelFileDescriptor[] createReliablePipe = android.os.ParcelFileDescriptor.createReliablePipe();
            android.os.ParcelFileDescriptor parcelFileDescriptor = createReliablePipe[c];
            android.os.ParcelFileDescriptor parcelFileDescriptor2 = createReliablePipe[1];
            arrayList.add(hotwordAudioStream.buildUpon().setAudioStreamParcelFileDescriptor(parcelFileDescriptor).build());
            android.os.PersistableBundle metadata = hotwordAudioStream.getMetadata();
            int parcelableSize = i2 + android.service.voice.HotwordDetectedResult.getParcelableSize(metadata);
            if (metadata.containsKey("android.service.voice.key.AUDIO_STREAM_COPY_BUFFER_LENGTH_BYTES")) {
                i = metadata.getInt("android.service.voice.key.AUDIO_STREAM_COPY_BUFFER_LENGTH_BYTES", -1);
                if (i < 1 || i > 65536) {
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeAudioEgressEvent(this.mDetectorType, 9, this.mVoiceInteractorUid, 0, 0, size);
                    android.util.Slog.w(TAG, "Attempted to set an invalid copy buffer length (" + i + ") for: " + hotwordAudioStream);
                    i = 32768;
                }
            } else {
                i = 32768;
            }
            i3 += hotwordAudioStream.getInitialAudio().length;
            arrayList2.add(new com.android.server.voiceinteraction.HotwordAudioStreamCopier.CopyTaskInfo(hotwordAudioStream.getAudioStreamParcelFileDescriptor(), parcelFileDescriptor2, i));
            i2 = parcelableSize;
            c = 0;
        }
        this.mExecutorService.execute(new com.android.server.voiceinteraction.HotwordAudioStreamCopier.HotwordDetectedResultCopyTask(TASK_ID_PREFIX + java.lang.System.identityHashCode(hotwordDetectedResult), arrayList2, i2, i3, z));
        return hotwordDetectedResult.buildUpon().setAudioStreams(arrayList).build();
    }

    private static class CopyTaskInfo {
        private final int mCopyBufferLength;
        private final android.os.ParcelFileDescriptor mSink;
        private final android.os.ParcelFileDescriptor mSource;

        CopyTaskInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i) {
            this.mSource = parcelFileDescriptor;
            this.mSink = parcelFileDescriptor2;
            this.mCopyBufferLength = i;
        }
    }

    private class HotwordDetectedResultCopyTask implements java.lang.Runnable {
        private final java.util.List<com.android.server.voiceinteraction.HotwordAudioStreamCopier.CopyTaskInfo> mCopyTaskInfos;
        private final java.util.concurrent.ExecutorService mExecutorService = java.util.concurrent.Executors.newCachedThreadPool();
        private final java.lang.String mResultTaskId;
        private final boolean mShouldNotifyAppOpsManager;
        private final int mTotalInitialAudioSizeBytes;
        private final int mTotalMetadataSizeBytes;

        HotwordDetectedResultCopyTask(java.lang.String str, java.util.List<com.android.server.voiceinteraction.HotwordAudioStreamCopier.CopyTaskInfo> list, int i, int i2, boolean z) {
            this.mResultTaskId = str;
            this.mCopyTaskInfos = list;
            this.mTotalMetadataSizeBytes = i;
            this.mTotalInitialAudioSizeBytes = i2;
            this.mShouldNotifyAppOpsManager = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            java.lang.Thread.currentThread().setName(com.android.server.voiceinteraction.HotwordAudioStreamCopier.THREAD_NAME_PREFIX + this.mResultTaskId);
            int size = this.mCopyTaskInfos.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i = 0; i < size; i++) {
                com.android.server.voiceinteraction.HotwordAudioStreamCopier.CopyTaskInfo copyTaskInfo = this.mCopyTaskInfos.get(i);
                arrayList.add(new com.android.server.voiceinteraction.HotwordAudioStreamCopier.SingleAudioStreamCopyTask(this.mResultTaskId + "@" + i, copyTaskInfo.mSource, copyTaskInfo.mSink, copyTaskInfo.mCopyBufferLength, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mDetectorType, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorUid));
            }
            if (this.mShouldNotifyAppOpsManager && com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mAppOpsManager.startOpNoThrow("android:record_audio_hotword", com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorUid, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorPackageName, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorAttributionTag, com.android.server.voiceinteraction.HotwordAudioStreamCopier.OP_MESSAGE) != 0) {
                com.android.server.voiceinteraction.HotwordMetricsLogger.writeAudioEgressEvent(com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mDetectorType, 4, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorUid, 0, 0, size);
                bestEffortPropagateError("Failed to obtain RECORD_AUDIO_HOTWORD permission for voice interactor with uid=" + com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorUid + " packageName=" + com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorPackageName + " attributionTag=" + com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorAttributionTag);
                return;
            }
            try {
                try {
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeAudioEgressEvent(com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mDetectorType, 1, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorUid, this.mTotalInitialAudioSizeBytes, this.mTotalMetadataSizeBytes, size);
                    this.mExecutorService.invokeAll(arrayList);
                    int i2 = this.mTotalInitialAudioSizeBytes;
                    java.util.Iterator it = arrayList.iterator();
                    int i3 = i2;
                    while (it.hasNext()) {
                        i3 += ((com.android.server.voiceinteraction.HotwordAudioStreamCopier.SingleAudioStreamCopyTask) it.next()).mTotalCopiedBytes;
                    }
                    android.util.Slog.i(com.android.server.voiceinteraction.HotwordAudioStreamCopier.TAG, this.mResultTaskId + ": Task was completed. Total bytes egressed: " + i3 + " (including " + this.mTotalInitialAudioSizeBytes + " bytes NOT streamed), total metadata bundle size bytes: " + this.mTotalMetadataSizeBytes);
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeAudioEgressEvent(com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mDetectorType, 2, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorUid, i3, this.mTotalMetadataSizeBytes, size);
                    if (!this.mShouldNotifyAppOpsManager) {
                        return;
                    }
                } catch (java.lang.InterruptedException e) {
                    int i4 = this.mTotalInitialAudioSizeBytes;
                    java.util.Iterator it2 = arrayList.iterator();
                    int i5 = i4;
                    while (it2.hasNext()) {
                        i5 += ((com.android.server.voiceinteraction.HotwordAudioStreamCopier.SingleAudioStreamCopyTask) it2.next()).mTotalCopiedBytes;
                    }
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeAudioEgressEvent(com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mDetectorType, 3, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorUid, i5, this.mTotalMetadataSizeBytes, size);
                    android.util.Slog.i(com.android.server.voiceinteraction.HotwordAudioStreamCopier.TAG, this.mResultTaskId + ": Task was interrupted. Total bytes egressed: " + i5 + " (including " + this.mTotalInitialAudioSizeBytes + " bytes NOT streamed), total metadata bundle size bytes: " + this.mTotalMetadataSizeBytes);
                    bestEffortPropagateError(e.getMessage());
                    if (!this.mShouldNotifyAppOpsManager) {
                        return;
                    }
                }
                com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mAppOpsManager.finishOp("android:record_audio_hotword", com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorUid, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorPackageName, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorAttributionTag);
            } catch (java.lang.Throwable th) {
                if (this.mShouldNotifyAppOpsManager) {
                    com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mAppOpsManager.finishOp("android:record_audio_hotword", com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorUid, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorPackageName, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorAttributionTag);
                }
                throw th;
            }
        }

        private void bestEffortPropagateError(@android.annotation.NonNull java.lang.String str) {
            try {
                for (com.android.server.voiceinteraction.HotwordAudioStreamCopier.CopyTaskInfo copyTaskInfo : this.mCopyTaskInfos) {
                    copyTaskInfo.mSource.closeWithError(str);
                    copyTaskInfo.mSink.closeWithError(str);
                }
                com.android.server.voiceinteraction.HotwordMetricsLogger.writeAudioEgressEvent(com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mDetectorType, 10, com.android.server.voiceinteraction.HotwordAudioStreamCopier.this.mVoiceInteractorUid, 0, 0, this.mCopyTaskInfos.size());
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.voiceinteraction.HotwordAudioStreamCopier.TAG, this.mResultTaskId + ": Failed to propagate error", e);
            }
        }
    }

    private static class SingleAudioStreamCopyTask implements java.util.concurrent.Callable<java.lang.Void> {
        private final android.os.ParcelFileDescriptor mAudioSink;
        private final android.os.ParcelFileDescriptor mAudioSource;
        private final int mCopyBufferLength;
        private final int mDetectorType;
        private final java.lang.String mStreamTaskId;
        private volatile int mTotalCopiedBytes = 0;
        private final int mUid;

        SingleAudioStreamCopyTask(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i, int i2, int i3) {
            this.mStreamTaskId = str;
            this.mAudioSource = parcelFileDescriptor;
            this.mAudioSink = parcelFileDescriptor2;
            this.mCopyBufferLength = i;
            this.mDetectorType = i2;
            this.mUid = i3;
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x00cc, code lost:
        
            if (r3 != null) goto L17;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:40:0x00d2  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00d7  */
        /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Thread] */
        /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r2v3 */
        /* JADX WARN: Type inference failed for: r2v5, types: [android.os.ParcelFileDescriptor$AutoCloseInputStream, java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r3v12 */
        /* JADX WARN: Type inference failed for: r3v2 */
        /* JADX WARN: Type inference failed for: r3v4, types: [java.io.OutputStream] */
        @Override // java.util.concurrent.Callable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public java.lang.Void call() throws java.lang.Exception {
            ?? r3;
            java.io.InputStream inputStream;
            android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
            ?? currentThread = java.lang.Thread.currentThread();
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(com.android.server.voiceinteraction.HotwordAudioStreamCopier.THREAD_NAME_PREFIX);
            java.lang.String str = this.mStreamTaskId;
            sb.append(str);
            ?? sb2 = sb.toString();
            currentThread.setName(sb2);
            java.io.InputStream inputStream2 = null;
            try {
                try {
                    sb2 = new android.os.ParcelFileDescriptor.AutoCloseInputStream(this.mAudioSource);
                } catch (java.io.IOException e) {
                    e = e;
                    inputStream = null;
                    autoCloseOutputStream = null;
                } catch (java.lang.Throwable th) {
                    th = th;
                    r3 = 0;
                    if (inputStream2 != null) {
                    }
                    if (r3 != 0) {
                    }
                    throw th;
                }
                try {
                    autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(this.mAudioSink);
                } catch (java.io.IOException e2) {
                    e = e2;
                    autoCloseOutputStream = null;
                    inputStream = sb2;
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    str = null;
                    inputStream2 = sb2;
                    r3 = str;
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    if (r3 != 0) {
                        r3.close();
                    }
                    throw th;
                }
                try {
                    byte[] bArr = new byte[this.mCopyBufferLength];
                    while (true) {
                        if (java.lang.Thread.interrupted()) {
                            android.util.Slog.e(com.android.server.voiceinteraction.HotwordAudioStreamCopier.TAG, this.mStreamTaskId + ": SingleAudioStreamCopyTask task was interrupted");
                            break;
                        }
                        int read = sb2.read(bArr);
                        if (read < 0) {
                            android.util.Slog.i(com.android.server.voiceinteraction.HotwordAudioStreamCopier.TAG, this.mStreamTaskId + ": Reached end of audio stream");
                            break;
                        }
                        if (read > 0) {
                            autoCloseOutputStream.write(bArr, 0, read);
                            this.mTotalCopiedBytes += read;
                        }
                    }
                    sb2.close();
                } catch (java.io.IOException e3) {
                    e = e3;
                    inputStream = sb2;
                    this.mAudioSource.closeWithError(e.getMessage());
                    this.mAudioSink.closeWithError(e.getMessage());
                    android.util.Slog.i(com.android.server.voiceinteraction.HotwordAudioStreamCopier.TAG, this.mStreamTaskId + ": Failed to copy audio stream", e);
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeAudioEgressEvent(this.mDetectorType, 10, this.mUid, 0, 0, 0);
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                autoCloseOutputStream.close();
                return null;
            } catch (java.lang.Throwable th3) {
                th = th3;
            }
        }
    }
}
