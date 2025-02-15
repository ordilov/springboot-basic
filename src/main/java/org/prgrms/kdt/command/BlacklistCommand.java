package org.prgrms.kdt.command;

import java.util.Locale;
import org.prgrms.kdt.service.BlacklistService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class BlacklistCommand implements Command {

  private final BlacklistService blacklistService;
  private final MessageSource messageSource;

  public BlacklistCommand(BlacklistService blacklistService,
      MessageSource messageSource) {
    this.blacklistService = blacklistService;
    this.messageSource = messageSource;
  }

  @Override
  public String execute() {
    var blacklistInfos = blacklistService.findAll();
    var menu = messageSource.getMessage("menu.blacklist", null, Locale.ROOT);
    return blacklistInfos.stream()
        .reduce(menu, (a, b) -> a + "\n" + b, String::concat);
  }
}