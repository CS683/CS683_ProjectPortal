package edu.bu.projectportal.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import edu.bu.projectportal.database.Project;
import edu.bu.projectportal.database.ProjectPortalDatabase;
import edu.bu.projectportal.R;

//import edu.bu.projectportal.database.ProjectDao;

public class AddProjectActivity extends AppCompatActivity {
  //  ProjectDao projectDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    public void onClickSubmit(View view){

        EditText titleEditView =  findViewById(R.id.titleEditTextId);
        String title = titleEditView.getText().toString();

        EditText descEditView= findViewById(R.id.descEditTextId);
        String summary = descEditView.getText().toString();

        Project project = new Project(title, summary);

        // insert the project into the database, get the generated id
        long id = ProjectPortalDatabase.getInstance(this).projectDao().
                insertProject(project);

        // update the projects list
        project.setId(id);
        Project.projects.add(project);

        Intent intent = new Intent(this, ProjectsListActivity.class);
        startActivity(intent);
    }

    public void onClickCancel(View v){
        Intent intent = new Intent(this, ProjectsListActivity.class);
        startActivity(intent);
    }


}
