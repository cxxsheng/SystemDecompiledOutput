package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IAppOpsService extends android.os.IInterface {
    void addHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps) throws android.os.RemoteException;

    int checkAudioOperation(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException;

    int checkOperation(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    int checkOperationForDevice(int i, int i2, java.lang.String str, int i3) throws android.os.RemoteException;

    int checkOperationRaw(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int checkOperationRawForDevice(int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException;

    int checkPackage(int i, java.lang.String str) throws android.os.RemoteException;

    void clearHistory() throws android.os.RemoteException;

    void collectNoteOpCallsForValidation(java.lang.String str, int i, java.lang.String str2, long j) throws android.os.RemoteException;

    android.app.RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() throws android.os.RemoteException;

    java.util.List<android.app.AsyncNotedAppOp> extractAsyncOps(java.lang.String str) throws android.os.RemoteException;

    void finishOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void finishOperationForDevice(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException;

    void finishProxyOperation(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource, boolean z) throws android.os.RemoteException;

    void finishProxyOperationWithState(android.os.IBinder iBinder, int i, android.content.AttributionSourceState attributionSourceState, boolean z) throws android.os.RemoteException;

    void getHistoricalOps(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3, long j, long j2, int i4, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void getHistoricalOpsFromDiskRaw(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3, long j, long j2, int i4, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    java.util.List<android.app.AppOpsManager.PackageOps> getOpsForPackage(int i, java.lang.String str, int[] iArr) throws android.os.RemoteException;

    java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOps(int[] iArr) throws android.os.RemoteException;

    java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOpsForDevice(int[] iArr, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.app.AppOpsManager.PackageOps> getUidOps(int i, int[] iArr) throws android.os.RemoteException;

    boolean isOperationActive(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    boolean isProxying(int i, java.lang.String str, java.lang.String str2, int i2, java.lang.String str3) throws android.os.RemoteException;

    android.app.SyncNotedAppOp noteOperation(int i, int i2, java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, boolean z2) throws android.os.RemoteException;

    android.app.SyncNotedAppOp noteOperationForDevice(int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, java.lang.String str3, boolean z2) throws android.os.RemoteException;

    android.app.SyncNotedAppOp noteProxyOperation(int i, android.content.AttributionSource attributionSource, boolean z, java.lang.String str, boolean z2, boolean z3) throws android.os.RemoteException;

    android.app.SyncNotedAppOp noteProxyOperationWithState(int i, android.content.AttributionSourceState attributionSourceState, boolean z, java.lang.String str, boolean z2, boolean z3) throws android.os.RemoteException;

    void offsetHistory(long j) throws android.os.RemoteException;

    int permissionToOpCode(java.lang.String str) throws android.os.RemoteException;

    void rebootHistory(long j) throws android.os.RemoteException;

    void reloadNonHistoricalState() throws android.os.RemoteException;

    void removeUser(int i) throws android.os.RemoteException;

    com.android.internal.app.MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig(java.lang.String str, android.app.SyncNotedAppOp syncNotedAppOp, java.lang.String str2) throws android.os.RemoteException;

    void resetAllModes(int i, java.lang.String str) throws android.os.RemoteException;

    void resetHistoryParameters() throws android.os.RemoteException;

    void resetPackageOpsNoHistory(java.lang.String str) throws android.os.RemoteException;

    void setAudioRestriction(int i, int i2, int i3, int i4, java.lang.String[] strArr) throws android.os.RemoteException;

    void setCameraAudioRestriction(int i) throws android.os.RemoteException;

    void setHistoryParameters(int i, long j, int i2) throws android.os.RemoteException;

    void setMode(int i, int i2, java.lang.String str, int i3) throws android.os.RemoteException;

    void setUidMode(int i, int i2, int i3) throws android.os.RemoteException;

    void setUserRestriction(int i, boolean z, android.os.IBinder iBinder, int i2, android.os.PackageTagsList packageTagsList) throws android.os.RemoteException;

    void setUserRestrictions(android.os.Bundle bundle, android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    boolean shouldCollectNotes(int i) throws android.os.RemoteException;

    android.app.SyncNotedAppOp startOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, boolean z, boolean z2, java.lang.String str3, boolean z3, int i3, int i4) throws android.os.RemoteException;

    android.app.SyncNotedAppOp startOperationForDevice(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, boolean z2, java.lang.String str3, boolean z3, int i4, int i5) throws android.os.RemoteException;

    android.app.SyncNotedAppOp startProxyOperation(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) throws android.os.RemoteException;

    android.app.SyncNotedAppOp startProxyOperationWithState(android.os.IBinder iBinder, int i, android.content.AttributionSourceState attributionSourceState, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) throws android.os.RemoteException;

    void startWatchingActive(int[] iArr, com.android.internal.app.IAppOpsActiveCallback iAppOpsActiveCallback) throws android.os.RemoteException;

    void startWatchingAsyncNoted(java.lang.String str, com.android.internal.app.IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws android.os.RemoteException;

    void startWatchingMode(int i, java.lang.String str, com.android.internal.app.IAppOpsCallback iAppOpsCallback) throws android.os.RemoteException;

    void startWatchingModeWithFlags(int i, java.lang.String str, int i2, com.android.internal.app.IAppOpsCallback iAppOpsCallback) throws android.os.RemoteException;

    void startWatchingNoted(int[] iArr, com.android.internal.app.IAppOpsNotedCallback iAppOpsNotedCallback) throws android.os.RemoteException;

    void startWatchingStarted(int[] iArr, com.android.internal.app.IAppOpsStartedCallback iAppOpsStartedCallback) throws android.os.RemoteException;

    void stopWatchingActive(com.android.internal.app.IAppOpsActiveCallback iAppOpsActiveCallback) throws android.os.RemoteException;

    void stopWatchingAsyncNoted(java.lang.String str, com.android.internal.app.IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws android.os.RemoteException;

    void stopWatchingMode(com.android.internal.app.IAppOpsCallback iAppOpsCallback) throws android.os.RemoteException;

    void stopWatchingNoted(com.android.internal.app.IAppOpsNotedCallback iAppOpsNotedCallback) throws android.os.RemoteException;

    void stopWatchingStarted(com.android.internal.app.IAppOpsStartedCallback iAppOpsStartedCallback) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IAppOpsService {
        @Override // com.android.internal.app.IAppOpsService
        public int checkOperation(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public android.app.SyncNotedAppOp noteOperation(int i, int i2, java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, boolean z2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public android.app.SyncNotedAppOp startOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, boolean z, boolean z2, java.lang.String str3, boolean z3, int i3, int i4) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void finishOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingMode(int i, java.lang.String str, com.android.internal.app.IAppOpsCallback iAppOpsCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void stopWatchingMode(com.android.internal.app.IAppOpsCallback iAppOpsCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public int permissionToOpCode(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkAudioOperation(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public boolean shouldCollectNotes(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setCameraAudioRestriction(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingModeWithFlags(int i, java.lang.String str, int i2, com.android.internal.app.IAppOpsCallback iAppOpsCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public android.app.SyncNotedAppOp noteProxyOperation(int i, android.content.AttributionSource attributionSource, boolean z, java.lang.String str, boolean z2, boolean z3) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public android.app.SyncNotedAppOp startProxyOperation(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void finishProxyOperation(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkPackage(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public android.app.RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public com.android.internal.app.MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig(java.lang.String str, android.app.SyncNotedAppOp syncNotedAppOp, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOps(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public java.util.List<android.app.AppOpsManager.PackageOps> getOpsForPackage(int i, java.lang.String str, int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void getHistoricalOps(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3, long j, long j2, int i4, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void getHistoricalOpsFromDiskRaw(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3, long j, long j2, int i4, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void offsetHistory(long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setHistoryParameters(int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void addHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void resetHistoryParameters() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void resetPackageOpsNoHistory(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void clearHistory() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void rebootHistory(long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public java.util.List<android.app.AppOpsManager.PackageOps> getUidOps(int i, int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setUidMode(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setMode(int i, int i2, java.lang.String str, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void resetAllModes(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setAudioRestriction(int i, int i2, int i3, int i4, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setUserRestrictions(android.os.Bundle bundle, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setUserRestriction(int i, boolean z, android.os.IBinder iBinder, int i2, android.os.PackageTagsList packageTagsList) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void removeUser(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingActive(int[] iArr, com.android.internal.app.IAppOpsActiveCallback iAppOpsActiveCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void stopWatchingActive(com.android.internal.app.IAppOpsActiveCallback iAppOpsActiveCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public boolean isOperationActive(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IAppOpsService
        public boolean isProxying(int i, java.lang.String str, java.lang.String str2, int i2, java.lang.String str3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingStarted(int[] iArr, com.android.internal.app.IAppOpsStartedCallback iAppOpsStartedCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void stopWatchingStarted(com.android.internal.app.IAppOpsStartedCallback iAppOpsStartedCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingNoted(int[] iArr, com.android.internal.app.IAppOpsNotedCallback iAppOpsNotedCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void stopWatchingNoted(com.android.internal.app.IAppOpsNotedCallback iAppOpsNotedCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingAsyncNoted(java.lang.String str, com.android.internal.app.IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void stopWatchingAsyncNoted(java.lang.String str, com.android.internal.app.IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public java.util.List<android.app.AsyncNotedAppOp> extractAsyncOps(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkOperationRaw(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void reloadNonHistoricalState() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void collectNoteOpCallsForValidation(java.lang.String str, int i, java.lang.String str2, long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public android.app.SyncNotedAppOp noteProxyOperationWithState(int i, android.content.AttributionSourceState attributionSourceState, boolean z, java.lang.String str, boolean z2, boolean z3) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public android.app.SyncNotedAppOp startProxyOperationWithState(android.os.IBinder iBinder, int i, android.content.AttributionSourceState attributionSourceState, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void finishProxyOperationWithState(android.os.IBinder iBinder, int i, android.content.AttributionSourceState attributionSourceState, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkOperationRawForDevice(int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkOperationForDevice(int i, int i2, java.lang.String str, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public android.app.SyncNotedAppOp noteOperationForDevice(int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, java.lang.String str3, boolean z2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public android.app.SyncNotedAppOp startOperationForDevice(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, boolean z2, java.lang.String str3, boolean z3, int i4, int i5) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void finishOperationForDevice(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOpsForDevice(int[] iArr, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IAppOpsService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IAppOpsService";
        static final int TRANSACTION_addHistoricalOps = 24;
        static final int TRANSACTION_checkAudioOperation = 8;
        static final int TRANSACTION_checkOperation = 1;
        static final int TRANSACTION_checkOperationForDevice = 55;
        static final int TRANSACTION_checkOperationRaw = 48;
        static final int TRANSACTION_checkOperationRawForDevice = 54;
        static final int TRANSACTION_checkPackage = 15;
        static final int TRANSACTION_clearHistory = 27;
        static final int TRANSACTION_collectNoteOpCallsForValidation = 50;
        static final int TRANSACTION_collectRuntimeAppOpAccessMessage = 16;
        static final int TRANSACTION_extractAsyncOps = 47;
        static final int TRANSACTION_finishOperation = 4;
        static final int TRANSACTION_finishOperationForDevice = 58;
        static final int TRANSACTION_finishProxyOperation = 14;
        static final int TRANSACTION_finishProxyOperationWithState = 53;
        static final int TRANSACTION_getHistoricalOps = 20;
        static final int TRANSACTION_getHistoricalOpsFromDiskRaw = 21;
        static final int TRANSACTION_getOpsForPackage = 19;
        static final int TRANSACTION_getPackagesForOps = 18;
        static final int TRANSACTION_getPackagesForOpsForDevice = 59;
        static final int TRANSACTION_getUidOps = 29;
        static final int TRANSACTION_isOperationActive = 39;
        static final int TRANSACTION_isProxying = 40;
        static final int TRANSACTION_noteOperation = 2;
        static final int TRANSACTION_noteOperationForDevice = 56;
        static final int TRANSACTION_noteProxyOperation = 12;
        static final int TRANSACTION_noteProxyOperationWithState = 51;
        static final int TRANSACTION_offsetHistory = 22;
        static final int TRANSACTION_permissionToOpCode = 7;
        static final int TRANSACTION_rebootHistory = 28;
        static final int TRANSACTION_reloadNonHistoricalState = 49;
        static final int TRANSACTION_removeUser = 36;
        static final int TRANSACTION_reportRuntimeAppOpAccessMessageAndGetConfig = 17;
        static final int TRANSACTION_resetAllModes = 32;
        static final int TRANSACTION_resetHistoryParameters = 25;
        static final int TRANSACTION_resetPackageOpsNoHistory = 26;
        static final int TRANSACTION_setAudioRestriction = 33;
        static final int TRANSACTION_setCameraAudioRestriction = 10;
        static final int TRANSACTION_setHistoryParameters = 23;
        static final int TRANSACTION_setMode = 31;
        static final int TRANSACTION_setUidMode = 30;
        static final int TRANSACTION_setUserRestriction = 35;
        static final int TRANSACTION_setUserRestrictions = 34;
        static final int TRANSACTION_shouldCollectNotes = 9;
        static final int TRANSACTION_startOperation = 3;
        static final int TRANSACTION_startOperationForDevice = 57;
        static final int TRANSACTION_startProxyOperation = 13;
        static final int TRANSACTION_startProxyOperationWithState = 52;
        static final int TRANSACTION_startWatchingActive = 37;
        static final int TRANSACTION_startWatchingAsyncNoted = 45;
        static final int TRANSACTION_startWatchingMode = 5;
        static final int TRANSACTION_startWatchingModeWithFlags = 11;
        static final int TRANSACTION_startWatchingNoted = 43;
        static final int TRANSACTION_startWatchingStarted = 41;
        static final int TRANSACTION_stopWatchingActive = 38;
        static final int TRANSACTION_stopWatchingAsyncNoted = 46;
        static final int TRANSACTION_stopWatchingMode = 6;
        static final int TRANSACTION_stopWatchingNoted = 44;
        static final int TRANSACTION_stopWatchingStarted = 42;
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

        public static com.android.internal.app.IAppOpsService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IAppOpsService)) {
                return (com.android.internal.app.IAppOpsService) queryLocalInterface;
            }
            return new com.android.internal.app.IAppOpsService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "checkOperation";
                case 2:
                    return "noteOperation";
                case 3:
                    return "startOperation";
                case 4:
                    return "finishOperation";
                case 5:
                    return "startWatchingMode";
                case 6:
                    return "stopWatchingMode";
                case 7:
                    return "permissionToOpCode";
                case 8:
                    return "checkAudioOperation";
                case 9:
                    return "shouldCollectNotes";
                case 10:
                    return "setCameraAudioRestriction";
                case 11:
                    return "startWatchingModeWithFlags";
                case 12:
                    return "noteProxyOperation";
                case 13:
                    return "startProxyOperation";
                case 14:
                    return "finishProxyOperation";
                case 15:
                    return "checkPackage";
                case 16:
                    return "collectRuntimeAppOpAccessMessage";
                case 17:
                    return "reportRuntimeAppOpAccessMessageAndGetConfig";
                case 18:
                    return "getPackagesForOps";
                case 19:
                    return "getOpsForPackage";
                case 20:
                    return "getHistoricalOps";
                case 21:
                    return "getHistoricalOpsFromDiskRaw";
                case 22:
                    return "offsetHistory";
                case 23:
                    return "setHistoryParameters";
                case 24:
                    return "addHistoricalOps";
                case 25:
                    return "resetHistoryParameters";
                case 26:
                    return "resetPackageOpsNoHistory";
                case 27:
                    return "clearHistory";
                case 28:
                    return "rebootHistory";
                case 29:
                    return "getUidOps";
                case 30:
                    return "setUidMode";
                case 31:
                    return "setMode";
                case 32:
                    return "resetAllModes";
                case 33:
                    return "setAudioRestriction";
                case 34:
                    return "setUserRestrictions";
                case 35:
                    return "setUserRestriction";
                case 36:
                    return "removeUser";
                case 37:
                    return "startWatchingActive";
                case 38:
                    return "stopWatchingActive";
                case 39:
                    return "isOperationActive";
                case 40:
                    return "isProxying";
                case 41:
                    return "startWatchingStarted";
                case 42:
                    return "stopWatchingStarted";
                case 43:
                    return "startWatchingNoted";
                case 44:
                    return "stopWatchingNoted";
                case 45:
                    return "startWatchingAsyncNoted";
                case 46:
                    return "stopWatchingAsyncNoted";
                case 47:
                    return "extractAsyncOps";
                case 48:
                    return "checkOperationRaw";
                case 49:
                    return "reloadNonHistoricalState";
                case 50:
                    return "collectNoteOpCallsForValidation";
                case 51:
                    return "noteProxyOperationWithState";
                case 52:
                    return "startProxyOperationWithState";
                case 53:
                    return "finishProxyOperationWithState";
                case 54:
                    return "checkOperationRawForDevice";
                case 55:
                    return "checkOperationForDevice";
                case 56:
                    return "noteOperationForDevice";
                case 57:
                    return "startOperationForDevice";
                case 58:
                    return "finishOperationForDevice";
                case 59:
                    return "getPackagesForOpsForDevice";
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkOperation = checkOperation(readInt, readInt2, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkOperation);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    java.lang.String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.app.SyncNotedAppOp noteOperation = noteOperation(readInt3, readInt4, readString2, readString3, readBoolean, readString4, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(noteOperation, 1);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    java.lang.String readString7 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.SyncNotedAppOp startOperation = startOperation(readStrongBinder, readInt5, readInt6, readString5, readString6, readBoolean3, readBoolean4, readString7, readBoolean5, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startOperation, 1);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    finishOperation(readStrongBinder2, readInt9, readInt10, readString8, readString9);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt11 = parcel.readInt();
                    java.lang.String readString10 = parcel.readString();
                    com.android.internal.app.IAppOpsCallback asInterface = com.android.internal.app.IAppOpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingMode(readInt11, readString10, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    com.android.internal.app.IAppOpsCallback asInterface2 = com.android.internal.app.IAppOpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingMode(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int permissionToOpCode = permissionToOpCode(readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(permissionToOpCode);
                    return true;
                case 8:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkAudioOperation = checkAudioOperation(readInt12, readInt13, readInt14, readString12);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkAudioOperation);
                    return true;
                case 9:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldCollectNotes = shouldCollectNotes(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldCollectNotes);
                    return true;
                case 10:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCameraAudioRestriction(readInt16);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt17 = parcel.readInt();
                    java.lang.String readString13 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    com.android.internal.app.IAppOpsCallback asInterface3 = com.android.internal.app.IAppOpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingModeWithFlags(readInt17, readString13, readInt18, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt19 = parcel.readInt();
                    android.content.AttributionSource attributionSource = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    java.lang.String readString14 = parcel.readString();
                    boolean readBoolean7 = parcel.readBoolean();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.app.SyncNotedAppOp noteProxyOperation = noteProxyOperation(readInt19, attributionSource, readBoolean6, readString14, readBoolean7, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(noteProxyOperation, 1);
                    return true;
                case 13:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt20 = parcel.readInt();
                    android.content.AttributionSource attributionSource2 = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    boolean readBoolean10 = parcel.readBoolean();
                    java.lang.String readString15 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.SyncNotedAppOp startProxyOperation = startProxyOperation(readStrongBinder3, readInt20, attributionSource2, readBoolean9, readBoolean10, readString15, readBoolean11, readBoolean12, readInt21, readInt22, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startProxyOperation, 1);
                    return true;
                case 14:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt24 = parcel.readInt();
                    android.content.AttributionSource attributionSource3 = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishProxyOperation(readStrongBinder4, readInt24, attributionSource3, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt25 = parcel.readInt();
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkPackage = checkPackage(readInt25, readString16);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPackage);
                    return true;
                case 16:
                    android.app.RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage = collectRuntimeAppOpAccessMessage();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(collectRuntimeAppOpAccessMessage, 1);
                    return true;
                case 17:
                    java.lang.String readString17 = parcel.readString();
                    android.app.SyncNotedAppOp syncNotedAppOp = (android.app.SyncNotedAppOp) parcel.readTypedObject(android.app.SyncNotedAppOp.CREATOR);
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    com.android.internal.app.MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig = reportRuntimeAppOpAccessMessageAndGetConfig(readString17, syncNotedAppOp, readString18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(reportRuntimeAppOpAccessMessageAndGetConfig, 1);
                    return true;
                case 18:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.AppOpsManager.PackageOps> packagesForOps = getPackagesForOps(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packagesForOps, 1);
                    return true;
                case 19:
                    int readInt26 = parcel.readInt();
                    java.lang.String readString19 = parcel.readString();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.AppOpsManager.PackageOps> opsForPackage = getOpsForPackage(readInt26, readString19, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(opsForPackage, 1);
                    return true;
                case 20:
                    int readInt27 = parcel.readInt();
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    int readInt30 = parcel.readInt();
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getHistoricalOps(readInt27, readString20, readString21, createStringArrayList, readInt28, readInt29, readLong, readLong2, readInt30, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt31 = parcel.readInt();
                    java.lang.String readString22 = parcel.readString();
                    java.lang.String readString23 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    int readInt34 = parcel.readInt();
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getHistoricalOpsFromDiskRaw(readInt31, readString22, readString23, createStringArrayList2, readInt32, readInt33, readLong3, readLong4, readInt34, remoteCallback2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    offsetHistory(readLong5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt35 = parcel.readInt();
                    long readLong6 = parcel.readLong();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHistoryParameters(readInt35, readLong6, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.app.AppOpsManager.HistoricalOps historicalOps = (android.app.AppOpsManager.HistoricalOps) parcel.readTypedObject(android.app.AppOpsManager.HistoricalOps.CREATOR);
                    parcel.enforceNoDataAvail();
                    addHistoricalOps(historicalOps);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    resetHistoryParameters();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetPackageOpsNoHistory(readString24);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    clearHistory();
                    parcel2.writeNoException();
                    return true;
                case 28:
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    rebootHistory(readLong7);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt37 = parcel.readInt();
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.AppOpsManager.PackageOps> uidOps = getUidOps(readInt37, createIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(uidOps, 1);
                    return true;
                case 30:
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUidMode(readInt38, readInt39, readInt40);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    java.lang.String readString25 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMode(readInt41, readInt42, readString25, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt44 = parcel.readInt();
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetAllModes(readInt44, readString26);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setAudioRestriction(readInt45, readInt46, readInt47, readInt48, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserRestrictions(bundle, readStrongBinder5, readInt49);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt50 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt51 = parcel.readInt();
                    android.os.PackageTagsList packageTagsList = (android.os.PackageTagsList) parcel.readTypedObject(android.os.PackageTagsList.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserRestriction(readInt50, readBoolean14, readStrongBinder6, readInt51, packageTagsList);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUser(readInt52);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int[] createIntArray4 = parcel.createIntArray();
                    com.android.internal.app.IAppOpsActiveCallback asInterface4 = com.android.internal.app.IAppOpsActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingActive(createIntArray4, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    com.android.internal.app.IAppOpsActiveCallback asInterface5 = com.android.internal.app.IAppOpsActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingActive(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isOperationActive = isOperationActive(readInt53, readInt54, readString27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOperationActive);
                    return true;
                case 40:
                    int readInt55 = parcel.readInt();
                    java.lang.String readString28 = parcel.readString();
                    java.lang.String readString29 = parcel.readString();
                    int readInt56 = parcel.readInt();
                    java.lang.String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProxying = isProxying(readInt55, readString28, readString29, readInt56, readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProxying);
                    return true;
                case 41:
                    int[] createIntArray5 = parcel.createIntArray();
                    com.android.internal.app.IAppOpsStartedCallback asInterface6 = com.android.internal.app.IAppOpsStartedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingStarted(createIntArray5, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    com.android.internal.app.IAppOpsStartedCallback asInterface7 = com.android.internal.app.IAppOpsStartedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingStarted(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int[] createIntArray6 = parcel.createIntArray();
                    com.android.internal.app.IAppOpsNotedCallback asInterface8 = com.android.internal.app.IAppOpsNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingNoted(createIntArray6, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    com.android.internal.app.IAppOpsNotedCallback asInterface9 = com.android.internal.app.IAppOpsNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingNoted(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    java.lang.String readString31 = parcel.readString();
                    com.android.internal.app.IAppOpsAsyncNotedCallback asInterface10 = com.android.internal.app.IAppOpsAsyncNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingAsyncNoted(readString31, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    java.lang.String readString32 = parcel.readString();
                    com.android.internal.app.IAppOpsAsyncNotedCallback asInterface11 = com.android.internal.app.IAppOpsAsyncNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingAsyncNoted(readString32, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    java.lang.String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.AsyncNotedAppOp> extractAsyncOps = extractAsyncOps(readString33);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(extractAsyncOps, 1);
                    return true;
                case 48:
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    java.lang.String readString34 = parcel.readString();
                    java.lang.String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkOperationRaw = checkOperationRaw(readInt57, readInt58, readString34, readString35);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkOperationRaw);
                    return true;
                case 49:
                    reloadNonHistoricalState();
                    parcel2.writeNoException();
                    return true;
                case 50:
                    java.lang.String readString36 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    java.lang.String readString37 = parcel.readString();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    collectNoteOpCallsForValidation(readString36, readInt59, readString37, readLong8);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int readInt60 = parcel.readInt();
                    android.content.AttributionSourceState attributionSourceState = (android.content.AttributionSourceState) parcel.readTypedObject(android.content.AttributionSourceState.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    java.lang.String readString38 = parcel.readString();
                    boolean readBoolean16 = parcel.readBoolean();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.app.SyncNotedAppOp noteProxyOperationWithState = noteProxyOperationWithState(readInt60, attributionSourceState, readBoolean15, readString38, readBoolean16, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(noteProxyOperationWithState, 1);
                    return true;
                case 52:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    int readInt61 = parcel.readInt();
                    android.content.AttributionSourceState attributionSourceState2 = (android.content.AttributionSourceState) parcel.readTypedObject(android.content.AttributionSourceState.CREATOR);
                    boolean readBoolean18 = parcel.readBoolean();
                    boolean readBoolean19 = parcel.readBoolean();
                    java.lang.String readString39 = parcel.readString();
                    boolean readBoolean20 = parcel.readBoolean();
                    boolean readBoolean21 = parcel.readBoolean();
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.SyncNotedAppOp startProxyOperationWithState = startProxyOperationWithState(readStrongBinder7, readInt61, attributionSourceState2, readBoolean18, readBoolean19, readString39, readBoolean20, readBoolean21, readInt62, readInt63, readInt64);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startProxyOperationWithState, 1);
                    return true;
                case 53:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    int readInt65 = parcel.readInt();
                    android.content.AttributionSourceState attributionSourceState3 = (android.content.AttributionSourceState) parcel.readTypedObject(android.content.AttributionSourceState.CREATOR);
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishProxyOperationWithState(readStrongBinder8, readInt65, attributionSourceState3, readBoolean22);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    java.lang.String readString40 = parcel.readString();
                    java.lang.String readString41 = parcel.readString();
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkOperationRawForDevice = checkOperationRawForDevice(readInt66, readInt67, readString40, readString41, readInt68);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkOperationRawForDevice);
                    return true;
                case 55:
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    java.lang.String readString42 = parcel.readString();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkOperationForDevice = checkOperationForDevice(readInt69, readInt70, readString42, readInt71);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkOperationForDevice);
                    return true;
                case 56:
                    int readInt72 = parcel.readInt();
                    int readInt73 = parcel.readInt();
                    java.lang.String readString43 = parcel.readString();
                    java.lang.String readString44 = parcel.readString();
                    int readInt74 = parcel.readInt();
                    boolean readBoolean23 = parcel.readBoolean();
                    java.lang.String readString45 = parcel.readString();
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.app.SyncNotedAppOp noteOperationForDevice = noteOperationForDevice(readInt72, readInt73, readString43, readString44, readInt74, readBoolean23, readString45, readBoolean24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(noteOperationForDevice, 1);
                    return true;
                case 57:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt75 = parcel.readInt();
                    int readInt76 = parcel.readInt();
                    java.lang.String readString46 = parcel.readString();
                    java.lang.String readString47 = parcel.readString();
                    int readInt77 = parcel.readInt();
                    boolean readBoolean25 = parcel.readBoolean();
                    boolean readBoolean26 = parcel.readBoolean();
                    java.lang.String readString48 = parcel.readString();
                    boolean readBoolean27 = parcel.readBoolean();
                    int readInt78 = parcel.readInt();
                    int readInt79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.SyncNotedAppOp startOperationForDevice = startOperationForDevice(readStrongBinder9, readInt75, readInt76, readString46, readString47, readInt77, readBoolean25, readBoolean26, readString48, readBoolean27, readInt78, readInt79);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startOperationForDevice, 1);
                    return true;
                case 58:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt80 = parcel.readInt();
                    int readInt81 = parcel.readInt();
                    java.lang.String readString49 = parcel.readString();
                    java.lang.String readString50 = parcel.readString();
                    int readInt82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishOperationForDevice(readStrongBinder10, readInt80, readInt81, readString49, readString50, readInt82);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int[] createIntArray7 = parcel.createIntArray();
                    java.lang.String readString51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.AppOpsManager.PackageOps> packagesForOpsForDevice = getPackagesForOpsForDevice(createIntArray7, readString51);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packagesForOpsForDevice, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IAppOpsService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperation(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public android.app.SyncNotedAppOp noteOperation(int i, int i2, java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.SyncNotedAppOp) obtain2.readTypedObject(android.app.SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public android.app.SyncNotedAppOp startOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, boolean z, boolean z2, java.lang.String str3, boolean z3, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z3);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.SyncNotedAppOp) obtain2.readTypedObject(android.app.SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingMode(int i, java.lang.String str, com.android.internal.app.IAppOpsCallback iAppOpsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAppOpsCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingMode(com.android.internal.app.IAppOpsCallback iAppOpsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppOpsCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int permissionToOpCode(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkAudioOperation(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public boolean shouldCollectNotes(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setCameraAudioRestriction(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingModeWithFlags(int i, java.lang.String str, int i2, com.android.internal.app.IAppOpsCallback iAppOpsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAppOpsCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public android.app.SyncNotedAppOp noteProxyOperation(int i, android.content.AttributionSource attributionSource, boolean z, java.lang.String str, boolean z2, boolean z3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.SyncNotedAppOp) obtain2.readTypedObject(android.app.SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public android.app.SyncNotedAppOp startProxyOperation(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.SyncNotedAppOp) obtain2.readTypedObject(android.app.SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishProxyOperation(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkPackage(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
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

            @Override // com.android.internal.app.IAppOpsService
            public android.app.RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.RuntimeAppOpAccessMessage) obtain2.readTypedObject(android.app.RuntimeAppOpAccessMessage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public com.android.internal.app.MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig(java.lang.String str, android.app.SyncNotedAppOp syncNotedAppOp, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(syncNotedAppOp, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.app.MessageSamplingConfig) obtain2.readTypedObject(com.android.internal.app.MessageSamplingConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOps(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.AppOpsManager.PackageOps.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public java.util.List<android.app.AppOpsManager.PackageOps> getOpsForPackage(int i, java.lang.String str, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.AppOpsManager.PackageOps.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void getHistoricalOps(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3, long j, long j2, int i4, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void getHistoricalOpsFromDiskRaw(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3, long j, long j2, int i4, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void offsetHistory(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setHistoryParameters(int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void addHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(historicalOps, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void resetHistoryParameters() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void resetPackageOpsNoHistory(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void clearHistory() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void rebootHistory(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public java.util.List<android.app.AppOpsManager.PackageOps> getUidOps(int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.AppOpsManager.PackageOps.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setUidMode(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setMode(int i, int i2, java.lang.String str, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void resetAllModes(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setAudioRestriction(int i, int i2, int i3, int i4, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setUserRestrictions(android.os.Bundle bundle, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setUserRestriction(int i, boolean z, android.os.IBinder iBinder, int i2, android.os.PackageTagsList packageTagsList) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(packageTagsList, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void removeUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingActive(int[] iArr, com.android.internal.app.IAppOpsActiveCallback iAppOpsActiveCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStrongInterface(iAppOpsActiveCallback);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingActive(com.android.internal.app.IAppOpsActiveCallback iAppOpsActiveCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppOpsActiveCallback);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public boolean isOperationActive(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public boolean isProxying(int i, java.lang.String str, java.lang.String str2, int i2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingStarted(int[] iArr, com.android.internal.app.IAppOpsStartedCallback iAppOpsStartedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStrongInterface(iAppOpsStartedCallback);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingStarted(com.android.internal.app.IAppOpsStartedCallback iAppOpsStartedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppOpsStartedCallback);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingNoted(int[] iArr, com.android.internal.app.IAppOpsNotedCallback iAppOpsNotedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStrongInterface(iAppOpsNotedCallback);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingNoted(com.android.internal.app.IAppOpsNotedCallback iAppOpsNotedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppOpsNotedCallback);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingAsyncNoted(java.lang.String str, com.android.internal.app.IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAppOpsAsyncNotedCallback);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingAsyncNoted(java.lang.String str, com.android.internal.app.IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAppOpsAsyncNotedCallback);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public java.util.List<android.app.AsyncNotedAppOp> extractAsyncOps(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.AsyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperationRaw(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void reloadNonHistoricalState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void collectNoteOpCallsForValidation(java.lang.String str, int i, java.lang.String str2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public android.app.SyncNotedAppOp noteProxyOperationWithState(int i, android.content.AttributionSourceState attributionSourceState, boolean z, java.lang.String str, boolean z2, boolean z3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.SyncNotedAppOp) obtain2.readTypedObject(android.app.SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public android.app.SyncNotedAppOp startProxyOperationWithState(android.os.IBinder iBinder, int i, android.content.AttributionSourceState attributionSourceState, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.SyncNotedAppOp) obtain2.readTypedObject(android.app.SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishProxyOperationWithState(android.os.IBinder iBinder, int i, android.content.AttributionSourceState attributionSourceState, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperationRawForDevice(int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperationForDevice(int i, int i2, java.lang.String str, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public android.app.SyncNotedAppOp noteOperationForDevice(int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, java.lang.String str3, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.SyncNotedAppOp) obtain2.readTypedObject(android.app.SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public android.app.SyncNotedAppOp startOperationForDevice(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, boolean z2, java.lang.String str3, boolean z3, int i4, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.SyncNotedAppOp) obtain2.readTypedObject(android.app.SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishOperationForDevice(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOpsForDevice(int[] iArr, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.AppOpsManager.PackageOps.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void offsetHistory_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void setHistoryParameters_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void addHistoricalOps_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void resetHistoryParameters_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void resetPackageOpsNoHistory_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void clearHistory_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void rebootHistory_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 58;
        }
    }
}
