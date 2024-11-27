package android.app;

/* loaded from: classes.dex */
abstract class ActivityTransitionCoordinator extends android.os.ResultReceiver {
    protected static final java.lang.String KEY_ELEVATION = "shared_element:elevation";
    protected static final java.lang.String KEY_IMAGE_MATRIX = "shared_element:imageMatrix";
    static final java.lang.String KEY_REMOTE_RECEIVER = "android:remoteReceiver";
    protected static final java.lang.String KEY_SCALE_TYPE = "shared_element:scaleType";
    protected static final java.lang.String KEY_SCREEN_BOTTOM = "shared_element:screenBottom";
    protected static final java.lang.String KEY_SCREEN_LEFT = "shared_element:screenLeft";
    protected static final java.lang.String KEY_SCREEN_RIGHT = "shared_element:screenRight";
    protected static final java.lang.String KEY_SCREEN_TOP = "shared_element:screenTop";
    protected static final java.lang.String KEY_SNAPSHOT = "shared_element:bitmap";
    protected static final java.lang.String KEY_TRANSLATION_Z = "shared_element:translationZ";
    public static final int MSG_ALLOW_RETURN_TRANSITION = 108;
    public static final int MSG_CANCEL = 106;
    public static final int MSG_EXIT_TRANSITION_COMPLETE = 104;
    public static final int MSG_HIDE_SHARED_ELEMENTS = 101;
    public static final int MSG_SET_REMOTE_RECEIVER = 100;
    public static final int MSG_SHARED_ELEMENT_DESTINATION = 107;
    public static final int MSG_START_EXIT_TRANSITION = 105;
    public static final int MSG_TAKE_SHARED_ELEMENTS = 103;
    protected static final android.widget.ImageView.ScaleType[] SCALE_TYPE_VALUES = android.widget.ImageView.ScaleType.values();
    private static final java.lang.String TAG = "ActivityTransitionCoordinator";
    protected final java.util.ArrayList<java.lang.String> mAllSharedElementNames;
    private boolean mBackgroundAnimatorComplete;
    private final android.app.ActivityTransitionCoordinator.FixedEpicenterCallback mEpicenterCallback;
    private java.util.ArrayList<android.app.ActivityTransitionCoordinator.GhostViewListeners> mGhostViewListeners;
    protected final boolean mIsReturning;
    private boolean mIsStartingTransition;
    protected android.app.SharedElementCallback mListener;
    private android.util.ArrayMap<android.view.View, java.lang.Float> mOriginalAlphas;
    private java.lang.Runnable mPendingTransition;
    protected android.os.ResultReceiver mResultReceiver;
    protected final java.util.ArrayList<java.lang.String> mSharedElementNames;
    private java.util.ArrayList<android.graphics.Matrix> mSharedElementParentMatrices;
    private boolean mSharedElementTransitionComplete;
    protected final java.util.ArrayList<android.view.View> mSharedElements;
    private java.util.ArrayList<android.view.View> mStrippedTransitioningViews;
    protected java.util.ArrayList<android.view.View> mTransitioningViews;
    private boolean mViewsTransitionComplete;
    private android.view.Window mWindow;

    protected abstract android.transition.Transition getViewsTransition();

    public ActivityTransitionCoordinator(android.view.Window window, java.util.ArrayList<java.lang.String> arrayList, android.app.SharedElementCallback sharedElementCallback, boolean z) {
        super(new android.os.Handler());
        this.mSharedElements = new java.util.ArrayList<>();
        this.mSharedElementNames = new java.util.ArrayList<>();
        this.mTransitioningViews = new java.util.ArrayList<>();
        this.mEpicenterCallback = new android.app.ActivityTransitionCoordinator.FixedEpicenterCallback();
        this.mGhostViewListeners = new java.util.ArrayList<>();
        this.mOriginalAlphas = new android.util.ArrayMap<>();
        this.mStrippedTransitioningViews = new java.util.ArrayList<>();
        this.mWindow = window;
        this.mListener = sharedElementCallback;
        this.mAllSharedElementNames = arrayList;
        this.mIsReturning = z;
    }

