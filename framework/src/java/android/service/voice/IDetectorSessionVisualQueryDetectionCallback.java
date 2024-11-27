package android.service.voice;

/* loaded from: classes3.dex */
public interface IDetectorSessionVisualQueryDetectionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.voice.IDetectorSessionVisualQueryDetectionCallback";

    void onAttentionGained(android.service.voice.VisualQueryAttentionResult visualQueryAttentionResult) throws android.os.RemoteException;

    void onAttentionLost(int i) throws android.os.RemoteException;

    void onQueryDetected(java.lang.String str) throws android.os.RemoteException;

    void onQueryFinished() throws android.os.RemoteException;

    void onQueryRejected() throws android.os.RemoteException;

    void onResultDetected(android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) throws android.os.RemoteException;

    public static class Default implements android.service.voice.IDetectorSessionVisualQueryDetectionCallback {
        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onAttentionGained(android.service.voice.VisualQueryAttentionResult visualQueryAttentionResult) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onAttentionLost(int i) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onQueryDetected(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onResultDetected(android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onQueryFinished() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onQueryRejected() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.voice.IDetectorSessionVisualQueryDetectionCallback {
        static final int TRANSACTION_onAttentionGained = 1;
        static final int TRANSACTION_onAttentionLost = 2;
        static final int TRANSACTION_onQueryDetected = 3;
        static final int TRANSACTION_onQueryFinished = 5;
        static final int TRANSACTION_onQueryRejected = 6;
        static final int TRANSACTION_onResultDetected = 4;

        public Stub() {
            attachInterface(this, android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
        }

        public static android.service.voice.IDetectorSessionVisualQueryDetectionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.voice.IDetectorSessionVisualQueryDetectionCallback)) {
                return (android.service.voice.IDetectorSessionVisualQueryDetectionCallback) queryLocalInterface;
            }
            return new android.service.voice.IDetectorSessionVisualQueryDetectionCallback.Stub.Proxy(iBinder);
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
                case 3:
                    return "onQueryDetected";
                case 4:
                    return "onResultDetected";
                case 5:
                    return "onQueryFinished";
                case 6:
                    return "onQueryRejected";
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
                parcel.enforceInterface(android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
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
                case 3:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onQueryDetected(readString);
                    return true;
                case 4:
                    android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult = (android.service.voice.VisualQueryDetectedResult) parcel.readTypedObject(android.service.voice.VisualQueryDetectedResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResultDetected(visualQueryDetectedResult);
                    return true;
                case 5:
                    onQueryFinished();
                    return true;
                case 6:
                    onQueryRejected();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.voice.IDetectorSessionVisualQueryDetectionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR;
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onAttentionGained(android.service.voice.VisualQueryAttentionResult visualQueryAttentionResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryAttentionResult, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onAttentionLost(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onQueryDetected(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onResultDetected(android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryDetectedResult, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onQueryFinished() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onQueryRejected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
