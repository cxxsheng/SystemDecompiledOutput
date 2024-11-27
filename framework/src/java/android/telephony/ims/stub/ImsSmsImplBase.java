package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class ImsSmsImplBase {
    public static final int DELIVER_STATUS_ERROR_GENERIC = 2;
    public static final int DELIVER_STATUS_ERROR_NO_MEMORY = 3;
    public static final int DELIVER_STATUS_ERROR_REQUEST_NOT_SUPPORTED = 4;
    public static final int DELIVER_STATUS_OK = 1;
    private static final java.lang.String LOG_TAG = "SmsImplBase";
    public static final int RESULT_NO_NETWORK_ERROR = -1;
    public static final int SEND_STATUS_ERROR = 2;
    public static final int SEND_STATUS_ERROR_FALLBACK = 4;
    public static final int SEND_STATUS_ERROR_RETRY = 3;
    public static final int SEND_STATUS_OK = 1;
    public static final int STATUS_REPORT_STATUS_ERROR = 2;
    public static final int STATUS_REPORT_STATUS_OK = 1;
    private java.util.concurrent.Executor mExecutor;
    private android.telephony.ims.aidl.IImsSmsListener mListener;
    private final java.lang.Object mLock = new java.lang.Object();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeliverStatusResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SendStatusResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StatusReportResult {
    }

    public ImsSmsImplBase() {
    }

    public ImsSmsImplBase(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }

    public final void registerSmsListener(android.telephony.ims.aidl.IImsSmsListener iImsSmsListener) {
        synchronized (this.mLock) {
            this.mListener = iImsSmsListener;
        }
    }

    public void sendSms(int i, int i2, java.lang.String str, java.lang.String str2, boolean z, byte[] bArr) {
        try {
            onSendSmsResult(i, i2, 2, 1);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(LOG_TAG, "Can not send sms: " + e.getMessage());
        }
    }

    public void onMemoryAvailable(int i) {
    }

    public void acknowledgeSms(int i, int i2, int i3) {
        android.util.Log.e(LOG_TAG, "acknowledgeSms() not implemented.");
    }

    public void acknowledgeSms(int i, int i2, int i3, byte[] bArr) {
        android.util.Log.e(LOG_TAG, "acknowledgeSms() not implemented. acknowledgeSms(int, int, int) called.");
        acknowledgeSms(i, i2, i3);
    }

    public void acknowledgeSmsReport(int i, int i2, int i3) {
        android.util.Log.e(LOG_TAG, "acknowledgeSmsReport() not implemented.");
    }

    public final void onSmsReceived(int i, java.lang.String str, byte[] bArr) throws java.lang.RuntimeException {
        android.telephony.ims.aidl.IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new java.lang.RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSmsReceived(i, str, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Can not deliver sms: " + e.getMessage());
            android.telephony.SmsMessage createFromPdu = android.telephony.SmsMessage.createFromPdu(bArr, str);
            if (createFromPdu != null && createFromPdu.mWrappedSmsMessage != null) {
                acknowledgeSms(i, createFromPdu.mWrappedSmsMessage.mMessageRef, 2);
            } else {
                android.util.Log.w(LOG_TAG, "onSmsReceived: Invalid pdu entered.");
                acknowledgeSms(i, 0, 2);
            }
        }
    }

    public final void onSendSmsResultSuccess(int i, int i2) throws java.lang.RuntimeException {
        android.telephony.ims.aidl.IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new java.lang.RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSendSmsResult(i, i2, 1, 0, -1);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public final void onSendSmsResult(int i, int i2, int i3, int i4) throws java.lang.RuntimeException {
        android.telephony.ims.aidl.IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new java.lang.RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSendSmsResult(i, i2, i3, i4, -1);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void onSendSmsResultError(int i, int i2, int i3, int i4, int i5) throws java.lang.RuntimeException {
        android.telephony.ims.aidl.IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new java.lang.RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSendSmsResult(i, i2, i3, i4, i5);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void onMemoryAvailableResult(int i, int i2, int i3) throws java.lang.RuntimeException {
        android.telephony.ims.aidl.IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new java.lang.RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onMemoryAvailableResult(i, i2, i3);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public final void onSmsStatusReportReceived(int i, int i2, java.lang.String str, byte[] bArr) throws java.lang.RuntimeException {
        android.telephony.ims.aidl.IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new java.lang.RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSmsStatusReportReceived(i, str, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Can not process sms status report: " + e.getMessage());
            acknowledgeSmsReport(i, i2, 2);
        }
    }

    public final void onSmsStatusReportReceived(int i, java.lang.String str, byte[] bArr) throws java.lang.RuntimeException {
        android.telephony.ims.aidl.IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new java.lang.RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSmsStatusReportReceived(i, str, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Can not process sms status report: " + e.getMessage());
            android.telephony.SmsMessage createFromPdu = android.telephony.SmsMessage.createFromPdu(bArr, str);
            if (createFromPdu != null && createFromPdu.mWrappedSmsMessage != null) {
                acknowledgeSmsReport(i, createFromPdu.mWrappedSmsMessage.mMessageRef, 2);
            } else {
                android.util.Log.w(LOG_TAG, "onSmsStatusReportReceived: Invalid pdu entered.");
                acknowledgeSmsReport(i, 0, 2);
            }
        }
    }

    public java.lang.String getSmsFormat() {
        return "3gpp";
    }

    public void onReady() {
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        if (this.mExecutor == null) {
            this.mExecutor = executor;
        }
    }

    public java.util.concurrent.Executor getExecutor() {
        return this.mExecutor != null ? this.mExecutor : new android.app.PendingIntent$$ExternalSyntheticLambda0();
    }
}
