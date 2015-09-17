package com.sisa.tabata.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * {@link Parcelable} domain object describing a workout section.
 *
 * @author Laszlo Sisa
 */
public class WorkoutSection implements Parcelable {

    public static final Parcelable.Creator<WorkoutSection> CREATOR = new Parcelable.Creator<WorkoutSection>() {
        public WorkoutSection createFromParcel(Parcel input) {
            return new WorkoutSection(input);
        }

        public WorkoutSection[] newArray(int size) {
            return new WorkoutSection[size];
        }
    };

    private int rounds;
    private long warmUp;
    private long work;
    private long rest;
    private long coolDown;

    /**
     * Default constructor.
     */
    public WorkoutSection() {
    }

    /**
     * Constructor for inflating the objects from {@link Parcel}.
     *
     * @param input {@link Parcel} the data source
     */
    public WorkoutSection(Parcel input) {
        rounds = input.readInt();
        warmUp = input.readLong();
        work = input.readLong();
        rest = input.readLong();
        coolDown = input.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel output, int flags) {
        output.writeInt(rounds);
        output.writeLong(warmUp);
        output.writeLong(work);
        output.writeLong(rest);
        output.writeLong(coolDown);
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public long getWarmUp() {
        return warmUp;
    }

    public void setWarmUp(long warmUp) {
        this.warmUp = warmUp;
    }

    public long getWork() {
        return work;
    }

    public void setWork(long work) {
        this.work = work;
    }

    public long getRest() {
        return rest;
    }

    public void setRest(long rest) {
        this.rest = rest;
    }

    public long getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(long coolDown) {
        this.coolDown = coolDown;
    }

}
