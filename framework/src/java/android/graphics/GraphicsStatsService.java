package android.graphics;

/* loaded from: classes.dex */
public class GraphicsStatsService extends android.view.IGraphicsStats.Stub {
    private static final int AID_STATSD = 1066;
    private static final int DELETE_OLD = 2;
    public static final java.lang.String GRAPHICS_STATS_SERVICE = "graphicsstats";
    private static final int SAVE_BUFFER = 1;
    private static final java.lang.String TAG = "GraphicsStatsService";
    private final android.app.AlarmManager mAlarmManager;
    private final android.app.AppOpsManager mAppOps;
    private final android.content.Context mContext;
    private android.os.Handler mWriteOutHandler;
    private final int mAshmemSize = nGetAshmemSize();
    private final byte[] mZeroData = new byte[this.mAshmemSize];
    private final java.lang.Object mLock = new java.lang.Object();
    private java.util.ArrayList<android.graphics.GraphicsStatsService.ActiveBuffer> mActive = new java.util.ArrayList<>();
    private final java.lang.Object mFileAccessLock = new java.lang.Object();
    private boolean mRotateIsScheduled = false;
    private java.io.File mGraphicsStatsDir = new java.io.File(new java.io.File(android.os.Environment.getDataDirectory(), "system"), GRAPHICS_STATS_SERVICE);

    private static native void nAddToDump(long j, java.lang.String str);

    private static native void nAddToDump(long j, java.lang.String str, java.lang.String str2, long j2, long j3, long j4, byte[] bArr);

    private static native long nCreateDump(int i, boolean z);

    private static native void nFinishDump(long j);

    private static native void nFinishDumpInMemory(long j, long j2, boolean z);

    private static native int nGetAshmemSize();

    private static native void nSaveBuffer(java.lang.String str, java.lang.String str2, long j, long j2, long j3, byte[] bArr);

    private static native void nativeDestructor();

    private native void nativeInit();

