package android.service.contentcapture;

/* loaded from: classes3.dex */
public interface IContentCaptureServiceCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.contentcapture.IContentCaptureServiceCallback";

    void disableSelf() throws android.os.RemoteException;

    void setContentCaptureConditions(java.lang.String str, java.util.List<android.view.contentcapture.ContentCaptureCondition> list) throws android.os.RemoteException;

    void setContentCaptureWhitelist(java.util.List<java.lang.String> list, java.util.List<android.content.ComponentName> list2) throws android.os.RemoteException;

    void writeSessionFlush(int i, android.content.ComponentName componentName, android.service.contentcapture.FlushMetrics flushMetrics, android.content.ContentCaptureOptions contentCaptureOptions, int i2) throws android.os.RemoteException;

    public static class Default implements android.service.contentcapture.IContentCaptureServiceCallback {
        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void setContentCaptureWhitelist(java.util.List<java.lang.String> list, java.util.List<android.content.ComponentName> list2) throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void setContentCaptureConditions(java.lang.String str, java.util.List<android.view.contentcapture.ContentCaptureCondition> list) throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void disableSelf() throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void writeSessionFlush(int i, android.content.ComponentName componentName, android.service.contentcapture.FlushMetrics flushMetrics, android.content.ContentCaptureOptions contentCaptureOptions, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.contentcapture.IContentCaptureServiceCallback {
        static final int TRANSACTION_disableSelf = 3;
        static final int TRANSACTION_setContentCaptureConditions = 2;
        static final int TRANSACTION_setContentCaptureWhitelist = 1;
        static final int TRANSACTION_writeSessionFlush = 4;

        public Stub() {
            attachInterface(this, android.service.contentcapture.IContentCaptureServiceCallback.DESCRIPTOR);
        }

        public static android.service.contentcapture.IContentCaptureServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.contentcapture.IContentCaptureServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.contentcapture.IContentCaptureServiceCallback)) {
                return (android.service.contentcapture.IContentCaptureServiceCallback) queryLocalInterface;
            }
            return new android.service.contentcapture.IContentCaptureServiceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setContentCaptureWhitelist";
                case 2:
                    return "setContentCaptureConditions";
                case 3:
                    return "disableSelf";
                case 4:
                    return "writeSessionFlush";
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
                parcel.enforceInterface(android.service.contentcapture.IContentCaptureServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.contentcapture.IContentCaptureServiceCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setContentCaptureWhitelist(createStringArrayList, createTypedArrayList);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.view.contentcapture.ContentCaptureCondition.CREATOR);
                    parcel.enforceNoDataAvail();
                    setContentCaptureConditions(readString, createTypedArrayList2);
                    return true;
                case 3:
                    disableSelf();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.service.contentcapture.FlushMetrics flushMetrics = (android.service.contentcapture.FlushMetrics) parcel.readTypedObject(android.service.contentcapture.FlushMetrics.CREATOR);
                    android.content.ContentCaptureOptions contentCaptureOptions = (android.content.ContentCaptureOptions) parcel.readTypedObject(android.content.ContentCaptureOptions.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    writeSessionFlush(readInt, componentName, flushMetrics, contentCaptureOptions, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.contentcapture.IContentCaptureServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.contentcapture.IContentCaptureServiceCallback.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void setContentCaptureWhitelist(java.util.List<java.lang.String> list, java.util.List<android.content.ComponentName> list2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureServiceCallback.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void setContentCaptureConditions(java.lang.String str, java.util.List<android.view.contentcapture.ContentCaptureCondition> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureServiceCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void disableSelf() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void writeSessionFlush(int i, android.content.ComponentName componentName, android.service.contentcapture.FlushMetrics flushMetrics, android.content.ContentCaptureOptions contentCaptureOptions, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(flushMetrics, 0);
                    obtain.writeTypedObject(contentCaptureOptions, 0);
                    obtain.writeInt(i2);
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
