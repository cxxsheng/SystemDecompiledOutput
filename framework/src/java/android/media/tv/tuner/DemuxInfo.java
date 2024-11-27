package android.media.tv.tuner;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class DemuxInfo {
    private int mFilterTypes;

    public DemuxInfo(int i) {
        setFilterTypes(i);
    }

    public int getFilterTypes() {
        return this.mFilterTypes;
    }

    public void setFilterTypes(int i) {
        this.mFilterTypes = i;
    }
}
