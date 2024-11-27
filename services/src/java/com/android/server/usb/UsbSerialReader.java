package com.android.server.usb;

/* loaded from: classes2.dex */
class UsbSerialReader extends android.hardware.usb.IUsbSerialReader.Stub {

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private java.lang.Object mDevice;

    @android.annotation.NonNull
    private final com.android.server.usb.UsbPermissionManager mPermissionManager;

    @android.annotation.Nullable
    private final java.lang.String mSerialNumber;

    UsbSerialReader(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.usb.UsbPermissionManager usbPermissionManager, @android.annotation.Nullable java.lang.String str) {
        this.mContext = context;
        this.mPermissionManager = usbPermissionManager;
        this.mSerialNumber = str;
    }

    public void setDevice(@android.annotation.NonNull java.lang.Object obj) {
        this.mDevice = obj;
    }

    public java.lang.String getSerial(java.lang.String str) throws android.os.RemoteException {
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 1000) {
            enforcePackageBelongsToUid(callingUid, str);
            android.os.UserHandle callingUserHandle = android.os.Binder.getCallingUserHandle();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    if (this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, callingUserHandle.getIdentifier()).applicationInfo.targetSdkVersion >= 29 && this.mContext.checkPermission("android.permission.MANAGE_USB", callingPid, callingUid) == -1) {
                        int userId = android.os.UserHandle.getUserId(callingUid);
                        if (this.mDevice instanceof android.hardware.usb.UsbDevice) {
                            this.mPermissionManager.getPermissionsForUser(userId).checkPermission((android.hardware.usb.UsbDevice) this.mDevice, str, callingPid, callingUid);
                        } else {
                            this.mPermissionManager.getPermissionsForUser(userId).checkPermission((android.hardware.usb.UsbAccessory) this.mDevice, callingPid, callingUid);
                        }
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    throw new android.os.RemoteException("package " + str + " cannot be found");
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return this.mSerialNumber;
    }

    private void enforcePackageBelongsToUid(int i, @android.annotation.NonNull java.lang.String str) {
        if (!com.android.internal.util.ArrayUtils.contains(this.mContext.getPackageManager().getPackagesForUid(i), str)) {
            throw new java.lang.IllegalArgumentException(str + " does to belong to the " + i);
        }
    }
}
