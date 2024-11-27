package com.android.server.usb;

/* loaded from: classes2.dex */
class MtpNotificationManager {
    private static final java.lang.String ACTION_OPEN_IN_APPS = "com.android.server.usb.ACTION_OPEN_IN_APPS";
    private static final int PROTOCOL_MTP = 0;
    private static final int PROTOCOL_PTP = 1;
    private static final int SUBCLASS_MTP = 255;
    private static final int SUBCLASS_STILL_IMAGE_CAPTURE = 1;
    private static final java.lang.String TAG = "UsbMtpNotificationManager";
    private final android.content.Context mContext;
    private final com.android.server.usb.MtpNotificationManager.OnOpenInAppListener mListener;
    private final com.android.server.usb.MtpNotificationManager.Receiver mReceiver = new com.android.server.usb.MtpNotificationManager.Receiver();

    interface OnOpenInAppListener {
        void onOpenInApp(android.hardware.usb.UsbDevice usbDevice);
    }

    MtpNotificationManager(android.content.Context context, com.android.server.usb.MtpNotificationManager.OnOpenInAppListener onOpenInAppListener) {
        this.mContext = context;
        this.mListener = onOpenInAppListener;
        context.registerReceiver(this.mReceiver, new android.content.IntentFilter(ACTION_OPEN_IN_APPS));
    }

    void showNotification(android.hardware.usb.UsbDevice usbDevice) {
        android.content.res.Resources resources = this.mContext.getResources();
        android.app.Notification.Builder category = new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.USB).setContentTitle(resources.getString(android.R.string.time_picker_text_input_mode_description, usbDevice.getProductName())).setContentText(resources.getString(android.R.string.time_picker_radial_mode_description)).setSmallIcon(android.R.drawable.stat_sys_battery_charge_anim85).setCategory("sys");
        android.content.Intent intent = new android.content.Intent(ACTION_OPEN_IN_APPS);
        intent.putExtra("device", usbDevice);
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
        category.setContentIntent(android.app.PendingIntent.getBroadcastAsUser(this.mContext, usbDevice.getDeviceId(), intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, android.os.UserHandle.SYSTEM));
        android.app.Notification build = category.build();
        build.flags |= 256;
        ((android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class)).notify(java.lang.Integer.toString(usbDevice.getDeviceId()), 25, build);
    }

    void hideNotification(int i) {
        ((android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class)).cancel(java.lang.Integer.toString(i), 25);
    }

    private class Receiver extends android.content.BroadcastReceiver {
        private Receiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            android.hardware.usb.UsbDevice usbDevice = (android.hardware.usb.UsbDevice) intent.getExtras().getParcelable("device", android.hardware.usb.UsbDevice.class);
            if (usbDevice == null) {
            }
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case 768361239:
                    if (action.equals(com.android.server.usb.MtpNotificationManager.ACTION_OPEN_IN_APPS)) {
                        c = 0;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    com.android.server.usb.MtpNotificationManager.this.mListener.onOpenInApp(usbDevice);
                    break;
            }
        }
    }

    static boolean shouldShowNotification(android.content.pm.PackageManager packageManager, android.hardware.usb.UsbDevice usbDevice) {
        return !packageManager.hasSystemFeature("android.hardware.type.automotive") && isMtpDevice(usbDevice);
    }

    private static boolean isMtpDevice(android.hardware.usb.UsbDevice usbDevice) {
        for (int i = 0; i < usbDevice.getInterfaceCount(); i++) {
            android.hardware.usb.UsbInterface usbInterface = usbDevice.getInterface(i);
            if (usbInterface.getInterfaceClass() == 6 && usbInterface.getInterfaceSubclass() == 1 && usbInterface.getInterfaceProtocol() == 1) {
                return true;
            }
            if (usbInterface.getInterfaceClass() == 255 && usbInterface.getInterfaceSubclass() == 255 && usbInterface.getInterfaceProtocol() == 0 && "MTP".equals(usbInterface.getName())) {
                return true;
            }
        }
        return false;
    }

    public void unregister() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }
}
