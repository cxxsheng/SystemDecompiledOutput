package android.service.voice;

/* loaded from: classes3.dex */
public interface IVisualQueryDetectionVoiceInteractionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.voice.IVisualQueryDetectionVoiceInteractionCallback";

    void onQueryDetected(java.lang.String str) throws android.os.RemoteException;

    void onQueryFinished() throws android.os.RemoteException;

    void onQueryRejected() throws android.os.RemoteException;

    void onResultDetected(android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) throws android.os.RemoteException;

    void onVisualQueryDetectionServiceFailure(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws android.os.RemoteException;

    public static class Default implements android.service.voice.IVisualQueryDetectionVoiceInteractionCallback {
        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryDetected(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onResultDetected(android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryFinished() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryRejected() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onVisualQueryDetectionServiceFailure(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.voice.IVisualQueryDetectionVoiceInteractionCallback {
        static final int TRANSACTION_onQueryDetected = 1;
        static final int TRANSACTION_onQueryFinished = 3;
        static final int TRANSACTION_onQueryRejected = 4;
        static final int TRANSACTION_onResultDetected = 2;
        static final int TRANSACTION_onVisualQueryDetectionServiceFailure = 5;

        public Stub() {
            attachInterface(this, android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
        }

        public static android.service.voice.IVisualQueryDetectionVoiceInteractionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.voice.IVisualQueryDetectionVoiceInteractionCallback)) {
                return (android.service.voice.IVisualQueryDetectionVoiceInteractionCallback) queryLocalInterface;
            }
            return new android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onQueryDetected";
                case 2:
                    return "onResultDetected";
                case 3:
                    return "onQueryFinished";
                case 4:
                    return "onQueryRejected";
                case 5:
                    return "onVisualQueryDetectionServiceFailure";
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
                parcel.enforceInterface(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onQueryDetected(readString);
                    return true;
                case 2:
                    android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult = (android.service.voice.VisualQueryDetectedResult) parcel.readTypedObject(android.service.voice.VisualQueryDetectedResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResultDetected(visualQueryDetectedResult);
                    return true;
                case 3:
                    onQueryFinished();
                    return true;
                case 4:
                    onQueryRejected();
                    return true;
                case 5:
                    android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure = (android.service.voice.VisualQueryDetectionServiceFailure) parcel.readTypedObject(android.service.voice.VisualQueryDetectionServiceFailure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVisualQueryDetectionServiceFailure(visualQueryDetectionServiceFailure);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.voice.IVisualQueryDetectionVoiceInteractionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR;
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryDetected(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onResultDetected(android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryDetectedResult, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryFinished() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryRejected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onVisualQueryDetectionServiceFailure(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryDetectionServiceFailure, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
