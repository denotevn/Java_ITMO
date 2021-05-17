package interaction;

import data.*;

import java.io.Serializable;
/**
 * Class for get Marines value.
 */
public class MarineRaw implements Serializable {
    private String name;
    private Coordinates coordinates;
    private java.util.Date date;
    private long health;
    private AstartesCategory category;
    private Weapon weaponType;
    private MeleeWeapon meleeWeapon;
    private Chapter chapter;

    public MarineRaw(String name, Coordinates coordinates, long health, AstartesCategory category, Weapon weaponType,
                     MeleeWeapon meleeWeapon, Chapter chapter) {
        this.name = name;
        this.coordinates = coordinates;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }
    /**
     * @return Name of the marine.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Coordinates of the marine.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return Health of the marine*/
    public long getHealth() {
        return health;
    }

    /**
     * @return Category of the marine.
     */
    public AstartesCategory getCategory() {
        return category;
    }


    /**
     * @return Weapon type of the marine.
     */
    public Weapon getWeaponType() {
        return weaponType;
    }

    /**
     * @return Melee weapon of the marine.
     */
    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    /**
     * @return Chapter of the marine.
     */
    public Chapter getChapter() {
        return chapter;
    }

    @Override
    public String toString() {
        String info = "";
        info += "Marine";
        info += "\n Name: " + name;
        info += "\n Coordinates: " + coordinates;
        info += "\n Health: " + health;
        info += "\n Category: " + category;
        info += "\n WeaponType: " + weaponType;
        info += "\n MeleeWeapon: " + meleeWeapon;
        info += "\n Chapter: " + chapter;
        return info;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + coordinates.hashCode() + (int) health + category.hashCode() + weaponType.hashCode() +
                meleeWeapon.hashCode() + chapter.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof SpaceMarine){
            SpaceMarine marineObj = (SpaceMarine) obj;
            return name.equals(marineObj.getName()) && coordinates.equals(marineObj.getCoordinates()) &&
                    (health == marineObj.getHealth()) && (category == marineObj.getCategory()) &&
                    (weaponType == marineObj.getWeaponType()) && (meleeWeapon == marineObj.getMeleeWeapon()) &&
                    chapter.equals(marineObj.getChapter());
        }
        return false;
    }
}

