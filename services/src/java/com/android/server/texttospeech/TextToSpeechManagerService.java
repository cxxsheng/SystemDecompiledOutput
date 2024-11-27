package com.android.server.texttospeech;

/* loaded from: classes2.dex */
public final class TextToSpeechManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.texttospeech.TextToSpeechManagerService, com.android.server.texttospeech.TextToSpeechManagerPerUserService> {
    private static final java.lang.String TAG = com.android.server.texttospeech.TextToSpeechManagerService.class.getSimpleName();

    public TextToSpeechManagerService(@android.annotation.NonNull android.content.Context context) {
        super(context, null, null);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("texttospeech", new com.android.server.texttospeech.TextToSpeechManagerService.TextToSpeechManagerServiceStub());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.texttospeech.TextToSpeechManagerPerUserService newServiceLocked(int i, boolean z) {
        return new com.android.server.texttospeech.TextToSpeechManagerPerUserService(this, this.mLock, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class TextToSpeechManagerServiceStub extends android.speech.tts.ITextToSpeechManager.Stub {
        private TextToSpeechManagerServiceStub() {
        }

        public void createSession(java.lang.String str, final android.speech.tts.ITextToSpeechSessionCallback iTextToSpeechSessionCallback) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.texttospeech.TextToSpeechManagerService.this).mLock) {
                try {
                    if (str != null) {
                        com.android.server.texttospeech.TextToSpeechManagerPerUserService textToSpeechManagerPerUserService = (com.android.server.texttospeech.TextToSpeechManagerPerUserService) com.android.server.texttospeech.TextToSpeechManagerService.this.getServiceForUserLocked(android.os.UserHandle.getCallingUserId());
                        if (textToSpeechManagerPerUserService != null) {
                            textToSpeechManagerPerUserService.createSessionLocked(str, iTextToSpeechSessionCallback);
                        } else {
                            com.android.server.texttospeech.TextToSpeechManagerPerUserService.runSessionCallbackMethod(new com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable() { // from class: com.android.server.texttospeech.TextToSpeechManagerService$TextToSpeechManagerServiceStub$$ExternalSyntheticLambda1
                                @Override // com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable
                                public final void runOrThrow() {
                                    iTextToSpeechSessionCallback.onError("Service is not available for user");
                                }
                            });
                        }
                        return;
                    }
                    com.android.server.texttospeech.TextToSpeechManagerPerUserService.runSessionCallbackMethod(new com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable() { // from class: com.android.server.texttospeech.TextToSpeechManagerService$TextToSpeechManagerServiceStub$$ExternalSyntheticLambda0
                        @Override // com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable
                        public final void runOrThrow() {
                            iTextToSpeechSessionCallback.onError("Engine cannot be null");
                        }
                    });
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
