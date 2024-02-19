package  com.APP.Project.UserCoreLogic.map_features.adapters;

import  com.APP.Project.UserCoreLogic.exceptions.AbsentTagException;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URL;

/**
 * Test cases for the {@link EditMapAdapter} class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class EditMapAdapterTest {
    private EditMapAdapter d_editMapAdapter;
    private URL d_testFilePath;

    /**
     * This method runs before the test case runs. This method initializes different objects required to perform test.
     */
    @Before
    public void before() {
        d_editMapAdapter = new EditMapAdapter();
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_blank_data_fields.map");
    }

    /**
     * This is a method that performs actual test. It test passes if .map file consists of any empty field.
     *
     * @throws Exception IOException
     */
    @Test(expected = AbsentTagException.class)
    public void testLoadCorruptedMap() throws Exception {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapAdapter.handleLoadMap(l_url);
    }
}
