package com.android.internal.textservice;

/* loaded from: classes5.dex */
public interface ISpellCheckerService extends android.os.IInterface {
    void getISpellCheckerSession(java.lang.String str, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener, android.os.Bundle bundle, int i, com.android.internal.textservice.ISpellCheckerServiceCallback iSpellCheckerServiceCallback) throws android.os.RemoteException;

    public static class Default implements com.android.internal.textservice.ISpellCheckerService {
        @Override // com.android.internal.textservice.ISpellCheckerService
        public void getISpellCheckerSession(java.lang.String str, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener, android.os.Bundle bundle, int i, com.android.internal.textservice.ISpellCheckerServiceCallback iSpellCheckerServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.textservice.ISpellCheckerService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerService";
        static final int TRANSACTION_getISpellCheckerSession = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.textservice.ISpellCheckerService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.textservice.ISpellCheckerService)) {
                return (com.android.internal.textservice.ISpellCheckerService) queryLocalInterface;
            }
            return new com.android.internal.textservice.ISpellCheckerService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getISpellCheckerSession";
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
                    java.lang.String readString = parcel.readString();
                    com.android.internal.textservice.ISpellCheckerSessionListener asInterface = com.android.internal.textservice.ISpellCheckerSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    com.android.internal.textservice.ISpellCheckerServiceCallback asInterface2 = com.android.internal.textservice.ISpellCheckerServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getISpellCheckerSession(readString, asInterface, bundle, readInt, asInterface2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.textservice.ISpellCheckerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.textservice.ISpellCheckerService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.textservice.ISpellCheckerService
            public void getISpellCheckerSession(java.lang.String str, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener, android.os.Bundle bundle, int i, com.android.internal.textservice.ISpellCheckerServiceCallback iSpellCheckerServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ISpellCheckerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSpellCheckerSessionListener);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSpellCheckerServiceCallback);
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
