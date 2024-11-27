package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public abstract class FragmentTransaction {
    public static final int TRANSIT_ENTER_MASK = 4096;
    public static final int TRANSIT_EXIT_MASK = 8192;
    public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
    public static final int TRANSIT_FRAGMENT_FADE = 4099;
    public static final int TRANSIT_FRAGMENT_OPEN = 4097;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_UNSET = -1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Transit {
    }

    public abstract android.app.FragmentTransaction add(int i, android.app.Fragment fragment);

    public abstract android.app.FragmentTransaction add(int i, android.app.Fragment fragment, java.lang.String str);

    public abstract android.app.FragmentTransaction add(android.app.Fragment fragment, java.lang.String str);

    public abstract android.app.FragmentTransaction addSharedElement(android.view.View view, java.lang.String str);

    public abstract android.app.FragmentTransaction addToBackStack(java.lang.String str);

    public abstract android.app.FragmentTransaction attach(android.app.Fragment fragment);

    public abstract int commit();

    public abstract int commitAllowingStateLoss();

    public abstract void commitNow();

    public abstract void commitNowAllowingStateLoss();

    public abstract android.app.FragmentTransaction detach(android.app.Fragment fragment);

    public abstract android.app.FragmentTransaction disallowAddToBackStack();

    public abstract android.app.FragmentTransaction hide(android.app.Fragment fragment);

    public abstract boolean isAddToBackStackAllowed();

    public abstract boolean isEmpty();

    public abstract android.app.FragmentTransaction remove(android.app.Fragment fragment);

    public abstract android.app.FragmentTransaction replace(int i, android.app.Fragment fragment);

    public abstract android.app.FragmentTransaction replace(int i, android.app.Fragment fragment, java.lang.String str);

    public abstract android.app.FragmentTransaction runOnCommit(java.lang.Runnable runnable);

    public abstract android.app.FragmentTransaction setBreadCrumbShortTitle(int i);

    public abstract android.app.FragmentTransaction setBreadCrumbShortTitle(java.lang.CharSequence charSequence);

    public abstract android.app.FragmentTransaction setBreadCrumbTitle(int i);

    public abstract android.app.FragmentTransaction setBreadCrumbTitle(java.lang.CharSequence charSequence);

    public abstract android.app.FragmentTransaction setCustomAnimations(int i, int i2);

    public abstract android.app.FragmentTransaction setCustomAnimations(int i, int i2, int i3, int i4);

    public abstract android.app.FragmentTransaction setPrimaryNavigationFragment(android.app.Fragment fragment);

    public abstract android.app.FragmentTransaction setReorderingAllowed(boolean z);

    public abstract android.app.FragmentTransaction setTransition(int i);

    public abstract android.app.FragmentTransaction setTransitionStyle(int i);

    public abstract android.app.FragmentTransaction show(android.app.Fragment fragment);
}
