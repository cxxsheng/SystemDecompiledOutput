package android.view;

/* loaded from: classes4.dex */
public interface IDecorViewGestureListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IDecorViewGestureListener";

    void onInterceptionChanged(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    public static class Default implements android.view.IDecorViewGestureListener {
        @Override // android.view.IDecorViewGestureListener
        public void onInterceptionChanged(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IDecorViewGestureListener {
        static final int TRANSACTION_onInterceptionChanged = 1;

        public Stub() {
            attachInterface(this, android.view.IDecorViewGestureListener.DESCRIPTOR);
        }

        public static android.view.IDecorViewGestureListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IDecorViewGestureListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IDecorViewGestureListener)) {
                return (android.view.IDecorViewGestureListener) queryLocalInterface;
            }
            return new android.view.IDecorViewGestureListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onInterceptionChanged";
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
                parcel.enforceInterface(android.view.IDecorViewGestureListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IDecorViewGestureListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onInterceptionChanged(readStrongBinder, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IDecorViewGestureListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IDecorViewGestureListener.DESCRIPTOR;
            }

            @Override // android.view.IDecorViewGestureListener
            public void onInterceptionChanged(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IDecorViewGestureListener.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
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
