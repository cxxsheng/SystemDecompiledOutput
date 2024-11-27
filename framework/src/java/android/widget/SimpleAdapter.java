package android.widget;

/* loaded from: classes4.dex */
public class SimpleAdapter extends android.widget.BaseAdapter implements android.widget.Filterable, android.widget.ThemedSpinnerAdapter {
    private java.util.List<? extends java.util.Map<java.lang.String, ?>> mData;
    private android.view.LayoutInflater mDropDownInflater;
    private int mDropDownResource;
    private android.widget.SimpleAdapter.SimpleFilter mFilter;
    private java.lang.String[] mFrom;
    private final android.view.LayoutInflater mInflater;
    private int mResource;
    private int[] mTo;
    private java.util.ArrayList<java.util.Map<java.lang.String, ?>> mUnfilteredData;
    private android.widget.SimpleAdapter.ViewBinder mViewBinder;

    public interface ViewBinder {
        boolean setViewValue(android.view.View view, java.lang.Object obj, java.lang.String str);
    }

    public SimpleAdapter(android.content.Context context, java.util.List<? extends java.util.Map<java.lang.String, ?>> list, int i, java.lang.String[] strArr, int[] iArr) {
        this.mData = list;
        this.mDropDownResource = i;
        this.mResource = i;
        this.mFrom = strArr;
        this.mTo = iArr;
        this.mInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mData.size();
    }

    @Override // android.widget.Adapter
    public java.lang.Object getItem(int i) {
        return this.mData.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        return createViewFromResource(this.mInflater, i, view, viewGroup, this.mResource);
    }

    private android.view.View createViewFromResource(android.view.LayoutInflater layoutInflater, int i, android.view.View view, android.view.ViewGroup viewGroup, int i2) {
        if (view == null) {
            view = layoutInflater.inflate(i2, viewGroup, false);
        }
        bindView(i, view);
        return view;
    }

    public void setDropDownViewResource(int i) {
        this.mDropDownResource = i;
    }

    @Override // android.widget.ThemedSpinnerAdapter
    public void setDropDownViewTheme(android.content.res.Resources.Theme theme) {
        if (theme == null) {
            this.mDropDownInflater = null;
        } else if (theme == this.mInflater.getContext().getTheme()) {
            this.mDropDownInflater = this.mInflater;
        } else {
            this.mDropDownInflater = android.view.LayoutInflater.from(new android.view.ContextThemeWrapper(this.mInflater.getContext(), theme));
        }
    }

    @Override // android.widget.ThemedSpinnerAdapter
    public android.content.res.Resources.Theme getDropDownViewTheme() {
        if (this.mDropDownInflater == null) {
            return null;
        }
        return this.mDropDownInflater.getContext().getTheme();
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public android.view.View getDropDownView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        return createViewFromResource(this.mDropDownInflater == null ? this.mInflater : this.mDropDownInflater, i, view, viewGroup, this.mDropDownResource);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void bindView(int i, android.view.View view) {
        boolean z;
        java.util.Map<java.lang.String, ?> map = this.mData.get(i);
        if (map == null) {
            return;
        }
        android.widget.SimpleAdapter.ViewBinder viewBinder = this.mViewBinder;
        java.lang.String[] strArr = this.mFrom;
        int[] iArr = this.mTo;
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            android.view.View findViewById = view.findViewById(iArr[i2]);
            if (findViewById != 0) {
                java.lang.Object obj = map.get(strArr[i2]);
                java.lang.String str = "";
                java.lang.String obj2 = obj == null ? "" : obj.toString();
                if (obj2 != null) {
                    str = obj2;
                }
                if (viewBinder == null) {
                    z = false;
                } else {
                    z = viewBinder.setViewValue(findViewById, obj, str);
                }
                if (z) {
                    continue;
                } else if (findViewById instanceof android.widget.Checkable) {
                    if (obj instanceof java.lang.Boolean) {
                        ((android.widget.Checkable) findViewById).setChecked(((java.lang.Boolean) obj).booleanValue());
                    } else if (findViewById instanceof android.widget.TextView) {
                        setViewText((android.widget.TextView) findViewById, str);
                    } else {
                        throw new java.lang.IllegalStateException(findViewById.getClass().getName() + " should be bound to a Boolean, not a " + (obj == null ? "<unknown type>" : obj.getClass()));
                    }
                } else if (findViewById instanceof android.widget.TextView) {
                    setViewText((android.widget.TextView) findViewById, str);
                } else if (findViewById instanceof android.widget.ImageView) {
                    if (obj instanceof java.lang.Integer) {
                        setViewImage((android.widget.ImageView) findViewById, ((java.lang.Integer) obj).intValue());
                    } else {
                        setViewImage((android.widget.ImageView) findViewById, str);
                    }
                } else {
                    throw new java.lang.IllegalStateException(findViewById.getClass().getName() + " is not a  view that can be bounds by this SimpleAdapter");
                }
            }
        }
    }

