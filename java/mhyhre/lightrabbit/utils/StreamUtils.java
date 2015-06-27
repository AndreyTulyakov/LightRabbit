/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.utils;

import java.io.Closeable;
import java.io.IOException;

public class StreamUtils {

    public static void silentClose(Closeable target) {
        
        if(target == null) {
            return;
        }
        
        try {
            target.close();
        } catch (IOException e) {
            // Do nothing
        }
    }
}