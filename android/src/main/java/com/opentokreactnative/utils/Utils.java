package com.opentokreactnative.utils;

import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;
import com.opentok.android.Session.Builder.TransportPolicy;
import com.opentok.android.Session.Builder.IncludeServers;
import com.opentok.android.Session.Builder.IceServer;
import com.opentokreactnative.OTRN;

import com.facebook.react.bridge.ReadableArray;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public final class Utils {

    public static boolean didConnectionFail(OpentokError errorCode) {

        switch (errorCode.getErrorCode()) {
            case ConnectionFailed:
                return true;
            case ConnectionRefused:
                return true;
            case ConnectionTimedOut:
                return true;
            default:
                return false;
        }
    }

    public static boolean contains(ArrayList array, String value) {

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getPublisherId(PublisherKit publisherKit) {

        Map<String, Publisher> publishers = OTRN.sharedState.getPublishers();
        for (Map.Entry<String, Publisher> entry: publishers.entrySet()) {
            Publisher mPublisher = entry.getValue();
            if (mPublisher.equals(publisherKit)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static String getStreamIdBySubscriber(SubscriberKit subscriberKit) {

        Map<String, Subscriber> subscribers = OTRN.sharedState.getSubscribers();
        for (Map.Entry<String, Subscriber> entry: subscribers.entrySet()) {
            Subscriber mSubcriber = entry.getValue();
            if (mSubcriber.equals(subscriberKit)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static IncludeServers sanitizeIncludeServer(String value) {
        IncludeServers includeServers =  IncludeServers.All;
        if (value != null && value.equals("custom")) {
            includeServers = IncludeServers.Custom;
        }
        return includeServers;
    }

    public static TransportPolicy sanitizeTransportPolicy(String value) {
        TransportPolicy transportPolicy =  TransportPolicy.All;
        if (value != null && value.equals("relay")) {
            transportPolicy =  TransportPolicy.Relay;
        }  
        return transportPolicy;
    }

    public static List<IceServer> sanitizeIceServer(ReadableArray serverList) {
        List<IceServer> iceServers = new ArrayList<>();
        if (serverList != null) {
            for (int i = 0; i < serverList.size(); i++) {
                for (int j = 0; j < serverList.getMap(i).getArray("urls").size(); j++) {
                    iceServers.add(new IceServer(
                            serverList.getMap(i).getArray("urls").getString(j),
                            serverList.getMap(i).getString("username"),
                            serverList.getMap(i).getString("credential")

                    ));
                }
            }
        }
        return iceServers;
    }
}
