package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public interface IUdfpsOverlayController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.fingerprint.IUdfpsOverlayController";

    void hideUdfpsOverlay(int i) throws android.os.RemoteException;

    void onAcquired(int i, int i2) throws android.os.RemoteException;

    void onEnrollmentHelp(int i) throws android.os.RemoteException;

    void onEnrollmentProgress(int i, int i2) throws android.os.RemoteException;

    void setDebugMessage(int i, java.lang.String str) throws android.os.RemoteException;

    void showUdfpsOverlay(long j, int i, int i2, android.hardware.fingerprint.IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) throws android.os.RemoteException;

    public static class Default implements android.hardware.fingerprint.IUdfpsOverlayController {
        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void showUdfpsOverlay(long j, int i, int i2, android.hardware.fingerprint.IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void hideUdfpsOverlay(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void onAcquired(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void onEnrollmentProgress(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void onEnrollmentHelp(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void setDebugMessage(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.fingerprint.IUdfpsOverlayController {
        static final int TRANSACTION_hideUdfpsOverlay = 2;
        static final int TRANSACTION_onAcquired = 3;
        static final int TRANSACTION_onEnrollmentHelp = 5;
        static final int TRANSACTION_onEnrollmentProgress = 4;
        static final int TRANSACTION_setDebugMessage = 6;
        static final int TRANSACTION_showUdfpsOverlay = 1;

        public Stub() {
            attachInterface(this, android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR);
        }

        public static android.hardware.fingerprint.IUdfpsOverlayController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.fingerprint.IUdfpsOverlayController)) {
                return (android.hardware.fingerprint.IUdfpsOverlayController) queryLocalInterface;
            }
            return new android.hardware.fingerprint.IUdfpsOverlayController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "showUdfpsOverlay";
                case 2:
                    return "hideUdfpsOverlay";
                case 3:
                    return "onAcquired";
                case 4:
                    return "onEnrollmentProgress";
                case 5:
                    return "onEnrollmentHelp";
                case 6:
                    return "setDebugMessage";
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
                parcel.enforceInterface(android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.hardware.fingerprint.IUdfpsOverlayControllerCallback asInterface = android.hardware.fingerprint.IUdfpsOverlayControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    showUdfpsOverlay(readLong, readInt, readInt2, asInterface);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideUdfpsOverlay(readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAcquired(readInt4, readInt5);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollmentProgress(readInt6, readInt7);
                    return true;
                case 5:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollmentHelp(readInt8);
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDebugMessage(readInt9, readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.fingerprint.IUdfpsOverlayController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void showUdfpsOverlay(long j, int i, int i2, android.hardware.fingerprint.IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iUdfpsOverlayControllerCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void hideUdfpsOverlay(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void onAcquired(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void onEnrollmentProgress(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void onEnrollmentHelp(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void setDebugMessage(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IUdfpsOverlayController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
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
