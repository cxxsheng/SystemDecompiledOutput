package android.app;

/* loaded from: classes.dex */
public interface IApplicationThread extends android.os.IInterface {
    void attachAgent(java.lang.String str) throws android.os.RemoteException;

    void attachStartupAgents(java.lang.String str) throws android.os.RemoteException;

    void bindApplication(java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str2, java.lang.String str3, boolean z, android.content.pm.ProviderInfoList providerInfoList, android.content.ComponentName componentName, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, int i, boolean z2, boolean z3, boolean z4, boolean z5, android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo, java.util.Map map, android.os.Bundle bundle2, java.lang.String str4, android.content.AutofillOptions autofillOptions, android.content.ContentCaptureOptions contentCaptureOptions, long[] jArr, android.os.SharedMemory sharedMemory, long j, long j2) throws android.os.RemoteException;

    void clearDnsCache() throws android.os.RemoteException;

    void dispatchPackageBroadcast(int i, java.lang.String[] strArr) throws android.os.RemoteException;

    void dumpActivity(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException;

    void dumpCacheInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException;

    void dumpDbInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException;

    void dumpGfxInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException;

    void dumpHeap(boolean z, boolean z2, boolean z3, java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void dumpMemInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, java.lang.String[] strArr) throws android.os.RemoteException;

    void dumpMemInfoProto(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, java.lang.String[] strArr) throws android.os.RemoteException;

    void dumpProvider(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String[] strArr) throws android.os.RemoteException;

    void dumpResources(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void dumpService(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String[] strArr) throws android.os.RemoteException;

    void handleTrustStorageUpdate() throws android.os.RemoteException;

    void instrumentWithoutRestart(android.content.ComponentName componentName, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException;

    void notifyCleartextNetwork(byte[] bArr) throws android.os.RemoteException;

    void notifyContentProviderPublishStatus(android.app.ContentProviderHolder contentProviderHolder, java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void performDirectAction(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException;

    void processInBackground() throws android.os.RemoteException;

    void profilerControl(boolean z, android.app.ProfilerInfo profilerInfo, int i) throws android.os.RemoteException;

    void requestAssistContextExtras(android.os.IBinder iBinder, android.os.IBinder iBinder2, int i, int i2, int i3) throws android.os.RemoteException;

    void requestDirectActions(android.os.IBinder iBinder, com.android.internal.app.IVoiceInteractor iVoiceInteractor, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException;

    void runIsolatedEntryPoint(java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException;

    void scheduleApplicationInfoChanged(android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException;

    void scheduleBindService(android.os.IBinder iBinder, android.content.Intent intent, boolean z, int i, long j) throws android.os.RemoteException;

    void scheduleCrash(java.lang.String str, int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void scheduleCreateBackupAgent(android.content.pm.ApplicationInfo applicationInfo, int i, int i2, int i3) throws android.os.RemoteException;

    void scheduleCreateService(android.os.IBinder iBinder, android.content.pm.ServiceInfo serviceInfo, android.content.res.CompatibilityInfo compatibilityInfo, int i) throws android.os.RemoteException;

    void scheduleDestroyBackupAgent(android.content.pm.ApplicationInfo applicationInfo, int i) throws android.os.RemoteException;

    void scheduleEnterAnimationComplete(android.os.IBinder iBinder) throws android.os.RemoteException;

    void scheduleExit() throws android.os.RemoteException;

    void scheduleInstallProvider(android.content.pm.ProviderInfo providerInfo) throws android.os.RemoteException;

    void scheduleLocalVoiceInteractionStarted(android.os.IBinder iBinder, com.android.internal.app.IVoiceInteractor iVoiceInteractor) throws android.os.RemoteException;

    void scheduleLowMemory() throws android.os.RemoteException;

    void scheduleOnNewSceneTransitionInfo(android.os.IBinder iBinder, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo) throws android.os.RemoteException;

    void schedulePing(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void scheduleReceiver(android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, android.content.res.CompatibilityInfo compatibilityInfo, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, java.lang.String str2) throws android.os.RemoteException;

    void scheduleReceiverList(java.util.List<android.app.ReceiverInfo> list) throws android.os.RemoteException;

    void scheduleRegisteredReceiver(android.content.IIntentReceiver iIntentReceiver, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, java.lang.String str2) throws android.os.RemoteException;

    void scheduleServiceArgs(android.os.IBinder iBinder, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    void scheduleStopService(android.os.IBinder iBinder) throws android.os.RemoteException;

    void scheduleSuicide() throws android.os.RemoteException;

    void scheduleTaskFragmentTransaction(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, android.window.TaskFragmentTransaction taskFragmentTransaction) throws android.os.RemoteException;

    void scheduleTimeoutService(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void scheduleTimeoutServiceForType(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void scheduleTransaction(android.app.servertransaction.ClientTransaction clientTransaction) throws android.os.RemoteException;

    void scheduleTranslucentConversionComplete(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void scheduleTrimMemory(int i) throws android.os.RemoteException;

    void scheduleUnbindService(android.os.IBinder iBinder, android.content.Intent intent) throws android.os.RemoteException;

    void setCoreSettings(android.os.Bundle bundle) throws android.os.RemoteException;

    void setNetworkBlockSeq(long j) throws android.os.RemoteException;

    void setProcessState(int i) throws android.os.RemoteException;

    void setSchedulingGroup(int i) throws android.os.RemoteException;

    void startBinderTracking() throws android.os.RemoteException;

    void stopBinderTrackingAndDump(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void unstableProviderDied(android.os.IBinder iBinder) throws android.os.RemoteException;

    void updateHttpProxy() throws android.os.RemoteException;

    void updatePackageCompatibilityInfo(java.lang.String str, android.content.res.CompatibilityInfo compatibilityInfo) throws android.os.RemoteException;

    void updateTimePrefs(int i) throws android.os.RemoteException;

    void updateTimeZone() throws android.os.RemoteException;

    void updateUiTranslationState(android.os.IBinder iBinder, int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.view.translation.UiTranslationSpec uiTranslationSpec) throws android.os.RemoteException;

    public static class Default implements android.app.IApplicationThread {
        @Override // android.app.IApplicationThread
        public void scheduleReceiver(android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, android.content.res.CompatibilityInfo compatibilityInfo, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleReceiverList(java.util.List<android.app.ReceiverInfo> list) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCreateService(android.os.IBinder iBinder, android.content.pm.ServiceInfo serviceInfo, android.content.res.CompatibilityInfo compatibilityInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleStopService(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void bindApplication(java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str2, java.lang.String str3, boolean z, android.content.pm.ProviderInfoList providerInfoList, android.content.ComponentName componentName, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, int i, boolean z2, boolean z3, boolean z4, boolean z5, android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo, java.util.Map map, android.os.Bundle bundle2, java.lang.String str4, android.content.AutofillOptions autofillOptions, android.content.ContentCaptureOptions contentCaptureOptions, long[] jArr, android.os.SharedMemory sharedMemory, long j, long j2) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void runIsolatedEntryPoint(java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleExit() throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleServiceArgs(android.os.IBinder iBinder, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateTimeZone() throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void processInBackground() throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleBindService(android.os.IBinder iBinder, android.content.Intent intent, boolean z, int i, long j) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleUnbindService(android.os.IBinder iBinder, android.content.Intent intent) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpService(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleRegisteredReceiver(android.content.IIntentReceiver iIntentReceiver, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleLowMemory() throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void profilerControl(boolean z, android.app.ProfilerInfo profilerInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setSchedulingGroup(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCreateBackupAgent(android.content.pm.ApplicationInfo applicationInfo, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleDestroyBackupAgent(android.content.pm.ApplicationInfo applicationInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleOnNewSceneTransitionInfo(android.os.IBinder iBinder, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleSuicide() throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dispatchPackageBroadcast(int i, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCrash(java.lang.String str, int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpHeap(boolean z, boolean z2, boolean z3, java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpActivity(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpResources(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void clearDnsCache() throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateHttpProxy() throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setCoreSettings(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updatePackageCompatibilityInfo(java.lang.String str, android.content.res.CompatibilityInfo compatibilityInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTrimMemory(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfoProto(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpGfxInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpCacheInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpProvider(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpDbInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void unstableProviderDied(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtras(android.os.IBinder iBinder, android.os.IBinder iBinder2, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTranslucentConversionComplete(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setProcessState(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleInstallProvider(android.content.pm.ProviderInfo providerInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateTimePrefs(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleEnterAnimationComplete(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void notifyCleartextNetwork(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void startBinderTracking() throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDump(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleLocalVoiceInteractionStarted(android.os.IBinder iBinder, com.android.internal.app.IVoiceInteractor iVoiceInteractor) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void handleTrustStorageUpdate() throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void attachAgent(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void attachStartupAgents(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleApplicationInfoChanged(android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setNetworkBlockSeq(long j) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTransaction(android.app.servertransaction.ClientTransaction clientTransaction) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTaskFragmentTransaction(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, android.window.TaskFragmentTransaction taskFragmentTransaction) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void requestDirectActions(android.os.IBinder iBinder, com.android.internal.app.IVoiceInteractor iVoiceInteractor, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void performDirectAction(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void notifyContentProviderPublishStatus(android.app.ContentProviderHolder contentProviderHolder, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void instrumentWithoutRestart(android.content.ComponentName componentName, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateUiTranslationState(android.os.IBinder iBinder, int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.view.translation.UiTranslationSpec uiTranslationSpec) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTimeoutService(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTimeoutServiceForType(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void schedulePing(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IApplicationThread {
        public static final java.lang.String DESCRIPTOR = "android.app.IApplicationThread";
        static final int TRANSACTION_attachAgent = 50;
        static final int TRANSACTION_attachStartupAgents = 51;
        static final int TRANSACTION_bindApplication = 5;
        static final int TRANSACTION_clearDnsCache = 27;
        static final int TRANSACTION_dispatchPackageBroadcast = 22;
        static final int TRANSACTION_dumpActivity = 25;
        static final int TRANSACTION_dumpCacheInfo = 35;
        static final int TRANSACTION_dumpDbInfo = 37;
        static final int TRANSACTION_dumpGfxInfo = 34;
        static final int TRANSACTION_dumpHeap = 24;
        static final int TRANSACTION_dumpMemInfo = 32;
        static final int TRANSACTION_dumpMemInfoProto = 33;
        static final int TRANSACTION_dumpProvider = 36;
        static final int TRANSACTION_dumpResources = 26;
        static final int TRANSACTION_dumpService = 13;
        static final int TRANSACTION_handleTrustStorageUpdate = 49;
        static final int TRANSACTION_instrumentWithoutRestart = 59;
        static final int TRANSACTION_notifyCleartextNetwork = 45;
        static final int TRANSACTION_notifyContentProviderPublishStatus = 58;
        static final int TRANSACTION_performDirectAction = 57;
        static final int TRANSACTION_processInBackground = 10;
        static final int TRANSACTION_profilerControl = 16;
        static final int TRANSACTION_requestAssistContextExtras = 39;
        static final int TRANSACTION_requestDirectActions = 56;
        static final int TRANSACTION_runIsolatedEntryPoint = 6;
        static final int TRANSACTION_scheduleApplicationInfoChanged = 52;
        static final int TRANSACTION_scheduleBindService = 11;
        static final int TRANSACTION_scheduleCrash = 23;
        static final int TRANSACTION_scheduleCreateBackupAgent = 18;
        static final int TRANSACTION_scheduleCreateService = 3;
        static final int TRANSACTION_scheduleDestroyBackupAgent = 19;
        static final int TRANSACTION_scheduleEnterAnimationComplete = 44;
        static final int TRANSACTION_scheduleExit = 7;
        static final int TRANSACTION_scheduleInstallProvider = 42;
        static final int TRANSACTION_scheduleLocalVoiceInteractionStarted = 48;
        static final int TRANSACTION_scheduleLowMemory = 15;
        static final int TRANSACTION_scheduleOnNewSceneTransitionInfo = 20;
        static final int TRANSACTION_schedulePing = 63;
        static final int TRANSACTION_scheduleReceiver = 1;
        static final int TRANSACTION_scheduleReceiverList = 2;
        static final int TRANSACTION_scheduleRegisteredReceiver = 14;
        static final int TRANSACTION_scheduleServiceArgs = 8;
        static final int TRANSACTION_scheduleStopService = 4;
        static final int TRANSACTION_scheduleSuicide = 21;
        static final int TRANSACTION_scheduleTaskFragmentTransaction = 55;
        static final int TRANSACTION_scheduleTimeoutService = 61;
        static final int TRANSACTION_scheduleTimeoutServiceForType = 62;
        static final int TRANSACTION_scheduleTransaction = 54;
        static final int TRANSACTION_scheduleTranslucentConversionComplete = 40;
        static final int TRANSACTION_scheduleTrimMemory = 31;
        static final int TRANSACTION_scheduleUnbindService = 12;
        static final int TRANSACTION_setCoreSettings = 29;
        static final int TRANSACTION_setNetworkBlockSeq = 53;
        static final int TRANSACTION_setProcessState = 41;
        static final int TRANSACTION_setSchedulingGroup = 17;
        static final int TRANSACTION_startBinderTracking = 46;
        static final int TRANSACTION_stopBinderTrackingAndDump = 47;
        static final int TRANSACTION_unstableProviderDied = 38;
        static final int TRANSACTION_updateHttpProxy = 28;
        static final int TRANSACTION_updatePackageCompatibilityInfo = 30;
        static final int TRANSACTION_updateTimePrefs = 43;
        static final int TRANSACTION_updateTimeZone = 9;
        static final int TRANSACTION_updateUiTranslationState = 60;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IApplicationThread asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IApplicationThread)) {
                return (android.app.IApplicationThread) queryLocalInterface;
            }
            return new android.app.IApplicationThread.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "scheduleReceiver";
                case 2:
                    return "scheduleReceiverList";
                case 3:
                    return "scheduleCreateService";
                case 4:
                    return "scheduleStopService";
                case 5:
                    return "bindApplication";
                case 6:
                    return "runIsolatedEntryPoint";
                case 7:
                    return "scheduleExit";
                case 8:
                    return "scheduleServiceArgs";
                case 9:
                    return "updateTimeZone";
                case 10:
                    return "processInBackground";
                case 11:
                    return "scheduleBindService";
                case 12:
                    return "scheduleUnbindService";
                case 13:
                    return "dumpService";
                case 14:
                    return "scheduleRegisteredReceiver";
                case 15:
                    return "scheduleLowMemory";
                case 16:
                    return "profilerControl";
                case 17:
                    return "setSchedulingGroup";
                case 18:
                    return "scheduleCreateBackupAgent";
                case 19:
                    return "scheduleDestroyBackupAgent";
                case 20:
                    return "scheduleOnNewSceneTransitionInfo";
                case 21:
                    return "scheduleSuicide";
                case 22:
                    return "dispatchPackageBroadcast";
                case 23:
                    return "scheduleCrash";
                case 24:
                    return "dumpHeap";
                case 25:
                    return "dumpActivity";
                case 26:
                    return "dumpResources";
                case 27:
                    return "clearDnsCache";
                case 28:
                    return "updateHttpProxy";
                case 29:
                    return "setCoreSettings";
                case 30:
                    return "updatePackageCompatibilityInfo";
                case 31:
                    return "scheduleTrimMemory";
                case 32:
                    return "dumpMemInfo";
                case 33:
                    return "dumpMemInfoProto";
                case 34:
                    return "dumpGfxInfo";
                case 35:
                    return "dumpCacheInfo";
                case 36:
                    return "dumpProvider";
                case 37:
                    return "dumpDbInfo";
                case 38:
                    return "unstableProviderDied";
                case 39:
                    return "requestAssistContextExtras";
                case 40:
                    return "scheduleTranslucentConversionComplete";
                case 41:
                    return "setProcessState";
                case 42:
                    return "scheduleInstallProvider";
                case 43:
                    return "updateTimePrefs";
                case 44:
                    return "scheduleEnterAnimationComplete";
                case 45:
                    return "notifyCleartextNetwork";
                case 46:
                    return "startBinderTracking";
                case 47:
                    return "stopBinderTrackingAndDump";
                case 48:
                    return "scheduleLocalVoiceInteractionStarted";
                case 49:
                    return "handleTrustStorageUpdate";
                case 50:
                    return "attachAgent";
                case 51:
                    return "attachStartupAgents";
                case 52:
                    return "scheduleApplicationInfoChanged";
                case 53:
                    return "setNetworkBlockSeq";
                case 54:
                    return "scheduleTransaction";
                case 55:
                    return "scheduleTaskFragmentTransaction";
                case 56:
                    return "requestDirectActions";
                case 57:
                    return "performDirectAction";
                case 58:
                    return "notifyContentProviderPublishStatus";
                case 59:
                    return "instrumentWithoutRestart";
                case 60:
                    return "updateUiTranslationState";
                case 61:
                    return "scheduleTimeoutService";
                case 62:
                    return "scheduleTimeoutServiceForType";
                case 63:
                    return "schedulePing";
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
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    android.content.pm.ActivityInfo activityInfo = (android.content.pm.ActivityInfo) parcel.readTypedObject(android.content.pm.ActivityInfo.CREATOR);
                    android.content.res.CompatibilityInfo compatibilityInfo = (android.content.res.CompatibilityInfo) parcel.readTypedObject(android.content.res.CompatibilityInfo.CREATOR);
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    scheduleReceiver(intent, activityInfo, compatibilityInfo, readInt, readString, bundle, readBoolean, readBoolean2, readInt2, readInt3, readInt4, readString2);
                    return true;
                case 2:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.app.ReceiverInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleReceiverList(createTypedArrayList);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.content.pm.ServiceInfo serviceInfo = (android.content.pm.ServiceInfo) parcel.readTypedObject(android.content.pm.ServiceInfo.CREATOR);
                    android.content.res.CompatibilityInfo compatibilityInfo2 = (android.content.res.CompatibilityInfo) parcel.readTypedObject(android.content.res.CompatibilityInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleCreateService(readStrongBinder, serviceInfo, compatibilityInfo2, readInt5);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    scheduleStopService(readStrongBinder2);
                    return true;
                case 5:
                    java.lang.String readString3 = parcel.readString();
                    android.content.pm.ApplicationInfo applicationInfo = (android.content.pm.ApplicationInfo) parcel.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    android.content.pm.ProviderInfoList providerInfoList = (android.content.pm.ProviderInfoList) parcel.readTypedObject(android.content.pm.ProviderInfoList.CREATOR);
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.app.ProfilerInfo profilerInfo = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.app.IInstrumentationWatcher asInterface = android.app.IInstrumentationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    android.app.IUiAutomationConnection asInterface2 = android.app.IUiAutomationConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt6 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    boolean readBoolean6 = parcel.readBoolean();
                    boolean readBoolean7 = parcel.readBoolean();
                    android.content.res.Configuration configuration = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
                    android.content.res.CompatibilityInfo compatibilityInfo3 = (android.content.res.CompatibilityInfo) parcel.readTypedObject(android.content.res.CompatibilityInfo.CREATOR);
                    java.util.HashMap readHashMap = parcel.readHashMap(getClass().getClassLoader());
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    android.content.AutofillOptions autofillOptions = (android.content.AutofillOptions) parcel.readTypedObject(android.content.AutofillOptions.CREATOR);
                    android.content.ContentCaptureOptions contentCaptureOptions = (android.content.ContentCaptureOptions) parcel.readTypedObject(android.content.ContentCaptureOptions.CREATOR);
                    long[] createLongArray = parcel.createLongArray();
                    android.os.SharedMemory sharedMemory = (android.os.SharedMemory) parcel.readTypedObject(android.os.SharedMemory.CREATOR);
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    bindApplication(readString3, applicationInfo, readString4, readString5, readBoolean3, providerInfoList, componentName, profilerInfo, bundle2, asInterface, asInterface2, readInt6, readBoolean4, readBoolean5, readBoolean6, readBoolean7, configuration, compatibilityInfo3, readHashMap, bundle3, readString6, autofillOptions, contentCaptureOptions, createLongArray, sharedMemory, readLong, readLong2);
                    return true;
                case 6:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    runIsolatedEntryPoint(readString7, createStringArray);
                    return true;
                case 7:
                    scheduleExit();
                    return true;
                case 8:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleServiceArgs(readStrongBinder3, parceledListSlice);
                    return true;
                case 9:
                    updateTimeZone();
                    return true;
                case 10:
                    processInBackground();
                    return true;
                case 11:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    scheduleBindService(readStrongBinder4, intent2, readBoolean8, readInt7, readLong3);
                    return true;
                case 12:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    android.content.Intent intent3 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleUnbindService(readStrongBinder5, intent3);
                    return true;
                case 13:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpService(parcelFileDescriptor, readStrongBinder6, createStringArray2);
                    return true;
                case 14:
                    android.content.IIntentReceiver asInterface3 = android.content.IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.content.Intent intent4 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt8 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    boolean readBoolean10 = parcel.readBoolean();
                    boolean readBoolean11 = parcel.readBoolean();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    scheduleRegisteredReceiver(asInterface3, intent4, readInt8, readString8, bundle4, readBoolean9, readBoolean10, readBoolean11, readInt9, readInt10, readInt11, readString9);
                    return true;
                case 15:
                    scheduleLowMemory();
                    return true;
                case 16:
                    boolean readBoolean12 = parcel.readBoolean();
                    android.app.ProfilerInfo profilerInfo2 = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    profilerControl(readBoolean12, profilerInfo2, readInt12);
                    return true;
                case 17:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSchedulingGroup(readInt13);
                    return true;
                case 18:
                    android.content.pm.ApplicationInfo applicationInfo2 = (android.content.pm.ApplicationInfo) parcel.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleCreateBackupAgent(applicationInfo2, readInt14, readInt15, readInt16);
                    return true;
                case 19:
                    android.content.pm.ApplicationInfo applicationInfo3 = (android.content.pm.ApplicationInfo) parcel.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleDestroyBackupAgent(applicationInfo3, readInt17);
                    return true;
                case 20:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo = (android.app.ActivityOptions.SceneTransitionInfo) parcel.readTypedObject(android.app.ActivityOptions.SceneTransitionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleOnNewSceneTransitionInfo(readStrongBinder7, sceneTransitionInfo);
                    return true;
                case 21:
                    scheduleSuicide();
                    return true;
                case 22:
                    int readInt18 = parcel.readInt();
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dispatchPackageBroadcast(readInt18, createStringArray3);
                    return true;
                case 23:
                    java.lang.String readString10 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleCrash(readString10, readInt19, bundle5);
                    return true;
                case 24:
                    boolean readBoolean13 = parcel.readBoolean();
                    boolean readBoolean14 = parcel.readBoolean();
                    boolean readBoolean15 = parcel.readBoolean();
                    java.lang.String readString11 = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    dumpHeap(readBoolean13, readBoolean14, readBoolean15, readString11, parcelFileDescriptor2, remoteCallback);
                    return true;
                case 25:
                    android.os.ParcelFileDescriptor parcelFileDescriptor3 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpActivity(parcelFileDescriptor3, readStrongBinder8, readString12, createStringArray4);
                    return true;
                case 26:
                    android.os.ParcelFileDescriptor parcelFileDescriptor4 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    dumpResources(parcelFileDescriptor4, remoteCallback2);
                    return true;
                case 27:
                    clearDnsCache();
                    return true;
                case 28:
                    updateHttpProxy();
                    return true;
                case 29:
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCoreSettings(bundle6);
                    return true;
                case 30:
                    java.lang.String readString13 = parcel.readString();
                    android.content.res.CompatibilityInfo compatibilityInfo4 = (android.content.res.CompatibilityInfo) parcel.readTypedObject(android.content.res.CompatibilityInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updatePackageCompatibilityInfo(readString13, compatibilityInfo4);
                    return true;
                case 31:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleTrimMemory(readInt20);
                    return true;
                case 32:
                    android.os.ParcelFileDescriptor parcelFileDescriptor5 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.Debug.MemoryInfo memoryInfo = (android.os.Debug.MemoryInfo) parcel.readTypedObject(android.os.Debug.MemoryInfo.CREATOR);
                    boolean readBoolean16 = parcel.readBoolean();
                    boolean readBoolean17 = parcel.readBoolean();
                    boolean readBoolean18 = parcel.readBoolean();
                    boolean readBoolean19 = parcel.readBoolean();
                    boolean readBoolean20 = parcel.readBoolean();
                    boolean readBoolean21 = parcel.readBoolean();
                    java.lang.String[] createStringArray5 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpMemInfo(parcelFileDescriptor5, memoryInfo, readBoolean16, readBoolean17, readBoolean18, readBoolean19, readBoolean20, readBoolean21, createStringArray5);
                    return true;
                case 33:
                    android.os.ParcelFileDescriptor parcelFileDescriptor6 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.Debug.MemoryInfo memoryInfo2 = (android.os.Debug.MemoryInfo) parcel.readTypedObject(android.os.Debug.MemoryInfo.CREATOR);
                    boolean readBoolean22 = parcel.readBoolean();
                    boolean readBoolean23 = parcel.readBoolean();
                    boolean readBoolean24 = parcel.readBoolean();
                    boolean readBoolean25 = parcel.readBoolean();
                    java.lang.String[] createStringArray6 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpMemInfoProto(parcelFileDescriptor6, memoryInfo2, readBoolean22, readBoolean23, readBoolean24, readBoolean25, createStringArray6);
                    return true;
                case 34:
                    android.os.ParcelFileDescriptor parcelFileDescriptor7 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    java.lang.String[] createStringArray7 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpGfxInfo(parcelFileDescriptor7, createStringArray7);
                    return true;
                case 35:
                    android.os.ParcelFileDescriptor parcelFileDescriptor8 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    java.lang.String[] createStringArray8 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpCacheInfo(parcelFileDescriptor8, createStringArray8);
                    return true;
                case 36:
                    android.os.ParcelFileDescriptor parcelFileDescriptor9 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    java.lang.String[] createStringArray9 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpProvider(parcelFileDescriptor9, readStrongBinder9, createStringArray9);
                    return true;
                case 37:
                    android.os.ParcelFileDescriptor parcelFileDescriptor10 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    java.lang.String[] createStringArray10 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpDbInfo(parcelFileDescriptor10, createStringArray10);
                    return true;
                case 38:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unstableProviderDied(readStrongBinder10);
                    return true;
                case 39:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAssistContextExtras(readStrongBinder11, readStrongBinder12, readInt21, readInt22, readInt23);
                    return true;
                case 40:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    boolean readBoolean26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    scheduleTranslucentConversionComplete(readStrongBinder13, readBoolean26);
                    return true;
                case 41:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProcessState(readInt24);
                    return true;
                case 42:
                    android.content.pm.ProviderInfo providerInfo = (android.content.pm.ProviderInfo) parcel.readTypedObject(android.content.pm.ProviderInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleInstallProvider(providerInfo);
                    return true;
                case 43:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateTimePrefs(readInt25);
                    return true;
                case 44:
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    scheduleEnterAnimationComplete(readStrongBinder14);
                    return true;
                case 45:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyCleartextNetwork(createByteArray);
                    return true;
                case 46:
                    startBinderTracking();
                    return true;
                case 47:
                    android.os.ParcelFileDescriptor parcelFileDescriptor11 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopBinderTrackingAndDump(parcelFileDescriptor11);
                    return true;
                case 48:
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    com.android.internal.app.IVoiceInteractor asInterface4 = com.android.internal.app.IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    scheduleLocalVoiceInteractionStarted(readStrongBinder15, asInterface4);
                    return true;
                case 49:
                    handleTrustStorageUpdate();
                    return true;
                case 50:
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    attachAgent(readString14);
                    return true;
                case 51:
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    attachStartupAgents(readString15);
                    return true;
                case 52:
                    android.content.pm.ApplicationInfo applicationInfo4 = (android.content.pm.ApplicationInfo) parcel.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleApplicationInfoChanged(applicationInfo4);
                    return true;
                case 53:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setNetworkBlockSeq(readLong4);
                    return true;
                case 54:
                    android.app.servertransaction.ClientTransaction clientTransaction = (android.app.servertransaction.ClientTransaction) parcel.readTypedObject(android.app.servertransaction.ClientTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleTransaction(clientTransaction);
                    return true;
                case 55:
                    android.window.ITaskFragmentOrganizer asInterface5 = android.window.ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    android.window.TaskFragmentTransaction taskFragmentTransaction = (android.window.TaskFragmentTransaction) parcel.readTypedObject(android.window.TaskFragmentTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleTaskFragmentTransaction(asInterface5, taskFragmentTransaction);
                    return true;
                case 56:
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    com.android.internal.app.IVoiceInteractor asInterface6 = com.android.internal.app.IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder());
                    android.os.RemoteCallback remoteCallback3 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    android.os.RemoteCallback remoteCallback4 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestDirectActions(readStrongBinder16, asInterface6, remoteCallback3, remoteCallback4);
                    return true;
                case 57:
                    android.os.IBinder readStrongBinder17 = parcel.readStrongBinder();
                    java.lang.String readString16 = parcel.readString();
                    android.os.Bundle bundle7 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.RemoteCallback remoteCallback5 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    android.os.RemoteCallback remoteCallback6 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    performDirectAction(readStrongBinder17, readString16, bundle7, remoteCallback5, remoteCallback6);
                    return true;
                case 58:
                    android.app.ContentProviderHolder contentProviderHolder = (android.app.ContentProviderHolder) parcel.readTypedObject(android.app.ContentProviderHolder.CREATOR);
                    java.lang.String readString17 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyContentProviderPublishStatus(contentProviderHolder, readString17, readInt26, readBoolean27);
                    return true;
                case 59:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.Bundle bundle8 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.app.IInstrumentationWatcher asInterface7 = android.app.IInstrumentationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    android.app.IUiAutomationConnection asInterface8 = android.app.IUiAutomationConnection.Stub.asInterface(parcel.readStrongBinder());
                    android.content.pm.ApplicationInfo applicationInfo5 = (android.content.pm.ApplicationInfo) parcel.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    instrumentWithoutRestart(componentName2, bundle8, asInterface7, asInterface8, applicationInfo5);
                    return true;
                case 60:
                    android.os.IBinder readStrongBinder18 = parcel.readStrongBinder();
                    int readInt27 = parcel.readInt();
                    android.view.translation.TranslationSpec translationSpec = (android.view.translation.TranslationSpec) parcel.readTypedObject(android.view.translation.TranslationSpec.CREATOR);
                    android.view.translation.TranslationSpec translationSpec2 = (android.view.translation.TranslationSpec) parcel.readTypedObject(android.view.translation.TranslationSpec.CREATOR);
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
                    android.view.translation.UiTranslationSpec uiTranslationSpec = (android.view.translation.UiTranslationSpec) parcel.readTypedObject(android.view.translation.UiTranslationSpec.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateUiTranslationState(readStrongBinder18, readInt27, translationSpec, translationSpec2, createTypedArrayList2, uiTranslationSpec);
                    return true;
                case 61:
                    android.os.IBinder readStrongBinder19 = parcel.readStrongBinder();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleTimeoutService(readStrongBinder19, readInt28);
                    return true;
                case 62:
                    android.os.IBinder readStrongBinder20 = parcel.readStrongBinder();
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleTimeoutServiceForType(readStrongBinder20, readInt29, readInt30);
                    return true;
                case 63:
                    android.os.RemoteCallback remoteCallback7 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    schedulePing(remoteCallback7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IApplicationThread {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IApplicationThread.Stub.DESCRIPTOR;
            }

            @Override // android.app.IApplicationThread
            public void scheduleReceiver(android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, android.content.res.CompatibilityInfo compatibilityInfo, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(activityInfo, 0);
                    obtain.writeTypedObject(compatibilityInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleReceiverList(java.util.List<android.app.ReceiverInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCreateService(android.os.IBinder iBinder, android.content.pm.ServiceInfo serviceInfo, android.content.res.CompatibilityInfo compatibilityInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(serviceInfo, 0);
                    obtain.writeTypedObject(compatibilityInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleStopService(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void bindApplication(java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str2, java.lang.String str3, boolean z, android.content.pm.ProviderInfoList providerInfoList, android.content.ComponentName componentName, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, int i, boolean z2, boolean z3, boolean z4, boolean z5, android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo, java.util.Map map, android.os.Bundle bundle2, java.lang.String str4, android.content.AutofillOptions autofillOptions, android.content.ContentCaptureOptions contentCaptureOptions, long[] jArr, android.os.SharedMemory sharedMemory, long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(applicationInfo, 0);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(providerInfoList, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iInstrumentationWatcher);
                    obtain.writeStrongInterface(iUiAutomationConnection);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeBoolean(z5);
                    obtain.writeTypedObject(configuration, 0);
                    obtain.writeTypedObject(compatibilityInfo, 0);
                    obtain.writeMap(map);
                    obtain.writeTypedObject(bundle2, 0);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(autofillOptions, 0);
                    obtain.writeTypedObject(contentCaptureOptions, 0);
                    obtain.writeLongArray(jArr);
                    obtain.writeTypedObject(sharedMemory, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void runIsolatedEntryPoint(java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleExit() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleServiceArgs(android.os.IBinder iBinder, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateTimeZone() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void processInBackground() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleBindService(android.os.IBinder iBinder, android.content.Intent intent, boolean z, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleUnbindService(android.os.IBinder iBinder, android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpService(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleRegisteredReceiver(android.content.IIntentReceiver iIntentReceiver, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleLowMemory() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void profilerControl(boolean z, android.app.ProfilerInfo profilerInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setSchedulingGroup(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCreateBackupAgent(android.content.pm.ApplicationInfo applicationInfo, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(applicationInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleDestroyBackupAgent(android.content.pm.ApplicationInfo applicationInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(applicationInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleOnNewSceneTransitionInfo(android.os.IBinder iBinder, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(sceneTransitionInfo, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleSuicide() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dispatchPackageBroadcast(int i, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCrash(java.lang.String str, int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpHeap(boolean z, boolean z2, boolean z3, java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpActivity(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpResources(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void clearDnsCache() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateHttpProxy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setCoreSettings(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updatePackageCompatibilityInfo(java.lang.String str, android.content.res.CompatibilityInfo compatibilityInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(compatibilityInfo, 0);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTrimMemory(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpMemInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(memoryInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeBoolean(z5);
                    obtain.writeBoolean(z6);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpMemInfoProto(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(memoryInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpGfxInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpCacheInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpProvider(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpDbInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void unstableProviderDied(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void requestAssistContextExtras(android.os.IBinder iBinder, android.os.IBinder iBinder2, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTranslucentConversionComplete(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setProcessState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleInstallProvider(android.content.pm.ProviderInfo providerInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(providerInfo, 0);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateTimePrefs(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleEnterAnimationComplete(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void notifyCleartextNetwork(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void startBinderTracking() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void stopBinderTrackingAndDump(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(47, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleLocalVoiceInteractionStarted(android.os.IBinder iBinder, com.android.internal.app.IVoiceInteractor iVoiceInteractor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iVoiceInteractor);
                    this.mRemote.transact(48, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void handleTrustStorageUpdate() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void attachAgent(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void attachStartupAgents(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(51, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleApplicationInfoChanged(android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(52, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setNetworkBlockSeq(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(53, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTransaction(android.app.servertransaction.ClientTransaction clientTransaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(clientTransaction, 0);
                    this.mRemote.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTaskFragmentTransaction(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, android.window.TaskFragmentTransaction taskFragmentTransaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFragmentOrganizer);
                    obtain.writeTypedObject(taskFragmentTransaction, 0);
                    this.mRemote.transact(55, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void requestDirectActions(android.os.IBinder iBinder, com.android.internal.app.IVoiceInteractor iVoiceInteractor, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iVoiceInteractor);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(56, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void performDirectAction(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(57, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void notifyContentProviderPublishStatus(android.app.ContentProviderHolder contentProviderHolder, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(contentProviderHolder, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(58, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void instrumentWithoutRestart(android.content.ComponentName componentName, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iInstrumentationWatcher);
                    obtain.writeStrongInterface(iUiAutomationConnection);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(59, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateUiTranslationState(android.os.IBinder iBinder, int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.view.translation.UiTranslationSpec uiTranslationSpec) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(translationSpec, 0);
                    obtain.writeTypedObject(translationSpec2, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(uiTranslationSpec, 0);
                    this.mRemote.transact(60, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTimeoutService(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTimeoutServiceForType(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(62, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void schedulePing(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationThread.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(63, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 62;
        }
    }
}
