package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IVisualQueryRecognitionStatusListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IVisualQueryRecognitionStatusListener";

    void onStartPerceiving() throws android.os.RemoteException;

    void onStopPerceiving() throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IVisualQueryRecognitionStatusListener {
        @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
        public void onStartPerceiving() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
        public void onStopPerceiving() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IVisualQueryRecognitionStatusListener {
        static final int TRANSACTION_onStartPerceiving = 1;
        static final int TRANSACTION_onStopPerceiving = 2;

        public Stub() {
            attachInterface(this, com.android.internal.app.IVisualQueryRecognitionStatusListener.DESCRIPTOR);
        }

        public static com.android.internal.app.IVisualQueryRecognitionStatusListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.app.IVisualQueryRecognitionStatusListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IVisualQueryRecognitionStatusListener)) {
                return (com.android.internal.app.IVisualQueryRecognitionStatusListener) queryLocalInterface;
            }
            return new com.android.internal.app.IVisualQueryRecognitionStatusListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStartPerceiving";
                case 2:
                    return "onStopPerceiving";
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
                parcel.enforceInterface(com.android.internal.app.IVisualQueryRecognitionStatusListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.app.IVisualQueryRecognitionStatusListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onStartPerceiving();
                    return true;
                case 2:
                    onStopPerceiving();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IVisualQueryRecognitionStatusListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IVisualQueryRecognitionStatusListener.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
            public void onStartPerceiving() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVisualQueryRecognitionStatusListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
            public void onStopPerceiving() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVisualQueryRecognitionStatusListener.DESCRIPTOR);
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
