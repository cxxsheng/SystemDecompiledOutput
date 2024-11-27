package android.media;

/* loaded from: classes2.dex */
public class LoudnessCodecController implements android.media.permission.SafeCloseable {
    private static final java.lang.String TAG = "LoudnessCodecController";
    private final android.media.LoudnessCodecDispatcher mLcDispatcher;
    private final int mSessionId;
    private final java.lang.Object mControllerLock = new java.lang.Object();
    private final java.util.HashMap<android.media.LoudnessCodecInfo, java.util.Set<android.media.MediaCodec>> mMediaCodecs = new java.util.HashMap<>();

    public interface OnLoudnessCodecUpdateListener {
        default android.os.Bundle onLoudnessCodecUpdate(android.media.MediaCodec mediaCodec, android.os.Bundle bundle) {
            return bundle;
        }
    }

    public static android.media.LoudnessCodecController create(int i) {
        android.media.LoudnessCodecDispatcher loudnessCodecDispatcher = new android.media.LoudnessCodecDispatcher(android.media.AudioManager.getService());
        android.media.LoudnessCodecController loudnessCodecController = new android.media.LoudnessCodecController(loudnessCodecDispatcher, i);
        loudnessCodecDispatcher.addLoudnessCodecListener(loudnessCodecController, java.util.concurrent.Executors.newSingleThreadExecutor(), new android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener() { // from class: android.media.LoudnessCodecController.1
        });
        loudnessCodecDispatcher.startLoudnessCodecUpdates(i);
        return loudnessCodecController;
    }

    public static android.media.LoudnessCodecController create(int i, java.util.concurrent.Executor executor, android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener) {
        java.util.Objects.requireNonNull(executor, "Executor cannot be null");
        java.util.Objects.requireNonNull(onLoudnessCodecUpdateListener, "OnLoudnessCodecUpdateListener cannot be null");
        android.media.LoudnessCodecDispatcher loudnessCodecDispatcher = new android.media.LoudnessCodecDispatcher(android.media.AudioManager.getService());
        android.media.LoudnessCodecController loudnessCodecController = new android.media.LoudnessCodecController(loudnessCodecDispatcher, i);
        loudnessCodecDispatcher.addLoudnessCodecListener(loudnessCodecController, executor, onLoudnessCodecUpdateListener);
        loudnessCodecDispatcher.startLoudnessCodecUpdates(i);
        return loudnessCodecController;
    }

    public static android.media.LoudnessCodecController createForTesting(int i, java.util.concurrent.Executor executor, android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener, android.media.IAudioService iAudioService) {
        java.util.Objects.requireNonNull(iAudioService, "IAudioService cannot be null");
        java.util.Objects.requireNonNull(executor, "Executor cannot be null");
        java.util.Objects.requireNonNull(onLoudnessCodecUpdateListener, "OnLoudnessCodecUpdateListener cannot be null");
        android.media.LoudnessCodecDispatcher loudnessCodecDispatcher = new android.media.LoudnessCodecDispatcher(iAudioService);
        android.media.LoudnessCodecController loudnessCodecController = new android.media.LoudnessCodecController(loudnessCodecDispatcher, i);
        loudnessCodecDispatcher.addLoudnessCodecListener(loudnessCodecController, executor, onLoudnessCodecUpdateListener);
        loudnessCodecDispatcher.startLoudnessCodecUpdates(i);
        return loudnessCodecController;
    }

    private LoudnessCodecController(android.media.LoudnessCodecDispatcher loudnessCodecDispatcher, int i) {
        this.mLcDispatcher = (android.media.LoudnessCodecDispatcher) java.util.Objects.requireNonNull(loudnessCodecDispatcher, "Dispatcher cannot be null");
        this.mSessionId = i;
    }

