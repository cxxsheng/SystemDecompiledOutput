package android.hardware.hdmi;

/* loaded from: classes2.dex */
public interface IHdmiControlService extends android.os.IInterface {
    void addCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws android.os.RemoteException;

    void addDeviceEventListener(android.hardware.hdmi.IHdmiDeviceEventListener iHdmiDeviceEventListener) throws android.os.RemoteException;

    void addHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws android.os.RemoteException;

    void addHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws android.os.RemoteException;

    void addHdmiMhlVendorCommandListener(android.hardware.hdmi.IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) throws android.os.RemoteException;

    void addHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) throws android.os.RemoteException;

    void addSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws android.os.RemoteException;

    void addVendorCommandListener(android.hardware.hdmi.IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) throws android.os.RemoteException;

    void askRemoteDeviceToBecomeActiveSource(int i) throws android.os.RemoteException;

    boolean canChangeSystemAudioMode() throws android.os.RemoteException;

    void clearTimerRecording(int i, int i2, byte[] bArr) throws android.os.RemoteException;

    void deviceSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException;

    android.hardware.hdmi.HdmiDeviceInfo getActiveSource() throws android.os.RemoteException;

    int[] getAllowedCecSettingIntValues(java.lang.String str) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAllowedCecSettingStringValues(java.lang.String str) throws android.os.RemoteException;

    int getCecSettingIntValue(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getCecSettingStringValue(java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getDeviceList() throws android.os.RemoteException;

    java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getInputDevices() throws android.os.RemoteException;

    int getMessageHistorySize() throws android.os.RemoteException;

    int getPhysicalAddress() throws android.os.RemoteException;

    java.util.List<android.hardware.hdmi.HdmiPortInfo> getPortInfo() throws android.os.RemoteException;

    int[] getSupportedTypes() throws android.os.RemoteException;

    boolean getSystemAudioMode() throws android.os.RemoteException;

    java.util.List<java.lang.String> getUserCecSettings() throws android.os.RemoteException;

    void oneTouchPlay(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException;

    void portSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException;

    void powerOffRemoteDevice(int i, int i2) throws android.os.RemoteException;

    void powerOnRemoteDevice(int i, int i2) throws android.os.RemoteException;

    void queryDisplayStatus(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException;

    void removeCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws android.os.RemoteException;

    void removeHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws android.os.RemoteException;

    void removeHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws android.os.RemoteException;

    void removeHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) throws android.os.RemoteException;

    void removeSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws android.os.RemoteException;

    void reportAudioStatus(int i, int i2, int i3, boolean z) throws android.os.RemoteException;

    void sendKeyEvent(int i, int i2, boolean z) throws android.os.RemoteException;

    void sendMhlVendorCommand(int i, int i2, int i3, byte[] bArr) throws android.os.RemoteException;

    void sendStandby(int i, int i2) throws android.os.RemoteException;

    void sendVendorCommand(int i, int i2, byte[] bArr, boolean z) throws android.os.RemoteException;

    void sendVolumeKeyEvent(int i, int i2, boolean z) throws android.os.RemoteException;

    void setArcMode(boolean z) throws android.os.RemoteException;

    void setCecSettingIntValue(java.lang.String str, int i) throws android.os.RemoteException;

    void setCecSettingStringValue(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setHdmiRecordListener(android.hardware.hdmi.IHdmiRecordListener iHdmiRecordListener) throws android.os.RemoteException;

    void setInputChangeListener(android.hardware.hdmi.IHdmiInputChangeListener iHdmiInputChangeListener) throws android.os.RemoteException;

    boolean setMessageHistorySize(int i) throws android.os.RemoteException;

    void setProhibitMode(boolean z) throws android.os.RemoteException;

    void setStandbyMode(boolean z) throws android.os.RemoteException;

    void setSystemAudioMode(boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException;

    void setSystemAudioModeOnForAudioOnlySource() throws android.os.RemoteException;

    void setSystemAudioMute(boolean z) throws android.os.RemoteException;

    void setSystemAudioVolume(int i, int i2, int i3) throws android.os.RemoteException;

    boolean shouldHandleTvPowerKey() throws android.os.RemoteException;

    void startOneTouchRecord(int i, byte[] bArr) throws android.os.RemoteException;

    void startTimerRecording(int i, int i2, byte[] bArr) throws android.os.RemoteException;

    void stopOneTouchRecord(int i) throws android.os.RemoteException;

    void toggleAndFollowTvPower() throws android.os.RemoteException;

    public static class Default implements android.hardware.hdmi.IHdmiControlService {
        @Override // android.hardware.hdmi.IHdmiControlService
        public int[] getSupportedTypes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public android.hardware.hdmi.HdmiDeviceInfo getActiveSource() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void oneTouchPlay(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void toggleAndFollowTvPower() throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean shouldHandleTvPowerKey() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void queryDisplayStatus(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addDeviceEventListener(android.hardware.hdmi.IHdmiDeviceEventListener iHdmiDeviceEventListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void deviceSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void portSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendKeyEvent(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendVolumeKeyEvent(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.util.List<android.hardware.hdmi.HdmiPortInfo> getPortInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean canChangeSystemAudioMode() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean getSystemAudioMode() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int getPhysicalAddress() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioMode(boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setArcMode(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setProhibitMode(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioVolume(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioMute(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setInputChangeListener(android.hardware.hdmi.IHdmiInputChangeListener iHdmiInputChangeListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getInputDevices() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getDeviceList() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void powerOffRemoteDevice(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void powerOnRemoteDevice(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void askRemoteDeviceToBecomeActiveSource(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendVendorCommand(int i, int i2, byte[] bArr, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addVendorCommandListener(android.hardware.hdmi.IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendStandby(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setHdmiRecordListener(android.hardware.hdmi.IHdmiRecordListener iHdmiRecordListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void startOneTouchRecord(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void stopOneTouchRecord(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void startTimerRecording(int i, int i2, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void clearTimerRecording(int i, int i2, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendMhlVendorCommand(int i, int i2, int i3, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHdmiMhlVendorCommandListener(android.hardware.hdmi.IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setStandbyMode(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void reportAudioStatus(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioModeOnForAudioOnlySource() throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean setMessageHistorySize(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int getMessageHistorySize() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.util.List<java.lang.String> getUserCecSettings() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.util.List<java.lang.String> getAllowedCecSettingStringValues(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int[] getAllowedCecSettingIntValues(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.lang.String getCecSettingStringValue(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setCecSettingStringValue(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int getCecSettingIntValue(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setCecSettingIntValue(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.hdmi.IHdmiControlService {
        public static final java.lang.String DESCRIPTOR = "android.hardware.hdmi.IHdmiControlService";
        static final int TRANSACTION_addCecSettingChangeListener = 50;
        static final int TRANSACTION_addDeviceEventListener = 13;
        static final int TRANSACTION_addHdmiCecVolumeControlFeatureListener = 9;
        static final int TRANSACTION_addHdmiControlStatusChangeListener = 7;
        static final int TRANSACTION_addHdmiMhlVendorCommandListener = 44;
        static final int TRANSACTION_addHotplugEventListener = 11;
        static final int TRANSACTION_addSystemAudioModeChangeListener = 23;
        static final int TRANSACTION_addVendorCommandListener = 36;
        static final int TRANSACTION_askRemoteDeviceToBecomeActiveSource = 34;
        static final int TRANSACTION_canChangeSystemAudioMode = 19;
        static final int TRANSACTION_clearTimerRecording = 42;
        static final int TRANSACTION_deviceSelect = 14;
        static final int TRANSACTION_getActiveSource = 2;
        static final int TRANSACTION_getAllowedCecSettingIntValues = 54;
        static final int TRANSACTION_getAllowedCecSettingStringValues = 53;
        static final int TRANSACTION_getCecSettingIntValue = 57;
        static final int TRANSACTION_getCecSettingStringValue = 55;
        static final int TRANSACTION_getDeviceList = 31;
        static final int TRANSACTION_getInputDevices = 30;
        static final int TRANSACTION_getMessageHistorySize = 49;
        static final int TRANSACTION_getPhysicalAddress = 21;
        static final int TRANSACTION_getPortInfo = 18;
        static final int TRANSACTION_getSupportedTypes = 1;
        static final int TRANSACTION_getSystemAudioMode = 20;
        static final int TRANSACTION_getUserCecSettings = 52;
        static final int TRANSACTION_oneTouchPlay = 3;
        static final int TRANSACTION_portSelect = 15;
        static final int TRANSACTION_powerOffRemoteDevice = 32;
        static final int TRANSACTION_powerOnRemoteDevice = 33;
        static final int TRANSACTION_queryDisplayStatus = 6;
        static final int TRANSACTION_removeCecSettingChangeListener = 51;
        static final int TRANSACTION_removeHdmiCecVolumeControlFeatureListener = 10;
        static final int TRANSACTION_removeHdmiControlStatusChangeListener = 8;
        static final int TRANSACTION_removeHotplugEventListener = 12;
        static final int TRANSACTION_removeSystemAudioModeChangeListener = 24;
        static final int TRANSACTION_reportAudioStatus = 46;
        static final int TRANSACTION_sendKeyEvent = 16;
        static final int TRANSACTION_sendMhlVendorCommand = 43;
        static final int TRANSACTION_sendStandby = 37;
        static final int TRANSACTION_sendVendorCommand = 35;
        static final int TRANSACTION_sendVolumeKeyEvent = 17;
        static final int TRANSACTION_setArcMode = 25;
        static final int TRANSACTION_setCecSettingIntValue = 58;
        static final int TRANSACTION_setCecSettingStringValue = 56;
        static final int TRANSACTION_setHdmiRecordListener = 38;
        static final int TRANSACTION_setInputChangeListener = 29;
        static final int TRANSACTION_setMessageHistorySize = 48;
        static final int TRANSACTION_setProhibitMode = 26;
        static final int TRANSACTION_setStandbyMode = 45;
        static final int TRANSACTION_setSystemAudioMode = 22;
        static final int TRANSACTION_setSystemAudioModeOnForAudioOnlySource = 47;
        static final int TRANSACTION_setSystemAudioMute = 28;
        static final int TRANSACTION_setSystemAudioVolume = 27;
        static final int TRANSACTION_shouldHandleTvPowerKey = 5;
        static final int TRANSACTION_startOneTouchRecord = 39;
        static final int TRANSACTION_startTimerRecording = 41;
        static final int TRANSACTION_stopOneTouchRecord = 40;
        static final int TRANSACTION_toggleAndFollowTvPower = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.hdmi.IHdmiControlService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.hdmi.IHdmiControlService)) {
                return (android.hardware.hdmi.IHdmiControlService) queryLocalInterface;
            }
            return new android.hardware.hdmi.IHdmiControlService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSupportedTypes";
                case 2:
                    return "getActiveSource";
                case 3:
                    return "oneTouchPlay";
                case 4:
                    return "toggleAndFollowTvPower";
                case 5:
                    return "shouldHandleTvPowerKey";
                case 6:
                    return "queryDisplayStatus";
                case 7:
                    return "addHdmiControlStatusChangeListener";
                case 8:
                    return "removeHdmiControlStatusChangeListener";
                case 9:
                    return "addHdmiCecVolumeControlFeatureListener";
                case 10:
                    return "removeHdmiCecVolumeControlFeatureListener";
                case 11:
                    return "addHotplugEventListener";
                case 12:
                    return "removeHotplugEventListener";
                case 13:
                    return "addDeviceEventListener";
                case 14:
                    return "deviceSelect";
                case 15:
                    return "portSelect";
                case 16:
                    return "sendKeyEvent";
                case 17:
                    return "sendVolumeKeyEvent";
                case 18:
                    return "getPortInfo";
                case 19:
                    return "canChangeSystemAudioMode";
                case 20:
                    return "getSystemAudioMode";
                case 21:
                    return "getPhysicalAddress";
                case 22:
                    return "setSystemAudioMode";
                case 23:
                    return "addSystemAudioModeChangeListener";
                case 24:
                    return "removeSystemAudioModeChangeListener";
                case 25:
                    return "setArcMode";
                case 26:
                    return "setProhibitMode";
                case 27:
                    return "setSystemAudioVolume";
                case 28:
                    return "setSystemAudioMute";
                case 29:
                    return "setInputChangeListener";
                case 30:
                    return "getInputDevices";
                case 31:
                    return "getDeviceList";
                case 32:
                    return "powerOffRemoteDevice";
                case 33:
                    return "powerOnRemoteDevice";
                case 34:
                    return "askRemoteDeviceToBecomeActiveSource";
                case 35:
                    return "sendVendorCommand";
                case 36:
                    return "addVendorCommandListener";
                case 37:
                    return "sendStandby";
                case 38:
                    return "setHdmiRecordListener";
                case 39:
                    return "startOneTouchRecord";
                case 40:
                    return "stopOneTouchRecord";
                case 41:
                    return "startTimerRecording";
                case 42:
                    return "clearTimerRecording";
                case 43:
                    return "sendMhlVendorCommand";
                case 44:
                    return "addHdmiMhlVendorCommandListener";
                case 45:
                    return "setStandbyMode";
                case 46:
                    return "reportAudioStatus";
                case 47:
                    return "setSystemAudioModeOnForAudioOnlySource";
                case 48:
                    return "setMessageHistorySize";
                case 49:
                    return "getMessageHistorySize";
                case 50:
                    return "addCecSettingChangeListener";
                case 51:
                    return "removeCecSettingChangeListener";
                case 52:
                    return "getUserCecSettings";
                case 53:
                    return "getAllowedCecSettingStringValues";
                case 54:
                    return "getAllowedCecSettingIntValues";
                case 55:
                    return "getCecSettingStringValue";
                case 56:
                    return "setCecSettingStringValue";
                case 57:
                    return "getCecSettingIntValue";
                case 58:
                    return "setCecSettingIntValue";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] supportedTypes = getSupportedTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedTypes);
                    return true;
                case 2:
                    android.hardware.hdmi.HdmiDeviceInfo activeSource = getActiveSource();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSource, 1);
                    return true;
                case 3:
                    android.hardware.hdmi.IHdmiControlCallback asInterface = android.hardware.hdmi.IHdmiControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    oneTouchPlay(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    toggleAndFollowTvPower();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean shouldHandleTvPowerKey = shouldHandleTvPowerKey();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldHandleTvPowerKey);
                    return true;
                case 6:
                    android.hardware.hdmi.IHdmiControlCallback asInterface2 = android.hardware.hdmi.IHdmiControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    queryDisplayStatus(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.hardware.hdmi.IHdmiControlStatusChangeListener asInterface3 = android.hardware.hdmi.IHdmiControlStatusChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addHdmiControlStatusChangeListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.hardware.hdmi.IHdmiControlStatusChangeListener asInterface4 = android.hardware.hdmi.IHdmiControlStatusChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeHdmiControlStatusChangeListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener asInterface5 = android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addHdmiCecVolumeControlFeatureListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener asInterface6 = android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeHdmiCecVolumeControlFeatureListener(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.hardware.hdmi.IHdmiHotplugEventListener asInterface7 = android.hardware.hdmi.IHdmiHotplugEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addHotplugEventListener(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.hardware.hdmi.IHdmiHotplugEventListener asInterface8 = android.hardware.hdmi.IHdmiHotplugEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeHotplugEventListener(asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.hardware.hdmi.IHdmiDeviceEventListener asInterface9 = android.hardware.hdmi.IHdmiDeviceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addDeviceEventListener(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt = parcel.readInt();
                    android.hardware.hdmi.IHdmiControlCallback asInterface10 = android.hardware.hdmi.IHdmiControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deviceSelect(readInt, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt2 = parcel.readInt();
                    android.hardware.hdmi.IHdmiControlCallback asInterface11 = android.hardware.hdmi.IHdmiControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    portSelect(readInt2, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendKeyEvent(readInt3, readInt4, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendVolumeKeyEvent(readInt5, readInt6, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    java.util.List<android.hardware.hdmi.HdmiPortInfo> portInfo = getPortInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(portInfo, 1);
                    return true;
                case 19:
                    boolean canChangeSystemAudioMode = canChangeSystemAudioMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canChangeSystemAudioMode);
                    return true;
                case 20:
                    boolean systemAudioMode = getSystemAudioMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(systemAudioMode);
                    return true;
                case 21:
                    int physicalAddress = getPhysicalAddress();
                    parcel2.writeNoException();
                    parcel2.writeInt(physicalAddress);
                    return true;
                case 22:
                    boolean readBoolean3 = parcel.readBoolean();
                    android.hardware.hdmi.IHdmiControlCallback asInterface12 = android.hardware.hdmi.IHdmiControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSystemAudioMode(readBoolean3, asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    android.hardware.hdmi.IHdmiSystemAudioModeChangeListener asInterface13 = android.hardware.hdmi.IHdmiSystemAudioModeChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addSystemAudioModeChangeListener(asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.hardware.hdmi.IHdmiSystemAudioModeChangeListener asInterface14 = android.hardware.hdmi.IHdmiSystemAudioModeChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSystemAudioModeChangeListener(asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setArcMode(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setProhibitMode(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSystemAudioVolume(readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSystemAudioMute(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    android.hardware.hdmi.IHdmiInputChangeListener asInterface15 = android.hardware.hdmi.IHdmiInputChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setInputChangeListener(asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    java.util.List<android.hardware.hdmi.HdmiDeviceInfo> inputDevices = getInputDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(inputDevices, 1);
                    return true;
                case 31:
                    java.util.List<android.hardware.hdmi.HdmiDeviceInfo> deviceList = getDeviceList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(deviceList, 1);
                    return true;
                case 32:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    powerOffRemoteDevice(readInt10, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    powerOnRemoteDevice(readInt12, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    askRemoteDeviceToBecomeActiveSource(readInt14);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendVendorCommand(readInt15, readInt16, createByteArray, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    android.hardware.hdmi.IHdmiVendorCommandListener asInterface16 = android.hardware.hdmi.IHdmiVendorCommandListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addVendorCommandListener(asInterface16, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendStandby(readInt18, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    android.hardware.hdmi.IHdmiRecordListener asInterface17 = android.hardware.hdmi.IHdmiRecordListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setHdmiRecordListener(asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt20 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    startOneTouchRecord(readInt20, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopOneTouchRecord(readInt21);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    startTimerRecording(readInt22, readInt23, createByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    byte[] createByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    clearTimerRecording(readInt24, readInt25, createByteArray4);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    byte[] createByteArray5 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendMhlVendorCommand(readInt26, readInt27, readInt28, createByteArray5);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    android.hardware.hdmi.IHdmiMhlVendorCommandListener asInterface18 = android.hardware.hdmi.IHdmiMhlVendorCommandListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addHdmiMhlVendorCommandListener(asInterface18);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStandbyMode(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportAudioStatus(readInt29, readInt30, readInt31, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    setSystemAudioModeOnForAudioOnlySource();
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean messageHistorySize = setMessageHistorySize(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(messageHistorySize);
                    return true;
                case 49:
                    int messageHistorySize2 = getMessageHistorySize();
                    parcel2.writeNoException();
                    parcel2.writeInt(messageHistorySize2);
                    return true;
                case 50:
                    java.lang.String readString = parcel.readString();
                    android.hardware.hdmi.IHdmiCecSettingChangeListener asInterface19 = android.hardware.hdmi.IHdmiCecSettingChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCecSettingChangeListener(readString, asInterface19);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    java.lang.String readString2 = parcel.readString();
                    android.hardware.hdmi.IHdmiCecSettingChangeListener asInterface20 = android.hardware.hdmi.IHdmiCecSettingChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCecSettingChangeListener(readString2, asInterface20);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    java.util.List<java.lang.String> userCecSettings = getUserCecSettings();
                    parcel2.writeNoException();
                    parcel2.writeStringList(userCecSettings);
                    return true;
                case 53:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> allowedCecSettingStringValues = getAllowedCecSettingStringValues(readString3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowedCecSettingStringValues);
                    return true;
                case 54:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] allowedCecSettingIntValues = getAllowedCecSettingIntValues(readString4);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(allowedCecSettingIntValues);
                    return true;
                case 55:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String cecSettingStringValue = getCecSettingStringValue(readString5);
                    parcel2.writeNoException();
                    parcel2.writeString(cecSettingStringValue);
                    return true;
                case 56:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCecSettingStringValue(readString6, readString7);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cecSettingIntValue = getCecSettingIntValue(readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(cecSettingIntValue);
                    return true;
                case 58:
                    java.lang.String readString9 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCecSettingIntValue(readString9, readInt33);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.hdmi.IHdmiControlService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public int[] getSupportedTypes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public android.hardware.hdmi.HdmiDeviceInfo getActiveSource() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.hdmi.HdmiDeviceInfo) obtain2.readTypedObject(android.hardware.hdmi.HdmiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void oneTouchPlay(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiControlCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void toggleAndFollowTvPower() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public boolean shouldHandleTvPowerKey() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void queryDisplayStatus(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiControlCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiControlStatusChangeListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void removeHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiControlStatusChangeListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiCecVolumeControlFeatureListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void removeHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiCecVolumeControlFeatureListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiHotplugEventListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void removeHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiHotplugEventListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addDeviceEventListener(android.hardware.hdmi.IHdmiDeviceEventListener iHdmiDeviceEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiDeviceEventListener);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void deviceSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iHdmiControlCallback);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void portSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iHdmiControlCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void sendKeyEvent(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void sendVolumeKeyEvent(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public java.util.List<android.hardware.hdmi.HdmiPortInfo> getPortInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.hdmi.HdmiPortInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public boolean canChangeSystemAudioMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public boolean getSystemAudioMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public int getPhysicalAddress() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setSystemAudioMode(boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iHdmiControlCallback);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiSystemAudioModeChangeListener);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void removeSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiSystemAudioModeChangeListener);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setArcMode(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setProhibitMode(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setSystemAudioVolume(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setSystemAudioMute(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setInputChangeListener(android.hardware.hdmi.IHdmiInputChangeListener iHdmiInputChangeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiInputChangeListener);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getInputDevices() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.hdmi.HdmiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getDeviceList() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.hdmi.HdmiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void powerOffRemoteDevice(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void powerOnRemoteDevice(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void askRemoteDeviceToBecomeActiveSource(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void sendVendorCommand(int i, int i2, byte[] bArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addVendorCommandListener(android.hardware.hdmi.IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiVendorCommandListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void sendStandby(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setHdmiRecordListener(android.hardware.hdmi.IHdmiRecordListener iHdmiRecordListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiRecordListener);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void startOneTouchRecord(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void stopOneTouchRecord(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void startTimerRecording(int i, int i2, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void clearTimerRecording(int i, int i2, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void sendMhlVendorCommand(int i, int i2, int i3, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addHdmiMhlVendorCommandListener(android.hardware.hdmi.IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiMhlVendorCommandListener);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setStandbyMode(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void reportAudioStatus(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setSystemAudioModeOnForAudioOnlySource() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public boolean setMessageHistorySize(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public int getMessageHistorySize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iHdmiCecSettingChangeListener);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void removeCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iHdmiCecSettingChangeListener);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public java.util.List<java.lang.String> getUserCecSettings() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public java.util.List<java.lang.String> getAllowedCecSettingStringValues(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public int[] getAllowedCecSettingIntValues(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public java.lang.String getCecSettingStringValue(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setCecSettingStringValue(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public int getCecSettingIntValue(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setCecSettingIntValue(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.hdmi.IHdmiControlService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 57;
        }
    }
}
