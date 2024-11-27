package com.android.internal.os;

/* loaded from: classes4.dex */
class ZygoteArguments {
    boolean mAbiListQuery;
    java.lang.String[] mAllowlistedDataInfoList;
    java.lang.String[] mApiDenylistExemptions;
    java.lang.String mAppDataDir;
    boolean mBindMountAppDataDirs;
    boolean mBindMountAppStorageDirs;
    boolean mBindMountSyspropOverrides;
    boolean mBootCompleted;
    private boolean mCapabilitiesSpecified;
    long mEffectiveCapabilities;
    boolean mGidSpecified;
    int[] mGids;
    java.lang.String mInstructionSet;
    java.lang.String mInvokeWith;
    boolean mIsTopApp;
    java.lang.String mNiceName;
    java.lang.String mPackageName;
    long mPermittedCapabilities;
    boolean mPidQuery;
    java.lang.String[] mPkgDataInfoList;
    java.lang.String mPreloadApp;
    boolean mPreloadDefault;
    java.lang.String mPreloadPackage;
    java.lang.String mPreloadPackageCacheKey;
    java.lang.String mPreloadPackageLibFileName;
    java.lang.String mPreloadPackageLibs;
    java.util.ArrayList<int[]> mRLimits;
    java.lang.String[] mRemainingArgs;
    int mRuntimeFlags;
    java.lang.String mSeInfo;
    private boolean mSeInfoSpecified;
    boolean mStartChildZygote;
    int mTargetSdkVersion;
    private boolean mTargetSdkVersionSpecified;
    boolean mUidSpecified;
    boolean mUsapPoolEnabled;
    int mUid = 0;
    int mGid = 0;
    int mMountExternal = 0;
    boolean mUsapPoolStatusSpecified = false;
    int mHiddenApiAccessLogSampleRate = -1;
    int mHiddenApiAccessStatslogSampleRate = -1;
    long[] mDisabledCompatChanges = null;

    private ZygoteArguments(com.android.internal.os.ZygoteCommandBuffer zygoteCommandBuffer, int i) throws java.lang.IllegalArgumentException, java.io.EOFException {
        parseArgs(zygoteCommandBuffer, i);
    }

    public static com.android.internal.os.ZygoteArguments getInstance(com.android.internal.os.ZygoteCommandBuffer zygoteCommandBuffer) throws java.lang.IllegalArgumentException, java.io.EOFException {
        int count = zygoteCommandBuffer.getCount();
        if (count == 0) {
            return null;
        }
        return new com.android.internal.os.ZygoteArguments(zygoteCommandBuffer, count);
    }

    /* JADX WARN: Code restructure failed: missing block: B:250:0x03a7, code lost:
    
        if (r11.mBootCompleted == false) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x03a9, code lost:
    
        if (r13 > r2) goto L201;
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x03b4, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --boot-completed");
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x041f, code lost:
    
        if (r11.mStartChildZygote == false) goto L246;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x0422, code lost:
    
        r12 = r11.mRemainingArgs;
        r13 = r12.length;
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x0426, code lost:
    
        if (r2 >= r13) goto L309;
     */
    /* JADX WARN: Code restructure failed: missing block: B:259:0x0430, code lost:
    
        if (r12[r2].startsWith(com.android.internal.os.Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG) == false) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x0435, code lost:
    
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x0433, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:263:0x0438, code lost:
    
        if (r0 == false) goto L244;
     */
    /* JADX WARN: Code restructure failed: missing block: B:265:0x0442, code lost:
    
        throw new java.lang.IllegalArgumentException("--start-child-zygote specified without --zygote-socket=");
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0443, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x03b7, code lost:
    
        if (r11.mAbiListQuery != false) goto L233;
     */
    /* JADX WARN: Code restructure failed: missing block: B:272:0x03bb, code lost:
    
        if (r11.mPidQuery == false) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x03c0, code lost:
    
        if (r11.mPreloadPackage == null) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:275:0x03c2, code lost:
    
        if (r13 > r2) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:277:0x03cc, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --preload-package.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x03cf, code lost:
    
        if (r11.mPreloadApp == null) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x03d1, code lost:
    
        if (r13 > r2) goto L218;
     */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x03db, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --preload-app.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x03dc, code lost:
    
