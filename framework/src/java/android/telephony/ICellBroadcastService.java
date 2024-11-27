package android.telephony;

/* loaded from: classes3.dex */
public interface ICellBroadcastService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ICellBroadcastService";

    java.lang.CharSequence getCellBroadcastAreaInfo(int i) throws android.os.RemoteException;

    void handleCdmaCellBroadcastSms(int i, byte[] bArr, int i2) throws android.os.RemoteException;

    void handleCdmaScpMessage(int i, java.util.List<android.telephony.cdma.CdmaSmsCbProgramData> list, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void handleGsmCellBroadcastSms(int i, byte[] bArr) throws android.os.RemoteException;

    public static class Default implements android.telephony.ICellBroadcastService {
        @Override // android.telephony.ICellBroadcastService
        public void handleGsmCellBroadcastSms(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.telephony.ICellBroadcastService
        public void handleCdmaCellBroadcastSms(int i, byte[] bArr, int i2) throws android.os.RemoteException {
        }

        @Override // android.telephony.ICellBroadcastService
        public void handleCdmaScpMessage(int i, java.util.List<android.telephony.cdma.CdmaSmsCbProgramData> list, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ICellBroadcastService
        public java.lang.CharSequence getCellBroadcastAreaInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ICellBroadcastService {
        static final int TRANSACTION_getCellBroadcastAreaInfo = 4;
        static final int TRANSACTION_handleCdmaCellBroadcastSms = 2;
        static final int TRANSACTION_handleCdmaScpMessage = 3;
        static final int TRANSACTION_handleGsmCellBroadcastSms = 1;

        public Stub() {
            attachInterface(this, android.telephony.ICellBroadcastService.DESCRIPTOR);
        }

        public static android.telephony.ICellBroadcastService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ICellBroadcastService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ICellBroadcastService)) {
                return (android.telephony.ICellBroadcastService) queryLocalInterface;
            }
            return new android.telephony.ICellBroadcastService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "handleGsmCellBroadcastSms";
                case 2:
                    return "handleCdmaCellBroadcastSms";
                case 3:
                    return "handleCdmaScpMessage";
                case 4:
                    return "getCellBroadcastAreaInfo";
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
                parcel.enforceInterface(android.telephony.ICellBroadcastService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ICellBroadcastService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    handleGsmCellBroadcastSms(readInt, createByteArray);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    handleCdmaCellBroadcastSms(readInt2, createByteArray2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.cdma.CdmaSmsCbProgramData.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleCdmaScpMessage(readInt4, createTypedArrayList, readString, remoteCallback);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence cellBroadcastAreaInfo = getCellBroadcastAreaInfo(readInt5);
                    parcel2.writeNoException();
                    if (cellBroadcastAreaInfo != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(cellBroadcastAreaInfo, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ICellBroadcastService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ICellBroadcastService.DESCRIPTOR;
            }

            @Override // android.telephony.ICellBroadcastService
            public void handleGsmCellBroadcastSms(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ICellBroadcastService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ICellBroadcastService
            public void handleCdmaCellBroadcastSms(int i, byte[] bArr, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ICellBroadcastService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ICellBroadcastService
            public void handleCdmaScpMessage(int i, java.util.List<android.telephony.cdma.CdmaSmsCbProgramData> list, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ICellBroadcastService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ICellBroadcastService
            public java.lang.CharSequence getCellBroadcastAreaInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ICellBroadcastService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
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
