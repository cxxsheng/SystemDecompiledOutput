package android.printservice.recommendation;

/* loaded from: classes3.dex */
public interface IRecommendationServiceCallbacks extends android.os.IInterface {
    void onRecommendationsUpdated(java.util.List<android.printservice.recommendation.RecommendationInfo> list) throws android.os.RemoteException;

    public static class Default implements android.printservice.recommendation.IRecommendationServiceCallbacks {
        @Override // android.printservice.recommendation.IRecommendationServiceCallbacks
        public void onRecommendationsUpdated(java.util.List<android.printservice.recommendation.RecommendationInfo> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.printservice.recommendation.IRecommendationServiceCallbacks {
        public static final java.lang.String DESCRIPTOR = "android.printservice.recommendation.IRecommendationServiceCallbacks";
        static final int TRANSACTION_onRecommendationsUpdated = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.printservice.recommendation.IRecommendationServiceCallbacks asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.printservice.recommendation.IRecommendationServiceCallbacks)) {
                return (android.printservice.recommendation.IRecommendationServiceCallbacks) queryLocalInterface;
            }
            return new android.printservice.recommendation.IRecommendationServiceCallbacks.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRecommendationsUpdated";
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
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.printservice.recommendation.RecommendationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRecommendationsUpdated(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.printservice.recommendation.IRecommendationServiceCallbacks {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.printservice.recommendation.IRecommendationServiceCallbacks.Stub.DESCRIPTOR;
            }

            @Override // android.printservice.recommendation.IRecommendationServiceCallbacks
            public void onRecommendationsUpdated(java.util.List<android.printservice.recommendation.RecommendationInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.recommendation.IRecommendationServiceCallbacks.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
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
