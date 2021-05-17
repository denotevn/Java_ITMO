package commands;

import data.*;
import exception.CollectionIsEmptyException;
import exception.MarineNotFoundException;
import exception.WrongAmountOfElementException;
import interaction.MarineRaw;
import utility.CollectionManager;
import utility.InputChek;
import utility.MarineAsk;
import utility.ResponseOutputer;

import java.io.IOException;

public class UpdateIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private InputChek inPutCheck;
    public UpdateIdCommand(CollectionManager collectionManager, InputChek inPutCheck){
        super("update: ", "ID {element}","update the value " +
                "of the collection element whose id is equal to the given");
        this.collectionManager = collectionManager;
        this.inPutCheck = inPutCheck;
    }

    @Override
    public boolean executed(String argument, Object ObjectArgument) {
        try{
            if (argument.isEmpty() || ObjectArgument == null) throw new WrongAmountOfElementException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            if (inPutCheck.longInValidCheck(argument,(long)0,Long.MAX_VALUE)){
                Long id = Long.parseLong(argument);
                if (id<0) throw new NumberFormatException();
                SpaceMarine marine = collectionManager.getById(id);
                if (marine == null) throw new MarineNotFoundException();

                MarineRaw marineRaw = (MarineRaw) ObjectArgument;

                String name = marineRaw.getName() == null ? marine.getName() : marineRaw.getName();
                Coordinates coordinates = marineRaw.getCoordinates() == null ? marine.getCoordinates() : marineRaw.getCoordinates();
                java.util.Date creationDate = marine.getCreationDate();
                long health = marineRaw.getHealth() == -1 ? marine.getHealth() : marineRaw.getHealth();
                AstartesCategory category = marineRaw.getCategory() == null ? marine.getCategory() : marineRaw.getCategory();
                Weapon weaponType = marineRaw.getWeaponType() == null ? marine.getWeaponType() : marineRaw.getWeaponType();
                MeleeWeapon meleeWeapon = marineRaw.getMeleeWeapon() == null ? marine.getMeleeWeapon() : marineRaw.getMeleeWeapon();
                Chapter chapter = marineRaw.getChapter() == null ? marine.getChapter() : marineRaw.getChapter();

                collectionManager.removeFromCollection(marine);

                collectionManager.add(new SpaceMarine(
                        id,
                        name,
                        coordinates,
                        creationDate,
                        health,
                        category,
                        weaponType,
                        meleeWeapon,
                        chapter
                ));

                ResponseOutputer.appendln("Success update marine ! ");
                return true;
            }
        } catch (WrongAmountOfElementException e) {
            ResponseOutputer.appendln("Using: "+ getName() + getUsage() + " ");
        } catch (CollectionIsEmptyException e) {
            e.printStackTrace();
        }catch (MarineNotFoundException e) {
            e.printStackTrace();
        }catch(NumberFormatException exception){
            ResponseOutputer.appenderror("The index id needs to be greater than 0 !");
        }
        return false;
    }
}
