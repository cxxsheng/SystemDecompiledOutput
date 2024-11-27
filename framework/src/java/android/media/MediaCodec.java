package android.media;

/* loaded from: classes2.dex */
public final class MediaCodec {
    public static final int BUFFER_FLAG_CODEC_CONFIG = 2;
    public static final int BUFFER_FLAG_DECODE_ONLY = 32;
    public static final int BUFFER_FLAG_END_OF_STREAM = 4;
    public static final int BUFFER_FLAG_KEY_FRAME = 1;
    public static final int BUFFER_FLAG_MUXER_DATA = 16;
    public static final int BUFFER_FLAG_PARTIAL_FRAME = 8;
    public static final int BUFFER_FLAG_SYNC_FRAME = 1;
    private static final int BUFFER_MODE_BLOCK = 1;
    private static final int BUFFER_MODE_INVALID = -1;
    private static final int BUFFER_MODE_LEGACY = 0;
    private static final int CB_CRYPTO_ERROR = 6;
    private static final int CB_ERROR = 3;
    private static final int CB_INPUT_AVAILABLE = 1;
    private static final int CB_LARGE_FRAME_OUTPUT_AVAILABLE = 7;
    private static final int CB_OUTPUT_AVAILABLE = 2;
    private static final int CB_OUTPUT_FORMAT_CHANGE = 4;
    public static final int CONFIGURE_FLAG_DETACHED_SURFACE = 8;
    public static final int CONFIGURE_FLAG_ENCODE = 1;
    public static final int CONFIGURE_FLAG_USE_BLOCK_MODEL = 2;
    public static final int CONFIGURE_FLAG_USE_CRYPTO_ASYNC = 4;
    public static final int CRYPTO_MODE_AES_CBC = 2;
    public static final int CRYPTO_MODE_AES_CTR = 1;
    public static final int CRYPTO_MODE_UNENCRYPTED = 0;
    private static final java.lang.String EOS_AND_DECODE_ONLY_ERROR_MESSAGE = "An input buffer cannot have both BUFFER_FLAG_END_OF_STREAM and BUFFER_FLAG_DECODE_ONLY flags";
    private static final int EVENT_CALLBACK = 1;
    private static final int EVENT_FIRST_TUNNEL_FRAME_READY = 4;
    private static final int EVENT_FRAME_RENDERED = 3;
    private static final int EVENT_SET_CALLBACK = 2;
    public static final int INFO_OUTPUT_BUFFERS_CHANGED = -3;
    public static final int INFO_OUTPUT_FORMAT_CHANGED = -2;
    public static final int INFO_TRY_AGAIN_LATER = -1;
    public static final java.lang.String PARAMETER_KEY_HDR10_PLUS_INFO = "hdr10-plus-info";
    public static final java.lang.String PARAMETER_KEY_LOW_LATENCY = "low-latency";
    public static final java.lang.String PARAMETER_KEY_OFFSET_TIME = "time-offset-us";
    public static final java.lang.String PARAMETER_KEY_QP_OFFSET_MAP = "qp-offset-map";
    public static final java.lang.String PARAMETER_KEY_QP_OFFSET_RECTS = "qp-offset-rects";
    public static final java.lang.String PARAMETER_KEY_REQUEST_SYNC_FRAME = "request-sync";
    public static final java.lang.String PARAMETER_KEY_SUSPEND = "drop-input-frames";
    public static final java.lang.String PARAMETER_KEY_SUSPEND_TIME = "drop-start-time-us";
    public static final java.lang.String PARAMETER_KEY_TUNNEL_PEEK = "tunnel-peek";
    public static final java.lang.String PARAMETER_KEY_VIDEO_BITRATE = "video-bitrate";
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    private final java.lang.Object mBufferLock;
    private int mBufferMode;
    private java.nio.ByteBuffer[] mCachedInputBuffers;
    private java.nio.ByteBuffer[] mCachedOutputBuffers;
    private android.media.MediaCodec.Callback mCallback;
    private android.media.MediaCodec.EventHandler mCallbackHandler;
    private android.media.MediaCodecInfo mCodecInfo;
    private final java.lang.Object mCodecInfoLock;
    private android.media.MediaCrypto mCrypto;
    private final android.media.MediaCodec.BufferMap mDequeuedInputBuffers;
    private final android.media.MediaCodec.BufferMap mDequeuedOutputBuffers;
    private final java.util.Map<java.lang.Integer, android.media.MediaCodec.BufferInfo> mDequeuedOutputInfos;
    private android.media.MediaCodec.EventHandler mEventHandler;
    private boolean mHasSurface;
    private final java.lang.Object mListenerLock;
    private java.lang.String mNameAtCreation;
    private long mNativeContext;
    private final java.util.concurrent.locks.Lock mNativeContextLock;
    private android.media.MediaCodec.EventHandler mOnFirstTunnelFrameReadyHandler;
    private android.media.MediaCodec.OnFirstTunnelFrameReadyListener mOnFirstTunnelFrameReadyListener;
    private android.media.MediaCodec.EventHandler mOnFrameRenderedHandler;
    private android.media.MediaCodec.OnFrameRenderedListener mOnFrameRenderedListener;
    private final java.util.ArrayList<android.media.MediaCodec.OutputFrame> mOutputFrames;
    private final java.util.ArrayList<android.media.MediaCodec.QueueRequest> mQueueRequests;
    private java.util.BitSet mValidInputIndices;
    private java.util.BitSet mValidOutputIndices;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BufferFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConfigureFlag {
    }

    public interface OnFirstTunnelFrameReadyListener {
        void onFirstTunnelFrameReady(android.media.MediaCodec mediaCodec);
    }

