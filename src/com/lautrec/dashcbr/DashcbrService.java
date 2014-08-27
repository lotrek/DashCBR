package com.lautrec.dashcbr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by lotrek on 18.03.14.
 */
public class DashcbrService extends DashClockExtension {

    public static final String PREF_LIST = "PREF_LIST";


    @Override
    protected void onUpdateData(int i) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sp.getString(PREF_LIST, getString(R.string.pref_list_default));
try{
    HttpClient httpClient = new DefaultHttpClient();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Date currentDate = new Date();
    GregorianCalendar gCal =  new GregorianCalendar();
    gCal.add(Calendar.DAY_OF_MONTH, 1);
    String endDate = df.format(gCal.getTime());
    gCal.add(Calendar.DAY_OF_MONTH, -15);
    String startDate = df.format(gCal.getTime());
    String uri = "http://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=" + startDate + "&date_req2=" + endDate + "&VAL_NM_RQ=" + name;
    System.out.println(uri);
    HttpResponse response = httpClient.execute(new HttpGet(uri));
    StatusLine statusLine = response.getStatusLine();
    SAXParserFactory factory = SAXParserFactory.newInstance();

    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
        InputStream data = response.getEntity().getContent();
        SAXParser parser = factory.newSAXParser();

        // parse the file

        CBRParser cbrParser = new CBRParser();
        parser.parse(data, cbrParser);
        publishUpdate(new ExtensionData()
                .visible(true)
                .status(cbrParser.getResult().getResult())
                .icon(R.drawable.icon)
                .expandedTitle(cbrParser.getResult().getResult())
                .expandedBody(df.format(cbrParser.getResult().getLatestDate()))
                .clickIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cbr.ru"))));
    }

}catch (IOException e){

} catch (ParserConfigurationException e) {
    e.printStackTrace();
} catch (SAXException e) {
    e.printStackTrace();
}

        // Publish the extension data update.

    }

}
