package commands;

import data.SpaceMarine;
import exception.MarineIsEmptyCollection;
import exception.MarineNotFoundException;
import exception.WrongAmountOfElementException;
import interaction.MarineRaw;
import utility.CollectionManager;
import utility.MarineAsk;
import utility.ResponseOutputer;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

public class RemoveLowerCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    private MarineAsk MarineAsker;
    public RemoveLowerCommand(CollectionManager collectionManager){
        super("remove_lower"," {element}","remove from the collection all elements less than the given one");
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean executed(String argument, Object ObjectArgument) {
        try{
            if (!argument.isEmpty() || ObjectArgument == null) throw new WrongAmountOfElementException();
            if (collectionManager.collectionSize() == 0) throw new MarineIsEmptyCollection();
            MarineRaw marineRaw = (MarineRaw)ObjectArgument;
            SpaceMarine marineToFind = new SpaceMarine(
                    collectionManager.generateNextId(),
                    marineRaw.getName(),
                    marineRaw.getCoordinates(),
                    java.util.Date.from(Instant.now()),
                    marineRaw.getHealth(),
                    marineRaw.getCategory(),
                    marineRaw.getWeaponType(),
                    marineRaw.getMeleeWeapon(),
                    marineRaw.getChapter()
            );
            List<SpaceMarine> arrSpaceMarine = collectionManager.getByValue(marineToFind);
            if (arrSpaceMarine == null) throw new MarineNotFoundException();
            collectionManager.removeAllMarine(arrSpaceMarine);
            ResponseOutputer.appendln("Successfully deleted element");
            return true;
        }catch (WrongAmountOfElementException | MarineIsEmptyCollection e){
            e.printStackTrace();
            ResponseOutputer.appendln("Using "+ getName() +" "+ getUsage()+" ");
        } catch (MarineNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
