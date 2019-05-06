package com.andoresu.gordiskyapp.core.resume;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.core.resume.data.Resume;
import com.andoresu.gordiskyapp.utils.BaseDataModel;
import com.andoresu.gordiskyapp.utils.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class ResumeFragment extends BaseFragment implements ResumeContract.View, SlyCalendarDialog.Callback {

    @BindView(R.id.totalInvertedTextInputLayout)
    public TextInputLayout totalInvertedTextInputLayout;
    @BindView(R.id.totalInvertedEditText)
    public EditText totalInvertedEditText;
    @BindView(R.id.totalInvertedWithInterestTextInputLayout)
    public TextInputLayout totalInvertedWithInterestTextInputLayout;
    @BindView(R.id.totalInvertedWithInterestEditText)
    public EditText totalInvertedWithInterestEditText;
    @BindView(R.id.totalAssetsTextInputLayout)
    public TextInputLayout totalAssetsTextInputLayout;
    @BindView(R.id.totalAssetsEditText)
    public EditText totalAssetsEditText;
    @BindView(R.id.totalLiabilitiesTextInputLayout)
    public TextInputLayout totalLiabilitiesTextInputLayout;
    @BindView(R.id.totalLiabilitiesEditText)
    public EditText totalLiabilitiesEditText;
    @BindView(R.id.totalGainTextInputLayout)
    public TextInputLayout totalGainTextInputLayout;
    @BindView(R.id.totalGainEditText)
    public EditText totalGainEditText;

    @BindView(R.id.totalProfitEditText)
    public EditText totalProfitEditText;

    private ResumeContract.ActionsListener actionsListener;

    public Date startDate;
    public Date endDate;
    public Date date;

    public static ResumeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ResumeFragment fragment = new ResumeFragment();
        fragment.setArguments(args);
        fragment.setCustomTitle("Resumen");
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if(bundle != null){}
        actionsListener = new ResumePresenter(this, getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);
        MenuItem calendarItem = menu.findItem(R.id.action_calendar);
        calendarItem.setVisible(true);
        calendarItem.setOnMenuItemClickListener(menuItem -> {
            openCalendarDialog();
            return true;
        });

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

    @Override
    public void handleView() {
        actionsListener.getResume(getOptions());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_resume;
    }

    @Override
    public void showResume(Resume resume) {
        totalInvertedEditText.setText(resume.getTotalInverted());
        totalInvertedWithInterestEditText.setText(resume.getTotalInvertedWithInterest());
        totalAssetsEditText.setText(resume.getTotalAssets());
        totalLiabilitiesEditText.setText(resume.getTotalLiabilities());
        totalGainEditText.setText(resume.getTotalGain());
        totalProfitEditText.setText(resume.getTotalProfit());
    }

    @SuppressLint("SimpleDateFormat")
    private Map<String, String> getOptions(){
        Map<String, String> options = new HashMap<>();
        if(date != null){
            options.put("day", new SimpleDateFormat(BaseDataModel.DATE_FORMAT_SIMPLE_QUERY).format(date));
        }else{
            if(startDate != null){
                options.put("start_date", new SimpleDateFormat(BaseDataModel.DATE_FORMAT_SIMPLE_QUERY).format(startDate));
            }
            if(endDate != null){
                options.put("end_date", new SimpleDateFormat(BaseDataModel.DATE_FORMAT_SIMPLE_QUERY).format(endDate));
            }
        }
        return options;
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
            actionsListener.getResume(getOptions());
        }
    }
}
