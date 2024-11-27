package com.android.internal.textservice;

/* loaded from: classes5.dex */
public interface ITextServicesSessionListener extends android.os.IInterface {
    void onServiceConnected(com.android.internal.textservice.ISpellCheckerSession iSpellCheckerSession) throws android.os.RemoteException;

    public static class Default implements com.android.internal.textservice.ITextServicesSessionListener {
        @Override // com.android.internal.textservice.ITextServicesSessionListener
        public void onServiceConnected(com.android.internal.textservice.ISpellCheckerSession iSpellCheckerSession) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.textservice.ITextServicesSessionListener {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.textservice.ITextServicesSessionListener";
        static final int TRANSACTION_onServiceConnected = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.textservice.ITextServicesSessionListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.textservice.ITextServicesSessionListener)) {
                return (com.android.internal.textservice.ITextServicesSessionListener) queryLocalInterface;
            }
            return new com.android.internal.textservice.ITextServicesSessionListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onServiceConnected";
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
                    com.android.internal.textservice.ISpellCheckerSession asInterface = com.android.internal.textservice.ISpellCheckerSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onServiceConnected(asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.textservice.ITextServicesSessionListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.textservice.ITextServicesSessionListener.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.textservice.ITextServicesSessionListener
            public void onServiceConnected(com.android.internal.textservice.ISpellCheckerSession iSpellCheckerSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ITextServicesSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpellCheckerSession);
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