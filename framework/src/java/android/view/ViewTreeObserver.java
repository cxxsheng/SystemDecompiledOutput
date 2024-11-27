package android.view;

/* loaded from: classes4.dex */
public final class ViewTreeObserver {
    private static boolean sIllegalOnDrawModificationIsFatal;
    private boolean mAlive = true;
    private android.view.ViewTreeObserver.CopyOnWriteArray<java.util.function.Consumer<java.util.List<android.graphics.Rect>>> mGestureExclusionListeners;
    private boolean mInDispatchOnDraw;
    private java.lang.StringBuilder mLastDispatchOnPreDrawCanceledReason;
    private android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnComputeInternalInsetsListener> mOnComputeInternalInsetsListeners;
    private java.util.ArrayList<android.view.ViewTreeObserver.OnDrawListener> mOnDrawListeners;
    private java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnEnterAnimationCompleteListener> mOnEnterAnimationCompleteListeners;
    private java.util.ArrayList<java.lang.Runnable> mOnFrameCommitListeners;
    private java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnGlobalFocusChangeListener> mOnGlobalFocusListeners;
    private android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnGlobalLayoutListener> mOnGlobalLayoutListeners;
    private android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnPreDrawListener> mOnPreDrawListeners;
    private android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnScrollChangedListener> mOnScrollChangedListeners;
    private java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnTouchModeChangeListener> mOnTouchModeChangeListeners;
    private java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnWindowAttachListener> mOnWindowAttachListeners;
    private java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnWindowFocusChangeListener> mOnWindowFocusListeners;
    private android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnWindowShownListener> mOnWindowShownListeners;
    private java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnWindowVisibilityChangeListener> mOnWindowVisibilityListeners;
    private boolean mWindowShown;

    public interface OnComputeInternalInsetsListener {
        void onComputeInternalInsets(android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo);
    }

    public interface OnDrawListener {
        void onDraw();
    }

    public interface OnEnterAnimationCompleteListener {
        void onEnterAnimationComplete();
    }

    public interface OnGlobalFocusChangeListener {
        void onGlobalFocusChanged(android.view.View view, android.view.View view2);
    }

    public interface OnGlobalLayoutListener {
        void onGlobalLayout();
    }

    public interface OnPreDrawListener {
        boolean onPreDraw();
    }

    public interface OnScrollChangedListener {
        void onScrollChanged();
    }

    public interface OnTouchModeChangeListener {
        void onTouchModeChanged(boolean z);
    }

    public interface OnWindowAttachListener {
        void onWindowAttached();

        void onWindowDetached();
    }

    public interface OnWindowFocusChangeListener {
        void onWindowFocusChanged(boolean z);
    }

    public interface OnWindowShownListener {
        void onWindowShown();
    }

    public interface OnWindowVisibilityChangeListener {
        void onWindowVisibilityChanged(int i);
    }

    public static final class InternalInsetsInfo {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public static final int TOUCHABLE_INSETS_VISIBLE = 2;
        int mTouchableInsets;
        public final android.graphics.Rect contentInsets = new android.graphics.Rect();
        public final android.graphics.Rect visibleInsets = new android.graphics.Rect();
        public final android.graphics.Region touchableRegion = new android.graphics.Region();

        public void setTouchableInsets(int i) {
            this.mTouchableInsets = i;
        }

        void reset() {
            this.contentInsets.setEmpty();
            this.visibleInsets.setEmpty();
            this.touchableRegion.setEmpty();
            this.mTouchableInsets = 0;
        }

        boolean isEmpty() {
            return this.contentInsets.isEmpty() && this.visibleInsets.isEmpty() && this.touchableRegion.isEmpty() && this.mTouchableInsets == 0;
        }

