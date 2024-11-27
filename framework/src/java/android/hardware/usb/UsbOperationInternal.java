package android.hardware.usb;

/* loaded from: classes2.dex */
public final class UsbOperationInternal extends android.hardware.usb.IUsbOperationInternal.Stub {
    private static final java.lang.String TAG = "UsbPortStatus";
    public static final int USB_OPERATION_ERROR_INTERNAL = 1;
    public static final int USB_OPERATION_ERROR_NOT_SUPPORTED = 2;
    public static final int USB_OPERATION_ERROR_PORT_MISMATCH = 3;
    public static final int USB_OPERATION_SUCCESS = 0;
    private static final int USB_OPERATION_TIMEOUT_MSECS = 5000;
    private boolean mAsynchronous;
    private java.util.function.Consumer<java.lang.Integer> mConsumer;
    private java.util.concurrent.Executor mExecutor;
    private final java.lang.String mId;
    final java.util.concurrent.locks.ReentrantLock mLock;
    private boolean mOperationComplete;
    private final int mOperationID;
    final java.util.concurrent.locks.Condition mOperationWait;
    private int mResult;
    private int mStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface UsbOperationStatus {
    }

    UsbOperationInternal(int i, java.lang.String str, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        this.mAsynchronous = false;
        this.mResult = 0;
        this.mLock = new java.util.concurrent.locks.ReentrantLock();
        this.mOperationWait = this.mLock.newCondition();
        this.mOperationID = i;
        this.mId = str;
        this.mExecutor = executor;
        this.mConsumer = consumer;
        this.mAsynchronous = true;
    }

    UsbOperationInternal(int i, java.lang.String str) {
        this.mAsynchronous = false;
        this.mResult = 0;
        this.mLock = new java.util.concurrent.locks.ReentrantLock();
        this.mOperationWait = this.mLock.newCondition();
        this.mOperationID = i;
        this.mId = str;
    }

    @Override // android.hardware.usb.IUsbOperationInternal
    public void onOperationComplete(int i) {
        this.mLock.lock();
        try {
            this.mOperationComplete = true;
            this.mStatus = i;
            android.util.Log.i(TAG, "Port:" + this.mId + " opID:" + this.mOperationID + " status:" + this.mStatus);
            if (this.mAsynchronous) {
                switch (this.mStatus) {
                    case 0:
                        this.mResult = 0;
                        break;
                    case 1:
                        this.mResult = 1;
                        break;
                    case 2:
                        this.mResult = 2;
                        break;
                    case 3:
                        this.mResult = 3;
                        break;
                    default:
                        this.mResult = 4;
                        break;
                }
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.usb.UsbOperationInternal$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.usb.UsbOperationInternal.this.lambda$onOperationComplete$0();
                    }
                });
            } else {
                this.mOperationWait.signal();
            }
        } finally {
            this.mLock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onOperationComplete$0() {
        this.mConsumer.accept(java.lang.Integer.valueOf(this.mResult));
    }

    public void waitForOperationComplete() {
        this.mLock.lock();
        try {
            try {
                long currentTimeMillis = java.lang.System.currentTimeMillis() + 5000;
                do {
                    this.mOperationWait.await(currentTimeMillis - java.lang.System.currentTimeMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
                    if (this.mOperationComplete) {
                        break;
                    }
                } while (java.lang.System.currentTimeMillis() < currentTimeMillis);
                if (!this.mOperationComplete) {
                    android.util.Log.e(TAG, "Port:" + this.mId + " opID:" + this.mOperationID + " operationComplete not received in 5000msecs");
                }
            } catch (java.lang.InterruptedException e) {
                android.util.Log.e(TAG, "Port:" + this.mId + " opID:" + this.mOperationID + " operationComplete interrupted");
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public int getStatus() {
        if (this.mOperationComplete) {
            return this.mStatus;
        }
        return 1;
    }
}
