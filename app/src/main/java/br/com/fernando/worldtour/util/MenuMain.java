package br.com.fernando.worldtour.util;

import android.content.Context;
import android.content.res.TypedArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.fernando.worldtour.R;

/**
 * Created by fernando on 03/09/16.
 */
public class MenuMain implements Serializable {

    private String name;
    private int imageId;

    public MenuMain() {
    }

    public MenuMain(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public static List<MenuMain> buildMenu(Context context) {
        List<MenuMain> menus = new ArrayList<>();
        String[] names = context.getResources().getStringArray(R.array.menus_names);
        TypedArray images = context.getResources().obtainTypedArray(R.array.menus_imgs);

        for ( int i = 0; i < names.length; i++ ) {
            MenuMain menuMain = new MenuMain(names[i], images.getResourceId(i,-1));
            menus.add(menuMain);
        }

        images.recycle();

        return menus;
    }





}
