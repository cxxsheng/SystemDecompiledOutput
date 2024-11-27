package android.view;

/* loaded from: classes4.dex */
public class HandwritingInitiator {
    private final int mHandwritingSlop;
    private final android.view.inputmethod.InputMethodManager mImm;
    private android.view.HandwritingInitiator.State mState;
    private final android.view.HandwritingInitiator.HandwritingAreaTracker mHandwritingAreasTracker = new android.view.HandwritingInitiator.HandwritingAreaTracker();
    public java.lang.ref.WeakReference<android.view.View> mConnectedView = null;
    private int mConnectionCount = 0;
    public java.lang.ref.WeakReference<android.view.View> mFocusedView = null;
    private final int[] mTempLocation = new int[2];
    private final android.graphics.Rect mTempRect = new android.graphics.Rect();
    private final android.graphics.RectF mTempRectF = new android.graphics.RectF();
    private final android.graphics.Region mTempRegion = new android.graphics.Region();
    private final android.graphics.Matrix mTempMatrix = new android.graphics.Matrix();
    private java.lang.ref.WeakReference<android.view.View> mCachedHoverTarget = null;
    private boolean mShowHoverIconForConnectedView = true;
    private final boolean mInitiateWithoutConnection = android.view.inputmethod.Flags.initiationWithoutInputConnection();
    private final long mHandwritingTimeoutInMillis = android.view.ViewConfiguration.getLongPressTimeout();

