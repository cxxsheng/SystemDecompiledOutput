package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class ListFragment extends android.app.Fragment {
    android.widget.ListAdapter mAdapter;
    java.lang.CharSequence mEmptyText;
    android.view.View mEmptyView;
    android.widget.ListView mList;
    android.view.View mListContainer;
    boolean mListShown;
    android.view.View mProgressContainer;
    android.widget.TextView mStandardEmptyView;
    private final android.os.Handler mHandler = new android.os.Handler();
    private final java.lang.Runnable mRequestFocus = new java.lang.Runnable() { // from class: android.app.ListFragment.1
        @Override // java.lang.Runnable
        public void run() {
            android.app.ListFragment.this.mList.focusableViewAvailable(android.app.ListFragment.this.mList);
        }
    };
    private final android.widget.AdapterView.OnItemClickListener mOnClickListener = new android.widget.AdapterView.OnItemClickListener() { // from class: android.app.ListFragment.2
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            android.app.ListFragment.this.onListItemClick((android.widget.ListView) adapterView, view, i, j);
        }
    };

    @Override // android.app.Fragment
    public android.view.View onCreateView(android.view.LayoutInflater layoutInflater, android.view.ViewGroup viewGroup, android.os.Bundle bundle) {
        return layoutInflater.inflate(17367060, viewGroup, false);
    }

    @Override // android.app.Fragment
    public void onViewCreated(android.view.View view, android.os.Bundle bundle) {
        super.onViewCreated(view, bundle);
        ensureList();
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mList = null;
        this.mListShown = false;
        this.mListContainer = null;
        this.mProgressContainer = null;
        this.mEmptyView = null;
        this.mStandardEmptyView = null;
        super.onDestroyView();
    }

    public void onListItemClick(android.widget.ListView listView, android.view.View view, int i, long j) {
    }

    public void setListAdapter(android.widget.ListAdapter listAdapter) {
        boolean z = this.mAdapter != null;
        this.mAdapter = listAdapter;
        if (this.mList != null) {
            this.mList.setAdapter(listAdapter);
            if (!this.mListShown && !z) {
                setListShown(true, getView().getWindowToken() != null);
            }
        }
    }

    public void setSelection(int i) {
        ensureList();
        this.mList.setSelection(i);
    }

    public int getSelectedItemPosition() {
        ensureList();
        return this.mList.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        ensureList();
        return this.mList.getSelectedItemId();
    }

    public android.widget.ListView getListView() {
        ensureList();
        return this.mList;
    }

    public void setEmptyText(java.lang.CharSequence charSequence) {
        ensureList();
        if (this.mStandardEmptyView == null) {
            throw new java.lang.IllegalStateException("Can't be used with a custom content view");
        }
        this.mStandardEmptyView.lambda$setTextAsync$0(charSequence);
        if (this.mEmptyText == null) {
            this.mList.setEmptyView(this.mStandardEmptyView);
        }
        this.mEmptyText = charSequence;
    }

    public void setListShown(boolean z) {
        setListShown(z, true);
    }

    public void setListShownNoAnimation(boolean z) {
        setListShown(z, false);
    }

    private void setListShown(boolean z, boolean z2) {
        ensureList();
        if (this.mProgressContainer == null) {
            throw new java.lang.IllegalStateException("Can't be used with a custom content view");
        }
        if (this.mListShown == z) {
            return;
        }
        this.mListShown = z;
        if (z) {
            if (z2) {
                this.mProgressContainer.startAnimation(android.view.animation.AnimationUtils.loadAnimation(getContext(), 17432577));
                this.mListContainer.startAnimation(android.view.animation.AnimationUtils.loadAnimation(getContext(), 17432576));
            } else {
                this.mProgressContainer.clearAnimation();
                this.mListContainer.clearAnimation();
            }
            this.mProgressContainer.setVisibility(8);
            this.mListContainer.setVisibility(0);
            return;
        }
        if (z2) {
            this.mProgressContainer.startAnimation(android.view.animation.AnimationUtils.loadAnimation(getContext(), 17432576));
            this.mListContainer.startAnimation(android.view.animation.AnimationUtils.loadAnimation(getContext(), 17432577));
        } else {
            this.mProgressContainer.clearAnimation();
            this.mListContainer.clearAnimation();
        }
        this.mProgressContainer.setVisibility(0);
        this.mListContainer.setVisibility(8);
    }

    public android.widget.ListAdapter getListAdapter() {
        return this.mAdapter;
    }

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        android.view.View view = getView();
        if (view == null) {
            throw new java.lang.IllegalStateException("Content view not yet created");
        }
        if (view instanceof android.widget.ListView) {
            this.mList = (android.widget.ListView) view;
        } else {
            this.mStandardEmptyView = (android.widget.TextView) view.findViewById(com.android.internal.R.id.internalEmpty);
            if (this.mStandardEmptyView == null) {
                this.mEmptyView = view.findViewById(16908292);
            } else {
                this.mStandardEmptyView.setVisibility(8);
            }
            this.mProgressContainer = view.findViewById(com.android.internal.R.id.progressContainer);
            this.mListContainer = view.findViewById(com.android.internal.R.id.listContainer);
            android.view.View findViewById = view.findViewById(16908298);
            if (!(findViewById instanceof android.widget.ListView)) {
                throw new java.lang.RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
            }
            this.mList = (android.widget.ListView) findViewById;
            if (this.mList == null) {
                throw new java.lang.RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
            }
            if (this.mEmptyView != null) {
                this.mList.setEmptyView(this.mEmptyView);
            } else if (this.mEmptyText != null) {
                this.mStandardEmptyView.lambda$setTextAsync$0(this.mEmptyText);
                this.mList.setEmptyView(this.mStandardEmptyView);
            }
        }
        this.mListShown = true;
        this.mList.setOnItemClickListener(this.mOnClickListener);
        if (this.mAdapter != null) {
            android.widget.ListAdapter listAdapter = this.mAdapter;
            this.mAdapter = null;
            setListAdapter(listAdapter);
        } else if (this.mProgressContainer != null) {
            setListShown(false, false);
        }
        this.mHandler.post(this.mRequestFocus);
    }
}
