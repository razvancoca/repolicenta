package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DBConnectionTest.class, FacturaTest.class, InregistrareFacturaTest.class })
public class AllTests {

}
