package com.coshx.chocolatine.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.coshx.chocolatine.helpers.AdapterHelper;

import java.util.List;

/**
 * @class SmartAdapter
 * @brief
 */
public abstract class SmartAdapter<T> extends BaseAdapter {
    private List<T>        _items;
    private Context        _context;
    private LayoutInflater _inflater;

    public SmartAdapter(List<T> items, Context context) {
        this._items = items;
        this._context = context;
        this._inflater = LayoutInflater.from(this._context);
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    @Override
    public Object getItem(int position) {
        return _items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getItems() {
        return _items;
    }

    public T itemAt(int position) {
        return _items.get(position);
    }

    public Context getContext() {
        return _context;
    }

    public LayoutInflater getLayoutInflater() {
        return _inflater;
    }

    public <U extends View> U recycle(View convertView, int layoutId, ViewGroup parent) {
        return AdapterHelper.recycle(convertView, _inflater, layoutId, parent);
    }
}
