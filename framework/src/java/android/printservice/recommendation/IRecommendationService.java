package android.printservice.recommendation;

/* loaded from: classes3.dex */
public interface IRecommendationService extends android.os.IInterface {
    void registerCallbacks(android.printservice.recommendation.IRecommendationServiceCallbacks iRecommendationServiceCallbacks) throws android.os.RemoteException;

    public static class Default implements android.printservice.recommendation.IRecommendationService {
        @Override // android.printservice.recommendation.IRecommendationService
        public void registerCallbacks(android.printservice.recommendation.IRecommendationServiceCallbacks iRecommendationServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.printservice.recommendation.IRecommendationService {
        public static final java.lang.String DESCRIPTOR = "android.printservice.recommendation.IRecommendationService";
        static final int TRANSACTION_registerCallbacks = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.printservice.recommendation.IRecommendationService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.printservice.recommendation.IRecommendationService)) {
                return (android.printservice.recommendation.IRecommendationService) queryLocalInterface;
            }
            return new android.printservice.recommendation.IRecommendationService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallbacks";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.printservice.recommendation.IRecommendationServiceCallbacks asInterface = android.printservice.recommendation.IRecommendationServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallbacks(asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.printservice.recommendation.IRecommendationService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.printservice.recommendation.IRecommendationService.Stub.DESCRIPTOR;
            }

            @Override // android.printservice.recommendation.IRecommendationService
            public void registerCallbacks(android.printservice.recommendation.IRecommendationServiceCallbacks iRecommendationServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.recommendation.IRecommendationService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecommendationServiceCallbacks);
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
