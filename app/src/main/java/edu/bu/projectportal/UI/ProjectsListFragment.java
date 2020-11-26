package edu.bu.projectportal.UI;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.bu.projectportal.database.Project;
import edu.bu.projectportal.database.ProjectPortalDatabase;
import edu.bu.projectportal.R;

public class ProjectsListFragment extends Fragment {

    private ProjectListAdapter projectListAdapter;

    public ProjectsListFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_projects_list, container, false);

        RecyclerView projectsListRecyclerView =
                (v.findViewById(R.id.projectlist_recyclerview));

        Project.projects = ProjectPortalDatabase
                .getInstance(this.getContext()).projectDao().getAll();
        projectListAdapter = new ProjectListAdapter(Project.projects);

        projectsListRecyclerView.setAdapter(projectListAdapter);

        projectListAdapter.setListener((ProjectListAdapter.Listener)getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        projectsListRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }

    // whenever the fragment is bought to the foreground,
    // onResume is called to refresh the UI.
    public void onResume(){
        super.onResume();
        updateUI();
    }

    // update the recycler view whenever the data changes
    public void updateUI(){
        projectListAdapter.notifyDataSetChanged();
    }

}