        if (r4 == false) goto L234;
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x03de, code lost:
    
        if (r3 != false) goto L227;
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x03e0, code lost:
    
        r0 = new java.lang.StringBuilder().append("Unexpected argument : ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:286:0x03ed, code lost:
    
        if (r5 != null) goto L225;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x03ef, code lost:
    
        r5 = r12.nextArg();
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x03fe, code lost:
    
        throw new java.lang.IllegalArgumentException(r0.append(r5).toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x03ff, code lost:
    
        r13 = r13 - r2;
        r11.mRemainingArgs = new java.lang.String[r13];
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x0405, code lost:
    
        if (r5 == null) goto L230;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x0407, code lost:
    
        r11.mRemainingArgs[0] = r5;
        r2 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x040e, code lost:
    
        if (r2 >= r13) goto L310;
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x0410, code lost:
    
        r11.mRemainingArgs[r2] = r12.nextArg();
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:297:0x040d, code lost:
    
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:298:0x041b, code lost:
    
        if (r13 > r2) goto L247;
     */
    /* JADX WARN: Code restructure failed: missing block: B:300:0x044b, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --query-abi-list.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseArgs(com.android.internal.os.ZygoteCommandBuffer zygoteCommandBuffer, int i) throws java.lang.IllegalArgumentException, java.io.EOFException {
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = true;
        while (true) {
            java.lang.String str = null;
            if (i2 >= i) {
                break;
            }
            java.lang.String nextArg = zygoteCommandBuffer.nextArg();
            if (nextArg.equals("--")) {
                i2++;
                break;
            }
            if (nextArg.startsWith("--setuid=")) {
                if (this.mUidSpecified) {
                    throw new java.lang.IllegalArgumentException("Duplicate arg specified");
                }
                this.mUidSpecified = true;
                this.mUid = java.lang.Integer.parseInt(getAssignmentValue(nextArg));
            } else if (nextArg.startsWith("--setgid=")) {
                if (this.mGidSpecified) {
                    throw new java.lang.IllegalArgumentException("Duplicate arg specified");
                }
                this.mGidSpecified = true;
                this.mGid = java.lang.Integer.parseInt(getAssignmentValue(nextArg));
            } else if (nextArg.startsWith("--target-sdk-version=")) {
                if (this.mTargetSdkVersionSpecified) {
                    throw new java.lang.IllegalArgumentException("Duplicate target-sdk-version specified");
                }
                this.mTargetSdkVersionSpecified = true;
                this.mTargetSdkVersion = java.lang.Integer.parseInt(getAssignmentValue(nextArg));
            } else if (nextArg.equals("--runtime-args")) {
                z2 = true;
            } else if (nextArg.startsWith("--runtime-flags=")) {
                this.mRuntimeFlags = java.lang.Integer.parseInt(getAssignmentValue(nextArg));
            } else if (nextArg.startsWith("--seinfo=")) {
                if (this.mSeInfoSpecified) {
                    throw new java.lang.IllegalArgumentException("Duplicate arg specified");
                }
                this.mSeInfoSpecified = true;
                this.mSeInfo = getAssignmentValue(nextArg);
            } else if (nextArg.startsWith("--capabilities=")) {
                if (this.mCapabilitiesSpecified) {
                    throw new java.lang.IllegalArgumentException("Duplicate arg specified");
                }
                this.mCapabilitiesSpecified = true;
                java.lang.String[] split = getAssignmentValue(nextArg).split(",", 2);
                if (split.length == 1) {
                    this.mEffectiveCapabilities = java.lang.Long.decode(split[0]).longValue();
                    this.mPermittedCapabilities = this.mEffectiveCapabilities;
                } else {
                    this.mPermittedCapabilities = java.lang.Long.decode(split[0]).longValue();
                    this.mEffectiveCapabilities = java.lang.Long.decode(split[1]).longValue();
                }
            } else if (nextArg.startsWith("--rlimit=")) {
                java.lang.String[] assignmentList = getAssignmentList(nextArg);
                if (assignmentList.length != 3) {
                    throw new java.lang.IllegalArgumentException("--rlimit= should have 3 comma-delimited ints");
                }
                int[] iArr = new int[assignmentList.length];
                for (int i3 = 0; i3 < assignmentList.length; i3++) {
                    iArr[i3] = java.lang.Integer.parseInt(assignmentList[i3]);
                }
                if (this.mRLimits == null) {
                    this.mRLimits = new java.util.ArrayList<>();
                }
                this.mRLimits.add(iArr);
            } else if (nextArg.startsWith("--setgroups=")) {
                if (this.mGids != null) {
                    throw new java.lang.IllegalArgumentException("Duplicate arg specified");
                }
                java.lang.String[] assignmentList2 = getAssignmentList(nextArg);
                this.mGids = new int[assignmentList2.length];
                for (int length = assignmentList2.length - 1; length >= 0; length--) {
                    this.mGids[length] = java.lang.Integer.parseInt(assignmentList2[length]);
                }
            } else if (nextArg.equals("--invoke-with")) {
                if (this.mInvokeWith != null) {
                    throw new java.lang.IllegalArgumentException("Duplicate arg specified");
                }
                i2++;
                try {
                    this.mInvokeWith = zygoteCommandBuffer.nextArg();
                } catch (java.lang.IndexOutOfBoundsException e) {
                    throw new java.lang.IllegalArgumentException("--invoke-with requires argument");
                }
            } else if (nextArg.startsWith("--nice-name=")) {
                if (this.mNiceName != null) {
                    throw new java.lang.IllegalArgumentException("Duplicate arg specified");
                }
                this.mNiceName = getAssignmentValue(nextArg);
            } else if (nextArg.equals("--mount-external-default")) {
                this.mMountExternal = 1;
            } else if (nextArg.equals("--mount-external-installer")) {
                this.mMountExternal = 2;
            } else if (nextArg.equals("--mount-external-pass-through")) {
                this.mMountExternal = 3;
            } else if (nextArg.equals("--mount-external-android-writable")) {
                this.mMountExternal = 4;
            } else if (nextArg.equals("--query-abi-list")) {
                this.mAbiListQuery = true;
            } else if (nextArg.equals("--get-pid")) {
                this.mPidQuery = true;
            } else if (nextArg.equals("--boot-completed")) {
                this.mBootCompleted = true;
            } else if (nextArg.startsWith("--instruction-set=")) {
                this.mInstructionSet = getAssignmentValue(nextArg);
            } else if (nextArg.startsWith("--app-data-dir=")) {
                this.mAppDataDir = getAssignmentValue(nextArg);
            } else if (nextArg.equals("--preload-app")) {
                i2++;
                this.mPreloadApp = zygoteCommandBuffer.nextArg();
            } else if (nextArg.equals("--preload-package")) {
                i2 += 4;
                this.mPreloadPackage = zygoteCommandBuffer.nextArg();
                this.mPreloadPackageLibs = zygoteCommandBuffer.nextArg();
                this.mPreloadPackageLibFileName = zygoteCommandBuffer.nextArg();
                this.mPreloadPackageCacheKey = zygoteCommandBuffer.nextArg();
            } else if (nextArg.equals("--preload-default")) {
                this.mPreloadDefault = true;
                z3 = false;
            } else if (nextArg.equals("--start-child-zygote")) {
                this.mStartChildZygote = true;
            } else if (nextArg.equals("--set-api-denylist-exemptions")) {
                this.mApiDenylistExemptions = new java.lang.String[(i - i2) - 1];
                i2++;
                int i4 = 0;
                while (i2 < i) {
                    this.mApiDenylistExemptions[i4] = zygoteCommandBuffer.nextArg();
                    i2++;
                    i4++;
                }
                z3 = false;
            } else if (nextArg.startsWith("--hidden-api-log-sampling-rate=")) {
                java.lang.String assignmentValue = getAssignmentValue(nextArg);
                try {
                    this.mHiddenApiAccessLogSampleRate = java.lang.Integer.parseInt(assignmentValue);
                    z3 = false;
                } catch (java.lang.NumberFormatException e2) {
                    throw new java.lang.IllegalArgumentException("Invalid log sampling rate: " + assignmentValue, e2);
                }
            } else if (nextArg.startsWith("--hidden-api-statslog-sampling-rate=")) {
                java.lang.String assignmentValue2 = getAssignmentValue(nextArg);
                try {
                    this.mHiddenApiAccessStatslogSampleRate = java.lang.Integer.parseInt(assignmentValue2);
                    z3 = false;
                } catch (java.lang.NumberFormatException e3) {
                    throw new java.lang.IllegalArgumentException("Invalid statslog sampling rate: " + assignmentValue2, e3);
                }
            } else if (nextArg.startsWith("--package-name=")) {
                if (this.mPackageName != null) {
                    throw new java.lang.IllegalArgumentException("Duplicate arg specified");
                }
                this.mPackageName = getAssignmentValue(nextArg);
            } else if (nextArg.startsWith("--usap-pool-enabled=")) {
                this.mUsapPoolStatusSpecified = true;
                this.mUsapPoolEnabled = java.lang.Boolean.parseBoolean(getAssignmentValue(nextArg));
                z3 = false;
            } else if (nextArg.startsWith(com.android.internal.os.Zygote.START_AS_TOP_APP_ARG)) {
                this.mIsTopApp = true;
            } else if (nextArg.startsWith("--disabled-compat-changes=")) {
                if (this.mDisabledCompatChanges != null) {
                    throw new java.lang.IllegalArgumentException("Duplicate arg specified");
                }
                java.lang.String[] assignmentList3 = getAssignmentList(nextArg);
                int length2 = assignmentList3.length;
                this.mDisabledCompatChanges = new long[length2];
                for (int i5 = 0; i5 < length2; i5++) {
                    this.mDisabledCompatChanges[i5] = java.lang.Long.parseLong(assignmentList3[i5]);
                }
            } else if (nextArg.startsWith(com.android.internal.os.Zygote.PKG_DATA_INFO_MAP)) {
                this.mPkgDataInfoList = getAssignmentList(nextArg);
            } else if (nextArg.startsWith(com.android.internal.os.Zygote.ALLOWLISTED_DATA_INFO_MAP)) {
                this.mAllowlistedDataInfoList = getAssignmentList(nextArg);
            } else if (nextArg.equals(com.android.internal.os.Zygote.BIND_MOUNT_APP_STORAGE_DIRS)) {
                this.mBindMountAppStorageDirs = true;
            } else if (nextArg.equals(com.android.internal.os.Zygote.BIND_MOUNT_APP_DATA_DIRS)) {
                this.mBindMountAppDataDirs = true;
            } else if (nextArg.equals(com.android.internal.os.Zygote.BIND_MOUNT_SYSPROP_OVERRIDES)) {
                this.mBindMountSyspropOverrides = true;
            } else {
                str = nextArg;
                break;
            }
            i2++;
        }
    }

    private static java.lang.String getAssignmentValue(java.lang.String str) {
        return str.substring(str.indexOf(61) + 1);
    }

    private static java.lang.String[] getAssignmentList(java.lang.String str) {
        return getAssignmentValue(str).split(",");
    }
}
