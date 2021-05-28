package commands;

import data.SpaceMarine;
import exception.DatabaseHandlingException;
import exception.WrongAmountOfElementException;
import interaction.MarineRaw;
import interaction.User;
import utility.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;

public class AddCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;


    /**
     * Command 'add'. Adds a new element to collection.
     */

    public AddCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("add ","{element}", "add a new item to the collection.");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager =  databaseCollectionManager;
    }
    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean executed(String stringArgument, Object objectArgument, User user) {
        try{
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementException();
            MarineRaw marineRaw = (MarineRaw) objectArgument;
            collectionManager.add(databaseCollectionManager.insertMarine(marineRaw,user));
//            collectionManager.add(new SpaceMarine(
//                    collectionManager.generateNextId(),
//                    marineRaw.getName(),
//                    marineRaw.getCoordinates(),
//                    java.util.Date.from(Instant.now()),
//                    marineRaw.getHealth(),
//                    marineRaw.getCategory(),
//                    marineRaw.getWeaponType(),
//                    marineRaw.getMeleeWeapon(),
//                    marineRaw.getChapter()
//            ));
            ResponseOutputer.appendln("Success add marine !");
            return true;
        } catch (WrongAmountOfElementException e) {
            e.printStackTrace();
            ResponseOutputer.appenderror("The command entered is incorrect, " +
                    "please press help for more details");
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (DatabaseHandlingException e) {
            ResponseOutputer.appenderror ("An error occurred while accessing the database!");
        }
        return false;
    }
}
