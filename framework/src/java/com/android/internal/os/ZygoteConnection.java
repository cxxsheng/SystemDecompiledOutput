package com.android.internal.os;

/* loaded from: classes4.dex */
class ZygoteConnection {
    private static final java.lang.String TAG = "Zygote";
    private final java.lang.String abiList;
    private boolean isEof;
    private final android.net.LocalSocket mSocket;
    private final java.io.DataOutputStream mSocketOutStream;
    private final android.net.Credentials peer;

    ZygoteConnection(android.net.LocalSocket localSocket, java.lang.String str) throws java.io.IOException {
        this.mSocket = localSocket;
        this.abiList = str;
        this.mSocketOutStream = new java.io.DataOutputStream(localSocket.getOutputStream());
        this.mSocket.setSoTimeout(1000);
        try {
            this.peer = this.mSocket.getPeerCredentials();
            if (this.peer.getUid() != 1000) {
                throw new com.android.internal.os.ZygoteSecurityException("Only system UID is allowed to connect to Zygote.");
            }
            this.isEof = false;
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Cannot read peer credentials", e);
            throw e;
        }
    }

    java.io.FileDescriptor getFileDescriptor() {
        return this.mSocket.getFileDescriptor();
    }

    /* JADX WARN: Code restructure failed: missing block: B:120:0x0239, code lost:
    
        throw new com.android.internal.os.ZygoteSecurityException("Client may not specify capabilities: permitted=0x" + java.lang.Long.toHexString(r3.mPermittedCapabilities) + ", effective=0x" + java.lang.Long.toHexString(r3.mEffectiveCapabilities));
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x023d, code lost:
    
        r28.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0242, code lost:
    
        if (r3.mUsapPoolStatusSpecified == false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x024c, code lost:
    
        return r1.handleUsapPoolStatusChange(r31, r3.mUsapPoolEnabled);
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0251, code lost:
    
        if (r3.mApiDenylistExemptions == null) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0259, code lost:
    
        return r1.handleApiDenylistExemptions(r31, r3.mApiDenylistExemptions);
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x025c, code lost:
    
        if (r3.mHiddenApiAccessLogSampleRate != (-1)) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x0260, code lost:
    
        if (r3.mHiddenApiAccessStatslogSampleRate == (-1)) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x026a, code lost:
    
        throw new java.lang.AssertionError("Shouldn't get here");
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0273, code lost:
    
        return r1.handleHiddenApiAccessLogSampleRate(r31, r3.mHiddenApiAccessLogSampleRate, r3.mHiddenApiAccessStatslogSampleRate);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x018e, code lost:
    
        r29 = r6;
        r4 = r15;
        r0 = com.android.internal.os.Zygote.forkAndSpecialize(r3.mUid, r3.mGid, r3.mGids, r3.mRuntimeFlags, r13, r3.mMountExternal, r3.mSeInfo, r3.mNiceName, r14, r18, r3.mStartChildZygote, r3.mInstructionSet, r3.mAppDataDir, r3.mIsTopApp, r3.mPkgDataInfoList, r3.mAllowlistedDataInfoList, r3.mBindMountAppDataDirs, r3.mBindMountAppStorageDirs, r3.mBindMountSyspropOverrides);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x01bb, code lost:
    
        if (r0 != 0) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x01e3, code lost:
    
        libcore.io.IoUtils.closeQuietly(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01e9, code lost:
    
        handleParentProc(r0, r29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01ee, code lost:
    
        libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) null);
        libcore.io.IoUtils.closeQuietly(r29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01f4, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01f7, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01f8, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01f9, code lost:
    
        r29 = r29;
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0202, code lost:
    
        libcore.io.IoUtils.closeQuietly(r5);
        libcore.io.IoUtils.closeQuietly(r29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0208, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01fe, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0201, code lost:
    
        r5 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01bd, code lost:
    
        r31.setForkChild();
        r31.closeServerSocket();
        libcore.io.IoUtils.closeQuietly(r29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01c7, code lost:
    
        r0 = handleChildProc(r3, r4, r3.mStartChildZygote);
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01cf, code lost:
    
        libcore.io.IoUtils.closeQuietly(r4);
        libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01d6, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01d9, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01da, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01db, code lost:
    
        r5 = r4;
        r29 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01df, code lost:
    
        r0 = th;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    java.lang.Runnable processCommand(com.android.internal.os.ZygoteServer zygoteServer, boolean z) {
        com.android.internal.os.ZygoteCommandBuffer zygoteCommandBuffer;
        java.lang.Throwable th;
        java.io.FileDescriptor fileDescriptor;
        java.io.FileDescriptor fileDescriptor2;
        int[] iArr;
        com.android.internal.os.ZygoteCommandBuffer zygoteCommandBuffer2 = new com.android.internal.os.ZygoteCommandBuffer(this.mSocket);
        while (true) {
            try {
                try {
                    try {
                        com.android.internal.os.ZygoteArguments zygoteArguments = com.android.internal.os.ZygoteArguments.getInstance(zygoteCommandBuffer2);
                        if (zygoteArguments != null) {
                            if (!zygoteArguments.mBootCompleted) {
                                if (!zygoteArguments.mAbiListQuery) {
                                    if (!zygoteArguments.mPidQuery) {
                                        if (zygoteArguments.mUsapPoolStatusSpecified || zygoteArguments.mApiDenylistExemptions != null || zygoteArguments.mHiddenApiAccessLogSampleRate != -1) {
                                            break;
                                        }
                                        if (zygoteArguments.mHiddenApiAccessStatslogSampleRate == -1) {
                                            if (!zygoteArguments.mPreloadDefault) {
                                                if (zygoteArguments.mPreloadPackage == null) {
                                                    if (canPreloadApp()) {
                                                        try {
                                                            if (zygoteArguments.mPreloadApp != null) {
                                                                byte[] decode = java.util.Base64.getDecoder().decode(zygoteArguments.mPreloadApp);
                                                                android.os.Parcel obtain = android.os.Parcel.obtain();
                                                                obtain.unmarshall(decode, 0, decode.length);
                                                                obtain.setDataPosition(0);
                                                                android.content.pm.ApplicationInfo createFromParcel = android.content.pm.ApplicationInfo.CREATOR.createFromParcel(obtain);
                                                                obtain.recycle();
                                                                if (createFromParcel == null) {
                                                                    throw new java.lang.IllegalArgumentException("Failed to deserialize --preload-app");
                                                                }
                                                                handlePreloadApp(createFromParcel);
                                                                zygoteCommandBuffer2.close();
                                                                return null;
                                                            }
                                                        } catch (java.lang.Throwable th2) {
                                                            th = th2;
                                                            zygoteCommandBuffer = zygoteCommandBuffer2;
                                                            try {
                                                                zygoteCommandBuffer.close();
                                                                throw th;
                                                            } catch (java.lang.Throwable th3) {
                                                                th.addSuppressed(th3);
                                                                throw th;
                                                            }
                                                        }
                                                    }
                                                    if (zygoteArguments.mPermittedCapabilities != 0 || zygoteArguments.mEffectiveCapabilities != 0) {
                                                        break;
                                                    }
                                                    com.android.internal.os.Zygote.applyUidSecurityPolicy(zygoteArguments, this.peer);
                                                    com.android.internal.os.Zygote.applyInvokeWithSecurityPolicy(zygoteArguments, this.peer);
                                                    com.android.internal.os.Zygote.applyDebuggerSystemProperty(zygoteArguments);
                                                    com.android.internal.os.Zygote.applyInvokeWithSystemProperty(zygoteArguments);
                                                    int[][] iArr2 = zygoteArguments.mRLimits != null ? (int[][]) zygoteArguments.mRLimits.toArray(com.android.internal.os.Zygote.INT_ARRAY_2D) : null;
                                                    if (zygoteArguments.mInvokeWith != null) {
                                                        try {
                                                            java.io.FileDescriptor[] pipe2 = android.system.Os.pipe2(android.system.OsConstants.O_CLOEXEC);
                                                            java.io.FileDescriptor fileDescriptor3 = pipe2[1];
                                                            fileDescriptor = pipe2[0];
                                                            android.system.Os.fcntlInt(fileDescriptor3, android.system.OsConstants.F_SETFD, 0);
                                                            fileDescriptor2 = fileDescriptor3;
                                                            iArr = new int[]{fileDescriptor3.getInt$(), fileDescriptor.getInt$()};
                                                        } catch (android.system.ErrnoException e) {
                                                            throw new java.lang.IllegalStateException("Unable to set up pipe for invoke-with", e);
                                                        }
                                                    } else {
                                                        fileDescriptor = null;
                                                        fileDescriptor2 = null;
                                                        iArr = null;
                                                    }
                                                    int[] iArr3 = {-1, -1};
                                                    java.io.FileDescriptor fileDescriptor4 = this.mSocket.getFileDescriptor();
                                                    if (fileDescriptor4 != null) {
                                                        iArr3[0] = fileDescriptor4.getInt$();
                                                    }
                                                    java.io.FileDescriptor zygoteSocketFileDescriptor = zygoteServer.getZygoteSocketFileDescriptor();
                                                    if (zygoteSocketFileDescriptor != null) {
                                                        iArr3[1] = zygoteSocketFileDescriptor.getInt$();
                                                    }
                                                    if (zygoteArguments.mInvokeWith != null || zygoteArguments.mStartChildZygote || !z || this.peer.getUid() != 1000) {
                                                        break;
                                                    }
                                                    dalvik.system.ZygoteHooks.preFork();
                                                    java.lang.Runnable forkSimpleApps = com.android.internal.os.Zygote.forkSimpleApps(zygoteCommandBuffer2, zygoteServer.getZygoteSocketFileDescriptor(), this.peer.getUid(), com.android.internal.os.Zygote.minChildUid(this.peer), zygoteArguments.mNiceName);
                                                    if (forkSimpleApps != null) {
                                                        zygoteServer.setForkChild();
                                                        zygoteCommandBuffer2.close();
                                                        return forkSimpleApps;
                                                    }
                                                    dalvik.system.ZygoteHooks.postForkCommon();
                                                } else {
                                                    handlePreloadPackage(zygoteArguments.mPreloadPackage, zygoteArguments.mPreloadPackageLibs, zygoteArguments.mPreloadPackageLibFileName, zygoteArguments.mPreloadPackageCacheKey);
                                                    zygoteCommandBuffer2.close();
                                                    return null;
                                                }
                                            } else {
                                                handlePreload();
                                                zygoteCommandBuffer2.close();
                                                return null;
                                            }
                                        } else {
                                            com.android.internal.os.ZygoteConnection zygoteConnection = this;
                                            com.android.internal.os.ZygoteCommandBuffer zygoteCommandBuffer3 = zygoteCommandBuffer2;
                                            break;
                                        }
                                    } else {
                                        handlePidQuery();
                                        zygoteCommandBuffer2.close();
                                        return null;
                                    }
                                } else {
                                    handleAbiListQuery();
                                    zygoteCommandBuffer2.close();
                                    return null;
                                }
                            } else {
                                handleBootCompleted();
                                zygoteCommandBuffer2.close();
                                return null;
                            }
                        } else {
                            this.isEof = true;
                            zygoteCommandBuffer2.close();
                            return null;
                        }
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        th = th;
                        zygoteCommandBuffer.close();
                        throw th;
                    }
                } catch (java.lang.Throwable th5) {
                    th = th5;
                    zygoteCommandBuffer = zygoteCommandBuffer2;
                    th = th;
                    zygoteCommandBuffer.close();
                    throw th;
                }
            } catch (java.io.IOException e2) {
                throw new java.lang.IllegalStateException("IOException on command socket", e2);
            }
        }
    }

    private void handleAbiListQuery() {
        try {
            byte[] bytes = this.abiList.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(bytes.length);
            this.mSocketOutStream.write(bytes);
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("Error writing to command socket", e);
        }
    }

    private void handlePidQuery() {
        try {
            byte[] bytes = java.lang.String.valueOf(android.os.Process.myPid()).getBytes(java.nio.charset.StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(bytes.length);
            this.mSocketOutStream.write(bytes);
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("Error writing to command socket", e);
        }
    }

    private void handleBootCompleted() {
        try {
            this.mSocketOutStream.writeInt(0);
            dalvik.system.VMRuntime.bootCompleted();
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("Error writing to command socket", e);
        }
    }

    private void handlePreload() {
        try {
            if (isPreloadComplete()) {
                this.mSocketOutStream.writeInt(1);
            } else {
                preload();
                this.mSocketOutStream.writeInt(0);
            }
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("Error writing to command socket", e);
        }
    }

    private java.lang.Runnable stateChangeWithUsapPoolReset(com.android.internal.os.ZygoteServer zygoteServer, java.lang.Runnable runnable) {
        try {
            if (zygoteServer.isUsapPoolEnabled()) {
                android.util.Log.i(TAG, "Emptying USAP Pool due to state change.");
                com.android.internal.os.Zygote.emptyUsapPool();
            }
            runnable.run();
            if (zygoteServer.isUsapPoolEnabled()) {
                java.lang.Runnable fillUsapPool = zygoteServer.fillUsapPool(new int[]{this.mSocket.getFileDescriptor().getInt$()}, false);
                if (fillUsapPool != null) {
                    zygoteServer.setForkChild();
                    return fillUsapPool;
                }
                android.util.Log.i(TAG, "Finished refilling USAP Pool after state change.");
            }
            this.mSocketOutStream.writeInt(0);
            return null;
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("Error writing to command socket", e);
        }
    }

    private java.lang.Runnable handleApiDenylistExemptions(com.android.internal.os.ZygoteServer zygoteServer, final java.lang.String[] strArr) {
        return stateChangeWithUsapPoolReset(zygoteServer, new java.lang.Runnable() { // from class: com.android.internal.os.ZygoteConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.os.ZygoteInit.setApiDenylistExemptions(strArr);
            }
        });
    }

    private java.lang.Runnable handleUsapPoolStatusChange(com.android.internal.os.ZygoteServer zygoteServer, boolean z) {
        try {
            java.lang.Runnable usapPoolStatus = zygoteServer.setUsapPoolStatus(z, this.mSocket);
            if (usapPoolStatus == null) {
                this.mSocketOutStream.writeInt(0);
            } else {
                zygoteServer.setForkChild();
            }
            return usapPoolStatus;
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("Error writing to command socket", e);
        }
    }

    private java.lang.Runnable handleHiddenApiAccessLogSampleRate(com.android.internal.os.ZygoteServer zygoteServer, final int i, final int i2) {
        return stateChangeWithUsapPoolReset(zygoteServer, new java.lang.Runnable() { // from class: com.android.internal.os.ZygoteConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.os.ZygoteConnection.lambda$handleHiddenApiAccessLogSampleRate$1(i, i2);
            }
        });
    }

    static /* synthetic */ void lambda$handleHiddenApiAccessLogSampleRate$1(int i, int i2) {
        com.android.internal.os.ZygoteInit.setHiddenApiAccessLogSampleRate(java.lang.Math.max(i, i2));
        com.android.internal.os.StatsdHiddenApiUsageLogger.setHiddenApiAccessLogSampleRates(i, i2);
        com.android.internal.os.ZygoteInit.setHiddenApiUsageLogger(com.android.internal.os.StatsdHiddenApiUsageLogger.getInstance());
    }