        public int hashCode() {
            return (((((this.contentInsets.hashCode() * 31) + this.visibleInsets.hashCode()) * 31) + this.touchableRegion.hashCode()) * 31) + this.mTouchableInsets;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo = (android.view.ViewTreeObserver.InternalInsetsInfo) obj;
            if (this.mTouchableInsets == internalInsetsInfo.mTouchableInsets && this.contentInsets.equals(internalInsetsInfo.contentInsets) && this.visibleInsets.equals(internalInsetsInfo.visibleInsets) && this.touchableRegion.equals(internalInsetsInfo.touchableRegion)) {
                return true;
            }
            return false;
        }

        void set(android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
            this.contentInsets.set(internalInsetsInfo.contentInsets);
            this.visibleInsets.set(internalInsetsInfo.visibleInsets);
            this.touchableRegion.set(internalInsetsInfo.touchableRegion);
            this.mTouchableInsets = internalInsetsInfo.mTouchableInsets;
        }
    }

    ViewTreeObserver(android.content.Context context) {
        sIllegalOnDrawModificationIsFatal = context.getApplicationInfo().targetSdkVersion >= 26;
    }

    void merge(android.view.ViewTreeObserver viewTreeObserver) {
        if (viewTreeObserver.mOnWindowAttachListeners != null) {
            if (this.mOnWindowAttachListeners != null) {
                this.mOnWindowAttachListeners.addAll(viewTreeObserver.mOnWindowAttachListeners);
            } else {
                this.mOnWindowAttachListeners = viewTreeObserver.mOnWindowAttachListeners;
            }
        }
        if (viewTreeObserver.mOnWindowFocusListeners != null) {
            if (this.mOnWindowFocusListeners != null) {
                this.mOnWindowFocusListeners.addAll(viewTreeObserver.mOnWindowFocusListeners);
            } else {
                this.mOnWindowFocusListeners = viewTreeObserver.mOnWindowFocusListeners;
            }
        }
        if (viewTreeObserver.mOnWindowVisibilityListeners != null) {
            if (this.mOnWindowVisibilityListeners != null) {
                this.mOnWindowVisibilityListeners.addAll(viewTreeObserver.mOnWindowVisibilityListeners);
            } else {
                this.mOnWindowVisibilityListeners = viewTreeObserver.mOnWindowVisibilityListeners;
            }
        }
        if (viewTreeObserver.mOnGlobalFocusListeners != null) {
            if (this.mOnGlobalFocusListeners != null) {
                this.mOnGlobalFocusListeners.addAll(viewTreeObserver.mOnGlobalFocusListeners);
            } else {
                this.mOnGlobalFocusListeners = viewTreeObserver.mOnGlobalFocusListeners;
            }
        }
        if (viewTreeObserver.mOnGlobalLayoutListeners != null) {
            if (this.mOnGlobalLayoutListeners != null) {
                this.mOnGlobalLayoutListeners.addAll(viewTreeObserver.mOnGlobalLayoutListeners);
            } else {
                this.mOnGlobalLayoutListeners = viewTreeObserver.mOnGlobalLayoutListeners;
            }
        }
        if (viewTreeObserver.mOnPreDrawListeners != null) {
            if (this.mOnPreDrawListeners != null) {
                this.mOnPreDrawListeners.addAll(viewTreeObserver.mOnPreDrawListeners);
            } else {
                this.mOnPreDrawListeners = viewTreeObserver.mOnPreDrawListeners;
            }
        }
        if (viewTreeObserver.mOnDrawListeners != null) {
            if (this.mOnDrawListeners != null) {
                this.mOnDrawListeners.addAll(viewTreeObserver.mOnDrawListeners);
            } else {
                this.mOnDrawListeners = viewTreeObserver.mOnDrawListeners;
            }
        }
        if (viewTreeObserver.mOnFrameCommitListeners != null) {
            if (this.mOnFrameCommitListeners != null) {
                this.mOnFrameCommitListeners.addAll(viewTreeObserver.captureFrameCommitCallbacks());
            } else {
                this.mOnFrameCommitListeners = viewTreeObserver.captureFrameCommitCallbacks();
            }
        }
        if (viewTreeObserver.mOnTouchModeChangeListeners != null) {
            if (this.mOnTouchModeChangeListeners != null) {
                this.mOnTouchModeChangeListeners.addAll(viewTreeObserver.mOnTouchModeChangeListeners);
            } else {
                this.mOnTouchModeChangeListeners = viewTreeObserver.mOnTouchModeChangeListeners;
            }
        }
        if (viewTreeObserver.mOnComputeInternalInsetsListeners != null) {
            if (this.mOnComputeInternalInsetsListeners != null) {
                this.mOnComputeInternalInsetsListeners.addAll(viewTreeObserver.mOnComputeInternalInsetsListeners);
            } else {
                this.mOnComputeInternalInsetsListeners = viewTreeObserver.mOnComputeInternalInsetsListeners;
            }
        }
        if (viewTreeObserver.mOnScrollChangedListeners != null) {
            if (this.mOnScrollChangedListeners != null) {
                this.mOnScrollChangedListeners.addAll(viewTreeObserver.mOnScrollChangedListeners);
            } else {
                this.mOnScrollChangedListeners = viewTreeObserver.mOnScrollChangedListeners;
            }
        }
        if (viewTreeObserver.mOnWindowShownListeners != null) {
            if (this.mOnWindowShownListeners != null) {
                this.mOnWindowShownListeners.addAll(viewTreeObserver.mOnWindowShownListeners);
            } else {
                this.mOnWindowShownListeners = viewTreeObserver.mOnWindowShownListeners;
            }
        }
        if (viewTreeObserver.mGestureExclusionListeners != null) {
            if (this.mGestureExclusionListeners != null) {
                this.mGestureExclusionListeners.addAll(viewTreeObserver.mGestureExclusionListeners);
            } else {
                this.mGestureExclusionListeners = viewTreeObserver.mGestureExclusionListeners;
            }
        }
        viewTreeObserver.kill();
    }

