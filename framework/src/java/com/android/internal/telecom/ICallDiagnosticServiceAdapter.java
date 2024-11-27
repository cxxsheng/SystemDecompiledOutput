package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface ICallDiagnosticServiceAdapter extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.ICallDiagnosticServiceAdapter";

    void clearDiagnosticMessage(java.lang.String str, int i) throws android.os.RemoteException;

    void displayDiagnosticMessage(java.lang.String str, int i, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void overrideDisconnectMessage(java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void sendDeviceToDeviceMessage(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.ICallDiagnosticServiceAdapter {
        @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
        public void displayDiagnosticMessage(java.lang.String str, int i, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
        public void clearDiagnosticMessage(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
        public void sendDeviceToDeviceMessage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
        public void overrideDisconnectMessage(java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.ICallDiagnosticServiceAdapter {
        static final int TRANSACTION_clearDiagnosticMessage = 2;
        static final int TRANSACTION_displayDiagnosticMessage = 1;
        static final int TRANSACTION_overrideDisconnectMessage = 4;
        static final int TRANSACTION_sendDeviceToDeviceMessage = 3;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.ICallDiagnosticServiceAdapter.DESCRIPTOR);
        }

        public static com.android.internal.telecom.ICallDiagnosticServiceAdapter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.ICallDiagnosticServiceAdapter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.ICallDiagnosticServiceAdapter)) {
                return (com.android.internal.telecom.ICallDiagnosticServiceAdapter) queryLocalInterface;
            }
            return new com.android.internal.telecom.ICallDiagnosticServiceAdapter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "displayDiagnosticMessage";
                case 2:
                    return "clearDiagnosticMessage";
                case 3:
                    return "sendDeviceToDeviceMessage";
                case 4:
                    return "overrideDisconnectMessage";
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
                parcel.enforceInterface(com.android.internal.telecom.ICallDiagnosticServiceAdapter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.ICallDiagnosticServiceAdapter.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    displayDiagnosticMessage(readString, readInt, charSequence);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearDiagnosticMessage(readString2, readInt2);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendDeviceToDeviceMessage(readString3, readInt3, readInt4);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.CharSequence charSequence2 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    overrideDisconnectMessage(readString4, charSequence2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.ICallDiagnosticServiceAdapter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.ICallDiagnosticServiceAdapter.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void displayDiagnosticMessage(java.lang.String str, int i, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void clearDiagnosticMessage(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void sendDeviceToDeviceMessage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void overrideDisconnectMessage(java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    obtain.writeString(str);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
