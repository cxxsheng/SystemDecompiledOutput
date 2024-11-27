package android.printservice;

/* loaded from: classes3.dex */
public interface IPrintServiceClient extends android.os.IInterface {
    android.print.PrintJobInfo getPrintJobInfo(android.print.PrintJobId printJobId) throws android.os.RemoteException;

    java.util.List<android.print.PrintJobInfo> getPrintJobInfos() throws android.os.RemoteException;

    void onCustomPrinterIconLoaded(android.print.PrinterId printerId, android.graphics.drawable.Icon icon) throws android.os.RemoteException;

    void onPrintersAdded(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    void onPrintersRemoved(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    boolean setPrintJobState(android.print.PrintJobId printJobId, int i, java.lang.String str) throws android.os.RemoteException;

    boolean setPrintJobTag(android.print.PrintJobId printJobId, java.lang.String str) throws android.os.RemoteException;

    void setProgress(android.print.PrintJobId printJobId, float f) throws android.os.RemoteException;

    void setStatus(android.print.PrintJobId printJobId, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void setStatusRes(android.print.PrintJobId printJobId, int i, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void writePrintJobData(android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.PrintJobId printJobId) throws android.os.RemoteException;

    public static class Default implements android.printservice.IPrintServiceClient {
        @Override // android.printservice.IPrintServiceClient
        public java.util.List<android.print.PrintJobInfo> getPrintJobInfos() throws android.os.RemoteException {
            return null;
        }

        @Override // android.printservice.IPrintServiceClient
        public android.print.PrintJobInfo getPrintJobInfo(android.print.PrintJobId printJobId) throws android.os.RemoteException {
            return null;
        }

        @Override // android.printservice.IPrintServiceClient
        public boolean setPrintJobState(android.print.PrintJobId printJobId, int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.printservice.IPrintServiceClient
        public boolean setPrintJobTag(android.print.PrintJobId printJobId, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.printservice.IPrintServiceClient
        public void writePrintJobData(android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.PrintJobId printJobId) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void setProgress(android.print.PrintJobId printJobId, float f) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void setStatus(android.print.PrintJobId printJobId, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void setStatusRes(android.print.PrintJobId printJobId, int i, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void onPrintersAdded(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void onPrintersRemoved(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void onCustomPrinterIconLoaded(android.print.PrinterId printerId, android.graphics.drawable.Icon icon) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.printservice.IPrintServiceClient {
        public static final java.lang.String DESCRIPTOR = "android.printservice.IPrintServiceClient";
        static final int TRANSACTION_getPrintJobInfo = 2;
        static final int TRANSACTION_getPrintJobInfos = 1;
        static final int TRANSACTION_onCustomPrinterIconLoaded = 11;
        static final int TRANSACTION_onPrintersAdded = 9;
        static final int TRANSACTION_onPrintersRemoved = 10;
        static final int TRANSACTION_setPrintJobState = 3;
        static final int TRANSACTION_setPrintJobTag = 4;
        static final int TRANSACTION_setProgress = 6;
        static final int TRANSACTION_setStatus = 7;
        static final int TRANSACTION_setStatusRes = 8;
        static final int TRANSACTION_writePrintJobData = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.printservice.IPrintServiceClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.printservice.IPrintServiceClient)) {
                return (android.printservice.IPrintServiceClient) queryLocalInterface;
            }
            return new android.printservice.IPrintServiceClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPrintJobInfos";
                case 2:
                    return "getPrintJobInfo";
                case 3:
                    return "setPrintJobState";
                case 4:
                    return "setPrintJobTag";
                case 5:
                    return "writePrintJobData";
                case 6:
                    return "setProgress";
                case 7:
                    return "setStatus";
                case 8:
                    return "setStatusRes";
                case 9:
                    return "onPrintersAdded";
                case 10:
                    return "onPrintersRemoved";
                case 11:
                    return "onCustomPrinterIconLoaded";
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
                    java.util.List<android.print.PrintJobInfo> printJobInfos = getPrintJobInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printJobInfos, 1);
                    return true;
                case 2:
                    android.print.PrintJobId printJobId = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.print.PrintJobInfo printJobInfo = getPrintJobInfo(printJobId);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(printJobInfo, 1);
                    return true;
                case 3:
                    android.print.PrintJobId printJobId2 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean printJobState = setPrintJobState(printJobId2, readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(printJobState);
                    return true;
                case 4:
                    android.print.PrintJobId printJobId3 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean printJobTag = setPrintJobTag(printJobId3, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(printJobTag);
                    return true;
                case 5:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.print.PrintJobId printJobId4 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    parcel.enforceNoDataAvail();
                    writePrintJobData(parcelFileDescriptor, printJobId4);
                    return true;
                case 6:
                    android.print.PrintJobId printJobId5 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setProgress(printJobId5, readFloat);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.print.PrintJobId printJobId6 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatus(printJobId6, charSequence);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.print.PrintJobId printJobId7 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    int readInt2 = parcel.readInt();
                    java.lang.CharSequence charSequence2 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatusRes(printJobId7, readInt2, charSequence2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrintersAdded(parceledListSlice);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.content.pm.ParceledListSlice parceledListSlice2 = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrintersRemoved(parceledListSlice2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.print.PrinterId printerId = (android.print.PrinterId) parcel.readTypedObject(android.print.PrinterId.CREATOR);
                    android.graphics.drawable.Icon icon = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCustomPrinterIconLoaded(printerId, icon);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.printservice.IPrintServiceClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.printservice.IPrintServiceClient.Stub.DESCRIPTOR;
            }

            @Override // android.printservice.IPrintServiceClient
            public java.util.List<android.print.PrintJobInfo> getPrintJobInfos() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.print.PrintJobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public android.print.PrintJobInfo getPrintJobInfo(android.print.PrintJobId printJobId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.print.PrintJobInfo) obtain2.readTypedObject(android.print.PrintJobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public boolean setPrintJobState(android.print.PrintJobId printJobId, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public boolean setPrintJobTag(android.print.PrintJobId printJobId, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void writePrintJobData(android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.PrintJobId printJobId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(printJobId, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void setProgress(android.print.PrintJobId printJobId, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeFloat(f);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void setStatus(android.print.PrintJobId printJobId, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void setStatusRes(android.print.PrintJobId printJobId, int i, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void onPrintersAdded(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void onPrintersRemoved(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void onCustomPrinterIconLoaded(android.print.PrinterId printerId, android.graphics.drawable.Icon icon) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeTypedObject(icon, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
