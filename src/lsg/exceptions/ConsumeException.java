package lsg.exceptions;

import lsg.consumables.Consumable;

public abstract class ConsumeException extends Exception {

    private String messsage;
    private Consumable consumable;

    public ConsumeException(String message, Consumable consumable) {

        super(message);
        this.messsage = message;
        this.consumable = consumable;

    }

    public Consumable getConsumable() {
        return consumable;
    }
}
