package com.APP.Project.UserInterface.models;

import java.util.*;
import java.util.stream.Collectors;
public class UserCommand {
    private static final String D_ARG_PREFIX = "-";

    private String d_headCommand;

    private final List<CommandLineArgument> d_commandArgumentList;

    private final Map<String, List<String>> d_userArguments;

    private CommandSpecification d_commandSpecification;

    public UserCommand() {
        d_commandArgumentList = new ArrayList<CommandLineArgument>();
        d_userArguments = new HashMap<>();
    }

    public String getHeadCommand() {
        return d_headCommand;
    }

    public void setHeadCommand(String p_headCommand) {
        d_headCommand = p_headCommand;
    }

    public List<CommandArgument> getCommandArgumentList() {
        return d_commandArgumentList;
    }

    public void pushCommandArgument(CommandArgument p_commandArgument) {
        d_commandArgumentList.add(p_commandArgument);
    }

    public Map<String, List<String>> getUserArguments() {
        return d_userArguments;
    }

    public void pushUserArgument(String argKey, List<String> values) {
        d_userArguments.put(argKey, values);
    }

    public List<String> getArgumentKeys() {
        return this.d_commandArgumentList.stream().map((CommandArgument::getArgumentKey))
                .collect(Collectors.toList());
    }

    public CommandArgument matchCommandArgument(String p_argumentKey) {
        // Returns only one element
        return this.d_commandArgumentList.stream().filter((p_p_argumentKey) ->
                p_argumentKey.equals(UserCommand.D_ARG_PREFIX.concat(p_p_argumentKey.getArgumentKey()))
        ).collect(Collectors.toList()).get(0);
    }

    public boolean isKeyOfCommand(String p_argKey) {
        if (!p_argKey.startsWith(UserCommand.D_ARG_PREFIX))
            return false;
        return this.getArgumentKeys().stream().anyMatch((p_p_argKey) ->
                p_argKey.equals(UserCommand.D_ARG_PREFIX.concat(p_p_argKey))
        );
    }

    public void setCommandSpecification(CommandSpecification p_commandSpecification) {
        this.d_commandSpecification = p_commandSpecification;
    }


    public CommandSpecification getCommandSpecification() {
        return d_commandSpecification;
    }

    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        UserCommand l_that = (UserCommand) l_p_o;
        return Objects.equals(d_headCommand, l_that.d_headCommand) &&
                Objects.equals(d_userArguments.keySet(), l_that.d_userArguments.keySet());
    }


}
