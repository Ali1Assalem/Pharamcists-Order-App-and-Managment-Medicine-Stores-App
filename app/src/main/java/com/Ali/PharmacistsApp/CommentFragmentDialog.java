package com.Ali.PharmacistsApp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Adapter.CommentAdapter;
import com.Ali.PharmacistsApp.Model.Comment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class CommentFragmentDialog extends BottomSheetDialogFragment {

    List<Comment> comments;
    Context context;

    public CommentFragmentDialog(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog=(BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view= LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_comment,null);
        bottomSheetDialog.setContentView(view);

        RecyclerView rec_comment=view.findViewById(R.id.recycler_comment);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        rec_comment.setLayoutManager(linearLayoutManager);

        CommentAdapter commentAdapter=new CommentAdapter(comments,context);
        rec_comment.setAdapter(commentAdapter);

        return bottomSheetDialog;
    }
}
