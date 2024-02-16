package com.APP.Project.UserInterface;

import com.APP.Project.Main;
import com.APP.Project.UserInterface.constants.states.UserInteractionState;
import com.APP.Project.UserInterface.mappers.UserCommandsMapper;
import com.APP.Project.UserInterface.models.UserCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class UserInterfaceClass implements Runnable{

    private static UserCommandsMapper d_userCommandMapper;
    private UserInteractionState d_interactionState = UserInteractionState.WAIT;

    public UserInteractionState getInteractionState() {
        return d_interactionState;
    }

    public void setInteractionState(UserInteractionState p_interactionState) {
        this.d_interactionState = p_interactionState;
    }

    public final Thread d_thread;

    public UserInterfaceClass() {
        d_thread = new Thread(this);
        d_userCommandMapper = new UserCommandsMapper();
    }

    private String waitForUserInput() throws IOException {
        // Enter data using BufferReader
        BufferedReader l_bufferedReader =
                new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        return l_bufferedReader.readLine();
    }

    public void run() {
        while (Main.isRunning()) {
            try {
                if (this.getInteractionState() == UserInteractionState.WAIT) {
                    try {
                        // Takes user input and interprets it for further processing
                        UserCommand l_userCommand = d_userCommandMapper.toUserCommand(this.waitForUserInput());
                        // Takes action according to command instructions.
                        this.takeAction(l_userCommand);
                    } catch (IOException p_e) {
                        p_e.printStackTrace();
                    }
                }
            } catch (InvalidArgumentException | InvalidCommandException e) {
                // Show exception message
                // In Graphical User Interface, we can show different modals respective to the exception.
                System.out.println(e.getMessage());
            }
        }
    }
    public void takeAction(UserCommand p_userCommand) throws InvalidArgumentException, InvalidCommandException {
        try {
            // Gets the mapped class of the command and calls its function; With arguments, if any.
            Class<?> l_class = Class.forName(PlayerClassLayout.GetClassName(p_userCommand.getHeadCommand()));
            Object l_object = l_class.getDeclaredConstructor().newInstance();

            // If the command does not have any argument keys
            if (p_userCommand.getUserArguments().size() == 0) {
                // Call the class constructor in this case
                // This type of commands only represents the data
                Constructor<?> l_constructor = l_object.getClass().getConstructor();
                l_constructor.newInstance();
            } else {
                // Iterate over the user arguments
                for (Map.Entry<String, List<String>> entry : p_userCommand.getUserArguments().entrySet()) {
                    String p_argKey = entry.getKey();
                    List<String> p_argValues = entry.getValue();
                    Method l_methodReference;

                    // If the argument key does not have any value
                    if (p_argValues.size() == 0) {
                        l_methodReference = l_object.getClass().getMethod(p_argKey);
                        l_methodReference.invoke(l_object);
                    } else {
                        // Create two arrays:
                        // 1. For type of the value
                        // 2. For the values
                        Class<?>[] l_valueTypes = new Class[p_argValues.size()];
                        Object[] l_values = p_argValues.toArray();
                        for (int l_argIndex = 0; l_argIndex < p_argValues.size(); l_argIndex++) {
                            l_valueTypes[l_argIndex] = String.class;
                        }
                        // Get the reference and call the method with arguments
                        l_methodReference = l_object.getClass().getMethod(p_argKey, l_valueTypes);
                        l_methodReference.invoke(l_object, l_values);
                    }
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException p_e) {
            throw new InvalidCommandException("Command not found!");
        } catch (NoSuchMethodException | InvocationTargetException p_e) {
            throw new InvalidArgumentException("Unrecognized argument and/or its values");
        }
    }
}
