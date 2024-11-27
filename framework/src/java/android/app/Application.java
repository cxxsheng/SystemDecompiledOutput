package android.app;

/* loaded from: classes.dex */
public class Application extends android.content.ContextWrapper implements android.content.ComponentCallbacks2 {
    private static final java.lang.String TAG = "Application";
    private java.util.ArrayList<android.app.Application.ActivityLifecycleCallbacks> mActivityLifecycleCallbacks;
    private java.util.ArrayList<android.app.Application.OnProvideAssistDataListener> mAssistCallbacks;
    private final android.content.ComponentCallbacksController mCallbacksController;
    public android.app.LoadedApk mLoadedApk;

    public interface OnProvideAssistDataListener {
        void onProvideAssistData(android.app.Activity activity, android.os.Bundle bundle);
    }

    public interface ActivityLifecycleCallbacks {
        void onActivityCreated(android.app.Activity activity, android.os.Bundle bundle);

        void onActivityDestroyed(android.app.Activity activity);

        void onActivityPaused(android.app.Activity activity);

        void onActivityResumed(android.app.Activity activity);

        void onActivitySaveInstanceState(android.app.Activity activity, android.os.Bundle bundle);

        void onActivityStarted(android.app.Activity activity);

        void onActivityStopped(android.app.Activity activity);

        default void onActivityPreCreated(android.app.Activity activity, android.os.Bundle bundle) {
        }

        default void onActivityPostCreated(android.app.Activity activity, android.os.Bundle bundle) {
        }

        default void onActivityPreStarted(android.app.Activity activity) {
        }

        default void onActivityPostStarted(android.app.Activity activity) {
        }

        default void onActivityPreResumed(android.app.Activity activity) {
        }

        default void onActivityPostResumed(android.app.Activity activity) {
        }

        default void onActivityPrePaused(android.app.Activity activity) {
        }

        default void onActivityPostPaused(android.app.Activity activity) {
        }

        default void onActivityPreStopped(android.app.Activity activity) {
        }

        default void onActivityPostStopped(android.app.Activity activity) {
        }

        default void onActivityPreSaveInstanceState(android.app.Activity activity, android.os.Bundle bundle) {
        }

        default void onActivityPostSaveInstanceState(android.app.Activity activity, android.os.Bundle bundle) {
        }

        default void onActivityPreDestroyed(android.app.Activity activity) {
        }

        default void onActivityPostDestroyed(android.app.Activity activity) {
        }

        default void onActivityConfigurationChanged(android.app.Activity activity) {
        }
    }

    public Application() {
        super(null);
        this.mActivityLifecycleCallbacks = new java.util.ArrayList<>();
        this.mAssistCallbacks = null;
        this.mCallbacksController = new android.content.ComponentCallbacksController();
    }

    private java.lang.String getLoadedApkInfo() {
        if (this.mLoadedApk == null) {
            return "null";
        }
        return this.mLoadedApk + "/pkg=" + this.mLoadedApk.mPackageName;
    }

    public void onCreate() {
    }

    public void onTerminate() {
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        this.mCallbacksController.dispatchConfigurationChanged(configuration);
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCallbacksController.dispatchLowMemory();
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        this.mCallbacksController.dispatchTrimMemory(i);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.registerCallbacks(componentCallbacks);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.unregisterCallbacks(componentCallbacks);
    }

