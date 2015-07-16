package io.jari.materialup.exeptions;

import android.accounts.NetworkErrorException;

/**
 * Created by rsicarelli on 7/15/15.
 */
public class ItemException extends NetworkErrorException {

    public ItemException(String message) {
        super(message);
    }

}
