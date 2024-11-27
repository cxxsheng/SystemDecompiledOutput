package android.speech.tts;

/* loaded from: classes3.dex */
class SilencePlaybackQueueItem extends android.speech.tts.PlaybackQueueItem {
    private final android.os.ConditionVariable mCondVar;
    private final long mSilenceDurationMs;

    SilencePlaybackQueueItem(android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, java.lang.Object obj, long j) {
        super(utteranceProgressDispatcher, obj);
        this.mCondVar = new android.os.ConditionVariable();
        this.mSilenceDurationMs = j;
    }

    @Override // android.speech.tts.PlaybackQueueItem, java.lang.Runnable
    public void run() {
        boolean z;
        getDispatcher().dispatchOnStart();
        if (this.mSilenceDurationMs <= 0) {
            z = false;
        } else {
            z = this.mCondVar.block(this.mSilenceDurationMs);
        }
        if (z) {
            getDispatcher().dispatchOnStop();
        } else {
            getDispatcher().dispatchOnSuccess();
        }
    }

    @Override // android.speech.tts.PlaybackQueueItem
    void stop(int i) {
        this.mCondVar.open();
    }
}
