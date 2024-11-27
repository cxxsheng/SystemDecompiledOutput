package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public abstract class LoaderManager {

    @java.lang.Deprecated
    public interface LoaderCallbacks<D> {
        android.content.Loader<D> onCreateLoader(int i, android.os.Bundle bundle);

        void onLoadFinished(android.content.Loader<D> loader, D d);

        void onLoaderReset(android.content.Loader<D> loader);
    }

    public abstract void destroyLoader(int i);

    public abstract void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr);

    public abstract <D> android.content.Loader<D> getLoader(int i);

    public abstract <D> android.content.Loader<D> initLoader(int i, android.os.Bundle bundle, android.app.LoaderManager.LoaderCallbacks<D> loaderCallbacks);

    public abstract <D> android.content.Loader<D> restartLoader(int i, android.os.Bundle bundle, android.app.LoaderManager.LoaderCallbacks<D> loaderCallbacks);

    public static void enableDebugLogging(boolean z) {
        android.app.LoaderManagerImpl.DEBUG = z;
    }

    public android.app.FragmentHostCallback getFragmentHostCallback() {
        return null;
    }
}
