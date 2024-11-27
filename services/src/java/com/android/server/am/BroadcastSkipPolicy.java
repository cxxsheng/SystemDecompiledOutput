package com.android.server.am;

/* loaded from: classes.dex */
public class BroadcastSkipPolicy {
    private final com.android.server.am.ActivityManagerService mService;

    public BroadcastSkipPolicy(@android.annotation.NonNull com.android.server.am.ActivityManagerService activityManagerService) {
        java.util.Objects.requireNonNull(activityManagerService);
        this.mService = activityManagerService;
    }

    public boolean shouldSkip(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, @android.annotation.NonNull java.lang.Object obj) {
        java.lang.String shouldSkipMessage = shouldSkipMessage(broadcastRecord, obj);
        if (shouldSkipMessage != null) {
            android.util.Slog.w(com.android.server.am.BroadcastQueue.TAG, shouldSkipMessage);
            return true;
        }
        return false;
    }

    @android.annotation.Nullable
    public java.lang.String shouldSkipMessage(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, @android.annotation.NonNull java.lang.Object obj) {
        if (obj instanceof com.android.server.am.BroadcastFilter) {
            return shouldSkipMessage(broadcastRecord, (com.android.server.am.BroadcastFilter) obj);
        }
        return shouldSkipMessage(broadcastRecord, (android.content.pm.ResolveInfo) obj);
    }

