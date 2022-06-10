package com.example.final_project.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.final_project.Model.Album;
import com.example.final_project.R;

import java.util.ArrayList;
import java.util.Comparator;

public class LibraryAlbumAdapter extends ArrayAdapter<Album> {
    public LibraryAlbumAdapter(@NonNull Context context, ArrayList<Album> albumArrayList) {
        super(context, 0, albumArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.album_layout, parent, false);
        }
        Album album = getItem(position);
        ImageView albumCover = listItemView.findViewById(R.id.albumCover);
        new ImageDownloaderAsync(albumCover).execute(album.getCover());
        return listItemView;
    }

    @Override
    public void sort(@NonNull Comparator<? super Album> comparator) {
        super.sort(comparator);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
