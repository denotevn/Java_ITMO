package commands;

import exception.WrongAmountOfElementException;
import utility.CollectionManager;
import utility.ResponseOutputer;

import javax.swing.*;

public class SaveCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    /**
     * Command 'save'. Saves the collection to a file.
     */
    public SaveCommand(CollectionManager collectionManager){
        super("save"," "," save collection in file.");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean executed(String argument, Object ObjectArgument) {
        try{
            if (!argument.isEmpty() || ObjectArgument !=null) throw new WrongAmountOfElementException();
            collectionManager.saveCollection();
            ResponseOutputer.appendln("Success use command save.");
            return true;
        } catch (WrongAmountOfElementException e) {
            ResponseOutputer.appendln("Using: "+ getName()+" "+ getUsage()+ " ");
        }

        return false;
    }
    /**
     * Executes the command.
     * @return Command exit status.
     */
}
