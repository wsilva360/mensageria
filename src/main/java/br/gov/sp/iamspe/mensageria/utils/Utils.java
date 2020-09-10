package br.gov.sp.iamspe.mensageria.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	/***
	 * getData - Formata Data e Hora
	 * 
	 * @return String
	 */
	public static String getData() {
		return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
	}

	/***
	 * getDateTimeFull - Data e Hora atual
	 * 
	 * @return String
	 */
	public String getDateTimeFull() {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

		// System.out.print("HORA =========== " + agora.format(formatter));

		return agora.format(formatter);
	}

	public static Date formatToDate(String data) {
		SimpleDateFormat of = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		try {
			return of.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Formata xx/xx/xxxx xx:xx:xx para Date
	 * 
	 * @param data
	 * @return
	 */
	public static Date formatToDataBase(String data) {
		SimpleDateFormat of = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
		try {
			return (data == null)? null : of.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatToString(Date data) {
		SimpleDateFormat nf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return nf.format(data);
	}

	public static String formatToMaxxMobi(Date data) {
		SimpleDateFormat nf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return (data == null) ? null : nf.format(data);
	}

	public static String getStrackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return e.getMessage() + "\n" + sw.toString();
	}

	public static boolean isJSONValid(String str) {
		try {
			new JSONObject(str);
		} catch (JSONException ex) {
			try {
				new JSONArray(str);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}
}