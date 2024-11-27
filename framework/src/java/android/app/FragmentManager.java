package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public abstract class FragmentManager {
    public static final int POP_BACK_STACK_INCLUSIVE = 1;

    @java.lang.Deprecated
    public interface BackStackEntry {
        java.lang.CharSequence getBreadCrumbShortTitle();

        int getBreadCrumbShortTitleRes();

        java.lang.CharSequence getBreadCrumbTitle();

        int getBreadCrumbTitleRes();

        int getId();

        java.lang.String getName();
    }

    @java.lang.Deprecated
    public interface OnBackStackChangedListener {
        void onBackStackChanged();
    }

    public abstract void addOnBackStackChangedListener(android.app.FragmentManager.OnBackStackChangedListener onBackStackChangedListener);

    public abstract android.app.FragmentTransaction beginTransaction();

    public abstract void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr);

    public abstract boolean executePendingTransactions();

    public abstract android.app.Fragment findFragmentById(int i);

    public abstract android.app.Fragment findFragmentByTag(java.lang.String str);

    public abstract android.app.FragmentManager.BackStackEntry getBackStackEntryAt(int i);

    public abstract int getBackStackEntryCount();

    public abstract android.app.Fragment getFragment(android.os.Bundle bundle, java.lang.String str);

    public abstract java.util.List<android.app.Fragment> getFragments();

    public abstract android.app.Fragment getPrimaryNavigationFragment();

    public abstract boolean isDestroyed();

    public abstract boolean isStateSaved();

    public abstract void popBackStack();

    public abstract void popBackStack(int i, int i2);

    public abstract void popBackStack(java.lang.String str, int i);

    public abstract boolean popBackStackImmediate();

    public abstract boolean popBackStackImmediate(int i, int i2);

    public abstract boolean popBackStackImmediate(java.lang.String str, int i);

    public abstract void putFragment(android.os.Bundle bundle, java.lang.String str, android.app.Fragment fragment);

    public abstract void registerFragmentLifecycleCallbacks(android.app.FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z);

    public abstract void removeOnBackStackChangedListener(android.app.FragmentManager.OnBackStackChangedListener onBackStackChangedListener);

    public abstract android.app.Fragment.SavedState saveFragmentInstanceState(android.app.Fragment fragment);

    public abstract void unregisterFragmentLifecycleCallbacks(android.app.FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks);

    @java.lang.Deprecated
    public android.app.FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    public static void enableDebugLogging(boolean z) {
        android.app.FragmentManagerImpl.DEBUG = z;
    }

    public void invalidateOptionsMenu() {
    }

    @java.lang.Deprecated
    public static abstract class FragmentLifecycleCallbacks {
        public void onFragmentPreAttached(android.app.FragmentManager fragmentManager, android.app.Fragment fragment, android.content.Context context) {
        }

        public void onFragmentAttached(android.app.FragmentManager fragmentManager, android.app.Fragment fragment, android.content.Context context) {
        }

        public void onFragmentPreCreated(android.app.FragmentManager fragmentManager, android.app.Fragment fragment, android.os.Bundle bundle) {
        }

        public void onFragmentCreated(android.app.FragmentManager fragmentManager, android.app.Fragment fragment, android.os.Bundle bundle) {
        }

        public void onFragmentActivityCreated(android.app.FragmentManager fragmentManager, android.app.Fragment fragment, android.os.Bundle bundle) {
        }

        public void onFragmentViewCreated(android.app.FragmentManager fragmentManager, android.app.Fragment fragment, android.view.View view, android.os.Bundle bundle) {
        }

        public void onFragmentStarted(android.app.FragmentManager fragmentManager, android.app.Fragment fragment) {
        }

        public void onFragmentResumed(android.app.FragmentManager fragmentManager, android.app.Fragment fragment) {
        }

        public void onFragmentPaused(android.app.FragmentManager fragmentManager, android.app.Fragment fragment) {
        }

        public void onFragmentStopped(android.app.FragmentManager fragmentManager, android.app.Fragment fragment) {
        }

        public void onFragmentSaveInstanceState(android.app.FragmentManager fragmentManager, android.app.Fragment fragment, android.os.Bundle bundle) {
        }

        public void onFragmentViewDestroyed(android.app.FragmentManager fragmentManager, android.app.Fragment fragment) {
        }

        public void onFragmentDestroyed(android.app.FragmentManager fragmentManager, android.app.Fragment fragment) {
        }

        public void onFragmentDetached(android.app.FragmentManager fragmentManager, android.app.Fragment fragment) {
        }
    }
}
