package android.telephony.gba;

/* loaded from: classes3.dex */
public interface IGbaService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.gba.IGbaService";

    void authenticationRequest(android.telephony.gba.GbaAuthRequest gbaAuthRequest) throws android.os.RemoteException;

    public static class Default implements android.telephony.gba.IGbaService {
        @Override // android.telephony.gba.IGbaService
        public void authenticationRequest(android.telephony.gba.GbaAuthRequest gbaAuthRequest) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.gba.IGbaService {
        static final int TRANSACTION_authenticationRequest = 1;

        public Stub() {
            attachInterface(this, android.telephony.gba.IGbaService.DESCRIPTOR);
        }

        public static android.telephony.gba.IGbaService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.gba.IGbaService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.gba.IGbaService)) {
                return (android.telephony.gba.IGbaService) queryLocalInterface;
            }
            return new android.telephony.gba.IGbaService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "authenticationRequest";
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
                parcel.enforceInterface(android.telephony.gba.IGbaService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.gba.IGbaService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.gba.GbaAuthRequest gbaAuthRequest = (android.telephony.gba.GbaAuthRequest) parcel.readTypedObject(android.telephony.gba.GbaAuthRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    authenticationRequest(gbaAuthRequest);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.gba.IGbaService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.gba.IGbaService.DESCRIPTOR;
            }

            @Override // android.telephony.gba.IGbaService
            public void authenticationRequest(android.telephony.gba.GbaAuthRequest gbaAuthRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.gba.IGbaService.DESCRIPTOR);
                    obtain.writeTypedObject(gbaAuthRequest, 0);
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
