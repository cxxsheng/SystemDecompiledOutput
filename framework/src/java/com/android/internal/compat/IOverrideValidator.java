package com.android.internal.compat;

/* loaded from: classes4.dex */
public interface IOverrideValidator extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.compat.IOverrideValidator";

    com.android.internal.compat.OverrideAllowedState getOverrideAllowedState(long j, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.compat.IOverrideValidator {
        @Override // com.android.internal.compat.IOverrideValidator
        public com.android.internal.compat.OverrideAllowedState getOverrideAllowedState(long j, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.compat.IOverrideValidator {
        static final int TRANSACTION_getOverrideAllowedState = 1;

        public Stub() {
            attachInterface(this, com.android.internal.compat.IOverrideValidator.DESCRIPTOR);
        }

        public static com.android.internal.compat.IOverrideValidator asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.compat.IOverrideValidator.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.compat.IOverrideValidator)) {
                return (com.android.internal.compat.IOverrideValidator) queryLocalInterface;
            }
            return new com.android.internal.compat.IOverrideValidator.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getOverrideAllowedState";
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
                parcel.enforceInterface(com.android.internal.compat.IOverrideValidator.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.compat.IOverrideValidator.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    com.android.internal.compat.OverrideAllowedState overrideAllowedState = getOverrideAllowedState(readLong, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overrideAllowedState, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.compat.IOverrideValidator {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.compat.IOverrideValidator.DESCRIPTOR;
            }

            @Override // com.android.internal.compat.IOverrideValidator
            public com.android.internal.compat.OverrideAllowedState getOverrideAllowedState(long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IOverrideValidator.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.compat.OverrideAllowedState) obtain2.readTypedObject(com.android.internal.compat.OverrideAllowedState.CREATOR);
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
