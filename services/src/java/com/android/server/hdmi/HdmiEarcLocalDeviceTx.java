package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class HdmiEarcLocalDeviceTx extends com.android.server.hdmi.HdmiEarcLocalDevice {
    private static final int EARC_CAPS_DATA_START = 3;
    private static final int EARC_CAPS_LENGTH_MASK = 31;
    private static final int EARC_CAPS_PAYLOAD_LENGTH = 2;
    private static final int EARC_CAPS_TAGCODE_MASK = 224;
    private static final int EARC_CAPS_TAGCODE_SHIFT = 5;
    private static final int EXTENDED_TAGCODE_VSADB = 17;
    static final long REPORT_CAPS_MAX_DELAY_MS = 2000;
    private static final java.lang.String TAG = "HdmiEarcLocalDeviceTx";
    private static final int TAGCODE_AUDIO_DATA_BLOCK = 1;
    private static final int TAGCODE_SADB_DATA_BLOCK = 4;
    private static final int TAGCODE_USE_EXTENDED_TAG = 7;
    private static final java.lang.String[] earcStatusNames = {"HDMI_EARC_STATUS_IDLE", "HDMI_EARC_STATUS_EARC_PENDING", "HDMI_EARC_STATUS_ARC_PENDING", "HDMI_EARC_STATUS_EARC_CONNECTED"};
    private android.os.Handler mReportCapsHandler;
    private com.android.server.hdmi.HdmiEarcLocalDeviceTx.ReportCapsRunnable mReportCapsRunnable;

    HdmiEarcLocalDeviceTx(com.android.server.hdmi.HdmiControlService hdmiControlService) {
        super(hdmiControlService, 0);
        synchronized (this.mLock) {
            this.mEarcStatus = 1;
        }
        this.mReportCapsHandler = new android.os.Handler(hdmiControlService.getServiceLooper());
        this.mReportCapsRunnable = new com.android.server.hdmi.HdmiEarcLocalDeviceTx.ReportCapsRunnable();
    }

    private java.lang.String earcStatusToString(int i) {
        return earcStatusNames[i];
    }

    @Override // com.android.server.hdmi.HdmiEarcLocalDevice
    protected void handleEarcStateChange(@com.android.server.hdmi.Constants.EarcStatus int i) {
        int i2;
        synchronized (this.mLock) {
            com.android.server.hdmi.HdmiLogger.debug("eARC state change [old: %s(%d) new: %s(%d)]", earcStatusToString(this.mEarcStatus), java.lang.Integer.valueOf(this.mEarcStatus), earcStatusToString(i), java.lang.Integer.valueOf(i));
            i2 = this.mEarcStatus;
            this.mEarcStatus = i;
        }
        this.mReportCapsHandler.removeCallbacksAndMessages(null);
        if (i == 0) {
            this.mService.notifyEarcStatusToAudioService(false, new java.util.ArrayList());
            this.mService.startArcAction(false, null);
            return;
        }
        if (i == 2) {
            this.mService.notifyEarcStatusToAudioService(false, new java.util.ArrayList());
            this.mService.startArcAction(true, null);
        } else if (i == 1 && i2 == 2) {
            this.mService.startArcAction(false, null);
        } else if (i == 3) {
            if (i2 == 2) {
                this.mService.startArcAction(false, null);
            }
            this.mReportCapsHandler.postDelayed(this.mReportCapsRunnable, REPORT_CAPS_MAX_DELAY_MS);
        }
    }

    @Override // com.android.server.hdmi.HdmiEarcLocalDevice
    protected void handleEarcCapabilitiesReported(byte[] bArr) {
        synchronized (this.mLock) {
            try {
                if (this.mEarcStatus == 3 && this.mReportCapsHandler.hasCallbacks(this.mReportCapsRunnable)) {
                    this.mReportCapsHandler.removeCallbacksAndMessages(null);
                    this.mService.notifyEarcStatusToAudioService(true, parseCapabilities(bArr));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class ReportCapsRunnable implements java.lang.Runnable {
        private ReportCapsRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.hdmi.HdmiEarcLocalDeviceTx.this.mLock) {
                try {
                    if (com.android.server.hdmi.HdmiEarcLocalDeviceTx.this.mEarcStatus == 3) {
                        com.android.server.hdmi.HdmiEarcLocalDeviceTx.this.mService.notifyEarcStatusToAudioService(true, new java.util.ArrayList());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private java.util.List<android.media.AudioDescriptor> parseCapabilities(byte[] bArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (bArr.length < 4) {
            android.util.Slog.i(TAG, "Raw eARC capabilities array doesn´t contain any blocks.");
            return arrayList;
        }
        int i = bArr[2];
        if (bArr.length < i) {
            android.util.Slog.i(TAG, "Raw eARC capabilities array is shorter than the reported payload length.");
            return arrayList;
        }
        int i2 = 3;
        while (i2 < i) {
            int i3 = (bArr[i2] & 224) >> 5;
            int i4 = bArr[i2] & 31;
            if (i4 != 0) {
                switch (i3) {
                    case 1:
                        int i5 = i4 % 3;
                        if (i5 != 0) {
                            android.util.Slog.e(TAG, "Invalid length of SAD block: expected a factor of 3 but got " + i5);
                            break;
                        } else {
                            byte[] bArr2 = new byte[i4];
                            java.lang.System.arraycopy(bArr, i2 + 1, bArr2, 0, i4);
                            int i6 = 0;
                            while (i6 < i4) {
                                int i7 = i6 + 3;
                                arrayList.add(new android.media.AudioDescriptor(1, 0, java.util.Arrays.copyOfRange(bArr2, i6, i7)));
                                i6 = i7;
                            }
                            break;
                        }
                    case 4:
                        int i8 = i4 + 1;
                        byte[] bArr3 = new byte[i8];
                        java.lang.System.arraycopy(bArr, i2, bArr3, 0, i8);
                        arrayList.add(new android.media.AudioDescriptor(2, 0, bArr3));
                        break;
                    case 7:
                        if (bArr[i2 + 1] != 17) {
                            break;
                        } else {
                            int i9 = i4 + 1;
                            byte[] bArr4 = new byte[i9];
                            java.lang.System.arraycopy(bArr, i2, bArr4, 0, i9);
                            arrayList.add(new android.media.AudioDescriptor(3, 0, bArr4));
                            break;
                        }
                    default:
                        android.util.Slog.w(TAG, "This tagcode was not handled: " + i3);
                        break;
                }
                i2 += i4 + 1;
            } else {
                return arrayList;
            }
        }
        return arrayList;
    }

    @Override // com.android.server.hdmi.HdmiEarcLocalDevice
    protected void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.println("TX, mEarcStatus: " + this.mEarcStatus);
        }
    }
}
