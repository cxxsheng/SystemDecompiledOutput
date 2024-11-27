package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public abstract class FragmentHostCallback<E> extends android.app.FragmentContainer {
    private final android.app.Activity mActivity;
    private android.util.ArrayMap<java.lang.String, android.app.LoaderManager> mAllLoaderManagers;
    private boolean mCheckedForLoaderManager;
    final android.content.Context mContext;
    final android.app.FragmentManagerImpl mFragmentManager;
    private final android.os.Handler mHandler;
    private android.app.LoaderManagerImpl mLoaderManager;
    private boolean mLoadersStarted;
    private boolean mRetainLoaders;
    final int mWindowAnimations;

    public abstract E onGetHost();

    public FragmentHostCallback(android.content.Context context, android.os.Handler handler, int i) {
        this(context instanceof android.app.Activity ? (android.app.Activity) context : null, context, chooseHandler(context, handler), i);
    }

    FragmentHostCallback(android.app.Activity activity) {
        this(activity, activity, activity.mHandler, 0);
    }

    FragmentHostCallback(android.app.Activity activity, android.content.Context context, android.os.Handler handler, int i) {
        this.mFragmentManager = new android.app.FragmentManagerImpl();
        this.mActivity = activity;
        this.mContext = context;
        this.mHandler = handler;
        this.mWindowAnimations = i;
    }

    private static android.os.Handler chooseHandler(android.content.Context context, android.os.Handler handler) {
        if (handler == null && (context instanceof android.app.Activity)) {
            return ((android.app.Activity) context).mHandler;
        }
        return handler;
    }

    public void onDump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
    }

    public boolean onShouldSaveFragmentState(android.app.Fragment fragment) {
        return true;
    }

    public android.view.LayoutInflater onGetLayoutInflater() {
        return (android.view.LayoutInflater) this.mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
    }

    public boolean onUseFragmentManagerInflaterFactory() {
        return false;
    }

    public void onInvalidateOptionsMenu() {
    }

    public void onStartActivityFromFragment(android.app.Fragment fragment, android.content.Intent intent, int i, android.os.Bundle bundle) {
        if (i != -1) {
            throw new java.lang.IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
        }
        this.mContext.startActivity(intent);
    }

    public void onStartActivityAsUserFromFragment(android.app.Fragment fragment, android.content.Intent intent, int i, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        if (i != -1) {
            throw new java.lang.IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
        }
        this.mContext.startActivityAsUser(intent, userHandle);
    }

    public void onStartIntentSenderFromFragment(android.app.Fragment fragment, android.content.IntentSender intentSender, int i, android.content.Intent intent, int i2, int i3, int i4, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        if (i != -1) {
            throw new java.lang.IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
        }
        this.mContext.startIntentSender(intentSender, intent, i2, i3, i4, bundle);
    }

    public void onRequestPermissionsFromFragment(android.app.Fragment fragment, java.lang.String[] strArr, int i) {
    }

    public boolean onHasWindowAnimations() {
        return true;
    }

    public int onGetWindowAnimations() {
        return this.mWindowAnimations;
    }

    public void onAttachFragment(android.app.Fragment fragment) {
    }

    @Override // android.app.FragmentContainer
    public <T extends android.view.View> T onFindViewById(int i) {
        return null;
    }

    @Override // android.app.FragmentContainer
    public boolean onHasView() {
        return true;
    }

    boolean getRetainLoaders() {
        return this.mRetainLoaders;
    }

    android.app.Activity getActivity() {
        return this.mActivity;
    }

    android.content.Context getContext() {
        return this.mContext;
    }

    android.os.Handler getHandler() {
        return this.mHandler;
    }

    android.app.FragmentManagerImpl getFragmentManagerImpl() {
        return this.mFragmentManager;
    }

    android.app.LoaderManagerImpl getLoaderManagerImpl() {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        this.mCheckedForLoaderManager = true;
        this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, true);
        return this.mLoaderManager;
    }

    void inactivateFragment(java.lang.String str) {
        android.app.LoaderManagerImpl loaderManagerImpl;
        if (this.mAllLoaderManagers != null && (loaderManagerImpl = (android.app.LoaderManagerImpl) this.mAllLoaderManagers.get(str)) != null && !loaderManagerImpl.mRetaining) {
            loaderManagerImpl.doDestroy();
            this.mAllLoaderManagers.remove(str);
        }
    }

    void doLoaderStart() {
        if (this.mLoadersStarted) {
            return;
        }
        this.mLoadersStarted = true;
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doStart();
        } else if (!this.mCheckedForLoaderManager) {
            this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, false);
        }
        this.mCheckedForLoaderManager = true;
    }

    void doLoaderStop(boolean z) {
        this.mRetainLoaders = z;
        if (this.mLoaderManager == null || !this.mLoadersStarted) {
            return;
        }
        this.mLoadersStarted = false;
        if (z) {
            this.mLoaderManager.doRetain();
        } else {
            this.mLoaderManager.doStop();
        }
    }

    void doLoaderRetain() {
        if (this.mLoaderManager == null) {
            return;
        }
        this.mLoaderManager.doRetain();
    }

    void doLoaderDestroy() {
        if (this.mLoaderManager == null) {
            return;
        }
        this.mLoaderManager.doDestroy();
    }

    void reportLoaderStart() {
        if (this.mAllLoaderManagers != null) {
            int size = this.mAllLoaderManagers.size();
            android.app.LoaderManagerImpl[] loaderManagerImplArr = new android.app.LoaderManagerImpl[size];
            for (int i = size - 1; i >= 0; i--) {
                loaderManagerImplArr[i] = (android.app.LoaderManagerImpl) this.mAllLoaderManagers.valueAt(i);
            }
            for (int i2 = 0; i2 < size; i2++) {
                android.app.LoaderManagerImpl loaderManagerImpl = loaderManagerImplArr[i2];
                loaderManagerImpl.finishRetain();
                loaderManagerImpl.doReportStart();
            }
        }
    }

    android.app.LoaderManagerImpl getLoaderManager(java.lang.String str, boolean z, boolean z2) {
        if (this.mAllLoaderManagers == null) {
            this.mAllLoaderManagers = new android.util.ArrayMap<>();
        }
        android.app.LoaderManagerImpl loaderManagerImpl = (android.app.LoaderManagerImpl) this.mAllLoaderManagers.get(str);
        if (loaderManagerImpl == null && z2) {
            android.app.LoaderManagerImpl loaderManagerImpl2 = new android.app.LoaderManagerImpl(str, this, z);
            this.mAllLoaderManagers.put(str, loaderManagerImpl2);
            return loaderManagerImpl2;
        }
        if (z && loaderManagerImpl != null && !loaderManagerImpl.mStarted) {
            loaderManagerImpl.doStart();
            return loaderManagerImpl;
        }
        return loaderManagerImpl;
    }

    android.util.ArrayMap<java.lang.String, android.app.LoaderManager> retainLoaderNonConfig() {
        int i = 0;
        if (this.mAllLoaderManagers != null) {
            int size = this.mAllLoaderManagers.size();
            android.app.LoaderManagerImpl[] loaderManagerImplArr = new android.app.LoaderManagerImpl[size];
            for (int i2 = size - 1; i2 >= 0; i2--) {
                loaderManagerImplArr[i2] = (android.app.LoaderManagerImpl) this.mAllLoaderManagers.valueAt(i2);
            }
            boolean retainLoaders = getRetainLoaders();
            int i3 = 0;
            while (i < size) {
                android.app.LoaderManagerImpl loaderManagerImpl = loaderManagerImplArr[i];
                if (!loaderManagerImpl.mRetaining && retainLoaders) {
                    if (!loaderManagerImpl.mStarted) {
                        loaderManagerImpl.doStart();
                    }
                    loaderManagerImpl.doRetain();
                }
                if (loaderManagerImpl.mRetaining) {
                    i3 = 1;
                } else {
                    loaderManagerImpl.doDestroy();
                    this.mAllLoaderManagers.remove(loaderManagerImpl.mWho);
                }
                i++;
            }
            i = i3;
        }
        if (i != 0) {
            return this.mAllLoaderManagers;
        }
        return null;
    }

    void restoreLoaderNonConfig(android.util.ArrayMap<java.lang.String, android.app.LoaderManager> arrayMap) {
        if (arrayMap != null) {
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                ((android.app.LoaderManagerImpl) arrayMap.valueAt(i)).updateHostController(this);
            }
        }
        this.mAllLoaderManagers = arrayMap;
    }

    void dumpLoaders(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.print(str);
        printWriter.print("mLoadersStarted=");
        printWriter.println(this.mLoadersStarted);
        if (this.mLoaderManager != null) {
            printWriter.print(str);
            printWriter.print("Loader Manager ");
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mLoaderManager)));
            printWriter.println(":");
            this.mLoaderManager.dump(str + "  ", fileDescriptor, printWriter, strArr);
        }
    }
}
