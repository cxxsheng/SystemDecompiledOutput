package com.android.ims.internal.uce.options;

/* loaded from: classes4.dex */
public interface IOptionsListener extends android.os.IInterface {
    void cmdStatus(com.android.ims.internal.uce.options.OptionsCmdStatus optionsCmdStatus) throws android.os.RemoteException;

    void getVersionCb(java.lang.String str) throws android.os.RemoteException;

    void incomingOptions(java.lang.String str, com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo, int i) throws android.os.RemoteException;

    void serviceAvailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException;

    void serviceUnavailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException;

    void sipResponseReceived(java.lang.String str, com.android.ims.internal.uce.options.OptionsSipResponse optionsSipResponse, com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.uce.options.IOptionsListener {
        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void getVersionCb(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void serviceAvailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void serviceUnavailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void sipResponseReceived(java.lang.String str, com.android.ims.internal.uce.options.OptionsSipResponse optionsSipResponse, com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void cmdStatus(com.android.ims.internal.uce.options.OptionsCmdStatus optionsCmdStatus) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void incomingOptions(java.lang.String str, com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.uce.options.IOptionsListener {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.uce.options.IOptionsListener";
        static final int TRANSACTION_cmdStatus = 5;
        static final int TRANSACTION_getVersionCb = 1;
        static final int TRANSACTION_incomingOptions = 6;
        static final int TRANSACTION_serviceAvailable = 2;
        static final int TRANSACTION_serviceUnavailable = 3;
        static final int TRANSACTION_sipResponseReceived = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.uce.options.IOptionsListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.uce.options.IOptionsListener)) {
                return (com.android.ims.internal.uce.options.IOptionsListener) queryLocalInterface;
            }
            return new com.android.ims.internal.uce.options.IOptionsListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVersionCb";
                case 2:
                    return "serviceAvailable";
                case 3:
                    return "serviceUnavailable";
                case 4:
                    return "sipResponseReceived";
                case 5:
                    return "cmdStatus";
                case 6:
                    return "incomingOptions";
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
                    parcel.enforceNoDataAvail();
                    getVersionCb(readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    com.android.ims.internal.uce.common.StatusCode statusCode = (com.android.ims.internal.uce.common.StatusCode) parcel.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    serviceAvailable(statusCode);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    com.android.ims.internal.uce.common.StatusCode statusCode2 = (com.android.ims.internal.uce.common.StatusCode) parcel.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    serviceUnavailable(statusCode2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString2 = parcel.readString();
                    com.android.ims.internal.uce.options.OptionsSipResponse optionsSipResponse = (com.android.ims.internal.uce.options.OptionsSipResponse) parcel.readTypedObject(com.android.ims.internal.uce.options.OptionsSipResponse.CREATOR);
                    com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo = (com.android.ims.internal.uce.options.OptionsCapInfo) parcel.readTypedObject(com.android.ims.internal.uce.options.OptionsCapInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sipResponseReceived(readString2, optionsSipResponse, optionsCapInfo);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    com.android.ims.internal.uce.options.OptionsCmdStatus optionsCmdStatus = (com.android.ims.internal.uce.options.OptionsCmdStatus) parcel.readTypedObject(com.android.ims.internal.uce.options.OptionsCmdStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    cmdStatus(optionsCmdStatus);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString3 = parcel.readString();
                    com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo2 = (com.android.ims.internal.uce.options.OptionsCapInfo) parcel.readTypedObject(com.android.ims.internal.uce.options.OptionsCapInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    incomingOptions(readString3, optionsCapInfo2, readInt);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.uce.options.IOptionsListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.uce.options.IOptionsListener.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void getVersionCb(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void serviceAvailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(statusCode, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void serviceUnavailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(statusCode, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void sipResponseReceived(java.lang.String str, com.android.ims.internal.uce.options.OptionsSipResponse optionsSipResponse, com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(optionsSipResponse, 0);
                    obtain.writeTypedObject(optionsCapInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void cmdStatus(com.android.ims.internal.uce.options.OptionsCmdStatus optionsCmdStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(optionsCmdStatus, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void incomingOptions(java.lang.String str, com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(optionsCapInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
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
