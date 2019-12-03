package ru.irregularexpression.atostest.meetingrooms.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public abstract class TaskFragment extends Fragment {
    // Define the listener of the interface type
    // listener is the activity itself
    private TaskCallbacksListener mListener;

    // Define the events that the fragment will use to communicate
    public interface TaskCallbacksListener {
        void onPreExecute();
        void onProgressUpdate(int percent);
        void onCancelled();
        void onPostExecute();
    }

    // Store the listener that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TaskCallbacksListener) {
            mListener = (TaskCallbacksListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement TaskFragment.TaskCallbacksListener");
        }
    }

    public abstract String getFragmentTag();

    protected TaskCallbacksListener getListener() {
        return mListener;
    }

}
