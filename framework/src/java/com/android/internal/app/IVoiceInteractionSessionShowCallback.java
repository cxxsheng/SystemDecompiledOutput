package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IVoiceInteractionSessionShowCallback extends android.os.IInterface {
    void onFailed() throws android.os.RemoteException;

    void onShown() throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IVoiceInteractionSessionShowCallback {
        @Override // com.android.internal.app.IVoiceInteractionSessionShowCallback
        public void onFailed() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionSessionShowCallback
        public void onShown() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IVoiceInteractionSessionShowCallback {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionSessionShowCallback";
        static final int TRANSACTION_onFailed = 1;
        static final int TRANSACTION_onShown = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.app.IVoiceInteractionSessionShowCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IVoiceInteractionSessionShowCallback)) {
                return (com.android.internal.app.IVoiceInteractionSessionShowCallback) queryLocalInterface;
            }
            return new com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onFailed";
                case 2:
                    return "onShown";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onFailed();
                    return true;
                case 2:
                    onShown();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IVoiceInteractionSessionShowCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractionSessionShowCallback
            public void onFailed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSessionShowCallback
            public void onShown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
