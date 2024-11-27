package android.security.attestationverification;

/* loaded from: classes3.dex */
public interface IAttestationVerificationManagerService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.attestationverification.IAttestationVerificationManagerService";

    void verifyAttestation(android.security.attestationverification.AttestationProfile attestationProfile, int i, android.os.Bundle bundle, byte[] bArr, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void verifyToken(android.security.attestationverification.VerificationToken verificationToken, android.os.ParcelDuration parcelDuration, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    public static class Default implements android.security.attestationverification.IAttestationVerificationManagerService {
        @Override // android.security.attestationverification.IAttestationVerificationManagerService
        public void verifyAttestation(android.security.attestationverification.AttestationProfile attestationProfile, int i, android.os.Bundle bundle, byte[] bArr, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.security.attestationverification.IAttestationVerificationManagerService
        public void verifyToken(android.security.attestationverification.VerificationToken verificationToken, android.os.ParcelDuration parcelDuration, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.attestationverification.IAttestationVerificationManagerService {
        static final int TRANSACTION_verifyAttestation = 1;
        static final int TRANSACTION_verifyToken = 2;

        public Stub() {
            attachInterface(this, android.security.attestationverification.IAttestationVerificationManagerService.DESCRIPTOR);
        }

        public static android.security.attestationverification.IAttestationVerificationManagerService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.attestationverification.IAttestationVerificationManagerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.attestationverification.IAttestationVerificationManagerService)) {
                return (android.security.attestationverification.IAttestationVerificationManagerService) queryLocalInterface;
            }
            return new android.security.attestationverification.IAttestationVerificationManagerService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "verifyAttestation";
                case 2:
                    return "verifyToken";
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
                parcel.enforceInterface(android.security.attestationverification.IAttestationVerificationManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.attestationverification.IAttestationVerificationManagerService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.security.attestationverification.AttestationProfile attestationProfile = (android.security.attestationverification.AttestationProfile) parcel.readTypedObject(android.security.attestationverification.AttestationProfile.CREATOR);
                    int readInt = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    com.android.internal.infra.AndroidFuture androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    verifyAttestation(attestationProfile, readInt, bundle, createByteArray, androidFuture);
                    return true;
                case 2:
                    android.security.attestationverification.VerificationToken verificationToken = (android.security.attestationverification.VerificationToken) parcel.readTypedObject(android.security.attestationverification.VerificationToken.CREATOR);
                    android.os.ParcelDuration parcelDuration = (android.os.ParcelDuration) parcel.readTypedObject(android.os.ParcelDuration.CREATOR);
                    com.android.internal.infra.AndroidFuture androidFuture2 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    verifyToken(verificationToken, parcelDuration, androidFuture2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.attestationverification.IAttestationVerificationManagerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.attestationverification.IAttestationVerificationManagerService.DESCRIPTOR;
            }

            @Override // android.security.attestationverification.IAttestationVerificationManagerService
            public void verifyAttestation(android.security.attestationverification.AttestationProfile attestationProfile, int i, android.os.Bundle bundle, byte[] bArr, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.attestationverification.IAttestationVerificationManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(attestationProfile, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.security.attestationverification.IAttestationVerificationManagerService
            public void verifyToken(android.security.attestationverification.VerificationToken verificationToken, android.os.ParcelDuration parcelDuration, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.attestationverification.IAttestationVerificationManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(verificationToken, 0);
                    obtain.writeTypedObject(parcelDuration, 0);
                    obtain.writeTypedObject(androidFuture, 0);
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
