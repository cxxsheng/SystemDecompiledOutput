package android.view;

/* loaded from: classes4.dex */
public class FocusFinder {
    private static final java.lang.ThreadLocal<android.view.FocusFinder> tlFocusFinder = new java.lang.ThreadLocal<android.view.FocusFinder>() { // from class: android.view.FocusFinder.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public android.view.FocusFinder initialValue() {
            return new android.view.FocusFinder();
        }
    };
    final android.graphics.Rect mBestCandidateRect;
    private final android.view.FocusFinder.FocusSorter mFocusSorter;
    final android.graphics.Rect mFocusedRect;
    final android.graphics.Rect mOtherRect;
    private final java.util.ArrayList<android.view.View> mTempList;
    private final android.view.FocusFinder.UserSpecifiedFocusComparator mUserSpecifiedClusterComparator;
    private final android.view.FocusFinder.UserSpecifiedFocusComparator mUserSpecifiedFocusComparator;

    public static android.view.FocusFinder getInstance() {
        return tlFocusFinder.get();
    }

    static /* synthetic */ android.view.View lambda$new$0(android.view.View view, android.view.View view2) {
        if (isValidId(view2.getNextFocusForwardId())) {
            return view2.findUserSetNextFocus(view, 2);
        }
        return null;
    }

    static /* synthetic */ android.view.View lambda$new$1(android.view.View view, android.view.View view2) {
        if (isValidId(view2.getNextClusterForwardId())) {
            return view2.findUserSetNextKeyboardNavigationCluster(view, 2);
        }
        return null;
    }

    private FocusFinder() {
        this.mFocusedRect = new android.graphics.Rect();
        this.mOtherRect = new android.graphics.Rect();
        this.mBestCandidateRect = new android.graphics.Rect();
        this.mUserSpecifiedFocusComparator = new android.view.FocusFinder.UserSpecifiedFocusComparator(new android.view.FocusFinder.UserSpecifiedFocusComparator.NextFocusGetter() { // from class: android.view.FocusFinder$$ExternalSyntheticLambda0
            @Override // android.view.FocusFinder.UserSpecifiedFocusComparator.NextFocusGetter
            public final android.view.View get(android.view.View view, android.view.View view2) {
                return android.view.FocusFinder.lambda$new$0(view, view2);
            }
        });
        this.mUserSpecifiedClusterComparator = new android.view.FocusFinder.UserSpecifiedFocusComparator(new android.view.FocusFinder.UserSpecifiedFocusComparator.NextFocusGetter() { // from class: android.view.FocusFinder$$ExternalSyntheticLambda1
            @Override // android.view.FocusFinder.UserSpecifiedFocusComparator.NextFocusGetter
            public final android.view.View get(android.view.View view, android.view.View view2) {
                return android.view.FocusFinder.lambda$new$1(view, view2);
            }
        });
        this.mFocusSorter = new android.view.FocusFinder.FocusSorter();
        this.mTempList = new java.util.ArrayList<>();
    }

    public final android.view.View findNextFocus(android.view.ViewGroup viewGroup, android.view.View view, int i) {
        return findNextFocus(viewGroup, view, null, i);
    }

    public android.view.View findNextFocusFromRect(android.view.ViewGroup viewGroup, android.graphics.Rect rect, int i) {
        this.mFocusedRect.set(rect);
        return findNextFocus(viewGroup, null, this.mFocusedRect, i);
    }

    private android.view.View findNextFocus(android.view.ViewGroup viewGroup, android.view.View view, android.graphics.Rect rect, int i) {
        android.view.View view2;
        android.view.ViewGroup effectiveRoot = getEffectiveRoot(viewGroup, view);
        if (view == null) {
            view2 = null;
        } else {
            view2 = findNextUserSpecifiedFocus(effectiveRoot, view, i);
        }
        if (view2 != null) {
            return view2;
        }
        java.util.ArrayList<android.view.View> arrayList = this.mTempList;
        try {
            arrayList.clear();
            effectiveRoot.addFocusables(arrayList, i);
            if (!arrayList.isEmpty()) {
                view2 = findNextFocus(effectiveRoot, view, rect, i, arrayList);
            }
            return view2;
        } finally {
            arrayList.clear();
        }
    }

    private android.view.ViewGroup getEffectiveRoot(android.view.ViewGroup viewGroup, android.view.View view) {
        if (view == null || view == viewGroup) {
            return viewGroup;
        }
        android.view.ViewParent parent = view.getParent();
        android.view.ViewGroup viewGroup2 = null;
        while (parent != viewGroup) {
            android.view.ViewGroup viewGroup3 = (android.view.ViewGroup) parent;
            if (viewGroup3.getTouchscreenBlocksFocus() && view.getContext().getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_TOUCHSCREEN) && viewGroup3.isKeyboardNavigationCluster()) {
                viewGroup2 = viewGroup3;
            }
            parent = parent.getParent();
            if (!(parent instanceof android.view.ViewGroup)) {
                return viewGroup;
            }
        }
        return viewGroup2 != null ? viewGroup2 : viewGroup;
    }

    public android.view.View findNextKeyboardNavigationCluster(android.view.View view, android.view.View view2, int i) {
        android.view.View view3;
        if (view2 == null) {
            view3 = null;
        } else {
            view3 = findNextUserSpecifiedKeyboardNavigationCluster(view, view2, i);
            if (view3 != null) {
                return view3;
            }
        }
        java.util.ArrayList<android.view.View> arrayList = this.mTempList;
        try {
            arrayList.clear();
            view.addKeyboardNavigationClusters(arrayList, i);
            if (!arrayList.isEmpty()) {
                view3 = findNextKeyboardNavigationCluster(view, view2, arrayList, i);
            }
            return view3;
        } finally {
            arrayList.clear();
        }
    }

    private android.view.View findNextUserSpecifiedKeyboardNavigationCluster(android.view.View view, android.view.View view2, int i) {
        android.view.View findUserSetNextKeyboardNavigationCluster = view2.findUserSetNextKeyboardNavigationCluster(view, i);
        if (findUserSetNextKeyboardNavigationCluster != null && findUserSetNextKeyboardNavigationCluster.hasFocusable()) {
            return findUserSetNextKeyboardNavigationCluster;
        }
        return null;
    }

    private android.view.View findNextUserSpecifiedFocus(android.view.ViewGroup viewGroup, android.view.View view, int i) {
        android.view.View findUserSetNextFocus = view.findUserSetNextFocus(viewGroup, i);
        android.view.View view2 = findUserSetNextFocus;
        boolean z = true;
        while (findUserSetNextFocus != null) {
            if (findUserSetNextFocus.isFocusable() && findUserSetNextFocus.getVisibility() == 0 && (!findUserSetNextFocus.isInTouchMode() || findUserSetNextFocus.isFocusableInTouchMode())) {
                return findUserSetNextFocus;
            }
            findUserSetNextFocus = findUserSetNextFocus.findUserSetNextFocus(viewGroup, i);
            z = !z;
            if (z && (view2 = view2.findUserSetNextFocus(viewGroup, i)) == findUserSetNextFocus) {
                return null;
            }
        }
        return null;
    }

    private android.view.View findNextFocus(android.view.ViewGroup viewGroup, android.view.View view, android.graphics.Rect rect, int i, java.util.ArrayList<android.view.View> arrayList) {
        android.graphics.Rect rect2;
        if (view != null) {
            if (rect == null) {
                rect = this.mFocusedRect;
            }
            view.getFocusedRect(rect);
            viewGroup.offsetDescendantRectToMyCoords(view, rect);
            rect2 = rect;
        } else if (rect != null) {
            rect2 = rect;
        } else {
            android.graphics.Rect rect3 = this.mFocusedRect;
            switch (i) {
                case 1:
                    if (viewGroup.isLayoutRtl()) {
                        setFocusTopLeft(viewGroup, rect3);
                        break;
                    } else {
                        setFocusBottomRight(viewGroup, rect3);
                        break;
                    }
                case 2:
                    if (viewGroup.isLayoutRtl()) {
                        setFocusBottomRight(viewGroup, rect3);
                        break;
                    } else {
                        setFocusTopLeft(viewGroup, rect3);
                        break;
                    }
                case 17:
                case 33:
                    setFocusBottomRight(viewGroup, rect3);
                    break;
                case 66:
                case 130:
                    setFocusTopLeft(viewGroup, rect3);
                    break;
            }
            rect2 = rect3;
        }
        switch (i) {
            case 1:
            case 2:
                return findNextFocusInRelativeDirection(arrayList, viewGroup, view, rect2, i);
            case 17:
            case 33:
            case 66:
            case 130:
                return findNextFocusInAbsoluteDirection(arrayList, viewGroup, view, rect2, i);
            default:
                throw new java.lang.IllegalArgumentException("Unknown direction: " + i);
        }
    }

    private android.view.View findNextKeyboardNavigationCluster(android.view.View view, android.view.View view2, java.util.List<android.view.View> list, int i) {
        try {
            this.mUserSpecifiedClusterComparator.setFocusables(list, view);
            java.util.Collections.sort(list, this.mUserSpecifiedClusterComparator);
            this.mUserSpecifiedClusterComparator.recycle();
            int size = list.size();
            switch (i) {
                case 1:
                case 17:
                case 33:
                    return getPreviousKeyboardNavigationCluster(view, view2, list, size);
                case 2:
                case 66:
                case 130:
                    return getNextKeyboardNavigationCluster(view, view2, list, size);
                default:
                    throw new java.lang.IllegalArgumentException("Unknown direction: " + i);
            }
        } catch (java.lang.Throwable th) {
            this.mUserSpecifiedClusterComparator.recycle();
            throw th;
        }
    }

    private android.view.View findNextFocusInRelativeDirection(java.util.ArrayList<android.view.View> arrayList, android.view.ViewGroup viewGroup, android.view.View view, android.graphics.Rect rect, int i) {
        try {
            this.mUserSpecifiedFocusComparator.setFocusables(arrayList, viewGroup);
            java.util.Collections.sort(arrayList, this.mUserSpecifiedFocusComparator);
            this.mUserSpecifiedFocusComparator.recycle();
            int size = arrayList.size();
            android.view.View view2 = null;
            if (size < 2) {
                return null;
            }
            boolean[] zArr = new boolean[1];
            switch (i) {
                case 1:
                    view2 = getPreviousFocusable(view, arrayList, size, zArr);
                    break;
                case 2:
                    view2 = getNextFocusable(view, arrayList, size, zArr);
                    break;
            }
            if (viewGroup != null && viewGroup.mAttachInfo != null && viewGroup == viewGroup.getRootView()) {
                viewGroup.mAttachInfo.mNextFocusLooped = zArr[0];
            }
            return view2 != null ? view2 : arrayList.get(size - 1);
        } catch (java.lang.Throwable th) {
            this.mUserSpecifiedFocusComparator.recycle();
            throw th;
        }
    }

    private void setFocusBottomRight(android.view.ViewGroup viewGroup, android.graphics.Rect rect) {
        int scrollY = viewGroup.getScrollY() + viewGroup.getHeight();
        int scrollX = viewGroup.getScrollX() + viewGroup.getWidth();
        rect.set(scrollX, scrollY, scrollX, scrollY);
    }

    private void setFocusTopLeft(android.view.ViewGroup viewGroup, android.graphics.Rect rect) {
        int scrollY = viewGroup.getScrollY();
        int scrollX = viewGroup.getScrollX();
        rect.set(scrollX, scrollY, scrollX, scrollY);
    }

    android.view.View findNextFocusInAbsoluteDirection(java.util.ArrayList<android.view.View> arrayList, android.view.ViewGroup viewGroup, android.view.View view, android.graphics.Rect rect, int i) {
        this.mBestCandidateRect.set(rect);
        switch (i) {
            case 17:
                this.mBestCandidateRect.offset(rect.width() + 1, 0);
                break;
            case 33:
                this.mBestCandidateRect.offset(0, rect.height() + 1);
                break;
            case 66:
                this.mBestCandidateRect.offset(-(rect.width() + 1), 0);
                break;
            case 130:
                this.mBestCandidateRect.offset(0, -(rect.height() + 1));
                break;
        }
        int size = arrayList.size();
        android.view.View view2 = null;
        for (int i2 = 0; i2 < size; i2++) {
            android.view.View view3 = arrayList.get(i2);
            if (view3 != view && view3 != viewGroup) {
                view3.getFocusedRect(this.mOtherRect);
                viewGroup.offsetDescendantRectToMyCoords(view3, this.mOtherRect);
                if (isBetterCandidate(i, rect, this.mOtherRect, this.mBestCandidateRect)) {
                    this.mBestCandidateRect.set(this.mOtherRect);
                    view2 = view3;
                }
            }
        }
        return view2;
    }

    private static android.view.View getNextFocusable(android.view.View view, java.util.ArrayList<android.view.View> arrayList, int i, boolean[] zArr) {
        int lastIndexOf;
        int i2;
        if (i < 2) {
            return null;
        }
        if (view == null || (lastIndexOf = arrayList.lastIndexOf(view)) < 0 || (i2 = lastIndexOf + 1) >= i) {
            zArr[0] = true;
            return arrayList.get(0);
        }
        return arrayList.get(i2);
    }

    private static android.view.View getPreviousFocusable(android.view.View view, java.util.ArrayList<android.view.View> arrayList, int i, boolean[] zArr) {
        int indexOf;
        if (i < 2) {
            return null;
        }
        if (view == null || (indexOf = arrayList.indexOf(view)) <= 0) {
            zArr[0] = true;
            return arrayList.get(i - 1);
        }
        return arrayList.get(indexOf - 1);
    }

    private static android.view.View getNextKeyboardNavigationCluster(android.view.View view, android.view.View view2, java.util.List<android.view.View> list, int i) {
        int i2;
        if (view2 == null) {
            return list.get(0);
        }
        int lastIndexOf = list.lastIndexOf(view2);
        if (lastIndexOf >= 0 && (i2 = lastIndexOf + 1) < i) {
            return list.get(i2);
        }
        return view;
    }

    private static android.view.View getPreviousKeyboardNavigationCluster(android.view.View view, android.view.View view2, java.util.List<android.view.View> list, int i) {
        if (view2 == null) {
            return list.get(i - 1);
        }
        int indexOf = list.indexOf(view2);
        if (indexOf > 0) {
            return list.get(indexOf - 1);
        }
        return view;
    }

    boolean isBetterCandidate(int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        if (!isCandidate(rect, rect2, i)) {
            return false;
        }
        if (isCandidate(rect, rect3, i) && !beamBeats(i, rect, rect2, rect3)) {
            return !beamBeats(i, rect, rect3, rect2) && getWeightedDistanceFor((long) majorAxisDistance(i, rect, rect2), (long) minorAxisDistance(i, rect, rect2)) < getWeightedDistanceFor((long) majorAxisDistance(i, rect, rect3), (long) minorAxisDistance(i, rect, rect3));
        }
        return true;
    }

    boolean beamBeats(int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        boolean beamsOverlap = beamsOverlap(i, rect, rect2);
        if (beamsOverlap(i, rect, rect3) || !beamsOverlap) {
            return false;
        }
        return !isToDirectionOf(i, rect, rect3) || i == 17 || i == 66 || majorAxisDistance(i, rect, rect2) < majorAxisDistanceToFarEdge(i, rect, rect3);
    }

    long getWeightedDistanceFor(long j, long j2) {
        return (13 * j * j) + (j2 * j2);
    }

    boolean isCandidate(android.graphics.Rect rect, android.graphics.Rect rect2, int i) {
        switch (i) {
            case 17:
                return (rect.right > rect2.right || rect.left >= rect2.right) && rect.left > rect2.left;
            case 33:
                return (rect.bottom > rect2.bottom || rect.top >= rect2.bottom) && rect.top > rect2.top;
            case 66:
                return (rect.left < rect2.left || rect.right <= rect2.left) && rect.right < rect2.right;
            case 130:
                return (rect.top < rect2.top || rect.bottom <= rect2.top) && rect.bottom < rect2.bottom;
            default:
                throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    boolean beamsOverlap(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        switch (i) {
            case 17:
            case 66:
                return rect2.bottom > rect.top && rect2.top < rect.bottom;
            case 33:
            case 130:
                return rect2.right > rect.left && rect2.left < rect.right;
            default:
                throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    boolean isToDirectionOf(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        switch (i) {
            case 17:
                return rect.left >= rect2.right;
            case 33:
                return rect.top >= rect2.bottom;
            case 66:
                return rect.right <= rect2.left;
            case 130:
                return rect.bottom <= rect2.top;
            default:
                throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    static int majorAxisDistance(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        return java.lang.Math.max(0, majorAxisDistanceRaw(i, rect, rect2));
    }

    static int majorAxisDistanceRaw(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        switch (i) {
            case 17:
                return rect.left - rect2.right;
            case 33:
                return rect.top - rect2.bottom;
            case 66:
                return rect2.left - rect.right;
            case 130:
                return rect2.top - rect.bottom;
            default:
                throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    static int majorAxisDistanceToFarEdge(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        return java.lang.Math.max(1, majorAxisDistanceToFarEdgeRaw(i, rect, rect2));
    }

    static int majorAxisDistanceToFarEdgeRaw(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        switch (i) {
            case 17:
                return rect.left - rect2.left;
            case 33:
                return rect.top - rect2.top;
            case 66:
                return rect2.right - rect.right;
            case 130:
                return rect2.bottom - rect.bottom;
            default:
                throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    static int minorAxisDistance(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        switch (i) {
            case 17:
            case 66:
                return java.lang.Math.abs((rect.top + (rect.height() / 2)) - (rect2.top + (rect2.height() / 2)));
            case 33:
            case 130:
                return java.lang.Math.abs((rect.left + (rect.width() / 2)) - (rect2.left + (rect2.width() / 2)));
            default:
                throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    public android.view.View findNearestTouchable(android.view.ViewGroup viewGroup, int i, int i2, int i3, int[] iArr) {
        int i4;
        java.util.ArrayList<android.view.View> touchables = viewGroup.getTouchables();
        int size = touchables.size();
        int scaledEdgeSlop = android.view.ViewConfiguration.get(viewGroup.mContext).getScaledEdgeSlop();
        android.graphics.Rect rect = new android.graphics.Rect();
        android.graphics.Rect rect2 = this.mOtherRect;
        android.view.View view = null;
        int i5 = Integer.MAX_VALUE;
        for (int i6 = 0; i6 < size; i6++) {
            android.view.View view2 = touchables.get(i6);
            view2.getDrawingRect(rect2);
            viewGroup.offsetRectBetweenParentAndChild(view2, rect2, true, true);
            if (isTouchCandidate(i, i2, rect2, i3)) {
                switch (i3) {
                    case 17:
                        i4 = (i - rect2.right) + 1;
                        break;
                    case 33:
                        i4 = (i2 - rect2.bottom) + 1;
                        break;
                    case 66:
                        i4 = rect2.left;
                        break;
                    case 130:
                        i4 = rect2.top;
                        break;
                    default:
                        i4 = Integer.MAX_VALUE;
                        break;
                }
                if (i4 < scaledEdgeSlop) {
                    if (view == null || rect.contains(rect2) || (!rect2.contains(rect) && i4 < i5)) {
                        rect.set(rect2);
                        switch (i3) {
                            case 17:
                                iArr[0] = -i4;
                                break;
                            case 33:
                                iArr[1] = -i4;
                                break;
                            case 66:
                                iArr[0] = i4;
                                break;
                            case 130:
                                iArr[1] = i4;
                                break;
                        }
                        i5 = i4;
                        view = view2;
                    }
                }
            }
        }
        return view;
    }

    private boolean isTouchCandidate(int i, int i2, android.graphics.Rect rect, int i3) {
        switch (i3) {
            case 17:
                return rect.left <= i && rect.top <= i2 && i2 <= rect.bottom;
            case 33:
                return rect.top <= i2 && rect.left <= i && i <= rect.right;
            case 66:
                return rect.left >= i && rect.top <= i2 && i2 <= rect.bottom;
            case 130:
                return rect.top >= i2 && rect.left <= i && i <= rect.right;
            default:
                throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static final boolean isValidId(int i) {
        return (i == 0 || i == -1) ? false : true;
    }

    static final class FocusSorter {
        private int mLastPoolRect;
        private int mRtlMult;
        private java.util.ArrayList<android.graphics.Rect> mRectPool = new java.util.ArrayList<>();
        private java.util.HashMap<android.view.View, android.graphics.Rect> mRectByView = null;
        private java.util.Comparator<android.view.View> mTopsComparator = new java.util.Comparator() { // from class: android.view.FocusFinder$FocusSorter$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$new$0;
                lambda$new$0 = android.view.FocusFinder.FocusSorter.this.lambda$new$0((android.view.View) obj, (android.view.View) obj2);
                return lambda$new$0;
            }
        };
        private java.util.Comparator<android.view.View> mSidesComparator = new java.util.Comparator() { // from class: android.view.FocusFinder$FocusSorter$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$new$1;
                lambda$new$1 = android.view.FocusFinder.FocusSorter.this.lambda$new$1((android.view.View) obj, (android.view.View) obj2);
                return lambda$new$1;
            }
        };

        FocusSorter() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$new$0(android.view.View view, android.view.View view2) {
            if (view == view2) {
                return 0;
            }
            android.graphics.Rect rect = this.mRectByView.get(view);
            android.graphics.Rect rect2 = this.mRectByView.get(view2);
            int i = rect.top - rect2.top;
            if (i == 0) {
                return rect.bottom - rect2.bottom;
            }
            return i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$new$1(android.view.View view, android.view.View view2) {
            if (view == view2) {
                return 0;
            }
            android.graphics.Rect rect = this.mRectByView.get(view);
            android.graphics.Rect rect2 = this.mRectByView.get(view2);
            int i = rect.left - rect2.left;
            if (i == 0) {
                return rect.right - rect2.right;
            }
            return this.mRtlMult * i;
        }

        public void sort(android.view.View[] viewArr, int i, int i2, android.view.ViewGroup viewGroup, boolean z) {
            int i3 = i2 - i;
            if (i3 < 2) {
                return;
            }
            if (this.mRectByView == null) {
                this.mRectByView = new java.util.HashMap<>();
            }
            this.mRtlMult = z ? -1 : 1;
            for (int size = this.mRectPool.size(); size < i3; size++) {
                this.mRectPool.add(new android.graphics.Rect());
            }
            for (int i4 = i; i4 < i2; i4++) {
                java.util.ArrayList<android.graphics.Rect> arrayList = this.mRectPool;
                int i5 = this.mLastPoolRect;
                this.mLastPoolRect = i5 + 1;
                android.graphics.Rect rect = arrayList.get(i5);
                viewArr[i4].getDrawingRect(rect);
                viewGroup.offsetDescendantRectToMyCoords(viewArr[i4], rect);
                this.mRectByView.put(viewArr[i4], rect);
            }
            java.util.Arrays.sort(viewArr, i, i3, this.mTopsComparator);
            int i6 = this.mRectByView.get(viewArr[i]).bottom;
            int i7 = i + 1;
            while (i7 < i2) {
                android.graphics.Rect rect2 = this.mRectByView.get(viewArr[i7]);
                if (rect2.top >= i6) {
                    if (i7 - i > 1) {
                        java.util.Arrays.sort(viewArr, i, i7, this.mSidesComparator);
                    }
                    i6 = rect2.bottom;
                    i = i7;
                } else {
                    i6 = java.lang.Math.max(i6, rect2.bottom);
                }
                i7++;
            }
            if (i7 - i > 1) {
                java.util.Arrays.sort(viewArr, i, i7, this.mSidesComparator);
            }
            this.mLastPoolRect = 0;
            this.mRectByView.clear();
        }
    }

    public static void sort(android.view.View[] viewArr, int i, int i2, android.view.ViewGroup viewGroup, boolean z) {
        getInstance().mFocusSorter.sort(viewArr, i, i2, viewGroup, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class UserSpecifiedFocusComparator implements java.util.Comparator<android.view.View> {
        private final android.view.FocusFinder.UserSpecifiedFocusComparator.NextFocusGetter mNextFocusGetter;
        private android.view.View mRoot;
        private final android.util.ArrayMap<android.view.View, android.view.View> mNextFoci = new android.util.ArrayMap<>();
        private final android.util.ArraySet<android.view.View> mIsConnectedTo = new android.util.ArraySet<>();
        private final android.util.ArrayMap<android.view.View, android.view.View> mHeadsOfChains = new android.util.ArrayMap<>();
        private final android.util.ArrayMap<android.view.View, java.lang.Integer> mOriginalOrdinal = new android.util.ArrayMap<>();

        public interface NextFocusGetter {
            android.view.View get(android.view.View view, android.view.View view2);
        }

        UserSpecifiedFocusComparator(android.view.FocusFinder.UserSpecifiedFocusComparator.NextFocusGetter nextFocusGetter) {
            this.mNextFocusGetter = nextFocusGetter;
        }

        public void recycle() {
            this.mRoot = null;
            this.mHeadsOfChains.clear();
            this.mIsConnectedTo.clear();
            this.mOriginalOrdinal.clear();
            this.mNextFoci.clear();
        }

        public void setFocusables(java.util.List<android.view.View> list, android.view.View view) {
            this.mRoot = view;
            for (int i = 0; i < list.size(); i++) {
                this.mOriginalOrdinal.put(list.get(i), java.lang.Integer.valueOf(i));
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                android.view.View view2 = list.get(size);
                android.view.View view3 = this.mNextFocusGetter.get(this.mRoot, view2);
                if (view3 != null && this.mOriginalOrdinal.containsKey(view3)) {
                    this.mNextFoci.put(view2, view3);
                    this.mIsConnectedTo.add(view3);
                }
            }
            for (int size2 = list.size() - 1; size2 >= 0; size2--) {
                android.view.View view4 = list.get(size2);
                if (this.mNextFoci.get(view4) != null && !this.mIsConnectedTo.contains(view4)) {
                    setHeadOfChain(view4);
                }
            }
        }

        private void setHeadOfChain(android.view.View view) {
            android.view.View view2 = view;
            while (view != null) {
                android.view.View view3 = this.mHeadsOfChains.get(view);
                if (view3 != null) {
                    if (view3 == view2) {
                        return;
                    }
                    view = view2;
                    view2 = view3;
                }
                this.mHeadsOfChains.put(view, view2);
                view = this.mNextFoci.get(view);
            }
        }

        @Override // java.util.Comparator
        public int compare(android.view.View view, android.view.View view2) {
            boolean z;
            if (view == view2) {
                return 0;
            }
            android.view.View view3 = this.mHeadsOfChains.get(view);
            android.view.View view4 = this.mHeadsOfChains.get(view2);
            if (view3 == view4 && view3 != null) {
                if (view == view3) {
                    return -1;
                }
                return (view2 == view3 || this.mNextFoci.get(view) == null) ? 1 : -1;
            }
            if (view3 == null) {
                z = false;
            } else {
                view = view3;
                z = true;
            }
            if (view4 != null) {
                view2 = view4;
                z = true;
            }
            if (z) {
                return this.mOriginalOrdinal.get(view).intValue() < this.mOriginalOrdinal.get(view2).intValue() ? -1 : 1;
            }
            return 0;
        }
    }
}
