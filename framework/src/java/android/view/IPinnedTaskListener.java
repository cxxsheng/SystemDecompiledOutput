package android.view;

/* loaded from: classes4.dex */
public interface IPinnedTaskListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IPinnedTaskListener";

    void onActivityHidden(android.content.ComponentName componentName) throws android.os.RemoteException;

    void onImeVisibilityChanged(boolean z, int i) throws android.os.RemoteException;

    void onMovementBoundsChanged(boolean z) throws android.os.RemoteException;

    public static class Default implements android.view.IPinnedTaskListener {
        @Override // android.view.IPinnedTaskListener
        public void onMovementBoundsChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IPinnedTaskListener
        public void onImeVisibilityChanged(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IPinnedTaskListener
        public void onActivityHidden(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IPinnedTaskListener {
        static final int TRANSACTION_onActivityHidden = 3;
        static final int TRANSACTION_onImeVisibilityChanged = 2;
        static final int TRANSACTION_onMovementBoundsChanged = 1;

        public Stub() {
            attachInterface(this, android.view.IPinnedTaskListener.DESCRIPTOR);
        }

        public static android.view.IPinnedTaskListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IPinnedTaskListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IPinnedTaskListener)) {
                return (android.view.IPinnedTaskListener) queryLocalInterface;
            }
            return new android.view.IPinnedTaskListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onMovementBoundsChanged";
                case 2:
                    return "onImeVisibilityChanged";
                case 3:
                    return "onActivityHidden";
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
                parcel.enforceInterface(android.view.IPinnedTaskListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IPinnedTaskListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onMovementBoundsChanged(readBoolean);
                    return true;
                case 2:
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onImeVisibilityChanged(readBoolean2, readInt);
                    return true;
                case 3:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    onActivityHidden(componentName);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IPinnedTaskListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IPinnedTaskListener.DESCRIPTOR;
            }

            @Override // android.view.IPinnedTaskListener
            public void onMovementBoundsChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IPinnedTaskListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IPinnedTaskListener
            public void onImeVisibilityChanged(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IPinnedTaskListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IPinnedTaskListener
            public void onActivityHidden(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IPinnedTaskListener.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
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
