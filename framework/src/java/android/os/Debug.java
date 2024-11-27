package android.os;

/* loaded from: classes3.dex */
public final class Debug {
    private static final java.lang.String DEFAULT_TRACE_BODY = "dmtrace";
    private static final java.lang.String DEFAULT_TRACE_EXTENSION = ".trace";
    public static final int MEMINFO_ACTIVE = 16;
    public static final int MEMINFO_ACTIVE_ANON = 20;
    public static final int MEMINFO_ACTIVE_FILE = 22;
    public static final int MEMINFO_AVAILABLE = 19;
    public static final int MEMINFO_BUFFERS = 2;
    public static final int MEMINFO_CACHED = 3;
    public static final int MEMINFO_CMA_FREE = 25;
    public static final int MEMINFO_CMA_TOTAL = 24;
    public static final int MEMINFO_COUNT = 26;
    public static final int MEMINFO_FREE = 1;
    public static final int MEMINFO_INACTIVE = 17;
    public static final int MEMINFO_INACTIVE_ANON = 21;
    public static final int MEMINFO_INACTIVE_FILE = 23;
    public static final int MEMINFO_KERNEL_STACK = 14;
    public static final int MEMINFO_KRECLAIMABLE = 15;
    public static final int MEMINFO_MAPPED = 11;
    public static final int MEMINFO_PAGE_TABLES = 13;
    public static final int MEMINFO_SHMEM = 4;
    public static final int MEMINFO_SLAB = 5;
    public static final int MEMINFO_SLAB_RECLAIMABLE = 6;
    public static final int MEMINFO_SLAB_UNRECLAIMABLE = 7;
    public static final int MEMINFO_SWAP_FREE = 9;
    public static final int MEMINFO_SWAP_TOTAL = 8;
    public static final int MEMINFO_TOTAL = 0;
    public static final int MEMINFO_UNEVICTABLE = 18;
    public static final int MEMINFO_VM_ALLOC_USED = 12;
    public static final int MEMINFO_ZRAM_TOTAL = 10;
    private static final int MIN_DEBUGGER_IDLE = 1300;
    public static final int SHOW_CLASSLOADER = 2;
    public static final int SHOW_FULL_DETAIL = 1;
    public static final int SHOW_INITIALIZED = 4;
    private static final int SPIN_DELAY = 200;
    private static final java.lang.String SYSFS_QEMU_TRACE_STATE = "/sys/qemu_trace/state";
    private static final java.lang.String TAG = "Debug";

    @java.lang.Deprecated
    public static final int TRACE_COUNT_ALLOCS = 1;
    private static volatile boolean mWaiting = false;
    private static final com.android.internal.util.TypedProperties debugProperties = null;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface DebugProperty {
    }

    public static native boolean dumpJavaBacktraceToFileTimeout(int i, java.lang.String str, int i2);

    public static native boolean dumpNativeBacktraceToFileTimeout(int i, java.lang.String str, int i2);

    public static native void dumpNativeHeap(java.io.FileDescriptor fileDescriptor);

    public static native void dumpNativeMallocInfo(java.io.FileDescriptor fileDescriptor);

    public static final native int getBinderDeathObjectCount();

    public static final native int getBinderLocalObjectCount();

    public static final native int getBinderProxyObjectCount();

    public static native int getBinderReceivedTransactions();

    public static native int getBinderSentTransactions();

    public static native long getDmabufHeapPoolsSizeKb();

    public static native long getDmabufHeapTotalExportedKb();

    public static native long getDmabufMappedSizeKb();

    public static native long getDmabufTotalExportedKb();

    public static native long getGpuPrivateMemoryKb();

    public static native long getGpuTotalUsageKb();

    public static native long getIonHeapsSizeKb();

    public static native long getIonPoolsSizeKb();

    public static native void getMemInfo(long[] jArr);

    public static native void getMemoryInfo(android.os.Debug.MemoryInfo memoryInfo);

    public static native boolean getMemoryInfo(int i, android.os.Debug.MemoryInfo memoryInfo);

    public static native long getNativeHeapAllocatedSize();

    public static native long getNativeHeapFreeSize();

    public static native long getNativeHeapSize();

    public static native long getPss();

    public static native long getPss(int i, long[] jArr, long[] jArr2);

    public static native long getRss();

    public static native long getRss(int i, long[] jArr);

    public static native java.lang.String getUnreachableMemory(int i, boolean z);

    public static native long getZramFreeKb();

    public static native boolean isVmapStack();

    public static native boolean logAllocatorStats();

    private Debug() {
    }

