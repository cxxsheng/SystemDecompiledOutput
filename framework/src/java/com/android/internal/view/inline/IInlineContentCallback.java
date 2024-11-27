package com.android.internal.view.inline;

/* loaded from: classes5.dex */
public interface IInlineContentCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.view.inline.IInlineContentCallback";

    void onClick() throws android.os.RemoteException;

    void onContent(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws android.os.RemoteException;

    void onLongClick() throws android.os.RemoteException;

    public static class Default implements com.android.internal.view.inline.IInlineContentCallback {
        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onContent(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onClick() throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onLongClick() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.view.inline.IInlineContentCallback {
        static final int TRANSACTION_onClick = 2;
        static final int TRANSACTION_onContent = 1;
        static final int TRANSACTION_onLongClick = 3;

        public Stub() {
            attachInterface(this, com.android.internal.view.inline.IInlineContentCallback.DESCRIPTOR);
        }

        public static com.android.internal.view.inline.IInlineContentCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.view.inline.IInlineContentCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.view.inline.IInlineContentCallback)) {
                return (com.android.internal.view.inline.IInlineContentCallback) queryLocalInterface;
            }
            return new com.android.internal.view.inline.IInlineContentCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onContent";
                case 2:
                    return "onClick";
                case 3:
                    return "onLongClick";
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
                parcel.enforceInterface(com.android.internal.view.inline.IInlineContentCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.view.inline.IInlineContentCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.SurfaceControlViewHost.SurfacePackage surfacePackage = (android.view.SurfaceControlViewHost.SurfacePackage) parcel.readTypedObject(android.view.SurfaceControlViewHost.SurfacePackage.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onContent(surfacePackage, readInt, readInt2);
                    return true;
                case 2:
                    onClick();
                    return true;
                case 3:
                    onLongClick();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.view.inline.IInlineContentCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.view.inline.IInlineContentCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.view.inline.IInlineContentCallback
            public void onContent(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.inline.IInlineContentCallback.DESCRIPTOR);
                    obtain.writeTypedObject(surfacePackage, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.inline.IInlineContentCallback
            public void onClick() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.inline.IInlineContentCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.inline.IInlineContentCallback
            public void onLongClick() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.inline.IInlineContentCallback.DESCRIPTOR);
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
