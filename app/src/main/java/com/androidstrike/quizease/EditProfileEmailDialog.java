package com.androidstrike.quizease;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditProfileEmailDialog extends AppCompatDialogFragment {

    private EditText editTextEmailAddress;
    private EmailChangeDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_edit_profile_email_dialog, null);
        builder.setView(view)
                .setTitle("Edit Email Address")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String emailAddress = editTextEmailAddress.getText().toString();
                        listener.applyEmailText(emailAddress);
                    }
                });
        editTextEmailAddress = view.findViewById(R.id.edit_text_email_address);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        listener= (EmailChangeDialogListener) getActivity();
    }

    public interface EmailChangeDialogListener{
        void applyEmailText(String emailAddress);
    }
}
