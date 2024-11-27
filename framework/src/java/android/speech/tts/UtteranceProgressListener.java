package android.speech.tts;

/* loaded from: classes3.dex */
public abstract class UtteranceProgressListener {
    public abstract void onDone(java.lang.String str);

    @java.lang.Deprecated
    public abstract void onError(java.lang.String str);

    public abstract void onStart(java.lang.String str);

    public void onError(java.lang.String str, int i) {
        onError(str);
    }

    public void onStop(java.lang.String str, boolean z) {
    }

    public void onBeginSynthesis(java.lang.String str, int i, int i2, int i3) {
    }

    public void onAudioAvailable(java.lang.String str, byte[] bArr) {
    }

    public void onRangeStart(java.lang.String str, int i, int i2, int i3) {
        onUtteranceRangeStart(str, i, i2);
    }

    @java.lang.Deprecated
    public void onUtteranceRangeStart(java.lang.String str, int i, int i2) {
    }

    static android.speech.tts.UtteranceProgressListener from(final android.speech.tts.TextToSpeech.OnUtteranceCompletedListener onUtteranceCompletedListener) {
        return new android.speech.tts.UtteranceProgressListener() { // from class: android.speech.tts.UtteranceProgressListener.1
            @Override // android.speech.tts.UtteranceProgressListener
            public synchronized void onDone(java.lang.String str) {
                android.speech.tts.TextToSpeech.OnUtteranceCompletedListener.this.onUtteranceCompleted(str);
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onError(java.lang.String str) {
                android.speech.tts.TextToSpeech.OnUtteranceCompletedListener.this.onUtteranceCompleted(str);
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onStart(java.lang.String str) {
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onStop(java.lang.String str, boolean z) {
                android.speech.tts.TextToSpeech.OnUtteranceCompletedListener.this.onUtteranceCompleted(str);
            }
        };
    }
}
