package android.view;

/* loaded from: classes4.dex */
public interface IAppTransitionAnimationSpecsFuture extends android.os.IInterface {
    android.view.AppTransitionAnimationSpec[] get() throws android.os.RemoteException;

    public static class Default implements android.view.IAppTransitionAnimationSpecsFuture {
        @Override // android.view.IAppTransitionAnimationSpecsFuture
        public android.view.AppTransitionAnimationSpec[] get() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IAppTransitionAnimationSpecsFuture {
        public static final java.lang.String DESCRIPTOR = "android.view.IAppTransitionAnimationSpecsFuture";
        static final int TRANSACTION_get = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.IAppTransitionAnimationSpecsFuture asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IAppTransitionAnimationSpecsFuture)) {
                return (android.view.IAppTransitionAnimationSpecsFuture) queryLocalInterface;
            }
            return new android.view.IAppTransitionAnimationSpecsFuture.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "get";
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
                    android.view.AppTransitionAnimationSpec[] appTransitionAnimationSpecArr = get();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(appTransitionAnimationSpecArr, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IAppTransitionAnimationSpecsFuture {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IAppTransitionAnimationSpecsFuture.Stub.DESCRIPTOR;
            }

            @Override // android.view.IAppTransitionAnimationSpecsFuture
            public android.view.AppTransitionAnimationSpec[] get() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IAppTransitionAnimationSpecsFuture.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.AppTransitionAnimationSpec[]) obtain2.createTypedArray(android.view.AppTransitionAnimationSpec.CREATOR);
                } finally {
                    obtain2.recycle();
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
