package android.service.ondeviceintelligence;

/* loaded from: classes3.dex */
public interface IRemoteProcessingService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.ondeviceintelligence.IRemoteProcessingService";

    void updateProcessingState(android.os.Bundle bundle, android.service.ondeviceintelligence.IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) throws android.os.RemoteException;

    public static class Default implements android.service.ondeviceintelligence.IRemoteProcessingService {
        @Override // android.service.ondeviceintelligence.IRemoteProcessingService
        public void updateProcessingState(android.os.Bundle bundle, android.service.ondeviceintelligence.IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.ondeviceintelligence.IRemoteProcessingService {
        static final int TRANSACTION_updateProcessingState = 1;

        public Stub() {
            attachInterface(this, android.service.ondeviceintelligence.IRemoteProcessingService.DESCRIPTOR);
        }

        public static android.service.ondeviceintelligence.IRemoteProcessingService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.ondeviceintelligence.IRemoteProcessingService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.ondeviceintelligence.IRemoteProcessingService)) {
                return (android.service.ondeviceintelligence.IRemoteProcessingService) queryLocalInterface;
            }
            return new android.service.ondeviceintelligence.IRemoteProcessingService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateProcessingState";
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
                parcel.enforceInterface(android.service.ondeviceintelligence.IRemoteProcessingService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.ondeviceintelligence.IRemoteProcessingService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.service.ondeviceintelligence.IProcessingUpdateStatusCallback asInterface = android.service.ondeviceintelligence.IProcessingUpdateStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateProcessingState(bundle, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.ondeviceintelligence.IRemoteProcessingService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.ondeviceintelligence.IRemoteProcessingService.DESCRIPTOR;
            }

            @Override // android.service.ondeviceintelligence.IRemoteProcessingService
            public void updateProcessingState(android.os.Bundle bundle, android.service.ondeviceintelligence.IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IRemoteProcessingService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iProcessingUpdateStatusCallback);
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
