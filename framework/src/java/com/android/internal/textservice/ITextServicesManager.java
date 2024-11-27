package com.android.internal.textservice;

/* loaded from: classes5.dex */
public interface ITextServicesManager extends android.os.IInterface {
    void finishSpellCheckerService(int i, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener) throws android.os.RemoteException;

    android.view.textservice.SpellCheckerInfo getCurrentSpellChecker(int i, java.lang.String str) throws android.os.RemoteException;

    android.view.textservice.SpellCheckerSubtype getCurrentSpellCheckerSubtype(int i, boolean z) throws android.os.RemoteException;

    android.view.textservice.SpellCheckerInfo[] getEnabledSpellCheckers(int i) throws android.os.RemoteException;

    void getSpellCheckerService(int i, java.lang.String str, java.lang.String str2, com.android.internal.textservice.ITextServicesSessionListener iTextServicesSessionListener, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener, android.os.Bundle bundle, int i2) throws android.os.RemoteException;

    boolean isSpellCheckerEnabled(int i) throws android.os.RemoteException;

    public static class Default implements com.android.internal.textservice.ITextServicesManager {
        @Override // com.android.internal.textservice.ITextServicesManager
        public android.view.textservice.SpellCheckerInfo getCurrentSpellChecker(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public android.view.textservice.SpellCheckerSubtype getCurrentSpellCheckerSubtype(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public void getSpellCheckerService(int i, java.lang.String str, java.lang.String str2, com.android.internal.textservice.ITextServicesSessionListener iTextServicesSessionListener, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public void finishSpellCheckerService(int i, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public boolean isSpellCheckerEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public android.view.textservice.SpellCheckerInfo[] getEnabledSpellCheckers(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.textservice.ITextServicesManager {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.textservice.ITextServicesManager";
        static final int TRANSACTION_finishSpellCheckerService = 4;
        static final int TRANSACTION_getCurrentSpellChecker = 1;
        static final int TRANSACTION_getCurrentSpellCheckerSubtype = 2;
        static final int TRANSACTION_getEnabledSpellCheckers = 6;
        static final int TRANSACTION_getSpellCheckerService = 3;
        static final int TRANSACTION_isSpellCheckerEnabled = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.textservice.ITextServicesManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.textservice.ITextServicesManager)) {
                return (com.android.internal.textservice.ITextServicesManager) queryLocalInterface;
            }
            return new com.android.internal.textservice.ITextServicesManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCurrentSpellChecker";
                case 2:
                    return "getCurrentSpellCheckerSubtype";
                case 3:
                    return "getSpellCheckerService";
                case 4:
                    return "finishSpellCheckerService";
                case 5:
                    return "isSpellCheckerEnabled";
                case 6:
                    return "getEnabledSpellCheckers";
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
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.view.textservice.SpellCheckerInfo currentSpellChecker = getCurrentSpellChecker(readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentSpellChecker, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.view.textservice.SpellCheckerSubtype currentSpellCheckerSubtype = getCurrentSpellCheckerSubtype(readInt2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentSpellCheckerSubtype, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    com.android.internal.textservice.ITextServicesSessionListener asInterface = com.android.internal.textservice.ITextServicesSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    com.android.internal.textservice.ISpellCheckerSessionListener asInterface2 = com.android.internal.textservice.ISpellCheckerSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSpellCheckerService(readInt3, readString2, readString3, asInterface, asInterface2, bundle, readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    com.android.internal.textservice.ISpellCheckerSessionListener asInterface3 = com.android.internal.textservice.ISpellCheckerSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishSpellCheckerService(readInt5, asInterface3);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSpellCheckerEnabled = isSpellCheckerEnabled(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSpellCheckerEnabled);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.textservice.SpellCheckerInfo[] enabledSpellCheckers = getEnabledSpellCheckers(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(enabledSpellCheckers, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.textservice.ITextServicesManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.textservice.ITextServicesManager.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public android.view.textservice.SpellCheckerInfo getCurrentSpellChecker(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ITextServicesManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.textservice.SpellCheckerInfo) obtain2.readTypedObject(android.view.textservice.SpellCheckerInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public android.view.textservice.SpellCheckerSubtype getCurrentSpellCheckerSubtype(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ITextServicesManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.textservice.SpellCheckerSubtype) obtain2.readTypedObject(android.view.textservice.SpellCheckerSubtype.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public void getSpellCheckerService(int i, java.lang.String str, java.lang.String str2, com.android.internal.textservice.ITextServicesSessionListener iTextServicesSessionListener, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ITextServicesManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iTextServicesSessionListener);
                    obtain.writeStrongInterface(iSpellCheckerSessionListener);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public void finishSpellCheckerService(int i, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ITextServicesManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSpellCheckerSessionListener);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public boolean isSpellCheckerEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ITextServicesManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public android.view.textservice.SpellCheckerInfo[] getEnabledSpellCheckers(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.textservice.ITextServicesManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.textservice.SpellCheckerInfo[]) obtain2.createTypedArray(android.view.textservice.SpellCheckerInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
