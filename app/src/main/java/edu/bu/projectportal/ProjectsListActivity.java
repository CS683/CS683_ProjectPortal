package edu.bu.projectportal;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

public class ProjectsListActivity extends AppCompatActivity
        implements ProjectListAdapter.Listener, EditFragmentInterface{

    ProjectDetailFragment projectDetailFragment;
    ProjectEditFragment projectEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_list);

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
    public void onItemClick(int projectId) {

        // This case is for the devices with the smaller screen.
        // In this case, this activity only has one fragment, list fragment.
        // It doesn't have the detail fragment
        if (projectDetailFragment == null)
        {
            // use intent to navigate to the detail activity
            Intent intent = new Intent(this, ProjectDetailActivity.class);
            // pass the project id through the extra field in the intent
            intent.putExtra("ProjectId", projectId);
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
            projectDetailFragment.setProject(projectId);

    }

    // This method implements the edit() method
    // defined in the Interface EditFragmentInterface,
    // which is required for the activity to contain the detail
    // fragment and the edit fragment
    public void edit(int projectId, boolean done){
        FragmentManager fragManager = getSupportFragmentManager();
        // get the reference to the FragmentTransaction object
        FragmentTransaction transaction = fragManager.beginTransaction();

        if (!done) {
            // This case is to switch the edit fragment
            // replace detail fragment with editfragment
            transaction.replace(R.id.proDetailEditfragContainer, projectEditFragment);
            transaction.commitNow();
            // pass the project Id to the edit fragment
            projectEditFragment.setProject(projectId);
        }
        else {
            // This case is to switch back to the detail fragment
            // when the edit is done.
            // replace edit fragment with detail fragment
            transaction.replace(R.id.proDetailEditfragContainer, projectDetailFragment);
            transaction.commitNow();
            // pass the project Id to the detail fragment
            projectDetailFragment.setProject(projectId);
            ProjectsListFragment projectListFragment =
                    (ProjectsListFragment)fragManager.findFragmentById(R.id.listfragment);
            projectListFragment.updateUI();

        }
    }


}
