package lsg.exceptions;

import lsg.weapons.Weapon;

import java.lang.reflect.Executable;

public class WeaponBrokenException extends Exception {

    private Weapon weapon;

    public WeaponBrokenException(Weapon weapon) {

        super(weapon.getName() + " is broken !");
        this.weapon = weapon;

    }

    public Weapon getWeapon() {

        return weapon;

    }

}
