package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class HdmiMhlControllerStub {
    private static final int INVALID_DEVICE_ROLES = 0;
    private static final int INVALID_MHL_VERSION = 0;
    private static final int NO_SUPPORTED_FEATURES = 0;
    private static final android.util.SparseArray<com.android.server.hdmi.HdmiMhlLocalDeviceStub> mLocalDevices = new android.util.SparseArray<>();
    private static final android.hardware.hdmi.HdmiPortInfo[] EMPTY_PORT_INFO = new android.hardware.hdmi.HdmiPortInfo[0];

    private HdmiMhlControllerStub(com.android.server.hdmi.HdmiControlService hdmiControlService) {
    }

    boolean isReady() {
        return false;
    }

    static com.android.server.hdmi.HdmiMhlControllerStub create(com.android.server.hdmi.HdmiControlService hdmiControlService) {
        return new com.android.server.hdmi.HdmiMhlControllerStub(hdmiControlService);
    }

    android.hardware.hdmi.HdmiPortInfo[] getPortInfos() {
        return EMPTY_PORT_INFO;
    }

    com.android.server.hdmi.HdmiMhlLocalDeviceStub getLocalDevice(int i) {
        return null;
    }

    com.android.server.hdmi.HdmiMhlLocalDeviceStub getLocalDeviceById(int i) {
        return null;
    }

    android.util.SparseArray<com.android.server.hdmi.HdmiMhlLocalDeviceStub> getAllLocalDevices() {
        return mLocalDevices;
    }

    com.android.server.hdmi.HdmiMhlLocalDeviceStub removeLocalDevice(int i) {
        return null;
    }

    com.android.server.hdmi.HdmiMhlLocalDeviceStub addLocalDevice(com.android.server.hdmi.HdmiMhlLocalDeviceStub hdmiMhlLocalDeviceStub) {
        return null;
    }

    void clearAllLocalDevices() {
    }

    void sendVendorCommand(int i, int i2, int i3, byte[] bArr) {
    }

    void setOption(int i, int i2) {
    }

    int getMhlVersion(int i) {
        return 0;
    }

    int getPeerMhlVersion(int i) {
        return 0;
    }

    int getSupportedFeatures(int i) {
        return 0;
    }

    int getEcbusDeviceRoles(int i) {
        return 0;
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
    }
}
