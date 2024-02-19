package com.APP.Project.UserInterface.mappers;

import com.APP.Project.Main;
import com.APP.Project.UserInterface.layouts.PlayerCommandLayout;
import com.APP.Project.UserInterface.mappers.UserCommandsMapper;
import com.APP.Project.UserInterface.models.UsersCommands;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CommandMapperTest {
    private String d_commandWithArg;
    private String d_commandWithVal;
    private UsersCommands d_correctCommandWithArg;
    private UsersCommands d_correctCommandWithVal;

    public CommandMapperTest() {

    }

    @BeforeClass
    public static void beforeClass() {
        Main l_app = new Main();
        l_app.handleApplicationStartup();
    }

    @Before
    public void before() {
        MapFeatureEngine.getInstance().initialise();
        this.d_commandWithArg = "editcontinent -add Canada 10 -remove Continent";
        this.d_correctCommandWithArg = new UsersCommands(PlayerCommandLayout.getUserCommand("editcontinent"));
        this.d_correctCommandWithArg.pushUserArgument("add", Arrays.asList("continentID", "continentvalue"));
        this.d_correctCommandWithArg.pushUserArgument("remove", Collections.singletonList("continentID"));
        this.d_commandWithVal = "savemap filename";
        this.d_correctCommandWithVal = new UsersCommands(PlayerCommandLayout.getUserCommand("savemap"));
        this.d_correctCommandWithVal.setCommandValuesList(Collections.singletonList("filename"));
        Main l_app = new Main();
        l_app.handleApplicationStartup();
    }

    @Test
    public void testUserInput() {
        UserCommandsMapper l_userCommandsMapper = new UserCommandsMapper();
        UsersCommands l_commandWithArg = l_userCommandsMapper.toUserCommand(this.d_commandWithArg);
        UsersCommands l_commandWithValue = l_userCommandsMapper.toUserCommand(this.d_commandWithVal);
        Assert.assertEquals(l_commandWithArg, this.d_correctCommandWithArg);
        Assert.assertEquals(l_commandWithValue, this.d_correctCommandWithVal);
    }
}