    public GraphicsStatsService(android.content.Context context) {
        this.mContext = context;
        this.mAppOps = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mAlarmManager = (android.app.AlarmManager) context.getSystemService(android.app.AlarmManager.class);
        this.mGraphicsStatsDir.mkdirs();
        if (!this.mGraphicsStatsDir.exists()) {
            throw new java.lang.IllegalStateException("Graphics stats directory does not exist: " + this.mGraphicsStatsDir.getAbsolutePath());
        }
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("GraphicsStats-disk", 10);
        handlerThread.start();
        this.mWriteOutHandler = new android.os.Handler(handlerThread.getLooper(), new android.os.Handler.Callback() { // from class: android.graphics.GraphicsStatsService.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        android.graphics.GraphicsStatsService.this.saveBuffer((android.graphics.GraphicsStatsService.HistoricalBuffer) message.obj);
                        break;
                    case 2:
                        android.graphics.GraphicsStatsService.this.deleteOldBuffers();
                        break;
                }
                return true;
            }
        });
        nativeInit();
    }

    private void scheduleRotateLocked() {
        if (this.mRotateIsScheduled) {
            return;
        }
        this.mRotateIsScheduled = true;
        java.util.Calendar normalizeDate = normalizeDate(java.lang.System.currentTimeMillis());
        normalizeDate.add(5, 1);
        this.mAlarmManager.setExact(1, normalizeDate.getTimeInMillis(), TAG, new android.app.AlarmManager.OnAlarmListener() { // from class: android.graphics.GraphicsStatsService$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                android.graphics.GraphicsStatsService.this.onAlarm();
            }
        }, this.mWriteOutHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAlarm() {
        int i;
        android.graphics.GraphicsStatsService.ActiveBuffer[] activeBufferArr;
        synchronized (this.mLock) {
            this.mRotateIsScheduled = false;
            scheduleRotateLocked();
            activeBufferArr = (android.graphics.GraphicsStatsService.ActiveBuffer[]) this.mActive.toArray(new android.graphics.GraphicsStatsService.ActiveBuffer[0]);
        }
        for (android.graphics.GraphicsStatsService.ActiveBuffer activeBuffer : activeBufferArr) {
            try {
                activeBuffer.mCallback.onRotateGraphicsStatsBuffer();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, java.lang.String.format("Failed to notify '%s' (pid=%d) to rotate buffers", activeBuffer.mInfo.mPackageName, java.lang.Integer.valueOf(activeBuffer.mPid)), e);
            }
        }
        this.mWriteOutHandler.sendEmptyMessageDelayed(2, android.app.job.JobInfo.MIN_BACKOFF_MILLIS);
    }

    @Override // android.view.IGraphicsStats
    public android.os.ParcelFileDescriptor requestBufferForProcess(java.lang.String str, android.view.IGraphicsStatsCallback iGraphicsStatsCallback) throws android.os.RemoteException {
        android.os.ParcelFileDescriptor requestBufferForProcessLocked;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mAppOps.checkPackage(callingUid, str);
                android.content.pm.PackageInfo packageInfoAsUser = this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, android.os.UserHandle.getUserId(callingUid));
                synchronized (this.mLock) {
                    requestBufferForProcessLocked = requestBufferForProcessLocked(iGraphicsStatsCallback, callingUid, callingPid, str, packageInfoAsUser.getLongVersionCode());
                }
                return requestBufferForProcessLocked;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new android.os.RemoteException("Unable to find package: '" + str + "'");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void pullGraphicsStats(boolean z, long j) throws android.os.RemoteException {
        if (android.os.Binder.getCallingUid() != 1066) {
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(stringWriter);
            if (!com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, fastPrintWriter)) {
                fastPrintWriter.flush();
                throw new android.os.RemoteException(stringWriter.toString());
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            pullGraphicsStatsImpl(z, j);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void pullGraphicsStatsImpl(boolean z, long j) {
        java.util.ArrayList<android.graphics.GraphicsStatsService.HistoricalBuffer> arrayList;
        long timeInMillis = z ? normalizeDate(java.lang.System.currentTimeMillis() - 86400000).getTimeInMillis() : normalizeDate(java.lang.System.currentTimeMillis()).getTimeInMillis();
        synchronized (this.mLock) {
            arrayList = new java.util.ArrayList<>(this.mActive.size());
            for (int i = 0; i < this.mActive.size(); i++) {
                android.graphics.GraphicsStatsService.ActiveBuffer activeBuffer = this.mActive.get(i);
                if (activeBuffer.mInfo.mStartTime == timeInMillis) {
                    try {
                        arrayList.add(new android.graphics.GraphicsStatsService.HistoricalBuffer(activeBuffer));
                    } catch (java.io.IOException e) {
                    }
                }
            }
        }
        long nCreateDump = nCreateDump(-1, true);
        try {
            synchronized (this.mFileAccessLock) {
                java.util.HashSet<java.io.File> dumpActiveLocked = dumpActiveLocked(nCreateDump, arrayList);
                arrayList.clear();
                java.io.File file = new java.io.File(this.mGraphicsStatsDir, java.lang.String.format("%d", java.lang.Long.valueOf(timeInMillis)));
                if (file.exists()) {
                    java.io.File[] listFiles = file.listFiles();
                    for (java.io.File file2 : listFiles) {
                        for (java.io.File file3 : file2.listFiles()) {
                            java.io.File file4 = new java.io.File(file3, "total");
                            if (!dumpActiveLocked.contains(file4)) {
                                nAddToDump(nCreateDump, file4.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        } finally {
            nFinishDumpInMemory(nCreateDump, j, z);
        }
    }

    private android.os.ParcelFileDescriptor requestBufferForProcessLocked(android.view.IGraphicsStatsCallback iGraphicsStatsCallback, int i, int i2, java.lang.String str, long j) throws android.os.RemoteException {
        android.graphics.GraphicsStatsService.ActiveBuffer fetchActiveBuffersLocked = fetchActiveBuffersLocked(iGraphicsStatsCallback, i, i2, str, j);
        scheduleRotateLocked();
        return fetchActiveBuffersLocked.getPfd();
    }

    private java.util.Calendar normalizeDate(long j) {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone(android.text.format.Time.TIMEZONE_UTC));
        calendar.setTimeInMillis(j);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar;
    }

    private java.io.File pathForApp(android.graphics.GraphicsStatsService.BufferInfo bufferInfo) {
        return new java.io.File(this.mGraphicsStatsDir, java.lang.String.format("%d/%s/%d/total", java.lang.Long.valueOf(normalizeDate(bufferInfo.mStartTime).getTimeInMillis()), bufferInfo.mPackageName, java.lang.Long.valueOf(bufferInfo.mVersionCode)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveBuffer(android.graphics.GraphicsStatsService.HistoricalBuffer historicalBuffer) {
        if (android.os.Trace.isTagEnabled(524288L)) {
            android.os.Trace.traceBegin(524288L, "saving graphicsstats for " + historicalBuffer.mInfo.mPackageName);
        }
        synchronized (this.mFileAccessLock) {
            java.io.File pathForApp = pathForApp(historicalBuffer.mInfo);
            java.io.File parentFile = pathForApp.getParentFile();
            parentFile.mkdirs();
            if (!parentFile.exists()) {
                android.util.Log.w(TAG, "Unable to create path: '" + parentFile.getAbsolutePath() + "'");
            } else {
                nSaveBuffer(pathForApp.getAbsolutePath(), historicalBuffer.mInfo.mPackageName, historicalBuffer.mInfo.mVersionCode, historicalBuffer.mInfo.mStartTime, historicalBuffer.mInfo.mEndTime, historicalBuffer.mData);
                android.os.Trace.traceEnd(524288L);
            }
        }
    }

    private void deleteRecursiveLocked(java.io.File file) {
        if (file.isDirectory()) {
            for (java.io.File file2 : file.listFiles()) {
                deleteRecursiveLocked(file2);
            }
        }
        if (!file.delete()) {
            android.util.Log.w(TAG, "Failed to delete '" + file.getAbsolutePath() + "'!");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteOldBuffers() {
        android.os.Trace.traceBegin(524288L, "deleting old graphicsstats buffers");
        synchronized (this.mFileAccessLock) {
            java.io.File[] listFiles = this.mGraphicsStatsDir.listFiles();
            if (listFiles != null && listFiles.length > 3) {
                int length = listFiles.length;
                long[] jArr = new long[length];
                for (int i = 0; i < listFiles.length; i++) {
                    try {
                        jArr[i] = java.lang.Long.parseLong(listFiles[i].getName());
                    } catch (java.lang.NumberFormatException e) {
                    }
                }
                if (length <= 3) {
                    return;
                }
                java.util.Arrays.sort(jArr);
                for (int i2 = 0; i2 < length - 3; i2++) {
                    deleteRecursiveLocked(new java.io.File(this.mGraphicsStatsDir, java.lang.Long.toString(jArr[i2])));
                }
                android.os.Trace.traceEnd(524288L);
            }
        }
    }

    private void addToSaveQueue(android.graphics.GraphicsStatsService.ActiveBuffer activeBuffer) {
        try {
            android.os.Message.obtain(this.mWriteOutHandler, 1, new android.graphics.GraphicsStatsService.HistoricalBuffer(activeBuffer)).sendToTarget();
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "Failed to copy graphicsstats from " + activeBuffer.mInfo.mPackageName, e);
        }
        activeBuffer.closeAllBuffers();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processDied(android.graphics.GraphicsStatsService.ActiveBuffer activeBuffer) {
        synchronized (this.mLock) {
            this.mActive.remove(activeBuffer);
        }
        addToSaveQueue(activeBuffer);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003b, code lost:
    
        r9 = new android.graphics.GraphicsStatsService.ActiveBuffer(r11, r12, r13, r14, r15, r16);
        r11.mActive.add(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x004d, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0056, code lost:
    
        throw new android.os.RemoteException("Failed to allocate space");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.graphics.GraphicsStatsService.ActiveBuffer fetchActiveBuffersLocked(android.view.IGraphicsStatsCallback iGraphicsStatsCallback, int i, int i2, java.lang.String str, long j) throws android.os.RemoteException {
        int size = this.mActive.size();
        long timeInMillis = normalizeDate(java.lang.System.currentTimeMillis()).getTimeInMillis();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                break;
            }
            android.graphics.GraphicsStatsService.ActiveBuffer activeBuffer = this.mActive.get(i3);
            if (activeBuffer.mPid == i2 && activeBuffer.mUid == i) {
                if (activeBuffer.mInfo.mStartTime < timeInMillis) {
                    activeBuffer.binderDied();
                } else {
                    return activeBuffer;
                }
            }
            i3++;
        }
    }

    private java.util.HashSet<java.io.File> dumpActiveLocked(long j, java.util.ArrayList<android.graphics.GraphicsStatsService.HistoricalBuffer> arrayList) {
        java.util.HashSet<java.io.File> hashSet = new java.util.HashSet<>(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            android.graphics.GraphicsStatsService.HistoricalBuffer historicalBuffer = arrayList.get(i);
            java.io.File pathForApp = pathForApp(historicalBuffer.mInfo);
            hashSet.add(pathForApp);
            nAddToDump(j, pathForApp.getAbsolutePath(), historicalBuffer.mInfo.mPackageName, historicalBuffer.mInfo.mVersionCode, historicalBuffer.mInfo.mStartTime, historicalBuffer.mInfo.mEndTime, historicalBuffer.mData);
        }
        return hashSet;
    }

    private void dumpHistoricalLocked(long j, java.util.HashSet<java.io.File> hashSet) {
        for (java.io.File file : this.mGraphicsStatsDir.listFiles()) {
            for (java.io.File file2 : file.listFiles()) {
                for (java.io.File file3 : file2.listFiles()) {
                    java.io.File file4 = new java.io.File(file3, "total");
                    if (!hashSet.contains(file4)) {
                        nAddToDump(j, file4.getAbsolutePath());
                    }
                }
            }
        }
    }

    @Override // android.os.Binder
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        boolean z;
        java.util.ArrayList<android.graphics.GraphicsStatsService.HistoricalBuffer> arrayList;
        if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, printWriter)) {
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    if (!android.app.time.LocationTimeZoneManager.DUMP_STATE_OPTION_PROTO.equals(strArr[i])) {
                        i++;
                    } else {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            synchronized (this.mLock) {
                arrayList = new java.util.ArrayList<>(this.mActive.size());
                for (int i2 = 0; i2 < this.mActive.size(); i2++) {
                    try {
                        arrayList.add(new android.graphics.GraphicsStatsService.HistoricalBuffer(this.mActive.get(i2)));
                    } catch (java.io.IOException e) {
                    }
                }
            }
            long nCreateDump = nCreateDump(fileDescriptor.getInt$(), z);
            try {
                synchronized (this.mFileAccessLock) {
                    java.util.HashSet<java.io.File> dumpActiveLocked = dumpActiveLocked(nCreateDump, arrayList);
                    arrayList.clear();
                    dumpHistoricalLocked(nCreateDump, dumpActiveLocked);
                }
            } finally {
                nFinishDump(nCreateDump);
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        nativeDestructor();
    }

    private final class BufferInfo {
        long mEndTime;
        final java.lang.String mPackageName;
        long mStartTime;
        final long mVersionCode;

        BufferInfo(java.lang.String str, long j, long j2) {
            this.mPackageName = str;
            this.mVersionCode = j;
            this.mStartTime = j2;
        }
    }

    private final class ActiveBuffer implements android.os.IBinder.DeathRecipient {
        final android.view.IGraphicsStatsCallback mCallback;
        final android.graphics.GraphicsStatsService.BufferInfo mInfo;
        java.nio.ByteBuffer mMapping;
        final int mPid;
        android.os.SharedMemory mProcessBuffer;
        final android.os.IBinder mToken;
        final int mUid;

        ActiveBuffer(android.view.IGraphicsStatsCallback iGraphicsStatsCallback, int i, int i2, java.lang.String str, long j) throws android.os.RemoteException, java.io.IOException {
            this.mInfo = android.graphics.GraphicsStatsService.this.new BufferInfo(str, j, java.lang.System.currentTimeMillis());
            this.mUid = i;
            this.mPid = i2;
            this.mCallback = iGraphicsStatsCallback;
            this.mToken = this.mCallback.asBinder();
            this.mToken.linkToDeath(this, 0);
            try {
                this.mProcessBuffer = android.os.SharedMemory.create("GFXStats-" + i2, android.graphics.GraphicsStatsService.this.mAshmemSize);
                this.mMapping = this.mProcessBuffer.mapReadWrite();
            } catch (android.system.ErrnoException e) {
                e.rethrowAsIOException();
            }
            this.mMapping.position(0);
            this.mMapping.put(android.graphics.GraphicsStatsService.this.mZeroData, 0, android.graphics.GraphicsStatsService.this.mAshmemSize);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mToken.unlinkToDeath(this, 0);
            android.graphics.GraphicsStatsService.this.processDied(this);
        }

        void closeAllBuffers() {
            if (this.mMapping != null) {
                android.os.SharedMemory.unmap(this.mMapping);
                this.mMapping = null;
            }
            if (this.mProcessBuffer != null) {
                this.mProcessBuffer.close();
                this.mProcessBuffer = null;
            }
        }

        android.os.ParcelFileDescriptor getPfd() {
            try {
                return this.mProcessBuffer.getFdDup();
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalStateException("Failed to get PFD from memory file", e);
            }
        }

        void readBytes(byte[] bArr, int i) throws java.io.IOException {
            if (this.mMapping == null) {
                throw new java.io.IOException("SharedMemory has been deactivated");
            }
            this.mMapping.position(0);
            this.mMapping.get(bArr, 0, i);
        }
    }

    private final class HistoricalBuffer {
        final byte[] mData;
        final android.graphics.GraphicsStatsService.BufferInfo mInfo;

        HistoricalBuffer(android.graphics.GraphicsStatsService.ActiveBuffer activeBuffer) throws java.io.IOException {
            this.mData = new byte[android.graphics.GraphicsStatsService.this.mAshmemSize];
            this.mInfo = activeBuffer.mInfo;
            this.mInfo.mEndTime = java.lang.System.currentTimeMillis();
            activeBuffer.readBytes(this.mData, android.graphics.GraphicsStatsService.this.mAshmemSize);
        }
    }
}
