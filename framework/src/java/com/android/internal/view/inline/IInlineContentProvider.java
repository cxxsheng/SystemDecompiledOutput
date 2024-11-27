package com.android.internal.view.inline;

/* loaded from: classes5.dex */
public interface IInlineContentProvider extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.view.inline.IInlineContentProvider";

    void onSurfacePackageReleased() throws android.os.RemoteException;

    void provideContent(int i, int i2, com.android.internal.view.inline.IInlineContentCallback iInlineContentCallback) throws android.os.RemoteException;

    void requestSurfacePackage() throws android.os.RemoteException;

    public static class Default implements com.android.internal.view.inline.IInlineContentProvider {
        @Override // com.android.internal.view.inline.IInlineContentProvider
        public void provideContent(int i, int i2, com.android.internal.view.inline.IInlineContentCallback iInlineContentCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.inline.IInlineContentProvider
        public void requestSurfacePackage() throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.inline.IInlineContentProvider
        public void onSurfacePackageReleased() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.view.inline.IInlineContentProvider {
        static final int TRANSACTION_onSurfacePackageReleased = 3;
        static final int TRANSACTION_provideContent = 1;
        static final int TRANSACTION_requestSurfacePackage = 2;

        public Stub() {
            attachInterface(this, com.android.internal.view.inline.IInlineContentProvider.DESCRIPTOR);
        }

        public static com.android.internal.view.inline.IInlineContentProvider asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.view.inline.IInlineContentProvider.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.view.inline.IInlineContentProvider)) {
                return (com.android.internal.view.inline.IInlineContentProvider) queryLocalInterface;
            }
            return new com.android.internal.view.inline.IInlineContentProvider.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "provideContent";
                case 2:
                    return "requestSurfacePackage";
                case 3:
                    return "onSurfacePackageReleased";
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
                parcel.enforceInterface(com.android.internal.view.inline.IInlineContentProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.view.inline.IInlineContentProvider.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    com.android.internal.view.inline.IInlineContentCallback asInterface = com.android.internal.view.inline.IInlineContentCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    provideContent(readInt, readInt2, asInterface);
                    return true;
                case 2:
                    requestSurfacePackage();
                    return true;
                case 3:
                    onSurfacePackageReleased();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.view.inline.IInlineContentProvider {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.view.inline.IInlineContentProvider.DESCRIPTOR;
            }

            @Override // com.android.internal.view.inline.IInlineContentProvider
            public void provideContent(int i, int i2, com.android.internal.view.inline.IInlineContentCallback iInlineContentCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.inline.IInlineContentProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInlineContentCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.inline.IInlineContentProvider
            public void requestSurfacePackage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.inline.IInlineContentProvider.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.inline.IInlineContentProvider
            public void onSurfacePackageReleased() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.inline.IInlineContentProvider.DESCRIPTOR);
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
