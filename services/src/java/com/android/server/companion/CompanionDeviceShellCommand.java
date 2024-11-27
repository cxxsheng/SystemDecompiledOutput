package com.android.server.companion;

/* loaded from: classes.dex */
class CompanionDeviceShellCommand extends android.os.ShellCommand {
    private static final java.lang.String TAG = "CDM_CompanionDeviceShellCommand";
    private final com.android.server.companion.AssociationRequestsProcessor mAssociationRequestsProcessor;
    private final com.android.server.companion.AssociationStoreImpl mAssociationStore;
    private final com.android.server.companion.BackupRestoreProcessor mBackupRestoreProcessor;
    private final com.android.server.companion.presence.CompanionDevicePresenceMonitor mDevicePresenceMonitor;
    private final com.android.server.companion.AssociationRevokeProcessor mRevokeProcessor;
    private final com.android.server.companion.CompanionDeviceManagerService mService;
    private final com.android.server.companion.datatransfer.SystemDataTransferProcessor mSystemDataTransferProcessor;
    private final com.android.server.companion.transport.CompanionTransportManager mTransportManager;

    CompanionDeviceShellCommand(com.android.server.companion.CompanionDeviceManagerService companionDeviceManagerService, com.android.server.companion.AssociationStoreImpl associationStoreImpl, com.android.server.companion.presence.CompanionDevicePresenceMonitor companionDevicePresenceMonitor, com.android.server.companion.transport.CompanionTransportManager companionTransportManager, com.android.server.companion.datatransfer.SystemDataTransferProcessor systemDataTransferProcessor, com.android.server.companion.AssociationRequestsProcessor associationRequestsProcessor, com.android.server.companion.BackupRestoreProcessor backupRestoreProcessor, com.android.server.companion.AssociationRevokeProcessor associationRevokeProcessor) {
        this.mService = companionDeviceManagerService;
        this.mAssociationStore = associationStoreImpl;
        this.mDevicePresenceMonitor = companionDevicePresenceMonitor;
        this.mTransportManager = companionTransportManager;
        this.mSystemDataTransferProcessor = systemDataTransferProcessor;
        this.mAssociationRequestsProcessor = associationRequestsProcessor;
        this.mBackupRestoreProcessor = backupRestoreProcessor;
        this.mRevokeProcessor = associationRevokeProcessor;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            if ("simulate-device-event".equals(str) && android.companion.Flags.devicePresence()) {
                this.mDevicePresenceMonitor.simulateDeviceEvent(getNextIntArgRequired(), getNextIntArgRequired());
                return 0;
            }
            if ("simulate-device-uuid-event".equals(str) && android.companion.Flags.devicePresence()) {
                this.mDevicePresenceMonitor.simulateDeviceEventByUuid(new com.android.server.companion.presence.ObservableUuid(getNextIntArgRequired(), android.os.ParcelUuid.fromString(getNextArgRequired()), getNextArgRequired(), java.lang.Long.valueOf(java.lang.System.currentTimeMillis())), getNextIntArgRequired());
                return 0;
            }
            switch (str.hashCode()) {
                case -2105020158:
                    if (str.equals("clear-association-memory-cache")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -2027841817:
                    if (str.equals("send-context-sync-call-message")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -1993991071:
                    if (str.equals("send-context-sync-call-facilitators-message")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1905713190:
                    if (str.equals("get-backup-payload")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -1860608114:
                    if (str.equals("enable-perm-sync")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case -1855910485:
                    if (str.equals("remove-inactive-associations")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -1224243197:
                    if (str.equals("enable-context-sync")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case -984232290:
                    if (str.equals("create-emulated-transport")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -757319323:
                    if (str.equals("get-perm-sync-state")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -632811356:
                    if (str.equals("disassociate-all")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -318851754:
                    if (str.equals("send-context-sync-call-create-message")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -191868716:
                    if (str.equals("simulate-device-disappeared")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals("list")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 330925715:
                    if (str.equals("remove-perm-sync-state")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 551574230:
                    if (str.equals("apply-restored-payload")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 665140760:
                    if (str.equals("send-context-sync-empty-message")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 784321104:
                    if (str.equals("disassociate")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1069692606:
                    if (str.equals("disable-context-sync")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 1527030451:
                    if (str.equals("disable-perm-sync")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 1586499358:
                    if (str.equals("associate")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1996509815:
                    if (str.equals("send-context-sync-call-control-message")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 2001610978:
                    if (str.equals("simulate-device-appeared")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            long j = 1138166333441L;
            switch (c) {
                case 0:
                    for (android.companion.AssociationInfo associationInfo : this.mAssociationStore.getAssociationsForUser(getNextIntArgRequired())) {
                        outPrintWriter.println(associationInfo.getPackageName() + " " + associationInfo.getDeviceMacAddress() + " " + associationInfo.getId());
                    }
                    break;
                case 1:
                    int nextIntArgRequired = getNextIntArgRequired();
                    java.lang.String nextArgRequired = getNextArgRequired();
                    java.lang.String nextArgRequired2 = getNextArgRequired();
                    java.lang.String nextArg = getNextArg();
                    this.mService.createNewAssociation(nextIntArgRequired, nextArgRequired, android.net.MacAddress.fromString(nextArgRequired2), nextArg, nextArg, false);
                    break;
                case 2:
                    android.companion.AssociationInfo associationWithCallerChecks = this.mService.getAssociationWithCallerChecks(getNextIntArgRequired(), getNextArgRequired(), getNextArgRequired());
                    if (associationWithCallerChecks != null) {
                        this.mRevokeProcessor.disassociateInternal(associationWithCallerChecks.getId());
                    }
                    break;
                case 3:
                    for (android.companion.AssociationInfo associationInfo2 : this.mAssociationStore.getAssociationsForPackage(getNextIntArgRequired(), getNextArgRequired())) {
                        if (com.android.server.companion.utils.PermissionsUtils.sanitizeWithCallerChecks(this.mService.getContext(), associationInfo2) != null) {
                            this.mRevokeProcessor.disassociateInternal(associationInfo2.getId());
                        }
                    }
                    break;
                case 4:
                    this.mService.persistState();
                    this.mService.loadAssociationsFromDisk();
                    break;
                case 5:
                    this.mDevicePresenceMonitor.simulateDeviceEvent(getNextIntArgRequired(), 0);
                    break;
                case 6:
                    this.mDevicePresenceMonitor.simulateDeviceEvent(getNextIntArgRequired(), 1);
                    break;
                case 7:
                    outPrintWriter.println(android.util.Base64.encodeToString(this.mBackupRestoreProcessor.getBackupPayload(getNextIntArgRequired()), 2));
                    break;
                case '\b':
                    this.mBackupRestoreProcessor.applyRestoredPayload(android.util.Base64.decode(getNextArgRequired(), 2), getNextIntArgRequired());
                    break;
                case '\t':
                    final com.android.server.companion.CompanionDeviceManagerService companionDeviceManagerService = this.mService;
                    java.util.Objects.requireNonNull(companionDeviceManagerService);
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.companion.CompanionDeviceShellCommand$$ExternalSyntheticLambda0
                        public final void runOrThrow() {
                            com.android.server.companion.CompanionDeviceManagerService.this.removeInactiveSelfManagedAssociations();
                        }
                    });
                    break;
                case '\n':
                    this.mTransportManager.createEmulatedTransport(getNextIntArgRequired());
                    break;
                case 11:
                    this.mTransportManager.createEmulatedTransport(getNextIntArgRequired()).processMessage(1667729539, 0, com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.createEmptyMessage());
                    break;
                case '\f':
                    this.mTransportManager.createEmulatedTransport(getNextIntArgRequired()).processMessage(1667729539, 0, com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.createCallCreateMessage(getNextArgRequired(), getNextArgRequired(), getNextArgRequired()));
                    break;
                case '\r':
                    this.mTransportManager.createEmulatedTransport(getNextIntArgRequired()).processMessage(1667729539, 0, com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.createCallControlMessage(getNextArgRequired(), getNextIntArgRequired()));
                    break;
                case 14:
                    int nextIntArgRequired2 = getNextIntArgRequired();
                    int nextIntArgRequired3 = getNextIntArgRequired();
                    java.lang.String nextArgRequired3 = getNextArgRequired();
                    java.lang.String nextArgRequired4 = getNextArgRequired();
                    android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
                    protoOutputStream.write(1120986464257L, 1);
                    long start = protoOutputStream.start(1146756268036L);
                    int i = 0;
                    while (i < nextIntArgRequired3) {
                        long start2 = protoOutputStream.start(2246267895811L);
                        protoOutputStream.write(j, nextIntArgRequired3 == 1 ? nextArgRequired3 : nextArgRequired3 + i);
                        protoOutputStream.write(1138166333442L, nextIntArgRequired3 == 1 ? nextArgRequired4 : nextArgRequired4 + i);
                        protoOutputStream.end(start2);
                        i++;
                        j = 1138166333441L;
                    }
                    protoOutputStream.end(start);
                    this.mTransportManager.createEmulatedTransport(nextIntArgRequired2).processMessage(1667729539, 0, protoOutputStream.getBytes());
                    break;
                case 15:
                    int nextIntArgRequired4 = getNextIntArgRequired();
                    java.lang.String nextArgRequired5 = getNextArgRequired();
                    java.lang.String nextArgRequired6 = getNextArgRequired();
                    int nextIntArgRequired5 = getNextIntArgRequired();
                    boolean nextBooleanArgRequired = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired2 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired3 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired4 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired5 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired6 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired7 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired8 = getNextBooleanArgRequired();
                    android.util.proto.ProtoOutputStream protoOutputStream2 = new android.util.proto.ProtoOutputStream();
                    protoOutputStream2.write(1120986464257L, 1);
                    long start3 = protoOutputStream2.start(1146756268036L);
                    long start4 = protoOutputStream2.start(2246267895809L);
                    protoOutputStream2.write(1138166333441L, nextArgRequired5);
                    long start5 = protoOutputStream2.start(1146756268034L);
                    protoOutputStream2.write(1138166333441L, "Caller Name");
                    protoOutputStream2.write(1151051235330L, com.android.server.companion.datatransfer.contextsync.BitmapUtils.renderDrawableToByteArray(this.mService.getContext().getPackageManager().getApplicationIcon(nextArgRequired6)));
                    long start6 = protoOutputStream2.start(1146756268035L);
                    protoOutputStream2.write(1138166333441L, "Test App Name");
                    protoOutputStream2.write(1138166333442L, nextArgRequired6);
                    protoOutputStream2.end(start6);
                    protoOutputStream2.end(start5);
                    protoOutputStream2.write(1159641169923L, nextIntArgRequired5);
                    if (nextBooleanArgRequired) {
                        protoOutputStream2.write(2259152797700L, 1);
                    }
                    if (nextBooleanArgRequired2) {
                        protoOutputStream2.write(2259152797700L, 2);
                    }
                    if (nextBooleanArgRequired3) {
                        protoOutputStream2.write(2259152797700L, 3);
                    }
                    if (nextBooleanArgRequired4) {
                        protoOutputStream2.write(2259152797700L, 4);
                    }
                    if (nextBooleanArgRequired5) {
                        protoOutputStream2.write(2259152797700L, 5);
                    }
                    if (nextBooleanArgRequired6) {
                        protoOutputStream2.write(2259152797700L, 6);
                    }
                    if (nextBooleanArgRequired7) {
                        protoOutputStream2.write(2259152797700L, 7);
                    }
                    if (nextBooleanArgRequired8) {
                        protoOutputStream2.write(2259152797700L, 8);
                    }
                    protoOutputStream2.end(start4);
                    protoOutputStream2.end(start3);
                    this.mTransportManager.createEmulatedTransport(nextIntArgRequired4).processMessage(1667729539, 0, protoOutputStream2.getBytes());
                    break;
                case 16:
                    this.mAssociationRequestsProcessor.disableSystemDataSync(getNextIntArgRequired(), getNextIntArgRequired());
                    break;
                case 17:
                    this.mAssociationRequestsProcessor.enableSystemDataSync(getNextIntArgRequired(), getNextIntArgRequired());
                    break;
                case 18:
                    android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest = this.mSystemDataTransferProcessor.getPermissionSyncRequest(getNextIntArgRequired());
                    outPrintWriter.println(permissionSyncRequest != null ? java.lang.Boolean.valueOf(permissionSyncRequest.isUserConsented()) : "null");
                    break;
                case 19:
                    int nextIntArgRequired6 = getNextIntArgRequired();
                    android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest2 = this.mSystemDataTransferProcessor.getPermissionSyncRequest(nextIntArgRequired6);
                    outPrintWriter.print(permissionSyncRequest2 == null ? "null" : java.lang.Boolean.valueOf(permissionSyncRequest2.isUserConsented()));
                    this.mSystemDataTransferProcessor.removePermissionSyncRequest(nextIntArgRequired6);
                    android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest3 = this.mSystemDataTransferProcessor.getPermissionSyncRequest(nextIntArgRequired6);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(" -> ");
                    sb.append(permissionSyncRequest3 != null ? java.lang.Boolean.valueOf(permissionSyncRequest3.isUserConsented()) : "null");
                    outPrintWriter.println(sb.toString());
                    break;
                case 20:
                    int nextIntArgRequired7 = getNextIntArgRequired();
                    android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest4 = this.mSystemDataTransferProcessor.getPermissionSyncRequest(nextIntArgRequired7);
                    outPrintWriter.print(permissionSyncRequest4 != null ? java.lang.Boolean.valueOf(permissionSyncRequest4.isUserConsented()) : "null");
                    this.mSystemDataTransferProcessor.enablePermissionsSync(nextIntArgRequired7);
                    outPrintWriter.println(" -> " + this.mSystemDataTransferProcessor.getPermissionSyncRequest(nextIntArgRequired7).isUserConsented());
                    break;
                case 21:
                    int nextIntArgRequired8 = getNextIntArgRequired();
                    android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest5 = this.mSystemDataTransferProcessor.getPermissionSyncRequest(nextIntArgRequired8);
                    outPrintWriter.print(permissionSyncRequest5 != null ? java.lang.Boolean.valueOf(permissionSyncRequest5.isUserConsented()) : "null");
                    this.mSystemDataTransferProcessor.disablePermissionsSync(nextIntArgRequired8);
                    outPrintWriter.println(" -> " + this.mSystemDataTransferProcessor.getPermissionSyncRequest(nextIntArgRequired8).isUserConsented());
                    break;
            }
            return 0;
        } catch (java.lang.Throwable th) {
            java.io.PrintWriter errPrintWriter = getErrPrintWriter();
            errPrintWriter.println();
            errPrintWriter.println("Exception occurred while executing '" + str + "':");
            th.printStackTrace(errPrintWriter);
            return 1;
        }
    }

    private int getNextIntArgRequired() {
        return java.lang.Integer.parseInt(getNextArgRequired());
    }

    private boolean getNextBooleanArgRequired() {
        java.lang.String nextArgRequired = getNextArgRequired();
        if ("true".equalsIgnoreCase(nextArgRequired) || "false".equalsIgnoreCase(nextArgRequired)) {
            return java.lang.Boolean.parseBoolean(nextArgRequired);
        }
        throw new java.lang.IllegalArgumentException("Expected a boolean argument but was: " + nextArgRequired);
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Companion Device Manager (companiondevice) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  list USER_ID");
        outPrintWriter.println("      List all Associations for a user.");
        outPrintWriter.println("  associate USER_ID PACKAGE MAC_ADDRESS [DEVICE_PROFILE]");
        outPrintWriter.println("      Create a new Association.");
        outPrintWriter.println("  disassociate USER_ID PACKAGE MAC_ADDRESS");
        outPrintWriter.println("      Remove an existing Association.");
        outPrintWriter.println("  disassociate-all USER_ID");
        outPrintWriter.println("      Remove all Associations for a user.");
        outPrintWriter.println("  clear-association-memory-cache");
        outPrintWriter.println("      Clear the in-memory association cache and reload all association ");
        outPrintWriter.println("      information from persistent storage. USE FOR DEBUGGING PURPOSES ONLY.");
        outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
        outPrintWriter.println("  simulate-device-appeared ASSOCIATION_ID");
        outPrintWriter.println("      Make CDM act as if the given companion device has appeared.");
        outPrintWriter.println("      I.e. bind the associated companion application's");
        outPrintWriter.println("      CompanionDeviceService(s) and trigger onDeviceAppeared() callback.");
        outPrintWriter.println("      The CDM will consider the devices as present for 60 seconds and then");
        outPrintWriter.println("      will act as if device disappeared, unless 'simulate-device-disappeared'");
        outPrintWriter.println("      or 'simulate-device-appeared' is called again before 60 seconds run out.");
        outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
        outPrintWriter.println("  simulate-device-disappeared ASSOCIATION_ID");
        outPrintWriter.println("      Make CDM act as if the given companion device has disappeared.");
        outPrintWriter.println("      I.e. unbind the associated companion application's");
        outPrintWriter.println("      CompanionDeviceService(s) and trigger onDeviceDisappeared() callback.");
        outPrintWriter.println("      NOTE: This will only have effect if 'simulate-device-appeared' was");
        outPrintWriter.println("      invoked for the same device (same ASSOCIATION_ID) no longer than");
        outPrintWriter.println("      60 seconds ago.");
        outPrintWriter.println("  get-backup-payload USER_ID");
        outPrintWriter.println("      Generate backup payload for the given user and print its content");
        outPrintWriter.println("      encoded to a Base64 string.");
        outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
        outPrintWriter.println("  apply-restored-payload USER_ID PAYLOAD");
        outPrintWriter.println("      Apply restored backup payload for the given user.");
        outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
        if (android.companion.Flags.devicePresence()) {
            outPrintWriter.println("  simulate-device-event ASSOCIATION_ID EVENT");
            outPrintWriter.println("  Simulate the companion device event changes:");
            outPrintWriter.println("    Case(0): ");
            outPrintWriter.println("      Make CDM act as if the given companion device has appeared.");
            outPrintWriter.println("      I.e. bind the associated companion application's");
            outPrintWriter.println("      CompanionDeviceService(s) and trigger onDeviceAppeared() callback.");
            outPrintWriter.println("      The CDM will consider the devices as present for60 seconds and then");
            outPrintWriter.println("      will act as if device disappeared, unless'simulate-device-disappeared'");
            outPrintWriter.println("      or 'simulate-device-appeared' is called again before 60 secondsrun out.");
            outPrintWriter.println("    Case(1): ");
            outPrintWriter.println("      Make CDM act as if the given companion device has disappeared.");
            outPrintWriter.println("      I.e. unbind the associated companion application's");
            outPrintWriter.println("      CompanionDeviceService(s) and trigger onDeviceDisappeared()callback.");
            outPrintWriter.println("      NOTE: This will only have effect if 'simulate-device-appeared' was");
            outPrintWriter.println("      invoked for the same device (same ASSOCIATION_ID) no longer than");
            outPrintWriter.println("      60 seconds ago.");
            outPrintWriter.println("    Case(2): ");
            outPrintWriter.println("      Make CDM act as if the given companion device is BT connected ");
            outPrintWriter.println("    Case(3): ");
            outPrintWriter.println("      Make CDM act as if the given companion device is BT disconnected ");
            outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
            outPrintWriter.println("  simulate-device-uuid-event UUID PACKAGE USERID EVENT");
            outPrintWriter.println("  Simulate the companion device event changes:");
            outPrintWriter.println("    Case(2): ");
            outPrintWriter.println("      Make CDM act as if the given DEVICE is BT connected baseon the UUID");
            outPrintWriter.println("    Case(3): ");
            outPrintWriter.println("      Make CDM act as if the given DEVICE is BT disconnected baseon the UUID");
            outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
        }
        outPrintWriter.println("  remove-inactive-associations");
        outPrintWriter.println("      Remove self-managed associations that have not been active ");
        outPrintWriter.println("      for a long time (90 days or as configured via ");
        outPrintWriter.println("      \"debug.cdm.cdmservice.removal_time_window\" system property). ");
        outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
        outPrintWriter.println("  create-emulated-transport <ASSOCIATION_ID>");
        outPrintWriter.println("      Create an EmulatedTransport for testing purposes only");
        outPrintWriter.println("  enable-perm-sync <ASSOCIATION_ID>");
        outPrintWriter.println("      Enable perm sync for the association.");
        outPrintWriter.println("  disable-perm-sync <ASSOCIATION_ID>");
        outPrintWriter.println("      Disable perm sync for the association.");
        outPrintWriter.println("  get-perm-sync-state <ASSOCIATION_ID>");
        outPrintWriter.println("      Get perm sync state for the association.");
        outPrintWriter.println("  remove-perm-sync-state <ASSOCIATION_ID>");
        outPrintWriter.println("      Remove perm sync state for the association.");
    }
}
