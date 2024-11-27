package android.view;

/* loaded from: classes4.dex */
public class ViewOverlay {
    android.view.ViewOverlay.OverlayViewGroup mOverlayViewGroup;

    ViewOverlay(android.content.Context context, android.view.View view) {
        this.mOverlayViewGroup = new android.view.ViewOverlay.OverlayViewGroup(context, view);
    }

    android.view.ViewGroup getOverlayView() {
        return this.mOverlayViewGroup;
    }

    public void add(android.graphics.drawable.Drawable drawable) {
        this.mOverlayViewGroup.add(drawable);
    }

    public void remove(android.graphics.drawable.Drawable drawable) {
        this.mOverlayViewGroup.remove(drawable);
    }

    public void clear() {
        this.mOverlayViewGroup.clear();
    }

    boolean isEmpty() {
        return this.mOverlayViewGroup.isEmpty();
    }

    static class OverlayViewGroup extends android.view.ViewGroup {
        java.util.ArrayList<android.graphics.drawable.Drawable> mDrawables;
        final android.view.View mHostView;

        OverlayViewGroup(android.content.Context context, android.view.View view) {
            super(context);
            this.mDrawables = null;
            this.mHostView = view;
            this.mAttachInfo = this.mHostView.mAttachInfo;
            this.mRight = view.getWidth();
            this.mBottom = view.getHeight();
            this.mRenderNode.setLeftTopRightBottom(0, 0, this.mRight, this.mBottom);
        }

        public void add(android.graphics.drawable.Drawable drawable) {
            if (drawable == null) {
                throw new java.lang.IllegalArgumentException("drawable must be non-null");
            }
            if (this.mDrawables == null) {
                this.mDrawables = new java.util.ArrayList<>();
            }
            if (!this.mDrawables.contains(drawable)) {
                this.mDrawables.add(drawable);
                invalidate(drawable.getBounds());
                drawable.setCallback(this);
            }
        }

        public void remove(android.graphics.drawable.Drawable drawable) {
            if (drawable == null) {
                throw new java.lang.IllegalArgumentException("drawable must be non-null");
            }
            if (this.mDrawables != null) {
                this.mDrawables.remove(drawable);
                invalidate(drawable.getBounds());
                drawable.setCallback(null);
            }
        }

        @Override // android.view.View
        protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
            return super.verifyDrawable(drawable) || (this.mDrawables != null && this.mDrawables.contains(drawable));
        }

        public void add(android.view.View view) {
            if (view == null) {
                throw new java.lang.IllegalArgumentException("view must be non-null");
            }
            if (view.getParent() instanceof android.view.ViewGroup) {
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) view.getParent();
                if (viewGroup != this.mHostView && viewGroup.getParent() != null && viewGroup.mAttachInfo != null) {
                    int[] iArr = new int[2];
                    int[] iArr2 = new int[2];
                    viewGroup.getLocationOnScreen(iArr);
                    this.mHostView.getLocationOnScreen(iArr2);
                    view.offsetLeftAndRight(iArr[0] - iArr2[0]);
                    view.offsetTopAndBottom(iArr[1] - iArr2[1]);
                }
                viewGroup.removeView(view);
                if (viewGroup.getLayoutTransition() != null) {
                    viewGroup.getLayoutTransition().cancel(3);
                }
                if (view.getParent() != null) {
                    view.mParent = null;
                }
            }
            super.addView(view);
        }

        public void remove(android.view.View view) {
            if (view == null) {
                throw new java.lang.IllegalArgumentException("view must be non-null");
            }
            super.removeView(view);
        }

        public void clear() {
            removeAllViews();
            if (this.mDrawables != null) {
                java.util.Iterator<android.graphics.drawable.Drawable> it = this.mDrawables.iterator();
                while (it.hasNext()) {
                    it.next().setCallback(null);
                }
                this.mDrawables.clear();
            }
        }

        boolean isEmpty() {
            if (getChildCount() == 0) {
                if (this.mDrawables == null || this.mDrawables.size() == 0) {
                    return true;
                }
                return false;
            }
            return false;
        }

        @Override // android.view.View, android.graphics.drawable.Drawable.Callback
        public void invalidateDrawable(android.graphics.drawable.Drawable drawable) {
            invalidate(drawable.getBounds());
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(android.graphics.Canvas canvas) {
            canvas.enableZ();
            super.dispatchDraw(canvas);
            canvas.disableZ();
            int size = this.mDrawables == null ? 0 : this.mDrawables.size();
            for (int i = 0; i < size; i++) {
                this.mDrawables.get(i).draw(canvas);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        }

        @Override // android.view.View
        public void invalidate(android.graphics.Rect rect) {
            super.invalidate(rect);
            if (this.mHostView != null) {
                this.mHostView.invalidate(rect);
            }
        }

        @Override // android.view.View
        public void invalidate(int i, int i2, int i3, int i4) {
            super.invalidate(i, i2, i3, i4);
            if (this.mHostView != null) {
                this.mHostView.invalidate(i, i2, i3, i4);
            }
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            if (this.mHostView != null) {
                this.mHostView.invalidate();
            }
        }

        @Override // android.view.View
        public void invalidate(boolean z) {
            super.invalidate(z);
            if (this.mHostView != null) {
                this.mHostView.invalidate(z);
            }
        }

        @Override // android.view.View
        void invalidateViewProperty(boolean z, boolean z2) {
            super.invalidateViewProperty(z, z2);
            if (this.mHostView != null) {
                this.mHostView.invalidateViewProperty(z, z2);
            }
        }

        @Override // android.view.View
        protected void invalidateParentCaches() {
            super.invalidateParentCaches();
            if (this.mHostView != null) {
                this.mHostView.invalidateParentCaches();
            }
        }

        @Override // android.view.View
        protected void invalidateParentIfNeeded() {
            super.invalidateParentIfNeeded();
            if (this.mHostView != null) {
                this.mHostView.invalidateParentIfNeeded();
            }
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public void onDescendantInvalidated(android.view.View view, android.view.View view2) {
            if (this.mHostView != null) {
                if (this.mHostView instanceof android.view.ViewGroup) {
                    ((android.view.ViewGroup) this.mHostView).onDescendantInvalidated(this.mHostView, view2);
                    super.onDescendantInvalidated(view, view2);
                } else {
                    invalidate();
                }
            }
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public android.view.ViewParent invalidateChildInParent(int[] iArr, android.graphics.Rect rect) {
            if (this.mHostView != null) {
                rect.offset(iArr[0], iArr[1]);
                if (this.mHostView instanceof android.view.ViewGroup) {
                    iArr[0] = 0;
                    iArr[1] = 0;
                    super.invalidateChildInParent(iArr, rect);
                    return ((android.view.ViewGroup) this.mHostView).invalidateChildInParent(iArr, rect);
                }
                invalidate(rect);
                return null;
            }
            return null;
        }
    }
}
