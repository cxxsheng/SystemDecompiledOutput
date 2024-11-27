package android.view;

/* loaded from: classes4.dex */
public interface ICrossWindowBlurEnabledListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.ICrossWindowBlurEnabledListener";

    void onCrossWindowBlurEnabledChanged(boolean z) throws android.os.RemoteException;

    public static class Default implements android.view.ICrossWindowBlurEnabledListener {
        @Override // android.view.ICrossWindowBlurEnabledListener
        public void onCrossWindowBlurEnabledChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.ICrossWindowBlurEnabledListener {
        static final int TRANSACTION_onCrossWindowBlurEnabledChanged = 1;

        public Stub() {
            attachInterface(this, android.view.ICrossWindowBlurEnabledListener.DESCRIPTOR);
        }

        public static android.view.ICrossWindowBlurEnabledListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.ICrossWindowBlurEnabledListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.ICrossWindowBlurEnabledListener)) {
                return (android.view.ICrossWindowBlurEnabledListener) queryLocalInterface;
            }
            return new android.view.ICrossWindowBlurEnabledListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCrossWindowBlurEnabledChanged";
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
                parcel.enforceInterface(android.view.ICrossWindowBlurEnabledListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.ICrossWindowBlurEnabledListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCrossWindowBlurEnabledChanged(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.ICrossWindowBlurEnabledListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.ICrossWindowBlurEnabledListener.DESCRIPTOR;
            }

            @Override // android.view.ICrossWindowBlurEnabledListener
            public void onCrossWindowBlurEnabledChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.ICrossWindowBlurEnabledListener.DESCRIPTOR);
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