    public void addOnWindowAttachListener(android.view.ViewTreeObserver.OnWindowAttachListener onWindowAttachListener) {
        checkIsAlive();
        if (this.mOnWindowAttachListeners == null) {
            this.mOnWindowAttachListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        }
        this.mOnWindowAttachListeners.add(onWindowAttachListener);
    }

    public void removeOnWindowAttachListener(android.view.ViewTreeObserver.OnWindowAttachListener onWindowAttachListener) {
        checkIsAlive();
        if (this.mOnWindowAttachListeners == null) {
            return;
        }
        this.mOnWindowAttachListeners.remove(onWindowAttachListener);
    }

    public void addOnWindowFocusChangeListener(android.view.ViewTreeObserver.OnWindowFocusChangeListener onWindowFocusChangeListener) {
        checkIsAlive();
        if (this.mOnWindowFocusListeners == null) {
            this.mOnWindowFocusListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        }
        this.mOnWindowFocusListeners.add(onWindowFocusChangeListener);
    }

    public void removeOnWindowFocusChangeListener(android.view.ViewTreeObserver.OnWindowFocusChangeListener onWindowFocusChangeListener) {
        checkIsAlive();
        if (this.mOnWindowFocusListeners == null) {
            return;
        }
        this.mOnWindowFocusListeners.remove(onWindowFocusChangeListener);
    }

    public void addOnWindowVisibilityChangeListener(android.view.ViewTreeObserver.OnWindowVisibilityChangeListener onWindowVisibilityChangeListener) {
        checkIsAlive();
        if (this.mOnWindowVisibilityListeners == null) {
            this.mOnWindowVisibilityListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        }
        this.mOnWindowVisibilityListeners.add(onWindowVisibilityChangeListener);
    }

    public void removeOnWindowVisibilityChangeListener(android.view.ViewTreeObserver.OnWindowVisibilityChangeListener onWindowVisibilityChangeListener) {
        checkIsAlive();
        if (this.mOnWindowVisibilityListeners == null) {
            return;
        }
        this.mOnWindowVisibilityListeners.remove(onWindowVisibilityChangeListener);
    }

