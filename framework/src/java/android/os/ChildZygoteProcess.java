package android.os;

/* loaded from: classes3.dex */
public class ChildZygoteProcess extends android.os.ZygoteProcess {
    private final int mPid;

    ChildZygoteProcess(android.net.LocalSocketAddress localSocketAddress, int i) {
        super(localSocketAddress, null);
        this.mPid = i;
    }

    public int getPid() {
        return this.mPid;
    }
}
