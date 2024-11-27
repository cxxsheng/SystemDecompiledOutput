package android.service.displayhash;

/* loaded from: classes3.dex */
public interface IDisplayHashingService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.displayhash.IDisplayHashingService";

    void generateDisplayHash(byte[] bArr, android.hardware.HardwareBuffer hardwareBuffer, android.graphics.Rect rect, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void getDisplayHashAlgorithms(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void getIntervalBetweenRequestsMillis(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void verifyDisplayHash(byte[] bArr, android.view.displayhash.DisplayHash displayHash, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    public static class Default implements android.service.displayhash.IDisplayHashingService {
        @Override // android.service.displayhash.IDisplayHashingService
        public void generateDisplayHash(byte[] bArr, android.hardware.HardwareBuffer hardwareBuffer, android.graphics.Rect rect, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void verifyDisplayHash(byte[] bArr, android.view.displayhash.DisplayHash displayHash, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void getDisplayHashAlgorithms(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void getIntervalBetweenRequestsMillis(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.displayhash.IDisplayHashingService {
        static final int TRANSACTION_generateDisplayHash = 1;
        static final int TRANSACTION_getDisplayHashAlgorithms = 3;
        static final int TRANSACTION_getIntervalBetweenRequestsMillis = 4;
        static final int TRANSACTION_verifyDisplayHash = 2;

        public Stub() {
            attachInterface(this, android.service.displayhash.IDisplayHashingService.DESCRIPTOR);
        }

        public static android.service.displayhash.IDisplayHashingService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.displayhash.IDisplayHashingService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.displayhash.IDisplayHashingService)) {
                return (android.service.displayhash.IDisplayHashingService) queryLocalInterface;
            }
            return new android.service.displayhash.IDisplayHashingService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "generateDisplayHash";
                case 2:
                    return "verifyDisplayHash";
                case 3:
                    return "getDisplayHashAlgorithms";
                case 4:
                    return "getIntervalBetweenRequestsMillis";
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
                parcel.enforceInterface(android.service.displayhash.IDisplayHashingService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.displayhash.IDisplayHashingService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte[] createByteArray = parcel.createByteArray();
                    android.hardware.HardwareBuffer hardwareBuffer = (android.hardware.HardwareBuffer) parcel.readTypedObject(android.hardware.HardwareBuffer.CREATOR);
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    generateDisplayHash(createByteArray, hardwareBuffer, rect, readString, remoteCallback);
                    return true;
                case 2:
                    byte[] createByteArray2 = parcel.createByteArray();
                    android.view.displayhash.DisplayHash displayHash = (android.view.displayhash.DisplayHash) parcel.readTypedObject(android.view.displayhash.DisplayHash.CREATOR);
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    verifyDisplayHash(createByteArray2, displayHash, remoteCallback2);
                    return true;
                case 3:
                    android.os.RemoteCallback remoteCallback3 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDisplayHashAlgorithms(remoteCallback3);
                    return true;
                case 4:
                    android.os.RemoteCallback remoteCallback4 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getIntervalBetweenRequestsMillis(remoteCallback4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.displayhash.IDisplayHashingService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.displayhash.IDisplayHashingService.DESCRIPTOR;
            }

            @Override // android.service.displayhash.IDisplayHashingService
            public void generateDisplayHash(byte[] bArr, android.hardware.HardwareBuffer hardwareBuffer, android.graphics.Rect rect, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.displayhash.IDisplayHashingService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(hardwareBuffer, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.displayhash.IDisplayHashingService
            public void verifyDisplayHash(byte[] bArr, android.view.displayhash.DisplayHash displayHash, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.displayhash.IDisplayHashingService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(displayHash, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.displayhash.IDisplayHashingService
            public void getDisplayHashAlgorithms(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.displayhash.IDisplayHashingService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.displayhash.IDisplayHashingService
            public void getIntervalBetweenRequestsMillis(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.displayhash.IDisplayHashingService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
