package com.android.internal.infra;

/* loaded from: classes4.dex */
public final class WhitelistHelper {
    private static final java.lang.String TAG = "WhitelistHelper";
    private android.util.ArrayMap<java.lang.String, android.util.ArraySet<android.content.ComponentName>> mWhitelistedPackages;

    public void setWhitelist(android.util.ArraySet<java.lang.String> arraySet, android.util.ArraySet<android.content.ComponentName> arraySet2) {
        this.mWhitelistedPackages = null;
        if (arraySet == null && arraySet2 == null) {
            return;
        }
        if ((arraySet != null && arraySet.isEmpty()) || (arraySet2 != null && arraySet2.isEmpty())) {
            throw new java.lang.IllegalArgumentException("Packages or Components cannot be empty.");
        }
        this.mWhitelistedPackages = new android.util.ArrayMap<>();
        if (arraySet != null) {
            for (int i = 0; i < arraySet.size(); i++) {
                this.mWhitelistedPackages.put(arraySet.valueAt(i), null);
            }
        }
        if (arraySet2 != null) {
            for (int i2 = 0; i2 < arraySet2.size(); i2++) {
                android.content.ComponentName valueAt = arraySet2.valueAt(i2);
                if (valueAt == null) {
                    android.util.Log.w(TAG, "setWhitelist(): component is null");
                } else {
                    java.lang.String packageName = valueAt.getPackageName();
                    android.util.ArraySet<android.content.ComponentName> arraySet3 = this.mWhitelistedPackages.get(packageName);
                    if (arraySet3 == null) {
                        arraySet3 = new android.util.ArraySet<>();
                        this.mWhitelistedPackages.put(packageName, arraySet3);
                    }
                    arraySet3.add(valueAt);
                }
            }
        }
    }

    public void setWhitelist(java.util.List<java.lang.String> list, java.util.List<android.content.ComponentName> list2) {
        android.util.ArraySet<java.lang.String> arraySet;
        android.util.ArraySet<android.content.ComponentName> arraySet2 = null;
        if (list == null) {
            arraySet = null;
        } else {
            arraySet = new android.util.ArraySet<>(list);
        }
        if (list2 != null) {
            arraySet2 = new android.util.ArraySet<>(list2);
        }
        setWhitelist(arraySet, arraySet2);
    }

    public boolean isWhitelisted(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        return this.mWhitelistedPackages != null && this.mWhitelistedPackages.containsKey(str) && this.mWhitelistedPackages.get(str) == null;
    }

    public boolean isWhitelisted(android.content.ComponentName componentName) {
        java.util.Objects.requireNonNull(componentName);
        java.lang.String packageName = componentName.getPackageName();
        android.util.ArraySet<android.content.ComponentName> whitelistedComponents = getWhitelistedComponents(packageName);
        if (whitelistedComponents != null) {
            return whitelistedComponents.contains(componentName);
        }
        return isWhitelisted(packageName);
    }

    public android.util.ArraySet<android.content.ComponentName> getWhitelistedComponents(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (this.mWhitelistedPackages == null) {
            return null;
        }
        return this.mWhitelistedPackages.get(str);
    }

    public android.util.ArraySet<java.lang.String> getWhitelistedPackages() {
        if (this.mWhitelistedPackages == null) {
            return null;
        }
        return new android.util.ArraySet<>(this.mWhitelistedPackages.keySet());
    }

    public java.lang.String toString() {
        return "WhitelistHelper[" + this.mWhitelistedPackages + ']';
    }

    public void dump(java.lang.String str, java.lang.String str2, java.io.PrintWriter printWriter) {
        if (this.mWhitelistedPackages == null || this.mWhitelistedPackages.size() == 0) {
            printWriter.print(str);
            printWriter.print(str2);
            printWriter.println(": (no whitelisted packages)");
            return;
        }
        java.lang.String str3 = str + "  ";
        int size = this.mWhitelistedPackages.size();
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print(": ");
        printWriter.print(size);
        printWriter.println(" packages");
        for (int i = 0; i < this.mWhitelistedPackages.size(); i++) {
            java.lang.String keyAt = this.mWhitelistedPackages.keyAt(i);
            android.util.ArraySet<android.content.ComponentName> valueAt = this.mWhitelistedPackages.valueAt(i);
            printWriter.print(str3);
            printWriter.print(i);
            printWriter.print(android.media.MediaMetrics.SEPARATOR);
            printWriter.print(keyAt);
            printWriter.print(": ");
            if (valueAt == null) {
                printWriter.println("(whole package)");
            } else {
                printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
                printWriter.print(valueAt.valueAt(0));
                for (int i2 = 1; i2 < valueAt.size(); i2++) {
                    printWriter.print(", ");
                    printWriter.print(valueAt.valueAt(i2));
                }
                printWriter.println(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
    }
}
