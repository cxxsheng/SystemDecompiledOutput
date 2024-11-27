package com.android.server.usb;

/* loaded from: classes2.dex */
public final class UsbAlsaDevice {
    protected static final boolean DEBUG = false;
    private static final java.lang.String[] DIRECTION_STR = {"INPUT", "OUTPUT"};
    private static final int INPUT = 0;
    private static final int NUM_DIRECTIONS = 2;
    private static final int OUTPUT = 1;
    private static final java.lang.String TAG = "UsbAlsaDevice";
    private final java.lang.String mAlsaCardDeviceString;
    private android.media.IAudioService mAudioService;
    private final int mCardNum;
    private final java.lang.String mDeviceAddress;
    private final int mDeviceNum;
    private final boolean mIsDock;
    private com.android.server.usb.UsbAlsaJackDetector mJackDetector;
    private final boolean[] mHasDevice = new boolean[2];
    private final boolean[] mIsHeadset = new boolean[2];
    private final int[] mDeviceType = new int[2];
    private boolean[] mIsSelected = new boolean[2];
    private int[] mState = new int[2];
    private java.lang.String mDeviceName = "";
    private java.lang.String mDeviceDescription = "";
    private boolean mHasJackDetect = true;

    public UsbAlsaDevice(android.media.IAudioService iAudioService, int i, int i2, java.lang.String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.mAudioService = iAudioService;
        this.mCardNum = i;
        this.mDeviceNum = i2;
        this.mDeviceAddress = str;
        this.mHasDevice[1] = z;
        this.mHasDevice[0] = z2;
        this.mIsHeadset[0] = z3;
        this.mIsHeadset[1] = z4;
        this.mIsDock = z5;
        initDeviceType();
        this.mAlsaCardDeviceString = getAlsaCardDeviceString();
    }

    public int getCardNum() {
        return this.mCardNum;
    }

    public int getDeviceNum() {
        return this.mDeviceNum;
    }

    public java.lang.String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    public java.lang.String getAlsaCardDeviceString() {
        if (this.mCardNum < 0 || this.mDeviceNum < 0) {
            android.util.Slog.e(TAG, "Invalid alsa card or device alsaCard: " + this.mCardNum + " alsaDevice: " + this.mDeviceNum);
            return null;
        }
        return com.android.server.audio.AudioService.makeAlsaAddressString(this.mCardNum, this.mDeviceNum);
    }

    public boolean hasOutput() {
        return this.mHasDevice[1];
    }

    public boolean hasInput() {
        return this.mHasDevice[0];
    }

    public boolean isOutputHeadset() {
        return this.mIsHeadset[1];
    }

    public boolean isInputHeadset() {
        return this.mIsHeadset[0];
    }

    public boolean isDock() {
        return this.mIsDock;
    }

    private synchronized boolean isInputJackConnected() {
        if (this.mJackDetector == null) {
            return true;
        }
        return this.mJackDetector.isInputJackConnected();
    }

    private synchronized boolean isOutputJackConnected() {
        if (this.mJackDetector == null) {
            return true;
        }
        return this.mJackDetector.isOutputJackConnected();
    }

    private synchronized void startJackDetect() {
        if (this.mJackDetector != null) {
            return;
        }
        if (this.mHasJackDetect) {
            this.mJackDetector = com.android.server.usb.UsbAlsaJackDetector.startJackDetect(this);
            if (this.mJackDetector == null) {
                this.mHasJackDetect = false;
            }
        }
    }

