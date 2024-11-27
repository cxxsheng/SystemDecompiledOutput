package android.service.rotationresolver;

/* loaded from: classes3.dex */
public interface IRotationResolverService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.rotationresolver.IRotationResolverService";

    void resolveRotation(android.service.rotationresolver.IRotationResolverCallback iRotationResolverCallback, android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest) throws android.os.RemoteException;

    public static class Default implements android.service.rotationresolver.IRotationResolverService {
        @Override // android.service.rotationresolver.IRotationResolverService
        public void resolveRotation(android.service.rotationresolver.IRotationResolverCallback iRotationResolverCallback, android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.rotationresolver.IRotationResolverService {
        static final int TRANSACTION_resolveRotation = 1;

        public Stub() {
            attachInterface(this, android.service.rotationresolver.IRotationResolverService.DESCRIPTOR);
        }

        public static android.service.rotationresolver.IRotationResolverService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.rotationresolver.IRotationResolverService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.rotationresolver.IRotationResolverService)) {
                return (android.service.rotationresolver.IRotationResolverService) queryLocalInterface;
            }
            return new android.service.rotationresolver.IRotationResolverService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "resolveRotation";
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
                parcel.enforceInterface(android.service.rotationresolver.IRotationResolverService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.rotationresolver.IRotationResolverService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.rotationresolver.IRotationResolverCallback asInterface = android.service.rotationresolver.IRotationResolverCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest = (android.service.rotationresolver.RotationResolutionRequest) parcel.readTypedObject(android.service.rotationresolver.RotationResolutionRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    resolveRotation(asInterface, rotationResolutionRequest);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.rotationresolver.IRotationResolverService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.rotationresolver.IRotationResolverService.DESCRIPTOR;
            }

            @Override // android.service.rotationresolver.IRotationResolverService
            public void resolveRotation(android.service.rotationresolver.IRotationResolverCallback iRotationResolverCallback, android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.rotationresolver.IRotationResolverService.DESCRIPTOR);
                    obtain.writeStrongInterface(iRotationResolverCallback);
                    obtain.writeTypedObject(rotationResolutionRequest, 0);
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
