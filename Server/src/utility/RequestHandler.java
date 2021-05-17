package utility;

import interaction.Request;
import interaction.Response;
import interaction.ResponseCode;


/**
 * Handles requests.
 */

public class RequestHandler {
    private final CommandManager commandManager;
    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * Handles requests.
     *
     * @param request Request to be processed.
     * @return Response to request.
     */
    public Response handle(Request request) {
        ResponseCode responseCode = executeCommand(
                request.getCommandName(),
                request.getCommandStringArgument(),
                request.getCommandObjectArgument());
        return new Response(responseCode, ResponseOutputer.getAndClear());
    }
    /**
     * Executes a command from a request.
     *
     * @param command               Name of command.
     * @param commandStringArgument String argument for command.
     * @param commandObjectArgument Object argument for command.
     * @return Command execute status.
     */
    private ResponseCode executeCommand(String command, String commandStringArgument,
                                        Object commandObjectArgument) {
        switch (command){
            case "":
                break;
            case "help":
                if (!commandManager.help(commandStringArgument,commandObjectArgument)) return ResponseCode.ERROR;
                break;
            case "add":
                if (!commandManager.add(commandStringArgument,commandObjectArgument)) return ResponseCode.ERROR;
                break;
            case "add_if_min":
                if (!commandManager.addIfMin(commandStringArgument,commandObjectArgument)) return ResponseCode.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "execute_script":
                if (!commandManager.executeScript(commandStringArgument,commandObjectArgument))
                   return ResponseCode.ERROR;
                break;
            case "exit":
                if (!commandManager.exit(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                return ResponseCode.SERVER_EXIT;
            case "info":
                if (!commandManager.info(commandStringArgument,commandObjectArgument ))
                    return ResponseCode.ERROR;
                break;
            case "max_by_health":
                if (!commandManager.maxByHealth(commandStringArgument,commandObjectArgument ))
                    return ResponseCode.ERROR;
                break;
            case "print_ascending":
                if (!commandManager.printAscending(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_all_by_health":
                if (!commandManager.removeByHealth(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_lower":
                if(!commandManager.removeLower(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "show":
                if (!commandManager.show(commandStringArgument,commandObjectArgument ))
                    return ResponseCode.ERROR;
                break;
            case "sort":
                if (!commandManager.sort(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "update":
                if (!commandManager.updateById(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            default:
                ResponseOutputer.appendln("Command "+command+" not found");
                return ResponseCode.ERROR;
        }
        return ResponseCode.OK;

    }

}
