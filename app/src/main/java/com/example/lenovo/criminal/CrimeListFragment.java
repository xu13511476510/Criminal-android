package com.example.lenovo.criminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

/**
 * Created by Lenovo on 2018/4/7.
 */

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int position;
    private boolean mSubtitleVisible;
    private TextView mNullTextView;
    private static final String SAVED_SUBTITLE_VISIBLE="subtitle";
    private static final String DELETE_CRIME ="delete";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_crime_list,container,false);
        mNullTextView=view.findViewById(R.id.crime_ifnull);
        mCrimeRecyclerView=view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState!=null){
            mSubtitleVisible=savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();
        return view;
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);

        MenuItem subtitleItem=menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_crime:
                Crime crime=new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent=CrimePagerActivity.newIntent((getActivity()),crime.getId());
                startActivity(intent);
                return  true;
            case R.id.show_subtitle:
                mSubtitleVisible=!mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void   updateSubtitle(){
        CrimeLab crimeLab=CrimeLab.get(getActivity());
        int crimecount=crimeLab.getCrimes().size();
        String subtitle=getString(R.string.subtitle_format,crimecount);

        if(!mSubtitleVisible){
            subtitle=null;
        }

        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private void updateUI() {
    CrimeLab crimelab=CrimeLab.get(getActivity());
        List<Crime> crimes=crimelab.getCrimes();
        if(crimes.isEmpty())
        {
            mNullTextView.setVisibility(View.VISIBLE);
        }
        else {
            mNullTextView.setVisibility(View.INVISIBLE);
        }
        if(mAdapter==null)
        {
            mAdapter=new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
       else
        {
            mAdapter.notifyItemChanged(position);
        }
        updateSubtitle();
    }


    private class CrimeHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        private Crime mCrime;

        public void  bind(Crime crime)
        {
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDatetTextView.setText((String) DateFormat.format("MMM dd, yyyy h:mmaa", mCrime.getDate()));
        }
        private TextView mTitleTextView;
        private TextView mDatetTextView;

        public CrimeHolder(LayoutInflater inflater,ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_crime,parent,false));
            mTitleTextView=itemView.findViewById(R.id.crime_title);
            mDatetTextView=itemView.findViewById(R.id.crime_date);
            itemView.setOnClickListener(this);
        }
        public CrimeHolder(LayoutInflater inflater,ViewGroup parent,int viewtype)
        {
            super(inflater.inflate(R.layout.list_item_crime2,parent,false));
            mTitleTextView=itemView.findViewById(R.id.crime_title);
            mDatetTextView=itemView.findViewById(R.id.crime_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            position=mCrimeRecyclerView.getChildAdapterPosition(v);
            Intent intent=CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }
    }

    private  class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>
    {
        private List<Crime>mCrimes;
        public CrimeAdapter(List<Crime>crimes)
        {
            mCrimes=crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            if(viewType==1)return new CrimeHolder(layoutInflater,parent,viewType);
            return  new CrimeHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime=mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isRequiresPolice())
                return 1;
            return 0;
        }
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }


}
