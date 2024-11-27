package android.service.ambientcontext;

/* loaded from: classes3.dex */
public interface IAmbientContextDetectionService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.ambientcontext.IAmbientContextDetectionService";

    void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void startDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException;

    void stopDetection(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.service.ambientcontext.IAmbientContextDetectionService {
        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void startDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void stopDetection(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.ambientcontext.IAmbientContextDetectionService {
        static final int TRANSACTION_queryServiceStatus = 3;
        static final int TRANSACTION_startDetection = 1;
        static final int TRANSACTION_stopDetection = 2;

        public Stub() {
            attachInterface(this, android.service.ambientcontext.IAmbientContextDetectionService.DESCRIPTOR);
        }

        public static android.service.ambientcontext.IAmbientContextDetectionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.ambientcontext.IAmbientContextDetectionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.ambientcontext.IAmbientContextDetectionService)) {
                return (android.service.ambientcontext.IAmbientContextDetectionService) queryLocalInterface;
            }
            return new android.service.ambientcontext.IAmbientContextDetectionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startDetection";
                case 2:
                    return "stopDetection";
                case 3:
                    return "queryServiceStatus";
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
                parcel.enforceInterface(android.service.ambientcontext.IAmbientContextDetectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.ambientcontext.IAmbientContextDetectionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest = (android.app.ambientcontext.AmbientContextEventRequest) parcel.readTypedObject(android.app.ambientcontext.AmbientContextEventRequest.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    startDetection(ambientContextEventRequest, readString, remoteCallback, remoteCallback2);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopDetection(readString2);
                    return true;
                case 3:
                    int[] createIntArray = parcel.createIntArray();
                    java.lang.String readString3 = parcel.readString();
                    android.os.RemoteCallback remoteCallback3 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    queryServiceStatus(createIntArray, readString3, remoteCallback3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.ambientcontext.IAmbientContextDetectionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.ambientcontext.IAmbientContextDetectionService.DESCRIPTOR;
            }

            @Override // android.service.ambientcontext.IAmbientContextDetectionService
            public void startDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ambientcontext.IAmbientContextDetectionService.DESCRIPTOR);
                    obtain.writeTypedObject(ambientContextEventRequest, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ambientcontext.IAmbientContextDetectionService
            public void stopDetection(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ambientcontext.IAmbientContextDetectionService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ambientcontext.IAmbientContextDetectionService
            public void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ambientcontext.IAmbientContextDetectionService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
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
