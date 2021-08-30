package com.vukic.rma_projekt_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vukic.rma_projekt_2.R;
import com.vukic.rma_projekt_2.model.Serija;
import com.vukic.rma_projekt_2.view.adapter.SerijaAdapter;
import com.vukic.rma_projekt_2.view.adapter.SerijaClickListener;
import com.vukic.rma_projekt_2.viewmodel.SerijaViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReadFragment extends Fragment {

    @BindView(R.id.lista)
    RecyclerView recyclerView;

    SerijaViewModel serijaViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,container,false);
        ButterKnife.bind(this,view);

        serijaViewModel = ((MainActivity) getActivity()).getSerijaViewModel();

        defLst();
        refreshData();
        return view;
    }

    public void refreshData(){
        serijaViewModel.getSerije().observe(getViewLifecycleOwner(), new Observer<List<Serija>>() {
            @Override
            public void onChanged(List<Serija> serijas) {
                ((SerijaAdapter)recyclerView.getAdapter()).setData(serijas);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void defLst(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SerijaAdapter(new SerijaClickListener() {
            @Override
            public void onItemClick(Serija serija) {
                serijaViewModel.setSerija(serija);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }

    @OnClick(R.id.fab)
    public void newPrs(){
        serijaViewModel.setSerija(new Serija());
        ((MainActivity)getActivity()).cud();
    }
}
