package android.service.translation;

/* loaded from: classes3.dex */
public interface ITranslationService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.translation.ITranslationService";

    void onConnected(android.os.IBinder iBinder) throws android.os.RemoteException;

    void onCreateTranslationSession(android.view.translation.TranslationContext translationContext, int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void onDisconnected() throws android.os.RemoteException;

    void onTranslationCapabilitiesRequest(int i, int i2, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    public static class Default implements android.service.translation.ITranslationService {
        @Override // android.service.translation.ITranslationService
        public void onConnected(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.service.translation.ITranslationService
        public void onDisconnected() throws android.os.RemoteException {
        }

        @Override // android.service.translation.ITranslationService
        public void onCreateTranslationSession(android.view.translation.TranslationContext translationContext, int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.service.translation.ITranslationService
        public void onTranslationCapabilitiesRequest(int i, int i2, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.translation.ITranslationService {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onCreateTranslationSession = 3;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onTranslationCapabilitiesRequest = 4;

        public Stub() {
            attachInterface(this, android.service.translation.ITranslationService.DESCRIPTOR);
        }

        public static android.service.translation.ITranslationService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.translation.ITranslationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.translation.ITranslationService)) {
                return (android.service.translation.ITranslationService) queryLocalInterface;
            }
            return new android.service.translation.ITranslationService.Stub.Proxy(iBinder);
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
                    return "onCreateTranslationSession";
                case 4:
                    return "onTranslationCapabilitiesRequest";
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
                parcel.enforceInterface(android.service.translation.ITranslationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.translation.ITranslationService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onConnected(readStrongBinder);
                    return true;
                case 2:
                    onDisconnected();
                    return true;
                case 3:
                    android.view.translation.TranslationContext translationContext = (android.view.translation.TranslationContext) parcel.readTypedObject(android.view.translation.TranslationContext.CREATOR);
                    int readInt = parcel.readInt();
                    com.android.internal.os.IResultReceiver asInterface = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onCreateTranslationSession(translationContext, readInt, asInterface);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTranslationCapabilitiesRequest(readInt2, readInt3, resultReceiver);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.translation.ITranslationService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.translation.ITranslationService.DESCRIPTOR;
            }

            @Override // android.service.translation.ITranslationService
            public void onConnected(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.translation.ITranslationService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.translation.ITranslationService
            public void onDisconnected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.translation.ITranslationService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.translation.ITranslationService
            public void onCreateTranslationSession(android.view.translation.TranslationContext translationContext, int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.translation.ITranslationService.DESCRIPTOR);
                    obtain.writeTypedObject(translationContext, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.translation.ITranslationService
            public void onTranslationCapabilitiesRequest(int i, int i2, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.translation.ITranslationService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(resultReceiver, 0);
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
