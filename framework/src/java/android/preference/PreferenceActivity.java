package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class PreferenceActivity extends android.app.ListActivity implements android.preference.PreferenceManager.OnPreferenceTreeClickListener, android.preference.PreferenceFragment.OnPreferenceStartFragmentCallback {
    private static final java.lang.String BACK_STACK_PREFS = ":android:prefs";
    private static final java.lang.String CUR_HEADER_TAG = ":android:cur_header";
    public static final java.lang.String EXTRA_NO_HEADERS = ":android:no_headers";
    private static final java.lang.String EXTRA_PREFS_SET_BACK_TEXT = "extra_prefs_set_back_text";
    private static final java.lang.String EXTRA_PREFS_SET_NEXT_TEXT = "extra_prefs_set_next_text";
    private static final java.lang.String EXTRA_PREFS_SHOW_BUTTON_BAR = "extra_prefs_show_button_bar";
    private static final java.lang.String EXTRA_PREFS_SHOW_SKIP = "extra_prefs_show_skip";
    public static final java.lang.String EXTRA_SHOW_FRAGMENT = ":android:show_fragment";
    public static final java.lang.String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":android:show_fragment_args";
    public static final java.lang.String EXTRA_SHOW_FRAGMENT_SHORT_TITLE = ":android:show_fragment_short_title";
    public static final java.lang.String EXTRA_SHOW_FRAGMENT_TITLE = ":android:show_fragment_title";
    private static final int FIRST_REQUEST_CODE = 100;
    private static final java.lang.String HEADERS_TAG = ":android:headers";
    public static final long HEADER_ID_UNDEFINED = -1;
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final int MSG_BUILD_HEADERS = 2;
    private static final java.lang.String PREFERENCES_TAG = ":android:preferences";
    private static final java.lang.String TAG = "PreferenceActivity";
    private java.lang.CharSequence mActivityTitle;
    private android.preference.PreferenceActivity.Header mCurHeader;
    private android.app.FragmentBreadCrumbs mFragmentBreadCrumbs;
    private android.view.ViewGroup mHeadersContainer;
    private android.widget.FrameLayout mListFooter;
    private android.widget.Button mNextButton;
    private android.preference.PreferenceManager mPreferenceManager;
    private android.view.ViewGroup mPrefsContainer;
    private android.os.Bundle mSavedInstanceState;
    private boolean mSinglePane;
    private final java.util.ArrayList<android.preference.PreferenceActivity.Header> mHeaders = new java.util.ArrayList<>();
    private int mPreferenceHeaderItemResId = 0;
    private boolean mPreferenceHeaderRemoveEmptyIcon = false;
    private android.os.Handler mHandler = new android.os.Handler() { // from class: android.preference.PreferenceActivity.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.preference.PreferenceActivity.Header findBestMatchingHeader;
            switch (message.what) {
                case 1:
                    android.preference.PreferenceActivity.this.bindPreferences();
                    break;
                case 2:
                    java.util.ArrayList<android.preference.PreferenceActivity.Header> arrayList = new java.util.ArrayList<>(android.preference.PreferenceActivity.this.mHeaders);
                    android.preference.PreferenceActivity.this.mHeaders.clear();
                    android.preference.PreferenceActivity.this.onBuildHeaders(android.preference.PreferenceActivity.this.mHeaders);
                    if (android.preference.PreferenceActivity.this.mAdapter instanceof android.widget.BaseAdapter) {
                        ((android.widget.BaseAdapter) android.preference.PreferenceActivity.this.mAdapter).notifyDataSetChanged();
                    }
                    android.preference.PreferenceActivity.Header onGetNewHeader = android.preference.PreferenceActivity.this.onGetNewHeader();
                    if (onGetNewHeader != null && onGetNewHeader.fragment != null) {
                        android.preference.PreferenceActivity.Header findBestMatchingHeader2 = android.preference.PreferenceActivity.this.findBestMatchingHeader(onGetNewHeader, arrayList);
                        if (findBestMatchingHeader2 == null || android.preference.PreferenceActivity.this.mCurHeader != findBestMatchingHeader2) {
                            android.preference.PreferenceActivity.this.switchToHeader(onGetNewHeader);
                            break;
                        }
                    } else if (android.preference.PreferenceActivity.this.mCurHeader != null && (findBestMatchingHeader = android.preference.PreferenceActivity.this.findBestMatchingHeader(android.preference.PreferenceActivity.this.mCurHeader, android.preference.PreferenceActivity.this.mHeaders)) != null) {
                        android.preference.PreferenceActivity.this.setSelectedHeader(findBestMatchingHeader);
                        break;
                    }
                    break;
            }
        }
    };

    private static class HeaderAdapter extends android.widget.ArrayAdapter<android.preference.PreferenceActivity.Header> {
        private android.view.LayoutInflater mInflater;
        private int mLayoutResId;
        private boolean mRemoveIconIfEmpty;

        private static class HeaderViewHolder {
            android.widget.ImageView icon;
            android.widget.TextView summary;
            android.widget.TextView title;

            private HeaderViewHolder() {
            }
        }

        public HeaderAdapter(android.content.Context context, java.util.List<android.preference.PreferenceActivity.Header> list, int i, boolean z) {
            super(context, 0, list);
            this.mInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            this.mLayoutResId = i;
            this.mRemoveIconIfEmpty = z;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            android.preference.PreferenceActivity.HeaderAdapter.HeaderViewHolder headerViewHolder;
            if (view == null) {
                view = this.mInflater.inflate(this.mLayoutResId, viewGroup, false);
                headerViewHolder = new android.preference.PreferenceActivity.HeaderAdapter.HeaderViewHolder();
                headerViewHolder.icon = (android.widget.ImageView) view.findViewById(16908294);
                headerViewHolder.title = (android.widget.TextView) view.findViewById(16908310);
                headerViewHolder.summary = (android.widget.TextView) view.findViewById(16908304);
                view.setTag(headerViewHolder);
            } else {
                headerViewHolder = (android.preference.PreferenceActivity.HeaderAdapter.HeaderViewHolder) view.getTag();
            }
            android.preference.PreferenceActivity.Header item = getItem(i);
            if (this.mRemoveIconIfEmpty) {
                if (item.iconRes != 0) {
                    headerViewHolder.icon.setVisibility(0);
                    headerViewHolder.icon.setImageResource(item.iconRes);
                } else {
                    headerViewHolder.icon.setVisibility(8);
                }
            } else {
                headerViewHolder.icon.setImageResource(item.iconRes);
            }
            headerViewHolder.title.lambda$setTextAsync$0(item.getTitle(getContext().getResources()));
            java.lang.CharSequence summary = item.getSummary(getContext().getResources());
            if (!android.text.TextUtils.isEmpty(summary)) {
                headerViewHolder.summary.setVisibility(0);
                headerViewHolder.summary.lambda$setTextAsync$0(summary);
            } else {
                headerViewHolder.summary.setVisibility(8);
            }
            return view;
        }
    }

    @java.lang.Deprecated
    public static final class Header implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.preference.PreferenceActivity.Header> CREATOR = new android.os.Parcelable.Creator<android.preference.PreferenceActivity.Header>() { // from class: android.preference.PreferenceActivity.Header.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.PreferenceActivity.Header createFromParcel(android.os.Parcel parcel) {
                return new android.preference.PreferenceActivity.Header(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.PreferenceActivity.Header[] newArray(int i) {
                return new android.preference.PreferenceActivity.Header[i];
            }
        };
        public java.lang.CharSequence breadCrumbShortTitle;
        public int breadCrumbShortTitleRes;
        public java.lang.CharSequence breadCrumbTitle;
        public int breadCrumbTitleRes;
        public android.os.Bundle extras;
        public java.lang.String fragment;
        public android.os.Bundle fragmentArguments;
        public int iconRes;
        public long id = -1;
        public android.content.Intent intent;
        public java.lang.CharSequence summary;
        public int summaryRes;
        public java.lang.CharSequence title;
        public int titleRes;

        public Header() {
        }

        public java.lang.CharSequence getTitle(android.content.res.Resources resources) {
            if (this.titleRes != 0) {
                return resources.getText(this.titleRes);
            }
            return this.title;
        }

        public java.lang.CharSequence getSummary(android.content.res.Resources resources) {
            if (this.summaryRes != 0) {
                return resources.getText(this.summaryRes);
            }
            return this.summary;
        }

        public java.lang.CharSequence getBreadCrumbTitle(android.content.res.Resources resources) {
            if (this.breadCrumbTitleRes != 0) {
                return resources.getText(this.breadCrumbTitleRes);
            }
            return this.breadCrumbTitle;
        }

        public java.lang.CharSequence getBreadCrumbShortTitle(android.content.res.Resources resources) {
            if (this.breadCrumbShortTitleRes != 0) {
                return resources.getText(this.breadCrumbShortTitleRes);
            }
            return this.breadCrumbShortTitle;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.id);
            parcel.writeInt(this.titleRes);
            android.text.TextUtils.writeToParcel(this.title, parcel, i);
            parcel.writeInt(this.summaryRes);
            android.text.TextUtils.writeToParcel(this.summary, parcel, i);
            parcel.writeInt(this.breadCrumbTitleRes);
            android.text.TextUtils.writeToParcel(this.breadCrumbTitle, parcel, i);
            parcel.writeInt(this.breadCrumbShortTitleRes);
            android.text.TextUtils.writeToParcel(this.breadCrumbShortTitle, parcel, i);
            parcel.writeInt(this.iconRes);
            parcel.writeString(this.fragment);
            parcel.writeBundle(this.fragmentArguments);
            if (this.intent != null) {
                parcel.writeInt(1);
                this.intent.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeBundle(this.extras);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.id = parcel.readLong();
            this.titleRes = parcel.readInt();
            this.title = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.summaryRes = parcel.readInt();
            this.summary = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.breadCrumbTitleRes = parcel.readInt();
            this.breadCrumbTitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.breadCrumbShortTitleRes = parcel.readInt();
            this.breadCrumbShortTitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.iconRes = parcel.readInt();
            this.fragment = parcel.readString();
            this.fragmentArguments = parcel.readBundle();
            if (parcel.readInt() != 0) {
                this.intent = android.content.Intent.CREATOR.createFromParcel(parcel);
            }
            this.extras = parcel.readBundle();
        }

        Header(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(android.view.MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        android.content.res.TypedArray obtainStyledAttributes = obtainStyledAttributes(null, com.android.internal.R.styleable.PreferenceActivity, com.android.internal.R.attr.preferenceActivityStyle, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, com.android.internal.R.layout.preference_list_content);
        this.mPreferenceHeaderItemResId = obtainStyledAttributes.getResourceId(1, com.android.internal.R.layout.preference_header_item);
        this.mPreferenceHeaderRemoveEmptyIcon = obtainStyledAttributes.getBoolean(2, false);
        obtainStyledAttributes.recycle();
        setContentView(resourceId);
        this.mListFooter = (android.widget.FrameLayout) findViewById(com.android.internal.R.id.list_footer);
        this.mPrefsContainer = (android.view.ViewGroup) findViewById(com.android.internal.R.id.prefs_frame);
        this.mHeadersContainer = (android.view.ViewGroup) findViewById(com.android.internal.R.id.headers);
        this.mSinglePane = onIsHidingHeaders() || !onIsMultiPane();
        java.lang.String stringExtra = getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT);
        android.os.Bundle bundleExtra = getIntent().getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS);
        int intExtra = getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_TITLE, 0);
        int intExtra2 = getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, 0);
        this.mActivityTitle = getTitle();
        if (bundle != null) {
            java.util.ArrayList parcelableArrayList = bundle.getParcelableArrayList(HEADERS_TAG, android.preference.PreferenceActivity.Header.class);
            if (parcelableArrayList != null) {
                this.mHeaders.addAll(parcelableArrayList);
                int i = bundle.getInt(CUR_HEADER_TAG, -1);
                if (i >= 0 && i < this.mHeaders.size()) {
                    setSelectedHeader(this.mHeaders.get(i));
                } else if (!this.mSinglePane && stringExtra == null) {
                    switchToHeader(onGetInitialHeader());
                }
            } else {
                showBreadCrumbs(getTitle(), null);
            }
        } else {
            if (!onIsHidingHeaders()) {
                onBuildHeaders(this.mHeaders);
            }
            if (stringExtra != null) {
                switchToHeader(stringExtra, bundleExtra);
            } else if (!this.mSinglePane && this.mHeaders.size() > 0) {
                switchToHeader(onGetInitialHeader());
            }
        }
        if (this.mHeaders.size() > 0) {
            setListAdapter(new android.preference.PreferenceActivity.HeaderAdapter(this, this.mHeaders, this.mPreferenceHeaderItemResId, this.mPreferenceHeaderRemoveEmptyIcon));
            if (!this.mSinglePane) {
                getListView().setChoiceMode(1);
            }
        }
        if (this.mSinglePane && stringExtra != null && intExtra != 0) {
            showBreadCrumbs(getText(intExtra), intExtra2 != 0 ? getText(intExtra2) : null);
        }
        if (this.mHeaders.size() == 0 && stringExtra == null) {
            setContentView(com.android.internal.R.layout.preference_list_content_single);
            this.mListFooter = (android.widget.FrameLayout) findViewById(com.android.internal.R.id.list_footer);
            this.mPrefsContainer = (android.view.ViewGroup) findViewById(com.android.internal.R.id.prefs);
            this.mPreferenceManager = new android.preference.PreferenceManager(this, 100);
            this.mPreferenceManager.setOnPreferenceTreeClickListener(this);
            this.mHeadersContainer = null;
        } else if (this.mSinglePane) {
            if (stringExtra != null || this.mCurHeader != null) {
                this.mHeadersContainer.setVisibility(8);
            } else {
                this.mPrefsContainer.setVisibility(8);
            }
            ((android.view.ViewGroup) findViewById(com.android.internal.R.id.prefs_container)).setLayoutTransition(new android.animation.LayoutTransition());
        } else if (this.mHeaders.size() > 0 && this.mCurHeader != null) {
            setSelectedHeader(this.mCurHeader);
        }
        android.content.Intent intent = getIntent();
        if (intent.getBooleanExtra(EXTRA_PREFS_SHOW_BUTTON_BAR, false)) {
            findViewById(com.android.internal.R.id.button_bar).setVisibility(0);
            android.widget.Button button = (android.widget.Button) findViewById(com.android.internal.R.id.back_button);
            button.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.preference.PreferenceActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(android.view.View view) {
                    android.preference.PreferenceActivity.this.setResult(0);
                    android.preference.PreferenceActivity.this.finish();
                }
            });
            android.widget.Button button2 = (android.widget.Button) findViewById(com.android.internal.R.id.skip_button);
            button2.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.preference.PreferenceActivity.3
                @Override // android.view.View.OnClickListener
                public void onClick(android.view.View view) {
                    android.preference.PreferenceActivity.this.setResult(-1);
                    android.preference.PreferenceActivity.this.finish();
                }
            });
            this.mNextButton = (android.widget.Button) findViewById(com.android.internal.R.id.next_button);
            this.mNextButton.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.preference.PreferenceActivity.4
                @Override // android.view.View.OnClickListener
                public void onClick(android.view.View view) {
                    android.preference.PreferenceActivity.this.setResult(-1);
                    android.preference.PreferenceActivity.this.finish();
                }
            });
            if (intent.hasExtra(EXTRA_PREFS_SET_NEXT_TEXT)) {
                java.lang.String stringExtra2 = intent.getStringExtra(EXTRA_PREFS_SET_NEXT_TEXT);
                if (android.text.TextUtils.isEmpty(stringExtra2)) {
                    this.mNextButton.setVisibility(8);
                } else {
                    this.mNextButton.lambda$setTextAsync$0(stringExtra2);
                }
            }
            if (intent.hasExtra(EXTRA_PREFS_SET_BACK_TEXT)) {
                java.lang.String stringExtra3 = intent.getStringExtra(EXTRA_PREFS_SET_BACK_TEXT);
                if (android.text.TextUtils.isEmpty(stringExtra3)) {
                    button.setVisibility(8);
                } else {
                    button.lambda$setTextAsync$0(stringExtra3);
                }
            }
            if (intent.getBooleanExtra(EXTRA_PREFS_SHOW_SKIP, false)) {
                button2.setVisibility(0);
            }
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.mCurHeader != null && this.mSinglePane && getFragmentManager().getBackStackEntryCount() == 0 && getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT) == null) {
            this.mCurHeader = null;
            this.mPrefsContainer.setVisibility(8);
            this.mHeadersContainer.setVisibility(0);
            if (this.mActivityTitle != null) {
                showBreadCrumbs(this.mActivityTitle, null);
            }
            getListView().clearChoices();
            return;
        }
        super.onBackPressed();
    }

    public boolean hasHeaders() {
        return this.mHeadersContainer != null && this.mHeadersContainer.getVisibility() == 0;
    }

    public java.util.List<android.preference.PreferenceActivity.Header> getHeaders() {
        return this.mHeaders;
    }

    public boolean isMultiPane() {
        return !this.mSinglePane;
    }

    public boolean onIsMultiPane() {
        return getResources().getBoolean(com.android.internal.R.bool.preferences_prefer_dual_pane);
    }

    public boolean onIsHidingHeaders() {
        return getIntent().getBooleanExtra(EXTRA_NO_HEADERS, false);
    }

    public android.preference.PreferenceActivity.Header onGetInitialHeader() {
        for (int i = 0; i < this.mHeaders.size(); i++) {
            android.preference.PreferenceActivity.Header header = this.mHeaders.get(i);
            if (header.fragment != null) {
                return header;
            }
        }
        throw new java.lang.IllegalStateException("Must have at least one header with a fragment");
    }

    public android.preference.PreferenceActivity.Header onGetNewHeader() {
        return null;
    }

    public void onBuildHeaders(java.util.List<android.preference.PreferenceActivity.Header> list) {
    }

    public void invalidateHeaders() {
        if (!this.mHandler.hasMessages(2)) {
            this.mHandler.sendEmptyMessage(2);
        }
    }

    public void loadHeadersFromResource(int i, java.util.List<android.preference.PreferenceActivity.Header> list) {
        int next;
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                android.content.res.XmlResourceParser xml = getResources().getXml(i);
                try {
                    android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xml);
                    do {
                        next = xml.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    java.lang.String name = xml.getName();
                    if (!"preference-headers".equals(name)) {
                        throw new java.lang.RuntimeException("XML document must start with <preference-headers> tag; found" + name + " at " + xml.getPositionDescription());
                    }
                    int depth = xml.getDepth();
                    android.os.Bundle bundle = null;
                    while (true) {
                        int next2 = xml.next();
                        if (next2 == 1 || (next2 == 3 && xml.getDepth() <= depth)) {
                            break;
                        }
                        if (next2 != 3 && next2 != 4) {
                            if (android.provider.Downloads.Impl.RequestHeaders.COLUMN_HEADER.equals(xml.getName())) {
                                android.preference.PreferenceActivity.Header header = new android.preference.PreferenceActivity.Header();
                                android.content.res.TypedArray obtainStyledAttributes = obtainStyledAttributes(asAttributeSet, com.android.internal.R.styleable.PreferenceHeader);
                                header.id = obtainStyledAttributes.getResourceId(1, -1);
                                android.util.TypedValue peekValue = obtainStyledAttributes.peekValue(2);
                                if (peekValue != null && peekValue.type == 3) {
                                    if (peekValue.resourceId != 0) {
                                        header.titleRes = peekValue.resourceId;
                                    } else {
                                        header.title = peekValue.string;
                                    }
                                }
                                android.util.TypedValue peekValue2 = obtainStyledAttributes.peekValue(3);
                                if (peekValue2 != null && peekValue2.type == 3) {
                                    if (peekValue2.resourceId != 0) {
                                        header.summaryRes = peekValue2.resourceId;
                                    } else {
                                        header.summary = peekValue2.string;
                                    }
                                }
                                android.util.TypedValue peekValue3 = obtainStyledAttributes.peekValue(5);
                                if (peekValue3 != null && peekValue3.type == 3) {
                                    if (peekValue3.resourceId != 0) {
                                        header.breadCrumbTitleRes = peekValue3.resourceId;
                                    } else {
                                        header.breadCrumbTitle = peekValue3.string;
                                    }
                                }
                                android.util.TypedValue peekValue4 = obtainStyledAttributes.peekValue(6);
                                if (peekValue4 != null && peekValue4.type == 3) {
                                    if (peekValue4.resourceId != 0) {
                                        header.breadCrumbShortTitleRes = peekValue4.resourceId;
                                    } else {
                                        header.breadCrumbShortTitle = peekValue4.string;
                                    }
                                }
                                header.iconRes = obtainStyledAttributes.getResourceId(0, 0);
                                header.fragment = obtainStyledAttributes.getString(4);
                                obtainStyledAttributes.recycle();
                                if (bundle == null) {
                                    bundle = new android.os.Bundle();
                                }
                                int depth2 = xml.getDepth();
                                while (true) {
                                    int next3 = xml.next();
                                    if (next3 == 1 || (next3 == 3 && xml.getDepth() <= depth2)) {
                                        break;
                                    }
                                    if (next3 != 3 && next3 != 4) {
                                        java.lang.String name2 = xml.getName();
                                        if (name2.equals("extra")) {
                                            getResources().parseBundleExtra("extra", asAttributeSet, bundle);
                                            com.android.internal.util.XmlUtils.skipCurrentTag(xml);
                                        } else if (name2.equals("intent")) {
                                            header.intent = android.content.Intent.parseIntent(getResources(), xml, asAttributeSet);
                                        } else {
                                            com.android.internal.util.XmlUtils.skipCurrentTag(xml);
                                        }
                                    }
                                }
                                if (bundle.size() > 0) {
                                    header.fragmentArguments = bundle;
                                    bundle = null;
                                }
                                list.add(header);
                            } else {
                                com.android.internal.util.XmlUtils.skipCurrentTag(xml);
                            }
                        }
                    }
                    if (xml != null) {
                        xml.close();
                    }
                } catch (java.io.IOException e) {
                    e = e;
                    throw new java.lang.RuntimeException("Error parsing headers", e);
                } catch (org.xmlpull.v1.XmlPullParserException e2) {
                    e = e2;
                    throw new java.lang.RuntimeException("Error parsing headers", e);
                } catch (java.lang.Throwable th) {
                    th = th;
                    xmlResourceParser = xml;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (java.io.IOException e3) {
                e = e3;
            } catch (org.xmlpull.v1.XmlPullParserException e4) {
                e = e4;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    protected boolean isValidFragment(java.lang.String str) {
        if (getApplicationInfo().targetSdkVersion >= 19) {
            throw new java.lang.RuntimeException("Subclasses of PreferenceActivity must override isValidFragment(String) to verify that the Fragment class is valid! " + getClass().getName() + " has not checked if fragment " + str + " is valid.");
        }
        return true;
    }

    public void setListFooter(android.view.View view) {
        this.mListFooter.removeAllViews();
        this.mListFooter.addView(view, new android.widget.FrameLayout.LayoutParams(-1, -2));
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityStop();
        }
    }

    @Override // android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        super.onDestroy();
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityDestroy();
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(android.os.Bundle bundle) {
        android.preference.PreferenceScreen preferenceScreen;
        int indexOf;
        super.onSaveInstanceState(bundle);
        if (this.mHeaders.size() > 0) {
            bundle.putParcelableArrayList(HEADERS_TAG, this.mHeaders);
            if (this.mCurHeader != null && (indexOf = this.mHeaders.indexOf(this.mCurHeader)) >= 0) {
                bundle.putInt(CUR_HEADER_TAG, indexOf);
            }
        }
        if (this.mPreferenceManager != null && (preferenceScreen = getPreferenceScreen()) != null) {
            android.os.Bundle bundle2 = new android.os.Bundle();
            preferenceScreen.saveHierarchyState(bundle2);
            bundle.putBundle(PREFERENCES_TAG, bundle2);
        }
    }

    @Override // android.app.ListActivity, android.app.Activity
    protected void onRestoreInstanceState(android.os.Bundle bundle) {
        android.os.Bundle bundle2;
        android.preference.PreferenceScreen preferenceScreen;
        if (this.mPreferenceManager != null && (bundle2 = bundle.getBundle(PREFERENCES_TAG)) != null && (preferenceScreen = getPreferenceScreen()) != null) {
            preferenceScreen.restoreHierarchyState(bundle2);
            this.mSavedInstanceState = bundle;
            return;
        }
        super.onRestoreInstanceState(bundle);
        if (!this.mSinglePane && this.mCurHeader != null) {
            setSelectedHeader(this.mCurHeader);
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityResult(i, i2, intent);
        }
    }

    @Override // android.app.ListActivity, android.app.Activity, android.view.Window.Callback
    public void onContentChanged() {
        super.onContentChanged();
        if (this.mPreferenceManager != null) {
            postBindPreferences();
        }
    }

    @Override // android.app.ListActivity
    protected void onListItemClick(android.widget.ListView listView, android.view.View view, int i, long j) {
        if (!isResumed()) {
            return;
        }
        super.onListItemClick(listView, view, i, j);
        if (this.mAdapter != null) {
            java.lang.Object item = this.mAdapter.getItem(i);
            if (item instanceof android.preference.PreferenceActivity.Header) {
                onHeaderClick((android.preference.PreferenceActivity.Header) item, i);
            }
        }
    }

    public void onHeaderClick(android.preference.PreferenceActivity.Header header, int i) {
        if (header.fragment != null) {
            switchToHeader(header);
        } else if (header.intent != null) {
            startActivity(header.intent);
        }
    }

    public android.content.Intent onBuildStartFragmentIntent(java.lang.String str, android.os.Bundle bundle, int i, int i2) {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MAIN);
        intent.setClass(this, getClass());
        intent.putExtra(EXTRA_SHOW_FRAGMENT, str);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, bundle);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, i);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, i2);
        intent.putExtra(EXTRA_NO_HEADERS, true);
        return intent;
    }

    public void startWithFragment(java.lang.String str, android.os.Bundle bundle, android.app.Fragment fragment, int i) {
        startWithFragment(str, bundle, fragment, i, 0, 0);
    }

    public void startWithFragment(java.lang.String str, android.os.Bundle bundle, android.app.Fragment fragment, int i, int i2, int i3) {
        android.content.Intent onBuildStartFragmentIntent = onBuildStartFragmentIntent(str, bundle, i2, i3);
        if (fragment == null) {
            startActivity(onBuildStartFragmentIntent);
        } else {
            fragment.startActivityForResult(onBuildStartFragmentIntent, i);
        }
    }

    public void showBreadCrumbs(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        if (this.mFragmentBreadCrumbs == null) {
            try {
                this.mFragmentBreadCrumbs = (android.app.FragmentBreadCrumbs) findViewById(16908310);
                if (this.mFragmentBreadCrumbs == null) {
                    if (charSequence != null) {
                        setTitle(charSequence);
                        return;
                    }
                    return;
                }
                if (this.mSinglePane) {
                    this.mFragmentBreadCrumbs.setVisibility(8);
                    android.view.View findViewById = findViewById(com.android.internal.R.id.breadcrumb_section);
                    if (findViewById != null) {
                        findViewById.setVisibility(8);
                    }
                    setTitle(charSequence);
                }
                this.mFragmentBreadCrumbs.setMaxVisible(2);
                this.mFragmentBreadCrumbs.setActivity(this);
            } catch (java.lang.ClassCastException e) {
                setTitle(charSequence);
                return;
            }
        }
        if (this.mFragmentBreadCrumbs.getVisibility() != 0) {
            setTitle(charSequence);
        } else {
            this.mFragmentBreadCrumbs.setTitle(charSequence, charSequence2);
            this.mFragmentBreadCrumbs.setParentTitle(null, null, null);
        }
    }

    public void setParentTitle(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.view.View.OnClickListener onClickListener) {
        if (this.mFragmentBreadCrumbs != null) {
            this.mFragmentBreadCrumbs.setParentTitle(charSequence, charSequence2, onClickListener);
        }
    }

    void setSelectedHeader(android.preference.PreferenceActivity.Header header) {
        this.mCurHeader = header;
        int indexOf = this.mHeaders.indexOf(header);
        if (indexOf >= 0) {
            getListView().setItemChecked(indexOf, true);
        } else {
            getListView().clearChoices();
        }
        showBreadCrumbs(header);
    }

    void showBreadCrumbs(android.preference.PreferenceActivity.Header header) {
        if (header != null) {
            java.lang.CharSequence breadCrumbTitle = header.getBreadCrumbTitle(getResources());
            if (breadCrumbTitle == null) {
                breadCrumbTitle = header.getTitle(getResources());
            }
            if (breadCrumbTitle == null) {
                breadCrumbTitle = getTitle();
            }
            showBreadCrumbs(breadCrumbTitle, header.getBreadCrumbShortTitle(getResources()));
            return;
        }
        showBreadCrumbs(getTitle(), null);
    }

    private void switchToHeaderInner(java.lang.String str, android.os.Bundle bundle) {
        int i;
        getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
        if (!isValidFragment(str)) {
            throw new java.lang.IllegalArgumentException("Invalid fragment for this activity: " + str);
        }
        android.app.Fragment instantiate = android.app.Fragment.instantiate(this, str, bundle);
        android.app.FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        if (this.mSinglePane) {
            i = 0;
        } else {
            i = 4099;
        }
        beginTransaction.setTransition(i);
        beginTransaction.replace(com.android.internal.R.id.prefs, instantiate);
        beginTransaction.commitAllowingStateLoss();
        if (this.mSinglePane && this.mPrefsContainer.getVisibility() == 8) {
            this.mPrefsContainer.setVisibility(0);
            this.mHeadersContainer.setVisibility(8);
        }
    }

    public void switchToHeader(java.lang.String str, android.os.Bundle bundle) {
        android.preference.PreferenceActivity.Header header;
        int i = 0;
        while (true) {
            if (i >= this.mHeaders.size()) {
                header = null;
                break;
            } else if (!str.equals(this.mHeaders.get(i).fragment)) {
                i++;
            } else {
                header = this.mHeaders.get(i);
                break;
            }
        }
        setSelectedHeader(header);
        switchToHeaderInner(str, bundle);
    }

    public void switchToHeader(android.preference.PreferenceActivity.Header header) {
        if (this.mCurHeader == header) {
            getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
        } else {
            if (header.fragment == null) {
                throw new java.lang.IllegalStateException("can't switch to header that has no fragment");
            }
            switchToHeaderInner(header.fragment, header.fragmentArguments);
            setSelectedHeader(header);
        }
    }

    android.preference.PreferenceActivity.Header findBestMatchingHeader(android.preference.PreferenceActivity.Header header, java.util.ArrayList<android.preference.PreferenceActivity.Header> arrayList) {
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            android.preference.PreferenceActivity.Header header2 = arrayList.get(i);
            if (header == header2 || (header.id != -1 && header.id == header2.id)) {
                arrayList2.clear();
                arrayList2.add(header2);
                break;
            }
            if (header.fragment != null) {
                if (header.fragment.equals(header2.fragment)) {
                    arrayList2.add(header2);
                }
            } else if (header.intent != null) {
                if (header.intent.equals(header2.intent)) {
                    arrayList2.add(header2);
                }
            } else if (header.title != null && header.title.equals(header2.title)) {
                arrayList2.add(header2);
            }
        }
        int size = arrayList2.size();
        if (size == 1) {
            return (android.preference.PreferenceActivity.Header) arrayList2.get(0);
        }
        if (size > 1) {
            for (int i2 = 0; i2 < size; i2++) {
                android.preference.PreferenceActivity.Header header3 = (android.preference.PreferenceActivity.Header) arrayList2.get(i2);
                if (header.fragmentArguments != null && header.fragmentArguments.equals(header3.fragmentArguments)) {
                    return header3;
                }
                if (header.extras != null && header.extras.equals(header3.extras)) {
                    return header3;
                }
                if (header.title != null && header.title.equals(header3.title)) {
                    return header3;
                }
            }
            return null;
        }
        return null;
    }

    public void startPreferenceFragment(android.app.Fragment fragment, boolean z) {
        android.app.FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.replace(com.android.internal.R.id.prefs, fragment);
        if (z) {
            beginTransaction.setTransition(4097);
            beginTransaction.addToBackStack(BACK_STACK_PREFS);
        } else {
            beginTransaction.setTransition(4099);
        }
        beginTransaction.commitAllowingStateLoss();
    }

    public void startPreferencePanel(java.lang.String str, android.os.Bundle bundle, int i, java.lang.CharSequence charSequence, android.app.Fragment fragment, int i2) {
        android.app.Fragment instantiate = android.app.Fragment.instantiate(this, str, bundle);
        if (fragment != null) {
            instantiate.setTargetFragment(fragment, i2);
        }
        android.app.FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.replace(com.android.internal.R.id.prefs, instantiate);
        if (i != 0) {
            beginTransaction.setBreadCrumbTitle(i);
        } else if (charSequence != null) {
            beginTransaction.setBreadCrumbTitle(charSequence);
        }
        beginTransaction.setTransition(4097);
        beginTransaction.addToBackStack(BACK_STACK_PREFS);
        beginTransaction.commitAllowingStateLoss();
    }

    public void finishPreferencePanel(android.app.Fragment fragment, int i, android.content.Intent intent) {
        onBackPressed();
        if (fragment != null && fragment.getTargetFragment() != null) {
            fragment.getTargetFragment().onActivityResult(fragment.getTargetRequestCode(), i, intent);
        }
    }

    @Override // android.preference.PreferenceFragment.OnPreferenceStartFragmentCallback
    public boolean onPreferenceStartFragment(android.preference.PreferenceFragment preferenceFragment, android.preference.Preference preference) {
        startPreferencePanel(preference.getFragment(), preference.getExtras(), preference.getTitleRes(), preference.getTitle(), null, 0);
        return true;
    }

    private void postBindPreferences() {
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindPreferences() {
        android.preference.PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.bind(getListView());
            if (this.mSavedInstanceState != null) {
                super.onRestoreInstanceState(this.mSavedInstanceState);
                this.mSavedInstanceState = null;
            }
        }
    }

    @java.lang.Deprecated
    public android.preference.PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    private void requirePreferenceManager() {
        if (this.mPreferenceManager == null) {
            if (this.mAdapter == null) {
                throw new java.lang.RuntimeException("This should be called after super.onCreate.");
            }
            throw new java.lang.RuntimeException("Modern two-pane PreferenceActivity requires use of a PreferenceFragment");
        }
    }

    @java.lang.Deprecated
    public void setPreferenceScreen(android.preference.PreferenceScreen preferenceScreen) {
        requirePreferenceManager();
        if (this.mPreferenceManager.setPreferences(preferenceScreen) && preferenceScreen != null) {
            postBindPreferences();
            java.lang.CharSequence title = getPreferenceScreen().getTitle();
            if (title != null) {
                setTitle(title);
            }
        }
    }

    @java.lang.Deprecated
    public android.preference.PreferenceScreen getPreferenceScreen() {
        if (this.mPreferenceManager != null) {
            return this.mPreferenceManager.getPreferenceScreen();
        }
        return null;
    }

    @java.lang.Deprecated
    public void addPreferencesFromIntent(android.content.Intent intent) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, getPreferenceScreen()));
    }

    @java.lang.Deprecated
    public void addPreferencesFromResource(int i) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromResource(this, i, getPreferenceScreen()));
    }

    @Override // android.preference.PreferenceManager.OnPreferenceTreeClickListener
    @java.lang.Deprecated
    public boolean onPreferenceTreeClick(android.preference.PreferenceScreen preferenceScreen, android.preference.Preference preference) {
        return false;
    }

    @java.lang.Deprecated
    public android.preference.Preference findPreference(java.lang.CharSequence charSequence) {
        if (this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.findPreference(charSequence);
    }

    @Override // android.app.Activity
    protected void onNewIntent(android.content.Intent intent) {
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchNewIntent(intent);
        }
    }

    protected boolean hasNextButton() {
        return this.mNextButton != null;
    }

    protected android.widget.Button getNextButton() {
        return this.mNextButton;
    }
}
