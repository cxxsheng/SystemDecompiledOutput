package android.media;

/* loaded from: classes2.dex */
public final class MediaExtractor {
    public static final int SAMPLE_FLAG_ENCRYPTED = 2;
    public static final int SAMPLE_FLAG_PARTIAL_FRAME = 4;
    public static final int SAMPLE_FLAG_SYNC = 1;
    public static final int SEEK_TO_CLOSEST_SYNC = 2;
    public static final int SEEK_TO_NEXT_SYNC = 1;
    public static final int SEEK_TO_PREVIOUS_SYNC = 0;
    private android.media.metrics.LogSessionId mLogSessionId = android.media.metrics.LogSessionId.LOG_SESSION_ID_NONE;
    private android.media.MediaCas mMediaCas;
    private long mNativeContext;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SampleFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SeekMode {
    }

    private native java.util.Map<java.lang.String, java.lang.Object> getFileFormatNative();

    private native java.util.Map<java.lang.String, java.lang.Object> getTrackFormatNative(int i);

    private final native void nativeSetDataSource(android.os.IBinder iBinder, java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2) throws java.io.IOException;

    private final native void nativeSetMediaCas(android.os.IHwBinder iHwBinder);

    private final native void native_finalize();

    private native java.util.List<android.media.AudioPresentation> native_getAudioPresentations(int i);

    private native android.os.PersistableBundle native_getMetrics();

    private static final native void native_init();

    private native void native_setLogSessionId(java.lang.String str);

    private final native void native_setup();

    public native boolean advance();

    public native long getCachedDuration();

    public native boolean getSampleCryptoInfo(android.media.MediaCodec.CryptoInfo cryptoInfo);

    public native int getSampleFlags();

    public native long getSampleSize();

    public native long getSampleTime();

    public native int getSampleTrackIndex();

    public final native int getTrackCount();

    public native boolean hasCacheReachedEndOfStream();

    public native int readSampleData(java.nio.ByteBuffer byteBuffer, int i);

    public final native void release();

    public native void seekTo(long j, int i);

    public native void selectTrack(int i);

    public final native void setDataSource(android.media.MediaDataSource mediaDataSource) throws java.io.IOException;

    public final native void setDataSource(java.io.FileDescriptor fileDescriptor, long j, long j2) throws java.io.IOException;

    public native void unselectTrack(int i);

