package android.companion.virtual.audio;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class VirtualAudioDevice implements java.io.Closeable {
    private final android.companion.virtual.audio.VirtualAudioDevice.AudioConfigurationChangeCallback mCallback;
    private final android.content.Context mContext;
    private final java.util.concurrent.Executor mExecutor;
    private final android.companion.virtual.audio.VirtualAudioDevice.CloseListener mListener;
    private android.companion.virtual.audio.VirtualAudioSession mOngoingSession;
    private final android.companion.virtual.IVirtualDevice mVirtualDevice;
    private final android.hardware.display.VirtualDisplay mVirtualDisplay;

    @android.annotation.SystemApi
    public interface AudioConfigurationChangeCallback {
        void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list);

        void onRecordingConfigChanged(java.util.List<android.media.AudioRecordingConfiguration> list);
    }

    public interface CloseListener {
        void onClosed();
    }

    public VirtualAudioDevice(android.content.Context context, android.companion.virtual.IVirtualDevice iVirtualDevice, android.hardware.display.VirtualDisplay virtualDisplay, java.util.concurrent.Executor executor, android.companion.virtual.audio.VirtualAudioDevice.AudioConfigurationChangeCallback audioConfigurationChangeCallback, android.companion.virtual.audio.VirtualAudioDevice.CloseListener closeListener) {
        this.mContext = context;
        this.mVirtualDevice = iVirtualDevice;
        this.mVirtualDisplay = virtualDisplay;
        this.mExecutor = executor;
        this.mCallback = audioConfigurationChangeCallback;
        this.mListener = closeListener;
    }

    public android.companion.virtual.audio.AudioInjection startAudioInjection(android.media.AudioFormat audioFormat) {
        java.util.Objects.requireNonNull(audioFormat, "injectionFormat must not be null");
        if (this.mOngoingSession != null && this.mOngoingSession.getAudioInjection() != null) {
            throw new java.lang.IllegalStateException("Cannot start an audio injection while a session is ongoing. Call close() on this device first to end the previous session.");
        }
        if (this.mOngoingSession == null) {
            this.mOngoingSession = new android.companion.virtual.audio.VirtualAudioSession(this.mContext, this.mCallback, this.mExecutor);
        }
        try {
            this.mVirtualDevice.onAudioSessionStarting(this.mVirtualDisplay.getDisplay().getDisplayId(), this.mOngoingSession, this.mOngoingSession.getAudioConfigChangedListener());
            return this.mOngoingSession.startAudioInjection(audioFormat);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.companion.virtual.audio.AudioCapture startAudioCapture(android.media.AudioFormat audioFormat) {
        java.util.Objects.requireNonNull(audioFormat, "captureFormat must not be null");
        if (this.mOngoingSession != null && this.mOngoingSession.getAudioCapture() != null) {
            throw new java.lang.IllegalStateException("Cannot start an audio capture while a session is ongoing. Call close() on this device first to end the previous session.");
        }
        if (this.mOngoingSession == null) {
            this.mOngoingSession = new android.companion.virtual.audio.VirtualAudioSession(this.mContext, this.mCallback, this.mExecutor);
        }
        try {
            this.mVirtualDevice.onAudioSessionStarting(this.mVirtualDisplay.getDisplay().getDisplayId(), this.mOngoingSession, this.mOngoingSession.getAudioConfigChangedListener());
            return this.mOngoingSession.startAudioCapture(audioFormat);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.companion.virtual.audio.AudioCapture getAudioCapture() {
        if (this.mOngoingSession != null) {
            return this.mOngoingSession.getAudioCapture();
        }
        return null;
    }

    public android.companion.virtual.audio.AudioInjection getAudioInjection() {
        if (this.mOngoingSession != null) {
            return this.mOngoingSession.getAudioInjection();
        }
        return null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.mOngoingSession != null) {
            this.mOngoingSession.close();
            this.mOngoingSession = null;
            try {
                this.mVirtualDevice.onAudioSessionEnded();
                if (this.mListener != null) {
                    this.mListener.onClosed();
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
