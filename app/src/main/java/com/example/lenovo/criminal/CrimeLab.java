package com.example.lenovo.criminal;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Lenovo on 2018/4/7.
 */

public class CrimeLab {
    private static  CrimeLab sCrimeLab;

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id)
    {
        for(Crime crime:mCrimes)
        {
            if(crime.getId().equals(id))
            {
                return crime;
            }
        }
        return null;
    }
    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public void deleteCrime(Crime c){
                mCrimes.remove(c);

    }


    private List<Crime> mCrimes;


    private CrimeLab(Context context) {
        mCrimes=new ArrayList<>();

    }


    public static  CrimeLab get(Context context)
    {
        if(sCrimeLab==null)
        {
            sCrimeLab=new CrimeLab(context);
        }
        return sCrimeLab;
    }

}
