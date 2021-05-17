import commands.*;
import utility.*;

import java.io.IOException;
import java.util.logging.*;

public class ServerApplication {
    public static final int PORT = 6789;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static Logger LOGGER = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        Handler consoleHandler;
        Handler fileHandler;
        try{
            //Creating consoleHandler and fileHandler
            consoleHandler = new ConsoleHandler();
            fileHandler  = new FileHandler("logback.log");

            //Assigning handlers to LOGGER object
            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);

            //Setting levels to handlers and LOGGER
            consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);

            LOGGER.config("Configuration done.");

            //Console handler removed
            LOGGER.removeHandler(consoleHandler);

            LOGGER.log(Level.FINE, "Finer logged");
        }catch( IOException exception){
            LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);
        }

        LOGGER.finer("Finest example on LOGGER handler completed.");
        //E:\Programing\Lab_06_new\fileInput.json
        String fileName = "fileInput.json";
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager();
        collectionManager.loadCollection(fileName);
        InputChek inputChek = new InputChek();
        CommandManager commandManager = new CommandManager(new AddCommand(collectionManager),
                new AddIfMinCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExecuteScriptCommand(),
                new ExitCommand(),
                new HelpCommand(collectionManager),
                new InfoCommand(collectionManager),
                new MaxByHealthCommand(collectionManager),
                new PrintAscendingCommand(collectionManager),
                new RemoveByHealthCommand(collectionManager,inputChek),
                new RemoveByIdCommand(collectionManager,inputChek),
                new RemoveLowerCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ShowCommand(collectionManager),
                new SortCommand(collectionManager),
                new UpdateIdCommand(collectionManager,inputChek));

        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler,collectionManager,fileName);
        server.run();
    }
}
