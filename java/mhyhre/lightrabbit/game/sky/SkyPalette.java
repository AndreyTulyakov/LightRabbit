/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.sky;

import org.andengine.util.adt.color.Color;

public enum SkyPalette {
    
    CLEAN(new Color(0.40f, 0.88f, 0.99f), new Color(0.05f, 0.05f, 0.20f), new Color(1f, 0.75f, 0.5f), new Color(1f, 1f, 0.5f)),
    RAINY(new Color(0.70f, 0.70f, 0.70f), new  Color(0.20f, 0.20f, 0.25f)),
    BIOHAZARD(new Color(0.40f, 0.70f, 0.50f), new  Color(0.10f, 0.25f, 0.30f)),
    IMPERIAL_DARK(new Color(0.50f, 0.50f, 0.55f), new  Color(0.15f, 0.15f, 0.20f));
        
    private final boolean hasGradients;
    
    private final Color dayColor;
    private final Color nightColor;
    
    private final Color sunsetColor;
    private final Color sunriseColor;
    
    
    private SkyPalette(Color day, Color night) {
        hasGradients = false;
        dayColor = day;
        nightColor = night;
        sunsetColor = Color.CYAN;
        sunriseColor = Color.CYAN;
    }
    
    private SkyPalette(Color day, Color night, Color sunset, Color sunrise) {
        hasGradients = true;
        dayColor = day;
        nightColor = night;
        sunsetColor = sunset;
        sunriseColor = sunrise;
    }

    public boolean isHasGradients() {
        return hasGradients;
    }

    public Color getDayColor() {
        return dayColor;
    }

    public Color getNightColor() {
        return nightColor;
    }

    public Color getSunsetColor() {
        return sunsetColor;
    }

    public Color getSunriseColor() {
        return sunriseColor;
    }
}
