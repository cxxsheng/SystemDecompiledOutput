package android.os;

/* loaded from: classes3.dex */
public class StatFs {
    private android.system.StructStatVfs mStat;

    public StatFs(java.lang.String str) {
        this.mStat = doStat(str);
    }

    private static android.system.StructStatVfs doStat(java.lang.String str) {
        try {
            return android.system.Os.statvfs(str);
        } catch (android.system.ErrnoException e) {
            throw new java.lang.IllegalArgumentException("Invalid path: " + str, e);
        }
    }

    public void restat(java.lang.String str) {
        this.mStat = doStat(str);
    }

    @java.lang.Deprecated
    public int getBlockSize() {
        return (int) this.mStat.f_frsize;
    }

    public long getBlockSizeLong() {
        return this.mStat.f_frsize;
    }

    @java.lang.Deprecated
    public int getBlockCount() {
        return (int) this.mStat.f_blocks;
    }

    public long getBlockCountLong() {
        return this.mStat.f_blocks;
    }

    @java.lang.Deprecated
    public int getFreeBlocks() {
        return (int) this.mStat.f_bfree;
    }

    public long getFreeBlocksLong() {
        return this.mStat.f_bfree;
    }

    public long getFreeBytes() {
        return this.mStat.f_bfree * this.mStat.f_frsize;
    }

    @java.lang.Deprecated
    public int getAvailableBlocks() {
        return (int) this.mStat.f_bavail;
    }

    public long getAvailableBlocksLong() {
        return this.mStat.f_bavail;
    }

    public long getAvailableBytes() {
        return this.mStat.f_bavail * this.mStat.f_frsize;
    }

    public long getTotalBytes() {
        return this.mStat.f_blocks * this.mStat.f_frsize;
    }
}
