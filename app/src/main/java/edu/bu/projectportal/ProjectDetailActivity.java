package edu.bu.projectportal;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class ProjectDetailActivity extends AppCompatActivity implements EditFragmentInterface {

    ProjectDetailFragment projectDetailFragment;
    ProjectEditFragment projectEditFragment;
    List<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        projects = ProjectPortalDatabase
                .getInstance(this)
                .projectDao().getAll();

        //add fragments dynamically

        //create detail fragment
        projectDetailFragment = new ProjectDetailFragment();
        //create the edit fragment
        projectEditFragment = new ProjectEditFragment();

        //pass the project id through the intent to the detail fragment
        projectDetailFragment.setArguments(getIntent().getExtras());

        // get the reference to the FragmentManger object
        FragmentManager fragManager = getSupportFragmentManager();
        // get the reference to the FragmentTransaction object
        FragmentTransaction transaction = fragManager.beginTransaction();
        // add the detail fragment into the activity
        transaction.add(R.id.proDetailfragContainer, projectDetailFragment);
        // commit the transaction.
        transaction.commitNow();




    }
    // this method is called when the Next button is clicked,
    // so that the next project is shown on the screen
    public void onClick(View view){

        if (projectDetailFragment!= null &&
                projectDetailFragment.isVisible()) {
            int id = projectDetailFragment.getProjectPos();
            projectDetailFragment.setProject((id + 1) % Project.projects.size());
        }
    }

    // This method implements the edit() method
    // defined in the Interface EditFragmentInterface,
    // which is required for the activity to contain the detail
    // fragment and the edit fragment
    public void edit(int position, boolean done){
        FragmentManager fragManager = getSupportFragmentManager();
        // get the reference to the FragmentTransaction object
        FragmentTransaction transaction = fragManager.beginTransaction();
        Button nextButton = findViewById(R.id.button);

        if (!done) {
            // This case is to switch the edit fragment
            // replace detail fragment with editfragment
            transaction.replace(R.id.proDetailfragContainer, projectEditFragment);
            transaction.commitNow();
            // pass the project Id to the edit fragment
            projectEditFragment.setProject(position);
            //disable the next project button
            nextButton.setEnabled(false);
        }
        else {
            // This case is to switch back to the detail fragment
            // when the edit is done.
            // replace edit fragment with detail fragment
            transaction.replace(R.id.proDetailfragContainer, projectDetailFragment);
            transaction.commitNow();
            // pass the project Id to the detail fragment
            projectDetailFragment.setProject(position);
            //re-enable the next project button
            nextButton.setEnabled(true);
        }
    }

    // This method implements the del() method
    // defined in the Interface EditFragmentInterface,
    // which is required for the activity to contain the detail
    // fragment and the edit fragment
    public void del(int position){
        Project project = Project.projects.get(position);
        // show next project
        if (projectDetailFragment!= null &&
                projectDetailFragment.isVisible()) {
            int id = projectDetailFragment.getProjectPos();
            projectDetailFragment.setProject((position + 1) % Project.projects.size());
        }
        //delete from the list
        Project.projects.remove(position);

        // delete from the database
        // update the database
        ProjectPortalDatabase
                .getInstance(this.getApplicationContext())
                .projectDao().delete(project);
    }

}