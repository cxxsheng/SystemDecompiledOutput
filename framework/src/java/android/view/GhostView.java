package android.view;

/* loaded from: classes4.dex */
public class GhostView extends android.view.View {
    private boolean mBeingMoved;
    private int mReferences;
    private final android.view.View mView;

    private GhostView(android.view.View view) {
        super(view.getContext());
        this.mView = view;
        this.mView.mGhostView = this;
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mView.getParent();
        this.mView.setTransitionVisibility(4);
        viewGroup.invalidate();
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        if (canvas instanceof android.graphics.RecordingCanvas) {
            android.graphics.RecordingCanvas recordingCanvas = (android.graphics.RecordingCanvas) canvas;
            this.mView.mRecreateDisplayList = true;
            android.graphics.RenderNode updateDisplayListIfDirty = this.mView.updateDisplayListIfDirty();
            if (updateDisplayListIfDirty.hasDisplayList()) {
                recordingCanvas.enableZ();
                recordingCanvas.drawRenderNode(updateDisplayListIfDirty);
                recordingCanvas.disableZ();
            }
        }
    }

    public void setMatrix(android.graphics.Matrix matrix) {
        this.mRenderNode.setAnimationMatrix(matrix);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (this.mView.mGhostView == this) {
            this.mView.setTransitionVisibility(i == 0 ? 4 : 0);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!this.mBeingMoved) {
            this.mView.setTransitionVisibility(0);
            this.mView.mGhostView = null;
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mView.getParent();
            if (viewGroup != null) {
                viewGroup.invalidate();
            }
        }
    }

