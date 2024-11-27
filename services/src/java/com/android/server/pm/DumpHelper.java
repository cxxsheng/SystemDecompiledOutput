package com.android.server.pm;

/* loaded from: classes2.dex */
final class DumpHelper {
    private final android.util.ArrayMap<java.lang.String, android.content.pm.FeatureInfo> mAvailableFeatures;
    private final com.android.server.pm.ChangedPackagesTracker mChangedPackagesTracker;
    private final com.android.server.pm.verify.domain.DomainVerificationManagerInternal mDomainVerificationManager;
    private final com.android.server.pm.PackageInstallerService mInstallerService;
    private final com.android.server.pm.KnownPackages mKnownPackages;
    private final android.os.incremental.PerUidReadTimeouts[] mPerUidReadTimeouts;
    private final com.android.server.pm.permission.PermissionManagerServiceInternal mPermissionManager;
    private final android.util.ArraySet<java.lang.String> mProtectedBroadcasts;
    private final java.lang.String[] mRequiredVerifierPackages;
    private final com.android.server.pm.SnapshotStatistics mSnapshotStatistics;
    private final com.android.server.pm.StorageEventHelper mStorageEventHelper;

    DumpHelper(com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal, com.android.server.pm.StorageEventHelper storageEventHelper, com.android.server.pm.verify.domain.DomainVerificationManagerInternal domainVerificationManagerInternal, com.android.server.pm.PackageInstallerService packageInstallerService, java.lang.String[] strArr, com.android.server.pm.KnownPackages knownPackages, com.android.server.pm.ChangedPackagesTracker changedPackagesTracker, android.util.ArrayMap<java.lang.String, android.content.pm.FeatureInfo> arrayMap, android.util.ArraySet<java.lang.String> arraySet, android.os.incremental.PerUidReadTimeouts[] perUidReadTimeoutsArr, com.android.server.pm.SnapshotStatistics snapshotStatistics) {
        this.mPermissionManager = permissionManagerServiceInternal;
        this.mStorageEventHelper = storageEventHelper;
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        this.mInstallerService = packageInstallerService;
        this.mRequiredVerifierPackages = strArr;
        this.mKnownPackages = knownPackages;
        this.mChangedPackagesTracker = changedPackagesTracker;
        this.mAvailableFeatures = arrayMap;
        this.mProtectedBroadcasts = arraySet;
        this.mPerUidReadTimeouts = perUidReadTimeoutsArr;
        this.mSnapshotStatistics = snapshotStatistics;
    }

