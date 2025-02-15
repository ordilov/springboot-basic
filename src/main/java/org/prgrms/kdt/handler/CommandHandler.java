package org.prgrms.kdt.handler;

import javax.validation.ConstraintViolationException;
import org.prgrms.kdt.command.CommandFactory;
import org.prgrms.kdt.exception.TypedException;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.type.CommandType;
import org.prgrms.kdt.type.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class CommandHandler {

  private final Input input;
  private final Output output;
  private final CommandFactory commandFactory;
  private final Logger log = LoggerFactory.getLogger(CommandHandler.class);

  public CommandHandler(
      Input input,
      Output output,
      CommandFactory commandFactory) {
    this.input = input;
    this.output = output;
    this.commandFactory = commandFactory;
  }

  public void handle() {
    var commandType = CommandType.INIT;
    while (commandType != CommandType.EXIT) {
      output.printMenu();
      commandType = getCommandType(input.read());
      output.printLine(executeCommand(commandType));
    }
  }

  private CommandType getCommandType(String command) {
    try {
      return CommandType.valueOf(command.toUpperCase().trim());
    } catch (IllegalArgumentException e) {
      log.error("Invalid command input: {}", command);
      output.printLine(ErrorType.INVALID_MENU.getErrorMessage());
      return CommandType.INIT;
    }
  }

  private String executeCommand(CommandType commandType) {
    var command = commandFactory.getCommand(commandType);
    try {
      return command.execute();
    } catch (IllegalArgumentException | ConstraintViolationException | TypedException e) {
      return e.getMessage();
    }
  }
}