    public static void calculateMatrix(android.view.View view, android.view.ViewGroup viewGroup, android.graphics.Matrix matrix) {
        android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) view.getParent();
        matrix.reset();
        viewGroup2.transformMatrixToGlobal(matrix);
        matrix.preTranslate(-viewGroup2.getScrollX(), -viewGroup2.getScrollY());
        viewGroup.transformMatrixToLocal(matrix);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.view.GhostView addGhost(android.view.View view, android.view.ViewGroup viewGroup, android.graphics.Matrix matrix) {
        int i;
        if (!(view.getParent() instanceof android.view.ViewGroup)) {
            throw new java.lang.IllegalArgumentException("Ghosted views must be parented by a ViewGroup");
        }
        android.view.ViewGroupOverlay overlay = viewGroup.getOverlay();
        android.view.ViewOverlay.OverlayViewGroup overlayViewGroup = overlay.mOverlayViewGroup;
        android.view.GhostView ghostView = view.mGhostView;
        if (ghostView != null) {
            android.view.View view2 = (android.view.View) ghostView.getParent();
            android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) view2.getParent();
            if (viewGroup2 != overlayViewGroup) {
                i = ghostView.mReferences;
                viewGroup2.removeView(view2);
                ghostView = null;
                if (ghostView != null) {
                    if (matrix == null) {
                        matrix = new android.graphics.Matrix();
                        calculateMatrix(view, viewGroup, matrix);
                    }
                    ghostView = new android.view.GhostView(view);
                    ghostView.setMatrix(matrix);
                    android.widget.FrameLayout frameLayout = new android.widget.FrameLayout(view.getContext());
                    frameLayout.setClipChildren(false);
                    copySize(viewGroup, frameLayout);
                    copySize(viewGroup, ghostView);
                    frameLayout.addView(ghostView);
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    insertIntoOverlay(overlay.mOverlayViewGroup, frameLayout, ghostView, arrayList, moveGhostViewsToTop(overlay.mOverlayViewGroup, arrayList));
                    ghostView.mReferences = i;
                } else if (matrix != null) {
                    ghostView.setMatrix(matrix);
                }
                ghostView.mReferences++;
                return ghostView;
            }
        }
        i = 0;
        if (ghostView != null) {
        }
        ghostView.mReferences++;
        return ghostView;
    }

    public static android.view.GhostView addGhost(android.view.View view, android.view.ViewGroup viewGroup) {
        return addGhost(view, viewGroup, null);
    }

    public static void removeGhost(android.view.View view) {
        android.view.GhostView ghostView = view.mGhostView;
        if (ghostView != null) {
            ghostView.mReferences--;
            if (ghostView.mReferences == 0) {
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) ghostView.getParent();
                ((android.view.ViewGroup) viewGroup.getParent()).removeView(viewGroup);
            }
        }
    }

    public static android.view.GhostView getGhost(android.view.View view) {
        return view.mGhostView;
    }

    private static void copySize(android.view.View view, android.view.View view2) {
        view2.setLeft(0);
        view2.setTop(0);
        view2.setRight(view.getWidth());
        view2.setBottom(view.getHeight());
    }

    private static int moveGhostViewsToTop(android.view.ViewGroup viewGroup, java.util.ArrayList<android.view.View> arrayList) {
        int childCount = viewGroup.getChildCount();
        int i = -1;
        if (childCount == 0) {
            return -1;
        }
        int i2 = childCount - 1;
        if (isGhostWrapper(viewGroup.getChildAt(i2))) {
            int i3 = i2;
            int i4 = childCount - 2;
            while (i4 >= 0 && isGhostWrapper(viewGroup.getChildAt(i4))) {
                int i5 = i4;
                i4--;
                i3 = i5;
            }
            return i3;
        }
        for (int i6 = childCount - 2; i6 >= 0; i6--) {
            android.view.View childAt = viewGroup.getChildAt(i6);
            if (isGhostWrapper(childAt)) {
                arrayList.add(childAt);
                android.view.GhostView ghostView = (android.view.GhostView) ((android.view.ViewGroup) childAt).getChildAt(0);
                ghostView.mBeingMoved = true;
                viewGroup.removeViewAt(i6);
                ghostView.mBeingMoved = false;
            }
        }
        if (!arrayList.isEmpty()) {
            i = viewGroup.getChildCount();
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                viewGroup.addView(arrayList.get(size));
            }
            arrayList.clear();
        }
        return i;
    }

    private static void insertIntoOverlay(android.view.ViewGroup viewGroup, android.view.ViewGroup viewGroup2, android.view.GhostView ghostView, java.util.ArrayList<android.view.View> arrayList, int i) {
        if (i == -1) {
            viewGroup.addView(viewGroup2);
            return;
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        getParents(ghostView.mView, arrayList2);
        int insertIndex = getInsertIndex(viewGroup, arrayList2, arrayList, i);
        if (insertIndex < 0 || insertIndex >= viewGroup.getChildCount()) {
            viewGroup.addView(viewGroup2);
        } else {
            viewGroup.addView(viewGroup2, insertIndex);
        }
    }

    private static int getInsertIndex(android.view.ViewGroup viewGroup, java.util.ArrayList<android.view.View> arrayList, java.util.ArrayList<android.view.View> arrayList2, int i) {
        int childCount = viewGroup.getChildCount() - 1;
        while (i <= childCount) {
            int i2 = (i + childCount) / 2;
            getParents(((android.view.GhostView) ((android.view.ViewGroup) viewGroup.getChildAt(i2)).getChildAt(0)).mView, arrayList2);
            if (isOnTop(arrayList, arrayList2)) {
                i = i2 + 1;
            } else {
                childCount = i2 - 1;
            }
            arrayList2.clear();
        }
        return i;
    }

    private static boolean isGhostWrapper(android.view.View view) {
        if (view instanceof android.widget.FrameLayout) {
            android.widget.FrameLayout frameLayout = (android.widget.FrameLayout) view;
            if (frameLayout.getChildCount() == 1) {
                return frameLayout.getChildAt(0) instanceof android.view.GhostView;
            }
        }
        return false;
    }

    private static boolean isOnTop(java.util.ArrayList<android.view.View> arrayList, java.util.ArrayList<android.view.View> arrayList2) {
        if (arrayList.isEmpty() || arrayList2.isEmpty() || arrayList.get(0) != arrayList2.get(0)) {
            return true;
        }
        int min = java.lang.Math.min(arrayList.size(), arrayList2.size());
        for (int i = 1; i < min; i++) {
            android.view.View view = arrayList.get(i);
            android.view.View view2 = arrayList2.get(i);
            if (view != view2) {
                return isOnTop(view, view2);
            }
        }
        return arrayList2.size() == min;
    }

    private static void getParents(android.view.View view, java.util.ArrayList<android.view.View> arrayList) {
        java.lang.Object parent = view.getParent();
        if (parent != null && (parent instanceof android.view.ViewGroup)) {
            getParents((android.view.View) parent, arrayList);
        }
        arrayList.add(view);
    }

    private static boolean isOnTop(android.view.View view, android.view.View view2) {
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view.getParent();
        int childCount = viewGroup.getChildCount();
        java.util.ArrayList<android.view.View> buildOrderedChildList = viewGroup.buildOrderedChildList();
        boolean z = false;
        boolean z2 = buildOrderedChildList == null && viewGroup.isChildrenDrawingOrderEnabled();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                z = true;
                break;
            }
            int childDrawingOrder = z2 ? viewGroup.getChildDrawingOrder(childCount, i) : i;
            android.view.View childAt = buildOrderedChildList == null ? viewGroup.getChildAt(childDrawingOrder) : buildOrderedChildList.get(childDrawingOrder);
            if (childAt == view) {
                break;
            }
            if (childAt != view2) {
                i++;
            } else {
                z = true;
                break;
            }
        }
        if (buildOrderedChildList != null) {
            buildOrderedChildList.clear();
        }
        return z;
    }
}
