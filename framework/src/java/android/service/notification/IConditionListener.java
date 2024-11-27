package android.service.notification;

/* loaded from: classes3.dex */
public interface IConditionListener extends android.os.IInterface {
    void onConditionsReceived(android.service.notification.Condition[] conditionArr) throws android.os.RemoteException;

    public static class Default implements android.service.notification.IConditionListener {
        @Override // android.service.notification.IConditionListener
        public void onConditionsReceived(android.service.notification.Condition[] conditionArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.notification.IConditionListener {
        public static final java.lang.String DESCRIPTOR = "android.service.notification.IConditionListener";
        static final int TRANSACTION_onConditionsReceived = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.notification.IConditionListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.notification.IConditionListener)) {
                return (android.service.notification.IConditionListener) queryLocalInterface;
            }
            return new android.service.notification.IConditionListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConditionsReceived";
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
                    android.service.notification.Condition[] conditionArr = (android.service.notification.Condition[]) parcel.createTypedArray(android.service.notification.Condition.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConditionsReceived(conditionArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.notification.IConditionListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.notification.IConditionListener.Stub.DESCRIPTOR;
            }

            @Override // android.service.notification.IConditionListener
            public void onConditionsReceived(android.service.notification.Condition[] conditionArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.IConditionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(conditionArr, 0);
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