    public static class MemoryInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.os.Debug.MemoryInfo> CREATOR = new android.os.Parcelable.Creator<android.os.Debug.MemoryInfo>() { // from class: android.os.Debug.MemoryInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.Debug.MemoryInfo createFromParcel(android.os.Parcel parcel) {
                return new android.os.Debug.MemoryInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.Debug.MemoryInfo[] newArray(int i) {
                return new android.os.Debug.MemoryInfo[i];
            }
        };
        public static final int HEAP_DALVIK = 1;
        public static final int HEAP_NATIVE = 2;
        public static final int HEAP_UNKNOWN = 0;
        public static final int NUM_CATEGORIES = 9;
        public static final int NUM_DVK_STATS = 15;
        public static final int NUM_OTHER_STATS = 17;
        public static final int OFFSET_PRIVATE_CLEAN = 5;
        public static final int OFFSET_PRIVATE_DIRTY = 3;
        public static final int OFFSET_PSS = 0;
        public static final int OFFSET_RSS = 2;
        public static final int OFFSET_SHARED_CLEAN = 6;
        public static final int OFFSET_SHARED_DIRTY = 4;
        public static final int OFFSET_SWAPPABLE_PSS = 1;
        public static final int OFFSET_SWAPPED_OUT = 7;
        public static final int OFFSET_SWAPPED_OUT_PSS = 8;
        public static final int OTHER_APK = 8;
        public static final int OTHER_ART = 12;
        public static final int OTHER_ART_APP = 30;
        public static final int OTHER_ART_BOOT = 31;
        public static final int OTHER_ASHMEM = 3;
        public static final int OTHER_CURSOR = 2;
        public static final int OTHER_DALVIK_LARGE = 18;
        public static final int OTHER_DALVIK_NON_MOVING = 20;
        public static final int OTHER_DALVIK_NORMAL = 17;
        public static final int OTHER_DALVIK_OTHER = 0;
        public static final int OTHER_DALVIK_OTHER_ACCOUNTING = 22;
        public static final int OTHER_DALVIK_OTHER_APP_CODE_CACHE = 24;
        public static final int OTHER_DALVIK_OTHER_COMPILER_METADATA = 25;
        public static final int OTHER_DALVIK_OTHER_INDIRECT_REFERENCE_TABLE = 26;
        public static final int OTHER_DALVIK_OTHER_LINEARALLOC = 21;
        public static final int OTHER_DALVIK_OTHER_ZYGOTE_CODE_CACHE = 23;
        public static final int OTHER_DALVIK_ZYGOTE = 19;
        public static final int OTHER_DEX = 10;
        public static final int OTHER_DEX_APP_DEX = 28;
        public static final int OTHER_DEX_APP_VDEX = 29;
        public static final int OTHER_DEX_BOOT_VDEX = 27;
        public static final int OTHER_DVK_STAT_ART_END = 14;
        public static final int OTHER_DVK_STAT_ART_START = 13;
        public static final int OTHER_DVK_STAT_DALVIK_END = 3;
        public static final int OTHER_DVK_STAT_DALVIK_OTHER_END = 9;
        public static final int OTHER_DVK_STAT_DALVIK_OTHER_START = 4;
        public static final int OTHER_DVK_STAT_DALVIK_START = 0;
        public static final int OTHER_DVK_STAT_DEX_END = 12;
        public static final int OTHER_DVK_STAT_DEX_START = 10;
        public static final int OTHER_GL = 15;
        public static final int OTHER_GL_DEV = 4;
        public static final int OTHER_GRAPHICS = 14;
        public static final int OTHER_JAR = 7;
        public static final int OTHER_OAT = 11;
        public static final int OTHER_OTHER_MEMTRACK = 16;
        public static final int OTHER_SO = 6;
        public static final int OTHER_STACK = 1;
        public static final int OTHER_TTF = 9;
        public static final int OTHER_UNKNOWN_DEV = 5;
        public static final int OTHER_UNKNOWN_MAP = 13;
        public int dalvikPrivateClean;
        public int dalvikPrivateDirty;
        public int dalvikPss;
        public int dalvikRss;
        public int dalvikSharedClean;
        public int dalvikSharedDirty;
        public int dalvikSwappablePss;
        public int dalvikSwappedOut;
        public int dalvikSwappedOutPss;
        public boolean hasSwappedOutPss;
        public int nativePrivateClean;
        public int nativePrivateDirty;
        public int nativePss;
        public int nativeRss;
        public int nativeSharedClean;
        public int nativeSharedDirty;
        public int nativeSwappablePss;
        public int nativeSwappedOut;
        public int nativeSwappedOutPss;
        public int otherPrivateClean;
        public int otherPrivateDirty;
        public int otherPss;
        public int otherRss;
        public int otherSharedClean;
        public int otherSharedDirty;
        private int[] otherStats;
        public int otherSwappablePss;
        public int otherSwappedOut;
        public int otherSwappedOutPss;

        public MemoryInfo() {
            this.otherStats = new int[288];
        }

