package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class FilterConfiguration {
    final android.media.tv.tuner.filter.Settings mSettings;

    public abstract int getType();

    FilterConfiguration(android.media.tv.tuner.filter.Settings settings) {
        this.mSettings = settings;
    }

    public android.media.tv.tuner.filter.Settings getSettings() {
        return this.mSettings;
    }
}
