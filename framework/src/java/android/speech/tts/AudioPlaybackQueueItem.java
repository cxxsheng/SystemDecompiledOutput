package android.speech.tts;

/* loaded from: classes3.dex */
class AudioPlaybackQueueItem extends android.speech.tts.PlaybackQueueItem {
    private static final java.lang.String TAG = "TTS.AudioQueueItem";
    private final android.speech.tts.TextToSpeechService.AudioOutputParams mAudioParams;
    private final android.content.Context mContext;
    private final android.os.ConditionVariable mDone;
    private volatile boolean mFinished;
    private android.media.MediaPlayer mPlayer;
    private final android.net.Uri mUri;

    AudioPlaybackQueueItem(android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, java.lang.Object obj, android.content.Context context, android.net.Uri uri, android.speech.tts.TextToSpeechService.AudioOutputParams audioOutputParams) {
        super(utteranceProgressDispatcher, obj);
        this.mContext = context;
        this.mUri = uri;
        this.mAudioParams = audioOutputParams;
        this.mDone = new android.os.ConditionVariable();
        this.mPlayer = null;
        this.mFinished = false;
    }

    @Override // android.speech.tts.PlaybackQueueItem, java.lang.Runnable
    public void run() {
        android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher dispatcher = getDispatcher();
        dispatcher.dispatchOnStart();
        int i = this.mAudioParams.mSessionId;
        android.content.Context context = this.mContext;
        android.net.Uri uri = this.mUri;
        android.media.AudioAttributes audioAttributes = this.mAudioParams.mAudioAttributes;
        if (i <= 0) {
            i = 0;
        }
        this.mPlayer = android.media.MediaPlayer.create(context, uri, null, audioAttributes, i);
        if (this.mPlayer == null) {
            dispatcher.dispatchOnError(-5);
            return;
        }
        try {
            this.mPlayer.setOnErrorListener(new android.media.MediaPlayer.OnErrorListener() { // from class: android.speech.tts.AudioPlaybackQueueItem.1
                @Override // android.media.MediaPlayer.OnErrorListener
                public boolean onError(android.media.MediaPlayer mediaPlayer, int i2, int i3) {
                    android.util.Log.w(android.speech.tts.AudioPlaybackQueueItem.TAG, "Audio playback error: " + i2 + ", " + i3);
                    android.speech.tts.AudioPlaybackQueueItem.this.mDone.open();
                    return true;
                }
            });
            this.mPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() { // from class: android.speech.tts.AudioPlaybackQueueItem.2
                @Override // android.media.MediaPlayer.OnCompletionListener
                public void onCompletion(android.media.MediaPlayer mediaPlayer) {
                    android.speech.tts.AudioPlaybackQueueItem.this.mFinished = true;
                    android.speech.tts.AudioPlaybackQueueItem.this.mDone.open();
                }
            });
            setupVolume(this.mPlayer, this.mAudioParams.mVolume, this.mAudioParams.mPan);
            this.mPlayer.start();
            this.mDone.block();
            finish();
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "MediaPlayer failed", e);
            this.mDone.open();
        }
        if (this.mFinished) {
            dispatcher.dispatchOnSuccess();
        } else {
            dispatcher.dispatchOnStop();
        }
    }

    private static void setupVolume(android.media.MediaPlayer mediaPlayer, float f, float f2) {
        float f3;
        float clip = clip(f, 0.0f, 1.0f);
        float clip2 = clip(f2, -1.0f, 1.0f);
        if (clip2 > 0.0f) {
            float f4 = (1.0f - clip2) * clip;
            f3 = clip;
            clip = f4;
        } else if (clip2 >= 0.0f) {
            f3 = clip;
        } else {
            f3 = (clip2 + 1.0f) * clip;
        }
        mediaPlayer.setVolume(clip, f3);
    }

    private static final float clip(float f, float f2, float f3) {
        return f < f2 ? f2 : f < f3 ? f : f3;
    }

    private void finish() {
        try {
            this.mPlayer.stop();
        } catch (java.lang.IllegalStateException e) {
        }
        this.mPlayer.release();
    }

    @Override // android.speech.tts.PlaybackQueueItem
    void stop(int i) {
        this.mDone.open();
    }
}
