package com.idorosh.i_dorosh_advancedviews;

import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;



import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    //Adapters, list and spinner
    Spinner mOSSpinner;
    ArrayAdapter<String> mOSAdapter;
    ArrayList<String> osList;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //runs populate view method
        populateView();
    }

    //Array that hold my custom objects
    private ArrayList<Contacts> Contacts = new ArrayList<>();

    private void populateView()
    {
        //Setting custom objects
        Contacts.add(new Contacts("Alex Johnson", "(511)-123-1234", "AJohnson@email.com", "AJohnson@aim.com", "2205 Hampton Meadows\n" + "Fitchburg, MA 01420", "contactblue", "#FF314CB4"));
        Contacts.add(new Contacts("Alicia Smith", "(511)-234-5678", "ASmith@email.com", "ASmith@aim.com", "4658 Kelley Road\n" + "Gulfport, MS 39507", "contactgreen", "#FF2CC266"));
        Contacts.add(new Contacts("Brandon Johnson", "(234)-567-1234", "BJohnson@email.com", "BJohnson@aim.com", "4125 Burwell Heights Road\n" + "Beaumont, TX 77701", "contactteal", "#FF32AFC2"));
        Contacts.add(new Contacts("Briana Watson", "(456)-790-1012", "BWatson@email.com", "Bwatson@aim.com", "2072 North Street\n" + "Salt Lake City, UT 8410", "contactyellow", "#FFEBE66A"));
        Contacts.add(new Contacts("Emily Carter", "(567)-891-01234", "ECarter@email.com", "ECarter@aim.com", "695 Leroy Lane\n" + "Freeman, SD 57029", "contactorange", "#FFEC8A14"));
        Contacts.add(new Contacts("Derek Nelson", "(789)-102-3456", "DNelson.@email.com", "DNelson@aim.com", "891 Cottonwood Lane\n" + "Nashville, TN 37207", "contactred", "#FFEC443C"));
        Contacts.add(new Contacts("Denise Smith", "(987)-654-4321", "DSmith@email.com", "DSmith@aim.com", "3454 Wilson Avenue\n" + "Irving, TX 75060", "contactpink", "#FFEC4D82"));
        Contacts.add(new Contacts("Peter Johnston", "(234)-432-1232","PJohnston@email.com","PJohnston@aim.com","2834 Still Street\n" + "Paulding, OH 45879", "contactlightpurple", "#FFA23DA7"));
        Contacts.add(new Contacts("Sam Smith", "(343)-564-564", "SSmith@email.com", "SSmith@aim.com", "2353 Davis Avenue\n" + "Benicia, CA 94510", "contactdarkpurple", "#FF542056"));
        Contacts.add(new Contacts("Sara Scott", "(221)-332-4433", "SScott@email.com","SScott@aim.com","896 Heavner Court\n" + "Des Moines, IA 50313", "contactlightblue", "#FF5799D0"));
        Contacts.add(new Contacts("Tyler Miller ", "(331)-441-5544", "TMillier@email.com", "TMiller@aim.com", "4735 Austin Secret Lane\n" + "Roosevelt, UT 84066", "contactsamon", "#FFEC8084"));


        //Array to hold all the names that were created by a for loop
        ArrayList<String> contactsNames = new ArrayList<>();

        for(int i =0; i < Contacts.size(); i++)
        {
            String contactName = Contacts.get(i).getName();
            contactsNames.add(contactName);
        }


        //Checking orentation of the device to display the proper view
        if (getResources().getConfiguration().orientation == 1) {
            mOSSpinner = (Spinner) findViewById(R.id.spinner);

            osList = contactsNames;

            mOSAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,osList);

            mOSSpinner.setAdapter(mOSAdapter);

            mOSSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    //Running setInfo method to populate the view with information based on the position if the item selected.
                    setInfo(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    System.out.println("Nothing Selected");
                }
            });


        }
        else {
            //If the orentation is in landscape then the view includes a listview.
            listView = (ListView) findViewById(R.id.listView);
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, contactsNames) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    View view = super.getView(position, convertView, parent);
                    //Using textviews that are included in simple_list_item_2 method
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    //Setting the text in the listview to the corresponding names and numbers.
                    text1.setText(Contacts.get(position).getName());
                    text2.setText(Contacts.get(position).getPhone());

                    return view;
                }


            };

            //Setting list view adapter.
            listView.setAdapter(adapter);

            //If the selected item in list view is null then the view is populated with the first object in the array.
            if (listView.getSelectedItem() == null)
            {
                setInfo(0);
            }
                //Running the same setinfo method as the spinner to set info in the view.
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        setInfo(position);
                    }
                });


        }
    }

    //UI Population Method
    public void setInfo(int index){
        //Getting Image using the string under each contact
        String getImage = Contacts.get(index).getPhoto();
        int id = getResources().getIdentifier("com.idorosh.i_dorosh_advancedviews:drawable/" + getImage, null, null);
        //One variable to shorten the amount of typing needed when setting the values of each text view.
        Contacts info = Contacts.get(index);

        //Labels for headers and information also for the Icon
        TextView contactName = (TextView)findViewById(R.id.contactName);
        TextView contactNumber = (TextView)findViewById(R.id.phoneNum);
        TextView contactEmail = (TextView)findViewById(R.id.emailInfo);
        TextView contactAim = (TextView)findViewById(R.id.aimInfo);
        TextView contactAddress = (TextView)findViewById(R.id.address);

        TextView emailLabel = (TextView)findViewById(R.id.email);
        TextView aimLabel = (TextView)findViewById(R.id.aimLabel);
        TextView addressLabel = (TextView)findViewById(R.id.addressLabel);
        ImageView contactImg = (ImageView)findViewById(R.id.imageView);

        //Setting Images
        contactImg.setImageResource(id);

        //Setting color of labels based on the color under each contact that corresponds with the image color.
        contactName.setTextColor(Color.parseColor(info.getColor()));
        emailLabel.setTextColor(Color.parseColor(info.getColor()));
        aimLabel.setTextColor(Color.parseColor(info.getColor()));
        addressLabel.setTextColor(Color.parseColor(info.getColor()));

        //Setting information for the text views from custom objects.
        contactName.setText(info.getName());
        contactNumber.setText(info.getPhone());
        contactEmail.setText(info.getEmail());
        contactAim.setText(info.getAim());
        contactAddress.setText(info.getAddress());
    }


}
