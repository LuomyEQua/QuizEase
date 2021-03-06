package com.androidstrike.quizease;

import android.content.Intent;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.Database.Database;
import com.androidstrike.quizease.Interface.GoClickListener;
import com.androidstrike.quizease.Model.PendingCourses;
import com.androidstrike.quizease.Model.RegisteredCourses;
import com.androidstrike.quizease.ViewHolder.PendingTestViewHolder;
import com.androidstrike.quizease.ui.quiz.QuizActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestPendingFragment extends Fragment {

    private DatabaseReference pendingCourses;

    private RecyclerView recycler_pending;
    private ProgressBar progressBarPend;

    private CoordinatorLayout coordinatorLayout;

    List<RegisteredCourses> registeredCoursesList = new ArrayList<>();
    public static List<RegisteredCourses> listCourses = new ArrayList<>();



    private FirebaseRecyclerAdapter<PendingCourses, PendingTestViewHolder> pendingCourseAdapter;

    public TestPendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_test_pending, container, false);

        Log.e("EQUA", "addToCourses: ");

        //Firebase init
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        pendingCourses = database.getReference("Tests/Pending/Course");
        Log.e("EQUA", "addToCourses: 2");


        recycler_pending = v.findViewById(R.id.recycler_pending_tests);
        recycler_pending.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recycler_pending.setLayoutManager(layoutManager);

        progressBarPend = v.findViewById(R.id.test_pend_check_progress_bar);
        coordinatorLayout = v.findViewById(R.id.layout_coordinate);

        loadPendingTests();
        Log.e("EQUA", "addToCourses: 3");


        return v;
    }


    private void loadPendingTests() {
        pendingCourseAdapter = new FirebaseRecyclerAdapter<PendingCourses, PendingTestViewHolder>(
                PendingCourses.class, R.layout.pending_test_item, PendingTestViewHolder.class, pendingCourses
        ) {

            @Override
            protected void populateViewHolder(PendingTestViewHolder pendingTestViewHolder, final PendingCourses pendingCourses, int i) {

                registeredCoursesList = new Database(getActivity()).getCourses();
                Log.e("EQUA", "addToCourses: 4");

                String regC = String.valueOf(registeredCoursesList);


                    pendingTestViewHolder.txtCourseTitle.setText(pendingCourses.getCourseTitle());
                    pendingTestViewHolder.txtCourseCode.setText(pendingCourses.getCourseCode());

                Log.e("EQUA", "populateViewHolder: Courses Loaded");

                    pendingTestViewHolder.setGoClickListener(new GoClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            progressBarPend.setVisibility(View.VISIBLE);
                            //write only registered course test
                            for (RegisteredCourses course : registeredCoursesList){
                                if (course.getCourseTitle().equals(pendingCourses.getCourseTitle())){
                                    Log.e("EQUA", "onClick: "+pendingCourses.getCourseTitle() + " == "+course.getCourseTitle());
                                    Toast.makeText(getActivity(), "Can Write", Toast.LENGTH_SHORT).show();
                                    progressBarPend.setVisibility(View.GONE);

                                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                                    Common.courseId = pendingCourseAdapter.getRef(position).getKey();
                                    Common.courseName = pendingCourses.getCourseTitle();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    break;
                                }else {
                                    Log.e("EQUA", "onClick: "+pendingCourses.getCourseTitle() + " != "+course.getCourseTitle());
//                                    Toast.makeText(getActivity(), "Cannot Write", Toast.LENGTH_SHORT).show();
                                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Course not Registered!", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }

                            }
                            progressBarPend.setVisibility(View.INVISIBLE);
                        }

                    });

                        }

                };

        recycler_pending.setAdapter(pendingCourseAdapter);

    }
}
