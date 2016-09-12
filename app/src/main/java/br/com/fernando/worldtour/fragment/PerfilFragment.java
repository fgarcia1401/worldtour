package br.com.fernando.worldtour.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.fernando.worldtour.R;
import br.com.fernando.worldtour.controller.UserController;
import br.com.fernando.worldtour.model.domain.User;

/**
 * Created by fernando on 06/09/16.
 */
public class PerfilFragment extends Fragment {

    private EditText edtName;
    private EditText edtEmail;
    private ImageView ivProfileFacebook;

    private UserController userController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }


        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        edtName = (EditText) view.findViewById(R.id.edtName);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        ivProfileFacebook = (ImageView) view.findViewById(R.id.ivProfileFacebook);

        userController = new UserController(getActivity().getApplicationContext());
        User userLogged = userController.getUserPreferences();

        edtName.setText(userLogged.getName());
        edtEmail.setText(userLogged.getEmail());
        Picasso.with(getActivity().getApplicationContext()).load(userLogged.getProfilePicUrl()).into(ivProfileFacebook);

        return view;
    }
}