    public boolean addMediaCodec(android.media.MediaCodec mediaCodec) {
        final android.media.MediaCodec mediaCodec2 = (android.media.MediaCodec) java.util.Objects.requireNonNull(mediaCodec, "MediaCodec for addMediaCodec cannot be null");
        android.media.LoudnessCodecInfo codecInfo = getCodecInfo(mediaCodec2);
        if (codecInfo == null) {
            android.util.Log.v(TAG, "Could not extract codec loudness information");
            return false;
        }
        synchronized (this.mControllerLock) {
            final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(false);
            if (this.mMediaCodecs.computeIfPresent(codecInfo, new java.util.function.BiFunction() { // from class: android.media.LoudnessCodecController$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                    return android.media.LoudnessCodecController.lambda$addMediaCodec$0(atomicBoolean, mediaCodec2, (android.media.LoudnessCodecInfo) obj, (java.util.Set) obj2);
                }
            }) == null) {
                java.util.HashSet hashSet = new java.util.HashSet();
                hashSet.add(mediaCodec2);
                this.mMediaCodecs.put(codecInfo, hashSet);
            }
            if (atomicBoolean.get()) {
                throw new java.lang.IllegalArgumentException("Loudness controller already added " + mediaCodec);
            }
        }
        this.mLcDispatcher.addLoudnessCodecInfo(this.mSessionId, mediaCodec.hashCode(), codecInfo);
        return true;
    }

    static /* synthetic */ java.util.Set lambda$addMediaCodec$0(java.util.concurrent.atomic.AtomicBoolean atomicBoolean, android.media.MediaCodec mediaCodec, android.media.LoudnessCodecInfo loudnessCodecInfo, java.util.Set set) {
        atomicBoolean.set(!set.add(mediaCodec));
        return set;
    }

    public void removeMediaCodec(final android.media.MediaCodec mediaCodec) {
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(false);
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean2 = new java.util.concurrent.atomic.AtomicBoolean(false);
        android.media.LoudnessCodecInfo codecInfo = getCodecInfo((android.media.MediaCodec) java.util.Objects.requireNonNull(mediaCodec, "MediaCodec for removeMediaCodec cannot be null"));
        if (codecInfo == null) {
            throw new java.lang.IllegalArgumentException("Could not extract codec loudness information");
        }
        synchronized (this.mControllerLock) {
            this.mMediaCodecs.computeIfPresent(codecInfo, new java.util.function.BiFunction() { // from class: android.media.LoudnessCodecController$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                    return android.media.LoudnessCodecController.lambda$removeMediaCodec$1(atomicBoolean, mediaCodec, atomicBoolean2, (android.media.LoudnessCodecInfo) obj, (java.util.Set) obj2);
                }
            });
            if (!atomicBoolean.get()) {
                throw new java.lang.IllegalArgumentException("Loudness controller does not contain " + mediaCodec);
            }
        }
        if (atomicBoolean2.get()) {
            this.mLcDispatcher.removeLoudnessCodecInfo(this.mSessionId, codecInfo);
        }
    }

    static /* synthetic */ java.util.Set lambda$removeMediaCodec$1(java.util.concurrent.atomic.AtomicBoolean atomicBoolean, android.media.MediaCodec mediaCodec, java.util.concurrent.atomic.AtomicBoolean atomicBoolean2, android.media.LoudnessCodecInfo loudnessCodecInfo, java.util.Set set) {
        atomicBoolean.set(set.remove(mediaCodec));
        if (set.isEmpty()) {
            atomicBoolean2.set(true);
            return null;
        }
        return set;
    }

    public android.os.Bundle getLoudnessCodecParams(android.media.MediaCodec mediaCodec) {
        java.util.Objects.requireNonNull(mediaCodec, "MediaCodec cannot be null");
        android.media.LoudnessCodecInfo codecInfo = getCodecInfo(mediaCodec);
        if (codecInfo == null) {
            throw new java.lang.IllegalArgumentException("MediaCodec does not have valid codec information");
        }
        synchronized (this.mControllerLock) {
            java.util.Set<android.media.MediaCodec> set = this.mMediaCodecs.get(codecInfo);
            if (set == null || !set.contains(mediaCodec)) {
                throw new java.lang.IllegalArgumentException("MediaCodec was not added for loudness annotation");
            }
        }
        return this.mLcDispatcher.getLoudnessCodecParams(codecInfo);
    }

    @Override // android.media.permission.SafeCloseable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mControllerLock) {
            this.mMediaCodecs.clear();
        }
        this.mLcDispatcher.stopLoudnessCodecUpdates(this.mSessionId);
    }

    int getSessionId() {
        return this.mSessionId;
    }

    void mediaCodecsConsume(java.util.function.Consumer<java.util.Map.Entry<android.media.LoudnessCodecInfo, java.util.Set<android.media.MediaCodec>>> consumer) {
        synchronized (this.mControllerLock) {
            java.util.Iterator<java.util.Map.Entry<android.media.LoudnessCodecInfo, java.util.Set<android.media.MediaCodec>>> it = this.mMediaCodecs.entrySet().iterator();
            while (it.hasNext()) {
                consumer.accept(it.next());
            }
        }
    }

    private static android.media.LoudnessCodecInfo getCodecInfo(android.media.MediaCodec mediaCodec) {
        int i;
        android.media.LoudnessCodecInfo loudnessCodecInfo = new android.media.LoudnessCodecInfo();
        if (mediaCodec.getCodecInfo().isEncoder()) {
            android.util.Log.w(TAG, "MediaCodec used for encoding does not support loudness annotation");
            return null;
        }
        try {
            android.media.MediaFormat inputFormat = mediaCodec.getInputFormat();
            if (android.media.MediaFormat.MIMETYPE_AUDIO_AAC.equalsIgnoreCase(inputFormat.getString(android.media.MediaFormat.KEY_MIME))) {
                int i2 = -1;
                try {
                    i = inputFormat.getInteger(android.media.MediaFormat.KEY_AAC_PROFILE);
                } catch (java.lang.NullPointerException e) {
                    i = -1;
                }
                try {
                    i2 = inputFormat.getInteger(android.media.MediaFormat.KEY_PROFILE);
                } catch (java.lang.NullPointerException e2) {
                }
                boolean z = true;
                if (i == 42 || i2 == 42) {
                    loudnessCodecInfo.metadataType = 2;
                } else {
                    loudnessCodecInfo.metadataType = 1;
                }
                if (mediaCodec.getOutputFormat().getInteger(android.media.MediaFormat.KEY_CHANNEL_COUNT) >= inputFormat.getInteger(android.media.MediaFormat.KEY_CHANNEL_COUNT)) {
                    z = false;
                }
                loudnessCodecInfo.isDownmixing = z;
                return loudnessCodecInfo;
            }
            android.util.Log.w(TAG, "MediaCodec mime type not supported for loudness annotation");
            return null;
        } catch (java.lang.IllegalStateException e3) {
            android.util.Log.e(TAG, "MediaCodec is not configured", e3);
            return null;
        }
    }
}