    public interface OnFrameRenderedListener {
        void onFrameRendered(android.media.MediaCodec mediaCodec, long j, long j2);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OutputBufferInfo {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VideoScalingMode {
    }

    private final native java.nio.ByteBuffer getBuffer(boolean z, int i);

    private final native java.nio.ByteBuffer[] getBuffers(boolean z);

    private final native java.util.Map<java.lang.String, java.lang.Object> getFormatNative(boolean z);

    private final native android.media.Image getImage(boolean z, int i);

    private final native java.util.Map<java.lang.String, java.lang.Object> getOutputFormatNative(int i);

    private final native android.media.MediaCodecInfo getOwnCodecInfo();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_closeMediaImage(long j);

    private final native void native_configure(java.lang.String[] strArr, java.lang.Object[] objArr, android.view.Surface surface, android.media.MediaCrypto mediaCrypto, android.os.IHwBinder iHwBinder, int i);

    private static final native android.media.MediaCodec.PersistentSurface native_createPersistentInputSurface();

    private final native int native_dequeueInputBuffer(long j);

    private final native int native_dequeueOutputBuffer(android.media.MediaCodec.BufferInfo bufferInfo, long j);

    private native void native_enableOnFirstTunnelFrameReadyListener(boolean z);

    private native void native_enableOnFrameRenderedListener(boolean z);

    private final native void native_finalize();

    private final native void native_flush();

    private native android.os.PersistableBundle native_getMetrics();

    private native void native_getOutputFrame(android.media.MediaCodec.OutputFrame outputFrame, int i);

    private native android.media.MediaCodec.ParameterDescriptor native_getParameterDescriptor(java.lang.String str);

    private native java.util.List<java.lang.String> native_getSupportedVendorParameters();

    private static final native void native_init();

    private static native android.media.Image native_mapHardwareBuffer(android.hardware.HardwareBuffer hardwareBuffer);

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_queueHardwareBuffer(int i, android.hardware.HardwareBuffer hardwareBuffer, long j, int i2, java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<java.lang.Object> arrayList2);

    private final native void native_queueInputBuffer(int i, int i2, int i3, long j, int i4) throws android.media.MediaCodec.CryptoException;

    private final native void native_queueInputBuffers(int i, java.lang.Object[] objArr) throws android.media.MediaCodec.CryptoException, android.media.MediaCodec.CodecException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_queueLinearBlock(int i, android.media.MediaCodec.LinearBlock linearBlock, java.lang.Object[] objArr, java.lang.Object[] objArr2, java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<java.lang.Object> arrayList2);

    private final native void native_queueSecureInputBuffer(int i, int i2, android.media.MediaCodec.CryptoInfo cryptoInfo, long j, int i3) throws android.media.MediaCodec.CryptoException;

    private final native void native_queueSecureInputBuffers(int i, java.lang.Object[] objArr, java.lang.Object[] objArr2) throws android.media.MediaCodec.CryptoException, android.media.MediaCodec.CodecException;

    private final native void native_release();

    /* JADX INFO: Access modifiers changed from: private */
    public static final native void native_releasePersistentInputSurface(android.view.Surface surface);

    private final native void native_reset();

    private native void native_setAudioPresentation(int i, int i2);

    private final native void native_setCallback(android.media.MediaCodec.Callback callback);

    private final native void native_setInputSurface(android.view.Surface surface);

    private native void native_setSurface(android.view.Surface surface);

    private final native void native_setup(java.lang.String str, boolean z, boolean z2, int i, int i2);

    private final native void native_start();

    private final native void native_stop();

    private native void native_subscribeToVendorParameters(java.util.List<java.lang.String> list);

    private native void native_unsubscribeFromVendorParameters(java.util.List<java.lang.String> list);

    private final native void releaseOutputBuffer(int i, boolean z, boolean z2, long j);

    private final native void setParameters(java.lang.String[] strArr, java.lang.Object[] objArr);

    public final native android.view.Surface createInputSurface();

    public final native java.lang.String getCanonicalName();

    public final native void setVideoScalingMode(int i);

    public final native void signalEndOfInputStream();

    public static final class BufferInfo {
        public int flags;
        public int offset;
        public long presentationTimeUs;
        public int size;

        public void set(int i, int i2, long j, int i3) {
            this.offset = i;
            this.size = i2;
            this.presentationTimeUs = j;
            this.flags = i3;
        }

        public android.media.MediaCodec.BufferInfo dup() {
            android.media.MediaCodec.BufferInfo bufferInfo = new android.media.MediaCodec.BufferInfo();
            bufferInfo.set(this.offset, this.size, this.presentationTimeUs, this.flags);
            return bufferInfo;
        }
    }

    private class EventHandler extends android.os.Handler {
        private android.media.MediaCodec mCodec;

        public EventHandler(android.media.MediaCodec mediaCodec, android.os.Looper looper) {
            super(looper);
            this.mCodec = mediaCodec;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.media.MediaCodec.OnFrameRenderedListener onFrameRenderedListener;
            android.media.MediaCodec.OnFirstTunnelFrameReadyListener onFirstTunnelFrameReadyListener;
            switch (message.what) {
                case 1:
                    handleCallback(message);
                    return;
                case 2:
                    android.media.MediaCodec.this.mCallback = (android.media.MediaCodec.Callback) message.obj;
                    return;
                case 3:
                    java.util.Map map = (java.util.Map) message.obj;
                    int i = 0;
                    while (true) {
                        java.lang.Object obj = map.get(i + "-media-time-us");
                        java.lang.Object obj2 = map.get(i + "-system-nano");
                        synchronized (android.media.MediaCodec.this.mListenerLock) {
                            onFrameRenderedListener = android.media.MediaCodec.this.mOnFrameRenderedListener;
                        }
                        if (obj != null && obj2 != null && onFrameRenderedListener != null) {
                            onFrameRenderedListener.onFrameRendered(this.mCodec, ((java.lang.Long) obj).longValue(), ((java.lang.Long) obj2).longValue());
                            i++;
                        } else {
                            return;
                        }
                    }
                    break;
                case 4:
                    synchronized (android.media.MediaCodec.this.mListenerLock) {
                        onFirstTunnelFrameReadyListener = android.media.MediaCodec.this.mOnFirstTunnelFrameReadyListener;
                    }
                    if (onFirstTunnelFrameReadyListener != null) {
                        onFirstTunnelFrameReadyListener.onFirstTunnelFrameReady(this.mCodec);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void handleCallback(android.os.Message message) {
            if (android.media.MediaCodec.this.mCallback == null) {
                return;
            }
            switch (message.arg1) {
                case 1:
                    int i = message.arg2;
                    synchronized (android.media.MediaCodec.this.mBufferLock) {
                        switch (android.media.MediaCodec.this.mBufferMode) {
                            case 0:
                                android.media.MediaCodec.this.validateInputByteBufferLocked(android.media.MediaCodec.this.mCachedInputBuffers, i);
                                break;
                            case 1:
                                while (android.media.MediaCodec.this.mQueueRequests.size() <= i) {
                                    android.media.MediaCodec.this.mQueueRequests.add(null);
                                }
                                android.media.MediaCodec.QueueRequest queueRequest = (android.media.MediaCodec.QueueRequest) android.media.MediaCodec.this.mQueueRequests.get(i);
                                if (queueRequest == null) {
                                    queueRequest = new android.media.MediaCodec.QueueRequest(this.mCodec, i);
                                    android.media.MediaCodec.this.mQueueRequests.set(i, queueRequest);
                                }
                                queueRequest.setAccessible(true);
                                break;
                            default:
                                throw new java.lang.IllegalStateException("Unrecognized buffer mode: " + android.media.MediaCodec.this.mBufferMode);
                        }
                    }
                    android.media.MediaCodec.this.mCallback.onInputBufferAvailable(this.mCodec, i);
                    return;
                case 2:
                    int i2 = message.arg2;
                    android.media.MediaCodec.BufferInfo bufferInfo = (android.media.MediaCodec.BufferInfo) message.obj;
                    synchronized (android.media.MediaCodec.this.mBufferLock) {
                        switch (android.media.MediaCodec.this.mBufferMode) {
                            case 0:
                                android.media.MediaCodec.this.validateOutputByteBufferLocked(android.media.MediaCodec.this.mCachedOutputBuffers, i2, bufferInfo);
                                break;
                            case 1:
                                while (android.media.MediaCodec.this.mOutputFrames.size() <= i2) {
                                    android.media.MediaCodec.this.mOutputFrames.add(null);
                                }
                                android.media.MediaCodec.OutputFrame outputFrame = (android.media.MediaCodec.OutputFrame) android.media.MediaCodec.this.mOutputFrames.get(i2);
                                if (outputFrame == null) {
                                    outputFrame = new android.media.MediaCodec.OutputFrame(i2);
                                    android.media.MediaCodec.this.mOutputFrames.set(i2, outputFrame);
                                }
                                outputFrame.setBufferInfo(bufferInfo);
                                outputFrame.setAccessible(true);
                                break;
                            default:
                                throw new java.lang.IllegalStateException("Unrecognized buffer mode: " + android.media.MediaCodec.this.mBufferMode);
                        }
                    }
                    android.media.MediaCodec.this.mCallback.onOutputBufferAvailable(this.mCodec, i2, bufferInfo);
                    return;
                case 3:
                    android.media.MediaCodec.this.mCallback.onError(this.mCodec, (android.media.MediaCodec.CodecException) message.obj);
                    return;
                case 4:
                    android.media.MediaCodec.this.mCallback.onOutputFormatChanged(this.mCodec, new android.media.MediaFormat((java.util.Map<java.lang.String, java.lang.Object>) message.obj));
                    return;
                case 5:
                default:
                    return;
                case 6:
                    android.media.MediaCodec.this.mCallback.onCryptoError(this.mCodec, (android.media.MediaCodec.CryptoException) message.obj);
                    return;
                case 7:
                    int i3 = message.arg2;
                    java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> arrayDeque = (java.util.ArrayDeque) message.obj;
                    synchronized (android.media.MediaCodec.this.mBufferLock) {
                        switch (android.media.MediaCodec.this.mBufferMode) {
                            case 0:
                                android.media.MediaCodec.this.validateOutputByteBuffersLocked(android.media.MediaCodec.this.mCachedOutputBuffers, i3, arrayDeque);
                                break;
                            case 1:
                                while (android.media.MediaCodec.this.mOutputFrames.size() <= i3) {
                                    android.media.MediaCodec.this.mOutputFrames.add(null);
                                }
                                android.media.MediaCodec.OutputFrame outputFrame2 = (android.media.MediaCodec.OutputFrame) android.media.MediaCodec.this.mOutputFrames.get(i3);
                                if (outputFrame2 == null) {
                                    outputFrame2 = new android.media.MediaCodec.OutputFrame(i3);
                                    android.media.MediaCodec.this.mOutputFrames.set(i3, outputFrame2);
                                }
                                outputFrame2.setBufferInfos(arrayDeque);
                                outputFrame2.setAccessible(true);
                                break;
                            default:
                                throw new java.lang.IllegalArgumentException("Unrecognized buffer mode: for large frame output");
                        }
                    }
                    android.media.MediaCodec.this.mCallback.onOutputBuffersAvailable(this.mCodec, i3, arrayDeque);
                    return;
            }
        }
    }

    public static android.media.MediaCodec createDecoderByType(java.lang.String str) throws java.io.IOException {
        return new android.media.MediaCodec(str, true, false);
    }

    public static android.media.MediaCodec createEncoderByType(java.lang.String str) throws java.io.IOException {
        return new android.media.MediaCodec(str, true, true);
    }

    public static android.media.MediaCodec createByCodecName(java.lang.String str) throws java.io.IOException {
        return new android.media.MediaCodec(str, false, false);
    }

    @android.annotation.SystemApi
    public static android.media.MediaCodec createByCodecNameForClient(java.lang.String str, int i, int i2) throws java.io.IOException {
        return new android.media.MediaCodec(str, false, false, i, i2);
    }

    private MediaCodec(java.lang.String str, boolean z, boolean z2) {
        this(str, z, z2, -1, -1);
    }

    private MediaCodec(java.lang.String str, boolean z, boolean z2, int i, int i2) {
        this.mListenerLock = new java.lang.Object();
        this.mCodecInfoLock = new java.lang.Object();
        this.mHasSurface = false;
        this.mBufferMode = -1;
        this.mQueueRequests = new java.util.ArrayList<>();
        this.mValidInputIndices = new java.util.BitSet();
        this.mValidOutputIndices = new java.util.BitSet();
        this.mDequeuedInputBuffers = new android.media.MediaCodec.BufferMap();
        this.mDequeuedOutputBuffers = new android.media.MediaCodec.BufferMap();
        this.mDequeuedOutputInfos = new java.util.HashMap();
        this.mOutputFrames = new java.util.ArrayList<>();
        this.mNativeContext = 0L;
        this.mNativeContextLock = new java.util.concurrent.locks.ReentrantLock();
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null) {
            this.mEventHandler = new android.media.MediaCodec.EventHandler(this, myLooper);
        } else {
            android.os.Looper mainLooper = android.os.Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new android.media.MediaCodec.EventHandler(this, mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        this.mCallbackHandler = this.mEventHandler;
        this.mOnFirstTunnelFrameReadyHandler = this.mEventHandler;
        this.mOnFrameRenderedHandler = this.mEventHandler;
        this.mBufferLock = new java.lang.Object();
        this.mNameAtCreation = z ? null : str;
        native_setup(str, z, z2, i, i2);
    }

    protected void finalize() {
        native_finalize();
        this.mCrypto = null;
    }

    public final void reset() {
        freeAllTrackedBuffers();
        native_reset();
        this.mCrypto = null;
    }

    public final void release() {
        freeAllTrackedBuffers();
        native_release();
        this.mCrypto = null;
    }

    public class IncompatibleWithBlockModelException extends java.lang.RuntimeException {
        IncompatibleWithBlockModelException() {
        }

        IncompatibleWithBlockModelException(java.lang.String str) {
            super(str);
        }

        IncompatibleWithBlockModelException(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
        }

        IncompatibleWithBlockModelException(java.lang.Throwable th) {
            super(th);
        }
    }

    public class InvalidBufferFlagsException extends java.lang.RuntimeException {
        InvalidBufferFlagsException(java.lang.String str) {
            super(str);
        }
    }

    public void configure(android.media.MediaFormat mediaFormat, android.view.Surface surface, android.media.MediaCrypto mediaCrypto, int i) {
        configure(mediaFormat, surface, mediaCrypto, null, i);
    }

    public void configure(android.media.MediaFormat mediaFormat, android.view.Surface surface, int i, android.media.MediaDescrambler mediaDescrambler) {
        configure(mediaFormat, surface, null, mediaDescrambler != null ? mediaDescrambler.getBinder() : null, i);
    }

    private void configure(android.media.MediaFormat mediaFormat, android.view.Surface surface, android.media.MediaCrypto mediaCrypto, android.os.IHwBinder iHwBinder, int i) {
        java.lang.String[] strArr;
        java.lang.Object[] objArr;
        boolean z;
        if (mediaCrypto != null && iHwBinder != null) {
            throw new java.lang.IllegalArgumentException("Can't use crypto and descrambler together!");
        }
        if (mediaFormat == null) {
            strArr = null;
            objArr = null;
        } else {
            java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat.getMap();
            java.lang.String[] strArr2 = new java.lang.String[map.size()];
            java.lang.Object[] objArr2 = new java.lang.Object[map.size()];
            int i2 = 0;
            for (java.util.Map.Entry<java.lang.String, java.lang.Object> entry : map.entrySet()) {
                if (entry.getKey().equals(android.media.MediaFormat.KEY_AUDIO_SESSION_ID)) {
                    try {
                        int intValue = ((java.lang.Integer) entry.getValue()).intValue();
                        strArr2[i2] = android.media.MediaFormat.KEY_AUDIO_HW_SYNC;
                        objArr2[i2] = java.lang.Integer.valueOf(android.media.AudioSystem.getAudioHwSyncForSession(intValue));
                    } catch (java.lang.Exception e) {
                        throw new java.lang.IllegalArgumentException("Wrong Session ID Parameter!");
                    }
                } else {
                    strArr2[i2] = entry.getKey();
                    objArr2[i2] = entry.getValue();
                }
                i2++;
            }
            strArr = strArr2;
            objArr = objArr2;
        }
        if (surface == null) {
            z = false;
        } else {
            z = true;
        }
        this.mHasSurface = z;
        this.mCrypto = mediaCrypto;
        synchronized (this.mBufferLock) {
            if ((i & 2) != 0) {
                this.mBufferMode = 1;
            } else {
                this.mBufferMode = 0;
            }
        }
        native_configure(strArr, objArr, surface, mediaCrypto, iHwBinder, i);
    }

    public void setOutputSurface(android.view.Surface surface) {
        if (!this.mHasSurface) {
            throw new java.lang.IllegalStateException("codec was not configured for an output surface");
        }
        native_setSurface(surface);
    }

    public void detachOutputSurface() {
        if (!this.mHasSurface) {
            throw new java.lang.IllegalStateException("codec was not configured for an output surface");
        }
    }

    public static android.view.Surface createPersistentInputSurface() {
        return native_createPersistentInputSurface();
    }

    static class PersistentSurface extends android.view.Surface {
        private long mPersistentObject;

        PersistentSurface() {
        }

        @Override // android.view.Surface
        public void release() {
            android.media.MediaCodec.native_releasePersistentInputSurface(this);
            super.release();
        }
    }

    public void setInputSurface(android.view.Surface surface) {
        if (!(surface instanceof android.media.MediaCodec.PersistentSurface)) {
            throw new java.lang.IllegalArgumentException("not a PersistentSurface");
        }
        native_setInputSurface(surface);
    }

    public final void start() {
        native_start();
    }

    public final void stop() {
        native_stop();
        freeAllTrackedBuffers();
        synchronized (this.mListenerLock) {
            if (this.mCallbackHandler != null) {
                this.mCallbackHandler.removeMessages(2);
                this.mCallbackHandler.removeMessages(1);
            }
            if (this.mOnFirstTunnelFrameReadyHandler != null) {
                this.mOnFirstTunnelFrameReadyHandler.removeMessages(4);
            }
            if (this.mOnFrameRenderedHandler != null) {
                this.mOnFrameRenderedHandler.removeMessages(3);
            }
        }
    }

    public final void flush() {
        synchronized (this.mBufferLock) {
            invalidateByteBuffersLocked(this.mCachedInputBuffers);
            invalidateByteBuffersLocked(this.mCachedOutputBuffers);
            this.mValidInputIndices.clear();
            this.mValidOutputIndices.clear();
            this.mDequeuedInputBuffers.clear();
            this.mDequeuedOutputBuffers.clear();
        }
        native_flush();
    }

    public static final class CodecException extends java.lang.IllegalStateException {
        private static final int ACTION_RECOVERABLE = 2;
        private static final int ACTION_TRANSIENT = 1;
        public static final int ERROR_INSUFFICIENT_RESOURCE = 1100;
        public static final int ERROR_RECLAIMED = 1101;
        private final int mActionCode;
        private final java.lang.String mDiagnosticInfo;
        private final int mErrorCode;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ReasonCode {
        }

        CodecException(int i, int i2, java.lang.String str) {
            super(str);
            this.mErrorCode = i;
            this.mActionCode = i2;
            this.mDiagnosticInfo = "android.media.MediaCodec.error_" + (i < 0 ? "neg_" : "") + java.lang.Math.abs(i);
        }

        public boolean isTransient() {
            return this.mActionCode == 1;
        }

        public boolean isRecoverable() {
            return this.mActionCode == 2;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public java.lang.String getDiagnosticInfo() {
            return this.mDiagnosticInfo;
        }
    }

    public static final class CryptoException extends java.lang.RuntimeException implements android.media.MediaDrmThrowable {
        public static final int ERROR_FRAME_TOO_LARGE = 8;
        public static final int ERROR_INSUFFICIENT_OUTPUT_PROTECTION = 4;
        public static final int ERROR_INSUFFICIENT_SECURITY = 7;
        public static final int ERROR_KEY_EXPIRED = 2;
        public static final int ERROR_LOST_STATE = 9;
        public static final int ERROR_NO_KEY = 1;
        public static final int ERROR_RESOURCE_BUSY = 3;
        public static final int ERROR_SESSION_NOT_OPENED = 5;
        public static final int ERROR_UNSUPPORTED_OPERATION = 6;
        private android.media.MediaCodec.CryptoInfo mCryptoInfo;
        private final int mErrorCode;
        private final int mErrorContext;
        private final int mOemError;
        private final int mVendorError;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface CryptoErrorCode {
        }

        public CryptoException(int i, java.lang.String str) {
            this(str, i, 0, 0, 0, null);
        }

        public CryptoException(java.lang.String str, int i, int i2, int i3, int i4, android.media.MediaCodec.CryptoInfo cryptoInfo) {
            super(str);
            this.mErrorCode = i;
            this.mVendorError = i2;
            this.mOemError = i3;
            this.mErrorContext = i4;
            this.mCryptoInfo = cryptoInfo;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public android.media.MediaCodec.CryptoInfo getCryptoInfo() {
            return this.mCryptoInfo;
        }

        @Override // android.media.MediaDrmThrowable
        public int getVendorError() {
            return this.mVendorError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getOemError() {
            return this.mOemError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getErrorContext() {
            return this.mErrorContext;
        }
    }

    public final void queueInputBuffer(int i, int i2, int i3, long j, int i4) throws android.media.MediaCodec.CryptoException {
        if ((i4 & 32) != 0 && (i4 & 4) != 0) {
            throw new android.media.MediaCodec.InvalidBufferFlagsException(EOS_AND_DECODE_ONLY_ERROR_MESSAGE);
        }
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("queueInputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getQueueRequest() to queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.remove(i);
        }
        try {
            native_queueInputBuffer(i, i2, i3, j, i4);
        } catch (android.media.MediaCodec.CryptoException | java.lang.IllegalStateException e) {
            revalidateByteBuffer(this.mCachedInputBuffers, i, true);
            throw e;
        }
    }

    public final void queueInputBuffers(int i, java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> arrayDeque) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("queueInputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getQueueRequest() to queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.remove(i);
        }
        try {
            native_queueInputBuffers(i, arrayDeque.toArray());
        } catch (android.media.MediaCodec.CryptoException | java.lang.IllegalArgumentException | java.lang.IllegalStateException e) {
            revalidateByteBuffer(this.mCachedInputBuffers, i, true);
            throw e;
        }
    }

    public static final class CryptoInfo {
        private static final android.media.MediaCodec.CryptoInfo.Pattern ZERO_PATTERN = new android.media.MediaCodec.CryptoInfo.Pattern(0, 0);
        public byte[] iv;
        public byte[] key;
        private android.media.MediaCodec.CryptoInfo.Pattern mPattern = ZERO_PATTERN;
        public int mode;
        public int[] numBytesOfClearData;
        public int[] numBytesOfEncryptedData;
        public int numSubSamples;

        public static final class Pattern {
            private int mEncryptBlocks;
            private int mSkipBlocks;

            public Pattern(int i, int i2) {
                set(i, i2);
            }

            public void set(int i, int i2) {
                this.mEncryptBlocks = i;
                this.mSkipBlocks = i2;
            }

            public int getSkipBlocks() {
                return this.mSkipBlocks;
            }

            public int getEncryptBlocks() {
                return this.mEncryptBlocks;
            }
        }

        public void set(int i, int[] iArr, int[] iArr2, byte[] bArr, byte[] bArr2, int i2) {
            this.numSubSamples = i;
            this.numBytesOfClearData = iArr;
            this.numBytesOfEncryptedData = iArr2;
            this.key = bArr;
            this.iv = bArr2;
            this.mode = i2;
            this.mPattern = ZERO_PATTERN;
        }

        public android.media.MediaCodec.CryptoInfo.Pattern getPattern() {
            return new android.media.MediaCodec.CryptoInfo.Pattern(this.mPattern.getEncryptBlocks(), this.mPattern.getSkipBlocks());
        }

        public void setPattern(android.media.MediaCodec.CryptoInfo.Pattern pattern) {
            if (pattern == null) {
                pattern = ZERO_PATTERN;
            }
            setPattern(pattern.getEncryptBlocks(), pattern.getSkipBlocks());
        }

        private void setPattern(int i, int i2) {
            this.mPattern = new android.media.MediaCodec.CryptoInfo.Pattern(i, i2);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.numSubSamples + " subsamples, key [");
            for (int i = 0; i < this.key.length; i++) {
                sb.append("0123456789abcdef".charAt((this.key[i] & 240) >> 4));
                sb.append("0123456789abcdef".charAt(this.key[i] & 15));
            }
            sb.append("], iv [");
            for (int i2 = 0; i2 < this.iv.length; i2++) {
                sb.append("0123456789abcdef".charAt((this.iv[i2] & 240) >> 4));
                sb.append("0123456789abcdef".charAt(this.iv[i2] & 15));
            }
            sb.append("], clear ");
            sb.append(java.util.Arrays.toString(this.numBytesOfClearData));
            sb.append(", encrypted ");
            sb.append(java.util.Arrays.toString(this.numBytesOfEncryptedData));
            sb.append(", pattern (encrypt: ");
            sb.append(this.mPattern.mEncryptBlocks);
            sb.append(", skip: ");
            sb.append(this.mPattern.mSkipBlocks);
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            return sb.toString();
        }
    }

    public final void queueSecureInputBuffer(int i, int i2, android.media.MediaCodec.CryptoInfo cryptoInfo, long j, int i3) throws android.media.MediaCodec.CryptoException {
        if ((i3 & 32) != 0 && (i3 & 4) != 0) {
            throw new android.media.MediaCodec.InvalidBufferFlagsException(EOS_AND_DECODE_ONLY_ERROR_MESSAGE);
        }
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("queueSecureInputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getQueueRequest() to queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.remove(i);
        }
        try {
            native_queueSecureInputBuffer(i, i2, cryptoInfo, j, i3);
        } catch (android.media.MediaCodec.CryptoException | java.lang.IllegalStateException e) {
            revalidateByteBuffer(this.mCachedInputBuffers, i, true);
            throw e;
        }
    }

    public final void queueSecureInputBuffers(int i, java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> arrayDeque, java.util.ArrayDeque<android.media.MediaCodec.CryptoInfo> arrayDeque2) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("queueSecureInputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getQueueRequest() to queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.remove(i);
        }
        try {
            native_queueSecureInputBuffers(i, arrayDeque.toArray(), arrayDeque2.toArray());
        } catch (android.media.MediaCodec.CryptoException | java.lang.IllegalArgumentException | java.lang.IllegalStateException e) {
            revalidateByteBuffer(this.mCachedInputBuffers, i, true);
            throw e;
        }
    }

    public final int dequeueInputBuffer(long j) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("dequeueInputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use MediaCodec.Callback objectes to get input buffer slots.");
            }
        }
        int native_dequeueInputBuffer = native_dequeueInputBuffer(j);
        if (native_dequeueInputBuffer >= 0) {
            synchronized (this.mBufferLock) {
                validateInputByteBufferLocked(this.mCachedInputBuffers, native_dequeueInputBuffer);
            }
        }
        return native_dequeueInputBuffer;
    }

    public static final class LinearBlock {
        private static final java.util.concurrent.BlockingQueue<android.media.MediaCodec.LinearBlock> sPool = new java.util.concurrent.LinkedBlockingQueue();
        private final java.lang.Object mLock = new java.lang.Object();
        private boolean mValid = false;
        private boolean mMappable = false;
        private java.nio.ByteBuffer mMapped = null;
        private long mNativeContext = 0;
        private boolean mInternal = false;

        private static native boolean native_checkCompatible(java.lang.String[] strArr);

        private native java.nio.ByteBuffer native_map();

        private native void native_obtain(int i, java.lang.String[] strArr);

        private native void native_recycle();

        private LinearBlock() {
        }

        public boolean isMappable() {
            boolean z;
            synchronized (this.mLock) {
                if (!this.mValid) {
                    throw new java.lang.IllegalStateException("The linear block is invalid");
                }
                z = this.mMappable;
            }
            return z;
        }

        public java.nio.ByteBuffer map() {
            java.nio.ByteBuffer byteBuffer;
            synchronized (this.mLock) {
                if (!this.mValid) {
                    throw new java.lang.IllegalStateException("The linear block is invalid");
                }
                if (!this.mMappable) {
                    throw new java.lang.IllegalStateException("The linear block is not mappable");
                }
                if (this.mMapped == null) {
                    this.mMapped = native_map();
                }
                byteBuffer = this.mMapped;
            }
            return byteBuffer;
        }

        public void recycle() {
            synchronized (this.mLock) {
                if (!this.mValid) {
                    throw new java.lang.IllegalStateException("The linear block is invalid");
                }
                if (this.mMapped != null) {
                    this.mMapped.setAccessible(false);
                    this.mMapped = null;
                }
                native_recycle();
                this.mValid = false;
                this.mNativeContext = 0L;
            }
            if (!this.mInternal) {
                sPool.offer(this);
            }
        }

        protected void finalize() {
            native_recycle();
        }

        public static boolean isCodecCopyFreeCompatible(java.lang.String[] strArr) {
            return native_checkCompatible(strArr);
        }

        public static android.media.MediaCodec.LinearBlock obtain(int i, java.lang.String[] strArr) {
            android.media.MediaCodec.LinearBlock poll = sPool.poll();
            if (poll == null) {
                poll = new android.media.MediaCodec.LinearBlock();
            }
            synchronized (poll.mLock) {
                poll.native_obtain(i, strArr);
            }
            return poll;
        }

        private void setInternalStateLocked(long j, boolean z) {
            this.mNativeContext = j;
            this.mMappable = z;
            this.mValid = j != 0;
            this.mInternal = true;
        }
    }

    public static android.media.Image mapHardwareBuffer(android.hardware.HardwareBuffer hardwareBuffer) {
        return native_mapHardwareBuffer(hardwareBuffer);
    }

    public final class QueueRequest {
        private boolean mAccessible;
        private final java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> mBufferInfos;
        private final android.media.MediaCodec mCodec;
        private final java.util.ArrayDeque<android.media.MediaCodec.CryptoInfo> mCryptoInfos;
        private int mFlags;
        private android.hardware.HardwareBuffer mHardwareBuffer;
        private final int mIndex;
        private android.media.MediaCodec.LinearBlock mLinearBlock;
        private int mOffset;
        private long mPresentationTimeUs;
        private int mSize;
        private final java.util.ArrayList<java.lang.String> mTuningKeys;
        private final java.util.ArrayList<java.lang.Object> mTuningValues;

        private QueueRequest(android.media.MediaCodec mediaCodec, int i) {
            this.mLinearBlock = null;
            this.mOffset = 0;
            this.mSize = 0;
            this.mHardwareBuffer = null;
            this.mPresentationTimeUs = 0L;
            this.mFlags = 0;
            this.mBufferInfos = new java.util.ArrayDeque<>();
            this.mCryptoInfos = new java.util.ArrayDeque<>();
            this.mTuningKeys = new java.util.ArrayList<>();
            this.mTuningValues = new java.util.ArrayList<>();
            this.mAccessible = false;
            this.mCodec = mediaCodec;
            this.mIndex = i;
        }

        public android.media.MediaCodec.QueueRequest setLinearBlock(android.media.MediaCodec.LinearBlock linearBlock, int i, int i2) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new java.lang.IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = linearBlock;
            this.mOffset = i;
            this.mSize = i2;
            this.mCryptoInfos.clear();
            return this;
        }

        public android.media.MediaCodec.QueueRequest setMultiFrameLinearBlock(android.media.MediaCodec.LinearBlock linearBlock, java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> arrayDeque) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new java.lang.IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = linearBlock;
            this.mBufferInfos.clear();
            this.mBufferInfos.addAll(arrayDeque);
            this.mCryptoInfos.clear();
            return this;
        }

        public android.media.MediaCodec.QueueRequest setEncryptedLinearBlock(android.media.MediaCodec.LinearBlock linearBlock, int i, int i2, android.media.MediaCodec.CryptoInfo cryptoInfo) {
            java.util.Objects.requireNonNull(cryptoInfo);
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new java.lang.IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = linearBlock;
            this.mOffset = i;
            this.mSize = i2;
            this.mCryptoInfos.clear();
            this.mCryptoInfos.add(cryptoInfo);
            return this;
        }

        public android.media.MediaCodec.QueueRequest setMultiFrameEncryptedLinearBlock(android.media.MediaCodec.LinearBlock linearBlock, java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> arrayDeque, java.util.ArrayDeque<android.media.MediaCodec.CryptoInfo> arrayDeque2) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new java.lang.IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = linearBlock;
            this.mBufferInfos.clear();
            this.mBufferInfos.addAll(arrayDeque);
            this.mCryptoInfos.clear();
            this.mCryptoInfos.addAll(arrayDeque2);
            return this;
        }

        public android.media.MediaCodec.QueueRequest setHardwareBuffer(android.hardware.HardwareBuffer hardwareBuffer) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new java.lang.IllegalStateException("Cannot set block twice");
            }
            this.mHardwareBuffer = hardwareBuffer;
            return this;
        }

