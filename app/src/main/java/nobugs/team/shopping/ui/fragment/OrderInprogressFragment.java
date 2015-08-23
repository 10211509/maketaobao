package nobugs.team.shopping.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OrderInprogressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderInprogressFragment extends BaseFragment<> {

    public static OrderInprogressFragment newInstance() {
        OrderInprogressFragment fragment = new OrderInprogressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public OrderInprogressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_inprogress, container, false);
    }
}
