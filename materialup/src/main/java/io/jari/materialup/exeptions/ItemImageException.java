package io.jari.materialup.exeptions;

import android.accounts.NetworkErrorException;

/**
 * Created by rsicarelli on 7/16/15.
 */
public class ItemImageException extends NetworkErrorException{

    public ItemImageException(String message) {
        super(message);
    }
}
