package edu.bu.projectportal;


// This interface is used to define a method
// that needs to be implemented in the activity
// that contains ProjectEditFragment and ProjectDetailFragment
public interface EditFragmentInterface {
        void edit(int id, boolean done);
        void del(int id);
}