    protected void preload() {
        com.android.internal.os.ZygoteInit.lazyPreload();
    }

    protected boolean isPreloadComplete() {
        return com.android.internal.os.ZygoteInit.isPreloadComplete();
    }

    protected java.io.DataOutputStream getSocketOutputStream() {
        return this.mSocketOutStream;
    }

    protected void handlePreloadPackage(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        throw new java.lang.RuntimeException("Zygote does not support package preloading");
    }

    protected boolean canPreloadApp() {
        return false;
    }

    protected void handlePreloadApp(android.content.pm.ApplicationInfo applicationInfo) {
        throw new java.lang.RuntimeException("Zygote does not support app preloading");
    }

    void closeSocket() {
        try {
            this.mSocket.close();
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Exception while closing command socket in parent", e);
        }
    }

    boolean isClosedByPeer() {
        return this.isEof;
    }

    private java.lang.Runnable handleChildProc(com.android.internal.os.ZygoteArguments zygoteArguments, java.io.FileDescriptor fileDescriptor, boolean z) {
        closeSocket();
        com.android.internal.os.Zygote.setAppProcessName(zygoteArguments, TAG);
        android.os.Trace.traceEnd(64L);
        if (zygoteArguments.mInvokeWith != null) {
            com.android.internal.os.WrapperInit.execApplication(zygoteArguments.mInvokeWith, zygoteArguments.mNiceName, zygoteArguments.mTargetSdkVersion, dalvik.system.VMRuntime.getCurrentInstructionSet(), fileDescriptor, zygoteArguments.mRemainingArgs);
            throw new java.lang.IllegalStateException("WrapperInit.execApplication unexpectedly returned");
        }
        if (!z) {
            return com.android.internal.os.ZygoteInit.zygoteInit(zygoteArguments.mTargetSdkVersion, zygoteArguments.mDisabledCompatChanges, zygoteArguments.mRemainingArgs, null);
        }
        return com.android.internal.os.ZygoteInit.childZygoteInit(zygoteArguments.mRemainingArgs);
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void handleParentProc(int i, java.io.FileDescriptor fileDescriptor) {
        int i2;
        int i3 = i;
        if (i3 > 0) {
            setChildPgid(i);
        }
        boolean z = false;
        if (fileDescriptor != null && i3 > 0) {
            boolean z2 = true;
            try {
                android.system.StructPollfd[] structPollfdArr = {new android.system.StructPollfd()};
                int i4 = 4;
                byte[] bArr = new byte[4];
                long nanoTime = java.lang.System.nanoTime();
                int i5 = 0;
                int i6 = 20000;
                while (true) {
                    if (i5 >= i4 || i6 <= 0) {
                        break;
                    }
                    try {
                        structPollfdArr[0].fd = fileDescriptor;
                        structPollfdArr[0].events = (short) android.system.OsConstants.POLLIN;
                        structPollfdArr[0].revents = (short) 0;
                        structPollfdArr[0].userData = null;
                        int poll = android.system.Os.poll(structPollfdArr, i6);
                        android.system.StructPollfd[] structPollfdArr2 = structPollfdArr;
                        try {
                            i6 = 20000 - ((int) java.util.concurrent.TimeUnit.MILLISECONDS.convert(java.lang.System.nanoTime() - nanoTime, java.util.concurrent.TimeUnit.NANOSECONDS));
                            if (poll > 0) {
                                if ((structPollfdArr2[0].revents & android.system.OsConstants.POLLIN) == 0) {
                                    z2 = true;
                                    break;
                                }
                                z2 = true;
                                int read = android.system.Os.read(fileDescriptor, bArr, i5, 1);
                                if (read < 0) {
                                    throw new java.lang.RuntimeException("Some error");
                                }
                                i5 += read;
                            } else {
                                z2 = true;
                                if (poll == 0) {
                                    android.util.Log.w(TAG, "Timed out waiting for child.");
                                }
                            }
                            structPollfdArr = structPollfdArr2;
                            i4 = 4;
                        } catch (java.lang.Exception e) {
                            e = e;
                            z2 = true;
                            i2 = -1;
                            android.util.Log.w(TAG, "Error reading pid from wrapped process, child may have died", e);
                            if (i2 > 0) {
                            }
                            this.mSocketOutStream.writeInt(i3);
                            this.mSocketOutStream.writeBoolean(z);
                        }
                    } catch (java.lang.Exception e2) {
                        e = e2;
                    }
                }
                if (i5 != 4) {
                    i2 = -1;
                } else {
                    i2 = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr)).readInt();
                }
                if (i2 == -1) {
                    try {
                        android.util.Log.w(TAG, "Error reading pid from wrapped process, child may have died");
                    } catch (java.lang.Exception e3) {
                        e = e3;
                        android.util.Log.w(TAG, "Error reading pid from wrapped process, child may have died", e);
                        if (i2 > 0) {
                        }
                        this.mSocketOutStream.writeInt(i3);
                        this.mSocketOutStream.writeBoolean(z);
                    }
                }
            } catch (java.lang.Exception e4) {
                e = e4;
                i2 = -1;
            }
            if (i2 > 0) {
                int i7 = i2;
                while (i7 > 0 && i7 != i3) {
                    i7 = android.os.Process.getParentPid(i7);
                }
                if (i7 > 0) {
                    android.util.Log.i(TAG, "Wrapped process has pid " + i2);
                    i3 = i2;
                    z = z2;
                } else {
                    android.util.Log.w(TAG, "Wrapped process reported a pid that is not a child of the process that we forked: childPid=" + i3 + " innerPid=" + i2);
                }
            }
        }
        try {
            this.mSocketOutStream.writeInt(i3);
            this.mSocketOutStream.writeBoolean(z);
        } catch (java.io.IOException e5) {
            throw new java.lang.IllegalStateException("Error writing to command socket", e5);
        }
    }

    private void setChildPgid(int i) {
        try {
            android.system.Os.setpgid(i, android.system.Os.getpgid(this.peer.getPid()));
        } catch (android.system.ErrnoException e) {
            android.util.Log.i(TAG, "Zygote: setpgid failed. This is normal if peer is not in our session");
        }
    }
}