    public android.widget.SimpleAdapter.ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setViewBinder(android.widget.SimpleAdapter.ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    public void setViewImage(android.widget.ImageView imageView, int i) {
        imageView.setImageResource(i);
    }

    public void setViewImage(android.widget.ImageView imageView, java.lang.String str) {
        try {
            imageView.setImageResource(java.lang.Integer.parseInt(str));
        } catch (java.lang.NumberFormatException e) {
            imageView.setImageURI(android.net.Uri.parse(str));
        }
    }

    public void setViewText(android.widget.TextView textView, java.lang.String str) {
        textView.lambda$setTextAsync$0(str);
    }

    @Override // android.widget.Filterable
    public android.widget.Filter getFilter() {
        if (this.mFilter == null) {
            this.mFilter = new android.widget.SimpleAdapter.SimpleFilter();
        }
        return this.mFilter;
    }

    private class SimpleFilter extends android.widget.Filter {
        private SimpleFilter() {
        }

        @Override // android.widget.Filter
        protected android.widget.Filter.FilterResults performFiltering(java.lang.CharSequence charSequence) {
            android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
            if (android.widget.SimpleAdapter.this.mUnfilteredData == null) {
                android.widget.SimpleAdapter.this.mUnfilteredData = new java.util.ArrayList(android.widget.SimpleAdapter.this.mData);
            }
            if (charSequence == null || charSequence.length() == 0) {
                java.util.ArrayList arrayList = android.widget.SimpleAdapter.this.mUnfilteredData;
                filterResults.values = arrayList;
                filterResults.count = arrayList.size();
            } else {
                java.lang.String lowerCase = charSequence.toString().toLowerCase();
                java.util.ArrayList arrayList2 = android.widget.SimpleAdapter.this.mUnfilteredData;
                int size = arrayList2.size();
                java.util.ArrayList arrayList3 = new java.util.ArrayList(size);
                for (int i = 0; i < size; i++) {
                    java.util.Map map = (java.util.Map) arrayList2.get(i);
                    if (map != null) {
                        int length = android.widget.SimpleAdapter.this.mTo.length;
                        for (int i2 = 0; i2 < length; i2++) {
                            java.lang.String[] split = ((java.lang.String) map.get(android.widget.SimpleAdapter.this.mFrom[i2])).split(" ");
                            int length2 = split.length;
                            int i3 = 0;
                            while (true) {
                                if (i3 >= length2) {
                                    break;
                                }
                                if (!split[i3].toLowerCase().startsWith(lowerCase)) {
                                    i3++;
                                } else {
                                    arrayList3.add(map);
                                    break;
                                }
                            }
                        }
                    }
                }
                filterResults.values = arrayList3;
                filterResults.count = arrayList3.size();
            }
            return filterResults;
        }

        @Override // android.widget.Filter
        protected void publishResults(java.lang.CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {
            android.widget.SimpleAdapter.this.mData = (java.util.List) filterResults.values;
            if (filterResults.count > 0) {
                android.widget.SimpleAdapter.this.notifyDataSetChanged();
            } else {
                android.widget.SimpleAdapter.this.notifyDataSetInvalidated();
            }
        }
    }
}
