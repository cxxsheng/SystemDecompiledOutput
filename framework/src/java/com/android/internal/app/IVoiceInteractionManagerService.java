package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IVoiceInteractionManagerService extends android.os.IInterface {
    boolean activeServiceSupportsAssist() throws android.os.RemoteException;

    boolean activeServiceSupportsLaunchFromKeyguard() throws android.os.RemoteException;

    void closeSystemDialogs(android.os.IBinder iBinder) throws android.os.RemoteException;

    com.android.internal.app.IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator(android.media.permission.Identity identity, android.os.IBinder iBinder, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties) throws android.os.RemoteException;

    int deleteKeyphraseSoundModel(int i, java.lang.String str) throws android.os.RemoteException;

    boolean deliverNewSession(android.os.IBinder iBinder, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor) throws android.os.RemoteException;

    void destroyDetector(android.os.IBinder iBinder) throws android.os.RemoteException;

    void disableVisualQueryDetection() throws android.os.RemoteException;

    void enableVisualQueryDetection(com.android.internal.app.IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) throws android.os.RemoteException;

    void finish(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean getAccessibilityDetectionEnabled() throws android.os.RemoteException;

    android.content.ComponentName getActiveServiceComponentName() throws android.os.RemoteException;

    void getActiveServiceSupportedActions(java.util.List<java.lang.String> list, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) throws android.os.RemoteException;

    int getDisabledShowContext() throws android.os.RemoteException;

    android.hardware.soundtrigger.KeyphraseMetadata getEnrolledKeyphraseMetadata(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, java.lang.String str) throws android.os.RemoteException;

    int getUserDisabledShowContext() throws android.os.RemoteException;

    void hideCurrentSession() throws android.os.RemoteException;

    boolean hideSessionFromSession(android.os.IBinder iBinder) throws android.os.RemoteException;

    void initAndVerifyDetector(android.media.permission.Identity identity, android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IBinder iBinder, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) throws android.os.RemoteException;

    boolean isEnrolledForKeyphrase(int i, java.lang.String str) throws android.os.RemoteException;

    boolean isSessionRunning() throws android.os.RemoteException;

    void launchVoiceAssistFromKeyguard() throws android.os.RemoteException;

    java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties(android.media.permission.Identity identity) throws android.os.RemoteException;

    void notifyActivityEventChanged(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void onLockscreenShown() throws android.os.RemoteException;

    void performDirectAction(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i, android.os.IBinder iBinder2, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException;

    void registerAccessibilityDetectionSettingsListener(com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws android.os.RemoteException;

    void registerVoiceInteractionSessionListener(com.android.internal.app.IVoiceInteractionSessionListener iVoiceInteractionSessionListener) throws android.os.RemoteException;

    void requestDirectActions(android.os.IBinder iBinder, int i, android.os.IBinder iBinder2, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException;

    void setDisabled(boolean z) throws android.os.RemoteException;

    void setDisabledShowContext(int i) throws android.os.RemoteException;

    void setKeepAwake(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setModelDatabaseForTestEnabled(boolean z, android.os.IBinder iBinder) throws android.os.RemoteException;

    void setSessionWindowVisible(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setUiHints(android.os.Bundle bundle) throws android.os.RemoteException;

    void showSession(android.os.Bundle bundle, int i, java.lang.String str) throws android.os.RemoteException;

    boolean showSessionForActiveService(android.os.Bundle bundle, int i, java.lang.String str, com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean showSessionFromSession(android.os.IBinder iBinder, android.os.Bundle bundle, int i, java.lang.String str) throws android.os.RemoteException;

    void shutdownHotwordDetectionService() throws android.os.RemoteException;

    int startAssistantActivity(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void startListeningFromExternalSource(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.os.IBinder iBinder, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws android.os.RemoteException;

    void startListeningFromMic(android.media.AudioFormat audioFormat, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws android.os.RemoteException;

    void startListeningVisibleActivityChanged(android.os.IBinder iBinder) throws android.os.RemoteException;

    void startPerceiving(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) throws android.os.RemoteException;

    int startVoiceActivity(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void stopListeningFromMic() throws android.os.RemoteException;

    void stopListeningVisibleActivityChanged(android.os.IBinder iBinder) throws android.os.RemoteException;

    void stopPerceiving() throws android.os.RemoteException;

    void subscribeVisualQueryRecognitionStatus(com.android.internal.app.IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) throws android.os.RemoteException;

    void triggerHardwareRecognitionEventForTest(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws android.os.RemoteException;

    void unregisterAccessibilityDetectionSettingsListener(com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws android.os.RemoteException;

    int updateKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws android.os.RemoteException;

    void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IVoiceInteractionManagerService {
        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void showSession(android.os.Bundle bundle, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean deliverNewSession(android.os.IBinder iBinder, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean showSessionFromSession(android.os.IBinder iBinder, android.os.Bundle bundle, int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean hideSessionFromSession(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int startVoiceActivity(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int startAssistantActivity(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setKeepAwake(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void closeSystemDialogs(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void finish(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setDisabledShowContext(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int getDisabledShowContext() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int getUserDisabledShowContext() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int updateKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int deleteKeyphraseSoundModel(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setModelDatabaseForTestEnabled(boolean z, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean isEnrolledForKeyphrase(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public android.hardware.soundtrigger.KeyphraseMetadata getEnrolledKeyphraseMetadata(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public android.content.ComponentName getActiveServiceComponentName() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean showSessionForActiveService(android.os.Bundle bundle, int i, java.lang.String str, com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void hideCurrentSession() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void launchVoiceAssistFromKeyguard() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean isSessionRunning() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean activeServiceSupportsAssist() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean activeServiceSupportsLaunchFromKeyguard() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void onLockscreenShown() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void registerVoiceInteractionSessionListener(com.android.internal.app.IVoiceInteractionSessionListener iVoiceInteractionSessionListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void getActiveServiceSupportedActions(java.util.List<java.lang.String> list, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setUiHints(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void requestDirectActions(android.os.IBinder iBinder, int i, android.os.IBinder iBinder2, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void performDirectAction(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i, android.os.IBinder iBinder2, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setDisabled(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public com.android.internal.app.IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator(android.media.permission.Identity identity, android.os.IBinder iBinder, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties(android.media.permission.Identity identity) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void initAndVerifyDetector(android.media.permission.Identity identity, android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IBinder iBinder, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void destroyDetector(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void shutdownHotwordDetectionService() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void subscribeVisualQueryRecognitionStatus(com.android.internal.app.IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void enableVisualQueryDetection(com.android.internal.app.IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void disableVisualQueryDetection() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void startPerceiving(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void stopPerceiving() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void startListeningFromMic(android.media.AudioFormat audioFormat, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void stopListeningFromMic() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void startListeningFromExternalSource(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.os.IBinder iBinder, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void triggerHardwareRecognitionEventForTest(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void startListeningVisibleActivityChanged(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void stopListeningVisibleActivityChanged(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setSessionWindowVisible(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void notifyActivityEventChanged(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean getAccessibilityDetectionEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void registerAccessibilityDetectionSettingsListener(com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void unregisterAccessibilityDetectionSettingsListener(com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IVoiceInteractionManagerService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionManagerService";
        static final int TRANSACTION_activeServiceSupportsAssist = 24;
        static final int TRANSACTION_activeServiceSupportsLaunchFromKeyguard = 25;
        static final int TRANSACTION_closeSystemDialogs = 8;
        static final int TRANSACTION_createSoundTriggerSessionAsOriginator = 33;
        static final int TRANSACTION_deleteKeyphraseSoundModel = 15;
        static final int TRANSACTION_deliverNewSession = 2;
        static final int TRANSACTION_destroyDetector = 37;
        static final int TRANSACTION_disableVisualQueryDetection = 41;
        static final int TRANSACTION_enableVisualQueryDetection = 40;
        static final int TRANSACTION_finish = 9;
        static final int TRANSACTION_getAccessibilityDetectionEnabled = 52;
        static final int TRANSACTION_getActiveServiceComponentName = 19;
        static final int TRANSACTION_getActiveServiceSupportedActions = 28;
        static final int TRANSACTION_getDisabledShowContext = 11;
        static final int TRANSACTION_getEnrolledKeyphraseMetadata = 18;
        static final int TRANSACTION_getKeyphraseSoundModel = 13;
        static final int TRANSACTION_getUserDisabledShowContext = 12;
        static final int TRANSACTION_hideCurrentSession = 21;
        static final int TRANSACTION_hideSessionFromSession = 4;
        static final int TRANSACTION_initAndVerifyDetector = 36;
        static final int TRANSACTION_isEnrolledForKeyphrase = 17;
        static final int TRANSACTION_isSessionRunning = 23;
        static final int TRANSACTION_launchVoiceAssistFromKeyguard = 22;
        static final int TRANSACTION_listModuleProperties = 34;
        static final int TRANSACTION_notifyActivityEventChanged = 51;
        static final int TRANSACTION_onLockscreenShown = 26;
        static final int TRANSACTION_performDirectAction = 31;
        static final int TRANSACTION_registerAccessibilityDetectionSettingsListener = 53;
        static final int TRANSACTION_registerVoiceInteractionSessionListener = 27;
        static final int TRANSACTION_requestDirectActions = 30;
        static final int TRANSACTION_setDisabled = 32;
        static final int TRANSACTION_setDisabledShowContext = 10;
        static final int TRANSACTION_setKeepAwake = 7;
        static final int TRANSACTION_setModelDatabaseForTestEnabled = 16;
        static final int TRANSACTION_setSessionWindowVisible = 50;
        static final int TRANSACTION_setUiHints = 29;
        static final int TRANSACTION_showSession = 1;
        static final int TRANSACTION_showSessionForActiveService = 20;
        static final int TRANSACTION_showSessionFromSession = 3;
        static final int TRANSACTION_shutdownHotwordDetectionService = 38;
        static final int TRANSACTION_startAssistantActivity = 6;
        static final int TRANSACTION_startListeningFromExternalSource = 46;
        static final int TRANSACTION_startListeningFromMic = 44;
        static final int TRANSACTION_startListeningVisibleActivityChanged = 48;
        static final int TRANSACTION_startPerceiving = 42;
        static final int TRANSACTION_startVoiceActivity = 5;
        static final int TRANSACTION_stopListeningFromMic = 45;
        static final int TRANSACTION_stopListeningVisibleActivityChanged = 49;
        static final int TRANSACTION_stopPerceiving = 43;
        static final int TRANSACTION_subscribeVisualQueryRecognitionStatus = 39;
        static final int TRANSACTION_triggerHardwareRecognitionEventForTest = 47;
        static final int TRANSACTION_unregisterAccessibilityDetectionSettingsListener = 54;
        static final int TRANSACTION_updateKeyphraseSoundModel = 14;
        static final int TRANSACTION_updateState = 35;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static com.android.internal.app.IVoiceInteractionManagerService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IVoiceInteractionManagerService)) {
                return (com.android.internal.app.IVoiceInteractionManagerService) queryLocalInterface;
            }
            return new com.android.internal.app.IVoiceInteractionManagerService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "showSession";
                case 2:
                    return "deliverNewSession";
                case 3:
                    return "showSessionFromSession";
                case 4:
                    return "hideSessionFromSession";
                case 5:
                    return "startVoiceActivity";
                case 6:
                    return "startAssistantActivity";
                case 7:
                    return "setKeepAwake";
                case 8:
                    return "closeSystemDialogs";
                case 9:
                    return "finish";
                case 10:
                    return "setDisabledShowContext";
                case 11:
                    return "getDisabledShowContext";
                case 12:
                    return "getUserDisabledShowContext";
                case 13:
                    return "getKeyphraseSoundModel";
                case 14:
                    return "updateKeyphraseSoundModel";
                case 15:
                    return "deleteKeyphraseSoundModel";
                case 16:
                    return "setModelDatabaseForTestEnabled";
                case 17:
                    return "isEnrolledForKeyphrase";
                case 18:
                    return "getEnrolledKeyphraseMetadata";
                case 19:
                    return "getActiveServiceComponentName";
                case 20:
                    return "showSessionForActiveService";
                case 21:
                    return "hideCurrentSession";
                case 22:
                    return "launchVoiceAssistFromKeyguard";
                case 23:
                    return "isSessionRunning";
                case 24:
                    return "activeServiceSupportsAssist";
                case 25:
                    return "activeServiceSupportsLaunchFromKeyguard";
                case 26:
                    return "onLockscreenShown";
                case 27:
                    return "registerVoiceInteractionSessionListener";
                case 28:
                    return "getActiveServiceSupportedActions";
                case 29:
                    return "setUiHints";
                case 30:
                    return "requestDirectActions";
                case 31:
                    return "performDirectAction";
                case 32:
                    return "setDisabled";
                case 33:
                    return "createSoundTriggerSessionAsOriginator";
                case 34:
                    return "listModuleProperties";
                case 35:
                    return "updateState";
                case 36:
                    return "initAndVerifyDetector";
                case 37:
                    return "destroyDetector";
                case 38:
                    return "shutdownHotwordDetectionService";
                case 39:
                    return "subscribeVisualQueryRecognitionStatus";
                case 40:
                    return "enableVisualQueryDetection";
                case 41:
                    return "disableVisualQueryDetection";
                case 42:
                    return "startPerceiving";
                case 43:
                    return "stopPerceiving";
                case 44:
                    return "startListeningFromMic";
                case 45:
                    return "stopListeningFromMic";
                case 46:
                    return "startListeningFromExternalSource";
                case 47:
                    return "triggerHardwareRecognitionEventForTest";
                case 48:
                    return "startListeningVisibleActivityChanged";
                case 49:
                    return "stopListeningVisibleActivityChanged";
                case 50:
                    return "setSessionWindowVisible";
                case 51:
                    return "notifyActivityEventChanged";
                case 52:
                    return "getAccessibilityDetectionEnabled";
                case 53:
                    return "registerAccessibilityDetectionSettingsListener";
                case 54:
                    return "unregisterAccessibilityDetectionSettingsListener";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showSession(bundle, readInt, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.service.voice.IVoiceInteractionSession asInterface = android.service.voice.IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    com.android.internal.app.IVoiceInteractor asInterface2 = com.android.internal.app.IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean deliverNewSession = deliverNewSession(readStrongBinder, asInterface, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deliverNewSession);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt2 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean showSessionFromSession = showSessionFromSession(readStrongBinder2, bundle2, readInt2, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showSessionFromSession);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean hideSessionFromSession = hideSessionFromSession(readStrongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hideSessionFromSession);
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int startVoiceActivity = startVoiceActivity(readStrongBinder4, intent, readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(startVoiceActivity);
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startAssistantActivity = startAssistantActivity(readStrongBinder5, intent2, readString5, readString6, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeInt(startAssistantActivity);
                    return true;
                case 7:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKeepAwake(readStrongBinder6, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(readStrongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    finish(readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisabledShowContext(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int disabledShowContext = getDisabledShowContext();
                    parcel2.writeNoException();
                    parcel2.writeInt(disabledShowContext);
                    return true;
                case 12:
                    int userDisabledShowContext = getUserDisabledShowContext();
                    parcel2.writeNoException();
                    parcel2.writeInt(userDisabledShowContext);
                    return true;
                case 13:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = getKeyphraseSoundModel(readInt4, readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyphraseSoundModel, 1);
                    return true;
                case 14:
                    android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel2 = (android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int updateKeyphraseSoundModel = updateKeyphraseSoundModel(keyphraseSoundModel2);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateKeyphraseSoundModel);
                    return true;
                case 15:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int deleteKeyphraseSoundModel = deleteKeyphraseSoundModel(readInt5, readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(deleteKeyphraseSoundModel);
                    return true;
                case 16:
                    boolean readBoolean2 = parcel.readBoolean();
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setModelDatabaseForTestEnabled(readBoolean2, readStrongBinder9);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt6 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEnrolledForKeyphrase = isEnrolledForKeyphrase(readInt6, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnrolledForKeyphrase);
                    return true;
                case 18:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.soundtrigger.KeyphraseMetadata enrolledKeyphraseMetadata = getEnrolledKeyphraseMetadata(readString10, readString11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enrolledKeyphraseMetadata, 1);
                    return true;
                case 19:
                    android.content.ComponentName activeServiceComponentName = getActiveServiceComponentName();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeServiceComponentName, 1);
                    return true;
                case 20:
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt7 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    com.android.internal.app.IVoiceInteractionSessionShowCallback asInterface3 = com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean showSessionForActiveService = showSessionForActiveService(bundle4, readInt7, readString12, asInterface3, readStrongBinder10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showSessionForActiveService);
                    return true;
                case 21:
                    hideCurrentSession();
                    parcel2.writeNoException();
                    return true;
                case 22:
                    launchVoiceAssistFromKeyguard();
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean isSessionRunning = isSessionRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSessionRunning);
                    return true;
                case 24:
                    boolean activeServiceSupportsAssist = activeServiceSupportsAssist();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activeServiceSupportsAssist);
                    return true;
                case 25:
                    boolean activeServiceSupportsLaunchFromKeyguard = activeServiceSupportsLaunchFromKeyguard();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activeServiceSupportsLaunchFromKeyguard);
                    return true;
                case 26:
                    onLockscreenShown();
                    parcel2.writeNoException();
                    return true;
                case 27:
                    com.android.internal.app.IVoiceInteractionSessionListener asInterface4 = com.android.internal.app.IVoiceInteractionSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerVoiceInteractionSessionListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    com.android.internal.app.IVoiceActionCheckCallback asInterface5 = com.android.internal.app.IVoiceActionCheckCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getActiveServiceSupportedActions(createStringArrayList, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiHints(bundle5);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    int readInt8 = parcel.readInt();
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestDirectActions(readStrongBinder11, readInt8, readStrongBinder12, remoteCallback, remoteCallback2);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    java.lang.String readString13 = parcel.readString();
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt9 = parcel.readInt();
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    android.os.RemoteCallback remoteCallback3 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    android.os.RemoteCallback remoteCallback4 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    performDirectAction(readStrongBinder13, readString13, bundle6, readInt9, readStrongBinder14, remoteCallback3, remoteCallback4);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDisabled(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    android.media.permission.Identity identity = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties = (android.hardware.soundtrigger.SoundTrigger.ModuleProperties) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.ModuleProperties.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.internal.app.IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator = createSoundTriggerSessionAsOriginator(identity, readStrongBinder15, moduleProperties);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createSoundTriggerSessionAsOriginator);
                    return true;
                case 34:
                    android.media.permission.Identity identity2 = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties = listModuleProperties(identity2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listModuleProperties, 1);
                    return true;
                case 35:
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    android.os.SharedMemory sharedMemory = (android.os.SharedMemory) parcel.readTypedObject(android.os.SharedMemory.CREATOR);
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateState(persistableBundle, sharedMemory, readStrongBinder16);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    android.media.permission.Identity identity3 = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    android.os.PersistableBundle persistableBundle2 = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    android.os.SharedMemory sharedMemory2 = (android.os.SharedMemory) parcel.readTypedObject(android.os.SharedMemory.CREATOR);
                    android.os.IBinder readStrongBinder17 = parcel.readStrongBinder();
                    com.android.internal.app.IHotwordRecognitionStatusCallback asInterface6 = com.android.internal.app.IHotwordRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    initAndVerifyDetector(identity3, persistableBundle2, sharedMemory2, readStrongBinder17, asInterface6, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    android.os.IBinder readStrongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    destroyDetector(readStrongBinder18);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    shutdownHotwordDetectionService();
                    parcel2.writeNoException();
                    return true;
                case 39:
                    com.android.internal.app.IVisualQueryRecognitionStatusListener asInterface7 = com.android.internal.app.IVisualQueryRecognitionStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribeVisualQueryRecognitionStatus(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    com.android.internal.app.IVisualQueryDetectionAttentionListener asInterface8 = com.android.internal.app.IVisualQueryDetectionAttentionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableVisualQueryDetection(asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    disableVisualQueryDetection();
                    parcel2.writeNoException();
                    return true;
                case 42:
                    android.service.voice.IVisualQueryDetectionVoiceInteractionCallback asInterface9 = android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startPerceiving(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    stopPerceiving();
                    parcel2.writeNoException();
                    return true;
                case 44:
                    android.media.AudioFormat audioFormat = (android.media.AudioFormat) parcel.readTypedObject(android.media.AudioFormat.CREATOR);
                    android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback asInterface10 = android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startListeningFromMic(audioFormat, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    stopListeningFromMic();
                    parcel2.writeNoException();
                    return true;
                case 46:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.media.AudioFormat audioFormat2 = (android.media.AudioFormat) parcel.readTypedObject(android.media.AudioFormat.CREATOR);
                    android.os.PersistableBundle persistableBundle3 = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    android.os.IBinder readStrongBinder19 = parcel.readStrongBinder();
                    android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback asInterface11 = android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startListeningFromExternalSource(parcelFileDescriptor, audioFormat2, persistableBundle3, readStrongBinder19, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent = (android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
                    com.android.internal.app.IHotwordRecognitionStatusCallback asInterface12 = com.android.internal.app.IHotwordRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    triggerHardwareRecognitionEventForTest(keyphraseRecognitionEvent, asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    android.os.IBinder readStrongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startListeningVisibleActivityChanged(readStrongBinder20);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    android.os.IBinder readStrongBinder21 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopListeningVisibleActivityChanged(readStrongBinder21);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    android.os.IBinder readStrongBinder22 = parcel.readStrongBinder();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSessionWindowVisible(readStrongBinder22, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    android.os.IBinder readStrongBinder23 = parcel.readStrongBinder();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyActivityEventChanged(readStrongBinder23, readInt11);
                    return true;
                case 52:
                    boolean accessibilityDetectionEnabled = getAccessibilityDetectionEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accessibilityDetectionEnabled);
                    return true;
                case 53:
                    com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener asInterface13 = com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAccessibilityDetectionSettingsListener(asInterface13);
                    return true;
                case 54:
                    com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener asInterface14 = com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAccessibilityDetectionSettingsListener(asInterface14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IVoiceInteractionManagerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void showSession(android.os.Bundle bundle, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean deliverNewSession(android.os.IBinder iBinder, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iVoiceInteractionSession);
                    obtain.writeStrongInterface(iVoiceInteractor);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean showSessionFromSession(android.os.IBinder iBinder, android.os.Bundle bundle, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean hideSessionFromSession(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int startVoiceActivity(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int startAssistantActivity(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setKeepAwake(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void closeSystemDialogs(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void finish(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setDisabledShowContext(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int getDisabledShowContext() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int getUserDisabledShowContext() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) obtain2.readTypedObject(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int updateKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyphraseSoundModel, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int deleteKeyphraseSoundModel(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setModelDatabaseForTestEnabled(boolean z, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean isEnrolledForKeyphrase(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public android.hardware.soundtrigger.KeyphraseMetadata getEnrolledKeyphraseMetadata(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.soundtrigger.KeyphraseMetadata) obtain2.readTypedObject(android.hardware.soundtrigger.KeyphraseMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public android.content.ComponentName getActiveServiceComponentName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean showSessionForActiveService(android.os.Bundle bundle, int i, java.lang.String str, com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractionSessionShowCallback);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void hideCurrentSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void launchVoiceAssistFromKeyguard() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean isSessionRunning() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean activeServiceSupportsAssist() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean activeServiceSupportsLaunchFromKeyguard() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void onLockscreenShown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void registerVoiceInteractionSessionListener(com.android.internal.app.IVoiceInteractionSessionListener iVoiceInteractionSessionListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractionSessionListener);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void getActiveServiceSupportedActions(java.util.List<java.lang.String> list, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iVoiceActionCheckCallback);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setUiHints(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void requestDirectActions(android.os.IBinder iBinder, int i, android.os.IBinder iBinder2, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void performDirectAction(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i, android.os.IBinder iBinder2, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setDisabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public com.android.internal.app.IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator(android.media.permission.Identity identity, android.os.IBinder iBinder, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(moduleProperties, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.app.IVoiceInteractionSoundTriggerSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties(android.media.permission.Identity identity) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.soundtrigger.SoundTrigger.ModuleProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(sharedMemory, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void initAndVerifyDetector(android.media.permission.Identity identity, android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IBinder iBinder, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(sharedMemory, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iHotwordRecognitionStatusCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void destroyDetector(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void shutdownHotwordDetectionService() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void subscribeVisualQueryRecognitionStatus(com.android.internal.app.IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVisualQueryRecognitionStatusListener);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void enableVisualQueryDetection(com.android.internal.app.IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVisualQueryDetectionAttentionListener);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void disableVisualQueryDetection() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startPerceiving(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVisualQueryDetectionVoiceInteractionCallback);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void stopPerceiving() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startListeningFromMic(android.media.AudioFormat audioFormat, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFormat, 0);
                    obtain.writeStrongInterface(iMicrophoneHotwordDetectionVoiceInteractionCallback);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void stopListeningFromMic() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startListeningFromExternalSource(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.os.IBinder iBinder, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(audioFormat, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iMicrophoneHotwordDetectionVoiceInteractionCallback);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void triggerHardwareRecognitionEventForTest(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyphraseRecognitionEvent, 0);
                    obtain.writeStrongInterface(iHotwordRecognitionStatusCallback);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startListeningVisibleActivityChanged(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void stopListeningVisibleActivityChanged(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setSessionWindowVisible(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void notifyActivityEventChanged(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean getAccessibilityDetectionEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void registerAccessibilityDetectionSettingsListener(com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractionAccessibilitySettingsListener);
                    this.mRemote.transact(53, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void unregisterAccessibilityDetectionSettingsListener(com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionManagerService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractionAccessibilitySettingsListener);
                    this.mRemote.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void setModelDatabaseForTestEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_VOICE_KEYPHRASES, getCallingPid(), getCallingUid());
        }

        protected void showSessionForActiveService_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void hideCurrentSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void launchVoiceAssistFromKeyguard_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void isSessionRunning_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void activeServiceSupportsAssist_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void activeServiceSupportsLaunchFromKeyguard_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void onLockscreenShown_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void registerVoiceInteractionSessionListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void getActiveServiceSupportedActions_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void setDisabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void updateState_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_HOTWORD_DETECTION, getCallingPid(), getCallingUid());
        }

        protected void initAndVerifyDetector_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_HOTWORD_DETECTION, getCallingPid(), getCallingUid());
        }

        protected void subscribeVisualQueryRecognitionStatus_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void enableVisualQueryDetection_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void disableVisualQueryDetection_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 53;
        }
    }
}
