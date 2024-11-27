package android.service.autofill.augmented;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class FillWindow implements java.lang.AutoCloseable {
    private static final java.lang.String TAG = android.service.autofill.augmented.FillWindow.class.getSimpleName();
    private android.graphics.Rect mBounds;
    private boolean mDestroyed;
    private android.view.View mFillView;
    private android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy mProxy;
    private boolean mShowing;
    private boolean mUpdateCalled;
    private android.view.WindowManager mWm;
    private final java.lang.Object mLock = new java.lang.Object();
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final android.os.Handler mUiThreadHandler = new android.os.Handler(android.os.Looper.getMainLooper());

    public boolean update(android.service.autofill.augmented.PresentationParams.Area area, android.view.View view, long j) {
        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
            android.util.Log.d(TAG, "Updating " + area + " + with " + view);
        }
        java.util.Objects.requireNonNull(area);
        java.util.Objects.requireNonNull(area.proxy);
        java.util.Objects.requireNonNull(view);
        android.service.autofill.augmented.PresentationParams.SystemPopupPresentationParams smartSuggestionParams = area.proxy.getSmartSuggestionParams();
        if (smartSuggestionParams == null) {
            android.util.Log.w(TAG, "No SmartSuggestionParams");
            return false;
        }
        if (area.getBounds() == null) {
            android.util.Log.wtf(TAG, "No Rect on SmartSuggestionParams");
            return false;
        }
        synchronized (this.mLock) {
            checkNotDestroyedLocked();
            this.mProxy = area.proxy;
            this.mWm = (android.view.WindowManager) view.getContext().getSystemService(android.view.WindowManager.class);
            this.mFillView = view;
            this.mFillView.setOnTouchListener(new android.view.View.OnTouchListener() { // from class: android.service.autofill.augmented.FillWindow$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(android.view.View view2, android.view.MotionEvent motionEvent) {
                    boolean lambda$update$0;
                    lambda$update$0 = android.service.autofill.augmented.FillWindow.this.lambda$update$0(view2, motionEvent);
                    return lambda$update$0;
                }
            });
            this.mShowing = false;
            this.mBounds = new android.graphics.Rect(area.getBounds());
            if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                android.util.Log.d(TAG, "Created FillWindow: params= " + smartSuggestionParams + " view=" + view);
            }
            this.mUpdateCalled = true;
            this.mDestroyed = false;
            this.mProxy.setFillWindow(this);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$update$0(android.view.View view, android.view.MotionEvent motionEvent) {
        if (motionEvent.getAction() == 4) {
            if (android.service.autofill.augmented.AugmentedAutofillService.sVerbose) {
                android.util.Log.v(TAG, "Outside touch detected, hiding the window");
            }
            hide();
            return false;
        }
        return false;
    }

    void show() {
        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
            android.util.Log.d(TAG, "show()");
        }
        synchronized (this.mLock) {
            checkNotDestroyedLocked();
            if (this.mWm == null || this.mFillView == null) {
                throw new java.lang.IllegalStateException("update() not called yet, or already destroyed()");
            }
            if (this.mProxy != null) {
                try {
                    this.mProxy.requestShowFillUi(this.mBounds.right - this.mBounds.left, this.mBounds.bottom - this.mBounds.top, null, new android.service.autofill.augmented.FillWindow.FillWindowPresenter(this));
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(TAG, "Error requesting to show fill window", e);
                }
                this.mProxy.logEvent(2);
            }
        }
    }

    private void hide() {
        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
            android.util.Log.d(TAG, "hide()");
        }
        synchronized (this.mLock) {
            checkNotDestroyedLocked();
            if (this.mWm == null || this.mFillView == null) {
                throw new java.lang.IllegalStateException("update() not called yet, or already destroyed()");
            }
            if (this.mProxy != null && this.mShowing) {
                try {
                    this.mProxy.requestHideFillUi();
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(TAG, "Error requesting to hide fill window", e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShow(android.view.WindowManager.LayoutParams layoutParams) {
        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
            android.util.Log.d(TAG, "handleShow()");
        }
        synchronized (this.mLock) {
            if (this.mWm != null && this.mFillView != null) {
                try {
                    layoutParams.flags |= 262144;
                    if (!this.mShowing) {
                        this.mWm.addView(this.mFillView, layoutParams);
                        this.mShowing = true;
                    } else {
                        this.mWm.updateViewLayout(this.mFillView, layoutParams);
                    }
                } catch (android.view.WindowManager.BadTokenException e) {
                    if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                        android.util.Log.d(TAG, "Filed with token " + layoutParams.token + " gone.");
                    }
                } catch (java.lang.IllegalStateException e2) {
                    if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                        android.util.Log.d(TAG, "Exception showing window.");
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHide() {
        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
            android.util.Log.d(TAG, "handleHide()");
        }
        synchronized (this.mLock) {
            if (this.mWm != null && this.mFillView != null && this.mShowing) {
                try {
                    this.mWm.removeView(this.mFillView);
                    this.mShowing = false;
                } catch (java.lang.IllegalStateException e) {
                    if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                        android.util.Log.d(TAG, "Exception hiding window.");
                    }
                }
            }
        }
    }

    public void destroy() {
        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
            android.util.Log.d(TAG, "destroy(): mDestroyed=" + this.mDestroyed + " mShowing=" + this.mShowing + " mFillView=" + this.mFillView);
        }
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            if (this.mUpdateCalled) {
                this.mFillView.setOnClickListener(null);
                hide();
                this.mProxy.logEvent(3);
            }
            this.mDestroyed = true;
            this.mCloseGuard.close();
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            this.mCloseGuard.warnIfOpen();
            destroy();
        } finally {
            super.finalize();
        }
    }

    private void checkNotDestroyedLocked() {
        if (this.mDestroyed) {
            throw new java.lang.IllegalStateException("already destroyed()");
        }
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        synchronized (this) {
            printWriter.print(str);
            printWriter.print("destroyed: ");
            printWriter.println(this.mDestroyed);
            printWriter.print(str);
            printWriter.print("updateCalled: ");
            printWriter.println(this.mUpdateCalled);
            if (this.mFillView != null) {
                printWriter.print(str);
                printWriter.print("fill window: ");
                printWriter.println(this.mShowing ? "shown" : "hidden");
                printWriter.print(str);
                printWriter.print("fill view: ");
                printWriter.println(this.mFillView);
                printWriter.print(str);
                printWriter.print("mBounds: ");
                printWriter.println(this.mBounds);
                printWriter.print(str);
                printWriter.print("mWm: ");
                printWriter.println(this.mWm);
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        destroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FillWindowPresenter extends android.view.autofill.IAutofillWindowPresenter.Stub {
        private final java.lang.ref.WeakReference<android.service.autofill.augmented.FillWindow> mFillWindowReference;

        FillWindowPresenter(android.service.autofill.augmented.FillWindow fillWindow) {
            this.mFillWindowReference = new java.lang.ref.WeakReference<>(fillWindow);
        }

        @Override // android.view.autofill.IAutofillWindowPresenter
        public void show(android.view.WindowManager.LayoutParams layoutParams, android.graphics.Rect rect, boolean z, int i) {
            if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                android.util.Log.d(android.service.autofill.augmented.FillWindow.TAG, "FillWindowPresenter.show()");
            }
            android.service.autofill.augmented.FillWindow fillWindow = this.mFillWindowReference.get();
            if (fillWindow != null) {
                fillWindow.mUiThreadHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.autofill.augmented.FillWindow$FillWindowPresenter$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((android.service.autofill.augmented.FillWindow) obj).handleShow((android.view.WindowManager.LayoutParams) obj2);
                    }
                }, fillWindow, layoutParams));
            }
        }

        @Override // android.view.autofill.IAutofillWindowPresenter
        public void hide(android.graphics.Rect rect) {
            if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                android.util.Log.d(android.service.autofill.augmented.FillWindow.TAG, "FillWindowPresenter.hide()");
            }
            android.service.autofill.augmented.FillWindow fillWindow = this.mFillWindowReference.get();
            if (fillWindow != null) {
                fillWindow.mUiThreadHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.autofill.augmented.FillWindow$FillWindowPresenter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((android.service.autofill.augmented.FillWindow) obj).handleHide();
                    }
                }, fillWindow));
            }
        }
    }
}
