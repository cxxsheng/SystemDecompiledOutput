package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class Settings {
    private final int mType;

    Settings(int i) {
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }
}
