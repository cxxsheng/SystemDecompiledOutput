package android.os;

/* loaded from: classes3.dex */
public class ZygoteProcess {
    private static final java.lang.String[] INVALID_USAP_FLAGS = {"--query-abi-list", "--get-pid", "--preload-default", "--preload-package", "--preload-app", "--start-child-zygote", "--set-api-denylist-exemptions", "--hidden-api-log-sampling-rate", "--hidden-api-statslog-sampling-rate", "--invoke-with"};
    private static final java.lang.String LOG_TAG = "ZygoteProcess";
    private static final int ZYGOTE_CONNECT_RETRY_DELAY_MS = 50;
    private static final int ZYGOTE_CONNECT_TIMEOUT_MS = 60000;
    static final int ZYGOTE_RETRY_MILLIS = 500;
    private java.util.List<java.lang.String> mApiDenylistExemptions;
    private int mHiddenApiAccessLogSampleRate;
    private int mHiddenApiAccessStatslogSampleRate;
    private boolean mIsFirstPropCheck;
    private long mLastPropCheckTimestamp;
    private final java.lang.Object mLock;
    private boolean mUsapPoolEnabled;
    private final android.net.LocalSocketAddress mUsapPoolSecondarySocketAddress;
    private final android.net.LocalSocketAddress mUsapPoolSocketAddress;
    private final boolean mUsapPoolSupported;
    private final android.net.LocalSocketAddress mZygoteSecondarySocketAddress;
    private final android.net.LocalSocketAddress mZygoteSocketAddress;
    private android.os.ZygoteProcess.ZygoteState primaryZygoteState;
    private android.os.ZygoteProcess.ZygoteState secondaryZygoteState;

    public ZygoteProcess() {
        this.mLock = new java.lang.Object();
        this.mApiDenylistExemptions = java.util.Collections.emptyList();
        this.mUsapPoolEnabled = false;
        this.mIsFirstPropCheck = true;
        this.mLastPropCheckTimestamp = 0L;
        this.mZygoteSocketAddress = new android.net.LocalSocketAddress(com.android.internal.os.Zygote.PRIMARY_SOCKET_NAME, android.net.LocalSocketAddress.Namespace.RESERVED);
        this.mZygoteSecondarySocketAddress = new android.net.LocalSocketAddress(com.android.internal.os.Zygote.SECONDARY_SOCKET_NAME, android.net.LocalSocketAddress.Namespace.RESERVED);
        this.mUsapPoolSocketAddress = new android.net.LocalSocketAddress(com.android.internal.os.Zygote.USAP_POOL_PRIMARY_SOCKET_NAME, android.net.LocalSocketAddress.Namespace.RESERVED);
        this.mUsapPoolSecondarySocketAddress = new android.net.LocalSocketAddress(com.android.internal.os.Zygote.USAP_POOL_SECONDARY_SOCKET_NAME, android.net.LocalSocketAddress.Namespace.RESERVED);
        this.mUsapPoolSupported = true;
    }

    public ZygoteProcess(android.net.LocalSocketAddress localSocketAddress, android.net.LocalSocketAddress localSocketAddress2) {
        this.mLock = new java.lang.Object();
        this.mApiDenylistExemptions = java.util.Collections.emptyList();
        this.mUsapPoolEnabled = false;
        this.mIsFirstPropCheck = true;
        this.mLastPropCheckTimestamp = 0L;
        this.mZygoteSocketAddress = localSocketAddress;
        this.mZygoteSecondarySocketAddress = localSocketAddress2;
        this.mUsapPoolSocketAddress = null;
        this.mUsapPoolSecondarySocketAddress = null;
        this.mUsapPoolSupported = false;
    }

    public android.net.LocalSocketAddress getPrimarySocketAddress() {
        return this.mZygoteSocketAddress;
    }

    private static class ZygoteState implements java.lang.AutoCloseable {
        private final java.util.List<java.lang.String> mAbiList;
        private boolean mClosed;
        final android.net.LocalSocketAddress mUsapSocketAddress;
        final java.io.DataInputStream mZygoteInputStream;
        final java.io.BufferedWriter mZygoteOutputWriter;
        private final android.net.LocalSocket mZygoteSessionSocket;
        final android.net.LocalSocketAddress mZygoteSocketAddress;

