package android.service.contentcapture;

/* loaded from: classes3.dex */
public interface IContentProtectionService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.contentcapture.IContentProtectionService";

    void onLoginDetected(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    void onUpdateAllowlistRequest(android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.service.contentcapture.IContentProtectionService {
        @Override // android.service.contentcapture.IContentProtectionService
        public void onLoginDetected(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentProtectionService
        public void onUpdateAllowlistRequest(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.contentcapture.IContentProtectionService {
        static final int TRANSACTION_onLoginDetected = 1;
        static final int TRANSACTION_onUpdateAllowlistRequest = 2;

        public Stub() {
            attachInterface(this, android.service.contentcapture.IContentProtectionService.DESCRIPTOR);
        }

        public static android.service.contentcapture.IContentProtectionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.contentcapture.IContentProtectionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.contentcapture.IContentProtectionService)) {
                return (android.service.contentcapture.IContentProtectionService) queryLocalInterface;
            }
            return new android.service.contentcapture.IContentProtectionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onLoginDetected";
                case 2:
                    return "onUpdateAllowlistRequest";
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
                parcel.enforceInterface(android.service.contentcapture.IContentProtectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.contentcapture.IContentProtectionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onLoginDetected(parceledListSlice);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onUpdateAllowlistRequest(readStrongBinder);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.contentcapture.IContentProtectionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.contentcapture.IContentProtectionService.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IContentProtectionService
            public void onLoginDetected(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentProtectionService.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentProtectionService
            public void onUpdateAllowlistRequest(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentProtectionService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
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