    public HandwritingInitiator(android.view.ViewConfiguration viewConfiguration, android.view.inputmethod.InputMethodManager inputMethodManager) {
        this.mHandwritingSlop = viewConfiguration.getScaledHandwritingSlop();
        this.mImm = inputMethodManager;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ed  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case 0:
            case 5:
                this.mState = null;
                if (motionEvent.isStylusPointer()) {
                    this.mState = new android.view.HandwritingInitiator.State(motionEvent);
                    break;
                }
                break;
            case 1:
            case 3:
                if (this.mState != null) {
                    this.mState.mShouldInitHandwriting = false;
                    if (!this.mState.mHandled) {
                        this.mShowHoverIconForConnectedView = true;
                        break;
                    }
                }
                break;
            case 2:
                if (this.mState != null) {
                    if (this.mState.mShouldInitHandwriting && !this.mState.mExceedHandwritingSlop) {
                        if (motionEvent.getEventTime() - this.mState.mStylusDownTimeInMillis > this.mHandwritingTimeoutInMillis) {
                            this.mState.mShouldInitHandwriting = false;
                            break;
                        } else {
                            int findPointerIndex = motionEvent.findPointerIndex(this.mState.mStylusPointerId);
                            if (largerThanTouchSlop(motionEvent.getX(findPointerIndex), motionEvent.getY(findPointerIndex), this.mState.mStylusDownX, this.mState.mStylusDownY)) {
                                this.mState.mExceedHandwritingSlop = true;
                                android.view.View findBestCandidateView = findBestCandidateView(this.mState.mStylusDownX, this.mState.mStylusDownY, false);
                                if (findBestCandidateView != null && findBestCandidateView.isEnabled()) {
                                    if (findBestCandidateView == getConnectedOrFocusedView()) {
                                        if (!this.mInitiateWithoutConnection && !findBestCandidateView.hasFocus()) {
                                            requestFocusWithoutReveal(findBestCandidateView);
                                        }
                                        startHandwriting(findBestCandidateView);
                                    } else if (findBestCandidateView.getHandwritingDelegatorCallback() != null) {
                                        prepareDelegation(findBestCandidateView);
                                    } else {
                                        if (!this.mInitiateWithoutConnection) {
                                            this.mState.mPendingConnectedView = new java.lang.ref.WeakReference(findBestCandidateView);
                                        }
                                        if (!findBestCandidateView.hasFocus()) {
                                            requestFocusWithoutReveal(findBestCandidateView);
                                        }
                                        if (this.mInitiateWithoutConnection && updateFocusedView(findBestCandidateView, true)) {
                                            startHandwriting(findBestCandidateView);
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    } else {
                        break;
                    }
                }
                break;
            case 6:
                int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                if (this.mState == null || pointerId != this.mState.mStylusPointerId) {
                }
                if (this.mState != null) {
                }
                break;
        }
        return false;
    }

    private android.view.View getConnectedView() {
        if (this.mConnectedView == null) {
            return null;
        }
        return this.mConnectedView.get();
    }

    private void clearConnectedView() {
        this.mConnectedView = null;
        this.mConnectionCount = 0;
    }

    public void onDelegateViewFocused(android.view.View view) {
        if (view == getConnectedView()) {
            tryAcceptStylusHandwritingDelegation(view);
        }
    }

    public void onInputConnectionCreated(android.view.View view) {
        if (this.mInitiateWithoutConnection && !view.isHandwritingDelegate()) {
            return;
        }
        if (!view.isAutoHandwritingEnabled()) {
            clearConnectedView();
            return;
        }
        if (getConnectedView() == view) {
            this.mConnectionCount++;
            return;
        }
        this.mConnectedView = new java.lang.ref.WeakReference<>(view);
        this.mConnectionCount = 1;
        this.mShowHoverIconForConnectedView = true;
        if (view.isHandwritingDelegate() && tryAcceptStylusHandwritingDelegation(view)) {
            this.mShowHoverIconForConnectedView = false;
        } else if (!this.mInitiateWithoutConnection && this.mState != null && this.mState.mPendingConnectedView != null && this.mState.mPendingConnectedView.get() == view) {
            startHandwriting(view);
        }
    }

    public void onInputConnectionClosed(android.view.View view) {
        android.view.View connectedView;
        if ((!this.mInitiateWithoutConnection || view.isHandwritingDelegate()) && (connectedView = getConnectedView()) != null) {
            if (connectedView == view) {
                this.mConnectionCount--;
                if (this.mConnectionCount == 0) {
                    clearConnectedView();
                    return;
                }
                return;
            }
            clearConnectedView();
        }
    }

    private android.view.View getFocusedView() {
        if (this.mFocusedView == null) {
            return null;
        }
        return this.mFocusedView.get();
    }

    public void clearFocusedView(android.view.View view) {
        if (view != null && this.mFocusedView != null && this.mFocusedView.get() == view) {
            this.mFocusedView = null;
        }
    }

    public boolean updateFocusedView(android.view.View view, boolean z) {
        if (!view.shouldInitiateHandwriting()) {
            this.mFocusedView = null;
            return false;
        }
        if (getFocusedView() != view) {
            this.mFocusedView = new java.lang.ref.WeakReference<>(view);
            this.mShowHoverIconForConnectedView = true;
        }
        if (!z && view.isHandwritingDelegate()) {
            tryAcceptStylusHandwritingDelegation(view);
        }
        return true;
    }

    public void startHandwriting(android.view.View view) {
        this.mImm.startStylusHandwriting(view);
        this.mState.mHandled = true;
        this.mState.mShouldInitHandwriting = false;
        this.mShowHoverIconForConnectedView = false;
        if (view instanceof android.widget.TextView) {
            ((android.widget.TextView) view).hideHint();
        }
    }

    private void prepareDelegation(android.view.View view) {
        java.lang.String str;
        java.lang.String allowedHandwritingDelegatePackageName = view.getAllowedHandwritingDelegatePackageName();
        if (allowedHandwritingDelegatePackageName != null) {
            str = allowedHandwritingDelegatePackageName;
        } else {
            str = view.getContext().getOpPackageName();
        }
        if (this.mImm.isConnectionlessStylusHandwritingAvailable()) {
            view.getViewRootImpl().getView().clearFocus();
            android.view.inputmethod.InputMethodManager inputMethodManager = this.mImm;
            android.view.inputmethod.CursorAnchorInfo cursorAnchorInfoForConnectionless = getCursorAnchorInfoForConnectionless(view);
            java.util.Objects.requireNonNull(view);
            inputMethodManager.startConnectionlessStylusHandwritingForDelegation(view, cursorAnchorInfoForConnectionless, str, new android.view.HandwritingInitiator$$ExternalSyntheticLambda0(view), new android.view.HandwritingInitiator.DelegationCallback(view, str));
            this.mState.mShouldInitHandwriting = false;
        } else {
            this.mImm.prepareStylusHandwritingDelegation(view, str);
            view.getHandwritingDelegatorCallback().run();
        }
        this.mState.mHandled = true;
    }

    public boolean tryAcceptStylusHandwritingDelegation(android.view.View view) {
        if (android.view.inputmethod.Flags.useZeroJankProxy()) {
            tryAcceptStylusHandwritingDelegationAsync(view);
            return false;
        }
        return tryAcceptStylusHandwritingDelegationInternal(view);
    }

    private boolean tryAcceptStylusHandwritingDelegationInternal(android.view.View view) {
        java.lang.String allowedHandwritingDelegatorPackageName = view.getAllowedHandwritingDelegatorPackageName();
        if (allowedHandwritingDelegatorPackageName == null) {
            allowedHandwritingDelegatorPackageName = view.getContext().getOpPackageName();
        }
        if (this.mImm.acceptStylusHandwritingDelegation(view, allowedHandwritingDelegatorPackageName)) {
            onDelegationAccepted(view);
            return true;
        }
        return false;
    }

    private void tryAcceptStylusHandwritingDelegationAsync(final android.view.View view) {
        java.lang.String allowedHandwritingDelegatorPackageName = view.getAllowedHandwritingDelegatorPackageName();
        if (allowedHandwritingDelegatorPackageName == null) {
            allowedHandwritingDelegatorPackageName = view.getContext().getOpPackageName();
        }
        java.util.function.Consumer<java.lang.Boolean> consumer = new java.util.function.Consumer() { // from class: android.view.HandwritingInitiator$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.view.HandwritingInitiator.this.lambda$tryAcceptStylusHandwritingDelegationAsync$0(view, (java.lang.Boolean) obj);
            }
        };
        android.view.inputmethod.InputMethodManager inputMethodManager = this.mImm;
        java.util.Objects.requireNonNull(view);
        inputMethodManager.acceptStylusHandwritingDelegation(view, allowedHandwritingDelegatorPackageName, new android.view.HandwritingInitiator$$ExternalSyntheticLambda0(view), consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryAcceptStylusHandwritingDelegationAsync$0(android.view.View view, java.lang.Boolean bool) {
        if (bool.booleanValue()) {
            onDelegationAccepted(view);
        }
    }

    private void onDelegationAccepted(android.view.View view) {
        if (this.mState != null) {
            this.mState.mHandled = true;
            this.mState.mShouldInitHandwriting = false;
        }
        if (view instanceof android.widget.TextView) {
            ((android.widget.TextView) view).hideHint();
        }
        this.mShowHoverIconForConnectedView = false;
    }

    public void updateHandwritingAreasForView(android.view.View view) {
        this.mHandwritingAreasTracker.updateHandwritingAreaForView(view);
    }

    private static boolean shouldTriggerStylusHandwritingForView(android.view.View view) {
        if (!view.shouldInitiateHandwriting()) {
            return false;
        }
        return view.isStylusHandwritingAvailable();
    }

    public android.view.PointerIcon onResolvePointerIcon(android.content.Context context, android.view.MotionEvent motionEvent) {
        android.view.View findHoverView = findHoverView(motionEvent);
        if (findHoverView == null) {
            return null;
        }
        if (this.mShowHoverIconForConnectedView) {
            return android.view.PointerIcon.getSystemIcon(context, 1022);
        }
        if (findHoverView == getConnectedOrFocusedView()) {
            return null;
        }
        this.mShowHoverIconForConnectedView = true;
        return android.view.PointerIcon.getSystemIcon(context, 1022);
    }

    private android.view.View getConnectedOrFocusedView() {
        if (this.mInitiateWithoutConnection) {
            if (this.mFocusedView == null) {
                return null;
            }
            return this.mFocusedView.get();
        }
        if (this.mConnectedView == null) {
            return null;
        }
        return this.mConnectedView.get();
    }

    private android.view.View getCachedHoverTarget() {
        if (this.mCachedHoverTarget == null) {
            return null;
        }
        return this.mCachedHoverTarget.get();
    }

    private android.view.View findHoverView(android.view.MotionEvent motionEvent) {
        if (!motionEvent.isStylusPointer() || !motionEvent.isHoverEvent()) {
            return null;
        }
        if (motionEvent.getActionMasked() == 9 || motionEvent.getActionMasked() == 7) {
            float x = motionEvent.getX(motionEvent.getActionIndex());
            float y = motionEvent.getY(motionEvent.getActionIndex());
            android.view.View cachedHoverTarget = getCachedHoverTarget();
            if (cachedHoverTarget != null) {
                android.graphics.Rect rect = this.mTempRect;
                if (getViewHandwritingArea(cachedHoverTarget, rect) && isInHandwritingArea(rect, x, y, cachedHoverTarget, true) && shouldTriggerStylusHandwritingForView(cachedHoverTarget)) {
                    return cachedHoverTarget;
                }
            }
            android.view.View findBestCandidateView = findBestCandidateView(x, y, true);
            if (findBestCandidateView != null) {
                this.mCachedHoverTarget = new java.lang.ref.WeakReference<>(findBestCandidateView);
                return findBestCandidateView;
            }
        }
        this.mCachedHoverTarget = null;
        return null;
    }

    private void requestFocusWithoutReveal(android.view.View view) {
        if (view instanceof android.widget.EditText) {
            android.widget.EditText editText = (android.widget.EditText) view;
            if (!this.mState.mStylusDownWithinEditorBounds) {
                view.getLocationInWindow(this.mTempLocation);
                editText.setSelection(editText.getOffsetForPosition(this.mState.mStylusDownX - this.mTempLocation[0], this.mState.mStylusDownY - this.mTempLocation[1]));
            }
        }
        if (view.getRevealOnFocusHint()) {
            view.setRevealOnFocusHint(false);
            view.requestFocus();
            view.setRevealOnFocusHint(true);
            return;
        }
        view.requestFocus();
    }

    private android.view.View findBestCandidateView(float f, float f2, boolean z) {
        android.view.View connectedOrFocusedView = getConnectedOrFocusedView();
        if (connectedOrFocusedView != null) {
            android.graphics.Rect rect = this.mTempRect;
            if (getViewHandwritingArea(connectedOrFocusedView, rect) && isInHandwritingArea(rect, f, f2, connectedOrFocusedView, z) && shouldTriggerStylusHandwritingForView(connectedOrFocusedView)) {
                if (!z && this.mState != null) {
                    this.mState.mStylusDownWithinEditorBounds = contains(rect, f, f2, 0.0f, 0.0f, 0.0f, 0.0f);
                }
                return connectedOrFocusedView;
            }
        }
        float f3 = Float.MAX_VALUE;
        android.view.View view = null;
        for (android.view.HandwritingInitiator.HandwritableViewInfo handwritableViewInfo : this.mHandwritingAreasTracker.computeViewInfos()) {
            android.view.View view2 = handwritableViewInfo.getView();
            android.graphics.Rect handwritingArea = handwritableViewInfo.getHandwritingArea();
            if (isInHandwritingArea(handwritingArea, f, f2, view2, z) && shouldTriggerStylusHandwritingForView(view2)) {
                float distance = distance(handwritingArea, f, f2);
                if (distance == 0.0f) {
                    if (!z && this.mState != null) {
                        this.mState.mStylusDownWithinEditorBounds = true;
                    }
                    return view2;
                }
                if (distance < f3) {
                    f3 = distance;
                    view = view2;
                }
            }
        }
        return view;
    }

    private static float distance(android.graphics.Rect rect, float f, float f2) {
        float f3;
        float f4 = 0.0f;
        if (contains(rect, f, f2, 0.0f, 0.0f, 0.0f, 0.0f)) {
            return 0.0f;
        }
        if (f >= rect.left && f < rect.right) {
            f3 = 0.0f;
        } else if (f < rect.left) {
            f3 = rect.left - f;
        } else {
            f3 = f - rect.right;
        }
        if (f2 < rect.top || f2 >= rect.bottom) {
            if (f2 < rect.top) {
                f4 = rect.top - f2;
            } else {
                f4 = f2 - rect.bottom;
            }
        }
        return (f3 * f3) + (f4 * f4);
    }

    private static boolean getViewHandwritingArea(android.view.View view, android.graphics.Rect rect) {
        android.view.ViewParent parent = view.getParent();
        if (parent == null || !view.isAttachedToWindow() || !view.isAggregatedVisible()) {
            return false;
        }
        android.graphics.Rect handwritingArea = view.getHandwritingArea();
        if (handwritingArea == null) {
            rect.set(0, 0, view.getWidth(), view.getHeight());
        } else {
            rect.set(handwritingArea);
        }
        return parent.getChildVisibleRect(view, rect, null);
    }

    private boolean isInHandwritingArea(android.graphics.Rect rect, float f, float f2, android.view.View view, boolean z) {
        if (rect == null || !contains(rect, f, f2, view.getHandwritingBoundsOffsetLeft(), view.getHandwritingBoundsOffsetTop(), view.getHandwritingBoundsOffsetRight(), view.getHandwritingBoundsOffsetBottom())) {
            return false;
        }
        android.view.ViewParent parent = view.getParent();
        if (parent == null) {
            return true;
        }
        android.graphics.Region region = this.mTempRegion;
        this.mTempRegion.set(0, 0, view.getWidth(), view.getHeight());
        android.graphics.Matrix matrix = this.mTempMatrix;
        matrix.reset();
        if (!parent.getChildLocalHitRegion(view, region, matrix, z)) {
            return false;
        }
        float handwritingBoundsOffsetRight = f - view.getHandwritingBoundsOffsetRight();
        float handwritingBoundsOffsetBottom = f2 - view.getHandwritingBoundsOffsetBottom();
        float max = java.lang.Math.max(f + view.getHandwritingBoundsOffsetLeft(), handwritingBoundsOffsetRight + 1.0f);
        float max2 = java.lang.Math.max(f2 + view.getHandwritingBoundsOffsetTop(), 1.0f + handwritingBoundsOffsetBottom);
        android.graphics.RectF rectF = this.mTempRectF;
        rectF.set(handwritingBoundsOffsetRight, handwritingBoundsOffsetBottom, max, max2);
        matrix.mapRect(rectF);
        return region.op(java.lang.Math.round(rectF.left), java.lang.Math.round(rectF.top), java.lang.Math.round(rectF.right), java.lang.Math.round(rectF.bottom), android.graphics.Region.Op.INTERSECT);
    }

    private static boolean contains(android.graphics.Rect rect, float f, float f2, float f3, float f4, float f5, float f6) {
        return f >= ((float) rect.left) - f3 && f < ((float) rect.right) + f5 && f2 >= ((float) rect.top) - f4 && f2 < ((float) rect.bottom) + f6;
    }

    private boolean largerThanTouchSlop(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return (f5 * f5) + (f6 * f6) > ((float) (this.mHandwritingSlop * this.mHandwritingSlop));
    }

    private static class State {
        private boolean mExceedHandwritingSlop;
        private boolean mHandled;
        private java.lang.ref.WeakReference<android.view.View> mPendingConnectedView;
        private boolean mShouldInitHandwriting;
        private final long mStylusDownTimeInMillis;
        private boolean mStylusDownWithinEditorBounds;
        private final float mStylusDownX;
        private final float mStylusDownY;
        private final int mStylusPointerId;

        private State(android.view.MotionEvent motionEvent) {
            this.mPendingConnectedView = null;
            int actionIndex = motionEvent.getActionIndex();
            this.mStylusPointerId = motionEvent.getPointerId(actionIndex);
            this.mStylusDownTimeInMillis = motionEvent.getEventTime();
            this.mStylusDownX = motionEvent.getX(actionIndex);
            this.mStylusDownY = motionEvent.getY(actionIndex);
            this.mShouldInitHandwriting = true;
            this.mHandled = false;
            this.mExceedHandwritingSlop = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isViewActive(android.view.View view) {
        return view != null && view.isAttachedToWindow() && view.isAggregatedVisible() && view.shouldInitiateHandwriting();
    }

    private android.view.inputmethod.CursorAnchorInfo getCursorAnchorInfoForConnectionless(android.view.View view) {
        android.view.inputmethod.CursorAnchorInfo.Builder builder = new android.view.inputmethod.CursorAnchorInfo.Builder();
        android.widget.TextView findFirstTextViewDescendent = findFirstTextViewDescendent(view);
        if (findFirstTextViewDescendent != null) {
            findFirstTextViewDescendent.getCursorAnchorInfo(0, builder, this.mTempMatrix);
            if (findFirstTextViewDescendent.getSelectionStart() < 0) {
                float height = findFirstTextViewDescendent.getHeight() - findFirstTextViewDescendent.getExtendedPaddingBottom();
                builder.setInsertionMarkerLocation(findFirstTextViewDescendent.getCompoundPaddingStart(), findFirstTextViewDescendent.getExtendedPaddingTop(), height, height, 0);
            }
        } else {
            this.mTempMatrix.reset();
            view.transformMatrixToGlobal(this.mTempMatrix);
            builder.setMatrix(this.mTempMatrix);
            builder.setInsertionMarkerLocation(view.isLayoutRtl() ? view.getWidth() : 0.0f, 0.0f, view.getHeight(), view.getHeight(), 0);
        }
        return builder.build();
    }

    private static android.widget.TextView findFirstTextViewDescendent(android.view.View view) {
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                android.view.View childAt = viewGroup.getChildAt(i);
                android.widget.TextView findFirstTextViewDescendent = childAt instanceof android.widget.TextView ? (android.widget.TextView) childAt : findFirstTextViewDescendent(viewGroup.getChildAt(i));
                if (findFirstTextViewDescendent != null && findFirstTextViewDescendent.isAggregatedVisible() && (!android.text.TextUtils.isEmpty(findFirstTextViewDescendent.getText()) || !android.text.TextUtils.isEmpty(findFirstTextViewDescendent.getHint()))) {
                    return findFirstTextViewDescendent;
                }
            }
            return null;
        }
        return null;
    }

    public static class HandwritingAreaTracker {
        private final java.util.List<android.view.HandwritingInitiator.HandwritableViewInfo> mHandwritableViewInfos = new java.util.ArrayList();

        public void updateHandwritingAreaForView(android.view.View view) {
            java.util.Iterator<android.view.HandwritingInitiator.HandwritableViewInfo> it = this.mHandwritableViewInfos.iterator();
            boolean z = false;
            while (it.hasNext()) {
                android.view.HandwritingInitiator.HandwritableViewInfo next = it.next();
                android.view.View view2 = next.getView();
                if (!android.view.HandwritingInitiator.isViewActive(view2)) {
                    it.remove();
                }
                if (view2 == view) {
                    z = true;
                    next.mIsDirty = true;
                }
            }
            if (!z && android.view.HandwritingInitiator.isViewActive(view)) {
                this.mHandwritableViewInfos.add(new android.view.HandwritingInitiator.HandwritableViewInfo(view));
            }
        }

        static /* synthetic */ boolean lambda$computeViewInfos$0(android.view.HandwritingInitiator.HandwritableViewInfo handwritableViewInfo) {
            return !handwritableViewInfo.update();
        }

        public java.util.List<android.view.HandwritingInitiator.HandwritableViewInfo> computeViewInfos() {
            this.mHandwritableViewInfos.removeIf(new java.util.function.Predicate() { // from class: android.view.HandwritingInitiator$HandwritingAreaTracker$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.view.HandwritingInitiator.HandwritingAreaTracker.lambda$computeViewInfos$0((android.view.HandwritingInitiator.HandwritableViewInfo) obj);
                }
            });
            return this.mHandwritableViewInfos;
        }
    }

    public static class HandwritableViewInfo {
        android.graphics.Rect mHandwritingArea = null;
        public boolean mIsDirty = true;
        final java.lang.ref.WeakReference<android.view.View> mViewRef;

        public HandwritableViewInfo(android.view.View view) {
            this.mViewRef = new java.lang.ref.WeakReference<>(view);
        }

        public android.view.View getView() {
            return this.mViewRef.get();
        }

        public android.graphics.Rect getHandwritingArea() {
            return this.mHandwritingArea;
        }

        public boolean update() {
            android.view.View view = getView();
            if (!android.view.HandwritingInitiator.isViewActive(view)) {
                return false;
            }
            if (!this.mIsDirty) {
                return true;
            }
            android.graphics.Rect handwritingArea = view.getHandwritingArea();
            if (handwritingArea == null) {
                return false;
            }
            android.view.ViewParent parent = view.getParent();
            if (parent != null) {
                if (this.mHandwritingArea == null) {
                    this.mHandwritingArea = new android.graphics.Rect();
                }
                this.mHandwritingArea.set(handwritingArea);
                if (!parent.getChildVisibleRect(view, this.mHandwritingArea, null)) {
                    this.mHandwritingArea = null;
                }
            }
            this.mIsDirty = false;
            return true;
        }
    }

    private class DelegationCallback implements android.view.inputmethod.ConnectionlessHandwritingCallback {
        private final java.lang.String mDelegatePackageName;
        private final android.view.View mView;

        private DelegationCallback(android.view.View view, java.lang.String str) {
            this.mView = view;
            this.mDelegatePackageName = str;
        }

        @Override // android.view.inputmethod.ConnectionlessHandwritingCallback
        public void onResult(java.lang.CharSequence charSequence) {
            this.mView.getHandwritingDelegatorCallback().run();
        }

        @Override // android.view.inputmethod.ConnectionlessHandwritingCallback
        public void onError(int i) {
            switch (i) {
                case 0:
                    this.mView.getHandwritingDelegatorCallback().run();
                    break;
                case 1:
                    android.view.HandwritingInitiator.this.mImm.prepareStylusHandwritingDelegation(this.mView, this.mDelegatePackageName);
                    this.mView.getHandwritingDelegatorCallback().run();
                    break;
            }
        }
    }
}
