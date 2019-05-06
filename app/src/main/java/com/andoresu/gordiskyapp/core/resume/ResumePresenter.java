package com.andoresu.gordiskyapp.core.resume;

import android.content.Context;

import com.andoresu.gordiskyapp.client.ObserverResponse;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.core.resume.data.Resume;
import com.andoresu.gordiskyapp.core.transactions.TransactionsContract;
import com.andoresu.gordiskyapp.core.transactions.TransactionsService;
import com.andoresu.gordiskyapp.security.SecureData;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ResumePresenter implements ResumeContract.ActionsListener {

    private final ResumeContract.View view;
    private final Context context;
    private final TransactionsService transactionsService;

    public ResumePresenter(ResumeContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.transactionsService = ServiceGenerator.createService(TransactionsService.class, SecureData.getToken());
    }

    @Override
    public void getResume(Map<String, String> options) {
        transactionsService.getResume(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<Resume>>(view) {
                    @Override
                    public void onNext(Response<Resume> response) {
                        super.onNext(response);
                        if (response.isSuccessful()) {
                            Resume resume = response.body();
                            view.showResume(resume);
                        }
                    }
                });
    }
}