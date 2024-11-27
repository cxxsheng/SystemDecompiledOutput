package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerMiddlewarePermission implements com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal, com.android.server.soundtrigger_middleware.Dumpable {
    private static final java.lang.String TAG = "SoundTriggerMiddlewarePermission";

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal mDelegate;

    public SoundTriggerMiddlewarePermission(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal, @android.annotation.NonNull android.content.Context context) {
        this.mDelegate = iSoundTriggerMiddlewareInternal;
        this.mContext = context;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    @android.annotation.NonNull
    public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModules() {
        enforcePermissionForPreflight(this.mContext, getIdentity(), "android.permission.CAPTURE_AUDIO_HOTWORD");
        return this.mDelegate.listModules();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    @android.annotation.NonNull
    public android.media.soundtrigger_middleware.ISoundTriggerModule attach(int i, @android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
        android.media.permission.Identity identity = getIdentity();
        enforcePermissionsForPreflight(identity);
        com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.ModuleWrapper moduleWrapper = new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.ModuleWrapper(identity, iSoundTriggerCallback, z);
        return moduleWrapper.attach(this.mDelegate.attach(i, moduleWrapper.getCallbackWrapper(), z));
    }

    public java.lang.String toString() {
        return java.util.Objects.toString(this.mDelegate);
    }

    @android.annotation.NonNull
    private static android.media.permission.Identity getIdentity() {
        return android.media.permission.IdentityContext.getNonNull();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforcePermissionsForPreflight(@android.annotation.NonNull android.media.permission.Identity identity) {
        enforcePermissionForPreflight(this.mContext, identity, "android.permission.RECORD_AUDIO");
        enforcePermissionForPreflight(this.mContext, identity, "android.permission.CAPTURE_AUDIO_HOTWORD");
    }

    void enforcePermissionsForDataDelivery(@android.annotation.NonNull android.media.permission.Identity identity, @android.annotation.NonNull java.lang.String str) {
        enforceSoundTriggerRecordAudioPermissionForDataDelivery(identity, str);
        enforcePermissionForDataDelivery(this.mContext, identity, "android.permission.CAPTURE_AUDIO_HOTWORD", str);
    }

    private static void enforcePermissionForDataDelivery(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.media.permission.Identity identity, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        if (android.media.permission.PermissionUtil.checkPermissionForDataDelivery(context, identity, str, str2) != 0) {
            throw new java.lang.SecurityException(java.lang.String.format("Failed to obtain permission %s for identity %s", str, com.android.server.soundtrigger_middleware.ObjectPrinter.print(identity, 16)));
        }
    }

    private static void enforceSoundTriggerRecordAudioPermissionForDataDelivery(@android.annotation.NonNull android.media.permission.Identity identity, @android.annotation.NonNull java.lang.String str) {
        if (((com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class)).checkSoundTriggerRecordAudioPermissionForDataDelivery(identity.uid, identity.packageName, identity.attributionTag, str) != 0) {
            throw new java.lang.SecurityException(java.lang.String.format("Failed to obtain permission RECORD_AUDIO for identity %s", com.android.server.soundtrigger_middleware.ObjectPrinter.print(identity, 16)));
        }
    }

    private static void enforcePermissionForPreflight(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.media.permission.Identity identity, @android.annotation.NonNull java.lang.String str) {
        switch (android.media.permission.PermissionUtil.checkPermissionForPreflight(context, identity, str)) {
            case 0:
            case 1:
                return;
            case 2:
                throw new java.lang.SecurityException(java.lang.String.format("Failed to obtain permission %s for identity %s", str, com.android.server.soundtrigger_middleware.ObjectPrinter.print(identity, 16)));
            default:
                throw new java.lang.RuntimeException("Unexpected perimission check result.");
        }
    }

    @Override // com.android.server.soundtrigger_middleware.Dumpable
    public void dump(java.io.PrintWriter printWriter) {
        if (this.mDelegate instanceof com.android.server.soundtrigger_middleware.Dumpable) {
            ((com.android.server.soundtrigger_middleware.Dumpable) this.mDelegate).dump(printWriter);
        }
    }

    private class ModuleWrapper extends android.media.soundtrigger_middleware.ISoundTriggerModule.Stub {

        @android.annotation.NonNull
        private final com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.ModuleWrapper.CallbackWrapper mCallbackWrapper;
        private android.media.soundtrigger_middleware.ISoundTriggerModule mDelegate;
        private final boolean mIsTrusted;

        @android.annotation.NonNull
        private final android.media.permission.Identity mOriginatorIdentity;

        ModuleWrapper(@android.annotation.NonNull android.media.permission.Identity identity, @android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
            this.mOriginatorIdentity = identity;
            this.mCallbackWrapper = new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.ModuleWrapper.CallbackWrapper(iSoundTriggerCallback);
            this.mIsTrusted = z;
        }

        com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.ModuleWrapper attach(@android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerModule iSoundTriggerModule) {
            this.mDelegate = iSoundTriggerModule;
            return this;
        }

        android.media.soundtrigger_middleware.ISoundTriggerCallback getCallbackWrapper() {
            return this.mCallbackWrapper;
        }

        public int loadModel(@android.annotation.NonNull android.media.soundtrigger.SoundModel soundModel) throws android.os.RemoteException {
            enforcePermissions();
            return this.mDelegate.loadModel(soundModel);
        }

        public int loadPhraseModel(@android.annotation.NonNull android.media.soundtrigger.PhraseSoundModel phraseSoundModel) throws android.os.RemoteException {
            enforcePermissions();
            return this.mDelegate.loadPhraseModel(phraseSoundModel);
        }

        public void unloadModel(int i) throws android.os.RemoteException {
            this.mDelegate.unloadModel(i);
        }

        public android.os.IBinder startRecognition(int i, @android.annotation.NonNull android.media.soundtrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
            enforcePermissions();
            return this.mDelegate.startRecognition(i, recognitionConfig);
        }

        public void stopRecognition(int i) throws android.os.RemoteException {
            this.mDelegate.stopRecognition(i);
        }

        public void forceRecognitionEvent(int i) throws android.os.RemoteException {
            enforcePermissions();
            this.mDelegate.forceRecognitionEvent(i);
        }

        public void setModelParameter(int i, int i2, int i3) throws android.os.RemoteException {
            enforcePermissions();
            this.mDelegate.setModelParameter(i, i2, i3);
        }

        public int getModelParameter(int i, int i2) throws android.os.RemoteException {
            enforcePermissions();
            return this.mDelegate.getModelParameter(i, i2);
        }

        @android.annotation.Nullable
        public android.media.soundtrigger.ModelParameterRange queryModelParameterSupport(int i, int i2) throws android.os.RemoteException {
            enforcePermissions();
            return this.mDelegate.queryModelParameterSupport(i, i2);
        }

        public void detach() throws android.os.RemoteException {
            this.mDelegate.detach();
        }

        public java.lang.String toString() {
            return java.util.Objects.toString(this.mDelegate);
        }

        private void enforcePermissions() {
            com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.this.enforcePermissionsForPreflight(this.mOriginatorIdentity);
        }

        private class CallbackWrapper implements android.media.soundtrigger_middleware.ISoundTriggerCallback {
            private final android.media.soundtrigger_middleware.ISoundTriggerCallback mDelegate;

            private CallbackWrapper(android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback) {
                this.mDelegate = iSoundTriggerCallback;
            }

            public void onRecognition(int i, android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys, int i2) throws android.os.RemoteException {
                enforcePermissions("Sound trigger recognition.");
                this.mDelegate.onRecognition(i, recognitionEventSys, i2);
            }

            public void onPhraseRecognition(int i, android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) throws android.os.RemoteException {
                enforcePermissions("Sound trigger phrase recognition.");
                this.mDelegate.onPhraseRecognition(i, phraseRecognitionEventSys, i2);
            }

            public void onResourcesAvailable() throws android.os.RemoteException {
                this.mDelegate.onResourcesAvailable();
            }

            public void onModelUnloaded(int i) throws android.os.RemoteException {
                this.mDelegate.onModelUnloaded(i);
            }

            public void onModuleDied() throws android.os.RemoteException {
                this.mDelegate.onModuleDied();
            }

            public android.os.IBinder asBinder() {
                return this.mDelegate.asBinder();
            }

            public java.lang.String toString() {
                return this.mDelegate.toString();
            }

            private void enforcePermissions(java.lang.String str) {
                if (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.ModuleWrapper.this.mIsTrusted) {
                    com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.this.enforcePermissionsForPreflight(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.ModuleWrapper.this.mOriginatorIdentity);
                } else {
                    com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.this.enforcePermissionsForDataDelivery(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission.ModuleWrapper.this.mOriginatorIdentity, str);
                }
            }
        }
    }
}