    protected void viewsReady(android.util.ArrayMap<java.lang.String, android.view.View> arrayMap) {
        arrayMap.retainAll(this.mAllSharedElementNames);
        if (this.mListener != null) {
            this.mListener.onMapSharedElements(this.mAllSharedElementNames, arrayMap);
        }
        setSharedElements(arrayMap);
        if (getViewsTransition() != null && this.mTransitioningViews != null) {
            android.view.ViewGroup decor = getDecor();
            if (decor != null) {
                decor.captureTransitioningViews(this.mTransitioningViews);
            }
            this.mTransitioningViews.removeAll(this.mSharedElements);
        }
        setEpicenter();
    }

    private void setSharedElements(android.util.ArrayMap<java.lang.String, android.view.View> arrayMap) {
        boolean z = true;
        while (!arrayMap.isEmpty()) {
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                android.view.View valueAt = arrayMap.valueAt(size);
                java.lang.String keyAt = arrayMap.keyAt(size);
                if (z && (valueAt == null || !valueAt.isAttachedToWindow() || keyAt == null)) {
                    arrayMap.removeAt(size);
                } else if (!isNested(valueAt, arrayMap)) {
                    this.mSharedElementNames.add(keyAt);
                    this.mSharedElements.add(valueAt);
                    arrayMap.removeAt(size);
                }
            }
            z = false;
        }
    }

    private static boolean isNested(android.view.View view, android.util.ArrayMap<java.lang.String, android.view.View> arrayMap) {
        java.lang.Object parent = view.getParent();
        while (parent instanceof android.view.View) {
            android.view.View view2 = (android.view.View) parent;
            if (arrayMap.containsValue(view2)) {
                return true;
            }
            parent = view2.getParent();
        }
        return false;
    }

    protected void stripOffscreenViews() {
        if (this.mTransitioningViews == null) {
            return;
        }
        android.graphics.Rect rect = new android.graphics.Rect();
        for (int size = this.mTransitioningViews.size() - 1; size >= 0; size--) {
            android.view.View view = this.mTransitioningViews.get(size);
            if (!view.getGlobalVisibleRect(rect)) {
                this.mTransitioningViews.remove(size);
                this.mStrippedTransitioningViews.add(view);
            }
        }
    }

    protected android.view.Window getWindow() {
        return this.mWindow;
    }

    public android.view.ViewGroup getDecor() {
        if (this.mWindow == null) {
            return null;
        }
        return (android.view.ViewGroup) this.mWindow.getDecorView();
    }

    protected void setEpicenter() {
        android.view.View view;
        int indexOf;
        if (!this.mAllSharedElementNames.isEmpty() && !this.mSharedElementNames.isEmpty() && (indexOf = this.mSharedElementNames.indexOf(this.mAllSharedElementNames.get(0))) >= 0) {
            view = this.mSharedElements.get(indexOf);
        } else {
            view = null;
        }
        setEpicenter(view);
    }

    private void setEpicenter(android.view.View view) {
        if (view == null) {
            this.mEpicenterCallback.setEpicenter(null);
            return;
        }
        android.graphics.Rect rect = new android.graphics.Rect();
        view.getBoundsOnScreen(rect);
        this.mEpicenterCallback.setEpicenter(rect);
    }

    public java.util.ArrayList<java.lang.String> getAcceptedNames() {
        return this.mSharedElementNames;
    }

    public java.util.ArrayList<java.lang.String> getMappedNames() {
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>(this.mSharedElements.size());
        for (int i = 0; i < this.mSharedElements.size(); i++) {
            arrayList.add(this.mSharedElements.get(i).getTransitionName());
        }
        return arrayList;
    }

    public java.util.ArrayList<android.view.View> copyMappedViews() {
        return new java.util.ArrayList<>(this.mSharedElements);
    }

    protected android.transition.Transition setTargets(android.transition.Transition transition, boolean z) {
        if (transition != null) {
            if (z && (this.mTransitioningViews == null || this.mTransitioningViews.isEmpty())) {
                return null;
            }
            android.transition.TransitionSet transitionSet = new android.transition.TransitionSet();
            if (this.mTransitioningViews != null) {
                for (int size = this.mTransitioningViews.size() - 1; size >= 0; size--) {
                    android.view.View view = this.mTransitioningViews.get(size);
                    if (z) {
                        transitionSet.addTarget(view);
                    } else {
                        transitionSet.excludeTarget(view, true);
                    }
                }
            }
            if (this.mStrippedTransitioningViews != null) {
                for (int size2 = this.mStrippedTransitioningViews.size() - 1; size2 >= 0; size2--) {
                    transitionSet.excludeTarget(this.mStrippedTransitioningViews.get(size2), true);
                }
            }
            transitionSet.addTransition(transition);
            if (!z && this.mTransitioningViews != null && !this.mTransitioningViews.isEmpty()) {
                return new android.transition.TransitionSet().addTransition(transitionSet);
            }
            return transitionSet;
        }
        return null;
    }

    protected android.transition.Transition configureTransition(android.transition.Transition transition, boolean z) {
        if (transition != null) {
            android.transition.Transition mo4806clone = transition.mo4806clone();
            mo4806clone.setEpicenterCallback(this.mEpicenterCallback);
            transition = setTargets(mo4806clone, z);
        }
        noLayoutSuppressionForVisibilityTransitions(transition);
        return transition;
    }

    protected static void removeExcludedViews(android.transition.Transition transition, java.util.ArrayList<android.view.View> arrayList) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        findIncludedViews(transition, arrayList, arraySet);
        arrayList.clear();
        arrayList.addAll(arraySet);
    }

    private static void findIncludedViews(android.transition.Transition transition, java.util.ArrayList<android.view.View> arrayList, android.util.ArraySet<android.view.View> arraySet) {
        int i = 0;
        if (transition instanceof android.transition.TransitionSet) {
            android.transition.TransitionSet transitionSet = (android.transition.TransitionSet) transition;
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.view.View view = arrayList.get(i2);
                if (transition.isValidTarget(view)) {
                    arrayList2.add(view);
                }
            }
            int transitionCount = transitionSet.getTransitionCount();
            while (i < transitionCount) {
                findIncludedViews(transitionSet.getTransitionAt(i), arrayList2, arraySet);
                i++;
            }
            return;
        }
        int size2 = arrayList.size();
        while (i < size2) {
            android.view.View view2 = arrayList.get(i);
            if (transition.isValidTarget(view2)) {
                arraySet.add(view2);
            }
            i++;
        }
    }

    protected static android.transition.Transition mergeTransitions(android.transition.Transition transition, android.transition.Transition transition2) {
        if (transition == null) {
            return transition2;
        }
        if (transition2 == null) {
            return transition;
        }
        android.transition.TransitionSet transitionSet = new android.transition.TransitionSet();
        transitionSet.addTransition(transition);
        transitionSet.addTransition(transition2);
        return transitionSet;
    }

    protected android.util.ArrayMap<java.lang.String, android.view.View> mapSharedElements(java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<android.view.View> arrayList2) {
        android.util.ArrayMap<java.lang.String, android.view.View> arrayMap = new android.util.ArrayMap<>();
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                arrayMap.put(arrayList.get(i), arrayList2.get(i));
            }
        } else {
            android.view.ViewGroup decor = getDecor();
            if (decor != null) {
                decor.findNamedViews(arrayMap);
            }
        }
        return arrayMap;
    }

    protected void setResultReceiver(android.os.ResultReceiver resultReceiver) {
        this.mResultReceiver = resultReceiver;
    }

    private void setSharedElementState(android.view.View view, java.lang.String str, android.os.Bundle bundle, android.graphics.Matrix matrix, android.graphics.RectF rectF, int[] iArr) {
        float f;
        float f2;
        float f3;
        float f4;
        int i;
        android.os.Bundle bundle2 = bundle.getBundle(str);
        if (bundle2 == null) {
            return;
        }
        if ((view instanceof android.widget.ImageView) && (i = bundle2.getInt(KEY_SCALE_TYPE, -1)) >= 0) {
            android.widget.ImageView imageView = (android.widget.ImageView) view;
            android.widget.ImageView.ScaleType scaleType = SCALE_TYPE_VALUES[i];
            imageView.setScaleType(scaleType);
            if (scaleType == android.widget.ImageView.ScaleType.MATRIX) {
                matrix.setValues(bundle2.getFloatArray(KEY_IMAGE_MATRIX));
                imageView.setImageMatrix(matrix);
            }
        }
        view.setTranslationZ(bundle2.getFloat(KEY_TRANSLATION_Z));
        view.setElevation(bundle2.getFloat(KEY_ELEVATION));
        float f5 = bundle2.getFloat(KEY_SCREEN_LEFT);
        float f6 = bundle2.getFloat(KEY_SCREEN_TOP);
        float f7 = bundle2.getFloat(KEY_SCREEN_RIGHT);
        float f8 = bundle2.getFloat(KEY_SCREEN_BOTTOM);
        if (iArr != null) {
            f3 = f5 - iArr[0];
            f = f6 - iArr[1];
            f2 = f7 - iArr[0];
            f4 = f8 - iArr[1];
        } else {
            getSharedElementParentMatrix(view, matrix);
            rectF.set(f5, f6, f7, f8);
            matrix.mapRect(rectF);
            float f9 = rectF.left;
            float f10 = rectF.top;
            view.getInverseMatrix().mapRect(rectF);
            float width = rectF.width();
            float height = rectF.height();
            view.setLeft(0);
            view.setTop(0);
            view.setRight(java.lang.Math.round(width));
            view.setBottom(java.lang.Math.round(height));
            rectF.set(0.0f, 0.0f, width, height);
            view.getMatrix().mapRect(rectF);
            float f11 = f9 - rectF.left;
            f = f10 - rectF.top;
            f2 = f11 + width;
            f3 = f11;
            f4 = f + height;
        }
        int round = java.lang.Math.round(f3);
        int round2 = java.lang.Math.round(f);
        int round3 = java.lang.Math.round(f2) - round;
        int round4 = java.lang.Math.round(f4) - round2;
        view.measure(android.view.View.MeasureSpec.makeMeasureSpec(round3, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(round4, 1073741824));
        view.layout(round, round2, round3 + round, round4 + round2);
    }

    private void setSharedElementMatrices() {
        int size = this.mSharedElements.size();
        if (size > 0) {
            this.mSharedElementParentMatrices = new java.util.ArrayList<>(size);
        }
        for (int i = 0; i < size; i++) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mSharedElements.get(i).getParent();
            android.graphics.Matrix matrix = new android.graphics.Matrix();
            if (viewGroup != null) {
                viewGroup.transformMatrixToLocal(matrix);
                matrix.postTranslate(viewGroup.getScrollX(), viewGroup.getScrollY());
            }
            this.mSharedElementParentMatrices.add(matrix);
        }
    }

    private void getSharedElementParentMatrix(android.view.View view, android.graphics.Matrix matrix) {
        int indexOf = this.mSharedElementParentMatrices == null ? -1 : this.mSharedElements.indexOf(view);
        if (indexOf < 0) {
            matrix.reset();
            android.view.ViewParent parent = view.getParent();
            if (parent instanceof android.view.ViewGroup) {
                ((android.view.ViewGroup) parent).transformMatrixToLocal(matrix);
                matrix.postTranslate(r2.getScrollX(), r2.getScrollY());
                return;
            }
            return;
        }
        matrix.set(this.mSharedElementParentMatrices.get(indexOf));
    }

    protected java.util.ArrayList<android.app.ActivityTransitionCoordinator.SharedElementOriginalState> setSharedElementState(android.os.Bundle bundle, java.util.ArrayList<android.view.View> arrayList) {
        java.util.ArrayList<android.app.ActivityTransitionCoordinator.SharedElementOriginalState> arrayList2 = new java.util.ArrayList<>();
        if (bundle != null) {
            android.graphics.Matrix matrix = new android.graphics.Matrix();
            android.graphics.RectF rectF = new android.graphics.RectF();
            int size = this.mSharedElements.size();
            for (int i = 0; i < size; i++) {
                android.view.View view = this.mSharedElements.get(i);
                java.lang.String str = this.mSharedElementNames.get(i);
                arrayList2.add(getOldSharedElementState(view, str, bundle));
                setSharedElementState(view, str, bundle, matrix, rectF, null);
            }
        }
        if (this.mListener != null) {
            this.mListener.onSharedElementStart(this.mSharedElementNames, this.mSharedElements, arrayList);
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: notifySharedElementEnd, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleSetSharedElementEnd$0(java.util.ArrayList<android.view.View> arrayList) {
        if (this.mListener != null) {
            this.mListener.onSharedElementEnd(this.mSharedElementNames, this.mSharedElements, arrayList);
        }
    }

    protected void scheduleSetSharedElementEnd(final java.util.ArrayList<android.view.View> arrayList) {
        android.view.ViewGroup decor = getDecor();
        if (decor != null) {
            com.android.internal.view.OneShotPreDrawListener.add(decor, new java.lang.Runnable() { // from class: android.app.ActivityTransitionCoordinator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.ActivityTransitionCoordinator.this.lambda$scheduleSetSharedElementEnd$0(arrayList);
                }
            });
        }
    }

    private static android.app.ActivityTransitionCoordinator.SharedElementOriginalState getOldSharedElementState(android.view.View view, java.lang.String str, android.os.Bundle bundle) {
        android.app.ActivityTransitionCoordinator.SharedElementOriginalState sharedElementOriginalState = new android.app.ActivityTransitionCoordinator.SharedElementOriginalState();
        sharedElementOriginalState.mLeft = view.getLeft();
        sharedElementOriginalState.mTop = view.getTop();
        sharedElementOriginalState.mRight = view.getRight();
        sharedElementOriginalState.mBottom = view.getBottom();
        sharedElementOriginalState.mMeasuredWidth = view.getMeasuredWidth();
        sharedElementOriginalState.mMeasuredHeight = view.getMeasuredHeight();
        sharedElementOriginalState.mTranslationZ = view.getTranslationZ();
        sharedElementOriginalState.mElevation = view.getElevation();
        if (!(view instanceof android.widget.ImageView)) {
            return sharedElementOriginalState;
        }
        android.os.Bundle bundle2 = bundle.getBundle(str);
        if (bundle2 == null) {
            return sharedElementOriginalState;
        }
        if (bundle2.getInt(KEY_SCALE_TYPE, -1) < 0) {
            return sharedElementOriginalState;
        }
        android.widget.ImageView imageView = (android.widget.ImageView) view;
        sharedElementOriginalState.mScaleType = imageView.getScaleType();
        if (sharedElementOriginalState.mScaleType == android.widget.ImageView.ScaleType.MATRIX) {
            sharedElementOriginalState.mMatrix = new android.graphics.Matrix(imageView.getImageMatrix());
        }
        return sharedElementOriginalState;
    }

    protected java.util.ArrayList<android.view.View> createSnapshots(android.os.Bundle bundle, java.util.Collection<java.lang.String> collection) {
        android.view.View view;
        int size = collection.size();
        java.util.ArrayList<android.view.View> arrayList = new java.util.ArrayList<>(size);
        if (size == 0) {
            return arrayList;
        }
        android.content.Context context = getWindow().getContext();
        int[] iArr = new int[2];
        android.view.ViewGroup decor = getDecor();
        if (decor != null) {
            decor.getLocationOnScreen(iArr);
        }
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        for (java.lang.String str : collection) {
            android.os.Bundle bundle2 = bundle.getBundle(str);
            android.view.View view2 = null;
            if (bundle2 != null) {
                android.os.Parcelable parcelable = bundle2.getParcelable(KEY_SNAPSHOT);
                if (parcelable != null && this.mListener != null) {
                    view = this.mListener.onCreateSnapshotView(context, parcelable);
                } else {
                    view = null;
                }
                if (view != null) {
                    setSharedElementState(view, str, bundle, matrix, null, iArr);
                }
                view2 = view;
            }
            arrayList.add(view2);
        }
        return arrayList;
    }

    protected static void setOriginalSharedElementState(java.util.ArrayList<android.view.View> arrayList, java.util.ArrayList<android.app.ActivityTransitionCoordinator.SharedElementOriginalState> arrayList2) {
        for (int i = 0; i < arrayList2.size(); i++) {
            android.view.View view = arrayList.get(i);
            android.app.ActivityTransitionCoordinator.SharedElementOriginalState sharedElementOriginalState = arrayList2.get(i);
            if ((view instanceof android.widget.ImageView) && sharedElementOriginalState.mScaleType != null) {
                android.widget.ImageView imageView = (android.widget.ImageView) view;
                imageView.setScaleType(sharedElementOriginalState.mScaleType);
                if (sharedElementOriginalState.mScaleType == android.widget.ImageView.ScaleType.MATRIX) {
                    imageView.setImageMatrix(sharedElementOriginalState.mMatrix);
                }
            }
            view.setElevation(sharedElementOriginalState.mElevation);
            view.setTranslationZ(sharedElementOriginalState.mTranslationZ);
            view.measure(android.view.View.MeasureSpec.makeMeasureSpec(sharedElementOriginalState.mMeasuredWidth, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(sharedElementOriginalState.mMeasuredHeight, 1073741824));
            view.layout(sharedElementOriginalState.mLeft, sharedElementOriginalState.mTop, sharedElementOriginalState.mRight, sharedElementOriginalState.mBottom);
        }
    }

    protected android.os.Bundle captureSharedElementState() {
        android.os.Bundle bundle = new android.os.Bundle();
        android.graphics.RectF rectF = new android.graphics.RectF();
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        for (int i = 0; i < this.mSharedElements.size(); i++) {
            captureSharedElementState(this.mSharedElements.get(i), this.mSharedElementNames.get(i), bundle, matrix, rectF);
        }
        return bundle;
    }

    protected void clearState() {
        this.mWindow = null;
        this.mSharedElements.clear();
        this.mTransitioningViews = null;
        this.mStrippedTransitioningViews = null;
        this.mOriginalAlphas.clear();
        this.mResultReceiver = null;
        this.mPendingTransition = null;
        this.mListener = null;
        this.mSharedElementParentMatrices = null;
    }

    protected long getFadeDuration() {
        return getWindow().getTransitionBackgroundFadeDuration();
    }

    protected void hideViews(java.util.ArrayList<android.view.View> arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            android.view.View view = arrayList.get(i);
            if (!this.mOriginalAlphas.containsKey(view)) {
                this.mOriginalAlphas.put(view, java.lang.Float.valueOf(view.getAlpha()));
            }
            view.setAlpha(0.0f);
        }
    }

    protected void showViews(java.util.ArrayList<android.view.View> arrayList, boolean z) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            showView(arrayList.get(i), z);
        }
    }

    private void showView(android.view.View view, boolean z) {
        java.lang.Float remove = this.mOriginalAlphas.remove(view);
        if (remove != null) {
            view.setAlpha(remove.floatValue());
        }
        if (z) {
            view.setTransitionAlpha(1.0f);
        }
    }

    protected void captureSharedElementState(android.view.View view, java.lang.String str, android.os.Bundle bundle, android.graphics.Matrix matrix, android.graphics.RectF rectF) {
        android.os.Parcelable parcelable;
        android.os.Bundle bundle2 = new android.os.Bundle();
        matrix.reset();
        view.transformMatrixToGlobal(matrix);
        rectF.set(0.0f, 0.0f, view.getWidth(), view.getHeight());
        matrix.mapRect(rectF);
        bundle2.putFloat(KEY_SCREEN_LEFT, rectF.left);
        bundle2.putFloat(KEY_SCREEN_RIGHT, rectF.right);
        bundle2.putFloat(KEY_SCREEN_TOP, rectF.top);
        bundle2.putFloat(KEY_SCREEN_BOTTOM, rectF.bottom);
        bundle2.putFloat(KEY_TRANSLATION_Z, view.getTranslationZ());
        bundle2.putFloat(KEY_ELEVATION, view.getElevation());
        if (this.mListener == null) {
            parcelable = null;
        } else {
            parcelable = this.mListener.onCaptureSharedElementSnapshot(view, matrix, rectF);
        }
        if (parcelable != null) {
            bundle2.putParcelable(KEY_SNAPSHOT, parcelable);
        }
        if (view instanceof android.widget.ImageView) {
            android.widget.ImageView imageView = (android.widget.ImageView) view;
            bundle2.putInt(KEY_SCALE_TYPE, scaleTypeToInt(imageView.getScaleType()));
            if (imageView.getScaleType() == android.widget.ImageView.ScaleType.MATRIX) {
                float[] fArr = new float[9];
                imageView.getImageMatrix().getValues(fArr);
                bundle2.putFloatArray(KEY_IMAGE_MATRIX, fArr);
            }
        }
        bundle.putBundle(str, bundle2);
    }

    protected void startTransition(java.lang.Runnable runnable) {
        if (this.mIsStartingTransition) {
            this.mPendingTransition = runnable;
        } else {
            this.mIsStartingTransition = true;
            runnable.run();
        }
    }

    protected void transitionStarted() {
        this.mIsStartingTransition = false;
    }

    protected boolean cancelPendingTransitions() {
        this.mPendingTransition = null;
        return this.mIsStartingTransition;
    }

    protected void moveSharedElementsToOverlay() {
        if (this.mWindow == null || !this.mWindow.getSharedElementsUseOverlay()) {
            return;
        }
        setSharedElementMatrices();
        int size = this.mSharedElements.size();
        android.view.ViewGroup decor = getDecor();
        if (decor != null) {
            boolean moveSharedElementWithParent = moveSharedElementWithParent();
            android.graphics.Matrix matrix = new android.graphics.Matrix();
            for (int i = 0; i < size; i++) {
                android.view.View view = this.mSharedElements.get(i);
                if (view.isAttachedToWindow()) {
                    matrix.reset();
                    this.mSharedElementParentMatrices.get(i).invert(matrix);
                    decor.transformMatrixToLocal(matrix);
                    android.view.GhostView.addGhost(view, decor, matrix);
                    android.view.ViewGroup viewGroup = (android.view.ViewGroup) view.getParent();
                    if (moveSharedElementWithParent && !isInTransitionGroup(viewGroup, decor)) {
                        android.app.ActivityTransitionCoordinator.GhostViewListeners ghostViewListeners = new android.app.ActivityTransitionCoordinator.GhostViewListeners(view, viewGroup, decor);
                        viewGroup.getViewTreeObserver().addOnPreDrawListener(ghostViewListeners);
                        viewGroup.addOnAttachStateChangeListener(ghostViewListeners);
                        this.mGhostViewListeners.add(ghostViewListeners);
                    }
                }
            }
        }
    }

    protected boolean moveSharedElementWithParent() {
        return true;
    }

    public static boolean isInTransitionGroup(android.view.ViewParent viewParent, android.view.ViewGroup viewGroup) {
        if (viewParent == viewGroup || !(viewParent instanceof android.view.ViewGroup)) {
            return false;
        }
        android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) viewParent;
        if (viewGroup2.isTransitionGroup()) {
            return true;
        }
        return isInTransitionGroup(viewGroup2.getParent(), viewGroup);
    }

    protected void moveSharedElementsFromOverlay() {
        android.view.ViewGroup decor;
        int size = this.mGhostViewListeners.size();
        for (int i = 0; i < size; i++) {
            this.mGhostViewListeners.get(i).removeListener();
        }
        this.mGhostViewListeners.clear();
        if (this.mWindow != null && this.mWindow.getSharedElementsUseOverlay() && (decor = getDecor()) != null) {
            decor.getOverlay();
            int size2 = this.mSharedElements.size();
            for (int i2 = 0; i2 < size2; i2++) {
                android.view.GhostView.removeGhost(this.mSharedElements.get(i2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: setGhostVisibility, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleGhostVisibilityChange$1(int i) {
        int size = this.mSharedElements.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.view.GhostView ghost = android.view.GhostView.getGhost(this.mSharedElements.get(i2));
            if (ghost != null) {
                ghost.setVisibility(i);
            }
        }
    }

    protected void scheduleGhostVisibilityChange(final int i) {
        android.view.ViewGroup decor = getDecor();
        if (decor != null) {
            com.android.internal.view.OneShotPreDrawListener.add(decor, new java.lang.Runnable() { // from class: android.app.ActivityTransitionCoordinator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.ActivityTransitionCoordinator.this.lambda$scheduleGhostVisibilityChange$1(i);
                }
            });
        }
    }

    protected boolean isViewsTransitionComplete() {
        return this.mViewsTransitionComplete;
    }

    protected void viewsTransitionComplete() {
        this.mViewsTransitionComplete = true;
        startInputWhenTransitionsComplete();
    }

    protected void backgroundAnimatorComplete() {
        this.mBackgroundAnimatorComplete = true;
    }

    protected void sharedElementTransitionComplete() {
        this.mSharedElementTransitionComplete = true;
        startInputWhenTransitionsComplete();
    }

    private void startInputWhenTransitionsComplete() {
        android.view.ViewRootImpl viewRootImpl;
        if (this.mViewsTransitionComplete && this.mSharedElementTransitionComplete) {
            android.view.ViewGroup decor = getDecor();
            if (decor != null && (viewRootImpl = decor.getViewRootImpl()) != null) {
                viewRootImpl.setPausedForTransition(false);
            }
            onTransitionsComplete();
        }
    }

    protected void pauseInput() {
        android.view.ViewGroup decor = getDecor();
        android.view.ViewRootImpl viewRootImpl = decor == null ? null : decor.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.setPausedForTransition(true);
        }
    }

    protected void onTransitionsComplete() {
    }

    protected class ContinueTransitionListener extends android.transition.TransitionListenerAdapter {
        protected ContinueTransitionListener() {
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionStart(android.transition.Transition transition) {
            android.app.ActivityTransitionCoordinator.this.mIsStartingTransition = false;
            java.lang.Runnable runnable = android.app.ActivityTransitionCoordinator.this.mPendingTransition;
            android.app.ActivityTransitionCoordinator.this.mPendingTransition = null;
            if (runnable != null) {
                android.app.ActivityTransitionCoordinator.this.startTransition(runnable);
            }
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionEnd(android.transition.Transition transition) {
            transition.removeListener(this);
        }
    }

    private static int scaleTypeToInt(android.widget.ImageView.ScaleType scaleType) {
        for (int i = 0; i < SCALE_TYPE_VALUES.length; i++) {
            if (scaleType == SCALE_TYPE_VALUES[i]) {
                return i;
            }
        }
        return -1;
    }

    protected void setTransitioningViewsVisiblity(int i, boolean z) {
        int size = this.mTransitioningViews == null ? 0 : this.mTransitioningViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.view.View view = this.mTransitioningViews.get(i2);
            if (z) {
                view.setVisibility(i);
            } else {
                view.setTransitionVisibility(i);
            }
        }
    }

    private static void noLayoutSuppressionForVisibilityTransitions(android.transition.Transition transition) {
        if (transition instanceof android.transition.Visibility) {
            ((android.transition.Visibility) transition).setSuppressLayout(false);
            return;
        }
        if (transition instanceof android.transition.TransitionSet) {
            android.transition.TransitionSet transitionSet = (android.transition.TransitionSet) transition;
            int transitionCount = transitionSet.getTransitionCount();
            for (int i = 0; i < transitionCount; i++) {
                noLayoutSuppressionForVisibilityTransitions(transitionSet.getTransitionAt(i));
            }
        }
    }

    public boolean isTransitionRunning() {
        return (this.mViewsTransitionComplete && this.mSharedElementTransitionComplete && this.mBackgroundAnimatorComplete) ? false : true;
    }

    private static class FixedEpicenterCallback extends android.transition.Transition.EpicenterCallback {
        private android.graphics.Rect mEpicenter;

        private FixedEpicenterCallback() {
        }

        public void setEpicenter(android.graphics.Rect rect) {
            this.mEpicenter = rect;
        }

        @Override // android.transition.Transition.EpicenterCallback
        public android.graphics.Rect onGetEpicenter(android.transition.Transition transition) {
            return this.mEpicenter;
        }
    }

    private static class GhostViewListeners implements android.view.ViewTreeObserver.OnPreDrawListener, android.view.View.OnAttachStateChangeListener {
        private android.view.ViewGroup mDecor;
        private android.graphics.Matrix mMatrix = new android.graphics.Matrix();
        private android.view.View mParent;
        private android.view.View mView;
        private android.view.ViewTreeObserver mViewTreeObserver;

        public GhostViewListeners(android.view.View view, android.view.View view2, android.view.ViewGroup viewGroup) {
            this.mView = view;
            this.mParent = view2;
            this.mDecor = viewGroup;
            this.mViewTreeObserver = view2.getViewTreeObserver();
        }

        public android.view.View getView() {
            return this.mView;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            android.view.GhostView ghost = android.view.GhostView.getGhost(this.mView);
            if (ghost == null || !this.mView.isAttachedToWindow()) {
                removeListener();
                return true;
            }
            android.view.GhostView.calculateMatrix(this.mView, this.mDecor, this.mMatrix);
            ghost.setMatrix(this.mMatrix);
            return true;
        }

        public void removeListener() {
            if (this.mViewTreeObserver.isAlive()) {
                this.mViewTreeObserver.removeOnPreDrawListener(this);
            } else {
                this.mParent.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            this.mParent.removeOnAttachStateChangeListener(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
            this.mViewTreeObserver = view.getViewTreeObserver();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
            removeListener();
        }
    }

    static class SharedElementOriginalState {
        int mBottom;
        float mElevation;
        int mLeft;
        android.graphics.Matrix mMatrix;
        int mMeasuredHeight;
        int mMeasuredWidth;
        int mRight;
        android.widget.ImageView.ScaleType mScaleType;
        int mTop;
        float mTranslationZ;

        SharedElementOriginalState() {
        }
    }
}
