package com.APP.Project;

import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserInterface.UserInterfaceClass;
import com.APP.Project.UserInterface.constants.states.GamingStateInfo;

/**
 * The execution of the game will start from this class
 *
 * @author Jayati Thakkar
 * @version 1.0
 */
public class Main {

    /**
     * If the user is playing, it will return false; true otherwise
     */
    private static volatile boolean d_ifRunning = true;

    /**
     * an instance of the user interface class
     */
    private static UserInterfaceClass d_CommandLineInterface;

    /**
     * an instance of the user core logic class
     */
    private static UserCoreLogic d_VirtualMachine;


    public Main(){
        // Creates interface for user interaction.
        // Just a local variable as the instance is not being used/shared with any other class.
        d_CommandLineInterface = new UserInterfaceClass();

        // Starts the runtime engine for the game.
        // Virtual Machine will have the UI middleware.
        d_VirtualMachine = UserCoreLogic.newInstance();

        // Attaches the CLI (stub) to VM.
        d_VirtualMachine.attachUIMiddleware(d_CommandLineInterface);
    }

    public static UserCoreLogic VIRTUAL_MACHINE() {
        return d_VirtualMachine;
    }

    /**
     * sets d_isRunning False if user is playing; true otherwise
     *
     * @param d_ifRunning
     */

    public static void setIfRunning(boolean d_ifRunning) {
        d_ifRunning = d_ifRunning;
    }

    /**
     * gets the state of the game
     *
     * @return
     */
    public static GamingStateInfo getGameState() {
        return d_VirtualMachine.getGameState();
    }

    /**
     * it handles the startup of the game engine
     */
    public void handleApplicationStartup() {
        setIfRunning(true);
        VIRTUAL_MACHINE().setGameState(GamingStateInfo.MAP_EDITOR);
    }

    /**
     * it handles the startup for the User Interface
     * @throws InterruptedException
     */

    public void handleCLIStartUp() throws InterruptedException {
        d_CommandLineInterface.d_thread.start();
        // Wait till the game is over.
        d_CommandLineInterface.d_thread.join();
    }

    /**
     * sets the value of the variable if the game is running
     * @return
     */
    public static boolean isRunning() {
        return d_ifRunning;
    }
    public static void main(String[] args) throws InterruptedException {
        setIfRunning(true);

        //An object of the interface for user to interact
        UserInterfaceClass l_userInteraction = new UserInterfaceClass();

        l_userInteraction.d_thread.start();

        //It waits until the game is over.
        l_userInteraction.d_thread.join();
    }

    /**
     * This is to exit the game
     */
    public static void exit() {
        Main.setIfRunning(false);
        UserCoreLogic.exit();
        System.exit(0);
    }
}