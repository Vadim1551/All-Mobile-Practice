package com.mirea.fedotov.loadermanger;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MyLoader extends AsyncTaskLoader<String> {
    private String editText;
    public String result;
    public static final String ARG_WORD = "asd";

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null)
            editText = args.getString(ARG_WORD);
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        List<String> list = new ArrayList<>();
        list.add(editText);
        Collections.shuffle(list);
        result = list.toString();
        return result;
    }
}