    /* JADX WARN: Removed duplicated region for block: B:202:0x0744  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x077a  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x079c  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0809  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0822  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x083c  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x086e  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x08c9 A[LOOP:5: B:270:0x08c7->B:271:0x08c9, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0951  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x075b  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x04ce  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x04f8  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x050f  */
    @dalvik.annotation.optimization.NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void doDump(com.android.server.pm.Computer computer, java.io.FileDescriptor fileDescriptor, final java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        android.util.ArraySet<java.lang.String> arraySet;
        java.lang.String targetPackageName;
        boolean isCheckIn;
        java.lang.String str;
        java.lang.String str2;
        com.android.server.pm.resolution.ComponentResolverApi componentResolver;
        java.lang.String str3;
        android.util.ArraySet<java.lang.String> arraySet2;
        int i;
        int i2;
        java.lang.String str4;
        java.lang.String str5;
        int i3;
        java.lang.String str6;
        com.android.server.pm.DumpState dumpState = new com.android.server.pm.DumpState();
        int i4 = 0;
        while (i4 < strArr.length && (str6 = strArr[i4]) != null && str6.length() > 0 && str6.charAt(0) == '-') {
            i4++;
            if (!"-a".equals(str6)) {
                if ("-h".equals(str6)) {
                    printHelp(printWriter);
                    return;
                }
                if ("--checkin".equals(str6)) {
                    dumpState.setCheckIn(true);
                } else if ("--all-components".equals(str6)) {
                    dumpState.setOptionEnabled(2);
                } else if ("-f".equals(str6)) {
                    dumpState.setOptionEnabled(1);
                } else if ("--include-apex".equals(str6)) {
                    dumpState.setOptionEnabled(8);
                } else {
                    if ("--proto".equals(str6)) {
                        dumpProto(computer, fileDescriptor);
                        return;
                    }
                    printWriter.println("Unknown argument: " + str6 + "; use -h for help");
                }
            }
        }
        if (i4 < strArr.length) {
            java.lang.String str7 = strArr[i4];
            int i5 = i4 + 1;
            if (!com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str7) && !str7.contains(".")) {
                if ("check-permission".equals(str7)) {
                    if (i5 >= strArr.length) {
                        printWriter.println("Error: check-permission missing permission argument");
                        return;
                    }
                    java.lang.String str8 = strArr[i5];
                    int i6 = i5 + 1;
                    if (i6 >= strArr.length) {
                        printWriter.println("Error: check-permission missing package argument");
                        return;
                    }
                    java.lang.String str9 = strArr[i6];
                    int i7 = i6 + 1;
                    int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
                    if (i7 < strArr.length) {
                        try {
                            userId = java.lang.Integer.parseInt(strArr[i7]);
                        } catch (java.lang.NumberFormatException e) {
                            printWriter.println("Error: check-permission user argument is not a number: " + strArr[i7]);
                            return;
                        }
                    }
                    printWriter.println(this.mPermissionManager.checkPermission(computer.resolveInternalPackageName(str9, -1L), str8, "default:0", userId));
                    return;
                }
                if (!"l".equals(str7) && !"libraries".equals(str7)) {
                    if (!"f".equals(str7) && !"features".equals(str7)) {
                        if (!com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD.equals(str7) && !"resolvers".equals(str7)) {
                            if (!"perm".equals(str7) && !"permissions".equals(str7)) {
                                if ("permission".equals(str7)) {
                                    if (i5 >= strArr.length) {
                                        printWriter.println("Error: permission requires permission name");
                                        return;
                                    }
                                    android.util.ArraySet<java.lang.String> arraySet3 = new android.util.ArraySet<>();
                                    while (i5 < strArr.length) {
                                        arraySet3.add(strArr[i5]);
                                        i5++;
                                    }
                                    dumpState.setDump(448);
                                    arraySet = arraySet3;
                                    targetPackageName = dumpState.getTargetPackageName();
                                    isCheckIn = dumpState.isCheckIn();
                                    if (targetPackageName == null && computer.getPackageStateInternal(targetPackageName) == null && !computer.isApexPackage(targetPackageName)) {
                                        printWriter.println("Unable to find package: " + targetPackageName);
                                        return;
                                    }
                                    if (isCheckIn) {
                                        printWriter.println("vers,1");
                                    }
                                    if (!isCheckIn && dumpState.isDumping(32768) && targetPackageName == null) {
                                        computer.dump(32768, fileDescriptor, printWriter, dumpState);
                                    }
                                    java.lang.String str10 = "  ";
                                    if (!isCheckIn && dumpState.isDumping(134217728) && targetPackageName == null) {
                                        if (dumpState.onTitlePrinted()) {
                                            printWriter.println();
                                        }
                                        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ", 120);
                                        indentingPrintWriter.println("Known Packages:");
                                        indentingPrintWriter.increaseIndent();
                                        for (i3 = 0; i3 <= 19; i3++) {
                                            indentingPrintWriter.print(com.android.server.pm.KnownPackages.knownPackageToString(i3));
                                            indentingPrintWriter.println(":");
                                            java.lang.String[] knownPackageNames = this.mKnownPackages.getKnownPackageNames(computer, i3, 0);
                                            indentingPrintWriter.increaseIndent();
                                            if (com.android.internal.util.ArrayUtils.isEmpty(knownPackageNames)) {
                                                indentingPrintWriter.println("none");
                                            } else {
                                                for (java.lang.String str11 : knownPackageNames) {
                                                    indentingPrintWriter.println(str11);
                                                }
                                            }
                                            indentingPrintWriter.decreaseIndent();
                                        }
                                        indentingPrintWriter.decreaseIndent();
                                    }
                                    if (dumpState.isDumping(2048) || targetPackageName != null) {
                                        str = "  ";
                                    } else {
                                        if (!isCheckIn && this.mRequiredVerifierPackages.length > 0) {
                                            if (dumpState.onTitlePrinted()) {
                                                printWriter.println();
                                            }
                                            printWriter.println("Verifiers:");
                                        }
                                        java.lang.String[] strArr2 = this.mRequiredVerifierPackages;
                                        int length = strArr2.length;
                                        int i8 = 0;
                                        while (i8 < length) {
                                            java.lang.String str12 = strArr2[i8];
                                            if (!isCheckIn) {
                                                printWriter.print("  Required: ");
                                                printWriter.print(str12);
                                                printWriter.print(" (uid=");
                                                str5 = str10;
                                                printWriter.print(computer.getPackageUid(str12, 268435456L, 0));
                                                printWriter.println(")");
                                            } else {
                                                str5 = str10;
                                                printWriter.print("vrfy,");
                                                printWriter.print(str12);
                                                printWriter.print(",");
                                                printWriter.println(computer.getPackageUid(str12, 268435456L, 0));
                                            }
                                            i8++;
                                            str10 = str5;
                                        }
                                        str = str10;
                                    }
                                    if (!dumpState.isDumping(131072) && targetPackageName == null) {
                                        android.content.ComponentName componentName = this.mDomainVerificationManager.getProxy().getComponentName();
                                        if (componentName != null) {
                                            java.lang.String packageName = componentName.getPackageName();
                                            if (!isCheckIn) {
                                                if (dumpState.onTitlePrinted()) {
                                                    printWriter.println();
                                                }
                                                printWriter.println("Domain Verifier:");
                                                printWriter.print("  Using: ");
                                                printWriter.print(packageName);
                                                printWriter.print(" (uid=");
                                                printWriter.print(computer.getPackageUid(packageName, 268435456L, 0));
                                                printWriter.println(")");
                                            } else if (packageName != null) {
                                                printWriter.print("dv,");
                                                printWriter.print(packageName);
                                                printWriter.print(",");
                                                printWriter.println(computer.getPackageUid(packageName, 268435456L, 0));
                                            }
                                        } else {
                                            printWriter.println();
                                            printWriter.println("No Domain Verifier available!");
                                        }
                                    }
                                    if (dumpState.isDumping(1) && targetPackageName == null) {
                                        computer.dump(1, fileDescriptor, printWriter, dumpState);
                                    }
                                    if (dumpState.isDumping(2) || targetPackageName != null) {
                                        str2 = str;
                                    } else {
                                        if (dumpState.onTitlePrinted()) {
                                            printWriter.println();
                                        }
                                        if (!isCheckIn) {
                                            printWriter.println("Features:");
                                        }
                                        for (android.content.pm.FeatureInfo featureInfo : this.mAvailableFeatures.values()) {
                                            if (!isCheckIn) {
                                                str4 = str;
                                                printWriter.print(str4);
                                                printWriter.print(featureInfo.name);
                                                if (featureInfo.version > 0) {
                                                    printWriter.print(" version=");
                                                    printWriter.print(featureInfo.version);
                                                }
                                                printWriter.println();
                                            } else {
                                                str4 = str;
                                                printWriter.print("feat,");
                                                printWriter.print(featureInfo.name);
                                                printWriter.print(",");
                                                printWriter.println(featureInfo.version);
                                            }
                                            str = str4;
                                        }
                                        str2 = str;
                                    }
                                    componentResolver = computer.getComponentResolver();
                                    if (!isCheckIn && dumpState.isDumping(4)) {
                                        componentResolver.dumpActivityResolvers(printWriter, dumpState, targetPackageName);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(16)) {
                                        componentResolver.dumpReceiverResolvers(printWriter, dumpState, targetPackageName);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(8)) {
                                        componentResolver.dumpServiceResolvers(printWriter, dumpState, targetPackageName);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(32)) {
                                        componentResolver.dumpProviderResolvers(printWriter, dumpState, targetPackageName);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(4096)) {
                                        computer.dump(4096, fileDescriptor, printWriter, dumpState);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(8192) && targetPackageName == null) {
                                        computer.dump(8192, fileDescriptor, printWriter, dumpState);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(262144)) {
                                        computer.dump(262144, fileDescriptor, printWriter, dumpState);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(64)) {
                                        computer.dumpPermissions(printWriter, targetPackageName, arraySet, dumpState);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(1024)) {
                                        componentResolver.dumpContentProviders(computer, printWriter, dumpState, targetPackageName);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(16384)) {
                                        computer.dumpKeySet(printWriter, targetPackageName, dumpState);
                                    }
                                    if (dumpState.isDumping(128)) {
                                        str3 = targetPackageName;
                                        arraySet2 = arraySet;
                                        i = 512;
                                        i2 = 2097152;
                                    } else {
                                        str3 = targetPackageName;
                                        i2 = 2097152;
                                        arraySet2 = arraySet;
                                        i = 512;
                                        computer.dumpPackages(printWriter, targetPackageName, arraySet, dumpState, isCheckIn);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(67108864)) {
                                        computer.dump(67108864, fileDescriptor, printWriter, dumpState);
                                    }
                                    if (dumpState.isDumping(256)) {
                                        computer.dumpSharedUsers(printWriter, str3, arraySet2, dumpState, isCheckIn);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(4194304) && str3 == null) {
                                        if (dumpState.onTitlePrinted()) {
                                            printWriter.println();
                                        }
                                        printWriter.println("Package Changes:");
                                        this.mChangedPackagesTracker.iterateAll(new java.util.function.BiConsumer() { // from class: com.android.server.pm.DumpHelper$$ExternalSyntheticLambda0
                                            @Override // java.util.function.BiConsumer
                                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                                com.android.server.pm.DumpHelper.lambda$doDump$0(printWriter, (java.lang.Integer) obj, (android.util.SparseArray) obj2);
                                            }
                                        });
                                    }
                                    if (!isCheckIn && dumpState.isDumping(524288) && str3 == null) {
                                        computer.dump(524288, fileDescriptor, printWriter, dumpState);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(8388608) && str3 == null) {
                                        this.mStorageEventHelper.dumpLoadedVolumes(printWriter, dumpState);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(16777216) && str3 == null) {
                                        componentResolver.dumpServicePermissions(printWriter, dumpState);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(1048576)) {
                                        computer.dump(1048576, fileDescriptor, printWriter, dumpState);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(i2)) {
                                        computer.dump(i2, fileDescriptor, printWriter, dumpState);
                                    }
                                    if (dumpState.isDumping(i) && str3 == null) {
                                        if (isCheckIn) {
                                            if (dumpState.onTitlePrinted()) {
                                                printWriter.println();
                                            }
                                            computer.dump(i, fileDescriptor, printWriter, dumpState);
                                            printWriter.println();
                                            printWriter.println("Package warning messages:");
                                            com.android.server.pm.PackageManagerServiceUtils.dumpCriticalInfo(printWriter, null);
                                        } else {
                                            com.android.server.pm.PackageManagerServiceUtils.dumpCriticalInfo(printWriter, "msg,");
                                        }
                                    }
                                    if (!isCheckIn && dumpState.isDumping(65536) && str3 == null) {
                                        if (dumpState.onTitlePrinted()) {
                                            printWriter.println();
                                        }
                                        this.mInstallerService.dump(new com.android.internal.util.IndentingPrintWriter(printWriter, str2, 120));
                                    }
                                    if (!isCheckIn && dumpState.isDumping(33554432)) {
                                        computer.dump(33554432, fileDescriptor, printWriter, dumpState);
                                    }
                                    if (!isCheckIn && dumpState.isDumping(268435456) && str3 == null) {
                                        if (dumpState.onTitlePrinted()) {
                                            printWriter.println();
                                        }
                                        printWriter.println("Per UID read timeouts:");
                                        printWriter.println("    Default timeouts flag: " + com.android.server.pm.PackageManagerService.getDefaultTimeouts());
                                        printWriter.println("    Known digesters list flag: " + com.android.server.pm.PackageManagerService.getKnownDigestersList());
                                        printWriter.println("    Timeouts (" + this.mPerUidReadTimeouts.length + "):");
                                        for (android.os.incremental.PerUidReadTimeouts perUidReadTimeouts : this.mPerUidReadTimeouts) {
                                            printWriter.print("        (");
                                            printWriter.print("uid=" + perUidReadTimeouts.uid + ", ");
                                            printWriter.print("minTimeUs=" + perUidReadTimeouts.minTimeUs + ", ");
                                            printWriter.print("minPendingTimeUs=" + perUidReadTimeouts.minPendingTimeUs + ", ");
                                            java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                            sb.append("maxPendingTimeUs=");
                                            sb.append(perUidReadTimeouts.maxPendingTimeUs);
                                            printWriter.print(sb.toString());
                                            printWriter.println(")");
                                        }
                                    }
                                    if (!isCheckIn && dumpState.isDumping(536870912) && str3 == null) {
                                        if (dumpState.onTitlePrinted()) {
                                            printWriter.println();
                                        }
                                        printWriter.println("Snapshot statistics:");
                                        this.mSnapshotStatistics.dump(printWriter, "   ", android.os.SystemClock.currentTimeMicro(), computer.getUsed(), dumpState.isBrief());
                                    }
                                    if (isCheckIn && dumpState.isDumping(1073741824) && str3 == null) {
                                        if (dumpState.onTitlePrinted()) {
                                            printWriter.println();
                                        }
                                        printWriter.println("Protected broadcast actions:");
                                        for (int i9 = 0; i9 < this.mProtectedBroadcasts.size(); i9++) {
                                            printWriter.print(str2);
                                            printWriter.println(this.mProtectedBroadcasts.valueAt(i9));
                                        }
                                        return;
                                    }
                                    return;
                                }
                                if (!"pref".equals(str7) && !"preferred".equals(str7)) {
                                    if ("preferred-xml".equals(str7)) {
                                        dumpState.setDump(8192);
                                        if (i5 < strArr.length && "--full".equals(strArr[i5])) {
                                            dumpState.setFullPreferred(true);
                                        }
                                    } else {
                                        if (!"d".equals(str7) && !"domain-preferred-apps".equals(str7)) {
                                            if (!"p".equals(str7) && !"packages".equals(str7)) {
                                                if (!"q".equals(str7) && !"queries".equals(str7)) {
                                                    if (!"s".equals(str7) && !"shared-users".equals(str7)) {
                                                        if (!"prov".equals(str7) && !"providers".equals(str7)) {
                                                            if ("m".equals(str7) || "messages".equals(str7)) {
                                                                dumpState.setDump(512);
                                                            } else if ("v".equals(str7) || "verifiers".equals(str7)) {
                                                                dumpState.setDump(2048);
                                                            } else if ("dv".equals(str7) || "domain-verifier".equals(str7)) {
                                                                dumpState.setDump(131072);
                                                            } else if ("version".equals(str7)) {
                                                                dumpState.setDump(32768);
                                                            } else if ("k".equals(str7) || "keysets".equals(str7)) {
                                                                dumpState.setDump(16384);
                                                            } else if ("installs".equals(str7)) {
                                                                dumpState.setDump(65536);
                                                            } else if ("frozen".equals(str7)) {
                                                                dumpState.setDump(524288);
                                                            } else if ("volumes".equals(str7)) {
                                                                dumpState.setDump(8388608);
                                                            } else if ("dexopt".equals(str7)) {
                                                                dumpState.setDump(1048576);
                                                            } else if ("compiler-stats".equals(str7)) {
                                                                dumpState.setDump(2097152);
                                                            } else if ("changes".equals(str7)) {
                                                                dumpState.setDump(4194304);
                                                            } else if ("service-permissions".equals(str7)) {
                                                                dumpState.setDump(16777216);
                                                            } else if ("known-packages".equals(str7)) {
                                                                dumpState.setDump(134217728);
                                                            } else if ("t".equals(str7) || "timeouts".equals(str7)) {
                                                                dumpState.setDump(268435456);
                                                            } else if ("snapshot".equals(str7)) {
                                                                dumpState.setDump(536870912);
                                                                if (i5 < strArr.length) {
                                                                    if ("--full".equals(strArr[i5])) {
                                                                        dumpState.setBrief(false);
                                                                    } else if ("--brief".equals(strArr[i5])) {
                                                                        dumpState.setBrief(true);
                                                                    }
                                                                }
                                                            } else if ("protected-broadcasts".equals(str7)) {
                                                                dumpState.setDump(1073741824);
                                                            }
                                                        }
                                                        dumpState.setDump(1024);
                                                    }
                                                    dumpState.setDump(256);
                                                    if (i5 < strArr.length && "noperm".equals(strArr[i5])) {
                                                        dumpState.setOptionEnabled(4);
                                                    }
                                                }
                                                dumpState.setDump(67108864);
                                            }
                                            dumpState.setDump(128);
                                        }
                                        dumpState.setDump(262144);
                                    }
                                }
                                dumpState.setDump(4096);
                            }
                            dumpState.setDump(64);
                        }
                        if (i5 >= strArr.length) {
                            dumpState.setDump(60);
                        } else {
                            while (i5 < strArr.length) {
                                java.lang.String str13 = strArr[i5];
                                if (com.android.server.wm.ActivityTaskManagerService.DUMP_ACTIVITIES_SHORT_CMD.equals(str13) || com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY.equals(str13)) {
                                    dumpState.setDump(4);
                                } else if ("s".equals(str13) || com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE.equals(str13)) {
                                    dumpState.setDump(8);
                                } else if (com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD.equals(str13) || "receiver".equals(str13)) {
                                    dumpState.setDump(16);
                                } else if ("c".equals(str13) || com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(str13)) {
                                    dumpState.setDump(32);
                                } else {
                                    printWriter.println("Error: unknown resolver table type: " + str13);
                                    return;
                                }
                                i5++;
                            }
                        }
                    }
                    dumpState.setDump(2);
                }
                dumpState.setDump(1);
            }
            dumpState.setTargetPackageName(str7);
            dumpState.setOptionEnabled(1);
        }
        arraySet = null;
        targetPackageName = dumpState.getTargetPackageName();
        isCheckIn = dumpState.isCheckIn();
        if (targetPackageName == null) {
        }
        if (isCheckIn) {
        }
        if (!isCheckIn) {
            computer.dump(32768, fileDescriptor, printWriter, dumpState);
        }
        java.lang.String str102 = "  ";
        if (!isCheckIn) {
            if (dumpState.onTitlePrinted()) {
            }
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter2 = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ", 120);
            indentingPrintWriter2.println("Known Packages:");
            indentingPrintWriter2.increaseIndent();
            while (i3 <= 19) {
            }
            indentingPrintWriter2.decreaseIndent();
        }
        if (dumpState.isDumping(2048)) {
        }
        str = "  ";
        if (!dumpState.isDumping(131072)) {
        }
        if (dumpState.isDumping(1)) {
            computer.dump(1, fileDescriptor, printWriter, dumpState);
        }
        if (dumpState.isDumping(2)) {
        }
        str2 = str;
        componentResolver = computer.getComponentResolver();
        if (!isCheckIn) {
            componentResolver.dumpActivityResolvers(printWriter, dumpState, targetPackageName);
        }
        if (!isCheckIn) {
            componentResolver.dumpReceiverResolvers(printWriter, dumpState, targetPackageName);
        }
        if (!isCheckIn) {
            componentResolver.dumpServiceResolvers(printWriter, dumpState, targetPackageName);
        }
        if (!isCheckIn) {
            componentResolver.dumpProviderResolvers(printWriter, dumpState, targetPackageName);
        }
        if (!isCheckIn) {
            computer.dump(4096, fileDescriptor, printWriter, dumpState);
        }
        if (!isCheckIn) {
            computer.dump(8192, fileDescriptor, printWriter, dumpState);
        }
        if (!isCheckIn) {
            computer.dump(262144, fileDescriptor, printWriter, dumpState);
        }
        if (!isCheckIn) {
            computer.dumpPermissions(printWriter, targetPackageName, arraySet, dumpState);
        }
        if (!isCheckIn) {
            componentResolver.dumpContentProviders(computer, printWriter, dumpState, targetPackageName);
        }
        if (!isCheckIn) {
            computer.dumpKeySet(printWriter, targetPackageName, dumpState);
        }
        if (dumpState.isDumping(128)) {
        }
        if (!isCheckIn) {
            computer.dump(67108864, fileDescriptor, printWriter, dumpState);
        }
        if (dumpState.isDumping(256)) {
        }
        if (!isCheckIn) {
            if (dumpState.onTitlePrinted()) {
            }
            printWriter.println("Package Changes:");
            this.mChangedPackagesTracker.iterateAll(new java.util.function.BiConsumer() { // from class: com.android.server.pm.DumpHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.pm.DumpHelper.lambda$doDump$0(printWriter, (java.lang.Integer) obj, (android.util.SparseArray) obj2);
                }
            });
        }
        if (!isCheckIn) {
            computer.dump(524288, fileDescriptor, printWriter, dumpState);
        }
        if (!isCheckIn) {
            this.mStorageEventHelper.dumpLoadedVolumes(printWriter, dumpState);
        }
        if (!isCheckIn) {
            componentResolver.dumpServicePermissions(printWriter, dumpState);
        }
        if (!isCheckIn) {
            computer.dump(1048576, fileDescriptor, printWriter, dumpState);
        }
        if (!isCheckIn) {
            computer.dump(i2, fileDescriptor, printWriter, dumpState);
        }
        if (dumpState.isDumping(i)) {
            if (isCheckIn) {
            }
        }
        if (!isCheckIn) {
            if (dumpState.onTitlePrinted()) {
            }
            this.mInstallerService.dump(new com.android.internal.util.IndentingPrintWriter(printWriter, str2, 120));
        }
        if (!isCheckIn) {
            computer.dump(33554432, fileDescriptor, printWriter, dumpState);
        }
        if (!isCheckIn) {
            if (dumpState.onTitlePrinted()) {
            }
            printWriter.println("Per UID read timeouts:");
            printWriter.println("    Default timeouts flag: " + com.android.server.pm.PackageManagerService.getDefaultTimeouts());
            printWriter.println("    Known digesters list flag: " + com.android.server.pm.PackageManagerService.getKnownDigestersList());
            printWriter.println("    Timeouts (" + this.mPerUidReadTimeouts.length + "):");
            while (r3 < r2) {
            }
        }
        if (!isCheckIn) {
            if (dumpState.onTitlePrinted()) {
            }
            printWriter.println("Snapshot statistics:");
            this.mSnapshotStatistics.dump(printWriter, "   ", android.os.SystemClock.currentTimeMicro(), computer.getUsed(), dumpState.isBrief());
        }
        if (isCheckIn) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doDump$0(java.io.PrintWriter printWriter, java.lang.Integer num, android.util.SparseArray sparseArray) {
        printWriter.print("  Sequence number=");
        printWriter.println(num);
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            android.util.SparseArray sparseArray2 = (android.util.SparseArray) sparseArray.valueAt(i);
            printWriter.print("  User ");
            printWriter.print(sparseArray.keyAt(i));
            printWriter.println(":");
            int size2 = sparseArray2.size();
            if (size2 == 0) {
                printWriter.print("    ");
                printWriter.println("No packages changed");
            } else {
                for (int i2 = 0; i2 < size2; i2++) {
                    java.lang.String str = (java.lang.String) sparseArray2.valueAt(i2);
                    int keyAt = sparseArray2.keyAt(i2);
                    printWriter.print("    ");
                    printWriter.print("seq=");
                    printWriter.print(keyAt);
                    printWriter.print(", package=");
                    printWriter.println(str);
                }
            }
        }
    }

    private void printHelp(java.io.PrintWriter printWriter) {
        printWriter.println("Package manager dump options:");
        printWriter.println("  [-h] [-f] [--checkin] [--all-components] [cmd] ...");
        printWriter.println("    --checkin: dump for a checkin");
        printWriter.println("    -f: print details of intent filters");
        printWriter.println("    -h: print this help");
        printWriter.println("    --proto: dump data to proto");
        printWriter.println("    --all-components: include all component names in package dump");
        printWriter.println("    --include-apex: includes the apex packages in package dump");
        printWriter.println("  cmd may be one of:");
        printWriter.println("    apex: list active APEXes and APEX session state");
        printWriter.println("    l[ibraries]: list known shared libraries");
        printWriter.println("    f[eatures]: list device features");
        printWriter.println("    k[eysets]: print known keysets");
        printWriter.println("    r[esolvers] [activity|service|receiver|content]: dump intent resolvers");
        printWriter.println("    perm[issions]: dump permissions");
        printWriter.println("    permission [name ...]: dump declaration and use of given permission");
        printWriter.println("    pref[erred]: print preferred package settings");
        printWriter.println("    preferred-xml [--full]: print preferred package settings as xml");
        printWriter.println("    prov[iders]: dump content providers");
        printWriter.println("    p[ackages]: dump installed packages");
        printWriter.println("    q[ueries]: dump app queryability calculations");
        printWriter.println("    s[hared-users] [noperm]: dump shared user IDs");
        printWriter.println("    m[essages]: print collected runtime messages");
        printWriter.println("    v[erifiers]: print package verifier info");
        printWriter.println("    d[omain-preferred-apps]: print domains preferred apps");
        printWriter.println("    i[ntent-filter-verifiers]|ifv: print intent filter verifier info");
        printWriter.println("    t[imeouts]: print read timeouts for known digesters");
        printWriter.println("    version: print database version info");
        printWriter.println("    write: write current settings now");
        printWriter.println("    installs: details about install sessions");
        printWriter.println("    check-permission <permission> <package> [<user>]: does pkg hold perm?");
        printWriter.println("    dexopt: dump dexopt state");
        printWriter.println("    compiler-stats: dump compiler statistics");
        printWriter.println("    service-permissions: dump permissions required by services");
        printWriter.println("    snapshot [--full|--brief]: dump snapshot statistics");
        printWriter.println("    protected-broadcasts: print list of protected broadcast actions");
        printWriter.println("    known-packages: dump known packages");
        printWriter.println("    changes: dump the packages that have been changed");
        printWriter.println("    frozen: dump the frozen packages");
        printWriter.println("    volumes: dump the loaded volumes");
        printWriter.println("    <package.name>: info about given package");
    }

    private void dumpProto(com.android.server.pm.Computer computer, java.io.FileDescriptor fileDescriptor) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        for (java.lang.String str : this.mRequiredVerifierPackages) {
            long start = protoOutputStream.start(1146756268033L);
            protoOutputStream.write(1138166333441L, str);
            protoOutputStream.write(1120986464258L, computer.getPackageUid(str, 268435456L, 0));
            protoOutputStream.end(start);
        }
        android.content.ComponentName componentName = this.mDomainVerificationManager.getProxy().getComponentName();
        if (componentName != null) {
            java.lang.String packageName = componentName.getPackageName();
            long start2 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1138166333441L, packageName);
            protoOutputStream.write(1120986464258L, computer.getPackageUid(packageName, 268435456L, 0));
            protoOutputStream.end(start2);
        }
        computer.dumpSharedLibrariesProto(protoOutputStream);
        dumpAvailableFeaturesProto(protoOutputStream);
        computer.dumpPackagesProto(protoOutputStream);
        computer.dumpSharedUsersProto(protoOutputStream);
        com.android.server.pm.PackageManagerServiceUtils.dumpCriticalInfo(protoOutputStream);
        protoOutputStream.flush();
    }

    private void dumpAvailableFeaturesProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream) {
        int size = this.mAvailableFeatures.size();
        for (int i = 0; i < size; i++) {
            this.mAvailableFeatures.valueAt(i).dumpDebug(protoOutputStream, 2246267895812L);
        }
    }
}
