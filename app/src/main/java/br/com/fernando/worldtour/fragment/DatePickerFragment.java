package br.com.fernando.worldtour.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private static final String TAG = "BANCO INTERMEDIUM";
	public EditText editText;
	
	public DatePickerFragment(EditText edit_text) {
		editText = edit_text;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		final Calendar c = Calendar.getInstance();
		int year = 0;
		int month = 0;
		int day = 0;

		// Se o editText estiver vazio exibe a data atual no Dialog
		if (editText.getText().toString().equals("")) {
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
		} else {
			// Caso contrário exibe a data que estiver no editText
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				c.setTime(sdf.parse(editText.getText().toString()));
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
			} catch (ParseException e) {
				Log.e(TAG, "Erro converter data DatePicker: " + e.getMessage());
			}
		}
		
		// Criando nova instancia de DatePickerDialog e retornando
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}


	public void onDateSet(DatePicker view, int year, int month, int day) {
		String sDay = "";
		String sMonth = "";
		String sYear = "";
		
		//Adicionando Zero na string do dia menor que 10
		if (day < 10) {
			sDay = 0 + String.valueOf(day);
		} else {
			sDay = String.valueOf(day);
		}
		
		//Adicionando Zero na string do mês menor que 10
		if (month < 9) {
			sMonth = 0 + String.valueOf(month + 1);
		} else {
			sMonth = String.valueOf(month + 1);
		}
		sYear = String.valueOf(year);

		editText.setText(sDay + "/" + sMonth + "/" + sYear);
	}
}