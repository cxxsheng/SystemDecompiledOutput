package android.widget;

/* loaded from: classes4.dex */
public class RemoteViews implements android.os.Parcelable, android.view.LayoutInflater.Filter {
    private static final int ATTRIBUTE_REFLECTION_ACTION_TAG = 32;
    private static final int BITMAP_REFLECTION_ACTION_TAG = 12;
    private static final int COMPLEX_UNIT_DIMENSION_REFLECTION_ACTION_TAG = 25;
    public static final java.lang.String EXTRA_CHECKED = "android.widget.extra.CHECKED";
    static final java.lang.String EXTRA_REMOTEADAPTER_APPWIDGET_ID = "remoteAdapterAppWidgetId";
    static final java.lang.String EXTRA_REMOTEADAPTER_ON_LIGHT_BACKGROUND = "remoteAdapterOnLightBackground";
    public static final java.lang.String EXTRA_SHARED_ELEMENT_BOUNDS = "android.widget.extra.SHARED_ELEMENT_BOUNDS";
    static final int FLAG_MASK_TO_PROPAGATE = 6;
    public static final int FLAG_REAPPLY_DISALLOWED = 1;
    public static final int FLAG_USE_LIGHT_BACKGROUND_LAYOUT = 4;
    public static final int FLAG_WIDGET_IS_COLLECTION_CHILD = 2;
    private static final int LAYOUT_PARAM_ACTION_TAG = 19;
    private static final java.lang.String LOG_TAG = "RemoteViews";
    public static final int MARGIN_BOTTOM = 3;
    public static final int MARGIN_END = 5;
    public static final int MARGIN_LEFT = 0;
    public static final int MARGIN_RIGHT = 2;
    public static final int MARGIN_START = 4;
    public static final int MARGIN_TOP = 1;
    private static final int MAX_ADAPTER_CONVERSION_WAITING_TIME_MS = 5000;
    private static final int MAX_INIT_VIEW_COUNT = 16;
    private static final int MAX_NESTED_VIEWS = 10;
    private static final int MODE_HAS_LANDSCAPE_AND_PORTRAIT = 1;
    private static final int MODE_HAS_SIZED_REMOTEVIEWS = 2;
    private static final int MODE_NORMAL = 0;
    private static final int NIGHT_MODE_REFLECTION_ACTION_TAG = 30;
    private static final int REFLECTION_ACTION_TAG = 2;
    private static final int REMOVE_FROM_PARENT_ACTION_TAG = 23;
    private static final int RESOURCE_REFLECTION_ACTION_TAG = 24;
    private static final int SET_COMPOUND_BUTTON_CHECKED_TAG = 26;
    private static final int SET_DRAWABLE_TINT_TAG = 3;
    private static final int SET_DRAW_INSTRUCTION_TAG = 35;
    private static final int SET_EMPTY_VIEW_ACTION_TAG = 6;
    private static final int SET_INT_TAG_TAG = 22;
    private static final int SET_ON_CHECKED_CHANGE_RESPONSE_TAG = 29;
    private static final int SET_ON_CLICK_RESPONSE_TAG = 1;
    private static final int SET_ON_STYLUS_HANDWRITING_RESPONSE_TAG = 34;
    private static final int SET_PENDING_INTENT_TEMPLATE_TAG = 8;
    private static final int SET_RADIO_GROUP_CHECKED = 27;
    private static final int SET_REMOTE_ADAPTER_TAG = 33;
    private static final int SET_REMOTE_COLLECTION_ITEMS_ADAPTER_TAG = 31;
    private static final int SET_REMOTE_INPUTS_ACTION_TAG = 18;
    private static final int SET_REMOTE_VIEW_ADAPTER_INTENT_TAG = 10;
    private static final int SET_RIPPLE_DRAWABLE_COLOR_TAG = 21;
    private static final int SET_VIEW_OUTLINE_RADIUS_TAG = 28;
    private static final int TEXT_VIEW_DRAWABLE_ACTION_TAG = 11;
    private static final int TEXT_VIEW_SIZE_ACTION_TAG = 13;
    static final int VALUE_TYPE_ATTRIBUTE = 4;
    static final int VALUE_TYPE_COMPLEX_UNIT = 2;
    static final int VALUE_TYPE_RAW = 1;
    static final int VALUE_TYPE_RESOURCE = 3;
    private static final int VIEW_CONTENT_NAVIGATION_TAG = 5;
    private static final int VIEW_GROUP_ACTION_ADD_TAG = 4;
    private static final int VIEW_GROUP_ACTION_REMOVE_TAG = 7;
    private static final int VIEW_PADDING_ACTION_TAG = 14;
    private java.util.ArrayList<android.widget.RemoteViews.Action> mActions;
    public android.content.pm.ApplicationInfo mApplication;
    private android.widget.RemoteViews.ApplicationInfoCache mApplicationInfoCache;
    private int mApplyFlags;
    private android.widget.RemoteViews.BitmapCache mBitmapCache;
    private java.util.Map<java.lang.Class, java.lang.Object> mClassCookies;
    private android.widget.RemoteViews.RemoteCollectionCache mCollectionCache;
    private android.util.SparseArray<android.content.Intent> mFillInIntent;
    private boolean mHasDrawInstructions;
    private android.util.SizeF mIdealSize;
    private boolean mIsRoot;
    private android.widget.RemoteViews mLandscape;
    private int mLayoutId;
    private android.view.LayoutInflater.Factory2 mLayoutInflaterFactory2;
    private int mLightBackgroundLayoutId;
    private android.util.SparseArray<android.app.PendingIntent> mPendingIntentTemplate;
    private android.widget.RemoteViews mPortrait;
    private long mProviderInstanceId;
    private java.util.List<android.widget.RemoteViews> mSizedRemoteViews;
    private int mViewId;
    private static final android.os.Parcel.ReadWriteHelper ALTERNATIVE_DEFAULT = new android.os.Parcel.ReadWriteHelper();
    private static final android.view.LayoutInflater.Filter INFLATER_FILTER = new android.view.LayoutInflater.Filter() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda1
        @Override // android.view.LayoutInflater.Filter
        public final boolean onLoadClass(java.lang.Class cls) {
            boolean isAnnotationPresent;
            isAnnotationPresent = cls.isAnnotationPresent(android.widget.RemoteViews.RemoteView.class);
            return isAnnotationPresent;
        }
    };
    private static final android.widget.RemoteViews.InteractionHandler DEFAULT_INTERACTION_HANDLER = new android.widget.RemoteViews.InteractionHandler() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda2
        @Override // android.widget.RemoteViews.InteractionHandler
        public final boolean onInteraction(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
            boolean startPendingIntent;
            startPendingIntent = android.widget.RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
            return startPendingIntent;
        }
    };
    private static final android.util.ArrayMap<android.widget.RemoteViews.MethodKey, android.widget.RemoteViews.MethodArgs> sMethods = new android.util.ArrayMap<>();
    private static final android.widget.RemoteViews.MethodKey sLookupKey = new android.widget.RemoteViews.MethodKey();
    private static final android.widget.RemoteViews.Action ACTION_NOOP = new android.widget.RemoteViews.RuntimeAction() { // from class: android.widget.RemoteViews.1
        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
        }
    };
    public static final android.os.Parcelable.Creator<android.widget.RemoteViews> CREATOR = new android.os.Parcelable.Creator<android.widget.RemoteViews>() { // from class: android.widget.RemoteViews.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.widget.RemoteViews createFromParcel(android.os.Parcel parcel) {
            return new android.widget.RemoteViews(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.widget.RemoteViews[] newArray(int i) {
            return new android.widget.RemoteViews[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ApplyFlags {
    }

    public interface InteractionHandler {
        boolean onInteraction(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MarginType {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface RemoteView {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ValueType {
    }

    public void setRemoteInputs(int i, android.app.RemoteInput[] remoteInputArr) {
        this.mActions.add(new android.widget.RemoteViews.SetRemoteInputsAction(i, remoteInputArr));
    }

    public void setLayoutInflaterFactory(android.view.LayoutInflater.Factory2 factory2) {
        this.mLayoutInflaterFactory2 = factory2;
    }

    public android.view.LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this.mLayoutInflaterFactory2;
    }

    public void reduceImageSizes(int i, int i2) {
        java.util.ArrayList<android.graphics.Bitmap> arrayList = this.mBitmapCache.mBitmaps;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            arrayList.set(i3, android.graphics.drawable.Icon.scaleDownIfNecessary(arrayList.get(i3), i, i2));
        }
    }

    public void setIntTag(int i, int i2, int i3) {
        addAction(new android.widget.RemoteViews.SetIntTagAction(i, i2, i3));
    }

    public void addFlags(int i) {
        this.mApplyFlags |= i;
        int i2 = i & 6;
        if (i2 != 0) {
            if (hasSizedRemoteViews()) {
                java.util.Iterator<android.widget.RemoteViews> it = this.mSizedRemoteViews.iterator();
                while (it.hasNext()) {
                    it.next().addFlags(i2);
                }
            } else if (hasLandscapeAndPortraitLayouts()) {
                this.mLandscape.addFlags(i2);
                this.mPortrait.addFlags(i2);
            }
        }
    }

    public boolean hasFlags(int i) {
        return (this.mApplyFlags & i) == i;
    }

    static class MethodKey {
        public java.lang.String methodName;
        public java.lang.Class paramClass;
        public java.lang.Class targetClass;

        MethodKey() {
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.widget.RemoteViews.MethodKey)) {
                return false;
            }
            android.widget.RemoteViews.MethodKey methodKey = (android.widget.RemoteViews.MethodKey) obj;
            return java.util.Objects.equals(methodKey.targetClass, this.targetClass) && java.util.Objects.equals(methodKey.paramClass, this.paramClass) && java.util.Objects.equals(methodKey.methodName, this.methodName);
        }

        public int hashCode() {
            return (java.util.Objects.hashCode(this.targetClass) ^ java.util.Objects.hashCode(this.paramClass)) ^ java.util.Objects.hashCode(this.methodName);
        }

        public void set(java.lang.Class cls, java.lang.Class cls2, java.lang.String str) {
            this.targetClass = cls;
            this.paramClass = cls2;
            this.methodName = str;
        }
    }

    static class MethodArgs {
        public java.lang.invoke.MethodHandle asyncMethod;
        public java.lang.String asyncMethodName;
        public java.lang.invoke.MethodHandle syncMethod;

        MethodArgs() {
        }
    }

    public static class ActionException extends java.lang.RuntimeException {
        public ActionException(java.lang.Exception exc) {
            super(exc);
        }

        public ActionException(java.lang.String str) {
            super(str);
        }

        public ActionException(java.lang.Throwable th) {
            super(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class Action {
        public static final int MERGE_APPEND = 1;
        public static final int MERGE_IGNORE = 2;
        public static final int MERGE_REPLACE = 0;
        int mViewId;

        public abstract void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) throws android.widget.RemoteViews.ActionException;

        public abstract int getActionTag();

        public abstract void writeToParcel(android.os.Parcel parcel, int i);

        private Action() {
        }

        public void setHierarchyRootData(android.widget.RemoteViews.HierarchyRootData hierarchyRootData) {
        }

        public int mergeBehavior() {
            return 0;
        }

        public java.lang.String getUniqueKey() {
            return getActionTag() + android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD + this.mViewId;
        }

        public android.widget.RemoteViews.Action initActionAsync(android.widget.RemoteViews.ViewTree viewTree, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            return this;
        }

        public boolean prefersAsyncApply() {
            return false;
        }

        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
        }
    }

    private static abstract class RuntimeAction extends android.widget.RemoteViews.Action {
        private RuntimeAction() {
            super();
        }

        @Override // android.widget.RemoteViews.Action
        public final int getActionTag() {
            return 0;
        }

        @Override // android.widget.RemoteViews.Action
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public void mergeRemoteViews(android.widget.RemoteViews remoteViews) {
        if (remoteViews == null) {
            return;
        }
        android.widget.RemoteViews remoteViews2 = new android.widget.RemoteViews(remoteViews);
        java.util.HashMap hashMap = new java.util.HashMap();
        if (this.mActions == null) {
            this.mActions = new java.util.ArrayList<>();
        }
        int size = this.mActions.size();
        for (int i = 0; i < size; i++) {
            android.widget.RemoteViews.Action action = this.mActions.get(i);
            hashMap.put(action.getUniqueKey(), action);
        }
        java.util.ArrayList<android.widget.RemoteViews.Action> arrayList = remoteViews2.mActions;
        if (arrayList == null) {
            return;
        }
        int size2 = arrayList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            android.widget.RemoteViews.Action action2 = arrayList.get(i2);
            java.lang.String uniqueKey = arrayList.get(i2).getUniqueKey();
            int mergeBehavior = arrayList.get(i2).mergeBehavior();
            if (hashMap.containsKey(uniqueKey) && mergeBehavior == 0) {
                this.mActions.remove(hashMap.get(uniqueKey));
                hashMap.remove(uniqueKey);
            }
            if (mergeBehavior == 0 || mergeBehavior == 1) {
                this.mActions.add(action2);
            }
        }
        reconstructCaches();
    }

    public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
        if (this.mActions != null) {
            for (int i = 0; i < this.mActions.size(); i++) {
                this.mActions.get(i).visitUris(consumer);
            }
        }
        if (this.mSizedRemoteViews != null) {
            for (int i2 = 0; i2 < this.mSizedRemoteViews.size(); i2++) {
                this.mSizedRemoteViews.get(i2).visitUris(consumer);
            }
        }
        if (this.mLandscape != null) {
            this.mLandscape.visitUris(consumer);
        }
        if (this.mPortrait != null) {
            this.mPortrait.visitUris(consumer);
        }
    }

    public boolean replaceRemoteCollections(int i) {
        boolean z;
        if (this.mActions == null) {
            z = false;
        } else {
            z = false;
            for (int i2 = 0; i2 < this.mActions.size(); i2++) {
                android.widget.RemoteViews.Action action = this.mActions.get(i2);
                if (action instanceof android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction) {
                    android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction setRemoteCollectionItemListAdapterAction = (android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction) action;
                    if (setRemoteCollectionItemListAdapterAction.mViewId == i && setRemoteCollectionItemListAdapterAction.mServiceIntent != null) {
                        android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction setRemoteCollectionItemListAdapterAction2 = new android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction(setRemoteCollectionItemListAdapterAction.mViewId, setRemoteCollectionItemListAdapterAction.mServiceIntent);
                        setRemoteCollectionItemListAdapterAction2.mIntentId = setRemoteCollectionItemListAdapterAction.mIntentId;
                        setRemoteCollectionItemListAdapterAction2.mIsReplacedIntoAction = true;
                        this.mActions.set(i2, setRemoteCollectionItemListAdapterAction2);
                        z = true;
                    }
                }
                if (action instanceof android.widget.RemoteViews.SetRemoteViewsAdapterIntent) {
                    android.widget.RemoteViews.SetRemoteViewsAdapterIntent setRemoteViewsAdapterIntent = (android.widget.RemoteViews.SetRemoteViewsAdapterIntent) action;
                    if (setRemoteViewsAdapterIntent.mViewId == i) {
                        this.mActions.set(i2, new android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction(setRemoteViewsAdapterIntent.mViewId, setRemoteViewsAdapterIntent.mIntent));
                        z = true;
                    }
                }
                if (action instanceof android.widget.RemoteViews.ViewGroupActionAdd) {
                    android.widget.RemoteViews.ViewGroupActionAdd viewGroupActionAdd = (android.widget.RemoteViews.ViewGroupActionAdd) action;
                    if (viewGroupActionAdd.mNestedViews != null) {
                        z |= viewGroupActionAdd.mNestedViews.replaceRemoteCollections(i);
                    }
                }
            }
        }
        if (this.mSizedRemoteViews != null) {
            for (int i3 = 0; i3 < this.mSizedRemoteViews.size(); i3++) {
                z |= this.mSizedRemoteViews.get(i3).replaceRemoteCollections(i);
            }
        }
        if (this.mLandscape != null) {
            z |= this.mLandscape.replaceRemoteCollections(i);
        }
        if (this.mPortrait != null) {
            return z | this.mPortrait.replaceRemoteCollections(i);
        }
        return z;
    }

    public boolean hasLegacyLists() {
        if (this.mActions != null) {
            for (int i = 0; i < this.mActions.size(); i++) {
                android.widget.RemoteViews.Action action = this.mActions.get(i);
                if ((!(action instanceof android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction) || ((android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction) action).mServiceIntent == null) && (!(action instanceof android.widget.RemoteViews.SetRemoteViewsAdapterIntent) || ((android.widget.RemoteViews.SetRemoteViewsAdapterIntent) action).mIntent == null)) {
                    if (action instanceof android.widget.RemoteViews.ViewGroupActionAdd) {
                        android.widget.RemoteViews.ViewGroupActionAdd viewGroupActionAdd = (android.widget.RemoteViews.ViewGroupActionAdd) action;
                        if (viewGroupActionAdd.mNestedViews != null && viewGroupActionAdd.mNestedViews.hasLegacyLists()) {
                        }
                    }
                }
                return true;
            }
        }
        if (this.mSizedRemoteViews != null) {
            for (int i2 = 0; i2 < this.mSizedRemoteViews.size(); i2++) {
                if (this.mSizedRemoteViews.get(i2).hasLegacyLists()) {
                    return true;
                }
            }
        }
        if (this.mLandscape == null || !this.mLandscape.hasLegacyLists()) {
            return this.mPortrait != null && this.mPortrait.hasLegacyLists();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void visitIconUri(android.graphics.drawable.Icon icon, java.util.function.Consumer<android.net.Uri> consumer) {
        if (icon != null) {
            if (icon.getType() == 4 || icon.getType() == 6) {
                consumer.accept(icon.getUri());
            }
        }
    }

    private static class RemoteViewsContextWrapper extends android.content.ContextWrapper {
        private final android.content.Context mContextForResources;

        RemoteViewsContextWrapper(android.content.Context context, android.content.Context context2) {
            super(context);
            this.mContextForResources = context2;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public android.content.res.Resources getResources() {
            return this.mContextForResources.getResources();
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public android.content.res.Resources.Theme getTheme() {
            return this.mContextForResources.getTheme();
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public java.lang.String getPackageName() {
            return this.mContextForResources.getPackageName();
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public android.os.UserHandle getUser() {
            return this.mContextForResources.getUser();
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public int getUserId() {
            return this.mContextForResources.getUserId();
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public boolean isRestricted() {
            return this.mContextForResources.isRestricted();
        }
    }

    private static class SetEmptyView extends android.widget.RemoteViews.Action {
        int mEmptyViewId;

        SetEmptyView(int i, int i2) {
            super();
            this.mViewId = i;
            this.mEmptyViewId = i2;
        }

        SetEmptyView(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mEmptyViewId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mEmptyViewId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById instanceof android.widget.AdapterView) {
                android.widget.AdapterView adapterView = (android.widget.AdapterView) findViewById;
                android.view.View findViewById2 = view.findViewById(this.mEmptyViewId);
                if (findViewById2 == null) {
                    return;
                }
                adapterView.setEmptyView(findViewById2);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 6;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetPendingIntentTemplate extends android.widget.RemoteViews.Action {
        android.app.PendingIntent mPendingIntentTemplate;

        public SetPendingIntentTemplate(int i, android.app.PendingIntent pendingIntent) {
            super();
            this.mViewId = i;
            this.mPendingIntentTemplate = pendingIntent;
        }

        public SetPendingIntentTemplate(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mPendingIntentTemplate = android.app.PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            android.app.PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntentTemplate, parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, final android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            if (findViewById instanceof android.widget.AdapterView) {
                android.widget.AdapterView adapterView = (android.widget.AdapterView) findViewById;
                adapterView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: android.widget.RemoteViews$SetPendingIntentTemplate$$ExternalSyntheticLambda0
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(android.widget.AdapterView adapterView2, android.view.View view2, int i, long j) {
                        android.widget.RemoteViews.SetPendingIntentTemplate.this.lambda$apply$0(actionApplyParams, adapterView2, view2, i, j);
                    }
                });
                adapterView.setTag(this.mPendingIntentTemplate);
                return;
            }
            android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Cannot setPendingIntentTemplate on a view which is notan AdapterView (id: " + this.mViewId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(android.widget.RemoteViews.ActionApplyParams actionApplyParams, android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
            android.widget.RemoteViews.RemoteResponse findRemoteResponseTag = findRemoteResponseTag(view);
            if (findRemoteResponseTag != null) {
                findRemoteResponseTag.handleViewInteraction(view, actionApplyParams.handler);
            }
        }

        private android.widget.RemoteViews.RemoteResponse findRemoteResponseTag(android.view.View view) {
            if (view == null) {
                return null;
            }
            java.util.ArrayDeque arrayDeque = new java.util.ArrayDeque();
            arrayDeque.addLast(view);
            while (!arrayDeque.isEmpty()) {
                android.view.View view2 = (android.view.View) arrayDeque.removeFirst();
                java.lang.Object tag = view2.getTag(com.android.internal.R.id.fillInIntent);
                if (tag instanceof android.widget.RemoteViews.RemoteResponse) {
                    return (android.widget.RemoteViews.RemoteResponse) tag;
                }
                if (view2 instanceof android.view.ViewGroup) {
                    android.view.ViewGroup viewGroup = (android.view.ViewGroup) view2;
                    for (int i = 0; i < viewGroup.getChildCount(); i++) {
                        arrayDeque.addLast(viewGroup.getChildAt(i));
                    }
                }
            }
            return null;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 8;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            this.mPendingIntentTemplate.visitUris(consumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ApplicationInfoCache {
        private final java.util.Map<android.util.Pair<java.lang.String, java.lang.Integer>, android.content.pm.ApplicationInfo> mPackageUserToApplicationInfo = new android.util.ArrayMap();

        ApplicationInfoCache() {
        }

        android.content.pm.ApplicationInfo getOrPut(final android.content.pm.ApplicationInfo applicationInfo) {
            android.util.Pair<java.lang.String, java.lang.Integer> packageUserKey = android.widget.RemoteViews.getPackageUserKey(applicationInfo);
            if (packageUserKey == null) {
                return null;
            }
            return this.mPackageUserToApplicationInfo.computeIfAbsent(packageUserKey, new java.util.function.Function() { // from class: android.widget.RemoteViews$ApplicationInfoCache$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.widget.RemoteViews.ApplicationInfoCache.lambda$getOrPut$0(android.content.pm.ApplicationInfo.this, (android.util.Pair) obj);
                }
            });
        }

        static /* synthetic */ android.content.pm.ApplicationInfo lambda$getOrPut$0(android.content.pm.ApplicationInfo applicationInfo, android.util.Pair pair) {
            return applicationInfo;
        }

        void put(android.content.pm.ApplicationInfo applicationInfo) {
            android.util.Pair<java.lang.String, java.lang.Integer> packageUserKey = android.widget.RemoteViews.getPackageUserKey(applicationInfo);
            if (packageUserKey == null) {
                return;
            }
            this.mPackageUserToApplicationInfo.put(packageUserKey, applicationInfo);
        }

        android.content.pm.ApplicationInfo get(android.content.pm.ApplicationInfo applicationInfo) {
            android.util.Pair packageUserKey = android.widget.RemoteViews.getPackageUserKey(applicationInfo);
            if (packageUserKey == null) {
                return null;
            }
            return this.mPackageUserToApplicationInfo.get(packageUserKey);
        }
    }

    private class SetRemoteCollectionItemListAdapterAction extends android.widget.RemoteViews.Action {
        int mIntentId;
        boolean mIsReplacedIntoAction;
        private android.widget.RemoteViews.RemoteCollectionItems mItems;
        final android.content.Intent mServiceIntent;

        SetRemoteCollectionItemListAdapterAction(int i, android.widget.RemoteViews.RemoteCollectionItems remoteCollectionItems) {
            super();
            this.mIntentId = -1;
            this.mIsReplacedIntoAction = false;
            this.mViewId = i;
            remoteCollectionItems.setHierarchyRootData(android.widget.RemoteViews.this.getHierarchyRootData());
            this.mItems = remoteCollectionItems;
            this.mServiceIntent = null;
        }

        SetRemoteCollectionItemListAdapterAction(int i, android.content.Intent intent) {
            super();
            this.mIntentId = -1;
            this.mIsReplacedIntoAction = false;
            this.mViewId = i;
            this.mItems = null;
            this.mServiceIntent = intent;
        }

        SetRemoteCollectionItemListAdapterAction(android.os.Parcel parcel) {
            super();
            this.mIntentId = -1;
            this.mIsReplacedIntoAction = false;
            this.mViewId = parcel.readInt();
            this.mIntentId = parcel.readInt();
            this.mServiceIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
            this.mItems = this.mServiceIntent == null ? new android.widget.RemoteViews.RemoteCollectionItems(parcel, android.widget.RemoteViews.this.getHierarchyRootData()) : null;
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(android.widget.RemoteViews.HierarchyRootData hierarchyRootData) {
            if (this.mItems != null) {
                this.mItems.setHierarchyRootData(hierarchyRootData);
            } else if (this.mIntentId != -1) {
                android.widget.RemoteViews.this.mCollectionCache.setHierarchyDataForId(this.mIntentId, hierarchyRootData);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mIntentId);
            parcel.writeTypedObject(this.mServiceIntent, i);
            if (this.mItems != null) {
                this.mItems.writeToParcel(parcel, i, true);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) throws android.widget.RemoteViews.ActionException {
            android.widget.RemoteViews.RemoteCollectionItems itemsForId;
            android.widget.RemoteViews.ActionException actionException;
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            if (this.mIntentId == -1) {
                if (this.mItems == null) {
                    itemsForId = new android.widget.RemoteViews.RemoteCollectionItems.Builder().build();
                } else {
                    itemsForId = this.mItems;
                }
            } else {
                itemsForId = android.widget.RemoteViews.this.mCollectionCache.getItemsForId(this.mIntentId);
            }
            if (!(viewGroup instanceof android.appwidget.AppWidgetHostView)) {
                android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "setRemoteAdapter can only be used for AppWidgets (root id: " + this.mViewId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (!(findViewById instanceof android.widget.AdapterView)) {
                android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Cannot call setRemoteAdapter on a view which is not an AdapterView (id: " + this.mViewId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            android.widget.AdapterView adapterView = (android.widget.AdapterView) findViewById;
            android.widget.Adapter adapter = adapterView.getAdapter();
            if ((adapter instanceof android.widget.RemoteCollectionItemsAdapter) && adapter.getViewTypeCount() >= itemsForId.getViewTypeCount()) {
                try {
                    ((android.widget.RemoteCollectionItemsAdapter) adapter).setData(itemsForId, actionApplyParams.handler, actionApplyParams.colorResources);
                } finally {
                }
            } else {
                try {
                    adapterView.setAdapter(new android.widget.RemoteCollectionItemsAdapter(itemsForId, actionApplyParams.handler, actionApplyParams.colorResources));
                } finally {
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 31;
        }

        @Override // android.widget.RemoteViews.Action
        public java.lang.String getUniqueKey() {
            return "33_" + this.mViewId;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            if (this.mIntentId != -1 || this.mItems == null) {
                return;
            }
            this.mItems.visitUris(consumer);
        }
    }

    public java.util.concurrent.CompletableFuture<java.lang.Void> collectAllIntents() {
        return this.mCollectionCache.collectAllIntentsNoComplete(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class RemoteCollectionCache {
        private android.util.SparseArray<java.lang.String> mIdToUriMapping = new android.util.SparseArray<>();
        private java.util.HashMap<java.lang.String, android.widget.RemoteViews.RemoteCollectionItems> mUriToCollectionMapping = new java.util.HashMap<>();

        RemoteCollectionCache() {
        }

        RemoteCollectionCache(android.widget.RemoteViews.RemoteCollectionCache remoteCollectionCache) {
            for (int i = 0; i < remoteCollectionCache.mIdToUriMapping.size(); i++) {
                java.lang.String valueAt = remoteCollectionCache.mIdToUriMapping.valueAt(i);
                this.mIdToUriMapping.put(remoteCollectionCache.mIdToUriMapping.keyAt(i), valueAt);
                this.mUriToCollectionMapping.put(valueAt, remoteCollectionCache.mUriToCollectionMapping.get(valueAt));
            }
        }

        RemoteCollectionCache(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            android.widget.RemoteViews.HierarchyRootData hierarchyRootData = new android.widget.RemoteViews.HierarchyRootData(android.widget.RemoteViews.this.mBitmapCache, this, android.widget.RemoteViews.this.mApplicationInfoCache, android.widget.RemoteViews.this.mClassCookies);
            for (int i = 0; i < readInt; i++) {
                int readInt2 = parcel.readInt();
                java.lang.String readString8 = parcel.readString8();
                android.widget.RemoteViews.RemoteCollectionItems remoteCollectionItems = new android.widget.RemoteViews.RemoteCollectionItems(parcel, hierarchyRootData);
                this.mIdToUriMapping.put(readInt2, readString8);
                this.mUriToCollectionMapping.put(readString8, remoteCollectionItems);
            }
        }

        void setHierarchyDataForId(int i, android.widget.RemoteViews.HierarchyRootData hierarchyRootData) {
            java.lang.String str = this.mIdToUriMapping.get(i);
            if (this.mUriToCollectionMapping.get(str) == null) {
                android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Error setting hierarchy data for id=" + i);
            } else {
                this.mUriToCollectionMapping.get(str).setHierarchyRootData(hierarchyRootData);
            }
        }

        android.widget.RemoteViews.RemoteCollectionItems getItemsForId(int i) {
            return this.mUriToCollectionMapping.get(this.mIdToUriMapping.get(i));
        }

        java.util.concurrent.CompletableFuture<java.lang.Void> collectAllIntentsNoComplete(android.widget.RemoteViews remoteViews) {
            java.util.concurrent.CompletableFuture<java.lang.Void> completedFuture = java.util.concurrent.CompletableFuture.completedFuture(null);
            if (remoteViews.hasSizedRemoteViews()) {
                java.util.Iterator it = remoteViews.mSizedRemoteViews.iterator();
                while (it.hasNext()) {
                    completedFuture = java.util.concurrent.CompletableFuture.allOf(completedFuture, collectAllIntentsNoComplete((android.widget.RemoteViews) it.next()));
                }
                return completedFuture;
            }
            if (remoteViews.hasLandscapeAndPortraitLayouts()) {
                return java.util.concurrent.CompletableFuture.allOf(collectAllIntentsNoComplete(remoteViews.mLandscape), collectAllIntentsNoComplete(remoteViews.mPortrait));
            }
            if (remoteViews.mActions != null) {
                java.util.Iterator it2 = remoteViews.mActions.iterator();
                while (it2.hasNext()) {
                    android.widget.RemoteViews.Action action = (android.widget.RemoteViews.Action) it2.next();
                    if (action instanceof android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction) {
                        android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction setRemoteCollectionItemListAdapterAction = (android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction) action;
                        if (setRemoteCollectionItemListAdapterAction.mIntentId == -1 || setRemoteCollectionItemListAdapterAction.mIsReplacedIntoAction) {
                            if (setRemoteCollectionItemListAdapterAction.mIntentId != -1 && setRemoteCollectionItemListAdapterAction.mIsReplacedIntoAction) {
                                final java.lang.String str = this.mIdToUriMapping.get(setRemoteCollectionItemListAdapterAction.mIntentId);
                                completedFuture = java.util.concurrent.CompletableFuture.allOf(completedFuture, getItemsFutureFromIntentWithTimeout(setRemoteCollectionItemListAdapterAction.mServiceIntent).thenAccept(new java.util.function.Consumer() { // from class: android.widget.RemoteViews$RemoteCollectionCache$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        android.widget.RemoteViews.RemoteCollectionCache.this.lambda$collectAllIntentsNoComplete$0(str, (android.widget.RemoteViews.RemoteCollectionItems) obj);
                                    }
                                }));
                                setRemoteCollectionItemListAdapterAction.mItems = null;
                            } else if (setRemoteCollectionItemListAdapterAction.mServiceIntent != null) {
                                final java.lang.String uri = setRemoteCollectionItemListAdapterAction.mServiceIntent.toUri(0);
                                int indexOfValue = this.mIdToUriMapping.indexOfValue(uri);
                                if (indexOfValue == -1) {
                                    int size = this.mIdToUriMapping.size();
                                    setRemoteCollectionItemListAdapterAction.mIntentId = size;
                                    this.mIdToUriMapping.put(size, uri);
                                    completedFuture = java.util.concurrent.CompletableFuture.allOf(completedFuture, getItemsFutureFromIntentWithTimeout(setRemoteCollectionItemListAdapterAction.mServiceIntent).thenAccept(new java.util.function.Consumer() { // from class: android.widget.RemoteViews$RemoteCollectionCache$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Consumer
                                        public final void accept(java.lang.Object obj) {
                                            android.widget.RemoteViews.RemoteCollectionCache.this.lambda$collectAllIntentsNoComplete$1(uri, (android.widget.RemoteViews.RemoteCollectionItems) obj);
                                        }
                                    }));
                                    setRemoteCollectionItemListAdapterAction.mItems = null;
                                } else {
                                    setRemoteCollectionItemListAdapterAction.mIntentId = this.mIdToUriMapping.keyAt(indexOfValue);
                                    setRemoteCollectionItemListAdapterAction.mItems = null;
                                }
                            } else {
                                for (android.widget.RemoteViews remoteViews2 : setRemoteCollectionItemListAdapterAction.mItems.mViews) {
                                    completedFuture = java.util.concurrent.CompletableFuture.allOf(completedFuture, collectAllIntentsNoComplete(remoteViews2));
                                }
                            }
                        }
                    } else if (action instanceof android.widget.RemoteViews.ViewGroupActionAdd) {
                        android.widget.RemoteViews.ViewGroupActionAdd viewGroupActionAdd = (android.widget.RemoteViews.ViewGroupActionAdd) action;
                        if (viewGroupActionAdd.mNestedViews != null) {
                            completedFuture = java.util.concurrent.CompletableFuture.allOf(completedFuture, collectAllIntentsNoComplete(viewGroupActionAdd.mNestedViews));
                        }
                    }
                }
                return completedFuture;
            }
            return completedFuture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$collectAllIntentsNoComplete$0(java.lang.String str, android.widget.RemoteViews.RemoteCollectionItems remoteCollectionItems) {
            remoteCollectionItems.setHierarchyRootData(android.widget.RemoteViews.this.getHierarchyRootData());
            this.mUriToCollectionMapping.put(str, remoteCollectionItems);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$collectAllIntentsNoComplete$1(java.lang.String str, android.widget.RemoteViews.RemoteCollectionItems remoteCollectionItems) {
            remoteCollectionItems.setHierarchyRootData(android.widget.RemoteViews.this.getHierarchyRootData());
            this.mUriToCollectionMapping.put(str, remoteCollectionItems);
        }

        private static java.util.concurrent.CompletableFuture<android.widget.RemoteViews.RemoteCollectionItems> getItemsFutureFromIntentWithTimeout(android.content.Intent intent) {
            if (intent == null) {
                android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Null intent received when generating adapter future");
                return java.util.concurrent.CompletableFuture.completedFuture(new android.widget.RemoteViews.RemoteCollectionItems.Builder().build());
            }
            final android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
            final java.util.concurrent.CompletableFuture<android.widget.RemoteViews.RemoteCollectionItems> completableFuture = new java.util.concurrent.CompletableFuture<>();
            currentApplication.bindService(intent, android.content.Context.BindServiceFlags.of(1L), completableFuture.defaultExecutor(), new android.content.ServiceConnection() { // from class: android.widget.RemoteViews.RemoteCollectionCache.1
                @Override // android.content.ServiceConnection
                public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                    android.widget.RemoteViews.RemoteCollectionItems remoteCollectionItems;
                    try {
                        try {
                            remoteCollectionItems = com.android.internal.widget.IRemoteViewsFactory.Stub.asInterface(iBinder).getRemoteCollectionItems();
                        } catch (android.os.RemoteException e) {
                            android.widget.RemoteViews.RemoteCollectionItems build = new android.widget.RemoteViews.RemoteCollectionItems.Builder().build();
                            android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Error getting collection items from the factory", e);
                            android.content.Context.this.unbindService(this);
                            remoteCollectionItems = build;
                        }
                        completableFuture.complete(remoteCollectionItems);
                    } finally {
                        android.content.Context.this.unbindService(this);
                    }
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(android.content.ComponentName componentName) {
                }
            });
            completableFuture.completeOnTimeout(new android.widget.RemoteViews.RemoteCollectionItems.Builder().build(), 5000L, java.util.concurrent.TimeUnit.MILLISECONDS);
            return completableFuture;
        }

        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mIdToUriMapping.size());
            for (int i2 = 0; i2 < this.mIdToUriMapping.size(); i2++) {
                parcel.writeInt(this.mIdToUriMapping.keyAt(i2));
                java.lang.String valueAt = this.mIdToUriMapping.valueAt(i2);
                parcel.writeString8(valueAt);
                this.mUriToCollectionMapping.get(valueAt).writeToParcel(parcel, i, true);
            }
        }
    }

    private class SetRemoteViewsAdapterIntent extends android.widget.RemoteViews.Action {
        android.content.Intent mIntent;
        boolean mIsAsync;

        public SetRemoteViewsAdapterIntent(int i, android.content.Intent intent) {
            super();
            this.mIsAsync = false;
            this.mViewId = i;
            this.mIntent = intent;
        }

        public SetRemoteViewsAdapterIntent(android.os.Parcel parcel) {
            super();
            this.mIsAsync = false;
            this.mViewId = parcel.readInt();
            this.mIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeTypedObject(this.mIntent, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            if (!(viewGroup instanceof android.appwidget.AppWidgetHostView)) {
                android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "setRemoteAdapter can only be used for AppWidgets (root id: " + this.mViewId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            boolean z = findViewById instanceof android.widget.AbsListView;
            if (!z && !(findViewById instanceof android.widget.AdapterViewAnimator)) {
                android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Cannot setRemoteAdapter on a view which is not an AbsListView or AdapterViewAnimator (id: " + this.mViewId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            this.mIntent.putExtra(android.widget.RemoteViews.EXTRA_REMOTEADAPTER_APPWIDGET_ID, ((android.appwidget.AppWidgetHostView) viewGroup).getAppWidgetId()).putExtra(android.widget.RemoteViews.EXTRA_REMOTEADAPTER_ON_LIGHT_BACKGROUND, android.widget.RemoteViews.this.hasFlags(4));
            if (z) {
                android.widget.AbsListView absListView = (android.widget.AbsListView) findViewById;
                absListView.setRemoteViewsAdapter(this.mIntent, this.mIsAsync);
                absListView.setRemoteViewsInteractionHandler(actionApplyParams.handler);
            } else if (findViewById instanceof android.widget.AdapterViewAnimator) {
                android.widget.AdapterViewAnimator adapterViewAnimator = (android.widget.AdapterViewAnimator) findViewById;
                adapterViewAnimator.setRemoteViewsAdapter(this.mIntent, this.mIsAsync);
                adapterViewAnimator.setRemoteViewsOnClickHandler(actionApplyParams.handler);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public android.widget.RemoteViews.Action initActionAsync(android.widget.RemoteViews.ViewTree viewTree, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.widget.RemoteViews.SetRemoteViewsAdapterIntent setRemoteViewsAdapterIntent = android.widget.RemoteViews.this.new SetRemoteViewsAdapterIntent(this.mViewId, this.mIntent);
            setRemoteViewsAdapterIntent.mIsAsync = true;
            return setRemoteViewsAdapterIntent;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 10;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            this.mIntent.visitUris(consumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SetOnClickResponse extends android.widget.RemoteViews.Action {
        final android.widget.RemoteViews.RemoteResponse mResponse;

        SetOnClickResponse(int i, android.widget.RemoteViews.RemoteResponse remoteResponse) {
            super();
            this.mViewId = i;
            this.mResponse = remoteResponse;
        }

        SetOnClickResponse(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mResponse = new android.widget.RemoteViews.RemoteResponse();
            this.mResponse.readFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            this.mResponse.writeToParcel(parcel, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, final android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById;
            if ((android.widget.RemoteViews.this.hasDrawInstructions() && (view instanceof com.android.internal.widget.remotecompose.player.RemoteComposePlayer)) || (findViewById = view.findViewById(this.mViewId)) == null) {
                return;
            }
            if (this.mResponse.mPendingIntent != null) {
                if (android.widget.RemoteViews.this.hasFlags(2)) {
                    android.util.Log.w(android.widget.RemoteViews.LOG_TAG, "Cannot SetOnClickResponse for collection item (id: " + this.mViewId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    android.content.pm.ApplicationInfo applicationInfo = view.getContext().getApplicationInfo();
                    if (applicationInfo != null && applicationInfo.targetSdkVersion >= 16) {
                        return;
                    }
                }
                findViewById.setTagInternal(com.android.internal.R.id.pending_intent_tag, this.mResponse.mPendingIntent);
            } else if (this.mResponse.mFillIntent != null) {
                if (!android.widget.RemoteViews.this.hasFlags(2)) {
                    android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "The method setOnClickFillInIntent is available only from RemoteViewsFactory (ie. on collection items).");
                    return;
                } else if (findViewById == view) {
                    findViewById.setTagInternal(com.android.internal.R.id.fillInIntent, this.mResponse);
                    return;
                }
            } else {
                findViewById.setOnClickListener(null);
                findViewById.setTagInternal(com.android.internal.R.id.pending_intent_tag, null);
                findViewById.setTagInternal(com.android.internal.R.id.fillInIntent, null);
                return;
            }
            findViewById.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.widget.RemoteViews$SetOnClickResponse$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view2) {
                    android.widget.RemoteViews.SetOnClickResponse.this.lambda$apply$0(actionApplyParams, view2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(android.widget.RemoteViews.ActionApplyParams actionApplyParams, android.view.View view) {
            this.mResponse.handleViewInteraction(view, actionApplyParams.handler);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 1;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            this.mResponse.visitUris(consumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SetOnStylusHandwritingResponse extends android.widget.RemoteViews.Action {
        final android.app.PendingIntent mPendingIntent;

        SetOnStylusHandwritingResponse(int i, android.app.PendingIntent pendingIntent) {
            super();
            this.mViewId = i;
            this.mPendingIntent = pendingIntent;
        }

        SetOnStylusHandwritingResponse(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mPendingIntent = android.app.PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            android.app.PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntent, parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, final android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            final android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            if (android.widget.RemoteViews.this.hasFlags(2)) {
                android.util.Log.w(android.widget.RemoteViews.LOG_TAG, "Cannot use setOnStylusHandwritingPendingIntent for collection item (id: " + this.mViewId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (this.mPendingIntent != null) {
                final android.widget.RemoteViews.RemoteResponse fromPendingIntent = android.widget.RemoteViews.RemoteResponse.fromPendingIntent(this.mPendingIntent);
                findViewById.setHandwritingDelegatorCallback(new java.lang.Runnable() { // from class: android.widget.RemoteViews$SetOnStylusHandwritingResponse$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.widget.RemoteViews.RemoteResponse.this.handleViewInteraction(findViewById, actionApplyParams.handler);
                    }
                });
                findViewById.setAllowedHandwritingDelegatePackage(this.mPendingIntent.getCreatorPackage());
            } else {
                findViewById.setHandwritingDelegatorCallback(null);
                findViewById.setAllowedHandwritingDelegatePackage(null);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 34;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            this.mPendingIntent.visitUris(consumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SetOnCheckedChangeResponse extends android.widget.RemoteViews.Action {
        private final android.widget.RemoteViews.RemoteResponse mResponse;

        SetOnCheckedChangeResponse(int i, android.widget.RemoteViews.RemoteResponse remoteResponse) {
            super();
            this.mViewId = i;
            this.mResponse = remoteResponse;
        }

        SetOnCheckedChangeResponse(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mResponse = new android.widget.RemoteViews.RemoteResponse();
            this.mResponse.readFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            this.mResponse.writeToParcel(parcel, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, final android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            if (!(findViewById instanceof android.widget.CompoundButton)) {
                android.util.Log.w(android.widget.RemoteViews.LOG_TAG, "setOnCheckedChange methods cannot be used on non-CompoundButton child (id: " + this.mViewId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            android.widget.CompoundButton compoundButton = (android.widget.CompoundButton) findViewById;
            if (this.mResponse.mPendingIntent != null) {
                if (android.widget.RemoteViews.this.hasFlags(2)) {
                    android.util.Log.w(android.widget.RemoteViews.LOG_TAG, "Cannot setOnCheckedChangePendingIntent for collection item (id: " + this.mViewId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    return;
                }
                findViewById.setTagInternal(com.android.internal.R.id.pending_intent_tag, this.mResponse.mPendingIntent);
            } else if (this.mResponse.mFillIntent != null) {
                if (!android.widget.RemoteViews.this.hasFlags(2)) {
                    android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "The method setOnCheckedChangeFillInIntent is available only from RemoteViewsFactory (ie. on collection items).");
                    return;
                }
            } else {
                compoundButton.setOnCheckedChangeListener(null);
                compoundButton.setTagInternal(com.android.internal.R.id.remote_checked_change_listener_tag, null);
                return;
            }
            android.widget.CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new android.widget.CompoundButton.OnCheckedChangeListener() { // from class: android.widget.RemoteViews$SetOnCheckedChangeResponse$$ExternalSyntheticLambda0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(android.widget.CompoundButton compoundButton2, boolean z) {
                    android.widget.RemoteViews.SetOnCheckedChangeResponse.this.lambda$apply$0(actionApplyParams, compoundButton2, z);
                }
            };
            compoundButton.setTagInternal(com.android.internal.R.id.remote_checked_change_listener_tag, onCheckedChangeListener);
            compoundButton.setOnCheckedChangeListener(onCheckedChangeListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(android.widget.RemoteViews.ActionApplyParams actionApplyParams, android.widget.CompoundButton compoundButton, boolean z) {
            this.mResponse.handleViewInteraction(compoundButton, actionApplyParams.handler);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 29;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            this.mResponse.visitUris(consumer);
        }
    }

    public static android.graphics.Rect getSourceBounds(android.view.View view) {
        float f = view.getContext().getResources().getCompatibilityInfo().applicationScale;
        view.getLocationOnScreen(new int[2]);
        android.graphics.Rect rect = new android.graphics.Rect();
        rect.left = (int) ((r1[0] * f) + 0.5f);
        rect.top = (int) ((r1[1] * f) + 0.5f);
        rect.right = (int) (((r1[0] + view.getWidth()) * f) + 0.5f);
        rect.bottom = (int) (((r1[1] + view.getHeight()) * f) + 0.5f);
        return rect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.Class<?> getParameterType(int i) {
        switch (i) {
            case 1:
                return java.lang.Boolean.TYPE;
            case 2:
                return java.lang.Byte.TYPE;
            case 3:
                return java.lang.Short.TYPE;
            case 4:
                return java.lang.Integer.TYPE;
            case 5:
                return java.lang.Long.TYPE;
            case 6:
                return java.lang.Float.TYPE;
            case 7:
                return java.lang.Double.TYPE;
            case 8:
                return java.lang.Character.TYPE;
            case 9:
                return java.lang.String.class;
            case 10:
                return java.lang.CharSequence.class;
            case 11:
                return android.net.Uri.class;
            case 12:
                return android.graphics.Bitmap.class;
            case 13:
                return android.os.Bundle.class;
            case 14:
                return android.content.Intent.class;
            case 15:
                return android.content.res.ColorStateList.class;
            case 16:
                return android.graphics.drawable.Icon.class;
            case 17:
                return android.graphics.BlendMode.class;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.invoke.MethodHandle getMethod(android.view.View view, java.lang.String str, java.lang.Class<?> cls, boolean z) {
        java.lang.reflect.Method method;
        java.lang.Class<?> cls2 = view.getClass();
        synchronized (sMethods) {
            sLookupKey.set(cls2, cls, str);
            android.widget.RemoteViews.MethodArgs methodArgs = sMethods.get(sLookupKey);
            if (methodArgs == null) {
                try {
                    if (cls == null) {
                        method = cls2.getMethod(str, new java.lang.Class[0]);
                    } else {
                        method = cls2.getMethod(str, cls);
                    }
                    if (!method.isAnnotationPresent(android.view.RemotableViewMethod.class)) {
                        throw new android.widget.RemoteViews.ActionException("view: " + cls2.getName() + " can't use method with RemoteViews: " + str + getParameters(cls));
                    }
                    android.widget.RemoteViews.MethodArgs methodArgs2 = new android.widget.RemoteViews.MethodArgs();
                    methodArgs2.syncMethod = java.lang.invoke.MethodHandles.publicLookup().unreflect(method);
                    methodArgs2.asyncMethodName = ((android.view.RemotableViewMethod) method.getAnnotation(android.view.RemotableViewMethod.class)).asyncImpl();
                    android.widget.RemoteViews.MethodKey methodKey = new android.widget.RemoteViews.MethodKey();
                    methodKey.set(cls2, cls, str);
                    sMethods.put(methodKey, methodArgs2);
                    methodArgs = methodArgs2;
                } catch (java.lang.IllegalAccessException | java.lang.NoSuchMethodException e) {
                    throw new android.widget.RemoteViews.ActionException("view: " + cls2.getName() + " doesn't have method: " + str + getParameters(cls));
                }
            }
            if (!z) {
                return methodArgs.syncMethod;
            }
            if (methodArgs.asyncMethodName.isEmpty()) {
                return null;
            }
            if (methodArgs.asyncMethod == null) {
                java.lang.invoke.MethodType changeReturnType = methodArgs.syncMethod.type().dropParameterTypes(0, 1).changeReturnType(java.lang.Runnable.class);
                try {
                    methodArgs.asyncMethod = java.lang.invoke.MethodHandles.publicLookup().findVirtual(cls2, methodArgs.asyncMethodName, changeReturnType);
                } catch (java.lang.IllegalAccessException | java.lang.NoSuchMethodException e2) {
                    throw new android.widget.RemoteViews.ActionException("Async implementation declared as " + methodArgs.asyncMethodName + " but not defined for " + str + ": public Runnable " + methodArgs.asyncMethodName + " (" + android.text.TextUtils.join(",", changeReturnType.parameterArray()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
            }
            return methodArgs.asyncMethod;
        }
    }

    private static java.lang.String getParameters(java.lang.Class<?> cls) {
        return cls == null ? "()" : android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + cls + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    private static class SetDrawableTint extends android.widget.RemoteViews.Action {
        int mColorFilter;
        android.graphics.PorterDuff.Mode mFilterMode;
        boolean mTargetBackground;

        SetDrawableTint(int i, boolean z, int i2, android.graphics.PorterDuff.Mode mode) {
            super();
            this.mViewId = i;
            this.mTargetBackground = z;
            this.mColorFilter = i2;
            this.mFilterMode = mode;
        }

        SetDrawableTint(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mTargetBackground = parcel.readInt() != 0;
            this.mColorFilter = parcel.readInt();
            this.mFilterMode = android.graphics.PorterDuff.intToMode(parcel.readInt());
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mTargetBackground ? 1 : 0);
            parcel.writeInt(this.mColorFilter);
            parcel.writeInt(android.graphics.PorterDuff.modeToInt(this.mFilterMode));
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.graphics.drawable.Drawable drawable;
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            if (this.mTargetBackground) {
                drawable = findViewById.getBackground();
            } else if (!(findViewById instanceof android.widget.ImageView)) {
                drawable = null;
            } else {
                drawable = ((android.widget.ImageView) findViewById).getDrawable();
            }
            if (drawable != null) {
                drawable.mutate().setColorFilter(this.mColorFilter, this.mFilterMode);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 3;
        }
    }

    private class SetRippleDrawableColor extends android.widget.RemoteViews.Action {
        android.content.res.ColorStateList mColorStateList;

        SetRippleDrawableColor(int i, android.content.res.ColorStateList colorStateList) {
            super();
            this.mViewId = i;
            this.mColorStateList = colorStateList;
        }

        SetRippleDrawableColor(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mColorStateList = (android.content.res.ColorStateList) parcel.readParcelable(null, android.content.res.ColorStateList.class);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeParcelable(this.mColorStateList, 0);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            android.graphics.drawable.Drawable background = findViewById.getBackground();
            if (background instanceof android.graphics.drawable.RippleDrawable) {
                ((android.graphics.drawable.RippleDrawable) background.mutate()).setColor(this.mColorStateList);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 21;
        }
    }

    @java.lang.Deprecated
    private final class ViewContentNavigation extends android.widget.RemoteViews.Action {
        final boolean mNext;

        ViewContentNavigation(int i, boolean z) {
            super();
            this.mViewId = i;
            this.mNext = z;
        }

        ViewContentNavigation(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mNext = parcel.readBoolean();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeBoolean(this.mNext);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            try {
                (void) android.widget.RemoteViews.getMethod(findViewById, this.mNext ? "showNext" : "showPrevious", null, false).invoke(findViewById);
            } catch (java.lang.Throwable th) {
                throw new android.widget.RemoteViews.ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 2;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 5;
        }
    }

    private static class BitmapCache {
        android.util.SparseIntArray mBitmapHashes;
        int mBitmapMemory;
        java.util.ArrayList<android.graphics.Bitmap> mBitmaps;

        public BitmapCache() {
            this.mBitmapMemory = -1;
            this.mBitmaps = new java.util.ArrayList<>();
            this.mBitmapHashes = new android.util.SparseIntArray();
        }

        public BitmapCache(android.os.Parcel parcel) {
            this.mBitmapMemory = -1;
            this.mBitmaps = parcel.createTypedArrayList(android.graphics.Bitmap.CREATOR);
            this.mBitmapHashes = new android.util.SparseIntArray();
            for (int i = 0; i < this.mBitmaps.size(); i++) {
                android.graphics.Bitmap bitmap = this.mBitmaps.get(i);
                if (bitmap != null) {
                    this.mBitmapHashes.put(bitmap.hashCode(), i);
                }
            }
        }

        public int getBitmapId(android.graphics.Bitmap bitmap) {
            if (bitmap == null) {
                return -1;
            }
            int hashCode = bitmap.hashCode();
            int i = this.mBitmapHashes.get(hashCode, -1);
            if (i != -1) {
                return i;
            }
            if (bitmap.isMutable()) {
                bitmap = bitmap.asShared();
            }
            this.mBitmaps.add(bitmap);
            this.mBitmapHashes.put(hashCode, this.mBitmaps.size() - 1);
            this.mBitmapMemory = -1;
            return this.mBitmaps.size() - 1;
        }

        public android.graphics.Bitmap getBitmapForId(int i) {
            if (i == -1 || i >= this.mBitmaps.size()) {
                return null;
            }
            return this.mBitmaps.get(i);
        }

        public void writeBitmapsToParcel(android.os.Parcel parcel, int i) {
            parcel.writeTypedList(this.mBitmaps, i);
        }

        public int getBitmapMemory() {
            if (this.mBitmapMemory < 0) {
                this.mBitmapMemory = 0;
                int size = this.mBitmaps.size();
                for (int i = 0; i < size; i++) {
                    this.mBitmapMemory += this.mBitmaps.get(i).getAllocationByteCount();
                }
            }
            return this.mBitmapMemory;
        }
    }

    private class BitmapReflectionAction extends android.widget.RemoteViews.Action {
        android.graphics.Bitmap mBitmap;
        int mBitmapId;
        java.lang.String mMethodName;

        BitmapReflectionAction(int i, java.lang.String str, android.graphics.Bitmap bitmap) {
            super();
            this.mBitmap = bitmap;
            this.mViewId = i;
            this.mMethodName = str;
            this.mBitmapId = android.widget.RemoteViews.this.mBitmapCache.getBitmapId(bitmap);
        }

        BitmapReflectionAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mMethodName = parcel.readString8();
            this.mBitmapId = parcel.readInt();
            this.mBitmap = android.widget.RemoteViews.this.mBitmapCache.getBitmapForId(this.mBitmapId);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeString8(this.mMethodName);
            parcel.writeInt(this.mBitmapId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) throws android.widget.RemoteViews.ActionException {
            new android.widget.RemoteViews.ReflectionAction(this.mViewId, this.mMethodName, 12, this.mBitmap).apply(view, viewGroup, actionApplyParams);
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(android.widget.RemoteViews.HierarchyRootData hierarchyRootData) {
            this.mBitmapId = hierarchyRootData.mBitmapCache.getBitmapId(this.mBitmap);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 12;
        }
    }

    private static abstract class BaseReflectionAction extends android.widget.RemoteViews.Action {
        static final int BITMAP = 12;
        static final int BLEND_MODE = 17;
        static final int BOOLEAN = 1;
        static final int BUNDLE = 13;
        static final int BYTE = 2;
        static final int CHAR = 8;
        static final int CHAR_SEQUENCE = 10;
        static final int COLOR_STATE_LIST = 15;
        static final int DOUBLE = 7;
        static final int FLOAT = 6;
        static final int ICON = 16;
        static final int INT = 4;
        static final int INTENT = 14;
        static final int LONG = 5;
        static final int SHORT = 3;
        static final int STRING = 9;
        static final int URI = 11;
        java.lang.String mMethodName;
        int mType;

        protected abstract java.lang.Object getParameterValue(android.view.View view) throws android.widget.RemoteViews.ActionException;

        BaseReflectionAction(int i, java.lang.String str, int i2) {
            super();
            this.mViewId = i;
            this.mMethodName = str;
            this.mType = i2;
        }

        BaseReflectionAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mMethodName = parcel.readString8();
            this.mType = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeString8(this.mMethodName);
            parcel.writeInt(this.mType);
        }

        @Override // android.widget.RemoteViews.Action
        public final void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            java.lang.Class parameterType = android.widget.RemoteViews.getParameterType(this.mType);
            if (parameterType == null) {
                throw new android.widget.RemoteViews.ActionException("bad type: " + this.mType);
            }
            try {
                (void) android.widget.RemoteViews.getMethod(findViewById, this.mMethodName, parameterType, false).invoke(findViewById, getParameterValue(findViewById));
            } catch (java.lang.Throwable th) {
                throw new android.widget.RemoteViews.ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public final android.widget.RemoteViews.Action initActionAsync(android.widget.RemoteViews.ViewTree viewTree, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.graphics.Bitmap bitmap;
            android.view.View findViewById = viewTree.findViewById(this.mViewId);
            if (findViewById == null) {
                return android.widget.RemoteViews.ACTION_NOOP;
            }
            java.lang.Class parameterType = android.widget.RemoteViews.getParameterType(this.mType);
            if (parameterType == null) {
                throw new android.widget.RemoteViews.ActionException("bad type: " + this.mType);
            }
            java.lang.Object parameterValue = getParameterValue(findViewById);
            try {
                java.lang.invoke.MethodHandle method = android.widget.RemoteViews.getMethod(findViewById, this.mMethodName, parameterType, true);
                if (parameterValue instanceof android.graphics.Bitmap) {
                    ((android.graphics.Bitmap) parameterValue).prepareToDraw();
                }
                if (parameterValue instanceof android.graphics.drawable.Icon) {
                    android.graphics.drawable.Icon icon = (android.graphics.drawable.Icon) parameterValue;
                    if ((icon.getType() == 1 || icon.getType() == 5) && (bitmap = icon.getBitmap()) != null) {
                        bitmap.prepareToDraw();
                    }
                }
                if (method != null) {
                    java.lang.Runnable invoke = (java.lang.Runnable) method.invoke(findViewById, parameterValue);
                    if (invoke == null) {
                        return android.widget.RemoteViews.ACTION_NOOP;
                    }
                    if (invoke instanceof android.view.ViewStub.ViewReplaceRunnable) {
                        viewTree.createTree();
                        viewTree.findViewTreeById(this.mViewId).replaceView(((android.view.ViewStub.ViewReplaceRunnable) invoke).view);
                    }
                    return new android.widget.RemoteViews.RunnableAction(invoke);
                }
                return this;
            } catch (java.lang.Throwable th) {
                throw new android.widget.RemoteViews.ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public final int mergeBehavior() {
            if (this.mMethodName.equals("smoothScrollBy")) {
                return 1;
            }
            return 0;
        }

        @Override // android.widget.RemoteViews.Action
        public final java.lang.String getUniqueKey() {
            return super.getUniqueKey() + this.mMethodName + this.mType;
        }

        @Override // android.widget.RemoteViews.Action
        public final boolean prefersAsyncApply() {
            return this.mType == 11 || this.mType == 16;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            switch (this.mType) {
                case 11:
                    android.net.Uri uri = (android.net.Uri) getParameterValue(null);
                    if (uri != null) {
                        consumer.accept(uri);
                        break;
                    }
                    break;
                case 14:
                    android.content.Intent intent = (android.content.Intent) getParameterValue(null);
                    if (intent != null) {
                        intent.visitUris(consumer);
                        break;
                    }
                    break;
                case 16:
                    android.graphics.drawable.Icon icon = (android.graphics.drawable.Icon) getParameterValue(null);
                    if (icon != null) {
                        android.widget.RemoteViews.visitIconUri(icon, consumer);
                        break;
                    }
                    break;
            }
        }
    }

    private static final class ReflectionAction extends android.widget.RemoteViews.BaseReflectionAction {
        java.lang.Object mValue;

        ReflectionAction(int i, java.lang.String str, int i2, java.lang.Object obj) {
            super(i, str, i2);
            this.mValue = obj;
        }

        ReflectionAction(android.os.Parcel parcel) {
            super(parcel);
            switch (this.mType) {
                case 1:
                    this.mValue = java.lang.Boolean.valueOf(parcel.readBoolean());
                    break;
                case 2:
                    this.mValue = java.lang.Byte.valueOf(parcel.readByte());
                    break;
                case 3:
                    this.mValue = java.lang.Short.valueOf((short) parcel.readInt());
                    break;
                case 4:
                    this.mValue = java.lang.Integer.valueOf(parcel.readInt());
                    break;
                case 5:
                    this.mValue = java.lang.Long.valueOf(parcel.readLong());
                    break;
                case 6:
                    this.mValue = java.lang.Float.valueOf(parcel.readFloat());
                    break;
                case 7:
                    this.mValue = java.lang.Double.valueOf(parcel.readDouble());
                    break;
                case 8:
                    this.mValue = java.lang.Character.valueOf((char) parcel.readInt());
                    break;
                case 9:
                    this.mValue = parcel.readString8();
                    break;
                case 10:
                    this.mValue = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
                    break;
                case 11:
                    this.mValue = parcel.readTypedObject(android.net.Uri.CREATOR);
                    break;
                case 12:
                    this.mValue = parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
                    break;
                case 13:
                    if (parcel.hasReadWriteHelper()) {
                        this.mValue = parcel.readBundle();
                        break;
                    } else {
                        parcel.setReadWriteHelper(android.widget.RemoteViews.ALTERNATIVE_DEFAULT);
                        this.mValue = parcel.readBundle();
                        parcel.setReadWriteHelper(null);
                        break;
                    }
                case 14:
                    this.mValue = parcel.readTypedObject(android.content.Intent.CREATOR);
                    break;
                case 15:
                    this.mValue = parcel.readTypedObject(android.content.res.ColorStateList.CREATOR);
                    break;
                case 16:
                    this.mValue = parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                    break;
                case 17:
                    this.mValue = android.graphics.BlendMode.fromValue(parcel.readInt());
                    break;
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            switch (this.mType) {
                case 1:
                    parcel.writeBoolean(((java.lang.Boolean) this.mValue).booleanValue());
                    break;
                case 2:
                    parcel.writeByte(((java.lang.Byte) this.mValue).byteValue());
                    break;
                case 3:
                    parcel.writeInt(((java.lang.Short) this.mValue).shortValue());
                    break;
                case 4:
                    parcel.writeInt(((java.lang.Integer) this.mValue).intValue());
                    break;
                case 5:
                    parcel.writeLong(((java.lang.Long) this.mValue).longValue());
                    break;
                case 6:
                    parcel.writeFloat(((java.lang.Float) this.mValue).floatValue());
                    break;
                case 7:
                    parcel.writeDouble(((java.lang.Double) this.mValue).doubleValue());
                    break;
                case 8:
                    parcel.writeInt(((java.lang.Character) this.mValue).charValue());
                    break;
                case 9:
                    parcel.writeString8((java.lang.String) this.mValue);
                    break;
                case 10:
                    android.text.TextUtils.writeToParcel((java.lang.CharSequence) this.mValue, parcel, i);
                    break;
                case 11:
                case 12:
                case 14:
                case 15:
                case 16:
                    parcel.writeTypedObject((android.os.Parcelable) this.mValue, i);
                    break;
                case 13:
                    parcel.writeBundle((android.os.Bundle) this.mValue);
                    break;
                case 17:
                    parcel.writeInt(android.graphics.BlendMode.toValue((android.graphics.BlendMode) this.mValue));
                    break;
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected java.lang.Object getParameterValue(android.view.View view) throws android.widget.RemoteViews.ActionException {
            return this.mValue;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 2;
        }
    }

    private static final class ResourceReflectionAction extends android.widget.RemoteViews.BaseReflectionAction {
        static final int COLOR_RESOURCE = 2;
        static final int DIMEN_RESOURCE = 1;
        static final int STRING_RESOURCE = 3;
        private final int mResId;
        private final int mResourceType;

        ResourceReflectionAction(int i, java.lang.String str, int i2, int i3, int i4) {
            super(i, str, i2);
            this.mResourceType = i3;
            this.mResId = i4;
        }

        ResourceReflectionAction(android.os.Parcel parcel) {
            super(parcel);
            this.mResourceType = parcel.readInt();
            this.mResId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mResourceType);
            parcel.writeInt(this.mResId);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected java.lang.Object getParameterValue(android.view.View view) throws android.widget.RemoteViews.ActionException {
            if (view == null) {
                return null;
            }
            android.content.res.Resources resources = view.getContext().getResources();
            try {
                switch (this.mResourceType) {
                    case 1:
                        switch (this.mType) {
                            case 4:
                                return java.lang.Integer.valueOf(this.mResId != 0 ? resources.getDimensionPixelSize(this.mResId) : 0);
                            case 5:
                            default:
                                throw new android.widget.RemoteViews.ActionException("dimen resources must be used as INT or FLOAT, not " + this.mType);
                            case 6:
                                return java.lang.Float.valueOf(this.mResId == 0 ? 0.0f : resources.getDimension(this.mResId));
                        }
                    case 2:
                        switch (this.mType) {
                            case 4:
                                return java.lang.Integer.valueOf(this.mResId != 0 ? view.getContext().getColor(this.mResId) : 0);
                            case 15:
                                return this.mResId != 0 ? view.getContext().getColorStateList(this.mResId) : null;
                            default:
                                throw new android.widget.RemoteViews.ActionException("color resources must be used as INT or COLOR_STATE_LIST, not " + this.mType);
                        }
                    case 3:
                        switch (this.mType) {
                            case 9:
                                return this.mResId != 0 ? resources.getString(this.mResId) : null;
                            case 10:
                                return this.mResId != 0 ? resources.getText(this.mResId) : null;
                            default:
                                throw new android.widget.RemoteViews.ActionException("string resources must be used as STRING or CHAR_SEQUENCE, not " + this.mType);
                        }
                    default:
                        throw new android.widget.RemoteViews.ActionException("unknown resource type: " + this.mResourceType);
                }
            } catch (android.widget.RemoteViews.ActionException e) {
                throw e;
            } catch (java.lang.Throwable th) {
                throw new android.widget.RemoteViews.ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 24;
        }
    }

    private static final class AttributeReflectionAction extends android.widget.RemoteViews.BaseReflectionAction {
        static final int COLOR_RESOURCE = 2;
        static final int DIMEN_RESOURCE = 1;
        static final int STRING_RESOURCE = 3;
        private final int mAttrId;
        private final int mResourceType;

        AttributeReflectionAction(int i, java.lang.String str, int i2, int i3, int i4) {
            super(i, str, i2);
            this.mResourceType = i3;
            this.mAttrId = i4;
        }

        AttributeReflectionAction(android.os.Parcel parcel) {
            super(parcel);
            this.mResourceType = parcel.readInt();
            this.mAttrId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mResourceType);
            parcel.writeInt(this.mAttrId);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected java.lang.Object getParameterValue(android.view.View view) throws android.widget.RemoteViews.ActionException {
            android.content.res.TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(new int[]{this.mAttrId});
            try {
                try {
                    if (this.mAttrId != 0 && obtainStyledAttributes.getType(0) == 0) {
                        throw new android.widget.RemoteViews.ActionException("Attribute 0x" + java.lang.Integer.toHexString(this.mAttrId) + " is not defined");
                    }
                    switch (this.mResourceType) {
                        case 1:
                            switch (this.mType) {
                                case 4:
                                    return java.lang.Integer.valueOf(obtainStyledAttributes.getDimensionPixelSize(0, 0));
                                case 5:
                                default:
                                    throw new android.widget.RemoteViews.ActionException("dimen attribute 0x" + java.lang.Integer.toHexString(this.mAttrId) + " must be used as INT or FLOAT, not " + this.mType);
                                case 6:
                                    return java.lang.Float.valueOf(obtainStyledAttributes.getDimension(0, 0.0f));
                            }
                        case 2:
                            switch (this.mType) {
                                case 4:
                                    return java.lang.Integer.valueOf(obtainStyledAttributes.getColor(0, 0));
                                case 15:
                                    return obtainStyledAttributes.getColorStateList(0);
                                default:
                                    throw new android.widget.RemoteViews.ActionException("color attribute 0x" + java.lang.Integer.toHexString(this.mAttrId) + " must be used as INT or COLOR_STATE_LIST, not " + this.mType);
                            }
                        case 3:
                            switch (this.mType) {
                                case 9:
                                    return obtainStyledAttributes.getString(0);
                                case 10:
                                    return obtainStyledAttributes.getText(0);
                                default:
                                    throw new android.widget.RemoteViews.ActionException("string attribute 0x" + java.lang.Integer.toHexString(this.mAttrId) + " must be used as STRING or CHAR_SEQUENCE, not " + this.mType);
                            }
                        default:
                            throw new android.widget.RemoteViews.ActionException("Unknown resource type: " + this.mResourceType);
                    }
                } catch (android.widget.RemoteViews.ActionException e) {
                    throw e;
                } catch (java.lang.Throwable th) {
                    throw new android.widget.RemoteViews.ActionException(th);
                }
            } finally {
                obtainStyledAttributes.recycle();
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 32;
        }
    }

    private static final class ComplexUnitDimensionReflectionAction extends android.widget.RemoteViews.BaseReflectionAction {
        private final int mUnit;
        private final float mValue;

        ComplexUnitDimensionReflectionAction(int i, java.lang.String str, int i2, float f, int i3) {
            super(i, str, i2);
            this.mValue = f;
            this.mUnit = i3;
        }

        ComplexUnitDimensionReflectionAction(android.os.Parcel parcel) {
            super(parcel);
            this.mValue = parcel.readFloat();
            this.mUnit = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.mValue);
            parcel.writeInt(this.mUnit);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected java.lang.Object getParameterValue(android.view.View view) throws android.widget.RemoteViews.ActionException {
            if (view == null) {
                return null;
            }
            android.util.DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
            try {
                int createComplexDimension = android.util.TypedValue.createComplexDimension(this.mValue, this.mUnit);
                switch (this.mType) {
                    case 4:
                        return java.lang.Integer.valueOf(android.util.TypedValue.complexToDimensionPixelSize(createComplexDimension, displayMetrics));
                    case 5:
                    default:
                        throw new android.widget.RemoteViews.ActionException("parameter type must be INT or FLOAT, not " + this.mType);
                    case 6:
                        return java.lang.Float.valueOf(android.util.TypedValue.complexToDimension(createComplexDimension, displayMetrics));
                }
            } catch (android.widget.RemoteViews.ActionException e) {
                throw e;
            } catch (java.lang.Throwable th) {
                throw new android.widget.RemoteViews.ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 25;
        }
    }

    private static final class NightModeReflectionAction extends android.widget.RemoteViews.BaseReflectionAction {
        private final java.lang.Object mDarkValue;
        private final java.lang.Object mLightValue;

        NightModeReflectionAction(int i, java.lang.String str, int i2, java.lang.Object obj, java.lang.Object obj2) {
            super(i, str, i2);
            this.mLightValue = obj;
            this.mDarkValue = obj2;
        }

        NightModeReflectionAction(android.os.Parcel parcel) {
            super(parcel);
            switch (this.mType) {
                case 4:
                    this.mLightValue = java.lang.Integer.valueOf(parcel.readInt());
                    this.mDarkValue = java.lang.Integer.valueOf(parcel.readInt());
                    return;
                case 15:
                    this.mLightValue = parcel.readTypedObject(android.content.res.ColorStateList.CREATOR);
                    this.mDarkValue = parcel.readTypedObject(android.content.res.ColorStateList.CREATOR);
                    return;
                case 16:
                    this.mLightValue = parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                    this.mDarkValue = parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                    return;
                default:
                    throw new android.widget.RemoteViews.ActionException("Unexpected night mode action type: " + this.mType);
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            switch (this.mType) {
                case 4:
                    parcel.writeInt(((java.lang.Integer) this.mLightValue).intValue());
                    parcel.writeInt(((java.lang.Integer) this.mDarkValue).intValue());
                    break;
                case 15:
                case 16:
                    parcel.writeTypedObject((android.os.Parcelable) this.mLightValue, i);
                    parcel.writeTypedObject((android.os.Parcelable) this.mDarkValue, i);
                    break;
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected java.lang.Object getParameterValue(android.view.View view) throws android.widget.RemoteViews.ActionException {
            if (view == null) {
                return null;
            }
            return view.getResources().getConfiguration().isNightModeActive() ? this.mDarkValue : this.mLightValue;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 30;
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            if (this.mType == 16) {
                android.widget.RemoteViews.visitIconUri((android.graphics.drawable.Icon) this.mDarkValue, consumer);
                android.widget.RemoteViews.visitIconUri((android.graphics.drawable.Icon) this.mLightValue, consumer);
            }
        }
    }

    private static final class RunnableAction extends android.widget.RemoteViews.RuntimeAction {
        private final java.lang.Runnable mRunnable;

        RunnableAction(java.lang.Runnable runnable) {
            super();
            this.mRunnable = runnable;
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            this.mRunnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasStableId(android.view.View view) {
        return view.getTag(com.android.internal.R.id.remote_views_stable_id) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getStableId(android.view.View view) {
        java.lang.Integer num = (java.lang.Integer) view.getTag(com.android.internal.R.id.remote_views_stable_id);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setStableId(android.view.View view, int i) {
        view.setTagInternal(com.android.internal.R.id.remote_views_stable_id, java.lang.Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getNextRecyclableChild(android.view.ViewGroup viewGroup) {
        java.lang.Integer num = (java.lang.Integer) viewGroup.getTag(com.android.internal.R.id.remote_views_next_child);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    private static int getViewLayoutId(android.view.View view) {
        return ((java.lang.Integer) view.getTag(16908312)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setNextRecyclableChild(android.view.ViewGroup viewGroup, int i, int i2) {
        if (i < 0 || i >= i2) {
            viewGroup.setTagInternal(com.android.internal.R.id.remote_views_next_child, -1);
        } else {
            viewGroup.setTagInternal(com.android.internal.R.id.remote_views_next_child, java.lang.Integer.valueOf(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finalizeViewRecycling(android.view.ViewGroup viewGroup) {
        int nextRecyclableChild = getNextRecyclableChild(viewGroup);
        if (nextRecyclableChild >= 0 && nextRecyclableChild < viewGroup.getChildCount()) {
            viewGroup.removeViews(nextRecyclableChild, viewGroup.getChildCount() - nextRecyclableChild);
        }
        setNextRecyclableChild(viewGroup, -1, 0);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            android.view.View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof android.view.ViewGroup) && !childAt.isRootNamespace()) {
                finalizeViewRecycling((android.view.ViewGroup) childAt);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ViewGroupActionAdd extends android.widget.RemoteViews.Action {
        static final int NO_ID = -1;
        private int mIndex;
        private android.widget.RemoteViews mNestedViews;
        private int mStableId;

        ViewGroupActionAdd(android.widget.RemoteViews remoteViews, int i, android.widget.RemoteViews remoteViews2) {
            this(i, remoteViews2, -1, -1);
        }

        ViewGroupActionAdd(android.widget.RemoteViews remoteViews, int i, android.widget.RemoteViews remoteViews2, int i2) {
            this(i, remoteViews2, i2, -1);
        }

        ViewGroupActionAdd(int i, android.widget.RemoteViews remoteViews, int i2, int i3) {
            super();
            this.mViewId = i;
            this.mNestedViews = remoteViews;
            this.mIndex = i2;
            this.mStableId = i3;
            remoteViews.configureAsChild(android.widget.RemoteViews.this.getHierarchyRootData());
        }

        ViewGroupActionAdd(android.os.Parcel parcel, android.content.pm.ApplicationInfo applicationInfo, int i) {
            super();
            this.mViewId = parcel.readInt();
            this.mIndex = parcel.readInt();
            this.mStableId = parcel.readInt();
            this.mNestedViews = new android.widget.RemoteViews(parcel, android.widget.RemoteViews.this.getHierarchyRootData(), applicationInfo, i);
            this.mNestedViews.addFlags(android.widget.RemoteViews.this.mApplyFlags);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mIndex);
            parcel.writeInt(this.mStableId);
            this.mNestedViews.writeToParcel(parcel, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(android.widget.RemoteViews.HierarchyRootData hierarchyRootData) {
            this.mNestedViews.configureAsChild(hierarchyRootData);
        }

        private int findViewIndexToRecycle(android.view.ViewGroup viewGroup, android.widget.RemoteViews remoteViews) {
            for (int nextRecyclableChild = android.widget.RemoteViews.getNextRecyclableChild(viewGroup); nextRecyclableChild < viewGroup.getChildCount(); nextRecyclableChild++) {
                if (android.widget.RemoteViews.getStableId(viewGroup.getChildAt(nextRecyclableChild)) == this.mStableId) {
                    return nextRecyclableChild;
                }
            }
            return -1;
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            int findViewIndexToRecycle;
            android.content.Context context = view.getContext();
            android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) view.findViewById(this.mViewId);
            if (viewGroup2 == null) {
                return;
            }
            int nextRecyclableChild = android.widget.RemoteViews.getNextRecyclableChild(viewGroup2);
            android.widget.RemoteViews remoteViewsToApply = this.mNestedViews.getRemoteViewsToApply(context);
            int i = android.widget.RemoteViews.this.mApplyFlags & 6;
            if (i != 0) {
                remoteViewsToApply.addFlags(i);
            }
            if (nextRecyclableChild >= 0 && this.mStableId != -1 && (findViewIndexToRecycle = findViewIndexToRecycle(viewGroup2, remoteViewsToApply)) >= 0) {
                android.view.View childAt = viewGroup2.getChildAt(findViewIndexToRecycle);
                if (remoteViewsToApply.canRecycleView(childAt)) {
                    if (nextRecyclableChild < findViewIndexToRecycle) {
                        viewGroup2.removeViews(nextRecyclableChild, findViewIndexToRecycle - nextRecyclableChild);
                    }
                    android.widget.RemoteViews.setNextRecyclableChild(viewGroup2, nextRecyclableChild + 1, viewGroup2.getChildCount());
                    remoteViewsToApply.reapplyNestedViews(context, childAt, viewGroup, actionApplyParams);
                    return;
                }
                viewGroup2.removeViews(nextRecyclableChild, (findViewIndexToRecycle - nextRecyclableChild) + 1);
            }
            android.view.View apply = remoteViewsToApply.apply(context, viewGroup2, viewGroup, (android.util.SizeF) null, actionApplyParams);
            if (this.mStableId != -1) {
                android.widget.RemoteViews.setStableId(apply, this.mStableId);
            }
            viewGroup2.addView(apply, this.mIndex >= 0 ? this.mIndex : nextRecyclableChild);
            if (nextRecyclableChild >= 0) {
                android.widget.RemoteViews.setNextRecyclableChild(viewGroup2, nextRecyclableChild + 1, viewGroup2.getChildCount());
            }
        }

        @Override // android.widget.RemoteViews.Action
        public android.widget.RemoteViews.Action initActionAsync(android.widget.RemoteViews.ViewTree viewTree, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            viewTree.createTree();
            android.widget.RemoteViews.ViewTree findViewTreeById = viewTree.findViewTreeById(this.mViewId);
            if (findViewTreeById == null || !(findViewTreeById.mRoot instanceof android.view.ViewGroup)) {
                return android.widget.RemoteViews.ACTION_NOOP;
            }
            final android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) findViewTreeById.mRoot;
            android.content.Context context = viewTree.mRoot.getContext();
            final int nextRecyclableChild = android.widget.RemoteViews.getNextRecyclableChild(viewGroup2);
            if (nextRecyclableChild >= 0 && this.mStableId != -1) {
                android.widget.RemoteViews remoteViewsToApply = this.mNestedViews.getRemoteViewsToApply(context);
                final int findChildIndex = findViewTreeById.findChildIndex(nextRecyclableChild, new java.util.function.Predicate() { // from class: android.widget.RemoteViews$ViewGroupActionAdd$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$initActionAsync$0;
                        lambda$initActionAsync$0 = android.widget.RemoteViews.ViewGroupActionAdd.this.lambda$initActionAsync$0((android.view.View) obj);
                        return lambda$initActionAsync$0;
                    }
                });
                if (findChildIndex >= 0) {
                    android.widget.RemoteViews.ViewTree viewTree2 = (android.widget.RemoteViews.ViewTree) findViewTreeById.mChildren.get(findChildIndex);
                    if (remoteViewsToApply.canRecycleView(viewTree2.mRoot)) {
                        if (findChildIndex > nextRecyclableChild) {
                            findViewTreeById.removeChildren(nextRecyclableChild, findChildIndex - nextRecyclableChild);
                        }
                        android.widget.RemoteViews.setNextRecyclableChild(viewGroup2, nextRecyclableChild + 1, findViewTreeById.mChildren.size());
                        final android.widget.RemoteViews.AsyncApplyTask internalAsyncApplyTask = remoteViewsToApply.getInternalAsyncApplyTask(context, viewGroup2, null, actionApplyParams, null, viewTree2.mRoot);
                        final android.widget.RemoteViews.ViewTree doInBackground = internalAsyncApplyTask.doInBackground(new java.lang.Void[0]);
                        if (doInBackground == null) {
                            throw new android.widget.RemoteViews.ActionException(internalAsyncApplyTask.mError);
                        }
                        return new android.widget.RemoteViews.RuntimeAction() { // from class: android.widget.RemoteViews.ViewGroupActionAdd.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super();
                            }

                            @Override // android.widget.RemoteViews.Action
                            public void apply(android.view.View view, android.view.ViewGroup viewGroup3, android.widget.RemoteViews.ActionApplyParams actionApplyParams2) throws android.widget.RemoteViews.ActionException {
                                internalAsyncApplyTask.onPostExecute(doInBackground);
                                if (findChildIndex > nextRecyclableChild) {
                                    viewGroup2.removeViews(nextRecyclableChild, findChildIndex - nextRecyclableChild);
                                }
                            }
                        };
                    }
                    findViewTreeById.removeChildren(nextRecyclableChild, (findChildIndex - nextRecyclableChild) + 1);
                    return insertNewView(context, findViewTreeById, actionApplyParams, new java.lang.Runnable() { // from class: android.widget.RemoteViews$ViewGroupActionAdd$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.view.ViewGroup.this.removeViews(r1, (findChildIndex - nextRecyclableChild) + 1);
                        }
                    });
                }
            }
            return insertNewView(context, findViewTreeById, actionApplyParams, new java.lang.Runnable() { // from class: android.widget.RemoteViews$ViewGroupActionAdd$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.RemoteViews.ViewGroupActionAdd.lambda$initActionAsync$2();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$initActionAsync$0(android.view.View view) {
            return android.widget.RemoteViews.getStableId(view) == this.mStableId;
        }

        static /* synthetic */ void lambda$initActionAsync$2() {
        }

        private android.widget.RemoteViews.Action insertNewView(android.content.Context context, android.widget.RemoteViews.ViewTree viewTree, android.widget.RemoteViews.ActionApplyParams actionApplyParams, final java.lang.Runnable runnable) {
            final android.view.ViewGroup viewGroup = (android.view.ViewGroup) viewTree.mRoot;
            int nextRecyclableChild = android.widget.RemoteViews.getNextRecyclableChild(viewGroup);
            final android.widget.RemoteViews.AsyncApplyTask internalAsyncApplyTask = this.mNestedViews.getInternalAsyncApplyTask(context, viewGroup, null, actionApplyParams, null, null);
            final android.widget.RemoteViews.ViewTree doInBackground = internalAsyncApplyTask.doInBackground(new java.lang.Void[0]);
            if (doInBackground == null) {
                throw new android.widget.RemoteViews.ActionException(internalAsyncApplyTask.mError);
            }
            if (this.mStableId != -1) {
                android.widget.RemoteViews.setStableId(internalAsyncApplyTask.mResult, this.mStableId);
            }
            final int i = this.mIndex >= 0 ? this.mIndex : nextRecyclableChild;
            viewTree.addChild(doInBackground, i);
            if (nextRecyclableChild >= 0) {
                android.widget.RemoteViews.setNextRecyclableChild(viewGroup, nextRecyclableChild + 1, viewTree.mChildren.size());
            }
            return new android.widget.RemoteViews.RuntimeAction() { // from class: android.widget.RemoteViews.ViewGroupActionAdd.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // android.widget.RemoteViews.Action
                public void apply(android.view.View view, android.view.ViewGroup viewGroup2, android.widget.RemoteViews.ActionApplyParams actionApplyParams2) {
                    internalAsyncApplyTask.onPostExecute(doInBackground);
                    runnable.run();
                    viewGroup.addView(internalAsyncApplyTask.mResult, i);
                }
            };
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 1;
        }

        @Override // android.widget.RemoteViews.Action
        public boolean prefersAsyncApply() {
            return this.mNestedViews.prefersAsyncApply();
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 4;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            this.mNestedViews.visitUris(consumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ViewGroupActionRemove extends android.widget.RemoteViews.Action {
        private static final int REMOVE_ALL_VIEWS_ID = -2;
        private int mViewIdToKeep;

        ViewGroupActionRemove(int i) {
            this(i, -2);
        }

        ViewGroupActionRemove(int i, int i2) {
            super();
            this.mViewId = i;
            this.mViewIdToKeep = i2;
        }

        ViewGroupActionRemove(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mViewIdToKeep = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mViewIdToKeep);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) view.findViewById(this.mViewId);
            if (viewGroup2 == null) {
                return;
            }
            if (this.mViewIdToKeep == -2) {
                for (int childCount = viewGroup2.getChildCount() - 1; childCount >= 0; childCount--) {
                    if (!android.widget.RemoteViews.hasStableId(viewGroup2.getChildAt(childCount))) {
                        viewGroup2.removeViewAt(childCount);
                    }
                }
                android.widget.RemoteViews.setNextRecyclableChild(viewGroup2, 0, viewGroup2.getChildCount());
                return;
            }
            removeAllViewsExceptIdToKeep(viewGroup2);
        }

        @Override // android.widget.RemoteViews.Action
        public android.widget.RemoteViews.Action initActionAsync(android.widget.RemoteViews.ViewTree viewTree, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            viewTree.createTree();
            android.widget.RemoteViews.ViewTree findViewTreeById = viewTree.findViewTreeById(this.mViewId);
            if (findViewTreeById == null || !(findViewTreeById.mRoot instanceof android.view.ViewGroup)) {
                return android.widget.RemoteViews.ACTION_NOOP;
            }
            final android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) findViewTreeById.mRoot;
            if (this.mViewIdToKeep == -2) {
                findViewTreeById.mChildren.removeIf(new java.util.function.Predicate() { // from class: android.widget.RemoteViews$ViewGroupActionRemove$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        return android.widget.RemoteViews.ViewGroupActionRemove.lambda$initActionAsync$0((android.widget.RemoteViews.ViewTree) obj);
                    }
                });
                android.widget.RemoteViews.setNextRecyclableChild(viewGroup2, 0, findViewTreeById.mChildren.size());
            } else {
                findViewTreeById.mChildren.removeIf(new java.util.function.Predicate() { // from class: android.widget.RemoteViews$ViewGroupActionRemove$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$initActionAsync$1;
                        lambda$initActionAsync$1 = android.widget.RemoteViews.ViewGroupActionRemove.this.lambda$initActionAsync$1((android.widget.RemoteViews.ViewTree) obj);
                        return lambda$initActionAsync$1;
                    }
                });
                if (findViewTreeById.mChildren.isEmpty()) {
                    findViewTreeById.mChildren = null;
                }
            }
            return new android.widget.RemoteViews.RuntimeAction() { // from class: android.widget.RemoteViews.ViewGroupActionRemove.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // android.widget.RemoteViews.Action
                public void apply(android.view.View view, android.view.ViewGroup viewGroup3, android.widget.RemoteViews.ActionApplyParams actionApplyParams2) {
                    if (android.widget.RemoteViews.ViewGroupActionRemove.this.mViewIdToKeep == -2) {
                        for (int childCount = viewGroup2.getChildCount() - 1; childCount >= 0; childCount--) {
                            if (!android.widget.RemoteViews.hasStableId(viewGroup2.getChildAt(childCount))) {
                                viewGroup2.removeViewAt(childCount);
                            }
                        }
                        return;
                    }
                    android.widget.RemoteViews.ViewGroupActionRemove.this.removeAllViewsExceptIdToKeep(viewGroup2);
                }
            };
        }

        static /* synthetic */ boolean lambda$initActionAsync$0(android.widget.RemoteViews.ViewTree viewTree) {
            return !android.widget.RemoteViews.hasStableId(viewTree.mRoot);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$initActionAsync$1(android.widget.RemoteViews.ViewTree viewTree) {
            return viewTree.mRoot.getId() != this.mViewIdToKeep;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeAllViewsExceptIdToKeep(android.view.ViewGroup viewGroup) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                if (viewGroup.getChildAt(childCount).getId() != this.mViewIdToKeep) {
                    viewGroup.removeViewAt(childCount);
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 7;
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 1;
        }
    }

    private static class RemoveFromParentAction extends android.widget.RemoteViews.Action {
        RemoveFromParentAction(int i) {
            super();
            this.mViewId = i;
        }

        RemoveFromParentAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null || findViewById == view) {
                return;
            }
            android.view.ViewParent parent = findViewById.getParent();
            if (parent instanceof android.view.ViewManager) {
                ((android.view.ViewManager) parent).removeView(findViewById);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public android.widget.RemoteViews.Action initActionAsync(android.widget.RemoteViews.ViewTree viewTree, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            viewTree.createTree();
            final android.widget.RemoteViews.ViewTree findViewTreeById = viewTree.findViewTreeById(this.mViewId);
            if (findViewTreeById == null || findViewTreeById == viewTree) {
                return android.widget.RemoteViews.ACTION_NOOP;
            }
            android.widget.RemoteViews.ViewTree findViewTreeParentOf = viewTree.findViewTreeParentOf(findViewTreeById);
            if (findViewTreeParentOf == null || !(findViewTreeParentOf.mRoot instanceof android.view.ViewManager)) {
                return android.widget.RemoteViews.ACTION_NOOP;
            }
            final android.view.ViewManager viewManager = (android.view.ViewManager) findViewTreeParentOf.mRoot;
            findViewTreeParentOf.mChildren.remove(findViewTreeById);
            return new android.widget.RemoteViews.RuntimeAction() { // from class: android.widget.RemoteViews.RemoveFromParentAction.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // android.widget.RemoteViews.Action
                public void apply(android.view.View view, android.view.ViewGroup viewGroup2, android.widget.RemoteViews.ActionApplyParams actionApplyParams2) {
                    viewManager.removeView(findViewTreeById.mRoot);
                }
            };
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 23;
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 1;
        }
    }

    private static class TextViewDrawableAction extends android.widget.RemoteViews.Action {
        int mD1;
        int mD2;
        int mD3;
        int mD4;
        boolean mDrawablesLoaded;
        android.graphics.drawable.Icon mI1;
        android.graphics.drawable.Icon mI2;
        android.graphics.drawable.Icon mI3;
        android.graphics.drawable.Icon mI4;
        android.graphics.drawable.Drawable mId1;
        android.graphics.drawable.Drawable mId2;
        android.graphics.drawable.Drawable mId3;
        android.graphics.drawable.Drawable mId4;
        boolean mIsRelative;
        boolean mUseIcons;

        public TextViewDrawableAction(int i, boolean z, int i2, int i3, int i4, int i5) {
            super();
            this.mIsRelative = false;
            this.mUseIcons = false;
            this.mDrawablesLoaded = false;
            this.mViewId = i;
            this.mIsRelative = z;
            this.mUseIcons = false;
            this.mD1 = i2;
            this.mD2 = i3;
            this.mD3 = i4;
            this.mD4 = i5;
        }

        public TextViewDrawableAction(int i, boolean z, android.graphics.drawable.Icon icon, android.graphics.drawable.Icon icon2, android.graphics.drawable.Icon icon3, android.graphics.drawable.Icon icon4) {
            super();
            this.mIsRelative = false;
            this.mUseIcons = false;
            this.mDrawablesLoaded = false;
            this.mViewId = i;
            this.mIsRelative = z;
            this.mUseIcons = true;
            this.mI1 = icon;
            this.mI2 = icon2;
            this.mI3 = icon3;
            this.mI4 = icon4;
        }

        public TextViewDrawableAction(android.os.Parcel parcel) {
            super();
            this.mIsRelative = false;
            this.mUseIcons = false;
            this.mDrawablesLoaded = false;
            this.mViewId = parcel.readInt();
            this.mIsRelative = parcel.readInt() != 0;
            this.mUseIcons = parcel.readInt() != 0;
            if (this.mUseIcons) {
                this.mI1 = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                this.mI2 = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                this.mI3 = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                this.mI4 = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                return;
            }
            this.mD1 = parcel.readInt();
            this.mD2 = parcel.readInt();
            this.mD3 = parcel.readInt();
            this.mD4 = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mIsRelative ? 1 : 0);
            parcel.writeInt(this.mUseIcons ? 1 : 0);
            if (this.mUseIcons) {
                parcel.writeTypedObject(this.mI1, 0);
                parcel.writeTypedObject(this.mI2, 0);
                parcel.writeTypedObject(this.mI3, 0);
                parcel.writeTypedObject(this.mI4, 0);
                return;
            }
            parcel.writeInt(this.mD1);
            parcel.writeInt(this.mD2);
            parcel.writeInt(this.mD3);
            parcel.writeInt(this.mD4);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.widget.TextView textView = (android.widget.TextView) view.findViewById(this.mViewId);
            if (textView == null) {
                return;
            }
            if (this.mDrawablesLoaded) {
                if (this.mIsRelative) {
                    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(this.mId1, this.mId2, this.mId3, this.mId4);
                    return;
                } else {
                    textView.setCompoundDrawablesWithIntrinsicBounds(this.mId1, this.mId2, this.mId3, this.mId4);
                    return;
                }
            }
            if (this.mUseIcons) {
                android.content.Context context = textView.getContext();
                android.graphics.drawable.Drawable loadDrawable = this.mI1 == null ? null : this.mI1.loadDrawable(context);
                android.graphics.drawable.Drawable loadDrawable2 = this.mI2 == null ? null : this.mI2.loadDrawable(context);
                android.graphics.drawable.Drawable loadDrawable3 = this.mI3 == null ? null : this.mI3.loadDrawable(context);
                android.graphics.drawable.Drawable loadDrawable4 = this.mI4 != null ? this.mI4.loadDrawable(context) : null;
                if (this.mIsRelative) {
                    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(loadDrawable, loadDrawable2, loadDrawable3, loadDrawable4);
                    return;
                } else {
                    textView.setCompoundDrawablesWithIntrinsicBounds(loadDrawable, loadDrawable2, loadDrawable3, loadDrawable4);
                    return;
                }
            }
            if (this.mIsRelative) {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(this.mD1, this.mD2, this.mD3, this.mD4);
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(this.mD1, this.mD2, this.mD3, this.mD4);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public android.widget.RemoteViews.Action initActionAsync(android.widget.RemoteViews.ViewTree viewTree, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.widget.RemoteViews.TextViewDrawableAction textViewDrawableAction;
            android.widget.TextView textView = (android.widget.TextView) viewTree.findViewById(this.mViewId);
            if (textView == null) {
                return android.widget.RemoteViews.ACTION_NOOP;
            }
            if (this.mUseIcons) {
                textViewDrawableAction = new android.widget.RemoteViews.TextViewDrawableAction(this.mViewId, this.mIsRelative, this.mI1, this.mI2, this.mI3, this.mI4);
            } else {
                textViewDrawableAction = new android.widget.RemoteViews.TextViewDrawableAction(this.mViewId, this.mIsRelative, this.mD1, this.mD2, this.mD3, this.mD4);
            }
            textViewDrawableAction.mDrawablesLoaded = true;
            android.content.Context context = textView.getContext();
            if (this.mUseIcons) {
                textViewDrawableAction.mId1 = this.mI1 == null ? null : this.mI1.loadDrawable(context);
                textViewDrawableAction.mId2 = this.mI2 == null ? null : this.mI2.loadDrawable(context);
                textViewDrawableAction.mId3 = this.mI3 == null ? null : this.mI3.loadDrawable(context);
                textViewDrawableAction.mId4 = this.mI4 != null ? this.mI4.loadDrawable(context) : null;
            } else {
                textViewDrawableAction.mId1 = this.mD1 == 0 ? null : context.getDrawable(this.mD1);
                textViewDrawableAction.mId2 = this.mD2 == 0 ? null : context.getDrawable(this.mD2);
                textViewDrawableAction.mId3 = this.mD3 == 0 ? null : context.getDrawable(this.mD3);
                textViewDrawableAction.mId4 = this.mD4 != 0 ? context.getDrawable(this.mD4) : null;
            }
            return textViewDrawableAction;
        }

        @Override // android.widget.RemoteViews.Action
        public boolean prefersAsyncApply() {
            return this.mUseIcons;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 11;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            if (this.mUseIcons) {
                android.widget.RemoteViews.visitIconUri(this.mI1, consumer);
                android.widget.RemoteViews.visitIconUri(this.mI2, consumer);
                android.widget.RemoteViews.visitIconUri(this.mI3, consumer);
                android.widget.RemoteViews.visitIconUri(this.mI4, consumer);
            }
        }
    }

    private static class TextViewSizeAction extends android.widget.RemoteViews.Action {
        float mSize;
        int mUnits;

        TextViewSizeAction(int i, int i2, float f) {
            super();
            this.mViewId = i;
            this.mUnits = i2;
            this.mSize = f;
        }

        TextViewSizeAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mUnits = parcel.readInt();
            this.mSize = parcel.readFloat();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mUnits);
            parcel.writeFloat(this.mSize);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.widget.TextView textView = (android.widget.TextView) view.findViewById(this.mViewId);
            if (textView == null) {
                return;
            }
            textView.setTextSize(this.mUnits, this.mSize);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 13;
        }
    }

    private static class ViewPaddingAction extends android.widget.RemoteViews.Action {
        int mBottom;
        int mLeft;
        int mRight;
        int mTop;

        public ViewPaddingAction(int i, int i2, int i3, int i4, int i5) {
            super();
            this.mViewId = i;
            this.mLeft = i2;
            this.mTop = i3;
            this.mRight = i4;
            this.mBottom = i5;
        }

        public ViewPaddingAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mLeft = parcel.readInt();
            this.mTop = parcel.readInt();
            this.mRight = parcel.readInt();
            this.mBottom = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mLeft);
            parcel.writeInt(this.mTop);
            parcel.writeInt(this.mRight);
            parcel.writeInt(this.mBottom);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            findViewById.setPadding(this.mLeft, this.mTop, this.mRight, this.mBottom);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 14;
        }
    }

    private static class LayoutParamAction extends android.widget.RemoteViews.Action {
        static final int LAYOUT_HEIGHT = 9;
        static final int LAYOUT_MARGIN_BOTTOM = 3;
        static final int LAYOUT_MARGIN_END = 5;
        static final int LAYOUT_MARGIN_LEFT = 0;
        static final int LAYOUT_MARGIN_RIGHT = 2;
        static final int LAYOUT_MARGIN_START = 4;
        static final int LAYOUT_MARGIN_TOP = 1;
        static final int LAYOUT_WIDTH = 8;
        final int mProperty;
        final int mValue;
        final int mValueType;

        LayoutParamAction(int i, int i2, float f, int i3) {
            super();
            this.mViewId = i;
            this.mProperty = i2;
            this.mValueType = 2;
            this.mValue = android.util.TypedValue.createComplexDimension(f, i3);
        }

        LayoutParamAction(int i, int i2, int i3, int i4) {
            super();
            this.mViewId = i;
            this.mProperty = i2;
            this.mValueType = i4;
            this.mValue = i3;
        }

        public LayoutParamAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mProperty = parcel.readInt();
            this.mValueType = parcel.readInt();
            this.mValue = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mProperty);
            parcel.writeInt(this.mValueType);
            parcel.writeInt(this.mValue);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.ViewGroup.LayoutParams layoutParams;
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null || (layoutParams = findViewById.getLayoutParams()) == null) {
                return;
            }
            switch (this.mProperty) {
                case 0:
                    if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                        ((android.view.ViewGroup.MarginLayoutParams) layoutParams).leftMargin = getPixelOffset(findViewById);
                        findViewById.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 1:
                    if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                        ((android.view.ViewGroup.MarginLayoutParams) layoutParams).topMargin = getPixelOffset(findViewById);
                        findViewById.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 2:
                    if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                        ((android.view.ViewGroup.MarginLayoutParams) layoutParams).rightMargin = getPixelOffset(findViewById);
                        findViewById.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 3:
                    if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                        ((android.view.ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = getPixelOffset(findViewById);
                        findViewById.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 4:
                    if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                        ((android.view.ViewGroup.MarginLayoutParams) layoutParams).setMarginStart(getPixelOffset(findViewById));
                        findViewById.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 5:
                    if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                        ((android.view.ViewGroup.MarginLayoutParams) layoutParams).setMarginEnd(getPixelOffset(findViewById));
                        findViewById.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 6:
                case 7:
                default:
                    throw new java.lang.IllegalArgumentException("Unknown property " + this.mProperty);
                case 8:
                    layoutParams.width = getPixelSize(findViewById);
                    findViewById.setLayoutParams(layoutParams);
                    return;
                case 9:
                    layoutParams.height = getPixelSize(findViewById);
                    findViewById.setLayoutParams(layoutParams);
                    return;
            }
        }

        private int getPixelOffset(android.view.View view) {
            try {
                switch (this.mValueType) {
                    case 2:
                        return android.util.TypedValue.complexToDimensionPixelOffset(this.mValue, view.getResources().getDisplayMetrics());
                    case 3:
                        if (this.mValue == 0) {
                            return 0;
                        }
                        return view.getResources().getDimensionPixelOffset(this.mValue);
                    case 4:
                        android.content.res.TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(new int[]{this.mValue});
                        try {
                            return obtainStyledAttributes.getDimensionPixelOffset(0, 0);
                        } finally {
                            obtainStyledAttributes.recycle();
                        }
                    default:
                        return this.mValue;
                }
            } catch (java.lang.Throwable th) {
                throw new android.widget.RemoteViews.ActionException(th);
            }
            throw new android.widget.RemoteViews.ActionException(th);
        }

        private int getPixelSize(android.view.View view) {
            try {
                switch (this.mValueType) {
                    case 2:
                        return android.util.TypedValue.complexToDimensionPixelSize(this.mValue, view.getResources().getDisplayMetrics());
                    case 3:
                        if (this.mValue == 0) {
                            return 0;
                        }
                        return view.getResources().getDimensionPixelSize(this.mValue);
                    case 4:
                        android.content.res.TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(new int[]{this.mValue});
                        try {
                            return obtainStyledAttributes.getDimensionPixelSize(0, 0);
                        } finally {
                            obtainStyledAttributes.recycle();
                        }
                    default:
                        return this.mValue;
                }
            } catch (java.lang.Throwable th) {
                throw new android.widget.RemoteViews.ActionException(th);
            }
            throw new android.widget.RemoteViews.ActionException(th);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 19;
        }

        @Override // android.widget.RemoteViews.Action
        public java.lang.String getUniqueKey() {
            return super.getUniqueKey() + this.mProperty;
        }
    }

    private static class SetRemoteInputsAction extends android.widget.RemoteViews.Action {
        final android.os.Parcelable[] mRemoteInputs;

        public SetRemoteInputsAction(int i, android.app.RemoteInput[] remoteInputArr) {
            super();
            this.mViewId = i;
            this.mRemoteInputs = remoteInputArr;
        }

        public SetRemoteInputsAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mRemoteInputs = (android.os.Parcelable[]) parcel.createTypedArray(android.app.RemoteInput.CREATOR);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeTypedArray(this.mRemoteInputs, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            findViewById.setTagInternal(com.android.internal.R.id.remote_input_tag, this.mRemoteInputs);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 18;
        }
    }

    private static class SetIntTagAction extends android.widget.RemoteViews.Action {
        private final int mKey;
        private final int mTag;
        private final int mViewId;

        SetIntTagAction(int i, int i2, int i3) {
            super();
            this.mViewId = i;
            this.mKey = i2;
            this.mTag = i3;
        }

        SetIntTagAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mKey = parcel.readInt();
            this.mTag = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mKey);
            parcel.writeInt(this.mTag);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            findViewById.setTagInternal(this.mKey, java.lang.Integer.valueOf(this.mTag));
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 22;
        }
    }

    private static class SetCompoundButtonCheckedAction extends android.widget.RemoteViews.Action {
        private final boolean mChecked;

        SetCompoundButtonCheckedAction(int i, boolean z) {
            super();
            this.mViewId = i;
            this.mChecked = z;
        }

        SetCompoundButtonCheckedAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mChecked = parcel.readBoolean();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeBoolean(this.mChecked);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) throws android.widget.RemoteViews.ActionException {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            if (!(findViewById instanceof android.widget.CompoundButton)) {
                android.util.Log.w(android.widget.RemoteViews.LOG_TAG, "Cannot set checked to view " + this.mViewId + " because it is not a CompoundButton");
                return;
            }
            android.widget.CompoundButton compoundButton = (android.widget.CompoundButton) findViewById;
            java.lang.Object tag = compoundButton.getTag(com.android.internal.R.id.remote_checked_change_listener_tag);
            if (tag instanceof android.widget.CompoundButton.OnCheckedChangeListener) {
                compoundButton.setOnCheckedChangeListener(null);
                compoundButton.setChecked(this.mChecked);
                compoundButton.setOnCheckedChangeListener((android.widget.CompoundButton.OnCheckedChangeListener) tag);
                return;
            }
            compoundButton.setChecked(this.mChecked);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 26;
        }
    }

    private static class SetRadioGroupCheckedAction extends android.widget.RemoteViews.Action {
        private final int mCheckedId;

        SetRadioGroupCheckedAction(int i, int i2) {
            super();
            this.mViewId = i;
            this.mCheckedId = i2;
        }

        SetRadioGroupCheckedAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mCheckedId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mCheckedId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) throws android.widget.RemoteViews.ActionException {
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            if (!(findViewById instanceof android.widget.RadioGroup)) {
                android.util.Log.w(android.widget.RemoteViews.LOG_TAG, "Cannot check " + this.mViewId + " because it's not a RadioGroup");
                return;
            }
            android.widget.RadioGroup radioGroup = (android.widget.RadioGroup) findViewById;
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                android.view.View childAt = radioGroup.getChildAt(i);
                if ((childAt instanceof android.widget.CompoundButton) && (childAt.getTag(com.android.internal.R.id.remote_checked_change_listener_tag) instanceof android.widget.CompoundButton.OnCheckedChangeListener)) {
                    ((android.widget.CompoundButton) childAt).setOnCheckedChangeListener(null);
                }
            }
            radioGroup.check(this.mCheckedId);
            for (int i2 = 0; i2 < radioGroup.getChildCount(); i2++) {
                android.view.View childAt2 = radioGroup.getChildAt(i2);
                if (childAt2 instanceof android.widget.CompoundButton) {
                    java.lang.Object tag = childAt2.getTag(com.android.internal.R.id.remote_checked_change_listener_tag);
                    if (tag instanceof android.widget.CompoundButton.OnCheckedChangeListener) {
                        ((android.widget.CompoundButton) childAt2).setOnCheckedChangeListener((android.widget.CompoundButton.OnCheckedChangeListener) tag);
                    }
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 27;
        }
    }

    private static class SetViewOutlinePreferredRadiusAction extends android.widget.RemoteViews.Action {
        private final int mValue;
        private final int mValueType;

        SetViewOutlinePreferredRadiusAction(int i, int i2, int i3) {
            super();
            this.mViewId = i;
            this.mValueType = i3;
            this.mValue = i2;
        }

        SetViewOutlinePreferredRadiusAction(int i, float f, int i2) {
            super();
            this.mViewId = i;
            this.mValueType = 2;
            this.mValue = android.util.TypedValue.createComplexDimension(f, i2);
        }

        SetViewOutlinePreferredRadiusAction(android.os.Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mValueType = parcel.readInt();
            this.mValue = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mValueType);
            parcel.writeInt(this.mValue);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) throws android.widget.RemoteViews.ActionException {
            float complexToDimension;
            android.view.View findViewById = view.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            try {
                switch (this.mValueType) {
                    case 2:
                        complexToDimension = android.util.TypedValue.complexToDimension(this.mValue, findViewById.getResources().getDisplayMetrics());
                        findViewById.setOutlineProvider(new android.widget.RemoteViews.RemoteViewOutlineProvider(complexToDimension));
                        return;
                    case 3:
                        complexToDimension = this.mValue != 0 ? findViewById.getResources().getDimension(this.mValue) : 0.0f;
                        findViewById.setOutlineProvider(new android.widget.RemoteViews.RemoteViewOutlineProvider(complexToDimension));
                        return;
                    case 4:
                        android.content.res.TypedArray obtainStyledAttributes = findViewById.getContext().obtainStyledAttributes(new int[]{this.mValue});
                        try {
                            complexToDimension = obtainStyledAttributes.getDimension(0, 0.0f);
                            obtainStyledAttributes.recycle();
                            findViewById.setOutlineProvider(new android.widget.RemoteViews.RemoteViewOutlineProvider(complexToDimension));
                            return;
                        } catch (java.lang.Throwable th) {
                            obtainStyledAttributes.recycle();
                            throw th;
                        }
                    default:
                        complexToDimension = this.mValue;
                        findViewById.setOutlineProvider(new android.widget.RemoteViews.RemoteViewOutlineProvider(complexToDimension));
                        return;
                }
            } catch (java.lang.Throwable th2) {
                throw new android.widget.RemoteViews.ActionException(th2);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 28;
        }
    }

    public static final class RemoteViewOutlineProvider extends android.view.ViewOutlineProvider {
        private final float mRadius;

        public RemoteViewOutlineProvider(float f) {
            this.mRadius = f;
        }

        public float getRadius() {
            return this.mRadius;
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(android.view.View view, android.graphics.Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), this.mRadius);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SetDrawInstructionAction extends android.widget.RemoteViews.Action {
        private final android.widget.RemoteViews.DrawInstructions mInstructions;

        SetDrawInstructionAction(android.widget.RemoteViews.DrawInstructions drawInstructions) {
            super();
            this.mInstructions = drawInstructions;
        }

        SetDrawInstructionAction(android.os.Parcel parcel) {
            super();
            if (android.appwidget.flags.Flags.drawDataParcel()) {
                this.mInstructions = android.widget.RemoteViews.DrawInstructions.readFromParcel(parcel);
            } else {
                this.mInstructions = null;
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(android.os.Parcel parcel, int i) {
            if (android.appwidget.flags.Flags.drawDataParcel()) {
                android.widget.RemoteViews.DrawInstructions.writeToParcel(this.mInstructions, parcel, i);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(android.view.View view, android.view.ViewGroup viewGroup, final android.widget.RemoteViews.ActionApplyParams actionApplyParams) throws android.widget.RemoteViews.ActionException {
            if (android.appwidget.flags.Flags.drawDataParcel() && this.mInstructions != null && (view instanceof com.android.internal.widget.remotecompose.player.RemoteComposePlayer)) {
                final com.android.internal.widget.remotecompose.player.RemoteComposePlayer remoteComposePlayer = (com.android.internal.widget.remotecompose.player.RemoteComposePlayer) view;
                java.util.List<byte[]> list = this.mInstructions.mInstructions;
                if (list.isEmpty()) {
                    return;
                }
                try {
                    java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(list.get(0));
                    try {
                        remoteComposePlayer.setDocument(new com.android.internal.widget.remotecompose.player.RemoteComposeDocument(byteArrayInputStream));
                        remoteComposePlayer.addClickListener(new com.android.internal.widget.remotecompose.player.RemoteComposePlayer.ClickCallbacks() { // from class: android.widget.RemoteViews$SetDrawInstructionAction$$ExternalSyntheticLambda0
                            @Override // com.android.internal.widget.remotecompose.player.RemoteComposePlayer.ClickCallbacks
                            public final void click(int i, java.lang.String str) {
                                android.widget.RemoteViews.SetDrawInstructionAction.this.lambda$apply$1(remoteComposePlayer, actionApplyParams, i, str);
                            }
                        });
                        byteArrayInputStream.close();
                    } finally {
                    }
                } catch (java.io.IOException e) {
                    android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Failed to render draw instructions", e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$1(final com.android.internal.widget.remotecompose.player.RemoteComposePlayer remoteComposePlayer, final android.widget.RemoteViews.ActionApplyParams actionApplyParams, final int i, java.lang.String str) {
            android.widget.RemoteViews.this.mActions.forEach(new java.util.function.Consumer() { // from class: android.widget.RemoteViews$SetDrawInstructionAction$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.widget.RemoteViews.SetDrawInstructionAction.lambda$apply$0(i, remoteComposePlayer, actionApplyParams, (android.widget.RemoteViews.Action) obj);
                }
            });
        }

        static /* synthetic */ void lambda$apply$0(int i, com.android.internal.widget.remotecompose.player.RemoteComposePlayer remoteComposePlayer, android.widget.RemoteViews.ActionApplyParams actionApplyParams, android.widget.RemoteViews.Action action) {
            if (i == action.mViewId && (action instanceof android.widget.RemoteViews.SetOnClickResponse)) {
                ((android.widget.RemoteViews.SetOnClickResponse) action).mResponse.handleViewInteraction(remoteComposePlayer, actionApplyParams.handler);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 35;
        }
    }

    public RemoteViews(java.lang.String str, int i) {
        this(getApplicationInfo(str, android.os.UserHandle.myUserId()), i);
    }

    public RemoteViews(java.lang.String str, int i, int i2) {
        this(str, i);
        this.mViewId = i2;
    }

    protected RemoteViews(android.content.pm.ApplicationInfo applicationInfo, int i) {
        this.mLightBackgroundLayoutId = 0;
        this.mBitmapCache = new android.widget.RemoteViews.BitmapCache();
        this.mCollectionCache = new android.widget.RemoteViews.RemoteCollectionCache();
        this.mApplicationInfoCache = new android.widget.RemoteViews.ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        this.mApplication = applicationInfo;
        this.mLayoutId = i;
        this.mApplicationInfoCache.put(applicationInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasMultipleLayouts() {
        return hasLandscapeAndPortraitLayouts() || hasSizedRemoteViews();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasLandscapeAndPortraitLayouts() {
        return (this.mLandscape == null || this.mPortrait == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasSizedRemoteViews() {
        return this.mSizedRemoteViews != null;
    }

    private android.util.SizeF getIdealSize() {
        return this.mIdealSize;
    }

    private void setIdealSize(android.util.SizeF sizeF) {
        this.mIdealSize = sizeF;
    }

    private android.widget.RemoteViews findSmallestRemoteView() {
        return this.mSizedRemoteViews.get(this.mSizedRemoteViews.size() - 1);
    }

    public RemoteViews(android.widget.RemoteViews remoteViews, android.widget.RemoteViews remoteViews2) {
        this.mLightBackgroundLayoutId = 0;
        this.mBitmapCache = new android.widget.RemoteViews.BitmapCache();
        this.mCollectionCache = new android.widget.RemoteViews.RemoteCollectionCache();
        this.mApplicationInfoCache = new android.widget.RemoteViews.ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        if (remoteViews == null || remoteViews2 == null) {
            throw new java.lang.IllegalArgumentException("Both RemoteViews must be non-null");
        }
        if (!remoteViews.hasSameAppInfo(remoteViews2.mApplication)) {
            throw new java.lang.IllegalArgumentException("Both RemoteViews must share the same package and user");
        }
        this.mApplication = remoteViews2.mApplication;
        this.mLayoutId = remoteViews2.mLayoutId;
        this.mViewId = remoteViews2.mViewId;
        this.mLightBackgroundLayoutId = remoteViews2.mLightBackgroundLayoutId;
        this.mLandscape = remoteViews;
        this.mPortrait = remoteViews2;
        this.mClassCookies = remoteViews2.mClassCookies != null ? remoteViews2.mClassCookies : remoteViews.mClassCookies;
        configureDescendantsAsChildren();
    }

    public RemoteViews(java.util.Map<android.util.SizeF, android.widget.RemoteViews> map) {
        this.mLightBackgroundLayoutId = 0;
        this.mBitmapCache = new android.widget.RemoteViews.BitmapCache();
        this.mCollectionCache = new android.widget.RemoteViews.RemoteCollectionCache();
        this.mApplicationInfoCache = new android.widget.RemoteViews.ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        if (map.isEmpty()) {
            throw new java.lang.IllegalArgumentException("The set of RemoteViews cannot be empty");
        }
        if (map.size() <= 16) {
            if (map.size() == 1) {
                android.widget.RemoteViews next = map.values().iterator().next();
                initializeFrom(next, next);
                return;
            }
            this.mClassCookies = initializeSizedRemoteViews(map.entrySet().stream().map(new java.util.function.Function() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.widget.RemoteViews.lambda$new$2((java.util.Map.Entry) obj);
                }
            }).iterator());
            android.widget.RemoteViews findSmallestRemoteView = findSmallestRemoteView();
            this.mApplication = findSmallestRemoteView.mApplication;
            this.mLayoutId = findSmallestRemoteView.mLayoutId;
            this.mViewId = findSmallestRemoteView.mViewId;
            this.mLightBackgroundLayoutId = findSmallestRemoteView.mLightBackgroundLayoutId;
            configureDescendantsAsChildren();
            return;
        }
        throw new java.lang.IllegalArgumentException("Too many RemoteViews in constructor");
    }

    static /* synthetic */ android.widget.RemoteViews lambda$new$2(java.util.Map.Entry entry) {
        ((android.widget.RemoteViews) entry.getValue()).setIdealSize((android.util.SizeF) entry.getKey());
        return (android.widget.RemoteViews) entry.getValue();
    }

    private java.util.Map<java.lang.Class, java.lang.Object> initializeSizedRemoteViews(java.util.Iterator<android.widget.RemoteViews> it) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.widget.RemoteViews remoteViews = null;
        float f = Float.MAX_VALUE;
        java.util.Map<java.lang.Class, java.lang.Object> map = null;
        while (it.hasNext()) {
            android.widget.RemoteViews next = it.next();
            android.util.SizeF idealSize = next.getIdealSize();
            if (idealSize == null) {
                throw new java.lang.IllegalStateException("Expected RemoteViews to have ideal size");
            }
            float width = idealSize.getWidth() * idealSize.getHeight();
            if (remoteViews != null && !next.hasSameAppInfo(remoteViews.mApplication)) {
                throw new java.lang.IllegalArgumentException("All RemoteViews must share the same package and user");
            }
            if (remoteViews == null || width < f) {
                if (remoteViews != null) {
                    arrayList.add(remoteViews);
                }
                remoteViews = next;
                f = width;
            } else {
                arrayList.add(next);
            }
            next.setIdealSize(idealSize);
            if (map == null) {
                map = next.mClassCookies;
            }
        }
        arrayList.add(remoteViews);
        this.mSizedRemoteViews = arrayList;
        return map;
    }

    public RemoteViews(android.widget.RemoteViews remoteViews) {
        this.mLightBackgroundLayoutId = 0;
        this.mBitmapCache = new android.widget.RemoteViews.BitmapCache();
        this.mCollectionCache = new android.widget.RemoteViews.RemoteCollectionCache();
        this.mApplicationInfoCache = new android.widget.RemoteViews.ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        initializeFrom(remoteViews, null);
    }

    private RemoteViews() {
        this.mLightBackgroundLayoutId = 0;
        this.mBitmapCache = new android.widget.RemoteViews.BitmapCache();
        this.mCollectionCache = new android.widget.RemoteViews.RemoteCollectionCache();
        this.mApplicationInfoCache = new android.widget.RemoteViews.ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
    }

    private static android.widget.RemoteViews createInitializedFrom(android.widget.RemoteViews remoteViews, android.widget.RemoteViews remoteViews2) {
        android.widget.RemoteViews remoteViews3 = new android.widget.RemoteViews();
        remoteViews3.initializeFrom(remoteViews, remoteViews2);
        return remoteViews3;
    }

    private void initializeFrom(android.widget.RemoteViews remoteViews, android.widget.RemoteViews remoteViews2) {
        if (remoteViews2 == null) {
            this.mBitmapCache = remoteViews.mBitmapCache;
            this.mCollectionCache = new android.widget.RemoteViews.RemoteCollectionCache(remoteViews.mCollectionCache);
            this.mApplicationInfoCache = remoteViews.mApplicationInfoCache;
        } else {
            this.mBitmapCache = remoteViews2.mBitmapCache;
            this.mCollectionCache = remoteViews2.mCollectionCache;
            this.mApplicationInfoCache = remoteViews2.mApplicationInfoCache;
        }
        if (remoteViews2 == null || remoteViews.mIsRoot) {
            this.mIsRoot = true;
            remoteViews2 = this;
        } else {
            this.mIsRoot = false;
        }
        this.mApplication = remoteViews.mApplication;
        this.mLayoutId = remoteViews.mLayoutId;
        this.mLightBackgroundLayoutId = remoteViews.mLightBackgroundLayoutId;
        this.mApplyFlags = remoteViews.mApplyFlags;
        this.mClassCookies = remoteViews.mClassCookies;
        this.mIdealSize = remoteViews.mIdealSize;
        this.mProviderInstanceId = remoteViews.mProviderInstanceId;
        this.mHasDrawInstructions = remoteViews.mHasDrawInstructions;
        if (remoteViews.hasLandscapeAndPortraitLayouts()) {
            this.mLandscape = createInitializedFrom(remoteViews.mLandscape, remoteViews2);
            this.mPortrait = createInitializedFrom(remoteViews.mPortrait, remoteViews2);
        }
        if (remoteViews.hasSizedRemoteViews()) {
            this.mSizedRemoteViews = new java.util.ArrayList(remoteViews.mSizedRemoteViews.size());
            java.util.Iterator<android.widget.RemoteViews> it = remoteViews.mSizedRemoteViews.iterator();
            while (it.hasNext()) {
                this.mSizedRemoteViews.add(createInitializedFrom(it.next(), remoteViews2));
            }
        }
        if (remoteViews.mActions != null) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            obtain.putClassCookies(this.mClassCookies);
            remoteViews.writeActionsToParcel(obtain, 0);
            obtain.setDataPosition(0);
            readActionsFromParcel(obtain, 0);
            obtain.recycle();
        }
        if (this.mIsRoot) {
            reconstructCaches();
        }
    }

    public RemoteViews(android.os.Parcel parcel) {
        this(parcel, null, null, 0);
    }

    public RemoteViews(android.widget.RemoteViews.DrawInstructions drawInstructions) {
        this.mLightBackgroundLayoutId = 0;
        this.mBitmapCache = new android.widget.RemoteViews.BitmapCache();
        this.mCollectionCache = new android.widget.RemoteViews.RemoteCollectionCache();
        this.mApplicationInfoCache = new android.widget.RemoteViews.ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        java.util.Objects.requireNonNull(drawInstructions);
        this.mHasDrawInstructions = true;
        addAction(new android.widget.RemoteViews.SetDrawInstructionAction(drawInstructions));
    }

    private RemoteViews(android.os.Parcel parcel, android.widget.RemoteViews.HierarchyRootData hierarchyRootData, android.content.pm.ApplicationInfo applicationInfo, int i) {
        this.mLightBackgroundLayoutId = 0;
        this.mBitmapCache = new android.widget.RemoteViews.BitmapCache();
        this.mCollectionCache = new android.widget.RemoteViews.RemoteCollectionCache();
        this.mApplicationInfoCache = new android.widget.RemoteViews.ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        if (i > 10 && android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) != 1000) {
            throw new java.lang.IllegalArgumentException("Too many nested views.");
        }
        int i2 = i + 1;
        int readInt = parcel.readInt();
        if (hierarchyRootData == null) {
            this.mBitmapCache = new android.widget.RemoteViews.BitmapCache(parcel);
            this.mClassCookies = parcel.copyClassCookies();
            this.mCollectionCache = new android.widget.RemoteViews.RemoteCollectionCache(parcel);
        } else {
            configureAsChild(hierarchyRootData);
        }
        if (readInt == 0) {
            this.mApplication = android.content.pm.ApplicationInfo.CREATOR.createFromParcel(parcel);
            this.mIdealSize = parcel.readInt() != 0 ? android.util.SizeF.CREATOR.createFromParcel(parcel) : null;
            this.mLayoutId = parcel.readInt();
            this.mViewId = parcel.readInt();
            this.mLightBackgroundLayoutId = parcel.readInt();
            readActionsFromParcel(parcel, i2);
        } else if (readInt == 2) {
            int readInt2 = parcel.readInt();
            if (readInt2 > 16) {
                throw new java.lang.IllegalArgumentException("Too many views in mapping from size to RemoteViews.");
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(readInt2);
            for (int i3 = 0; i3 < readInt2; i3++) {
                android.widget.RemoteViews remoteViews = new android.widget.RemoteViews(parcel, getHierarchyRootData(), applicationInfo, i2);
                applicationInfo = remoteViews.mApplication;
                arrayList.add(remoteViews);
            }
            initializeSizedRemoteViews(arrayList.iterator());
            android.widget.RemoteViews findSmallestRemoteView = findSmallestRemoteView();
            this.mApplication = findSmallestRemoteView.mApplication;
            this.mLayoutId = findSmallestRemoteView.mLayoutId;
            this.mViewId = findSmallestRemoteView.mViewId;
            this.mLightBackgroundLayoutId = findSmallestRemoteView.mLightBackgroundLayoutId;
        } else {
            this.mLandscape = new android.widget.RemoteViews(parcel, getHierarchyRootData(), applicationInfo, i2);
            this.mPortrait = new android.widget.RemoteViews(parcel, getHierarchyRootData(), this.mLandscape.mApplication, i2);
            this.mApplication = this.mPortrait.mApplication;
            this.mLayoutId = this.mPortrait.mLayoutId;
            this.mViewId = this.mPortrait.mViewId;
            this.mLightBackgroundLayoutId = this.mPortrait.mLightBackgroundLayoutId;
        }
        this.mApplyFlags = parcel.readInt();
        this.mProviderInstanceId = parcel.readLong();
        this.mHasDrawInstructions = parcel.readBoolean();
        if (this.mIsRoot) {
            configureDescendantsAsChildren();
        }
    }

    private void readActionsFromParcel(android.os.Parcel parcel, int i) {
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mActions = new java.util.ArrayList<>(readInt);
            for (int i2 = 0; i2 < readInt; i2++) {
                this.mActions.add(getActionFromParcel(parcel, i));
            }
        }
    }

    private android.widget.RemoteViews.Action getActionFromParcel(android.os.Parcel parcel, int i) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 1:
                return new android.widget.RemoteViews.SetOnClickResponse(parcel);
            case 2:
                return new android.widget.RemoteViews.ReflectionAction(parcel);
            case 3:
                return new android.widget.RemoteViews.SetDrawableTint(parcel);
            case 4:
                return new android.widget.RemoteViews.ViewGroupActionAdd(parcel, this.mApplication, i);
            case 5:
                return new android.widget.RemoteViews.ViewContentNavigation(parcel);
            case 6:
                return new android.widget.RemoteViews.SetEmptyView(parcel);
            case 7:
                return new android.widget.RemoteViews.ViewGroupActionRemove(parcel);
            case 8:
                return new android.widget.RemoteViews.SetPendingIntentTemplate(parcel);
            case 9:
            case 15:
            case 16:
            case 17:
            case 20:
            case 33:
            default:
                throw new android.widget.RemoteViews.ActionException("Tag " + readInt + " not found");
            case 10:
                return new android.widget.RemoteViews.SetRemoteViewsAdapterIntent(parcel);
            case 11:
                return new android.widget.RemoteViews.TextViewDrawableAction(parcel);
            case 12:
                return new android.widget.RemoteViews.BitmapReflectionAction(parcel);
            case 13:
                return new android.widget.RemoteViews.TextViewSizeAction(parcel);
            case 14:
                return new android.widget.RemoteViews.ViewPaddingAction(parcel);
            case 18:
                return new android.widget.RemoteViews.SetRemoteInputsAction(parcel);
            case 19:
                return new android.widget.RemoteViews.LayoutParamAction(parcel);
            case 21:
                return new android.widget.RemoteViews.SetRippleDrawableColor(parcel);
            case 22:
                return new android.widget.RemoteViews.SetIntTagAction(parcel);
            case 23:
                return new android.widget.RemoteViews.RemoveFromParentAction(parcel);
            case 24:
                return new android.widget.RemoteViews.ResourceReflectionAction(parcel);
            case 25:
                return new android.widget.RemoteViews.ComplexUnitDimensionReflectionAction(parcel);
            case 26:
                return new android.widget.RemoteViews.SetCompoundButtonCheckedAction(parcel);
            case 27:
                return new android.widget.RemoteViews.SetRadioGroupCheckedAction(parcel);
            case 28:
                return new android.widget.RemoteViews.SetViewOutlinePreferredRadiusAction(parcel);
            case 29:
                return new android.widget.RemoteViews.SetOnCheckedChangeResponse(parcel);
            case 30:
                return new android.widget.RemoteViews.NightModeReflectionAction(parcel);
            case 31:
                return new android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction(parcel);
            case 32:
                return new android.widget.RemoteViews.AttributeReflectionAction(parcel);
            case 34:
                return new android.widget.RemoteViews.SetOnStylusHandwritingResponse(parcel);
            case 35:
                return new android.widget.RemoteViews.SetDrawInstructionAction(parcel);
        }
    }

    @Override // 
    @java.lang.Deprecated
    /* renamed from: clone */
    public android.widget.RemoteViews mo424clone() {
        com.android.internal.util.Preconditions.checkState(this.mIsRoot, "RemoteView has been attached to another RemoteView. May only clone the root of a RemoteView hierarchy.");
        return new android.widget.RemoteViews(this);
    }

    public java.lang.String getPackage() {
        if (this.mApplication != null) {
            return this.mApplication.packageName;
        }
        return null;
    }

    public int getLayoutId() {
        return (!hasFlags(4) || this.mLightBackgroundLayoutId == 0) ? this.mLayoutId : this.mLightBackgroundLayoutId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configureAsChild(android.widget.RemoteViews.HierarchyRootData hierarchyRootData) {
        this.mIsRoot = false;
        this.mBitmapCache = hierarchyRootData.mBitmapCache;
        this.mCollectionCache = hierarchyRootData.mRemoteCollectionCache;
        this.mApplicationInfoCache = hierarchyRootData.mApplicationInfoCache;
        this.mClassCookies = hierarchyRootData.mClassCookies;
        configureDescendantsAsChildren();
    }

    private void configureDescendantsAsChildren() {
        this.mApplication = this.mApplicationInfoCache.getOrPut(this.mApplication);
        android.widget.RemoteViews.HierarchyRootData hierarchyRootData = getHierarchyRootData();
        if (hasSizedRemoteViews()) {
            java.util.Iterator<android.widget.RemoteViews> it = this.mSizedRemoteViews.iterator();
            while (it.hasNext()) {
                it.next().configureAsChild(hierarchyRootData);
            }
        } else if (hasLandscapeAndPortraitLayouts()) {
            this.mLandscape.configureAsChild(hierarchyRootData);
            this.mPortrait.configureAsChild(hierarchyRootData);
        } else if (this.mActions != null) {
            java.util.Iterator<android.widget.RemoteViews.Action> it2 = this.mActions.iterator();
            while (it2.hasNext()) {
                it2.next().setHierarchyRootData(hierarchyRootData);
            }
        }
    }

    private void reconstructCaches() {
        if (this.mIsRoot) {
            this.mBitmapCache = new android.widget.RemoteViews.BitmapCache();
            this.mApplicationInfoCache = new android.widget.RemoteViews.ApplicationInfoCache();
            this.mApplication = this.mApplicationInfoCache.getOrPut(this.mApplication);
            configureDescendantsAsChildren();
        }
    }

    public int estimateMemoryUsage() {
        return this.mBitmapCache.getBitmapMemory();
    }

    private void addAction(android.widget.RemoteViews.Action action) {
        if (hasMultipleLayouts()) {
            throw new java.lang.RuntimeException("RemoteViews specifying separate layouts for orientation or size cannot be modified. Instead, fully configure each layouts individually before constructing the combined layout.");
        }
        if (this.mActions == null) {
            this.mActions = new java.util.ArrayList<>();
        }
        this.mActions.add(action);
    }

    public void addView(int i, android.widget.RemoteViews remoteViews) {
        android.widget.RemoteViews.Action viewGroupActionAdd;
        if (remoteViews == null) {
            viewGroupActionAdd = new android.widget.RemoteViews.ViewGroupActionRemove(i);
        } else {
            viewGroupActionAdd = new android.widget.RemoteViews.ViewGroupActionAdd(this, i, remoteViews);
        }
        addAction(viewGroupActionAdd);
    }

    public void addStableView(int i, android.widget.RemoteViews remoteViews, int i2) {
        addAction(new android.widget.RemoteViews.ViewGroupActionAdd(i, remoteViews, -1, i2));
    }

    public void addView(int i, android.widget.RemoteViews remoteViews, int i2) {
        addAction(new android.widget.RemoteViews.ViewGroupActionAdd(this, i, remoteViews, i2));
    }

    public void removeAllViews(int i) {
        addAction(new android.widget.RemoteViews.ViewGroupActionRemove(i));
    }

    public void removeAllViewsExceptId(int i, int i2) {
        addAction(new android.widget.RemoteViews.ViewGroupActionRemove(i, i2));
    }

    public void removeFromParent(int i) {
        addAction(new android.widget.RemoteViews.RemoveFromParentAction(i));
    }

    @java.lang.Deprecated
    public void showNext(int i) {
        addAction(new android.widget.RemoteViews.ViewContentNavigation(i, true));
    }

    @java.lang.Deprecated
    public void showPrevious(int i) {
        addAction(new android.widget.RemoteViews.ViewContentNavigation(i, false));
    }

    public void setDisplayedChild(int i, int i2) {
        setInt(i, "setDisplayedChild", i2);
    }

    public void setViewVisibility(int i, int i2) {
        setInt(i, "setVisibility", i2);
    }

    public void setTextViewText(int i, java.lang.CharSequence charSequence) {
        setCharSequence(i, "setText", charSequence);
    }

    public void setTextViewTextSize(int i, int i2, float f) {
        addAction(new android.widget.RemoteViews.TextViewSizeAction(i, i2, f));
    }

    public void setTextViewCompoundDrawables(int i, int i2, int i3, int i4, int i5) {
        addAction(new android.widget.RemoteViews.TextViewDrawableAction(i, false, i2, i3, i4, i5));
    }

    public void setTextViewCompoundDrawablesRelative(int i, int i2, int i3, int i4, int i5) {
        addAction(new android.widget.RemoteViews.TextViewDrawableAction(i, true, i2, i3, i4, i5));
    }

    public void setTextViewCompoundDrawables(int i, android.graphics.drawable.Icon icon, android.graphics.drawable.Icon icon2, android.graphics.drawable.Icon icon3, android.graphics.drawable.Icon icon4) {
        addAction(new android.widget.RemoteViews.TextViewDrawableAction(i, false, icon, icon2, icon3, icon4));
    }

    public void setTextViewCompoundDrawablesRelative(int i, android.graphics.drawable.Icon icon, android.graphics.drawable.Icon icon2, android.graphics.drawable.Icon icon3, android.graphics.drawable.Icon icon4) {
        addAction(new android.widget.RemoteViews.TextViewDrawableAction(i, true, icon, icon2, icon3, icon4));
    }

    public void setImageViewResource(int i, int i2) {
        setInt(i, "setImageResource", i2);
    }

    public void setImageViewUri(int i, android.net.Uri uri) {
        setUri(i, "setImageURI", uri);
    }

    public void setImageViewBitmap(int i, android.graphics.Bitmap bitmap) {
        setBitmap(i, "setImageBitmap", bitmap);
    }

    public void setImageViewIcon(int i, android.graphics.drawable.Icon icon) {
        setIcon(i, "setImageIcon", icon);
    }

    public void setEmptyView(int i, int i2) {
        addAction(new android.widget.RemoteViews.SetEmptyView(i, i2));
    }

    public void setChronometer(int i, long j, java.lang.String str, boolean z) {
        setLong(i, "setBase", j);
        setString(i, "setFormat", str);
        setBoolean(i, "setStarted", z);
    }

    public void setChronometerCountDown(int i, boolean z) {
        setBoolean(i, "setCountDown", z);
    }

    public void setProgressBar(int i, int i2, int i3, boolean z) {
        setBoolean(i, "setIndeterminate", z);
        if (!z) {
            setInt(i, "setMax", i2);
            setInt(i, "setProgress", i3);
        }
    }

    public void setOnClickPendingIntent(int i, android.app.PendingIntent pendingIntent) {
        setOnClickResponse(i, android.widget.RemoteViews.RemoteResponse.fromPendingIntent(pendingIntent));
    }

    public void setOnClickResponse(int i, android.widget.RemoteViews.RemoteResponse remoteResponse) {
        addAction(new android.widget.RemoteViews.SetOnClickResponse(i, remoteResponse));
    }

    public void setPendingIntentTemplate(int i, android.app.PendingIntent pendingIntent) {
        if (hasDrawInstructions()) {
            getPendingIntentTemplate().set(i, pendingIntent);
            tryAddRemoteResponse(i);
        } else {
            addAction(new android.widget.RemoteViews.SetPendingIntentTemplate(i, pendingIntent));
        }
    }

    public void setOnClickFillInIntent(int i, android.content.Intent intent) {
        if (hasDrawInstructions()) {
            getFillInIntent().set(i, intent);
            tryAddRemoteResponse(i);
        } else {
            setOnClickResponse(i, android.widget.RemoteViews.RemoteResponse.fromFillInIntent(intent));
        }
    }

    public void setOnCheckedChangeResponse(int i, android.widget.RemoteViews.RemoteResponse remoteResponse) {
        addAction(new android.widget.RemoteViews.SetOnCheckedChangeResponse(i, remoteResponse.setInteractionType(1)));
    }

    public void setOnStylusHandwritingPendingIntent(int i, android.app.PendingIntent pendingIntent) {
        addAction(new android.widget.RemoteViews.SetOnStylusHandwritingResponse(i, pendingIntent));
    }

    public void setDrawableTint(int i, boolean z, int i2, android.graphics.PorterDuff.Mode mode) {
        addAction(new android.widget.RemoteViews.SetDrawableTint(i, z, i2, mode));
    }

    public void setRippleDrawableColor(int i, android.content.res.ColorStateList colorStateList) {
        addAction(new android.widget.RemoteViews.SetRippleDrawableColor(i, colorStateList));
    }

    public void setProgressTintList(int i, android.content.res.ColorStateList colorStateList) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, "setProgressTintList", 15, colorStateList));
    }

    public void setProgressBackgroundTintList(int i, android.content.res.ColorStateList colorStateList) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, "setProgressBackgroundTintList", 15, colorStateList));
    }

    public void setProgressIndeterminateTintList(int i, android.content.res.ColorStateList colorStateList) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, "setIndeterminateTintList", 15, colorStateList));
    }

    public void setTextColor(int i, int i2) {
        setInt(i, "setTextColor", i2);
    }

    public void setTextColor(int i, android.content.res.ColorStateList colorStateList) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, "setTextColor", 15, colorStateList));
    }

    @java.lang.Deprecated
    public void setRemoteAdapter(int i, int i2, android.content.Intent intent) {
        setRemoteAdapter(i2, intent);
    }

    public void setRemoteAdapter(int i, android.content.Intent intent) {
        if (android.appwidget.flags.Flags.remoteAdapterConversion()) {
            addAction(new android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction(i, intent));
        } else {
            addAction(new android.widget.RemoteViews.SetRemoteViewsAdapterIntent(i, intent));
        }
    }

    @java.lang.Deprecated
    public void setRemoteAdapter(int i, java.util.ArrayList<android.widget.RemoteViews> arrayList, int i2) {
        android.widget.RemoteViews.RemoteCollectionItems.Builder builder = new android.widget.RemoteViews.RemoteCollectionItems.Builder();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            builder.addItem(i3, arrayList.get(i3));
        }
        setRemoteAdapter(i, builder.setViewTypeCount(i2).build());
    }

    public void setRemoteAdapter(int i, android.widget.RemoteViews.RemoteCollectionItems remoteCollectionItems) {
        addAction(new android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction(i, remoteCollectionItems));
    }

    public void setScrollPosition(int i, int i2) {
        setInt(i, "smoothScrollToPosition", i2);
    }

    public void setRelativeScrollPosition(int i, int i2) {
        setInt(i, "smoothScrollByOffset", i2);
    }

    public void setViewPadding(int i, int i2, int i3, int i4, int i5) {
        addAction(new android.widget.RemoteViews.ViewPaddingAction(i, i2, i3, i4, i5));
    }

    public void setViewLayoutMarginDimen(int i, int i2, int i3) {
        addAction(new android.widget.RemoteViews.LayoutParamAction(i, i2, i3, 3));
    }

    public void setViewLayoutMarginAttr(int i, int i2, int i3) {
        addAction(new android.widget.RemoteViews.LayoutParamAction(i, i2, i3, 4));
    }

    public void setViewLayoutMargin(int i, int i2, float f, int i3) {
        addAction(new android.widget.RemoteViews.LayoutParamAction(i, i2, f, i3));
    }

    public void setViewLayoutWidth(int i, float f, int i2) {
        addAction(new android.widget.RemoteViews.LayoutParamAction(i, 8, f, i2));
    }

    public void setViewLayoutWidthDimen(int i, int i2) {
        addAction(new android.widget.RemoteViews.LayoutParamAction(i, 8, i2, 3));
    }

    public void setViewLayoutWidthAttr(int i, int i2) {
        addAction(new android.widget.RemoteViews.LayoutParamAction(i, 8, i2, 4));
    }

    public void setViewLayoutHeight(int i, float f, int i2) {
        addAction(new android.widget.RemoteViews.LayoutParamAction(i, 9, f, i2));
    }

    public void setViewLayoutHeightDimen(int i, int i2) {
        addAction(new android.widget.RemoteViews.LayoutParamAction(i, 9, i2, 3));
    }

    public void setViewLayoutHeightAttr(int i, int i2) {
        addAction(new android.widget.RemoteViews.LayoutParamAction(i, 9, i2, 4));
    }

    public void setViewOutlinePreferredRadius(int i, float f, int i2) {
        addAction(new android.widget.RemoteViews.SetViewOutlinePreferredRadiusAction(i, f, i2));
    }

    public void setViewOutlinePreferredRadiusDimen(int i, int i2) {
        addAction(new android.widget.RemoteViews.SetViewOutlinePreferredRadiusAction(i, i2, 3));
    }

    public void setViewOutlinePreferredRadiusAttr(int i, int i2) {
        addAction(new android.widget.RemoteViews.SetViewOutlinePreferredRadiusAction(i, i2, 4));
    }

    public void setBoolean(int i, java.lang.String str, boolean z) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 1, java.lang.Boolean.valueOf(z)));
    }

    public void setByte(int i, java.lang.String str, byte b) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 2, java.lang.Byte.valueOf(b)));
    }

    public void setShort(int i, java.lang.String str, short s) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 3, java.lang.Short.valueOf(s)));
    }

    public void setInt(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 4, java.lang.Integer.valueOf(i2)));
    }

    public void setIntDimen(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.ResourceReflectionAction(i, str, 4, 1, i2));
    }

    public void setIntDimen(int i, java.lang.String str, float f, int i2) {
        addAction(new android.widget.RemoteViews.ComplexUnitDimensionReflectionAction(i, str, 4, f, i2));
    }

    public void setIntDimenAttr(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.AttributeReflectionAction(i, str, 4, 1, i2));
    }

    public void setColor(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.ResourceReflectionAction(i, str, 4, 2, i2));
    }

    public void setColorAttr(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.AttributeReflectionAction(i, str, 4, 2, i2));
    }

    public void setColorInt(int i, java.lang.String str, int i2, int i3) {
        addAction(new android.widget.RemoteViews.NightModeReflectionAction(i, str, 4, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)));
    }

    public void setColorStateList(int i, java.lang.String str, android.content.res.ColorStateList colorStateList) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 15, colorStateList));
    }

    public void setColorStateList(int i, java.lang.String str, android.content.res.ColorStateList colorStateList, android.content.res.ColorStateList colorStateList2) {
        addAction(new android.widget.RemoteViews.NightModeReflectionAction(i, str, 15, colorStateList, colorStateList2));
    }

    public void setColorStateList(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.ResourceReflectionAction(i, str, 15, 2, i2));
    }

    public void setColorStateListAttr(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.AttributeReflectionAction(i, str, 15, 2, i2));
    }

    public void setLong(int i, java.lang.String str, long j) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 5, java.lang.Long.valueOf(j)));
    }

    public void setFloat(int i, java.lang.String str, float f) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 6, java.lang.Float.valueOf(f)));
    }

    public void setFloatDimen(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.ResourceReflectionAction(i, str, 6, 1, i2));
    }

    public void setFloatDimen(int i, java.lang.String str, float f, int i2) {
        addAction(new android.widget.RemoteViews.ComplexUnitDimensionReflectionAction(i, str, 6, f, i2));
    }

    public void setFloatDimenAttr(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.AttributeReflectionAction(i, str, 6, 1, i2));
    }

    public void setDouble(int i, java.lang.String str, double d) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 7, java.lang.Double.valueOf(d)));
    }

    public void setChar(int i, java.lang.String str, char c) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 8, java.lang.Character.valueOf(c)));
    }

    public void setString(int i, java.lang.String str, java.lang.String str2) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 9, str2));
    }

    public void setCharSequence(int i, java.lang.String str, java.lang.CharSequence charSequence) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 10, charSequence));
    }

    public void setCharSequence(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.ResourceReflectionAction(i, str, 10, 3, i2));
    }

    public void setCharSequenceAttr(int i, java.lang.String str, int i2) {
        addAction(new android.widget.RemoteViews.AttributeReflectionAction(i, str, 10, 3, i2));
    }

    public void setUri(int i, java.lang.String str, android.net.Uri uri) {
        if (uri != null) {
            uri = uri.getCanonicalUri();
            if (android.os.StrictMode.vmFileUriExposureEnabled()) {
                uri.checkFileUriExposed("RemoteViews.setUri()");
            }
        }
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 11, uri));
    }

    public void setBitmap(int i, java.lang.String str, android.graphics.Bitmap bitmap) {
        addAction(new android.widget.RemoteViews.BitmapReflectionAction(i, str, bitmap));
    }

    public void setBlendMode(int i, java.lang.String str, android.graphics.BlendMode blendMode) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 17, blendMode));
    }

    public void setBundle(int i, java.lang.String str, android.os.Bundle bundle) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 13, bundle));
    }

    public void setIntent(int i, java.lang.String str, android.content.Intent intent) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 14, intent));
    }

    public void setIcon(int i, java.lang.String str, android.graphics.drawable.Icon icon) {
        addAction(new android.widget.RemoteViews.ReflectionAction(i, str, 16, icon));
    }

    public void setIcon(int i, java.lang.String str, android.graphics.drawable.Icon icon, android.graphics.drawable.Icon icon2) {
        addAction(new android.widget.RemoteViews.NightModeReflectionAction(i, str, 16, icon, icon2));
    }

    public void setContentDescription(int i, java.lang.CharSequence charSequence) {
        setCharSequence(i, "setContentDescription", charSequence);
    }

    public void setAccessibilityTraversalBefore(int i, int i2) {
        setInt(i, "setAccessibilityTraversalBefore", i2);
    }

    public void setAccessibilityTraversalAfter(int i, int i2) {
        setInt(i, "setAccessibilityTraversalAfter", i2);
    }

    public void setLabelFor(int i, int i2) {
        setInt(i, "setLabelFor", i2);
    }

    public void setCompoundButtonChecked(int i, boolean z) {
        addAction(new android.widget.RemoteViews.SetCompoundButtonCheckedAction(i, z));
    }

    public void setRadioGroupChecked(int i, int i2) {
        addAction(new android.widget.RemoteViews.SetRadioGroupCheckedAction(i, i2));
    }

    public void setLightBackgroundLayoutId(int i) {
        this.mLightBackgroundLayoutId = i;
    }

    public android.widget.RemoteViews getDarkTextViews() {
        if (hasFlags(4)) {
            return this;
        }
        try {
            addFlags(4);
            return new android.widget.RemoteViews(this);
        } finally {
            this.mApplyFlags &= -5;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasDrawInstructions() {
        return this.mHasDrawInstructions;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.widget.RemoteViews getRemoteViewsToApply(android.content.Context context) {
        if (hasLandscapeAndPortraitLayouts()) {
            if (context.getResources().getConfiguration().orientation == 2) {
                return this.mLandscape;
            }
            return this.mPortrait;
        }
        if (hasSizedRemoteViews()) {
            return findSmallestRemoteView();
        }
        return this;
    }

    private static float squareDistance(android.util.SizeF sizeF, android.util.SizeF sizeF2) {
        float width = sizeF.getWidth() - sizeF2.getWidth();
        float height = sizeF.getHeight() - sizeF2.getHeight();
        return (width * width) + (height * height);
    }

    private static boolean fitsIn(android.util.SizeF sizeF, android.util.SizeF sizeF2) {
        return sizeF2 != null && java.lang.Math.ceil((double) sizeF2.getWidth()) + 1.0d > ((double) sizeF.getWidth()) && java.lang.Math.ceil((double) sizeF2.getHeight()) + 1.0d > ((double) sizeF.getHeight());
    }

    private android.widget.RemoteViews findBestFitLayout(android.util.SizeF sizeF) {
        android.widget.RemoteViews remoteViews = null;
        float f = Float.MAX_VALUE;
        for (android.widget.RemoteViews remoteViews2 : this.mSizedRemoteViews) {
            android.util.SizeF idealSize = remoteViews2.getIdealSize();
            if (idealSize == null) {
                throw new java.lang.IllegalStateException("Expected RemoteViews to have ideal size");
            }
            if (fitsIn(idealSize, sizeF)) {
                if (remoteViews == null) {
                    f = squareDistance(idealSize, sizeF);
                    remoteViews = remoteViews2;
                } else {
                    float squareDistance = squareDistance(idealSize, sizeF);
                    if (squareDistance < f) {
                        remoteViews = remoteViews2;
                        f = squareDistance;
                    }
                }
            }
        }
        if (remoteViews == null) {
            android.util.Log.w(LOG_TAG, "Could not find a RemoteViews fitting the current size: " + sizeF);
            return findSmallestRemoteView();
        }
        return remoteViews;
    }

    public android.widget.RemoteViews getRemoteViewsToApply(android.content.Context context, android.util.SizeF sizeF) {
        if (!hasSizedRemoteViews() || sizeF == null) {
            return getRemoteViewsToApply(context);
        }
        return findBestFitLayout(sizeF);
    }

    public android.widget.RemoteViews getRemoteViewsToApplyIfDifferent(android.util.SizeF sizeF, android.util.SizeF sizeF2) {
        if (!hasSizedRemoteViews()) {
            return null;
        }
        android.widget.RemoteViews findSmallestRemoteView = sizeF == null ? findSmallestRemoteView() : findBestFitLayout(sizeF);
        android.widget.RemoteViews findBestFitLayout = findBestFitLayout(sizeF2);
        if (findSmallestRemoteView != findBestFitLayout) {
            return findBestFitLayout;
        }
        return null;
    }

    public android.view.View apply(android.content.Context context, android.view.ViewGroup viewGroup) {
        return apply(context, viewGroup, null);
    }

    public android.view.View apply(android.content.Context context, android.view.ViewGroup viewGroup, android.widget.RemoteViews.InteractionHandler interactionHandler) {
        return apply(context, viewGroup, interactionHandler, (android.util.SizeF) null);
    }

    public android.view.View apply(android.content.Context context, android.view.ViewGroup viewGroup, android.widget.RemoteViews.InteractionHandler interactionHandler, android.util.SizeF sizeF) {
        return apply(context, viewGroup, sizeF, new android.widget.RemoteViews.ActionApplyParams().withInteractionHandler(interactionHandler));
    }

    public android.view.View applyWithTheme(android.content.Context context, android.view.ViewGroup viewGroup, android.widget.RemoteViews.InteractionHandler interactionHandler, int i) {
        return apply(context, viewGroup, (android.util.SizeF) null, new android.widget.RemoteViews.ActionApplyParams().withInteractionHandler(interactionHandler).withThemeResId(i));
    }

    public android.view.View apply(android.content.Context context, android.view.ViewGroup viewGroup, android.widget.RemoteViews.InteractionHandler interactionHandler, android.util.SizeF sizeF, android.widget.RemoteViews.ColorResources colorResources) {
        return apply(context, viewGroup, sizeF, new android.widget.RemoteViews.ActionApplyParams().withInteractionHandler(interactionHandler).withColorResources(colorResources));
    }

    public android.view.View apply(android.content.Context context, android.view.ViewGroup viewGroup, android.util.SizeF sizeF, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
        return apply(context, viewGroup, viewGroup, sizeF, actionApplyParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.View apply(android.content.Context context, android.view.ViewGroup viewGroup, android.view.ViewGroup viewGroup2, android.util.SizeF sizeF, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
        android.widget.RemoteViews remoteViewsToApply = getRemoteViewsToApply(context, sizeF);
        android.view.View inflateView = inflateView(context, remoteViewsToApply, viewGroup, actionApplyParams.applyThemeResId, actionApplyParams.colorResources);
        remoteViewsToApply.performApply(inflateView, viewGroup2, actionApplyParams);
        return inflateView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.View inflateView(android.content.Context context, android.widget.RemoteViews remoteViews, android.view.ViewGroup viewGroup, int i, android.widget.RemoteViews.ColorResources colorResources) {
        android.view.View view;
        android.content.Context contextForResourcesEnsuringCorrectCachedApkPaths = getContextForResourcesEnsuringCorrectCachedApkPaths(context);
        if (colorResources != null) {
            colorResources.apply(contextForResourcesEnsuringCorrectCachedApkPaths);
        }
        android.content.Context remoteViewsContextWrapper = new android.widget.RemoteViews.RemoteViewsContextWrapper(context, contextForResourcesEnsuringCorrectCachedApkPaths);
        if (i != 0) {
            remoteViewsContextWrapper = new android.view.ContextThemeWrapper(remoteViewsContextWrapper, i);
        }
        if (remoteViews.hasDrawInstructions()) {
            com.android.internal.widget.remotecompose.player.RemoteComposePlayer remoteComposePlayer = new com.android.internal.widget.remotecompose.player.RemoteComposePlayer(remoteViewsContextWrapper);
            remoteComposePlayer.setDebug((android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG) ? 1 : 0);
            view = remoteComposePlayer;
        } else {
            android.view.LayoutInflater cloneInContext = android.view.LayoutInflater.from(context).cloneInContext(remoteViewsContextWrapper);
            cloneInContext.setFilter(shouldUseStaticFilter() ? INFLATER_FILTER : this);
            if (this.mLayoutInflaterFactory2 != null) {
                cloneInContext.setFactory2(this.mLayoutInflaterFactory2);
            }
            view = cloneInContext.inflate(remoteViews.getLayoutId(), viewGroup, false);
        }
        if (this.mViewId != -1) {
            view.setId(this.mViewId);
            view.setTagInternal(com.android.internal.R.id.remote_views_override_id, java.lang.Integer.valueOf(this.mViewId));
        }
        view.setTagInternal(16908312, java.lang.Integer.valueOf(remoteViews.getLayoutId()));
        return view;
    }

    protected boolean shouldUseStaticFilter() {
        return getClass().equals(android.widget.RemoteViews.class);
    }

    public interface OnViewAppliedListener {
        void onError(java.lang.Exception exc);

        void onViewApplied(android.view.View view);

        default void onViewInflated(android.view.View view) {
        }
    }

    public android.os.CancellationSignal applyAsync(android.content.Context context, android.view.ViewGroup viewGroup, java.util.concurrent.Executor executor, android.widget.RemoteViews.OnViewAppliedListener onViewAppliedListener) {
        return applyAsync(context, viewGroup, executor, onViewAppliedListener, null);
    }

    public android.os.CancellationSignal applyAsync(android.content.Context context, android.view.ViewGroup viewGroup, java.util.concurrent.Executor executor, android.widget.RemoteViews.OnViewAppliedListener onViewAppliedListener, android.widget.RemoteViews.InteractionHandler interactionHandler) {
        return applyAsync(context, viewGroup, executor, onViewAppliedListener, interactionHandler, null);
    }

    public android.os.CancellationSignal applyAsync(android.content.Context context, android.view.ViewGroup viewGroup, java.util.concurrent.Executor executor, android.widget.RemoteViews.OnViewAppliedListener onViewAppliedListener, android.widget.RemoteViews.InteractionHandler interactionHandler, android.util.SizeF sizeF) {
        return applyAsync(context, viewGroup, executor, onViewAppliedListener, interactionHandler, sizeF, null);
    }

    public android.os.CancellationSignal applyAsync(android.content.Context context, android.view.ViewGroup viewGroup, java.util.concurrent.Executor executor, android.widget.RemoteViews.OnViewAppliedListener onViewAppliedListener, android.widget.RemoteViews.InteractionHandler interactionHandler, android.util.SizeF sizeF, android.widget.RemoteViews.ColorResources colorResources) {
        return new android.widget.RemoteViews.AsyncApplyTask(getRemoteViewsToApply(context, sizeF), viewGroup, context, onViewAppliedListener, new android.widget.RemoteViews.ActionApplyParams().withInteractionHandler(interactionHandler).withColorResources(colorResources).withExecutor(executor), null, true).startTaskOnExecutor(executor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.widget.RemoteViews.AsyncApplyTask getInternalAsyncApplyTask(android.content.Context context, android.view.ViewGroup viewGroup, android.widget.RemoteViews.OnViewAppliedListener onViewAppliedListener, android.widget.RemoteViews.ActionApplyParams actionApplyParams, android.util.SizeF sizeF, android.view.View view) {
        return new android.widget.RemoteViews.AsyncApplyTask(getRemoteViewsToApply(context, sizeF), viewGroup, context, onViewAppliedListener, actionApplyParams, view, false);
    }

    private class AsyncApplyTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, android.widget.RemoteViews.ViewTree> implements android.os.CancellationSignal.OnCancelListener {
        private android.widget.RemoteViews.Action[] mActions;
        final android.widget.RemoteViews.ActionApplyParams mApplyParams;
        final android.os.CancellationSignal mCancelSignal;
        final android.content.Context mContext;
        private java.lang.Exception mError;
        final android.widget.RemoteViews.OnViewAppliedListener mListener;
        final android.view.ViewGroup mParent;
        final android.widget.RemoteViews mRV;
        private android.view.View mResult;
        final boolean mTopLevel;
        private android.widget.RemoteViews.ViewTree mTree;

        private AsyncApplyTask(android.widget.RemoteViews remoteViews, android.view.ViewGroup viewGroup, android.content.Context context, android.widget.RemoteViews.OnViewAppliedListener onViewAppliedListener, android.widget.RemoteViews.ActionApplyParams actionApplyParams, android.view.View view, boolean z) {
            this.mCancelSignal = new android.os.CancellationSignal();
            this.mRV = remoteViews;
            this.mParent = viewGroup;
            this.mContext = context;
            this.mListener = onViewAppliedListener;
            this.mTopLevel = z;
            this.mApplyParams = actionApplyParams;
            this.mResult = view;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public android.widget.RemoteViews.ViewTree doInBackground(java.lang.Void... voidArr) {
            try {
                if (this.mResult == null) {
                    this.mResult = android.widget.RemoteViews.this.inflateView(this.mContext, this.mRV, this.mParent, 0, this.mApplyParams.colorResources);
                }
                this.mTree = new android.widget.RemoteViews.ViewTree(this.mResult);
                if (this.mRV.mActions != null) {
                    int size = this.mRV.mActions.size();
                    this.mActions = new android.widget.RemoteViews.Action[size];
                    for (int i = 0; i < size && !isCancelled(); i++) {
                        this.mActions[i] = ((android.widget.RemoteViews.Action) this.mRV.mActions.get(i)).initActionAsync(this.mTree, this.mParent, this.mApplyParams);
                    }
                } else {
                    this.mActions = null;
                }
                return this.mTree;
            } catch (java.lang.Exception e) {
                this.mError = e;
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(android.widget.RemoteViews.ViewTree viewTree) {
            this.mCancelSignal.setOnCancelListener(null);
            if (this.mError == null) {
                if (this.mListener != null) {
                    this.mListener.onViewInflated(viewTree.mRoot);
                }
                try {
                    if (this.mActions != null) {
                        android.widget.RemoteViews.ActionApplyParams m6208clone = this.mApplyParams.m6208clone();
                        if (m6208clone.handler == null) {
                            m6208clone.handler = android.widget.RemoteViews.DEFAULT_INTERACTION_HANDLER;
                        }
                        for (android.widget.RemoteViews.Action action : this.mActions) {
                            action.apply(viewTree.mRoot, this.mParent, m6208clone);
                        }
                    }
                    if (this.mTopLevel && (this.mResult instanceof android.view.ViewGroup)) {
                        android.widget.RemoteViews.this.finalizeViewRecycling((android.view.ViewGroup) this.mResult);
                    }
                } catch (java.lang.Exception e) {
                    this.mError = e;
                }
            }
            if (this.mListener != null) {
                if (this.mError != null) {
                    this.mListener.onError(this.mError);
                    return;
                } else {
                    this.mListener.onViewApplied(viewTree.mRoot);
                    return;
                }
            }
            if (this.mError != null) {
                if (this.mError instanceof android.widget.RemoteViews.ActionException) {
                    throw ((android.widget.RemoteViews.ActionException) this.mError);
                }
                throw new android.widget.RemoteViews.ActionException(this.mError);
            }
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            cancel(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.os.CancellationSignal startTaskOnExecutor(java.util.concurrent.Executor executor) {
            this.mCancelSignal.setOnCancelListener(this);
            if (executor == null) {
                executor = android.os.AsyncTask.THREAD_POOL_EXECUTOR;
            }
            executeOnExecutor(executor, new java.lang.Void[0]);
            return this.mCancelSignal;
        }
    }

    public void reapply(android.content.Context context, android.view.View view) {
        reapply(context, view, null, new android.widget.RemoteViews.ActionApplyParams());
    }

    public void reapply(android.content.Context context, android.view.View view, android.widget.RemoteViews.InteractionHandler interactionHandler) {
        reapply(context, view, null, new android.widget.RemoteViews.ActionApplyParams().withInteractionHandler(interactionHandler));
    }

    public void reapply(android.content.Context context, android.view.View view, android.widget.RemoteViews.InteractionHandler interactionHandler, android.util.SizeF sizeF, android.widget.RemoteViews.ColorResources colorResources) {
        reapply(context, view, sizeF, new android.widget.RemoteViews.ActionApplyParams().withInteractionHandler(interactionHandler).withColorResources(colorResources));
    }

    public void reapply(android.content.Context context, android.view.View view, android.util.SizeF sizeF, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
        reapply(context, view, (android.view.ViewGroup) view.getParent(), sizeF, actionApplyParams, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reapplyNestedViews(android.content.Context context, android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
        reapply(context, view, viewGroup, null, actionApplyParams, false);
    }

    private void reapply(android.content.Context context, android.view.View view, android.view.ViewGroup viewGroup, android.util.SizeF sizeF, android.widget.RemoteViews.ActionApplyParams actionApplyParams, boolean z) {
        getRemoteViewsToReapply(context, view, sizeF).performApply(view, viewGroup, actionApplyParams);
        if (z && (view instanceof android.view.ViewGroup)) {
            finalizeViewRecycling((android.view.ViewGroup) view);
        }
    }

    public boolean canRecycleView(android.view.View view) {
        java.lang.Integer num;
        if (view == null || hasDrawInstructions() || (num = (java.lang.Integer) view.getTag(16908312)) == null) {
            return false;
        }
        java.lang.Integer num2 = (java.lang.Integer) view.getTag(com.android.internal.R.id.remote_views_override_id);
        return num.intValue() == getLayoutId() && this.mViewId == (num2 == null ? -1 : num2.intValue());
    }

    private android.widget.RemoteViews getRemoteViewsToReapply(android.content.Context context, android.view.View view, android.util.SizeF sizeF) {
        android.widget.RemoteViews remoteViewsToApply = getRemoteViewsToApply(context, sizeF);
        if ((hasMultipleLayouts() || remoteViewsToApply.mViewId != -1 || view.getTag(com.android.internal.R.id.remote_views_override_id) != null) && !remoteViewsToApply.canRecycleView(view)) {
            throw new java.lang.RuntimeException("Attempting to re-apply RemoteViews to a view that that does not share the same root layout id.");
        }
        return remoteViewsToApply;
    }

    public android.os.CancellationSignal reapplyAsync(android.content.Context context, android.view.View view, java.util.concurrent.Executor executor, android.widget.RemoteViews.OnViewAppliedListener onViewAppliedListener) {
        return reapplyAsync(context, view, executor, onViewAppliedListener, null);
    }

    public android.os.CancellationSignal reapplyAsync(android.content.Context context, android.view.View view, java.util.concurrent.Executor executor, android.widget.RemoteViews.OnViewAppliedListener onViewAppliedListener, android.widget.RemoteViews.InteractionHandler interactionHandler) {
        return reapplyAsync(context, view, executor, onViewAppliedListener, interactionHandler, null, null);
    }

    public android.os.CancellationSignal reapplyAsync(android.content.Context context, android.view.View view, java.util.concurrent.Executor executor, android.widget.RemoteViews.OnViewAppliedListener onViewAppliedListener, android.widget.RemoteViews.InteractionHandler interactionHandler, android.util.SizeF sizeF, android.widget.RemoteViews.ColorResources colorResources) {
        return new android.widget.RemoteViews.AsyncApplyTask(getRemoteViewsToReapply(context, view, sizeF), (android.view.ViewGroup) view.getParent(), context, onViewAppliedListener, new android.widget.RemoteViews.ActionApplyParams().withColorResources(colorResources).withInteractionHandler(interactionHandler).withExecutor(executor), view, true).startTaskOnExecutor(executor);
    }

    private void performApply(android.view.View view, android.view.ViewGroup viewGroup, android.widget.RemoteViews.ActionApplyParams actionApplyParams) {
        android.widget.RemoteViews.ActionApplyParams m6208clone = actionApplyParams.m6208clone();
        if (m6208clone.handler == null) {
            m6208clone.handler = DEFAULT_INTERACTION_HANDLER;
        }
        if (view instanceof com.android.internal.widget.remotecompose.player.RemoteComposePlayer) {
            ((com.android.internal.widget.remotecompose.player.RemoteComposePlayer) view).setTheme(view.getResources().getConfiguration().isNightModeActive() ? -2 : -3);
        }
        if (this.mActions != null) {
            int size = this.mActions.size();
            for (int i = 0; i < size; i++) {
                this.mActions.get(i).apply(view, viewGroup, m6208clone);
            }
        }
    }

    public boolean prefersAsyncApply() {
        if (this.mActions != null) {
            int size = this.mActions.size();
            for (int i = 0; i < size; i++) {
                if (this.mActions.get(i).prefersAsyncApply()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateAppInfo(android.content.pm.ApplicationInfo applicationInfo) {
        android.content.pm.ApplicationInfo applicationInfo2 = this.mApplicationInfoCache.get(applicationInfo);
        if (applicationInfo2 != null && !applicationInfo2.sourceDir.equals(applicationInfo.sourceDir)) {
            return;
        }
        this.mApplicationInfoCache.put(applicationInfo);
        configureDescendantsAsChildren();
    }

    private android.content.Context getContextForResourcesEnsuringCorrectCachedApkPaths(android.content.Context context) {
        if (this.mApplication != null) {
            if (context.getUserId() == android.os.UserHandle.getUserId(this.mApplication.uid) && context.getPackageName().equals(this.mApplication.packageName)) {
                return context;
            }
            try {
                android.app.LoadedApk.checkAndUpdateApkPaths(this.mApplication);
                return context.createApplicationContext(this.mApplication, 4);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.e(LOG_TAG, "Package name " + this.mApplication.packageName + " not found");
            }
        }
        return context;
    }

    private android.util.SparseArray<android.app.PendingIntent> getPendingIntentTemplate() {
        if (this.mPendingIntentTemplate == null) {
            this.mPendingIntentTemplate = new android.util.SparseArray<>();
        }
        return this.mPendingIntentTemplate;
    }

    private android.util.SparseArray<android.content.Intent> getFillInIntent() {
        if (this.mFillInIntent == null) {
            this.mFillInIntent = new android.util.SparseArray<>();
        }
        return this.mFillInIntent;
    }

    private void tryAddRemoteResponse(int i) {
        android.app.PendingIntent pendingIntent = getPendingIntentTemplate().get(i);
        android.content.Intent intent = getFillInIntent().get(i);
        if (pendingIntent != null && intent != null) {
            addAction(new android.widget.RemoteViews.SetOnClickResponse(i, android.widget.RemoteViews.RemoteResponse.fromPendingIntentTemplateAndFillInIntent(pendingIntent, intent)));
        }
    }

    public class ActionApplyParams {
        public int applyThemeResId;
        public android.widget.RemoteViews.ColorResources colorResources;
        public java.util.concurrent.Executor executor;
        public android.widget.RemoteViews.InteractionHandler handler;

        public ActionApplyParams() {
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public android.widget.RemoteViews.ActionApplyParams m6208clone() {
            return android.widget.RemoteViews.this.new ActionApplyParams().withInteractionHandler(this.handler).withColorResources(this.colorResources).withExecutor(this.executor).withThemeResId(this.applyThemeResId);
        }

        public android.widget.RemoteViews.ActionApplyParams withInteractionHandler(android.widget.RemoteViews.InteractionHandler interactionHandler) {
            this.handler = interactionHandler;
            return this;
        }

        public android.widget.RemoteViews.ActionApplyParams withColorResources(android.widget.RemoteViews.ColorResources colorResources) {
            this.colorResources = colorResources;
            return this;
        }

        public android.widget.RemoteViews.ActionApplyParams withThemeResId(int i) {
            this.applyThemeResId = i;
            return this;
        }

        public android.widget.RemoteViews.ActionApplyParams withExecutor(java.util.concurrent.Executor executor) {
            this.executor = executor;
            return this;
        }
    }

    public static final class ColorResources {
        private static final int ARSC_ENTRY_SIZE = 16;
        private static final int FIRST_RESOURCE_COLOR_ID = 17170461;
        private static final int LAST_RESOURCE_COLOR_ID = 17170525;
        private final android.util.SparseIntArray mColorMapping;
        private final android.content.res.loader.ResourcesLoader mLoader;

        private ColorResources(android.content.res.loader.ResourcesLoader resourcesLoader, android.util.SparseIntArray sparseIntArray) {
            this.mLoader = resourcesLoader;
            this.mColorMapping = sparseIntArray;
        }

        public void apply(android.content.Context context) {
            context.getResources().addLoaders(this.mLoader);
        }

        public android.util.SparseIntArray getColorMapping() {
            return this.mColorMapping;
        }

        private static java.io.ByteArrayOutputStream readFileContent(java.io.InputStream inputStream) throws java.io.IOException {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(2048);
            byte[] bArr = new byte[4096];
            while (inputStream.available() > 0) {
                byteArrayOutputStream.write(bArr, 0, inputStream.read(bArr));
            }
            return byteArrayOutputStream;
        }

        private static byte[] createCompiledResourcesContent(android.content.Context context, android.util.SparseIntArray sparseIntArray) throws java.io.IOException {
            java.io.InputStream openRawResource = context.getResources().openRawResource(com.android.internal.R.raw.remote_views_color_resources);
            try {
                byte[] byteArray = readFileContent(openRawResource).toByteArray();
                if (openRawResource != null) {
                    openRawResource.close();
                }
                int length = (byteArray.length - 1488) - 4;
                if (length < 0) {
                    android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "ARSC file for theme colors is invalid.");
                    return null;
                }
                for (int i = 17170461; i <= 17170525; i++) {
                    int i2 = ((65535 & i) * 16) + length;
                    int i3 = sparseIntArray.get(i, context.getColor(i));
                    for (int i4 = 0; i4 < 4; i4++) {
                        byteArray[i2 + i4] = (byte) (i3 & 255);
                        i3 >>= 8;
                    }
                }
                return byteArray;
            } catch (java.lang.Throwable th) {
                if (openRawResource != null) {
                    try {
                        openRawResource.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public static android.widget.RemoteViews.ColorResources create(android.content.Context context, android.util.SparseIntArray sparseIntArray) {
            java.io.FileDescriptor fileDescriptor;
            try {
                byte[] createCompiledResourcesContent = createCompiledResourcesContent(context, sparseIntArray);
                if (createCompiledResourcesContent == null) {
                    return null;
                }
                try {
                    fileDescriptor = android.system.Os.memfd_create("remote_views_theme_colors.arsc", 0);
                } catch (java.lang.Throwable th) {
                    th = th;
                    fileDescriptor = null;
                }
                try {
                    java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(fileDescriptor);
                    try {
                        fileOutputStream.write(createCompiledResourcesContent);
                        android.os.ParcelFileDescriptor dup = android.os.ParcelFileDescriptor.dup(fileDescriptor);
                        try {
                            android.content.res.loader.ResourcesLoader resourcesLoader = new android.content.res.loader.ResourcesLoader();
                            resourcesLoader.addProvider(android.content.res.loader.ResourcesProvider.loadFromTable(dup, null));
                            android.widget.RemoteViews.ColorResources colorResources = new android.widget.RemoteViews.ColorResources(resourcesLoader, sparseIntArray.m4838clone());
                            if (dup != null) {
                                dup.close();
                            }
                            fileOutputStream.close();
                            if (fileDescriptor != null) {
                                android.system.Os.close(fileDescriptor);
                            }
                            return colorResources;
                        } finally {
                        }
                    } finally {
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    if (fileDescriptor != null) {
                        android.system.Os.close(fileDescriptor);
                    }
                    throw th;
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Failed to setup the context for theme colors", e);
                return null;
            }
        }
    }

    public int getSequenceNumber() {
        if (this.mActions == null) {
            return 0;
        }
        return this.mActions.size();
    }

    @Override // android.view.LayoutInflater.Filter
    @java.lang.Deprecated
    public boolean onLoadClass(java.lang.Class cls) {
        return cls.isAnnotationPresent(android.widget.RemoteViews.RemoteView.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        boolean allowSquashing = parcel.allowSquashing();
        if (!hasMultipleLayouts()) {
            parcel.writeInt(0);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(parcel, i);
                this.mCollectionCache.writeToParcel(parcel, i);
            }
            this.mApplication.writeToParcel(parcel, i);
            if (this.mIsRoot || this.mIdealSize == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                this.mIdealSize.writeToParcel(parcel, i);
            }
            parcel.writeInt(this.mLayoutId);
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mLightBackgroundLayoutId);
            writeActionsToParcel(parcel, i);
        } else if (hasSizedRemoteViews()) {
            parcel.writeInt(2);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(parcel, i);
                this.mCollectionCache.writeToParcel(parcel, i);
            }
            parcel.writeInt(this.mSizedRemoteViews.size());
            java.util.Iterator<android.widget.RemoteViews> it = this.mSizedRemoteViews.iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(parcel, i);
            }
        } else {
            parcel.writeInt(1);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(parcel, i);
                this.mCollectionCache.writeToParcel(parcel, i);
            }
            this.mLandscape.writeToParcel(parcel, i);
            this.mPortrait.writeToParcel(parcel, i);
        }
        parcel.writeInt(this.mApplyFlags);
        parcel.writeLong(this.mProviderInstanceId);
        parcel.writeBoolean(this.mHasDrawInstructions);
        parcel.restoreAllowSquashing(allowSquashing);
    }

    private void writeActionsToParcel(android.os.Parcel parcel, int i) {
        int i2;
        if (this.mActions != null) {
            i2 = this.mActions.size();
        } else {
            i2 = 0;
        }
        parcel.writeInt(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            android.widget.RemoteViews.Action action = this.mActions.get(i3);
            parcel.writeInt(action.getActionTag());
            action.writeToParcel(parcel, i);
        }
    }

    private static android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, int i) {
        if (str == null) {
            return null;
        }
        android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
        if (currentApplication == null) {
            throw new java.lang.IllegalStateException("Cannot create remote views out of an aplication.");
        }
        android.content.pm.ApplicationInfo applicationInfo = currentApplication.getApplicationInfo();
        if (android.os.UserHandle.getUserId(applicationInfo.uid) != i || !applicationInfo.packageName.equals(str)) {
            try {
                return currentApplication.getBaseContext().createPackageContextAsUser(str, 0, new android.os.UserHandle(i)).getApplicationInfo();
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new java.lang.IllegalArgumentException("No such package " + str);
            }
        }
        return applicationInfo;
    }

    public boolean hasSameAppInfo(android.content.pm.ApplicationInfo applicationInfo) {
        return this.mApplication.packageName.equals(applicationInfo.packageName) && this.mApplication.uid == applicationInfo.uid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ViewTree {
        private static final int INSERT_AT_END_INDEX = -1;
        private java.util.ArrayList<android.widget.RemoteViews.ViewTree> mChildren;
        private android.view.View mRoot;

        private ViewTree(android.view.View view) {
            this.mRoot = view;
        }

        public void createTree() {
            if (this.mChildren != null) {
                return;
            }
            this.mChildren = new java.util.ArrayList<>();
            if (this.mRoot instanceof android.view.ViewGroup) {
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mRoot;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    addViewChild(viewGroup.getChildAt(i));
                }
            }
        }

        public android.widget.RemoteViews.ViewTree findViewTreeById(int i) {
            if (this.mRoot.getId() == i) {
                return this;
            }
            if (this.mChildren == null) {
                return null;
            }
            java.util.Iterator<android.widget.RemoteViews.ViewTree> it = this.mChildren.iterator();
            while (it.hasNext()) {
                android.widget.RemoteViews.ViewTree findViewTreeById = it.next().findViewTreeById(i);
                if (findViewTreeById != null) {
                    return findViewTreeById;
                }
            }
            return null;
        }

        public android.widget.RemoteViews.ViewTree findViewTreeParentOf(android.widget.RemoteViews.ViewTree viewTree) {
            if (this.mChildren == null) {
                return null;
            }
            java.util.Iterator<android.widget.RemoteViews.ViewTree> it = this.mChildren.iterator();
            while (it.hasNext()) {
                android.widget.RemoteViews.ViewTree next = it.next();
                if (next == viewTree) {
                    return this;
                }
                android.widget.RemoteViews.ViewTree findViewTreeParentOf = next.findViewTreeParentOf(viewTree);
                if (findViewTreeParentOf != null) {
                    return findViewTreeParentOf;
                }
            }
            return null;
        }

        public void replaceView(android.view.View view) {
            this.mRoot = view;
            this.mChildren = null;
            createTree();
        }

        public <T extends android.view.View> T findViewById(int i) {
            if (this.mChildren == null) {
                return (T) this.mRoot.findViewById(i);
            }
            android.widget.RemoteViews.ViewTree findViewTreeById = findViewTreeById(i);
            if (findViewTreeById == null) {
                return null;
            }
            return (T) findViewTreeById.mRoot;
        }

        public void addChild(android.widget.RemoteViews.ViewTree viewTree) {
            addChild(viewTree, -1);
        }

        public void addChild(android.widget.RemoteViews.ViewTree viewTree, int i) {
            if (this.mChildren == null) {
                this.mChildren = new java.util.ArrayList<>();
            }
            viewTree.createTree();
            if (i == -1) {
                this.mChildren.add(viewTree);
            } else {
                this.mChildren.add(i, viewTree);
            }
        }

        public void removeChildren(int i, int i2) {
            if (this.mChildren != null) {
                for (int i3 = 0; i3 < i2; i3++) {
                    this.mChildren.remove(i);
                }
            }
        }

        private void addViewChild(android.view.View view) {
            android.widget.RemoteViews.ViewTree viewTree;
            if (view.isRootNamespace()) {
                return;
            }
            if (view.getId() != 0) {
                viewTree = new android.widget.RemoteViews.ViewTree(view);
                this.mChildren.add(viewTree);
            } else {
                viewTree = this;
            }
            if ((view instanceof android.view.ViewGroup) && viewTree.mChildren == null) {
                viewTree.mChildren = new java.util.ArrayList<>();
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    viewTree.addViewChild(viewGroup.getChildAt(i));
                }
            }
        }

        public int findChildIndex(java.util.function.Predicate<android.view.View> predicate) {
            return findChildIndex(0, predicate);
        }

        public int findChildIndex(int i, java.util.function.Predicate<android.view.View> predicate) {
            if (this.mChildren == null) {
                return -1;
            }
            while (i < this.mChildren.size()) {
                if (!predicate.test(this.mChildren.get(i).mRoot)) {
                    i++;
                } else {
                    return i;
                }
            }
            return -1;
        }
    }

    public static class RemoteResponse {
        public static final int INTERACTION_TYPE_CHECKED_CHANGE = 1;
        public static final int INTERACTION_TYPE_CLICK = 0;
        private java.util.ArrayList<java.lang.String> mElementNames;
        private android.content.Intent mFillIntent;
        private int mInteractionType = 0;
        private android.app.PendingIntent mPendingIntent;
        private android.util.IntArray mViewIds;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface InteractionType {
        }

        public static android.widget.RemoteViews.RemoteResponse fromPendingIntent(android.app.PendingIntent pendingIntent) {
            android.widget.RemoteViews.RemoteResponse remoteResponse = new android.widget.RemoteViews.RemoteResponse();
            remoteResponse.mPendingIntent = pendingIntent;
            return remoteResponse;
        }

        public static android.widget.RemoteViews.RemoteResponse fromFillInIntent(android.content.Intent intent) {
            android.widget.RemoteViews.RemoteResponse remoteResponse = new android.widget.RemoteViews.RemoteResponse();
            remoteResponse.mFillIntent = intent;
            return remoteResponse;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.widget.RemoteViews.RemoteResponse fromPendingIntentTemplateAndFillInIntent(android.app.PendingIntent pendingIntent, android.content.Intent intent) {
            android.widget.RemoteViews.RemoteResponse remoteResponse = new android.widget.RemoteViews.RemoteResponse();
            remoteResponse.mPendingIntent = pendingIntent;
            remoteResponse.mFillIntent = intent;
            return remoteResponse;
        }

        public android.widget.RemoteViews.RemoteResponse addSharedElement(int i, java.lang.String str) {
            if (this.mViewIds == null) {
                this.mViewIds = new android.util.IntArray();
                this.mElementNames = new java.util.ArrayList<>();
            }
            this.mViewIds.add(i);
            this.mElementNames.add(str);
            return this;
        }

        public android.widget.RemoteViews.RemoteResponse setInteractionType(int i) {
            this.mInteractionType = i;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(android.os.Parcel parcel, int i) {
            android.app.PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntent, parcel);
            parcel.writeBoolean(this.mFillIntent != null);
            if (this.mFillIntent != null) {
                parcel.writeTypedObject(this.mFillIntent, i);
            }
            parcel.writeInt(this.mInteractionType);
            parcel.writeIntArray(this.mViewIds == null ? null : this.mViewIds.toArray());
            parcel.writeStringList(this.mElementNames);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readFromParcel(android.os.Parcel parcel) {
            this.mPendingIntent = android.app.PendingIntent.readPendingIntentOrNullFromParcel(parcel);
            this.mFillIntent = parcel.readBoolean() ? (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR) : null;
            this.mInteractionType = parcel.readInt();
            int[] createIntArray = parcel.createIntArray();
            this.mViewIds = createIntArray != null ? android.util.IntArray.wrap(createIntArray) : null;
            this.mElementNames = parcel.createStringArrayList();
        }

        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            if (this.mPendingIntent != null) {
                this.mPendingIntent.visitUris(consumer);
            }
            if (this.mFillIntent != null) {
                this.mFillIntent.visitUris(consumer);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleViewInteraction(android.view.View view, android.widget.RemoteViews.InteractionHandler interactionHandler) {
            android.app.PendingIntent pendingIntent;
            if (this.mPendingIntent != null) {
                pendingIntent = this.mPendingIntent;
            } else if (this.mFillIntent != null) {
                android.widget.AdapterView<?> adapterViewAncestor = getAdapterViewAncestor(view);
                if (adapterViewAncestor == null) {
                    android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Collection item doesn't have AdapterView parent");
                    return;
                } else {
                    if (!(adapterViewAncestor.getTag() instanceof android.app.PendingIntent)) {
                        android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Attempting setOnClickFillInIntent or setOnCheckedChangeFillInIntent without calling setPendingIntentTemplate on parent.");
                        return;
                    }
                    pendingIntent = (android.app.PendingIntent) adapterViewAncestor.getTag();
                }
            } else {
                android.util.Log.e(android.widget.RemoteViews.LOG_TAG, "Response has neither pendingIntent nor fillInIntent");
                return;
            }
            interactionHandler.onInteraction(view, pendingIntent, this);
        }

        private static android.widget.AdapterView<?> getAdapterViewAncestor(android.view.View view) {
            if (view == null) {
                return null;
            }
            android.view.View view2 = (android.view.View) view.getParent();
            while (view2 != null && !(view2 instanceof android.widget.AdapterView) && (!(view2 instanceof android.appwidget.AppWidgetHostView) || (view2 instanceof android.appwidget.AppWidgetHostView.AdapterChildHostView))) {
                view2 = (android.view.View) view2.getParent();
            }
            if (view2 instanceof android.widget.AdapterView) {
                return (android.widget.AdapterView) view2;
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x0090  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00ae  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00bb  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00c7  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public android.util.Pair<android.content.Intent, android.app.ActivityOptions> getLaunchOptions(android.view.View view) {
            android.app.ActivityOptions activityOptions;
            android.view.View view2;
            android.content.Intent intent = this.mFillIntent == null ? new android.content.Intent() : new android.content.Intent(this.mFillIntent);
            intent.setSourceBounds(android.widget.RemoteViews.getSourceBounds(view));
            if ((view instanceof android.widget.CompoundButton) && this.mInteractionType == 1) {
                intent.putExtra(android.widget.RemoteViews.EXTRA_CHECKED, ((android.widget.CompoundButton) view).isChecked());
            }
            android.content.Context context = view.getContext();
            if (context.getResources().getBoolean(com.android.internal.R.bool.config_overrideRemoteViewsActivityTransition)) {
                android.content.res.TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(com.android.internal.R.styleable.Window);
                android.content.res.TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(obtainStyledAttributes.getResourceId(8, 0), com.android.internal.R.styleable.WindowAnimation);
                int resourceId = obtainStyledAttributes2.getResourceId(26, 0);
                obtainStyledAttributes.recycle();
                obtainStyledAttributes2.recycle();
                if (resourceId != 0) {
                    activityOptions = android.app.ActivityOptions.makeCustomAnimation(context, resourceId, 0);
                    activityOptions.setPendingIntentLaunchFlags(268435456);
                    if (activityOptions == null && this.mViewIds != null && this.mElementNames != null) {
                        view2 = (android.view.View) view.getParent();
                        while (view2 != null && !(view2 instanceof android.appwidget.AppWidgetHostView)) {
                            view2 = (android.view.View) view2.getParent();
                        }
                        if (view2 instanceof android.appwidget.AppWidgetHostView) {
                            activityOptions = ((android.appwidget.AppWidgetHostView) view2).createSharedElementActivityOptions(this.mViewIds.toArray(), (java.lang.String[]) this.mElementNames.toArray(new java.lang.String[this.mElementNames.size()]), intent);
                        }
                    }
                    if (activityOptions == null) {
                        activityOptions = android.app.ActivityOptions.makeBasic();
                        activityOptions.setPendingIntentLaunchFlags(268435456);
                    }
                    if (view.getDisplay() == null) {
                        activityOptions.setLaunchDisplayId(view.getDisplay().getDisplayId());
                    } else {
                        android.util.Log.w(android.widget.RemoteViews.LOG_TAG, "getLaunchOptions: view.getDisplay() is null!", new java.lang.Exception());
                    }
                    activityOptions.setPendingIntentBackgroundActivityStartMode(1);
                    activityOptions.setPendingIntentBackgroundActivityLaunchAllowedByPermission(true);
                    return android.util.Pair.create(intent, activityOptions);
                }
            }
            activityOptions = null;
            if (activityOptions == null) {
                view2 = (android.view.View) view.getParent();
                while (view2 != null) {
                    view2 = (android.view.View) view2.getParent();
                }
                if (view2 instanceof android.appwidget.AppWidgetHostView) {
                }
            }
            if (activityOptions == null) {
            }
            if (view.getDisplay() == null) {
            }
            activityOptions.setPendingIntentBackgroundActivityStartMode(1);
            activityOptions.setPendingIntentBackgroundActivityLaunchAllowedByPermission(true);
            return android.util.Pair.create(intent, activityOptions);
        }
    }

    public static boolean startPendingIntent(android.view.View view, android.app.PendingIntent pendingIntent, android.util.Pair<android.content.Intent, android.app.ActivityOptions> pair) {
        try {
            view.getContext().startIntentSender(pendingIntent.getIntentSender(), pair.first, 0, 0, 0, pair.second.toBundle());
            return true;
        } catch (android.content.IntentSender.SendIntentException e) {
            android.util.Log.e(LOG_TAG, "Cannot send pending intent: ", e);
            return false;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(LOG_TAG, "Cannot send pending intent due to unknown exception: ", e2);
            return false;
        }
    }

    public static final class RemoteCollectionItems implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.widget.RemoteViews.RemoteCollectionItems> CREATOR = new android.os.Parcelable.Creator<android.widget.RemoteViews.RemoteCollectionItems>() { // from class: android.widget.RemoteViews.RemoteCollectionItems.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.RemoteViews.RemoteCollectionItems createFromParcel(android.os.Parcel parcel) {
                return new android.widget.RemoteViews.RemoteCollectionItems(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.RemoteViews.RemoteCollectionItems[] newArray(int i) {
                return new android.widget.RemoteViews.RemoteCollectionItems[i];
            }
        };
        private final boolean mHasStableIds;
        private android.widget.RemoteViews.HierarchyRootData mHierarchyRootData;
        private final long[] mIds;
        private final int mViewTypeCount;
        private final android.widget.RemoteViews[] mViews;

        RemoteCollectionItems(long[] jArr, android.widget.RemoteViews[] remoteViewsArr, boolean z, int i) {
            this.mIds = jArr;
            this.mViews = remoteViewsArr;
            this.mHasStableIds = z;
            this.mViewTypeCount = i;
            if (jArr.length != remoteViewsArr.length) {
                throw new java.lang.IllegalArgumentException("RemoteCollectionItems has different number of ids and views");
            }
            if (i < 1) {
                throw new java.lang.IllegalArgumentException("View type count must be >= 1");
            }
            int count = (int) java.util.Arrays.stream(remoteViewsArr).mapToInt(new android.widget.RemoteViews$RemoteCollectionItems$$ExternalSyntheticLambda0()).distinct().count();
            if (count > i) {
                throw new java.lang.IllegalArgumentException("View type count is set to " + i + ", but the collection contains " + count + " different layout ids");
            }
            if (remoteViewsArr.length > 0) {
                setHierarchyRootData(remoteViewsArr[0].getHierarchyRootData());
                remoteViewsArr[0].mIsRoot = true;
            }
        }

        RemoteCollectionItems(android.os.Parcel parcel, android.widget.RemoteViews.HierarchyRootData hierarchyRootData) {
            this.mHasStableIds = parcel.readBoolean();
            this.mViewTypeCount = parcel.readInt();
            int readInt = parcel.readInt();
            this.mIds = new long[readInt];
            parcel.readLongArray(this.mIds);
            boolean readBoolean = parcel.readBoolean();
            this.mViews = new android.widget.RemoteViews[readInt];
            int i = 0;
            if (!readBoolean) {
                this.mViews[0] = new android.widget.RemoteViews(parcel);
                this.mHierarchyRootData = this.mViews[0].getHierarchyRootData();
                i = 1;
            } else {
                if (hierarchyRootData == null) {
                    throw new java.lang.IllegalStateException("Cannot unparcel a RemoteCollectionItems that was parceled as attached without providing data for a root RemoteViews");
                }
                this.mHierarchyRootData = hierarchyRootData;
            }
            while (i < readInt) {
                this.mViews[i] = new android.widget.RemoteViews(parcel, this.mHierarchyRootData, null, 0);
                i++;
            }
        }

        void setHierarchyRootData(android.widget.RemoteViews.HierarchyRootData hierarchyRootData) {
            this.mHierarchyRootData = hierarchyRootData;
            for (android.widget.RemoteViews remoteViews : this.mViews) {
                remoteViews.configureAsChild(hierarchyRootData);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            writeToParcel(parcel, i, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(android.os.Parcel parcel, int i, boolean z) {
            boolean z2;
            boolean allowSquashing = parcel.allowSquashing();
            parcel.writeBoolean(this.mHasStableIds);
            parcel.writeInt(this.mViewTypeCount);
            parcel.writeInt(this.mIds.length);
            parcel.writeLongArray(this.mIds);
            if (z && this.mHierarchyRootData == null) {
                throw new java.lang.IllegalStateException("Cannot call writeToParcelAttached for a RemoteCollectionItems without first calling setHierarchyRootData()");
            }
            parcel.writeBoolean(z);
            if (!z && this.mViews.length > 0 && !this.mViews[0].mIsRoot) {
                z2 = true;
                this.mViews[0].mIsRoot = true;
            } else {
                z2 = false;
            }
            for (android.widget.RemoteViews remoteViews : this.mViews) {
                remoteViews.writeToParcel(parcel, i);
            }
            if (z2) {
                this.mViews[0].mIsRoot = false;
            }
            parcel.restoreAllowSquashing(allowSquashing);
        }

        public long getItemId(int i) {
            return this.mIds[i];
        }

        public android.widget.RemoteViews getItemView(int i) {
            return this.mViews[i];
        }

        public int getItemCount() {
            return this.mIds.length;
        }

        public int getViewTypeCount() {
            return this.mViewTypeCount;
        }

        public boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public static final class Builder {
            private boolean mHasStableIds;
            private int mViewTypeCount;
            private final android.util.LongArray mIds = new android.util.LongArray();
            private final java.util.List<android.widget.RemoteViews> mViews = new java.util.ArrayList();

            public android.widget.RemoteViews.RemoteCollectionItems.Builder addItem(long j, android.widget.RemoteViews remoteViews) {
                if (remoteViews == null) {
                    throw new java.lang.NullPointerException();
                }
                if (remoteViews.hasMultipleLayouts()) {
                    throw new java.lang.IllegalArgumentException("RemoteViews used in a RemoteCollectionItems cannot specify separate layouts for orientations or sizes.");
                }
                this.mIds.add(j);
                this.mViews.add(remoteViews);
                return this;
            }

            public android.widget.RemoteViews.RemoteCollectionItems.Builder setHasStableIds(boolean z) {
                this.mHasStableIds = z;
                return this;
            }

            public android.widget.RemoteViews.RemoteCollectionItems.Builder setViewTypeCount(int i) {
                this.mViewTypeCount = i;
                return this;
            }

            public android.widget.RemoteViews.RemoteCollectionItems build() {
                if (this.mViewTypeCount < 1) {
                    this.mViewTypeCount = (int) this.mViews.stream().mapToInt(new android.widget.RemoteViews$RemoteCollectionItems$$ExternalSyntheticLambda0()).distinct().count();
                }
                return new android.widget.RemoteViews.RemoteCollectionItems(this.mIds.toArray(), (android.widget.RemoteViews[]) this.mViews.toArray(new android.widget.RemoteViews[0]), this.mHasStableIds, java.lang.Math.max(this.mViewTypeCount, 1));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            for (android.widget.RemoteViews remoteViews : this.mViews) {
                remoteViews.visitUris(consumer);
            }
        }
    }

    public static final class DrawInstructions {
        private static final long VERSION = 1;
        final java.util.List<byte[]> mInstructions;

        private DrawInstructions() {
            throw new java.lang.UnsupportedOperationException("DrawInstructions cannot be instantiate without instructions");
        }

        private DrawInstructions(java.util.List<byte[]> list) {
            this.mInstructions = new java.util.ArrayList(list.size());
            for (byte[] bArr : list) {
                int length = bArr.length;
                byte[] bArr2 = new byte[length];
                java.lang.System.arraycopy(bArr, 0, bArr2, 0, length);
                this.mInstructions.add(bArr2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.widget.RemoteViews.DrawInstructions readFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(readInt);
            for (int i = 0; i < readInt; i++) {
                byte[] bArr = new byte[parcel.readInt()];
                parcel.readByteArray(bArr);
                arrayList.add(bArr);
            }
            return new android.widget.RemoteViews.DrawInstructions(arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void writeToParcel(android.widget.RemoteViews.DrawInstructions drawInstructions, android.os.Parcel parcel, int i) {
            if (drawInstructions == null) {
                parcel.writeInt(-1);
                return;
            }
            java.util.List<byte[]> list = drawInstructions.mInstructions;
            parcel.writeInt(list.size());
            for (byte[] bArr : list) {
                parcel.writeInt(bArr.length);
                parcel.writeByteArray(bArr);
            }
        }

        public static long getSupportedVersion() {
            return 1L;
        }

        public static final class Builder {
            private final java.util.List<byte[]> mInstructions;

            public Builder(java.util.List<byte[]> list) {
                this.mInstructions = new java.util.ArrayList(list);
            }

            public android.widget.RemoteViews.DrawInstructions build() {
                return new android.widget.RemoteViews.DrawInstructions(this.mInstructions);
            }
        }
    }

    public int getViewId() {
        return this.mViewId;
    }

    public void setProviderInstanceId(long j) {
        this.mProviderInstanceId = j;
    }

    public long getProviderInstanceId() {
        return this.mProviderInstanceId;
    }

    private int getChildId(android.widget.RemoteViews remoteViews) {
        if (remoteViews == this) {
            return 0;
        }
        if (hasSizedRemoteViews()) {
            for (int i = 0; i < this.mSizedRemoteViews.size(); i++) {
                if (this.mSizedRemoteViews.get(i) == remoteViews) {
                    return i + 1;
                }
            }
        }
        if (hasLandscapeAndPortraitLayouts()) {
            if (this.mLandscape == remoteViews) {
                return 1;
            }
            if (this.mPortrait == remoteViews) {
                return 2;
            }
        }
        return 0;
    }

    public long computeUniqueId(android.widget.RemoteViews remoteViews) {
        int childId;
        if (this.mIsRoot) {
            long providerInstanceId = getProviderInstanceId();
            if (providerInstanceId != -1) {
                return providerInstanceId << 8;
            }
            return providerInstanceId;
        }
        if (remoteViews == null) {
            return -1L;
        }
        long providerInstanceId2 = remoteViews.getProviderInstanceId();
        if (providerInstanceId2 == -1 || (childId = remoteViews.getChildId(this)) == -1) {
            return -1L;
        }
        return (providerInstanceId2 << 8) | childId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.Pair<java.lang.String, java.lang.Integer> getPackageUserKey(android.content.pm.ApplicationInfo applicationInfo) {
        if (applicationInfo == null || applicationInfo.packageName == null) {
            return null;
        }
        return android.util.Pair.create(applicationInfo.packageName, java.lang.Integer.valueOf(applicationInfo.uid));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.widget.RemoteViews.HierarchyRootData getHierarchyRootData() {
        return new android.widget.RemoteViews.HierarchyRootData(this.mBitmapCache, this.mCollectionCache, this.mApplicationInfoCache, this.mClassCookies);
    }

    private static final class HierarchyRootData {
        final android.widget.RemoteViews.ApplicationInfoCache mApplicationInfoCache;
        final android.widget.RemoteViews.BitmapCache mBitmapCache;
        final java.util.Map<java.lang.Class, java.lang.Object> mClassCookies;
        final android.widget.RemoteViews.RemoteCollectionCache mRemoteCollectionCache;

        HierarchyRootData(android.widget.RemoteViews.BitmapCache bitmapCache, android.widget.RemoteViews.RemoteCollectionCache remoteCollectionCache, android.widget.RemoteViews.ApplicationInfoCache applicationInfoCache, java.util.Map<java.lang.Class, java.lang.Object> map) {
            this.mBitmapCache = bitmapCache;
            this.mRemoteCollectionCache = remoteCollectionCache;
            this.mApplicationInfoCache = applicationInfoCache;
            this.mClassCookies = map;
        }
    }
}
