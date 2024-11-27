package android.tracing;

/* loaded from: classes3.dex */
public interface ITracingServiceProxy extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.tracing.ITracingServiceProxy";

    void notifyTraceSessionEnded(boolean z) throws android.os.RemoteException;

    void reportTrace(android.tracing.TraceReportParams traceReportParams) throws android.os.RemoteException;

    public static class Default implements android.tracing.ITracingServiceProxy {
        @Override // android.tracing.ITracingServiceProxy
        public void notifyTraceSessionEnded(boolean z) throws android.os.RemoteException {
        }

        @Override // android.tracing.ITracingServiceProxy
        public void reportTrace(android.tracing.TraceReportParams traceReportParams) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.tracing.ITracingServiceProxy {
        static final int TRANSACTION_notifyTraceSessionEnded = 1;
        static final int TRANSACTION_reportTrace = 2;

        public Stub() {
            attachInterface(this, android.tracing.ITracingServiceProxy.DESCRIPTOR);
        }

        public static android.tracing.ITracingServiceProxy asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.tracing.ITracingServiceProxy.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.tracing.ITracingServiceProxy)) {
                return (android.tracing.ITracingServiceProxy) queryLocalInterface;
            }
            return new android.tracing.ITracingServiceProxy.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifyTraceSessionEnded";
                case 2:
                    return "reportTrace";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.tracing.ITracingServiceProxy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.tracing.ITracingServiceProxy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyTraceSessionEnded(readBoolean);
                    return true;
                case 2:
                    android.tracing.TraceReportParams traceReportParams = (android.tracing.TraceReportParams) parcel.readTypedObject(android.tracing.TraceReportParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportTrace(traceReportParams);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.tracing.ITracingServiceProxy {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.tracing.ITracingServiceProxy.DESCRIPTOR;
            }

            @Override // android.tracing.ITracingServiceProxy
            public void notifyTraceSessionEnded(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.tracing.ITracingServiceProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.tracing.ITracingServiceProxy
            public void reportTrace(android.tracing.TraceReportParams traceReportParams) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.tracing.ITracingServiceProxy.DESCRIPTOR);
                    obtain.writeTypedObject(traceReportParams, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