        private ZygoteState(android.net.LocalSocketAddress localSocketAddress, android.net.LocalSocketAddress localSocketAddress2, android.net.LocalSocket localSocket, java.io.DataInputStream dataInputStream, java.io.BufferedWriter bufferedWriter, java.util.List<java.lang.String> list) {
            this.mZygoteSocketAddress = localSocketAddress;
            this.mUsapSocketAddress = localSocketAddress2;
            this.mZygoteSessionSocket = localSocket;
            this.mZygoteInputStream = dataInputStream;
            this.mZygoteOutputWriter = bufferedWriter;
            this.mAbiList = list;
        }

        static android.os.ZygoteProcess.ZygoteState connect(android.net.LocalSocketAddress localSocketAddress, android.net.LocalSocketAddress localSocketAddress2) throws java.io.IOException {
            android.net.LocalSocket localSocket = new android.net.LocalSocket();
            if (localSocketAddress == null) {
                throw new java.lang.IllegalArgumentException("zygoteSocketAddress can't be null");
            }
            try {
                localSocket.connect(localSocketAddress);
                java.io.DataInputStream dataInputStream = new java.io.DataInputStream(localSocket.getInputStream());
                java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(localSocket.getOutputStream()), 256);
                return new android.os.ZygoteProcess.ZygoteState(localSocketAddress, localSocketAddress2, localSocket, dataInputStream, bufferedWriter, android.os.ZygoteProcess.getAbiList(bufferedWriter, dataInputStream));
            } catch (java.io.IOException e) {
                try {
                    localSocket.close();
                } catch (java.io.IOException e2) {
                }
                throw e;
            }
        }

        android.net.LocalSocket getUsapSessionSocket() throws java.io.IOException {
            android.net.LocalSocket localSocket = new android.net.LocalSocket();
            localSocket.connect(this.mUsapSocketAddress);
            return localSocket;
        }

        boolean matches(java.lang.String str) {
            return this.mAbiList.contains(str);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            try {
                this.mZygoteSessionSocket.close();
            } catch (java.io.IOException e) {
                android.util.Log.e(android.os.ZygoteProcess.LOG_TAG, "I/O exception on routine close", e);
            }
            this.mClosed = true;
        }

