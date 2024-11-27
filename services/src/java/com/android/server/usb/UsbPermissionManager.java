package com.android.server.usb;

/* loaded from: classes2.dex */
class UsbPermissionManager {
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = com.android.server.usb.UsbPermissionManager.class.getSimpleName();

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mPermissionsByUser"})
    private final android.util.SparseArray<com.android.server.usb.UsbUserPermissionManager> mPermissionsByUser = new android.util.SparseArray<>();
    final com.android.server.usb.UsbService mUsbService;

    UsbPermissionManager(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.usb.UsbService usbService) {
        this.mContext = context;
        this.mUsbService = usbService;
    }

    @android.annotation.NonNull
    com.android.server.usb.UsbUserPermissionManager getPermissionsForUser(int i) {
        com.android.server.usb.UsbUserPermissionManager usbUserPermissionManager;
        synchronized (this.mPermissionsByUser) {
            try {
                usbUserPermissionManager = this.mPermissionsByUser.get(i);
                if (usbUserPermissionManager == null) {
                    usbUserPermissionManager = new com.android.server.usb.UsbUserPermissionManager(this.mContext.createContextAsUser(android.os.UserHandle.of(i), 0), this.mUsbService.getSettingsForUser(i));
                    this.mPermissionsByUser.put(i, usbUserPermissionManager);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return usbUserPermissionManager;
    }

    @android.annotation.NonNull
    com.android.server.usb.UsbUserPermissionManager getPermissionsForUser(@android.annotation.NonNull android.os.UserHandle userHandle) {
        return getPermissionsForUser(userHandle.getIdentifier());
    }

    void remove(@android.annotation.NonNull android.os.UserHandle userHandle) {
        synchronized (this.mPermissionsByUser) {
            this.mPermissionsByUser.remove(userHandle.getIdentifier());
        }
    }

    void usbDeviceRemoved(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice) {
        synchronized (this.mPermissionsByUser) {
            for (int i = 0; i < this.mPermissionsByUser.size(); i++) {
                try {
                    this.mPermissionsByUser.valueAt(i).removeDevicePermissions(usbDevice);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        android.content.Intent intent = new android.content.Intent("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intent.addFlags(16777216);
        intent.putExtra("device", usbDevice);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
    }

    void usbAccessoryRemoved(@android.annotation.NonNull android.hardware.usb.UsbAccessory usbAccessory) {
        synchronized (this.mPermissionsByUser) {
            for (int i = 0; i < this.mPermissionsByUser.size(); i++) {
                try {
                    this.mPermissionsByUser.valueAt(i).removeAccessoryPermissions(usbAccessory);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        android.content.Intent intent = new android.content.Intent("android.hardware.usb.action.USB_ACCESSORY_DETACHED");
        intent.addFlags(16777216);
        intent.putExtra("accessory", usbAccessory);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
    }

    void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        synchronized (this.mPermissionsByUser) {
            try {
                java.util.List users = userManager.getUsers();
                int size = users.size();
                for (int i = 0; i < size; i++) {
                    getPermissionsForUser(((android.content.pm.UserInfo) users.get(i)).id).dump(dualDumpOutputStream, "user_permissions", 2246267895809L);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        dualDumpOutputStream.end(start);
    }
}
