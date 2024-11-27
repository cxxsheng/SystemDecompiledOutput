package android.app;

/* loaded from: classes.dex */
public interface IAlarmManager extends android.os.IInterface {
    boolean canScheduleExactAlarms(java.lang.String str) throws android.os.RemoteException;

    int getConfigVersion() throws android.os.RemoteException;

    android.app.AlarmManager.AlarmClockInfo getNextAlarmClock(int i) throws android.os.RemoteException;

    long getNextWakeFromIdleTime() throws android.os.RemoteException;

    boolean hasScheduleExactAlarm(java.lang.String str, int i) throws android.os.RemoteException;

    void remove(android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener) throws android.os.RemoteException;

    void removeAll(java.lang.String str) throws android.os.RemoteException;

    void set(java.lang.String str, int i, long j, long j2, long j3, int i2, android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener, java.lang.String str2, android.os.WorkSource workSource, android.app.AlarmManager.AlarmClockInfo alarmClockInfo) throws android.os.RemoteException;

    boolean setTime(long j) throws android.os.RemoteException;

    void setTimeZone(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.app.IAlarmManager {
        @Override // android.app.IAlarmManager
        public void set(java.lang.String str, int i, long j, long j2, long j3, int i2, android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener, java.lang.String str2, android.os.WorkSource workSource, android.app.AlarmManager.AlarmClockInfo alarmClockInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IAlarmManager
        public boolean setTime(long j) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IAlarmManager
        public void setTimeZone(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IAlarmManager
        public void remove(android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener) throws android.os.RemoteException {
        }

        @Override // android.app.IAlarmManager
        public void removeAll(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IAlarmManager
        public long getNextWakeFromIdleTime() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.IAlarmManager
        public android.app.AlarmManager.AlarmClockInfo getNextAlarmClock(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IAlarmManager
        public boolean canScheduleExactAlarms(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IAlarmManager
        public boolean hasScheduleExactAlarm(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IAlarmManager
        public int getConfigVersion() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IAlarmManager {
        public static final java.lang.String DESCRIPTOR = "android.app.IAlarmManager";
        static final int TRANSACTION_canScheduleExactAlarms = 8;
        static final int TRANSACTION_getConfigVersion = 10;
        static final int TRANSACTION_getNextAlarmClock = 7;
        static final int TRANSACTION_getNextWakeFromIdleTime = 6;
        static final int TRANSACTION_hasScheduleExactAlarm = 9;
        static final int TRANSACTION_remove = 4;
        static final int TRANSACTION_removeAll = 5;
        static final int TRANSACTION_set = 1;
        static final int TRANSACTION_setTime = 2;
        static final int TRANSACTION_setTimeZone = 3;
        private final android.os.PermissionEnforcer mEnforcer;

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

        public static android.app.IAlarmManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IAlarmManager)) {
                return (android.app.IAlarmManager) queryLocalInterface;
            }
            return new android.app.IAlarmManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "set";
                case 2:
                    return "setTime";
                case 3:
                    return "setTimeZone";
                case 4:
                    return "remove";
                case 5:
                    return "removeAll";
                case 6:
                    return "getNextWakeFromIdleTime";
                case 7:
                    return "getNextAlarmClock";
                case 8:
                    return "canScheduleExactAlarms";
                case 9:
                    return "hasScheduleExactAlarm";
                case 10:
                    return "getConfigVersion";
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
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    long readLong3 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.app.IAlarmListener asInterface = android.app.IAlarmListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString2 = parcel.readString();
                    android.os.WorkSource workSource = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    android.app.AlarmManager.AlarmClockInfo alarmClockInfo = (android.app.AlarmManager.AlarmClockInfo) parcel.readTypedObject(android.app.AlarmManager.AlarmClockInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    set(readString, readInt, readLong, readLong2, readLong3, readInt2, pendingIntent, asInterface, readString2, workSource, alarmClockInfo);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean time = setTime(readLong4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(time);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTimeZone(readString3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.app.IAlarmListener asInterface2 = android.app.IAlarmListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    remove(pendingIntent2, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeAll(readString4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    long nextWakeFromIdleTime = getNextWakeFromIdleTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(nextWakeFromIdleTime);
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.AlarmManager.AlarmClockInfo nextAlarmClock = getNextAlarmClock(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nextAlarmClock, 1);
                    return true;
                case 8:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canScheduleExactAlarms = canScheduleExactAlarms(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canScheduleExactAlarms);
                    return true;
                case 9:
                    java.lang.String readString6 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasScheduleExactAlarm = hasScheduleExactAlarm(readString6, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasScheduleExactAlarm);
                    return true;
                case 10:
                    int configVersion = getConfigVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(configVersion);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IAlarmManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IAlarmManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.IAlarmManager
            public void set(java.lang.String str, int i, long j, long j2, long j3, int i2, android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener, java.lang.String str2, android.os.WorkSource workSource, android.app.AlarmManager.AlarmClockInfo alarmClockInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iAlarmListener);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeTypedObject(alarmClockInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public boolean setTime(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void setTimeZone(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void remove(android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iAlarmListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void removeAll(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public long getNextWakeFromIdleTime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public android.app.AlarmManager.AlarmClockInfo getNextAlarmClock(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.AlarmManager.AlarmClockInfo) obtain2.readTypedObject(android.app.AlarmManager.AlarmClockInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public boolean canScheduleExactAlarms(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public boolean hasScheduleExactAlarm(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public int getConfigVersion() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setTime_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.SET_TIME, getCallingPid(), getCallingUid());
        }

        protected void setTimeZone_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.SET_TIME_ZONE, getCallingPid(), getCallingUid());
        }

        protected void getConfigVersion_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DUMP, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
