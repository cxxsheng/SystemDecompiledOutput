package android.service.voice;

/* loaded from: classes3.dex */
public interface IDetectorSessionStorageService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.voice.IDetectorSessionStorageService";

    void openFile(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    public static class Default implements android.service.voice.IDetectorSessionStorageService {
        @Override // android.service.voice.IDetectorSessionStorageService
        public void openFile(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.voice.IDetectorSessionStorageService {
        static final int TRANSACTION_openFile = 1;

        public Stub() {
            attachInterface(this, android.service.voice.IDetectorSessionStorageService.DESCRIPTOR);
        }

        public static android.service.voice.IDetectorSessionStorageService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.voice.IDetectorSessionStorageService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.voice.IDetectorSessionStorageService)) {
                return (android.service.voice.IDetectorSessionStorageService) queryLocalInterface;
            }
            return new android.service.voice.IDetectorSessionStorageService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "openFile";
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
                parcel.enforceInterface(android.service.voice.IDetectorSessionStorageService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.voice.IDetectorSessionStorageService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    com.android.internal.infra.AndroidFuture androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    openFile(readString, androidFuture);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.voice.IDetectorSessionStorageService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.voice.IDetectorSessionStorageService.DESCRIPTOR;
            }

            @Override // android.service.voice.IDetectorSessionStorageService
            public void openFile(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IDetectorSessionStorageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
