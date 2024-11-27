package com.android.internal.widget.helper;

/* loaded from: classes5.dex */
public class ItemTouchHelper extends com.android.internal.widget.RecyclerView.ItemDecoration implements com.android.internal.widget.RecyclerView.OnChildAttachStateChangeListener {
    static final int ACTION_MODE_DRAG_MASK = 16711680;
    private static final int ACTION_MODE_IDLE_MASK = 255;
    static final int ACTION_MODE_SWIPE_MASK = 65280;
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    static final int ACTIVE_POINTER_ID_NONE = -1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    static final boolean DEBUG = false;
    static final int DIRECTION_FLAG_COUNT = 8;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    private static final int PIXELS_PER_SECOND = 1000;
    public static final int RIGHT = 8;
    public static final int START = 16;
    static final java.lang.String TAG = "ItemTouchHelper";
    public static final int UP = 1;
    com.android.internal.widget.helper.ItemTouchHelper.Callback mCallback;
    private java.util.List<java.lang.Integer> mDistances;
    private long mDragScrollStartTimeInMs;
    float mDx;
    float mDy;
    android.view.GestureDetector mGestureDetector;
    float mInitialTouchX;
    float mInitialTouchY;
    float mMaxSwipeVelocity;
    com.android.internal.widget.RecyclerView mRecyclerView;
    int mSelectedFlags;
    float mSelectedStartX;
    float mSelectedStartY;
    private int mSlop;
    private java.util.List<com.android.internal.widget.RecyclerView.ViewHolder> mSwapTargets;
    float mSwipeEscapeVelocity;
    private android.graphics.Rect mTmpRect;
    android.view.VelocityTracker mVelocityTracker;
    final java.util.List<android.view.View> mPendingCleanup = new java.util.ArrayList();
    private final float[] mTmpPosition = new float[2];
    com.android.internal.widget.RecyclerView.ViewHolder mSelected = null;
    int mActivePointerId = -1;
    int mActionState = 0;
    java.util.List<com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation> mRecoverAnimations = new java.util.ArrayList();
    final java.lang.Runnable mScrollRunnable = new java.lang.Runnable() { // from class: com.android.internal.widget.helper.ItemTouchHelper.1
        @Override // java.lang.Runnable
        public void run() {
            if (com.android.internal.widget.helper.ItemTouchHelper.this.mSelected != null && com.android.internal.widget.helper.ItemTouchHelper.this.scrollIfNecessary()) {
                if (com.android.internal.widget.helper.ItemTouchHelper.this.mSelected != null) {
                    com.android.internal.widget.helper.ItemTouchHelper.this.moveIfNecessary(com.android.internal.widget.helper.ItemTouchHelper.this.mSelected);
                }
                com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView.removeCallbacks(com.android.internal.widget.helper.ItemTouchHelper.this.mScrollRunnable);
                com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView.postOnAnimation(this);
            }
        }
    };
    private com.android.internal.widget.RecyclerView.ChildDrawingOrderCallback mChildDrawingOrderCallback = null;
    android.view.View mOverdrawChild = null;
    int mOverdrawChildPosition = -1;
    private final com.android.internal.widget.RecyclerView.OnItemTouchListener mOnItemTouchListener = new com.android.internal.widget.RecyclerView.OnItemTouchListener() { // from class: com.android.internal.widget.helper.ItemTouchHelper.2
        @Override // com.android.internal.widget.RecyclerView.OnItemTouchListener
        public boolean onInterceptTouchEvent(com.android.internal.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent) {
            int findPointerIndex;
            com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation findAnimation;
            com.android.internal.widget.helper.ItemTouchHelper.this.mGestureDetector.onTouchEvent(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId = motionEvent.getPointerId(0);
                com.android.internal.widget.helper.ItemTouchHelper.this.mInitialTouchX = motionEvent.getX();
                com.android.internal.widget.helper.ItemTouchHelper.this.mInitialTouchY = motionEvent.getY();
                com.android.internal.widget.helper.ItemTouchHelper.this.obtainVelocityTracker();
                if (com.android.internal.widget.helper.ItemTouchHelper.this.mSelected == null && (findAnimation = com.android.internal.widget.helper.ItemTouchHelper.this.findAnimation(motionEvent)) != null) {
                    com.android.internal.widget.helper.ItemTouchHelper.this.mInitialTouchX -= findAnimation.mX;
                    com.android.internal.widget.helper.ItemTouchHelper.this.mInitialTouchY -= findAnimation.mY;
                    com.android.internal.widget.helper.ItemTouchHelper.this.endRecoverAnimation(findAnimation.mViewHolder, true);
                    if (com.android.internal.widget.helper.ItemTouchHelper.this.mPendingCleanup.remove(findAnimation.mViewHolder.itemView)) {
                        com.android.internal.widget.helper.ItemTouchHelper.this.mCallback.clearView(com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView, findAnimation.mViewHolder);
                    }
                    com.android.internal.widget.helper.ItemTouchHelper.this.select(findAnimation.mViewHolder, findAnimation.mActionState);
                    com.android.internal.widget.helper.ItemTouchHelper.this.updateDxDy(motionEvent, com.android.internal.widget.helper.ItemTouchHelper.this.mSelectedFlags, 0);
                }
            } else if (actionMasked == 3 || actionMasked == 1) {
                com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId = -1;
                com.android.internal.widget.helper.ItemTouchHelper.this.select(null, 0);
            } else if (com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId != -1 && (findPointerIndex = motionEvent.findPointerIndex(com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId)) >= 0) {
                com.android.internal.widget.helper.ItemTouchHelper.this.checkSelectForSwipe(actionMasked, motionEvent, findPointerIndex);
            }
            if (com.android.internal.widget.helper.ItemTouchHelper.this.mVelocityTracker != null) {
                com.android.internal.widget.helper.ItemTouchHelper.this.mVelocityTracker.addMovement(motionEvent);
            }
            return com.android.internal.widget.helper.ItemTouchHelper.this.mSelected != null;
        }

        @Override // com.android.internal.widget.RecyclerView.OnItemTouchListener
        public void onTouchEvent(com.android.internal.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent) {
            com.android.internal.widget.helper.ItemTouchHelper.this.mGestureDetector.onTouchEvent(motionEvent);
            if (com.android.internal.widget.helper.ItemTouchHelper.this.mVelocityTracker != null) {
                com.android.internal.widget.helper.ItemTouchHelper.this.mVelocityTracker.addMovement(motionEvent);
            }
            if (com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId == -1) {
                return;
            }
            int actionMasked = motionEvent.getActionMasked();
            int findPointerIndex = motionEvent.findPointerIndex(com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId);
            if (findPointerIndex >= 0) {
                com.android.internal.widget.helper.ItemTouchHelper.this.checkSelectForSwipe(actionMasked, motionEvent, findPointerIndex);
            }
            com.android.internal.widget.RecyclerView.ViewHolder viewHolder = com.android.internal.widget.helper.ItemTouchHelper.this.mSelected;
            if (viewHolder == null) {
                return;
            }
            switch (actionMasked) {
                case 1:
                    break;
                case 2:
                    if (findPointerIndex >= 0) {
                        com.android.internal.widget.helper.ItemTouchHelper.this.updateDxDy(motionEvent, com.android.internal.widget.helper.ItemTouchHelper.this.mSelectedFlags, findPointerIndex);
                        com.android.internal.widget.helper.ItemTouchHelper.this.moveIfNecessary(viewHolder);
                        com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView.removeCallbacks(com.android.internal.widget.helper.ItemTouchHelper.this.mScrollRunnable);
                        com.android.internal.widget.helper.ItemTouchHelper.this.mScrollRunnable.run();
                        com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView.invalidate();
                        return;
                    }
                    return;
                case 3:
                    if (com.android.internal.widget.helper.ItemTouchHelper.this.mVelocityTracker != null) {
                        com.android.internal.widget.helper.ItemTouchHelper.this.mVelocityTracker.clear();
                        break;
                    }
                    break;
                case 4:
                case 5:
                default:
                    return;
                case 6:
                    int actionIndex = motionEvent.getActionIndex();
                    if (motionEvent.getPointerId(actionIndex) == com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId) {
                        com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
                        com.android.internal.widget.helper.ItemTouchHelper.this.updateDxDy(motionEvent, com.android.internal.widget.helper.ItemTouchHelper.this.mSelectedFlags, actionIndex);
                        return;
                    }
                    return;
            }
            com.android.internal.widget.helper.ItemTouchHelper.this.select(null, 0);
            com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId = -1;
        }

        @Override // com.android.internal.widget.RecyclerView.OnItemTouchListener
        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            if (!z) {
                return;
            }
            com.android.internal.widget.helper.ItemTouchHelper.this.select(null, 0);
        }
    };

    public interface ViewDropHandler {
        void prepareForDrop(android.view.View view, android.view.View view2, int i, int i2);
    }

    public ItemTouchHelper(com.android.internal.widget.helper.ItemTouchHelper.Callback callback) {
        this.mCallback = callback;
    }

    private static boolean hitTest(android.view.View view, float f, float f2, float f3, float f4) {
        return f >= f3 && f <= f3 + ((float) view.getWidth()) && f2 >= f4 && f2 <= f4 + ((float) view.getHeight());
    }

    public void attachToRecyclerView(com.android.internal.widget.RecyclerView recyclerView) {
        if (this.mRecyclerView == recyclerView) {
            return;
        }
        if (this.mRecyclerView != null) {
            destroyCallbacks();
        }
        this.mRecyclerView = recyclerView;
        if (this.mRecyclerView != null) {
            android.content.res.Resources resources = recyclerView.getResources();
            this.mSwipeEscapeVelocity = resources.getDimension(com.android.internal.R.dimen.item_touch_helper_swipe_escape_velocity);
            this.mMaxSwipeVelocity = resources.getDimension(com.android.internal.R.dimen.item_touch_helper_swipe_escape_max_velocity);
            setupCallbacks();
        }
    }

    private void setupCallbacks() {
        this.mSlop = android.view.ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
        this.mRecyclerView.addItemDecoration(this);
        this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this);
        initGestureDetector();
    }

    private void destroyCallbacks() {
        this.mRecyclerView.removeItemDecoration(this);
        this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
        for (int size = this.mRecoverAnimations.size() - 1; size >= 0; size--) {
            this.mCallback.clearView(this.mRecyclerView, this.mRecoverAnimations.get(0).mViewHolder);
        }
        this.mRecoverAnimations.clear();
        this.mOverdrawChild = null;
        this.mOverdrawChildPosition = -1;
        releaseVelocityTracker();
    }

    private void initGestureDetector() {
        if (this.mGestureDetector != null) {
            return;
        }
        this.mGestureDetector = new android.view.GestureDetector(this.mRecyclerView.getContext(), new com.android.internal.widget.helper.ItemTouchHelper.ItemTouchHelperGestureListener());
    }

    private void getSelectedDxDy(float[] fArr) {
        if ((this.mSelectedFlags & 12) != 0) {
            fArr[0] = (this.mSelectedStartX + this.mDx) - this.mSelected.itemView.getLeft();
        } else {
            fArr[0] = this.mSelected.itemView.getTranslationX();
        }
        if ((this.mSelectedFlags & 3) != 0) {
            fArr[1] = (this.mSelectedStartY + this.mDy) - this.mSelected.itemView.getTop();
        } else {
            fArr[1] = this.mSelected.itemView.getTranslationY();
        }
    }

    @Override // com.android.internal.widget.RecyclerView.ItemDecoration
    public void onDrawOver(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.State state) {
        float f;
        float f2;
        if (this.mSelected == null) {
            f = 0.0f;
            f2 = 0.0f;
        } else {
            getSelectedDxDy(this.mTmpPosition);
            f = this.mTmpPosition[0];
            f2 = this.mTmpPosition[1];
        }
        this.mCallback.onDrawOver(canvas, recyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f, f2);
    }

    @Override // com.android.internal.widget.RecyclerView.ItemDecoration
    public void onDraw(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.State state) {
        float f;
        float f2;
        this.mOverdrawChildPosition = -1;
        if (this.mSelected == null) {
            f = 0.0f;
            f2 = 0.0f;
        } else {
            getSelectedDxDy(this.mTmpPosition);
            f = this.mTmpPosition[0];
            f2 = this.mTmpPosition[1];
        }
        this.mCallback.onDraw(canvas, recyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f, f2);
    }

    void select(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i) {
        boolean z;
        boolean z2;
        float f;
        float signum;
        int i2;
        if (viewHolder != this.mSelected || i != this.mActionState) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            int i3 = this.mActionState;
            endRecoverAnimation(viewHolder, true);
            this.mActionState = i;
            if (i == 2) {
                this.mOverdrawChild = viewHolder.itemView;
                addChildDrawingOrderCallback();
            }
            int i4 = (1 << ((i * 8) + 8)) - 1;
            if (this.mSelected == null) {
                z = false;
            } else {
                final com.android.internal.widget.RecyclerView.ViewHolder viewHolder2 = this.mSelected;
                if (viewHolder2.itemView.getParent() == null) {
                    removeChildDrawingOrderCallbackIfNecessary(viewHolder2.itemView);
                    this.mCallback.clearView(this.mRecyclerView, viewHolder2);
                    z = false;
                } else {
                    int swipeIfNecessary = i3 == 2 ? 0 : swipeIfNecessary(viewHolder2);
                    releaseVelocityTracker();
                    switch (swipeIfNecessary) {
                        case 1:
                        case 2:
                            f = 0.0f;
                            signum = java.lang.Math.signum(this.mDy) * this.mRecyclerView.getHeight();
                            break;
                        case 4:
                        case 8:
                        case 16:
                        case 32:
                            signum = 0.0f;
                            f = java.lang.Math.signum(this.mDx) * this.mRecyclerView.getWidth();
                            break;
                        default:
                            f = 0.0f;
                            signum = 0.0f;
                            break;
                    }
                    if (i3 == 2) {
                        i2 = 8;
                    } else if (swipeIfNecessary > 0) {
                        i2 = 2;
                    } else {
                        i2 = 4;
                    }
                    getSelectedDxDy(this.mTmpPosition);
                    float f2 = this.mTmpPosition[0];
                    float f3 = this.mTmpPosition[1];
                    final int i5 = swipeIfNecessary;
                    com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation recoverAnimation = new com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation(viewHolder2, i2, i3, f2, f3, f, signum) { // from class: com.android.internal.widget.helper.ItemTouchHelper.3
                        @Override // com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(android.animation.Animator animator) {
                            super.onAnimationEnd(animator);
                            if (this.mOverridden) {
                                return;
                            }
                            if (i5 <= 0) {
                                com.android.internal.widget.helper.ItemTouchHelper.this.mCallback.clearView(com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView, viewHolder2);
                            } else {
                                com.android.internal.widget.helper.ItemTouchHelper.this.mPendingCleanup.add(viewHolder2.itemView);
                                this.mIsPendingCleanup = true;
                                if (i5 > 0) {
                                    com.android.internal.widget.helper.ItemTouchHelper.this.postDispatchSwipe(this, i5);
                                }
                            }
                            if (com.android.internal.widget.helper.ItemTouchHelper.this.mOverdrawChild == viewHolder2.itemView) {
                                com.android.internal.widget.helper.ItemTouchHelper.this.removeChildDrawingOrderCallbackIfNecessary(viewHolder2.itemView);
                            }
                        }
                    };
                    recoverAnimation.setDuration(this.mCallback.getAnimationDuration(this.mRecyclerView, i2, f - f2, signum - f3));
                    this.mRecoverAnimations.add(recoverAnimation);
                    recoverAnimation.start();
                    z = true;
                }
                this.mSelected = null;
            }
            if (viewHolder != null) {
                this.mSelectedFlags = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, viewHolder) & i4) >> (this.mActionState * 8);
                this.mSelectedStartX = viewHolder.itemView.getLeft();
                this.mSelectedStartY = viewHolder.itemView.getTop();
                this.mSelected = viewHolder;
                if (i != 2) {
                    z2 = false;
                } else {
                    z2 = false;
                    this.mSelected.itemView.performHapticFeedback(0);
                }
            } else {
                z2 = false;
            }
            android.view.ViewParent parent = this.mRecyclerView.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(this.mSelected != null ? true : z2);
            }
            if (!z) {
                this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout();
            }
            this.mCallback.onSelectedChanged(this.mSelected, this.mActionState);
            this.mRecyclerView.invalidate();
        }
    }

    void postDispatchSwipe(final com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation recoverAnimation, final int i) {
        this.mRecyclerView.post(new java.lang.Runnable() { // from class: com.android.internal.widget.helper.ItemTouchHelper.4
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView != null && com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView.isAttachedToWindow() && !recoverAnimation.mOverridden && recoverAnimation.mViewHolder.getAdapterPosition() != -1) {
                    com.android.internal.widget.RecyclerView.ItemAnimator itemAnimator = com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView.getItemAnimator();
                    if ((itemAnimator == null || !itemAnimator.isRunning(null)) && !com.android.internal.widget.helper.ItemTouchHelper.this.hasRunningRecoverAnim()) {
                        com.android.internal.widget.helper.ItemTouchHelper.this.mCallback.onSwiped(recoverAnimation.mViewHolder, i);
                    } else {
                        com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView.post(this);
                    }
                }
            }
        });
    }

    boolean hasRunningRecoverAnim() {
        int size = this.mRecoverAnimations.size();
        for (int i = 0; i < size; i++) {
            if (!this.mRecoverAnimations.get(i).mEnded) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c9, code lost:
    
        if (r1 > 0) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x010a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean scrollIfNecessary() {
        int i;
        int i2;
        int i3;
        int i4;
        int width;
        if (this.mSelected == null) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return false;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long j = this.mDragScrollStartTimeInMs == Long.MIN_VALUE ? 0L : currentTimeMillis - this.mDragScrollStartTimeInMs;
        com.android.internal.widget.RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (this.mTmpRect == null) {
            this.mTmpRect = new android.graphics.Rect();
        }
        layoutManager.calculateItemDecorationsForChild(this.mSelected.itemView, this.mTmpRect);
        if (layoutManager.canScrollHorizontally()) {
            int i5 = (int) (this.mSelectedStartX + this.mDx);
            int paddingLeft = (i5 - this.mTmpRect.left) - this.mRecyclerView.getPaddingLeft();
            if (this.mDx < 0.0f && paddingLeft < 0) {
                i = paddingLeft;
            } else if (this.mDx > 0.0f && (width = ((i5 + this.mSelected.itemView.getWidth()) + this.mTmpRect.right) - (this.mRecyclerView.getWidth() - this.mRecyclerView.getPaddingRight())) > 0) {
                i = width;
            }
            if (layoutManager.canScrollVertically()) {
                int i6 = (int) (this.mSelectedStartY + this.mDy);
                int paddingTop = (i6 - this.mTmpRect.top) - this.mRecyclerView.getPaddingTop();
                if (this.mDy < 0.0f && paddingTop < 0) {
                    i2 = paddingTop;
                } else if (this.mDy > 0.0f) {
                    i2 = ((i6 + this.mSelected.itemView.getHeight()) + this.mTmpRect.bottom) - (this.mRecyclerView.getHeight() - this.mRecyclerView.getPaddingBottom());
                }
                if (i == 0) {
                    i3 = i;
                } else {
                    i3 = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getWidth(), i, this.mRecyclerView.getWidth(), j);
                }
                if (i2 == 0) {
                    i4 = i3;
                } else {
                    i4 = i3;
                    i2 = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getHeight(), i2, this.mRecyclerView.getHeight(), j);
                }
                if (i4 == 0 || i2 != 0) {
                    if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
                        this.mDragScrollStartTimeInMs = currentTimeMillis;
                    }
                    this.mRecyclerView.scrollBy(i4, i2);
                    return true;
                }
                this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
                return false;
            }
            i2 = 0;
            if (i == 0) {
            }
            if (i2 == 0) {
            }
            if (i4 == 0) {
            }
            if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
            }
            this.mRecyclerView.scrollBy(i4, i2);
            return true;
        }
        i = 0;
        if (layoutManager.canScrollVertically()) {
        }
        i2 = 0;
        if (i == 0) {
        }
        if (i2 == 0) {
        }
        if (i4 == 0) {
        }
        if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
        }
        this.mRecyclerView.scrollBy(i4, i2);
        return true;
    }

    private java.util.List<com.android.internal.widget.RecyclerView.ViewHolder> findSwapTargets(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        com.android.internal.widget.RecyclerView.ViewHolder viewHolder2 = viewHolder;
        if (this.mSwapTargets == null) {
            this.mSwapTargets = new java.util.ArrayList();
            this.mDistances = new java.util.ArrayList();
        } else {
            this.mSwapTargets.clear();
            this.mDistances.clear();
        }
        int boundingBoxMargin = this.mCallback.getBoundingBoxMargin();
        int round = java.lang.Math.round(this.mSelectedStartX + this.mDx) - boundingBoxMargin;
        int round2 = java.lang.Math.round(this.mSelectedStartY + this.mDy) - boundingBoxMargin;
        int i = boundingBoxMargin * 2;
        int width = viewHolder2.itemView.getWidth() + round + i;
        int height = viewHolder2.itemView.getHeight() + round2 + i;
        int i2 = (round + width) / 2;
        int i3 = (round2 + height) / 2;
        com.android.internal.widget.RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        int childCount = layoutManager.getChildCount();
        int i4 = 0;
        while (i4 < childCount) {
            android.view.View childAt = layoutManager.getChildAt(i4);
            if (childAt != viewHolder2.itemView && childAt.getBottom() >= round2 && childAt.getTop() <= height && childAt.getRight() >= round && childAt.getLeft() <= width) {
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(childAt);
                if (this.mCallback.canDropOver(this.mRecyclerView, this.mSelected, childViewHolder)) {
                    int abs = java.lang.Math.abs(i2 - ((childAt.getLeft() + childAt.getRight()) / 2));
                    int abs2 = java.lang.Math.abs(i3 - ((childAt.getTop() + childAt.getBottom()) / 2));
                    int i5 = (abs * abs) + (abs2 * abs2);
                    int size = this.mSwapTargets.size();
                    int i6 = 0;
                    for (int i7 = 0; i7 < size && i5 > this.mDistances.get(i7).intValue(); i7++) {
                        i6++;
                    }
                    this.mSwapTargets.add(i6, childViewHolder);
                    this.mDistances.add(i6, java.lang.Integer.valueOf(i5));
                }
            }
            i4++;
            viewHolder2 = viewHolder;
        }
        return this.mSwapTargets;
    }

    void moveIfNecessary(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        if (this.mRecyclerView.isLayoutRequested() || this.mActionState != 2) {
            return;
        }
        float moveThreshold = this.mCallback.getMoveThreshold(viewHolder);
        int i = (int) (this.mSelectedStartX + this.mDx);
        int i2 = (int) (this.mSelectedStartY + this.mDy);
        if (java.lang.Math.abs(i2 - viewHolder.itemView.getTop()) < viewHolder.itemView.getHeight() * moveThreshold && java.lang.Math.abs(i - viewHolder.itemView.getLeft()) < viewHolder.itemView.getWidth() * moveThreshold) {
            return;
        }
        java.util.List<com.android.internal.widget.RecyclerView.ViewHolder> findSwapTargets = findSwapTargets(viewHolder);
        if (findSwapTargets.size() == 0) {
            return;
        }
        com.android.internal.widget.RecyclerView.ViewHolder chooseDropTarget = this.mCallback.chooseDropTarget(viewHolder, findSwapTargets, i, i2);
        if (chooseDropTarget == null) {
            this.mSwapTargets.clear();
            this.mDistances.clear();
            return;
        }
        int adapterPosition = chooseDropTarget.getAdapterPosition();
        int adapterPosition2 = viewHolder.getAdapterPosition();
        if (this.mCallback.onMove(this.mRecyclerView, viewHolder, chooseDropTarget)) {
            this.mCallback.onMoved(this.mRecyclerView, viewHolder, adapterPosition2, chooseDropTarget, adapterPosition, i, i2);
        }
    }

    @Override // com.android.internal.widget.RecyclerView.OnChildAttachStateChangeListener
    public void onChildViewAttachedToWindow(android.view.View view) {
    }

    @Override // com.android.internal.widget.RecyclerView.OnChildAttachStateChangeListener
    public void onChildViewDetachedFromWindow(android.view.View view) {
        removeChildDrawingOrderCallbackIfNecessary(view);
        com.android.internal.widget.RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(view);
        if (childViewHolder == null) {
            return;
        }
        if (this.mSelected != null && childViewHolder == this.mSelected) {
            select(null, 0);
            return;
        }
        endRecoverAnimation(childViewHolder, false);
        if (this.mPendingCleanup.remove(childViewHolder.itemView)) {
            this.mCallback.clearView(this.mRecyclerView, childViewHolder);
        }
    }

    int endRecoverAnimation(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, boolean z) {
        for (int size = this.mRecoverAnimations.size() - 1; size >= 0; size--) {
            com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(size);
            if (recoverAnimation.mViewHolder == viewHolder) {
                recoverAnimation.mOverridden |= z;
                if (!recoverAnimation.mEnded) {
                    recoverAnimation.cancel();
                }
                this.mRecoverAnimations.remove(size);
                return recoverAnimation.mAnimationType;
            }
        }
        return 0;
    }

    @Override // com.android.internal.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(android.graphics.Rect rect, android.view.View view, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.State state) {
        rect.setEmpty();
    }

    void obtainVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }
        this.mVelocityTracker = android.view.VelocityTracker.obtain();
    }

    private void releaseVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private com.android.internal.widget.RecyclerView.ViewHolder findSwipedView(android.view.MotionEvent motionEvent) {
        android.view.View findChildView;
        com.android.internal.widget.RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (this.mActivePointerId == -1) {
            return null;
        }
        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        float x = motionEvent.getX(findPointerIndex) - this.mInitialTouchX;
        float y = motionEvent.getY(findPointerIndex) - this.mInitialTouchY;
        float abs = java.lang.Math.abs(x);
        float abs2 = java.lang.Math.abs(y);
        if (abs < this.mSlop && abs2 < this.mSlop) {
            return null;
        }
        if (abs > abs2 && layoutManager.canScrollHorizontally()) {
            return null;
        }
        if ((abs2 <= abs || !layoutManager.canScrollVertically()) && (findChildView = findChildView(motionEvent)) != null) {
            return this.mRecyclerView.getChildViewHolder(findChildView);
        }
        return null;
    }

    boolean checkSelectForSwipe(int i, android.view.MotionEvent motionEvent, int i2) {
        com.android.internal.widget.RecyclerView.ViewHolder findSwipedView;
        int absoluteMovementFlags;
        if (this.mSelected != null || i != 2 || this.mActionState == 2 || !this.mCallback.isItemViewSwipeEnabled() || this.mRecyclerView.getScrollState() == 1 || (findSwipedView = findSwipedView(motionEvent)) == null || (absoluteMovementFlags = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, findSwipedView) & 65280) >> 8) == 0) {
            return false;
        }
        float x = motionEvent.getX(i2);
        float y = motionEvent.getY(i2);
        float f = x - this.mInitialTouchX;
        float f2 = y - this.mInitialTouchY;
        float abs = java.lang.Math.abs(f);
        float abs2 = java.lang.Math.abs(f2);
        if (abs < this.mSlop && abs2 < this.mSlop) {
            return false;
        }
        if (abs > abs2) {
            if (f < 0.0f && (absoluteMovementFlags & 4) == 0) {
                return false;
            }
            if (f > 0.0f && (absoluteMovementFlags & 8) == 0) {
                return false;
            }
        } else {
            if (f2 < 0.0f && (absoluteMovementFlags & 1) == 0) {
                return false;
            }
            if (f2 > 0.0f && (absoluteMovementFlags & 2) == 0) {
                return false;
            }
        }
        this.mDy = 0.0f;
        this.mDx = 0.0f;
        this.mActivePointerId = motionEvent.getPointerId(0);
        select(findSwipedView, 1);
        return true;
    }

    android.view.View findChildView(android.view.MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.mSelected != null) {
            android.view.View view = this.mSelected.itemView;
            if (hitTest(view, x, y, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
                return view;
            }
        }
        for (int size = this.mRecoverAnimations.size() - 1; size >= 0; size--) {
            com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(size);
            android.view.View view2 = recoverAnimation.mViewHolder.itemView;
            if (hitTest(view2, x, y, recoverAnimation.mX, recoverAnimation.mY)) {
                return view2;
            }
        }
        return this.mRecyclerView.findChildViewUnder(x, y);
    }

    public void startDrag(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        if (!this.mCallback.hasDragFlag(this.mRecyclerView, viewHolder)) {
            android.util.Log.e(TAG, "Start drag has been called but dragging is not enabled");
            return;
        }
        if (viewHolder.itemView.getParent() != this.mRecyclerView) {
            android.util.Log.e(TAG, "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
            return;
        }
        obtainVelocityTracker();
        this.mDy = 0.0f;
        this.mDx = 0.0f;
        select(viewHolder, 2);
    }

    public void startSwipe(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        if (!this.mCallback.hasSwipeFlag(this.mRecyclerView, viewHolder)) {
            android.util.Log.e(TAG, "Start swipe has been called but swiping is not enabled");
            return;
        }
        if (viewHolder.itemView.getParent() != this.mRecyclerView) {
            android.util.Log.e(TAG, "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
            return;
        }
        obtainVelocityTracker();
        this.mDy = 0.0f;
        this.mDx = 0.0f;
        select(viewHolder, 1);
    }

    com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation findAnimation(android.view.MotionEvent motionEvent) {
        if (this.mRecoverAnimations.isEmpty()) {
            return null;
        }
        android.view.View findChildView = findChildView(motionEvent);
        for (int size = this.mRecoverAnimations.size() - 1; size >= 0; size--) {
            com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(size);
            if (recoverAnimation.mViewHolder.itemView == findChildView) {
                return recoverAnimation;
            }
        }
        return null;
    }

    void updateDxDy(android.view.MotionEvent motionEvent, int i, int i2) {
        float x = motionEvent.getX(i2);
        float y = motionEvent.getY(i2);
        this.mDx = x - this.mInitialTouchX;
        this.mDy = y - this.mInitialTouchY;
        if ((i & 4) == 0) {
            this.mDx = java.lang.Math.max(0.0f, this.mDx);
        }
        if ((i & 8) == 0) {
            this.mDx = java.lang.Math.min(0.0f, this.mDx);
        }
        if ((i & 1) == 0) {
            this.mDy = java.lang.Math.max(0.0f, this.mDy);
        }
        if ((i & 2) == 0) {
            this.mDy = java.lang.Math.min(0.0f, this.mDy);
        }
    }

    private int swipeIfNecessary(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        if (this.mActionState == 2) {
            return 0;
        }
        int movementFlags = this.mCallback.getMovementFlags(this.mRecyclerView, viewHolder);
        int convertToAbsoluteDirection = (this.mCallback.convertToAbsoluteDirection(movementFlags, this.mRecyclerView.getLayoutDirection()) & 65280) >> 8;
        if (convertToAbsoluteDirection == 0) {
            return 0;
        }
        int i = (movementFlags & 65280) >> 8;
        if (java.lang.Math.abs(this.mDx) > java.lang.Math.abs(this.mDy)) {
            int checkHorizontalSwipe = checkHorizontalSwipe(viewHolder, convertToAbsoluteDirection);
            if (checkHorizontalSwipe > 0) {
                if ((i & checkHorizontalSwipe) == 0) {
                    return com.android.internal.widget.helper.ItemTouchHelper.Callback.convertToRelativeDirection(checkHorizontalSwipe, this.mRecyclerView.getLayoutDirection());
                }
                return checkHorizontalSwipe;
            }
            int checkVerticalSwipe = checkVerticalSwipe(viewHolder, convertToAbsoluteDirection);
            if (checkVerticalSwipe > 0) {
                return checkVerticalSwipe;
            }
        } else {
            int checkVerticalSwipe2 = checkVerticalSwipe(viewHolder, convertToAbsoluteDirection);
            if (checkVerticalSwipe2 > 0) {
                return checkVerticalSwipe2;
            }
            int checkHorizontalSwipe2 = checkHorizontalSwipe(viewHolder, convertToAbsoluteDirection);
            if (checkHorizontalSwipe2 > 0) {
                if ((i & checkHorizontalSwipe2) == 0) {
                    return com.android.internal.widget.helper.ItemTouchHelper.Callback.convertToRelativeDirection(checkHorizontalSwipe2, this.mRecyclerView.getLayoutDirection());
                }
                return checkHorizontalSwipe2;
            }
        }
        return 0;
    }

    private int checkHorizontalSwipe(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i) {
        if ((i & 12) != 0) {
            int i2 = this.mDx > 0.0f ? 8 : 4;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
                float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                int i3 = xVelocity <= 0.0f ? 4 : 8;
                float abs = java.lang.Math.abs(xVelocity);
                if ((i3 & i) != 0 && i2 == i3 && abs >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && abs > java.lang.Math.abs(yVelocity)) {
                    return i3;
                }
            }
            float width = this.mRecyclerView.getWidth() * this.mCallback.getSwipeThreshold(viewHolder);
            if ((i & i2) != 0 && java.lang.Math.abs(this.mDx) > width) {
                return i2;
            }
            return 0;
        }
        return 0;
    }

    private int checkVerticalSwipe(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i) {
        if ((i & 3) != 0) {
            int i2 = this.mDy > 0.0f ? 2 : 1;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
                float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                int i3 = yVelocity <= 0.0f ? 1 : 2;
                float abs = java.lang.Math.abs(yVelocity);
                if ((i3 & i) != 0 && i3 == i2 && abs >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && abs > java.lang.Math.abs(xVelocity)) {
                    return i3;
                }
            }
            float height = this.mRecyclerView.getHeight() * this.mCallback.getSwipeThreshold(viewHolder);
            if ((i & i2) != 0 && java.lang.Math.abs(this.mDy) > height) {
                return i2;
            }
            return 0;
        }
        return 0;
    }

    private void addChildDrawingOrderCallback() {
    }

    /* renamed from: com.android.internal.widget.helper.ItemTouchHelper$5, reason: invalid class name */
    class AnonymousClass5 implements com.android.internal.widget.RecyclerView.ChildDrawingOrderCallback {
        AnonymousClass5() {
        }

        @Override // com.android.internal.widget.RecyclerView.ChildDrawingOrderCallback
        public int onGetChildDrawingOrder(int i, int i2) {
            if (com.android.internal.widget.helper.ItemTouchHelper.this.mOverdrawChild == null) {
                return i2;
            }
            int i3 = com.android.internal.widget.helper.ItemTouchHelper.this.mOverdrawChildPosition;
            if (i3 == -1) {
                i3 = com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView.indexOfChild(com.android.internal.widget.helper.ItemTouchHelper.this.mOverdrawChild);
                com.android.internal.widget.helper.ItemTouchHelper.this.mOverdrawChildPosition = i3;
            }
            if (i2 == i - 1) {
                return i3;
            }
            return i2 < i3 ? i2 : i2 + 1;
        }
    }

    void removeChildDrawingOrderCallbackIfNecessary(android.view.View view) {
        if (view == this.mOverdrawChild) {
            this.mOverdrawChild = null;
            if (this.mChildDrawingOrderCallback != null) {
                this.mRecyclerView.setChildDrawingOrderCallback(null);
            }
        }
    }

    public static abstract class Callback {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000;
        static final int RELATIVE_DIR_FLAGS = 3158064;
        private int mCachedMaxScrollSpeed = -1;
        private static final com.android.internal.widget.helper.ItemTouchUIUtil sUICallback = new com.android.internal.widget.helper.ItemTouchUIUtilImpl();
        private static final android.view.animation.Interpolator sDragScrollInterpolator = new android.view.animation.Interpolator() { // from class: com.android.internal.widget.helper.ItemTouchHelper.Callback.1
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        };
        private static final android.view.animation.Interpolator sDragViewScrollCapInterpolator = new android.view.animation.Interpolator() { // from class: com.android.internal.widget.helper.ItemTouchHelper.Callback.2
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        };

        public abstract int getMovementFlags(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder);

        public abstract boolean onMove(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ViewHolder viewHolder2);

        public abstract void onSwiped(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i);

        public static com.android.internal.widget.helper.ItemTouchUIUtil getDefaultUIUtil() {
            return sUICallback;
        }

        public static int convertToRelativeDirection(int i, int i2) {
            int i3 = i & ABS_HORIZONTAL_DIR_FLAGS;
            if (i3 == 0) {
                return i;
            }
            int i4 = i & (~i3);
            if (i2 == 0) {
                return i4 | (i3 << 2);
            }
            int i5 = i3 << 1;
            return i4 | ((-789517) & i5) | ((i5 & ABS_HORIZONTAL_DIR_FLAGS) << 2);
        }

        public static int makeMovementFlags(int i, int i2) {
            return makeFlag(2, i) | makeFlag(1, i2) | makeFlag(0, i2 | i);
        }

        public static int makeFlag(int i, int i2) {
            return i2 << (i * 8);
        }

        public int convertToAbsoluteDirection(int i, int i2) {
            int i3 = i & RELATIVE_DIR_FLAGS;
            if (i3 == 0) {
                return i;
            }
            int i4 = i & (~i3);
            if (i2 == 0) {
                return i4 | (i3 >> 2);
            }
            int i5 = i3 >> 1;
            return i4 | ((-3158065) & i5) | ((i5 & RELATIVE_DIR_FLAGS) >> 2);
        }

        final int getAbsoluteMovementFlags(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            return convertToAbsoluteDirection(getMovementFlags(recyclerView, viewHolder), recyclerView.getLayoutDirection());
        }

        boolean hasDragFlag(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            return (getAbsoluteMovementFlags(recyclerView, viewHolder) & 16711680) != 0;
        }

        boolean hasSwipeFlag(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            return (getAbsoluteMovementFlags(recyclerView, viewHolder) & 65280) != 0;
        }

        public boolean canDropOver(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ViewHolder viewHolder2) {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getSwipeThreshold(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getMoveThreshold(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getSwipeEscapeVelocity(float f) {
            return f;
        }

        public float getSwipeVelocityThreshold(float f) {
            return f;
        }

        public com.android.internal.widget.RecyclerView.ViewHolder chooseDropTarget(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, java.util.List<com.android.internal.widget.RecyclerView.ViewHolder> list, int i, int i2) {
            int bottom;
            int abs;
            int top;
            int abs2;
            int left;
            int abs3;
            int right;
            int abs4;
            int width = i + viewHolder.itemView.getWidth();
            int height = i2 + viewHolder.itemView.getHeight();
            int left2 = i - viewHolder.itemView.getLeft();
            int top2 = i2 - viewHolder.itemView.getTop();
            int size = list.size();
            com.android.internal.widget.RecyclerView.ViewHolder viewHolder2 = null;
            int i3 = -1;
            for (int i4 = 0; i4 < size; i4++) {
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder3 = list.get(i4);
                if (left2 > 0 && (right = viewHolder3.itemView.getRight() - width) < 0 && viewHolder3.itemView.getRight() > viewHolder.itemView.getRight() && (abs4 = java.lang.Math.abs(right)) > i3) {
                    viewHolder2 = viewHolder3;
                    i3 = abs4;
                }
                if (left2 < 0 && (left = viewHolder3.itemView.getLeft() - i) > 0 && viewHolder3.itemView.getLeft() < viewHolder.itemView.getLeft() && (abs3 = java.lang.Math.abs(left)) > i3) {
                    viewHolder2 = viewHolder3;
                    i3 = abs3;
                }
                if (top2 < 0 && (top = viewHolder3.itemView.getTop() - i2) > 0 && viewHolder3.itemView.getTop() < viewHolder.itemView.getTop() && (abs2 = java.lang.Math.abs(top)) > i3) {
                    viewHolder2 = viewHolder3;
                    i3 = abs2;
                }
                if (top2 > 0 && (bottom = viewHolder3.itemView.getBottom() - height) < 0 && viewHolder3.itemView.getBottom() > viewHolder.itemView.getBottom() && (abs = java.lang.Math.abs(bottom)) > i3) {
                    viewHolder2 = viewHolder3;
                    i3 = abs;
                }
            }
            return viewHolder2;
        }

        public void onSelectedChanged(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                sUICallback.onSelected(viewHolder.itemView);
            }
        }

        private int getMaxDragScroll(com.android.internal.widget.RecyclerView recyclerView) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(com.android.internal.R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onMoved(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i, com.android.internal.widget.RecyclerView.ViewHolder viewHolder2, int i2, int i3, int i4) {
            com.android.internal.widget.RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof com.android.internal.widget.helper.ItemTouchHelper.ViewDropHandler) {
                ((com.android.internal.widget.helper.ItemTouchHelper.ViewDropHandler) layoutManager).prepareForDrop(viewHolder.itemView, viewHolder2.itemView, i3, i4);
                return;
            }
            if (layoutManager.canScrollHorizontally()) {
                if (layoutManager.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
            if (layoutManager.canScrollVertically()) {
                if (layoutManager.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedBottom(viewHolder2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
        }

        void onDraw(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder, java.util.List<com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation> list, int i, float f, float f2) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation recoverAnimation = list.get(i2);
                recoverAnimation.update();
                int save = canvas.save();
                onChildDraw(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
                canvas.restoreToCount(save);
            }
            if (viewHolder != null) {
                int save2 = canvas.save();
                onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, true);
                canvas.restoreToCount(save2);
            }
        }

        void onDrawOver(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder, java.util.List<com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation> list, int i, float f, float f2) {
            int size = list.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation recoverAnimation = list.get(i2);
                int save = canvas.save();
                onChildDrawOver(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
                canvas.restoreToCount(save);
            }
            if (viewHolder != null) {
                int save2 = canvas.save();
                onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i, true);
                canvas.restoreToCount(save2);
            }
            for (int i3 = size - 1; i3 >= 0; i3--) {
                com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation recoverAnimation2 = list.get(i3);
                if (recoverAnimation2.mEnded && !recoverAnimation2.mIsPendingCleanup) {
                    list.remove(i3);
                } else if (!recoverAnimation2.mEnded) {
                    z = true;
                }
            }
            if (z) {
                recyclerView.invalidate();
            }
        }

        public void clearView(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            sUICallback.clearView(viewHolder.itemView);
        }

        public void onChildDraw(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            sUICallback.onDraw(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        public void onChildDrawOver(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            sUICallback.onDrawOver(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        public long getAnimationDuration(com.android.internal.widget.RecyclerView recyclerView, int i, float f, float f2) {
            com.android.internal.widget.RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            return itemAnimator == null ? i == 8 ? 200L : 250L : i == 8 ? itemAnimator.getMoveDuration() : itemAnimator.getRemoveDuration();
        }

        public int interpolateOutOfBoundsScroll(com.android.internal.widget.RecyclerView recyclerView, int i, int i2, int i3, long j) {
            int signum = (int) (((int) (((int) java.lang.Math.signum(i2)) * getMaxDragScroll(recyclerView) * sDragViewScrollCapInterpolator.getInterpolation(java.lang.Math.min(1.0f, (java.lang.Math.abs(i2) * 1.0f) / i)))) * sDragScrollInterpolator.getInterpolation(j <= DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS ? j / 2000.0f : 1.0f));
            if (signum == 0) {
                return i2 > 0 ? 1 : -1;
            }
            return signum;
        }
    }

    public static abstract class SimpleCallback extends com.android.internal.widget.helper.ItemTouchHelper.Callback {
        private int mDefaultDragDirs;
        private int mDefaultSwipeDirs;

        public SimpleCallback(int i, int i2) {
            this.mDefaultSwipeDirs = i2;
            this.mDefaultDragDirs = i;
        }

        public void setDefaultSwipeDirs(int i) {
            this.mDefaultSwipeDirs = i;
        }

        public void setDefaultDragDirs(int i) {
            this.mDefaultDragDirs = i;
        }

        public int getSwipeDirs(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            return this.mDefaultSwipeDirs;
        }

        public int getDragDirs(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            return this.mDefaultDragDirs;
        }

        @Override // com.android.internal.widget.helper.ItemTouchHelper.Callback
        public int getMovementFlags(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(getDragDirs(recyclerView, viewHolder), getSwipeDirs(recyclerView, viewHolder));
        }
    }

    private class ItemTouchHelperGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {
        ItemTouchHelperGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(android.view.MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(android.view.MotionEvent motionEvent) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolder;
            android.view.View findChildView = com.android.internal.widget.helper.ItemTouchHelper.this.findChildView(motionEvent);
            if (findChildView != null && (childViewHolder = com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView.getChildViewHolder(findChildView)) != null && com.android.internal.widget.helper.ItemTouchHelper.this.mCallback.hasDragFlag(com.android.internal.widget.helper.ItemTouchHelper.this.mRecyclerView, childViewHolder) && motionEvent.getPointerId(0) == com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId) {
                int findPointerIndex = motionEvent.findPointerIndex(com.android.internal.widget.helper.ItemTouchHelper.this.mActivePointerId);
                float x = motionEvent.getX(findPointerIndex);
                float y = motionEvent.getY(findPointerIndex);
                com.android.internal.widget.helper.ItemTouchHelper.this.mInitialTouchX = x;
                com.android.internal.widget.helper.ItemTouchHelper.this.mInitialTouchY = y;
                com.android.internal.widget.helper.ItemTouchHelper itemTouchHelper = com.android.internal.widget.helper.ItemTouchHelper.this;
                com.android.internal.widget.helper.ItemTouchHelper.this.mDy = 0.0f;
                itemTouchHelper.mDx = 0.0f;
                if (com.android.internal.widget.helper.ItemTouchHelper.this.mCallback.isLongPressDragEnabled()) {
                    com.android.internal.widget.helper.ItemTouchHelper.this.select(childViewHolder, 2);
                }
            }
        }
    }

    private class RecoverAnimation implements android.animation.Animator.AnimatorListener {
        final int mActionState;
        final int mAnimationType;
        private float mFraction;
        public boolean mIsPendingCleanup;
        final float mStartDx;
        final float mStartDy;
        final float mTargetX;
        final float mTargetY;
        final com.android.internal.widget.RecyclerView.ViewHolder mViewHolder;
        float mX;
        float mY;
        boolean mOverridden = false;
        boolean mEnded = false;
        private final android.animation.ValueAnimator mValueAnimator = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);

        RecoverAnimation(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i, int i2, float f, float f2, float f3, float f4) {
            this.mActionState = i2;
            this.mAnimationType = i;
            this.mViewHolder = viewHolder;
            this.mStartDx = f;
            this.mStartDy = f2;
            this.mTargetX = f3;
            this.mTargetY = f4;
            this.mValueAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                    com.android.internal.widget.helper.ItemTouchHelper.RecoverAnimation.this.setFraction(valueAnimator.getAnimatedFraction());
                }
            });
            this.mValueAnimator.setTarget(viewHolder.itemView);
            this.mValueAnimator.addListener(this);
            setFraction(0.0f);
        }

        public void setDuration(long j) {
            this.mValueAnimator.setDuration(j);
        }

        public void start() {
            this.mViewHolder.setIsRecyclable(false);
            this.mValueAnimator.start();
        }

        public void cancel() {
            this.mValueAnimator.cancel();
        }

        public void setFraction(float f) {
            this.mFraction = f;
        }

        public void update() {
            if (this.mStartDx == this.mTargetX) {
                this.mX = this.mViewHolder.itemView.getTranslationX();
            } else {
                this.mX = this.mStartDx + (this.mFraction * (this.mTargetX - this.mStartDx));
            }
            if (this.mStartDy == this.mTargetY) {
                this.mY = this.mViewHolder.itemView.getTranslationY();
            } else {
                this.mY = this.mStartDy + (this.mFraction * (this.mTargetY - this.mStartDy));
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            if (!this.mEnded) {
                this.mViewHolder.setIsRecyclable(true);
            }
            this.mEnded = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            setFraction(1.0f);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }
    }
}
