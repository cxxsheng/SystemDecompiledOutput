package android.window;

/* loaded from: classes4.dex */
public interface SplashScreen {
    public static final int SPLASH_SCREEN_STYLE_ICON = 1;
    public static final int SPLASH_SCREEN_STYLE_SOLID_COLOR = 0;
    public static final int SPLASH_SCREEN_STYLE_UNDEFINED = -1;

    public interface OnExitAnimationListener {
        void onSplashScreenExit(android.window.SplashScreenView splashScreenView);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SplashScreenStyle {
    }

    void clearOnExitAnimationListener();

    void setOnExitAnimationListener(android.window.SplashScreen.OnExitAnimationListener onExitAnimationListener);

    void setSplashScreenTheme(int i);

    public static class SplashScreenImpl implements android.window.SplashScreen {
        private static final java.lang.String TAG = "SplashScreenImpl";
        private final android.os.IBinder mActivityToken;
        private android.window.SplashScreen.OnExitAnimationListener mExitAnimationListener;
        private final android.window.SplashScreen.SplashScreenManagerGlobal mGlobal = android.window.SplashScreen.SplashScreenManagerGlobal.getInstance();

        public SplashScreenImpl(android.content.Context context) {
            this.mActivityToken = context.getActivityToken();
        }

        @Override // android.window.SplashScreen
        public void setOnExitAnimationListener(android.window.SplashScreen.OnExitAnimationListener onExitAnimationListener) {
            if (this.mActivityToken == null) {
                return;
            }
            synchronized (this.mGlobal.mGlobalLock) {
                if (onExitAnimationListener != null) {
                    this.mExitAnimationListener = onExitAnimationListener;
                    this.mGlobal.addImpl(this);
                }
            }
        }

        @Override // android.window.SplashScreen
        public void clearOnExitAnimationListener() {
            if (this.mActivityToken == null) {
                return;
            }
            synchronized (this.mGlobal.mGlobalLock) {
                this.mExitAnimationListener = null;
                this.mGlobal.removeImpl(this);
            }
        }

        @Override // android.window.SplashScreen
        public void setSplashScreenTheme(int i) {
            if (this.mActivityToken == null) {
                android.util.Log.w(TAG, "Couldn't persist the starting theme. This instance is not an Activity");
                return;
            }
            android.app.Activity activity = android.app.ActivityThread.currentActivityThread().getActivity(this.mActivityToken);
            if (activity == null) {
                return;
            }
            try {
                android.app.AppGlobals.getPackageManager().setSplashScreenTheme(activity.getComponentName().getPackageName(), i != 0 ? activity.getResources().getResourceName(i) : null, activity.getUserId());
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Couldn't persist the starting theme", e);
            }
        }
    }

    public static class SplashScreenManagerGlobal {
        private static final java.lang.String TAG = android.window.SplashScreen.class.getSimpleName();
        private static final android.util.Singleton<android.window.SplashScreen.SplashScreenManagerGlobal> sInstance = new android.util.Singleton<android.window.SplashScreen.SplashScreenManagerGlobal>() { // from class: android.window.SplashScreen.SplashScreenManagerGlobal.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.util.Singleton
            public android.window.SplashScreen.SplashScreenManagerGlobal create() {
                return new android.window.SplashScreen.SplashScreenManagerGlobal();
            }
        };
        private final java.lang.Object mGlobalLock;
        private final java.util.ArrayList<android.window.SplashScreen.SplashScreenImpl> mImpls;

        private SplashScreenManagerGlobal() {
            this.mGlobalLock = new java.lang.Object();
            this.mImpls = new java.util.ArrayList<>();
            android.app.ActivityThread.currentActivityThread().registerSplashScreenManager(this);
        }

        public static android.window.SplashScreen.SplashScreenManagerGlobal getInstance() {
            return sInstance.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addImpl(android.window.SplashScreen.SplashScreenImpl splashScreenImpl) {
            synchronized (this.mGlobalLock) {
                this.mImpls.add(splashScreenImpl);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeImpl(android.window.SplashScreen.SplashScreenImpl splashScreenImpl) {
            synchronized (this.mGlobalLock) {
                this.mImpls.remove(splashScreenImpl);
            }
        }

        private android.window.SplashScreen.SplashScreenImpl findImpl(android.os.IBinder iBinder) {
            synchronized (this.mGlobalLock) {
                java.util.Iterator<android.window.SplashScreen.SplashScreenImpl> it = this.mImpls.iterator();
                while (it.hasNext()) {
                    android.window.SplashScreen.SplashScreenImpl next = it.next();
                    if (next.mActivityToken == iBinder) {
                        return next;
                    }
                }
                return null;
            }
        }

        public void tokenDestroyed(android.os.IBinder iBinder) {
            synchronized (this.mGlobalLock) {
                android.window.SplashScreen.SplashScreenImpl findImpl = findImpl(iBinder);
                if (findImpl != null) {
                    removeImpl(findImpl);
                }
            }
        }

        public void handOverSplashScreenView(android.os.IBinder iBinder, android.window.SplashScreenView splashScreenView) {
            dispatchOnExitAnimation(iBinder, splashScreenView);
        }

        private void dispatchOnExitAnimation(android.os.IBinder iBinder, android.window.SplashScreenView splashScreenView) {
            synchronized (this.mGlobalLock) {
                android.window.SplashScreen.SplashScreenImpl findImpl = findImpl(iBinder);
                if (findImpl == null) {
                    return;
                }
                if (findImpl.mExitAnimationListener == null) {
                    android.util.Slog.e(TAG, "cannot dispatch onExitAnimation to listener " + iBinder);
                } else {
                    findImpl.mExitAnimationListener.onSplashScreenExit(splashScreenView);
                }
            }
        }

        public boolean containsExitListener(android.os.IBinder iBinder) {
            boolean z;
            synchronized (this.mGlobalLock) {
                android.window.SplashScreen.SplashScreenImpl findImpl = findImpl(iBinder);
                z = (findImpl == null || findImpl.mExitAnimationListener == null) ? false : true;
            }
            return z;
        }
    }
}