    public void addOnGlobalFocusChangeListener(android.view.ViewTreeObserver.OnGlobalFocusChangeListener onGlobalFocusChangeListener) {
        checkIsAlive();
        if (this.mOnGlobalFocusListeners == null) {
            this.mOnGlobalFocusListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        }
        this.mOnGlobalFocusListeners.add(onGlobalFocusChangeListener);
    }

    public void removeOnGlobalFocusChangeListener(android.view.ViewTreeObserver.OnGlobalFocusChangeListener onGlobalFocusChangeListener) {
        checkIsAlive();
        if (this.mOnGlobalFocusListeners == null) {
            return;
        }
        this.mOnGlobalFocusListeners.remove(onGlobalFocusChangeListener);
    }

    public void addOnGlobalLayoutListener(android.view.ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        checkIsAlive();
        if (this.mOnGlobalLayoutListeners == null) {
            this.mOnGlobalLayoutListeners = new android.view.ViewTreeObserver.CopyOnWriteArray<>();
        }
        this.mOnGlobalLayoutListeners.add(onGlobalLayoutListener);
    }

    @java.lang.Deprecated
    public void removeGlobalOnLayoutListener(android.view.ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    public void removeOnGlobalLayoutListener(android.view.ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        checkIsAlive();
        if (this.mOnGlobalLayoutListeners == null) {
            return;
        }
        this.mOnGlobalLayoutListeners.remove(onGlobalLayoutListener);
    }

    public void addOnPreDrawListener(android.view.ViewTreeObserver.OnPreDrawListener onPreDrawListener) {
        checkIsAlive();
        if (this.mOnPreDrawListeners == null) {
            this.mOnPreDrawListeners = new android.view.ViewTreeObserver.CopyOnWriteArray<>();
        }
        this.mOnPreDrawListeners.add(onPreDrawListener);
    }

    public void removeOnPreDrawListener(android.view.ViewTreeObserver.OnPreDrawListener onPreDrawListener) {
        checkIsAlive();
        if (this.mOnPreDrawListeners == null) {
            return;
        }
        this.mOnPreDrawListeners.remove(onPreDrawListener);
    }

    public void addOnWindowShownListener(android.view.ViewTreeObserver.OnWindowShownListener onWindowShownListener) {
        checkIsAlive();
        if (this.mOnWindowShownListeners == null) {
            this.mOnWindowShownListeners = new android.view.ViewTreeObserver.CopyOnWriteArray<>();
        }
        this.mOnWindowShownListeners.add(onWindowShownListener);
        if (this.mWindowShown) {
            onWindowShownListener.onWindowShown();
        }
    }

    public void removeOnWindowShownListener(android.view.ViewTreeObserver.OnWindowShownListener onWindowShownListener) {
        checkIsAlive();
        if (this.mOnWindowShownListeners == null) {
            return;
        }
        this.mOnWindowShownListeners.remove(onWindowShownListener);
    }

    public void addOnDrawListener(android.view.ViewTreeObserver.OnDrawListener onDrawListener) {
        checkIsAlive();
        if (this.mOnDrawListeners == null) {
            this.mOnDrawListeners = new java.util.ArrayList<>();
        }
        if (this.mInDispatchOnDraw) {
            java.lang.IllegalStateException illegalStateException = new java.lang.IllegalStateException("Cannot call addOnDrawListener inside of onDraw");
            if (sIllegalOnDrawModificationIsFatal) {
                throw illegalStateException;
            }
            android.util.Log.e("ViewTreeObserver", illegalStateException.getMessage(), illegalStateException);
        }
        this.mOnDrawListeners.add(onDrawListener);
    }

    public void removeOnDrawListener(android.view.ViewTreeObserver.OnDrawListener onDrawListener) {
        checkIsAlive();
        if (this.mOnDrawListeners == null) {
            return;
        }
        if (this.mInDispatchOnDraw) {
            java.lang.IllegalStateException illegalStateException = new java.lang.IllegalStateException("Cannot call removeOnDrawListener inside of onDraw");
            if (sIllegalOnDrawModificationIsFatal) {
                throw illegalStateException;
            }
            android.util.Log.e("ViewTreeObserver", illegalStateException.getMessage(), illegalStateException);
        }
        this.mOnDrawListeners.remove(onDrawListener);
    }

    public void registerFrameCommitCallback(java.lang.Runnable runnable) {
        checkIsAlive();
        if (this.mOnFrameCommitListeners == null) {
            this.mOnFrameCommitListeners = new java.util.ArrayList<>();
        }
        this.mOnFrameCommitListeners.add(runnable);
    }

    java.util.ArrayList<java.lang.Runnable> captureFrameCommitCallbacks() {
        java.util.ArrayList<java.lang.Runnable> arrayList = this.mOnFrameCommitListeners;
        this.mOnFrameCommitListeners = null;
        return arrayList;
    }

    public boolean unregisterFrameCommitCallback(java.lang.Runnable runnable) {
        checkIsAlive();
        if (this.mOnFrameCommitListeners == null) {
            return false;
        }
        return this.mOnFrameCommitListeners.remove(runnable);
    }

    public void addOnScrollChangedListener(android.view.ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
        checkIsAlive();
        if (this.mOnScrollChangedListeners == null) {
            this.mOnScrollChangedListeners = new android.view.ViewTreeObserver.CopyOnWriteArray<>();
        }
        this.mOnScrollChangedListeners.add(onScrollChangedListener);
    }

    public void removeOnScrollChangedListener(android.view.ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
        checkIsAlive();
        if (this.mOnScrollChangedListeners == null) {
            return;
        }
        this.mOnScrollChangedListeners.remove(onScrollChangedListener);
    }

    public void addOnTouchModeChangeListener(android.view.ViewTreeObserver.OnTouchModeChangeListener onTouchModeChangeListener) {
        checkIsAlive();
        if (this.mOnTouchModeChangeListeners == null) {
            this.mOnTouchModeChangeListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        }
        this.mOnTouchModeChangeListeners.add(onTouchModeChangeListener);
    }

    public void removeOnTouchModeChangeListener(android.view.ViewTreeObserver.OnTouchModeChangeListener onTouchModeChangeListener) {
        checkIsAlive();
        if (this.mOnTouchModeChangeListeners == null) {
            return;
        }
        this.mOnTouchModeChangeListeners.remove(onTouchModeChangeListener);
    }

    public void addOnComputeInternalInsetsListener(android.view.ViewTreeObserver.OnComputeInternalInsetsListener onComputeInternalInsetsListener) {
        checkIsAlive();
        if (this.mOnComputeInternalInsetsListeners == null) {
            this.mOnComputeInternalInsetsListeners = new android.view.ViewTreeObserver.CopyOnWriteArray<>();
        }
        this.mOnComputeInternalInsetsListeners.add(onComputeInternalInsetsListener);
    }

    public void removeOnComputeInternalInsetsListener(android.view.ViewTreeObserver.OnComputeInternalInsetsListener onComputeInternalInsetsListener) {
        checkIsAlive();
        if (this.mOnComputeInternalInsetsListeners == null) {
            return;
        }
        this.mOnComputeInternalInsetsListeners.remove(onComputeInternalInsetsListener);
    }

    public void addOnEnterAnimationCompleteListener(android.view.ViewTreeObserver.OnEnterAnimationCompleteListener onEnterAnimationCompleteListener) {
        checkIsAlive();
        if (this.mOnEnterAnimationCompleteListeners == null) {
            this.mOnEnterAnimationCompleteListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        }
        this.mOnEnterAnimationCompleteListeners.add(onEnterAnimationCompleteListener);
    }

    public void removeOnEnterAnimationCompleteListener(android.view.ViewTreeObserver.OnEnterAnimationCompleteListener onEnterAnimationCompleteListener) {
        checkIsAlive();
        if (this.mOnEnterAnimationCompleteListeners == null) {
            return;
        }
        this.mOnEnterAnimationCompleteListeners.remove(onEnterAnimationCompleteListener);
    }

    public void addOnSystemGestureExclusionRectsChangedListener(java.util.function.Consumer<java.util.List<android.graphics.Rect>> consumer) {
        checkIsAlive();
        if (this.mGestureExclusionListeners == null) {
            this.mGestureExclusionListeners = new android.view.ViewTreeObserver.CopyOnWriteArray<>();
        }
        this.mGestureExclusionListeners.add(consumer);
    }

    public void removeOnSystemGestureExclusionRectsChangedListener(java.util.function.Consumer<java.util.List<android.graphics.Rect>> consumer) {
        checkIsAlive();
        if (this.mGestureExclusionListeners == null) {
            return;
        }
        this.mGestureExclusionListeners.remove(consumer);
    }

    private void checkIsAlive() {
        if (!this.mAlive) {
            throw new java.lang.IllegalStateException("This ViewTreeObserver is not alive, call getViewTreeObserver() again");
        }
    }

    public boolean isAlive() {
        return this.mAlive;
    }

    private void kill() {
        this.mAlive = false;
    }

    final void dispatchOnWindowAttachedChange(boolean z) {
        java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnWindowAttachListener> copyOnWriteArrayList = this.mOnWindowAttachListeners;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            java.util.Iterator<android.view.ViewTreeObserver.OnWindowAttachListener> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                android.view.ViewTreeObserver.OnWindowAttachListener next = it.next();
                if (z) {
                    next.onWindowAttached();
                } else {
                    next.onWindowDetached();
                }
            }
        }
    }

    final void dispatchOnWindowFocusChange(boolean z) {
        java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnWindowFocusChangeListener> copyOnWriteArrayList = this.mOnWindowFocusListeners;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            java.util.Iterator<android.view.ViewTreeObserver.OnWindowFocusChangeListener> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                it.next().onWindowFocusChanged(z);
            }
        }
    }

    void dispatchOnWindowVisibilityChange(int i) {
        java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnWindowVisibilityChangeListener> copyOnWriteArrayList = this.mOnWindowVisibilityListeners;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            java.util.Iterator<android.view.ViewTreeObserver.OnWindowVisibilityChangeListener> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                it.next().onWindowVisibilityChanged(i);
            }
        }
    }

    final void dispatchOnGlobalFocusChange(android.view.View view, android.view.View view2) {
        java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnGlobalFocusChangeListener> copyOnWriteArrayList = this.mOnGlobalFocusListeners;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            java.util.Iterator<android.view.ViewTreeObserver.OnGlobalFocusChangeListener> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                it.next().onGlobalFocusChanged(view, view2);
            }
        }
    }

    public final void dispatchOnGlobalLayout() {
        android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnGlobalLayoutListener> copyOnWriteArray = this.mOnGlobalLayoutListeners;
        if (copyOnWriteArray != null && copyOnWriteArray.size() > 0) {
            android.view.ViewTreeObserver.CopyOnWriteArray.Access<android.view.ViewTreeObserver.OnGlobalLayoutListener> start = copyOnWriteArray.start();
            try {
                int size = start.size();
                for (int i = 0; i < size; i++) {
                    start.get(i).onGlobalLayout();
                }
            } finally {
                copyOnWriteArray.end();
            }
        }
    }

    final boolean hasOnPreDrawListeners() {
        return this.mOnPreDrawListeners != null && this.mOnPreDrawListeners.size() > 0;
    }

    public final boolean dispatchOnPreDraw() {
        this.mLastDispatchOnPreDrawCanceledReason = null;
        android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnPreDrawListener> copyOnWriteArray = this.mOnPreDrawListeners;
        if (copyOnWriteArray == null || copyOnWriteArray.size() <= 0) {
            return false;
        }
        android.view.ViewTreeObserver.CopyOnWriteArray.Access<android.view.ViewTreeObserver.OnPreDrawListener> start = copyOnWriteArray.start();
        try {
            int size = start.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                android.view.ViewTreeObserver.OnPreDrawListener onPreDrawListener = start.get(i);
                boolean z2 = !onPreDrawListener.onPreDraw();
                z |= z2;
                if (z2) {
                    java.lang.String name = onPreDrawListener.getClass().getName();
                    if (this.mLastDispatchOnPreDrawCanceledReason == null) {
                        this.mLastDispatchOnPreDrawCanceledReason = new java.lang.StringBuilder(name);
                    } else {
                        this.mLastDispatchOnPreDrawCanceledReason.append(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append(name);
                    }
                }
            }
            copyOnWriteArray.end();
            return z;
        } catch (java.lang.Throwable th) {
            copyOnWriteArray.end();
            throw th;
        }
    }

    final java.lang.String getLastDispatchOnPreDrawCanceledReason() {
        if (this.mLastDispatchOnPreDrawCanceledReason != null) {
            return this.mLastDispatchOnPreDrawCanceledReason.toString();
        }
        return null;
    }

    public final void dispatchOnWindowShown() {
        this.mWindowShown = true;
        android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnWindowShownListener> copyOnWriteArray = this.mOnWindowShownListeners;
        if (copyOnWriteArray != null && copyOnWriteArray.size() > 0) {
            android.view.ViewTreeObserver.CopyOnWriteArray.Access<android.view.ViewTreeObserver.OnWindowShownListener> start = copyOnWriteArray.start();
            try {
                int size = start.size();
                for (int i = 0; i < size; i++) {
                    start.get(i).onWindowShown();
                }
            } finally {
                copyOnWriteArray.end();
            }
        }
    }

    public final void dispatchOnDraw() {
        if (this.mOnDrawListeners != null) {
            this.mInDispatchOnDraw = true;
            java.util.ArrayList<android.view.ViewTreeObserver.OnDrawListener> arrayList = this.mOnDrawListeners;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                arrayList.get(i).onDraw();
            }
            this.mInDispatchOnDraw = false;
        }
    }

    final void dispatchOnTouchModeChanged(boolean z) {
        java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnTouchModeChangeListener> copyOnWriteArrayList = this.mOnTouchModeChangeListeners;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            java.util.Iterator<android.view.ViewTreeObserver.OnTouchModeChangeListener> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                it.next().onTouchModeChanged(z);
            }
        }
    }

    final void dispatchOnScrollChanged() {
        android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnScrollChangedListener> copyOnWriteArray = this.mOnScrollChangedListeners;
        if (copyOnWriteArray != null && copyOnWriteArray.size() > 0) {
            android.view.ViewTreeObserver.CopyOnWriteArray.Access<android.view.ViewTreeObserver.OnScrollChangedListener> start = copyOnWriteArray.start();
            try {
                int size = start.size();
                for (int i = 0; i < size; i++) {
                    start.get(i).onScrollChanged();
                }
            } finally {
                copyOnWriteArray.end();
            }
        }
    }

    final boolean hasComputeInternalInsetsListeners() {
        android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnComputeInternalInsetsListener> copyOnWriteArray = this.mOnComputeInternalInsetsListeners;
        return copyOnWriteArray != null && copyOnWriteArray.size() > 0;
    }

    final void dispatchOnComputeInternalInsets(android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        android.view.ViewTreeObserver.CopyOnWriteArray<android.view.ViewTreeObserver.OnComputeInternalInsetsListener> copyOnWriteArray = this.mOnComputeInternalInsetsListeners;
        if (copyOnWriteArray != null && copyOnWriteArray.size() > 0) {
            android.view.ViewTreeObserver.CopyOnWriteArray.Access<android.view.ViewTreeObserver.OnComputeInternalInsetsListener> start = copyOnWriteArray.start();
            try {
                int size = start.size();
                for (int i = 0; i < size; i++) {
                    start.get(i).onComputeInternalInsets(internalInsetsInfo);
                }
            } finally {
                copyOnWriteArray.end();
            }
        }
    }

    public final void dispatchOnEnterAnimationComplete() {
        java.util.concurrent.CopyOnWriteArrayList<android.view.ViewTreeObserver.OnEnterAnimationCompleteListener> copyOnWriteArrayList = this.mOnEnterAnimationCompleteListeners;
        if (copyOnWriteArrayList != null && !copyOnWriteArrayList.isEmpty()) {
            java.util.Iterator<android.view.ViewTreeObserver.OnEnterAnimationCompleteListener> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                it.next().onEnterAnimationComplete();
            }
        }
    }

    void dispatchOnSystemGestureExclusionRectsChanged(java.util.List<android.graphics.Rect> list) {
        android.view.ViewTreeObserver.CopyOnWriteArray<java.util.function.Consumer<java.util.List<android.graphics.Rect>>> copyOnWriteArray = this.mGestureExclusionListeners;
        if (copyOnWriteArray != null && copyOnWriteArray.size() > 0) {
            android.view.ViewTreeObserver.CopyOnWriteArray.Access<java.util.function.Consumer<java.util.List<android.graphics.Rect>>> start = copyOnWriteArray.start();
            try {
                int size = start.size();
                for (int i = 0; i < size; i++) {
                    start.get(i).accept(list);
                }
            } finally {
                copyOnWriteArray.end();
            }
        }
    }

    static class CopyOnWriteArray<T> {
        private java.util.ArrayList<T> mDataCopy;
        private boolean mStart;
        private java.util.ArrayList<T> mData = new java.util.ArrayList<>();
        private final android.view.ViewTreeObserver.CopyOnWriteArray.Access<T> mAccess = new android.view.ViewTreeObserver.CopyOnWriteArray.Access<>();

        static class Access<T> {
            private java.util.ArrayList<T> mData;
            private int mSize;

            Access() {
            }

            T get(int i) {
                return this.mData.get(i);
            }

            int size() {
                return this.mSize;
            }
        }

        CopyOnWriteArray() {
        }

        private java.util.ArrayList<T> getArray() {
            if (this.mStart) {
                if (this.mDataCopy == null) {
                    this.mDataCopy = new java.util.ArrayList<>(this.mData);
                }
                return this.mDataCopy;
            }
            return this.mData;
        }

        android.view.ViewTreeObserver.CopyOnWriteArray.Access<T> start() {
            if (this.mStart) {
                throw new java.lang.IllegalStateException("Iteration already started");
            }
            this.mStart = true;
            this.mDataCopy = null;
            ((android.view.ViewTreeObserver.CopyOnWriteArray.Access) this.mAccess).mData = this.mData;
            ((android.view.ViewTreeObserver.CopyOnWriteArray.Access) this.mAccess).mSize = this.mData.size();
            return this.mAccess;
        }

        void end() {
            if (!this.mStart) {
                throw new java.lang.IllegalStateException("Iteration not started");
            }
            this.mStart = false;
            if (this.mDataCopy != null) {
                this.mData = this.mDataCopy;
                ((android.view.ViewTreeObserver.CopyOnWriteArray.Access) this.mAccess).mData.clear();
                ((android.view.ViewTreeObserver.CopyOnWriteArray.Access) this.mAccess).mSize = 0;
            }
            this.mDataCopy = null;
        }

        int size() {
            return getArray().size();
        }

        void add(T t) {
            getArray().add(t);
        }

        void addAll(android.view.ViewTreeObserver.CopyOnWriteArray<T> copyOnWriteArray) {
            getArray().addAll(copyOnWriteArray.mData);
        }

        void remove(T t) {
            getArray().remove(t);
        }

        void clear() {
            getArray().clear();
        }
    }
}
