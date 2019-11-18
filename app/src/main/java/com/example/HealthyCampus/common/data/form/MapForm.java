package com.example.HealthyCampus.common.data.form;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.maps.model.LatLng;

public class MapForm implements Parcelable {
    public String address;
    public String location;
    public LatLng latLng;

    public MapForm() {
    }

    protected MapForm(Parcel in) {
        address = in.readString();
        location = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<MapForm> CREATOR = new Creator<MapForm>() {
        @Override
        public MapForm createFromParcel(Parcel in) {
            return new MapForm(in);
        }

        @Override
        public MapForm[] newArray(int size) {
            return new MapForm[size];
        }
    };

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MapForm(String address, LatLng latLng) {
        this.address = address;
        this.latLng = latLng;
    }

    public MapForm(String address, String location) {
        this.address = address;
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(location);
        dest.writeParcelable(latLng, flags);
    }

    @Override
    public String toString() {
        return "MapForm{" +
                "address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", latLng=" + latLng +
                '}';
    }
}
