package commands;

import data.SpaceMarine;
import exception.WrongAmountOfElementException;
import interaction.MarineRaw;
import utility.CollectionManager;
import utility.MarineAsk;
import utility.Outputer;
import utility.ResponseOutputer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;

public class AddCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    /**
     * Command 'add'. Adds a new element to collection.
     */

    public AddCommand(CollectionManager collectionManager) {
        super("add ","{element}", "add a new item to the collection.");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean executed(String stringArgument,Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementException();
            System.out.println(objectArgument);
            MarineRaw marineRaw = (MarineRaw) objectArgument;

            collectionManager.add(new SpaceMarine(
                    collectionManager.generateNextId(),
                    marineRaw.getName(),
                    marineRaw.getCoordinates(),
                    java.util.Date.from(Instant.now()),
                    marineRaw.getHealth(),
                    marineRaw.getCategory(),
                    marineRaw.getWeaponType(),
                    marineRaw.getMeleeWeapon(),
                    marineRaw.getChapter()
            ));
            System.out.println("from addcommand");
            ResponseOutputer.appendln("Success add marine !");
            return true;
        } catch (WrongAmountOfElementException e) {
            e.printStackTrace();
            ResponseOutputer.appenderror("The command entered is incorrect, " +
                    "please press help for more details");
        }
        return false;
    }
}
