/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.materialme;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/***
 * Main Activity for the Material Me app, a mock sports news application with poor design choices
 */
public class MainActivity extends AppCompatActivity {

    //Member variables
    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the RecyclerView
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        // add code to onCreate() to get the integer from the integers.xml resource file
        //for the orientation
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        //Set the Layout Manager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,gridColumnCount));

        //Initialize the ArrayLIst that will contain the data
        mSportsData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        //Get the data
        initializeData();

        //Use the gridColumnCount variable to disable the swipe action
        // (set swipeDirs to zero) when there is more than one column:
        int swipDirs;
        if (gridColumnCount>1){
            swipDirs = 0;
        }
        else {swipDirs= ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;}
        //ItemTouchHelper.SimpleCallback lets you define which directions are supported for swiping
        // and moving list items, and implement the swiping and moving behavior.
        //Because we are only implementing swipe to dismiss at the moment, you should pass in 0 for
        // the supported move directions and ItemTouchHelper.LEFT | ItemTouchHelper.
        // RIGHT for the supported swipe directions:
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.DOWN | ItemTouchHelper.UP, swipDirs) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull
                    RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //get the original and target index from the second and third argument passed
                // in (corresponding to the original and target view holders).
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                //pass in the dataset, and the initial and final indexes:
                Collections.swap(mSportsData, from, to);
                //Notify the adapter that the item was moved, passing in the old and new indexes,
                // and change the return statement to true:
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // implement the desired behavior in onSwiped()
                mSportsData.remove(viewHolder.getAdapterPosition());
                //To allow the RecyclerView to animate the deletion properly,
                // you must also call notifyItemRemoved(),
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        //call attachToRecyclerView() on the ItemTouchHelper instance to add it to your RecyclerView:
        helper.attachToRecyclerView(mRecyclerView);

    }

    /**
     * Method for initializing the sports data from resources.
     */
    private void initializeData() {
        //Get the resources from the XML file
        String[] sportsList = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
        TypedArray sportsImageResources =
                getResources().obtainTypedArray(R.array.sports_images);

        //Clear the existing data (to avoid duplication)
        mSportsData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for(int i=0;i<sportsList.length;i++){
            mSportsData.add(new Sport(sportsList[i],sportsInfo[i],
                    sportsImageResources.getResourceId(i,0)));
        }
        //Clean up the data in the typed array once you have created the Sport data ArrayList
        sportsImageResources.recycle();

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }
//to reset the data when FAB (Reset button is clicked
    public void resetSports(View view) {
        initializeData();
    }
}
