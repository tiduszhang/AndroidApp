package com.strongit.androidapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DemoEntity{

    public DemoEntity() {
    }

    private List<ItemsEntity> items;

    public List<ItemsEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public static class ItemsEntity implements Parcelable {

        private String name;

        public ItemsEntity() {
        }

        public ItemsEntity(Parcel in) {
            name = in.readString();
        }

        public static final Creator<ItemsEntity> CREATOR = new Creator<ItemsEntity>() {
            @Override
            public ItemsEntity createFromParcel(Parcel in) {
                return new ItemsEntity(in);
            }

            @Override
            public ItemsEntity[] newArray(int size) {
                return new ItemsEntity[size];
            }
        };

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
        }
    }
}
