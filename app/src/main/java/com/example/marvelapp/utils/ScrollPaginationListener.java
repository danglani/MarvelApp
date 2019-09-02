package com.example.marvelapp.utils;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ScrollPaginationListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;

    public ScrollPaginationListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

//        if (!isLoading() && !isLastPage()) {
        if (!isLoading()){
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

//    public abstract int getTotalPageCount();

//    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}