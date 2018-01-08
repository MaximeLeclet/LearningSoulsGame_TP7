package lsg.characters;

import lsg.armor.ArmorItem;
import lsg.bags.*;
import lsg.consumables.Consumable;
import lsg.consumables.drinks.Drink;
import lsg.consumables.food.Food;
import lsg.consumables.repair.RepairKit;
import lsg.exceptions.*;
import lsg.helpers.*;
import lsg.weapons.*;

import java.util.Locale;

public abstract class Character {

    public static final String LIFE_STAT_STRING = "life";
    public static final String STAM_STAT_STRING = "stamina";
    public static final String PROTECTION_STAT_STRING = "protection";
    public static final String BUFF_STAT_STRING = "buff";

    private String name;
    private int life;
    private int maxLife;
    private int stamina;
    private int maxStamina;
    private Dice dice;
    private Weapon weapon;
    private Consumable consumable;
    private Bag bag;

    public Character() {

        dice = new Dice(101);
        bag = new SmallBag();

    }

    public Character(String name) {
        this();
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return life;
    }

    protected void setLife(int life) {
        this.life = life;
    }

    public int getMaxLife() {
        return maxLife;
    }

    protected void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getStamina() {
        return stamina;
    }

    protected void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    protected void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Consumable getConsumable() {
        return consumable;
    }

    public void setConsumable(Consumable consumable) {
        this.consumable = consumable;
    }

    public boolean isAlive() {
        return(this.getLife()>0);
    }

    public int attack() throws WeaponNullException, WeaponBrokenException, StaminaEmptyException {
        return this.attackWith(weapon);
    }

    private int attackWith(Weapon weapon) throws WeaponNullException, WeaponBrokenException, StaminaEmptyException {

        if(weapon == null) {

            throw new WeaponNullException();

        }

        if(weapon.isBroken()) {

            throw new WeaponBrokenException(weapon);

        }

        if(this.getStamina() <= 0) {

            throw new StaminaEmptyException();

        }

        weapon.use();

        if(weapon.isBroken() || this.getStamina() <= 0) {

            return 0;

        }
        else {

            int precision = this.dice.roll();

            int degats = weapon.getMinDamage() + (weapon.getMaxDamage() - weapon.getMinDamage()) * precision/100;

            if(this.getStamina() < weapon.getStamCost()) {

                float proportion = ((float)this.getStamina()/(float)weapon.getStamCost());

                degats = Math.round(degats * proportion);

                this.setStamina(0);

            }
            else {
                this.setStamina(this.getStamina()-weapon.getStamCost());
            }

            degats = degats + Math.round(degats * computeBuff()/100.0f);

            return degats;

        }

    }

    public int getHitWith(int value) {

        float protection = this.computeProtection();

        int degats = (protection > 100.0) ? 0 : Math.round(value - (value * (protection/100.f)));

        degats = (this.getLife()-degats < 0) ? this.getLife() : degats;

        this.setLife(this.getLife()-degats);
        return degats;

    }

    public void consume() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException {

        if(consumable != null) { use(consumable); }

    }

    public void use(Consumable consumable) throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException {

        if(consumable == null) {

            throw new ConsumeNullException(consumable);

        }

        if(consumable instanceof Drink) {

            drink((Drink) consumable);

        }
        else if(consumable instanceof Food) {

            eat((Food) consumable);

        }
        else if(consumable instanceof RepairKit) {

            if(weapon == null) {

                throw new ConsumeRepairNullWeaponException();

            }

            repairWeaponWith((RepairKit) consumable);

        }

    }

    private void drink(Drink drink) throws ConsumeNullException, ConsumeEmptyException {

        if(drink == null) {

            throw new ConsumeNullException(drink);

        }

        System.out.println(name + " drinks " + drink.toString());
        int points = drink.use();
        stamina = (stamina + points > maxStamina) ? stamina + (maxStamina - stamina) : stamina + points;


    }

    private void eat(Food food) throws ConsumeNullException, ConsumeEmptyException {

        if(food == null) {

            throw new ConsumeNullException(food);

        }

        System.out.println(name + " eats " + food.toString());
        int points = food.use();
        life = (life + points > maxLife) ? life + (maxLife - life) : life + points;

    }

    private void repairWeaponWith(RepairKit kit) throws ConsumeNullException {

        if(kit == null) {

            throw new ConsumeNullException(kit);

        }

        try {

            System.out.println(name + " repairs " + weapon.toString() + " with " + kit.toString());
            weapon.repairWith(kit);

        } catch(Exception e) {

            e.printStackTrace();

        }

    }