        public android.media.MediaCodec.QueueRequest setPresentationTimeUs(long j) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            this.mPresentationTimeUs = j;
            return this;
        }

        public android.media.MediaCodec.QueueRequest setFlags(int i) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            this.mFlags = i;
            return this;
        }

        public android.media.MediaCodec.QueueRequest setIntegerParameter(java.lang.String str, int i) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(str);
            this.mTuningValues.add(java.lang.Integer.valueOf(i));
            return this;
        }

        public android.media.MediaCodec.QueueRequest setLongParameter(java.lang.String str, long j) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(str);
            this.mTuningValues.add(java.lang.Long.valueOf(j));
            return this;
        }

        public android.media.MediaCodec.QueueRequest setFloatParameter(java.lang.String str, float f) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(str);
            this.mTuningValues.add(java.lang.Float.valueOf(f));
            return this;
        }

        public android.media.MediaCodec.QueueRequest setByteBufferParameter(java.lang.String str, java.nio.ByteBuffer byteBuffer) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(str);
            this.mTuningValues.add(byteBuffer);
            return this;
        }

        public android.media.MediaCodec.QueueRequest setStringParameter(java.lang.String str, java.lang.String str2) {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(str);
            this.mTuningValues.add(str2);
            return this;
        }

        public void queue() {
            if (!isAccessible()) {
                throw new java.lang.IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock == null && this.mHardwareBuffer == null) {
                throw new java.lang.IllegalStateException("No block is set");
            }
            setAccessible(false);
            if (this.mBufferInfos.isEmpty()) {
                android.media.MediaCodec.BufferInfo bufferInfo = new android.media.MediaCodec.BufferInfo();
                bufferInfo.size = this.mSize;
                bufferInfo.offset = this.mOffset;
                bufferInfo.presentationTimeUs = this.mPresentationTimeUs;
                bufferInfo.flags = this.mFlags;
                this.mBufferInfos.add(bufferInfo);
            }
            if (this.mLinearBlock != null) {
                this.mCodec.native_queueLinearBlock(this.mIndex, this.mLinearBlock, this.mCryptoInfos.isEmpty() ? null : this.mCryptoInfos.toArray(), this.mBufferInfos.toArray(), this.mTuningKeys, this.mTuningValues);
            } else if (this.mHardwareBuffer != null) {
                this.mCodec.native_queueHardwareBuffer(this.mIndex, this.mHardwareBuffer, this.mPresentationTimeUs, this.mFlags, this.mTuningKeys, this.mTuningValues);
            }
            clear();
        }

        android.media.MediaCodec.QueueRequest clear() {
            this.mLinearBlock = null;
            this.mOffset = 0;
            this.mSize = 0;
            this.mHardwareBuffer = null;
            this.mPresentationTimeUs = 0L;
            this.mFlags = 0;
            this.mBufferInfos.clear();
            this.mCryptoInfos.clear();
            this.mTuningKeys.clear();
            this.mTuningValues.clear();
            return this;
        }

        boolean isAccessible() {
            return this.mAccessible;
        }

        android.media.MediaCodec.QueueRequest setAccessible(boolean z) {
            this.mAccessible = z;
            return this;
        }
    }

    public android.media.MediaCodec.QueueRequest getQueueRequest(int i) {
        android.media.MediaCodec.QueueRequest clear;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode != 1) {
                throw new java.lang.IllegalStateException("The codec is not configured for block model");
            }
            if (i < 0 || i >= this.mQueueRequests.size()) {
                throw new java.lang.IndexOutOfBoundsException("Expected range of index: [0," + (this.mQueueRequests.size() - 1) + "]; actual: " + i);
            }
            android.media.MediaCodec.QueueRequest queueRequest = this.mQueueRequests.get(i);
            if (queueRequest == null) {
                throw new java.lang.IllegalArgumentException("Unavailable index: " + i);
            }
            if (!queueRequest.isAccessible()) {
                throw new java.lang.IllegalArgumentException("The request is stale at index " + i);
            }
            clear = queueRequest.clear();
        }
        return clear;
    }

    public final int dequeueOutputBuffer(android.media.MediaCodec.BufferInfo bufferInfo, long j) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("dequeueOutputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use MediaCodec.Callback objects to get output buffer slots.");
            }
        }
        int native_dequeueOutputBuffer = native_dequeueOutputBuffer(bufferInfo, j);
        synchronized (this.mBufferLock) {
            try {
                if (native_dequeueOutputBuffer == -3) {
                    cacheBuffersLocked(false);
                } else if (native_dequeueOutputBuffer >= 0) {
                    validateOutputByteBufferLocked(this.mCachedOutputBuffers, native_dequeueOutputBuffer, bufferInfo);
                    if (this.mHasSurface || this.mCachedOutputBuffers == null) {
                        this.mDequeuedOutputInfos.put(java.lang.Integer.valueOf(native_dequeueOutputBuffer), bufferInfo.dup());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return native_dequeueOutputBuffer;
    }

    public final void releaseOutputBuffer(int i, boolean z) {
        releaseOutputBufferInternal(i, z, false, 0L);
    }

    public final void releaseOutputBuffer(int i, long j) {
        releaseOutputBufferInternal(i, true, true, j);
    }

    private void releaseOutputBufferInternal(int i, boolean z, boolean z2, long j) {
        synchronized (this.mBufferLock) {
            switch (this.mBufferMode) {
                case 0:
                    invalidateByteBufferLocked(this.mCachedOutputBuffers, i, false);
                    this.mDequeuedOutputBuffers.remove(i);
                    if (this.mHasSurface || this.mCachedOutputBuffers == null) {
                        this.mDequeuedOutputInfos.remove(java.lang.Integer.valueOf(i));
                        break;
                    }
                    break;
                case 1:
                    android.media.MediaCodec.OutputFrame outputFrame = this.mOutputFrames.get(i);
                    outputFrame.setAccessible(false);
                    outputFrame.clear();
                    break;
                default:
                    throw new java.lang.IllegalStateException("Unrecognized buffer mode: " + this.mBufferMode);
            }
        }
        releaseOutputBuffer(i, z, z2, j);
    }

    public final android.media.MediaFormat getOutputFormat() {
        return new android.media.MediaFormat(getFormatNative(false));
    }

    public final android.media.MediaFormat getInputFormat() {
        return new android.media.MediaFormat(getFormatNative(true));
    }

    public final android.media.MediaFormat getOutputFormat(int i) {
        return new android.media.MediaFormat(getOutputFormatNative(i));
    }

    private static class BufferMap {
        private final java.util.Map<java.lang.Integer, android.media.MediaCodec.BufferMap.CodecBuffer> mMap;

        private BufferMap() {
            this.mMap = new java.util.HashMap();
        }

        private static class CodecBuffer {
            private java.nio.ByteBuffer mByteBuffer;
            private android.media.Image mImage;

            private CodecBuffer() {
            }

            public void free() {
                if (this.mByteBuffer != null) {
                    java.nio.NioUtils.freeDirectBuffer(this.mByteBuffer);
                    this.mByteBuffer = null;
                }
                if (this.mImage != null) {
                    this.mImage.close();
                    this.mImage = null;
                }
            }

            public void setImage(android.media.Image image) {
                free();
                this.mImage = image;
            }

            public void setByteBuffer(java.nio.ByteBuffer byteBuffer) {
                free();
                this.mByteBuffer = byteBuffer;
            }
        }

        public void remove(int i) {
            android.media.MediaCodec.BufferMap.CodecBuffer codecBuffer = this.mMap.get(java.lang.Integer.valueOf(i));
            if (codecBuffer != null) {
                codecBuffer.free();
                this.mMap.remove(java.lang.Integer.valueOf(i));
            }
        }

        public void put(int i, java.nio.ByteBuffer byteBuffer) {
            android.media.MediaCodec.BufferMap.CodecBuffer codecBuffer = this.mMap.get(java.lang.Integer.valueOf(i));
            if (codecBuffer == null) {
                codecBuffer = new android.media.MediaCodec.BufferMap.CodecBuffer();
                this.mMap.put(java.lang.Integer.valueOf(i), codecBuffer);
            }
            codecBuffer.setByteBuffer(byteBuffer);
        }

        public void put(int i, android.media.Image image) {
            android.media.MediaCodec.BufferMap.CodecBuffer codecBuffer = this.mMap.get(java.lang.Integer.valueOf(i));
            if (codecBuffer == null) {
                codecBuffer = new android.media.MediaCodec.BufferMap.CodecBuffer();
                this.mMap.put(java.lang.Integer.valueOf(i), codecBuffer);
            }
            codecBuffer.setImage(image);
        }

        public void clear() {
            java.util.Iterator<android.media.MediaCodec.BufferMap.CodecBuffer> it = this.mMap.values().iterator();
            while (it.hasNext()) {
                it.next().free();
            }
            this.mMap.clear();
        }
    }

    private void invalidateByteBufferLocked(java.nio.ByteBuffer[] byteBufferArr, int i, boolean z) {
        java.nio.ByteBuffer byteBuffer;
        if (byteBufferArr == null) {
            if (i >= 0) {
                (z ? this.mValidInputIndices : this.mValidOutputIndices).clear(i);
            }
        } else if (i >= 0 && i < byteBufferArr.length && (byteBuffer = byteBufferArr[i]) != null) {
            byteBuffer.setAccessible(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateInputByteBufferLocked(java.nio.ByteBuffer[] byteBufferArr, int i) {
        java.nio.ByteBuffer byteBuffer;
        if (byteBufferArr == null) {
            if (i >= 0) {
                this.mValidInputIndices.set(i);
            }
        } else if (i >= 0 && i < byteBufferArr.length && (byteBuffer = byteBufferArr[i]) != null) {
            byteBuffer.setAccessible(true);
            byteBuffer.clear();
        }
    }

    private void revalidateByteBuffer(java.nio.ByteBuffer[] byteBufferArr, int i, boolean z) {
        java.nio.ByteBuffer byteBuffer;
        synchronized (this.mBufferLock) {
            if (byteBufferArr == null) {
                if (i >= 0) {
                    (z ? this.mValidInputIndices : this.mValidOutputIndices).set(i);
                }
            } else if (i >= 0 && i < byteBufferArr.length && (byteBuffer = byteBufferArr[i]) != null) {
                byteBuffer.setAccessible(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateOutputByteBuffersLocked(java.nio.ByteBuffer[] byteBufferArr, int i, java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> arrayDeque) {
        java.nio.ByteBuffer byteBuffer;
        java.util.Optional min = arrayDeque.stream().min(new java.util.Comparator() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int compare;
                compare = java.lang.Integer.compare(((android.media.MediaCodec.BufferInfo) obj).offset, ((android.media.MediaCodec.BufferInfo) obj2).offset);
                return compare;
            }
        });
        java.util.Optional max = arrayDeque.stream().max(new java.util.Comparator() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int compare;
                compare = java.lang.Integer.compare(((android.media.MediaCodec.BufferInfo) obj).offset, ((android.media.MediaCodec.BufferInfo) obj2).offset);
                return compare;
            }
        });
        if (byteBufferArr == null) {
            if (i >= 0) {
                this.mValidOutputIndices.set(i);
            }
        } else if (i >= 0 && i < byteBufferArr.length && (byteBuffer = byteBufferArr[i]) != null && min.isPresent() && max.isPresent()) {
            byteBuffer.setAccessible(true);
            byteBuffer.limit(((android.media.MediaCodec.BufferInfo) max.get()).offset + ((android.media.MediaCodec.BufferInfo) max.get()).size);
            byteBuffer.position(((android.media.MediaCodec.BufferInfo) min.get()).offset);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateOutputByteBufferLocked(java.nio.ByteBuffer[] byteBufferArr, int i, android.media.MediaCodec.BufferInfo bufferInfo) {
        java.nio.ByteBuffer byteBuffer;
        if (byteBufferArr == null) {
            if (i >= 0) {
                this.mValidOutputIndices.set(i);
            }
        } else if (i >= 0 && i < byteBufferArr.length && (byteBuffer = byteBufferArr[i]) != null) {
            byteBuffer.setAccessible(true);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size).position(bufferInfo.offset);
        }
    }

    private void invalidateByteBuffersLocked(java.nio.ByteBuffer[] byteBufferArr) {
        if (byteBufferArr != null) {
            for (java.nio.ByteBuffer byteBuffer : byteBufferArr) {
                if (byteBuffer != null) {
                    byteBuffer.setAccessible(false);
                }
            }
        }
    }

    private void freeByteBufferLocked(java.nio.ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            java.nio.NioUtils.freeDirectBuffer(byteBuffer);
        }
    }

    private void freeByteBuffersLocked(java.nio.ByteBuffer[] byteBufferArr) {
        if (byteBufferArr != null) {
            for (java.nio.ByteBuffer byteBuffer : byteBufferArr) {
                freeByteBufferLocked(byteBuffer);
            }
        }
    }

    private void freeAllTrackedBuffers() {
        synchronized (this.mBufferLock) {
            freeByteBuffersLocked(this.mCachedInputBuffers);
            freeByteBuffersLocked(this.mCachedOutputBuffers);
            this.mCachedInputBuffers = null;
            this.mCachedOutputBuffers = null;
            this.mValidInputIndices.clear();
            this.mValidOutputIndices.clear();
            this.mDequeuedInputBuffers.clear();
            this.mDequeuedOutputBuffers.clear();
            this.mQueueRequests.clear();
            this.mOutputFrames.clear();
        }
    }

    private void cacheBuffersLocked(boolean z) {
        java.nio.ByteBuffer[] byteBufferArr;
        android.media.MediaCodec.BufferInfo bufferInfo;
        try {
            byteBufferArr = getBuffers(z);
            try {
                invalidateByteBuffersLocked(byteBufferArr);
            } catch (java.lang.IllegalStateException e) {
            }
        } catch (java.lang.IllegalStateException e2) {
            byteBufferArr = null;
        }
        if (byteBufferArr != null) {
            java.util.BitSet bitSet = z ? this.mValidInputIndices : this.mValidOutputIndices;
            for (int i = 0; i < byteBufferArr.length; i++) {
                java.nio.ByteBuffer byteBuffer = byteBufferArr[i];
                if (byteBuffer != null && bitSet.get(i)) {
                    byteBuffer.setAccessible(true);
                    if (!z && (bufferInfo = this.mDequeuedOutputInfos.get(java.lang.Integer.valueOf(i))) != null) {
                        byteBuffer.limit(bufferInfo.offset + bufferInfo.size).position(bufferInfo.offset);
                    }
                }
            }
            bitSet.clear();
        }
        if (z) {
            this.mCachedInputBuffers = byteBufferArr;
        } else {
            this.mCachedOutputBuffers = byteBufferArr;
        }
    }

    public java.nio.ByteBuffer[] getInputBuffers() {
        java.nio.ByteBuffer[] byteBufferArr;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("getInputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please obtain MediaCodec.LinearBlock or HardwareBuffer objects and attach to QueueRequest objects.");
            }
            if (this.mCachedInputBuffers == null) {
                cacheBuffersLocked(true);
            }
            if (this.mCachedInputBuffers == null) {
                throw new java.lang.IllegalStateException();
            }
            byteBufferArr = this.mCachedInputBuffers;
        }
        return byteBufferArr;
    }

    public java.nio.ByteBuffer[] getOutputBuffers() {
        java.nio.ByteBuffer[] byteBufferArr;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("getOutputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getOutputFrame to get output frames.");
            }
            if (this.mCachedOutputBuffers == null) {
                cacheBuffersLocked(false);
            }
            if (this.mCachedOutputBuffers == null) {
                throw new java.lang.IllegalStateException();
            }
            byteBufferArr = this.mCachedOutputBuffers;
        }
        return byteBufferArr;
    }

    public java.nio.ByteBuffer getInputBuffer(int i) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("getInputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please obtain MediaCodec.LinearBlock or HardwareBuffer objects and attach to QueueRequest objects.");
            }
        }
        java.nio.ByteBuffer buffer = getBuffer(true, i);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.put(i, buffer);
        }
        return buffer;
    }

    public android.media.Image getInputImage(int i) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("getInputImage() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please obtain MediaCodec.LinearBlock or HardwareBuffer objects and attach to QueueRequest objects.");
            }
        }
        android.media.Image image = getImage(true, i);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.put(i, image);
        }
        return image;
    }

    public java.nio.ByteBuffer getOutputBuffer(int i) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("getOutputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getOutputFrame() to get output frames.");
            }
        }
        java.nio.ByteBuffer buffer = getBuffer(false, i);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedOutputBuffers, i, false);
            this.mDequeuedOutputBuffers.put(i, buffer);
        }
        return buffer;
    }

    public android.media.Image getOutputImage(int i) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new android.media.MediaCodec.IncompatibleWithBlockModelException("getOutputImage() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getOutputFrame() to get output frames.");
            }
        }
        android.media.Image image = getImage(false, i);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedOutputBuffers, i, false);
            this.mDequeuedOutputBuffers.put(i, image);
        }
        return image;
    }

    public static final class OutputFrame {
        private final int mIndex;
        private android.media.MediaCodec.LinearBlock mLinearBlock = null;
        private android.hardware.HardwareBuffer mHardwareBuffer = null;
        private long mPresentationTimeUs = 0;
        private int mFlags = 0;
        private android.media.MediaFormat mFormat = null;
        private final java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> mBufferInfos = new java.util.ArrayDeque<>();
        private final java.util.ArrayList<java.lang.String> mChangedKeys = new java.util.ArrayList<>();
        private final java.util.Set<java.lang.String> mKeySet = new java.util.HashSet();
        private boolean mAccessible = false;
        private boolean mLoaded = false;

        OutputFrame(int i) {
            this.mIndex = i;
        }

        public android.media.MediaCodec.LinearBlock getLinearBlock() {
            if (this.mHardwareBuffer != null) {
                throw new java.lang.IllegalStateException("This output frame is not linear");
            }
            return this.mLinearBlock;
        }

        public android.hardware.HardwareBuffer getHardwareBuffer() {
            if (this.mLinearBlock != null) {
                throw new java.lang.IllegalStateException("This output frame is not graphic");
            }
            return this.mHardwareBuffer;
        }

        public long getPresentationTimeUs() {
            return this.mPresentationTimeUs;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> getBufferInfos() {
            if (this.mBufferInfos.isEmpty()) {
                android.media.MediaCodec.BufferInfo bufferInfo = new android.media.MediaCodec.BufferInfo();
                bufferInfo.set(0, 0, this.mPresentationTimeUs, this.mFlags);
                this.mBufferInfos.add(bufferInfo);
            }
            return this.mBufferInfos;
        }

        public android.media.MediaFormat getFormat() {
            return this.mFormat;
        }

        public java.util.Set<java.lang.String> getChangedKeys() {
            if (this.mKeySet.isEmpty() && !this.mChangedKeys.isEmpty()) {
                this.mKeySet.addAll(this.mChangedKeys);
            }
            return java.util.Collections.unmodifiableSet(this.mKeySet);
        }

        void clear() {
            this.mLinearBlock = null;
            this.mHardwareBuffer = null;
            this.mFormat = null;
            this.mBufferInfos.clear();
            this.mChangedKeys.clear();
            this.mKeySet.clear();
            this.mLoaded = false;
        }

        boolean isAccessible() {
            return this.mAccessible;
        }

        void setAccessible(boolean z) {
            this.mAccessible = z;
        }

        void setBufferInfo(android.media.MediaCodec.BufferInfo bufferInfo) {
            this.mPresentationTimeUs = bufferInfo.presentationTimeUs;
            this.mFlags = bufferInfo.flags;
        }

        void setBufferInfos(java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> arrayDeque) {
            this.mBufferInfos.clear();
            this.mBufferInfos.addAll(arrayDeque);
        }

        boolean isLoaded() {
            return this.mLoaded;
        }

        void setLoaded(boolean z) {
            this.mLoaded = z;
        }
    }

    public android.media.MediaCodec.OutputFrame getOutputFrame(int i) {
        android.media.MediaCodec.OutputFrame outputFrame;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode != 1) {
                throw new java.lang.IllegalStateException("The codec is not configured for block model");
            }
            if (i < 0 || i >= this.mOutputFrames.size()) {
                throw new java.lang.IndexOutOfBoundsException("Expected range of index: [0," + (this.mQueueRequests.size() - 1) + "]; actual: " + i);
            }
            outputFrame = this.mOutputFrames.get(i);
            if (outputFrame == null) {
                throw new java.lang.IllegalArgumentException("Unavailable index: " + i);
            }
            if (!outputFrame.isAccessible()) {
                throw new java.lang.IllegalArgumentException("The output frame is stale at index " + i);
            }
            if (!outputFrame.isLoaded()) {
                native_getOutputFrame(outputFrame, i);
                outputFrame.setLoaded(true);
            }
        }
        return outputFrame;
    }

    public void setAudioPresentation(android.media.AudioPresentation audioPresentation) {
        if (audioPresentation == null) {
            throw new java.lang.NullPointerException("audio presentation is null");
        }
        native_setAudioPresentation(audioPresentation.getPresentationId(), audioPresentation.getProgramId());
    }

    public final java.lang.String getName() {
        return this.mNameAtCreation != null ? this.mNameAtCreation : getCanonicalName();
    }

    public android.os.PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    public final void setParameters(android.os.Bundle bundle) {
        if (bundle == null) {
            return;
        }
        java.lang.String[] strArr = new java.lang.String[bundle.size()];
        java.lang.Object[] objArr = new java.lang.Object[bundle.size()];
        int i = 0;
        for (java.lang.String str : bundle.keySet()) {
            if (str.equals(android.media.MediaFormat.KEY_AUDIO_SESSION_ID)) {
                try {
                    int intValue = ((java.lang.Integer) bundle.get(str)).intValue();
                    strArr[i] = android.media.MediaFormat.KEY_AUDIO_HW_SYNC;
                    objArr[i] = java.lang.Integer.valueOf(android.media.AudioSystem.getAudioHwSyncForSession(intValue));
                } catch (java.lang.Exception e) {
                    throw new java.lang.IllegalArgumentException("Wrong Session ID Parameter!");
                }
            } else {
                strArr[i] = str;
                java.lang.Object obj = bundle.get(str);
                if (obj instanceof byte[]) {
                    objArr[i] = java.nio.ByteBuffer.wrap((byte[]) obj);
                } else {
                    objArr[i] = obj;
                }
            }
            i++;
        }
        setParameters(strArr, objArr);
    }

    public void setCallback(android.media.MediaCodec.Callback callback, android.os.Handler handler) {
        if (callback != null) {
            synchronized (this.mListenerLock) {
                android.media.MediaCodec.EventHandler eventHandlerOn = getEventHandlerOn(handler, this.mCallbackHandler);
                if (eventHandlerOn != this.mCallbackHandler) {
                    this.mCallbackHandler.removeMessages(2);
                    this.mCallbackHandler.removeMessages(1);
                    this.mCallbackHandler = eventHandlerOn;
                }
            }
        } else if (this.mCallbackHandler != null) {
            this.mCallbackHandler.removeMessages(2);
            this.mCallbackHandler.removeMessages(1);
        }
        if (this.mCallbackHandler != null) {
            this.mCallbackHandler.sendMessage(this.mCallbackHandler.obtainMessage(2, 0, 0, callback));
            native_setCallback(callback);
        }
    }

    public void setCallback(android.media.MediaCodec.Callback callback) {
        setCallback(callback, null);
    }

    public void setOnFirstTunnelFrameReadyListener(android.os.Handler handler, android.media.MediaCodec.OnFirstTunnelFrameReadyListener onFirstTunnelFrameReadyListener) {
        synchronized (this.mListenerLock) {
            this.mOnFirstTunnelFrameReadyListener = onFirstTunnelFrameReadyListener;
            if (onFirstTunnelFrameReadyListener != null) {
                android.media.MediaCodec.EventHandler eventHandlerOn = getEventHandlerOn(handler, this.mOnFirstTunnelFrameReadyHandler);
                if (eventHandlerOn != this.mOnFirstTunnelFrameReadyHandler) {
                    this.mOnFirstTunnelFrameReadyHandler.removeMessages(4);
                }
                this.mOnFirstTunnelFrameReadyHandler = eventHandlerOn;
            } else if (this.mOnFirstTunnelFrameReadyHandler != null) {
                this.mOnFirstTunnelFrameReadyHandler.removeMessages(4);
            }
            native_enableOnFirstTunnelFrameReadyListener(onFirstTunnelFrameReadyListener != null);
        }
    }

    public void setOnFrameRenderedListener(android.media.MediaCodec.OnFrameRenderedListener onFrameRenderedListener, android.os.Handler handler) {
        synchronized (this.mListenerLock) {
            this.mOnFrameRenderedListener = onFrameRenderedListener;
            if (onFrameRenderedListener != null) {
                android.media.MediaCodec.EventHandler eventHandlerOn = getEventHandlerOn(handler, this.mOnFrameRenderedHandler);
                if (eventHandlerOn != this.mOnFrameRenderedHandler) {
                    this.mOnFrameRenderedHandler.removeMessages(3);
                }
                this.mOnFrameRenderedHandler = eventHandlerOn;
            } else if (this.mOnFrameRenderedHandler != null) {
                this.mOnFrameRenderedHandler.removeMessages(3);
            }
            native_enableOnFrameRenderedListener(onFrameRenderedListener != null);
        }
    }

    public java.util.List<java.lang.String> getSupportedVendorParameters() {
        return native_getSupportedVendorParameters();
    }

    public static class ParameterDescriptor {
        private java.lang.String mName;
        private int mType;

        private ParameterDescriptor() {
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public int getType() {
            return this.mType;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || !(obj instanceof android.media.MediaCodec.ParameterDescriptor)) {
                return false;
            }
            android.media.MediaCodec.ParameterDescriptor parameterDescriptor = (android.media.MediaCodec.ParameterDescriptor) obj;
            return this.mName.equals(parameterDescriptor.mName) && this.mType == parameterDescriptor.mType;
        }

        public int hashCode() {
            return java.util.Arrays.asList(this.mName, java.lang.Integer.valueOf(this.mType)).hashCode();
        }
    }

    public android.media.MediaCodec.ParameterDescriptor getParameterDescriptor(java.lang.String str) {
        return native_getParameterDescriptor(str);
    }

    public void subscribeToVendorParameters(java.util.List<java.lang.String> list) {
        native_subscribeToVendorParameters(list);
    }

    public void unsubscribeFromVendorParameters(java.util.List<java.lang.String> list) {
        native_unsubscribeFromVendorParameters(list);
    }

    private android.media.MediaCodec.EventHandler getEventHandlerOn(android.os.Handler handler, android.media.MediaCodec.EventHandler eventHandler) {
        if (handler == null) {
            return this.mEventHandler;
        }
        android.os.Looper looper = handler.getLooper();
        if (eventHandler.getLooper() == looper) {
            return eventHandler;
        }
        return new android.media.MediaCodec.EventHandler(this, looper);
    }

    public static abstract class Callback {
        public abstract void onError(android.media.MediaCodec mediaCodec, android.media.MediaCodec.CodecException codecException);

        public abstract void onInputBufferAvailable(android.media.MediaCodec mediaCodec, int i);

        public abstract void onOutputBufferAvailable(android.media.MediaCodec mediaCodec, int i, android.media.MediaCodec.BufferInfo bufferInfo);

        public abstract void onOutputFormatChanged(android.media.MediaCodec mediaCodec, android.media.MediaFormat mediaFormat);

        public void onOutputBuffersAvailable(android.media.MediaCodec mediaCodec, int i, java.util.ArrayDeque<android.media.MediaCodec.BufferInfo> arrayDeque) {
            throw new java.lang.IllegalStateException("Client must override onOutputBuffersAvailable when codec is configured to operate with multiple access units");
        }

        public void onCryptoError(android.media.MediaCodec mediaCodec, android.media.MediaCodec.CryptoException cryptoException) {
            throw new java.lang.IllegalStateException("Client must override onCryptoError when the codec is configured with CONFIGURE_FLAG_USE_CRYPTO_ASYNC.", cryptoException);
        }
    }

    private void postEventFromNative(int i, int i2, int i3, java.lang.Object obj) {
        synchronized (this.mListenerLock) {
            android.media.MediaCodec.EventHandler eventHandler = this.mEventHandler;
            if (i == 1) {
                eventHandler = this.mCallbackHandler;
            } else if (i == 4) {
                eventHandler = this.mOnFirstTunnelFrameReadyHandler;
            } else if (i == 3) {
                eventHandler = this.mOnFrameRenderedHandler;
            }
            if (eventHandler != null) {
                eventHandler.sendMessage(eventHandler.obtainMessage(i, i2, i3, obj));
            }
        }
    }

    public android.media.MediaCodecInfo getCodecInfo() {
        android.media.MediaCodecInfo mediaCodecInfo;
        java.lang.String name = getName();
        synchronized (this.mCodecInfoLock) {
            if (this.mCodecInfo == null) {
                this.mCodecInfo = getOwnCodecInfo();
                if (this.mCodecInfo == null) {
                    this.mCodecInfo = android.media.MediaCodecList.getInfoFor(name);
                }
            }
            mediaCodecInfo = this.mCodecInfo;
        }
        return mediaCodecInfo;
    }

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }

    private final long lockAndGetContext() {
        this.mNativeContextLock.lock();
        return this.mNativeContext;
    }

    private final void setAndUnlockContext(long j) {
        this.mNativeContext = j;
        this.mNativeContextLock.unlock();
    }

    public static class MediaImage extends android.media.Image {
        private static final int TYPE_YUV = 1;
        private final java.nio.ByteBuffer mBuffer;
        private final long mBufferContext;
        private final int mFormat;
        private final int mHeight;
        private final java.nio.ByteBuffer mInfo;
        private final boolean mIsReadOnly;
        private final android.media.Image.Plane[] mPlanes;
        private long mTimestamp;
        private final int mWidth;
        private final int mXOffset;
        private final int mYOffset;
        private final int mTransform = 0;
        private final int mScalingMode = 0;

        @Override // android.media.Image
        public int getFormat() {
            throwISEIfImageIsInvalid();
            return this.mFormat;
        }

        @Override // android.media.Image
        public int getHeight() {
            throwISEIfImageIsInvalid();
            return this.mHeight;
        }

        @Override // android.media.Image
        public int getWidth() {
            throwISEIfImageIsInvalid();
            return this.mWidth;
        }

        @Override // android.media.Image
        public int getTransform() {
            throwISEIfImageIsInvalid();
            return 0;
        }

        @Override // android.media.Image
        public int getScalingMode() {
            throwISEIfImageIsInvalid();
            return 0;
        }

        @Override // android.media.Image
        public long getTimestamp() {
            throwISEIfImageIsInvalid();
            return this.mTimestamp;
        }

        @Override // android.media.Image
        public android.media.Image.Plane[] getPlanes() {
            throwISEIfImageIsInvalid();
            return (android.media.Image.Plane[]) java.util.Arrays.copyOf(this.mPlanes, this.mPlanes.length);
        }

        @Override // android.media.Image, java.lang.AutoCloseable
        public void close() {
            if (this.mIsImageValid) {
                if (this.mBuffer != null) {
                    java.nio.NioUtils.freeDirectBuffer(this.mBuffer);
                }
                if (this.mBufferContext != 0) {
                    android.media.MediaCodec.native_closeMediaImage(this.mBufferContext);
                }
                this.mIsImageValid = false;
            }
        }

        @Override // android.media.Image
        public void setCropRect(android.graphics.Rect rect) {
            if (this.mIsReadOnly) {
                throw new java.nio.ReadOnlyBufferException();
            }
            super.setCropRect(rect);
        }

        public MediaImage(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2, boolean z, long j, int i, int i2, android.graphics.Rect rect) {
            int i3;
            int i4;
            android.graphics.Rect rect2;
            char c;
            this.mTimestamp = j;
            this.mIsImageValid = true;
            this.mIsReadOnly = byteBuffer.isReadOnly();
            this.mBuffer = byteBuffer.duplicate();
            this.mXOffset = i;
            this.mYOffset = i2;
            this.mInfo = byteBuffer2;
            this.mBufferContext = 0L;
            if (byteBuffer2.remaining() == 104) {
                int i5 = byteBuffer2.getInt();
                if (i5 != 1) {
                    throw new java.lang.UnsupportedOperationException("unsupported type: " + i5);
                }
                int i6 = byteBuffer2.getInt();
                if (i6 == 3) {
                    this.mWidth = byteBuffer2.getInt();
                    this.mHeight = byteBuffer2.getInt();
                    if (this.mWidth < 1 || this.mHeight < 1) {
                        throw new java.lang.UnsupportedOperationException("unsupported size: " + this.mWidth + "x" + this.mHeight);
                    }
                    int i7 = byteBuffer2.getInt();
                    if (i7 != 8 && i7 != 10) {
                        throw new java.lang.UnsupportedOperationException("unsupported bit depth: " + i7);
                    }
                    int i8 = byteBuffer2.getInt();
                    if (i8 == 8 || i8 == 16) {
                        if (i7 == 8 && i8 == 8) {
                            this.mFormat = 35;
                            i4 = 1;
                            i3 = 2;
                        } else if (i7 == 10 && i8 == 16) {
                            this.mFormat = 54;
                            i3 = 4;
                            i4 = 2;
                        } else {
                            throw new java.lang.UnsupportedOperationException("couldn't infer ImageFormat bitDepth: " + i7 + " bitDepthAllocated: " + i8);
                        }
                        this.mPlanes = new android.media.MediaCodec.MediaImage.MediaPlane[i6];
                        int i9 = -1;
                        int i10 = -1;
                        int i11 = 0;
                        while (i11 < i6) {
                            int i12 = byteBuffer2.getInt();
                            int i13 = byteBuffer2.getInt();
                            int i14 = byteBuffer2.getInt();
                            int i15 = byteBuffer2.getInt();
                            int i16 = byteBuffer2.getInt();
                            if (i15 == i16) {
                                if (i15 == (i11 == 0 ? 1 : 2)) {
                                    if (i13 < 1 || i14 < 1) {
                                        throw new java.lang.UnsupportedOperationException("unexpected strides: " + i13 + " pixel, " + i14 + " row on plane " + i11);
                                    }
                                    byteBuffer.clear();
                                    byteBuffer.position(this.mBuffer.position() + i12 + ((i / i15) * i13) + ((i2 / i16) * i14));
                                    int i17 = i6;
                                    byteBuffer.limit(byteBuffer.position() + android.media.Utils.divUp(i7, 8) + (((this.mHeight / i16) - 1) * i14) + (((this.mWidth / i15) - 1) * i13));
                                    this.mPlanes[i11] = new android.media.MediaCodec.MediaImage.MediaPlane(byteBuffer.slice(), i14, i13);
                                    if ((this.mFormat == 35 || this.mFormat == 54) && i11 == 1) {
                                        i10 = i12;
                                        c = '#';
                                    } else {
                                        c = '#';
                                        if ((this.mFormat == 35 || this.mFormat == 54) && i11 == 2) {
                                            i9 = i12;
                                        }
                                    }
                                    i11++;
                                    i6 = i17;
                                }
                            }
                            throw new java.lang.UnsupportedOperationException("unexpected subsampling: " + i15 + "x" + i16 + " on plane " + i11);
                        }
                        if (this.mFormat == 54) {
                            int i18 = i10;
                            if (i9 != i18 + i4) {
                                throw new java.lang.UnsupportedOperationException("Invalid plane offsets cbPlaneOffset: " + i18 + " crPlaneOffset: " + i9);
                            }
                            if (this.mPlanes[1].getPixelStride() != i3 || this.mPlanes[2].getPixelStride() != i3) {
                                throw new java.lang.UnsupportedOperationException("Invalid pixelStride");
                            }
                        }
                        if (rect != null) {
                            rect2 = rect;
                        } else {
                            rect2 = new android.graphics.Rect(0, 0, this.mWidth, this.mHeight);
                        }
                        rect2.offset(-i, -i2);
                        super.setCropRect(rect2);
                        return;
                    }
                    throw new java.lang.UnsupportedOperationException("unsupported allocated bit depth: " + i8);
                }
                throw new java.lang.RuntimeException("unexpected number of planes: " + i6);
            }
            throw new java.lang.UnsupportedOperationException("unsupported info length: " + byteBuffer2.remaining());
        }

        public MediaImage(java.nio.ByteBuffer[] byteBufferArr, int[] iArr, int[] iArr2, int i, int i2, int i3, boolean z, long j, int i4, int i5, android.graphics.Rect rect, long j2) {
            android.graphics.Rect rect2;
            if (byteBufferArr.length == iArr.length && byteBufferArr.length == iArr2.length) {
                this.mWidth = i;
                this.mHeight = i2;
                this.mFormat = i3;
                this.mTimestamp = j;
                this.mIsImageValid = true;
                this.mIsReadOnly = z;
                this.mBuffer = null;
                this.mInfo = null;
                this.mPlanes = new android.media.MediaCodec.MediaImage.MediaPlane[byteBufferArr.length];
                for (int i6 = 0; i6 < byteBufferArr.length; i6++) {
                    this.mPlanes[i6] = new android.media.MediaCodec.MediaImage.MediaPlane(byteBufferArr[i6], iArr[i6], iArr2[i6]);
                }
                this.mXOffset = i4;
                this.mYOffset = i5;
                if (rect != null) {
                    rect2 = rect;
                } else {
                    rect2 = new android.graphics.Rect(0, 0, this.mWidth, this.mHeight);
                }
                rect2.offset(-i4, -i5);
                super.setCropRect(rect2);
                this.mBufferContext = j2;
                return;
            }
            throw new java.lang.IllegalArgumentException("buffers, rowStrides and pixelStrides should have the same length");
        }

        private class MediaPlane extends android.media.Image.Plane {
            private final int mColInc;
            private final java.nio.ByteBuffer mData;
            private final int mRowInc;

            public MediaPlane(java.nio.ByteBuffer byteBuffer, int i, int i2) {
                this.mData = byteBuffer;
                this.mRowInc = i;
                this.mColInc = i2;
            }

            @Override // android.media.Image.Plane
            public int getRowStride() {
                android.media.MediaCodec.MediaImage.this.throwISEIfImageIsInvalid();
                return this.mRowInc;
            }

            @Override // android.media.Image.Plane
            public int getPixelStride() {
                android.media.MediaCodec.MediaImage.this.throwISEIfImageIsInvalid();
                return this.mColInc;
            }

            @Override // android.media.Image.Plane
            public java.nio.ByteBuffer getBuffer() {
                android.media.MediaCodec.MediaImage.this.throwISEIfImageIsInvalid();
                return this.mData;
            }
        }
    }

    public static final class MetricsConstants {
        public static final java.lang.String CODEC = "android.media.mediacodec.codec";
        public static final java.lang.String ENCODER = "android.media.mediacodec.encoder";
        public static final java.lang.String HEIGHT = "android.media.mediacodec.height";
        public static final java.lang.String MIME_TYPE = "android.media.mediacodec.mime";
        public static final java.lang.String MODE = "android.media.mediacodec.mode";
        public static final java.lang.String MODE_AUDIO = "audio";
        public static final java.lang.String MODE_VIDEO = "video";
        public static final java.lang.String ROTATION = "android.media.mediacodec.rotation";
        public static final java.lang.String SECURE = "android.media.mediacodec.secure";
        public static final java.lang.String WIDTH = "android.media.mediacodec.width";

        private MetricsConstants() {
        }
    }
}
