package edu.bu.projectportal.UI;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import edu.bu.projectportal.database.Project;
import edu.bu.projectportal.database.ProjectPortalDatabase;
import edu.bu.projectportal.R;

public class ProjectEditFragment extends Fragment {

    private int position = 0;
    private EditText titleEditText, summaryEditText;
    private CheckBox favCheckBox1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_project_edit, container, false);

        // In the Project Edit fragment, we use EditText instead of TextView
        titleEditText = view.findViewById(R.id.projTitleEditTextId);
        summaryEditText = view.findViewById(R.id.projSummaryEditTextId);

        // get the project id from the argument.
        position = 0;
        if (getArguments()!= null)
            position = getArguments().getInt("position");

        setProject(position);

        Button doneButton  = view.findViewById(R.id.DoneBtnId);

        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Similarly, To switch from the Project Edit fragment to the Project Edit fragment
                // for the correct project, we need to call the method in the parent activity
                // The parent activity will implement this edit() method to switch back to the
                // Project Detail fragment from Project Edit fragment


                //update project in the list
                Project project = Project.projects.get(position);
                project.setTitle(titleEditText.getText().toString());
                project.setSummary(summaryEditText.getText().toString());

                // update the database
                ProjectPortalDatabase
                        .getInstance(view.getContext())
                        .projectDao().update(project);


                ((EditFragmentInterface) (view.getContext()))
                        .edit(position, true);
            }
        });

        return view;

    }

    public void setProject(int projPos) {
        position = projPos;
        Project project = Project.projects.get(position);
        // if we use project id instead of position
//        Project project = ProjectPortalDatabase
//                .getInstance(this.getContext())
//                .projectDao().findById(projectId);
        if (project != null) {
            titleEditText.setText(project.getTitle());
            summaryEditText.setText(project.getSummary());
        }
    }

}