    public void pickUp(Collectible item) throws NoBagException, BagFullException {

        if(bag == null) {

            throw new NoBagException();

        }

        System.out.println(name + " picks up " + weapon.toString());
        bag.push(item);

    }

    public Collectible pullOut(Collectible item) throws NoBagException {

        if(bag == null) {

            throw new NoBagException();

        }

        Collectible itemToPull = null;

        if(bag.contains(item)) {

            System.out.println(name + " pulls out " + item);
            itemToPull = bag.pop(item);

        }

        return itemToPull;

    }

    public void printBag() {

        System.out.println("BAG : " + bag);

    }

    public int getBagCapacity() throws NoBagException {

        if(bag == null) {

            throw new NoBagException();

        }

        return bag.getCapacity();

    }

    public int getBagWeight() throws NoBagException {

        if(bag == null) {

            throw new NoBagException();

        }

        return bag.getCapacity() - bag.getWeight();

    }

    public Collectible[] getBagItems() throws NoBagException {

        if(bag == null) {

            throw new NoBagException();

        }

        return bag.getItems();

    }

    public Bag setBag(Bag bag) throws BagFullException {

        if(bag == null) {

            System.out.println(name + " changes " + this.bag.getClass().getSimpleName() + " for null");

        }
        else {

            System.out.println(name + " changes " + this.bag.getClass().getSimpleName() + " for " + bag.getClass().getSimpleName());

        }

        Bag.transfer(this.bag, bag);
        Bag oldBag = this.bag;
        this.bag = bag;

        return oldBag;

    }

    public void equip(Weapon weapon) throws NoBagException {

        if(bag == null) {

            throw new NoBagException();

        }

        Collectible item = (Collectible)weapon;
        if(pullOut(item) != null) {

            System.out.println(name + " pulls out " + item + " and equips it !");
            this.weapon = weapon;

        }

    }

    public void equip(Consumable consumable) throws NoBagException {

        if(bag == null) {

            throw new NoBagException();

        }

        Collectible item = (Collectible)consumable;
        if(pullOut(item) != null) {

            System.out.println(name + " pulls out " + item + " and equips it !");
            this.consumable = consumable;

        }

    }

    private Consumable fastUseFirst(Class<? extends Consumable> type) throws ConsumeEmptyException, ConsumeNullException, ConsumeRepairNullWeaponException {

        Collectible[] items = bag.getItems();

        for(int i = 0; i < items.length; i++) {

            if(items[i].getClass() == type) {

                Consumable item = (Consumable)items[i];
                item.use();
                if(item.getCapacity() == 0) {

                    bag.pop(item);

                }

                return item;

            }

        }

        return null;

    }

    public Drink fastDrink() throws ConsumeEmptyException {

        System.out.println(name + " drinks FAST :");

        Drink drink = null;

        try {

            drink = (Drink) fastUseFirst(Drink.class);

        } catch(Exception e) {



        }

        return drink;

    }

    public Food fastEat() throws ConsumeEmptyException {

        System.out.println(name + " eats FAST :");

        Food food = null;

        try {

            food = (Food) fastUseFirst(Food.class);

        } catch (Exception e) {}

        return food;

    }

    public RepairKit fastRepair() throws ConsumeEmptyException, ConsumeNullException, ConsumeRepairNullWeaponException {

        System.out.println(name + " repairs FAST :");
        return (RepairKit) fastUseFirst(RepairKit.class);

    }

    public abstract float computeProtection();

    public abstract float computeBuff();

    public void printStats() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        String LIFE = String.format("%5d", this.getLife());
        String STAMINA = String.format("%5d",this.getStamina());
        return (String.format(Locale.US,"%-20s %-20s " + LIFE_STAT_STRING.toUpperCase() + ":%-10s " + STAM_STAT_STRING.toUpperCase() + ":%-10s " + PROTECTION_STAT_STRING.toUpperCase() + ": %-10s " + BUFF_STAT_STRING.toUpperCase() + ": %-10s", ("[ " + this.getClass().getSimpleName() + " ]"), this.getName(), LIFE, STAMINA, computeProtection(), computeBuff()) + (this.isAlive() ? "(ALIVE)" : "(DEAD)"));
    }

    public void printConsumable() {

        System.out.println("CONSUMABLE : " + consumable);

    }

}
