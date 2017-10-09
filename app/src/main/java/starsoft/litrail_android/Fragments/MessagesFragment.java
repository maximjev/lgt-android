package starsoft.litrail_android.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import starsoft.litrail_android.MainActivity;
import starsoft.litrail_android.Model.Message;
import starsoft.litrail_android.Model.Demo.MessagesDemo;
import starsoft.litrail_android.R;

public class MessagesFragment extends Fragment {

    public static final String TAG = "MessagesFragment";

    private OnListFragmentInteractionListener mListener;

    public MessagesFragment() {
    }

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: duomenys iš atminties
//        if (getArguments() != null) {
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages_list, container, false);
        getActivity().setTitle("Pranešimai");
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MessagesRecyclerViewAdapter(MessagesDemo.ITEMS, mListener));
        }
        ((MainActivity)getActivity()).showHomeAsUp(false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
//        else {
//
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Message message);
    }
}
