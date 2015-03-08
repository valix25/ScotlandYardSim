package scotlandyard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import junit.framework.TestCase;
//import org.junit.Assert.*;

public class TestDet extends TestCase {

    
    @Test
    public void testTaxiTokens(){
        // Detective class tested
        Detective d_test = new Detective();
        
        // some functions tested
        assertEquals("Starting 10 taxi tokens ",10, d_test.taxi_tokens());
        assertEquals("subway tokens should be subway ",d_test.subway_tokens(),d_test.tokens("subway"));
    }
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    Detective d_t = new Detective();
    @Test //(expected = IllegalArgumentException.class)
    public void testTokensEx(){
       try{
        d_t.tokens("blabla");
        fail("should throw exception");
       } catch(IllegalArgumentException e){
           //expected
       }
    }
    
}
