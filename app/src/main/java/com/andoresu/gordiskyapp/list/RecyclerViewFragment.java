package com.andoresu.gordiskyapp.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.utils.BaseFragment;
import com.andoresu.gordiskyapp.utils.BaseListResponse;
import com.andoresu.gordiskyapp.utils.PaginationScrollListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class RecyclerViewFragment<T> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        SlyCalendarDialog.Callback {

    public static final String TAG = "GORDISKY_" + RecyclerViewFragment.class.getSimpleName();

    private static final int PAGE_START = 1;
    private boolean isLoading = false;

    @BindView(R.id.listRecyclerView)
    public RecyclerView listRecyclerView;

    @BindView(R.id.listSwipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.emptyTextView)
    public TextView emptyTextView;

    private LinearLayoutManager linearLayoutManager;

    public int currentPage = PAGE_START;

    public RecyclerViewAdapter<T> viewAdapter;

    public boolean addSearch = false;

    public SearchView searchView;

    public String searchQuery = null;

    public boolean showCalendar = false;

    public Date startDate;
    public Date endDate;
    public Date date;

    public RecyclerViewFragment(){}

    public static <T>RecyclerViewFragment<T> newInstance() {
        Bundle args = new Bundle();
        RecyclerViewFragment<T> fragment = new RecyclerViewFragment<>();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void handleView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(this.getContext(), 1, false);
        listRecyclerView.setLayoutManager(linearLayoutManager);
        listRecyclerView.setAdapter(viewAdapter);
        listRecyclerView.addOnScrollListener(getPaginationScrollListener());
        isEmpty();
    }

    public void setViewAdapter(RecyclerViewAdapter<T> viewAdapter){
        this.viewAdapter = viewAdapter;
    }

    @Override
    public  void onRefresh(){
        onRefresh(true);
    }

    public  void onRefresh(boolean clean){
        showLoading(true);
        if(clean){
            Log.i(TAG, "onRefresh: clean");
            currentPage = 1;
            viewAdapter.clear();
            viewAdapter.set(new ArrayList<>());
        }else{
            Log.i(TAG, "onRefresh: no clean");
        }
        Log.i(TAG, "onRefresh: currentPage " + currentPage);
    }

    public void showLoading(boolean show){
        isLoading = show;
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(show);
        }
    }

    public void isEmpty(){
        if(emptyTextView == null){
            return;
        }
        if(viewAdapter == null || viewAdapter.items == null || viewAdapter.items.isEmpty()){
            emptyTextView.setVisibility(View.VISIBLE);
        }else{
            emptyTextView.setVisibility(View.GONE);
        }
    }

    public int getTotalItems(){
        return 0;
    }

    public int getTotalPageCount(){
        Log.i(TAG, "getTotalPageCount: getTotalItems() " + getTotalItems());
        Log.i(TAG, "getTotalPageCount: BaseListResponse.PER_PAGE " + BaseListResponse.PER_PAGE);
        double a = (double) getTotalItems()/ (double) BaseListResponse.PER_PAGE;
        double totalPages = Math.ceil(a);
        Log.i(TAG, "getTotalPageCount totalPages : " + totalPages);
        return (int) totalPages;
    }


    @Override
    public int getLayoutId(){
        return R.layout.fragment_list;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Log.i(TAG, "onCreateOptionsMenu: " + addSearch);
        if(addSearch){
            menuInflater.inflate(R.menu.main, menu);
            MenuItem searchItem = menu.findItem(R.id.action_search);
            searchView = (SearchView) searchItem.getActionView();
            searchView.setMaxWidth(Integer.MAX_VALUE);
            MenuItem calendarItem = menu.findItem(R.id.action_calendar);
            calendarItem.setVisible(showCalendar);
            calendarItem.setOnMenuItemClickListener(menuItem -> {
                openCalendarDialog();
                return true;
            });

            searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
//                    calendarItem.setVisible(false);
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    calendarItem.setVisible(showCalendar);
                    currentPage = 1;
                    searchQuery = null;
                    onRefresh(true);
                    return true;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    currentPage = 1;
                    searchQuery = query;
                    onRefresh(true);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
//                    currentPage = 1;
//                    searchQuery = newText;
//                    onRefresh(true);
                    return true;
                }
            });
        }else{
            super.onCreateOptionsMenu(menu, menuInflater);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    public void openCalendarDialog(){
        new SlyCalendarDialog()
                .setSingle(false)
                .setCallback(this)
                .show(getChildFragmentManager(), "TAG_SLYCALENDAR");
    }

    private PaginationScrollListener getPaginationScrollListener(){
        return new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if(!isLastPage()){
                    Log.i(TAG, "loadMoreItems: no last page");
                    isLoading = true;
                    currentPage++;
                    onRefresh(false);
                }else{
                    Log.i(TAG, "loadMoreItems: is last page");
                }
            }

            @Override
            public int getTotalPageCount() {
                return RecyclerViewFragment.this.getTotalPageCount();
            }

            @Override
            public boolean isLastPage() {
                return currentPage >= getTotalPageCount();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        };
    }


    @Override
    public void onCancelled() {

    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        if(firstDate != null && secondDate == null){
            date = firstDate.getTime();
        }else{
            date = null;
            if(firstDate != null) {
                startDate = firstDate.getTime();
            }else{
                startDate = null;
            }
            if(secondDate != null) {
                endDate = secondDate.getTime();
            }else{
                endDate = null;
            }
        }
        if(startDate != null || endDate != null || date != null){
            onRefresh();
        }
    }
}
