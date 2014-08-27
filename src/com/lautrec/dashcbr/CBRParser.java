package com.lautrec.dashcbr;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by lotrek on 18.03.14.
 */
public class CBRParser extends DefaultHandler {
    CBRData cbrData = new CBRData();
    String thisElement = "";
    String currentDate = "";

    public CBRData getResult(){
        return cbrData;
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;
        if(qName.equals("Record")){
            currentDate = atts.getValue("Date");
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        thisElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (thisElement.equals("Value")) {
            cbrData.addData(new String(ch, start, length), currentDate);
        }
    }

    @Override
    public void endDocument() {
    }
}
