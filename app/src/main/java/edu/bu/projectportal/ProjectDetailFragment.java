package edu.bu.projectportal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProjectDetailFragment extends Fragment {

    private final static String TAG = ProjectDetailFragment.class.getSimpleName ();

    private int position = 0;
    private TextView titleTextView, summaryTextView;
    private CheckBox favCheckBox;


    public ProjectDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_detail, container, false);

        titleTextView = view.findViewById(R.id.projTitleTextViewId);
        summaryTextView = view.findViewById(R.id.projSummaryTextViewId);
        favCheckBox = view.findViewById(R.id.projFavoriteCheckbox);

        //get the project Id from the argument passed to this fragment
        position = 0;
        if (getArguments()!= null)
            position = getArguments().getInt("position");


        Log.d(TAG, " Project position: " + position);
        setProject(position);

        // When this checkbox is clicked, the project is marked as the
        // favorite project.
        favCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isFav) {
                Project project = Project.projects.get(position);
                project.setFavorite(isFav);
                // update the database
                ProjectPortalDatabase
                        .getInstance(compoundButton.getContext())
                        .projectDao().update(project);
            }
        });


        // When this button is clicked, the Project Edit fragment will be displayed.

        ImageButton editButton  = view.findViewById(R.id.EditBtn);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // To switch from the Project Detail fragment to the Project Edit fragment
                // for the correct project, we need to call the method in the parent activity
                // The parent activity will implement this edit() method to switch to the
                // Project Edit fragment.

                ((EditFragmentInterface) (view.getContext())).edit(position, false);
            }
        });

        ImageButton delButton  = view.findViewById(R.id.delBtn);
        delButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // To switch from the Project Detail fragment to the Project Edit fragment
                // for the correct project, we need to call the method in the parent activity
                // The parent activity will implement this edit() method to switch to the
                // Project Edit fragment.

                ((EditFragmentInterface) (view.getContext())).del(position);
            }
        });



        return view;
    }

    //make sure all fields display the correct project information
    public void setProject(int projPos) {
        position = projPos;

        Log.d("position", ""+ position);

        titleTextView.setText(Project.projects.get(position).getTitle());
        summaryTextView.setText(Project.projects.get(position).getSummary());
        favCheckBox.setChecked(Project.projects.get(position).isFavorite());
        Log.d("favorite setproject ",favCheckBox.isChecked()+" "
                + Project.projects.get(position).isFavorite());

    }

    public int getProjectPos(){
        return position;
    }
}
