package com.android.server.broadcastradio;

/* loaded from: classes.dex */
final class IRadioServiceHidlImpl extends android.hardware.radio.IRadioService.Stub {
    private static final java.lang.String TAG = "BcRadioSrvHidl";
    private final com.android.server.broadcastradio.hal1.BroadcastRadioService mHal1;
    private final com.android.server.broadcastradio.hal2.BroadcastRadioService mHal2;
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.server.broadcastradio.BroadcastRadioService mService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<android.hardware.radio.RadioManager.ModuleProperties> mV1Modules;

    IRadioServiceHidlImpl(com.android.server.broadcastradio.BroadcastRadioService broadcastRadioService) {
        java.util.Objects.requireNonNull(broadcastRadioService, "broadcast radio service cannot be null");
        this.mService = broadcastRadioService;
        this.mHal1 = new com.android.server.broadcastradio.hal1.BroadcastRadioService();
        this.mV1Modules = this.mHal1.loadModules();
        java.util.OptionalInt max = this.mV1Modules.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.broadcastradio.IRadioServiceHidlImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                return ((android.hardware.radio.RadioManager.ModuleProperties) obj).getId();
            }
        }).max();
        this.mHal2 = new com.android.server.broadcastradio.hal2.BroadcastRadioService(max.isPresent() ? max.getAsInt() + 1 : 0);
    }

    @com.android.internal.annotations.VisibleForTesting
    IRadioServiceHidlImpl(com.android.server.broadcastradio.BroadcastRadioService broadcastRadioService, com.android.server.broadcastradio.hal1.BroadcastRadioService broadcastRadioService2, com.android.server.broadcastradio.hal2.BroadcastRadioService broadcastRadioService3) {
        java.util.Objects.requireNonNull(broadcastRadioService, "Broadcast radio service cannot be null");
        this.mService = broadcastRadioService;
        java.util.Objects.requireNonNull(broadcastRadioService2, "Broadcast radio service implementation for HIDL 1 HAL cannot be null");
        this.mHal1 = broadcastRadioService2;
        this.mV1Modules = this.mHal1.loadModules();
        java.util.Objects.requireNonNull(broadcastRadioService3, "Broadcast radio service implementation for HIDL 2 HAL cannot be null");
        this.mHal2 = broadcastRadioService3;
    }

    public java.util.List<android.hardware.radio.RadioManager.ModuleProperties> listModules() {
        java.util.ArrayList arrayList;
        this.mService.enforcePolicyAccess();
        java.util.Collection<android.hardware.radio.RadioManager.ModuleProperties> listModules = this.mHal2.listModules();
        synchronized (this.mLock) {
            arrayList = new java.util.ArrayList(this.mV1Modules.size() + listModules.size());
            arrayList.addAll(this.mV1Modules);
        }
        arrayList.addAll(listModules);
        return arrayList;
    }

    public android.hardware.radio.ITuner openTuner(int i, android.hardware.radio.RadioManager.BandConfig bandConfig, boolean z, android.hardware.radio.ITunerCallback iTunerCallback) throws android.os.RemoteException {
        if (isDebugEnabled()) {
            android.util.Slog.d(TAG, "Opening module " + i);
        }
        this.mService.enforcePolicyAccess();
        java.util.Objects.requireNonNull(iTunerCallback, "Callback must not be null");
        synchronized (this.mLock) {
            try {
                if (this.mHal2.hasModule(i)) {
                    return this.mHal2.openSession(i, bandConfig, z, iTunerCallback);
                }
                return this.mHal1.openTuner(i, bandConfig, z, iTunerCallback);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.hardware.radio.ICloseHandle addAnnouncementListener(int[] iArr, android.hardware.radio.IAnnouncementListener iAnnouncementListener) {
        if (isDebugEnabled()) {
            android.util.Slog.d(TAG, "Adding announcement listener for " + java.util.Arrays.toString(iArr));
        }
        java.util.Objects.requireNonNull(iArr, "Enabled announcement types cannot be null");
        java.util.Objects.requireNonNull(iAnnouncementListener, "Announcement listener cannot be null");
        this.mService.enforcePolicyAccess();
        synchronized (this.mLock) {
            try {
                if (!this.mHal2.hasAnyModules()) {
                    android.util.Slog.w(TAG, "There are no HAL 2.0 modules registered");
                    return new com.android.server.broadcastradio.hal2.AnnouncementAggregator(iAnnouncementListener, this.mLock);
                }
                return this.mHal2.addAnnouncementListener(iArr, iAnnouncementListener);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (this.mService.getContext().checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump HIDL BroadcastRadioService from from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        indentingPrintWriter.printf("BroadcastRadioService\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("HAL1: %s\n", new java.lang.Object[]{this.mHal1});
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            indentingPrintWriter.printf("Modules of HAL1: %s\n", new java.lang.Object[]{this.mV1Modules});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.printf("HAL2:\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mHal2.dumpInfo(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    private static boolean isDebugEnabled() {
        return android.util.Log.isLoggable(TAG, 3);
    }
}
