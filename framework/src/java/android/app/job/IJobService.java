package android.app.job;

/* loaded from: classes.dex */
public interface IJobService extends android.os.IInterface {
    void getTransferredDownloadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException;

    void getTransferredUploadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException;

    void onNetworkChanged(android.app.job.JobParameters jobParameters) throws android.os.RemoteException;

    void startJob(android.app.job.JobParameters jobParameters) throws android.os.RemoteException;

    void stopJob(android.app.job.JobParameters jobParameters) throws android.os.RemoteException;

    public static class Default implements android.app.job.IJobService {
        @Override // android.app.job.IJobService
        public void startJob(android.app.job.JobParameters jobParameters) throws android.os.RemoteException {
        }

        @Override // android.app.job.IJobService
        public void stopJob(android.app.job.JobParameters jobParameters) throws android.os.RemoteException {
        }

        @Override // android.app.job.IJobService
        public void onNetworkChanged(android.app.job.JobParameters jobParameters) throws android.os.RemoteException {
        }

        @Override // android.app.job.IJobService
        public void getTransferredDownloadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException {
        }

        @Override // android.app.job.IJobService
        public void getTransferredUploadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.job.IJobService {
        public static final java.lang.String DESCRIPTOR = "android.app.job.IJobService";
        static final int TRANSACTION_getTransferredDownloadBytes = 4;
        static final int TRANSACTION_getTransferredUploadBytes = 5;
        static final int TRANSACTION_onNetworkChanged = 3;
        static final int TRANSACTION_startJob = 1;
        static final int TRANSACTION_stopJob = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.job.IJobService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.job.IJobService)) {
                return (android.app.job.IJobService) queryLocalInterface;
            }
            return new android.app.job.IJobService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startJob";
                case 2:
                    return "stopJob";
                case 3:
                    return "onNetworkChanged";
                case 4:
                    return "getTransferredDownloadBytes";
                case 5:
                    return "getTransferredUploadBytes";
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
                    android.app.job.JobParameters jobParameters = (android.app.job.JobParameters) parcel.readTypedObject(android.app.job.JobParameters.CREATOR);
                    parcel.enforceNoDataAvail();
                    startJob(jobParameters);
                    return true;
                case 2:
                    android.app.job.JobParameters jobParameters2 = (android.app.job.JobParameters) parcel.readTypedObject(android.app.job.JobParameters.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopJob(jobParameters2);
                    return true;
                case 3:
                    android.app.job.JobParameters jobParameters3 = (android.app.job.JobParameters) parcel.readTypedObject(android.app.job.JobParameters.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNetworkChanged(jobParameters3);
                    return true;
                case 4:
                    android.app.job.JobParameters jobParameters4 = (android.app.job.JobParameters) parcel.readTypedObject(android.app.job.JobParameters.CREATOR);
                    android.app.job.JobWorkItem jobWorkItem = (android.app.job.JobWorkItem) parcel.readTypedObject(android.app.job.JobWorkItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    getTransferredDownloadBytes(jobParameters4, jobWorkItem);
                    return true;
                case 5:
                    android.app.job.JobParameters jobParameters5 = (android.app.job.JobParameters) parcel.readTypedObject(android.app.job.JobParameters.CREATOR);
                    android.app.job.JobWorkItem jobWorkItem2 = (android.app.job.JobWorkItem) parcel.readTypedObject(android.app.job.JobWorkItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    getTransferredUploadBytes(jobParameters5, jobWorkItem2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.job.IJobService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.job.IJobService.Stub.DESCRIPTOR;
            }

            @Override // android.app.job.IJobService
            public void startJob(android.app.job.JobParameters jobParameters) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(jobParameters, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobService
            public void stopJob(android.app.job.JobParameters jobParameters) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(jobParameters, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobService
            public void onNetworkChanged(android.app.job.JobParameters jobParameters) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(jobParameters, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobService
            public void getTransferredDownloadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(jobParameters, 0);
                    obtain.writeTypedObject(jobWorkItem, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobService
            public void getTransferredUploadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(jobParameters, 0);
                    obtain.writeTypedObject(jobWorkItem, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