        public void set(android.os.Debug.MemoryInfo memoryInfo) {
            this.dalvikPss = memoryInfo.dalvikPss;
            this.dalvikSwappablePss = memoryInfo.dalvikSwappablePss;
            this.dalvikRss = memoryInfo.dalvikRss;
            this.dalvikPrivateDirty = memoryInfo.dalvikPrivateDirty;
            this.dalvikSharedDirty = memoryInfo.dalvikSharedDirty;
            this.dalvikPrivateClean = memoryInfo.dalvikPrivateClean;
            this.dalvikSharedClean = memoryInfo.dalvikSharedClean;
            this.dalvikSwappedOut = memoryInfo.dalvikSwappedOut;
            this.dalvikSwappedOutPss = memoryInfo.dalvikSwappedOutPss;
            this.nativePss = memoryInfo.nativePss;
            this.nativeSwappablePss = memoryInfo.nativeSwappablePss;
            this.nativeRss = memoryInfo.nativeRss;
            this.nativePrivateDirty = memoryInfo.nativePrivateDirty;
            this.nativeSharedDirty = memoryInfo.nativeSharedDirty;
            this.nativePrivateClean = memoryInfo.nativePrivateClean;
            this.nativeSharedClean = memoryInfo.nativeSharedClean;
            this.nativeSwappedOut = memoryInfo.nativeSwappedOut;
            this.nativeSwappedOutPss = memoryInfo.nativeSwappedOutPss;
            this.otherPss = memoryInfo.otherPss;
            this.otherSwappablePss = memoryInfo.otherSwappablePss;
            this.otherRss = memoryInfo.otherRss;
            this.otherPrivateDirty = memoryInfo.otherPrivateDirty;
            this.otherSharedDirty = memoryInfo.otherSharedDirty;
            this.otherPrivateClean = memoryInfo.otherPrivateClean;
            this.otherSharedClean = memoryInfo.otherSharedClean;
            this.otherSwappedOut = memoryInfo.otherSwappedOut;
            this.otherSwappedOutPss = memoryInfo.otherSwappedOutPss;
            this.hasSwappedOutPss = memoryInfo.hasSwappedOutPss;
            java.lang.System.arraycopy(memoryInfo.otherStats, 0, this.otherStats, 0, this.otherStats.length);
        }

        public int getTotalPss() {
            return this.dalvikPss + this.nativePss + this.otherPss + getTotalSwappedOutPss();
        }

        public int getTotalUss() {
            return this.dalvikPrivateClean + this.dalvikPrivateDirty + this.nativePrivateClean + this.nativePrivateDirty + this.otherPrivateClean + this.otherPrivateDirty;
        }

        public int getTotalSwappablePss() {
            return this.dalvikSwappablePss + this.nativeSwappablePss + this.otherSwappablePss;
        }

        public int getTotalRss() {
            return this.dalvikRss + this.nativeRss + this.otherRss;
        }

        public int getTotalPrivateDirty() {
            return this.dalvikPrivateDirty + this.nativePrivateDirty + this.otherPrivateDirty;
        }

        public int getTotalSharedDirty() {
            return this.dalvikSharedDirty + this.nativeSharedDirty + this.otherSharedDirty;
        }

        public int getTotalPrivateClean() {
            return this.dalvikPrivateClean + this.nativePrivateClean + this.otherPrivateClean;
        }

        public int getTotalSharedClean() {
            return this.dalvikSharedClean + this.nativeSharedClean + this.otherSharedClean;
        }

        public int getTotalSwappedOut() {
            return this.dalvikSwappedOut + this.nativeSwappedOut + this.otherSwappedOut;
        }

        public int getTotalSwappedOutPss() {
            return this.dalvikSwappedOutPss + this.nativeSwappedOutPss + this.otherSwappedOutPss;
        }

        public int getOtherPss(int i) {
            return this.otherStats[(i * 9) + 0];
        }

        public int getOtherSwappablePss(int i) {
            return this.otherStats[(i * 9) + 1];
        }

        public int getOtherRss(int i) {
            return this.otherStats[(i * 9) + 2];
        }

        public int getOtherPrivateDirty(int i) {
            return this.otherStats[(i * 9) + 3];
        }

        public int getOtherSharedDirty(int i) {
            return this.otherStats[(i * 9) + 4];
        }

        public int getOtherPrivateClean(int i) {
            return this.otherStats[(i * 9) + 5];
        }

        public int getOtherPrivate(int i) {
            return getOtherPrivateClean(i) + getOtherPrivateDirty(i);
        }

        public int getOtherSharedClean(int i) {
            return this.otherStats[(i * 9) + 6];
        }

        public int getOtherSwappedOut(int i) {
            return this.otherStats[(i * 9) + 7];
        }

        public int getOtherSwappedOutPss(int i) {
            return this.otherStats[(i * 9) + 8];
        }

