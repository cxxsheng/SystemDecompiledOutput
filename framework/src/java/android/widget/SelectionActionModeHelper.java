package android.widget;

/* loaded from: classes4.dex */
public class SelectionActionModeHelper {
    private static final java.lang.String LOG_TAG = "SelectActionModeHelper";
    private final android.widget.Editor mEditor;
    private final android.widget.SelectionActionModeHelper.SelectionTracker mSelectionTracker;
    private final android.widget.SmartSelectSprite mSmartSelectSprite;
    private android.view.textclassifier.TextClassification mTextClassification;
    private android.os.AsyncTask mTextClassificationAsyncTask;
    private final android.widget.SelectionActionModeHelper.TextClassificationHelper mTextClassificationHelper;
    private final android.widget.TextView mTextView;

    SelectionActionModeHelper(android.widget.Editor editor) {
        this.mEditor = (android.widget.Editor) java.util.Objects.requireNonNull(editor);
        this.mTextView = this.mEditor.getTextView();
        android.content.Context context = this.mTextView.getContext();
        android.widget.TextView textView = this.mTextView;
        java.util.Objects.requireNonNull(textView);
        this.mTextClassificationHelper = new android.widget.SelectionActionModeHelper.TextClassificationHelper(context, new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda10(textView), getText(this.mTextView), 0, 1, this.mTextView.getTextLocales());
        this.mSelectionTracker = new android.widget.SelectionActionModeHelper.SelectionTracker(this.mTextView);
        if (getTextClassificationSettings().isSmartSelectionAnimationEnabled()) {
            android.content.Context context2 = this.mTextView.getContext();
            int i = editor.getTextView().mHighlightColor;
            final android.widget.TextView textView2 = this.mTextView;
            java.util.Objects.requireNonNull(textView2);
            this.mSmartSelectSprite = new android.widget.SmartSelectSprite(context2, i, new java.lang.Runnable() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.TextView.this.invalidate();
                }
            });
            return;
        }
        this.mSmartSelectSprite = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int[] sortSelectionIndices(int i, int i2) {
        if (i < i2) {
            return new int[]{i, i2};
        }
        return new int[]{i2, i};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int[] sortSelectionIndicesFromTextView(android.widget.TextView textView) {
        return sortSelectionIndices(textView.getSelectionStart(), textView.getSelectionEnd());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda2] */
    public void startSelectionActionModeAsync(boolean z) {
        android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda3 selectionActionModeHelper$$ExternalSyntheticLambda3;
        java.util.function.Consumer consumer;
        boolean isSmartSelectionEnabled = z & getTextClassificationSettings().isSmartSelectionEnabled();
        int[] sortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
        this.mSelectionTracker.onOriginalSelection(getText(this.mTextView), sortSelectionIndicesFromTextView[0], sortSelectionIndicesFromTextView[1], false);
        cancelAsyncTask();
        if (skipTextClassification()) {
            startSelectionActionMode(null);
            return;
        }
        resetTextClassificationHelper();
        if (this.mSmartSelectSprite != null && this.mSmartSelectSprite.isAnimationActive()) {
            this.mSmartSelectSprite.cancelAnimation();
        }
        android.widget.TextView textView = this.mTextView;
        int timeoutDuration = this.mTextClassificationHelper.getTimeoutDuration();
        if (isSmartSelectionEnabled) {
            final android.widget.SelectionActionModeHelper.TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
            java.util.Objects.requireNonNull(textClassificationHelper);
            selectionActionModeHelper$$ExternalSyntheticLambda3 = new java.util.function.Supplier() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return android.widget.SelectionActionModeHelper.TextClassificationHelper.this.suggestSelection();
                }
            };
        } else {
            android.widget.SelectionActionModeHelper.TextClassificationHelper textClassificationHelper2 = this.mTextClassificationHelper;
            java.util.Objects.requireNonNull(textClassificationHelper2);
            selectionActionModeHelper$$ExternalSyntheticLambda3 = new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda3(textClassificationHelper2);
        }
        if (this.mSmartSelectSprite != null) {
            consumer = new java.util.function.Consumer() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.widget.SelectionActionModeHelper.this.startSelectionActionModeWithSmartSelectAnimation((android.widget.SelectionActionModeHelper.SelectionResult) obj);
                }
            };
        } else {
            consumer = new java.util.function.Consumer() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.widget.SelectionActionModeHelper.this.startSelectionActionMode((android.widget.SelectionActionModeHelper.SelectionResult) obj);
                }
            };
        }
        android.widget.SelectionActionModeHelper.TextClassificationHelper textClassificationHelper3 = this.mTextClassificationHelper;
        java.util.Objects.requireNonNull(textClassificationHelper3);
        this.mTextClassificationAsyncTask = new android.widget.SelectionActionModeHelper.TextClassificationAsyncTask(textView, timeoutDuration, selectionActionModeHelper$$ExternalSyntheticLambda3, consumer, new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda6(textClassificationHelper3)).execute(new java.lang.Void[0]);
    }

    public void startLinkActionModeAsync(int i, int i2) {
        int[] sortSelectionIndices = sortSelectionIndices(i, i2);
        this.mSelectionTracker.onOriginalSelection(getText(this.mTextView), sortSelectionIndices[0], sortSelectionIndices[1], true);
        cancelAsyncTask();
        if (skipTextClassification()) {
            startLinkActionMode(null);
            return;
        }
        resetTextClassificationHelper(sortSelectionIndices[0], sortSelectionIndices[1]);
        android.widget.TextView textView = this.mTextView;
        int timeoutDuration = this.mTextClassificationHelper.getTimeoutDuration();
        android.widget.SelectionActionModeHelper.TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
        java.util.Objects.requireNonNull(textClassificationHelper);
        android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda3 selectionActionModeHelper$$ExternalSyntheticLambda3 = new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda3(textClassificationHelper);
        java.util.function.Consumer consumer = new java.util.function.Consumer() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.widget.SelectionActionModeHelper.this.startLinkActionMode((android.widget.SelectionActionModeHelper.SelectionResult) obj);
            }
        };
        android.widget.SelectionActionModeHelper.TextClassificationHelper textClassificationHelper2 = this.mTextClassificationHelper;
        java.util.Objects.requireNonNull(textClassificationHelper2);
        this.mTextClassificationAsyncTask = new android.widget.SelectionActionModeHelper.TextClassificationAsyncTask(textView, timeoutDuration, selectionActionModeHelper$$ExternalSyntheticLambda3, consumer, new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda6(textClassificationHelper2)).execute(new java.lang.Void[0]);
    }

    public void invalidateActionModeAsync() {
        cancelAsyncTask();
        if (skipTextClassification()) {
            invalidateActionMode(null);
            return;
        }
        resetTextClassificationHelper();
        android.widget.TextView textView = this.mTextView;
        int timeoutDuration = this.mTextClassificationHelper.getTimeoutDuration();
        android.widget.SelectionActionModeHelper.TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
        java.util.Objects.requireNonNull(textClassificationHelper);
        android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda3 selectionActionModeHelper$$ExternalSyntheticLambda3 = new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda3(textClassificationHelper);
        java.util.function.Consumer consumer = new java.util.function.Consumer() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.widget.SelectionActionModeHelper.this.invalidateActionMode((android.widget.SelectionActionModeHelper.SelectionResult) obj);
            }
        };
        android.widget.SelectionActionModeHelper.TextClassificationHelper textClassificationHelper2 = this.mTextClassificationHelper;
        java.util.Objects.requireNonNull(textClassificationHelper2);
        this.mTextClassificationAsyncTask = new android.widget.SelectionActionModeHelper.TextClassificationAsyncTask(textView, timeoutDuration, selectionActionModeHelper$$ExternalSyntheticLambda3, consumer, new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda6(textClassificationHelper2)).execute(new java.lang.Void[0]);
    }

    public void onSelectionAction(int i, java.lang.String str) {
        int[] sortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
        this.mSelectionTracker.onSelectionAction(sortSelectionIndicesFromTextView[0], sortSelectionIndicesFromTextView[1], getActionType(i), str, this.mTextClassification);
    }

    public void onSelectionDrag() {
        int[] sortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
        this.mSelectionTracker.onSelectionAction(sortSelectionIndicesFromTextView[0], sortSelectionIndicesFromTextView[1], 106, null, this.mTextClassification);
    }

    public void onTextChanged(int i, int i2) {
        int[] sortSelectionIndices = sortSelectionIndices(i, i2);
        this.mSelectionTracker.onTextChanged(sortSelectionIndices[0], sortSelectionIndices[1], this.mTextClassification);
    }

    public boolean resetSelection(int i) {
        if (this.mSelectionTracker.resetSelection(i, this.mEditor)) {
            invalidateActionModeAsync();
            return true;
        }
        return false;
    }

    public android.view.textclassifier.TextClassification getTextClassification() {
        return this.mTextClassification;
    }

    public void onDestroyActionMode() {
        cancelSmartSelectAnimation();
        this.mSelectionTracker.onSelectionDestroyed();
        cancelAsyncTask();
    }

    public void onDraw(android.graphics.Canvas canvas) {
        if (isDrawingHighlight() && this.mSmartSelectSprite != null) {
            this.mSmartSelectSprite.draw(canvas);
        }
    }

    public boolean isDrawingHighlight() {
        return this.mSmartSelectSprite != null && this.mSmartSelectSprite.isAnimationActive();
    }

    private android.view.textclassifier.TextClassificationConstants getTextClassificationSettings() {
        return android.view.textclassifier.TextClassificationManager.getSettings(this.mTextView.getContext());
    }

    private void cancelAsyncTask() {
        if (this.mTextClassificationAsyncTask != null) {
            this.mTextClassificationAsyncTask.cancel(true);
            this.mTextClassificationAsyncTask = null;
        }
        this.mTextClassification = null;
    }

    private boolean skipTextClassification() {
        return this.mTextView.usesNoOpTextClassifier() || (this.mTextView.getSelectionEnd() == this.mTextView.getSelectionStart()) || (this.mTextView.hasPasswordTransformationMethod() || android.widget.TextView.isPasswordInputType(this.mTextView.getInputType()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLinkActionMode(android.widget.SelectionActionModeHelper.SelectionResult selectionResult) {
        startActionMode(2, selectionResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSelectionActionMode(android.widget.SelectionActionModeHelper.SelectionResult selectionResult) {
        startActionMode(0, selectionResult);
    }

    private void startActionMode(int i, android.widget.SelectionActionModeHelper.SelectionResult selectionResult) {
        java.lang.CharSequence text = getText(this.mTextView);
        if (selectionResult != null && (text instanceof android.text.Spannable) && (this.mTextView.isTextSelectable() || this.mTextView.isTextEditable())) {
            if (!getTextClassificationSettings().isModelDarkLaunchEnabled()) {
                android.text.Selection.setSelection((android.text.Spannable) text, selectionResult.mStart, selectionResult.mEnd);
                this.mTextView.invalidate();
            }
            this.mTextClassification = selectionResult.mClassification;
        } else if (selectionResult != null && i == 2) {
            this.mTextClassification = selectionResult.mClassification;
        } else {
            this.mTextClassification = null;
        }
        if (this.mEditor.startActionModeInternal(i)) {
            android.widget.Editor.SelectionModifierCursorController selectionController = this.mEditor.getSelectionController();
            if (selectionController != null && (this.mTextView.isTextSelectable() || this.mTextView.isTextEditable())) {
                if (this.mTextView.showUIForTouchScreen()) {
                    selectionController.show();
                } else {
                    selectionController.hide();
                }
            }
            if (selectionResult != null) {
                switch (i) {
                    case 0:
                        this.mSelectionTracker.onSmartSelection(selectionResult);
                        break;
                    case 2:
                        this.mSelectionTracker.onLinkSelected(selectionResult);
                        break;
                }
            }
        }
        this.mEditor.setRestartActionModeOnNextRefresh(false);
        this.mTextClassificationAsyncTask = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSelectionActionModeWithSmartSelectAnimation(final android.widget.SelectionActionModeHelper.SelectionResult selectionResult) {
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.SelectionActionModeHelper.this.lambda$startSelectionActionModeWithSmartSelectAnimation$0(selectionResult);
            }
        };
        int[] sortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
        boolean z = false;
        if (selectionResult != null && (sortSelectionIndicesFromTextView[0] != selectionResult.mStart || sortSelectionIndicesFromTextView[1] != selectionResult.mEnd)) {
            z = true;
        }
        if (!z) {
            runnable.run();
            return;
        }
        java.util.List<android.widget.SmartSelectSprite.RectangleWithTextSelectionLayout> convertSelectionToRectangles = convertSelectionToRectangles(this.mTextView, selectionResult.mStart, selectionResult.mEnd);
        this.mSmartSelectSprite.startAnimation(movePointInsideNearestRectangle(new android.graphics.PointF(this.mEditor.getLastUpPositionX(), this.mEditor.getLastUpPositionY()), convertSelectionToRectangles, new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda1()), convertSelectionToRectangles, runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSelectionActionModeWithSmartSelectAnimation$0(android.widget.SelectionActionModeHelper.SelectionResult selectionResult) {
        if (selectionResult == null || selectionResult.mStart < 0 || selectionResult.mEnd > getText(this.mTextView).length() || selectionResult.mStart > selectionResult.mEnd) {
            selectionResult = null;
        }
        startSelectionActionMode(selectionResult);
    }

    private java.util.List<android.widget.SmartSelectSprite.RectangleWithTextSelectionLayout> convertSelectionToRectangles(android.widget.TextView textView, int i, int i2) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        android.text.Layout.SelectionRectangleConsumer selectionRectangleConsumer = new android.text.Layout.SelectionRectangleConsumer() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda12
            @Override // android.text.Layout.SelectionRectangleConsumer
            public final void accept(float f, float f2, float f3, float f4, int i3) {
                android.widget.SelectionActionModeHelper.mergeRectangleIntoList(arrayList, new android.graphics.RectF(f, f2, f3, f4), new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda1(), new java.util.function.Function() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda8
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return android.widget.SelectionActionModeHelper.lambda$convertSelectionToRectangles$1(i3, (android.graphics.RectF) obj);
                    }
                });
            }
        };
        textView.getLayout().getSelection(textView.originalToTransformed(i, 1), textView.originalToTransformed(i2, 1), selectionRectangleConsumer);
        arrayList.sort(java.util.Comparator.comparing(new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda1(), android.widget.SmartSelectSprite.RECTANGLE_COMPARATOR));
        return arrayList;
    }

    static /* synthetic */ android.widget.SmartSelectSprite.RectangleWithTextSelectionLayout lambda$convertSelectionToRectangles$1(int i, android.graphics.RectF rectF) {
        return new android.widget.SmartSelectSprite.RectangleWithTextSelectionLayout(rectF, i);
    }

    public static <T> void mergeRectangleIntoList(java.util.List<T> list, android.graphics.RectF rectF, java.util.function.Function<T, android.graphics.RectF> function, java.util.function.Function<android.graphics.RectF, T> function2) {
        if (rectF.isEmpty()) {
            return;
        }
        int size = list.size();
        int i = 0;
        while (true) {
            boolean z = true;
            if (i < size) {
                android.graphics.RectF apply = function.apply(list.get(i));
                if (apply.contains(rectF)) {
                    return;
                }
                if (!rectF.contains(apply)) {
                    boolean z2 = rectF.left == apply.right || rectF.right == apply.left;
                    if (rectF.top != apply.top || rectF.bottom != apply.bottom || (!android.graphics.RectF.intersects(rectF, apply) && !z2)) {
                        z = false;
                    }
                    if (z) {
                        rectF.union(apply);
                        apply.setEmpty();
                    }
                } else {
                    apply.setEmpty();
                }
                i++;
            } else {
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    if (function.apply(list.get(i2)).isEmpty()) {
                        list.remove(i2);
                    }
                }
                list.add(function2.apply(rectF));
                return;
            }
        }
    }

    public static <T> android.graphics.PointF movePointInsideNearestRectangle(android.graphics.PointF pointF, java.util.List<T> list, java.util.function.Function<T, android.graphics.RectF> function) {
        float f;
        android.graphics.PointF pointF2 = pointF;
        int size = list.size();
        float f2 = -1.0f;
        int i = 0;
        double d = Double.MAX_VALUE;
        float f3 = -1.0f;
        while (i < size) {
            android.graphics.RectF apply = function.apply(list.get(i));
            float centerY = apply.centerY();
            if (pointF2.x > apply.right) {
                f = apply.right;
            } else if (pointF2.x < apply.left) {
                f = apply.left;
            } else {
                f = pointF2.x;
            }
            int i2 = size;
            double pow = java.lang.Math.pow(pointF2.x - f, 2.0d) + java.lang.Math.pow(pointF2.y - centerY, 2.0d);
            if (pow < d) {
                f2 = f;
                f3 = centerY;
                d = pow;
            }
            i++;
            pointF2 = pointF;
            size = i2;
        }
        return new android.graphics.PointF(f2, f3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateActionMode(android.widget.SelectionActionModeHelper.SelectionResult selectionResult) {
        cancelSmartSelectAnimation();
        this.mTextClassification = selectionResult != null ? selectionResult.mClassification : null;
        android.view.ActionMode textActionMode = this.mEditor.getTextActionMode();
        if (textActionMode != null) {
            textActionMode.invalidate();
        }
        int[] sortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
        this.mSelectionTracker.onSelectionUpdated(sortSelectionIndicesFromTextView[0], sortSelectionIndicesFromTextView[1], this.mTextClassification);
        this.mTextClassificationAsyncTask = null;
    }

    private void resetTextClassificationHelper(int i, int i2) {
        int i3;
        int i4;
        if (i < 0 || i2 < 0) {
            int[] sortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
            int i5 = sortSelectionIndicesFromTextView[0];
            i3 = sortSelectionIndicesFromTextView[1];
            i4 = i5;
        } else {
            i4 = i;
            i3 = i2;
        }
        android.widget.SelectionActionModeHelper.TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
        android.widget.TextView textView = this.mTextView;
        java.util.Objects.requireNonNull(textView);
        textClassificationHelper.init(new android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda10(textView), getText(this.mTextView), i4, i3, this.mTextView.getTextLocales());
    }

    private void resetTextClassificationHelper() {
        resetTextClassificationHelper(-1, -1);
    }

    private void cancelSmartSelectAnimation() {
        if (this.mSmartSelectSprite != null) {
            this.mSmartSelectSprite.cancelAnimation();
        }
    }

    private static final class SelectionTracker {
        private boolean mAllowReset;
        private final android.widget.SelectionActionModeHelper.SelectionTracker.LogAbandonRunnable mDelayedLogAbandon = new android.widget.SelectionActionModeHelper.SelectionTracker.LogAbandonRunnable();
        private android.widget.SelectionActionModeHelper.SelectionMetricsLogger mLogger;
        private int mOriginalEnd;
        private int mOriginalStart;
        private int mSelectionEnd;
        private int mSelectionStart;
        private final android.widget.TextView mTextView;

        SelectionTracker(android.widget.TextView textView) {
            this.mTextView = (android.widget.TextView) java.util.Objects.requireNonNull(textView);
            this.mLogger = new android.widget.SelectionActionModeHelper.SelectionMetricsLogger(textView);
        }

        public void onOriginalSelection(java.lang.CharSequence charSequence, int i, int i2, boolean z) {
            this.mDelayedLogAbandon.flush();
            this.mSelectionStart = i;
            this.mOriginalStart = i;
            this.mSelectionEnd = i2;
            this.mOriginalEnd = i2;
            this.mAllowReset = false;
            maybeInvalidateLogger();
            this.mLogger.logSelectionStarted(this.mTextView.getTextClassificationSession(), this.mTextView.getTextClassificationContext(), charSequence, i, z ? 2 : 1);
        }

        public void onSmartSelection(android.widget.SelectionActionModeHelper.SelectionResult selectionResult) {
            onClassifiedSelection(selectionResult);
            this.mTextView.notifyContentCaptureTextChanged();
            this.mLogger.logSelectionModified(selectionResult.mStart, selectionResult.mEnd, selectionResult.mClassification, selectionResult.mSelection);
        }

        public void onLinkSelected(android.widget.SelectionActionModeHelper.SelectionResult selectionResult) {
            onClassifiedSelection(selectionResult);
        }

        private void onClassifiedSelection(android.widget.SelectionActionModeHelper.SelectionResult selectionResult) {
            if (isSelectionStarted()) {
                this.mSelectionStart = selectionResult.mStart;
                this.mSelectionEnd = selectionResult.mEnd;
                this.mAllowReset = (this.mSelectionStart == this.mOriginalStart && this.mSelectionEnd == this.mOriginalEnd) ? false : true;
            }
        }

        public void onSelectionUpdated(int i, int i2, android.view.textclassifier.TextClassification textClassification) {
            if (isSelectionStarted()) {
                this.mSelectionStart = i;
                this.mSelectionEnd = i2;
                this.mAllowReset = false;
                this.mTextView.notifyContentCaptureTextChanged();
                this.mLogger.logSelectionModified(i, i2, textClassification, null);
            }
        }

        public void onSelectionDestroyed() {
            this.mAllowReset = false;
            this.mTextView.notifyContentCaptureTextChanged();
            this.mDelayedLogAbandon.schedule(100);
        }

        public void onSelectionAction(int i, int i2, int i3, java.lang.String str, android.view.textclassifier.TextClassification textClassification) {
            if (isSelectionStarted()) {
                this.mAllowReset = false;
                this.mLogger.logSelectionAction(i, i2, i3, str, textClassification);
            }
        }

        public boolean resetSelection(int i, android.widget.Editor editor) {
            android.widget.TextView textView = editor.getTextView();
            if (!isSelectionStarted() || !this.mAllowReset || i < this.mSelectionStart || i > this.mSelectionEnd || !(android.widget.SelectionActionModeHelper.getText(textView) instanceof android.text.Spannable)) {
                return false;
            }
            this.mAllowReset = false;
            boolean selectCurrentWord = editor.selectCurrentWord();
            if (selectCurrentWord) {
                int[] sortSelectionIndicesFromTextView = android.widget.SelectionActionModeHelper.sortSelectionIndicesFromTextView(textView);
                this.mSelectionStart = sortSelectionIndicesFromTextView[0];
                this.mSelectionEnd = sortSelectionIndicesFromTextView[1];
                this.mLogger.logSelectionAction(sortSelectionIndicesFromTextView[0], sortSelectionIndicesFromTextView[1], 201, null, null);
            }
            return selectCurrentWord;
        }

        public void onTextChanged(int i, int i2, android.view.textclassifier.TextClassification textClassification) {
            if (isSelectionStarted() && i == this.mSelectionStart && i2 == this.mSelectionEnd) {
                onSelectionAction(i, i2, 100, null, textClassification);
            }
        }

        private void maybeInvalidateLogger() {
            if (this.mLogger.isEditTextLogger() != this.mTextView.isTextEditable()) {
                this.mLogger = new android.widget.SelectionActionModeHelper.SelectionMetricsLogger(this.mTextView);
            }
        }

        private boolean isSelectionStarted() {
            return this.mSelectionStart >= 0 && this.mSelectionEnd >= 0 && this.mSelectionStart != this.mSelectionEnd;
        }

        private final class LogAbandonRunnable implements java.lang.Runnable {
            private boolean mIsPending;

            private LogAbandonRunnable() {
            }

            void schedule(int i) {
                if (this.mIsPending) {
                    android.util.Log.e(android.widget.SelectionActionModeHelper.LOG_TAG, "Force flushing abandon due to new scheduling request");
                    flush();
                }
                this.mIsPending = true;
                android.widget.SelectionActionModeHelper.SelectionTracker.this.mTextView.postDelayed(this, i);
            }

            void flush() {
                android.widget.SelectionActionModeHelper.SelectionTracker.this.mTextView.removeCallbacks(this);
                run();
            }

            @Override // java.lang.Runnable
            public void run() {
                if (this.mIsPending) {
                    android.widget.SelectionActionModeHelper.SelectionTracker.this.mLogger.logSelectionAction(android.widget.SelectionActionModeHelper.SelectionTracker.this.mSelectionStart, android.widget.SelectionActionModeHelper.SelectionTracker.this.mSelectionEnd, 107, null, null);
                    android.widget.SelectionActionModeHelper.SelectionTracker selectionTracker = android.widget.SelectionActionModeHelper.SelectionTracker.this;
                    android.widget.SelectionActionModeHelper.SelectionTracker.this.mSelectionEnd = -1;
                    selectionTracker.mSelectionStart = -1;
                    android.widget.SelectionActionModeHelper.SelectionTracker.this.mLogger.endTextClassificationSession();
                    this.mIsPending = false;
                }
            }
        }
    }

    private static final class SelectionMetricsLogger {
        private static final java.lang.String LOG_TAG = "SelectionMetricsLogger";
        private static final java.util.regex.Pattern PATTERN_WHITESPACE = java.util.regex.Pattern.compile("\\s+");
        private android.view.textclassifier.TextClassificationContext mClassificationContext;
        private android.view.textclassifier.TextClassifier mClassificationSession;
        private final boolean mEditTextLogger;
        private int mStartIndex;
        private java.lang.String mText;
        private final java.text.BreakIterator mTokenIterator;
        private android.view.textclassifier.TextClassifierEvent mTranslateClickEvent;
        private android.view.textclassifier.TextClassifierEvent mTranslateViewEvent;

        SelectionMetricsLogger(android.widget.TextView textView) {
            java.util.Objects.requireNonNull(textView);
            this.mEditTextLogger = textView.isTextEditable();
            this.mTokenIterator = java.text.BreakIterator.getWordInstance(textView.getTextLocale());
        }

        public void logSelectionStarted(android.view.textclassifier.TextClassifier textClassifier, android.view.textclassifier.TextClassificationContext textClassificationContext, java.lang.CharSequence charSequence, int i, int i2) {
            try {
                java.util.Objects.requireNonNull(charSequence);
                com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, charSequence.length(), android.graphics.FontListParser.ATTR_INDEX);
                if (this.mText == null || !this.mText.contentEquals(charSequence)) {
                    this.mText = charSequence.toString();
                }
                this.mTokenIterator.setText(this.mText);
                this.mStartIndex = i;
                this.mClassificationSession = textClassifier;
                this.mClassificationContext = textClassificationContext;
                if (hasActiveClassificationSession()) {
                    this.mClassificationSession.onSelectionEvent(android.view.textclassifier.SelectionEvent.createSelectionStartedEvent(i2, 0));
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(LOG_TAG, "" + e.getMessage(), e);
            }
        }

        public void logSelectionModified(int i, int i2, android.view.textclassifier.TextClassification textClassification, android.view.textclassifier.TextSelection textSelection) {
            try {
                if (hasActiveClassificationSession()) {
                    com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, this.mText.length(), "start");
                    com.android.internal.util.Preconditions.checkArgumentInRange(i2, i, this.mText.length(), "end");
                    int[] wordDelta = getWordDelta(i, i2);
                    if (textSelection != null) {
                        this.mClassificationSession.onSelectionEvent(android.view.textclassifier.SelectionEvent.createSelectionModifiedEvent(wordDelta[0], wordDelta[1], textSelection));
                    } else if (textClassification != null) {
                        this.mClassificationSession.onSelectionEvent(android.view.textclassifier.SelectionEvent.createSelectionModifiedEvent(wordDelta[0], wordDelta[1], textClassification));
                    } else {
                        this.mClassificationSession.onSelectionEvent(android.view.textclassifier.SelectionEvent.createSelectionModifiedEvent(wordDelta[0], wordDelta[1]));
                    }
                    maybeGenerateTranslateViewEvent(textClassification);
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(LOG_TAG, "" + e.getMessage(), e);
            }
        }

        public void logSelectionAction(int i, int i2, int i3, java.lang.String str, android.view.textclassifier.TextClassification textClassification) {
            try {
                if (hasActiveClassificationSession()) {
                    com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, this.mText.length(), "start");
                    com.android.internal.util.Preconditions.checkArgumentInRange(i2, i, this.mText.length(), "end");
                    int[] wordDelta = getWordDelta(i, i2);
                    if (textClassification != null) {
                        this.mClassificationSession.onSelectionEvent(android.view.textclassifier.SelectionEvent.createSelectionActionEvent(wordDelta[0], wordDelta[1], i3, textClassification));
                    } else {
                        this.mClassificationSession.onSelectionEvent(android.view.textclassifier.SelectionEvent.createSelectionActionEvent(wordDelta[0], wordDelta[1], i3));
                    }
                    maybeGenerateTranslateClickEvent(textClassification, str);
                    if (android.view.textclassifier.SelectionEvent.isTerminal(i3)) {
                        endTextClassificationSession();
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(LOG_TAG, "" + e.getMessage(), e);
            }
        }

        public boolean isEditTextLogger() {
            return this.mEditTextLogger;
        }

        public void endTextClassificationSession() {
            if (hasActiveClassificationSession()) {
                maybeReportTranslateEvents();
                this.mClassificationSession.destroy();
            }
        }

        private boolean hasActiveClassificationSession() {
            return (this.mClassificationSession == null || this.mClassificationSession.isDestroyed()) ? false : true;
        }

        private int[] getWordDelta(int i, int i2) {
            int[] iArr = new int[2];
            if (i == this.mStartIndex) {
                iArr[0] = 0;
            } else if (i < this.mStartIndex) {
                iArr[0] = -countWordsForward(i);
            } else {
                iArr[0] = countWordsBackward(i);
                if (!this.mTokenIterator.isBoundary(i) && !isWhitespace(this.mTokenIterator.preceding(i), this.mTokenIterator.following(i))) {
                    iArr[0] = iArr[0] - 1;
                }
            }
            if (i2 == this.mStartIndex) {
                iArr[1] = 0;
            } else if (i2 < this.mStartIndex) {
                iArr[1] = -countWordsForward(i2);
            } else {
                iArr[1] = countWordsBackward(i2);
            }
            return iArr;
        }

        private int countWordsBackward(int i) {
            int i2 = 0;
            com.android.internal.util.Preconditions.checkArgument(i >= this.mStartIndex);
            while (i > this.mStartIndex) {
                int preceding = this.mTokenIterator.preceding(i);
                if (!isWhitespace(preceding, i)) {
                    i2++;
                }
                i = preceding;
            }
            return i2;
        }

        private int countWordsForward(int i) {
            int i2 = 0;
            com.android.internal.util.Preconditions.checkArgument(i <= this.mStartIndex);
            while (i < this.mStartIndex) {
                int following = this.mTokenIterator.following(i);
                if (!isWhitespace(i, following)) {
                    i2++;
                }
                i = following;
            }
            return i2;
        }

        private boolean isWhitespace(int i, int i2) {
            return PATTERN_WHITESPACE.matcher(this.mText.substring(i, i2)).matches();
        }

        private void maybeGenerateTranslateViewEvent(android.view.textclassifier.TextClassification textClassification) {
            if (textClassification != null) {
                android.view.textclassifier.TextClassifierEvent generateTranslateEvent = generateTranslateEvent(6, textClassification, this.mClassificationContext, null);
                if (generateTranslateEvent == null) {
                    generateTranslateEvent = this.mTranslateViewEvent;
                }
                this.mTranslateViewEvent = generateTranslateEvent;
            }
        }

        private void maybeGenerateTranslateClickEvent(android.view.textclassifier.TextClassification textClassification, java.lang.String str) {
            if (textClassification != null) {
                this.mTranslateClickEvent = generateTranslateEvent(13, textClassification, this.mClassificationContext, str);
            }
        }

        private void maybeReportTranslateEvents() {
            if (this.mTranslateViewEvent != null) {
                this.mClassificationSession.onTextClassifierEvent(this.mTranslateViewEvent);
                this.mTranslateViewEvent = null;
            }
            if (this.mTranslateClickEvent != null) {
                this.mClassificationSession.onTextClassifierEvent(this.mTranslateClickEvent);
                this.mTranslateClickEvent = null;
            }
        }

        private static android.view.textclassifier.TextClassifierEvent generateTranslateEvent(int i, android.view.textclassifier.TextClassification textClassification, android.view.textclassifier.TextClassificationContext textClassificationContext, java.lang.String str) {
            android.app.RemoteAction findTranslateAction = android.view.textclassifier.ExtrasUtils.findTranslateAction(textClassification);
            if (findTranslateAction == null) {
                return null;
            }
            if (i == 13 && !findTranslateAction.getTitle().toString().equals(str)) {
                return null;
            }
            android.os.Bundle foreignLanguageExtra = android.view.textclassifier.ExtrasUtils.getForeignLanguageExtra(textClassification);
            android.view.textclassifier.ExtrasUtils.getEntityType(foreignLanguageExtra);
            return new android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent.Builder(i).setEventContext(textClassificationContext).setResultId(textClassification.getId()).setScores(android.view.textclassifier.ExtrasUtils.getScore(foreignLanguageExtra)).setActionIndices(textClassification.getActions().indexOf(findTranslateAction)).setModelName(android.view.textclassifier.ExtrasUtils.getModelName(foreignLanguageExtra)).build();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TextClassificationAsyncTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, android.widget.SelectionActionModeHelper.SelectionResult> {
        private final java.lang.String mOriginalText;
        private final java.util.function.Consumer<android.widget.SelectionActionModeHelper.SelectionResult> mSelectionResultCallback;
        private final java.util.function.Supplier<android.widget.SelectionActionModeHelper.SelectionResult> mSelectionResultSupplier;
        private final android.widget.TextView mTextView;
        private final int mTimeOutDuration;
        private final java.util.function.Supplier<android.widget.SelectionActionModeHelper.SelectionResult> mTimeOutResultSupplier;

        TextClassificationAsyncTask(android.widget.TextView textView, int i, java.util.function.Supplier<android.widget.SelectionActionModeHelper.SelectionResult> supplier, java.util.function.Consumer<android.widget.SelectionActionModeHelper.SelectionResult> consumer, java.util.function.Supplier<android.widget.SelectionActionModeHelper.SelectionResult> supplier2) {
            super(textView != null ? textView.getHandler() : null);
            this.mTextView = (android.widget.TextView) java.util.Objects.requireNonNull(textView);
            this.mTimeOutDuration = i;
            this.mSelectionResultSupplier = (java.util.function.Supplier) java.util.Objects.requireNonNull(supplier);
            this.mSelectionResultCallback = (java.util.function.Consumer) java.util.Objects.requireNonNull(consumer);
            this.mTimeOutResultSupplier = (java.util.function.Supplier) java.util.Objects.requireNonNull(supplier2);
            this.mOriginalText = android.widget.SelectionActionModeHelper.getText(this.mTextView).toString();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public android.widget.SelectionActionModeHelper.SelectionResult doInBackground(java.lang.Void... voidArr) {
            android.widget.SelectionActionModeHelper.SelectionResult selectionResult;
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.widget.SelectionActionModeHelper$TextClassificationAsyncTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.SelectionActionModeHelper.TextClassificationAsyncTask.this.onTimeOut();
                }
            };
            this.mTextView.postDelayed(runnable, this.mTimeOutDuration);
            try {
                selectionResult = this.mSelectionResultSupplier.get();
            } catch (java.lang.IllegalStateException e) {
                android.util.Log.w(android.widget.SelectionActionModeHelper.LOG_TAG, "TextClassificationAsyncTask failed.", e);
                selectionResult = null;
            }
            this.mTextView.removeCallbacks(runnable);
            return selectionResult;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(android.widget.SelectionActionModeHelper.SelectionResult selectionResult) {
            if (!android.text.TextUtils.equals(this.mOriginalText, android.widget.SelectionActionModeHelper.getText(this.mTextView))) {
                selectionResult = null;
            }
            this.mSelectionResultCallback.accept(selectionResult);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onTimeOut() {
            android.util.Log.d(android.widget.SelectionActionModeHelper.LOG_TAG, "Timeout in TextClassificationAsyncTask");
            if (getStatus() == android.os.AsyncTask.Status.RUNNING) {
                onPostExecute(this.mTimeOutResultSupplier.get());
            }
            cancel(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TextClassificationHelper {
        private static final int TRIM_DELTA_UPPER_BOUND = 240;
        private final android.content.Context mContext;
        private android.os.LocaleList mDefaultLocales;
        private boolean mInitialized;
        private android.os.LocaleList mLastClassificationLocales;
        private android.widget.SelectionActionModeHelper.SelectionResult mLastClassificationResult;
        private int mLastClassificationSelectionEnd;
        private int mLastClassificationSelectionStart;
        private java.lang.CharSequence mLastClassificationText;
        private int mRelativeEnd;
        private int mRelativeStart;
        private int mSelectionEnd;
        private int mSelectionStart;
        private java.lang.String mText;
        private java.util.function.Supplier<android.view.textclassifier.TextClassifier> mTextClassifier;
        private int mTrimStart;
        private java.lang.CharSequence mTrimmedText;
        private final android.view.ViewConfiguration mViewConfiguration;

        TextClassificationHelper(android.content.Context context, java.util.function.Supplier<android.view.textclassifier.TextClassifier> supplier, java.lang.CharSequence charSequence, int i, int i2, android.os.LocaleList localeList) {
            init(supplier, charSequence, i, i2, localeList);
            this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
            this.mViewConfiguration = android.view.ViewConfiguration.get(this.mContext);
        }

        public void init(java.util.function.Supplier<android.view.textclassifier.TextClassifier> supplier, java.lang.CharSequence charSequence, int i, int i2, android.os.LocaleList localeList) {
            this.mTextClassifier = (java.util.function.Supplier) java.util.Objects.requireNonNull(supplier);
            this.mText = ((java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence)).toString();
            this.mLastClassificationText = null;
            com.android.internal.util.Preconditions.checkArgument(i2 > i);
            this.mSelectionStart = i;
            this.mSelectionEnd = i2;
            this.mDefaultLocales = localeList;
        }

        public android.widget.SelectionActionModeHelper.SelectionResult classifyText() {
            this.mInitialized = true;
            return performClassification(null);
        }

        public android.widget.SelectionActionModeHelper.SelectionResult suggestSelection() {
            android.view.textclassifier.TextSelection suggestSelection;
            this.mInitialized = true;
            trimText();
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                suggestSelection = this.mTextClassifier.get().suggestSelection(new android.view.textclassifier.TextSelection.Request.Builder(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd).setDefaultLocales(this.mDefaultLocales).setDarkLaunchAllowed(true).setIncludeTextClassification(true).build());
            } else {
                suggestSelection = this.mTextClassifier.get().suggestSelection(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd, this.mDefaultLocales);
            }
            if (!isDarkLaunchEnabled()) {
                this.mSelectionStart = java.lang.Math.max(0, suggestSelection.getSelectionStartIndex() + this.mTrimStart);
                this.mSelectionEnd = java.lang.Math.min(this.mText.length(), suggestSelection.getSelectionEndIndex() + this.mTrimStart);
            }
            return performClassification(suggestSelection);
        }

        public android.widget.SelectionActionModeHelper.SelectionResult getOriginalSelection() {
            return new android.widget.SelectionActionModeHelper.SelectionResult(this.mSelectionStart, this.mSelectionEnd, null, null);
        }

        public int getTimeoutDuration() {
            if (this.mInitialized) {
                return this.mViewConfiguration.getSmartSelectionInitializedTimeout();
            }
            return this.mViewConfiguration.getSmartSelectionInitializingTimeout();
        }

        private boolean isDarkLaunchEnabled() {
            return android.view.textclassifier.TextClassificationManager.getSettings(this.mContext).isModelDarkLaunchEnabled();
        }

        private android.widget.SelectionActionModeHelper.SelectionResult performClassification(android.view.textclassifier.TextSelection textSelection) {
            android.view.textclassifier.TextClassification classifyText;
            if (!java.util.Objects.equals(this.mText, this.mLastClassificationText) || this.mSelectionStart != this.mLastClassificationSelectionStart || this.mSelectionEnd != this.mLastClassificationSelectionEnd || !java.util.Objects.equals(this.mDefaultLocales, this.mLastClassificationLocales)) {
                this.mLastClassificationText = this.mText;
                this.mLastClassificationSelectionStart = this.mSelectionStart;
                this.mLastClassificationSelectionEnd = this.mSelectionEnd;
                this.mLastClassificationLocales = this.mDefaultLocales;
                trimText();
                if (android.text.util.Linkify.containsUnsupportedCharacters(this.mText)) {
                    android.util.EventLog.writeEvent(1397638484, "116321860", -1, "");
                    classifyText = android.view.textclassifier.TextClassification.EMPTY;
                } else if (textSelection != null && textSelection.getTextClassification() != null) {
                    classifyText = textSelection.getTextClassification();
                } else if (this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                    classifyText = this.mTextClassifier.get().classifyText(new android.view.textclassifier.TextClassification.Request.Builder(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd).setDefaultLocales(this.mDefaultLocales).build());
                } else {
                    classifyText = this.mTextClassifier.get().classifyText(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd, this.mDefaultLocales);
                }
                this.mLastClassificationResult = new android.widget.SelectionActionModeHelper.SelectionResult(this.mSelectionStart, this.mSelectionEnd, classifyText, textSelection);
            }
            return this.mLastClassificationResult;
        }

        private void trimText() {
            int min = java.lang.Math.min(android.view.textclassifier.TextClassificationManager.getSettings(this.mContext).getSmartSelectionTrimDelta(), 240);
            this.mTrimStart = java.lang.Math.max(0, this.mSelectionStart - min);
            this.mTrimmedText = this.mText.subSequence(this.mTrimStart, java.lang.Math.min(this.mText.length(), this.mSelectionEnd + min));
            this.mRelativeStart = this.mSelectionStart - this.mTrimStart;
            this.mRelativeEnd = this.mSelectionEnd - this.mTrimStart;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SelectionResult {
        private final android.view.textclassifier.TextClassification mClassification;
        private final int mEnd;
        private final android.view.textclassifier.TextSelection mSelection;
        private final int mStart;

        SelectionResult(int i, int i2, android.view.textclassifier.TextClassification textClassification, android.view.textclassifier.TextSelection textSelection) {
            int[] sortSelectionIndices = android.widget.SelectionActionModeHelper.sortSelectionIndices(i, i2);
            this.mStart = sortSelectionIndices[0];
            this.mEnd = sortSelectionIndices[1];
            this.mClassification = textClassification;
            this.mSelection = textSelection;
        }
    }

    private static int getActionType(int i) {
        switch (i) {
            case 16908319:
                return 200;
            case 16908320:
                return 103;
            case 16908321:
                return 101;
            case 16908322:
            case 16908337:
                return 102;
            case 16908341:
                return 104;
            case 16908353:
                return 105;
            default:
                return 108;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.CharSequence getText(android.widget.TextView textView) {
        java.lang.CharSequence text = textView.getText();
        if (text != null) {
            return text;
        }
        return "";
    }
}
