package android.hardware;

/* loaded from: classes.dex */
public final class ConsumerIrManager {
    private static final java.lang.String TAG = "ConsumerIr";
    private final java.lang.String mPackageName;
    private final android.hardware.IConsumerIrService mService = android.hardware.IConsumerIrService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.CONSUMER_IR_SERVICE));

    public ConsumerIrManager(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mPackageName = context.getPackageName();
    }

    public boolean hasIrEmitter() {
        if (this.mService == null) {
            android.util.Log.w(TAG, "no consumer ir service.");
            return false;
        }
        try {
            return this.mService.hasIrEmitter();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void transmit(int i, int[] iArr) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "failed to transmit; no consumer ir service.");
            return;
        }
        try {
            this.mService.transmit(this.mPackageName, i, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final class CarrierFrequencyRange {
        private final int mMaxFrequency;
        private final int mMinFrequency;

        public CarrierFrequencyRange(int i, int i2) {
            this.mMinFrequency = i;
            this.mMaxFrequency = i2;
        }

        public int getMinFrequency() {
            return this.mMinFrequency;
        }

        public int getMaxFrequency() {
            return this.mMaxFrequency;
        }
    }

    public android.hardware.ConsumerIrManager.CarrierFrequencyRange[] getCarrierFrequencies() {
        if (this.mService == null) {
            android.util.Log.w(TAG, "no consumer ir service.");
            return null;
        }
        try {
            int[] carrierFrequencies = this.mService.getCarrierFrequencies();
            if (carrierFrequencies.length % 2 != 0) {
                android.util.Log.w(TAG, "consumer ir service returned an uneven number of frequencies.");
                return null;
            }
            android.hardware.ConsumerIrManager.CarrierFrequencyRange[] carrierFrequencyRangeArr = new android.hardware.ConsumerIrManager.CarrierFrequencyRange[carrierFrequencies.length / 2];
            for (int i = 0; i < carrierFrequencies.length; i += 2) {
                carrierFrequencyRangeArr[i / 2] = new android.hardware.ConsumerIrManager.CarrierFrequencyRange(carrierFrequencies[i], carrierFrequencies[i + 1]);
            }
            return carrierFrequencyRangeArr;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
