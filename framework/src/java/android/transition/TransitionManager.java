package android.transition;

/* loaded from: classes3.dex */
public class TransitionManager {
    private static java.lang.String LOG_TAG = "TransitionManager";
    private static android.transition.Transition sDefaultTransition = new android.transition.AutoTransition();
    private static final java.lang.String[] EMPTY_STRINGS = new java.lang.String[0];
    private static java.lang.ThreadLocal<java.lang.ref.WeakReference<android.util.ArrayMap<android.view.ViewGroup, java.util.ArrayList<android.transition.Transition>>>> sRunningTransitions = new java.lang.ThreadLocal<>();
    private static java.util.ArrayList<android.view.ViewGroup> sPendingTransitions = new java.util.ArrayList<>();
    android.util.ArrayMap<android.transition.Scene, android.transition.Transition> mSceneTransitions = new android.util.ArrayMap<>();
    android.util.ArrayMap<android.transition.Scene, android.util.ArrayMap<android.transition.Scene, android.transition.Transition>> mScenePairTransitions = new android.util.ArrayMap<>();

    public void setDefaultTransition(android.transition.Transition transition) {
        sDefaultTransition = transition;
    }

    public static android.transition.Transition getDefaultTransition() {
        return sDefaultTransition;
    }

    public void setTransition(android.transition.Scene scene, android.transition.Transition transition) {
        this.mSceneTransitions.put(scene, transition);
    }

