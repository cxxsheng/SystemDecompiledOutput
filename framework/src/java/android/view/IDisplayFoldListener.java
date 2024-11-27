package android.view;

/* loaded from: classes4.dex */
public interface IDisplayFoldListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IDisplayFoldListener";

    void onDisplayFoldChanged(int i, boolean z) throws android.os.RemoteException;

    public static class Default implements android.view.IDisplayFoldListener {
        @Override // android.view.IDisplayFoldListener
        public void onDisplayFoldChanged(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IDisplayFoldListener {
        static final int TRANSACTION_onDisplayFoldChanged = 1;

        public Stub() {
            attachInterface(this, android.view.IDisplayFoldListener.DESCRIPTOR);
        }

        public static android.view.IDisplayFoldListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IDisplayFoldListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IDisplayFoldListener)) {
                return (android.view.IDisplayFoldListener) queryLocalInterface;
            }
            return new android.view.IDisplayFoldListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDisplayFoldChanged";
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
                parcel.enforceInterface(android.view.IDisplayFoldListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IDisplayFoldListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onDisplayFoldChanged(readInt, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IDisplayFoldListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IDisplayFoldListener.DESCRIPTOR;
            }

            @Override // android.view.IDisplayFoldListener
            public void onDisplayFoldChanged(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayFoldListener.DESCRIPTOR);
                    obtain.writeInt(i);
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
