package edu.bu.projectportal;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class ProjectEditFragment extends Fragment {

    private int projectId = 0;
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
        projectId = 0;
        if (getArguments()!= null)
            projectId = getArguments().getInt("ProjectId");

        setProject(projectId);


        Button doneButton  = view.findViewById(R.id.DoneBtnId);

        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Similarly, To switch from the Project Edit fragment to the Project Edit fragment
                // for the correct project, we need to call the method in the parent activity
                // The parent activity will implement this edit() method to switch back to the
                // Project Detail fragment from Project Edit fragment
                Project.projects[projectId].setTitle(titleEditText.getText().toString());
                Project.projects[projectId].setSummary(summaryEditText.getText().toString());
                ((EditFragmentInterface) (view.getContext())).edit(projectId, true);
            }
        });



        return view;

    }

    public void setProject(int projId) {
        projectId = projId;
        titleEditText.setText(Project.projects[projectId].getTitle());
        summaryEditText.setText(Project.projects[projectId].getSummary());
    }

}