        public static java.lang.String getOtherLabel(int i) {
            switch (i) {
                case 0:
                    return "Dalvik Other";
                case 1:
                    return "Stack";
                case 2:
                    return "Cursor";
                case 3:
                    return "Ashmem";
                case 4:
                    return "Gfx dev";
                case 5:
                    return "Other dev";
                case 6:
                    return ".so mmap";
                case 7:
                    return ".jar mmap";
                case 8:
                    return ".apk mmap";
                case 9:
                    return ".ttf mmap";
                case 10:
                    return ".dex mmap";
                case 11:
                    return ".oat mmap";
                case 12:
                    return ".art mmap";
                case 13:
                    return "Other mmap";
                case 14:
                    return "EGL mtrack";
                case 15:
                    return "GL mtrack";
                case 16:
                    return "Other mtrack";
                case 17:
                    return ".Heap";
                case 18:
                    return ".LOS";
                case 19:
                    return ".Zygote";
                case 20:
                    return ".NonMoving";
                case 21:
                    return ".LinearAlloc";
                case 22:
                    return ".GC";
                case 23:
                    return ".ZygoteJIT";
                case 24:
                    return ".AppJIT";
                case 25:
                    return ".CompilerMetadata";
                case 26:
                    return ".IndirectRef";
                case 27:
                    return ".Boot vdex";
                case 28:
                    return ".App dex";
                case 29:
                    return ".App vdex";
                case 30:
                    return ".App art";
                case 31:
                    return ".Boot art";
                default:
                    return "????";
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public java.lang.String getMemoryStat(java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -1629983121:
                    if (str.equals("summary.java-heap")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1318722433:
                    if (str.equals("summary.total-pss")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -1086991874:
                    if (str.equals("summary.private-other")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1040176230:
                    if (str.equals("summary.native-heap")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -675184064:
                    if (str.equals("summary.stack")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 549300599:
                    if (str.equals("summary.system")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1640306485:
                    if (str.equals("summary.code")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2016489427:
                    if (str.equals("summary.graphics")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2069370308:
                    if (str.equals("summary.total-swap")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return java.lang.Integer.toString(getSummaryJavaHeap());
                case 1:
                    return java.lang.Integer.toString(getSummaryNativeHeap());
                case 2:
                    return java.lang.Integer.toString(getSummaryCode());
                case 3:
                    return java.lang.Integer.toString(getSummaryStack());
                case 4:
                    return java.lang.Integer.toString(getSummaryGraphics());
                case 5:
                    return java.lang.Integer.toString(getSummaryPrivateOther());
                case 6:
                    return java.lang.Integer.toString(getSummarySystem());
                case 7:
                    return java.lang.Integer.toString(getSummaryTotalPss());
                case '\b':
                    return java.lang.Integer.toString(getSummaryTotalSwap());
                default:
                    return null;
            }
        }

        public java.util.Map<java.lang.String, java.lang.String> getMemoryStats() {
            java.util.HashMap hashMap = new java.util.HashMap();
            hashMap.put("summary.java-heap", java.lang.Integer.toString(getSummaryJavaHeap()));
            hashMap.put("summary.native-heap", java.lang.Integer.toString(getSummaryNativeHeap()));
            hashMap.put("summary.code", java.lang.Integer.toString(getSummaryCode()));
            hashMap.put("summary.stack", java.lang.Integer.toString(getSummaryStack()));
            hashMap.put("summary.graphics", java.lang.Integer.toString(getSummaryGraphics()));
            hashMap.put("summary.private-other", java.lang.Integer.toString(getSummaryPrivateOther()));
            hashMap.put("summary.system", java.lang.Integer.toString(getSummarySystem()));
            hashMap.put("summary.total-pss", java.lang.Integer.toString(getSummaryTotalPss()));
            hashMap.put("summary.total-swap", java.lang.Integer.toString(getSummaryTotalSwap()));
            return hashMap;
        }

        public int getSummaryJavaHeap() {
            return this.dalvikPrivateDirty + getOtherPrivate(12);
        }

        public int getSummaryNativeHeap() {
            return this.nativePrivateDirty;
        }

        public int getSummaryCode() {
            return getOtherPrivate(6) + getOtherPrivate(7) + getOtherPrivate(8) + getOtherPrivate(9) + getOtherPrivate(10) + getOtherPrivate(11) + getOtherPrivate(23) + getOtherPrivate(24);
        }

        public int getSummaryStack() {
            return getOtherPrivateDirty(1);
        }

        public int getSummaryGraphics() {
            return getOtherPrivate(4) + getOtherPrivate(14) + getOtherPrivate(15);
        }

        public int getSummaryPrivateOther() {
            return (((((getTotalPrivateClean() + getTotalPrivateDirty()) - getSummaryJavaHeap()) - getSummaryNativeHeap()) - getSummaryCode()) - getSummaryStack()) - getSummaryGraphics();
        }

        public int getSummarySystem() {
            return (getTotalPss() - getTotalPrivateClean()) - getTotalPrivateDirty();
        }

        public int getSummaryJavaHeapRss() {
            return this.dalvikRss + getOtherRss(12);
        }

        public int getSummaryNativeHeapRss() {
            return this.nativeRss;
        }

        public int getSummaryCodeRss() {
            return getOtherRss(6) + getOtherRss(7) + getOtherRss(8) + getOtherRss(9) + getOtherRss(10) + getOtherRss(11) + getOtherRss(23) + getOtherRss(24);
        }

        public int getSummaryStackRss() {
            return getOtherRss(1);
        }

        public int getSummaryGraphicsRss() {
            return getOtherRss(4) + getOtherRss(14) + getOtherRss(15);
        }

        public int getSummaryUnknownRss() {
            return ((((getTotalRss() - getSummaryJavaHeapRss()) - getSummaryNativeHeapRss()) - getSummaryCodeRss()) - getSummaryStackRss()) - getSummaryGraphicsRss();
        }

        public int getSummaryTotalPss() {
            return getTotalPss();
        }

        public int getSummaryTotalSwap() {
            return getTotalSwappedOut();
        }

        public int getSummaryTotalSwapPss() {
            return getTotalSwappedOutPss();
        }

        public boolean hasSwappedOutPss() {
            return this.hasSwappedOutPss;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.dalvikPss);
            parcel.writeInt(this.dalvikSwappablePss);
            parcel.writeInt(this.dalvikRss);
            parcel.writeInt(this.dalvikPrivateDirty);
            parcel.writeInt(this.dalvikSharedDirty);
            parcel.writeInt(this.dalvikPrivateClean);
            parcel.writeInt(this.dalvikSharedClean);
            parcel.writeInt(this.dalvikSwappedOut);
            parcel.writeInt(this.dalvikSwappedOutPss);
            parcel.writeInt(this.nativePss);
            parcel.writeInt(this.nativeSwappablePss);
            parcel.writeInt(this.nativeRss);
            parcel.writeInt(this.nativePrivateDirty);
            parcel.writeInt(this.nativeSharedDirty);
            parcel.writeInt(this.nativePrivateClean);
            parcel.writeInt(this.nativeSharedClean);
            parcel.writeInt(this.nativeSwappedOut);
            parcel.writeInt(this.nativeSwappedOutPss);
            parcel.writeInt(this.otherPss);
            parcel.writeInt(this.otherSwappablePss);
            parcel.writeInt(this.otherRss);
            parcel.writeInt(this.otherPrivateDirty);
            parcel.writeInt(this.otherSharedDirty);
            parcel.writeInt(this.otherPrivateClean);
            parcel.writeInt(this.otherSharedClean);
            parcel.writeInt(this.otherSwappedOut);
            parcel.writeInt(this.hasSwappedOutPss ? 1 : 0);
            parcel.writeInt(this.otherSwappedOutPss);
            parcel.writeIntArray(this.otherStats);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.dalvikPss = parcel.readInt();
            this.dalvikSwappablePss = parcel.readInt();
            this.dalvikRss = parcel.readInt();
            this.dalvikPrivateDirty = parcel.readInt();
            this.dalvikSharedDirty = parcel.readInt();
            this.dalvikPrivateClean = parcel.readInt();
            this.dalvikSharedClean = parcel.readInt();
            this.dalvikSwappedOut = parcel.readInt();
            this.dalvikSwappedOutPss = parcel.readInt();
            this.nativePss = parcel.readInt();
            this.nativeSwappablePss = parcel.readInt();
            this.nativeRss = parcel.readInt();
            this.nativePrivateDirty = parcel.readInt();
            this.nativeSharedDirty = parcel.readInt();
            this.nativePrivateClean = parcel.readInt();
            this.nativeSharedClean = parcel.readInt();
            this.nativeSwappedOut = parcel.readInt();
            this.nativeSwappedOutPss = parcel.readInt();
            this.otherPss = parcel.readInt();
            this.otherSwappablePss = parcel.readInt();
            this.otherRss = parcel.readInt();
            this.otherPrivateDirty = parcel.readInt();
            this.otherSharedDirty = parcel.readInt();
            this.otherPrivateClean = parcel.readInt();
            this.otherSharedClean = parcel.readInt();
            this.otherSwappedOut = parcel.readInt();
            this.hasSwappedOutPss = parcel.readInt() != 0;
            this.otherSwappedOutPss = parcel.readInt();
            this.otherStats = parcel.createIntArray();
        }

        private MemoryInfo(android.os.Parcel parcel) {
            this.otherStats = new int[288];
            readFromParcel(parcel);
        }
    }

    public static void suspendAllAndSendVmStart() {
        if (!dalvik.system.VMDebug.isDebuggingEnabled()) {
            return;
        }
        java.lang.System.out.println("Sending WAIT chunk");
        org.apache.harmony.dalvik.ddmc.DdmServer.sendChunk(new org.apache.harmony.dalvik.ddmc.Chunk(org.apache.harmony.dalvik.ddmc.ChunkHandler.type("WAIT"), new byte[]{0}, 0, 1));
        java.lang.System.out.println("Waiting for debugger first packet");
        mWaiting = true;
        while (!isDebuggerConnected()) {
            try {
                java.lang.Thread.sleep(100L);
            } catch (java.lang.InterruptedException e) {
            }
        }
        mWaiting = false;
        java.lang.System.out.println("Debug.suspendAllAndSentVmStart");
        dalvik.system.VMDebug.suspendAllAndSendVmStart();
        java.lang.System.out.println("Debug.suspendAllAndSendVmStart, resumed");
    }

    public static void waitForDebugger() {
        if (!dalvik.system.VMDebug.isDebuggingEnabled() || isDebuggerConnected()) {
            return;
        }
        java.lang.System.out.println("Sending WAIT chunk");
        org.apache.harmony.dalvik.ddmc.DdmServer.sendChunk(new org.apache.harmony.dalvik.ddmc.Chunk(org.apache.harmony.dalvik.ddmc.ChunkHandler.type("WAIT"), new byte[]{0}, 0, 1));
        mWaiting = true;
        while (!isDebuggerConnected()) {
            try {
                java.lang.Thread.sleep(200L);
            } catch (java.lang.InterruptedException e) {
            }
        }
        mWaiting = false;
        java.lang.System.out.println("Debugger has connected");
        while (true) {
            long lastDebuggerActivity = dalvik.system.VMDebug.lastDebuggerActivity();
            if (lastDebuggerActivity < 0) {
                java.lang.System.out.println("debugger detached?");
                return;
            } else if (lastDebuggerActivity < 1300) {
                java.lang.System.out.println("waiting for debugger to settle...");
                try {
                    java.lang.Thread.sleep(200L);
                } catch (java.lang.InterruptedException e2) {
                }
            } else {
                java.lang.System.out.println("debugger has settled (" + lastDebuggerActivity + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
        }
    }

    public static boolean waitingForDebugger() {
        return mWaiting;
    }

    public static boolean isDebuggerConnected() {
        return dalvik.system.VMDebug.isDebuggerConnected();
    }

    public static java.lang.String[] getVmFeatureList() {
        return dalvik.system.VMDebug.getVmFeatureList();
    }

    @java.lang.Deprecated
    public static void changeDebugPort(int i) {
    }

    public static void startNativeTracing() {
        com.android.internal.util.FastPrintWriter fastPrintWriter;
        java.lang.Throwable th;
        com.android.internal.util.FastPrintWriter fastPrintWriter2 = null;
        try {
            fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(SYSFS_QEMU_TRACE_STATE));
        } catch (java.lang.Exception e) {
        } catch (java.lang.Throwable th2) {
            fastPrintWriter = null;
            th = th2;
        }
        try {
            fastPrintWriter.println("1");
            fastPrintWriter.close();
        } catch (java.lang.Exception e2) {
            fastPrintWriter2 = fastPrintWriter;
            if (fastPrintWriter2 != null) {
                fastPrintWriter2.close();
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
            if (fastPrintWriter != null) {
                fastPrintWriter.close();
            }
            throw th;
        }
    }

    public static void stopNativeTracing() {
        com.android.internal.util.FastPrintWriter fastPrintWriter;
        java.lang.Throwable th;
        com.android.internal.util.FastPrintWriter fastPrintWriter2 = null;
        try {
            fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(SYSFS_QEMU_TRACE_STATE));
        } catch (java.lang.Exception e) {
        } catch (java.lang.Throwable th2) {
            fastPrintWriter = null;
            th = th2;
        }
        try {
            fastPrintWriter.println(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
            fastPrintWriter.close();
        } catch (java.lang.Exception e2) {
            fastPrintWriter2 = fastPrintWriter;
            if (fastPrintWriter2 != null) {
                fastPrintWriter2.close();
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
            if (fastPrintWriter != null) {
                fastPrintWriter.close();
            }
            throw th;
        }
    }

    public static void enableEmulatorTraceOutput() {
        android.util.Log.w(TAG, "Unimplemented");
    }

    public static void startMethodTracing() {
        dalvik.system.VMDebug.startMethodTracing(fixTracePath(null), 0, 0, false, 0);
    }

    public static void startMethodTracing(java.lang.String str) {
        startMethodTracing(str, 0, 0);
    }

    public static void startMethodTracing(java.lang.String str, int i) {
        startMethodTracing(str, i, 0);
    }

    public static void startMethodTracing(java.lang.String str, int i, int i2) {
        dalvik.system.VMDebug.startMethodTracing(fixTracePath(str), i, i2, false, 0);
    }

    public static void startMethodTracingSampling(java.lang.String str, int i, int i2) {
        dalvik.system.VMDebug.startMethodTracing(fixTracePath(str), i, 0, true, i2);
    }

    private static java.lang.String fixTracePath(java.lang.String str) {
        java.io.File externalStorageDirectory;
        if (str == null || str.charAt(0) != '/') {
            android.app.Application initialApplication = android.app.AppGlobals.getInitialApplication();
            if (initialApplication != null) {
                externalStorageDirectory = initialApplication.getExternalFilesDir(null);
            } else {
                externalStorageDirectory = android.os.Environment.getExternalStorageDirectory();
            }
            if (str == null) {
                str = new java.io.File(externalStorageDirectory, DEFAULT_TRACE_BODY).getAbsolutePath();
            } else {
                str = new java.io.File(externalStorageDirectory, str).getAbsolutePath();
            }
        }
        if (!str.endsWith(DEFAULT_TRACE_EXTENSION)) {
            return str + DEFAULT_TRACE_EXTENSION;
        }
        return str;
    }

    public static void startMethodTracing(java.lang.String str, java.io.FileDescriptor fileDescriptor, int i, int i2, boolean z) {
        dalvik.system.VMDebug.startMethodTracing(str, fileDescriptor, i, i2, false, 0, z);
    }

    public static void startMethodTracingDdms(int i, int i2, boolean z, int i3) {
        dalvik.system.VMDebug.startMethodTracingDdms(i, i2, z, i3);
    }

    public static int getMethodTracingMode() {
        return dalvik.system.VMDebug.getMethodTracingMode();
    }

    public static void stopMethodTracing() {
        dalvik.system.VMDebug.stopMethodTracing();
    }

    public static long threadCpuTimeNanos() {
        return dalvik.system.VMDebug.threadCpuTimeNanos();
    }

    @java.lang.Deprecated
    public static void startAllocCounting() {
        dalvik.system.VMDebug.startAllocCounting();
    }

    @java.lang.Deprecated
    public static void stopAllocCounting() {
        dalvik.system.VMDebug.stopAllocCounting();
    }

    @java.lang.Deprecated
    public static int getGlobalAllocCount() {
        return dalvik.system.VMDebug.getAllocCount(1);
    }

    @java.lang.Deprecated
    public static void resetGlobalAllocCount() {
        dalvik.system.VMDebug.resetAllocCount(1);
    }

    @java.lang.Deprecated
    public static int getGlobalAllocSize() {
        return dalvik.system.VMDebug.getAllocCount(2);
    }

    @java.lang.Deprecated
    public static void resetGlobalAllocSize() {
        dalvik.system.VMDebug.resetAllocCount(2);
    }

    @java.lang.Deprecated
    public static int getGlobalFreedCount() {
        return dalvik.system.VMDebug.getAllocCount(4);
    }

    @java.lang.Deprecated
    public static void resetGlobalFreedCount() {
        dalvik.system.VMDebug.resetAllocCount(4);
    }

    @java.lang.Deprecated
    public static int getGlobalFreedSize() {
        return dalvik.system.VMDebug.getAllocCount(8);
    }

    @java.lang.Deprecated
    public static void resetGlobalFreedSize() {
        dalvik.system.VMDebug.resetAllocCount(8);
    }

    @java.lang.Deprecated
    public static int getGlobalGcInvocationCount() {
        return dalvik.system.VMDebug.getAllocCount(16);
    }

    @java.lang.Deprecated
    public static void resetGlobalGcInvocationCount() {
        dalvik.system.VMDebug.resetAllocCount(16);
    }

    @java.lang.Deprecated
    public static int getGlobalClassInitCount() {
        return dalvik.system.VMDebug.getAllocCount(32);
    }

    @java.lang.Deprecated
    public static void resetGlobalClassInitCount() {
        dalvik.system.VMDebug.resetAllocCount(32);
    }

    @java.lang.Deprecated
    public static int getGlobalClassInitTime() {
        return dalvik.system.VMDebug.getAllocCount(64);
    }

    @java.lang.Deprecated
    public static void resetGlobalClassInitTime() {
        dalvik.system.VMDebug.resetAllocCount(64);
    }

    @java.lang.Deprecated
    public static int getGlobalExternalAllocCount() {
        return 0;
    }

    @java.lang.Deprecated
    public static void resetGlobalExternalAllocSize() {
    }

    @java.lang.Deprecated
    public static void resetGlobalExternalAllocCount() {
    }

    @java.lang.Deprecated
    public static int getGlobalExternalAllocSize() {
        return 0;
    }

    @java.lang.Deprecated
    public static int getGlobalExternalFreedCount() {
        return 0;
    }

    @java.lang.Deprecated
    public static void resetGlobalExternalFreedCount() {
    }

    @java.lang.Deprecated
    public static int getGlobalExternalFreedSize() {
        return 0;
    }

    @java.lang.Deprecated
    public static void resetGlobalExternalFreedSize() {
    }

    @java.lang.Deprecated
    public static int getThreadAllocCount() {
        return dalvik.system.VMDebug.getAllocCount(65536);
    }

    @java.lang.Deprecated
    public static void resetThreadAllocCount() {
        dalvik.system.VMDebug.resetAllocCount(65536);
    }

    @java.lang.Deprecated
    public static int getThreadAllocSize() {
        return dalvik.system.VMDebug.getAllocCount(131072);
    }

    @java.lang.Deprecated
    public static void resetThreadAllocSize() {
        dalvik.system.VMDebug.resetAllocCount(131072);
    }

    @java.lang.Deprecated
    public static int getThreadExternalAllocCount() {
        return 0;
    }

    @java.lang.Deprecated
    public static void resetThreadExternalAllocCount() {
    }

    @java.lang.Deprecated
    public static int getThreadExternalAllocSize() {
        return 0;
    }

    @java.lang.Deprecated
    public static void resetThreadExternalAllocSize() {
    }

    @java.lang.Deprecated
    public static int getThreadGcInvocationCount() {
        return dalvik.system.VMDebug.getAllocCount(1048576);
    }

    @java.lang.Deprecated
    public static void resetThreadGcInvocationCount() {
        dalvik.system.VMDebug.resetAllocCount(1048576);
    }

    @java.lang.Deprecated
    public static void resetAllCounts() {
        dalvik.system.VMDebug.resetAllocCount(-1);
    }

    public static java.lang.String getRuntimeStat(java.lang.String str) {
        return dalvik.system.VMDebug.getRuntimeStat(str);
    }

    public static java.util.Map<java.lang.String, java.lang.String> getRuntimeStats() {
        return dalvik.system.VMDebug.getRuntimeStats();
    }

    @java.lang.Deprecated
    public static int setAllocationLimit(int i) {
        return -1;
    }

    @java.lang.Deprecated
    public static int setGlobalAllocationLimit(int i) {
        return -1;
    }

    public static void printLoadedClasses(int i) {
        dalvik.system.VMDebug.printLoadedClasses(i);
    }

    public static int getLoadedClassCount() {
        return dalvik.system.VMDebug.getLoadedClassCount();
    }

    public static void dumpHprofData(java.lang.String str) throws java.io.IOException {
        dalvik.system.VMDebug.dumpHprofData(str);
    }

    public static void dumpHprofData(java.lang.String str, java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        dalvik.system.VMDebug.dumpHprofData(str, fileDescriptor);
    }

    public static void dumpHprofDataDdms() {
        dalvik.system.VMDebug.dumpHprofDataDdms();
    }

    public static long countInstancesOfClass(java.lang.Class cls) {
        return dalvik.system.VMDebug.countInstancesOfClass(cls, true);
    }

    public static final void dumpReferenceTables() {
        dalvik.system.VMDebug.dumpReferenceTables();
    }

    @java.lang.Deprecated
    public static class InstructionCount {
        public boolean resetAndStart() {
            return false;
        }

        public boolean collect() {
            return false;
        }

        public int globalTotal() {
            return 0;
        }

        public int globalMethodInvocations() {
            return 0;
        }
    }

    private static boolean fieldTypeMatches(java.lang.reflect.Field field, java.lang.Class<?> cls) {
        java.lang.Class<?> type = field.getType();
        if (type == cls) {
            return true;
        }
        try {
            try {
                if (type == ((java.lang.Class) cls.getField("TYPE").get(null))) {
                    return true;
                }
                return false;
            } catch (java.lang.IllegalAccessException e) {
                return false;
            }
        } catch (java.lang.NoSuchFieldException e2) {
            return false;
        }
    }

    private static void modifyFieldIfSet(java.lang.reflect.Field field, com.android.internal.util.TypedProperties typedProperties, java.lang.String str) {
        if (field.getType() == java.lang.String.class) {
            int stringInfo = typedProperties.getStringInfo(str);
            switch (stringInfo) {
                case -2:
                    throw new java.lang.IllegalArgumentException("Type of " + str + "  does not match field type (" + field.getType() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                case -1:
                    return;
                case 0:
                    try {
                        field.set(null, null);
                        return;
                    } catch (java.lang.IllegalAccessException e) {
                        throw new java.lang.IllegalArgumentException("Cannot set field for " + str, e);
                    }
                case 1:
                    break;
                default:
                    throw new java.lang.IllegalStateException("Unexpected getStringInfo(" + str + ") return value " + stringInfo);
            }
        }
        java.lang.Object obj = typedProperties.get(str);
        if (obj != null) {
            if (!fieldTypeMatches(field, obj.getClass())) {
                throw new java.lang.IllegalArgumentException("Type of " + str + " (" + obj.getClass() + ")  does not match field type (" + field.getType() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            try {
                field.set(null, obj);
            } catch (java.lang.IllegalAccessException e2) {
                throw new java.lang.IllegalArgumentException("Cannot set field for " + str, e2);
            }
        }
    }

    public static void setFieldsOn(java.lang.Class<?> cls) {
        setFieldsOn(cls, false);
    }

    public static void setFieldsOn(java.lang.Class<?> cls, boolean z) {
        android.util.Log.wtf(TAG, "setFieldsOn(" + (cls == null ? "null" : cls.getName()) + ") called in non-DEBUG build");
    }

    public static boolean dumpService(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) {
        android.os.IBinder service = android.os.ServiceManager.getService(str);
        if (service == null) {
            android.util.Log.e(TAG, "Can't find service to dump: " + str);
            return false;
        }
        try {
            service.dump(fileDescriptor, strArr);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Can't dump service: " + str, e);
            return false;
        }
    }

    private static java.lang.String getCaller(java.lang.StackTraceElement[] stackTraceElementArr, int i) {
        int i2 = i + 4;
        if (i2 >= stackTraceElementArr.length) {
            return "<bottom of call stack>";
        }
        java.lang.StackTraceElement stackTraceElement = stackTraceElementArr[i2];
        return stackTraceElement.getClassName() + android.media.MediaMetrics.SEPARATOR + stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
    }

    public static java.lang.String getCallers(int i) {
        java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(getCaller(stackTrace, i2)).append(" ");
        }
        return sb.toString();
    }

    public static java.lang.String getCallers(int i, int i2) {
        java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int i3 = i2 + i;
        while (i < i3) {
            sb.append(getCaller(stackTrace, i)).append(" ");
            i++;
        }
        return sb.toString();
    }

    public static java.lang.String getCallers(int i, java.lang.String str) {
        java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(str).append(getCaller(stackTrace, i2)).append("\n");
        }
        return sb.toString();
    }

    public static java.lang.String getCaller() {
        return getCaller(java.lang.Thread.currentThread().getStackTrace(), 0);
    }

    public static void attachJvmtiAgent(java.lang.String str, java.lang.String str2, java.lang.ClassLoader classLoader) throws java.io.IOException {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkArgument(!str.contains("="));
        if (str2 != null) {
            dalvik.system.VMDebug.attachAgent(str + "=" + str2, classLoader);
        } else {
            dalvik.system.VMDebug.attachAgent(str, classLoader);
        }
    }
}
