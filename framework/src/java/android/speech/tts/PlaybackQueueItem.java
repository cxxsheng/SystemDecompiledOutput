package android.speech.tts;

/* loaded from: classes3.dex */
abstract class PlaybackQueueItem implements java.lang.Runnable {
    private final java.lang.Object mCallerIdentity;
    private final android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher mDispatcher;

    @Override // java.lang.Runnable
    public abstract void run();

    abstract void stop(int i);

    PlaybackQueueItem(android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, java.lang.Object obj) {
        this.mDispatcher = utteranceProgressDispatcher;
        this.mCallerIdentity = obj;
    }

    java.lang.Object getCallerIdentity() {
        return this.mCallerIdentity;
    }

    protected android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher getDispatcher() {
        return this.mDispatcher;
    }
}