    public MediaExtractor() {
        native_setup();
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0054, code lost:
    
        if (0 == 0) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005d, code lost:
    
        setDataSource(r9.toString(), r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0064, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005a, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0058, code lost:
    
        if (0 == 0) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setDataSource(android.content.Context context, android.net.Uri uri, java.util.Map<java.lang.String, java.lang.String> map) throws java.io.IOException {
        java.lang.String scheme = uri.getScheme();
        if (scheme == null || scheme.equals("file")) {
            setDataSource(uri.getPath());
            return;
        }
        java.lang.AutoCloseable autoCloseable = null;
        try {
            android.content.res.AssetFileDescriptor openAssetFileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
            if (openAssetFileDescriptor == null) {
                if (openAssetFileDescriptor != null) {
                    openAssetFileDescriptor.close();
                }
            } else {
                if (openAssetFileDescriptor.getDeclaredLength() < 0) {
                    setDataSource(openAssetFileDescriptor.getFileDescriptor());
                } else {
                    setDataSource(openAssetFileDescriptor.getFileDescriptor(), openAssetFileDescriptor.getStartOffset(), openAssetFileDescriptor.getDeclaredLength());
                }
                if (openAssetFileDescriptor != null) {
                    openAssetFileDescriptor.close();
                }
            }
        } catch (java.io.IOException e) {
        } catch (java.lang.SecurityException e2) {
        } catch (java.lang.Throwable th) {
            if (0 != 0) {
                autoCloseable.close();
            }
            throw th;
        }
    }

    public final void setDataSource(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map) throws java.io.IOException {
        java.lang.String[] strArr;
        java.lang.String[] strArr2;
        if (map == null) {
            strArr = null;
            strArr2 = null;
        } else {
            strArr = new java.lang.String[map.size()];
            strArr2 = new java.lang.String[map.size()];
            int i = 0;
            for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : map.entrySet()) {
                strArr[i] = entry.getKey();
                strArr2[i] = entry.getValue();
                i++;
            }
        }
        nativeSetDataSource(android.media.MediaHTTPService.createHttpServiceBinderIfNecessary(str), str, strArr, strArr2);
    }

    public final void setDataSource(java.lang.String str) throws java.io.IOException {
        nativeSetDataSource(android.media.MediaHTTPService.createHttpServiceBinderIfNecessary(str), str, null, null);
    }

    public final void setDataSource(android.content.res.AssetFileDescriptor assetFileDescriptor) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        com.android.internal.util.Preconditions.checkNotNull(assetFileDescriptor);
        if (assetFileDescriptor.getDeclaredLength() < 0) {
            setDataSource(assetFileDescriptor.getFileDescriptor());
        } else {
            setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getDeclaredLength());
        }
    }

    public final void setDataSource(java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        setDataSource(fileDescriptor, 0L, 576460752303423487L);
    }

    @java.lang.Deprecated
    public final void setMediaCas(android.media.MediaCas mediaCas) {
        this.mMediaCas = mediaCas;
        nativeSetMediaCas(mediaCas.getBinder());
    }

    public static final class CasInfo {
        private final byte[] mPrivateData;
        private final android.media.MediaCas.Session mSession;
        private final int mSystemId;

        CasInfo(int i, android.media.MediaCas.Session session, byte[] bArr) {
            this.mSystemId = i;
            this.mSession = session;
            this.mPrivateData = bArr;
        }

        public int getSystemId() {
            return this.mSystemId;
        }

        public byte[] getPrivateData() {
            return this.mPrivateData;
        }

        public android.media.MediaCas.Session getSession() {
            return this.mSession;
        }
    }

    public android.media.MediaExtractor.CasInfo getCasInfo(int i) {
        byte[] bArr;
        java.util.Map<java.lang.String, java.lang.Object> trackFormatNative = getTrackFormatNative(i);
        android.media.MediaCas.Session session = null;
        if (!trackFormatNative.containsKey(android.media.MediaFormat.KEY_CA_SYSTEM_ID)) {
            return null;
        }
        int intValue = ((java.lang.Integer) trackFormatNative.get(android.media.MediaFormat.KEY_CA_SYSTEM_ID)).intValue();
        if (!trackFormatNative.containsKey(android.media.MediaFormat.KEY_CA_PRIVATE_DATA)) {
            bArr = null;
        } else {
            java.nio.ByteBuffer byteBuffer = (java.nio.ByteBuffer) trackFormatNative.get(android.media.MediaFormat.KEY_CA_PRIVATE_DATA);
            byteBuffer.rewind();
            bArr = new byte[byteBuffer.remaining()];
            byteBuffer.get(bArr);
        }
        if (this.mMediaCas != null && trackFormatNative.containsKey(android.media.MediaFormat.KEY_CA_SESSION_ID)) {
            java.nio.ByteBuffer byteBuffer2 = (java.nio.ByteBuffer) trackFormatNative.get(android.media.MediaFormat.KEY_CA_SESSION_ID);
            byteBuffer2.rewind();
            byte[] bArr2 = new byte[byteBuffer2.remaining()];
            byteBuffer2.get(bArr2);
            session = this.mMediaCas.createFromSessionId(bArr2);
        }
        return new android.media.MediaExtractor.CasInfo(intValue, session, bArr);
    }

    protected void finalize() {
        native_finalize();
    }

    public android.media.DrmInitData getDrmInitData() {
        java.util.Map<java.lang.String, java.lang.Object> fileFormatNative = getFileFormatNative();
        if (fileFormatNative == null) {
            return null;
        }
        if (fileFormatNative.containsKey("pssh")) {
            final android.media.DrmInitData.SchemeInitData[] schemeInitDataArr = (android.media.DrmInitData.SchemeInitData[]) getPsshInfo().entrySet().stream().map(new java.util.function.Function() { // from class: android.media.MediaExtractor$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.media.MediaExtractor.lambda$getDrmInitData$0((java.util.Map.Entry) obj);
                }
            }).toArray(new java.util.function.IntFunction() { // from class: android.media.MediaExtractor$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i) {
                    return android.media.MediaExtractor.lambda$getDrmInitData$1(i);
                }
            });
            final java.util.Map map = (java.util.Map) java.util.Arrays.stream(schemeInitDataArr).collect(java.util.stream.Collectors.toMap(new java.util.function.Function() { // from class: android.media.MediaExtractor$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.util.UUID uuid;
                    uuid = ((android.media.DrmInitData.SchemeInitData) obj).uuid;
                    return uuid;
                }
            }, new java.util.function.Function() { // from class: android.media.MediaExtractor$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.media.MediaExtractor.lambda$getDrmInitData$3((android.media.DrmInitData.SchemeInitData) obj);
                }
            }));
            return new android.media.DrmInitData() { // from class: android.media.MediaExtractor.1
                @Override // android.media.DrmInitData
                public android.media.DrmInitData.SchemeInitData get(java.util.UUID uuid) {
                    return (android.media.DrmInitData.SchemeInitData) map.get(uuid);
                }

                @Override // android.media.DrmInitData
                public int getSchemeInitDataCount() {
                    return schemeInitDataArr.length;
                }

                @Override // android.media.DrmInitData
                public android.media.DrmInitData.SchemeInitData getSchemeInitDataAt(int i) {
                    return schemeInitDataArr[i];
                }
            };
        }
        int trackCount = getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            java.util.Map<java.lang.String, java.lang.Object> trackFormatNative = getTrackFormatNative(i);
            if (trackFormatNative.containsKey("crypto-key")) {
                java.nio.ByteBuffer byteBuffer = (java.nio.ByteBuffer) trackFormatNative.get("crypto-key");
                byteBuffer.rewind();
                byte[] bArr = new byte[byteBuffer.remaining()];
                byteBuffer.get(bArr);
                final android.media.DrmInitData.SchemeInitData schemeInitData = new android.media.DrmInitData.SchemeInitData(android.media.DrmInitData.SchemeInitData.UUID_NIL, "webm", bArr);
                return new android.media.DrmInitData() { // from class: android.media.MediaExtractor.2
                    @Override // android.media.DrmInitData
                    public android.media.DrmInitData.SchemeInitData get(java.util.UUID uuid) {
                        return schemeInitData;
                    }

                    @Override // android.media.DrmInitData
                    public int getSchemeInitDataCount() {
                        return 1;
                    }

                    @Override // android.media.DrmInitData
                    public android.media.DrmInitData.SchemeInitData getSchemeInitDataAt(int i2) {
                        return schemeInitData;
                    }
                };
            }
        }
        return null;
    }

    static /* synthetic */ android.media.DrmInitData.SchemeInitData lambda$getDrmInitData$0(java.util.Map.Entry entry) {
        return new android.media.DrmInitData.SchemeInitData((java.util.UUID) entry.getKey(), "cenc", (byte[]) entry.getValue());
    }

    static /* synthetic */ android.media.DrmInitData.SchemeInitData[] lambda$getDrmInitData$1(int i) {
        return new android.media.DrmInitData.SchemeInitData[i];
    }

    static /* synthetic */ android.media.DrmInitData.SchemeInitData lambda$getDrmInitData$3(android.media.DrmInitData.SchemeInitData schemeInitData) {
        return schemeInitData;
    }

    public java.util.List<android.media.AudioPresentation> getAudioPresentations(int i) {
        return native_getAudioPresentations(i);
    }

    public java.util.Map<java.util.UUID, byte[]> getPsshInfo() {
        java.util.Map<java.lang.String, java.lang.Object> fileFormatNative = getFileFormatNative();
        if (fileFormatNative != null && fileFormatNative.containsKey("pssh")) {
            java.nio.ByteBuffer byteBuffer = (java.nio.ByteBuffer) fileFormatNative.get("pssh");
            byteBuffer.order(java.nio.ByteOrder.nativeOrder());
            byteBuffer.rewind();
            fileFormatNative.remove("pssh");
            java.util.HashMap hashMap = new java.util.HashMap();
            while (byteBuffer.remaining() > 0) {
                byteBuffer.order(java.nio.ByteOrder.BIG_ENDIAN);
                java.util.UUID uuid = new java.util.UUID(byteBuffer.getLong(), byteBuffer.getLong());
                byteBuffer.order(java.nio.ByteOrder.nativeOrder());
                byte[] bArr = new byte[byteBuffer.getInt()];
                byteBuffer.get(bArr);
                hashMap.put(uuid, bArr);
            }
            return hashMap;
        }
        return null;
    }

    public android.media.MediaFormat getTrackFormat(int i) {
        return new android.media.MediaFormat(getTrackFormatNative(i));
    }

    public void setLogSessionId(android.media.metrics.LogSessionId logSessionId) {
        this.mLogSessionId = (android.media.metrics.LogSessionId) java.util.Objects.requireNonNull(logSessionId);
        native_setLogSessionId(logSessionId.getStringId());
    }

    public android.media.metrics.LogSessionId getLogSessionId() {
        return this.mLogSessionId;
    }

    public android.os.PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }

    public static final class MetricsConstants {
        public static final java.lang.String FORMAT = "android.media.mediaextractor.fmt";
        public static final java.lang.String MIME_TYPE = "android.media.mediaextractor.mime";
        public static final java.lang.String TRACKS = "android.media.mediaextractor.ntrk";

        private MetricsConstants() {
        }
    }
}
