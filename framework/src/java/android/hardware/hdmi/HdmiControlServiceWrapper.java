package android.hardware.hdmi;

/* loaded from: classes2.dex */
public final class HdmiControlServiceWrapper {
    public static final int DEVICE_PURE_CEC_SWITCH = 6;
    private java.util.List<android.hardware.hdmi.HdmiPortInfo> mInfoList = null;
    private int[] mTypes = null;
    private final android.hardware.hdmi.IHdmiControlService mInterface = new android.hardware.hdmi.IHdmiControlService.Stub() { // from class: android.hardware.hdmi.HdmiControlServiceWrapper.1
        @Override // android.hardware.hdmi.IHdmiControlService
        public int[] getSupportedTypes() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getSupportedTypes();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public android.hardware.hdmi.HdmiDeviceInfo getActiveSource() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getActiveSource();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void oneTouchPlay(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.oneTouchPlay(iHdmiControlCallback);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void toggleAndFollowTvPower() {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.toggleAndFollowTvPower();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean shouldHandleTvPowerKey() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.shouldHandleTvPowerKey();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void queryDisplayStatus(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.queryDisplayStatus(iHdmiControlCallback);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.addHdmiControlStatusChangeListener(iHdmiControlStatusChangeListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.removeHdmiControlStatusChangeListener(iHdmiControlStatusChangeListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.addHotplugEventListener(iHdmiHotplugEventListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.removeHotplugEventListener(iHdmiHotplugEventListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addDeviceEventListener(android.hardware.hdmi.IHdmiDeviceEventListener iHdmiDeviceEventListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.addDeviceEventListener(iHdmiDeviceEventListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void deviceSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.deviceSelect(i, iHdmiControlCallback);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void portSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.portSelect(i, iHdmiControlCallback);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendKeyEvent(int i, int i2, boolean z) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.sendKeyEvent(i, i2, z);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendVolumeKeyEvent(int i, int i2, boolean z) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.sendVolumeKeyEvent(i, i2, z);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.util.List<android.hardware.hdmi.HdmiPortInfo> getPortInfo() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getPortInfo();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean canChangeSystemAudioMode() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.canChangeSystemAudioMode();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean getSystemAudioMode() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getSystemAudioMode();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int getPhysicalAddress() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getPhysicalAddress();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioMode(boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setSystemAudioMode(z, iHdmiControlCallback);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.addSystemAudioModeChangeListener(iHdmiSystemAudioModeChangeListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.removeSystemAudioModeChangeListener(iHdmiSystemAudioModeChangeListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setArcMode(boolean z) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setArcMode(z);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setProhibitMode(boolean z) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setProhibitMode(z);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioVolume(int i, int i2, int i3) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setSystemAudioVolume(i, i2, i3);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioMute(boolean z) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setSystemAudioMute(z);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setInputChangeListener(android.hardware.hdmi.IHdmiInputChangeListener iHdmiInputChangeListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setInputChangeListener(iHdmiInputChangeListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getInputDevices() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getInputDevices();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getDeviceList() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getDeviceList();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void powerOffRemoteDevice(int i, int i2) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.powerOffRemoteDevice(i, i2);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void powerOnRemoteDevice(int i, int i2) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.powerOnRemoteDevice(i, i2);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void askRemoteDeviceToBecomeActiveSource(int i) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.askRemoteDeviceToBecomeActiveSource(i);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendVendorCommand(int i, int i2, byte[] bArr, boolean z) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.sendVendorCommand(i, i2, bArr, z);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addVendorCommandListener(android.hardware.hdmi.IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.addVendorCommandListener(iHdmiVendorCommandListener, i);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendStandby(int i, int i2) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.sendStandby(i, i2);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setHdmiRecordListener(android.hardware.hdmi.IHdmiRecordListener iHdmiRecordListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setHdmiRecordListener(iHdmiRecordListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void startOneTouchRecord(int i, byte[] bArr) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.startOneTouchRecord(i, bArr);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void stopOneTouchRecord(int i) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.stopOneTouchRecord(i);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void startTimerRecording(int i, int i2, byte[] bArr) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.startTimerRecording(i, i2, bArr);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void clearTimerRecording(int i, int i2, byte[] bArr) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.clearTimerRecording(i, i2, bArr);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendMhlVendorCommand(int i, int i2, int i3, byte[] bArr) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.sendMhlVendorCommand(i, i2, i3, bArr);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHdmiMhlVendorCommandListener(android.hardware.hdmi.IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.addHdmiMhlVendorCommandListener(iHdmiMhlVendorCommandListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setStandbyMode(boolean z) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setStandbyMode(z);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void reportAudioStatus(int i, int i2, int i3, boolean z) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.reportAudioStatus(i, i2, i3, z);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioModeOnForAudioOnlySource() {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setSystemAudioModeOnForAudioOnlySource();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.addHdmiCecVolumeControlFeatureListener(iHdmiCecVolumeControlFeatureListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.removeHdmiCecVolumeControlFeatureListener(iHdmiCecVolumeControlFeatureListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int getMessageHistorySize() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getMessageHistorySize();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean setMessageHistorySize(int i) {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.setMessageHistorySize(i);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.addCecSettingChangeListener(str, iHdmiCecSettingChangeListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.removeCecSettingChangeListener(str, iHdmiCecSettingChangeListener);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.util.List<java.lang.String> getUserCecSettings() {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getUserCecSettings();
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.util.List<java.lang.String> getAllowedCecSettingStringValues(java.lang.String str) {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getAllowedCecSettingStringValues(str);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int[] getAllowedCecSettingIntValues(java.lang.String str) {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getAllowedCecSettingIntValues(str);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public java.lang.String getCecSettingStringValue(java.lang.String str) {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getCecSettingStringValue(str);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setCecSettingStringValue(java.lang.String str, java.lang.String str2) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setCecSettingStringValue(str, str2);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int getCecSettingIntValue(java.lang.String str) {
            return android.hardware.hdmi.HdmiControlServiceWrapper.this.getCecSettingIntValue(str);
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setCecSettingIntValue(java.lang.String str, int i) {
            android.hardware.hdmi.HdmiControlServiceWrapper.this.setCecSettingIntValue(str, i);
        }
    };

    public android.hardware.hdmi.HdmiControlManager createHdmiControlManager() {
        return new android.hardware.hdmi.HdmiControlManager(this.mInterface);
    }

    public void setPortInfo(java.util.List<android.hardware.hdmi.HdmiPortInfo> list) {
        this.mInfoList = list;
    }

    public void setDeviceTypes(int[] iArr) {
        this.mTypes = iArr;
    }

    public java.util.List<android.hardware.hdmi.HdmiPortInfo> getPortInfo() {
        return this.mInfoList;
    }

    public int[] getSupportedTypes() {
        return this.mTypes;
    }

    public android.hardware.hdmi.HdmiDeviceInfo getActiveSource() {
        return null;
    }

    public void oneTouchPlay(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
    }

    public void toggleAndFollowTvPower() {
    }

    public boolean shouldHandleTvPowerKey() {
        return true;
    }

    public void queryDisplayStatus(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
    }

    public void addHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
    }

    public void removeHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
    }

    public void addHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) {
    }

    public void removeHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) {
    }

    public void addDeviceEventListener(android.hardware.hdmi.IHdmiDeviceEventListener iHdmiDeviceEventListener) {
    }

    public void deviceSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
    }

    public void portSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
    }

    public void sendKeyEvent(int i, int i2, boolean z) {
    }

    public void sendVolumeKeyEvent(int i, int i2, boolean z) {
    }

    public boolean canChangeSystemAudioMode() {
        return true;
    }

    public boolean getSystemAudioMode() {
        return true;
    }

    public int getPhysicalAddress() {
        return 65535;
    }

    public void setSystemAudioMode(boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
    }

    public void addSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
    }

    public void removeSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
    }

    public void setArcMode(boolean z) {
    }

    public void setProhibitMode(boolean z) {
    }

    public void setSystemAudioVolume(int i, int i2, int i3) {
    }

    public void setSystemAudioMute(boolean z) {
    }

    public void setInputChangeListener(android.hardware.hdmi.IHdmiInputChangeListener iHdmiInputChangeListener) {
    }

    public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getInputDevices() {
        return null;
    }

    public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getDeviceList() {
        return null;
    }

    public void powerOffRemoteDevice(int i, int i2) {
    }

    public void powerOnRemoteDevice(int i, int i2) {
    }

    public void askRemoteDeviceToBecomeActiveSource(int i) {
    }

    public void sendVendorCommand(int i, int i2, byte[] bArr, boolean z) {
    }

    public void addVendorCommandListener(android.hardware.hdmi.IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) {
    }

    public void sendStandby(int i, int i2) {
    }

    public void setHdmiRecordListener(android.hardware.hdmi.IHdmiRecordListener iHdmiRecordListener) {
    }

    public void startOneTouchRecord(int i, byte[] bArr) {
    }

    public void stopOneTouchRecord(int i) {
    }

    public void startTimerRecording(int i, int i2, byte[] bArr) {
    }

    public void clearTimerRecording(int i, int i2, byte[] bArr) {
    }

    public void sendMhlVendorCommand(int i, int i2, int i3, byte[] bArr) {
    }

    public void addHdmiMhlVendorCommandListener(android.hardware.hdmi.IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) {
    }

    public void setStandbyMode(boolean z) {
    }

    public void setHdmiCecVolumeControlEnabled(boolean z) {
    }

    public boolean isHdmiCecVolumeControlEnabled() {
        return true;
    }

    public void reportAudioStatus(int i, int i2, int i3, boolean z) {
    }

    public void setSystemAudioModeOnForAudioOnlySource() {
    }

    public void addHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
    }

    public void removeHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
    }

    public int getMessageHistorySize() {
        return 0;
    }

    public boolean setMessageHistorySize(int i) {
        return true;
    }

    public void addCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
    }

    public void removeCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
    }

    public java.util.List<java.lang.String> getUserCecSettings() {
        return new java.util.ArrayList();
    }

    public java.util.List<java.lang.String> getAllowedCecSettingStringValues(java.lang.String str) {
        return new java.util.ArrayList();
    }

    public int[] getAllowedCecSettingIntValues(java.lang.String str) {
        return new int[0];
    }

    public java.lang.String getCecSettingStringValue(java.lang.String str) {
        return "";
    }

    public void setCecSettingStringValue(java.lang.String str, java.lang.String str2) {
    }

    public int getCecSettingIntValue(java.lang.String str) {
        return 0;
    }

    public void setCecSettingIntValue(java.lang.String str, int i) {
    }
}
