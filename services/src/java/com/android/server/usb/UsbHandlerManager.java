package com.android.server.usb;

/* loaded from: classes2.dex */
public class UsbHandlerManager {
    private static final java.lang.String LOG_TAG = com.android.server.usb.UsbHandlerManager.class.getSimpleName();
    private final android.content.Context mContext;

    UsbHandlerManager(@android.annotation.NonNull android.content.Context context) {
        this.mContext = context;
    }

    void showUsbAccessoryUriActivity(@android.annotation.NonNull android.hardware.usb.UsbAccessory usbAccessory, @android.annotation.NonNull android.os.UserHandle userHandle) {
        java.lang.String uri = usbAccessory.getUri();
        if (uri != null && uri.length() > 0) {
            android.content.Intent createDialogIntent = createDialogIntent();
            createDialogIntent.setComponent(android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(android.R.string.config_sharedConnectivityServiceIntentAction)));
            createDialogIntent.putExtra("accessory", usbAccessory);
            createDialogIntent.putExtra("uri", uri);
            try {
                this.mContext.startActivityAsUser(createDialogIntent, userHandle);
            } catch (android.content.ActivityNotFoundException e) {
                android.util.Slog.e(LOG_TAG, "unable to start UsbAccessoryUriActivity");
            }
        }
    }

    void confirmUsbHandler(@android.annotation.NonNull android.content.pm.ResolveInfo resolveInfo, @android.annotation.Nullable android.hardware.usb.UsbDevice usbDevice, @android.annotation.Nullable android.hardware.usb.UsbAccessory usbAccessory) {
        android.content.Intent createDialogIntent = createDialogIntent();
        createDialogIntent.setComponent(android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(android.R.string.config_sharedConnectivityServicePackage)));
        createDialogIntent.putExtra("rinfo", resolveInfo);
        android.os.UserHandle userHandleForUid = android.os.UserHandle.getUserHandleForUid(resolveInfo.activityInfo.applicationInfo.uid);
        if (usbDevice != null) {
            createDialogIntent.putExtra("device", usbDevice);
        } else {
            createDialogIntent.putExtra("accessory", usbAccessory);
        }
        try {
            this.mContext.startActivityAsUser(createDialogIntent, userHandleForUid);
        } catch (android.content.ActivityNotFoundException e) {
            android.util.Slog.e(LOG_TAG, "unable to start activity " + createDialogIntent, e);
        }
    }

    void selectUsbHandler(@android.annotation.NonNull java.util.ArrayList<android.content.pm.ResolveInfo> arrayList, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull android.content.Intent intent) {
        android.content.Intent createDialogIntent = createDialogIntent();
        createDialogIntent.setComponent(android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(android.R.string.config_somnambulatorComponent)));
        createDialogIntent.putParcelableArrayListExtra("rlist", arrayList);
        createDialogIntent.putExtra("android.intent.extra.INTENT", intent);
        try {
            this.mContext.startActivityAsUser(createDialogIntent, userHandle);
        } catch (android.content.ActivityNotFoundException e) {
            android.util.Slog.e(LOG_TAG, "unable to start activity " + createDialogIntent, e);
        }
    }

    private android.content.Intent createDialogIntent() {
        android.content.Intent intent = new android.content.Intent();
        intent.addFlags(268435456);
        return intent;
    }
}