    @android.annotation.Nullable
    private java.lang.String shouldSkipMessage(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, @android.annotation.NonNull android.content.pm.ResolveInfo resolveInfo) {
        com.android.server.am.BroadcastSkipPolicy broadcastSkipPolicy;
        int i;
        int i2;
        int permissionToOpCode;
        com.android.server.am.BroadcastSkipPolicy broadcastSkipPolicy2 = this;
        android.app.BroadcastOptions broadcastOptions = broadcastRecord.options;
        android.content.ComponentName componentName = new android.content.ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name);
        if (broadcastOptions != null && (resolveInfo.activityInfo.applicationInfo.targetSdkVersion < broadcastOptions.getMinManifestReceiverApiLevel() || resolveInfo.activityInfo.applicationInfo.targetSdkVersion > broadcastOptions.getMaxManifestReceiverApiLevel())) {
            return "Target SDK mismatch: receiver " + resolveInfo.activityInfo + " targets " + resolveInfo.activityInfo.applicationInfo.targetSdkVersion + " but delivery restricted to [" + broadcastOptions.getMinManifestReceiverApiLevel() + ", " + broadcastOptions.getMaxManifestReceiverApiLevel() + "] broadcasting " + broadcastDescription(broadcastRecord, componentName);
        }
        if (broadcastOptions != null && !broadcastOptions.testRequireCompatChange(resolveInfo.activityInfo.applicationInfo.uid)) {
            return "Compat change filtered: broadcasting " + broadcastDescription(broadcastRecord, componentName) + " to uid " + resolveInfo.activityInfo.applicationInfo.uid + " due to compat change " + broadcastRecord.options.getRequireCompatChangeId();
        }
        if (!broadcastSkipPolicy2.mService.validateAssociationAllowedLocked(broadcastRecord.callerPackage, broadcastRecord.callingUid, componentName.getPackageName(), resolveInfo.activityInfo.applicationInfo.uid)) {
            return "Association not allowed: broadcasting " + broadcastDescription(broadcastRecord, componentName);
        }
        if (!broadcastSkipPolicy2.mService.mIntentFirewall.checkBroadcast(broadcastRecord.intent, broadcastRecord.callingUid, broadcastRecord.callingPid, broadcastRecord.resolvedType, resolveInfo.activityInfo.applicationInfo.uid)) {
            return "Firewall blocked: broadcasting " + broadcastDescription(broadcastRecord, componentName);
        }
        if (com.android.server.am.ActivityManagerService.checkComponentPermission(resolveInfo.activityInfo.permission, broadcastRecord.callingPid, broadcastRecord.callingUid, resolveInfo.activityInfo.applicationInfo.uid, resolveInfo.activityInfo.exported) != 0) {
            if (!resolveInfo.activityInfo.exported) {
                return "Permission Denial: broadcasting " + broadcastDescription(broadcastRecord, componentName) + " is not exported from uid " + resolveInfo.activityInfo.applicationInfo.uid;
            }
            return "Permission Denial: broadcasting " + broadcastDescription(broadcastRecord, componentName) + " requires " + resolveInfo.activityInfo.permission;
        }
        int i3 = -1;
        if (resolveInfo.activityInfo.permission != null && (permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(resolveInfo.activityInfo.permission)) != -1) {
            if (broadcastSkipPolicy2.mService.getAppOpsManager().noteOpNoThrow(permissionToOpCode, broadcastRecord.callingUid, broadcastRecord.callerPackage, broadcastRecord.callerFeatureId, "Broadcast delivered to " + resolveInfo.activityInfo.name) != 0) {
                return "Appop Denial: broadcasting " + broadcastDescription(broadcastRecord, componentName) + " requires appop " + android.app.AppOpsManager.permissionToOp(resolveInfo.activityInfo.permission);
            }
        }
        if ((resolveInfo.activityInfo.flags & 1073741824) != 0 && android.app.ActivityManager.checkUidPermission("android.permission.INTERACT_ACROSS_USERS", resolveInfo.activityInfo.applicationInfo.uid) != 0) {
            return "Permission Denial: Receiver " + componentName.flattenToShortString() + " requests FLAG_SINGLE_USER, but app does not hold android.permission.INTERACT_ACROSS_USERS";
        }
        if (resolveInfo.activityInfo.applicationInfo.isInstantApp() && broadcastRecord.callingUid != resolveInfo.activityInfo.applicationInfo.uid) {
            return "Instant App Denial: receiving " + broadcastRecord.intent + " to " + componentName.flattenToShortString() + " due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ") Instant Apps do not support manifest receivers";
        }
        if (broadcastRecord.callerInstantApp && (resolveInfo.activityInfo.flags & 1048576) == 0 && broadcastRecord.callingUid != resolveInfo.activityInfo.applicationInfo.uid) {
            return "Instant App Denial: receiving " + broadcastRecord.intent + " to " + componentName.flattenToShortString() + " requires receiver have visibleToInstantApps set due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
        }
        if (broadcastRecord.curApp != null && broadcastRecord.curApp.mErrorState.isCrashing()) {
            return "Skipping deliver ordered [" + broadcastRecord.queue.toString() + "] " + broadcastRecord + " to " + broadcastRecord.curApp + ": process crashing";
        }
        try {
            if (!android.app.AppGlobals.getPackageManager().isPackageAvailable(resolveInfo.activityInfo.packageName, android.os.UserHandle.getUserId(resolveInfo.activityInfo.applicationInfo.uid))) {
                return "Skipping delivery to " + resolveInfo.activityInfo.packageName + " / " + resolveInfo.activityInfo.applicationInfo.uid + " : package no longer available";
            }
            if (!broadcastSkipPolicy2.requestStartTargetPermissionsReviewIfNeededLocked(broadcastRecord, resolveInfo.activityInfo.packageName, android.os.UserHandle.getUserId(resolveInfo.activityInfo.applicationInfo.uid))) {
                return "Skipping delivery: permission review required for " + broadcastDescription(broadcastRecord, componentName);
            }
            int appStartModeLOSP = broadcastSkipPolicy2.mService.getAppStartModeLOSP(resolveInfo.activityInfo.applicationInfo.uid, resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.applicationInfo.targetSdkVersion, -1, true, false, false);
            if (appStartModeLOSP != 0) {
                if (appStartModeLOSP == 3) {
                    return "Background execution disabled: receiving " + broadcastRecord.intent + " to " + componentName.flattenToShortString();
                }
                if (disallowBackgroundStart(broadcastRecord)) {
                    broadcastSkipPolicy2.mService.addBackgroundCheckViolationLocked(broadcastRecord.intent.getAction(), componentName.getPackageName());
                    return "Background execution not allowed: receiving " + broadcastRecord.intent + " to " + componentName.flattenToShortString();
                }
            }
            if (!"android.intent.action.ACTION_SHUTDOWN".equals(broadcastRecord.intent.getAction()) && !broadcastSkipPolicy2.mService.mUserController.isUserRunning(android.os.UserHandle.getUserId(resolveInfo.activityInfo.applicationInfo.uid), 0)) {
                return "Skipping delivery to " + resolveInfo.activityInfo.packageName + " / " + resolveInfo.activityInfo.applicationInfo.uid + " : user is not running";
            }
            if (broadcastRecord.excludedPermissions != null && broadcastRecord.excludedPermissions.length > 0) {
                int i4 = 0;
                while (i4 < broadcastRecord.excludedPermissions.length) {
                    java.lang.String str = broadcastRecord.excludedPermissions[i4];
                    try {
                        i2 = android.app.AppGlobals.getPackageManager().checkPermission(str, resolveInfo.activityInfo.applicationInfo.packageName, android.os.UserHandle.getUserId(resolveInfo.activityInfo.applicationInfo.uid));
                    } catch (android.os.RemoteException e) {
                        i2 = i3;
                    }
                    int permissionToOpCode2 = android.app.AppOpsManager.permissionToOpCode(str);
                    if (permissionToOpCode2 != i3) {
                        if (i2 == 0 && broadcastSkipPolicy2.mService.getAppOpsManager().checkOpNoThrow(permissionToOpCode2, resolveInfo.activityInfo.applicationInfo.uid, resolveInfo.activityInfo.packageName) == 0) {
                            return "Skipping delivery to " + resolveInfo.activityInfo.packageName + " due to excluded permission " + str;
                        }
                    } else if (i2 == 0) {
                        return "Skipping delivery to " + resolveInfo.activityInfo.packageName + " due to excluded permission " + str;
                    }
                    i4++;
                    i3 = -1;
                    broadcastSkipPolicy2 = this;
                }
            }
            if (broadcastRecord.excludedPackages != null && broadcastRecord.excludedPackages.length > 0 && com.android.internal.util.ArrayUtils.contains(broadcastRecord.excludedPackages, componentName.getPackageName())) {
                return "Skipping delivery of excluded package " + broadcastRecord.intent + " to " + componentName.flattenToShortString() + " excludes package " + componentName.getPackageName() + " due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
            }
            if (resolveInfo.activityInfo.applicationInfo.uid == 1000 || broadcastRecord.requiredPermissions == null || broadcastRecord.requiredPermissions.length <= 0) {
                broadcastSkipPolicy = this;
            } else {
                for (int i5 = 0; i5 < broadcastRecord.requiredPermissions.length; i5++) {
                    java.lang.String str2 = broadcastRecord.requiredPermissions[i5];
                    try {
                        i = android.app.AppGlobals.getPackageManager().checkPermission(str2, resolveInfo.activityInfo.applicationInfo.packageName, android.os.UserHandle.getUserId(resolveInfo.activityInfo.applicationInfo.uid));
                    } catch (android.os.RemoteException e2) {
                        i = -1;
                    }
                    if (i != 0) {
                        return "Permission Denial: receiving " + broadcastRecord.intent + " to " + componentName.flattenToShortString() + " requires " + str2 + " due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
                    }
                    int permissionToOpCode3 = android.app.AppOpsManager.permissionToOpCode(str2);
                    if (permissionToOpCode3 != -1 && permissionToOpCode3 != broadcastRecord.appOp) {
                        if (!noteOpForManifestReceiver(permissionToOpCode3, broadcastRecord, resolveInfo, componentName)) {
                            return "Skipping delivery to " + resolveInfo.activityInfo.packageName + " due to required appop " + permissionToOpCode3;
                        }
                    }
                }
                broadcastSkipPolicy = this;
            }
            if (broadcastRecord.appOp != -1 && !broadcastSkipPolicy.noteOpForManifestReceiver(broadcastRecord.appOp, broadcastRecord, resolveInfo, componentName)) {
                return "Skipping delivery to " + resolveInfo.activityInfo.packageName + " due to required appop " + broadcastRecord.appOp;
            }
            return null;
        } catch (java.lang.Exception e3) {
            return "Exception getting recipient info for " + resolveInfo.activityInfo.packageName;
        }
    }

    public boolean disallowBackgroundStart(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        return (broadcastRecord.intent.getFlags() & 8388608) != 0 || (broadcastRecord.intent.getComponent() == null && broadcastRecord.intent.getPackage() == null && (broadcastRecord.intent.getFlags() & 16777216) == 0 && !isSignaturePerm(broadcastRecord.requiredPermissions));
    }

    @android.annotation.Nullable
    private java.lang.String shouldSkipMessage(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, @android.annotation.NonNull com.android.server.am.BroadcastFilter broadcastFilter) {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        com.android.server.am.BroadcastSkipPolicy broadcastSkipPolicy;
        java.lang.String str6;
        java.lang.String str7;
        java.lang.String str8;
        java.lang.String str9;
        java.lang.String str10;
        java.lang.String str11;
        java.lang.String str12;
        java.lang.String str13;
        java.lang.String str14;
        com.android.server.am.BroadcastSkipPolicy broadcastSkipPolicy2 = this;
        if (broadcastRecord.options != null && !broadcastRecord.options.testRequireCompatChange(broadcastFilter.owningUid)) {
            return "Compat change filtered: broadcasting " + broadcastRecord.intent.toString() + " to uid " + broadcastFilter.owningUid + " due to compat change " + broadcastRecord.options.getRequireCompatChangeId();
        }
        if (!broadcastSkipPolicy2.mService.validateAssociationAllowedLocked(broadcastRecord.callerPackage, broadcastRecord.callingUid, broadcastFilter.packageName, broadcastFilter.owningUid)) {
            return "Association not allowed: broadcasting " + broadcastRecord.intent.toString() + " from " + broadcastRecord.callerPackage + " (pid=" + broadcastRecord.callingPid + ", uid=" + broadcastRecord.callingUid + ") to " + broadcastFilter.packageName + " through " + broadcastFilter;
        }
        if (!broadcastSkipPolicy2.mService.mIntentFirewall.checkBroadcast(broadcastRecord.intent, broadcastRecord.callingUid, broadcastRecord.callingPid, broadcastRecord.resolvedType, broadcastFilter.receiverList.uid)) {
            return "Firewall blocked: broadcasting " + broadcastRecord.intent.toString() + " from " + broadcastRecord.callerPackage + " (pid=" + broadcastRecord.callingPid + ", uid=" + broadcastRecord.callingUid + ") to " + broadcastFilter.packageName + " through " + broadcastFilter;
        }
        java.lang.String str15 = ") requires ";
        java.lang.String str16 = ") requires appop ";
        if (broadcastFilter.requiredPermission != null) {
            if (com.android.server.am.ActivityManagerService.checkComponentPermission(broadcastFilter.requiredPermission, broadcastRecord.callingPid, broadcastRecord.callingUid, -1, true) != 0) {
                return "Permission Denial: broadcasting " + broadcastRecord.intent.toString() + " from " + broadcastRecord.callerPackage + " (pid=" + broadcastRecord.callingPid + ", uid=" + broadcastRecord.callingUid + ") requires " + broadcastFilter.requiredPermission + " due to registered receiver " + broadcastFilter;
            }
            int permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(broadcastFilter.requiredPermission);
            if (permissionToOpCode != -1 && broadcastSkipPolicy2.mService.getAppOpsManager().noteOpNoThrow(permissionToOpCode, broadcastRecord.callingUid, broadcastRecord.callerPackage, broadcastRecord.callerFeatureId, "Broadcast sent to protected receiver") != 0) {
                return "Appop Denial: broadcasting " + broadcastRecord.intent.toString() + " from " + broadcastRecord.callerPackage + " (pid=" + broadcastRecord.callingPid + ", uid=" + broadcastRecord.callingUid + ") requires appop " + android.app.AppOpsManager.permissionToOp(broadcastFilter.requiredPermission) + " due to registered receiver " + broadcastFilter;
            }
        }
        if (broadcastFilter.receiverList.app == null || broadcastFilter.receiverList.app.isKilled() || broadcastFilter.receiverList.app.mErrorState.isCrashing()) {
            return "Skipping deliver [" + broadcastRecord.queue.toString() + "] " + broadcastRecord + " to " + broadcastFilter.receiverList + ": process gone or crashing";
        }
        java.lang.String str17 = ") due to sender ";
        if (!((broadcastRecord.intent.getFlags() & 2097152) != 0) && broadcastFilter.instantApp && broadcastFilter.receiverList.uid != broadcastRecord.callingUid) {
            return "Instant App Denial: receiving " + broadcastRecord.intent.toString() + " to " + broadcastFilter.receiverList.app + " (pid=" + broadcastFilter.receiverList.pid + ", uid=" + broadcastFilter.receiverList.uid + ") due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ") not specifying FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS";
        }
        if (!broadcastFilter.visibleToInstantApp && broadcastRecord.callerInstantApp && broadcastFilter.receiverList.uid != broadcastRecord.callingUid) {
            return "Instant App Denial: receiving " + broadcastRecord.intent.toString() + " to " + broadcastFilter.receiverList.app + " (pid=" + broadcastFilter.receiverList.pid + ", uid=" + broadcastFilter.receiverList.uid + ") requires receiver be visible to instant apps due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
        }
        java.lang.String str18 = "Broadcast delivered to registered receiver ";
        java.lang.String str19 = "Permission Denial: receiving ";
        java.lang.String str20 = "Appop Denial: receiving ";
        if (broadcastRecord.requiredPermissions == null || broadcastRecord.requiredPermissions.length <= 0) {
            str = ") requires appop ";
            str2 = "Broadcast delivered to registered receiver ";
            str3 = "Appop Denial: receiving ";
            str4 = ") due to sender ";
            str5 = "Permission Denial: receiving ";
        } else {
            int i = 0;
            while (true) {
                str4 = str17;
                if (i >= broadcastRecord.requiredPermissions.length) {
                    str = str16;
                    str2 = str18;
                    str3 = str20;
                    str5 = str19;
                    break;
                }
                java.lang.String str21 = broadcastRecord.requiredPermissions[i];
                int i2 = i;
                java.lang.String str22 = str16;
                java.lang.String str23 = str18;
                java.lang.String str24 = str20;
                if (com.android.server.am.ActivityManagerService.checkComponentPermission(str21, broadcastFilter.receiverList.pid, broadcastFilter.receiverList.uid, -1, true) != 0) {
                    return str19 + broadcastRecord.intent.toString() + " to " + broadcastFilter.receiverList.app + " (pid=" + broadcastFilter.receiverList.pid + ", uid=" + broadcastFilter.receiverList.uid + str15 + str21 + " due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
                }
                int permissionToOpCode2 = android.app.AppOpsManager.permissionToOpCode(str21);
                if (permissionToOpCode2 == -1 || permissionToOpCode2 == broadcastRecord.appOp) {
                    str10 = str15;
                    str11 = str19;
                    str12 = str22;
                    str13 = str24;
                    str14 = str23;
                } else {
                    android.app.AppOpsManager appOpsManager = broadcastSkipPolicy2.mService.getAppOpsManager();
                    int i3 = broadcastFilter.receiverList.uid;
                    java.lang.String str25 = broadcastFilter.packageName;
                    str10 = str15;
                    java.lang.String str26 = broadcastFilter.featureId;
                    str11 = str19;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(str23);
                    str14 = str23;
                    sb.append(broadcastFilter.receiverId);
                    if (appOpsManager.noteOpNoThrow(permissionToOpCode2, i3, str25, str26, sb.toString()) == 0) {
                        str12 = str22;
                        str13 = str24;
                    } else {
                        return str24 + broadcastRecord.intent.toString() + " to " + broadcastFilter.receiverList.app + " (pid=" + broadcastFilter.receiverList.pid + ", uid=" + broadcastFilter.receiverList.uid + str22 + android.app.AppOpsManager.permissionToOp(str21) + " due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
                    }
                }
                str20 = str13;
                str16 = str12;
                str17 = str4;
                str15 = str10;
                str19 = str11;
                str18 = str14;
                i = i2 + 1;
                broadcastSkipPolicy2 = this;
            }
        }
        if ((broadcastRecord.requiredPermissions == null || broadcastRecord.requiredPermissions.length == 0) && com.android.server.am.ActivityManagerService.checkComponentPermission(null, broadcastFilter.receiverList.pid, broadcastFilter.receiverList.uid, -1, true) != 0) {
            return "Permission Denial: security check failed when receiving " + broadcastRecord.intent.toString() + " to " + broadcastFilter.receiverList.app + " (pid=" + broadcastFilter.receiverList.pid + ", uid=" + broadcastFilter.receiverList.uid + str4 + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
        }
        if (broadcastRecord.excludedPermissions == null || broadcastRecord.excludedPermissions.length <= 0) {
            broadcastSkipPolicy = this;
            str6 = str;
            str7 = str2;
        } else {
            int i4 = 0;
            while (i4 < broadcastRecord.excludedPermissions.length) {
                java.lang.String str27 = broadcastRecord.excludedPermissions[i4];
                int checkComponentPermission = com.android.server.am.ActivityManagerService.checkComponentPermission(str27, broadcastFilter.receiverList.pid, broadcastFilter.receiverList.uid, -1, true);
                int permissionToOpCode3 = android.app.AppOpsManager.permissionToOpCode(str27);
                if (permissionToOpCode3 == -1) {
                    str8 = str;
                    str9 = str2;
                    if (checkComponentPermission == 0) {
                        return str5 + broadcastRecord.intent.toString() + " to " + broadcastFilter.receiverList.app + " (pid=" + broadcastFilter.receiverList.pid + ", uid=" + broadcastFilter.receiverList.uid + ") excludes " + str27 + " due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
                    }
                } else if (checkComponentPermission == 0) {
                    java.lang.String str28 = str2;
                    str8 = str;
                    if (this.mService.getAppOpsManager().checkOpNoThrow(permissionToOpCode3, broadcastFilter.receiverList.uid, broadcastFilter.packageName) != 0) {
                        str9 = str28;
                    } else {
                        return str3 + broadcastRecord.intent.toString() + " to " + broadcastFilter.receiverList.app + " (pid=" + broadcastFilter.receiverList.pid + ", uid=" + broadcastFilter.receiverList.uid + ") excludes appop " + android.app.AppOpsManager.permissionToOp(str27) + " due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
                    }
                } else {
                    str8 = str;
                    str9 = str2;
                }
                i4++;
                str2 = str9;
                str5 = str5;
                str = str8;
            }
            broadcastSkipPolicy = this;
            str6 = str;
            str7 = str2;
        }
        if (broadcastRecord.excludedPackages != null && broadcastRecord.excludedPackages.length > 0 && com.android.internal.util.ArrayUtils.contains(broadcastRecord.excludedPackages, broadcastFilter.packageName)) {
            return "Skipping delivery of excluded package " + broadcastRecord.intent.toString() + " to " + broadcastFilter.receiverList.app + " (pid=" + broadcastFilter.receiverList.pid + ", uid=" + broadcastFilter.receiverList.uid + ") excludes package " + broadcastFilter.packageName + " due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
        }
        if (broadcastRecord.appOp != -1) {
            if (broadcastSkipPolicy.mService.getAppOpsManager().noteOpNoThrow(broadcastRecord.appOp, broadcastFilter.receiverList.uid, broadcastFilter.packageName, broadcastFilter.featureId, str7 + broadcastFilter.receiverId) != 0) {
                return str3 + broadcastRecord.intent.toString() + " to " + broadcastFilter.receiverList.app + " (pid=" + broadcastFilter.receiverList.pid + ", uid=" + broadcastFilter.receiverList.uid + str6 + android.app.AppOpsManager.opToName(broadcastRecord.appOp) + " due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")";
            }
        }
        int i5 = broadcastRecord.sticky ? broadcastRecord.originalStickyCallingUid : broadcastRecord.callingUid;
        if (!broadcastFilter.exported && com.android.server.am.ActivityManagerService.checkComponentPermission(null, broadcastRecord.callingPid, i5, broadcastFilter.receiverList.uid, broadcastFilter.exported) != 0) {
            return "Exported Denial: sending " + broadcastRecord.intent.toString() + ", action: " + broadcastRecord.intent.getAction() + " from " + broadcastRecord.callerPackage + " (uid=" + i5 + ") due to receiver " + broadcastFilter.receiverList.app + " (uid " + broadcastFilter.receiverList.uid + ") not specifying RECEIVER_EXPORTED";
        }
        if (!broadcastSkipPolicy.requestStartTargetPermissionsReviewIfNeededLocked(broadcastRecord, broadcastFilter.packageName, broadcastFilter.owningUserId)) {
            return "Skipping delivery to " + broadcastFilter.packageName + " due to permissions review";
        }
        return null;
    }

    private static java.lang.String broadcastDescription(com.android.server.am.BroadcastRecord broadcastRecord, android.content.ComponentName componentName) {
        return broadcastRecord.intent.toString() + " from " + broadcastRecord.callerPackage + " (pid=" + broadcastRecord.callingPid + ", uid=" + broadcastRecord.callingUid + ") to " + componentName.flattenToShortString();
    }

    private boolean noteOpForManifestReceiver(int i, com.android.server.am.BroadcastRecord broadcastRecord, android.content.pm.ResolveInfo resolveInfo, android.content.ComponentName componentName) {
        if (com.android.internal.util.ArrayUtils.isEmpty(resolveInfo.activityInfo.attributionTags)) {
            return noteOpForManifestReceiverInner(i, broadcastRecord, resolveInfo, componentName, null);
        }
        for (java.lang.String str : resolveInfo.activityInfo.attributionTags) {
            if (!noteOpForManifestReceiverInner(i, broadcastRecord, resolveInfo, componentName, str)) {
                return false;
            }
        }
        return true;
    }

    private boolean noteOpForManifestReceiverInner(int i, com.android.server.am.BroadcastRecord broadcastRecord, android.content.pm.ResolveInfo resolveInfo, android.content.ComponentName componentName, java.lang.String str) {
        if (this.mService.getAppOpsManager().noteOpNoThrow(i, resolveInfo.activityInfo.applicationInfo.uid, resolveInfo.activityInfo.packageName, str, "Broadcast delivered to " + resolveInfo.activityInfo.name) != 0) {
            android.util.Slog.w(com.android.server.am.BroadcastQueue.TAG, "Appop Denial: receiving " + broadcastRecord.intent + " to " + componentName.flattenToShortString() + " requires appop " + android.app.AppOpsManager.opToName(i) + " due to sender " + broadcastRecord.callerPackage + " (uid " + broadcastRecord.callingUid + ")");
            return false;
        }
        return true;
    }

    private static boolean isSignaturePerm(java.lang.String[] strArr) {
        if (strArr == null) {
            return false;
        }
        android.permission.IPermissionManager permissionManager = android.app.AppGlobals.getPermissionManager();
        for (int length = strArr.length - 1; length >= 0; length--) {
            try {
                android.content.pm.PermissionInfo permissionInfo = permissionManager.getPermissionInfo(strArr[length], com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, 0);
                if (permissionInfo == null || (permissionInfo.protectionLevel & 31) != 2) {
                    return false;
                }
            } catch (android.os.RemoteException e) {
                return false;
            }
        }
        return true;
    }

    private boolean requestStartTargetPermissionsReviewIfNeededLocked(com.android.server.am.BroadcastRecord broadcastRecord, java.lang.String str, final int i) {
        boolean z;
        if (!this.mService.getPackageManagerInternal().isPermissionsReviewRequired(str, i)) {
            return true;
        }
        if (broadcastRecord.callerApp != null) {
            z = broadcastRecord.callerApp.mState.getSetSchedGroup() != 0;
        } else {
            z = true;
        }
        if (z && broadcastRecord.intent.getComponent() != null) {
            com.android.server.am.PendingIntentRecord intentSender = this.mService.mPendingIntentController.getIntentSender(1, broadcastRecord.callerPackage, broadcastRecord.callerFeatureId, broadcastRecord.callingUid, broadcastRecord.userId, null, null, 0, new android.content.Intent[]{broadcastRecord.intent}, new java.lang.String[]{broadcastRecord.intent.resolveType(this.mService.mContext.getContentResolver())}, 1409286144, null);
            final android.content.Intent intent = new android.content.Intent("android.intent.action.REVIEW_PERMISSIONS");
            intent.addFlags(411041792);
            intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
            intent.putExtra("android.intent.extra.INTENT", new android.content.IntentSender(intentSender));
            this.mService.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BroadcastSkipPolicy.1
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.am.BroadcastSkipPolicy.this.mService.mContext.startActivityAsUser(intent, new android.os.UserHandle(i));
                }
            });
        } else {
            android.util.Slog.w(com.android.server.am.BroadcastQueue.TAG, "u" + i + " Receiving a broadcast in package" + str + " requires a permissions review");
        }
        return false;
    }
}
