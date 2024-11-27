package android.security.attestationverification;

/* loaded from: classes3.dex */
public interface IAttestationVerificationService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.attestationverification.IAttestationVerificationService";

    void onVerifyAttestation(android.os.Bundle bundle, byte[] bArr, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    public static class Default implements android.security.attestationverification.IAttestationVerificationService {
        @Override // android.security.attestationverification.IAttestationVerificationService
        public void onVerifyAttestation(android.os.Bundle bundle, byte[] bArr, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.attestationverification.IAttestationVerificationService {
        static final int TRANSACTION_onVerifyAttestation = 1;

        public Stub() {
            attachInterface(this, android.security.attestationverification.IAttestationVerificationService.DESCRIPTOR);
        }

        public static android.security.attestationverification.IAttestationVerificationService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.attestationverification.IAttestationVerificationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.attestationverification.IAttestationVerificationService)) {
                return (android.security.attestationverification.IAttestationVerificationService) queryLocalInterface;
            }
            return new android.security.attestationverification.IAttestationVerificationService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onVerifyAttestation";
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
                parcel.enforceInterface(android.security.attestationverification.IAttestationVerificationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.attestationverification.IAttestationVerificationService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    com.android.internal.infra.AndroidFuture androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVerifyAttestation(bundle, createByteArray, androidFuture);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.attestationverification.IAttestationVerificationService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.attestationverification.IAttestationVerificationService.DESCRIPTOR;
            }

            @Override // android.security.attestationverification.IAttestationVerificationService
            public void onVerifyAttestation(android.os.Bundle bundle, byte[] bArr, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.attestationverification.IAttestationVerificationService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeByteArray(bArr);
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
