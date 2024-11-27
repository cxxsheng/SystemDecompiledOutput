package android.widget;

/* loaded from: classes4.dex */
class RemoteCollectionItemsAdapter extends android.widget.BaseAdapter {
    private android.widget.RemoteViews.ColorResources mColorResources;
    private android.widget.RemoteViews.InteractionHandler mInteractionHandler;
    private android.widget.RemoteViews.RemoteCollectionItems mItems;
    private android.util.SparseIntArray mLayoutIdToViewType;
    private final int mViewTypeCount;

    RemoteCollectionItemsAdapter(android.widget.RemoteViews.RemoteCollectionItems remoteCollectionItems, android.widget.RemoteViews.InteractionHandler interactionHandler, android.widget.RemoteViews.ColorResources colorResources) {
        this.mViewTypeCount = remoteCollectionItems.getViewTypeCount();
        this.mItems = remoteCollectionItems;
        this.mInteractionHandler = interactionHandler;
        this.mColorResources = colorResources;
        initLayoutIdToViewType();
    }

    void setData(android.widget.RemoteViews.RemoteCollectionItems remoteCollectionItems, android.widget.RemoteViews.InteractionHandler interactionHandler, android.widget.RemoteViews.ColorResources colorResources) {
        if (this.mViewTypeCount < remoteCollectionItems.getViewTypeCount()) {
            throw new java.lang.IllegalArgumentException("RemoteCollectionItemsAdapter cannot increase view type count after creation");
        }
        this.mItems = remoteCollectionItems;
        this.mInteractionHandler = interactionHandler;
        this.mColorResources = colorResources;
        initLayoutIdToViewType();
        notifyDataSetChanged();
    }

    private void initLayoutIdToViewType() {
        android.util.SparseIntArray sparseIntArray = this.mLayoutIdToViewType;
        this.mLayoutIdToViewType = new android.util.SparseIntArray(this.mViewTypeCount);
        int[] array = java.util.stream.IntStream.range(0, this.mItems.getItemCount()).map(new java.util.function.IntUnaryOperator() { // from class: android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i) {
                int lambda$initLayoutIdToViewType$0;
                lambda$initLayoutIdToViewType$0 = android.widget.RemoteCollectionItemsAdapter.this.lambda$initLayoutIdToViewType$0(i);
                return lambda$initLayoutIdToViewType$0;
            }
        }).distinct().toArray();
        if (array.length > this.mViewTypeCount) {
            throw new java.lang.IllegalArgumentException("Collection items uses " + array.length + " distinct layouts, which is more than view type count of " + this.mViewTypeCount);
        }
        boolean[] zArr = new boolean[array.length];
        final boolean[] zArr2 = new boolean[this.mViewTypeCount];
        int i = -1;
        if (sparseIntArray != null) {
            for (int i2 = 0; i2 < array.length; i2++) {
                int i3 = array[i2];
                int i4 = sparseIntArray.get(i3, -1);
                if (i4 >= 0) {
                    this.mLayoutIdToViewType.put(i3, i4);
                    zArr[i2] = true;
                    zArr2[i4] = true;
                }
            }
        }
        for (int i5 = 0; i5 < array.length; i5++) {
            if (!zArr[i5]) {
                int i6 = array[i5];
                i = java.util.stream.IntStream.range(i + 1, array.length).filter(new java.util.function.IntPredicate() { // from class: android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntPredicate
                    public final boolean test(int i7) {
                        return android.widget.RemoteCollectionItemsAdapter.lambda$initLayoutIdToViewType$1(zArr2, i7);
                    }
                }).findFirst().orElseThrow(new java.util.function.Supplier() { // from class: android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        return android.widget.RemoteCollectionItemsAdapter.lambda$initLayoutIdToViewType$2();
                    }
                });
                this.mLayoutIdToViewType.put(i6, i);
                zArr[i5] = true;
                zArr2[i] = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$initLayoutIdToViewType$0(int i) {
        return this.mItems.getItemView(i).getLayoutId();
    }

    static /* synthetic */ boolean lambda$initLayoutIdToViewType$1(boolean[] zArr, int i) {
        return !zArr[i];
    }

    static /* synthetic */ java.lang.IllegalStateException lambda$initLayoutIdToViewType$2() {
        return new java.lang.IllegalStateException("RemoteCollectionItems has more distinct layout ids than its view type count");
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mItems.getItemCount();
    }

    @Override // android.widget.Adapter
    public android.widget.RemoteViews getItem(int i) {
        return this.mItems.getItemView(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return this.mItems.getItemId(i);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        return this.mLayoutIdToViewType.get(this.mItems.getItemView(i).getLayoutId());
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return this.mViewTypeCount;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return this.mItems.hasStableIds();
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        if (i >= getCount()) {
            return null;
        }
        android.widget.RemoteViews itemView = this.mItems.getItemView(i);
        itemView.addFlags(2);
        android.appwidget.AppWidgetHostView.AdapterChildHostView adapterChildHostView = view instanceof android.appwidget.AppWidgetHostView.AdapterChildHostView ? (android.appwidget.AppWidgetHostView.AdapterChildHostView) view : new android.appwidget.AppWidgetHostView.AdapterChildHostView(viewGroup.getContext());
        adapterChildHostView.setInteractionHandler(this.mInteractionHandler);
        adapterChildHostView.setColorResourcesNoReapply(this.mColorResources);
        adapterChildHostView.updateAppWidget(itemView);
        return adapterChildHostView;
    }
}
