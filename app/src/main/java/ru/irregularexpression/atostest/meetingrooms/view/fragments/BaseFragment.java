package ru.irregularexpression.atostest.meetingrooms.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import ru.irregularexpression.atostest.meetingrooms.presenter.BasePresenter;

import static android.content.Context.INPUT_METHOD_SERVICE;

public abstract class BaseFragment extends Fragment {
    // Define the listener of the interface type
    // listener is the activity itself
    private OnFragmentActionListener mListener;

    // Define the events that the fragment will use to communicate
    public interface OnFragmentActionListener {
        void onFragmentAction(String tag, Bundle data);
    }

    // Store the listener that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentActionListener) {
            mListener = (OnFragmentActionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement BaseFragment.OnFragmentActionListener");
        }
    }

    public void doOnActivity(String tag, Bundle data) {
        mListener.onFragmentAction(tag, data);
    }

    public void hideSoftInput() {
        if (getActivity() != null && getActivity().getCurrentFocus() != null) ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    public abstract String getFragmentTag();

}
