package android.hardware.face;

/* loaded from: classes2.dex */
public interface IFaceServiceReceiver extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.face.IFaceServiceReceiver";

    void onAcquired(int i, int i2) throws android.os.RemoteException;

    void onAuthenticationFailed() throws android.os.RemoteException;

    void onAuthenticationFrame(android.hardware.face.FaceAuthenticationFrame faceAuthenticationFrame) throws android.os.RemoteException;

    void onAuthenticationSucceeded(android.hardware.face.Face face, int i, boolean z) throws android.os.RemoteException;

    void onChallengeGenerated(int i, int i2, long j) throws android.os.RemoteException;

    void onEnrollResult(android.hardware.face.Face face, int i) throws android.os.RemoteException;

    void onEnrollmentFrame(android.hardware.face.FaceEnrollFrame faceEnrollFrame) throws android.os.RemoteException;

    void onError(int i, int i2) throws android.os.RemoteException;

    void onFaceDetected(int i, int i2, boolean z) throws android.os.RemoteException;

    void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) throws android.os.RemoteException;

    void onFeatureSet(boolean z, int i) throws android.os.RemoteException;

    void onRemoved(android.hardware.face.Face face, int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.face.IFaceServiceReceiver {
        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollResult(android.hardware.face.Face face, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAcquired(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationSucceeded(android.hardware.face.Face face, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFaceDetected(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFailed() throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onError(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onRemoved(android.hardware.face.Face face, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureSet(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onChallengeGenerated(int i, int i2, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFrame(android.hardware.face.FaceAuthenticationFrame faceAuthenticationFrame) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollmentFrame(android.hardware.face.FaceEnrollFrame faceEnrollFrame) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.face.IFaceServiceReceiver {
        static final int TRANSACTION_onAcquired = 2;
        static final int TRANSACTION_onAuthenticationFailed = 5;
        static final int TRANSACTION_onAuthenticationFrame = 11;
        static final int TRANSACTION_onAuthenticationSucceeded = 3;
        static final int TRANSACTION_onChallengeGenerated = 10;
        static final int TRANSACTION_onEnrollResult = 1;
        static final int TRANSACTION_onEnrollmentFrame = 12;
        static final int TRANSACTION_onError = 6;
        static final int TRANSACTION_onFaceDetected = 4;
        static final int TRANSACTION_onFeatureGet = 9;
        static final int TRANSACTION_onFeatureSet = 8;
        static final int TRANSACTION_onRemoved = 7;

        public Stub() {
            attachInterface(this, android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
        }

        public static android.hardware.face.IFaceServiceReceiver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.face.IFaceServiceReceiver)) {
                return (android.hardware.face.IFaceServiceReceiver) queryLocalInterface;
            }
            return new android.hardware.face.IFaceServiceReceiver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onEnrollResult";
                case 2:
                    return "onAcquired";
                case 3:
                    return "onAuthenticationSucceeded";
                case 4:
                    return "onFaceDetected";
                case 5:
                    return "onAuthenticationFailed";
                case 6:
                    return "onError";
                case 7:
                    return "onRemoved";
                case 8:
                    return "onFeatureSet";
                case 9:
                    return "onFeatureGet";
                case 10:
                    return "onChallengeGenerated";
                case 11:
                    return "onAuthenticationFrame";
                case 12:
                    return "onEnrollmentFrame";
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
                parcel.enforceInterface(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.face.Face face = (android.hardware.face.Face) parcel.readTypedObject(android.hardware.face.Face.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollResult(face, readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAcquired(readInt2, readInt3);
                    return true;
                case 3:
                    android.hardware.face.Face face2 = (android.hardware.face.Face) parcel.readTypedObject(android.hardware.face.Face.CREATOR);
                    int readInt4 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(face2, readInt4, readBoolean);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFaceDetected(readInt5, readInt6, readBoolean2);
                    return true;
                case 5:
                    onAuthenticationFailed();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt7, readInt8);
                    return true;
                case 7:
                    android.hardware.face.Face face3 = (android.hardware.face.Face) parcel.readTypedObject(android.hardware.face.Face.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoved(face3, readInt9);
                    return true;
                case 8:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFeatureSet(readBoolean3, readInt10);
                    return true;
                case 9:
                    boolean readBoolean4 = parcel.readBoolean();
                    int[] createIntArray = parcel.createIntArray();
                    boolean[] createBooleanArray = parcel.createBooleanArray();
                    parcel.enforceNoDataAvail();
                    onFeatureGet(readBoolean4, createIntArray, createBooleanArray);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChallengeGenerated(readInt11, readInt12, readLong);
                    return true;
                case 11:
                    android.hardware.face.FaceAuthenticationFrame faceAuthenticationFrame = (android.hardware.face.FaceAuthenticationFrame) parcel.readTypedObject(android.hardware.face.FaceAuthenticationFrame.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationFrame(faceAuthenticationFrame);
                    return true;
                case 12:
                    android.hardware.face.FaceEnrollFrame faceEnrollFrame = (android.hardware.face.FaceEnrollFrame) parcel.readTypedObject(android.hardware.face.FaceEnrollFrame.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEnrollmentFrame(faceEnrollFrame);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.face.IFaceServiceReceiver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.face.IFaceServiceReceiver.DESCRIPTOR;
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onEnrollResult(android.hardware.face.Face face, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(face, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAcquired(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAuthenticationSucceeded(android.hardware.face.Face face, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(face, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onFaceDetected(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAuthenticationFailed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onError(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onRemoved(android.hardware.face.Face face, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(face, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onFeatureSet(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeIntArray(iArr);
                    obtain.writeBooleanArray(zArr);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onChallengeGenerated(int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAuthenticationFrame(android.hardware.face.FaceAuthenticationFrame faceAuthenticationFrame) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(faceAuthenticationFrame, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onEnrollmentFrame(android.hardware.face.FaceEnrollFrame faceEnrollFrame) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(faceEnrollFrame, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
