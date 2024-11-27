package com.android.server.usb;

/* loaded from: classes2.dex */
public class UsbSettingsManager {
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = com.android.server.usb.UsbSettingsManager.class.getSimpleName();

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private com.android.server.usb.UsbHandlerManager mUsbHandlerManager;
    final com.android.server.usb.UsbService mUsbService;
    private android.os.UserManager mUserManager;

    @com.android.internal.annotations.GuardedBy({"mSettingsByUser"})
    private final android.util.SparseArray<com.android.server.usb.UsbUserSettingsManager> mSettingsByUser = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mSettingsByProfileGroup"})
    private final android.util.SparseArray<com.android.server.usb.UsbProfileGroupSettingsManager> mSettingsByProfileGroup = new android.util.SparseArray<>();

    UsbSettingsManager(@android.annotation.NonNull android.content.Context context, com.android.server.usb.UsbService usbService) {
        this.mContext = context;
        this.mUsbService = usbService;
        this.mUserManager = (android.os.UserManager) context.getSystemService("user");
        this.mUsbHandlerManager = new com.android.server.usb.UsbHandlerManager(context);
    }

    @android.annotation.NonNull
    public com.android.server.usb.UsbUserSettingsManager getSettingsForUser(int i) {
        com.android.server.usb.UsbUserSettingsManager usbUserSettingsManager;
        synchronized (this.mSettingsByUser) {
            try {
                usbUserSettingsManager = this.mSettingsByUser.get(i);
                if (usbUserSettingsManager == null) {
                    usbUserSettingsManager = new com.android.server.usb.UsbUserSettingsManager(this.mContext, android.os.UserHandle.of(i));
                    this.mSettingsByUser.put(i, usbUserSettingsManager);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return usbUserSettingsManager;
    }

    @android.annotation.NonNull
    com.android.server.usb.UsbProfileGroupSettingsManager getSettingsForProfileGroup(@android.annotation.NonNull android.os.UserHandle userHandle) {
        com.android.server.usb.UsbProfileGroupSettingsManager usbProfileGroupSettingsManager;
        android.content.pm.UserInfo profileParent = this.mUserManager.getProfileParent(userHandle.getIdentifier());
        if (profileParent != null) {
            userHandle = profileParent.getUserHandle();
        }
        synchronized (this.mSettingsByProfileGroup) {
            try {
                usbProfileGroupSettingsManager = this.mSettingsByProfileGroup.get(userHandle.getIdentifier());
                if (usbProfileGroupSettingsManager == null) {
                    usbProfileGroupSettingsManager = new com.android.server.usb.UsbProfileGroupSettingsManager(this.mContext, userHandle, this, this.mUsbHandlerManager);
                    this.mSettingsByProfileGroup.put(userHandle.getIdentifier(), usbProfileGroupSettingsManager);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return usbProfileGroupSettingsManager;
    }

    void remove(@android.annotation.NonNull android.os.UserHandle userHandle) {
        synchronized (this.mSettingsByUser) {
            this.mSettingsByUser.remove(userHandle.getIdentifier());
        }
        synchronized (this.mSettingsByProfileGroup) {
            try {
                if (this.mSettingsByProfileGroup.indexOfKey(userHandle.getIdentifier()) >= 0) {
                    this.mSettingsByProfileGroup.get(userHandle.getIdentifier()).unregisterReceivers();
                    this.mSettingsByProfileGroup.remove(userHandle.getIdentifier());
                } else {
                    int size = this.mSettingsByProfileGroup.size();
                    for (int i = 0; i < size; i++) {
                        this.mSettingsByProfileGroup.valueAt(i).removeUser(userHandle);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        int i;
        long start = dualDumpOutputStream.start(str, j);
        synchronized (this.mSettingsByUser) {
            try {
                java.util.List users = this.mUserManager.getUsers();
                int size = users.size();
                for (int i2 = 0; i2 < size; i2++) {
                    getSettingsForUser(((android.content.pm.UserInfo) users.get(i2)).id).dump(dualDumpOutputStream, "user_settings", 2246267895809L);
                }
            } finally {
            }
        }
        synchronized (this.mSettingsByProfileGroup) {
            try {
                int size2 = this.mSettingsByProfileGroup.size();
                for (i = 0; i < size2; i++) {
                    this.mSettingsByProfileGroup.valueAt(i).dump(dualDumpOutputStream, "profile_group_settings", 2246267895810L);
                }
            } finally {
            }
        }
        dualDumpOutputStream.end(start);
    }
}
