package com.android.internal.infra;

/* loaded from: classes4.dex */
public class GlobalWhitelistState {
    protected final java.lang.Object mGlobalWhitelistStateLock = new java.lang.Object();
    protected android.util.SparseArray<com.android.internal.infra.WhitelistHelper> mWhitelisterHelpers;

    public void setWhitelist(int i, java.util.List<java.lang.String> list, java.util.List<android.content.ComponentName> list2) {
        synchronized (this.mGlobalWhitelistStateLock) {
            if (this.mWhitelisterHelpers == null) {
                this.mWhitelisterHelpers = new android.util.SparseArray<>(1);
            }
            com.android.internal.infra.WhitelistHelper whitelistHelper = this.mWhitelisterHelpers.get(i);
            if (whitelistHelper == null) {
                whitelistHelper = new com.android.internal.infra.WhitelistHelper();
                this.mWhitelisterHelpers.put(i, whitelistHelper);
            }
            whitelistHelper.setWhitelist(list, list2);
        }
    }

    public boolean isWhitelisted(int i, java.lang.String str) {
        synchronized (this.mGlobalWhitelistStateLock) {
            boolean z = false;
            if (this.mWhitelisterHelpers == null) {
                return false;
            }
            com.android.internal.infra.WhitelistHelper whitelistHelper = this.mWhitelisterHelpers.get(i);
            if (whitelistHelper != null) {
                z = whitelistHelper.isWhitelisted(str);
            }
            return z;
        }
    }

    public boolean isWhitelisted(int i, android.content.ComponentName componentName) {
        synchronized (this.mGlobalWhitelistStateLock) {
            boolean z = false;
            if (this.mWhitelisterHelpers == null) {
                return false;
            }
            com.android.internal.infra.WhitelistHelper whitelistHelper = this.mWhitelisterHelpers.get(i);
            if (whitelistHelper != null) {
                z = whitelistHelper.isWhitelisted(componentName);
            }
            return z;
        }
    }

    public android.util.ArraySet<android.content.ComponentName> getWhitelistedComponents(int i, java.lang.String str) {
        synchronized (this.mGlobalWhitelistStateLock) {
            android.util.ArraySet<android.content.ComponentName> arraySet = null;
            if (this.mWhitelisterHelpers == null) {
                return null;
            }
            com.android.internal.infra.WhitelistHelper whitelistHelper = this.mWhitelisterHelpers.get(i);
            if (whitelistHelper != null) {
                arraySet = whitelistHelper.getWhitelistedComponents(str);
            }
            return arraySet;
        }
    }

    public android.util.ArraySet<java.lang.String> getWhitelistedPackages(int i) {
        synchronized (this.mGlobalWhitelistStateLock) {
            android.util.ArraySet<java.lang.String> arraySet = null;
            if (this.mWhitelisterHelpers == null) {
                return null;
            }
            com.android.internal.infra.WhitelistHelper whitelistHelper = this.mWhitelisterHelpers.get(i);
            if (whitelistHelper != null) {
                arraySet = whitelistHelper.getWhitelistedPackages();
            }
            return arraySet;
        }
    }

    public void resetWhitelist(int i) {
        synchronized (this.mGlobalWhitelistStateLock) {
            if (this.mWhitelisterHelpers == null) {
                return;
            }
            this.mWhitelisterHelpers.remove(i);
            if (this.mWhitelisterHelpers.size() == 0) {
                this.mWhitelisterHelpers = null;
            }
        }
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("State: ");
        synchronized (this.mGlobalWhitelistStateLock) {
            if (this.mWhitelisterHelpers == null) {
                printWriter.println("empty");
                return;
            }
            printWriter.print(this.mWhitelisterHelpers.size());
            printWriter.println(" services");
            java.lang.String str2 = str + "  ";
            for (int i = 0; i < this.mWhitelisterHelpers.size(); i++) {
                this.mWhitelisterHelpers.valueAt(i).dump(str2, "Whitelist for userId " + this.mWhitelisterHelpers.keyAt(i), printWriter);
            }
        }
    }
}
