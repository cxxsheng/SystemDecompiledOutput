package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsUtListener extends android.os.IInterface {
    void lineIdentificationSupplementaryServiceResponse(int i, android.telephony.ims.ImsSsInfo imsSsInfo) throws android.os.RemoteException;

    void onSupplementaryServiceIndication(android.telephony.ims.ImsSsData imsSsData) throws android.os.RemoteException;

    void utConfigurationCallBarringQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsSsInfo[] imsSsInfoArr) throws android.os.RemoteException;

    void utConfigurationCallForwardQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsCallForwardInfo[] imsCallForwardInfoArr) throws android.os.RemoteException;

    void utConfigurationCallWaitingQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsSsInfo[] imsSsInfoArr) throws android.os.RemoteException;

    void utConfigurationQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void utConfigurationQueryFailed(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void utConfigurationUpdateFailed(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void utConfigurationUpdated(com.android.ims.internal.IImsUt iImsUt, int i) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsUtListener {
        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationUpdated(com.android.ims.internal.IImsUt iImsUt, int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationUpdateFailed(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationQueryFailed(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void lineIdentificationSupplementaryServiceResponse(int i, android.telephony.ims.ImsSsInfo imsSsInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationCallBarringQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsSsInfo[] imsSsInfoArr) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationCallForwardQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsCallForwardInfo[] imsCallForwardInfoArr) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationCallWaitingQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsSsInfo[] imsSsInfoArr) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void onSupplementaryServiceIndication(android.telephony.ims.ImsSsData imsSsData) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsUtListener {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsUtListener";
        static final int TRANSACTION_lineIdentificationSupplementaryServiceResponse = 5;
        static final int TRANSACTION_onSupplementaryServiceIndication = 9;
        static final int TRANSACTION_utConfigurationCallBarringQueried = 6;
        static final int TRANSACTION_utConfigurationCallForwardQueried = 7;
        static final int TRANSACTION_utConfigurationCallWaitingQueried = 8;
        static final int TRANSACTION_utConfigurationQueried = 3;
        static final int TRANSACTION_utConfigurationQueryFailed = 4;
        static final int TRANSACTION_utConfigurationUpdateFailed = 2;
        static final int TRANSACTION_utConfigurationUpdated = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsUtListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsUtListener)) {
                return (com.android.ims.internal.IImsUtListener) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsUtListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "utConfigurationUpdated";
                case 2:
                    return "utConfigurationUpdateFailed";
                case 3:
                    return "utConfigurationQueried";
                case 4:
                    return "utConfigurationQueryFailed";
                case 5:
                    return "lineIdentificationSupplementaryServiceResponse";
                case 6:
                    return "utConfigurationCallBarringQueried";
                case 7:
                    return "utConfigurationCallForwardQueried";
                case 8:
                    return "utConfigurationCallWaitingQueried";
                case 9:
                    return "onSupplementaryServiceIndication";
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
                    com.android.ims.internal.IImsUt asInterface = com.android.ims.internal.IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    utConfigurationUpdated(asInterface, readInt);
                    return true;
                case 2:
                    com.android.ims.internal.IImsUt asInterface2 = com.android.ims.internal.IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    android.telephony.ims.ImsReasonInfo imsReasonInfo = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationUpdateFailed(asInterface2, readInt2, imsReasonInfo);
                    return true;
                case 3:
                    com.android.ims.internal.IImsUt asInterface3 = com.android.ims.internal.IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationQueried(asInterface3, readInt3, bundle);
                    return true;
                case 4:
                    com.android.ims.internal.IImsUt asInterface4 = com.android.ims.internal.IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    android.telephony.ims.ImsReasonInfo imsReasonInfo2 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationQueryFailed(asInterface4, readInt4, imsReasonInfo2);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    android.telephony.ims.ImsSsInfo imsSsInfo = (android.telephony.ims.ImsSsInfo) parcel.readTypedObject(android.telephony.ims.ImsSsInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    lineIdentificationSupplementaryServiceResponse(readInt5, imsSsInfo);
                    return true;
                case 6:
                    com.android.ims.internal.IImsUt asInterface5 = com.android.ims.internal.IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int readInt6 = parcel.readInt();
                    android.telephony.ims.ImsSsInfo[] imsSsInfoArr = (android.telephony.ims.ImsSsInfo[]) parcel.createTypedArray(android.telephony.ims.ImsSsInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationCallBarringQueried(asInterface5, readInt6, imsSsInfoArr);
                    return true;
                case 7:
                    com.android.ims.internal.IImsUt asInterface6 = com.android.ims.internal.IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    android.telephony.ims.ImsCallForwardInfo[] imsCallForwardInfoArr = (android.telephony.ims.ImsCallForwardInfo[]) parcel.createTypedArray(android.telephony.ims.ImsCallForwardInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationCallForwardQueried(asInterface6, readInt7, imsCallForwardInfoArr);
                    return true;
                case 8:
                    com.android.ims.internal.IImsUt asInterface7 = com.android.ims.internal.IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    android.telephony.ims.ImsSsInfo[] imsSsInfoArr2 = (android.telephony.ims.ImsSsInfo[]) parcel.createTypedArray(android.telephony.ims.ImsSsInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationCallWaitingQueried(asInterface7, readInt8, imsSsInfoArr2);
                    return true;
                case 9:
                    android.telephony.ims.ImsSsData imsSsData = (android.telephony.ims.ImsSsData) parcel.readTypedObject(android.telephony.ims.ImsSsData.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSupplementaryServiceIndication(imsSsData);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsUtListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsUtListener.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationUpdated(com.android.ims.internal.IImsUt iImsUt, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUtListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsUt);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationUpdateFailed(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUtListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsUt);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUtListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsUt);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationQueryFailed(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUtListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsUt);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void lineIdentificationSupplementaryServiceResponse(int i, android.telephony.ims.ImsSsInfo imsSsInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUtListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsSsInfo, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationCallBarringQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsSsInfo[] imsSsInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUtListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsUt);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(imsSsInfoArr, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationCallForwardQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsCallForwardInfo[] imsCallForwardInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUtListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsUt);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(imsCallForwardInfoArr, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationCallWaitingQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsSsInfo[] imsSsInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUtListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsUt);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(imsSsInfoArr, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void onSupplementaryServiceIndication(android.telephony.ims.ImsSsData imsSsData) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUtListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsSsData, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
