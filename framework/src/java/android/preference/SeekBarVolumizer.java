package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class SeekBarVolumizer implements android.widget.SeekBar.OnSeekBarChangeListener, android.os.Handler.Callback {
    private static final int CHECK_RINGTONE_PLAYBACK_DELAY_MS = 1000;
    private static final int CHECK_UPDATE_SLIDER_LATER_MS = 500;
    private static final int MSG_GROUP_VOLUME_CHANGED = 1;
    private static final int MSG_INIT_SAMPLE = 3;
    private static final int MSG_SET_STREAM_VOLUME = 0;
    private static final int MSG_START_SAMPLE = 1;
    private static final int MSG_STOP_SAMPLE = 2;
    private static final int MSG_UPDATE_SLIDER_MAYBE_LATER = 4;
    private static final java.lang.String TAG = "SeekBarVolumizer";
    private boolean mAffectedByRingerMode;
    private boolean mAllowAlarms;
    private boolean mAllowMedia;
    private boolean mAllowRinger;
    private android.media.AudioAttributes mAttributes;
    private final android.media.AudioManager mAudioManager;
    private final android.preference.SeekBarVolumizer.Callback mCallback;
    private final android.content.Context mContext;
    private final android.net.Uri mDefaultUri;
    private final boolean mDeviceHasProductStrategies;
    private android.os.Handler mHandler;
    private int mLastAudibleStreamVolume;
    private int mLastProgress;
    private final int mMaxStreamVolume;
    private boolean mMuted;
    private final android.app.NotificationManager mNotificationManager;
    private boolean mNotificationOrRing;
    private android.app.NotificationManager.Policy mNotificationPolicy;
    private int mOriginalStreamVolume;
    private boolean mPlaySample;
    private final android.preference.SeekBarVolumizer.Receiver mReceiver;
    private int mRingerMode;
    private android.media.Ringtone mRingtone;
    private android.widget.SeekBar mSeekBar;
    private final int mStreamType;
    private final android.preference.SeekBarVolumizer.H mUiHandler;
    private int mVolumeBeforeMute;
    private final android.media.AudioManager.VolumeGroupCallback mVolumeGroupCallback;
    private int mVolumeGroupId;
    private final android.os.Handler mVolumeHandler;
    private android.preference.SeekBarVolumizer.Observer mVolumeObserver;
    private int mZenMode;
    private static long sStopVolumeTime = 0;
    private static final long SET_STREAM_VOLUME_DELAY_MS = java.util.concurrent.TimeUnit.MILLISECONDS.toMillis(500);
    private static final long START_SAMPLE_DELAY_MS = java.util.concurrent.TimeUnit.MILLISECONDS.toMillis(500);
    private static final long DURATION_TO_START_DELAYING = java.util.concurrent.TimeUnit.MILLISECONDS.toMillis(2000);

    public interface Callback {
        void onMuted(boolean z, boolean z2);

        void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z);

        void onSampleStarting(android.preference.SeekBarVolumizer seekBarVolumizer);

        void onStartTrackingTouch(android.preference.SeekBarVolumizer seekBarVolumizer);

        default void onStopTrackingTouch(android.preference.SeekBarVolumizer seekBarVolumizer) {
        }
    }

    public SeekBarVolumizer(android.content.Context context, int i, android.net.Uri uri, android.preference.SeekBarVolumizer.Callback callback) {
        this(context, i, uri, callback, true);
    }

    public SeekBarVolumizer(android.content.Context context, int i, android.net.Uri uri, android.preference.SeekBarVolumizer.Callback callback, boolean z) {
        this.mVolumeHandler = new android.preference.SeekBarVolumizer.VolumeHandler();
        this.mVolumeGroupCallback = new android.media.AudioManager.VolumeGroupCallback() { // from class: android.preference.SeekBarVolumizer.1
            @Override // android.media.AudioManager.VolumeGroupCallback
            public void onAudioVolumeGroupChanged(int i2, int i3) {
                if (android.preference.SeekBarVolumizer.this.mHandler == null) {
                    return;
                }
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = java.lang.Integer.valueOf(i2);
                obtain.arg2 = java.lang.Integer.valueOf(i3);
                android.preference.SeekBarVolumizer.this.mVolumeHandler.sendMessage(android.preference.SeekBarVolumizer.this.mHandler.obtainMessage(1, obtain));
            }
        };
        this.mUiHandler = new android.preference.SeekBarVolumizer.H();
        this.mReceiver = new android.preference.SeekBarVolumizer.Receiver();
        this.mLastProgress = -1;
        this.mVolumeBeforeMute = -1;
        this.mContext = context;
        this.mAudioManager = (android.media.AudioManager) context.getSystemService(android.media.AudioManager.class);
        this.mDeviceHasProductStrategies = hasAudioProductStrategies();
        this.mNotificationManager = (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class);
        this.mNotificationPolicy = this.mNotificationManager.getConsolidatedNotificationPolicy();
        this.mAllowAlarms = (this.mNotificationPolicy.priorityCategories & 32) != 0;
        this.mAllowMedia = (this.mNotificationPolicy.priorityCategories & 64) != 0;
        this.mAllowRinger = !android.service.notification.ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(this.mNotificationPolicy);
        this.mStreamType = i;
        this.mAffectedByRingerMode = this.mAudioManager.isStreamAffectedByRingerMode(this.mStreamType);
        this.mNotificationOrRing = isNotificationOrRing(this.mStreamType);
        if (this.mNotificationOrRing) {
            this.mRingerMode = this.mAudioManager.getRingerModeInternal();
        }
        this.mZenMode = this.mNotificationManager.getZenMode();
        if (this.mDeviceHasProductStrategies) {
            this.mVolumeGroupId = getVolumeGroupIdForLegacyStreamType(this.mStreamType);
            this.mAttributes = getAudioAttributesForLegacyStreamType(this.mStreamType);
        }
        this.mMaxStreamVolume = this.mAudioManager.getStreamMaxVolume(this.mStreamType);
        this.mCallback = callback;
        this.mOriginalStreamVolume = this.mAudioManager.getStreamVolume(this.mStreamType);
        this.mLastAudibleStreamVolume = this.mAudioManager.getLastAudibleStreamVolume(this.mStreamType);
        this.mMuted = this.mAudioManager.isStreamMute(this.mStreamType);
        this.mPlaySample = z;
        if (this.mCallback != null) {
            this.mCallback.onMuted(this.mMuted, isZenMuted());
        }
        if (uri == null) {
            if (this.mStreamType == 2) {
                uri = android.provider.Settings.System.DEFAULT_RINGTONE_URI;
            } else if (this.mStreamType == 5) {
                uri = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
            } else {
                uri = android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI;
            }
        }
        this.mDefaultUri = uri;
    }

    private boolean hasAudioProductStrategies() {
        return android.media.AudioManager.getAudioProductStrategies().size() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getVolumeGroupIdForLegacyStreamType(int i) {
        java.util.Iterator<android.media.audiopolicy.AudioProductStrategy> it = android.media.AudioManager.getAudioProductStrategies().iterator();
        while (it.hasNext()) {
            int volumeGroupIdForLegacyStreamType = it.next().getVolumeGroupIdForLegacyStreamType(i);
            if (volumeGroupIdForLegacyStreamType != -1) {
                return volumeGroupIdForLegacyStreamType;
            }
        }
        return ((java.lang.Integer) android.media.AudioManager.getAudioProductStrategies().stream().map(new java.util.function.Function() { // from class: android.preference.SeekBarVolumizer$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer valueOf;
                valueOf = java.lang.Integer.valueOf(((android.media.audiopolicy.AudioProductStrategy) obj).getVolumeGroupIdForAudioAttributes(android.media.audiopolicy.AudioProductStrategy.getDefaultAttributes()));
                return valueOf;
            }
        }).filter(new java.util.function.Predicate() { // from class: android.preference.SeekBarVolumizer$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.preference.SeekBarVolumizer.lambda$getVolumeGroupIdForLegacyStreamType$1((java.lang.Integer) obj);
            }
        }).findFirst().orElse(-1)).intValue();
    }

    static /* synthetic */ boolean lambda$getVolumeGroupIdForLegacyStreamType$1(java.lang.Integer num) {
        return num.intValue() != -1;
    }

    private android.media.AudioAttributes getAudioAttributesForLegacyStreamType(int i) {
        java.util.Iterator<android.media.audiopolicy.AudioProductStrategy> it = android.media.AudioManager.getAudioProductStrategies().iterator();
        while (it.hasNext()) {
            android.media.AudioAttributes audioAttributesForLegacyStreamType = it.next().getAudioAttributesForLegacyStreamType(i);
            if (audioAttributesForLegacyStreamType != null) {
                return audioAttributesForLegacyStreamType;
            }
        }
        return new android.media.AudioAttributes.Builder().setContentType(0).setUsage(0).build();
    }

    private static boolean isNotificationOrRing(int i) {
        return i == 2 || i == 5;
    }

    private static boolean isAlarmsStream(int i) {
        return i == 4;
    }

    private static boolean isMediaStream(int i) {
        return i == 3;
    }

    public void setSeekBar(android.widget.SeekBar seekBar) {
        if (this.mSeekBar != null) {
            this.mSeekBar.setOnSeekBarChangeListener(null);
        }
        this.mSeekBar = seekBar;
        this.mSeekBar.setOnSeekBarChangeListener(null);
        this.mSeekBar.setMax(this.mMaxStreamVolume);
        updateSeekBar();
        this.mSeekBar.setOnSeekBarChangeListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isZenMuted() {
        if ((this.mNotificationOrRing && this.mZenMode == 3) || this.mZenMode == 2) {
            return true;
        }
        if (this.mZenMode == 1) {
            if (!this.mAllowAlarms && isAlarmsStream(this.mStreamType)) {
                return true;
            }
            if (!this.mAllowMedia && isMediaStream(this.mStreamType)) {
                return true;
            }
            if (!this.mAllowRinger && isNotificationOrRing(this.mStreamType)) {
                return true;
            }
        }
        return false;
    }

    protected void updateSeekBar() {
        boolean isZenMuted = isZenMuted();
        this.mSeekBar.setEnabled(!isZenMuted);
        if (isZenMuted) {
            this.mSeekBar.setProgress(this.mLastAudibleStreamVolume, true);
            return;
        }
        if (this.mNotificationOrRing && this.mRingerMode == 1) {
            if (this.mStreamType == 2 || (this.mStreamType == 5 && this.mMuted)) {
                this.mSeekBar.setProgress(0, true);
                return;
            }
            return;
        }
        if (this.mMuted) {
            this.mSeekBar.setProgress(0, true);
        } else {
            this.mSeekBar.setProgress(this.mLastProgress > -1 ? this.mLastProgress : this.mOriginalStreamVolume, true);
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        switch (message.what) {
            case 0:
                if (this.mMuted && this.mLastProgress > 0) {
                    this.mAudioManager.adjustStreamVolume(this.mStreamType, 100, 0);
                } else if (!this.mMuted && this.mLastProgress == 0) {
                    this.mAudioManager.adjustStreamVolume(this.mStreamType, -100, 0);
                }
                this.mAudioManager.setStreamVolume(this.mStreamType, this.mLastProgress, 1024);
                break;
            case 1:
                if (this.mPlaySample) {
                    onStartSample();
                    break;
                }
                break;
            case 2:
                if (this.mPlaySample) {
                    onStopSample();
                    break;
                }
                break;
            case 3:
                if (this.mPlaySample) {
                    onInitSample();
                    break;
                }
                break;
            case 4:
                onUpdateSliderMaybeLater();
                break;
            default:
                android.util.Log.e(TAG, "invalid SeekBarVolumizer message: " + message.what);
                break;
        }
        return true;
    }

    private void onInitSample() {
        synchronized (this) {
            this.mRingtone = android.media.RingtoneManager.getRingtone(this.mContext, this.mDefaultUri);
            if (this.mRingtone != null) {
                this.mRingtone.setStreamType(this.mStreamType);
            }
        }
    }

    private void postStartSample() {
        long j;
        if (this.mHandler == null) {
            return;
        }
        this.mHandler.removeMessages(1);
        android.os.Handler handler = this.mHandler;
        android.os.Message obtainMessage = this.mHandler.obtainMessage(1);
        if (isSamplePlaying()) {
            j = 1000;
        } else {
            j = isDelay() ? START_SAMPLE_DELAY_MS : 0L;
        }
        handler.sendMessageDelayed(obtainMessage, j);
    }

    private void onUpdateSliderMaybeLater() {
        if (isDelay()) {
            postUpdateSliderMaybeLater();
        } else {
            updateSlider();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postUpdateSliderMaybeLater() {
        if (this.mHandler == null) {
            return;
        }
        this.mHandler.removeMessages(4);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(4), 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDelay() {
        long currentTimeMillis = java.lang.System.currentTimeMillis() - sStopVolumeTime;
        return currentTimeMillis >= 0 && currentTimeMillis < DURATION_TO_START_DELAYING;
    }

    private void setStopVolumeTime() {
        if (this.mStreamType == 0 || this.mStreamType == 2 || this.mStreamType == 5 || this.mStreamType == 4) {
            sStopVolumeTime = java.lang.System.currentTimeMillis();
        }
    }

    private void onStartSample() {
        if (!isSamplePlaying()) {
            if (this.mCallback != null) {
                this.mCallback.onSampleStarting(this);
            }
            synchronized (this) {
                if (this.mRingtone != null) {
                    try {
                        this.mRingtone.setAudioAttributes(new android.media.AudioAttributes.Builder(this.mRingtone.getAudioAttributes()).setFlags(128).addTag("VX_AOSP_SAMPLESOUND").build());
                        this.mRingtone.play();
                    } catch (java.lang.Throwable th) {
                        android.util.Log.w(TAG, "Error playing ringtone, stream " + this.mStreamType, th);
                    }
                }
            }
        }
    }

    private void postStopSample() {
        if (this.mHandler == null) {
            return;
        }
        setStopVolumeTime();
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
    }

    private void onStopSample() {
        synchronized (this) {
            if (this.mRingtone != null) {
                this.mRingtone.stop();
            }
        }
    }

    public void stop() {
        if (this.mHandler == null) {
            return;
        }
        postStopSample();
        this.mContext.getContentResolver().unregisterContentObserver(this.mVolumeObserver);
        this.mReceiver.setListening(false);
        if (this.mDeviceHasProductStrategies) {
            unregisterVolumeGroupCb();
        }
        this.mSeekBar.setOnSeekBarChangeListener(null);
        this.mHandler.getLooper().quitSafely();
        this.mHandler = null;
        this.mVolumeObserver = null;
    }

    public void start() {
        if (this.mHandler != null) {
            return;
        }
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("SeekBarVolumizer.CallbackHandler");
        handlerThread.start();
        this.mHandler = new android.os.Handler(handlerThread.getLooper(), this);
        this.mHandler.sendEmptyMessage(3);
        this.mVolumeObserver = new android.preference.SeekBarVolumizer.Observer(this.mHandler);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor(android.provider.Settings.System.VOLUME_SETTINGS_INT[this.mStreamType]), false, this.mVolumeObserver);
        this.mReceiver.setListening(true);
        if (this.mDeviceHasProductStrategies) {
            registerVolumeGroupCb();
        }
    }

    public void revertVolume() {
        this.mAudioManager.setStreamVolume(this.mStreamType, this.mOriginalStreamVolume, 0);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z) {
        if (z) {
            postSetVolume(i);
        }
        if (this.mCallback != null) {
            this.mCallback.onProgressChanged(seekBar, i, z);
        }
    }

    private void postSetVolume(int i) {
        if (this.mHandler == null) {
            return;
        }
        this.mLastProgress = i;
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(4);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0), isDelay() ? SET_STREAM_VOLUME_DELAY_MS : 0L);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
        if (this.mCallback != null) {
            this.mCallback.onStartTrackingTouch(this);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
        postStartSample();
        if (this.mCallback != null) {
            this.mCallback.onStopTrackingTouch(this);
        }
    }

    public boolean isSamplePlaying() {
        boolean z;
        synchronized (this) {
            z = this.mRingtone != null && this.mRingtone.isPlaying();
        }
        return z;
    }

    public void startSample() {
        postStartSample();
    }

    public void stopSample() {
        postStopSample();
    }

    public android.widget.SeekBar getSeekBar() {
        return this.mSeekBar;
    }

    public void changeVolumeBy(int i) {
        this.mSeekBar.incrementProgressBy(i);
        postSetVolume(this.mSeekBar.getProgress());
        postStartSample();
        this.mVolumeBeforeMute = -1;
    }

    public void muteVolume() {
        if (this.mVolumeBeforeMute != -1) {
            this.mSeekBar.setProgress(this.mVolumeBeforeMute, true);
            postSetVolume(this.mVolumeBeforeMute);
            postStartSample();
            this.mVolumeBeforeMute = -1;
            return;
        }
        this.mVolumeBeforeMute = this.mSeekBar.getProgress();
        this.mSeekBar.setProgress(0, true);
        postStopSample();
        postSetVolume(0);
    }

    public void onSaveInstanceState(android.preference.VolumePreference.VolumeStore volumeStore) {
        if (this.mLastProgress >= 0) {
            volumeStore.volume = this.mLastProgress;
            volumeStore.originalVolume = this.mOriginalStreamVolume;
        }
    }

    public void onRestoreInstanceState(android.preference.VolumePreference.VolumeStore volumeStore) {
        if (volumeStore.volume != -1) {
            this.mOriginalStreamVolume = volumeStore.originalVolume;
            this.mLastProgress = volumeStore.volume;
            postSetVolume(this.mLastProgress);
        }
    }

    private final class H extends android.os.Handler {
        private static final int UPDATE_SLIDER = 1;

        private H() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (message.what == 1 && android.preference.SeekBarVolumizer.this.mSeekBar != null) {
                android.preference.SeekBarVolumizer.this.mLastProgress = message.arg1;
                android.preference.SeekBarVolumizer.this.mLastAudibleStreamVolume = message.arg2;
                boolean booleanValue = ((java.lang.Boolean) message.obj).booleanValue();
                if (booleanValue != android.preference.SeekBarVolumizer.this.mMuted) {
                    android.preference.SeekBarVolumizer.this.mMuted = booleanValue;
                    if (android.preference.SeekBarVolumizer.this.mCallback != null) {
                        android.preference.SeekBarVolumizer.this.mCallback.onMuted(android.preference.SeekBarVolumizer.this.mMuted, android.preference.SeekBarVolumizer.this.isZenMuted());
                    }
                }
                android.preference.SeekBarVolumizer.this.updateSeekBar();
            }
        }

        public void postUpdateSlider(int i, int i2, boolean z) {
            obtainMessage(1, i, i2, java.lang.Boolean.valueOf(z)).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSlider() {
        if (this.mSeekBar != null && this.mAudioManager != null) {
            this.mUiHandler.postUpdateSlider(this.mAudioManager.getStreamVolume(this.mStreamType), this.mAudioManager.getLastAudibleStreamVolume(this.mStreamType), this.mAudioManager.isStreamMute(this.mStreamType));
        }
    }

    private final class Observer extends android.database.ContentObserver {
        public Observer(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            android.preference.SeekBarVolumizer.this.updateSlider();
        }
    }

    private final class Receiver extends android.content.BroadcastReceiver {
        private boolean mListening;

        private Receiver() {
        }

        public void setListening(boolean z) {
            if (this.mListening == z) {
                return;
            }
            this.mListening = z;
            if (z) {
                android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.media.VOLUME_CHANGED_ACTION");
                intentFilter.addAction(android.media.AudioManager.INTERNAL_RINGER_MODE_CHANGED_ACTION);
                intentFilter.addAction(android.app.NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED);
                intentFilter.addAction(android.app.NotificationManager.ACTION_NOTIFICATION_POLICY_CHANGED);
                intentFilter.addAction(android.media.AudioManager.STREAM_DEVICES_CHANGED_ACTION);
                android.preference.SeekBarVolumizer.this.mContext.registerReceiver(this, intentFilter);
                return;
            }
            android.preference.SeekBarVolumizer.this.mContext.unregisterReceiver(this);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            if ("android.media.VOLUME_CHANGED_ACTION".equals(action)) {
                int intExtra = intent.getIntExtra(android.media.AudioManager.EXTRA_VOLUME_STREAM_TYPE, -1);
                int intExtra2 = intent.getIntExtra(android.media.AudioManager.EXTRA_VOLUME_STREAM_VALUE, -1);
                if (android.preference.SeekBarVolumizer.this.mDeviceHasProductStrategies && !android.preference.SeekBarVolumizer.this.isDelay()) {
                    updateVolumeSlider(intExtra, intExtra2);
                    return;
                }
                return;
            }
            if (android.media.AudioManager.INTERNAL_RINGER_MODE_CHANGED_ACTION.equals(action)) {
                if (android.preference.SeekBarVolumizer.this.mNotificationOrRing) {
                    android.preference.SeekBarVolumizer.this.mRingerMode = android.preference.SeekBarVolumizer.this.mAudioManager.getRingerModeInternal();
                }
                if (android.preference.SeekBarVolumizer.this.mAffectedByRingerMode) {
                    android.preference.SeekBarVolumizer.this.updateSlider();
                    return;
                }
                return;
            }
            if (!android.media.AudioManager.STREAM_DEVICES_CHANGED_ACTION.equals(action)) {
                if (android.app.NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED.equals(action)) {
                    android.preference.SeekBarVolumizer.this.mZenMode = android.preference.SeekBarVolumizer.this.mNotificationManager.getZenMode();
                    android.preference.SeekBarVolumizer.this.updateSlider();
                    return;
                } else {
                    if (android.app.NotificationManager.ACTION_NOTIFICATION_POLICY_CHANGED.equals(action)) {
                        android.preference.SeekBarVolumizer.this.mNotificationPolicy = android.preference.SeekBarVolumizer.this.mNotificationManager.getConsolidatedNotificationPolicy();
                        android.preference.SeekBarVolumizer.this.mAllowAlarms = (android.preference.SeekBarVolumizer.this.mNotificationPolicy.priorityCategories & 32) != 0;
                        android.preference.SeekBarVolumizer.this.mAllowMedia = (android.preference.SeekBarVolumizer.this.mNotificationPolicy.priorityCategories & 64) != 0;
                        android.preference.SeekBarVolumizer.this.mAllowRinger = !android.service.notification.ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(android.preference.SeekBarVolumizer.this.mNotificationPolicy);
                        android.preference.SeekBarVolumizer.this.updateSlider();
                        return;
                    }
                    return;
                }
            }
            int intExtra3 = intent.getIntExtra(android.media.AudioManager.EXTRA_VOLUME_STREAM_TYPE, -1);
            if (android.preference.SeekBarVolumizer.this.mDeviceHasProductStrategies) {
                if (android.preference.SeekBarVolumizer.this.isDelay()) {
                    android.preference.SeekBarVolumizer.this.postUpdateSliderMaybeLater();
                    return;
                } else {
                    updateVolumeSlider(intExtra3, android.preference.SeekBarVolumizer.this.mAudioManager.getStreamVolume(intExtra3));
                    return;
                }
            }
            int volumeGroupIdForLegacyStreamType = android.preference.SeekBarVolumizer.this.getVolumeGroupIdForLegacyStreamType(intExtra3);
            if (volumeGroupIdForLegacyStreamType != -1 && volumeGroupIdForLegacyStreamType == android.preference.SeekBarVolumizer.this.mVolumeGroupId) {
                int streamVolume = android.preference.SeekBarVolumizer.this.mAudioManager.getStreamVolume(intExtra3);
                if (!android.preference.SeekBarVolumizer.this.isDelay()) {
                    updateVolumeSlider(intExtra3, streamVolume);
                }
            }
        }

        private void updateVolumeSlider(int i, int i2) {
            boolean z = true;
            boolean z2 = i == android.preference.SeekBarVolumizer.this.mStreamType;
            if (android.preference.SeekBarVolumizer.this.mSeekBar != null && z2 && i2 != -1) {
                if (!android.preference.SeekBarVolumizer.this.mAudioManager.isStreamMute(android.preference.SeekBarVolumizer.this.mStreamType) && i2 != 0) {
                    z = false;
                }
                android.preference.SeekBarVolumizer.this.mUiHandler.postUpdateSlider(i2, android.preference.SeekBarVolumizer.this.mLastAudibleStreamVolume, z);
            }
        }
    }

    private void registerVolumeGroupCb() {
        if (this.mVolumeGroupId != -1) {
            this.mAudioManager.registerVolumeGroupCallback(new android.app.PendingIntent$$ExternalSyntheticLambda0(), this.mVolumeGroupCallback);
            updateSlider();
        }
    }

    private void unregisterVolumeGroupCb() {
        if (this.mVolumeGroupId != -1) {
            this.mAudioManager.unregisterVolumeGroupCallback(this.mVolumeGroupCallback);
        }
    }

    private class VolumeHandler extends android.os.Handler {
        private VolumeHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
            switch (message.what) {
                case 1:
                    if (android.preference.SeekBarVolumizer.this.mVolumeGroupId == ((java.lang.Integer) someArgs.arg1).intValue() && android.preference.SeekBarVolumizer.this.mVolumeGroupId != -1) {
                        android.preference.SeekBarVolumizer.this.updateSlider();
                        break;
                    }
                    break;
            }
        }
    }
}
