package edu.bu.projectportal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.bu.projectportal.database.Project;
import edu.bu.projectportal.database.ProjectPortalDatabase;
import edu.bu.projectportal.R;

public class ProjectsListActivity extends AppCompatActivity
        implements ProjectListAdapter.Listener, EditFragmentInterface {

    ProjectDetailFragment projectDetailFragment;
    ProjectEditFragment projectEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_main);

        // added a floating action button to add a new project
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddProjectActivity.class);
                startActivity(intent);
            }
        });


        // This case is for the wider screen, where detail fragment and
        // list fragment are on the same screen side by side.
        // In this case, it will use
        if (findViewById(R.id.proDetailEditfragContainer) !=null) {

            projectDetailFragment = new ProjectDetailFragment();
            projectEditFragment = new ProjectEditFragment();

            projectDetailFragment.setArguments(getIntent().getExtras());
            // get the reference to the FragmentManger object
            FragmentManager fragManager = getSupportFragmentManager();
            // get the reference to the FragmentTransaction object
            FragmentTransaction transaction = fragManager.beginTransaction();
            // add the fragment into the transaction
            transaction.add(R.id.proDetailEditfragContainer, projectDetailFragment);
            // commit the transaction.
            transaction.commitNow();
        }
    }

    // This activity implements the onItemClick() method defined in the
    // interface ProjectListAdapter.Listener. This method is called
    // when an item in the recycler view is clicked.

    @Override
    public void onItemClick(long projectId, int position) {

        // This case is for the devices with the smaller screen.
        // In this case, this activity only has one fragment, list fragment.
        // It doesn't have the detail fragment
        if (projectDetailFragment == null)
        {
            // use intent to navigate to the detail activity
            Intent intent = new Intent(this, ProjectDetailActivity.class);
            // pass the project id through the extra field in the intent
            intent.putExtra("ProjectId", projectId);
            intent.putExtra("position", position);
            // send the intent to the receiving activity
            this.startActivity(intent);

        }
        // This case is for the devices with the larger screen.
        // In this case, this activity contains two fragments, one is
        // the list fragment and the other is the detail fragment.
        // Since the detail fragment is contained in this activity,
        // we can simply call the setProject() method defined in
        // the detail fragment.
        else
            projectDetailFragment.setProject(position);


    }

    // This method implements the edit() method
    // defined in the Interface EditFragmentInterface,
    // which is required for the activity to contain the detail
    // fragment and the edit fragment
    public void edit(int position, boolean done){
        FragmentManager fragManager = getSupportFragmentManager();
        // get the reference to the FragmentTransaction object
        FragmentTransaction transaction = fragManager.beginTransaction();

        if (!done) {
            // This case is to switch the edit fragment
            // replace detail fragment with editfragment
            transaction.replace(R.id.proDetailEditfragContainer, projectEditFragment);
            transaction.commitNow();
            // pass the project Id to the edit fragment
            projectEditFragment.setProject(position);
        }
        else {
            // This case is to switch back to the detail fragment
            // when the edit is done.
            // replace edit fragment with detail fragment
            transaction.replace(R.id.proDetailEditfragContainer, projectDetailFragment);
            transaction.commitNow();
            // pass the project Id to the detail fragment
            projectDetailFragment.setProject(position);
            ProjectsListFragment projectListFragment =
                    (ProjectsListFragment)fragManager.findFragmentById(R.id.listfragment);
            projectListFragment.updateUI();

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

       // update the list fragment UI
        ProjectsListFragment projectListFragment =
                (ProjectsListFragment)getSupportFragmentManager().findFragmentById(R.id.listfragment);
        projectListFragment.updateUI();
    }



}