    public void registerActivityLifecycleCallbacks(android.app.Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.add(activityLifecycleCallbacks);
        }
    }

    public void unregisterActivityLifecycleCallbacks(android.app.Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.remove(activityLifecycleCallbacks);
        }
    }

    public void registerOnProvideAssistDataListener(android.app.Application.OnProvideAssistDataListener onProvideAssistDataListener) {
        synchronized (this) {
            if (this.mAssistCallbacks == null) {
                this.mAssistCallbacks = new java.util.ArrayList<>();
            }
            this.mAssistCallbacks.add(onProvideAssistDataListener);
        }
    }

    public void unregisterOnProvideAssistDataListener(android.app.Application.OnProvideAssistDataListener onProvideAssistDataListener) {
        synchronized (this) {
            if (this.mAssistCallbacks != null) {
                this.mAssistCallbacks.remove(onProvideAssistDataListener);
            }
        }
    }

    public static java.lang.String getProcessName() {
        return android.app.ActivityThread.currentProcessName();
    }

    final void attach(android.content.Context context) {
        attachBaseContext(context);
        this.mLoadedApk = android.app.ContextImpl.getImpl(context).mPackageInfo;
    }

    void dispatchActivityPreCreated(android.app.Activity activity, android.os.Bundle bundle) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPreCreated(activity, bundle);
            }
        }
    }

    void dispatchActivityCreated(android.app.Activity activity, android.os.Bundle bundle) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityCreated(activity, bundle);
            }
        }
    }

    void dispatchActivityPostCreated(android.app.Activity activity, android.os.Bundle bundle) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPostCreated(activity, bundle);
            }
        }
    }

    void dispatchActivityPreStarted(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPreStarted(activity);
            }
        }
    }

    void dispatchActivityStarted(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityStarted(activity);
            }
        }
    }

    void dispatchActivityPostStarted(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPostStarted(activity);
            }
        }
    }

    void dispatchActivityPreResumed(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPreResumed(activity);
            }
        }
    }

    void dispatchActivityResumed(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityResumed(activity);
            }
        }
    }

    void dispatchActivityPostResumed(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPostResumed(activity);
            }
        }
    }

    void dispatchActivityPrePaused(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPrePaused(activity);
            }
        }
    }

    void dispatchActivityPaused(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPaused(activity);
            }
        }
    }

    void dispatchActivityPostPaused(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPostPaused(activity);
            }
        }
    }

    void dispatchActivityPreStopped(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPreStopped(activity);
            }
        }
    }

    void dispatchActivityStopped(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityStopped(activity);
            }
        }
    }

    void dispatchActivityPostStopped(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPostStopped(activity);
            }
        }
    }

    void dispatchActivityPreSaveInstanceState(android.app.Activity activity, android.os.Bundle bundle) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPreSaveInstanceState(activity, bundle);
            }
        }
    }

    void dispatchActivitySaveInstanceState(android.app.Activity activity, android.os.Bundle bundle) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivitySaveInstanceState(activity, bundle);
            }
        }
    }

    void dispatchActivityPostSaveInstanceState(android.app.Activity activity, android.os.Bundle bundle) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPostSaveInstanceState(activity, bundle);
            }
        }
    }

    void dispatchActivityPreDestroyed(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPreDestroyed(activity);
            }
        }
    }

    void dispatchActivityDestroyed(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityDestroyed(activity);
            }
        }
    }

    void dispatchActivityPostDestroyed(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPostDestroyed(activity);
            }
        }
    }

    void dispatchActivityConfigurationChanged(android.app.Activity activity) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityConfigurationChanged(activity);
            }
        }
    }

    private java.lang.Object[] collectActivityLifecycleCallbacks() {
        java.lang.Object[] objArr;
        synchronized (this.mActivityLifecycleCallbacks) {
            if (this.mActivityLifecycleCallbacks.size() <= 0) {
                objArr = null;
            } else {
                objArr = this.mActivityLifecycleCallbacks.toArray();
            }
        }
        return objArr;
    }

    void dispatchOnProvideAssistData(android.app.Activity activity, android.os.Bundle bundle) {
        synchronized (this) {
            if (this.mAssistCallbacks == null) {
                return;
            }
            java.lang.Object[] array = this.mAssistCallbacks.toArray();
            if (array != null) {
                for (java.lang.Object obj : array) {
                    ((android.app.Application.OnProvideAssistDataListener) obj).onProvideAssistData(activity, bundle);
                }
            }
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.view.autofill.AutofillManager.AutofillClient getAutofillClient() {
        android.app.Activity activity;
        android.view.autofill.AutofillManager.AutofillClient autofillClient = super.getAutofillClient();
        if (autofillClient != null) {
            return autofillClient;
        }
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v(TAG, "getAutofillClient(): null on super, trying to find activity thread");
        }
        android.app.ActivityThread currentActivityThread = android.app.ActivityThread.currentActivityThread();
        if (currentActivityThread == null) {
            return null;
        }
        int size = currentActivityThread.mActivities.size();
        for (int i = 0; i < size; i++) {
            android.app.ActivityThread.ActivityClientRecord valueAt = currentActivityThread.mActivities.valueAt(i);
            if (valueAt != null && (activity = valueAt.activity) != null) {
                if (valueAt.isTopResumedActivity) {
                    if (android.view.autofill.Helper.sVerbose) {
                        android.util.Log.v(TAG, "getAutofillClient(): found top resumed activity for " + this + ": " + activity);
                    }
                    return activity.getAutofillClient();
                }
                if (activity.getWindow().getDecorView().hasFocus()) {
                    if (android.view.autofill.Helper.sVerbose) {
                        android.util.Log.v(TAG, "getAutofillClient(): found focused activity for " + this + ": " + activity);
                    }
                    return activity.getAutofillClient();
                }
            }
        }
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v(TAG, "getAutofillClient(): none of the " + size + " activities on " + this + " are top resumed nor have focus");
        }
        return null;
    }
}
