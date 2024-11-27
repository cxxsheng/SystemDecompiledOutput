package com.android.server.usb;

/* loaded from: classes2.dex */
public class UsbPortManager implements android.os.IBinder.DeathRecipient {
    private static final int MSG_SYSTEM_READY = 2;
    private static final int MSG_UPDATE_PORTS = 1;
    private static final java.lang.String PORT_INFO = "port_info";
    private static final java.lang.String TAG = "UsbPortManager";
    private final android.content.Context mContext;
    private int mIsPortContaminatedNotificationId;
    private android.app.NotificationManager mNotificationManager;
    private boolean mSystemReady;
    private long mTransactionId;
    private static final int COMBO_SOURCE_HOST = android.hardware.usb.UsbPort.combineRolesAsBit(1, 1);
    private static final int COMBO_SOURCE_DEVICE = android.hardware.usb.UsbPort.combineRolesAsBit(1, 2);
    private static final int COMBO_SINK_HOST = android.hardware.usb.UsbPort.combineRolesAsBit(2, 1);
    private static final int COMBO_SINK_DEVICE = android.hardware.usb.UsbPort.combineRolesAsBit(2, 2);
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<java.lang.String, com.android.server.usb.UsbPortManager.PortInfo> mPorts = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, com.android.server.usb.hal.port.RawPortInfo> mSimulatedPorts = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, java.lang.Boolean> mConnected = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mContaminantStatus = new android.util.ArrayMap<>();
    private final java.lang.Object mDisplayPortListenerLock = new java.lang.Object();
    private final android.util.ArrayMap<android.os.IBinder, android.hardware.usb.IDisplayPortAltModeInfoListener> mDisplayPortListeners = new android.util.ArrayMap<>();
    private final android.os.Handler mHandler = new android.os.Handler(com.android.server.FgThread.get().getLooper()) { // from class: com.android.server.usb.UsbPortManager.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    java.util.ArrayList parcelableArrayList = message.getData().getParcelableArrayList(com.android.server.usb.UsbPortManager.PORT_INFO, com.android.server.usb.hal.port.RawPortInfo.class);
                    synchronized (com.android.server.usb.UsbPortManager.this.mLock) {
                        com.android.server.usb.UsbPortManager.this.updatePortsLocked(null, parcelableArrayList);
                    }
                    return;
                case 2:
                    com.android.server.usb.UsbPortManager.this.mNotificationManager = (android.app.NotificationManager) com.android.server.usb.UsbPortManager.this.mContext.getSystemService("notification");
                    return;
                default:
                    return;
            }
        }
    };
    private com.android.server.usb.hal.port.UsbPortHal mUsbPortHal = com.android.server.usb.hal.port.UsbPortHalInstance.getInstance(this, null);

    public UsbPortManager(android.content.Context context) {
        this.mContext = context;
        logAndPrint(3, null, "getInstance done");
    }

    public void systemReady() {
        this.mSystemReady = true;
        if (this.mUsbPortHal != null) {
            this.mUsbPortHal.systemReady();
            try {
                com.android.server.usb.hal.port.UsbPortHal usbPortHal = this.mUsbPortHal;
                long j = this.mTransactionId + 1;
                this.mTransactionId = j;
                usbPortHal.queryPortStatus(j);
            } catch (java.lang.Exception e) {
                logAndPrintException(null, "ServiceStart: Failed to query port status", e);
            }
        }
        this.mHandler.sendEmptyMessage(2);
    }

    private void updateContaminantNotification() {
        com.android.server.usb.UsbPortManager.PortInfo portInfo;
        android.content.res.Resources resources = this.mContext.getResources();
        int i = 2;
        for (com.android.server.usb.UsbPortManager.PortInfo portInfo2 : this.mPorts.values()) {
            int contaminantDetectionStatus = portInfo2.mUsbPortStatus.getContaminantDetectionStatus();
            if (contaminantDetectionStatus == 3 || contaminantDetectionStatus == 1) {
                portInfo = portInfo2;
                i = contaminantDetectionStatus;
                break;
            }
            i = contaminantDetectionStatus;
        }
        portInfo = null;
        if (i != 3 || this.mIsPortContaminatedNotificationId == 52) {
            if (i != 3 && this.mIsPortContaminatedNotificationId == 52) {
                this.mNotificationManager.cancelAsUser(null, this.mIsPortContaminatedNotificationId, android.os.UserHandle.ALL);
                this.mIsPortContaminatedNotificationId = 0;
                if (i == 2) {
                    this.mIsPortContaminatedNotificationId = 53;
                    java.lang.CharSequence text = resources.getText(android.R.string.time_picker_increment_minute_button);
                    java.lang.String str = com.android.internal.notification.SystemNotificationChannels.ALERTS;
                    java.lang.CharSequence text2 = resources.getText(android.R.string.time_picker_increment_hour_button);
                    this.mNotificationManager.notifyAsUser(null, this.mIsPortContaminatedNotificationId, new android.app.Notification.Builder(this.mContext, str).setSmallIcon(android.R.drawable.ic_thermostat).setTicker(text).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).setContentTitle(text).setContentText(text2).setVisibility(1).setStyle(new android.app.Notification.BigTextStyle().bigText(text2)).build(), android.os.UserHandle.ALL);
                    return;
                }
                return;
            }
            return;
        }
        if (this.mIsPortContaminatedNotificationId == 53) {
            this.mNotificationManager.cancelAsUser(null, this.mIsPortContaminatedNotificationId, android.os.UserHandle.ALL);
        }
        this.mIsPortContaminatedNotificationId = 52;
        java.lang.CharSequence text3 = resources.getText(android.R.string.time_picker_hour_label);
        java.lang.String str2 = com.android.internal.notification.SystemNotificationChannels.ALERTS;
        java.lang.CharSequence text4 = resources.getText(android.R.string.time_picker_header_text);
        android.content.Intent intent = new android.content.Intent();
        intent.addFlags(268435456);
        intent.setComponent(android.content.ComponentName.unflattenFromString(resources.getString(android.R.string.config_signalAttributionPath)));
        intent.putExtra("port", (android.os.Parcelable) android.hardware.usb.ParcelableUsbPort.of(portInfo.mUsbPort));
        intent.putExtra("portStatus", (android.os.Parcelable) portInfo.mUsbPortStatus);
        this.mNotificationManager.notifyAsUser(null, this.mIsPortContaminatedNotificationId, new android.app.Notification.Builder(this.mContext, str2).setOngoing(true).setTicker(text3).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).setContentIntent(android.app.PendingIntent.getActivityAsUser(this.mContext, 0, intent, 67108864, null, android.os.UserHandle.CURRENT)).setContentTitle(text3).setContentText(text4).setVisibility(1).setSmallIcon(android.R.drawable.stat_sys_warning).setStyle(new android.app.Notification.BigTextStyle().bigText(text4)).build(), android.os.UserHandle.ALL);
    }

    public android.hardware.usb.UsbPort[] getPorts() {
        android.hardware.usb.UsbPort[] usbPortArr;
        synchronized (this.mLock) {
            try {
                int size = this.mPorts.size();
                usbPortArr = new android.hardware.usb.UsbPort[size];
                for (int i = 0; i < size; i++) {
                    usbPortArr[i] = this.mPorts.valueAt(i).mUsbPort;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return usbPortArr;
    }

    public android.hardware.usb.UsbPortStatus getPortStatus(java.lang.String str) {
        android.hardware.usb.UsbPortStatus usbPortStatus;
        synchronized (this.mLock) {
            try {
                com.android.server.usb.UsbPortManager.PortInfo portInfo = this.mPorts.get(str);
                usbPortStatus = portInfo != null ? portInfo.mUsbPortStatus : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return usbPortStatus;
    }

    public boolean isModeChangeSupported(java.lang.String str) {
        boolean z;
        synchronized (this.mLock) {
            try {
                com.android.server.usb.UsbPortManager.PortInfo portInfo = this.mPorts.get(str);
                z = portInfo != null ? portInfo.mCanChangeMode : false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public void enableContaminantDetection(@android.annotation.NonNull java.lang.String str, boolean z, @android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        com.android.server.usb.UsbPortManager.PortInfo portInfo = this.mPorts.get(str);
        if (portInfo == null) {
            if (indentingPrintWriter != null) {
                indentingPrintWriter.println("No such USB port: " + str);
                return;
            }
            return;
        }
        if (!portInfo.mUsbPort.supportsEnableContaminantPresenceDetection()) {
            return;
        }
        if (!z || portInfo.mUsbPortStatus.getContaminantDetectionStatus() == 1) {
            if ((!z && portInfo.mUsbPortStatus.getContaminantDetectionStatus() == 1) || portInfo.mUsbPortStatus.getContaminantDetectionStatus() == 0) {
                return;
            }
            try {
                com.android.server.usb.hal.port.UsbPortHal usbPortHal = this.mUsbPortHal;
                long j = this.mTransactionId + 1;
                this.mTransactionId = j;
                usbPortHal.enableContaminantPresenceDetection(str, z, j);
            } catch (java.lang.Exception e) {
                logAndPrintException(indentingPrintWriter, "Failed to set contaminant detection", e);
            }
        }
    }

    public void enableLimitPowerTransfer(@android.annotation.NonNull java.lang.String str, boolean z, long j, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        java.util.Objects.requireNonNull(str);
        if (this.mPorts.get(str) == null) {
            logAndPrint(6, indentingPrintWriter, "enableLimitPowerTransfer: No such port: " + str + " opId:" + j);
            if (iUsbOperationInternal != null) {
                try {
                    iUsbOperationInternal.onOperationComplete(3);
                    return;
                } catch (android.os.RemoteException e) {
                    logAndPrintException(indentingPrintWriter, "enableLimitPowerTransfer: Failed to call OperationComplete. opId:" + j, e);
                    return;
                }
            }
            return;
        }
        try {
            this.mUsbPortHal.enableLimitPowerTransfer(str, z, j, iUsbOperationInternal);
        } catch (java.lang.Exception e2) {
            try {
                logAndPrintException(indentingPrintWriter, "enableLimitPowerTransfer: Failed to limit power transfer. opId:" + j, e2);
                if (iUsbOperationInternal != null) {
                    iUsbOperationInternal.onOperationComplete(1);
                }
            } catch (android.os.RemoteException e3) {
                logAndPrintException(indentingPrintWriter, "enableLimitPowerTransfer:Failed to call onOperationComplete. opId:" + j, e3);
            }
        }
    }

    public void enableUsbDataWhileDocked(@android.annotation.NonNull java.lang.String str, long j, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        java.util.Objects.requireNonNull(str);
        if (this.mPorts.get(str) == null) {
            logAndPrint(6, indentingPrintWriter, "enableUsbDataWhileDocked: No such port: " + str + " opId:" + j);
            if (iUsbOperationInternal != null) {
                try {
                    iUsbOperationInternal.onOperationComplete(3);
                    return;
                } catch (android.os.RemoteException e) {
                    logAndPrintException(indentingPrintWriter, "enableUsbDataWhileDocked: Failed to call OperationComplete. opId:" + j, e);
                    return;
                }
            }
            return;
        }
        try {
            this.mUsbPortHal.enableUsbDataWhileDocked(str, j, iUsbOperationInternal);
        } catch (java.lang.Exception e2) {
            try {
                logAndPrintException(indentingPrintWriter, "enableUsbDataWhileDocked: Failed to limit power transfer. opId:" + j, e2);
                if (iUsbOperationInternal != null) {
                    iUsbOperationInternal.onOperationComplete(1);
                }
            } catch (android.os.RemoteException e3) {
                logAndPrintException(indentingPrintWriter, "enableUsbDataWhileDocked:Failed to call onOperationComplete. opId:" + j, e3);
            }
        }
    }

    public boolean enableUsbData(@android.annotation.NonNull java.lang.String str, boolean z, int i, @android.annotation.NonNull android.hardware.usb.IUsbOperationInternal iUsbOperationInternal, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        java.util.Objects.requireNonNull(iUsbOperationInternal);
        java.util.Objects.requireNonNull(str);
        if (this.mPorts.get(str) == null) {
            logAndPrint(6, indentingPrintWriter, "enableUsbData: No such port: " + str + " opId:" + i);
            try {
                iUsbOperationInternal.onOperationComplete(3);
            } catch (android.os.RemoteException e) {
                logAndPrintException(indentingPrintWriter, "enableUsbData: Failed to call OperationComplete. opId:" + i, e);
            }
            return false;
        }
        try {
            return this.mUsbPortHal.enableUsbData(str, z, i, iUsbOperationInternal);
        } catch (java.lang.Exception e2) {
            try {
                logAndPrintException(indentingPrintWriter, "enableUsbData: Failed to invoke enableUsbData. opId:" + i, e2);
                iUsbOperationInternal.onOperationComplete(1);
            } catch (android.os.RemoteException e3) {
                logAndPrintException(indentingPrintWriter, "enableUsbData: Failed to call onOperationComplete. opId:" + i, e3);
            }
            return false;
        }
    }

    public int getUsbHalVersion() {
        if (this.mUsbPortHal == null) {
            return -2;
        }
        try {
            return this.mUsbPortHal.getUsbHalVersion();
        } catch (android.os.RemoteException e) {
            return -2;
        }
    }

    private int toHalUsbDataRole(int i) {
        if (i == 2) {
            return 2;
        }
        return 1;
    }

    private int toHalUsbPowerRole(int i) {
        if (i == 2) {
            return 2;
        }
        return 1;
    }

    private int toHalUsbMode(int i) {
        if (i == 1) {
            return 1;
        }
        return 2;
    }

    public void resetUsbPort(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.hardware.usb.IUsbOperationInternal iUsbOperationInternal, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            java.util.Objects.requireNonNull(iUsbOperationInternal);
            java.util.Objects.requireNonNull(str);
            if (this.mPorts.get(str) == null) {
                logAndPrint(6, indentingPrintWriter, "resetUsbPort: No such port: " + str + " opId:" + i);
                try {
                    iUsbOperationInternal.onOperationComplete(3);
                } catch (android.os.RemoteException e) {
                    logAndPrintException(indentingPrintWriter, "resetUsbPort: Failed to call OperationComplete. opId:" + i, e);
                }
            }
            try {
                this.mUsbPortHal.resetUsbPort(str, i, iUsbOperationInternal);
            } catch (java.lang.Exception e2) {
                try {
                    logAndPrintException(indentingPrintWriter, "reseetUsbPort: Failed to resetUsbPort. opId:" + i, e2);
                    iUsbOperationInternal.onOperationComplete(1);
                } catch (android.os.RemoteException e3) {
                    logAndPrintException(indentingPrintWriter, "resetUsbPort: Failed to call onOperationComplete. opId:" + i, e3);
                }
            }
        }
    }

    public void setPortRoles(java.lang.String str, int i, int i2, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        com.android.server.usb.UsbPortManager.PortInfo portInfo;
        int i3;
        synchronized (this.mLock) {
            try {
                portInfo = this.mPorts.get(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
            if (portInfo == null) {
                if (indentingPrintWriter != null) {
                    indentingPrintWriter.println("No such USB port: " + str);
                }
                return;
            }
            if (!portInfo.mUsbPortStatus.isRoleCombinationSupported(i, i2)) {
                logAndPrint(6, indentingPrintWriter, "Attempted to set USB port into unsupported role combination: portId=" + str + ", newPowerRole=" + android.hardware.usb.UsbPort.powerRoleToString(i) + ", newDataRole=" + android.hardware.usb.UsbPort.dataRoleToString(i2));
                return;
            }
            int currentDataRole = portInfo.mUsbPortStatus.getCurrentDataRole();
            int currentPowerRole = portInfo.mUsbPortStatus.getCurrentPowerRole();
            if (currentDataRole == i2 && currentPowerRole == i) {
                if (indentingPrintWriter != null) {
                    indentingPrintWriter.println("No change.");
                }
                return;
            }
            boolean z = portInfo.mCanChangeMode;
            boolean z2 = portInfo.mCanChangePowerRole;
            boolean z3 = portInfo.mCanChangeDataRole;
            int currentMode = portInfo.mUsbPortStatus.getCurrentMode();
            if ((!z2 && currentPowerRole != i) || (!z3 && currentDataRole != i2)) {
                i3 = 2;
                if (!z || i != 1 || i2 != 1) {
                    if (z && i == 2 && i2 == 2) {
                        i3 = 1;
                    } else {
                        logAndPrint(6, indentingPrintWriter, "Found mismatch in supported USB role combinations while attempting to change role: " + portInfo + ", newPowerRole=" + android.hardware.usb.UsbPort.powerRoleToString(i) + ", newDataRole=" + android.hardware.usb.UsbPort.dataRoleToString(i2));
                        return;
                    }
                }
            } else {
                i3 = currentMode;
            }
            logAndPrint(4, indentingPrintWriter, "Setting USB port mode and role: portId=" + str + ", currentMode=" + android.hardware.usb.UsbPort.modeToString(currentMode) + ", currentPowerRole=" + android.hardware.usb.UsbPort.powerRoleToString(currentPowerRole) + ", currentDataRole=" + android.hardware.usb.UsbPort.dataRoleToString(currentDataRole) + ", newMode=" + android.hardware.usb.UsbPort.modeToString(i3) + ", newPowerRole=" + android.hardware.usb.UsbPort.powerRoleToString(i) + ", newDataRole=" + android.hardware.usb.UsbPort.dataRoleToString(i2));
            com.android.server.usb.hal.port.RawPortInfo rawPortInfo = this.mSimulatedPorts.get(str);
            if (rawPortInfo != null) {
                rawPortInfo.currentMode = i3;
                rawPortInfo.currentPowerRole = i;
                rawPortInfo.currentDataRole = i2;
                updatePortsLocked(indentingPrintWriter, null);
            } else if (this.mUsbPortHal != null) {
                if (currentMode != i3) {
                    logAndPrint(6, indentingPrintWriter, "Trying to set the USB port mode: portId=" + str + ", newMode=" + android.hardware.usb.UsbPort.modeToString(i3));
                    try {
                        com.android.server.usb.hal.port.UsbPortHal usbPortHal = this.mUsbPortHal;
                        int halUsbMode = toHalUsbMode(i3);
                        long j = this.mTransactionId + 1;
                        this.mTransactionId = j;
                        usbPortHal.switchMode(str, halUsbMode, j);
                    } catch (java.lang.Exception e) {
                        logAndPrintException(indentingPrintWriter, "Failed to set the USB port mode: portId=" + str + ", newMode=" + android.hardware.usb.UsbPort.modeToString(i3), e);
                    }
                } else {
                    if (currentPowerRole != i) {
                        try {
                            com.android.server.usb.hal.port.UsbPortHal usbPortHal2 = this.mUsbPortHal;
                            int halUsbPowerRole = toHalUsbPowerRole(i);
                            long j2 = this.mTransactionId + 1;
                            this.mTransactionId = j2;
                            usbPortHal2.switchPowerRole(str, halUsbPowerRole, j2);
                        } catch (java.lang.Exception e2) {
                            logAndPrintException(indentingPrintWriter, "Failed to set the USB port power role: portId=" + str + ", newPowerRole=" + android.hardware.usb.UsbPort.powerRoleToString(i), e2);
                            return;
                        }
                    }
                    if (currentDataRole != i2) {
                        try {
                            com.android.server.usb.hal.port.UsbPortHal usbPortHal3 = this.mUsbPortHal;
                            int halUsbDataRole = toHalUsbDataRole(i2);
                            long j3 = this.mTransactionId + 1;
                            this.mTransactionId = j3;
                            usbPortHal3.switchDataRole(str, halUsbDataRole, j3);
                        } catch (java.lang.Exception e3) {
                            logAndPrintException(indentingPrintWriter, "Failed to set the USB port data role: portId=" + str + ", newDataRole=" + android.hardware.usb.UsbPort.dataRoleToString(i2), e3);
                        }
                    }
                }
                throw th;
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.util.Slog.wtf(TAG, "binderDied() called unexpectedly");
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied(android.os.IBinder iBinder) {
        synchronized (this.mDisplayPortListenerLock) {
            this.mDisplayPortListeners.remove(iBinder);
            android.util.Slog.d(TAG, "DisplayPortEventDispatcherListener died at " + iBinder);
        }
    }

    public boolean registerForDisplayPortEvents(@android.annotation.NonNull android.hardware.usb.IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) {
        synchronized (this.mDisplayPortListenerLock) {
            try {
                if (this.mDisplayPortListeners.containsKey(iDisplayPortAltModeInfoListener.asBinder())) {
                    return false;
                }
                try {
                    iDisplayPortAltModeInfoListener.asBinder().linkToDeath(this, 0);
                    this.mDisplayPortListeners.put(iDisplayPortAltModeInfoListener.asBinder(), iDisplayPortAltModeInfoListener);
                    return true;
                } catch (android.os.RemoteException e) {
                    logAndPrintException(null, "Caught RemoteException in registerForDisplayPortEvents: ", e);
                    return false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterForDisplayPortEvents(@android.annotation.NonNull android.hardware.usb.IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) {
        synchronized (this.mDisplayPortListenerLock) {
            try {
                if (this.mDisplayPortListeners.remove(iDisplayPortAltModeInfoListener.asBinder()) != null) {
                    iDisplayPortAltModeInfoListener.asBinder().unlinkToDeath(this, 0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void updatePorts(java.util.ArrayList<com.android.server.usb.hal.port.RawPortInfo> arrayList) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelableArrayList(PORT_INFO, arrayList);
        obtainMessage.what = 1;
        obtainMessage.setData(bundle);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void addSimulatedPort(java.lang.String str, int i, boolean z, boolean z2, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo;
        int i2 = z2 ? 1 : 0;
        if (!z2) {
            displayPortAltModeInfo = null;
        } else {
            displayPortAltModeInfo = new android.hardware.usb.DisplayPortAltModeInfo();
        }
        synchronized (this.mLock) {
            try {
                try {
                    if (this.mSimulatedPorts.containsKey(str)) {
                        indentingPrintWriter.println("Port with same name already exists.  Please remove it first.");
                        return;
                    }
                    indentingPrintWriter.println("Adding simulated port: portId=" + str + ", supportedModes=" + android.hardware.usb.UsbPort.modeToString(i));
                    this.mSimulatedPorts.put(str, new com.android.server.usb.hal.port.RawPortInfo(str, i, 0, 0, false, 0, false, 0, false, false, 0, false, 0, 0, false, 0, z, new int[0], 0, i2, displayPortAltModeInfo));
                    updatePortsLocked(indentingPrintWriter, null);
                } catch (java.lang.Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public void connectSimulatedPort(java.lang.String str, int i, boolean z, int i2, boolean z2, int i3, boolean z3, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                com.android.server.usb.hal.port.RawPortInfo rawPortInfo = this.mSimulatedPorts.get(str);
                if (rawPortInfo == null) {
                    indentingPrintWriter.println("Cannot connect simulated port which does not exist.");
                    return;
                }
                if (i == 0 || i2 == 0 || i3 == 0) {
                    indentingPrintWriter.println("Cannot connect simulated port in null mode, power role, or data role.");
                    return;
                }
                if ((rawPortInfo.supportedModes & i) == 0) {
                    indentingPrintWriter.println("Simulated port does not support mode: " + android.hardware.usb.UsbPort.modeToString(i));
                    return;
                }
                indentingPrintWriter.println("Connecting simulated port: portId=" + str + ", mode=" + android.hardware.usb.UsbPort.modeToString(i) + ", canChangeMode=" + z + ", powerRole=" + android.hardware.usb.UsbPort.powerRoleToString(i2) + ", canChangePowerRole=" + z2 + ", dataRole=" + android.hardware.usb.UsbPort.dataRoleToString(i3) + ", canChangeDataRole=" + z3);
                rawPortInfo.currentMode = i;
                rawPortInfo.canChangeMode = z;
                rawPortInfo.currentPowerRole = i2;
                rawPortInfo.canChangePowerRole = z2;
                rawPortInfo.currentDataRole = i3;
                rawPortInfo.canChangeDataRole = z3;
                updatePortsLocked(indentingPrintWriter, null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void simulateContaminantStatus(java.lang.String str, boolean z, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        int i;
        synchronized (this.mLock) {
            try {
                com.android.server.usb.hal.port.RawPortInfo rawPortInfo = this.mSimulatedPorts.get(str);
                if (rawPortInfo == null) {
                    indentingPrintWriter.println("Simulated port not found.");
                    return;
                }
                indentingPrintWriter.println("Simulating wet port: portId=" + str + ", wet=" + z);
                if (z) {
                    i = 3;
                } else {
                    i = 2;
                }
                rawPortInfo.contaminantDetectionStatus = i;
                updatePortsLocked(indentingPrintWriter, null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void simulateComplianceWarnings(java.lang.String str, java.lang.String str2, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                com.android.server.usb.hal.port.RawPortInfo rawPortInfo = this.mSimulatedPorts.get(str);
                if (rawPortInfo == null) {
                    indentingPrintWriter.println("Simulated port not found");
                    return;
                }
                android.util.IntArray intArray = new android.util.IntArray();
                for (java.lang.String str3 : str2.split("[, ]")) {
                    if (str3.length() > 0) {
                        intArray.add(java.lang.Integer.parseInt(str3));
                    }
                }
                indentingPrintWriter.println("Simulating Compliance Warnings: portId=" + str + " Warnings=" + str2);
                rawPortInfo.complianceWarnings = intArray.toArray();
                updatePortsLocked(indentingPrintWriter, null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void simulateDisplayPortAltModeInfo(java.lang.String str, int i, int i2, int i3, boolean z, int i4, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                com.android.server.usb.hal.port.RawPortInfo rawPortInfo = this.mSimulatedPorts.get(str);
                if (rawPortInfo == null) {
                    indentingPrintWriter.println("Simulated port not found");
                    return;
                }
                android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo = new android.hardware.usb.DisplayPortAltModeInfo(i, i2, i3, z, i4);
                rawPortInfo.displayPortAltModeInfo = displayPortAltModeInfo;
                indentingPrintWriter.println("Simulating DisplayPort Info: " + displayPortAltModeInfo);
                updatePortsLocked(indentingPrintWriter, null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void disconnectSimulatedPort(java.lang.String str, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                com.android.server.usb.hal.port.RawPortInfo rawPortInfo = this.mSimulatedPorts.get(str);
                if (rawPortInfo == null) {
                    indentingPrintWriter.println("Cannot disconnect simulated port which does not exist.");
                    return;
                }
                indentingPrintWriter.println("Disconnecting simulated port: portId=" + str);
                rawPortInfo.currentMode = 0;
                rawPortInfo.canChangeMode = false;
                rawPortInfo.currentPowerRole = 0;
                rawPortInfo.canChangePowerRole = false;
                rawPortInfo.currentDataRole = 0;
                rawPortInfo.canChangeDataRole = false;
                updatePortsLocked(indentingPrintWriter, null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeSimulatedPort(java.lang.String str, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mSimulatedPorts.indexOfKey(str);
                if (indexOfKey < 0) {
                    indentingPrintWriter.println("Cannot remove simulated port which does not exist.");
                    return;
                }
                indentingPrintWriter.println("Disconnecting simulated port: portId=" + str);
                this.mSimulatedPorts.removeAt(indexOfKey);
                updatePortsLocked(indentingPrintWriter, null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void resetSimulation(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Removing all simulated ports and ending simulation.");
                if (!this.mSimulatedPorts.isEmpty()) {
                    this.mSimulatedPorts.clear();
                    updatePortsLocked(indentingPrintWriter, null);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        synchronized (this.mLock) {
            try {
                dualDumpOutputStream.write("is_simulation_active", 1133871366145L, !this.mSimulatedPorts.isEmpty());
                java.util.Iterator<com.android.server.usb.UsbPortManager.PortInfo> it = this.mPorts.values().iterator();
                while (it.hasNext()) {
                    it.next().dump(dualDumpOutputStream, "usb_ports", 2246267895810L);
                }
                dualDumpOutputStream.write("usb_hal_version", 1159641169924L, getUsbHalVersion());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        dualDumpOutputStream.end(start);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePortsLocked(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.util.ArrayList<com.android.server.usb.hal.port.RawPortInfo> arrayList) {
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter2;
        com.android.server.usb.UsbPortManager usbPortManager = this;
        int size = usbPortManager.mPorts.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                break;
            }
            usbPortManager.mPorts.valueAt(i).mDisposition = 3;
            size = i;
        }
        if (!usbPortManager.mSimulatedPorts.isEmpty()) {
            int i2 = 0;
            for (int size2 = usbPortManager.mSimulatedPorts.size(); i2 < size2; size2 = size2) {
                com.android.server.usb.hal.port.RawPortInfo valueAt = usbPortManager.mSimulatedPorts.valueAt(i2);
                usbPortManager = this;
                usbPortManager.addOrUpdatePortLocked(valueAt.portId, valueAt.supportedModes, valueAt.supportedContaminantProtectionModes, valueAt.currentMode, valueAt.canChangeMode, valueAt.currentPowerRole, valueAt.canChangePowerRole, valueAt.currentDataRole, valueAt.canChangeDataRole, valueAt.supportsEnableContaminantPresenceProtection, valueAt.contaminantProtectionStatus, valueAt.supportsEnableContaminantPresenceDetection, valueAt.contaminantDetectionStatus, valueAt.usbDataStatus, valueAt.powerTransferLimited, valueAt.powerBrickConnectionStatus, valueAt.supportsComplianceWarnings, valueAt.complianceWarnings, valueAt.plugState, valueAt.supportedAltModes, valueAt.displayPortAltModeInfo, indentingPrintWriter);
                i2++;
            }
        } else {
            java.util.Iterator<com.android.server.usb.hal.port.RawPortInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                com.android.server.usb.hal.port.RawPortInfo next = it.next();
                addOrUpdatePortLocked(next.portId, next.supportedModes, next.supportedContaminantProtectionModes, next.currentMode, next.canChangeMode, next.currentPowerRole, next.canChangePowerRole, next.currentDataRole, next.canChangeDataRole, next.supportsEnableContaminantPresenceProtection, next.contaminantProtectionStatus, next.supportsEnableContaminantPresenceDetection, next.contaminantDetectionStatus, next.usbDataStatus, next.powerTransferLimited, next.powerBrickConnectionStatus, next.supportsComplianceWarnings, next.complianceWarnings, next.plugState, next.supportedAltModes, next.displayPortAltModeInfo, indentingPrintWriter);
            }
        }
        int size3 = this.mPorts.size();
        while (true) {
            int i3 = size3 - 1;
            if (size3 > 0) {
                com.android.server.usb.UsbPortManager.PortInfo valueAt2 = this.mPorts.valueAt(i3);
                switch (valueAt2.mDisposition) {
                    case 0:
                        indentingPrintWriter2 = indentingPrintWriter;
                        handlePortAddedLocked(valueAt2, indentingPrintWriter2);
                        valueAt2.mDisposition = 2;
                        break;
                    case 1:
                        indentingPrintWriter2 = indentingPrintWriter;
                        handlePortChangedLocked(valueAt2, indentingPrintWriter2);
                        valueAt2.mDisposition = 2;
                        break;
                    case 2:
                    default:
                        indentingPrintWriter2 = indentingPrintWriter;
                        break;
                    case 3:
                        this.mPorts.removeAt(i3);
                        valueAt2.mUsbPortStatus = null;
                        indentingPrintWriter2 = indentingPrintWriter;
                        handlePortRemovedLocked(valueAt2, indentingPrintWriter2);
                        break;
                }
                if (valueAt2.mComplianceWarningChange == 1) {
                    handlePortComplianceWarningLocked(valueAt2, indentingPrintWriter2);
                }
                if (valueAt2.mDisplayPortAltModeChange == 1) {
                    handleDpAltModeLocked(valueAt2, indentingPrintWriter2);
                }
                size3 = i3;
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void addOrUpdatePortLocked(java.lang.String str, int i, int i2, int i3, boolean z, int i4, boolean z2, int i5, boolean z3, boolean z4, int i6, boolean z5, int i7, int i8, boolean z6, int i9, boolean z7, @android.annotation.NonNull int[] iArr, int i10, int i11, android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        boolean z8;
        int i12;
        int i13;
        com.android.server.usb.UsbPortManager.PortInfo portInfo;
        if ((i & 3) == 3) {
            z8 = z;
            i12 = i3;
        } else if (i3 != 0 && i3 != i) {
            logAndPrint(5, indentingPrintWriter, "Ignoring inconsistent current mode from USB port driver: supportedModes=" + android.hardware.usb.UsbPort.modeToString(i) + ", currentMode=" + android.hardware.usb.UsbPort.modeToString(i3));
            i12 = 0;
            z8 = false;
        } else {
            i12 = i3;
            z8 = false;
        }
        int combineRolesAsBit = android.hardware.usb.UsbPort.combineRolesAsBit(i4, i5);
        if (i12 != 0 && i4 != 0 && i5 != 0) {
            if (z2 && z3) {
                i13 = combineRolesAsBit | COMBO_SOURCE_HOST | COMBO_SOURCE_DEVICE | COMBO_SINK_HOST | COMBO_SINK_DEVICE;
            } else if (z2) {
                i13 = combineRolesAsBit | android.hardware.usb.UsbPort.combineRolesAsBit(1, i5) | android.hardware.usb.UsbPort.combineRolesAsBit(2, i5);
            } else if (z3) {
                i13 = combineRolesAsBit | android.hardware.usb.UsbPort.combineRolesAsBit(i4, 1) | android.hardware.usb.UsbPort.combineRolesAsBit(i4, 2);
            } else if (z8) {
                i13 = combineRolesAsBit | COMBO_SOURCE_HOST | COMBO_SINK_DEVICE;
            }
            portInfo = this.mPorts.get(str);
            if (portInfo != null) {
                com.android.server.usb.UsbPortManager.PortInfo portInfo2 = new com.android.server.usb.UsbPortManager.PortInfo((android.hardware.usb.UsbManager) this.mContext.getSystemService(android.hardware.usb.UsbManager.class), str, i, i2, z4, z5, z7, i11);
                portInfo2.setStatus(i12, z8, i4, z2, i5, z3, i13, i6, i7, i8, z6, i9, iArr, i10, displayPortAltModeInfo);
                this.mPorts.put(str, portInfo2);
                return;
            }
            if (i != portInfo.mUsbPort.getSupportedModes()) {
                logAndPrint(5, indentingPrintWriter, "Ignoring inconsistent list of supported modes from USB port driver (should be immutable): previous=" + android.hardware.usb.UsbPort.modeToString(portInfo.mUsbPort.getSupportedModes()) + ", current=" + android.hardware.usb.UsbPort.modeToString(i));
            }
            if (z4 != portInfo.mUsbPort.supportsEnableContaminantPresenceProtection()) {
                logAndPrint(5, indentingPrintWriter, "Ignoring inconsistent supportsEnableContaminantPresenceProtectionUSB port driver (should be immutable): previous=" + portInfo.mUsbPort.supportsEnableContaminantPresenceProtection() + ", current=" + z4);
            }
            if (z5 != portInfo.mUsbPort.supportsEnableContaminantPresenceDetection()) {
                logAndPrint(5, indentingPrintWriter, "Ignoring inconsistent supportsEnableContaminantPresenceDetection USB port driver (should be immutable): previous=" + portInfo.mUsbPort.supportsEnableContaminantPresenceDetection() + ", current=" + z5);
            }
            if (portInfo.setStatus(i12, z8, i4, z2, i5, z3, i13, i6, i7, i8, z6, i9, iArr, i10, displayPortAltModeInfo)) {
                portInfo.mDisposition = 1;
                return;
            } else {
                portInfo.mDisposition = 2;
                return;
            }
        }
        i13 = combineRolesAsBit;
        portInfo = this.mPorts.get(str);
        if (portInfo != null) {
        }
    }

    private void handlePortLocked(com.android.server.usb.UsbPortManager.PortInfo portInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        sendPortChangedBroadcastLocked(portInfo);
        logToStatsd(portInfo, indentingPrintWriter);
        updateContaminantNotification();
    }

    private void handlePortAddedLocked(com.android.server.usb.UsbPortManager.PortInfo portInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        logAndPrint(4, indentingPrintWriter, "USB port added: " + portInfo);
        handlePortLocked(portInfo, indentingPrintWriter);
    }

    private void handlePortChangedLocked(com.android.server.usb.UsbPortManager.PortInfo portInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        logAndPrint(4, indentingPrintWriter, "USB port changed: " + portInfo);
        enableContaminantDetectionIfNeeded(portInfo, indentingPrintWriter);
        disableLimitPowerTransferIfNeeded(portInfo, indentingPrintWriter);
        handlePortLocked(portInfo, indentingPrintWriter);
    }

    private void handlePortComplianceWarningLocked(com.android.server.usb.UsbPortManager.PortInfo portInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        logAndPrint(4, indentingPrintWriter, "USB port compliance warning changed: " + portInfo);
        logToStatsdComplianceWarnings(portInfo);
        sendComplianceWarningBroadcastLocked(portInfo);
    }

    private void handleDpAltModeLocked(com.android.server.usb.UsbPortManager.PortInfo portInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        logAndPrint(4, indentingPrintWriter, "USB port DisplayPort Alt Mode Status Changed: " + portInfo);
        sendDpAltModeCallbackLocked(portInfo, indentingPrintWriter);
    }

    private void handlePortRemovedLocked(com.android.server.usb.UsbPortManager.PortInfo portInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        logAndPrint(4, indentingPrintWriter, "USB port removed: " + portInfo);
        handlePortLocked(portInfo, indentingPrintWriter);
    }

    private static int convertContaminantDetectionStatusToProto(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                return 0;
        }
    }

    private static int[] toStatsLogConstant(@android.annotation.NonNull int[] iArr) {
        android.util.IntArray intArray = new android.util.IntArray();
        for (int i : iArr) {
            switch (i) {
                case 1:
                    intArray.add(4);
                    break;
                case 2:
                    intArray.add(1);
                    break;
                case 3:
                    intArray.add(2);
                    break;
                case 4:
                    intArray.add(3);
                    break;
                case 5:
                    intArray.add(5);
                    break;
                case 6:
                    intArray.add(6);
                    break;
                case 7:
                    intArray.add(7);
                    break;
                case 8:
                    intArray.add(8);
                    break;
                case 9:
                    intArray.add(9);
                    break;
            }
        }
        return intArray.toArray();
    }

    private void sendPortChangedBroadcastLocked(com.android.server.usb.UsbPortManager.PortInfo portInfo) {
        final android.content.Intent intent = new android.content.Intent("android.hardware.usb.action.USB_PORT_CHANGED");
        intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.EVRCB);
        intent.putExtra("port", (android.os.Parcelable) android.hardware.usb.ParcelableUsbPort.of(portInfo.mUsbPort));
        intent.putExtra("portStatus", (android.os.Parcelable) portInfo.mUsbPortStatus);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.usb.UsbPortManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.usb.UsbPortManager.this.lambda$sendPortChangedBroadcastLocked$0(intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPortChangedBroadcastLocked$0(android.content.Intent intent) {
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL, "android.permission.MANAGE_USB");
    }

    private void sendComplianceWarningBroadcastLocked(com.android.server.usb.UsbPortManager.PortInfo portInfo) {
        if (portInfo.mComplianceWarningChange == 0) {
            return;
        }
        final android.content.Intent intent = new android.content.Intent("android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED");
        intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.EVRCB);
        intent.putExtra("port", (android.os.Parcelable) android.hardware.usb.ParcelableUsbPort.of(portInfo.mUsbPort));
        intent.putExtra("portStatus", (android.os.Parcelable) portInfo.mUsbPortStatus);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.usb.UsbPortManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.usb.UsbPortManager.this.lambda$sendComplianceWarningBroadcastLocked$1(intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendComplianceWarningBroadcastLocked$1(android.content.Intent intent) {
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL, "android.permission.MANAGE_USB");
    }

    private void sendDpAltModeCallbackLocked(com.android.server.usb.UsbPortManager.PortInfo portInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        java.lang.String id = portInfo.mUsbPort.getId();
        synchronized (this.mDisplayPortListenerLock) {
            java.util.Iterator<android.hardware.usb.IDisplayPortAltModeInfoListener> it = this.mDisplayPortListeners.values().iterator();
            while (it.hasNext()) {
                try {
                    it.next().onDisplayPortAltModeInfoChanged(id, portInfo.mUsbPortStatus.getDisplayPortAltModeInfo());
                } catch (android.os.RemoteException e) {
                    logAndPrintException(indentingPrintWriter, "Caught RemoteException at sendDpAltModeCallbackLocked", e);
                }
            }
        }
    }

    private void enableContaminantDetectionIfNeeded(com.android.server.usb.UsbPortManager.PortInfo portInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        if (this.mConnected.containsKey(portInfo.mUsbPort.getId()) && this.mConnected.get(portInfo.mUsbPort.getId()).booleanValue() && !portInfo.mUsbPortStatus.isConnected() && portInfo.mUsbPortStatus.getContaminantDetectionStatus() == 1) {
            enableContaminantDetection(portInfo.mUsbPort.getId(), true, indentingPrintWriter);
        }
    }

    private void disableLimitPowerTransferIfNeeded(com.android.server.usb.UsbPortManager.PortInfo portInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        if (this.mConnected.containsKey(portInfo.mUsbPort.getId()) && this.mConnected.get(portInfo.mUsbPort.getId()).booleanValue() && !portInfo.mUsbPortStatus.isConnected() && portInfo.mUsbPortStatus.isPowerTransferLimited()) {
            java.lang.String id = portInfo.mUsbPort.getId();
            long j = this.mTransactionId + 1;
            this.mTransactionId = j;
            enableLimitPowerTransfer(id, false, j, null, indentingPrintWriter);
        }
    }

    private void logToStatsd(com.android.server.usb.UsbPortManager.PortInfo portInfo, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        if (portInfo.mUsbPortStatus == null) {
            if (this.mConnected.containsKey(portInfo.mUsbPort.getId())) {
                if (this.mConnected.get(portInfo.mUsbPort.getId()).booleanValue()) {
                    com.android.internal.util.FrameworkStatsLog.write(70, 0, portInfo.mUsbPort.getId(), portInfo.mLastConnectDurationMillis);
                }
                this.mConnected.remove(portInfo.mUsbPort.getId());
            }
            if (this.mContaminantStatus.containsKey(portInfo.mUsbPort.getId())) {
                if (this.mContaminantStatus.get(portInfo.mUsbPort.getId()).intValue() == 3) {
                    com.android.internal.util.FrameworkStatsLog.write(146, portInfo.mUsbPort.getId(), convertContaminantDetectionStatusToProto(2));
                }
                this.mContaminantStatus.remove(portInfo.mUsbPort.getId());
                return;
            }
            return;
        }
        if (!this.mConnected.containsKey(portInfo.mUsbPort.getId()) || this.mConnected.get(portInfo.mUsbPort.getId()).booleanValue() != portInfo.mUsbPortStatus.isConnected()) {
            this.mConnected.put(portInfo.mUsbPort.getId(), java.lang.Boolean.valueOf(portInfo.mUsbPortStatus.isConnected()));
            com.android.internal.util.FrameworkStatsLog.write(70, portInfo.mUsbPortStatus.isConnected() ? 1 : 0, portInfo.mUsbPort.getId(), portInfo.mLastConnectDurationMillis);
        }
        if (!this.mContaminantStatus.containsKey(portInfo.mUsbPort.getId()) || this.mContaminantStatus.get(portInfo.mUsbPort.getId()).intValue() != portInfo.mUsbPortStatus.getContaminantDetectionStatus()) {
            this.mContaminantStatus.put(portInfo.mUsbPort.getId(), java.lang.Integer.valueOf(portInfo.mUsbPortStatus.getContaminantDetectionStatus()));
            com.android.internal.util.FrameworkStatsLog.write(146, portInfo.mUsbPort.getId(), convertContaminantDetectionStatusToProto(portInfo.mUsbPortStatus.getContaminantDetectionStatus()));
        }
    }

    private void logToStatsdComplianceWarnings(com.android.server.usb.UsbPortManager.PortInfo portInfo) {
        if (portInfo.mUsbPortStatus == null || portInfo.mUsbPortStatus.getComplianceWarnings().length == 0) {
            return;
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.USB_COMPLIANCE_WARNINGS_REPORTED, portInfo.mUsbPort.getId(), toStatsLogConstant(portInfo.mUsbPortStatus.getComplianceWarnings()));
    }

    public static void logAndPrint(int i, com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str) {
        android.util.Slog.println(i, TAG, str);
        if (indentingPrintWriter != null) {
            indentingPrintWriter.println(str);
        }
    }

    public static void logAndPrintException(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.lang.Exception exc) {
        android.util.Slog.e(TAG, str, exc);
        if (indentingPrintWriter != null) {
            indentingPrintWriter.println(str + exc);
        }
    }

    public static final class PortInfo {
        public static final int ALTMODE_INFO_CHANGED = 1;
        public static final int ALTMODE_INFO_UNCHANGED = 0;
        public static final int COMPLIANCE_WARNING_CHANGED = 1;
        public static final int COMPLIANCE_WARNING_UNCHANGED = 0;
        public static final int DISPOSITION_ADDED = 0;
        public static final int DISPOSITION_CHANGED = 1;
        public static final int DISPOSITION_READY = 2;
        public static final int DISPOSITION_REMOVED = 3;
        public boolean mCanChangeDataRole;
        public boolean mCanChangeMode;
        public boolean mCanChangePowerRole;
        public long mConnectedAtMillis;
        public int mDisposition;
        public long mLastConnectDurationMillis;
        public final android.hardware.usb.UsbPort mUsbPort;
        public android.hardware.usb.UsbPortStatus mUsbPortStatus;
        public int mComplianceWarningChange = 0;
        public int mDisplayPortAltModeChange = 0;

        PortInfo(@android.annotation.NonNull android.hardware.usb.UsbManager usbManager, @android.annotation.NonNull java.lang.String str, int i, int i2, boolean z, boolean z2, boolean z3, int i3) {
            this.mUsbPort = new android.hardware.usb.UsbPort(usbManager, str, i, i2, z, z2, z3, i3);
        }

        public boolean complianceWarningsChanged(@android.annotation.NonNull int[] iArr) {
            if (java.util.Arrays.equals(iArr, this.mUsbPortStatus.getComplianceWarnings())) {
                this.mComplianceWarningChange = 0;
                return false;
            }
            this.mComplianceWarningChange = 1;
            return true;
        }

        public boolean displayPortAltModeChanged(android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo) {
            android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo2 = this.mUsbPortStatus.getDisplayPortAltModeInfo();
            this.mDisplayPortAltModeChange = 0;
            if (displayPortAltModeInfo == null && displayPortAltModeInfo2 != null) {
                this.mDisplayPortAltModeChange = 1;
                return true;
            }
            if (displayPortAltModeInfo2 == null) {
                if (displayPortAltModeInfo == null) {
                    return false;
                }
                this.mDisplayPortAltModeChange = 1;
                return true;
            }
            if (displayPortAltModeInfo2.equals(displayPortAltModeInfo)) {
                return false;
            }
            this.mDisplayPortAltModeChange = 1;
            return true;
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0039, code lost:
        
            if (r16.mUsbPortStatus.getSupportedRoleCombinations() == r23) goto L18;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean setStatus(int i, boolean z, int i2, boolean z2, int i3, boolean z3, int i4) {
            this.mCanChangeMode = z;
            this.mCanChangePowerRole = z2;
            this.mCanChangeDataRole = z3;
            boolean z4 = false;
            if (this.mUsbPortStatus != null) {
                if (this.mUsbPortStatus.getCurrentMode() == i) {
                    if (this.mUsbPortStatus.getCurrentPowerRole() == i2) {
                        if (this.mUsbPortStatus.getCurrentDataRole() != i3) {
                        }
                    }
                }
            }
            this.mUsbPortStatus = new android.hardware.usb.UsbPortStatus(i, i2, i3, i4, 0, 0, 0, false, 0, new int[0], 0, (android.hardware.usb.DisplayPortAltModeInfo) null);
            z4 = true;
            if (this.mUsbPortStatus.isConnected() && this.mConnectedAtMillis == 0) {
                this.mConnectedAtMillis = android.os.SystemClock.elapsedRealtime();
                this.mLastConnectDurationMillis = 0L;
            } else if (!this.mUsbPortStatus.isConnected() && this.mConnectedAtMillis != 0) {
                this.mLastConnectDurationMillis = android.os.SystemClock.elapsedRealtime() - this.mConnectedAtMillis;
                this.mConnectedAtMillis = 0L;
            }
            return z4;
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x006b, code lost:
        
            if (r16.mUsbPortStatus.getPowerBrickConnectionStatus() == r28) goto L33;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean setStatus(int i, boolean z, int i2, boolean z2, int i3, boolean z3, int i4, int i5, int i6, int i7, boolean z4, int i8) {
            this.mCanChangeMode = z;
            this.mCanChangePowerRole = z2;
            this.mCanChangeDataRole = z3;
            boolean z5 = false;
            if (this.mUsbPortStatus != null) {
                if (this.mUsbPortStatus.getCurrentMode() == i) {
                    if (this.mUsbPortStatus.getCurrentPowerRole() == i2) {
                        if (this.mUsbPortStatus.getCurrentDataRole() == i3) {
                            if (this.mUsbPortStatus.getSupportedRoleCombinations() == i4) {
                                if (this.mUsbPortStatus.getContaminantProtectionStatus() == i5) {
                                    if (this.mUsbPortStatus.getContaminantDetectionStatus() == i6) {
                                        if (this.mUsbPortStatus.getUsbDataStatus() == i7) {
                                            if (this.mUsbPortStatus.isPowerTransferLimited() != z4) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.mUsbPortStatus = new android.hardware.usb.UsbPortStatus(i, i2, i3, i4, i5, i6, i7, z4, i8, new int[0], 0, (android.hardware.usb.DisplayPortAltModeInfo) null);
            z5 = true;
            if (this.mUsbPortStatus.isConnected() && this.mConnectedAtMillis == 0) {
                this.mConnectedAtMillis = android.os.SystemClock.elapsedRealtime();
                this.mLastConnectDurationMillis = 0L;
            } else if (!this.mUsbPortStatus.isConnected() && this.mConnectedAtMillis != 0) {
                this.mLastConnectDurationMillis = android.os.SystemClock.elapsedRealtime() - this.mConnectedAtMillis;
                this.mConnectedAtMillis = 0L;
            }
            return z5;
        }

        public boolean setStatus(int i, boolean z, int i2, boolean z2, int i3, boolean z3, int i4, int i5, int i6, int i7, boolean z4, int i8, @android.annotation.NonNull int[] iArr, int i9, android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo) {
            boolean z5;
            boolean z6;
            boolean z7;
            if (this.mUsbPortStatus != null) {
                z5 = complianceWarningsChanged(iArr);
                z6 = displayPortAltModeChanged(displayPortAltModeInfo);
            } else {
                z5 = false;
                z6 = false;
            }
            this.mCanChangeMode = z;
            this.mCanChangePowerRole = z2;
            this.mCanChangeDataRole = z3;
            if (this.mUsbPortStatus != null && this.mUsbPortStatus.getCurrentMode() == i && this.mUsbPortStatus.getCurrentPowerRole() == i2 && this.mUsbPortStatus.getCurrentDataRole() == i3 && this.mUsbPortStatus.getSupportedRoleCombinations() == i4 && this.mUsbPortStatus.getContaminantProtectionStatus() == i5 && this.mUsbPortStatus.getContaminantDetectionStatus() == i6 && this.mUsbPortStatus.getUsbDataStatus() == i7 && this.mUsbPortStatus.isPowerTransferLimited() == z4 && this.mUsbPortStatus.getPowerBrickConnectionStatus() == i8 && this.mUsbPortStatus.getPlugState() == i9) {
                if (z5 || z6) {
                    this.mUsbPortStatus = new android.hardware.usb.UsbPortStatus(i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
                }
                z7 = false;
                if (!this.mUsbPortStatus.isConnected() && this.mConnectedAtMillis == 0) {
                    this.mConnectedAtMillis = android.os.SystemClock.elapsedRealtime();
                    this.mLastConnectDurationMillis = 0L;
                } else if (!this.mUsbPortStatus.isConnected() && this.mConnectedAtMillis != 0) {
                    this.mLastConnectDurationMillis = android.os.SystemClock.elapsedRealtime() - this.mConnectedAtMillis;
                    this.mConnectedAtMillis = 0L;
                }
                return z7;
            }
            if (this.mUsbPortStatus == null && iArr.length > 0) {
                this.mComplianceWarningChange = 1;
            }
            this.mUsbPortStatus = new android.hardware.usb.UsbPortStatus(i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
            z7 = true;
            if (!this.mUsbPortStatus.isConnected()) {
            }
            if (!this.mUsbPortStatus.isConnected()) {
                this.mLastConnectDurationMillis = android.os.SystemClock.elapsedRealtime() - this.mConnectedAtMillis;
                this.mConnectedAtMillis = 0L;
            }
            return z7;
        }

        void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, @android.annotation.NonNull java.lang.String str, long j) {
            long start = dualDumpOutputStream.start(str, j);
            com.android.internal.usb.DumpUtils.writePort(dualDumpOutputStream, "port", 1146756268033L, this.mUsbPort);
            com.android.internal.usb.DumpUtils.writePortStatus(dualDumpOutputStream, "status", 1146756268034L, this.mUsbPortStatus);
            dualDumpOutputStream.write("can_change_mode", 1133871366147L, this.mCanChangeMode);
            dualDumpOutputStream.write("can_change_power_role", 1133871366148L, this.mCanChangePowerRole);
            dualDumpOutputStream.write("can_change_data_role", 1133871366149L, this.mCanChangeDataRole);
            dualDumpOutputStream.write("connected_at_millis", 1112396529670L, this.mConnectedAtMillis);
            dualDumpOutputStream.write("last_connect_duration_millis", 1112396529671L, this.mLastConnectDurationMillis);
            dualDumpOutputStream.end(start);
        }

        public java.lang.String toString() {
            return "port=" + this.mUsbPort + ", status=" + this.mUsbPortStatus + ", canChangeMode=" + this.mCanChangeMode + ", canChangePowerRole=" + this.mCanChangePowerRole + ", canChangeDataRole=" + this.mCanChangeDataRole + ", connectedAtMillis=" + this.mConnectedAtMillis + ", lastConnectDurationMillis=" + this.mLastConnectDurationMillis;
        }
    }
}
