package com.APP.Project.UserInterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.APP.Project.InterfaceCoreMiddleware;
import com.APP.Project.Main;
import com.APP.Project.UserInterface.constants.states.UserInteractionState;
import com.APP.Project.UserInterface.exceptions.InvalidArgumentException;
import com.APP.Project.UserInterface.exceptions.InvalidCommandException;
import com.APP.Project.UserInterface.layouts.PlayerClassLayout;
import com.APP.Project.UserInterface.mappers.UserCommandsMapper;
import com.APP.Project.UserInterface.models.UsersCommands;
import com.APP.Project.UserInterface.service.RequestsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.io.InputStream;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class asks the user to enter the command, it is interpreted and redirected to its respective services for further processing.
 *
 * @author Jayati Thakkar
 * @version 1.0
 */

public class UserInterfaceClass implements Runnable, InterfaceCoreMiddleware {

    //used to convert user input to a form understandable by the engine
    private static UserCommandsMapper d_commandMapperForUser;

    //it indicates the current state of the user
    private UserInteractionState d_currentUserState = UserInteractionState.WAIT;

    /**
     * returns the current user state
     *
     * @return Value of the state of user interaction
     */

    public UserInteractionState getInteractionState() {
        return d_currentUserState;
    }

    /**
     * it sets the user's current state whether they are playing or waiting
     *
     * @param p_interactionState  indicates the state of the user
     */


    public void setInteractionState(UserInteractionState p_interactionState) {
        this.d_currentUserState = p_interactionState;
    }

    public final Thread d_thread;

    private Queue<UsersCommands> d_userCommandQueue = new LinkedList<>();

    private RequestsService d_requestService = new RequestsService();

    /**
     * It can lock the shared data
     */
    ReentrantLock d_reentrantLock = new ReentrantLock();

    /**
     * default constructor
     *
     * defines thread and assigns the mapper class
     */
    public UserInterfaceClass() {
        d_thread = new Thread(this);
        d_commandMapperForUser = new UserCommandsMapper();
        d_requestService = new RequestsService();
    }

    /**
     * Stream for the users to add the input
     * @param p_inputStream an object of input stream class for the user to enter the data from
     */

    public void setIn(InputStream p_inputStream) {
        System.setIn(p_inputStream);
    }

    /**
     *It waits for the user to input data
     * @return the string entered by the user
     * @throws IOException
     */

    private String waitForUserInput() throws IOException {
        // Enter data using BufferReader
        BufferedReader l_bufferedReader =
                new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        return l_bufferedReader.readLine();
    }

    /**
     * whenever thread is executed, this method is called
     */

    public void run() {
        while (Main.isRunning()) {
            try {
                try {
                    d_reentrantLock.lockInterruptibly();
                    if (this.getInteractionState() == UserInteractionState.WAIT) {
                        try {
                            // Takes user input and interprets it for further processing
                            UsersCommands l_userCommand = d_commandMapperForUser.toUserCommand(this.waitForUserInput());
                            this.setInteractionState(UserInteractionState.IN_PROGRESS);
                            // Takes action according to command instructions.
                            d_requestService.takeAction(l_userCommand);
                            if (l_userCommand.getPredefinedUserCommand().isGameEngineStartCommand()) {
                                this.setInteractionState(UserInteractionState.GAME_ENGINE);
                            } else {
                                this.setInteractionState(UserInteractionState.WAIT);
                            }
                        } catch (IOException p_e) {
                            p_e.printStackTrace();
                        }
                    }
                    if (!d_userCommandQueue.isEmpty()) {
                        UsersCommands l_userCommand = d_userCommandQueue.poll();
                        // Takes action according to command instructions.
                        d_requestService.takeAction(l_userCommand);
                    }
                } catch (InvalidArgumentException | InvalidCommandException e) {
                    // Show exception message
                    // In Graphical User Interface, we can show different modals respective to the exception.
                    System.out.println(e.getMessage());

                    if (this.getInteractionState() == UserInteractionState.IN_PROGRESS) {
                        this.setInteractionState(UserInteractionState.WAIT);
                    }
                } finally {
                    d_reentrantLock.unlock();
                }
            } catch (InterruptedException l_ignored) {
            }
        }
    }

    /**
     * it asks user to input the command in String format.
     * @param p_message message that asks user to input
     * @return
     */

    @Override
    public String askForUserInput(String p_message) {
        try {
            d_reentrantLock.lockInterruptibly();
            try {
                // Print the message if any.
                if (p_message != null && !p_message.isEmpty()) {
                    System.out.println(p_message);
                }
                ObjectMapper mapper = new ObjectMapper();
                UsersCommands l_userCommand = d_commandMapperForUser.toUserCommand(this.waitForUserInput());
                if (l_userCommand.getPredefinedUserCommand().isGameEngineCommand()) {
                    return mapper.writeValueAsString(l_userCommand);
                } else {
                    d_userCommandQueue.add(l_userCommand);
                }
                return "";
            } catch (IOException p_ioException) {
                return "";
            } catch (InvalidCommandException | InvalidArgumentException p_exception) {
                this.stderr(p_exception.getMessage());
                return "";
            }
        } catch (InterruptedException p_e) {
            return "";
        } finally {
            d_reentrantLock.unlock();
        }
    }

    /**
     *
     * @param p_message this is the message to print
     */

    public void stdout(String p_message) {
        if (p_message.equals("GAME_ENGINE_TO_WAIT")) {
            this.setInteractionState(UserInteractionState.WAIT);
        } else {
            System.out.println(p_message);
        }
    }

    /**
     *
     * @param p_message the message that is to be shown to the user
     */

    public void stderr(String p_message) {
        System.out.println(p_message);
    }
}
