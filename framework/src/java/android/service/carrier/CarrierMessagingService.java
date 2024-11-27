package android.service.carrier;

/* loaded from: classes3.dex */
public abstract class CarrierMessagingService extends android.app.Service {
    public static final int DOWNLOAD_STATUS_ERROR = 2;
    public static final int DOWNLOAD_STATUS_OK = 0;
    public static final int DOWNLOAD_STATUS_RETRY_ON_CARRIER_NETWORK = 1;
    public static final int RECEIVE_OPTIONS_DEFAULT = 0;
    public static final int RECEIVE_OPTIONS_DROP = 1;
    public static final int RECEIVE_OPTIONS_SKIP_NOTIFY_WHEN_CREDENTIAL_PROTECTED_STORAGE_UNAVAILABLE = 2;
    public static final int SEND_FLAG_REQUEST_DELIVERY_STATUS = 1;
    public static final int SEND_STATUS_ERROR = 2;
    public static final int SEND_STATUS_OK = 0;
    public static final int SEND_STATUS_RETRY_ON_CARRIER_NETWORK = 1;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.carrier.CarrierMessagingService";
    private final android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper mWrapper = new android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DownloadResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FilterCompleteResult {
    }

    public interface ResultCallback<T> {
        void onReceiveResult(T t) throws android.os.RemoteException;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SendRequest {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SendResult {
    }

    @java.lang.Deprecated
    public void onFilterSms(android.service.carrier.MessagePdu messagePdu, java.lang.String str, int i, int i2, android.service.carrier.CarrierMessagingService.ResultCallback<java.lang.Boolean> resultCallback) {
        try {
            resultCallback.onReceiveResult(true);
        } catch (android.os.RemoteException e) {
        }
    }

    public void onReceiveTextSms(android.service.carrier.MessagePdu messagePdu, java.lang.String str, int i, int i2, final android.service.carrier.CarrierMessagingService.ResultCallback<java.lang.Integer> resultCallback) {
        onFilterSms(messagePdu, str, i, i2, new android.service.carrier.CarrierMessagingService.ResultCallback<java.lang.Boolean>() { // from class: android.service.carrier.CarrierMessagingService.1
            @Override // android.service.carrier.CarrierMessagingService.ResultCallback
            public void onReceiveResult(java.lang.Boolean bool) throws android.os.RemoteException {
                resultCallback.onReceiveResult(java.lang.Integer.valueOf(bool.booleanValue() ? 0 : 3));
            }
        });
    }

    @java.lang.Deprecated
    public void onSendTextSms(java.lang.String str, int i, java.lang.String str2, android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendSmsResult> resultCallback) {
        try {
            resultCallback.onReceiveResult(new android.service.carrier.CarrierMessagingService.SendSmsResult(1, 0));
        } catch (android.os.RemoteException e) {
        }
    }

    public void onSendTextSms(java.lang.String str, int i, java.lang.String str2, int i2, android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendSmsResult> resultCallback) {
        onSendTextSms(str, i, str2, resultCallback);
    }

    @java.lang.Deprecated
    public void onSendDataSms(byte[] bArr, int i, java.lang.String str, int i2, android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendSmsResult> resultCallback) {
        try {
            resultCallback.onReceiveResult(new android.service.carrier.CarrierMessagingService.SendSmsResult(1, 0));
        } catch (android.os.RemoteException e) {
        }
    }

    public void onSendDataSms(byte[] bArr, int i, java.lang.String str, int i2, int i3, android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendSmsResult> resultCallback) {
        onSendDataSms(bArr, i, str, i2, resultCallback);
    }

    @java.lang.Deprecated
    public void onSendMultipartTextSms(java.util.List<java.lang.String> list, int i, java.lang.String str, android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendMultipartSmsResult> resultCallback) {
        try {
            resultCallback.onReceiveResult(new android.service.carrier.CarrierMessagingService.SendMultipartSmsResult(1, null));
        } catch (android.os.RemoteException e) {
        }
    }

    public void onSendMultipartTextSms(java.util.List<java.lang.String> list, int i, java.lang.String str, int i2, android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendMultipartSmsResult> resultCallback) {
        onSendMultipartTextSms(list, i, str, resultCallback);
    }

    public void onSendMms(android.net.Uri uri, int i, android.net.Uri uri2, android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendMmsResult> resultCallback) {
        try {
            resultCallback.onReceiveResult(new android.service.carrier.CarrierMessagingService.SendMmsResult(1, null));
        } catch (android.os.RemoteException e) {
        }
    }

    public void onDownloadMms(android.net.Uri uri, int i, android.net.Uri uri2, android.service.carrier.CarrierMessagingService.ResultCallback<java.lang.Integer> resultCallback) {
        try {
            resultCallback.onReceiveResult(1);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        return this.mWrapper;
    }

    public static final class SendMmsResult {
        private byte[] mSendConfPdu;
        private int mSendStatus;

        public SendMmsResult(int i, byte[] bArr) {
            this.mSendStatus = i;
            this.mSendConfPdu = bArr;
        }

        public int getSendStatus() {
            return this.mSendStatus;
        }

        public byte[] getSendConfPdu() {
            return this.mSendConfPdu;
        }
    }

    public static final class SendSmsResult {
        private final int mMessageRef;
        private final int mSendStatus;

        public SendSmsResult(int i, int i2) {
            this.mSendStatus = i;
            this.mMessageRef = i2;
        }

        public int getMessageRef() {
            return this.mMessageRef;
        }

        public int getSendStatus() {
            return this.mSendStatus;
        }
    }

    public static final class SendMultipartSmsResult {
        private final int[] mMessageRefs;
        private final int mSendStatus;

        public SendMultipartSmsResult(int i, int[] iArr) {
            this.mSendStatus = i;
            this.mMessageRefs = iArr;
        }

        public int[] getMessageRefs() {
            return this.mMessageRefs;
        }

        public int getSendStatus() {
            return this.mSendStatus;
        }
    }

    private class ICarrierMessagingWrapper extends android.service.carrier.ICarrierMessagingService.Stub {
        private ICarrierMessagingWrapper() {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void filterSms(android.service.carrier.MessagePdu messagePdu, java.lang.String str, int i, int i2, final android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) {
            android.service.carrier.CarrierMessagingService.this.onReceiveTextSms(messagePdu, str, i, i2, new android.service.carrier.CarrierMessagingService.ResultCallback<java.lang.Integer>() { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.1
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(java.lang.Integer num) throws android.os.RemoteException {
                    iCarrierMessagingCallback.onFilterComplete(num.intValue());
                }
            });
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendTextSms(java.lang.String str, int i, java.lang.String str2, int i2, final android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) {
            android.service.carrier.CarrierMessagingService.this.onSendTextSms(str, i, str2, i2, new android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendSmsResult>() { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.2
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(android.service.carrier.CarrierMessagingService.SendSmsResult sendSmsResult) throws android.os.RemoteException {
                    iCarrierMessagingCallback.onSendSmsComplete(sendSmsResult.getSendStatus(), sendSmsResult.getMessageRef());
                }
            });
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendDataSms(byte[] bArr, int i, java.lang.String str, int i2, int i3, final android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) {
            android.service.carrier.CarrierMessagingService.this.onSendDataSms(bArr, i, str, i2, i3, new android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendSmsResult>() { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.3
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(android.service.carrier.CarrierMessagingService.SendSmsResult sendSmsResult) throws android.os.RemoteException {
                    iCarrierMessagingCallback.onSendSmsComplete(sendSmsResult.getSendStatus(), sendSmsResult.getMessageRef());
                }
            });
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendMultipartTextSms(java.util.List<java.lang.String> list, int i, java.lang.String str, int i2, final android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) {
            android.service.carrier.CarrierMessagingService.this.onSendMultipartTextSms(list, i, str, i2, new android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendMultipartSmsResult>() { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.4
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(android.service.carrier.CarrierMessagingService.SendMultipartSmsResult sendMultipartSmsResult) throws android.os.RemoteException {
                    iCarrierMessagingCallback.onSendMultipartSmsComplete(sendMultipartSmsResult.getSendStatus(), sendMultipartSmsResult.getMessageRefs());
                }
            });
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendMms(android.net.Uri uri, int i, android.net.Uri uri2, final android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) {
            android.service.carrier.CarrierMessagingService.this.onSendMms(uri, i, uri2, new android.service.carrier.CarrierMessagingService.ResultCallback<android.service.carrier.CarrierMessagingService.SendMmsResult>() { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.5
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(android.service.carrier.CarrierMessagingService.SendMmsResult sendMmsResult) throws android.os.RemoteException {
                    iCarrierMessagingCallback.onSendMmsComplete(sendMmsResult.getSendStatus(), sendMmsResult.getSendConfPdu());
                }
            });
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void downloadMms(android.net.Uri uri, int i, android.net.Uri uri2, final android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) {
            android.service.carrier.CarrierMessagingService.this.onDownloadMms(uri, i, uri2, new android.service.carrier.CarrierMessagingService.ResultCallback<java.lang.Integer>() { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.6
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(java.lang.Integer num) throws android.os.RemoteException {
                    iCarrierMessagingCallback.onDownloadMmsComplete(num.intValue());
                }
            });
        }
    }
}
