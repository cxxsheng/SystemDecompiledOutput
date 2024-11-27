package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IVisualQueryDetectionAttentionListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IVisualQueryDetectionAttentionListener";

    void onAttentionGained(android.service.voice.VisualQueryAttentionResult visualQueryAttentionResult) throws android.os.RemoteException;

    void onAttentionLost(int i) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IVisualQueryDetectionAttentionListener {
        @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
        public void onAttentionGained(android.service.voice.VisualQueryAttentionResult visualQueryAttentionResult) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
        public void onAttentionLost(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IVisualQueryDetectionAttentionListener {
        static final int TRANSACTION_onAttentionGained = 1;
        static final int TRANSACTION_onAttentionLost = 2;

        public Stub() {
            attachInterface(this, com.android.internal.app.IVisualQueryDetectionAttentionListener.DESCRIPTOR);
        }

        public static com.android.internal.app.IVisualQueryDetectionAttentionListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.app.IVisualQueryDetectionAttentionListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IVisualQueryDetectionAttentionListener)) {
                return (com.android.internal.app.IVisualQueryDetectionAttentionListener) queryLocalInterface;
            }
            return new com.android.internal.app.IVisualQueryDetectionAttentionListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAttentionGained";
                case 2:
                    return "onAttentionLost";
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
                parcel.enforceInterface(com.android.internal.app.IVisualQueryDetectionAttentionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.app.IVisualQueryDetectionAttentionListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.voice.VisualQueryAttentionResult visualQueryAttentionResult = (android.service.voice.VisualQueryAttentionResult) parcel.readTypedObject(android.service.voice.VisualQueryAttentionResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAttentionGained(visualQueryAttentionResult);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAttentionLost(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IVisualQueryDetectionAttentionListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IVisualQueryDetectionAttentionListener.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
            public void onAttentionGained(android.service.voice.VisualQueryAttentionResult visualQueryAttentionResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVisualQueryDetectionAttentionListener.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryAttentionResult, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
            public void onAttentionLost(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVisualQueryDetectionAttentionListener.DESCRIPTOR);
                    obtain.writeInt(i);
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
