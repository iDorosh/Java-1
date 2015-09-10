package com.idorosh.just_testing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class ItWorked extends AppCompatActivity{
    String newString;

    //List to hold states that the user adds
    ArrayList<String> createdStrings = new ArrayList<>();

    //Array of states to verify that the user has entered a valid state
    String[] states = new String[] {
            "alabama", "alaska", "arizona", "arkansas", "california", "colorado", "connecticut", "delaware", "florida", "georgia", "hawaii", "idaho", "illinois", "indiana", "iowa", "kansas", "kentucky", "louisiana", "maine", "maryland", "massachusetts", "michigan", "minnesota", "mississippi", "missouri", "montana", "nebraska", "nevada","new hampshire","new jersey","new mexico","new york","north carolina","north dakota","ohio","oklahoma","oregon","pennsylvania","rhode island","south carolina","south dakota","tennessee","utah","vermont","virginia","washington","west virginia","wisconsin","wyoming"
    };

    String[] stringArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets the default number of items in createdstrings on start up.
        TextView numberEntries = (TextView) findViewById(R.id.itemNumber);
        String test = Integer.toString(createdStrings.size());
        numberEntries.setText(test);

        //Setting average label to 0 on start up.
        TextView startAverage = (TextView) findViewById(R.id.averageLabel);
        startAverage.setText("0");

        //Runs addState method when the add button is clicked
        View myButton = findViewById(R.id.button);
        myButton.setOnClickListener(addState);

        //Runs findState method when the find button is clicked
        View findButton = findViewById(R.id.button2);
        findButton.setOnClickListener(findState);

        View getAllButton = findViewById(R.id.allData);
        getAllButton.setOnClickListener(showAllData);

        //Setting the hint text in the index text field based on items in createdStrings
        int range = createdStrings.size();
        EditText hint = (EditText) findViewById(R.id.editText);

        if (createdStrings.size()>0)
        {
            hint.setHint("Enter Index 0-"+Integer.toString(range));
        }
        else
        {
            hint.setHint("First, Add some States");
        }
    }

    private View.OnClickListener showAllData = new View.OnClickListener() {
        String combined;

        @Override
        public void onClick(View v) {

            combined = null;

            for(int i =0; i < createdStrings.size(); i++)
            {
                if (i==0){
                    combined = createdStrings.get(i);
                }
                else
                {
                    combined = combined+", "+createdStrings.get(i);
                }

            }

            new AlertDialog.Builder(ItWorked.this)
                    .setTitle("All States")
                    .setMessage(combined.toUpperCase())
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
        }
    };



    private View.OnClickListener addState = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            final TextView label = (TextView) findViewById(R.id.currentText);
            TextView numberEntries = (TextView) findViewById(R.id.itemNumber);
            TextView averageEntries = (TextView) findViewById(R.id.averageLabel);

            //Sets the text field entry to lower case to varify if that state already exists
            newString = label.getText().toString().toLowerCase();
            if (label.getText().toString().equals(""))
            {
                //If text field is empty then toast will display with a message, State Empty
                Toast.makeText(getApplicationContext(),
                        "State empty", Toast.LENGTH_LONG)
                        .show();
            }
            else
            {
                //For loop to verify that the user entered a valid state.
                int verification = 0;
                for(int i =0; i < states.length; i++)
                {
                    if (newString.contains(states[i]))
                    {
                        verification = 1;
                    }
                }

                //If verification of state passed
                if (verification == 1) {
                    //verification if the state already exists in the array
                    if (createdStrings.contains(newString)) {
                        label.setText("");

                        //Toast notification for state already exists.
                        Toast.makeText(getApplicationContext(),
                                "State already exists", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        createdStrings.add(newString);
                        label.setText("");
                        float averageValue = findMedian();

                        //State added toast notification
                        averageEntries.setText(Float.toString(averageValue));
                        Toast.makeText(getApplicationContext(),
                                "State Added", Toast.LENGTH_LONG)
                                .show();

                        int range = createdStrings.size();
                        EditText hint = (EditText) findViewById(R.id.editText);

                        //If statedment to change the text hint in state field.
                        if (createdStrings.size() > 0) {
                            hint.setHint("Enter Index 0-" + Integer.toString(range - 1));
                        } else {
                            hint.setHint("First, Add some States");
                        }
                    }

                }
                else
                {
                    //If invalid entry alert dialog opens showing the user and error in the entry.
                    new AlertDialog.Builder(ItWorked.this)
                            .setTitle("Error")
                            .setMessage("Please enter a valid State.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    label.setText("");
                                }
                            }).create().show();
                }
            }

            String sizeString = Integer.toString(createdStrings.size());
            numberEntries.setText(sizeString);
            System.out.println(createdStrings);
        }
    };

    private View.OnClickListener findState = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            EditText hint = (EditText) findViewById(R.id.editText);

            //Checking text field value
            if (hint.getText().toString().equals(""))
            {
                //Toast notification allowing the user to know that the index field is empty
                Toast.makeText(getApplicationContext(),
                        "Index empty", Toast.LENGTH_LONG)
                        .show();
            }
            else
            {

                String currentString = hint.getText().toString();
                if (!currentString.equals(""))
                {
                    //If index field is not empty but the index is out of range then a toast notification pops up
                    int currentInt = Integer.parseInt(currentString);
                    if (currentInt+1>createdStrings.size() || currentInt+1<0)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Index out of range", Toast.LENGTH_LONG)
                                .show();

                    }
                    else {

                        //When the user successfully selects a state the alert will pop up displaying the state.
                        new AlertDialog.Builder(ItWorked.this)
                                .setTitle("You Selected")
                                .setMessage(createdStrings.get(currentInt).toUpperCase())
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).create().show();
                    }
                }
            }
            //sets text back to empty in the index text field
            hint.setText("");

        }
    };

    
    public float findMedian(){
        float finalMedian;
        int medianString;


        //For loop that gets the an object from the list, finds the length of that object, adds the count and the current letterCount together and then devices it by the amount of objects in the list.
            medianString = (createdStrings.size()/2);
            stringArray = new String[]{
                    createdStrings.get(medianString)
            };

        if((createdStrings.size()%2)==0)
        {
            String item = createdStrings.get(medianString);
            int firstItem = item.length();
            System.out.println(firstItem);
            String item2 = createdStrings.get(medianString-1);
            int secondItem = item2.length();
            System.out.println(secondItem);
            finalMedian = (firstItem + secondItem)/2;

        }
        else
        {
            String item = createdStrings.get(medianString);
            finalMedian = item.length();
        }

        return finalMedian;
    }
}
