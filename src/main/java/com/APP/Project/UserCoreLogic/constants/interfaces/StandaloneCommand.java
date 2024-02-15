package com.APP.Project.UserCoreLogic.constants.interfaces;

import com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;

import java.util.*;

public interface StandaloneCommand {
     String execute(List<String> p_commandValues) throws UserCoreLogicException;
}
