package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class FragmentManagerNonConfig {
    private final java.util.List<android.app.FragmentManagerNonConfig> mChildNonConfigs;
    private final java.util.List<android.app.Fragment> mFragments;

    FragmentManagerNonConfig(java.util.List<android.app.Fragment> list, java.util.List<android.app.FragmentManagerNonConfig> list2) {
        this.mFragments = list;
        this.mChildNonConfigs = list2;
    }

    java.util.List<android.app.Fragment> getFragments() {
        return this.mFragments;
    }

    java.util.List<android.app.FragmentManagerNonConfig> getChildNonConfigs() {
        return this.mChildNonConfigs;
    }
}
