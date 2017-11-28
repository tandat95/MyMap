package com.vbd.mapexam.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Result implements Parcelable{

    @SerializedName("IsSuccess")
    public boolean isSuccess;

    @SerializedName("Error")
    public Error error = new Error();
    
    @SerializedName("ResponseTime")
    public String responseTime;
    
    @SerializedName("HasMoreItem")
    public boolean hasMoreItem;
    
    public static class Error implements Parcelable{
    	
    	@SerializedName("ErrorCode")
    	public int errorCode;
    	
    	@SerializedName("ErrorMessage")
    	public String errorMessage;
    	
    	@SerializedName("ExceptionType")
    	public String exceptionType;
    	
    	@SerializedName("Message")
    	public String message;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.errorCode);
            dest.writeString(this.errorMessage);
            dest.writeString(this.exceptionType);
            dest.writeString(this.message);
        }

        public Error() {
        }

        protected Error(Parcel in) {
            this.errorCode = in.readInt();
            this.errorMessage = in.readString();
            this.exceptionType = in.readString();
            this.message = in.readString();
        }

        public static final Creator<Error> CREATOR = new Creator<Error>() {
            @Override
            public Error createFromParcel(Parcel source) {
                return new Error(source);
            }

            @Override
            public Error[] newArray(int size) {
                return new Error[size];
            }
        };
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSuccess ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.error, flags);
        dest.writeString(this.responseTime);
        dest.writeByte(this.hasMoreItem ? (byte) 1 : (byte) 0);
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.isSuccess = in.readByte() != 0;
        this.error = in.readParcelable(Error.class.getClassLoader());
        this.responseTime = in.readString();
        this.hasMoreItem = in.readByte() != 0;
    }

}