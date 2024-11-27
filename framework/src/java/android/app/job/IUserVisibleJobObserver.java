package android.app.job;

/* loaded from: classes.dex */
public interface IUserVisibleJobObserver extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.job.IUserVisibleJobObserver";

    void onUserVisibleJobStateChanged(android.app.job.UserVisibleJobSummary userVisibleJobSummary, boolean z) throws android.os.RemoteException;

    public static class Default implements android.app.job.IUserVisibleJobObserver {
        @Override // android.app.job.IUserVisibleJobObserver
        public void onUserVisibleJobStateChanged(android.app.job.UserVisibleJobSummary userVisibleJobSummary, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.job.IUserVisibleJobObserver {
        static final int TRANSACTION_onUserVisibleJobStateChanged = 1;

        public Stub() {
            attachInterface(this, android.app.job.IUserVisibleJobObserver.DESCRIPTOR);
        }

        public static android.app.job.IUserVisibleJobObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.job.IUserVisibleJobObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.job.IUserVisibleJobObserver)) {
                return (android.app.job.IUserVisibleJobObserver) queryLocalInterface;
            }
            return new android.app.job.IUserVisibleJobObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUserVisibleJobStateChanged";
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
                parcel.enforceInterface(android.app.job.IUserVisibleJobObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.job.IUserVisibleJobObserver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.job.UserVisibleJobSummary userVisibleJobSummary = (android.app.job.UserVisibleJobSummary) parcel.readTypedObject(android.app.job.UserVisibleJobSummary.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUserVisibleJobStateChanged(userVisibleJobSummary, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.job.IUserVisibleJobObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.job.IUserVisibleJobObserver.DESCRIPTOR;
            }

            @Override // android.app.job.IUserVisibleJobObserver
            public void onUserVisibleJobStateChanged(android.app.job.UserVisibleJobSummary userVisibleJobSummary, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.job.IUserVisibleJobObserver.DESCRIPTOR);
                    obtain.writeTypedObject(userVisibleJobSummary, 0);
                    obtain.writeBoolean(z);
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