    private synchronized void stopJackDetect() {
        try {
            if (this.mJackDetector != null) {
                this.mJackDetector.pleaseStop();
            }
            this.mJackDetector = null;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void start() {
        startOutput();
        startInput();
    }

    public synchronized void startInput() {
        startDevice(0);
    }

    public synchronized void startOutput() {
        startDevice(1);
    }

    private void startDevice(int i) {
        if (this.mIsSelected[i]) {
            return;
        }
        this.mIsSelected[i] = true;
        this.mState[i] = 0;
        startJackDetect();
        updateWiredDeviceConnectionState(i, true);
    }

    public synchronized void stop() {
        stopOutput();
        stopInput();
    }

    public synchronized void stopInput() {
        try {
            if (this.mIsSelected[0]) {
                if (!this.mIsSelected[1]) {
                    stopJackDetect();
                }
                updateInputWiredDeviceConnectionState(false);
                this.mIsSelected[0] = false;
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void stopOutput() {
        try {
            if (this.mIsSelected[1]) {
                if (!this.mIsSelected[0]) {
                    stopJackDetect();
                }
                updateOutputWiredDeviceConnectionState(false);
                this.mIsSelected[1] = false;
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private void initDeviceType() {
        int i;
        int[] iArr = this.mDeviceType;
        int i2 = 0;
        if (this.mHasDevice[0]) {
            i = this.mIsHeadset[0] ? android.hardware.audio.common.V2_0.AudioDevice.IN_USB_HEADSET : android.hardware.audio.common.V2_0.AudioDevice.IN_USB_DEVICE;
        } else {
            i = 0;
        }
        iArr[0] = i;
        int[] iArr2 = this.mDeviceType;
        if (this.mHasDevice[1]) {
            if (this.mIsDock) {
                i2 = 4096;
            } else {
                i2 = this.mIsHeadset[1] ? 67108864 : 16384;
            }
        }
        iArr2[1] = i2;
    }

    public int getOutputDeviceType() {
        return this.mDeviceType[1];
    }

    public int getInputDeviceType() {
        return this.mDeviceType[0];
    }

    private boolean updateWiredDeviceConnectionState(int i, boolean z) {
        if (!this.mIsSelected[i]) {
            android.util.Slog.e(TAG, "Updating wired device connection state on unselected device");
            return false;
        }
        if (this.mDeviceType[i] == 0) {
            android.util.Slog.d(TAG, "Unable to set device connection state as " + DIRECTION_STR[i] + " device type is none");
            return false;
        }
        if (this.mAlsaCardDeviceString == null) {
            android.util.Slog.w(TAG, "Failed to update " + DIRECTION_STR[i] + " device connection state failed as alsa card device string is null");
            return false;
        }
        boolean isInputJackConnected = i == 0 ? isInputJackConnected() : isOutputJackConnected();
        android.util.Slog.i(TAG, DIRECTION_STR[i] + " JACK connected: " + isInputJackConnected);
        int i2 = (z && isInputJackConnected) ? 1 : 0;
        if (i2 != this.mState[i]) {
            this.mState[i] = i2;
            try {
                this.mAudioService.setWiredDeviceConnectionState(new android.media.AudioDeviceAttributes(this.mDeviceType[i], this.mAlsaCardDeviceString, this.mDeviceName), i2, TAG);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "RemoteException in setWiredDeviceConnectionState for " + DIRECTION_STR[i]);
                return false;
            }
        }
        return true;
    }

    public synchronized boolean updateInputWiredDeviceConnectionState(boolean z) {
        return updateWiredDeviceConnectionState(0, z);
    }

    public synchronized boolean updateOutputWiredDeviceConnectionState(boolean z) {
        return updateWiredDeviceConnectionState(1, z);
    }

    public synchronized java.lang.String toString() {
        return "UsbAlsaDevice: [card: " + this.mCardNum + ", device: " + this.mDeviceNum + ", name: " + this.mDeviceName + ", hasOutput: " + this.mHasDevice[1] + ", hasInput: " + this.mHasDevice[0] + "]";
    }

    public synchronized void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("card", 1120986464257L, this.mCardNum);
        dualDumpOutputStream.write("device", 1120986464258L, this.mDeviceNum);
        dualDumpOutputStream.write("name", 1138166333443L, this.mDeviceName);
        dualDumpOutputStream.write("has_output", 1133871366148L, this.mHasDevice[1]);
        dualDumpOutputStream.write("has_input", 1133871366149L, this.mHasDevice[0]);
        dualDumpOutputStream.write("address", 1138166333446L, this.mDeviceAddress);
        dualDumpOutputStream.end(start);
    }

    synchronized java.lang.String toShortString() {
        return "[card:" + this.mCardNum + " device:" + this.mDeviceNum + " " + this.mDeviceName + "]";
    }

    synchronized java.lang.String getDeviceName() {
        return this.mDeviceName;
    }

    synchronized void setDeviceNameAndDescription(java.lang.String str, java.lang.String str2) {
        this.mDeviceName = str;
        this.mDeviceDescription = str2;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.server.usb.UsbAlsaDevice)) {
            return false;
        }
        com.android.server.usb.UsbAlsaDevice usbAlsaDevice = (com.android.server.usb.UsbAlsaDevice) obj;
        return this.mCardNum == usbAlsaDevice.mCardNum && this.mDeviceNum == usbAlsaDevice.mDeviceNum && java.util.Arrays.equals(this.mHasDevice, usbAlsaDevice.mHasDevice) && java.util.Arrays.equals(this.mIsHeadset, usbAlsaDevice.mIsHeadset) && this.mIsDock == usbAlsaDevice.mIsDock;
    }

    public int hashCode() {
        return ((((((((((((this.mCardNum + 31) * 31) + this.mDeviceNum) * 31) + (!this.mHasDevice[1] ? 1 : 0)) * 31) + (!this.mHasDevice[0] ? 1 : 0)) * 31) + (!this.mIsHeadset[0] ? 1 : 0)) * 31) + (!this.mIsHeadset[1] ? 1 : 0)) * 31) + (!this.mIsDock ? 1 : 0);
    }
}
