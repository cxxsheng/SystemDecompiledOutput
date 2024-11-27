package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class LegacyPermissionSettings {

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.permission.LegacyPermission> mPermissions = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.permission.LegacyPermission> mPermissionTrees = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final com.android.server.pm.PackageManagerTracedLock mLock = new com.android.server.pm.PackageManagerTracedLock();

    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.permission.LegacyPermission> getPermissions() {
        java.util.ArrayList arrayList;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                arrayList = new java.util.ArrayList(this.mPermissions.values());
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return arrayList;
    }

    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.permission.LegacyPermission> getPermissionTrees() {
        java.util.ArrayList arrayList;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                arrayList = new java.util.ArrayList(this.mPermissionTrees.values());
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return arrayList;
    }

    public void replacePermissions(@android.annotation.NonNull java.util.List<com.android.server.pm.permission.LegacyPermission> list) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPermissions.clear();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.pm.permission.LegacyPermission legacyPermission = list.get(i);
                    this.mPermissions.put(legacyPermission.getPermissionInfo().name, legacyPermission);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    public void replacePermissionTrees(@android.annotation.NonNull java.util.List<com.android.server.pm.permission.LegacyPermission> list) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPermissionTrees.clear();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.pm.permission.LegacyPermission legacyPermission = list.get(i);
                    this.mPermissionTrees.put(legacyPermission.getPermissionInfo().name, legacyPermission);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    public void readPermissions(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                readPermissions(this.mPermissions, typedXmlPullParser);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    public void readPermissionTrees(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                readPermissions(this.mPermissionTrees, typedXmlPullParser);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    public static void readPermissions(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, com.android.server.pm.permission.LegacyPermission> arrayMap, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (!com.android.server.pm.permission.LegacyPermission.read(arrayMap, typedXmlPullParser)) {
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element reading permissions: " + typedXmlPullParser.getName() + " at " + typedXmlPullParser.getPositionDescription());
                    }
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            } else {
                return;
            }
        }
    }

    public void writePermissions(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                java.util.Iterator<com.android.server.pm.permission.LegacyPermission> it = this.mPermissions.values().iterator();
                while (it.hasNext()) {
                    it.next().write(typedXmlSerializer);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    public void writePermissionTrees(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                java.util.Iterator<com.android.server.pm.permission.LegacyPermission> it = this.mPermissionTrees.values().iterator();
                while (it.hasNext()) {
                    it.next().write(typedXmlSerializer);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    public static void dumpPermissions(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet, @android.annotation.NonNull java.util.List<com.android.server.pm.permission.LegacyPermission> list, @android.annotation.NonNull java.util.Map<java.lang.String, java.util.Set<java.lang.String>> map, boolean z, @android.annotation.NonNull com.android.server.pm.DumpState dumpState) {
        int size = list.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            z2 = list.get(i).dump(printWriter, str, arraySet, z, z2, dumpState);
        }
        if (str == null && arraySet == null) {
            boolean z3 = true;
            for (java.util.Map.Entry<java.lang.String, java.util.Set<java.lang.String>> entry : map.entrySet()) {
                if (z3) {
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    printWriter.println("AppOp Permissions:");
                    z3 = false;
                }
                printWriter.print("  AppOp Permission ");
                printWriter.print(entry.getKey());
                printWriter.println(":");
                for (java.lang.String str2 : entry.getValue()) {
                    printWriter.print("    ");
                    printWriter.println(str2);
                }
            }
        }
    }
}
