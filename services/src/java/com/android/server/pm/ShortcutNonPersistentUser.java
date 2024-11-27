package com.android.server.pm;

/* loaded from: classes2.dex */
public class ShortcutNonPersistentUser {
    private final int mUserId;
    private final android.util.ArrayMap<java.lang.String, java.lang.String> mHostPackages = new android.util.ArrayMap<>();
    private final android.util.ArraySet<java.lang.String> mHostPackageSet = new android.util.ArraySet<>();

    public ShortcutNonPersistentUser(int i) {
        this.mUserId = i;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public void setShortcutHostPackage(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        if (str2 != null) {
            this.mHostPackages.put(str, str2);
        } else {
            this.mHostPackages.remove(str);
        }
        this.mHostPackageSet.clear();
        for (int i = 0; i < this.mHostPackages.size(); i++) {
            this.mHostPackageSet.add(this.mHostPackages.valueAt(i));
        }
    }

    public boolean hasHostPackage(@android.annotation.NonNull java.lang.String str) {
        return this.mHostPackageSet.contains(str);
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, com.android.server.pm.ShortcutService.DumpFilter dumpFilter) {
        if (dumpFilter.shouldDumpDetails() && this.mHostPackages.size() > 0) {
            printWriter.print(str);
            printWriter.print("Non-persistent: user ID:");
            printWriter.println(this.mUserId);
            printWriter.print(str);
            printWriter.println("  Host packages:");
            for (int i = 0; i < this.mHostPackages.size(); i++) {
                printWriter.print(str);
                printWriter.print("    ");
                printWriter.print(this.mHostPackages.keyAt(i));
                printWriter.print(": ");
                printWriter.println(this.mHostPackages.valueAt(i));
            }
            printWriter.println();
        }
    }
}
