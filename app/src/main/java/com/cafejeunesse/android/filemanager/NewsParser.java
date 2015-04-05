package com.cafejeunesse.android.filemanager;

import android.util.Xml;

import com.cafejeunesse.android.structure.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by David Levayer on 27/02/15.
 */
public class NewsParser {

    // TODO parser correctement les balises <entry>
    // ou carrement se tourner vers un format iCal ?

    // Google Calendar Api v3 aurait été cool, mais ça requiert oauth2 et une authorisation depuis
    // le device même si nos calendars sont publiques. (triste)
    // @see: https://developers.google.com/oauthplayground/

    // https://www.googleapis.com/calendar/v3/users/me/calendarList
    // https://www.googleapis.com/calendar/v3/calendars/{calendar_id}
    // https://www.googleapis.com/calendar/v3/calendars/{calendar_id}/events

    // We don't use namespaces
    private static final String ns = null;

    public List<News> parseFileForNews(String filepath)
        throws XmlPullParserException, IOException {

        return parse(new FileInputStream(filepath));
    }

    private List<News> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<News> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<News> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private News readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");
        String title = null;
        String article = null;
        Date date = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readSimpleString(parser, "title");
                    break;
                case "summary":
                    article = readSimpleString(parser, "summary");
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new News(title, new Date(), article);
    }

    private String readSimpleString(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {

        parser.require(XmlPullParser.START_TAG, ns, tag);

        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }

        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
