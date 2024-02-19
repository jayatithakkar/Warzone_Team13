package com.APP.Project.UserInterface.services;

import com.APP.Project.Main;

import com.APP.Project.UserInterface.layouts.PlayerClassLayout;
import com.APP.Project.UserInterface.layouts.PlayerCommandLayout;
import com.APP.Project.UserInterface.models.UsersCommands;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserInterface.service.RequestsService;
import java.util.Arrays;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RequestsServiceTest {
    private UsersCommands d_usersCommands;

    public RequestsServiceTest() {
    }

    @BeforeClass
    public static void beforeClass() {
        Main l_app = new Main();
        l_app.handleApplicationStartup();
    }

    @Before
    public void before() {
        MapFeatureEngine.getInstance().initialise();
        this.d_usersCommands = new UsersCommands(PlayerCommandLayout.getUserCommand("editcontinent"));
        this.d_usersCommands.pushUserArgument("add", Arrays.asList("continentID", "12"));
        Main l_app = new Main();
        l_app.handleApplicationStartup();
    }

    @Test(expected = Test.None.class)
    public void testTakeAction() {
        RequestsService l_reqService = new RequestsService();
        l_reqService.takeAction(this.d_usersCommands);
    }
}
