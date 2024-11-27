package android.service.assist.classification;

/* loaded from: classes3.dex */
public interface IFieldClassificationService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.assist.classification.IFieldClassificationService";

    void onConnected(boolean z, boolean z2) throws android.os.RemoteException;

    void onDisconnected() throws android.os.RemoteException;

    void onFieldClassificationRequest(android.service.assist.classification.FieldClassificationRequest fieldClassificationRequest, android.service.assist.classification.IFieldClassificationCallback iFieldClassificationCallback) throws android.os.RemoteException;

    public static class Default implements android.service.assist.classification.IFieldClassificationService {
        @Override // android.service.assist.classification.IFieldClassificationService
        public void onConnected(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onDisconnected() throws android.os.RemoteException {
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onFieldClassificationRequest(android.service.assist.classification.FieldClassificationRequest fieldClassificationRequest, android.service.assist.classification.IFieldClassificationCallback iFieldClassificationCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.assist.classification.IFieldClassificationService {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onFieldClassificationRequest = 3;

        public Stub() {
            attachInterface(this, android.service.assist.classification.IFieldClassificationService.DESCRIPTOR);
        }

        public static android.service.assist.classification.IFieldClassificationService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.assist.classification.IFieldClassificationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.assist.classification.IFieldClassificationService)) {
                return (android.service.assist.classification.IFieldClassificationService) queryLocalInterface;
            }
            return new android.service.assist.classification.IFieldClassificationService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConnected";
                case 2:
                    return "onDisconnected";
                case 3:
                    return "onFieldClassificationRequest";
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
                parcel.enforceInterface(android.service.assist.classification.IFieldClassificationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.assist.classification.IFieldClassificationService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConnected(readBoolean, readBoolean2);
                    return true;
                case 2:
                    onDisconnected();
                    return true;
                case 3:
                    android.service.assist.classification.FieldClassificationRequest fieldClassificationRequest = (android.service.assist.classification.FieldClassificationRequest) parcel.readTypedObject(android.service.assist.classification.FieldClassificationRequest.CREATOR);
                    android.service.assist.classification.IFieldClassificationCallback asInterface = android.service.assist.classification.IFieldClassificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onFieldClassificationRequest(fieldClassificationRequest, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.assist.classification.IFieldClassificationService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.assist.classification.IFieldClassificationService.DESCRIPTOR;
            }

            @Override // android.service.assist.classification.IFieldClassificationService
            public void onConnected(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.assist.classification.IFieldClassificationService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationService
            public void onDisconnected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.assist.classification.IFieldClassificationService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationService
            public void onFieldClassificationRequest(android.service.assist.classification.FieldClassificationRequest fieldClassificationRequest, android.service.assist.classification.IFieldClassificationCallback iFieldClassificationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.assist.classification.IFieldClassificationService.DESCRIPTOR);
                    obtain.writeTypedObject(fieldClassificationRequest, 0);
                    obtain.writeStrongInterface(iFieldClassificationCallback);
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
