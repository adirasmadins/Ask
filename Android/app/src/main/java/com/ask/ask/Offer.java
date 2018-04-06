package com.ask.ask;

import java.io.Serializable;

/**
 * Created by alexander on 2/26/2018.
 *
 * Where offers are made.
 *
 */

public class Offer implements Serializable {

    private String provider_id;
    private String offer_price;
    private String request_id;
    private String belonging_id;
    private String begin_date;
    private String end_date;
    private String description;
    private String message;

    private String provider_name;
    private String provider_surname;
    private String lon;
    private String lat;

    private int NOT_SELECTED = -1;
    private int IN_PROGRESS = 0;
    private int SELECTED = 1;
    private int MATCHED = 2;
    private int status;

    public Offer(String belonging_id, String request_id,
                 String provider_id,
                 String begin_date,String end_date,
                 String lon, String lat,
                 String offer_price, String description,
                 String message) {
        this.request_id = request_id;
        this.provider_id = provider_id;
        this.offer_price = offer_price;
        this.belonging_id = belonging_id;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.description = description;
        this.message = message;
        this.lon = lon;
        this.lat = lat;
        this.status = IN_PROGRESS;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public String getBelonging_id() {
        return belonging_id;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public String getProvider_surname() {
        return provider_surname;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public int getStatus() {
        return status;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    @Override
    public String toString() {
        return "Offer{" +
                ", provider_id='" + provider_id + '\'' +
                ", request_id='" + request_id + '\'' +
                ", offer_price='" + offer_price + '\'' +
                ", request_id='" + request_id + '\'' +
                ", belonging_id='" + belonging_id + '\'' +
                ", begin_date='" + begin_date + '\'' +
                ", end_Date='" + end_date + '\'' +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }

    public String toDescriptiveString(){
        Item item = LocalData.getHashMapItemsById().get(this.getBelonging_id());

        return "You are fulfilling a " + item.getName() +
                " from " + this.getBegin_date() + " to " + this.getEnd_date() +
                " for $" + item.getPrice() + " per day.";
    }

}