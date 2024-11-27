package android.printservice.recommendation;

/* loaded from: classes3.dex */
public interface IRecommendationsChangeListener extends android.os.IInterface {
    void onRecommendationsChanged() throws android.os.RemoteException;

    public static class Default implements android.printservice.recommendation.IRecommendationsChangeListener {
        @Override // android.printservice.recommendation.IRecommendationsChangeListener
        public void onRecommendationsChanged() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.printservice.recommendation.IRecommendationsChangeListener {
        public static final java.lang.String DESCRIPTOR = "android.printservice.recommendation.IRecommendationsChangeListener";
        static final int TRANSACTION_onRecommendationsChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.printservice.recommendation.IRecommendationsChangeListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.printservice.recommendation.IRecommendationsChangeListener)) {
                return (android.printservice.recommendation.IRecommendationsChangeListener) queryLocalInterface;
            }
            return new android.printservice.recommendation.IRecommendationsChangeListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRecommendationsChanged";
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
                    onRecommendationsChanged();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.printservice.recommendation.IRecommendationsChangeListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.printservice.recommendation.IRecommendationsChangeListener.Stub.DESCRIPTOR;
            }

            @Override // android.printservice.recommendation.IRecommendationsChangeListener
            public void onRecommendationsChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.recommendation.IRecommendationsChangeListener.Stub.DESCRIPTOR);
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
