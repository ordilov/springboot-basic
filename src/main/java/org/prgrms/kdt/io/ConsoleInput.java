package org.prgrms.kdt.io;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInput implements Input {

  private final Scanner scanner = new Scanner(System.in);

  @Override
  public String read() {
    return scanner.nextLine();
  }

  @Override
  public Integer readInt() {
    return Integer.parseInt(scanner.nextLine().trim());
  }

  @Override
  public Long readLong() {
    return Long.parseLong(scanner.nextLine().trim());
  }
}