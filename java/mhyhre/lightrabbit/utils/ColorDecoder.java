/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.utils;

import java.util.Arrays;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;

import org.andengine.util.adt.color.Color;

import android.util.Log;

public final class ColorDecoder {

    /**
     * @param src - color string in format "r,g,b,a" with float[0.0-1.0] components
     */
    public static Color convertFromString(String src) {
        Color result = new Color(Color.WHITE);

        List<String> strComponents = Arrays.asList(src.split("\\s*,\\s*"));

        try {
            if (strComponents.size() != 4) {
                new IllegalArgumentException();
            }

            result.setRed(Float.parseFloat(strComponents.get(0)));
            result.setGreen(Float.parseFloat(strComponents.get(1)));
            result.setBlue(Float.parseFloat(strComponents.get(2)));
            result.setAlpha(Float.parseFloat(strComponents.get(3)));

         } catch (IllegalArgumentException e) {
            Log.e(MainActivity.DEBUG_ID, "ColorDecoder::convertFromString: wrong color format");
        }

        return result;
    }
}

