package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the adding and removing of continent
 *
 * @author Raj Kumar Ramesh
 */
public class ContinentServiceTest {
    private static ContinentAdapter d_ContinentService;

    /**
     * Runs before the test case class runs.
     * Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void beforeClass() {
        d_ContinentService = new ContinentAdapter();
    }

    /**
     * Re-initializes the continent list before test case run.
     */
    @Before
    public void beforeTestCase() { MapFeatureEngine.getInstance().initialise();}

    /**
     * Tests whether the wrong continent value is being shown or not.
     *
     * @throws InvalidInputException Throws if country value is not number.
     */
    @Test(expected = InvalidInputException.class)
    public void testWrongContinentValue() throws InvalidInputException {
        d_ContinentService.add("Asia", "StringValue");
    }

    /**
     * Tests whether the continent is successfully added and removed or not; Passes if continent is removed.
     *
     * @throws EntityNotFoundException Throws this exception if continent is not available in list.
     * @throws InvalidInputException   Throws this exception if exception when country value is not number.
     */
    @Test(expected = Test.None.class)
    public void testAddAndRemoveContinent() throws EntityNotFoundException, InvalidInputException{
        String l_responseOfAddOp = d_ContinentService.add("Asia", "10");
        assertNotNull(l_responseOfAddOp);

        String l_responseOfRemoveOp = d_ContinentService.remove("Asia");
        assertNotNull(l_responseOfRemoveOp);
    }
}