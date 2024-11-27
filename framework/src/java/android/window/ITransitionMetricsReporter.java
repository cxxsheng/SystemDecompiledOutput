package android.window;

/* loaded from: classes4.dex */
public interface ITransitionMetricsReporter extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.ITransitionMetricsReporter";

    void reportAnimationStart(android.os.IBinder iBinder, long j) throws android.os.RemoteException;

    public static class Default implements android.window.ITransitionMetricsReporter {
        @Override // android.window.ITransitionMetricsReporter
        public void reportAnimationStart(android.os.IBinder iBinder, long j) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.ITransitionMetricsReporter {
        static final int TRANSACTION_reportAnimationStart = 1;

        public Stub() {
            attachInterface(this, android.window.ITransitionMetricsReporter.DESCRIPTOR);
        }

        public static android.window.ITransitionMetricsReporter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.ITransitionMetricsReporter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.ITransitionMetricsReporter)) {
                return (android.window.ITransitionMetricsReporter) queryLocalInterface;
            }
            return new android.window.ITransitionMetricsReporter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reportAnimationStart";
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
                parcel.enforceInterface(android.window.ITransitionMetricsReporter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.ITransitionMetricsReporter.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    reportAnimationStart(readStrongBinder, readLong);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.ITransitionMetricsReporter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.ITransitionMetricsReporter.DESCRIPTOR;
            }

            @Override // android.window.ITransitionMetricsReporter
            public void reportAnimationStart(android.os.IBinder iBinder, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITransitionMetricsReporter.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
