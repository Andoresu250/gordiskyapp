package com.andoresu.gordiskyapp.core.resume;

import com.andoresu.gordiskyapp.core.resume.data.Resume;
import com.andoresu.gordiskyapp.utils.BaseView;

import java.util.Map;

public interface ResumeContract {
    interface View extends BaseView {

        void showResume(Resume resume);

    }

    interface  ActionsListener {

        void getResume(Map<String, String> options);

    }

    interface InteractionListener {



    }
}
