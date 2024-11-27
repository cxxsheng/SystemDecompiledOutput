package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class FragmentController {
    private final android.app.FragmentHostCallback<?> mHost;

    public static final android.app.FragmentController createController(android.app.FragmentHostCallback<?> fragmentHostCallback) {
        return new android.app.FragmentController(fragmentHostCallback);
    }

    private FragmentController(android.app.FragmentHostCallback<?> fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    public android.app.FragmentManager getFragmentManager() {
        return this.mHost.getFragmentManagerImpl();
    }

    public android.app.LoaderManager getLoaderManager() {
        return this.mHost.getLoaderManagerImpl();
    }

    public android.app.Fragment findFragmentByWho(java.lang.String str) {
        return this.mHost.mFragmentManager.findFragmentByWho(str);
    }

    public void attachHost(android.app.Fragment fragment) {
        this.mHost.mFragmentManager.attachController(this.mHost, this.mHost, fragment);
    }

    public android.view.View onCreateView(android.view.View view, java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet) {
        return this.mHost.mFragmentManager.onCreateView(view, str, context, attributeSet);
    }

    public void noteStateNotSaved() {
        this.mHost.mFragmentManager.noteStateNotSaved();
    }

    public android.os.Parcelable saveAllState() {
        return this.mHost.mFragmentManager.saveAllState();
    }

    @java.lang.Deprecated
    public void restoreAllState(android.os.Parcelable parcelable, java.util.List<android.app.Fragment> list) {
        this.mHost.mFragmentManager.restoreAllState(parcelable, new android.app.FragmentManagerNonConfig(list, null));
    }

    public void restoreAllState(android.os.Parcelable parcelable, android.app.FragmentManagerNonConfig fragmentManagerNonConfig) {
        this.mHost.mFragmentManager.restoreAllState(parcelable, fragmentManagerNonConfig);
    }

    @java.lang.Deprecated
    public java.util.List<android.app.Fragment> retainNonConfig() {
        return this.mHost.mFragmentManager.retainNonConfig().getFragments();
    }

    public android.app.FragmentManagerNonConfig retainNestedNonConfig() {
        return this.mHost.mFragmentManager.retainNonConfig();
    }

    public void dispatchCreate() {
        this.mHost.mFragmentManager.dispatchCreate();
    }

    public void dispatchActivityCreated() {
        this.mHost.mFragmentManager.dispatchActivityCreated();
    }

    public void dispatchStart() {
        this.mHost.mFragmentManager.dispatchStart();
    }

    public void dispatchResume() {
        this.mHost.mFragmentManager.dispatchResume();
    }

    public void dispatchPause() {
        this.mHost.mFragmentManager.dispatchPause();
    }

    public void dispatchStop() {
        this.mHost.mFragmentManager.dispatchStop();
    }

    public void dispatchDestroyView() {
        this.mHost.mFragmentManager.dispatchDestroyView();
    }

    public void dispatchDestroy() {
        this.mHost.mFragmentManager.dispatchDestroy();
    }

    @java.lang.Deprecated
    public void dispatchMultiWindowModeChanged(boolean z) {
        this.mHost.mFragmentManager.dispatchMultiWindowModeChanged(z);
    }

    public void dispatchMultiWindowModeChanged(boolean z, android.content.res.Configuration configuration) {
        this.mHost.mFragmentManager.dispatchMultiWindowModeChanged(z, configuration);
    }

    @java.lang.Deprecated
    public void dispatchPictureInPictureModeChanged(boolean z) {
        this.mHost.mFragmentManager.dispatchPictureInPictureModeChanged(z);
    }

    public void dispatchPictureInPictureModeChanged(boolean z, android.content.res.Configuration configuration) {
        this.mHost.mFragmentManager.dispatchPictureInPictureModeChanged(z, configuration);
    }

    public void dispatchConfigurationChanged(android.content.res.Configuration configuration) {
        this.mHost.mFragmentManager.dispatchConfigurationChanged(configuration);
    }

    public void dispatchLowMemory() {
        this.mHost.mFragmentManager.dispatchLowMemory();
    }

    public void dispatchTrimMemory(int i) {
        this.mHost.mFragmentManager.dispatchTrimMemory(i);
    }

    public boolean dispatchCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater menuInflater) {
        return this.mHost.mFragmentManager.dispatchCreateOptionsMenu(menu, menuInflater);
    }

    public boolean dispatchPrepareOptionsMenu(android.view.Menu menu) {
        return this.mHost.mFragmentManager.dispatchPrepareOptionsMenu(menu);
    }

    public boolean dispatchOptionsItemSelected(android.view.MenuItem menuItem) {
        return this.mHost.mFragmentManager.dispatchOptionsItemSelected(menuItem);
    }

    public boolean dispatchContextItemSelected(android.view.MenuItem menuItem) {
        return this.mHost.mFragmentManager.dispatchContextItemSelected(menuItem);
    }

    public void dispatchOptionsMenuClosed(android.view.Menu menu) {
        this.mHost.mFragmentManager.dispatchOptionsMenuClosed(menu);
    }

    public boolean execPendingActions() {
        return this.mHost.mFragmentManager.execPendingActions();
    }

    public void doLoaderStart() {
        this.mHost.doLoaderStart();
    }

    public void doLoaderStop(boolean z) {
        this.mHost.doLoaderStop(z);
    }

    public void doLoaderDestroy() {
        this.mHost.doLoaderDestroy();
    }

    public void reportLoaderStart() {
        this.mHost.reportLoaderStart();
    }

    public android.util.ArrayMap<java.lang.String, android.app.LoaderManager> retainLoaderNonConfig() {
        return this.mHost.retainLoaderNonConfig();
    }

    public void restoreLoaderNonConfig(android.util.ArrayMap<java.lang.String, android.app.LoaderManager> arrayMap) {
        this.mHost.restoreLoaderNonConfig(arrayMap);
    }

    public void dumpLoaders(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        this.mHost.dumpLoaders(str, fileDescriptor, printWriter, strArr);
    }
}
