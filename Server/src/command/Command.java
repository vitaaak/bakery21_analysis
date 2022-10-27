package command;

import cooperation.ServerResponse;

public interface Command {
    ServerResponse execute();
}
