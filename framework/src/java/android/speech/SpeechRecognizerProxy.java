package android.speech;

/* loaded from: classes3.dex */
class SpeechRecognizerProxy extends android.speech.SpeechRecognizer {
    private final android.util.CloseGuard mCloseGuard = new android.util.CloseGuard();
    private final android.speech.SpeechRecognizer mDelegate;

    SpeechRecognizerProxy(android.speech.SpeechRecognizer speechRecognizer) {
        this.mDelegate = speechRecognizer;
        this.mCloseGuard.open("SpeechRecognizer#destroy()");
    }

    @Override // android.speech.SpeechRecognizer
    public void setRecognitionListener(android.speech.RecognitionListener recognitionListener) {
        this.mDelegate.setRecognitionListener(recognitionListener);
    }

    @Override // android.speech.SpeechRecognizer
    public void startListening(android.content.Intent intent) {
        this.mDelegate.startListening(intent);
    }

    @Override // android.speech.SpeechRecognizer
    public void stopListening() {
        this.mDelegate.stopListening();
    }

    @Override // android.speech.SpeechRecognizer
    public void cancel() {
        this.mDelegate.cancel();
    }

    @Override // android.speech.SpeechRecognizer
    public void destroy() {
        try {
            this.mCloseGuard.close();
            this.mDelegate.destroy();
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    @Override // android.speech.SpeechRecognizer
    public void checkRecognitionSupport(android.content.Intent intent, java.util.concurrent.Executor executor, android.speech.RecognitionSupportCallback recognitionSupportCallback) {
        this.mDelegate.checkRecognitionSupport(intent, executor, recognitionSupportCallback);
    }

    @Override // android.speech.SpeechRecognizer
    public void triggerModelDownload(android.content.Intent intent) {
        this.mDelegate.triggerModelDownload(intent);
    }

    @Override // android.speech.SpeechRecognizer
    public void triggerModelDownload(android.content.Intent intent, java.util.concurrent.Executor executor, android.speech.ModelDownloadListener modelDownloadListener) {
        this.mDelegate.triggerModelDownload(intent, executor, modelDownloadListener);
    }

    @Override // android.speech.SpeechRecognizer
    public void setTemporaryOnDeviceRecognizer(android.content.ComponentName componentName) {
        this.mDelegate.setTemporaryOnDeviceRecognizer(componentName);
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            this.mCloseGuard.warnIfOpen();
            destroy();
        } finally {
            super.finalize();
        }
    }
}
