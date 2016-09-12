package br.com.fernando.worldtour.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import br.com.fernando.worldtour.R;
import br.com.fernando.worldtour.controller.UserController;
import br.com.fernando.worldtour.fragment.ListCountryFragment;
import br.com.fernando.worldtour.fragment.ListCountryVisitedFragment;
import br.com.fernando.worldtour.fragment.PerfilFragment;
import br.com.fernando.worldtour.model.domain.User;
import br.com.fernando.worldtour.util.MenuMain;
import br.com.fernando.worldtour.adapter.MenuMainAdapter;

public class MainActivity extends Activity implements MenuMainAdapter.OnItemClickListener {

    private static final String INFORMACOES_PERFIL = "Informações Perfil";
    private static final String LISTAGEM_PAISES = "Listagem de Países";
    private static final String PAISES_VISITADOS = "Países Visitados";
    private static final String LOGOUT_FACEBOOK = "Logout Facebook";

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private UserController userController;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private User userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verificyStatusTokenFacebook();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        userController = new UserController(getApplicationContext());
        userLogged = userController.getUserPreferences();

        loadMenu();
    }

    private void verificyStatusTokenFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        }

    }

    private void loadMenu() {
        setHeaderNavigationDrawer();

        drawerList.setAdapter(new MenuMainAdapter(this, this));

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);

        }

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                setTitle(R.string.title_choose);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                setTitle(R.string.app_name);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    private void setHeaderNavigationDrawer()  {
            View header = getLayoutInflater().inflate(R.layout.header_menu, null);
            ImageView ivProfileFacebook  = (ImageView) header.findViewById(R.id.imgProfile);
            TextView tvNome = (TextView) header.findViewById(R.id.tvNome);

            Picasso.with(getApplicationContext()).load(userLogged.getProfilePicUrl()).into(ivProfileFacebook);
            tvNome.setText(userLogged.getName());

            drawerList.addHeaderView(header);
    }


    private void goLoginScreen() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onClick(MenuMain menuMain) {
        selectItemMenu(menuMain);
    }

    private void selectItemMenu(MenuMain menuSelected) {
        switch (menuSelected.getName()) {

            case INFORMACOES_PERFIL:
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                PerfilFragment perfilFragment = new PerfilFragment();
                fragmentTransaction.add(R.id.content_frame ,perfilFragment, "perfilFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                break;
            case LISTAGEM_PAISES:
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                ListCountryFragment listCountryFragment = new ListCountryFragment();
                fragmentTransaction.add(R.id.content_frame , listCountryFragment, "listCountryFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                break;
            case PAISES_VISITADOS:
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                ListCountryVisitedFragment listCountryVisitedFragment = new ListCountryVisitedFragment();
                fragmentTransaction.add(R.id.content_frame , listCountryVisitedFragment, "listCountryVisitedFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                break;
            case LOGOUT_FACEBOOK:
                LoginManager.getInstance().logOut();
                goLoginScreen();
                break;
            default:
                break;

        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