    public void setTransition(android.transition.Scene scene, android.transition.Scene scene2, android.transition.Transition transition) {
        android.util.ArrayMap<android.transition.Scene, android.transition.Transition> arrayMap = this.mScenePairTransitions.get(scene2);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>();
            this.mScenePairTransitions.put(scene2, arrayMap);
        }
        arrayMap.put(scene, transition);
    }

    public android.transition.Transition getTransition(android.transition.Scene scene) {
        android.transition.Scene currentScene;
        android.util.ArrayMap<android.transition.Scene, android.transition.Transition> arrayMap;
        android.transition.Transition transition;
        android.view.ViewGroup sceneRoot = scene.getSceneRoot();
        if (sceneRoot != null && (currentScene = android.transition.Scene.getCurrentScene(sceneRoot)) != null && (arrayMap = this.mScenePairTransitions.get(scene)) != null && (transition = arrayMap.get(currentScene)) != null) {
            return transition;
        }
        android.transition.Transition transition2 = this.mSceneTransitions.get(scene);
        return transition2 != null ? transition2 : sDefaultTransition;
    }

    private static void changeScene(android.transition.Scene scene, android.transition.Transition transition) {
        android.view.ViewGroup sceneRoot = scene.getSceneRoot();
        if (!sPendingTransitions.contains(sceneRoot)) {
            android.transition.Scene currentScene = android.transition.Scene.getCurrentScene(sceneRoot);
            if (transition == null) {
                if (currentScene != null) {
                    currentScene.exit();
                }
                scene.enter();
                return;
            }
            sPendingTransitions.add(sceneRoot);
            android.transition.Transition mo4806clone = transition.mo4806clone();
            mo4806clone.setSceneRoot(sceneRoot);
            if (currentScene != null && currentScene.isCreatedFromLayoutResource()) {
                mo4806clone.setCanRemoveViews(true);
            }
            sceneChangeSetup(sceneRoot, mo4806clone);
            scene.enter();
            sceneChangeRunTransition(sceneRoot, mo4806clone);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.ArrayMap<android.view.ViewGroup, java.util.ArrayList<android.transition.Transition>> getRunningTransitions() {
        android.util.ArrayMap<android.view.ViewGroup, java.util.ArrayList<android.transition.Transition>> arrayMap;
        java.lang.ref.WeakReference<android.util.ArrayMap<android.view.ViewGroup, java.util.ArrayList<android.transition.Transition>>> weakReference = sRunningTransitions.get();
        if (weakReference != null && (arrayMap = weakReference.get()) != null) {
            return arrayMap;
        }
        android.util.ArrayMap<android.view.ViewGroup, java.util.ArrayList<android.transition.Transition>> arrayMap2 = new android.util.ArrayMap<>();
        sRunningTransitions.set(new java.lang.ref.WeakReference<>(arrayMap2));
        return arrayMap2;
    }

    private static void sceneChangeRunTransition(android.view.ViewGroup viewGroup, android.transition.Transition transition) {
        if (transition != null && viewGroup != null) {
            android.transition.TransitionManager.MultiListener multiListener = new android.transition.TransitionManager.MultiListener(transition, viewGroup);
            viewGroup.addOnAttachStateChangeListener(multiListener);
            viewGroup.getViewTreeObserver().addOnPreDrawListener(multiListener);
        }
    }

    private static class MultiListener implements android.view.ViewTreeObserver.OnPreDrawListener, android.view.View.OnAttachStateChangeListener {
        android.view.ViewGroup mSceneRoot;
        android.transition.Transition mTransition;
        final android.view.ViewTreeObserver mViewTreeObserver;

        MultiListener(android.transition.Transition transition, android.view.ViewGroup viewGroup) {
            this.mTransition = transition;
            this.mSceneRoot = viewGroup;
            this.mViewTreeObserver = this.mSceneRoot.getViewTreeObserver();
        }

        private void removeListeners() {
            if (this.mViewTreeObserver.isAlive()) {
                this.mViewTreeObserver.removeOnPreDrawListener(this);
            } else {
                this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
            removeListeners();
            android.transition.TransitionManager.sPendingTransitions.remove(this.mSceneRoot);
            java.util.ArrayList arrayList = (java.util.ArrayList) android.transition.TransitionManager.getRunningTransitions().get(this.mSceneRoot);
            if (arrayList != null && arrayList.size() > 0) {
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((android.transition.Transition) it.next()).resume(this.mSceneRoot);
                }
            }
            this.mTransition.clearValues(true);
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            removeListeners();
            if (!android.transition.TransitionManager.sPendingTransitions.remove(this.mSceneRoot)) {
                return true;
            }
            final android.util.ArrayMap runningTransitions = android.transition.TransitionManager.getRunningTransitions();
            java.util.ArrayList arrayList = (java.util.ArrayList) runningTransitions.get(this.mSceneRoot);
            java.util.ArrayList arrayList2 = null;
            if (arrayList == null) {
                arrayList = new java.util.ArrayList();
                runningTransitions.put(this.mSceneRoot, arrayList);
            } else if (arrayList.size() > 0) {
                arrayList2 = new java.util.ArrayList(arrayList);
            }
            arrayList.add(this.mTransition);
            this.mTransition.addListener(new android.transition.TransitionListenerAdapter() { // from class: android.transition.TransitionManager.MultiListener.1
                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(android.transition.Transition transition) {
                    ((java.util.ArrayList) runningTransitions.get(android.transition.TransitionManager.MultiListener.this.mSceneRoot)).remove(transition);
                    transition.removeListener(this);
                }
            });
            this.mTransition.captureValues(this.mSceneRoot, false);
            if (arrayList2 != null) {
                java.util.Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    ((android.transition.Transition) it.next()).resume(this.mSceneRoot);
                }
            }
            this.mTransition.playTransition(this.mSceneRoot);
            return true;
        }
    }

    private static void sceneChangeSetup(android.view.ViewGroup viewGroup, android.transition.Transition transition) {
        java.util.ArrayList<android.transition.Transition> arrayList = getRunningTransitions().get(viewGroup);
        if (arrayList != null && arrayList.size() > 0) {
            java.util.Iterator<android.transition.Transition> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().pause(viewGroup);
            }
        }
        if (transition != null) {
            transition.captureValues(viewGroup, true);
        }
        android.transition.Scene currentScene = android.transition.Scene.getCurrentScene(viewGroup);
        if (currentScene != null) {
            currentScene.exit();
        }
    }

    public void transitionTo(android.transition.Scene scene) {
        changeScene(scene, getTransition(scene));
    }

    public static void go(android.transition.Scene scene) {
        changeScene(scene, sDefaultTransition);
    }

    public static void go(android.transition.Scene scene, android.transition.Transition transition) {
        changeScene(scene, transition);
    }

    public static void beginDelayedTransition(android.view.ViewGroup viewGroup) {
        beginDelayedTransition(viewGroup, null);
    }

    public static void beginDelayedTransition(android.view.ViewGroup viewGroup, android.transition.Transition transition) {
        if (!sPendingTransitions.contains(viewGroup) && viewGroup.isLaidOut()) {
            sPendingTransitions.add(viewGroup);
            if (transition == null) {
                transition = sDefaultTransition;
            }
            android.transition.Transition mo4806clone = transition.mo4806clone();
            sceneChangeSetup(viewGroup, mo4806clone);
            android.transition.Scene.setCurrentScene(viewGroup, null);
            sceneChangeRunTransition(viewGroup, mo4806clone);
        }
    }

    public static void endTransitions(android.view.ViewGroup viewGroup) {
        sPendingTransitions.remove(viewGroup);
        java.util.ArrayList<android.transition.Transition> arrayList = getRunningTransitions().get(viewGroup);
        if (arrayList != null && !arrayList.isEmpty()) {
            java.util.ArrayList arrayList2 = new java.util.ArrayList(arrayList);
            for (int size = arrayList2.size() - 1; size >= 0; size--) {
                ((android.transition.Transition) arrayList2.get(size)).forceToEnd(viewGroup);
            }
        }
    }
}
