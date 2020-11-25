package edu.bu.projectportal;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectListViewHolder>{
    private List<Project> projects;
    private Listener listener;

    interface Listener {
        void onItemClick(int position);
    }

    public ProjectListAdapter(List<Project> projects){this.projects = projects;}

    @Override
    public int getItemCount() {
        return projects.size();
    }

    @NotNull
    @Override
    public ProjectListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item, parent,
                        false);
        return new ProjectListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectListViewHolder viewHolder, final int position){
        viewHolder.projTitleView.setText(projects.get(position).getTitle());
        // set the click listener
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get references to the parent activity
/*
                // if you only want to support small screen size,
                // had have each fragment in a separate activity
                // then simply use intent to communicate between
                // the list activity and detail activity
                // and pass the project Id through the intent extra field.

                Intent intent = new Intent(view.getContext(), ProjectDetailActivity.class);
                // pass the project id through the extra field in the intent
                intent.putExtra("ProjectId", position);
                // send the intent to the receiving activity
                view.getContext().startActivity(intent);

*/
                // this is not a good way to implement
                // ((ProjectsListActivity) view.getContext()).onItemClick(position);



                listener = (Listener)view.getContext();
                // response the click event
                // this will delegate to the parent activity
                // The parent activity will implement this method to
                // response the click event, which will go to the
                // Project Detail fragment to display the project detail
                // for this particular project

                listener.onItemClick(position);
            }
        });


    }

    public void setListener(Listener listener){
        this.listener = listener;
    }


    //ViewHolder inner class
    public static class ProjectListViewHolder extends RecyclerView.ViewHolder {
        private TextView projTitleView;
        private CardView cardView;
        // get the reference of each children view
        public ProjectListViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            projTitleView = itemView.findViewById(R.id.projListTitleTextViewId);
        }
    }
}
