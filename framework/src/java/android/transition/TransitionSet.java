package android.transition;

/* loaded from: classes3.dex */
public class TransitionSet extends android.transition.Transition {
    static final int FLAG_CHANGE_EPICENTER = 8;
    private static final int FLAG_CHANGE_INTERPOLATOR = 1;
    private static final int FLAG_CHANGE_PATH_MOTION = 4;
    private static final int FLAG_CHANGE_PROPAGATION = 2;
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;
    private int mChangeFlags;
    int mCurrentListeners;
    private boolean mPlayTogether;
    boolean mStarted;
    java.util.ArrayList<android.transition.Transition> mTransitions;

    public TransitionSet() {
        this.mTransitions = new java.util.ArrayList<>();
        this.mPlayTogether = true;
        this.mStarted = false;
        this.mChangeFlags = 0;
    }

    public TransitionSet(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTransitions = new java.util.ArrayList<>();
        this.mPlayTogether = true;
        this.mStarted = false;
        this.mChangeFlags = 0;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TransitionSet);
        setOrdering(obtainStyledAttributes.getInt(0, 0));
        obtainStyledAttributes.recycle();
    }

    public android.transition.TransitionSet setOrdering(int i) {
        switch (i) {
            case 0:
                this.mPlayTogether = true;
                return this;
            case 1:
                this.mPlayTogether = false;
                return this;
            default:
                throw new android.util.AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + i);
        }
    }

    public int getOrdering() {
        return !this.mPlayTogether ? 1 : 0;
    }

    public android.transition.TransitionSet addTransition(android.transition.Transition transition) {
        if (transition != null) {
            addTransitionInternal(transition);
            if (this.mDuration >= 0) {
                transition.setDuration(this.mDuration);
            }
            if ((this.mChangeFlags & 1) != 0) {
                transition.setInterpolator(getInterpolator());
            }
            if ((this.mChangeFlags & 2) != 0) {
                transition.setPropagation(getPropagation());
            }
            if ((this.mChangeFlags & 4) != 0) {
                transition.setPathMotion(getPathMotion());
            }
            if ((this.mChangeFlags & 8) != 0) {
                transition.setEpicenterCallback(getEpicenterCallback());
            }
        }
        return this;
    }

    private void addTransitionInternal(android.transition.Transition transition) {
        this.mTransitions.add(transition);
        transition.mParent = this;
    }

    public int getTransitionCount() {
        return this.mTransitions.size();
    }

    public android.transition.Transition getTransitionAt(int i) {
        if (i < 0 || i >= this.mTransitions.size()) {
            return null;
        }
        return this.mTransitions.get(i);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet setDuration(long j) {
        super.setDuration(j);
        if (this.mDuration >= 0 && this.mTransitions != null) {
            int size = this.mTransitions.size();
            for (int i = 0; i < size; i++) {
                this.mTransitions.get(i).setDuration(j);
            }
        }
        return this;
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet setStartDelay(long j) {
        return (android.transition.TransitionSet) super.setStartDelay(j);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet setInterpolator(android.animation.TimeInterpolator timeInterpolator) {
        this.mChangeFlags |= 1;
        if (this.mTransitions != null) {
            int size = this.mTransitions.size();
            for (int i = 0; i < size; i++) {
                this.mTransitions.get(i).setInterpolator(timeInterpolator);
            }
        }
        return (android.transition.TransitionSet) super.setInterpolator(timeInterpolator);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet addTarget(android.view.View view) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            this.mTransitions.get(i).addTarget(view);
        }
        return (android.transition.TransitionSet) super.addTarget(view);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet addTarget(int i) {
        for (int i2 = 0; i2 < this.mTransitions.size(); i2++) {
            this.mTransitions.get(i2).addTarget(i);
        }
        return (android.transition.TransitionSet) super.addTarget(i);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet addTarget(java.lang.String str) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            this.mTransitions.get(i).addTarget(str);
        }
        return (android.transition.TransitionSet) super.addTarget(str);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet addTarget(java.lang.Class cls) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            this.mTransitions.get(i).addTarget(cls);
        }
        return (android.transition.TransitionSet) super.addTarget(cls);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet addListener(android.transition.Transition.TransitionListener transitionListener) {
        return (android.transition.TransitionSet) super.addListener(transitionListener);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet removeTarget(int i) {
        for (int i2 = 0; i2 < this.mTransitions.size(); i2++) {
            this.mTransitions.get(i2).removeTarget(i);
        }
        return (android.transition.TransitionSet) super.removeTarget(i);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet removeTarget(android.view.View view) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            this.mTransitions.get(i).removeTarget(view);
        }
        return (android.transition.TransitionSet) super.removeTarget(view);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet removeTarget(java.lang.Class cls) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            this.mTransitions.get(i).removeTarget(cls);
        }
        return (android.transition.TransitionSet) super.removeTarget(cls);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet removeTarget(java.lang.String str) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            this.mTransitions.get(i).removeTarget(str);
        }
        return (android.transition.TransitionSet) super.removeTarget(str);
    }

    @Override // android.transition.Transition
    public android.transition.Transition excludeTarget(android.view.View view, boolean z) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            this.mTransitions.get(i).excludeTarget(view, z);
        }
        return super.excludeTarget(view, z);
    }

    @Override // android.transition.Transition
    public android.transition.Transition excludeTarget(java.lang.String str, boolean z) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            this.mTransitions.get(i).excludeTarget(str, z);
        }
        return super.excludeTarget(str, z);
    }

    @Override // android.transition.Transition
    public android.transition.Transition excludeTarget(int i, boolean z) {
        for (int i2 = 0; i2 < this.mTransitions.size(); i2++) {
            this.mTransitions.get(i2).excludeTarget(i, z);
        }
        return super.excludeTarget(i, z);
    }

    @Override // android.transition.Transition
    public android.transition.Transition excludeTarget(java.lang.Class cls, boolean z) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            this.mTransitions.get(i).excludeTarget(cls, z);
        }
        return super.excludeTarget(cls, z);
    }

    @Override // android.transition.Transition
    public android.transition.TransitionSet removeListener(android.transition.Transition.TransitionListener transitionListener) {
        return (android.transition.TransitionSet) super.removeListener(transitionListener);
    }

    @Override // android.transition.Transition
    public void setPathMotion(android.transition.PathMotion pathMotion) {
        super.setPathMotion(pathMotion);
        this.mChangeFlags |= 4;
        if (this.mTransitions != null) {
            for (int i = 0; i < this.mTransitions.size(); i++) {
                this.mTransitions.get(i).setPathMotion(pathMotion);
            }
        }
    }

    public android.transition.TransitionSet removeTransition(android.transition.Transition transition) {
        this.mTransitions.remove(transition);
        transition.mParent = null;
        return this;
    }

    private void setupStartEndListeners() {
        android.transition.TransitionSet.TransitionSetListener transitionSetListener = new android.transition.TransitionSet.TransitionSetListener(this);
        java.util.Iterator<android.transition.Transition> it = this.mTransitions.iterator();
        while (it.hasNext()) {
            it.next().addListener(transitionSetListener);
        }
        this.mCurrentListeners = this.mTransitions.size();
    }

    static class TransitionSetListener extends android.transition.TransitionListenerAdapter {
        android.transition.TransitionSet mTransitionSet;

        TransitionSetListener(android.transition.TransitionSet transitionSet) {
            this.mTransitionSet = transitionSet;
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionStart(android.transition.Transition transition) {
            if (!this.mTransitionSet.mStarted) {
                this.mTransitionSet.start();
                this.mTransitionSet.mStarted = true;
            }
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionEnd(android.transition.Transition transition) {
            android.transition.TransitionSet transitionSet = this.mTransitionSet;
            transitionSet.mCurrentListeners--;
            if (this.mTransitionSet.mCurrentListeners == 0) {
                this.mTransitionSet.mStarted = false;
                this.mTransitionSet.end();
            }
            transition.removeListener(this);
        }
    }

    @Override // android.transition.Transition
    protected void createAnimators(android.view.ViewGroup viewGroup, android.transition.TransitionValuesMaps transitionValuesMaps, android.transition.TransitionValuesMaps transitionValuesMaps2, java.util.ArrayList<android.transition.TransitionValues> arrayList, java.util.ArrayList<android.transition.TransitionValues> arrayList2) {
        long startDelay = getStartDelay();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            android.transition.Transition transition = this.mTransitions.get(i);
            if (startDelay > 0 && (this.mPlayTogether || i == 0)) {
                long startDelay2 = transition.getStartDelay();
                if (startDelay2 > 0) {
                    transition.setStartDelay(startDelay2 + startDelay);
                } else {
                    transition.setStartDelay(startDelay);
                }
            }
            transition.createAnimators(viewGroup, transitionValuesMaps, transitionValuesMaps2, arrayList, arrayList2);
        }
    }

    @Override // android.transition.Transition
    protected void runAnimators() {
        if (this.mTransitions.isEmpty()) {
            start();
            end();
            return;
        }
        setupStartEndListeners();
        int size = this.mTransitions.size();
        if (!this.mPlayTogether) {
            for (int i = 1; i < size; i++) {
                android.transition.Transition transition = this.mTransitions.get(i - 1);
                final android.transition.Transition transition2 = this.mTransitions.get(i);
                transition.addListener(new android.transition.TransitionListenerAdapter() { // from class: android.transition.TransitionSet.1
                    @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public void onTransitionEnd(android.transition.Transition transition3) {
                        transition2.runAnimators();
                        transition3.removeListener(this);
                    }
                });
            }
            android.transition.Transition transition3 = this.mTransitions.get(0);
            if (transition3 != null) {
                transition3.runAnimators();
                return;
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            this.mTransitions.get(i2).runAnimators();
        }
    }

    @Override // android.transition.Transition
    public void captureStartValues(android.transition.TransitionValues transitionValues) {
        if (isValidTarget(transitionValues.view)) {
            java.util.Iterator<android.transition.Transition> it = this.mTransitions.iterator();
            while (it.hasNext()) {
                android.transition.Transition next = it.next();
                if (next.isValidTarget(transitionValues.view)) {
                    next.captureStartValues(transitionValues);
                    transitionValues.targetedTransitions.add(next);
                }
            }
        }
    }

    @Override // android.transition.Transition
    public void captureEndValues(android.transition.TransitionValues transitionValues) {
        if (isValidTarget(transitionValues.view)) {
            java.util.Iterator<android.transition.Transition> it = this.mTransitions.iterator();
            while (it.hasNext()) {
                android.transition.Transition next = it.next();
                if (next.isValidTarget(transitionValues.view)) {
                    next.captureEndValues(transitionValues);
                    transitionValues.targetedTransitions.add(next);
                }
            }
        }
    }

    @Override // android.transition.Transition
    void capturePropagationValues(android.transition.TransitionValues transitionValues) {
        super.capturePropagationValues(transitionValues);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).capturePropagationValues(transitionValues);
        }
    }

    @Override // android.transition.Transition
    public void pause(android.view.View view) {
        super.pause(view);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).pause(view);
        }
    }

    @Override // android.transition.Transition
    public void resume(android.view.View view) {
        super.resume(view);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).resume(view);
        }
    }

    @Override // android.transition.Transition
    protected void cancel() {
        super.cancel();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).cancel();
        }
    }

    @Override // android.transition.Transition
    void forceToEnd(android.view.ViewGroup viewGroup) {
        super.forceToEnd(viewGroup);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).forceToEnd(viewGroup);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.transition.Transition
    public android.transition.TransitionSet setSceneRoot(android.view.ViewGroup viewGroup) {
        super.setSceneRoot(viewGroup);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).setSceneRoot(viewGroup);
        }
        return this;
    }

    @Override // android.transition.Transition
    void setCanRemoveViews(boolean z) {
        super.setCanRemoveViews(z);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).setCanRemoveViews(z);
        }
    }

    @Override // android.transition.Transition
    public void setPropagation(android.transition.TransitionPropagation transitionPropagation) {
        super.setPropagation(transitionPropagation);
        this.mChangeFlags |= 2;
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).setPropagation(transitionPropagation);
        }
    }

    @Override // android.transition.Transition
    public void setEpicenterCallback(android.transition.Transition.EpicenterCallback epicenterCallback) {
        super.setEpicenterCallback(epicenterCallback);
        this.mChangeFlags |= 8;
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).setEpicenterCallback(epicenterCallback);
        }
    }

    @Override // android.transition.Transition
    java.lang.String toString(java.lang.String str) {
        java.lang.String transition = super.toString(str);
        for (int i = 0; i < this.mTransitions.size(); i++) {
            transition = transition + "\n" + this.mTransitions.get(i).toString(str + "  ");
        }
        return transition;
    }

    @Override // android.transition.Transition
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.transition.TransitionSet mo4806clone() {
        android.transition.TransitionSet transitionSet = (android.transition.TransitionSet) super.mo4806clone();
        transitionSet.mTransitions = new java.util.ArrayList<>();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            transitionSet.addTransitionInternal(this.mTransitions.get(i).mo4806clone());
        }
        return transitionSet;
    }
}
