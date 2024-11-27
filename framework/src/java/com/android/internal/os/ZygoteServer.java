package com.android.internal.os;

/* loaded from: classes4.dex */
class ZygoteServer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int INVALID_TIMESTAMP = -1;
    public static final java.lang.String TAG = "ZygoteServer";
    private boolean mCloseSocketFd;
    private boolean mIsFirstPropertyCheck;
    private boolean mIsForkChild;
    private long mLastPropCheckTimestamp;
    private boolean mUsapPoolEnabled;
    private final java.io.FileDescriptor mUsapPoolEventFD;
    private com.android.internal.os.ZygoteServer.UsapPoolRefillAction mUsapPoolRefillAction;
    private int mUsapPoolRefillDelayMs;
    private int mUsapPoolRefillThreshold;
    private long mUsapPoolRefillTriggerTimestamp;
    private int mUsapPoolSizeMax;
    private int mUsapPoolSizeMin;
    private final android.net.LocalServerSocket mUsapPoolSocket;
    private final boolean mUsapPoolSupported;
    private android.net.LocalServerSocket mZygoteSocket;

    private enum UsapPoolRefillAction {
        DELAYED,
        IMMEDIATE,
        NONE
    }

    ZygoteServer() {
        this.mUsapPoolEnabled = false;
        this.mUsapPoolSizeMax = 0;
        this.mUsapPoolSizeMin = 0;
        this.mUsapPoolRefillThreshold = 0;
        this.mUsapPoolRefillDelayMs = -1;
        this.mIsFirstPropertyCheck = true;
        this.mLastPropCheckTimestamp = 0L;
        this.mUsapPoolEventFD = null;
        this.mZygoteSocket = null;
        this.mUsapPoolSocket = null;
        this.mUsapPoolSupported = false;
    }

    ZygoteServer(boolean z) {
        this.mUsapPoolEnabled = false;
        this.mUsapPoolSizeMax = 0;
        this.mUsapPoolSizeMin = 0;
        this.mUsapPoolRefillThreshold = 0;
        this.mUsapPoolRefillDelayMs = -1;
        this.mIsFirstPropertyCheck = true;
        this.mLastPropCheckTimestamp = 0L;
        this.mUsapPoolEventFD = com.android.internal.os.Zygote.getUsapPoolEventFD();
        if (z) {
            this.mZygoteSocket = com.android.internal.os.Zygote.createManagedSocketFromInitSocket(com.android.internal.os.Zygote.PRIMARY_SOCKET_NAME);
            this.mUsapPoolSocket = com.android.internal.os.Zygote.createManagedSocketFromInitSocket(com.android.internal.os.Zygote.USAP_POOL_PRIMARY_SOCKET_NAME);
        } else {
            this.mZygoteSocket = com.android.internal.os.Zygote.createManagedSocketFromInitSocket(com.android.internal.os.Zygote.SECONDARY_SOCKET_NAME);
            this.mUsapPoolSocket = com.android.internal.os.Zygote.createManagedSocketFromInitSocket(com.android.internal.os.Zygote.USAP_POOL_SECONDARY_SOCKET_NAME);
        }
        this.mUsapPoolSupported = true;
        fetchUsapPoolPolicyProps();
    }

    void setForkChild() {
        this.mIsForkChild = true;
    }

    public boolean isUsapPoolEnabled() {
        return this.mUsapPoolEnabled;
    }

    void registerServerSocketAtAbstractName(java.lang.String str) {
        if (this.mZygoteSocket == null) {
            try {
                this.mZygoteSocket = new android.net.LocalServerSocket(str);
                this.mCloseSocketFd = false;
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException("Error binding to abstract socket '" + str + "'", e);
            }
        }
    }

    private com.android.internal.os.ZygoteConnection acceptCommandPeer(java.lang.String str) {
        try {
            return createNewConnection(this.mZygoteSocket.accept(), str);
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("IOException during accept()", e);
        }
    }

    protected com.android.internal.os.ZygoteConnection createNewConnection(android.net.LocalSocket localSocket, java.lang.String str) throws java.io.IOException {
        return new com.android.internal.os.ZygoteConnection(localSocket, str);
    }

    void closeServerSocket() {
        try {
            if (this.mZygoteSocket != null) {
                java.io.FileDescriptor fileDescriptor = this.mZygoteSocket.getFileDescriptor();
                this.mZygoteSocket.close();
                if (fileDescriptor != null && this.mCloseSocketFd) {
                    android.system.Os.close(fileDescriptor);
                }
            }
        } catch (android.system.ErrnoException e) {
            android.util.Log.e(TAG, "Zygote:  error closing descriptor", e);
        } catch (java.io.IOException e2) {
            android.util.Log.e(TAG, "Zygote:  error closing sockets", e2);
        }
        this.mZygoteSocket = null;
    }

    java.io.FileDescriptor getZygoteSocketFileDescriptor() {
        return this.mZygoteSocket.getFileDescriptor();
    }

    private void fetchUsapPoolPolicyProps() {
        if (this.mUsapPoolSupported) {
            this.mUsapPoolSizeMax = java.lang.Integer.min(com.android.internal.os.ZygoteConfig.getInt(com.android.internal.os.ZygoteConfig.USAP_POOL_SIZE_MAX, 3), 100);
            this.mUsapPoolSizeMin = java.lang.Integer.max(com.android.internal.os.ZygoteConfig.getInt(com.android.internal.os.ZygoteConfig.USAP_POOL_SIZE_MIN, 1), 1);
            this.mUsapPoolRefillThreshold = java.lang.Integer.min(com.android.internal.os.ZygoteConfig.getInt(com.android.internal.os.ZygoteConfig.USAP_POOL_REFILL_THRESHOLD, 1), this.mUsapPoolSizeMax);
            this.mUsapPoolRefillDelayMs = com.android.internal.os.ZygoteConfig.getInt(com.android.internal.os.ZygoteConfig.USAP_POOL_REFILL_DELAY_MS, 3000);
            if (this.mUsapPoolSizeMin >= this.mUsapPoolSizeMax) {
                android.util.Log.w(TAG, "The max size of the USAP pool must be greater than the minimum size.  Restoring default values.");
                this.mUsapPoolSizeMax = 3;
                this.mUsapPoolSizeMin = 1;
                this.mUsapPoolRefillThreshold = this.mUsapPoolSizeMax / 2;
            }
        }
    }

    private void fetchUsapPoolPolicyPropsWithMinInterval() {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (this.mIsFirstPropertyCheck || elapsedRealtime - this.mLastPropCheckTimestamp >= 60000) {
            this.mIsFirstPropertyCheck = false;
            this.mLastPropCheckTimestamp = elapsedRealtime;
            fetchUsapPoolPolicyProps();
        }
    }

    private void fetchUsapPoolPolicyPropsIfUnfetched() {
        if (this.mIsFirstPropertyCheck) {
            this.mIsFirstPropertyCheck = false;
            fetchUsapPoolPolicyProps();
        }
    }

    java.lang.Runnable fillUsapPool(int[] iArr, boolean z) {
        int i;
        java.lang.Runnable forkUsap;
        android.os.Trace.traceBegin(64L, "Zygote:FillUsapPool");
        fetchUsapPoolPolicyPropsIfUnfetched();
        int usapPoolCount = com.android.internal.os.Zygote.getUsapPoolCount();
        if (z) {
            i = this.mUsapPoolSizeMin - usapPoolCount;
            android.util.Log.i(com.android.internal.os.Zygote.PRIMARY_SOCKET_NAME, "Priority USAP Pool refill. New USAPs: " + i);
        } else {
            i = this.mUsapPoolSizeMax - usapPoolCount;
            android.util.Log.i(com.android.internal.os.Zygote.PRIMARY_SOCKET_NAME, "Delayed USAP Pool refill. New USAPs: " + i);
        }
        dalvik.system.ZygoteHooks.preFork();
        do {
            i--;
            if (i >= 0) {
                forkUsap = com.android.internal.os.Zygote.forkUsap(this.mUsapPoolSocket, iArr, z);
            } else {
                dalvik.system.ZygoteHooks.postForkCommon();
                resetUsapRefillState();
                android.os.Trace.traceEnd(64L);
                return null;
            }
        } while (forkUsap == null);
        return forkUsap;
    }

    java.lang.Runnable setUsapPoolStatus(boolean z, android.net.LocalSocket localSocket) {
        if (!this.mUsapPoolSupported) {
            android.util.Log.w(TAG, "Attempting to enable a USAP pool for a Zygote that doesn't support it.");
            return null;
        }
        if (this.mUsapPoolEnabled == z) {
            return null;
        }
        android.util.Log.i(TAG, "USAP Pool status change: " + (z ? "ENABLED" : "DISABLED"));
        this.mUsapPoolEnabled = z;
        if (z) {
            return fillUsapPool(new int[]{localSocket.getFileDescriptor().getInt$()}, false);
        }
        com.android.internal.os.Zygote.emptyUsapPool();
        return null;
    }

    private void resetUsapRefillState() {
        this.mUsapPoolRefillAction = com.android.internal.os.ZygoteServer.UsapPoolRefillAction.NONE;
        this.mUsapPoolRefillTriggerTimestamp = -1L;
    }

    java.lang.Runnable runSelectLoop(java.lang.String str) {
        android.system.StructPollfd[] structPollfdArr;
        int[] iArr;
        int i;
        int i2;
        int i3;
        boolean z;
        int i4;
        com.android.internal.os.ZygoteConnection zygoteConnection;
        java.lang.Runnable processCommand;
        byte[] bArr;
        int read;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        arrayList.add(this.mZygoteSocket.getFileDescriptor());
        int[] iArr2 = null;
        arrayList2.add(null);
        this.mUsapPoolRefillTriggerTimestamp = -1L;
        while (true) {
            fetchUsapPoolPolicyPropsWithMinInterval();
            this.mUsapPoolRefillAction = com.android.internal.os.ZygoteServer.UsapPoolRefillAction.NONE;
            if (this.mUsapPoolEnabled) {
                iArr = com.android.internal.os.Zygote.getUsapPipeFDs();
                structPollfdArr = new android.system.StructPollfd[arrayList.size() + 1 + iArr.length];
            } else {
                structPollfdArr = new android.system.StructPollfd[arrayList.size()];
                iArr = iArr2;
            }
            java.util.Iterator it = arrayList.iterator();
            int i5 = 0;
            while (it.hasNext()) {
                java.io.FileDescriptor fileDescriptor = (java.io.FileDescriptor) it.next();
                structPollfdArr[i5] = new android.system.StructPollfd();
                structPollfdArr[i5].fd = fileDescriptor;
                structPollfdArr[i5].events = (short) android.system.OsConstants.POLLIN;
                i5++;
            }
            if (this.mUsapPoolEnabled) {
                structPollfdArr[i5] = new android.system.StructPollfd();
                structPollfdArr[i5].fd = this.mUsapPoolEventFD;
                structPollfdArr[i5].events = (short) android.system.OsConstants.POLLIN;
                i = i5 + 1;
                for (int i6 : iArr) {
                    java.io.FileDescriptor fileDescriptor2 = new java.io.FileDescriptor();
                    fileDescriptor2.setInt$(i6);
                    structPollfdArr[i] = new android.system.StructPollfd();
                    structPollfdArr[i].fd = fileDescriptor2;
                    structPollfdArr[i].events = (short) android.system.OsConstants.POLLIN;
                    i++;
                }
            } else {
                i = i5;
            }
            if (this.mUsapPoolRefillTriggerTimestamp == -1) {
                i2 = i5;
                i3 = -1;
            } else {
                i2 = i5;
                long currentTimeMillis = java.lang.System.currentTimeMillis() - this.mUsapPoolRefillTriggerTimestamp;
                if (currentTimeMillis >= this.mUsapPoolRefillDelayMs) {
                    this.mUsapPoolRefillTriggerTimestamp = -1L;
                    this.mUsapPoolRefillAction = com.android.internal.os.ZygoteServer.UsapPoolRefillAction.DELAYED;
                    i3 = 0;
                } else {
                    i3 = currentTimeMillis <= 0 ? this.mUsapPoolRefillDelayMs : (int) (this.mUsapPoolRefillDelayMs - currentTimeMillis);
                }
            }
            try {
                if (android.system.Os.poll(structPollfdArr, i3) == 0) {
                    this.mUsapPoolRefillTriggerTimestamp = -1L;
                    this.mUsapPoolRefillAction = com.android.internal.os.ZygoteServer.UsapPoolRefillAction.DELAYED;
                    z = false;
                } else {
                    boolean z2 = false;
                    while (true) {
                        i--;
                        if (i >= 0) {
                            if ((structPollfdArr[i].revents & android.system.OsConstants.POLLIN) == 0) {
                                i4 = i2;
                            } else if (i == 0) {
                                com.android.internal.os.ZygoteConnection acceptCommandPeer = acceptCommandPeer(str);
                                arrayList2.add(acceptCommandPeer);
                                arrayList.add(acceptCommandPeer.getFileDescriptor());
                                i4 = i2;
                            } else {
                                i4 = i2;
                                if (i < i4) {
                                    try {
                                        try {
                                            zygoteConnection = (com.android.internal.os.ZygoteConnection) arrayList2.get(i);
                                            processCommand = zygoteConnection.processCommand(this, !isUsapPoolEnabled() && dalvik.system.ZygoteHooks.isIndefiniteThreadSuspensionSafe());
                                        } catch (java.lang.Exception e) {
                                            if (this.mIsForkChild) {
                                                android.util.Log.e(TAG, "Caught post-fork exception in child process.", e);
                                                throw e;
                                            }
                                            android.util.Slog.e(TAG, "Exception executing zygote command: ", e);
                                            ((com.android.internal.os.ZygoteConnection) arrayList2.remove(i)).closeSocket();
                                            arrayList.remove(i);
                                        }
                                        if (this.mIsForkChild) {
                                            if (processCommand != null) {
                                                return processCommand;
                                            }
                                            throw new java.lang.IllegalStateException("command == null");
                                        }
                                        if (processCommand != null) {
                                            throw new java.lang.IllegalStateException("command != null");
                                        }
                                        if (zygoteConnection.isClosedByPeer()) {
                                            zygoteConnection.closeSocket();
                                            arrayList2.remove(i);
                                            arrayList.remove(i);
                                        }
                                    } finally {
                                        this.mIsForkChild = false;
                                    }
                                } else {
                                    try {
                                        bArr = new byte[8];
                                        try {
                                            read = android.system.Os.read(structPollfdArr[i].fd, bArr, 0, 8);
                                        } catch (java.lang.Exception e2) {
                                            e = e2;
                                            if (i == i4) {
                                                android.util.Log.e(TAG, "Failed to read from USAP pool event FD: " + e.getMessage());
                                            } else {
                                                android.util.Log.e(TAG, "Failed to read from USAP reporting pipe: " + e.getMessage());
                                            }
                                            i2 = i4;
                                        }
                                    } catch (java.lang.Exception e3) {
                                        e = e3;
                                    }
                                    if (read == 8) {
                                        long readLong = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr)).readLong();
                                        if (i > i4) {
                                            com.android.internal.os.Zygote.removeUsapTableEntry((int) readLong);
                                        }
                                        z2 = true;
                                        i2 = i4;
                                    } else {
                                        android.util.Log.e(TAG, "Incomplete read from USAP management FD of size " + read);
                                    }
                                }
                            }
                            i2 = i4;
                        } else {
                            z = false;
                            if (z2) {
                                int usapPoolCount = com.android.internal.os.Zygote.getUsapPoolCount();
                                if (usapPoolCount < this.mUsapPoolSizeMin) {
                                    this.mUsapPoolRefillAction = com.android.internal.os.ZygoteServer.UsapPoolRefillAction.IMMEDIATE;
                                } else if (this.mUsapPoolSizeMax - usapPoolCount >= this.mUsapPoolRefillThreshold) {
                                    this.mUsapPoolRefillTriggerTimestamp = java.lang.System.currentTimeMillis();
                                }
                            }
                        }
                    }
                }
                if (this.mUsapPoolRefillAction != com.android.internal.os.ZygoteServer.UsapPoolRefillAction.NONE) {
                    int[] array = arrayList.subList(1, arrayList.size()).stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.internal.os.ZygoteServer$$ExternalSyntheticLambda0
                        @Override // java.util.function.ToIntFunction
                        public final int applyAsInt(java.lang.Object obj) {
                            return ((java.io.FileDescriptor) obj).getInt$();
                        }
                    }).toArray();
                    boolean z3 = this.mUsapPoolRefillAction != com.android.internal.os.ZygoteServer.UsapPoolRefillAction.IMMEDIATE ? z : true;
                    java.lang.Runnable fillUsapPool = fillUsapPool(array, z3);
                    if (fillUsapPool != null) {
                        return fillUsapPool;
                    }
                    if (z3) {
                        this.mUsapPoolRefillTriggerTimestamp = java.lang.System.currentTimeMillis();
                    }
                }
                iArr2 = null;
            } catch (android.system.ErrnoException e4) {
                throw new java.lang.RuntimeException("poll failed", e4);
            }
        }
    }
}
