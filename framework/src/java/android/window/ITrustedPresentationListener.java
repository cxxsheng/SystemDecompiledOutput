package android.window;

/* loaded from: classes4.dex */
public interface ITrustedPresentationListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.ITrustedPresentationListener";

    void onTrustedPresentationChanged(int[] iArr, int[] iArr2) throws android.os.RemoteException;

    public static class Default implements android.window.ITrustedPresentationListener {
        @Override // android.window.ITrustedPresentationListener
        public void onTrustedPresentationChanged(int[] iArr, int[] iArr2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.ITrustedPresentationListener {
        static final int TRANSACTION_onTrustedPresentationChanged = 1;

        public Stub() {
            attachInterface(this, android.window.ITrustedPresentationListener.DESCRIPTOR);
        }

        public static android.window.ITrustedPresentationListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.ITrustedPresentationListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.ITrustedPresentationListener)) {
                return (android.window.ITrustedPresentationListener) queryLocalInterface;
            }
            return new android.window.ITrustedPresentationListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTrustedPresentationChanged";
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
                parcel.enforceInterface(android.window.ITrustedPresentationListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.ITrustedPresentationListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onTrustedPresentationChanged(createIntArray, createIntArray2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.ITrustedPresentationListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.ITrustedPresentationListener.DESCRIPTOR;
            }

            @Override // android.window.ITrustedPresentationListener
            public void onTrustedPresentationChanged(int[] iArr, int[] iArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITrustedPresentationListener.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
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