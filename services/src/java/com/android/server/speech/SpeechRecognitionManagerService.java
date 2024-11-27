package com.android.server.speech;

/* loaded from: classes2.dex */
public final class SpeechRecognitionManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.speech.SpeechRecognitionManagerService, com.android.server.speech.SpeechRecognitionManagerServiceImpl> {
    private static final int MAX_TEMP_SERVICE_SUBSTITUTION_DURATION_MS = 60000;
    private static final java.lang.String TAG = com.android.server.speech.SpeechRecognitionManagerService.class.getSimpleName();

    public SpeechRecognitionManagerService(@android.annotation.NonNull android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultModuleMetadataProvider), null);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("speech_recognition", new com.android.server.speech.SpeechRecognitionManagerService.SpeechRecognitionManagerServiceStub());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_SPEECH_RECOGNITION", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected int getMaximumTemporaryServiceDurationMs() {
        return 60000;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.speech.SpeechRecognitionManagerServiceImpl newServiceLocked(int i, boolean z) {
        return new com.android.server.speech.SpeechRecognitionManagerServiceImpl(this, this.mLock, i);
    }

    final class SpeechRecognitionManagerServiceStub extends android.speech.IRecognitionServiceManager.Stub {
        SpeechRecognitionManagerServiceStub() {
        }

        public void createSession(android.content.ComponentName componentName, android.os.IBinder iBinder, boolean z, android.speech.IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.speech.SpeechRecognitionManagerService.this).mLock) {
                ((com.android.server.speech.SpeechRecognitionManagerServiceImpl) com.android.server.speech.SpeechRecognitionManagerService.this.getServiceForUserLocked(callingUserId)).createSessionLocked(componentName, iBinder, z, iRecognitionServiceManagerCallback);
            }
        }

        public void setTemporaryComponent(android.content.ComponentName componentName) {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            if (componentName == null) {
                com.android.server.speech.SpeechRecognitionManagerService.this.resetTemporaryService(callingUserId);
                android.util.Slog.i(com.android.server.speech.SpeechRecognitionManagerService.TAG, "Reset temporary service for user " + callingUserId);
                return;
            }
            com.android.server.speech.SpeechRecognitionManagerService.this.setTemporaryService(callingUserId, componentName.flattenToString(), 60000);
            android.util.Slog.i(com.android.server.speech.SpeechRecognitionManagerService.TAG, "SpeechRecognition temporarily set to " + componentName + " for 60000ms");
        }
    }
}
