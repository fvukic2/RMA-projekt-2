package com.vukic.rma_projekt_2.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vukic.rma_projekt_2.R;
import com.vukic.rma_projekt_2.model.Serija;
import com.vukic.rma_projekt_2.viewmodel.SerijaViewModel;

import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CUDFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.slika)
    ImageView slika;
    @BindView(R.id.serija)
    Spinner serija;
    @BindView(R.id.redatelj)
    TextView redatelj;
    @BindView(R.id.zanr)
    TextView zanr;
    @BindView(R.id.datum)
    TextView datum;
    @BindView(R.id.opis)
    TextView kratkiOpis;

    @BindView(R.id.serijaLayout)
    LinearLayout serijaLayout;
    @BindView(R.id.zanrLayout)
    LinearLayout zanrLayout;
    @BindView(R.id.redateljLayout)
    LinearLayout redateljLayout;
    @BindView(R.id.datumDodavanjaLayout)
    LinearLayout datumDodavanjaLayout;
    @BindView(R.id.opisLayout)
    LinearLayout opisLayout;

    @BindView(R.id.novaSerija)
    Button novaSerija;
    @BindView(R.id.izmjeniSeriju)
    Button izmjeniSeriju;
    @BindView(R.id.izbrisiSeriju)
    Button izbrisiSeriju;

    SerijaViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud,container,false);
        ButterKnife.bind(this,view);
        model = ((MainActivity) getActivity()).getSerijaViewModel();

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.serije, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serija.setAdapter(arrayAdapter);
        serija.setOnItemSelectedListener(this);

        serijaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serija.performClick();
            }
        });

        zanrLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zanrPopup();
            }
        });

        redateljLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redateljPopup();
            }
        });

        datumDodavanjaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datumDodavanjaPopup();
            }
        });

        opisLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opisPopup();
            }
        });

        if (model.getSerija().getId() == 0){
            defNewSer();
            return view;
        }

        defChngDelSer();
        return view;
    }

    private void defNewSer(){
        izmjeniSeriju.setVisibility(View.GONE);
        izbrisiSeriju.setVisibility(View.GONE);
        novaSerija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newSer();
            }
        });
    }

    private void newSer(){
        if (chechAll()){
            model.getSerija().setImeSerije(serija.getSelectedItemPosition());
            model.getSerija().setRedatelj(redatelj.getText().toString());
            model.getSerija().setZanr(zanr.getText().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy.");
            Date date = null;
            try {
                date = dateFormat.parse(datum.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            model.getSerija().setDatum(date == null ? new Date().getTime() : date.getTime());
            model.getSerija().setKratakOpis(kratkiOpis.getText().toString());

            model.dodajNovuSeriju();
            nazad();
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.ispunitePodatke), Toast.LENGTH_SHORT).show();
        }
    }

    private void defChngDelSer(){
        Serija ser = model.getSerija();
        novaSerija.setVisibility(View.GONE);
        serija.setSelection(ser.getImeSerije());
        redatelj.setText(ser.getRedatelj());
        zanr.setText(ser.getZanr());


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy.");
        String dateTime = dateFormat.format(ser.getDatum());
        datum.setText(dateTime);
        kratkiOpis.setText(ser.getKratakOpis());

        izmjeniSeriju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izmjeniSeriju();
            }
        });
        izbrisiSeriju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izbrisiSeriju();
            }
        });
    }

    private void izmjeniSeriju(){
        if (chechAll()) {
            model.getSerija().setImeSerije(serija.getSelectedItemPosition());
            model.getSerija().setRedatelj(redatelj.getText().toString());
            model.getSerija().setZanr(zanr.getText().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy.");
            Date date = null;
            try {
                date = dateFormat.parse(datum.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            model.getSerija().setDatum(date == null ? new Date().getTime() : date.getTime());
            model.getSerija().setKratakOpis(kratkiOpis.getText().toString());
            model.promjeniSeriju();
            nazad();
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.ispunitePodatke), Toast.LENGTH_SHORT).show();
        }

    }

    private void izbrisiSeriju(){
        model.obrisiSeriju();
        nazad();
    }

    private void datumDodavanjaPopup() {
        Calendar cal = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cal.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        }
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (model.getSerija().getDatum() != 0) {
            cal.setTimeInMillis(model.getSerija().getDatum());
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePicker = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
                DateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy.");
                String strDate = dateFormat.format(cal.getTime());
                datum.setText(strDate);
            }
        }, year, month, day);
        datePicker.setTitle(getString(R.string.datumDodavanjaPopup));
        datePicker.show();
    }

    private void zanrPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(getString(R.string.zanrPopup));

        final EditText input = new EditText(this.getActivity());
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.zanrPopupUredu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                zanr.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton(getString(R.string.zanrPopupOdustani), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void redateljPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(getString(R.string.redateljPopup));

        final EditText input = new EditText(this.getActivity());
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.redateljPopupUredu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                redatelj.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton(getString(R.string.redateljPopupOdustani), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void opisPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(getString(R.string.opisPopup));

        final EditText input = new EditText(this.getActivity());
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.opisPopupUredu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                kratkiOpis.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton(getString(R.string.opisPopupOdustani), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private boolean chechAll(){
        boolean redateljBoolean = false;
        boolean zanrBoolean = false;
        boolean datumB = false;
        boolean opisBoolean = false;
        if (!redatelj.getText().equals("")) {
            redateljBoolean = true;
        }
        if (!zanr.getText().equals("")) {
            zanrBoolean = true;
        }
        if (!datum.getText().equals("")) {
            datumB = true;
        }
        if (!kratkiOpis.getText().equals("")) {
            opisBoolean = true;
        }
        return redateljBoolean && zanrBoolean && datumB && opisBoolean;
    }


    @OnClick(R.id.nazad)
    public void nazad(){
        ((MainActivity)getActivity()).read();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                Picasso.get().load(R.drawable.breaking_bad).into(slika);
                break;
            case 1:
                Picasso.get().load(R.drawable.vikings).into(slika);
                break;
            case 2:
                Picasso.get().load(R.drawable.dark).into(slika);
                break;
            case 3:
                Picasso.get().load(R.drawable.sopranos).into(slika);
                break;
            case 4:
                Picasso.get().load(R.drawable.wire).into(slika);
                break;
            case 5:
                Picasso.get().load(R.drawable.game_of_thrones).into(slika);
                break;
            case 6:
                Picasso.get().load(R.drawable.peaky_blinders).into(slika);
                break;
            case 7:
                Picasso.get().load(R.drawable.only_fools_and_horses).into(slika);
                break;
            case 8:
                Picasso.get().load(R.drawable.stranger_things).into(slika);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
