import Server.Server;
import commands.*;
import utility.*;

import java.util.logging.*;

public class AppServer {
    private static final int MAX_CLIENTS = 999;

    public static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static String databaseHost;
    private static String databaseUrl;
    private static String databaseUserName = "s291184";
    private static String databasePassword;

    public static final int PORT = 6789;
//    private static String databaseHost;
//    private static String databaseURL = "jdbc:postgresql://localhost:5678/studs";
//    private static String databaseUsername = "postgres";
//    private static String databasePassword = "postgres";

    public static void main(String[] args) {
        DatabaseHandler databaseHandler = new DatabaseHandler(databaseUrl, databaseUserName, databasePassword);
        DatabaseUserManager databaseUserManager = new DatabaseUserManager(databaseHandler);
        DatabaseCollectionManager databaseCollectionManager = new DatabaseCollectionManager(databaseHandler, databaseUserManager);
        InputChek check = new InputChek();
        CollectionManager collectionManager = new CollectionManager(databaseCollectionManager);

        CommandManager commandManager = new CommandManager(
                new AddCommand(collectionManager, databaseCollectionManager),
                new AddIfMinCommand(collectionManager, databaseCollectionManager),
                new ClearCommand(collectionManager, databaseCollectionManager),
                new ExecuteScriptCommand(),
                new ExitCommand(),
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new MaxByHealthCommand(collectionManager),
                new PrintAscendingCommand(collectionManager),
                new RemoveByHealthCommand(collectionManager, check, databaseCollectionManager),
                new RemoveByIdCommand(collectionManager, check, databaseCollectionManager),
                new RemoveLowerCommand(collectionManager, databaseCollectionManager),
                new ShowCommand(collectionManager),
                new SortCommand(collectionManager),
                new UpdateIdCommand(collectionManager, check, databaseCollectionManager),
                new ServerExitCommand(),
                new LoginCommand(databaseUserManager),
                new RegisterCommand(databaseUserManager)
        );
        Server server = new Server(PORT, MAX_CLIENTS, commandManager);
        server.run();
        databaseHandler.closeConnection();
    }
}
