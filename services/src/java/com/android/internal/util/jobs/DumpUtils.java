package com.android.internal.util.jobs;

@android.ravenwood.annotation.RavenwoodKeepWholeClass
/* loaded from: classes.dex */
public final class DumpUtils {
    public static final android.content.ComponentName[] CRITICAL_SECTION_COMPONENTS = {new android.content.ComponentName("com.android.systemui", "com.android.systemui.SystemUIService")};
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "DumpUtils";

    public interface Dump {
        void dump(java.io.PrintWriter printWriter, java.lang.String str);
    }

    public interface KeyDumper {
        void dump(int i, int i2);
    }

    public interface ValueDumper<T> {
        void dump(T t);
    }

    private DumpUtils() {
    }

    public static void dumpAsync(android.os.Handler handler, final com.android.internal.util.jobs.DumpUtils.Dump dump, java.io.PrintWriter printWriter, final java.lang.String str, long j) {
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        if (handler.runWithScissors(new java.lang.Runnable() { // from class: com.android.internal.util.jobs.DumpUtils.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.util.jobs.FastPrintWriter fastPrintWriter = new com.android.internal.util.jobs.FastPrintWriter(stringWriter);
                dump.dump(fastPrintWriter, str);
                fastPrintWriter.close();
            }
        }, j)) {
            printWriter.print(stringWriter.toString());
        } else {
            printWriter.println("... timed out");
        }
    }

    private static void logMessage(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str);
    }

    @android.ravenwood.annotation.RavenwoodThrow(blockedBy = {android.permission.PermissionManager.class})
    public static boolean checkDumpPermission(android.content.Context context, java.lang.String str, java.io.PrintWriter printWriter) {
        if (context.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            logMessage(printWriter, "Permission Denial: can't dump " + str + " from from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " due to missing android.permission.DUMP permission");
            return false;
        }
        return true;
    }

    @android.ravenwood.annotation.RavenwoodThrow(blockedBy = {android.permission.PermissionManager.class})
    public static boolean checkUsageStatsPermission(android.content.Context context, java.lang.String str, java.io.PrintWriter printWriter) {
        int callingUid = android.os.Binder.getCallingUid();
        switch (callingUid) {
            case 0:
            case 1000:
            case 1067:
            case 2000:
                return true;
            default:
                if (context.checkCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS") != 0) {
                    logMessage(printWriter, "Permission Denial: can't dump " + str + " from from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " due to missing android.permission.PACKAGE_USAGE_STATS permission");
                    return false;
                }
                android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
                java.lang.String[] packagesForUid = context.getPackageManager().getPackagesForUid(callingUid);
                if (packagesForUid != null) {
                    for (java.lang.String str2 : packagesForUid) {
                        switch (appOpsManager.noteOpNoThrow(43, callingUid, str2)) {
                            case 0:
                                return true;
                            case 3:
                                return true;
                            default:
                        }
                    }
                }
                logMessage(printWriter, "Permission Denial: can't dump " + str + " from from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " due to android:get_usage_stats app-op not allowed");
                return false;
        }
    }

    @android.ravenwood.annotation.RavenwoodThrow(blockedBy = {android.permission.PermissionManager.class})
    public static boolean checkDumpAndUsageStatsPermission(android.content.Context context, java.lang.String str, java.io.PrintWriter printWriter) {
        return checkDumpPermission(context, str, printWriter) && checkUsageStatsPermission(context, str, printWriter);
    }

    public static boolean isPlatformPackage(@android.annotation.Nullable java.lang.String str) {
        return str != null && (str.equals(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME) || str.startsWith("android.") || str.startsWith("com.android."));
    }

    public static boolean isPlatformPackage(@android.annotation.Nullable android.content.ComponentName componentName) {
        return componentName != null && isPlatformPackage(componentName.getPackageName());
    }

    public static boolean isPlatformPackage(@android.annotation.Nullable android.content.ComponentName.WithComponentName withComponentName) {
        return withComponentName != null && isPlatformPackage(withComponentName.getComponentName());
    }

    public static boolean isNonPlatformPackage(@android.annotation.Nullable java.lang.String str) {
        return (str == null || isPlatformPackage(str)) ? false : true;
    }

    public static boolean isNonPlatformPackage(@android.annotation.Nullable android.content.ComponentName componentName) {
        return componentName != null && isNonPlatformPackage(componentName.getPackageName());
    }

    public static boolean isNonPlatformPackage(@android.annotation.Nullable android.content.ComponentName.WithComponentName withComponentName) {
        return (withComponentName == null || isPlatformPackage(withComponentName.getComponentName())) ? false : true;
    }

    private static boolean isCriticalPackage(@android.annotation.Nullable android.content.ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        for (int i = 0; i < CRITICAL_SECTION_COMPONENTS.length; i++) {
            if (componentName.equals(CRITICAL_SECTION_COMPONENTS[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPlatformCriticalPackage(@android.annotation.Nullable android.content.ComponentName.WithComponentName withComponentName) {
        return withComponentName != null && isPlatformPackage(withComponentName.getComponentName()) && isCriticalPackage(withComponentName.getComponentName());
    }

    public static boolean isPlatformNonCriticalPackage(@android.annotation.Nullable android.content.ComponentName.WithComponentName withComponentName) {
        return (withComponentName == null || !isPlatformPackage(withComponentName.getComponentName()) || isCriticalPackage(withComponentName.getComponentName())) ? false : true;
    }

    public static <TRec extends android.content.ComponentName.WithComponentName> java.util.function.Predicate<TRec> filterRecord(@android.annotation.Nullable final java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return new java.util.function.Predicate() { // from class: com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$filterRecord$0;
                    lambda$filterRecord$0 = com.android.internal.util.jobs.DumpUtils.lambda$filterRecord$0((android.content.ComponentName.WithComponentName) obj);
                    return lambda$filterRecord$0;
                }
            };
        }
        if ("all".equals(str)) {
            return new java.util.function.Predicate() { // from class: com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return java.util.Objects.nonNull((android.content.ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-platform".equals(str)) {
            return new java.util.function.Predicate() { // from class: com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return com.android.internal.util.jobs.DumpUtils.isPlatformPackage((android.content.ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-non-platform".equals(str)) {
            return new java.util.function.Predicate() { // from class: com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return com.android.internal.util.jobs.DumpUtils.isNonPlatformPackage((android.content.ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-platform-critical".equals(str)) {
            return new java.util.function.Predicate() { // from class: com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return com.android.internal.util.jobs.DumpUtils.isPlatformCriticalPackage((android.content.ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-platform-non-critical".equals(str)) {
            return new java.util.function.Predicate() { // from class: com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return com.android.internal.util.jobs.DumpUtils.isPlatformNonCriticalPackage((android.content.ComponentName.WithComponentName) obj);
                }
            };
        }
        final android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
            return new java.util.function.Predicate() { // from class: com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$filterRecord$1;
                    lambda$filterRecord$1 = com.android.internal.util.jobs.DumpUtils.lambda$filterRecord$1(unflattenFromString, (android.content.ComponentName.WithComponentName) obj);
                    return lambda$filterRecord$1;
                }
            };
        }
        final int parseIntWithBase = com.android.internal.util.jobs.ParseUtils.parseIntWithBase(str, 16, -1);
        return new java.util.function.Predicate() { // from class: com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$filterRecord$2;
                lambda$filterRecord$2 = com.android.internal.util.jobs.DumpUtils.lambda$filterRecord$2(parseIntWithBase, str, (android.content.ComponentName.WithComponentName) obj);
                return lambda$filterRecord$2;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$filterRecord$0(android.content.ComponentName.WithComponentName withComponentName) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$filterRecord$1(android.content.ComponentName componentName, android.content.ComponentName.WithComponentName withComponentName) {
        return withComponentName != null && componentName.equals(withComponentName.getComponentName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$filterRecord$2(int i, java.lang.String str, android.content.ComponentName.WithComponentName withComponentName) {
        return (i != -1 && java.lang.System.identityHashCode(withComponentName) == i) || withComponentName.getComponentName().flattenToString().toLowerCase().contains(str.toLowerCase());
    }

    public static void dumpSparseArray(java.io.PrintWriter printWriter, java.lang.String str, android.util.SparseArray<?> sparseArray, java.lang.String str2) {
        dumpSparseArray(printWriter, str, sparseArray, str2, null, null);
    }

    public static <T> void dumpSparseArrayValues(final java.io.PrintWriter printWriter, final java.lang.String str, android.util.SparseArray<T> sparseArray, java.lang.String str2) {
        dumpSparseArray(printWriter, str, sparseArray, str2, new com.android.internal.util.jobs.DumpUtils.KeyDumper() { // from class: com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.jobs.DumpUtils.KeyDumper
            public final void dump(int i, int i2) {
                com.android.internal.util.jobs.DumpUtils.lambda$dumpSparseArrayValues$3(printWriter, str, i, i2);
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpSparseArrayValues$3(java.io.PrintWriter printWriter, java.lang.String str, int i, int i2) {
        printWriter.printf("%s%s", str, str);
    }

    public static <T> void dumpSparseArray(java.io.PrintWriter printWriter, java.lang.String str, android.util.SparseArray<T> sparseArray, java.lang.String str2, @android.annotation.Nullable com.android.internal.util.jobs.DumpUtils.KeyDumper keyDumper, @android.annotation.Nullable com.android.internal.util.jobs.DumpUtils.ValueDumper<T> valueDumper) {
        int size = sparseArray.size();
        if (size == 0) {
            printWriter.print(str);
            printWriter.print("No ");
            printWriter.print(str2);
            printWriter.println("s");
            return;
        }
        printWriter.print(str);
        printWriter.print(size);
        printWriter.print(' ');
        printWriter.print(str2);
        printWriter.println("(s):");
        java.lang.String str3 = str + str;
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            T valueAt = sparseArray.valueAt(i);
            if (keyDumper != null) {
                keyDumper.dump(i, keyAt);
            } else {
                printWriter.print(str3);
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.print(keyAt);
                printWriter.print("->");
            }
            if (valueAt == null) {
                printWriter.print("(null)");
            } else if (valueDumper != null) {
                valueDumper.dump(valueAt);
            } else {
                printWriter.print(valueAt);
            }
            printWriter.println();
        }
    }
}
