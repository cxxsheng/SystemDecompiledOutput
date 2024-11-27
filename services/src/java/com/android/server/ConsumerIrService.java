package com.android.server;

/* loaded from: classes.dex */
public class ConsumerIrService extends android.hardware.IConsumerIrService.Stub {
    private static final int MAX_XMIT_TIME = 2000000;
    private static final java.lang.String TAG = "ConsumerIrService";
    private final android.content.Context mContext;
    private final boolean mHasNativeHal;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private final java.lang.Object mHalLock = new java.lang.Object();
    private android.hardware.ir.IConsumerIr mAidlService = null;

    private static native boolean getHidlHalService();

    private static native int[] halGetCarrierFrequencies();

    private static native int halTransmit(int i, int[] iArr);

    ConsumerIrService(android.content.Context context) {
        this.mContext = context;
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService("power")).newWakeLock(1, TAG);
        this.mWakeLock.setReferenceCounted(true);
        this.mHasNativeHal = getHalService();
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.consumerir")) {
            if (!this.mHasNativeHal) {
                throw new java.lang.RuntimeException("FEATURE_CONSUMER_IR present, but no IR HAL loaded!");
            }
        } else if (this.mHasNativeHal) {
            throw new java.lang.RuntimeException("IR HAL present, but FEATURE_CONSUMER_IR is not set!");
        }
    }

    @android.annotation.RequiresNoPermission
    public boolean hasIrEmitter() {
        return this.mHasNativeHal;
    }

    private boolean getHalService() {
        this.mAidlService = android.hardware.ir.IConsumerIr.Stub.asInterface(android.os.ServiceManager.waitForDeclaredService(android.hardware.ir.IConsumerIr.DESCRIPTOR + "/default"));
        if (this.mAidlService != null) {
            return true;
        }
        return getHidlHalService();
    }

    private void throwIfNoIrEmitter() {
        if (!this.mHasNativeHal) {
            throw new java.lang.UnsupportedOperationException("IR emitter not available");
        }
    }

    @android.annotation.EnforcePermission("android.permission.TRANSMIT_IR")
    public void transmit(java.lang.String str, int i, int[] iArr) {
        super.transmit_enforcePermission();
        long j = 0;
        for (int i2 : iArr) {
            if (i2 <= 0) {
                throw new java.lang.IllegalArgumentException("Non-positive IR slice");
            }
            j += i2;
        }
        if (j > 2000000) {
            throw new java.lang.IllegalArgumentException("IR pattern too long");
        }
        throwIfNoIrEmitter();
        synchronized (this.mHalLock) {
            if (this.mAidlService != null) {
                try {
                    this.mAidlService.transmit(i, iArr);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Error transmitting frequency: " + i);
                }
            } else {
                int halTransmit = halTransmit(i, iArr);
                if (halTransmit < 0) {
                    android.util.Slog.e(TAG, "Error transmitting: " + halTransmit);
                }
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.TRANSMIT_IR")
    public int[] getCarrierFrequencies() {
        super.getCarrierFrequencies_enforcePermission();
        throwIfNoIrEmitter();
        synchronized (this.mHalLock) {
            try {
                if (this.mAidlService != null) {
                    try {
                        android.hardware.ir.ConsumerIrFreqRange[] carrierFreqs = this.mAidlService.getCarrierFreqs();
                        if (carrierFreqs.length <= 0) {
                            android.util.Slog.e(TAG, "Error getting carrier frequencies.");
                        }
                        int[] iArr = new int[carrierFreqs.length * 2];
                        for (int i = 0; i < carrierFreqs.length; i++) {
                            int i2 = i * 2;
                            iArr[i2] = carrierFreqs[i].minHz;
                            iArr[i2 + 1] = carrierFreqs[i].maxHz;
                        }
                        return iArr;
                    } catch (android.os.RemoteException e) {
                        return null;
                    }
                }
                return halGetCarrierFrequencies();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
