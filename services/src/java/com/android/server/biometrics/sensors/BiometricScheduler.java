package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class BiometricScheduler<T, U> {
    protected static final int LOG_NUM_RECENT_OPERATIONS = 50;
    public static final int SENSOR_TYPE_FACE = 1;
    public static final int SENSOR_TYPE_FP_OTHER = 3;
    public static final int SENSOR_TYPE_UDFPS = 2;
    public static final int SENSOR_TYPE_UNKNOWN = 0;
    private static final java.lang.String TAG = "BiometricScheduler";

    @android.annotation.NonNull
    private final android.hardware.biometrics.IBiometricService mBiometricService;

    @android.annotation.NonNull
    private final java.util.ArrayDeque<com.android.server.biometrics.sensors.BiometricScheduler.CrashState> mCrashStates;

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.biometrics.sensors.BiometricSchedulerOperation mCurrentOperation;

    @android.annotation.NonNull
    private java.util.function.Supplier<java.lang.Integer> mCurrentUserRetriever;

    @android.annotation.Nullable
    private final com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher mGestureAvailabilityDispatcher;

    @android.annotation.NonNull
    protected final android.os.Handler mHandler;
    private final com.android.server.biometrics.sensors.ClientMonitorCallback mInternalCallback;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    final java.util.Deque<com.android.server.biometrics.sensors.BiometricSchedulerOperation> mPendingOperations;

    @android.annotation.NonNull
    private final java.util.List<java.lang.Integer> mRecentOperations;
    private final int mRecentOperationsLimit;
    private final int mSensorType;

    @android.annotation.Nullable
    private com.android.server.biometrics.sensors.StopUserClient<U> mStopUserClient;
    private int mTotalOperationsHandled;

    @android.annotation.Nullable
    private com.android.server.biometrics.sensors.UserSwitchProvider<T, U> mUserSwitchProvider;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SensorType {
    }

    private static final class CrashState {
        static final int NUM_ENTRIES = 10;
        final java.lang.String currentOperation;
        final java.util.List<java.lang.String> pendingOperations;
        final java.lang.String timestamp;

        CrashState(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list) {
            this.timestamp = str;
            this.currentOperation = str2;
            this.pendingOperations = list;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.timestamp);
            sb.append(": ");
            sb.append("Current Operation: {");
            sb.append(this.currentOperation);
            sb.append("}");
            sb.append(", Pending Operations(");
            sb.append(this.pendingOperations.size());
            sb.append(")");
            if (!this.pendingOperations.isEmpty()) {
                sb.append(": ");
            }
            for (int i = 0; i < this.pendingOperations.size(); i++) {
                sb.append(this.pendingOperations.get(i));
                if (i < this.pendingOperations.size() - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class UserSwitchClientCallback implements com.android.server.biometrics.sensors.ClientMonitorCallback {

        @android.annotation.NonNull
        private final com.android.server.biometrics.sensors.BaseClientMonitor mOwner;

        UserSwitchClientCallback(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
            this.mOwner = baseClientMonitor;
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public void onClientFinished(@android.annotation.NonNull final com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, final boolean z) {
            com.android.server.biometrics.sensors.BiometricScheduler.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.BiometricScheduler$UserSwitchClientCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.BiometricScheduler.UserSwitchClientCallback.this.lambda$onClientFinished$0(baseClientMonitor, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClientFinished$0(com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
            android.util.Slog.d(com.android.server.biometrics.sensors.BiometricScheduler.TAG, "[Client finished] " + baseClientMonitor + ", success: " + z);
            if (baseClientMonitor instanceof com.android.server.biometrics.sensors.StopUserClient) {
                if (!z) {
                    android.util.Slog.w(com.android.server.biometrics.sensors.BiometricScheduler.TAG, "StopUserClient failed(), is the HAL stuck? Clearing mStopUserClient");
                }
                com.android.server.biometrics.sensors.BiometricScheduler.this.mStopUserClient = null;
            }
            if (com.android.server.biometrics.sensors.BiometricScheduler.this.mCurrentOperation != null && com.android.server.biometrics.sensors.BiometricScheduler.this.mCurrentOperation.isFor(this.mOwner)) {
                com.android.server.biometrics.sensors.BiometricScheduler.this.mCurrentOperation = null;
            } else {
                android.util.Slog.w(com.android.server.biometrics.sensors.BiometricScheduler.TAG, "operation is already null or different (reset?): " + com.android.server.biometrics.sensors.BiometricScheduler.this.mCurrentOperation);
            }
            com.android.server.biometrics.sensors.BiometricScheduler.this.startNextOperationIfIdle();
        }
    }

    /* renamed from: com.android.server.biometrics.sensors.BiometricScheduler$1, reason: invalid class name */
    class AnonymousClass1 implements com.android.server.biometrics.sensors.ClientMonitorCallback {
        AnonymousClass1() {
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
            android.util.Slog.d(com.android.server.biometrics.sensors.BiometricScheduler.TAG, "[Started] " + baseClientMonitor);
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public void onClientFinished(@android.annotation.NonNull final com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, final boolean z) {
            com.android.server.biometrics.sensors.BiometricScheduler.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.BiometricScheduler$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.BiometricScheduler.AnonymousClass1.this.lambda$onClientFinished$0(baseClientMonitor, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClientFinished$0(com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
            if (com.android.server.biometrics.sensors.BiometricScheduler.this.mCurrentOperation == null) {
                android.util.Slog.e(com.android.server.biometrics.sensors.BiometricScheduler.TAG, "[Finishing] " + baseClientMonitor + " but current operation is null, success: " + z + ", possible lifecycle bug in clientMonitor implementation?");
                return;
            }
            if (!com.android.server.biometrics.sensors.BiometricScheduler.this.mCurrentOperation.isFor(baseClientMonitor)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.BiometricScheduler.TAG, "[Ignoring Finish] " + baseClientMonitor + " does not match current: " + com.android.server.biometrics.sensors.BiometricScheduler.this.mCurrentOperation);
                return;
            }
            android.util.Slog.d(com.android.server.biometrics.sensors.BiometricScheduler.TAG, "[Finishing] " + baseClientMonitor + ", success: " + z);
            if (com.android.server.biometrics.sensors.BiometricScheduler.this.mGestureAvailabilityDispatcher != null) {
                com.android.server.biometrics.sensors.BiometricScheduler.this.mGestureAvailabilityDispatcher.markSensorActive(com.android.server.biometrics.sensors.BiometricScheduler.this.mCurrentOperation.getSensorId(), false);
            }
            if (com.android.server.biometrics.sensors.BiometricScheduler.this.mRecentOperations.size() >= com.android.server.biometrics.sensors.BiometricScheduler.this.mRecentOperationsLimit) {
                com.android.server.biometrics.sensors.BiometricScheduler.this.mRecentOperations.remove(0);
            }
            com.android.server.biometrics.sensors.BiometricScheduler.this.mRecentOperations.add(java.lang.Integer.valueOf(com.android.server.biometrics.sensors.BiometricScheduler.this.mCurrentOperation.getProtoEnum()));
            com.android.server.biometrics.sensors.BiometricScheduler.this.mCurrentOperation = null;
            com.android.server.biometrics.sensors.BiometricScheduler.this.mTotalOperationsHandled++;
            com.android.server.biometrics.sensors.BiometricScheduler.this.startNextOperationIfIdle();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public BiometricScheduler(@android.annotation.NonNull android.os.Handler handler, int i, @android.annotation.Nullable com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull android.hardware.biometrics.IBiometricService iBiometricService, int i2) {
        this.mInternalCallback = new com.android.server.biometrics.sensors.BiometricScheduler.AnonymousClass1();
        this.mHandler = handler;
        this.mSensorType = i;
        this.mGestureAvailabilityDispatcher = gestureAvailabilityDispatcher;
        this.mPendingOperations = new java.util.ArrayDeque();
        this.mBiometricService = iBiometricService;
        this.mCrashStates = new java.util.ArrayDeque<>();
        this.mRecentOperationsLimit = i2;
        this.mRecentOperations = new java.util.ArrayList();
    }

    @com.android.internal.annotations.VisibleForTesting
    public BiometricScheduler(@android.annotation.NonNull android.os.Handler handler, int i, @android.annotation.Nullable com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull android.hardware.biometrics.IBiometricService iBiometricService, int i2, @android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier, @android.annotation.Nullable com.android.server.biometrics.sensors.UserSwitchProvider<T, U> userSwitchProvider) {
        this.mInternalCallback = new com.android.server.biometrics.sensors.BiometricScheduler.AnonymousClass1();
        this.mHandler = handler;
        this.mSensorType = i;
        this.mGestureAvailabilityDispatcher = gestureAvailabilityDispatcher;
        this.mPendingOperations = new java.util.ArrayDeque();
        this.mBiometricService = iBiometricService;
        this.mCrashStates = new java.util.ArrayDeque<>();
        this.mRecentOperationsLimit = i2;
        this.mRecentOperations = new java.util.ArrayList();
        this.mCurrentUserRetriever = supplier;
        this.mUserSwitchProvider = userSwitchProvider;
    }

    public BiometricScheduler(@android.annotation.NonNull android.os.Handler handler, int i, @android.annotation.Nullable com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier, @android.annotation.NonNull com.android.server.biometrics.sensors.UserSwitchProvider<T, U> userSwitchProvider) {
        this(handler, i, gestureAvailabilityDispatcher, android.hardware.biometrics.IBiometricService.Stub.asInterface(android.os.ServiceManager.getService("biometric")), 50, supplier, userSwitchProvider);
    }

    public BiometricScheduler(int i, @android.annotation.Nullable com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher) {
        this(new android.os.Handler(android.os.Looper.getMainLooper()), i, gestureAvailabilityDispatcher, android.hardware.biometrics.IBiometricService.Stub.asInterface(android.os.ServiceManager.getService("biometric")), 50);
    }

    public static int sensorTypeFromFingerprintProperties(@android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        if (fingerprintSensorPropertiesInternal.isAnyUdfpsType()) {
            return 2;
        }
        return 3;
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.biometrics.sensors.ClientMonitorCallback getInternalCallback() {
        return this.mInternalCallback;
    }

    protected void startNextOperationIfIdle() {
        com.android.server.biometrics.Flags.deHidl();
        startNextOperationIfIdleLegacy();
    }

    protected void startNextOperation() {
        if (this.mCurrentOperation != null) {
            android.util.Slog.v(TAG, "Not idle, current operation: " + this.mCurrentOperation);
            return;
        }
        if (this.mPendingOperations.isEmpty()) {
            android.util.Slog.d(TAG, "No operations, returning to idle");
            return;
        }
        int intValue = this.mCurrentUserRetriever.get().intValue();
        int targetUserId = this.mPendingOperations.getFirst().getTargetUserId();
        if (targetUserId == intValue || this.mPendingOperations.getFirst().isStartUserOperation()) {
            startNextOperationIfIdleLegacy();
            return;
        }
        if (intValue == -10000 && this.mUserSwitchProvider != null) {
            com.android.server.biometrics.sensors.StartUserClient<T, U> startUserClient = this.mUserSwitchProvider.getStartUserClient(targetUserId);
            com.android.server.biometrics.sensors.BiometricScheduler.UserSwitchClientCallback userSwitchClientCallback = new com.android.server.biometrics.sensors.BiometricScheduler.UserSwitchClientCallback(startUserClient);
            android.util.Slog.d(TAG, "[Starting User] " + startUserClient);
            this.mCurrentOperation = new com.android.server.biometrics.sensors.BiometricSchedulerOperation(startUserClient, userSwitchClientCallback, 2);
            startUserClient.start(userSwitchClientCallback);
            return;
        }
        if (this.mUserSwitchProvider != null) {
            if (this.mStopUserClient != null) {
                android.util.Slog.d(TAG, "[Waiting for StopUser] " + this.mStopUserClient);
                return;
            }
            this.mStopUserClient = this.mUserSwitchProvider.getStopUserClient(intValue);
            com.android.server.biometrics.sensors.BiometricScheduler.UserSwitchClientCallback userSwitchClientCallback2 = new com.android.server.biometrics.sensors.BiometricScheduler.UserSwitchClientCallback(this.mStopUserClient);
            android.util.Slog.d(TAG, "[Stopping User] current: " + intValue + ", next: " + targetUserId + ". " + this.mStopUserClient);
            this.mCurrentOperation = new com.android.server.biometrics.sensors.BiometricSchedulerOperation(this.mStopUserClient, userSwitchClientCallback2, 2);
            this.mStopUserClient.start(userSwitchClientCallback2);
            return;
        }
        android.util.Slog.e(TAG, "Cannot start next operation.");
    }

    protected void startNextOperationIfIdleLegacy() {
        if (this.mCurrentOperation != null) {
            android.util.Slog.v(TAG, "Not idle, current operation: " + this.mCurrentOperation);
            return;
        }
        if (this.mPendingOperations.isEmpty()) {
            android.util.Slog.d(TAG, "No operations, returning to idle");
            return;
        }
        this.mCurrentOperation = this.mPendingOperations.poll();
        android.util.Slog.d(TAG, "[Polled] " + this.mCurrentOperation);
        if (this.mCurrentOperation.isMarkedCanceling()) {
            android.util.Slog.d(TAG, "[Now Cancelling] " + this.mCurrentOperation);
            this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
            return;
        }
        if (this.mCurrentOperation.isAcquisitionOperation() && ((com.android.server.biometrics.sensors.AcquisitionClient) this.mCurrentOperation.getClientMonitor()).isAlreadyCancelled()) {
            this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
            return;
        }
        if (this.mGestureAvailabilityDispatcher != null && this.mCurrentOperation.isAcquisitionOperation()) {
            this.mGestureAvailabilityDispatcher.markSensorActive(this.mCurrentOperation.getSensorId(), true);
        }
        int isReadyToStart = this.mCurrentOperation.isReadyToStart(this.mInternalCallback);
        if (isReadyToStart == 0) {
            if (!this.mCurrentOperation.start(this.mInternalCallback)) {
                int size = this.mPendingOperations.size();
                android.util.Slog.e(TAG, "[Unable To Start] " + this.mCurrentOperation + ". Last pending operation: " + this.mPendingOperations.peekLast());
                for (int i = 0; i < size; i++) {
                    com.android.server.biometrics.sensors.BiometricSchedulerOperation pollFirst = this.mPendingOperations.pollFirst();
                    if (pollFirst != null) {
                        android.util.Slog.w(TAG, "[Aborting Operation] " + pollFirst);
                        pollFirst.abort();
                    } else {
                        android.util.Slog.e(TAG, "Null operation, index: " + i + ", expected length: " + size);
                    }
                }
                this.mCurrentOperation = null;
                startNextOperationIfIdle();
                return;
            }
            return;
        }
        try {
            this.mBiometricService.onReadyForAuthentication(this.mCurrentOperation.getClientMonitor().getRequestId(), isReadyToStart);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when contacting BiometricService", e);
        }
        android.util.Slog.d(TAG, "Waiting for cookie before starting: " + this.mCurrentOperation);
    }

    public void startPreparedClient(int i) {
        if (this.mCurrentOperation == null) {
            android.util.Slog.e(TAG, "Current operation is null");
            return;
        }
        if (this.mCurrentOperation.startWithCookie(this.mInternalCallback, i)) {
            android.util.Slog.d(TAG, "[Started] Prepared client: " + this.mCurrentOperation);
            return;
        }
        android.util.Slog.e(TAG, "[Unable To Start] Prepared client: " + this.mCurrentOperation);
        this.mCurrentOperation = null;
        startNextOperationIfIdle();
    }

    public void scheduleClientMonitor(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        scheduleClientMonitor(baseClientMonitor, null);
    }

    public void scheduleClientMonitor(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        if (baseClientMonitor.interruptsPrecedingClients()) {
            for (com.android.server.biometrics.sensors.BiometricSchedulerOperation biometricSchedulerOperation : this.mPendingOperations) {
                if (biometricSchedulerOperation.markCanceling()) {
                    android.util.Slog.d(TAG, "New client, marking pending op as canceling: " + biometricSchedulerOperation);
                }
            }
        }
        this.mPendingOperations.add(new com.android.server.biometrics.sensors.BiometricSchedulerOperation(baseClientMonitor, clientMonitorCallback));
        android.util.Slog.d(TAG, "[Added] " + baseClientMonitor + ", new queue size: " + this.mPendingOperations.size());
        if (baseClientMonitor.interruptsPrecedingClients() && this.mCurrentOperation != null && this.mCurrentOperation.isInterruptable() && this.mCurrentOperation.isStarted()) {
            android.util.Slog.d(TAG, "[Cancelling Interruptable]: " + this.mCurrentOperation);
            this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
            return;
        }
        startNextOperationIfIdle();
    }

    public void cancelEnrollment(android.os.IBinder iBinder, long j) {
        android.util.Slog.d(TAG, "cancelEnrollment, requestId: " + j);
        if (this.mCurrentOperation != null && canCancelEnrollOperation(this.mCurrentOperation, iBinder, j)) {
            android.util.Slog.d(TAG, "Cancelling enrollment op: " + this.mCurrentOperation);
            this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
            return;
        }
        for (com.android.server.biometrics.sensors.BiometricSchedulerOperation biometricSchedulerOperation : this.mPendingOperations) {
            if (canCancelEnrollOperation(biometricSchedulerOperation, iBinder, j)) {
                android.util.Slog.d(TAG, "Cancelling pending enrollment op: " + biometricSchedulerOperation);
                biometricSchedulerOperation.markCanceling();
            }
        }
    }

    public void cancelAuthenticationOrDetection(android.os.IBinder iBinder, long j) {
        android.util.Slog.d(TAG, "cancelAuthenticationOrDetection, requestId: " + j);
        if (this.mCurrentOperation != null && canCancelAuthOperation(this.mCurrentOperation, iBinder, j)) {
            android.util.Slog.d(TAG, "Cancelling auth/detect op: " + this.mCurrentOperation);
            this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
            return;
        }
        for (com.android.server.biometrics.sensors.BiometricSchedulerOperation biometricSchedulerOperation : this.mPendingOperations) {
            if (canCancelAuthOperation(biometricSchedulerOperation, iBinder, j)) {
                android.util.Slog.d(TAG, "Cancelling pending auth/detect op: " + biometricSchedulerOperation);
                biometricSchedulerOperation.markCanceling();
            }
        }
    }

    private static boolean canCancelEnrollOperation(com.android.server.biometrics.sensors.BiometricSchedulerOperation biometricSchedulerOperation, android.os.IBinder iBinder, long j) {
        return biometricSchedulerOperation.isEnrollOperation() && biometricSchedulerOperation.isMatchingToken(iBinder) && biometricSchedulerOperation.isMatchingRequestId(j);
    }

    private static boolean canCancelAuthOperation(com.android.server.biometrics.sensors.BiometricSchedulerOperation biometricSchedulerOperation, android.os.IBinder iBinder, long j) {
        return biometricSchedulerOperation.isAuthenticationOrDetectionOperation() && biometricSchedulerOperation.isMatchingToken(iBinder) && biometricSchedulerOperation.isMatchingRequestId(j);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public com.android.server.biometrics.sensors.BaseClientMonitor getCurrentClient() {
        if (this.mCurrentOperation != null) {
            return this.mCurrentOperation.getClientMonitor();
        }
        return null;
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public void getCurrentClientIfMatches(final long j, @android.annotation.NonNull final java.util.function.Consumer<com.android.server.biometrics.sensors.BaseClientMonitor> consumer) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.BiometricScheduler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.BiometricScheduler.this.lambda$getCurrentClientIfMatches$0(j, consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCurrentClientIfMatches$0(long j, java.util.function.Consumer consumer) {
        if (this.mCurrentOperation != null && this.mCurrentOperation.isMatchingRequestId(j)) {
            consumer.accept(this.mCurrentOperation.getClientMonitor());
        } else {
            consumer.accept(null);
        }
    }

    public int getCurrentPendingCount() {
        return this.mPendingOperations.size();
    }

    public void recordCrashState() {
        if (this.mCrashStates.size() >= 10) {
            this.mCrashStates.removeFirst();
        }
        java.lang.String format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", java.util.Locale.US).format(new java.util.Date(java.lang.System.currentTimeMillis()));
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.android.server.biometrics.sensors.BiometricSchedulerOperation> it = this.mPendingOperations.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().toString());
        }
        com.android.server.biometrics.sensors.BiometricScheduler.CrashState crashState = new com.android.server.biometrics.sensors.BiometricScheduler.CrashState(format, this.mCurrentOperation != null ? this.mCurrentOperation.toString() : null, arrayList);
        this.mCrashStates.add(crashState);
        android.util.Slog.e(TAG, "Recorded crash state: " + crashState.toString());
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("Dump of BiometricScheduler BiometricScheduler");
        printWriter.println("Type: " + this.mSensorType);
        printWriter.println("Current operation: " + this.mCurrentOperation);
        printWriter.println("Pending operations: " + this.mPendingOperations.size());
        java.util.Iterator<com.android.server.biometrics.sensors.BiometricSchedulerOperation> it = this.mPendingOperations.iterator();
        while (it.hasNext()) {
            printWriter.println("Pending operation: " + it.next());
        }
        java.util.Iterator<com.android.server.biometrics.sensors.BiometricScheduler.CrashState> it2 = this.mCrashStates.iterator();
        while (it2.hasNext()) {
            printWriter.println("Crash State " + it2.next());
        }
    }

    public byte[] dumpProtoState(boolean z) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        protoOutputStream.write(1159641169921L, this.mCurrentOperation != null ? this.mCurrentOperation.getProtoEnum() : 0);
        protoOutputStream.write(1120986464258L, this.mTotalOperationsHandled);
        if (this.mRecentOperations.isEmpty()) {
            protoOutputStream.write(2259152797699L, 0);
        } else {
            for (int i = 0; i < this.mRecentOperations.size(); i++) {
                protoOutputStream.write(2259152797699L, this.mRecentOperations.get(i).intValue());
            }
        }
        protoOutputStream.flush();
        if (z) {
            this.mRecentOperations.clear();
        }
        return protoOutputStream.getBytes();
    }

    public void reset() {
        android.util.Slog.d(TAG, "Resetting scheduler");
        this.mPendingOperations.clear();
        this.mCurrentOperation = null;
    }

    private void clearScheduler() {
        if (this.mCurrentOperation == null) {
            return;
        }
        for (com.android.server.biometrics.sensors.BiometricSchedulerOperation biometricSchedulerOperation : this.mPendingOperations) {
            android.util.Slog.d(TAG, "[Watchdog cancelling pending] " + biometricSchedulerOperation.getClientMonitor());
            biometricSchedulerOperation.markCancelingForWatchdog();
        }
        android.util.Slog.d(TAG, "[Watchdog cancelling current] " + this.mCurrentOperation.getClientMonitor());
        this.mCurrentOperation.cancel(this.mHandler, getInternalCallback());
    }

    public void startWatchdog() {
        if (this.mCurrentOperation == null) {
            return;
        }
        final com.android.server.biometrics.sensors.BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.BiometricScheduler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.BiometricScheduler.this.lambda$startWatchdog$1(biometricSchedulerOperation);
            }
        }, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startWatchdog$1(com.android.server.biometrics.sensors.BiometricSchedulerOperation biometricSchedulerOperation) {
        if (biometricSchedulerOperation == this.mCurrentOperation && !biometricSchedulerOperation.isFinished()) {
            com.android.modules.expresslog.Counter.logIncrement("biometric.value_scheduler_watchdog_triggered_count");
            clearScheduler();
        }
    }

    public void onUserStopped() {
        if (this.mStopUserClient == null) {
            android.util.Slog.e(TAG, "Unexpected onUserStopped");
            return;
        }
        android.util.Slog.d(TAG, "[OnUserStopped]: " + this.mStopUserClient);
        this.mStopUserClient.onUserStopped();
        this.mStopUserClient = null;
    }

    public android.os.Handler getHandler() {
        return this.mHandler;
    }

    @android.annotation.Nullable
    public com.android.server.biometrics.sensors.StopUserClient<?> getStopUserClient() {
        return this.mStopUserClient;
    }
}
