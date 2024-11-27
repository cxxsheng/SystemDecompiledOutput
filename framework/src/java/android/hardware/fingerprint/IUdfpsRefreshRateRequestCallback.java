package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public interface IUdfpsRefreshRateRequestCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback";

    void onAuthenticationPossible(int i, boolean z) throws android.os.RemoteException;

    void onRequestDisabled(int i) throws android.os.RemoteException;

    void onRequestEnabled(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback {
        @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
        public void onRequestEnabled(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
        public void onRequestDisabled(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
        public void onAuthenticationPossible(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback {
        static final int TRANSACTION_onAuthenticationPossible = 3;
        static final int TRANSACTION_onRequestDisabled = 2;
        static final int TRANSACTION_onRequestEnabled = 1;

        public Stub() {
            attachInterface(this, android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
        }

        public static android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback)) {
                return (android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback) queryLocalInterface;
            }
            return new android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRequestEnabled";
                case 2:
                    return "onRequestDisabled";
                case 3:
                    return "onAuthenticationPossible";
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
                parcel.enforceInterface(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestEnabled(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestDisabled(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAuthenticationPossible(readInt3, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
            public void onRequestEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
            public void onRequestDisabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
            public void onAuthenticationPossible(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
