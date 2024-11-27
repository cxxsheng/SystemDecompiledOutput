package android.media;

/* loaded from: classes2.dex */
public class Ringtone {
    private static final boolean LOGD = true;
    private static final java.lang.String MEDIA_SELECTION = "mime_type LIKE 'audio/%' OR mime_type IN ('application/ogg', 'application/x-flac')";
    private static final java.lang.String TAG = "Ringtone";
    private final boolean mAllowRemote;
    private final android.media.AudioManager mAudioManager;
    private final android.content.Context mContext;
    private android.media.audiofx.HapticGenerator mHapticGenerator;
    private android.media.MediaPlayer mLocalPlayer;
    private boolean mPreferBuiltinDevice;
    private final android.media.IRingtonePlayer mRemotePlayer;
    private final android.os.Binder mRemoteToken;
    private java.lang.String mTitle;
    private android.net.Uri mUri;
    private android.media.VolumeShaper mVolumeShaper;
    private android.media.VolumeShaper.Configuration mVolumeShaperConfig;
    private static final java.lang.String[] MEDIA_COLUMNS = {"_id", "title"};
    private static final java.util.ArrayList<android.media.Ringtone> sActiveRingtones = new java.util.ArrayList<>();
    private final android.media.Ringtone.MyOnCompletionListener mCompletionListener = new android.media.Ringtone.MyOnCompletionListener();
    private android.media.AudioAttributes mAudioAttributes = new android.media.AudioAttributes.Builder().setUsage(6).setContentType(4).build();
    private boolean mIsLooping = false;
    private float mVolume = 1.0f;
    private boolean mHapticGeneratorEnabled = false;
    private final java.lang.Object mPlaybackSettingsLock = new java.lang.Object();

    public Ringtone(android.content.Context context, boolean z) {
        this.mContext = context;
        this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService("audio");
        this.mAllowRemote = z;
        this.mRemotePlayer = z ? this.mAudioManager.getRingtonePlayer() : null;
        this.mRemoteToken = z ? new android.os.Binder() : null;
    }

    @java.lang.Deprecated
    public void setStreamType(int i) {
        android.media.PlayerBase.deprecateStreamTypeForPlayback(i, TAG, "setStreamType()");
        setAudioAttributes(new android.media.AudioAttributes.Builder().setInternalLegacyStreamType(i).build());
    }

    @java.lang.Deprecated
    public int getStreamType() {
        return android.media.AudioAttributes.toLegacyStreamType(this.mAudioAttributes);
    }

    public void setAudioAttributes(android.media.AudioAttributes audioAttributes) throws java.lang.IllegalArgumentException {
        setAudioAttributesField(audioAttributes);
        setUri(this.mUri, this.mVolumeShaperConfig);
        createLocalMediaPlayer();
    }

