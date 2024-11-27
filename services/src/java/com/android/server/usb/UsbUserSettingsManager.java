package com.android.server.usb;

/* loaded from: classes2.dex */
public class UsbUserSettingsManager {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = com.android.server.usb.UsbUserSettingsManager.class.getSimpleName();
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.content.pm.PackageManager mPackageManager;
    private final android.os.UserHandle mUser;
    private final android.content.Context mUserContext;

    UsbUserSettingsManager(android.content.Context context, android.os.UserHandle userHandle) {
        try {
            this.mUserContext = context.createPackageContextAsUser(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, 0, userHandle);
            this.mPackageManager = this.mUserContext.getPackageManager();
            this.mUser = userHandle;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.RuntimeException("Missing android package");
        }
    }

    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivities(@android.annotation.NonNull android.content.Intent intent) {
        return this.mPackageManager.queryIntentActivitiesAsUser(intent, 128, this.mUser.getIdentifier());
    }

    boolean canBeDefault(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice, java.lang.String str) {
        android.content.pm.ActivityInfo[] packageActivities = getPackageActivities(str);
        if (packageActivities != null) {
            for (android.content.pm.ActivityInfo activityInfo : packageActivities) {
                try {
                    android.content.res.XmlResourceParser loadXmlMetaData = activityInfo.loadXmlMetaData(this.mPackageManager, "android.hardware.usb.action.USB_DEVICE_ATTACHED");
                    if (loadXmlMetaData != null) {
                        try {
                            com.android.internal.util.XmlUtils.nextElement(loadXmlMetaData);
                            while (loadXmlMetaData.getEventType() != 1) {
                                if ("usb-device".equals(loadXmlMetaData.getName()) && android.hardware.usb.DeviceFilter.read(loadXmlMetaData).matches(usbDevice)) {
                                    loadXmlMetaData.close();
                                    return true;
                                }
                                com.android.internal.util.XmlUtils.nextElement(loadXmlMetaData);
                            }
                            loadXmlMetaData.close();
                        } finally {
                        }
                    } else if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(TAG, "Unable to load component info " + activityInfo.toString(), e);
                }
            }
        }
        return false;
    }

    boolean canBeDefault(@android.annotation.NonNull android.hardware.usb.UsbAccessory usbAccessory, java.lang.String str) {
        android.content.pm.ActivityInfo[] packageActivities = getPackageActivities(str);
        if (packageActivities != null) {
            for (android.content.pm.ActivityInfo activityInfo : packageActivities) {
                try {
                    android.content.res.XmlResourceParser loadXmlMetaData = activityInfo.loadXmlMetaData(this.mPackageManager, "android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
                    if (loadXmlMetaData != null) {
                        try {
                            com.android.internal.util.XmlUtils.nextElement(loadXmlMetaData);
                            while (loadXmlMetaData.getEventType() != 1) {
                                if ("usb-accessory".equals(loadXmlMetaData.getName()) && android.hardware.usb.AccessoryFilter.read(loadXmlMetaData).matches(usbAccessory)) {
                                    loadXmlMetaData.close();
                                    return true;
                                }
                                com.android.internal.util.XmlUtils.nextElement(loadXmlMetaData);
                            }
                            loadXmlMetaData.close();
                        } finally {
                        }
                    } else if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(TAG, "Unable to load component info " + activityInfo.toString(), e);
                }
            }
        }
        return false;
    }

    private android.content.pm.ActivityInfo[] getPackageActivities(java.lang.String str) {
        try {
            return this.mPackageManager.getPackageInfo(str, 129).activities;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, @android.annotation.NonNull java.lang.String str, long j) {
        java.util.List<android.content.pm.ResolveInfo> list;
        int i;
        long start = dualDumpOutputStream.start(str, j);
        synchronized (this.mLock) {
            try {
                dualDumpOutputStream.write("user_id", 1120986464257L, this.mUser.getIdentifier());
                java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = queryIntentActivities(new android.content.Intent("android.hardware.usb.action.USB_DEVICE_ATTACHED"));
                int size = queryIntentActivities.size();
                int i2 = 0;
                while (i2 < size) {
                    android.content.pm.ResolveInfo resolveInfo = queryIntentActivities.get(i2);
                    int i3 = i2;
                    long start2 = dualDumpOutputStream.start("device_attached_activities", 2246267895812L);
                    com.android.internal.util.dump.DumpUtils.writeComponentName(dualDumpOutputStream, com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY, 1146756268033L, new android.content.ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
                    java.util.ArrayList<android.hardware.usb.DeviceFilter> deviceFilters = com.android.server.usb.UsbProfileGroupSettingsManager.getDeviceFilters(this.mPackageManager, resolveInfo);
                    if (deviceFilters != null) {
                        int size2 = deviceFilters.size();
                        for (int i4 = 0; i4 < size2; i4++) {
                            deviceFilters.get(i4).dump(dualDumpOutputStream, "filters", 2246267895810L);
                        }
                    }
                    dualDumpOutputStream.end(start2);
                    i2 = i3 + 1;
                }
                java.util.List<android.content.pm.ResolveInfo> queryIntentActivities2 = queryIntentActivities(new android.content.Intent("android.hardware.usb.action.USB_ACCESSORY_ATTACHED"));
                int size3 = queryIntentActivities2.size();
                int i5 = 0;
                while (i5 < size3) {
                    android.content.pm.ResolveInfo resolveInfo2 = queryIntentActivities2.get(i5);
                    long start3 = dualDumpOutputStream.start("accessory_attached_activities", 2246267895813L);
                    com.android.internal.util.dump.DumpUtils.writeComponentName(dualDumpOutputStream, com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY, 1146756268033L, new android.content.ComponentName(resolveInfo2.activityInfo.packageName, resolveInfo2.activityInfo.name));
                    java.util.ArrayList<android.hardware.usb.AccessoryFilter> accessoryFilters = com.android.server.usb.UsbProfileGroupSettingsManager.getAccessoryFilters(this.mPackageManager, resolveInfo2);
                    if (accessoryFilters == null) {
                        list = queryIntentActivities2;
                        i = size3;
                    } else {
                        int size4 = accessoryFilters.size();
                        int i6 = 0;
                        while (i6 < size4) {
                            accessoryFilters.get(i6).dump(dualDumpOutputStream, "filters", 2246267895810L);
                            i6++;
                            queryIntentActivities2 = queryIntentActivities2;
                            size3 = size3;
                        }
                        list = queryIntentActivities2;
                        i = size3;
                    }
                    dualDumpOutputStream.end(start3);
                    i5++;
                    queryIntentActivities2 = list;
                    size3 = i;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        dualDumpOutputStream.end(start);
    }
}
