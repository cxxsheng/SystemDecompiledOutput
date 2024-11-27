package android.filterfw.core;

/* loaded from: classes.dex */
public class FilterFactory {
    private static android.filterfw.core.FilterFactory mSharedFactory;
    private java.util.HashSet<java.lang.String> mPackages = new java.util.HashSet<>();
    private static java.lang.ClassLoader mCurrentClassLoader = java.lang.Thread.currentThread().getContextClassLoader();
    private static java.util.HashSet<java.lang.String> mLibraries = new java.util.HashSet<>();
    private static java.lang.Object mClassLoaderGuard = new java.lang.Object();
    private static final java.lang.String TAG = "FilterFactory";
    private static boolean mLogVerbose = android.util.Log.isLoggable(TAG, 2);

    public static android.filterfw.core.FilterFactory sharedFactory() {
        if (mSharedFactory == null) {
            mSharedFactory = new android.filterfw.core.FilterFactory();
        }
        return mSharedFactory;
    }

    public static void addFilterLibrary(java.lang.String str) {
        if (mLogVerbose) {
            android.util.Log.v(TAG, "Adding filter library " + str);
        }
        synchronized (mClassLoaderGuard) {
            if (mLibraries.contains(str)) {
                if (mLogVerbose) {
                    android.util.Log.v(TAG, "Library already added");
                }
            } else {
                mLibraries.add(str);
                mCurrentClassLoader = new dalvik.system.PathClassLoader(str, mCurrentClassLoader);
            }
        }
    }

    public void addPackage(java.lang.String str) {
        if (mLogVerbose) {
            android.util.Log.v(TAG, "Adding package " + str);
        }
        this.mPackages.add(str);
    }

    public android.filterfw.core.Filter createFilterByClassName(java.lang.String str, java.lang.String str2) {
        if (mLogVerbose) {
            android.util.Log.v(TAG, "Looking up class " + str);
        }
        java.util.Iterator<java.lang.String> it = this.mPackages.iterator();
        java.lang.Class<?> cls = null;
        while (it.hasNext()) {
            java.lang.String next = it.next();
            try {
                if (mLogVerbose) {
                    android.util.Log.v(TAG, "Trying " + next + android.media.MediaMetrics.SEPARATOR + str);
                }
                synchronized (mClassLoaderGuard) {
                    cls = mCurrentClassLoader.loadClass(next + android.media.MediaMetrics.SEPARATOR + str);
                }
            } catch (java.lang.ClassNotFoundException e) {
            }
            if (cls != null) {
                break;
            }
        }
        if (cls == null) {
            throw new java.lang.IllegalArgumentException("Unknown filter class '" + str + "'!");
        }
        return createFilterByClass(cls, str2);
    }

    public android.filterfw.core.Filter createFilterByClass(java.lang.Class cls, java.lang.String str) {
        android.filterfw.core.Filter filter;
        if (!android.filterfw.core.Filter.class.isAssignableFrom(cls)) {
            throw new java.lang.IllegalArgumentException("Attempting to allocate class '" + cls + "' which is not a subclass of Filter!");
        }
        try {
            try {
                filter = (android.filterfw.core.Filter) cls.getConstructor(java.lang.String.class).newInstance(str);
            } catch (java.lang.Throwable th) {
                filter = null;
            }
            if (filter == null) {
                throw new java.lang.IllegalArgumentException("Could not construct the filter '" + str + "'!");
            }
            return filter;
        } catch (java.lang.NoSuchMethodException e) {
            throw new java.lang.IllegalArgumentException("The filter class '" + cls + "' does not have a constructor of the form <init>(String name)!");
        }
    }
}
