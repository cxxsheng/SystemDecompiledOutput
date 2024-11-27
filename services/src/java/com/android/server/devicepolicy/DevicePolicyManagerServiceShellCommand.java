package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class DevicePolicyManagerServiceShellCommand extends android.os.ShellCommand {
    private static final java.lang.String CMD_CLEAR_FREEZE_PERIOD_RECORD = "clear-freeze-period-record";
    private static final java.lang.String CMD_FORCE_NETWORK_LOGS = "force-network-logs";
    private static final java.lang.String CMD_FORCE_SECURITY_LOGS = "force-security-logs";
    private static final java.lang.String CMD_IS_SAFE_OPERATION = "is-operation-safe";
    private static final java.lang.String CMD_IS_SAFE_OPERATION_BY_REASON = "is-operation-safe-by-reason";
    private static final java.lang.String CMD_LIST_OWNERS = "list-owners";
    private static final java.lang.String CMD_LIST_POLICY_EXEMPT_APPS = "list-policy-exempt-apps";
    private static final java.lang.String CMD_MARK_PO_ON_ORG_OWNED_DEVICE = "mark-profile-owner-on-organization-owned-device";
    private static final java.lang.String CMD_REMOVE_ACTIVE_ADMIN = "remove-active-admin";
    private static final java.lang.String CMD_SET_ACTIVE_ADMIN = "set-active-admin";
    private static final java.lang.String CMD_SET_DEVICE_OWNER = "set-device-owner";
    private static final java.lang.String CMD_SET_PROFILE_OWNER = "set-profile-owner";
    private static final java.lang.String CMD_SET_SAFE_OPERATION = "set-operation-safe";
    private static final java.lang.String DO_ONLY_OPTION = "--device-owner-only";
    private static final java.lang.String USER_OPTION = "--user";
    private android.content.ComponentName mComponent;
    private final com.android.server.devicepolicy.DevicePolicyManagerService mService;
    private boolean mSetDoOnly;
    private int mUserId = 0;

    DevicePolicyManagerServiceShellCommand(com.android.server.devicepolicy.DevicePolicyManagerService devicePolicyManagerService) {
        java.util.Objects.requireNonNull(devicePolicyManagerService);
        this.mService = devicePolicyManagerService;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.printf("DevicePolicyManager Service (device_policy) commands:\n\n", new java.lang.Object[0]);
            showHelp(outPrintWriter);
            outPrintWriter.close();
        } catch (java.lang.Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -2077120112:
                    if (str.equals(CMD_FORCE_NETWORK_LOGS)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1819296492:
                    if (str.equals(CMD_LIST_POLICY_EXEMPT_APPS)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1791908857:
                    if (str.equals(CMD_SET_DEVICE_OWNER)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1073491921:
                    if (str.equals(CMD_LIST_OWNERS)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -905136898:
                    if (str.equals(CMD_SET_SAFE_OPERATION)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -898547037:
                    if (str.equals(CMD_IS_SAFE_OPERATION_BY_REASON)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -776610703:
                    if (str.equals(CMD_REMOVE_ACTIVE_ADMIN)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -577127626:
                    if (str.equals(CMD_IS_SAFE_OPERATION)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -536624985:
                    if (str.equals(CMD_CLEAR_FREEZE_PERIOD_RECORD)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 547934547:
                    if (str.equals(CMD_SET_ACTIVE_ADMIN)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 639813476:
                    if (str.equals(CMD_SET_PROFILE_OWNER)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1325530298:
                    if (str.equals(CMD_FORCE_SECURITY_LOGS)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 1509758184:
                    if (str.equals(CMD_MARK_PO_ON_ORG_OWNED_DEVICE)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    int runIsSafeOperation = runIsSafeOperation(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runIsSafeOperation;
                case 1:
                    int runIsSafeOperationByReason = runIsSafeOperationByReason(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runIsSafeOperationByReason;
                case 2:
                    int runSetSafeOperation = runSetSafeOperation(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runSetSafeOperation;
                case 3:
                    int runListOwners = runListOwners(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runListOwners;
                case 4:
                    int runListPolicyExemptApps = runListPolicyExemptApps(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runListPolicyExemptApps;
                case 5:
                    int runSetActiveAdmin = runSetActiveAdmin(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runSetActiveAdmin;
                case 6:
                    int runSetDeviceOwner = runSetDeviceOwner(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runSetDeviceOwner;
                case 7:
                    int runSetProfileOwner = runSetProfileOwner(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runSetProfileOwner;
                case '\b':
                    int runRemoveActiveAdmin = runRemoveActiveAdmin(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runRemoveActiveAdmin;
                case '\t':
                    int runClearFreezePeriodRecord = runClearFreezePeriodRecord(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runClearFreezePeriodRecord;
                case '\n':
                    int runForceNetworkLogs = runForceNetworkLogs(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runForceNetworkLogs;
                case 11:
                    int runForceSecurityLogs = runForceSecurityLogs(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runForceSecurityLogs;
                case '\f':
                    int runMarkProfileOwnerOnOrganizationOwnedDevice = runMarkProfileOwnerOnOrganizationOwnedDevice(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return runMarkProfileOwnerOnOrganizationOwnedDevice;
                default:
                    int onInvalidCommand = onInvalidCommand(outPrintWriter, str);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return onInvalidCommand;
            }
        } catch (java.lang.Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private int onInvalidCommand(java.io.PrintWriter printWriter, java.lang.String str) {
        if (super.handleDefaultCommands(str) == 0) {
            return 0;
        }
        printWriter.printf("Usage: \n", new java.lang.Object[0]);
        showHelp(printWriter);
        return -1;
    }

    private void showHelp(java.io.PrintWriter printWriter) {
        printWriter.printf("  help\n", new java.lang.Object[0]);
        printWriter.printf("    Prints this help text.\n\n", new java.lang.Object[0]);
        printWriter.printf("  %s <OPERATION_ID>\n", CMD_IS_SAFE_OPERATION);
        printWriter.printf("    Checks if the give operation is safe \n\n", new java.lang.Object[0]);
        printWriter.printf("  %s <REASON_ID>\n", CMD_IS_SAFE_OPERATION_BY_REASON);
        printWriter.printf("    Checks if the operations are safe for the given reason\n\n", new java.lang.Object[0]);
        printWriter.printf("  %s <OPERATION_ID> <REASON_ID>\n", CMD_SET_SAFE_OPERATION);
        printWriter.printf("    Emulates the result of the next call to check if the given operation is safe \n\n", new java.lang.Object[0]);
        printWriter.printf("  %s\n", CMD_LIST_OWNERS);
        printWriter.printf("    Lists the device / profile owners per user \n\n", new java.lang.Object[0]);
        printWriter.printf("  %s\n", CMD_LIST_POLICY_EXEMPT_APPS);
        printWriter.printf("    Lists the apps that are exempt from policies\n\n", new java.lang.Object[0]);
        printWriter.printf("  %s [ %s <USER_ID> | current ] <COMPONENT>\n", CMD_SET_ACTIVE_ADMIN, USER_OPTION);
        printWriter.printf("    Sets the given component as active admin for an existing user.\n\n", new java.lang.Object[0]);
        printWriter.printf("  %s [ %s <USER_ID> | current *EXPERIMENTAL* ] [ %s ]<COMPONENT>\n", CMD_SET_DEVICE_OWNER, USER_OPTION, DO_ONLY_OPTION);
        printWriter.printf("    Sets the given component as active admin, and its package as device owner.\n\n", new java.lang.Object[0]);
        printWriter.printf("  %s [ %s <USER_ID> | current ] <COMPONENT>\n", CMD_SET_PROFILE_OWNER, USER_OPTION);
        printWriter.printf("    Sets the given component as active admin and profile owner for an existing user.\n\n", new java.lang.Object[0]);
        printWriter.printf("  %s [ %s <USER_ID> | current ] <COMPONENT>\n", CMD_REMOVE_ACTIVE_ADMIN, USER_OPTION);
        printWriter.printf("    Disables an active admin, the admin must have declared android:testOnly in the application in its manifest. This will also remove device and profile owners.\n\n", new java.lang.Object[0]);
        printWriter.printf("  %s\n", CMD_CLEAR_FREEZE_PERIOD_RECORD);
        printWriter.printf("    Clears framework-maintained record of past freeze periods that the device went through. For use during feature development to prevent triggering restriction on setting freeze periods.\n\n", new java.lang.Object[0]);
        printWriter.printf("  %s\n", CMD_FORCE_NETWORK_LOGS);
        printWriter.printf("    Makes all network logs available to the DPC and triggers DeviceAdminReceiver.onNetworkLogsAvailable() if needed.\n\n", new java.lang.Object[0]);
        printWriter.printf("  %s\n", CMD_FORCE_SECURITY_LOGS);
        printWriter.printf("    Makes all security logs available to the DPC and triggers DeviceAdminReceiver.onSecurityLogsAvailable() if needed.\n\n", new java.lang.Object[0]);
        printWriter.printf("  %s [ %s <USER_ID> | current ] <COMPONENT>\n", CMD_MARK_PO_ON_ORG_OWNED_DEVICE, USER_OPTION);
        printWriter.printf("    Marks the profile owner of the given user as managing an organization-owneddevice. That will give it access to device identifiers (such as serial number, IMEI and MEID), as well as other privileges.\n\n", new java.lang.Object[0]);
    }

    private int runIsSafeOperation(java.io.PrintWriter printWriter) {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        int unsafeOperationReason = this.mService.getUnsafeOperationReason(parseInt);
        printWriter.printf("Operation %s is %s. Reason: %s\n", android.app.admin.DevicePolicyManager.operationToString(parseInt), safeToString(unsafeOperationReason == -1), android.app.admin.DevicePolicyManager.operationSafetyReasonToString(unsafeOperationReason));
        return 0;
    }

    private int runIsSafeOperationByReason(java.io.PrintWriter printWriter) {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        printWriter.printf("Operations affected by %s are %s\n", android.app.admin.DevicePolicyManager.operationSafetyReasonToString(parseInt), safeToString(this.mService.isSafeOperation(parseInt)));
        return 0;
    }

    private static java.lang.String safeToString(boolean z) {
        return z ? "SAFE" : "UNSAFE";
    }

    private int runSetSafeOperation(java.io.PrintWriter printWriter) {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        int parseInt2 = java.lang.Integer.parseInt(getNextArgRequired());
        this.mService.setNextOperationSafety(parseInt, parseInt2);
        printWriter.printf("Next call to check operation %s will return %s\n", android.app.admin.DevicePolicyManager.operationToString(parseInt), android.app.admin.DevicePolicyManager.operationSafetyReasonToString(parseInt2));
        return 0;
    }

    private int printAndGetSize(java.io.PrintWriter printWriter, java.util.Collection<?> collection, java.lang.String str) {
        if (collection.isEmpty()) {
            printWriter.printf("no %ss\n", str);
            return 0;
        }
        int size = collection.size();
        printWriter.printf("%d %s%s:\n", java.lang.Integer.valueOf(size), str, size == 1 ? "" : "s");
        return size;
    }

    private int runListOwners(java.io.PrintWriter printWriter) {
        java.util.List<com.android.server.devicepolicy.OwnerShellData> listAllOwners = this.mService.listAllOwners();
        int printAndGetSize = printAndGetSize(printWriter, listAllOwners, "owner");
        if (printAndGetSize == 0) {
            return 0;
        }
        for (int i = 0; i < printAndGetSize; i++) {
            com.android.server.devicepolicy.OwnerShellData ownerShellData = listAllOwners.get(i);
            printWriter.printf("User %2d: admin=%s", java.lang.Integer.valueOf(ownerShellData.userId), ownerShellData.admin.flattenToShortString());
            if (ownerShellData.isDeviceOwner) {
                printWriter.print(",DeviceOwner");
            }
            if (ownerShellData.isProfileOwner) {
                printWriter.print(",ProfileOwner");
            }
            if (ownerShellData.isManagedProfileOwner) {
                printWriter.printf(",ManagedProfileOwner(parentUserId=%d)", java.lang.Integer.valueOf(ownerShellData.parentUserId));
            }
            if (ownerShellData.isAffiliated) {
                printWriter.print(",Affiliated");
            }
            printWriter.println();
        }
        return 0;
    }

    private int runListPolicyExemptApps(java.io.PrintWriter printWriter) {
        java.util.List<java.lang.String> listPolicyExemptApps = this.mService.listPolicyExemptApps();
        int printAndGetSize = printAndGetSize(printWriter, listPolicyExemptApps, "policy exempt app");
        if (printAndGetSize == 0) {
            return 0;
        }
        for (int i = 0; i < printAndGetSize; i++) {
            printWriter.printf("  %d: %s\n", java.lang.Integer.valueOf(i), listPolicyExemptApps.get(i));
        }
        return 0;
    }

    private int runSetActiveAdmin(java.io.PrintWriter printWriter) {
        parseArgs();
        this.mService.setActiveAdmin(this.mComponent, true, this.mUserId);
        printWriter.printf("Success: Active admin set to component %s\n", this.mComponent.flattenToShortString());
        return 0;
    }

    private int runSetDeviceOwner(java.io.PrintWriter printWriter) {
        boolean z;
        parseArgs();
        boolean z2 = true;
        try {
            this.mService.setActiveAdmin(this.mComponent, false, this.mUserId);
            z = true;
        } catch (java.lang.IllegalArgumentException e) {
            printWriter.printf("%s was already an admin for user %d. No need to set it again.\n", this.mComponent.flattenToShortString(), java.lang.Integer.valueOf(this.mUserId));
            z = false;
        }
        try {
            com.android.server.devicepolicy.DevicePolicyManagerService devicePolicyManagerService = this.mService;
            android.content.ComponentName componentName = this.mComponent;
            int i = this.mUserId;
            if (this.mSetDoOnly) {
                z2 = false;
            }
            if (!devicePolicyManagerService.setDeviceOwner(componentName, i, z2)) {
                throw new java.lang.RuntimeException("Can't set package " + this.mComponent + " as device owner.");
            }
            this.mService.setUserProvisioningState(3, this.mUserId);
            printWriter.printf("Success: Device owner set to package %s\n", this.mComponent.flattenToShortString());
            printWriter.printf("Active admin set to component %s\n", this.mComponent.flattenToShortString());
            return 0;
        } catch (java.lang.Exception e2) {
            if (z) {
                this.mService.removeActiveAdmin(this.mComponent, this.mUserId);
            }
            throw e2;
        }
    }

    private int runRemoveActiveAdmin(java.io.PrintWriter printWriter) {
        parseArgs();
        this.mService.forceRemoveActiveAdmin(this.mComponent, this.mUserId);
        printWriter.printf("Success: Admin removed %s\n", this.mComponent);
        return 0;
    }

    private int runSetProfileOwner(java.io.PrintWriter printWriter) {
        parseArgs();
        this.mService.setActiveAdmin(this.mComponent, true, this.mUserId);
        try {
            if (!this.mService.setProfileOwner(this.mComponent, this.mUserId)) {
                throw new java.lang.RuntimeException("Can't set component " + this.mComponent.flattenToShortString() + " as profile owner for user " + this.mUserId);
            }
            this.mService.setUserProvisioningState(3, this.mUserId);
            printWriter.printf("Success: Active admin and profile owner set to %s for user %d\n", this.mComponent.flattenToShortString(), java.lang.Integer.valueOf(this.mUserId));
            return 0;
        } catch (java.lang.Exception e) {
            this.mService.removeActiveAdmin(this.mComponent, this.mUserId);
            throw e;
        }
    }

    private int runClearFreezePeriodRecord(java.io.PrintWriter printWriter) {
        this.mService.clearSystemUpdatePolicyFreezePeriodRecord();
        printWriter.printf("Success\n", new java.lang.Object[0]);
        return 0;
    }

    private int runForceNetworkLogs(java.io.PrintWriter printWriter) {
        while (true) {
            long forceNetworkLogs = this.mService.forceNetworkLogs();
            if (forceNetworkLogs != 0) {
                printWriter.printf("We have to wait for %d milliseconds...\n", java.lang.Long.valueOf(forceNetworkLogs));
                android.os.SystemClock.sleep(forceNetworkLogs);
            } else {
                printWriter.printf("Success\n", new java.lang.Object[0]);
                return 0;
            }
        }
    }

    private int runForceSecurityLogs(java.io.PrintWriter printWriter) {
        while (true) {
            long forceSecurityLogs = this.mService.forceSecurityLogs();
            if (forceSecurityLogs != 0) {
                printWriter.printf("We have to wait for %d milliseconds...\n", java.lang.Long.valueOf(forceSecurityLogs));
                android.os.SystemClock.sleep(forceSecurityLogs);
            } else {
                printWriter.printf("Success\n", new java.lang.Object[0]);
                return 0;
            }
        }
    }

    private int runMarkProfileOwnerOnOrganizationOwnedDevice(java.io.PrintWriter printWriter) {
        parseArgs();
        this.mService.setProfileOwnerOnOrganizationOwnedDevice(this.mComponent, this.mUserId, true);
        printWriter.printf("Success\n", new java.lang.Object[0]);
        return 0;
    }

    private void parseArgs() {
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (USER_OPTION.equals(nextOption)) {
                    this.mUserId = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    if (this.mUserId == -2) {
                        this.mUserId = android.app.ActivityManager.getCurrentUser();
                    }
                } else if (DO_ONLY_OPTION.equals(nextOption)) {
                    this.mSetDoOnly = true;
                } else {
                    throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                this.mComponent = parseComponentName(getNextArgRequired());
                return;
            }
        }
    }

    private android.content.ComponentName parseComponentName(java.lang.String str) {
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            throw new java.lang.IllegalArgumentException("Invalid component " + str);
        }
        return unflattenFromString;
    }
}
