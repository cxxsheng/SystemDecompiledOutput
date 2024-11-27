package android.view;

/* loaded from: classes4.dex */
public final class ScrollCaptureSearchResults {
    private static final int AFTER = 1;
    private static final int BEFORE = -1;
    private static final int EQUAL = 0;
    static final java.util.Comparator<android.view.ScrollCaptureTarget> PRIORITY_ORDER = new java.util.Comparator() { // from class: android.view.ScrollCaptureSearchResults$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            return android.view.ScrollCaptureSearchResults.lambda$static$1((android.view.ScrollCaptureTarget) obj, (android.view.ScrollCaptureTarget) obj2);
        }
    };
    private int mCompleted;
    private final java.util.concurrent.Executor mExecutor;
    private java.lang.Runnable mOnCompleteListener;
    private boolean mComplete = true;
    private final java.util.List<android.view.ScrollCaptureTarget> mTargets = new java.util.ArrayList();
    private final android.os.CancellationSignal mCancel = new android.os.CancellationSignal();

    public ScrollCaptureSearchResults(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }

    public void addTarget(android.view.ScrollCaptureTarget scrollCaptureTarget) {
        java.util.Objects.requireNonNull(scrollCaptureTarget);
        this.mTargets.add(scrollCaptureTarget);
        this.mComplete = false;
        final android.view.ScrollCaptureCallback callback = scrollCaptureTarget.getCallback();
        final android.view.ScrollCaptureSearchResults.SearchRequest searchRequest = new android.view.ScrollCaptureSearchResults.SearchRequest(scrollCaptureTarget);
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.ScrollCaptureSearchResults$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ScrollCaptureSearchResults.this.lambda$addTarget$0(callback, searchRequest);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addTarget$0(android.view.ScrollCaptureCallback scrollCaptureCallback, java.util.function.Consumer consumer) {
        scrollCaptureCallback.onScrollCaptureSearch(this.mCancel, consumer);
    }

    public boolean isComplete() {
        return this.mComplete;
    }

    public void setOnCompleteListener(java.lang.Runnable runnable) {
        if (this.mComplete) {
            runnable.run();
        } else {
            this.mOnCompleteListener = runnable;
        }
    }

    public boolean isEmpty() {
        return this.mTargets.isEmpty();
    }

    public void finish() {
        if (!this.mComplete) {
            this.mCancel.cancel();
            signalComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void signalComplete() {
        this.mComplete = true;
        this.mTargets.sort(PRIORITY_ORDER);
        if (this.mOnCompleteListener != null) {
            this.mOnCompleteListener.run();
            this.mOnCompleteListener = null;
        }
    }

    public java.util.List<android.view.ScrollCaptureTarget> getTargets() {
        return new java.util.ArrayList(this.mTargets);
    }

    public android.view.ScrollCaptureTarget getTopResult() {
        android.view.ScrollCaptureTarget scrollCaptureTarget = this.mTargets.isEmpty() ? null : this.mTargets.get(0);
        if (scrollCaptureTarget == null || scrollCaptureTarget.getScrollBounds() == null) {
            return null;
        }
        return scrollCaptureTarget;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SearchRequest implements java.util.function.Consumer<android.graphics.Rect> {
        private android.view.ScrollCaptureTarget mTarget;

        SearchRequest(android.view.ScrollCaptureTarget scrollCaptureTarget) {
            this.mTarget = scrollCaptureTarget;
        }

        @Override // java.util.function.Consumer
        public void accept(final android.graphics.Rect rect) {
            if (this.mTarget == null || android.view.ScrollCaptureSearchResults.this.mCancel.isCanceled()) {
                return;
            }
            android.view.ScrollCaptureSearchResults.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.ScrollCaptureSearchResults$SearchRequest$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.ScrollCaptureSearchResults.SearchRequest.this.lambda$accept$0(rect);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: consume, reason: merged with bridge method [inline-methods] */
        public void lambda$accept$0(android.graphics.Rect rect) {
            if (this.mTarget == null || android.view.ScrollCaptureSearchResults.this.mCancel.isCanceled()) {
                return;
            }
            if (!android.view.ScrollCaptureSearchResults.nullOrEmpty(rect)) {
                this.mTarget.setScrollBounds(rect);
                this.mTarget.updatePositionInWindow();
            }
            android.view.ScrollCaptureSearchResults.this.mCompleted++;
            this.mTarget = null;
            if (android.view.ScrollCaptureSearchResults.this.mCompleted == android.view.ScrollCaptureSearchResults.this.mTargets.size()) {
                android.view.ScrollCaptureSearchResults.this.signalComplete();
            }
        }
    }

    static /* synthetic */ int lambda$static$1(android.view.ScrollCaptureTarget scrollCaptureTarget, android.view.ScrollCaptureTarget scrollCaptureTarget2) {
        if (scrollCaptureTarget == null && scrollCaptureTarget2 == null) {
            return 0;
        }
        if (scrollCaptureTarget == null || scrollCaptureTarget2 == null) {
            return scrollCaptureTarget == null ? 1 : -1;
        }
        boolean nullOrEmpty = nullOrEmpty(scrollCaptureTarget.getScrollBounds());
        boolean nullOrEmpty2 = nullOrEmpty(scrollCaptureTarget2.getScrollBounds());
        if (nullOrEmpty || nullOrEmpty2) {
            if (nullOrEmpty && nullOrEmpty2) {
                return 0;
            }
            return nullOrEmpty ? 1 : -1;
        }
        android.view.View containingView = scrollCaptureTarget.getContainingView();
        android.view.View containingView2 = scrollCaptureTarget2.getContainingView();
        boolean hasIncludeHint = hasIncludeHint(containingView);
        if (hasIncludeHint != hasIncludeHint(containingView2)) {
            return hasIncludeHint ? -1 : 1;
        }
        if (isDescendant(containingView, containingView2)) {
            return -1;
        }
        return (!isDescendant(containingView2, containingView) && area(scrollCaptureTarget.getScrollBounds()) >= area(scrollCaptureTarget2.getScrollBounds())) ? -1 : 1;
    }

    private static int area(android.graphics.Rect rect) {
        return rect.width() * rect.height();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean nullOrEmpty(android.graphics.Rect rect) {
        return rect == null || rect.isEmpty();
    }

    private static boolean hasIncludeHint(android.view.View view) {
        return (view.getScrollCaptureHint() & 2) != 0;
    }

    private static boolean isDescendant(android.view.View view, android.view.View view2) {
        if (view == view2) {
            return false;
        }
        android.view.ViewParent parent = view2.getParent();
        while (parent != view && parent != null) {
            parent = parent.getParent();
        }
        return parent == view;
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("results:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("complete: " + isComplete());
        indentingPrintWriter.println("cancelled: " + this.mCancel.isCanceled());
        indentingPrintWriter.println("targets:");
        indentingPrintWriter.increaseIndent();
        if (isEmpty()) {
            indentingPrintWriter.println(android.accessibilityservice.AccessibilityTrace.NAME_NONE);
        } else {
            for (int i = 0; i < this.mTargets.size(); i++) {
                indentingPrintWriter.println(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
                indentingPrintWriter.increaseIndent();
                this.mTargets.get(i).dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }
}
