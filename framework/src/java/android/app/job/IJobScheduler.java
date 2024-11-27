package android.app.job;

/* loaded from: classes.dex */
public interface IJobScheduler extends android.os.IInterface {
    boolean canRunUserInitiatedJobs(java.lang.String str) throws android.os.RemoteException;

    void cancel(java.lang.String str, int i) throws android.os.RemoteException;

    void cancelAll() throws android.os.RemoteException;

    void cancelAllInNamespace(java.lang.String str) throws android.os.RemoteException;

    int enqueue(java.lang.String str, android.app.job.JobInfo jobInfo, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getAllJobSnapshots() throws android.os.RemoteException;

    java.util.Map<java.lang.String, android.content.pm.ParceledListSlice<android.app.job.JobInfo>> getAllPendingJobs() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.app.job.JobInfo> getAllPendingJobsInNamespace(java.lang.String str) throws android.os.RemoteException;

    android.app.job.JobInfo getPendingJob(java.lang.String str, int i) throws android.os.RemoteException;

    int getPendingJobReason(java.lang.String str, int i) throws android.os.RemoteException;

    java.util.List<android.app.job.JobInfo> getStartedJobs() throws android.os.RemoteException;

    boolean hasRunUserInitiatedJobsPermission(java.lang.String str, int i) throws android.os.RemoteException;

    void notePendingUserRequestedAppStop(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void registerUserVisibleJobObserver(android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver) throws android.os.RemoteException;

    int schedule(java.lang.String str, android.app.job.JobInfo jobInfo) throws android.os.RemoteException;

    int scheduleAsPackage(java.lang.String str, android.app.job.JobInfo jobInfo, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException;

    void unregisterUserVisibleJobObserver(android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver) throws android.os.RemoteException;

    public static class Default implements android.app.job.IJobScheduler {
        @Override // android.app.job.IJobScheduler
        public int schedule(java.lang.String str, android.app.job.JobInfo jobInfo) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.job.IJobScheduler
        public int enqueue(java.lang.String str, android.app.job.JobInfo jobInfo, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.job.IJobScheduler
        public int scheduleAsPackage(java.lang.String str, android.app.job.JobInfo jobInfo, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.job.IJobScheduler
        public void cancel(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.job.IJobScheduler
        public void cancelAll() throws android.os.RemoteException {
        }

        @Override // android.app.job.IJobScheduler
        public void cancelAllInNamespace(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.job.IJobScheduler
        public java.util.Map<java.lang.String, android.content.pm.ParceledListSlice<android.app.job.JobInfo>> getAllPendingJobs() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public android.content.pm.ParceledListSlice<android.app.job.JobInfo> getAllPendingJobsInNamespace(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public android.app.job.JobInfo getPendingJob(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public int getPendingJobReason(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.job.IJobScheduler
        public boolean canRunUserInitiatedJobs(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.job.IJobScheduler
        public boolean hasRunUserInitiatedJobsPermission(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.job.IJobScheduler
        public java.util.List<android.app.job.JobInfo> getStartedJobs() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public android.content.pm.ParceledListSlice getAllJobSnapshots() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public void registerUserVisibleJobObserver(android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver) throws android.os.RemoteException {
        }

        @Override // android.app.job.IJobScheduler
        public void unregisterUserVisibleJobObserver(android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver) throws android.os.RemoteException {
        }

        @Override // android.app.job.IJobScheduler
        public void notePendingUserRequestedAppStop(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.job.IJobScheduler {
        public static final java.lang.String DESCRIPTOR = "android.app.job.IJobScheduler";
        static final int TRANSACTION_canRunUserInitiatedJobs = 11;
        static final int TRANSACTION_cancel = 4;
        static final int TRANSACTION_cancelAll = 5;
        static final int TRANSACTION_cancelAllInNamespace = 6;
        static final int TRANSACTION_enqueue = 2;
        static final int TRANSACTION_getAllJobSnapshots = 14;
        static final int TRANSACTION_getAllPendingJobs = 7;
        static final int TRANSACTION_getAllPendingJobsInNamespace = 8;
        static final int TRANSACTION_getPendingJob = 9;
        static final int TRANSACTION_getPendingJobReason = 10;
        static final int TRANSACTION_getStartedJobs = 13;
        static final int TRANSACTION_hasRunUserInitiatedJobsPermission = 12;
        static final int TRANSACTION_notePendingUserRequestedAppStop = 17;
        static final int TRANSACTION_registerUserVisibleJobObserver = 15;
        static final int TRANSACTION_schedule = 1;
        static final int TRANSACTION_scheduleAsPackage = 3;
        static final int TRANSACTION_unregisterUserVisibleJobObserver = 16;
        private final android.os.PermissionEnforcer mEnforcer;
        static final java.lang.String[] PERMISSIONS_registerUserVisibleJobObserver = {android.Manifest.permission.MANAGE_ACTIVITY_TASKS, android.Manifest.permission.INTERACT_ACROSS_USERS_FULL};
        static final java.lang.String[] PERMISSIONS_unregisterUserVisibleJobObserver = {android.Manifest.permission.MANAGE_ACTIVITY_TASKS, android.Manifest.permission.INTERACT_ACROSS_USERS_FULL};
        static final java.lang.String[] PERMISSIONS_notePendingUserRequestedAppStop = {android.Manifest.permission.MANAGE_ACTIVITY_TASKS, android.Manifest.permission.INTERACT_ACROSS_USERS_FULL};

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.app.job.IJobScheduler asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.job.IJobScheduler)) {
                return (android.app.job.IJobScheduler) queryLocalInterface;
            }
            return new android.app.job.IJobScheduler.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.service.notification.ZenModeConfig.SCHEDULE_PATH;
                case 2:
                    return "enqueue";
                case 3:
                    return "scheduleAsPackage";
                case 4:
                    return "cancel";
                case 5:
                    return "cancelAll";
                case 6:
                    return "cancelAllInNamespace";
                case 7:
                    return "getAllPendingJobs";
                case 8:
                    return "getAllPendingJobsInNamespace";
                case 9:
                    return "getPendingJob";
                case 10:
                    return "getPendingJobReason";
                case 11:
                    return "canRunUserInitiatedJobs";
                case 12:
                    return "hasRunUserInitiatedJobsPermission";
                case 13:
                    return "getStartedJobs";
                case 14:
                    return "getAllJobSnapshots";
                case 15:
                    return "registerUserVisibleJobObserver";
                case 16:
                    return "unregisterUserVisibleJobObserver";
                case 17:
                    return "notePendingUserRequestedAppStop";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, final android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
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
                    android.app.job.JobInfo jobInfo = (android.app.job.JobInfo) parcel.readTypedObject(android.app.job.JobInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int schedule = schedule(readString, jobInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(schedule);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    android.app.job.JobInfo jobInfo2 = (android.app.job.JobInfo) parcel.readTypedObject(android.app.job.JobInfo.CREATOR);
                    android.app.job.JobWorkItem jobWorkItem = (android.app.job.JobWorkItem) parcel.readTypedObject(android.app.job.JobWorkItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    int enqueue = enqueue(readString2, jobInfo2, jobWorkItem);
                    parcel2.writeNoException();
                    parcel2.writeInt(enqueue);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    android.app.job.JobInfo jobInfo3 = (android.app.job.JobInfo) parcel.readTypedObject(android.app.job.JobInfo.CREATOR);
                    java.lang.String readString4 = parcel.readString();
                    int readInt = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int scheduleAsPackage = scheduleAsPackage(readString3, jobInfo3, readString4, readInt, readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(scheduleAsPackage);
                    return true;
                case 4:
                    java.lang.String readString6 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancel(readString6, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    cancelAll();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelAllInNamespace(readString7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.util.Map<java.lang.String, android.content.pm.ParceledListSlice<android.app.job.JobInfo>> allPendingJobs = getAllPendingJobs();
                    parcel2.writeNoException();
                    if (allPendingJobs == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(allPendingJobs.size());
                        allPendingJobs.forEach(new java.util.function.BiConsumer() { // from class: android.app.job.IJobScheduler$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.app.job.IJobScheduler.Stub.lambda$onTransact$0(android.os.Parcel.this, (java.lang.String) obj, (android.content.pm.ParceledListSlice) obj2);
                            }
                        });
                    }
                    return true;
                case 8:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.app.job.JobInfo> allPendingJobsInNamespace = getAllPendingJobsInNamespace(readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPendingJobsInNamespace, 1);
                    return true;
                case 9:
                    java.lang.String readString9 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.job.JobInfo pendingJob = getPendingJob(readString9, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pendingJob, 1);
                    return true;
                case 10:
                    java.lang.String readString10 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int pendingJobReason = getPendingJobReason(readString10, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(pendingJobReason);
                    return true;
                case 11:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canRunUserInitiatedJobs = canRunUserInitiatedJobs(readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canRunUserInitiatedJobs);
                    return true;
                case 12:
                    java.lang.String readString12 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasRunUserInitiatedJobsPermission = hasRunUserInitiatedJobsPermission(readString12, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasRunUserInitiatedJobsPermission);
                    return true;
                case 13:
                    java.util.List<android.app.job.JobInfo> startedJobs = getStartedJobs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(startedJobs, 1);
                    return true;
                case 14:
                    android.content.pm.ParceledListSlice allJobSnapshots = getAllJobSnapshots();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allJobSnapshots, 1);
                    return true;
                case 15:
                    android.app.job.IUserVisibleJobObserver asInterface = android.app.job.IUserVisibleJobObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUserVisibleJobObserver(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.app.job.IUserVisibleJobObserver asInterface2 = android.app.job.IUserVisibleJobObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUserVisibleJobObserver(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    java.lang.String readString13 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notePendingUserRequestedAppStop(readString13, readInt6, readString14);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(android.os.Parcel parcel, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) {
            parcel.writeString(str);
            parcel.writeTypedObject(parceledListSlice, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements android.app.job.IJobScheduler {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.job.IJobScheduler.Stub.DESCRIPTOR;
            }

            @Override // android.app.job.IJobScheduler
            public int schedule(java.lang.String str, android.app.job.JobInfo jobInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(jobInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int enqueue(java.lang.String str, android.app.job.JobInfo jobInfo, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(jobInfo, 0);
                    obtain.writeTypedObject(jobWorkItem, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int scheduleAsPackage(java.lang.String str, android.app.job.JobInfo jobInfo, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(jobInfo, 0);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void cancel(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void cancelAll() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void cancelAllInNamespace(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public java.util.Map<java.lang.String, android.content.pm.ParceledListSlice<android.app.job.JobInfo>> getAllPendingJobs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                final android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final java.util.HashMap hashMap = readInt < 0 ? null : new java.util.HashMap();
                    java.util.stream.IntStream.range(0, readInt).forEach(new java.util.function.IntConsumer() { // from class: android.app.job.IJobScheduler$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), (android.content.pm.ParceledListSlice) android.os.Parcel.this.readTypedObject(android.content.pm.ParceledListSlice.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public android.content.pm.ParceledListSlice<android.app.job.JobInfo> getAllPendingJobsInNamespace(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public android.app.job.JobInfo getPendingJob(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.job.JobInfo) obtain2.readTypedObject(android.app.job.JobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int getPendingJobReason(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public boolean canRunUserInitiatedJobs(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public boolean hasRunUserInitiatedJobsPermission(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public java.util.List<android.app.job.JobInfo> getStartedJobs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.job.JobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public android.content.pm.ParceledListSlice getAllJobSnapshots() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void registerUserVisibleJobObserver(android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserVisibleJobObserver);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void unregisterUserVisibleJobObserver(android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserVisibleJobObserver);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void notePendingUserRequestedAppStop(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.job.IJobScheduler.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void registerUserVisibleJobObserver_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_registerUserVisibleJobObserver, getCallingPid(), getCallingUid());
        }

        protected void unregisterUserVisibleJobObserver_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_unregisterUserVisibleJobObserver, getCallingPid(), getCallingUid());
        }

        protected void notePendingUserRequestedAppStop_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_notePendingUserRequestedAppStop, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }
    }
}
