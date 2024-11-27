package com.android.server.recoverysystem.hal;

/* loaded from: classes2.dex */
public class BootControlHIDL implements android.hardware.boot.IBootControl {
    private static final java.lang.String TAG = "BootControlHIDL";
    final android.hardware.boot.V1_1.IBootControl v1_1_hal;
    final android.hardware.boot.V1_2.IBootControl v1_2_hal;
    final android.hardware.boot.V1_0.IBootControl v1_hal;

    public static boolean isServicePresent() {
        try {
            android.hardware.boot.V1_0.IBootControl.getService(true);
            return true;
        } catch (android.os.RemoteException | java.util.NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isV1_2ServicePresent() {
        try {
            android.hardware.boot.V1_2.IBootControl.getService(true);
            return true;
        } catch (android.os.RemoteException | java.util.NoSuchElementException e) {
            return false;
        }
    }

    public static com.android.server.recoverysystem.hal.BootControlHIDL getService() throws android.os.RemoteException {
        android.hardware.boot.V1_0.IBootControl service = android.hardware.boot.V1_0.IBootControl.getService(true);
        return new com.android.server.recoverysystem.hal.BootControlHIDL(service, android.hardware.boot.V1_1.IBootControl.castFrom((android.os.IHwInterface) service), android.hardware.boot.V1_2.IBootControl.castFrom((android.os.IHwInterface) service));
    }

    private BootControlHIDL(android.hardware.boot.V1_0.IBootControl iBootControl, android.hardware.boot.V1_1.IBootControl iBootControl2, android.hardware.boot.V1_2.IBootControl iBootControl3) throws android.os.RemoteException {
        this.v1_hal = iBootControl;
        this.v1_1_hal = iBootControl2;
        this.v1_2_hal = iBootControl3;
        if (iBootControl == null) {
            throw new android.os.RemoteException("Failed to find V1.0 BootControl HIDL");
        }
        if (iBootControl3 != null) {
            android.util.Slog.i(TAG, "V1.2 version of BootControl HIDL HAL available, using V1.2");
        } else if (iBootControl2 != null) {
            android.util.Slog.i(TAG, "V1.1 version of BootControl HIDL HAL available, using V1.1");
        } else {
            android.util.Slog.i(TAG, "V1.0 version of BootControl HIDL HAL available, using V1.0");
        }
    }

    @Override // android.os.IInterface
    public android.os.IBinder asBinder() {
        return null;
    }

    @Override // android.hardware.boot.IBootControl
    public int getActiveBootSlot() throws android.os.RemoteException {
        if (this.v1_2_hal == null) {
            throw new android.os.RemoteException("getActiveBootSlot() requires V1.2 BootControl HAL");
        }
        return this.v1_2_hal.getActiveBootSlot();
    }

    @Override // android.hardware.boot.IBootControl
    public int getCurrentSlot() throws android.os.RemoteException {
        return this.v1_hal.getCurrentSlot();
    }

    @Override // android.hardware.boot.IBootControl
    public int getNumberSlots() throws android.os.RemoteException {
        return this.v1_hal.getNumberSlots();
    }

    @Override // android.hardware.boot.IBootControl
    public int getSnapshotMergeStatus() throws android.os.RemoteException {
        if (this.v1_1_hal == null) {
            throw new android.os.RemoteException("getSnapshotMergeStatus() requires V1.1 BootControl HAL");
        }
        return this.v1_1_hal.getSnapshotMergeStatus();
    }

    @Override // android.hardware.boot.IBootControl
    public java.lang.String getSuffix(int i) throws android.os.RemoteException {
        return this.v1_hal.getSuffix(i);
    }

    @Override // android.hardware.boot.IBootControl
    public boolean isSlotBootable(int i) throws android.os.RemoteException {
        int isSlotBootable = this.v1_hal.isSlotBootable(i);
        if (isSlotBootable != -1) {
            return isSlotBootable != 0;
        }
        throw new android.os.RemoteException("isSlotBootable() failed, Slot %d might be invalid.".formatted(java.lang.Integer.valueOf(i)));
    }

    @Override // android.hardware.boot.IBootControl
    public boolean isSlotMarkedSuccessful(int i) throws android.os.RemoteException {
        int isSlotMarkedSuccessful = this.v1_hal.isSlotMarkedSuccessful(i);
        if (isSlotMarkedSuccessful != -1) {
            return isSlotMarkedSuccessful != 0;
        }
        throw new android.os.RemoteException("isSlotMarkedSuccessful() failed, Slot %d might be invalid.".formatted(java.lang.Integer.valueOf(i)));
    }

    @Override // android.hardware.boot.IBootControl
    public void markBootSuccessful() throws android.os.RemoteException {
        android.hardware.boot.V1_0.CommandResult markBootSuccessful = this.v1_hal.markBootSuccessful();
        if (!markBootSuccessful.success) {
            throw new android.os.RemoteException("Error markBootSuccessful() " + markBootSuccessful.errMsg);
        }
    }

    @Override // android.hardware.boot.IBootControl
    public void setActiveBootSlot(int i) throws android.os.RemoteException {
        android.hardware.boot.V1_0.CommandResult activeBootSlot = this.v1_hal.setActiveBootSlot(i);
        if (!activeBootSlot.success) {
            throw new android.os.RemoteException("Error setActiveBootSlot(%d) %s".formatted(java.lang.Integer.valueOf(i), activeBootSlot.errMsg));
        }
    }

    @Override // android.hardware.boot.IBootControl
    public void setSlotAsUnbootable(int i) throws android.os.RemoteException {
        android.hardware.boot.V1_0.CommandResult slotAsUnbootable = this.v1_hal.setSlotAsUnbootable(i);
        if (!slotAsUnbootable.success) {
            throw new android.os.RemoteException("Error setSlotAsUnbootable(%d) %s".formatted(java.lang.Integer.valueOf(i), slotAsUnbootable.errMsg));
        }
    }

    @Override // android.hardware.boot.IBootControl
    public void setSnapshotMergeStatus(int i) throws android.os.RemoteException {
        if (this.v1_1_hal == null) {
            throw new android.os.RemoteException("getSnapshotMergeStatus() requires V1.1 BootControl HAL");
        }
        if (!this.v1_1_hal.setSnapshotMergeStatus(i)) {
            throw new android.os.RemoteException("Error setSnapshotMergeStatus(%d)".formatted(java.lang.Integer.valueOf(i)));
        }
    }

    @Override // android.hardware.boot.IBootControl
    public int getInterfaceVersion() throws android.os.RemoteException {
        return 1;
    }

    @Override // android.hardware.boot.IBootControl
    public java.lang.String getInterfaceHash() throws android.os.RemoteException {
        return this.v1_hal.interfaceDescriptor();
    }
}