    public void setAudioAttributesField(android.media.AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            throw new java.lang.IllegalArgumentException("Invalid null AudioAttributes for Ringtone");
        }
        this.mAudioAttributes = audioAttributes;
    }

    private android.media.AudioDeviceInfo getBuiltinDevice(android.media.AudioManager audioManager) {
        for (android.media.AudioDeviceInfo audioDeviceInfo : audioManager.getDevices(2)) {
            if (audioDeviceInfo.getType() == 2) {
                return audioDeviceInfo;
            }
        }
        return null;
    }

    public boolean preferBuiltinDevice(boolean z) {
        this.mPreferBuiltinDevice = z;
        if (this.mLocalPlayer == null) {
            return true;
        }
        return this.mLocalPlayer.setPreferredDevice(getBuiltinDevice(this.mAudioManager));
    }

    public boolean createLocalMediaPlayer() {
        android.os.Trace.beginSection("createLocalMediaPlayer");
        if (this.mUri == null) {
            android.util.Log.e(TAG, "Could not create media player as no URI was provided.");
            return this.mAllowRemote && this.mRemotePlayer != null;
        }
        destroyLocalPlayer();
        this.mLocalPlayer = new android.media.MediaPlayer();
        try {
            this.mLocalPlayer.setDataSource(this.mContext, this.mUri);
            this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
            this.mLocalPlayer.setPreferredDevice(this.mPreferBuiltinDevice ? getBuiltinDevice(this.mAudioManager) : null);
            synchronized (this.mPlaybackSettingsLock) {
                applyPlaybackProperties_sync();
            }
            if (this.mVolumeShaperConfig != null) {
                this.mVolumeShaper = this.mLocalPlayer.createVolumeShaper(this.mVolumeShaperConfig);
            }
            this.mLocalPlayer.prepare();
        } catch (java.io.IOException | java.lang.SecurityException e) {
            destroyLocalPlayer();
            if (!this.mAllowRemote) {
                android.util.Log.w(TAG, "Remote playback not allowed: " + e);
            }
        }
        if (this.mLocalPlayer != null) {
            android.util.Log.d(TAG, "Successfully created local player");
        } else {
            android.util.Log.d(TAG, "Problem opening; delegating to remote player");
        }
        android.os.Trace.endSection();
        if (this.mLocalPlayer == null) {
            return this.mAllowRemote && this.mRemotePlayer != null;
        }
        return true;
    }

    public boolean hasHapticChannels() {
        try {
            android.os.Trace.beginSection("Ringtone.hasHapticChannels");
            if (this.mLocalPlayer != null) {
                for (android.media.MediaPlayer.TrackInfo trackInfo : this.mLocalPlayer.getTrackInfo()) {
                    if (trackInfo.hasHapticChannels()) {
                        android.os.Trace.endSection();
                        return true;
                    }
                }
            }
            return false;
        } finally {
            android.os.Trace.endSection();
        }
    }

    public boolean hasLocalPlayer() {
        return this.mLocalPlayer != null;
    }

    public android.media.AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public void setLooping(boolean z) {
        synchronized (this.mPlaybackSettingsLock) {
            this.mIsLooping = z;
            applyPlaybackProperties_sync();
        }
    }

    public boolean isLooping() {
        boolean z;
        synchronized (this.mPlaybackSettingsLock) {
            z = this.mIsLooping;
        }
        return z;
    }

    public void setVolume(float f) {
        synchronized (this.mPlaybackSettingsLock) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            if (f > 1.0f) {
                f = 1.0f;
            }
            this.mVolume = f;
            applyPlaybackProperties_sync();
        }
    }

    public float getVolume() {
        float f;
        synchronized (this.mPlaybackSettingsLock) {
            f = this.mVolume;
        }
        return f;
    }

    public boolean setHapticGeneratorEnabled(boolean z) {
        if (!android.media.audiofx.HapticGenerator.isAvailable()) {
            return false;
        }
        synchronized (this.mPlaybackSettingsLock) {
            this.mHapticGeneratorEnabled = z;
            applyPlaybackProperties_sync();
        }
        return true;
    }

    public boolean isHapticGeneratorEnabled() {
        boolean z;
        synchronized (this.mPlaybackSettingsLock) {
            z = this.mHapticGeneratorEnabled;
        }
        return z;
    }

    private void applyPlaybackProperties_sync() {
        if (this.mLocalPlayer != null) {
            this.mLocalPlayer.setVolume(this.mVolume);
            this.mLocalPlayer.setLooping(this.mIsLooping);
            if (this.mHapticGenerator == null && this.mHapticGeneratorEnabled) {
                this.mHapticGenerator = android.media.audiofx.HapticGenerator.create(this.mLocalPlayer.getAudioSessionId());
            }
            if (this.mHapticGenerator != null) {
                this.mHapticGenerator.setEnabled(this.mHapticGeneratorEnabled);
                return;
            }
            return;
        }
        if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                this.mRemotePlayer.setPlaybackProperties(this.mRemoteToken, this.mVolume, this.mIsLooping, this.mHapticGeneratorEnabled);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Problem setting playback properties: ", e);
                return;
            }
        }
        android.util.Log.w(TAG, "Neither local nor remote player available when applying playback properties");
    }

    public java.lang.String getTitle(android.content.Context context) {
        if (this.mTitle != null) {
            return this.mTitle;
        }
        java.lang.String title = getTitle(context, this.mUri, true, this.mAllowRemote);
        this.mTitle = title;
        return title;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0068, code lost:
    
        if (r9 != null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006a, code lost:
    
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0099, code lost:
    
        if (r6 != null) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x009b, code lost:
    
        r6 = r8.getLastPathSegment();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0096, code lost:
    
        if (r9 == null) goto L50;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static java.lang.String getTitle(android.content.Context context, android.net.Uri uri, boolean z, boolean z2) {
        java.lang.String string;
        android.database.Cursor cursor;
        android.content.ContentResolver contentResolver = context.getContentResolver();
        if (uri != null) {
            java.lang.String authorityWithoutUserId = android.content.ContentProvider.getAuthorityWithoutUserId(uri.getAuthority());
            java.lang.AutoCloseable autoCloseable = null;
            string = null;
            string = null;
            string = null;
            string = null;
            string = null;
            try {
                if (!"settings".equals(authorityWithoutUserId)) {
                    try {
                        if ("media".equals(authorityWithoutUserId)) {
                            cursor = contentResolver.query(uri, MEDIA_COLUMNS, z2 ? null : MEDIA_SELECTION, null, null);
                            if (cursor != null) {
                                try {
                                    if (cursor.getCount() == 1) {
                                        cursor.moveToFirst();
                                        java.lang.String string2 = cursor.getString(1);
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                        return string2;
                                    }
                                } catch (java.lang.SecurityException e) {
                                    android.media.IRingtonePlayer ringtonePlayer = z2 ? ((android.media.AudioManager) context.getSystemService("audio")).getRingtonePlayer() : null;
                                    if (ringtonePlayer != null) {
                                        try {
                                            string = ringtonePlayer.getTitle(uri);
                                        } catch (android.os.RemoteException e2) {
                                        }
                                    }
                                }
                            }
                        } else {
                            cursor = null;
                        }
                    } catch (java.lang.SecurityException e3) {
                        cursor = null;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        if (autoCloseable != null) {
                            autoCloseable.close();
                        }
                        throw th;
                    }
                } else if (z != 0) {
                    string = context.getString(com.android.internal.R.string.ringtone_default_with_actual, getTitle(context, android.media.RingtoneManager.getActualDefaultRingtoneUri(context, android.media.RingtoneManager.getDefaultType(uri)), false, z2));
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                autoCloseable = z;
            }
        } else {
            string = context.getString(com.android.internal.R.string.ringtone_silent);
        }
        if (string != null) {
            return string;
        }
        java.lang.String string3 = context.getString(com.android.internal.R.string.ringtone_unknown);
        return string3 == null ? "" : string3;
    }

    public void setUri(android.net.Uri uri) {
        setUri(uri, null);
    }

    public void setVolumeShaperConfig(android.media.VolumeShaper.Configuration configuration) {
        this.mVolumeShaperConfig = configuration;
    }

    public void setUri(android.net.Uri uri, android.media.VolumeShaper.Configuration configuration) {
        this.mVolumeShaperConfig = configuration;
        this.mUri = uri;
        if (this.mUri == null) {
            destroyLocalPlayer();
        }
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    public void play() {
        boolean z;
        float f;
        if (this.mLocalPlayer != null) {
            if (this.mAudioManager.getStreamVolume(android.media.AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) != 0) {
                startLocalPlayer();
                return;
            } else {
                if (!this.mAudioAttributes.areHapticChannelsMuted() && hasHapticChannels()) {
                    startLocalPlayer();
                    return;
                }
                return;
            }
        }
        if (this.mAllowRemote && this.mRemotePlayer != null && this.mUri != null) {
            android.net.Uri canonicalUri = this.mUri.getCanonicalUri();
            synchronized (this.mPlaybackSettingsLock) {
                z = this.mIsLooping;
                f = this.mVolume;
            }
            try {
                this.mRemotePlayer.playWithVolumeShaping(this.mRemoteToken, canonicalUri, this.mAudioAttributes, f, z, this.mVolumeShaperConfig);
                return;
            } catch (android.os.RemoteException e) {
                if (!playFallbackRingtone()) {
                    android.util.Log.w(TAG, "Problem playing ringtone: " + e);
                    return;
                }
                return;
            }
        }
        if (!playFallbackRingtone()) {
            android.util.Log.w(TAG, "Neither local nor remote playback available");
        }
    }

    public void stop() {
        if (this.mLocalPlayer != null) {
            destroyLocalPlayer();
            return;
        }
        if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                this.mRemotePlayer.stop(this.mRemoteToken);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Problem stopping ringtone: " + e);
            }
        }
    }

    private void destroyLocalPlayer() {
        if (this.mLocalPlayer != null) {
            if (this.mHapticGenerator != null) {
                this.mHapticGenerator.release();
                this.mHapticGenerator = null;
            }
            this.mLocalPlayer.setOnCompletionListener(null);
            this.mLocalPlayer.reset();
            this.mLocalPlayer.release();
            this.mLocalPlayer = null;
            this.mVolumeShaper = null;
            synchronized (sActiveRingtones) {
                sActiveRingtones.remove(this);
            }
        }
    }

    private void startLocalPlayer() {
        if (this.mLocalPlayer == null) {
            return;
        }
        synchronized (sActiveRingtones) {
            sActiveRingtones.add(this);
        }
        this.mLocalPlayer.setOnCompletionListener(this.mCompletionListener);
        this.mLocalPlayer.start();
        if (this.mVolumeShaper != null) {
            this.mVolumeShaper.apply(android.media.VolumeShaper.Operation.PLAY);
        }
    }

    public boolean isPlaying() {
        if (this.mLocalPlayer != null) {
            return this.mLocalPlayer.isPlaying();
        }
        if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                return this.mRemotePlayer.isPlaying(this.mRemoteToken);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Problem checking ringtone: " + e);
                return false;
            }
        }
        android.util.Log.w(TAG, "Neither local nor remote playback available");
        return false;
    }

    private boolean playFallbackRingtone() {
        if (this.mAudioManager.getStreamVolume(android.media.AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) == 0) {
            return false;
        }
        int defaultType = android.media.RingtoneManager.getDefaultType(this.mUri);
        if (defaultType != -1 && android.media.RingtoneManager.getActualDefaultRingtoneUri(this.mContext, defaultType) == null) {
            android.util.Log.w(TAG, "not playing fallback for " + this.mUri);
            return false;
        }
        try {
            android.content.res.AssetFileDescriptor openRawResourceFd = this.mContext.getResources().openRawResourceFd(com.android.internal.R.raw.fallbackring);
            if (openRawResourceFd == null) {
                android.util.Log.e(TAG, "Could not load fallback ringtone");
                return false;
            }
            this.mLocalPlayer = new android.media.MediaPlayer();
            if (openRawResourceFd.getDeclaredLength() < 0) {
                this.mLocalPlayer.setDataSource(openRawResourceFd.getFileDescriptor());
            } else {
                this.mLocalPlayer.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getDeclaredLength());
            }
            this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
            synchronized (this.mPlaybackSettingsLock) {
                applyPlaybackProperties_sync();
            }
            if (this.mVolumeShaperConfig != null) {
                this.mVolumeShaper = this.mLocalPlayer.createVolumeShaper(this.mVolumeShaperConfig);
            }
            this.mLocalPlayer.prepare();
            startLocalPlayer();
            openRawResourceFd.close();
            return true;
        } catch (android.content.res.Resources.NotFoundException e) {
            android.util.Log.e(TAG, "Fallback ringtone does not exist");
            return false;
        } catch (java.io.IOException e2) {
            destroyLocalPlayer();
            android.util.Log.e(TAG, "Failed to open fallback ringtone");
            return false;
        }
    }

    void setTitle(java.lang.String str) {
        this.mTitle = str;
    }

    protected void finalize() {
        if (this.mLocalPlayer != null) {
            this.mLocalPlayer.release();
        }
    }

    class MyOnCompletionListener implements android.media.MediaPlayer.OnCompletionListener {
        MyOnCompletionListener() {
        }

        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(android.media.MediaPlayer mediaPlayer) {
            synchronized (android.media.Ringtone.sActiveRingtones) {
                android.media.Ringtone.sActiveRingtones.remove(android.media.Ringtone.this);
            }
            mediaPlayer.setOnCompletionListener(null);
        }
    }
}
