package com.android.server.broadcastradio;

/* loaded from: classes.dex */
final class IRadioServiceAidlImpl extends android.hardware.radio.IRadioService.Stub {
    private static final java.util.List<java.lang.String> SERVICE_NAMES = java.util.Arrays.asList(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR + "/amfm", android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR + "/dab");
    private static final java.lang.String TAG = "BcRadioSrvAidl";
    private final com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl mHalAidl;
    private final com.android.server.broadcastradio.BroadcastRadioService mService;

    public static java.util.ArrayList<java.lang.String> getServicesNames() {
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        for (int i = 0; i < SERVICE_NAMES.size(); i++) {
            if (android.os.ServiceManager.waitForDeclaredService(SERVICE_NAMES.get(i)) != null) {
                arrayList.add(SERVICE_NAMES.get(i));
            }
        }
        return arrayList;
    }

    IRadioServiceAidlImpl(com.android.server.broadcastradio.BroadcastRadioService broadcastRadioService, java.util.ArrayList<java.lang.String> arrayList) {
        this(broadcastRadioService, new com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl(arrayList));
        com.android.server.utils.Slogf.i(TAG, "Initialize BroadcastRadioServiceAidl(%s)", broadcastRadioService);
    }

    @com.android.internal.annotations.VisibleForTesting
    IRadioServiceAidlImpl(com.android.server.broadcastradio.BroadcastRadioService broadcastRadioService, com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl broadcastRadioServiceImpl) {
        java.util.Objects.requireNonNull(broadcastRadioService, "Broadcast radio service cannot be null");
        this.mService = broadcastRadioService;
        java.util.Objects.requireNonNull(broadcastRadioServiceImpl, "Broadcast radio service implementation for AIDL HAL cannot be null");
        this.mHalAidl = broadcastRadioServiceImpl;
    }

    public java.util.List<android.hardware.radio.RadioManager.ModuleProperties> listModules() {
        this.mService.enforcePolicyAccess();
        return this.mHalAidl.listModules();
    }

    public android.hardware.radio.ITuner openTuner(int i, android.hardware.radio.RadioManager.BandConfig bandConfig, boolean z, android.hardware.radio.ITunerCallback iTunerCallback) throws android.os.RemoteException {
        if (isDebugEnabled()) {
            com.android.server.utils.Slogf.d(TAG, "Opening module %d", java.lang.Integer.valueOf(i));
        }
        this.mService.enforcePolicyAccess();
        if (iTunerCallback == null) {
            throw new java.lang.IllegalArgumentException("Callback must not be null");
        }
        return this.mHalAidl.openSession(i, bandConfig, z, iTunerCallback);
    }

    public android.hardware.radio.ICloseHandle addAnnouncementListener(int[] iArr, android.hardware.radio.IAnnouncementListener iAnnouncementListener) {
        if (isDebugEnabled()) {
            com.android.server.utils.Slogf.d(TAG, "Adding announcement listener for %s", java.util.Arrays.toString(iArr));
        }
        java.util.Objects.requireNonNull(iArr, "Enabled announcement types cannot be null");
        java.util.Objects.requireNonNull(iAnnouncementListener, "Announcement listener cannot be null");
        this.mService.enforcePolicyAccess();
        return this.mHalAidl.addAnnouncementListener(iArr, iAnnouncementListener);
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (this.mService.getContext().checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump AIDL BroadcastRadioService from from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        indentingPrintWriter.printf("BroadcastRadioService\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("AIDL HAL:\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mHalAidl.dumpInfo(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    private static boolean isDebugEnabled() {
        return android.util.Log.isLoggable(TAG, 3);
    }
}
