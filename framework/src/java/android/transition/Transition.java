package android.transition;

/* loaded from: classes3.dex */
public abstract class Transition implements java.lang.Cloneable {
    static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "Transition";
    private static final int MATCH_FIRST = 1;
    public static final int MATCH_ID = 3;
    private static final java.lang.String MATCH_ID_STR = "id";
    public static final int MATCH_INSTANCE = 1;
    private static final java.lang.String MATCH_INSTANCE_STR = "instance";
    public static final int MATCH_ITEM_ID = 4;
    private static final java.lang.String MATCH_ITEM_ID_STR = "itemId";
    private static final int MATCH_LAST = 4;
    public static final int MATCH_NAME = 2;
    private static final java.lang.String MATCH_NAME_STR = "name";
    private static final java.lang.String MATCH_VIEW_NAME_STR = "viewName";
    java.util.ArrayList<android.transition.TransitionValues> mEndValuesList;
    android.transition.Transition.EpicenterCallback mEpicenterCallback;
    android.util.ArrayMap<java.lang.String, java.lang.String> mNameOverrides;
    android.transition.TransitionPropagation mPropagation;
    java.util.ArrayList<android.transition.TransitionValues> mStartValuesList;
    private static final int[] DEFAULT_MATCH_ORDER = {2, 1, 3, 4};
    private static final android.transition.PathMotion STRAIGHT_PATH_MOTION = new android.transition.PathMotion() { // from class: android.transition.Transition.1
        @Override // android.transition.PathMotion
        public android.graphics.Path getPath(float f, float f2, float f3, float f4) {
            android.graphics.Path path = new android.graphics.Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    };
    private static java.lang.ThreadLocal<android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo>> sRunningAnimators = new java.lang.ThreadLocal<>();
    private java.lang.String mName = getClass().getName();
    long mStartDelay = -1;
    long mDuration = -1;
    android.animation.TimeInterpolator mInterpolator = null;
    java.util.ArrayList<java.lang.Integer> mTargetIds = new java.util.ArrayList<>();
    java.util.ArrayList<android.view.View> mTargets = new java.util.ArrayList<>();
    java.util.ArrayList<java.lang.String> mTargetNames = null;
    java.util.ArrayList<java.lang.Class> mTargetTypes = null;
    java.util.ArrayList<java.lang.Integer> mTargetIdExcludes = null;
    java.util.ArrayList<android.view.View> mTargetExcludes = null;
    java.util.ArrayList<java.lang.Class> mTargetTypeExcludes = null;
    java.util.ArrayList<java.lang.String> mTargetNameExcludes = null;
    java.util.ArrayList<java.lang.Integer> mTargetIdChildExcludes = null;
    java.util.ArrayList<android.view.View> mTargetChildExcludes = null;
    java.util.ArrayList<java.lang.Class> mTargetTypeChildExcludes = null;
    private android.transition.TransitionValuesMaps mStartValues = new android.transition.TransitionValuesMaps();
    private android.transition.TransitionValuesMaps mEndValues = new android.transition.TransitionValuesMaps();
    android.transition.TransitionSet mParent = null;
    int[] mMatchOrder = DEFAULT_MATCH_ORDER;
    android.view.ViewGroup mSceneRoot = null;
    boolean mCanRemoveViews = false;
    private java.util.ArrayList<android.animation.Animator> mCurrentAnimators = new java.util.ArrayList<>();
    int mNumInstances = 0;
    boolean mPaused = false;
    private boolean mEnded = false;
    java.util.ArrayList<android.transition.Transition.TransitionListener> mListeners = null;
    java.util.ArrayList<android.animation.Animator> mAnimators = new java.util.ArrayList<>();
    android.transition.PathMotion mPathMotion = STRAIGHT_PATH_MOTION;

    public static abstract class EpicenterCallback {
        public abstract android.graphics.Rect onGetEpicenter(android.transition.Transition transition);
    }

    public interface TransitionListener {
        void onTransitionCancel(android.transition.Transition transition);

        void onTransitionEnd(android.transition.Transition transition);

        void onTransitionPause(android.transition.Transition transition);

        void onTransitionResume(android.transition.Transition transition);

        void onTransitionStart(android.transition.Transition transition);
    }

    public abstract void captureEndValues(android.transition.TransitionValues transitionValues);

    public abstract void captureStartValues(android.transition.TransitionValues transitionValues);

    public Transition() {
    }

    public Transition(android.content.Context context, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Transition);
        long j = obtainStyledAttributes.getInt(1, -1);
        if (j >= 0) {
            setDuration(j);
        }
        long j2 = obtainStyledAttributes.getInt(2, -1);
        if (j2 > 0) {
            setStartDelay(j2);
        }
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context, resourceId));
        }
        java.lang.String string = obtainStyledAttributes.getString(3);
        if (string != null) {
            setMatchOrder(parseMatchOrder(string));
        }
        obtainStyledAttributes.recycle();
    }

    private static int[] parseMatchOrder(java.lang.String str) {
        java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, ",");
        int[] iArr = new int[stringTokenizer.countTokens()];
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            java.lang.String trim = stringTokenizer.nextToken().trim();
            if ("id".equalsIgnoreCase(trim)) {
                iArr[i] = 3;
            } else if (MATCH_INSTANCE_STR.equalsIgnoreCase(trim)) {
                iArr[i] = 1;
            } else if ("name".equalsIgnoreCase(trim)) {
                iArr[i] = 2;
            } else if (MATCH_VIEW_NAME_STR.equalsIgnoreCase(trim)) {
                iArr[i] = 2;
            } else if (MATCH_ITEM_ID_STR.equalsIgnoreCase(trim)) {
                iArr[i] = 4;
            } else if (trim.isEmpty()) {
                int[] iArr2 = new int[iArr.length - 1];
                java.lang.System.arraycopy(iArr, 0, iArr2, 0, i);
                i--;
                iArr = iArr2;
            } else {
                throw new android.view.InflateException("Unknown match type in matchOrder: '" + trim + "'");
            }
            i++;
        }
        return iArr;
    }

    public android.transition.Transition setDuration(long j) {
        this.mDuration = j;
        return this;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public android.transition.Transition setStartDelay(long j) {
        this.mStartDelay = j;
        return this;
    }

    public long getStartDelay() {
        return this.mStartDelay;
    }

    public android.transition.Transition setInterpolator(android.animation.TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
        return this;
    }

    public android.animation.TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public java.lang.String[] getTransitionProperties() {
        return null;
    }

    public android.animation.Animator createAnimator(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        return null;
    }

    public void setMatchOrder(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            this.mMatchOrder = DEFAULT_MATCH_ORDER;
            return;
        }
        for (int i = 0; i < iArr.length; i++) {
            if (!isValidMatch(iArr[i])) {
                throw new java.lang.IllegalArgumentException("matches contains invalid value");
            }
            if (alreadyContains(iArr, i)) {
                throw new java.lang.IllegalArgumentException("matches contains a duplicate value");
            }
        }
        this.mMatchOrder = (int[]) iArr.clone();
    }

    private static boolean isValidMatch(int i) {
        return i >= 1 && i <= 4;
    }

    private static boolean alreadyContains(int[] iArr, int i) {
        int i2 = iArr[i];
        for (int i3 = 0; i3 < i; i3++) {
            if (iArr[i3] == i2) {
                return true;
            }
        }
        return false;
    }

    private void matchInstances(android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap, android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap2) {
        android.transition.TransitionValues remove;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            android.view.View keyAt = arrayMap.keyAt(size);
            if (keyAt != null && isValidTarget(keyAt) && (remove = arrayMap2.remove(keyAt)) != null && isValidTarget(remove.view)) {
                this.mStartValuesList.add(arrayMap.removeAt(size));
                this.mEndValuesList.add(remove);
            }
        }
    }

    private void matchItemIds(android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap, android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap2, android.util.LongSparseArray<android.view.View> longSparseArray, android.util.LongSparseArray<android.view.View> longSparseArray2) {
        android.view.View view;
        int size = longSparseArray.size();
        for (int i = 0; i < size; i++) {
            android.view.View valueAt = longSparseArray.valueAt(i);
            if (valueAt != null && isValidTarget(valueAt) && (view = longSparseArray2.get(longSparseArray.keyAt(i))) != null && isValidTarget(view)) {
                android.transition.TransitionValues transitionValues = arrayMap.get(valueAt);
                android.transition.TransitionValues transitionValues2 = arrayMap2.get(view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove(valueAt);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    private void matchIds(android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap, android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap2, android.util.SparseArray<android.view.View> sparseArray, android.util.SparseArray<android.view.View> sparseArray2) {
        android.view.View view;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            android.view.View valueAt = sparseArray.valueAt(i);
            if (valueAt != null && isValidTarget(valueAt) && (view = sparseArray2.get(sparseArray.keyAt(i))) != null && isValidTarget(view)) {
                android.transition.TransitionValues transitionValues = arrayMap.get(valueAt);
                android.transition.TransitionValues transitionValues2 = arrayMap2.get(view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove(valueAt);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    private void matchNames(android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap, android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap2, android.util.ArrayMap<java.lang.String, android.view.View> arrayMap3, android.util.ArrayMap<java.lang.String, android.view.View> arrayMap4) {
        android.view.View view;
        int size = arrayMap3.size();
        for (int i = 0; i < size; i++) {
            android.view.View valueAt = arrayMap3.valueAt(i);
            if (valueAt != null && isValidTarget(valueAt) && (view = arrayMap4.get(arrayMap3.keyAt(i))) != null && isValidTarget(view)) {
                android.transition.TransitionValues transitionValues = arrayMap.get(valueAt);
                android.transition.TransitionValues transitionValues2 = arrayMap2.get(view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove(valueAt);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    private void addUnmatched(android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap, android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap2) {
        for (int i = 0; i < arrayMap.size(); i++) {
            android.transition.TransitionValues valueAt = arrayMap.valueAt(i);
            if (isValidTarget(valueAt.view)) {
                this.mStartValuesList.add(valueAt);
                this.mEndValuesList.add(null);
            }
        }
        for (int i2 = 0; i2 < arrayMap2.size(); i2++) {
            android.transition.TransitionValues valueAt2 = arrayMap2.valueAt(i2);
            if (isValidTarget(valueAt2.view)) {
                this.mEndValuesList.add(valueAt2);
                this.mStartValuesList.add(null);
            }
        }
    }

    private void matchStartAndEnd(android.transition.TransitionValuesMaps transitionValuesMaps, android.transition.TransitionValuesMaps transitionValuesMaps2) {
        android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap = new android.util.ArrayMap<>(transitionValuesMaps.viewValues);
        android.util.ArrayMap<android.view.View, android.transition.TransitionValues> arrayMap2 = new android.util.ArrayMap<>(transitionValuesMaps2.viewValues);
        for (int i = 0; i < this.mMatchOrder.length; i++) {
            switch (this.mMatchOrder[i]) {
                case 1:
                    matchInstances(arrayMap, arrayMap2);
                    break;
                case 2:
                    matchNames(arrayMap, arrayMap2, transitionValuesMaps.nameValues, transitionValuesMaps2.nameValues);
                    break;
                case 3:
                    matchIds(arrayMap, arrayMap2, transitionValuesMaps.idValues, transitionValuesMaps2.idValues);
                    break;
                case 4:
                    matchItemIds(arrayMap, arrayMap2, transitionValuesMaps.itemIdValues, transitionValuesMaps2.itemIdValues);
                    break;
            }
        }
        addUnmatched(arrayMap, arrayMap2);
    }

    protected void createAnimators(android.view.ViewGroup viewGroup, android.transition.TransitionValuesMaps transitionValuesMaps, android.transition.TransitionValuesMaps transitionValuesMaps2, java.util.ArrayList<android.transition.TransitionValues> arrayList, java.util.ArrayList<android.transition.TransitionValues> arrayList2) {
        int i;
        int i2;
        android.view.View view;
        android.animation.Animator animator;
        android.transition.TransitionValues transitionValues;
        long j;
        android.animation.Animator animator2;
        android.transition.TransitionValues transitionValues2;
        android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo> runningAnimators = getRunningAnimators();
        this.mAnimators.size();
        android.util.SparseLongArray sparseLongArray = new android.util.SparseLongArray();
        int size = arrayList.size();
        long j2 = Long.MAX_VALUE;
        int i3 = 0;
        while (i3 < size) {
            android.transition.TransitionValues transitionValues3 = arrayList.get(i3);
            android.transition.TransitionValues transitionValues4 = arrayList2.get(i3);
            if (transitionValues3 != null && !transitionValues3.targetedTransitions.contains(this)) {
                transitionValues3 = null;
            }
            if (transitionValues4 != null && !transitionValues4.targetedTransitions.contains(this)) {
                transitionValues4 = null;
            }
            if (transitionValues3 == null && transitionValues4 == null) {
                i = size;
                i2 = i3;
            } else if (!(transitionValues3 == null || transitionValues4 == null || isTransitionRequired(transitionValues3, transitionValues4))) {
                i = size;
                i2 = i3;
            } else {
                android.animation.Animator createAnimator = createAnimator(viewGroup, transitionValues3, transitionValues4);
                if (createAnimator == null) {
                    i = size;
                    i2 = i3;
                } else {
                    if (transitionValues4 != null) {
                        view = transitionValues4.view;
                        java.lang.String[] transitionProperties = getTransitionProperties();
                        if (transitionProperties != null && transitionProperties.length > 0) {
                            transitionValues2 = new android.transition.TransitionValues(view);
                            i = size;
                            android.transition.TransitionValues transitionValues5 = transitionValuesMaps2.viewValues.get(view);
                            if (transitionValues5 == null) {
                                i2 = i3;
                            } else {
                                int i4 = 0;
                                while (i4 < transitionProperties.length) {
                                    transitionValues2.values.put(transitionProperties[i4], transitionValues5.values.get(transitionProperties[i4]));
                                    i4++;
                                    i3 = i3;
                                    transitionValues5 = transitionValues5;
                                }
                                i2 = i3;
                            }
                            int size2 = runningAnimators.size();
                            int i5 = 0;
                            while (true) {
                                if (i5 >= size2) {
                                    animator2 = createAnimator;
                                    break;
                                }
                                android.transition.Transition.AnimationInfo animationInfo = runningAnimators.get(runningAnimators.keyAt(i5));
                                if (animationInfo.values == null || animationInfo.view != view || (((animationInfo.name != null || getName() != null) && !animationInfo.name.equals(getName())) || !animationInfo.values.equals(transitionValues2))) {
                                    i5++;
                                } else {
                                    animator2 = null;
                                    break;
                                }
                            }
                        } else {
                            i = size;
                            i2 = i3;
                            animator2 = createAnimator;
                            transitionValues2 = null;
                        }
                        animator = animator2;
                        transitionValues = transitionValues2;
                    } else {
                        i = size;
                        i2 = i3;
                        view = transitionValues3 != null ? transitionValues3.view : null;
                        animator = createAnimator;
                        transitionValues = null;
                    }
                    if (animator != null) {
                        if (this.mPropagation == null) {
                            j = j2;
                        } else {
                            long startDelay = this.mPropagation.getStartDelay(viewGroup, this, transitionValues3, transitionValues4);
                            sparseLongArray.put(this.mAnimators.size(), startDelay);
                            j = java.lang.Math.min(startDelay, j2);
                        }
                        runningAnimators.put(animator, new android.transition.Transition.AnimationInfo(view, getName(), this, viewGroup.getWindowId(), transitionValues));
                        this.mAnimators.add(animator);
                        j2 = j;
                    }
                }
            }
            i3 = i2 + 1;
            size = i;
        }
        if (sparseLongArray.size() != 0) {
            for (int i6 = 0; i6 < sparseLongArray.size(); i6++) {
                android.animation.Animator animator3 = this.mAnimators.get(sparseLongArray.keyAt(i6));
                animator3.setStartDelay((sparseLongArray.valueAt(i6) - j2) + animator3.getStartDelay());
            }
        }
    }

    public boolean isValidTarget(android.view.View view) {
        if (view == null) {
            return false;
        }
        int id = view.getId();
        if (this.mTargetIdExcludes != null && this.mTargetIdExcludes.contains(java.lang.Integer.valueOf(id))) {
            return false;
        }
        if (this.mTargetExcludes != null && this.mTargetExcludes.contains(view)) {
            return false;
        }
        if (this.mTargetTypeExcludes != null && view != null) {
            int size = this.mTargetTypeExcludes.size();
            for (int i = 0; i < size; i++) {
                if (this.mTargetTypeExcludes.get(i).isInstance(view)) {
                    return false;
                }
            }
        }
        if (this.mTargetNameExcludes != null && view != null && view.getTransitionName() != null && this.mTargetNameExcludes.contains(view.getTransitionName())) {
            return false;
        }
        if ((this.mTargetIds.size() == 0 && this.mTargets.size() == 0 && ((this.mTargetTypes == null || this.mTargetTypes.isEmpty()) && (this.mTargetNames == null || this.mTargetNames.isEmpty()))) || this.mTargetIds.contains(java.lang.Integer.valueOf(id)) || this.mTargets.contains(view)) {
            return true;
        }
        if (this.mTargetNames != null && this.mTargetNames.contains(view.getTransitionName())) {
            return true;
        }
        if (this.mTargetTypes != null) {
            for (int i2 = 0; i2 < this.mTargetTypes.size(); i2++) {
                if (this.mTargetTypes.get(i2).isInstance(view)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo> getRunningAnimators() {
        android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo> arrayMap = sRunningAnimators.get();
        if (arrayMap == null) {
            android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo> arrayMap2 = new android.util.ArrayMap<>();
            sRunningAnimators.set(arrayMap2);
            return arrayMap2;
        }
        return arrayMap;
    }

    protected void runAnimators() {
        start();
        android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo> runningAnimators = getRunningAnimators();
        java.util.Iterator<android.animation.Animator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            android.animation.Animator next = it.next();
            if (runningAnimators.containsKey(next)) {
                start();
                runAnimator(next, runningAnimators);
            }
        }
        this.mAnimators.clear();
        end();
    }

    private void runAnimator(android.animation.Animator animator, final android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo> arrayMap) {
        if (animator != null) {
            animator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.transition.Transition.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(android.animation.Animator animator2) {
                    android.transition.Transition.this.mCurrentAnimators.add(animator2);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(android.animation.Animator animator2) {
                    arrayMap.remove(animator2);
                    android.transition.Transition.this.mCurrentAnimators.remove(animator2);
                }
            });
            animate(animator);
        }
    }

    public android.transition.Transition addTarget(int i) {
        if (i > 0) {
            this.mTargetIds.add(java.lang.Integer.valueOf(i));
        }
        return this;
    }

    public android.transition.Transition addTarget(java.lang.String str) {
        if (str != null) {
            if (this.mTargetNames == null) {
                this.mTargetNames = new java.util.ArrayList<>();
            }
            this.mTargetNames.add(str);
        }
        return this;
    }

    public android.transition.Transition addTarget(java.lang.Class cls) {
        if (cls != null) {
            if (this.mTargetTypes == null) {
                this.mTargetTypes = new java.util.ArrayList<>();
            }
            this.mTargetTypes.add(cls);
        }
        return this;
    }

    public android.transition.Transition removeTarget(int i) {
        if (i > 0) {
            this.mTargetIds.remove(java.lang.Integer.valueOf(i));
        }
        return this;
    }

    public android.transition.Transition removeTarget(java.lang.String str) {
        if (str != null && this.mTargetNames != null) {
            this.mTargetNames.remove(str);
        }
        return this;
    }

    public android.transition.Transition excludeTarget(int i, boolean z) {
        if (i >= 0) {
            this.mTargetIdExcludes = excludeObject(this.mTargetIdExcludes, java.lang.Integer.valueOf(i), z);
        }
        return this;
    }

    public android.transition.Transition excludeTarget(java.lang.String str, boolean z) {
        this.mTargetNameExcludes = excludeObject(this.mTargetNameExcludes, str, z);
        return this;
    }

    public android.transition.Transition excludeChildren(int i, boolean z) {
        if (i >= 0) {
            this.mTargetIdChildExcludes = excludeObject(this.mTargetIdChildExcludes, java.lang.Integer.valueOf(i), z);
        }
        return this;
    }

    public android.transition.Transition excludeTarget(android.view.View view, boolean z) {
        this.mTargetExcludes = excludeObject(this.mTargetExcludes, view, z);
        return this;
    }

    public android.transition.Transition excludeChildren(android.view.View view, boolean z) {
        this.mTargetChildExcludes = excludeObject(this.mTargetChildExcludes, view, z);
        return this;
    }

    private static <T> java.util.ArrayList<T> excludeObject(java.util.ArrayList<T> arrayList, T t, boolean z) {
        if (t != null) {
            if (z) {
                return android.transition.Transition.ArrayListManager.add(arrayList, t);
            }
            return android.transition.Transition.ArrayListManager.remove(arrayList, t);
        }
        return arrayList;
    }

    public android.transition.Transition excludeTarget(java.lang.Class cls, boolean z) {
        this.mTargetTypeExcludes = excludeObject(this.mTargetTypeExcludes, cls, z);
        return this;
    }

    public android.transition.Transition excludeChildren(java.lang.Class cls, boolean z) {
        this.mTargetTypeChildExcludes = excludeObject(this.mTargetTypeChildExcludes, cls, z);
        return this;
    }

    public android.transition.Transition addTarget(android.view.View view) {
        this.mTargets.add(view);
        return this;
    }

    public android.transition.Transition removeTarget(android.view.View view) {
        if (view != null) {
            this.mTargets.remove(view);
        }
        return this;
    }

    public android.transition.Transition removeTarget(java.lang.Class cls) {
        if (cls != null) {
            this.mTargetTypes.remove(cls);
        }
        return this;
    }

    public java.util.List<java.lang.Integer> getTargetIds() {
        return this.mTargetIds;
    }

    public java.util.List<android.view.View> getTargets() {
        return this.mTargets;
    }

    public java.util.List<java.lang.String> getTargetNames() {
        return this.mTargetNames;
    }

    public java.util.List<java.lang.String> getTargetViewNames() {
        return this.mTargetNames;
    }

    public java.util.List<java.lang.Class> getTargetTypes() {
        return this.mTargetTypes;
    }

    void captureValues(android.view.ViewGroup viewGroup, boolean z) {
        clearValues(z);
        if ((this.mTargetIds.size() > 0 || this.mTargets.size() > 0) && ((this.mTargetNames == null || this.mTargetNames.isEmpty()) && (this.mTargetTypes == null || this.mTargetTypes.isEmpty()))) {
            for (int i = 0; i < this.mTargetIds.size(); i++) {
                android.view.View findViewById = viewGroup.findViewById(this.mTargetIds.get(i).intValue());
                if (findViewById != null) {
                    android.transition.TransitionValues transitionValues = new android.transition.TransitionValues(findViewById);
                    if (z) {
                        captureStartValues(transitionValues);
                    } else {
                        captureEndValues(transitionValues);
                    }
                    transitionValues.targetedTransitions.add(this);
                    capturePropagationValues(transitionValues);
                    if (z) {
                        addViewValues(this.mStartValues, findViewById, transitionValues);
                    } else {
                        addViewValues(this.mEndValues, findViewById, transitionValues);
                    }
                }
            }
            for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                android.view.View view = this.mTargets.get(i2);
                android.transition.TransitionValues transitionValues2 = new android.transition.TransitionValues(view);
                if (z) {
                    captureStartValues(transitionValues2);
                } else {
                    captureEndValues(transitionValues2);
                }
                transitionValues2.targetedTransitions.add(this);
                capturePropagationValues(transitionValues2);
                if (z) {
                    addViewValues(this.mStartValues, view, transitionValues2);
                } else {
                    addViewValues(this.mEndValues, view, transitionValues2);
                }
            }
        } else {
            captureHierarchy(viewGroup, z);
        }
        if (!z && this.mNameOverrides != null) {
            int size = this.mNameOverrides.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i3 = 0; i3 < size; i3++) {
                arrayList.add(this.mStartValues.nameValues.remove(this.mNameOverrides.keyAt(i3)));
            }
            for (int i4 = 0; i4 < size; i4++) {
                android.view.View view2 = (android.view.View) arrayList.get(i4);
                if (view2 != null) {
                    this.mStartValues.nameValues.put(this.mNameOverrides.valueAt(i4), view2);
                }
            }
        }
    }

    static void addViewValues(android.transition.TransitionValuesMaps transitionValuesMaps, android.view.View view, android.transition.TransitionValues transitionValues) {
        transitionValuesMaps.viewValues.put(view, transitionValues);
        int id = view.getId();
        if (id >= 0) {
            if (transitionValuesMaps.idValues.indexOfKey(id) >= 0) {
                transitionValuesMaps.idValues.put(id, null);
            } else {
                transitionValuesMaps.idValues.put(id, view);
            }
        }
        java.lang.String transitionName = view.getTransitionName();
        if (transitionName != null) {
            if (transitionValuesMaps.nameValues.containsKey(transitionName)) {
                transitionValuesMaps.nameValues.put(transitionName, null);
            } else {
                transitionValuesMaps.nameValues.put(transitionName, view);
            }
        }
        if (view.getParent() instanceof android.widget.ListView) {
            android.widget.ListView listView = (android.widget.ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                if (transitionValuesMaps.itemIdValues.indexOfKey(itemIdAtPosition) >= 0) {
                    android.view.View view2 = transitionValuesMaps.itemIdValues.get(itemIdAtPosition);
                    if (view2 != null) {
                        view2.setHasTransientState(false);
                        transitionValuesMaps.itemIdValues.put(itemIdAtPosition, null);
                        return;
                    }
                    return;
                }
                view.setHasTransientState(true);
                transitionValuesMaps.itemIdValues.put(itemIdAtPosition, view);
            }
        }
    }

    void clearValues(boolean z) {
        if (z) {
            this.mStartValues.viewValues.clear();
            this.mStartValues.idValues.clear();
            this.mStartValues.itemIdValues.clear();
            this.mStartValues.nameValues.clear();
            this.mStartValuesList = null;
            return;
        }
        this.mEndValues.viewValues.clear();
        this.mEndValues.idValues.clear();
        this.mEndValues.itemIdValues.clear();
        this.mEndValues.nameValues.clear();
        this.mEndValuesList = null;
    }

    private void captureHierarchy(android.view.View view, boolean z) {
        if (view == null) {
            return;
        }
        int id = view.getId();
        if (this.mTargetIdExcludes != null && this.mTargetIdExcludes.contains(java.lang.Integer.valueOf(id))) {
            return;
        }
        if (this.mTargetExcludes != null && this.mTargetExcludes.contains(view)) {
            return;
        }
        if (this.mTargetTypeExcludes != null && view != null) {
            int size = this.mTargetTypeExcludes.size();
            for (int i = 0; i < size; i++) {
                if (this.mTargetTypeExcludes.get(i).isInstance(view)) {
                    return;
                }
            }
        }
        if (view.getParent() instanceof android.view.ViewGroup) {
            android.transition.TransitionValues transitionValues = new android.transition.TransitionValues(view);
            if (z) {
                captureStartValues(transitionValues);
            } else {
                captureEndValues(transitionValues);
            }
            transitionValues.targetedTransitions.add(this);
            capturePropagationValues(transitionValues);
            if (z) {
                addViewValues(this.mStartValues, view, transitionValues);
            } else {
                addViewValues(this.mEndValues, view, transitionValues);
            }
        }
        if (view instanceof android.view.ViewGroup) {
            if (this.mTargetIdChildExcludes != null && this.mTargetIdChildExcludes.contains(java.lang.Integer.valueOf(id))) {
                return;
            }
            if (this.mTargetChildExcludes != null && this.mTargetChildExcludes.contains(view)) {
                return;
            }
            if (this.mTargetTypeChildExcludes != null) {
                int size2 = this.mTargetTypeChildExcludes.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    if (this.mTargetTypeChildExcludes.get(i2).isInstance(view)) {
                        return;
                    }
                }
            }
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                captureHierarchy(viewGroup.getChildAt(i3), z);
            }
        }
    }

    public android.transition.TransitionValues getTransitionValues(android.view.View view, boolean z) {
        if (this.mParent != null) {
            return this.mParent.getTransitionValues(view, z);
        }
        return (z ? this.mStartValues : this.mEndValues).viewValues.get(view);
    }

    android.transition.TransitionValues getMatchedTransitionValues(android.view.View view, boolean z) {
        if (this.mParent != null) {
            return this.mParent.getMatchedTransitionValues(view, z);
        }
        java.util.ArrayList<android.transition.TransitionValues> arrayList = z ? this.mStartValuesList : this.mEndValuesList;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            }
            android.transition.TransitionValues transitionValues = arrayList.get(i);
            if (transitionValues == null) {
                return null;
            }
            if (transitionValues.view == view) {
                break;
            }
            i++;
        }
        if (i < 0) {
            return null;
        }
        return (z ? this.mEndValuesList : this.mStartValuesList).get(i);
    }

    public void pause(android.view.View view) {
        if (!this.mEnded) {
            android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo> runningAnimators = getRunningAnimators();
            int size = runningAnimators.size();
            if (view != null) {
                android.view.WindowId windowId = view.getWindowId();
                for (int i = size - 1; i >= 0; i--) {
                    android.transition.Transition.AnimationInfo valueAt = runningAnimators.valueAt(i);
                    if (valueAt.view != null && windowId != null && windowId.equals(valueAt.windowId)) {
                        runningAnimators.keyAt(i).pause();
                    }
                }
            }
            if (this.mListeners != null && this.mListeners.size() > 0) {
                java.util.ArrayList arrayList = (java.util.ArrayList) this.mListeners.clone();
                int size2 = arrayList.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    ((android.transition.Transition.TransitionListener) arrayList.get(i2)).onTransitionPause(this);
                }
            }
            this.mPaused = true;
        }
    }

    public void resume(android.view.View view) {
        if (this.mPaused) {
            if (!this.mEnded) {
                android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo> runningAnimators = getRunningAnimators();
                int size = runningAnimators.size();
                android.view.WindowId windowId = view.getWindowId();
                for (int i = size - 1; i >= 0; i--) {
                    android.transition.Transition.AnimationInfo valueAt = runningAnimators.valueAt(i);
                    if (valueAt.view != null && windowId != null && windowId.equals(valueAt.windowId)) {
                        runningAnimators.keyAt(i).resume();
                    }
                }
                if (this.mListeners != null && this.mListeners.size() > 0) {
                    java.util.ArrayList arrayList = (java.util.ArrayList) this.mListeners.clone();
                    int size2 = arrayList.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ((android.transition.Transition.TransitionListener) arrayList.get(i2)).onTransitionResume(this);
                    }
                }
            }
            this.mPaused = false;
        }
    }

    void playTransition(android.view.ViewGroup viewGroup) {
        android.transition.Transition.AnimationInfo animationInfo;
        this.mStartValuesList = new java.util.ArrayList<>();
        this.mEndValuesList = new java.util.ArrayList<>();
        matchStartAndEnd(this.mStartValues, this.mEndValues);
        android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo> runningAnimators = getRunningAnimators();
        int size = runningAnimators.size();
        android.view.WindowId windowId = viewGroup.getWindowId();
        for (int i = size - 1; i >= 0; i--) {
            android.animation.Animator keyAt = runningAnimators.keyAt(i);
            if (keyAt != null && (animationInfo = runningAnimators.get(keyAt)) != null && animationInfo.view != null && animationInfo.windowId == windowId) {
                android.transition.TransitionValues transitionValues = animationInfo.values;
                android.view.View view = animationInfo.view;
                android.transition.TransitionValues transitionValues2 = getTransitionValues(view, true);
                android.transition.TransitionValues matchedTransitionValues = getMatchedTransitionValues(view, true);
                if (transitionValues2 == null && matchedTransitionValues == null) {
                    matchedTransitionValues = this.mEndValues.viewValues.get(view);
                }
                if (!(transitionValues2 == null && matchedTransitionValues == null) && animationInfo.transition.isTransitionRequired(transitionValues, matchedTransitionValues)) {
                    if (keyAt.isRunning() || keyAt.isStarted()) {
                        keyAt.cancel();
                    } else {
                        runningAnimators.remove(keyAt);
                    }
                }
            }
        }
        createAnimators(viewGroup, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
        runAnimators();
    }

    public boolean isTransitionRequired(android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return false;
        }
        java.lang.String[] transitionProperties = getTransitionProperties();
        if (transitionProperties != null) {
            for (java.lang.String str : transitionProperties) {
                if (isValueChanged(transitionValues, transitionValues2, str)) {
                    return true;
                }
            }
            return false;
        }
        java.util.Iterator<java.lang.String> it = transitionValues.values.keySet().iterator();
        while (it.hasNext()) {
            if (isValueChanged(transitionValues, transitionValues2, it.next())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValueChanged(android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2, java.lang.String str) {
        if (transitionValues.values.containsKey(str) != transitionValues2.values.containsKey(str)) {
            return false;
        }
        java.lang.Object obj = transitionValues.values.get(str);
        java.lang.Object obj2 = transitionValues2.values.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return true ^ obj.equals(obj2);
    }

    protected void animate(android.animation.Animator animator) {
        if (animator == null) {
            end();
            return;
        }
        if (getDuration() >= 0) {
            animator.setDuration(getDuration());
        }
        if (getStartDelay() >= 0) {
            animator.setStartDelay(getStartDelay() + animator.getStartDelay());
        }
        if (getInterpolator() != null) {
            animator.setInterpolator(getInterpolator());
        }
        animator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.transition.Transition.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator2) {
                android.transition.Transition.this.end();
                animator2.removeListener(this);
            }
        });
        animator.start();
    }

    protected void start() {
        if (this.mNumInstances == 0) {
            if (this.mListeners != null && this.mListeners.size() > 0) {
                java.util.ArrayList arrayList = (java.util.ArrayList) this.mListeners.clone();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((android.transition.Transition.TransitionListener) arrayList.get(i)).onTransitionStart(this);
                }
            }
            this.mEnded = false;
        }
        this.mNumInstances++;
    }

    protected void end() {
        this.mNumInstances--;
        if (this.mNumInstances == 0) {
            if (this.mListeners != null && this.mListeners.size() > 0) {
                java.util.ArrayList arrayList = (java.util.ArrayList) this.mListeners.clone();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((android.transition.Transition.TransitionListener) arrayList.get(i)).onTransitionEnd(this);
                }
            }
            for (int i2 = 0; i2 < this.mStartValues.itemIdValues.size(); i2++) {
                android.view.View valueAt = this.mStartValues.itemIdValues.valueAt(i2);
                if (valueAt != null) {
                    valueAt.setHasTransientState(false);
                }
            }
            for (int i3 = 0; i3 < this.mEndValues.itemIdValues.size(); i3++) {
                android.view.View valueAt2 = this.mEndValues.itemIdValues.valueAt(i3);
                if (valueAt2 != null) {
                    valueAt2.setHasTransientState(false);
                }
            }
            this.mEnded = true;
        }
    }

    void forceToEnd(android.view.ViewGroup viewGroup) {
        android.util.ArrayMap<android.animation.Animator, android.transition.Transition.AnimationInfo> runningAnimators = getRunningAnimators();
        int size = runningAnimators.size();
        if (viewGroup == null || size == 0) {
            return;
        }
        android.view.WindowId windowId = viewGroup.getWindowId();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(runningAnimators);
        runningAnimators.clear();
        for (int i = size - 1; i >= 0; i--) {
            android.transition.Transition.AnimationInfo animationInfo = (android.transition.Transition.AnimationInfo) arrayMap.valueAt(i);
            if (animationInfo.view != null && windowId != null && windowId.equals(animationInfo.windowId)) {
                ((android.animation.Animator) arrayMap.keyAt(i)).end();
            }
        }
    }

    protected void cancel() {
        for (int size = this.mCurrentAnimators.size() - 1; size >= 0; size--) {
            this.mCurrentAnimators.get(size).cancel();
        }
        if (this.mListeners != null && this.mListeners.size() > 0) {
            java.util.ArrayList arrayList = (java.util.ArrayList) this.mListeners.clone();
            int size2 = arrayList.size();
            for (int i = 0; i < size2; i++) {
                ((android.transition.Transition.TransitionListener) arrayList.get(i)).onTransitionCancel(this);
            }
        }
    }

    public android.transition.Transition addListener(android.transition.Transition.TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new java.util.ArrayList<>();
        }
        this.mListeners.add(transitionListener);
        return this;
    }

    public android.transition.Transition removeListener(android.transition.Transition.TransitionListener transitionListener) {
        if (this.mListeners == null) {
            return this;
        }
        this.mListeners.remove(transitionListener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
        return this;
    }

    public void setEpicenterCallback(android.transition.Transition.EpicenterCallback epicenterCallback) {
        this.mEpicenterCallback = epicenterCallback;
    }

    public android.transition.Transition.EpicenterCallback getEpicenterCallback() {
        return this.mEpicenterCallback;
    }

    public android.graphics.Rect getEpicenter() {
        if (this.mEpicenterCallback == null) {
            return null;
        }
        return this.mEpicenterCallback.onGetEpicenter(this);
    }

    public void setPathMotion(android.transition.PathMotion pathMotion) {
        if (pathMotion == null) {
            this.mPathMotion = STRAIGHT_PATH_MOTION;
        } else {
            this.mPathMotion = pathMotion;
        }
    }

    public android.transition.PathMotion getPathMotion() {
        return this.mPathMotion;
    }

    public void setPropagation(android.transition.TransitionPropagation transitionPropagation) {
        this.mPropagation = transitionPropagation;
    }

    public android.transition.TransitionPropagation getPropagation() {
        return this.mPropagation;
    }

    void capturePropagationValues(android.transition.TransitionValues transitionValues) {
        java.lang.String[] propagationProperties;
        if (this.mPropagation == null || transitionValues.values.isEmpty() || (propagationProperties = this.mPropagation.getPropagationProperties()) == null) {
            return;
        }
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= propagationProperties.length) {
                z = true;
                break;
            } else if (!transitionValues.values.containsKey(propagationProperties[i])) {
                break;
            } else {
                i++;
            }
        }
        if (!z) {
            this.mPropagation.captureValues(transitionValues);
        }
    }

    android.transition.Transition setSceneRoot(android.view.ViewGroup viewGroup) {
        this.mSceneRoot = viewGroup;
        return this;
    }

    void setCanRemoveViews(boolean z) {
        this.mCanRemoveViews = z;
    }

    public boolean canRemoveViews() {
        return this.mCanRemoveViews;
    }

    public void setNameOverrides(android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap) {
        this.mNameOverrides = arrayMap;
    }

    public android.util.ArrayMap<java.lang.String, java.lang.String> getNameOverrides() {
        return this.mNameOverrides;
    }

    public java.lang.String toString() {
        return toString("");
    }

    @Override // 
    /* renamed from: clone */
    public android.transition.Transition mo4806clone() {
        android.transition.Transition transition;
        android.transition.Transition transition2 = null;
        try {
            transition = (android.transition.Transition) super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
        }
        try {
            transition.mAnimators = new java.util.ArrayList<>();
            transition.mStartValues = new android.transition.TransitionValuesMaps();
            transition.mEndValues = new android.transition.TransitionValuesMaps();
            transition.mStartValuesList = null;
            transition.mEndValuesList = null;
            return transition;
        } catch (java.lang.CloneNotSupportedException e2) {
            transition2 = transition;
            return transition2;
        }
    }

    public java.lang.String getName() {
        return this.mName;
    }

    java.lang.String toString(java.lang.String str) {
        java.lang.String str2 = str + getClass().getSimpleName() + "@" + java.lang.Integer.toHexString(hashCode()) + ": ";
        if (this.mDuration != -1) {
            str2 = str2 + "dur(" + this.mDuration + ") ";
        }
        if (this.mStartDelay != -1) {
            str2 = str2 + "dly(" + this.mStartDelay + ") ";
        }
        if (this.mInterpolator != null) {
            str2 = str2 + "interp(" + this.mInterpolator + ") ";
        }
        if (this.mTargetIds.size() > 0 || this.mTargets.size() > 0) {
            java.lang.String str3 = str2 + "tgts(";
            if (this.mTargetIds.size() > 0) {
                for (int i = 0; i < this.mTargetIds.size(); i++) {
                    if (i > 0) {
                        str3 = str3 + ", ";
                    }
                    str3 = str3 + this.mTargetIds.get(i);
                }
            }
            if (this.mTargets.size() > 0) {
                for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                    if (i2 > 0) {
                        str3 = str3 + ", ";
                    }
                    str3 = str3 + this.mTargets.get(i2);
                }
            }
            return str3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
        return str2;
    }

    public static class AnimationInfo {
        java.lang.String name;
        android.transition.Transition transition;
        android.transition.TransitionValues values;
        public android.view.View view;
        android.view.WindowId windowId;

        AnimationInfo(android.view.View view, java.lang.String str, android.transition.Transition transition, android.view.WindowId windowId, android.transition.TransitionValues transitionValues) {
            this.view = view;
            this.name = str;
            this.values = transitionValues;
            this.windowId = windowId;
            this.transition = transition;
        }
    }

    private static class ArrayListManager {
        private ArrayListManager() {
        }

        static <T> java.util.ArrayList<T> add(java.util.ArrayList<T> arrayList, T t) {
            if (arrayList == null) {
                arrayList = new java.util.ArrayList<>();
            }
            if (!arrayList.contains(t)) {
                arrayList.add(t);
            }
            return arrayList;
        }

        static <T> java.util.ArrayList<T> remove(java.util.ArrayList<T> arrayList, T t) {
            if (arrayList != null) {
                arrayList.remove(t);
                if (arrayList.isEmpty()) {
                    return null;
                }
                return arrayList;
            }
            return arrayList;
        }
    }
}
