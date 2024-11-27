package android.frameworks.location.altitude;

/* loaded from: classes.dex */
public interface IAltitudeService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$frameworks$location$altitude$IAltitudeService".replace('$', '.');
    public static final java.lang.String HASH = "e47d23f579ff7a897fb03e7e7f1c3006cfc6036b";
    public static final int VERSION = 2;

    android.frameworks.location.altitude.AddMslAltitudeToLocationResponse addMslAltitudeToLocation(android.frameworks.location.altitude.AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest) throws android.os.RemoteException;

    android.frameworks.location.altitude.GetGeoidHeightResponse getGeoidHeight(android.frameworks.location.altitude.GetGeoidHeightRequest getGeoidHeightRequest) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    public static class Default implements android.frameworks.location.altitude.IAltitudeService {
        @Override // android.frameworks.location.altitude.IAltitudeService
        public android.frameworks.location.altitude.AddMslAltitudeToLocationResponse addMslAltitudeToLocation(android.frameworks.location.altitude.AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest) throws android.os.RemoteException {
            return null;
        }

        @Override // android.frameworks.location.altitude.IAltitudeService
        public android.frameworks.location.altitude.GetGeoidHeightResponse getGeoidHeight(android.frameworks.location.altitude.GetGeoidHeightRequest getGeoidHeightRequest) throws android.os.RemoteException {
            return null;
        }

        @Override // android.frameworks.location.altitude.IAltitudeService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.frameworks.location.altitude.IAltitudeService
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.frameworks.location.altitude.IAltitudeService {
        static final int TRANSACTION_addMslAltitudeToLocation = 1;
        static final int TRANSACTION_getGeoidHeight = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.frameworks.location.altitude.IAltitudeService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.frameworks.location.altitude.IAltitudeService)) {
                return (android.frameworks.location.altitude.IAltitudeService) queryLocalInterface;
            }
            return new android.frameworks.location.altitude.IAltitudeService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addMslAltitudeToLocation";
                case 2:
                    return "getGeoidHeight";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    android.frameworks.location.altitude.AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest = (android.frameworks.location.altitude.AddMslAltitudeToLocationRequest) parcel.readTypedObject(android.frameworks.location.altitude.AddMslAltitudeToLocationRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.frameworks.location.altitude.AddMslAltitudeToLocationResponse addMslAltitudeToLocation = addMslAltitudeToLocation(addMslAltitudeToLocationRequest);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addMslAltitudeToLocation, 1);
                    return true;
                case 2:
                    android.frameworks.location.altitude.GetGeoidHeightRequest getGeoidHeightRequest = (android.frameworks.location.altitude.GetGeoidHeightRequest) parcel.readTypedObject(android.frameworks.location.altitude.GetGeoidHeightRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.frameworks.location.altitude.GetGeoidHeightResponse geoidHeight = getGeoidHeight(getGeoidHeightRequest);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(geoidHeight, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.frameworks.location.altitude.IAltitudeService {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.frameworks.location.altitude.IAltitudeService
            public android.frameworks.location.altitude.AddMslAltitudeToLocationResponse addMslAltitudeToLocation(android.frameworks.location.altitude.AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(addMslAltitudeToLocationRequest, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method addMslAltitudeToLocation is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.frameworks.location.altitude.AddMslAltitudeToLocationResponse) obtain2.readTypedObject(android.frameworks.location.altitude.AddMslAltitudeToLocationResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.frameworks.location.altitude.IAltitudeService
            public android.frameworks.location.altitude.GetGeoidHeightResponse getGeoidHeight(android.frameworks.location.altitude.GetGeoidHeightRequest getGeoidHeightRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(getGeoidHeightRequest, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getGeoidHeight is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.frameworks.location.altitude.GetGeoidHeightResponse) obtain2.readTypedObject(android.frameworks.location.altitude.GetGeoidHeightResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.frameworks.location.altitude.IAltitudeService
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.frameworks.location.altitude.IAltitudeService
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
