package android.app;

/* loaded from: classes.dex */
class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};

    public static class FragmentContainerTransition {
        public android.app.Fragment firstOut;
        public boolean firstOutIsPop;
        public android.app.BackStackRecord firstOutTransaction;
        public android.app.Fragment lastIn;
        public boolean lastInIsPop;
        public android.app.BackStackRecord lastInTransaction;
    }

    FragmentTransition() {
    }

    static void startTransitions(android.app.FragmentManagerImpl fragmentManagerImpl, java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2, int i, int i2, boolean z) {
        if (fragmentManagerImpl.mCurState < 1) {
            return;
        }
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        for (int i3 = i; i3 < i2; i3++) {
            android.app.BackStackRecord backStackRecord = arrayList.get(i3);
            if (arrayList2.get(i3).booleanValue()) {
                calculatePopFragments(backStackRecord, sparseArray, z);
            } else {
                calculateFragments(backStackRecord, sparseArray, z);
            }
        }
        if (sparseArray.size() != 0) {
            android.view.View view = new android.view.View(fragmentManagerImpl.mHost.getContext());
            int size = sparseArray.size();
            for (int i4 = 0; i4 < size; i4++) {
                int keyAt = sparseArray.keyAt(i4);
                android.util.ArrayMap<java.lang.String, java.lang.String> calculateNameOverrides = calculateNameOverrides(keyAt, arrayList, arrayList2, i, i2);
                android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition = (android.app.FragmentTransition.FragmentContainerTransition) sparseArray.valueAt(i4);
                if (z) {
                    configureTransitionsReordered(fragmentManagerImpl, keyAt, fragmentContainerTransition, view, calculateNameOverrides);
                } else {
                    configureTransitionsOrdered(fragmentManagerImpl, keyAt, fragmentContainerTransition, view, calculateNameOverrides);
                }
            }
        }
    }

    private static android.util.ArrayMap<java.lang.String, java.lang.String> calculateNameOverrides(int i, java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2, int i2, int i3) {
        java.util.ArrayList<java.lang.String> arrayList3;
        java.util.ArrayList<java.lang.String> arrayList4;
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = new android.util.ArrayMap<>();
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            android.app.BackStackRecord backStackRecord = arrayList.get(i4);
            if (backStackRecord.interactsWith(i)) {
                boolean booleanValue = arrayList2.get(i4).booleanValue();
                if (backStackRecord.mSharedElementSourceNames != null) {
                    int size = backStackRecord.mSharedElementSourceNames.size();
                    if (booleanValue) {
                        arrayList3 = backStackRecord.mSharedElementSourceNames;
                        arrayList4 = backStackRecord.mSharedElementTargetNames;
                    } else {
                        java.util.ArrayList<java.lang.String> arrayList5 = backStackRecord.mSharedElementSourceNames;
                        arrayList3 = backStackRecord.mSharedElementTargetNames;
                        arrayList4 = arrayList5;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        java.lang.String str = arrayList4.get(i5);
                        java.lang.String str2 = arrayList3.get(i5);
                        java.lang.String remove = arrayMap.remove(str2);
                        if (remove != null) {
                            arrayMap.put(str, remove);
                        } else {
                            arrayMap.put(str, str2);
                        }
                    }
                }
            }
        }
        return arrayMap;
    }

    private static void configureTransitionsReordered(android.app.FragmentManagerImpl fragmentManagerImpl, int i, android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition, android.view.View view, android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap) {
        android.view.ViewGroup viewGroup;
        android.transition.Transition transition;
        if (!fragmentManagerImpl.mContainer.onHasView()) {
            viewGroup = null;
        } else {
            viewGroup = (android.view.ViewGroup) fragmentManagerImpl.mContainer.onFindViewById(i);
        }
        if (viewGroup == null) {
            return;
        }
        android.app.Fragment fragment = fragmentContainerTransition.lastIn;
        android.app.Fragment fragment2 = fragmentContainerTransition.firstOut;
        boolean z = fragmentContainerTransition.lastInIsPop;
        boolean z2 = fragmentContainerTransition.firstOutIsPop;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        android.transition.Transition enterTransition = getEnterTransition(fragment, z);
        android.transition.Transition exitTransition = getExitTransition(fragment2, z2);
        android.transition.TransitionSet configureSharedElementsReordered = configureSharedElementsReordered(viewGroup, view, arrayMap, fragmentContainerTransition, arrayList2, arrayList, enterTransition, exitTransition);
        if (enterTransition == null && configureSharedElementsReordered == null) {
            transition = exitTransition;
            if (transition == null) {
                return;
            }
        } else {
            transition = exitTransition;
        }
        java.util.ArrayList<android.view.View> configureEnteringExitingViews = configureEnteringExitingViews(transition, fragment2, arrayList2, view);
        java.util.ArrayList<android.view.View> configureEnteringExitingViews2 = configureEnteringExitingViews(enterTransition, fragment, arrayList, view);
        setViewVisibility(configureEnteringExitingViews2, 4);
        android.transition.Transition mergeTransitions = mergeTransitions(enterTransition, transition, configureSharedElementsReordered, fragment, z);
        if (mergeTransitions != null) {
            replaceHide(transition, fragment2, configureEnteringExitingViews);
            mergeTransitions.setNameOverrides(arrayMap);
            scheduleRemoveTargets(mergeTransitions, enterTransition, configureEnteringExitingViews2, transition, configureEnteringExitingViews, configureSharedElementsReordered, arrayList);
            android.transition.TransitionManager.beginDelayedTransition(viewGroup, mergeTransitions);
            setViewVisibility(configureEnteringExitingViews2, 0);
            if (configureSharedElementsReordered != null) {
                configureSharedElementsReordered.getTargets().clear();
                configureSharedElementsReordered.getTargets().addAll(arrayList);
                replaceTargets(configureSharedElementsReordered, arrayList2, arrayList);
            }
        }
    }

    private static void configureTransitionsOrdered(android.app.FragmentManagerImpl fragmentManagerImpl, int i, android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition, android.view.View view, android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap) {
        android.view.ViewGroup viewGroup;
        if (!fragmentManagerImpl.mContainer.onHasView()) {
            viewGroup = null;
        } else {
            viewGroup = (android.view.ViewGroup) fragmentManagerImpl.mContainer.onFindViewById(i);
        }
        if (viewGroup == null) {
            return;
        }
        android.app.Fragment fragment = fragmentContainerTransition.lastIn;
        android.app.Fragment fragment2 = fragmentContainerTransition.firstOut;
        boolean z = fragmentContainerTransition.lastInIsPop;
        boolean z2 = fragmentContainerTransition.firstOutIsPop;
        android.transition.Transition enterTransition = getEnterTransition(fragment, z);
        android.transition.Transition exitTransition = getExitTransition(fragment2, z2);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        android.transition.TransitionSet configureSharedElementsOrdered = configureSharedElementsOrdered(viewGroup, view, arrayMap, fragmentContainerTransition, arrayList, arrayList2, enterTransition, exitTransition);
        if (enterTransition == null && configureSharedElementsOrdered == null && exitTransition == null) {
            return;
        }
        java.util.ArrayList<android.view.View> configureEnteringExitingViews = configureEnteringExitingViews(exitTransition, fragment2, arrayList, view);
        if (configureEnteringExitingViews == null || configureEnteringExitingViews.isEmpty()) {
            exitTransition = null;
        }
        if (enterTransition != null) {
            enterTransition.addTarget(view);
        }
        android.transition.Transition mergeTransitions = mergeTransitions(enterTransition, exitTransition, configureSharedElementsOrdered, fragment, fragmentContainerTransition.lastInIsPop);
        if (mergeTransitions != null) {
            mergeTransitions.setNameOverrides(arrayMap);
            java.util.ArrayList arrayList3 = new java.util.ArrayList();
            scheduleRemoveTargets(mergeTransitions, enterTransition, arrayList3, exitTransition, configureEnteringExitingViews, configureSharedElementsOrdered, arrayList2);
            scheduleTargetChange(viewGroup, fragment, view, arrayList2, enterTransition, arrayList3, exitTransition, configureEnteringExitingViews);
            android.transition.TransitionManager.beginDelayedTransition(viewGroup, mergeTransitions);
        }
    }

    private static void replaceHide(android.transition.Transition transition, android.app.Fragment fragment, final java.util.ArrayList<android.view.View> arrayList) {
        if (fragment != null && transition != null && fragment.mAdded && fragment.mHidden && fragment.mHiddenChanged) {
            fragment.setHideReplaced(true);
            final android.view.View view = fragment.getView();
            com.android.internal.view.OneShotPreDrawListener.add(fragment.mContainer, new java.lang.Runnable() { // from class: android.app.FragmentTransition$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.FragmentTransition.setViewVisibility(arrayList, 4);
                }
            });
            transition.addListener(new android.transition.TransitionListenerAdapter() { // from class: android.app.FragmentTransition.1
                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(android.transition.Transition transition2) {
                    transition2.removeListener(this);
                    android.view.View.this.setVisibility(8);
                    android.app.FragmentTransition.setViewVisibility(arrayList, 0);
                }
            });
        }
    }

    private static void scheduleTargetChange(android.view.ViewGroup viewGroup, final android.app.Fragment fragment, final android.view.View view, final java.util.ArrayList<android.view.View> arrayList, final android.transition.Transition transition, final java.util.ArrayList<android.view.View> arrayList2, final android.transition.Transition transition2, final java.util.ArrayList<android.view.View> arrayList3) {
        com.android.internal.view.OneShotPreDrawListener.add(viewGroup, new java.lang.Runnable() { // from class: android.app.FragmentTransition$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.app.FragmentTransition.lambda$scheduleTargetChange$1(android.transition.Transition.this, view, fragment, arrayList, arrayList2, arrayList3, transition2);
            }
        });
    }

    static /* synthetic */ void lambda$scheduleTargetChange$1(android.transition.Transition transition, android.view.View view, android.app.Fragment fragment, java.util.ArrayList arrayList, java.util.ArrayList arrayList2, java.util.ArrayList arrayList3, android.transition.Transition transition2) {
        if (transition != null) {
            transition.removeTarget(view);
            arrayList2.addAll(configureEnteringExitingViews(transition, fragment, arrayList, view));
        }
        if (arrayList3 != null) {
            if (transition2 != null) {
                java.util.ArrayList arrayList4 = new java.util.ArrayList();
                arrayList4.add(view);
                replaceTargets(transition2, arrayList3, arrayList4);
            }
            arrayList3.clear();
            arrayList3.add(view);
        }
    }

    private static android.transition.TransitionSet getSharedElementTransition(android.app.Fragment fragment, android.app.Fragment fragment2, boolean z) {
        android.transition.Transition sharedElementEnterTransition;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        if (z) {
            sharedElementEnterTransition = fragment2.getSharedElementReturnTransition();
        } else {
            sharedElementEnterTransition = fragment.getSharedElementEnterTransition();
        }
        android.transition.Transition cloneTransition = cloneTransition(sharedElementEnterTransition);
        if (cloneTransition == null) {
            return null;
        }
        android.transition.TransitionSet transitionSet = new android.transition.TransitionSet();
        transitionSet.addTransition(cloneTransition);
        return transitionSet;
    }

    private static android.transition.Transition getEnterTransition(android.app.Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return cloneTransition(z ? fragment.getReenterTransition() : fragment.getEnterTransition());
    }

    private static android.transition.Transition getExitTransition(android.app.Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return cloneTransition(z ? fragment.getReturnTransition() : fragment.getExitTransition());
    }

    private static android.transition.Transition cloneTransition(android.transition.Transition transition) {
        if (transition != null) {
            return transition.mo4806clone();
        }
        return transition;
    }

    private static android.transition.TransitionSet configureSharedElementsReordered(android.view.ViewGroup viewGroup, android.view.View view, android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap, android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition, java.util.ArrayList<android.view.View> arrayList, java.util.ArrayList<android.view.View> arrayList2, android.transition.Transition transition, android.transition.Transition transition2) {
        android.transition.TransitionSet sharedElementTransition;
        final android.view.View view2;
        final android.graphics.Rect rect;
        final android.app.Fragment fragment = fragmentContainerTransition.lastIn;
        final android.app.Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment != null) {
            fragment.getView().setVisibility(0);
        }
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = fragmentContainerTransition.lastInIsPop;
        if (arrayMap.isEmpty()) {
            sharedElementTransition = null;
        } else {
            sharedElementTransition = getSharedElementTransition(fragment, fragment2, z);
        }
        android.util.ArrayMap<java.lang.String, android.view.View> captureOutSharedElements = captureOutSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
        final android.util.ArrayMap<java.lang.String, android.view.View> captureInSharedElements = captureInSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            if (captureOutSharedElements != null) {
                captureOutSharedElements.clear();
            }
            if (captureInSharedElements != null) {
                captureInSharedElements.clear();
            }
            sharedElementTransition = null;
        } else {
            addSharedElementsWithMatchingNames(arrayList, captureOutSharedElements, arrayMap.keySet());
            addSharedElementsWithMatchingNames(arrayList2, captureInSharedElements, arrayMap.values());
        }
        if (transition == null && transition2 == null && sharedElementTransition == null) {
            return null;
        }
        callSharedElementStartEnd(fragment, fragment2, z, captureOutSharedElements, true);
        if (sharedElementTransition != null) {
            arrayList2.add(view);
            setSharedElementTargets(sharedElementTransition, view, arrayList);
            setOutEpicenter(sharedElementTransition, transition2, captureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            final android.graphics.Rect rect2 = new android.graphics.Rect();
            android.view.View inEpicenterView = getInEpicenterView(captureInSharedElements, fragmentContainerTransition, transition, z);
            if (inEpicenterView != null) {
                transition.setEpicenterCallback(new android.transition.Transition.EpicenterCallback() { // from class: android.app.FragmentTransition.2
                    @Override // android.transition.Transition.EpicenterCallback
                    public android.graphics.Rect onGetEpicenter(android.transition.Transition transition3) {
                        return android.graphics.Rect.this;
                    }
                });
            }
            rect = rect2;
            view2 = inEpicenterView;
        } else {
            view2 = null;
            rect = null;
        }
        com.android.internal.view.OneShotPreDrawListener.add(viewGroup, new java.lang.Runnable() { // from class: android.app.FragmentTransition$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.app.FragmentTransition.lambda$configureSharedElementsReordered$2(android.app.Fragment.this, fragment2, z, captureInSharedElements, view2, rect);
            }
        });
        return sharedElementTransition;
    }

    static /* synthetic */ void lambda$configureSharedElementsReordered$2(android.app.Fragment fragment, android.app.Fragment fragment2, boolean z, android.util.ArrayMap arrayMap, android.view.View view, android.graphics.Rect rect) {
        callSharedElementStartEnd(fragment, fragment2, z, arrayMap, false);
        if (view != null) {
            view.getBoundsOnScreen(rect);
        }
    }

    private static void addSharedElementsWithMatchingNames(java.util.ArrayList<android.view.View> arrayList, android.util.ArrayMap<java.lang.String, android.view.View> arrayMap, java.util.Collection<java.lang.String> collection) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            android.view.View valueAt = arrayMap.valueAt(size);
            if (valueAt != null && collection.contains(valueAt.getTransitionName())) {
                arrayList.add(valueAt);
            }
        }
    }

    private static android.transition.TransitionSet configureSharedElementsOrdered(android.view.ViewGroup viewGroup, final android.view.View view, final android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap, final android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition, final java.util.ArrayList<android.view.View> arrayList, final java.util.ArrayList<android.view.View> arrayList2, final android.transition.Transition transition, android.transition.Transition transition2) {
        android.transition.TransitionSet sharedElementTransition;
        android.transition.TransitionSet transitionSet;
        android.graphics.Rect rect;
        final android.app.Fragment fragment = fragmentContainerTransition.lastIn;
        final android.app.Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = fragmentContainerTransition.lastInIsPop;
        if (arrayMap.isEmpty()) {
            sharedElementTransition = null;
        } else {
            sharedElementTransition = getSharedElementTransition(fragment, fragment2, z);
        }
        android.util.ArrayMap<java.lang.String, android.view.View> captureOutSharedElements = captureOutSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            transitionSet = null;
        } else {
            arrayList.addAll(captureOutSharedElements.values());
            transitionSet = sharedElementTransition;
        }
        if (transition == null && transition2 == null && transitionSet == null) {
            return null;
        }
        callSharedElementStartEnd(fragment, fragment2, z, captureOutSharedElements, true);
        if (transitionSet == null) {
            rect = null;
        } else {
            final android.graphics.Rect rect2 = new android.graphics.Rect();
            setSharedElementTargets(transitionSet, view, arrayList);
            setOutEpicenter(transitionSet, transition2, captureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            if (transition != null) {
                transition.setEpicenterCallback(new android.transition.Transition.EpicenterCallback() { // from class: android.app.FragmentTransition.3
                    @Override // android.transition.Transition.EpicenterCallback
                    public android.graphics.Rect onGetEpicenter(android.transition.Transition transition3) {
                        if (android.graphics.Rect.this.isEmpty()) {
                            return null;
                        }
                        return android.graphics.Rect.this;
                    }
                });
            }
            rect = rect2;
        }
        final android.transition.TransitionSet transitionSet2 = transitionSet;
        final android.graphics.Rect rect3 = rect;
        com.android.internal.view.OneShotPreDrawListener.add(viewGroup, new java.lang.Runnable() { // from class: android.app.FragmentTransition$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.app.FragmentTransition.lambda$configureSharedElementsOrdered$3(android.util.ArrayMap.this, transitionSet2, fragmentContainerTransition, arrayList2, view, fragment, fragment2, z, arrayList, transition, rect3);
            }
        });
        return transitionSet;
    }

    static /* synthetic */ void lambda$configureSharedElementsOrdered$3(android.util.ArrayMap arrayMap, android.transition.TransitionSet transitionSet, android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition, java.util.ArrayList arrayList, android.view.View view, android.app.Fragment fragment, android.app.Fragment fragment2, boolean z, java.util.ArrayList arrayList2, android.transition.Transition transition, android.graphics.Rect rect) {
        android.util.ArrayMap<java.lang.String, android.view.View> captureInSharedElements = captureInSharedElements(arrayMap, transitionSet, fragmentContainerTransition);
        if (captureInSharedElements != null) {
            arrayList.addAll(captureInSharedElements.values());
            arrayList.add(view);
        }
        callSharedElementStartEnd(fragment, fragment2, z, captureInSharedElements, false);
        if (transitionSet != null) {
            transitionSet.getTargets().clear();
            transitionSet.getTargets().addAll(arrayList);
            replaceTargets(transitionSet, arrayList2, arrayList);
            android.view.View inEpicenterView = getInEpicenterView(captureInSharedElements, fragmentContainerTransition, transition, z);
            if (inEpicenterView != null) {
                inEpicenterView.getBoundsOnScreen(rect);
            }
        }
    }

    private static android.util.ArrayMap<java.lang.String, android.view.View> captureOutSharedElements(android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap, android.transition.TransitionSet transitionSet, android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition) {
        android.app.SharedElementCallback exitTransitionCallback;
        java.util.ArrayList<java.lang.String> arrayList;
        if (arrayMap.isEmpty() || transitionSet == null) {
            arrayMap.clear();
            return null;
        }
        android.app.Fragment fragment = fragmentContainerTransition.firstOut;
        android.util.ArrayMap<java.lang.String, android.view.View> arrayMap2 = new android.util.ArrayMap<>();
        fragment.getView().findNamedViews(arrayMap2);
        android.app.BackStackRecord backStackRecord = fragmentContainerTransition.firstOutTransaction;
        if (fragmentContainerTransition.firstOutIsPop) {
            exitTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = backStackRecord.mSharedElementTargetNames;
        } else {
            exitTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = backStackRecord.mSharedElementSourceNames;
        }
        arrayMap2.retainAll(arrayList);
        if (exitTransitionCallback != null) {
            exitTransitionCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                java.lang.String str = arrayList.get(size);
                android.view.View view = arrayMap2.get(str);
                if (view == null) {
                    arrayMap.remove(str);
                } else if (!str.equals(view.getTransitionName())) {
                    arrayMap.put(view.getTransitionName(), arrayMap.remove(str));
                }
            }
        } else {
            arrayMap.retainAll(arrayMap2.keySet());
        }
        return arrayMap2;
    }

    private static android.util.ArrayMap<java.lang.String, android.view.View> captureInSharedElements(android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap, android.transition.TransitionSet transitionSet, android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition) {
        android.app.SharedElementCallback enterTransitionCallback;
        java.util.ArrayList<java.lang.String> arrayList;
        java.lang.String findKeyForValue;
        android.app.Fragment fragment = fragmentContainerTransition.lastIn;
        android.view.View view = fragment.getView();
        if (arrayMap.isEmpty() || transitionSet == null || view == null) {
            arrayMap.clear();
            return null;
        }
        android.util.ArrayMap<java.lang.String, android.view.View> arrayMap2 = new android.util.ArrayMap<>();
        view.findNamedViews(arrayMap2);
        android.app.BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (fragmentContainerTransition.lastInIsPop) {
            enterTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = backStackRecord.mSharedElementSourceNames;
        } else {
            enterTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = backStackRecord.mSharedElementTargetNames;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
        }
        if (arrayList != null && enterTransitionCallback != null) {
            enterTransitionCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                java.lang.String str = arrayList.get(size);
                android.view.View view2 = arrayMap2.get(str);
                if (view2 == null) {
                    java.lang.String findKeyForValue2 = findKeyForValue(arrayMap, str);
                    if (findKeyForValue2 != null) {
                        arrayMap.remove(findKeyForValue2);
                    }
                } else if (!str.equals(view2.getTransitionName()) && (findKeyForValue = findKeyForValue(arrayMap, str)) != null) {
                    arrayMap.put(findKeyForValue, view2.getTransitionName());
                }
            }
        } else {
            retainValues(arrayMap, arrayMap2);
        }
        return arrayMap2;
    }

    private static java.lang.String findKeyForValue(android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap, java.lang.String str) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(arrayMap.valueAt(i))) {
                return arrayMap.keyAt(i);
            }
        }
        return null;
    }

    private static android.view.View getInEpicenterView(android.util.ArrayMap<java.lang.String, android.view.View> arrayMap, android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition, android.transition.Transition transition, boolean z) {
        android.app.BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (transition != null && arrayMap != null && backStackRecord.mSharedElementSourceNames != null && !backStackRecord.mSharedElementSourceNames.isEmpty()) {
            return arrayMap.get(z ? backStackRecord.mSharedElementSourceNames.get(0) : backStackRecord.mSharedElementTargetNames.get(0));
        }
        return null;
    }

    private static void setOutEpicenter(android.transition.TransitionSet transitionSet, android.transition.Transition transition, android.util.ArrayMap<java.lang.String, android.view.View> arrayMap, boolean z, android.app.BackStackRecord backStackRecord) {
        if (backStackRecord.mSharedElementSourceNames != null && !backStackRecord.mSharedElementSourceNames.isEmpty()) {
            android.view.View view = arrayMap.get(z ? backStackRecord.mSharedElementTargetNames.get(0) : backStackRecord.mSharedElementSourceNames.get(0));
            setEpicenter(transitionSet, view);
            if (transition != null) {
                setEpicenter(transition, view);
            }
        }
    }

    private static void setEpicenter(android.transition.Transition transition, android.view.View view) {
        if (view != null) {
            final android.graphics.Rect rect = new android.graphics.Rect();
            view.getBoundsOnScreen(rect);
            transition.setEpicenterCallback(new android.transition.Transition.EpicenterCallback() { // from class: android.app.FragmentTransition.4
                @Override // android.transition.Transition.EpicenterCallback
                public android.graphics.Rect onGetEpicenter(android.transition.Transition transition2) {
                    return android.graphics.Rect.this;
                }
            });
        }
    }

    private static void retainValues(android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap, android.util.ArrayMap<java.lang.String, android.view.View> arrayMap2) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            if (!arrayMap2.containsKey(arrayMap.valueAt(size))) {
                arrayMap.removeAt(size);
            }
        }
    }

    private static void callSharedElementStartEnd(android.app.Fragment fragment, android.app.Fragment fragment2, boolean z, android.util.ArrayMap<java.lang.String, android.view.View> arrayMap, boolean z2) {
        android.app.SharedElementCallback enterTransitionCallback;
        if (z) {
            enterTransitionCallback = fragment2.getEnterTransitionCallback();
        } else {
            enterTransitionCallback = fragment.getEnterTransitionCallback();
        }
        if (enterTransitionCallback != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            int size = arrayMap == null ? 0 : arrayMap.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(arrayMap.keyAt(i));
                arrayList.add(arrayMap.valueAt(i));
            }
            if (z2) {
                enterTransitionCallback.onSharedElementStart(arrayList2, arrayList, null);
            } else {
                enterTransitionCallback.onSharedElementEnd(arrayList2, arrayList, null);
            }
        }
    }

    private static void setSharedElementTargets(android.transition.TransitionSet transitionSet, android.view.View view, java.util.ArrayList<android.view.View> arrayList) {
        java.util.List<android.view.View> targets = transitionSet.getTargets();
        targets.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            bfsAddViewChildren(targets, arrayList.get(i));
        }
        targets.add(view);
        arrayList.add(view);
        addTargets(transitionSet, arrayList);
    }

    private static void bfsAddViewChildren(java.util.List<android.view.View> list, android.view.View view) {
        int size = list.size();
        if (containedBeforeIndex(list, view, size)) {
            return;
        }
        list.add(view);
        for (int i = size; i < list.size(); i++) {
            android.view.View view2 = list.get(i);
            if (view2 instanceof android.view.ViewGroup) {
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) view2;
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    android.view.View childAt = viewGroup.getChildAt(i2);
                    if (!containedBeforeIndex(list, childAt, size)) {
                        list.add(childAt);
                    }
                }
            }
        }
    }

    private static boolean containedBeforeIndex(java.util.List<android.view.View> list, android.view.View view, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (list.get(i2) == view) {
                return true;
            }
        }
        return false;
    }

    private static void scheduleRemoveTargets(android.transition.Transition transition, final android.transition.Transition transition2, final java.util.ArrayList<android.view.View> arrayList, final android.transition.Transition transition3, final java.util.ArrayList<android.view.View> arrayList2, final android.transition.TransitionSet transitionSet, final java.util.ArrayList<android.view.View> arrayList3) {
        transition.addListener(new android.transition.TransitionListenerAdapter() { // from class: android.app.FragmentTransition.5
            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionStart(android.transition.Transition transition4) {
                if (android.transition.Transition.this != null) {
                    android.app.FragmentTransition.replaceTargets(android.transition.Transition.this, arrayList, null);
                }
                if (transition3 != null) {
                    android.app.FragmentTransition.replaceTargets(transition3, arrayList2, null);
                }
                if (transitionSet != null) {
                    android.app.FragmentTransition.replaceTargets(transitionSet, arrayList3, null);
                }
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionEnd(android.transition.Transition transition4) {
                transition4.removeListener(this);
            }
        });
    }

    public static void replaceTargets(android.transition.Transition transition, java.util.ArrayList<android.view.View> arrayList, java.util.ArrayList<android.view.View> arrayList2) {
        java.util.List<android.view.View> targets;
        int i = 0;
        if (transition instanceof android.transition.TransitionSet) {
            android.transition.TransitionSet transitionSet = (android.transition.TransitionSet) transition;
            int transitionCount = transitionSet.getTransitionCount();
            while (i < transitionCount) {
                replaceTargets(transitionSet.getTransitionAt(i), arrayList, arrayList2);
                i++;
            }
            return;
        }
        if (!hasSimpleTarget(transition) && (targets = transition.getTargets()) != null && targets.size() == arrayList.size() && targets.containsAll(arrayList)) {
            int size = arrayList2 == null ? 0 : arrayList2.size();
            while (i < size) {
                transition.addTarget(arrayList2.get(i));
                i++;
            }
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                transition.removeTarget(arrayList.get(size2));
            }
        }
    }

    public static void addTargets(android.transition.Transition transition, java.util.ArrayList<android.view.View> arrayList) {
        if (transition == null) {
            return;
        }
        int i = 0;
        if (transition instanceof android.transition.TransitionSet) {
            android.transition.TransitionSet transitionSet = (android.transition.TransitionSet) transition;
            int transitionCount = transitionSet.getTransitionCount();
            while (i < transitionCount) {
                addTargets(transitionSet.getTransitionAt(i), arrayList);
                i++;
            }
            return;
        }
        if (!hasSimpleTarget(transition) && isNullOrEmpty(transition.getTargets())) {
            int size = arrayList.size();
            while (i < size) {
                transition.addTarget(arrayList.get(i));
                i++;
            }
        }
    }

    private static boolean hasSimpleTarget(android.transition.Transition transition) {
        return (isNullOrEmpty(transition.getTargetIds()) && isNullOrEmpty(transition.getTargetNames()) && isNullOrEmpty(transition.getTargetTypes())) ? false : true;
    }

    private static boolean isNullOrEmpty(java.util.List list) {
        return list == null || list.isEmpty();
    }

    private static java.util.ArrayList<android.view.View> configureEnteringExitingViews(android.transition.Transition transition, android.app.Fragment fragment, java.util.ArrayList<android.view.View> arrayList, android.view.View view) {
        if (transition == null) {
            return null;
        }
        java.util.ArrayList<android.view.View> arrayList2 = new java.util.ArrayList<>();
        android.view.View view2 = fragment.getView();
        if (view2 != null) {
            view2.captureTransitioningViews(arrayList2);
        }
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (!arrayList2.isEmpty()) {
            arrayList2.add(view);
            addTargets(transition, arrayList2);
            return arrayList2;
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setViewVisibility(java.util.ArrayList<android.view.View> arrayList, int i) {
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            arrayList.get(size).setVisibility(i);
        }
    }

    private static android.transition.Transition mergeTransitions(android.transition.Transition transition, android.transition.Transition transition2, android.transition.Transition transition3, android.app.Fragment fragment, boolean z) {
        boolean z2;
        if (transition != null && transition2 != null && fragment != null) {
            z2 = z ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap();
        } else {
            z2 = true;
        }
        if (z2) {
            android.transition.TransitionSet transitionSet = new android.transition.TransitionSet();
            if (transition != null) {
                transitionSet.addTransition(transition);
            }
            if (transition2 != null) {
                transitionSet.addTransition(transition2);
            }
            if (transition3 != null) {
                transitionSet.addTransition(transition3);
                return transitionSet;
            }
            return transitionSet;
        }
        if (transition2 != null && transition != null) {
            transition = new android.transition.TransitionSet().addTransition(transition2).addTransition(transition).setOrdering(1);
        } else if (transition2 != null) {
            transition = transition2;
        } else if (transition == null) {
            transition = null;
        }
        if (transition3 != null) {
            android.transition.TransitionSet transitionSet2 = new android.transition.TransitionSet();
            if (transition != null) {
                transitionSet2.addTransition(transition);
            }
            transitionSet2.addTransition(transition3);
            return transitionSet2;
        }
        return transition;
    }

    public static void calculateFragments(android.app.BackStackRecord backStackRecord, android.util.SparseArray<android.app.FragmentTransition.FragmentContainerTransition> sparseArray, boolean z) {
        int size = backStackRecord.mOps.size();
        for (int i = 0; i < size; i++) {
            addToFirstInLastOut(backStackRecord, backStackRecord.mOps.get(i), sparseArray, false, z);
        }
    }

    public static void calculatePopFragments(android.app.BackStackRecord backStackRecord, android.util.SparseArray<android.app.FragmentTransition.FragmentContainerTransition> sparseArray, boolean z) {
        if (!backStackRecord.mManager.mContainer.onHasView()) {
            return;
        }
        for (int size = backStackRecord.mOps.size() - 1; size >= 0; size--) {
            addToFirstInLastOut(backStackRecord, backStackRecord.mOps.get(size), sparseArray, true, z);
        }
    }

    private static void addToFirstInLastOut(android.app.BackStackRecord backStackRecord, android.app.BackStackRecord.Op op, android.util.SparseArray<android.app.FragmentTransition.FragmentContainerTransition> sparseArray, boolean z, boolean z2) {
        int i;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition;
        android.app.Fragment fragment = op.fragment;
        if (fragment == null || (i = fragment.mContainerId) == 0) {
            return;
        }
        boolean z10 = false;
        switch (z ? INVERSE_OPS[op.cmd] : op.cmd) {
            case 1:
            case 7:
                if (z2) {
                    z3 = fragment.mIsNewlyAdded;
                } else {
                    z3 = (fragment.mAdded || fragment.mHidden) ? false : true;
                }
                z4 = false;
                z5 = false;
                z10 = z3;
                z6 = true;
                break;
            case 2:
            default:
                z6 = false;
                z4 = false;
                z5 = false;
                break;
            case 3:
            case 6:
                if (z2) {
                    z7 = !fragment.mAdded && fragment.mView != null && fragment.mView.getVisibility() == 0 && fragment.mView.getTransitionAlpha() > 0.0f;
                } else {
                    z7 = fragment.mAdded && !fragment.mHidden;
                }
                z5 = z7;
                z6 = false;
                z4 = true;
                break;
            case 4:
                if (z2) {
                    z8 = fragment.mHiddenChanged && fragment.mAdded && fragment.mHidden;
                } else {
                    z8 = fragment.mAdded && !fragment.mHidden;
                }
                z5 = z8;
                z6 = false;
                z4 = true;
                break;
            case 5:
                if (z2) {
                    z9 = fragment.mHiddenChanged && !fragment.mHidden && fragment.mAdded;
                } else {
                    z9 = fragment.mHidden;
                }
                z4 = false;
                z5 = false;
                z10 = z9;
                z6 = true;
                break;
        }
        android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition2 = sparseArray.get(i);
        if (!z10) {
            fragmentContainerTransition = fragmentContainerTransition2;
        } else {
            android.app.FragmentTransition.FragmentContainerTransition ensureContainer = ensureContainer(fragmentContainerTransition2, sparseArray, i);
            ensureContainer.lastIn = fragment;
            ensureContainer.lastInIsPop = z;
            ensureContainer.lastInTransaction = backStackRecord;
            fragmentContainerTransition = ensureContainer;
        }
        if (!z2 && z6) {
            if (fragmentContainerTransition != null && fragmentContainerTransition.firstOut == fragment) {
                fragmentContainerTransition.firstOut = null;
            }
            android.app.FragmentManagerImpl fragmentManagerImpl = backStackRecord.mManager;
            if (fragment.mState < 1 && fragmentManagerImpl.mCurState >= 1 && fragmentManagerImpl.mHost.getContext().getApplicationInfo().targetSdkVersion >= 24 && !backStackRecord.mReorderingAllowed) {
                fragmentManagerImpl.makeActive(fragment);
                fragmentManagerImpl.moveToState(fragment, 1, 0, 0, false);
            }
        }
        if (z5 && (fragmentContainerTransition == null || fragmentContainerTransition.firstOut == null)) {
            fragmentContainerTransition = ensureContainer(fragmentContainerTransition, sparseArray, i);
            fragmentContainerTransition.firstOut = fragment;
            fragmentContainerTransition.firstOutIsPop = z;
            fragmentContainerTransition.firstOutTransaction = backStackRecord;
        }
        if (!z2 && z4 && fragmentContainerTransition != null && fragmentContainerTransition.lastIn == fragment) {
            fragmentContainerTransition.lastIn = null;
        }
    }

    private static android.app.FragmentTransition.FragmentContainerTransition ensureContainer(android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition, android.util.SparseArray<android.app.FragmentTransition.FragmentContainerTransition> sparseArray, int i) {
        if (fragmentContainerTransition == null) {
            android.app.FragmentTransition.FragmentContainerTransition fragmentContainerTransition2 = new android.app.FragmentTransition.FragmentContainerTransition();
            sparseArray.put(i, fragmentContainerTransition2);
            return fragmentContainerTransition2;
        }
        return fragmentContainerTransition;
    }
}