        boolean isClosed() {
            return this.mClosed;
        }
    }

    public final android.os.Process.ProcessStartResult start(java.lang.String str, java.lang.String str2, int i, int i2, int[] iArr, int i3, int i4, int i5, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, int i6, boolean z, long[] jArr, java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> map, java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> map2, boolean z2, boolean z3, boolean z4, java.lang.String[] strArr) {
        if (fetchUsapPoolEnabledPropWithMinInterval()) {
            informZygotesOfUsapPoolStatus();
        }
        try {
            return startViaZygote(str, str2, i, i2, iArr, i3, i4, i5, str3, str4, str5, str6, str7, false, str8, i6, z, jArr, map, map2, z2, z3, z4, strArr);
        } catch (android.os.ZygoteStartFailedEx e) {
            android.util.Log.e(LOG_TAG, "Starting VM process through Zygote failed");
            throw new java.lang.RuntimeException("Starting VM process through Zygote failed", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<java.lang.String> getAbiList(java.io.BufferedWriter bufferedWriter, java.io.DataInputStream dataInputStream) throws java.io.IOException {
        bufferedWriter.write("1");
        bufferedWriter.newLine();
        bufferedWriter.write("--query-abi-list");
        bufferedWriter.newLine();
        bufferedWriter.flush();
        byte[] bArr = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(bArr);
        return java.util.Arrays.asList(new java.lang.String(bArr, java.nio.charset.StandardCharsets.US_ASCII).split(","));
    }

    private android.os.Process.ProcessStartResult zygoteSendArgsAndGetResult(android.os.ZygoteProcess.ZygoteState zygoteState, int i, java.util.ArrayList<java.lang.String> arrayList) throws android.os.ZygoteStartFailedEx {
        java.util.Iterator<java.lang.String> it = arrayList.iterator();
        while (it.hasNext()) {
            java.lang.String next = it.next();
            if (next.indexOf(10) >= 0) {
                throw new android.os.ZygoteStartFailedEx("Embedded newlines not allowed");
            }
            if (next.indexOf(13) >= 0) {
                throw new android.os.ZygoteStartFailedEx("Embedded carriage returns not allowed");
            }
            if (next.indexOf(0) >= 0) {
                throw new android.os.ZygoteStartFailedEx("Embedded nulls not allowed");
            }
        }
        java.lang.String str = arrayList.size() + "\n" + java.lang.String.join("\n", arrayList) + "\n";
        if (shouldAttemptUsapLaunch(i, arrayList)) {
            try {
                return attemptUsapSendArgsAndGetResult(zygoteState, str);
            } catch (java.io.IOException e) {
                android.util.Log.e(LOG_TAG, "IO Exception while communicating with USAP pool - " + e.getMessage());
            }
        }
        return attemptZygoteSendArgsAndGetResult(zygoteState, str);
    }

    private android.os.Process.ProcessStartResult attemptZygoteSendArgsAndGetResult(android.os.ZygoteProcess.ZygoteState zygoteState, java.lang.String str) throws android.os.ZygoteStartFailedEx {
        try {
            java.io.BufferedWriter bufferedWriter = zygoteState.mZygoteOutputWriter;
            java.io.DataInputStream dataInputStream = zygoteState.mZygoteInputStream;
            bufferedWriter.write(str);
            bufferedWriter.flush();
            android.os.Process.ProcessStartResult processStartResult = new android.os.Process.ProcessStartResult();
            processStartResult.pid = dataInputStream.readInt();
            processStartResult.usingWrapper = dataInputStream.readBoolean();
            if (processStartResult.pid < 0) {
                throw new android.os.ZygoteStartFailedEx("fork() failed");
            }
            return processStartResult;
        } catch (java.io.IOException e) {
            zygoteState.close();
            android.util.Log.e(LOG_TAG, "IO Exception while communicating with Zygote - " + e.toString());
            throw new android.os.ZygoteStartFailedEx(e);
        }
    }

    private android.os.Process.ProcessStartResult attemptUsapSendArgsAndGetResult(android.os.ZygoteProcess.ZygoteState zygoteState, java.lang.String str) throws android.os.ZygoteStartFailedEx, java.io.IOException {
        android.net.LocalSocket usapSessionSocket = zygoteState.getUsapSessionSocket();
        try {
            java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(usapSessionSocket.getOutputStream()), 256);
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(usapSessionSocket.getInputStream());
            bufferedWriter.write(str);
            bufferedWriter.flush();
            android.os.Process.ProcessStartResult processStartResult = new android.os.Process.ProcessStartResult();
            processStartResult.pid = dataInputStream.readInt();
            processStartResult.usingWrapper = false;
            if (processStartResult.pid < 0) {
                throw new android.os.ZygoteStartFailedEx("USAP specialization failed");
            }
            if (usapSessionSocket != null) {
                usapSessionSocket.close();
            }
            return processStartResult;
        } catch (java.lang.Throwable th) {
            if (usapSessionSocket != null) {
                try {
                    usapSessionSocket.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private boolean shouldAttemptUsapLaunch(int i, java.util.ArrayList<java.lang.String> arrayList) {
        return this.mUsapPoolSupported && this.mUsapPoolEnabled && policySpecifiesUsapPoolLaunch(i) && commandSupportedByUsap(arrayList);
    }

    private static boolean policySpecifiesUsapPoolLaunch(int i) {
        return (i & 5) == 1;
    }

    private static boolean commandSupportedByUsap(java.util.ArrayList<java.lang.String> arrayList) {
        java.util.Iterator<java.lang.String> it = arrayList.iterator();
        while (it.hasNext()) {
            java.lang.String next = it.next();
            for (java.lang.String str : INVALID_USAP_FLAGS) {
                if (next.startsWith(str)) {
                    return false;
                }
            }
            if (next.startsWith("--nice-name=") && com.android.internal.os.Zygote.getWrapProperty(next.substring(12)) != null) {
                return false;
            }
        }
        return true;
    }

    private android.os.Process.ProcessStartResult startViaZygote(java.lang.String str, java.lang.String str2, int i, int i2, int[] iArr, int i3, int i4, int i5, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7, boolean z, java.lang.String str8, int i6, boolean z2, long[] jArr, java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> map, java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> map2, boolean z3, boolean z4, boolean z5, java.lang.String[] strArr) throws android.os.ZygoteStartFailedEx {
        android.os.Process.ProcessStartResult zygoteSendArgsAndGetResult;
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        arrayList.add("--runtime-args");
        arrayList.add("--setuid=" + i);
        arrayList.add("--setgid=" + i2);
        arrayList.add("--runtime-flags=" + i3);
        if (i4 == 1) {
            arrayList.add("--mount-external-default");
        } else if (i4 == 2) {
            arrayList.add("--mount-external-installer");
        } else if (i4 == 3) {
            arrayList.add("--mount-external-pass-through");
        } else if (i4 == 4) {
            arrayList.add("--mount-external-android-writable");
        }
        arrayList.add("--target-sdk-version=" + i5);
        if (iArr != null && iArr.length > 0) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("--setgroups=");
            int length = iArr.length;
            for (int i7 = 0; i7 < length; i7++) {
                if (i7 != 0) {
                    sb.append(',');
                }
                sb.append(iArr[i7]);
            }
            arrayList.add(sb.toString());
        }
        if (str2 != null) {
            arrayList.add("--nice-name=" + str2);
        }
        if (str3 != null) {
            arrayList.add("--seinfo=" + str3);
        }
        if (str5 != null) {
            arrayList.add("--instruction-set=" + str5);
        }
        if (str6 != null) {
            arrayList.add("--app-data-dir=" + str6);
        }
        if (str7 != null) {
            arrayList.add("--invoke-with");
            arrayList.add(str7);
        }
        if (z) {
            arrayList.add("--start-child-zygote");
        }
        if (str8 != null) {
            arrayList.add("--package-name=" + str8);
        }
        if (z2) {
            arrayList.add(com.android.internal.os.Zygote.START_AS_TOP_APP_ARG);
        }
        if (map != null && map.size() > 0) {
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append(com.android.internal.os.Zygote.PKG_DATA_INFO_MAP);
            sb2.append("=");
            boolean z6 = false;
            for (java.util.Map.Entry<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> entry : map.entrySet()) {
                if (z6) {
                    sb2.append(',');
                }
                sb2.append(entry.getKey());
                sb2.append(',');
                sb2.append(entry.getValue().first);
                sb2.append(',');
                sb2.append(entry.getValue().second);
                z6 = true;
            }
            arrayList.add(sb2.toString());
        }
        if (map2 != null && map2.size() > 0) {
            java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
            sb3.append(com.android.internal.os.Zygote.ALLOWLISTED_DATA_INFO_MAP);
            sb3.append("=");
            boolean z7 = false;
            for (java.util.Map.Entry<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> entry2 : map2.entrySet()) {
                if (z7) {
                    sb3.append(',');
                }
                sb3.append(entry2.getKey());
                sb3.append(',');
                sb3.append(entry2.getValue().first);
                sb3.append(',');
                sb3.append(entry2.getValue().second);
                z7 = true;
            }
            arrayList.add(sb3.toString());
        }
        if (z4) {
            arrayList.add(com.android.internal.os.Zygote.BIND_MOUNT_APP_STORAGE_DIRS);
        }
        if (z3) {
            arrayList.add(com.android.internal.os.Zygote.BIND_MOUNT_APP_DATA_DIRS);
        }
        if (z5) {
            arrayList.add(com.android.internal.os.Zygote.BIND_MOUNT_SYSPROP_OVERRIDES);
        }
        if (jArr != null && jArr.length > 0) {
            java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
            sb4.append("--disabled-compat-changes=");
            int length2 = jArr.length;
            for (int i8 = 0; i8 < length2; i8++) {
                if (i8 != 0) {
                    sb4.append(',');
                }
                sb4.append(jArr[i8]);
            }
            arrayList.add(sb4.toString());
        }
        arrayList.add(str);
        if (strArr != null) {
            java.util.Collections.addAll(arrayList, strArr);
        }
        synchronized (this.mLock) {
            zygoteSendArgsAndGetResult = zygoteSendArgsAndGetResult(openZygoteSocketIfNeeded(str4), i6, arrayList);
        }
        return zygoteSendArgsAndGetResult;
    }

    private boolean fetchUsapPoolEnabledProp() {
        boolean z = this.mUsapPoolEnabled;
        this.mUsapPoolEnabled = com.android.internal.os.ZygoteConfig.getBool(com.android.internal.os.ZygoteConfig.USAP_POOL_ENABLED, false);
        boolean z2 = z != this.mUsapPoolEnabled;
        if (z2) {
            android.util.Log.i(LOG_TAG, "usapPoolEnabled = " + this.mUsapPoolEnabled);
        }
        return z2;
    }

    private boolean fetchUsapPoolEnabledPropWithMinInterval() {
        if (!this.mUsapPoolSupported) {
            return false;
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (!this.mIsFirstPropCheck && elapsedRealtime - this.mLastPropCheckTimestamp < 60000) {
            return false;
        }
        this.mIsFirstPropCheck = false;
        this.mLastPropCheckTimestamp = elapsedRealtime;
        return fetchUsapPoolEnabledProp();
    }

    public void close() {
        if (this.primaryZygoteState != null) {
            this.primaryZygoteState.close();
        }
        if (this.secondaryZygoteState != null) {
            this.secondaryZygoteState.close();
        }
    }

    public void establishZygoteConnectionForAbi(java.lang.String str) {
        try {
            synchronized (this.mLock) {
                openZygoteSocketIfNeeded(str);
            }
        } catch (android.os.ZygoteStartFailedEx e) {
            throw new java.lang.RuntimeException("Unable to connect to zygote for abi: " + str, e);
        }
    }

    public int getZygotePid(java.lang.String str) {
        int parseInt;
        try {
            synchronized (this.mLock) {
                android.os.ZygoteProcess.ZygoteState openZygoteSocketIfNeeded = openZygoteSocketIfNeeded(str);
                openZygoteSocketIfNeeded.mZygoteOutputWriter.write("1");
                openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
                openZygoteSocketIfNeeded.mZygoteOutputWriter.write("--get-pid");
                openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
                openZygoteSocketIfNeeded.mZygoteOutputWriter.flush();
                byte[] bArr = new byte[openZygoteSocketIfNeeded.mZygoteInputStream.readInt()];
                openZygoteSocketIfNeeded.mZygoteInputStream.readFully(bArr);
                parseInt = java.lang.Integer.parseInt(new java.lang.String(bArr, java.nio.charset.StandardCharsets.US_ASCII));
            }
            return parseInt;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("Failure retrieving pid", e);
        }
    }

    public void bootCompleted() {
        if (android.os.Build.SUPPORTED_32_BIT_ABIS.length > 0) {
            bootCompleted(android.os.Build.SUPPORTED_32_BIT_ABIS[0]);
        }
        if (android.os.Build.SUPPORTED_64_BIT_ABIS.length > 0) {
            bootCompleted(android.os.Build.SUPPORTED_64_BIT_ABIS[0]);
        }
    }

    private void bootCompleted(java.lang.String str) {
        try {
            synchronized (this.mLock) {
                android.os.ZygoteProcess.ZygoteState openZygoteSocketIfNeeded = openZygoteSocketIfNeeded(str);
                openZygoteSocketIfNeeded.mZygoteOutputWriter.write("1\n--boot-completed\n");
                openZygoteSocketIfNeeded.mZygoteOutputWriter.flush();
                openZygoteSocketIfNeeded.mZygoteInputStream.readInt();
            }
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("Failed to inform zygote of boot_completed", e);
        }
    }

    public boolean setApiDenylistExemptions(java.util.List<java.lang.String> list) {
        boolean maybeSetApiDenylistExemptions;
        synchronized (this.mLock) {
            this.mApiDenylistExemptions = list;
            maybeSetApiDenylistExemptions = maybeSetApiDenylistExemptions(this.primaryZygoteState, true);
            if (maybeSetApiDenylistExemptions) {
                maybeSetApiDenylistExemptions = maybeSetApiDenylistExemptions(this.secondaryZygoteState, true);
            }
        }
        return maybeSetApiDenylistExemptions;
    }

    public void setHiddenApiAccessLogSampleRate(int i) {
        synchronized (this.mLock) {
            this.mHiddenApiAccessLogSampleRate = i;
            maybeSetHiddenApiAccessLogSampleRate(this.primaryZygoteState);
            maybeSetHiddenApiAccessLogSampleRate(this.secondaryZygoteState);
        }
    }

    public void setHiddenApiAccessStatslogSampleRate(int i) {
        synchronized (this.mLock) {
            this.mHiddenApiAccessStatslogSampleRate = i;
            maybeSetHiddenApiAccessStatslogSampleRate(this.primaryZygoteState);
            maybeSetHiddenApiAccessStatslogSampleRate(this.secondaryZygoteState);
        }
    }

    private boolean maybeSetApiDenylistExemptions(android.os.ZygoteProcess.ZygoteState zygoteState, boolean z) {
        if (zygoteState == null || zygoteState.isClosed()) {
            android.util.Slog.e(LOG_TAG, "Can't set API denylist exemptions: no zygote connection");
            return false;
        }
        if (!z && this.mApiDenylistExemptions.isEmpty()) {
            return true;
        }
        for (java.lang.String str : this.mApiDenylistExemptions) {
            if (str.indexOf(10) >= 0 || str.indexOf(13) >= 0 || str.indexOf(0) >= 0) {
                android.util.Slog.e(LOG_TAG, "Failed to set API denylist exemptions: Bad character");
                this.mApiDenylistExemptions = java.util.Collections.emptyList();
                return false;
            }
        }
        try {
            zygoteState.mZygoteOutputWriter.write(java.lang.Integer.toString(this.mApiDenylistExemptions.size() + 1));
            zygoteState.mZygoteOutputWriter.newLine();
            zygoteState.mZygoteOutputWriter.write("--set-api-denylist-exemptions");
            zygoteState.mZygoteOutputWriter.newLine();
            for (int i = 0; i < this.mApiDenylistExemptions.size(); i++) {
                zygoteState.mZygoteOutputWriter.write(this.mApiDenylistExemptions.get(i));
                zygoteState.mZygoteOutputWriter.newLine();
            }
            zygoteState.mZygoteOutputWriter.flush();
            int readInt = zygoteState.mZygoteInputStream.readInt();
            if (readInt != 0) {
                android.util.Slog.e(LOG_TAG, "Failed to set API denylist exemptions; status " + readInt);
            }
            return true;
        } catch (java.io.IOException e) {
            android.util.Slog.e(LOG_TAG, "Failed to set API denylist exemptions", e);
            this.mApiDenylistExemptions = java.util.Collections.emptyList();
            return false;
        }
    }

    private void maybeSetHiddenApiAccessLogSampleRate(android.os.ZygoteProcess.ZygoteState zygoteState) {
        if (zygoteState == null || zygoteState.isClosed() || this.mHiddenApiAccessLogSampleRate == -1) {
            return;
        }
        try {
            zygoteState.mZygoteOutputWriter.write(java.lang.Integer.toString(1));
            zygoteState.mZygoteOutputWriter.newLine();
            zygoteState.mZygoteOutputWriter.write("--hidden-api-log-sampling-rate=" + this.mHiddenApiAccessLogSampleRate);
            zygoteState.mZygoteOutputWriter.newLine();
            zygoteState.mZygoteOutputWriter.flush();
            int readInt = zygoteState.mZygoteInputStream.readInt();
            if (readInt != 0) {
                android.util.Slog.e(LOG_TAG, "Failed to set hidden API log sampling rate; status " + readInt);
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(LOG_TAG, "Failed to set hidden API log sampling rate", e);
        }
    }

    private void maybeSetHiddenApiAccessStatslogSampleRate(android.os.ZygoteProcess.ZygoteState zygoteState) {
        if (zygoteState == null || zygoteState.isClosed() || this.mHiddenApiAccessStatslogSampleRate == -1) {
            return;
        }
        try {
            zygoteState.mZygoteOutputWriter.write(java.lang.Integer.toString(1));
            zygoteState.mZygoteOutputWriter.newLine();
            zygoteState.mZygoteOutputWriter.write("--hidden-api-statslog-sampling-rate=" + this.mHiddenApiAccessStatslogSampleRate);
            zygoteState.mZygoteOutputWriter.newLine();
            zygoteState.mZygoteOutputWriter.flush();
            int readInt = zygoteState.mZygoteInputStream.readInt();
            if (readInt != 0) {
                android.util.Slog.e(LOG_TAG, "Failed to set hidden API statslog sampling rate; status " + readInt);
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(LOG_TAG, "Failed to set hidden API statslog sampling rate", e);
        }
    }

    private void attemptConnectionToPrimaryZygote() throws java.io.IOException {
        if (this.primaryZygoteState == null || this.primaryZygoteState.isClosed()) {
            this.primaryZygoteState = android.os.ZygoteProcess.ZygoteState.connect(this.mZygoteSocketAddress, this.mUsapPoolSocketAddress);
            maybeSetApiDenylistExemptions(this.primaryZygoteState, false);
            maybeSetHiddenApiAccessLogSampleRate(this.primaryZygoteState);
        }
    }

    private void attemptConnectionToSecondaryZygote() throws java.io.IOException {
        if (this.secondaryZygoteState == null || this.secondaryZygoteState.isClosed()) {
            this.secondaryZygoteState = android.os.ZygoteProcess.ZygoteState.connect(this.mZygoteSecondarySocketAddress, this.mUsapPoolSecondarySocketAddress);
            maybeSetApiDenylistExemptions(this.secondaryZygoteState, false);
            maybeSetHiddenApiAccessLogSampleRate(this.secondaryZygoteState);
        }
    }

    private android.os.ZygoteProcess.ZygoteState openZygoteSocketIfNeeded(java.lang.String str) throws android.os.ZygoteStartFailedEx {
        try {
            attemptConnectionToPrimaryZygote();
            if (this.primaryZygoteState.matches(str)) {
                return this.primaryZygoteState;
            }
            if (this.mZygoteSecondarySocketAddress != null) {
                attemptConnectionToSecondaryZygote();
                if (this.secondaryZygoteState.matches(str)) {
                    return this.secondaryZygoteState;
                }
            }
            throw new android.os.ZygoteStartFailedEx("Unsupported zygote ABI: " + str);
        } catch (java.io.IOException e) {
            throw new android.os.ZygoteStartFailedEx("Error connecting to zygote", e);
        }
    }

    public boolean preloadApp(android.content.pm.ApplicationInfo applicationInfo, java.lang.String str) throws android.os.ZygoteStartFailedEx, java.io.IOException {
        boolean z;
        synchronized (this.mLock) {
            android.os.ZygoteProcess.ZygoteState openZygoteSocketIfNeeded = openZygoteSocketIfNeeded(str);
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write("2");
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write("--preload-app");
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            android.os.Parcel obtain = android.os.Parcel.obtain();
            applicationInfo.writeToParcel(obtain, 0);
            java.lang.String encodeToString = java.util.Base64.getEncoder().encodeToString(obtain.marshall());
            obtain.recycle();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write(encodeToString);
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.flush();
            z = openZygoteSocketIfNeeded.mZygoteInputStream.readInt() == 0;
        }
        return z;
    }

    public boolean preloadPackageForAbi(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) throws android.os.ZygoteStartFailedEx, java.io.IOException {
        boolean z;
        synchronized (this.mLock) {
            android.os.ZygoteProcess.ZygoteState openZygoteSocketIfNeeded = openZygoteSocketIfNeeded(str5);
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write("5");
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write("--preload-package");
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write(str);
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write(str2);
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write(str3);
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write(str4);
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.flush();
            z = openZygoteSocketIfNeeded.mZygoteInputStream.readInt() == 0;
        }
        return z;
    }

    public boolean preloadDefault(java.lang.String str) throws android.os.ZygoteStartFailedEx, java.io.IOException {
        boolean z;
        synchronized (this.mLock) {
            android.os.ZygoteProcess.ZygoteState openZygoteSocketIfNeeded = openZygoteSocketIfNeeded(str);
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write("1");
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.write("--preload-default");
            openZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            openZygoteSocketIfNeeded.mZygoteOutputWriter.flush();
            z = openZygoteSocketIfNeeded.mZygoteInputStream.readInt() == 0;
        }
        return z;
    }

    public static void waitForConnectionToZygote(java.lang.String str) {
        waitForConnectionToZygote(new android.net.LocalSocketAddress(str, android.net.LocalSocketAddress.Namespace.RESERVED));
    }

    public static void waitForConnectionToZygote(android.net.LocalSocketAddress localSocketAddress) {
        for (int i = 1200; i >= 0; i--) {
            try {
                android.os.ZygoteProcess.ZygoteState.connect(localSocketAddress, null).close();
                return;
            } catch (java.io.IOException e) {
                android.util.Log.w(LOG_TAG, "Got error connecting to zygote, retrying. msg= " + e.getMessage());
                try {
                    java.lang.Thread.sleep(50L);
                } catch (java.lang.InterruptedException e2) {
                }
            }
        }
        android.util.Slog.wtf(LOG_TAG, "Failed to connect to Zygote through socket " + localSocketAddress.getName());
    }

    private void informZygotesOfUsapPoolStatus() {
        java.lang.String str = "1\n--usap-pool-enabled=" + this.mUsapPoolEnabled + "\n";
        synchronized (this.mLock) {
            try {
                try {
                    attemptConnectionToPrimaryZygote();
                    this.primaryZygoteState.mZygoteOutputWriter.write(str);
                    this.primaryZygoteState.mZygoteOutputWriter.flush();
                    if (this.mZygoteSecondarySocketAddress != null) {
                        try {
                            attemptConnectionToSecondaryZygote();
                            try {
                                this.secondaryZygoteState.mZygoteOutputWriter.write(str);
                                this.secondaryZygoteState.mZygoteOutputWriter.flush();
                                this.secondaryZygoteState.mZygoteInputStream.readInt();
                            } catch (java.io.IOException e) {
                                throw new java.lang.IllegalStateException("USAP pool state change cause an irrecoverable error", e);
                            }
                        } catch (java.io.IOException e2) {
                        }
                    }
                    try {
                        this.primaryZygoteState.mZygoteInputStream.readInt();
                    } catch (java.io.IOException e3) {
                        throw new java.lang.IllegalStateException("USAP pool state change cause an irrecoverable error", e3);
                    }
                } catch (java.io.IOException e4) {
                    this.mUsapPoolEnabled = !this.mUsapPoolEnabled;
                    android.util.Log.w(LOG_TAG, "Failed to inform zygotes of USAP pool status: " + e4.getMessage());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.os.ChildZygoteProcess startChildZygote(java.lang.String str, java.lang.String str2, int i, int i2, int[] iArr, int i3, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, int i4, int i5) {
        android.net.LocalSocketAddress localSocketAddress = new android.net.LocalSocketAddress(str + "/" + java.util.UUID.randomUUID().toString());
        try {
            return new android.os.ChildZygoteProcess(localSocketAddress, startViaZygote(str, str2, i, i2, iArr, i3, 0, 0, str3, str4, str6, null, null, true, null, 4, false, null, null, null, true, false, false, new java.lang.String[]{com.android.internal.os.Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG + localSocketAddress.getName(), com.android.internal.os.Zygote.CHILD_ZYGOTE_ABI_LIST_ARG + str5, com.android.internal.os.Zygote.CHILD_ZYGOTE_UID_RANGE_START + i4, com.android.internal.os.Zygote.CHILD_ZYGOTE_UID_RANGE_END + i5}).pid);
        } catch (android.os.ZygoteStartFailedEx e) {
            throw new java.lang.RuntimeException("Starting child-zygote through Zygote failed", e);
        }
    }
}
