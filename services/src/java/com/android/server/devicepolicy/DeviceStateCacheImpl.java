package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public class DeviceStateCacheImpl extends android.app.admin.DeviceStateCache {
    public static final int NO_DEVICE_OWNER = -1;
    private final java.lang.Object mLock = new java.lang.Object();
    private java.util.concurrent.atomic.AtomicInteger mDeviceOwnerType = new java.util.concurrent.atomic.AtomicInteger(-1);
    private java.util.Map<java.lang.Integer, java.lang.Boolean> mHasProfileOwner = new java.util.concurrent.ConcurrentHashMap();
    private java.util.Map<java.lang.Integer, java.lang.Boolean> mAffiliationWithDevice = new java.util.concurrent.ConcurrentHashMap();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsDeviceProvisioned = false;

    public boolean isDeviceProvisioned() {
        return this.mIsDeviceProvisioned;
    }

    public void setDeviceProvisioned(boolean z) {
        synchronized (this.mLock) {
            this.mIsDeviceProvisioned = z;
        }
    }

    void setDeviceOwnerType(int i) {
        this.mDeviceOwnerType.set(i);
    }

    void setHasProfileOwner(int i, boolean z) {
        if (z) {
            this.mHasProfileOwner.put(java.lang.Integer.valueOf(i), true);
        } else {
            this.mHasProfileOwner.remove(java.lang.Integer.valueOf(i));
        }
    }

    void setHasAffiliationWithDevice(int i, java.lang.Boolean bool) {
        if (bool.booleanValue()) {
            this.mAffiliationWithDevice.put(java.lang.Integer.valueOf(i), true);
        } else {
            this.mAffiliationWithDevice.remove(java.lang.Integer.valueOf(i));
        }
    }

    public boolean hasAffiliationWithDevice(int i) {
        return this.mAffiliationWithDevice.getOrDefault(java.lang.Integer.valueOf(i), false).booleanValue();
    }

    public boolean isUserOrganizationManaged(int i) {
        return this.mHasProfileOwner.getOrDefault(java.lang.Integer.valueOf(i), false).booleanValue() || hasEnterpriseDeviceOwner();
    }

    private boolean hasEnterpriseDeviceOwner() {
        return this.mDeviceOwnerType.get() == 0;
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Device state cache:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Device provisioned: " + this.mIsDeviceProvisioned);
        indentingPrintWriter.println("Device Owner Type: " + this.mDeviceOwnerType.get());
        indentingPrintWriter.println("Has PO:");
        for (java.lang.Integer num : this.mHasProfileOwner.keySet()) {
            indentingPrintWriter.println("User " + num + ": " + this.mHasProfileOwner.get(num));
        }
        indentingPrintWriter.decreaseIndent();
    }
}
