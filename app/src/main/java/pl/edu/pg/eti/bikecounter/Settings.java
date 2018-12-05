package pl.edu.pg.eti.bikecounter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class Settings extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner mSpinner;

    public static Settings newInstance() {
        return new Settings();
    }
    private final static String TAG = Settings.class.getSimpleName();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        final List<String> SystemsList = Arrays.asList(getResources().getStringArray(R.array.scale_wheel_sizes));

        mSpinner = view.findViewById(R.id.spinner);
        Spinner scaleSpinner = view.findViewById(R.id.scaleSpinner);
        ArrayAdapter<CharSequence> scaleAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.scale_wheel_sizes, android.R.layout.simple_spinner_item );
        scaleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        scaleSpinner.setAdapter(scaleAdapter);
        scaleSpinner.setSelection(SystemsList.indexOf(((MainActivity)getActivity()).mSharedPreferences.getString("WheelSizeScale",SystemsList.get(2))));
        scaleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (SystemsList.get(position).equals(getResources().getString(R.string.ETRTO_systems))){
                    ((MainActivity)getActivity()).mEditor.putString("WheelSizeScaleInt", getString(R.string.ETRTO_systems));
                } else if (SystemsList.get(position).equals(getResources().getString(R.string.inch_systems))){
                    ((MainActivity)getActivity()).mEditor.putString("WheelSizeScaleInt", getString(R.string.inch_systems));
                } else if (SystemsList.get(position).equals(getResources().getString(R.string.circ_systems))){
                    ((MainActivity)getActivity()).mEditor.putString("WheelSizeScaleInt", getString(R.string.circ_systems));
                } else {
                    Log.d(TAG, "Not correct String value of wheel scale");
                }
                ((MainActivity)getActivity()).mEditor.commit();

                ((MainActivity)getActivity()).mEditor.putString("WheelSizeScale", SystemsList.get(position));
                ((MainActivity)getActivity()).mEditor.commit();

                setValuesSpinner(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void setValuesSpinner(View view){
        final List<String> ValueList = Wheel.getValuesList(getContext(),(((MainActivity)getActivity()).mSharedPreferences.getString("WheelSizeScaleInt",Integer.toString(R.string.circ_systems))),Wheel.makeWheels());

        ArrayAdapter<String> adapter =new  ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,ValueList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(ValueList.indexOf(((MainActivity)getActivity()).mSharedPreferences.getString("wheelCircInSelectedSystem",ValueList.get(0))));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Context context = parent.getContext();

                double circuit = Wheel.getCircValue(getContext(),(((MainActivity)getActivity()).mSharedPreferences.getString("WheelSizeScaleInt",Integer.toString( R.string.circ_systems))),selected,Wheel.makeWheels());
                ((MainActivity)getActivity()).setWheelCirc(circuit);
                ((MainActivity)getActivity()).mEditor.putString("wheelCircInSelectedSystem",selected);
                ((MainActivity)getActivity()).mEditor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
