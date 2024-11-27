package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class CellBroadcastService extends android.app.Service {
    public static final java.lang.String CELL_BROADCAST_SERVICE_INTERFACE = "android.telephony.CellBroadcastService";
    private final android.telephony.ICellBroadcastService.Stub mStubWrapper = new android.telephony.CellBroadcastService.ICellBroadcastServiceWrapper();

    public abstract java.lang.CharSequence getCellBroadcastAreaInfo(int i);

    public abstract void onCdmaCellBroadcastSms(int i, byte[] bArr, int i2);

    public abstract void onCdmaScpMessage(int i, java.util.List<android.telephony.cdma.CdmaSmsCbProgramData> list, java.lang.String str, java.util.function.Consumer<android.os.Bundle> consumer);

    public abstract void onGsmCellBroadcastSms(int i, byte[] bArr);

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mStubWrapper;
    }

    public class ICellBroadcastServiceWrapper extends android.telephony.ICellBroadcastService.Stub {
        public ICellBroadcastServiceWrapper() {
        }

        @Override // android.telephony.ICellBroadcastService
        public void handleGsmCellBroadcastSms(int i, byte[] bArr) {
            android.telephony.CellBroadcastService.this.onGsmCellBroadcastSms(i, bArr);
        }

        @Override // android.telephony.ICellBroadcastService
        public void handleCdmaCellBroadcastSms(int i, byte[] bArr, int i2) {
            android.telephony.CellBroadcastService.this.onCdmaCellBroadcastSms(i, bArr, i2);
        }

        @Override // android.telephony.ICellBroadcastService
        public void handleCdmaScpMessage(int i, java.util.List<android.telephony.cdma.CdmaSmsCbProgramData> list, java.lang.String str, final android.os.RemoteCallback remoteCallback) {
            android.telephony.CellBroadcastService.this.onCdmaScpMessage(i, list, str, new java.util.function.Consumer() { // from class: android.telephony.CellBroadcastService$ICellBroadcastServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.os.RemoteCallback.this.sendResult((android.os.Bundle) obj);
                }
            });
        }

        @Override // android.telephony.ICellBroadcastService
        public java.lang.CharSequence getCellBroadcastAreaInfo(int i) {
            return android.telephony.CellBroadcastService.this.getCellBroadcastAreaInfo(i);
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            android.telephony.CellBroadcastService.this.dump(fileDescriptor, printWriter, strArr);
        }

        @Override // android.os.Binder, android.os.IBinder
        public void dump(java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) {
            android.telephony.CellBroadcastService.this.dump(fileDescriptor, new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(fileDescriptor)), strArr);
        }
    }
}
